package cz.fg.issuetracking.procedure.report;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.Version;

import java.util.List;

/**
 * Roadmap report - only unreleased versions and issues
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:23
 */
public class RoadmapReport extends ChangelogReport {

    @Override
    protected List<Issue> getIssues() {
        return issues.getUnderDevelopmentIssues();
    }

    @Override
    protected List<Version> getVersions() {
        return versions.getUnreleasedVersions();
    }

}
