package cz.fg.issuetracking.api.report;

/**
 * Report segment - part of report, usually single line
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:43
 */
public interface ReportSegment {

    /**
     * Dump segment to string
     * @return string value
     */
    String getAsString();

}
