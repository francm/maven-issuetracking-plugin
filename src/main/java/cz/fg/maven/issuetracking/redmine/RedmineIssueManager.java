package cz.fg.maven.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineManager;
import cz.fg.maven.issuetracking.api.Issue;
import cz.fg.maven.issuetracking.api.IssueManager;

import java.util.List;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 21:00
 */
public class RedmineIssueManager implements IssueManager {

    @Override
    public List<Issue> getSleepingIssues() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Issue> getSolvedIssues() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Issue> getUnderDevelopmentIssues() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Issue> getUnderDevelopmentSuspectsIssues() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Issue> getReleasedIssues() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    RedmineManager redmine;


}
