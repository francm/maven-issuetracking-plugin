package cz.fg.issuetracking.procedure.requirements;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.procedure.CheckProcedure;
import cz.fg.issuetracking.api.procedure.ProcedureContext;

import java.util.List;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:16
 */
public class CheckAtLeastOneIssueAssignedToCurrentVersion implements CheckProcedure {

    IssueManager issueManager;

    @Override
    public void check(ProcedureContext context) {
        List<Issue> solvedIssues = issueManager.getSolvedIssues();
        if (solvedIssues.size()==0) {
            throw new IllegalStateException("No issues exists. Create at least one issue.");
        }
    }
}
