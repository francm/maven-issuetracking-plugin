package cz.fg.issuetracking.maven;

import cz.fg.issuetracking.api.ManagerFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Properties;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         12.9.13 21:40
 */
public abstract class AbstractIssueTrackingMojo extends AbstractMojo {

    @Parameter
    protected Properties properties;

    @Parameter
    protected ManagerFactory factory;


}
