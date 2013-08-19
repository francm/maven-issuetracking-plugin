package cz.fg.issuetracking.api.report;

import java.util.List;

/**
 * Report data object
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:39
 */
public interface Report {

    /**
     * Get report segments
     * @return list of report segments or empty list
     */
    List<ReportSegment> getSegments();

}
