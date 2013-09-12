package cz.fg.issuetracking.procedure.requirements;

import cz.fg.issuetracking.api.VersionManager;
import cz.fg.issuetracking.api.procedure.ActionProcedure;
import cz.fg.issuetracking.api.procedure.CheckProcedure;
import cz.fg.issuetracking.api.procedure.ProcedureContext;

/**
 * Create new version
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:20
 */
public class ActionCreateNextVersion implements ActionProcedure {

    VersionManager versionManager;

    @Override
    public void process(ProcedureContext ctx) {
        versionManager.createVersion(ctx.getNextVersion());
    }

}
