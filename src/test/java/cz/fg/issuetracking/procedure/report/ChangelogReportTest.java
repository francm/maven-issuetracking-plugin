package cz.fg.issuetracking.procedure.report;

import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.StringReportRender;
import cz.fg.issuetracking.mock.IssueManagerFactory;
import cz.fg.issuetracking.mock.VersionManageFactory;
import org.junit.Test;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:28
 */
public class ChangelogReportTest {

    @Test
    public void testShouldCreateReport() {
        ChangelogReport report = new ChangelogReport();
        report.setVersions(VersionManageFactory.getInstance());
        report.setIssues(IssueManagerFactory.getInstance());
        Report result = report.create();
        StringReportRender out = new StringReportRender();
        out.render(result);
        System.out.println(out.getResult());
    }
}
