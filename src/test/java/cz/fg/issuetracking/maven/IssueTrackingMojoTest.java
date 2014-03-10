package cz.fg.issuetracking.maven;

import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.ReportSegment;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * Mojo test
 * @author Michal Franc, FG Forrest a.s. (c) 2014
 *         10.3.14 19:23
 */
public class IssueTrackingMojoTest {

    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
        }

        @Override
        protected void after() {
        }
    };

    @Test
    public void testShouldGenerateChangelog() throws Exception {
        ChangelogMojo mojo = (ChangelogMojo) rule.lookupMojo( "changelog", "src/test/resources/maven/test-project/pom.xml" );
        Assert.assertNotNull(mojo);
        mojo.execute();
        Assert.assertTrue(mojo.managerConfigured);
        Assert.assertNotNull(mojo.ctx);
        TestReportRender reportRender = (TestReportRender) mojo.reportRender;
        Report result = reportRender.getLast();
        List<ReportSegment> segments = result.getSegments();
        Assert.assertEquals(19,segments.size());
    }

    @Test
    public void testShouldGenerateRoadmap() throws Exception {
        RoadmapMojo mojo = (RoadmapMojo) rule.lookupMojo( "roadmap", "src/test/resources/maven/test-project/pom.xml" );
        Assert.assertNotNull(mojo);
        mojo.execute();
        Assert.assertTrue(mojo.managerConfigured);
        Assert.assertNotNull(mojo.ctx);
        TestReportRender reportRender = (TestReportRender) mojo.reportRender;
        Report result = reportRender.getLast();
        List<ReportSegment> segments = result.getSegments();
        Assert.assertEquals(3,segments.size());
    }


}
