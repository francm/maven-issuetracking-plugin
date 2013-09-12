package cz.fg.issuetracking.api.procedure;

import cz.fg.issuetracking.api.report.Report;

/**
 * Create report
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:31
 */
public interface ReportProcedure {

    /**
     * Create issues report
     * @return report
     */
    public Report create();

}
