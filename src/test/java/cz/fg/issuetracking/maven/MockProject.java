package cz.fg.issuetracking.maven;

import org.apache.maven.plugin.testing.stubs.MavenProjectStub;

/**
 * Mock project
 * @author Michal Franc, FG Forrest a.s. (c) 2014
 *         10.3.14 20:46
 */
public class MockProject extends MavenProjectStub {

    public MockProject() {
        super();
        setVersion("1.0-SNAPSHOT");
    }
}
