package cz.fg.maven.issuetracking.api;

import java.util.List;

/**
 * API to issue tracking system / Redmine
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 20:54
 */
public interface IssueManager {

    public List<Issue> getSleepingIssues();
    public List<Issue> getSolvedIssues();
    public List<Issue> getUnderDevelopmentIssues();
    public List<Issue> getUnderDevelopmentSuspectsIssues();
    public List<Issue> getReleasedIssues();

}
