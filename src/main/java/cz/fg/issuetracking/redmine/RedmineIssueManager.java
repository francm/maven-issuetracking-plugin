package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Journal;
import com.taskadapter.redmineapi.bean.User;
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

    RedmineManager redmine;
    String project;
    String currentVersionId;

    public RedmineIssueManager(RedmineManager redmine, String project,String currentVersionId) {
        this.redmine = redmine;
        this.project = project;
        this.currentVersionId = currentVersionId;
    }

    protected List<Issue> getIssues(Map<String,String> params) {
        log.debug("Get issues {}",params);
        try {
            List<com.taskadapter.redmineapi.bean.Issue> issues = redmine.getIssues(params);
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
        Issue result = new IssueImpl(issue.getId().toString(),issue.getSubject(),null,developer,version,false);
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
        r.deleteCharAt(r.length()-1);
        return r.toString();
    }

    @Override
    public List<Issue> getSleepingIssues() {
        Map<String, String> p = createParams();
        p.put("status_id","!6|7");
        p.put("fixed_version_id","!*");
        return getIssues(p);
    }

    @Override
    public List<Issue> getSolvedIssues() {
        Map<String, String> p = createParams();
        p.put("status_id","=6");
        p.put("fixed_version_id", currentVersionId);
        return getIssues(p);
    }

    @Override
    public List<Issue> getUnderDevelopmentIssues() {
        Map<String, String> p = createParams();
        p.put("status_id","!6|7");
        p.put("fixed_version_id", currentVersionId);
        return getIssues(p);
    }

    @Override
    public List<Issue> getUnderDevelopmentSuspectsIssues() {
        Map<String, String> p = createParams();
        p.put("status_id","=4");
        p.put("fixed_version_id","!*");
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
        p.put("status_id","=7");
        p.put("fixed_version_id","*");
        return getIssues(p);
    }

    protected Map<String,String> createParams() {
        Map<String,String> params = new HashMap<String, String>();
        params.put("set_filter","1");
        params.put("project_id",project);
        return params;
    }

    protected boolean isUnderDevelopmentSuspect(Issue issue) {
        try {
            Integer id = Integer.valueOf(issue.getId());
            log.debug("Get issue {}",id);
            com.taskadapter.redmineapi.bean.Issue redmineIssue = redmine.getIssueById(id, RedmineManager.INCLUDE.journals);
            boolean result = isIssueWithSpentHours(redmineIssue) || isIssueWithDoneRatio(redmineIssue) || isIssueWithCommits(redmineIssue);
            if ( result ) {
                log.debug("Issue {} is spsect as under development",redmineIssue);
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
        return login!=null?login.equals("SCM"):firstName.equals("SCM");
    }

    protected boolean isIssueWithSpentHours(com.taskadapter.redmineapi.bean.Issue issue) {
        Float spentHours = issue.getSpentHours();
        return spentHours>0.5f;
    }

    protected boolean isIssueWithDoneRatio(com.taskadapter.redmineapi.bean.Issue issue) {
        Integer doneRatio = issue.getDoneRatio();
        return doneRatio!=0;
    }

    /**
     * Operators
     * = is
     * ! is not
     * !* nothing
     * * all
     * @return
     */


}