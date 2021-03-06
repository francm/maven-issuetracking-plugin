package cz.fg.issuetracking.procedure.action;

import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.IssueState;
import cz.fg.issuetracking.api.VersionManager;
import cz.fg.issuetracking.api.procedure.ActionProcedure;
import cz.fg.issuetracking.api.procedure.ProcedureContext;

/**
 * Close current version
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:20
 */
public class ActionCloseCurrentVersion implements ActionProcedure {

    VersionManager versionManager;
    IssueManager issueManager;

    @Override
    public void process(ProcedureContext ctx) {
        String version = ctx.getCurrentVersion();
        issueManager.setIssueStateInVersion(IssueState.RELEASED,version);
        versionManager.releaseVersion(version);
    }

}
