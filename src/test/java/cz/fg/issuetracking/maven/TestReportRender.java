package cz.fg.issuetracking.maven;

import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.ReportRender;

import java.util.LinkedList;

/**
 * Test rendered
 * @author Michal Franc, FG Forrest a.s. (c) 2014
 *         10.3.14 21:07
 */
public class TestReportRender implements ReportRender {

    LinkedList<Report> results = new LinkedList<Report>();

    @Override
    public void render(Report report) {
        results.addLast(report);
    }

    public Report getLast() {
        return results.removeLast();
    }

}
