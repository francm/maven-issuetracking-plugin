package cz.fg.issuetracking.api;

/**
 * Issue object
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 20:56
 */
public interface Issue {

    public String getId();
    public String getName();
    public String getUrl();
    public String getDeveloper();
    public VersionDescriptor getVersionDescriptor();
    public boolean isDone();

}
