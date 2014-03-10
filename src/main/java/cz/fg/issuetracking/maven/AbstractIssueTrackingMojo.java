package cz.fg.issuetracking.maven;

import cz.fg.issuetracking.api.ManagerFactory;
import cz.fg.issuetracking.api.procedure.ProcedureContext;
import cz.fg.issuetracking.api.procedure.ProcedureContextImpl;
import cz.fg.issuetracking.api.report.ReportRender;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Properties;

/**
 * Abstract issue tracking mojo
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         12.9.13 21:40
 */
public abstract class AbstractIssueTrackingMojo extends AbstractMojo {

    @Parameter
    protected Properties properties;

    @Parameter
    protected ManagerFactory managerFactory;

    @Parameter
    protected ReportRender reportRender;

    protected boolean managerConfigured = false;

    protected ProcedureContext ctx;

    @Component
    protected MavenProject project;

    protected void configure() {
        if ( !managerConfigured ) {
            managerFactory.setProperties(properties);
            managerConfigured=true;
        }
        ProcedureContextImpl procedureContext = new ProcedureContextImpl();
        String version = project.getVersion();
        procedureContext.setCurrentVersion(version);
        ctx = procedureContext;
    }

    public ReportRender getReportRender() {
        return reportRender;
    }
}
