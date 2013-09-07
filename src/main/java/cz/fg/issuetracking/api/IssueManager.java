package cz.fg.issuetracking.api;

import java.util.List;

/**
 * Issue manager - provide list of issues from issue tracking system
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 20:54
 */
public interface IssueManager {

    /**
     * Sleeping issues / not assigned to current version
     * @return list of issues or empty list
     */
    public List<Issue> getSleepingIssues();

    /**
     * Solved issues ready to be released / assigned to current version
     * @return list of issues or empty list
     */
    public List<Issue> getSolvedIssues();

    /**
     * Issues under development / assigned to current version
     * @return list of issues or empty list
     */
    public List<Issue> getUnderDevelopmentIssues();

    /**
     * Issues which could be assigned to current version
     * @return list of issues or empty list
     */
    public List<Issue> getUnderDevelopmentSuspectsIssues();

    /**
     * Solved issues assigned to already released versions
     * @return list of issues or empty list
     */
    public List<Issue> getReleasedIssues();

    /**
     * Create new issue
     * @param name issue name
     * @param content issue content
     * @param developer developer
     * @return new issue instance with new id set
     */
    public Issue createIssue(String name,String content,String developer);

}
