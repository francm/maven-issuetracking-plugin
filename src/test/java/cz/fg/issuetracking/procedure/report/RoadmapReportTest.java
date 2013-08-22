package cz.fg.issuetracking.procedure.report;

import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.ReportSegment;
import cz.fg.issuetracking.mock.IssueManagerFactory;
import cz.fg.issuetracking.mock.VersionManagerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Roadmap test
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:28
 */
public class RoadmapReportTest extends ChangelogReportTest {

    @Test
    public void testShouldCreateReport() {
        RoadmapReport report = new RoadmapReport();
        report.setVersions(VersionManagerFactory.getInstance());
        report.setIssues(IssueManagerFactory.getInstance());

        Report result = report.create();
        Assert.assertNotNull("Null report result",result);
        List<ReportSegment> segments = result.getSegments();
        Assert.assertEquals("Bad number of report segments",3,segments.size());
        Assert.assertEquals("Bad report output",
                "= unknown#20328#20329",
                narrowReport(segments)
        );
    }
}
