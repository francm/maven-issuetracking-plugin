package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Journal;
import com.taskadapter.redmineapi.bean.User;
import com.taskadapter.redmineapi.bean.Version;
import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.IssueImpl;
import cz.fg.issuetracking.api.IssueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Issue manager Redmine implementation
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 21:00
 */
public class RedmineIssueManager implements IssueManager {

    static final Logger log = LoggerFactory.getLogger(RedmineIssueManager.class);

    static final String EQUALS ="=";
    static final String NOT_EQUALS ="!";
    static final String NOTHING="!*";
    static final String ALL="*";
    static final String OR="|";

    RedmineProject project;

    public RedmineIssueManager(RedmineProject project) {
        this.project = project;
    }

    protected List<Issue> getIssues(Map<String,String> params) {
        log.debug("Get issues {}",params);
        try {
            List<com.taskadapter.redmineapi.bean.Issue> issues = project.getRedmineManager().getIssues(params);
            List<Issue> result = new ArrayList<Issue>(issues.size());
            for (com.taskadapter.redmineapi.bean.Issue issue : issues) {
                result.add(convert(issue));
            }
            return result;
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
    }

    private Issue convert(com.taskadapter.redmineapi.bean.Issue issue) {
        com.taskadapter.redmineapi.bean.Version targetVersion = issue.getTargetVersion();
        String version = targetVersion==null?null:targetVersion.getName();
        User user = issue.getAssignee();
        String developer = user==null?null:convert(user);
        Issue result = new IssueImpl(issue.getId().toString(),issue.getSubject(),issue.getDescription(),null,developer,version,false);
        log.debug("Issue converted from Redmine object: {}", issue);
        return result;
    }

    private String convert(User user) {
        StringBuilder r = new StringBuilder();
        if (user.getFirstName()!=null) {
            r.append(user.getFirstName());
            r.append(" ");
        }
        if (user.getLastName()!=null) {
            r.append(user.getLastName());
            r.append(" ");
        }
        r.deleteCharAt(r.length() - 1);
        return r.toString();
    }

    @Override
    public List<Issue> getSleepingIssues() {
        Map<String, String> p = createParams();
        p.put("status_id",NOT_EQUALS + project.getStateResolved() + OR + project.getStateClosed() );
        p.put("fixed_version_id",NOTHING);
        return getIssues(p);
    }

    @Override
    public List<Issue> getSolvedIssues() {
        Map<String, String> p = createParams();
        p.put("status_id",EQUALS + project.getStateResolved());
        p.put("fixed_version_id",project.getCurrentVersionId());
        return getIssues(p);
    }

    @Override
    public List<Issue> getUnderDevelopmentIssues() {
        Map<String, String> p = createParams();
        p.put("status_id",NOT_EQUALS + project.getStateResolved() + OR + project.getStateClosed());
        p.put("fixed_version_id",project.getCurrentVersionId());
        return getIssues(p);
    }

    @Override
    public List<Issue> getUnderDevelopmentSuspectsIssues() {
        Map<String, String> p = createParams();
        p.put("status_id",EQUALS + project.getStateInProgress());
        p.put("fixed_version_id",NOTHING);
        List<Issue> issues = getIssues(p);
        List<Issue> result = new ArrayList<Issue>(issues.size());
        for (Issue issue : issues) {
            if ( isUnderDevelopmentSuspect(issue) ) {
                result.add(issue);
            }
        }
        return result;
    }

    @Override
    public List<Issue> getReleasedIssues() {
        Map<String, String> p = createParams();
        p.put("status_id",EQUALS + project.getStateClosed());
        p.put("fixed_version_id",ALL);
        return getIssues(p);
    }

    @Override
    public void closeIssues(String versionValue) {
        Map<String, String> p = createParams();
        p.put("status_id",NOT_EQUALS + project.getStateClosed());
        p.put("fixed_version_id",project.getVersionIdByName(versionValue).toString());
        try {
            List<com.taskadapter.redmineapi.bean.Issue> issues = project.getRedmineManager().getIssues(p);
            for (com.taskadapter.redmineapi.bean.Issue issue : issues) {
                issue.setStatusId(Integer.valueOf(project.getStateClosed()));
                issue.setDoneRatio(100);
                project.getRedmineManager().update(issue);
            }
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void assignVersion(String versionValue, String... issueIds) {
        try {
            Version version = new Version();
            version.setId(project.getVersionIdByName(versionValue));
            version.setName(versionValue);
            for (String issueId : issueIds) {
                com.taskadapter.redmineapi.bean.Issue issue = project.getRedmineManager()
                        .getIssueById(Integer.valueOf(issueId));
                issue.setTargetVersion(version);
                project.getRedmineManager().update(issue);
            }
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Issue createIssue(String name, String content, String developer) {
        com.taskadapter.redmineapi.bean.Issue redmineIssue = new com.taskadapter.redmineapi.bean.Issue();
        redmineIssue.setSubject(name);
        redmineIssue.setDescription(content);
        redmineIssue.setCustomFields(project.getCustomFields());
        redmineIssue.setAuthor(project.getUserByName(developer));
        try {
            com.taskadapter.redmineapi.bean.Issue createdIssue = project.getRedmineManager().createIssue(project.getProjectId(), redmineIssue);
            return convert(createdIssue);
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
    }

    protected Map<String,String> createParams() {
        Map<String,String> params = new HashMap<String, String>();
        params.put("set_filter","1");
        params.put("project_id",project.getProjectId());
        return params;
    }

    protected boolean isUnderDevelopmentSuspect(Issue issue) {
        try {
            Integer id = Integer.valueOf(issue.getId());
            log.debug("Get issue {}",id);
            com.taskadapter.redmineapi.bean.Issue redmineIssue = project.getRedmineManager().getIssueById(id, RedmineManager.INCLUDE.journals);
            boolean result = isIssueWithSpentHours(redmineIssue) || isIssueWithDoneRatio(redmineIssue) || isIssueWithCommits(redmineIssue);
            if ( result ) {
                log.debug("Issue {} is suspect as under development",redmineIssue);
            }
            return result;
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isIssueWithCommits(com.taskadapter.redmineapi.bean.Issue issue) {
        List<Journal> journals = issue.getJournals();
        boolean commits = false;
        for (Journal journal : journals) {
            if (isCommitJournal(journal)) {
                commits=true;
                break;
            }
        }
        return commits;
    }

    protected boolean isCommitJournal(Journal journal) {
        User user = journal.getUser();
        String login = user.getLogin();
        String firstName = user.getFirstName();
        return login!=null?login.equals(project.getScmUsername()):firstName.equals(project.getScmUsername());
    }

    protected boolean isIssueWithSpentHours(com.taskadapter.redmineapi.bean.Issue issue) {
        Float spentHours = issue.getSpentHours();
        return spentHours>0.5f;
    }

    protected boolean isIssueWithDoneRatio(com.taskadapter.redmineapi.bean.Issue issue) {
        Integer doneRatio = issue.getDoneRatio();
        return doneRatio!=0;
    }

}