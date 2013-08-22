package cz.fg.issuetracking.api;

/**
 * Manager factory
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         22.8.13 16:46
 */
public interface ManagerFactory {

    /**
     * Get issue manager implementation
     * @return manager instance
     */
    public IssueManager getIssueManager();

    /**
     * Get version manager implementation
     * @return manager instance
     */
    public VersionManager getVersionManager();

}
