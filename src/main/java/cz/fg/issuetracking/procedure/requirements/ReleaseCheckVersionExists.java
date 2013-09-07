package cz.fg.issuetracking.procedure.requirements;

import cz.fg.issuetracking.api.Version;
import cz.fg.issuetracking.api.VersionManager;
import cz.fg.issuetracking.api.procedure.CheckRequirements;
import cz.fg.issuetracking.api.procedure.ProcedureContext;

import java.util.List;

/**
 * Check if released version exists
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:19
 */
public class ReleaseCheckVersionExists implements CheckRequirements {

    VersionManager versionManager;

    @Override
    public void check(ProcedureContext context) {
        String currentVersion = context.getCurrentVersion();
        List<Version> versions = versionManager.getUnreleasedVersions();
        for (Version version : versions) {
            if (currentVersion.equals(version.getVersionValue())) {
                return;
            }
        }
        throw new RuntimeException("Version not exists");
    }

}
