package cz.fg.issuetracking.procedure.report;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.ManagerFactory;
import cz.fg.issuetracking.api.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * Roadmap report - only unreleased versions and issues
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:23
 */
public class RoadmapReport extends ChangelogReport {

    public RoadmapReport() {
        super();
    }

    public RoadmapReport(ManagerFactory managerFactory) {
        super(managerFactory);
    }

    @Override
    protected List<Issue> getIssues() {
        List<Issue> result = new ArrayList<Issue>();
        result.addAll(issues.getUnderDevelopmentIssues());
        result.addAll(issues.getSolvedIssues());
        return result;
    }

    @Override
    protected List<Version> getVersions() {
        return versions.getUnreleasedVersions();
    }

}
