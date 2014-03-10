package cz.fg.issuetracking.api.report;

/**
 * Report rendering
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         19.8.13 9:39
 */
public interface ReportRender {

    /**
     * Call report render
     * @param report report instance
     */
    void render(Report report);

}
