package cz.fg.issuetracking.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 20:17
 */
@Mojo( name = "foo",
        defaultPhase = LifecyclePhase.COMPILE,
        threadSafe = false )
public class ChangelogMojo extends AbstractIssueTrackingMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

    }

}
