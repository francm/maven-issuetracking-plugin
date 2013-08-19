package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.IssueImpl;
import cz.fg.issuetracking.api.IssueManager;

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

    RedmineManager redmine;
    String project;

    public RedmineIssueManager(RedmineManager redmine) {
        this.redmine = redmine;
    }

    protected List<Issue> getIssues(Map<String,String> params) {
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
        //VersionDescriptor version = targetVersion==null?null:new VersionDescriptor(targetVersion.getName());
        String version = targetVersion==null?null:targetVersion.getName();
        Issue result = new IssueImpl(issue.getId().toString(),issue.getSubject(),null,null,version,false);
        return result;
    }

    @Override
    public List<Issue> getSleepingIssues() {
        return getIssues(createParams());
    }

    @Override
    public List<Issue> getSolvedIssues() {
        return getIssues(createParams());
    }

    @Override
    public List<Issue> getUnderDevelopmentIssues() {
        return getIssues(createParams());
    }

    @Override
    public List<Issue> getUnderDevelopmentSuspectsIssues() {
        return getIssues(createParams());
    }

    @Override
    public List<Issue> getReleasedIssues() {
        return getIssues(createParams());
    }

    protected Map<String,String> createParams() {
        Map<String,String> params = new HashMap<String, String>();
        params.put("project_id",project);
        return params;
    }

}
