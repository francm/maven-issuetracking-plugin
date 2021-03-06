package cz.fg.issuetracking.procedure.report;

import cz.fg.issuetracking.api.Version;
import cz.fg.issuetracking.api.report.IssueSegment;
import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.ReportSegment;
import cz.fg.issuetracking.api.report.VersionSegment;
import cz.fg.issuetracking.mock.IssueManagerFactory;
import cz.fg.issuetracking.mock.VersionManagerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Changelog test
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:28
 */
public class ChangelogReportTest {

    @Test
    public void testShouldCreateReport() {
        ChangelogReport report = new ChangelogReport();
        report.setVersions(VersionManagerFactory.getInstance());
        report.setIssues(IssueManagerFactory.getInstance());

        Report result = report.create();
        Assert.assertNotNull("Null report result",result);
        List<ReportSegment> segments = result.getSegments();
        Assert.assertEquals("Bad number of report segments",19,segments.size());
        Assert.assertEquals("Bad report output",
                "=2.0.0#13467#13474#13495#13500#13666"+
                "=2.0.0-RC#13463" +
                "=1.2#12988#12989#12991" +
                "=1.1#9473#9475" +
                "=1.0.1#389" +
                "=1.0#1"                ,
                narrowReport(segments)
        );
    }

    protected String narrowReport(List<ReportSegment> segments) {
        StringBuilder sb = new StringBuilder();
        for (ReportSegment segment : segments) {
            if ( segment instanceof IssueSegment) {
                sb.append("#").append(((IssueSegment)segment).getIssue().getId());
            }
            else if ( segment instanceof VersionSegment) {
                Version version = ((VersionSegment) segment).getVersion();
                if ( version==null ) {
                    sb.append("= unknown");
                }
                else {
                    sb.append("=").append(version.getVersionValue());
                }
            }
        }
        return sb.toString();
    }

}
