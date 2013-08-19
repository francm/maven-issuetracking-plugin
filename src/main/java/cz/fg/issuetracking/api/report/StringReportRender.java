package cz.fg.issuetracking.api.report;

/**
 * String report render
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         19.8.13 9:41
 */
public class StringReportRender implements ReportRender {

    StringBuilder result;

    public StringReportRender() {
        result = new StringBuilder();
    }

    @Override
    public void render(Report report) {
        for (ReportSegment segment : report.getSegments()) {
            result.append(renderSegment(segment)).append("\n");
        }
    }

    protected String renderSegment(ReportSegment segment) {
        return segment.getAsString();
    }

    public String getResult() {
        return result.toString();
    }

}