package cz.fg.issuetracking.api.procedure;

/**
 * Procedure context impl
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         24.8.13 21:31
 */
public class ProcedureContextImpl implements ProcedureContext {

    String currentVersion;
    String nextVersion;

    @Override
    public String getCurrentVersion() {
        return currentVersion;
    }

    @Override
    public String getNextVersion() {
        return nextVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setNextVersion(String nextVersion) {
        this.nextVersion = nextVersion;
    }

}