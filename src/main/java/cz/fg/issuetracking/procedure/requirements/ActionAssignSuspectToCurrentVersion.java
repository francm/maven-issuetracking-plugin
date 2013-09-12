package cz.fg.issuetracking.procedure.requirements;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.procedure.ActionProcedure;
import cz.fg.issuetracking.api.procedure.ProcedureContext;

import java.util.List;

/**
 * Assign all under development suspect to current version
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:22
 */
public class ActionAssignSuspectToCurrentVersion implements ActionProcedure {

    IssueManager issueManager;

    @Override
    public void process(ProcedureContext ctx) {
        String currentVersion = ctx.getCurrentVersion();
        List<Issue> issues = issueManager.getUnderDevelopmentSuspectsIssues();
        String[] ids = new String[issues.size()];
        issueManager.assignVersion(currentVersion,ids);
    }
}
