package cz.fg.issuetracking.maven;

import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.ReportRender;
import cz.fg.issuetracking.procedure.report.ChangelogReport;
import cz.fg.issuetracking.procedure.report.RoadmapReport;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Changelog mojo
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 20:17
 */
@Mojo( name = "roadmap",
        defaultPhase = LifecyclePhase.COMPILE,
        requiresProject = true,
        threadSafe = true )
public class RoadmapMojo extends AbstractIssueTrackingMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        configure();
        RoadmapReport report = new RoadmapReport(managerFactory);
        Report roadmap = report.create();
        ReportRender render = getReportRender();
        render.render(roadmap);
    }

}
