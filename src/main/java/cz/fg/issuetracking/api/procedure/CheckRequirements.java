package cz.fg.issuetracking.api.procedure;

/**
 * Check action requirements
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:31
 */
public interface CheckRequirements {

    /**
     * Check requirements
     */
    public void check(ProcedureContext context);

}
