package cz.fg.issuetracking.api.procedure;

/**
 * Resolve requirements
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:34
 */
public interface ActionProcedure {

    public void process(ProcedureContext ctx);

}
