package cz.fg.issuetracking.api.procedure;

/**
 * Procedure context
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         24.8.13 21:30
 */
public interface ProcedureContext {

    /**
     * Get current version
     * @return current version number (releasing)
     */
    public String getCurrentVersion();

    /**
     * Get next version
     * @return next version number (next development after release)
     */
    public String getNextVersion();

    public void setCurrentVersion(String currentVersion);

    public void setNextVersion(String nextVersion);

}