package cz.fg.issuetracking.procedure.requirements;

import cz.fg.issuetracking.api.VersionManager;
import cz.fg.issuetracking.api.procedure.CheckRequirements;
import cz.fg.issuetracking.api.procedure.ProcedureContext;

/**
 * Create new version
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:20
 */
public class ReleaseFinishCreateNewVersion implements CheckRequirements {

    VersionManager versionManager;

    @Override
    public void check(ProcedureContext context) {
        versionManager.createVersion(context.getNextVersion());
    }

}
