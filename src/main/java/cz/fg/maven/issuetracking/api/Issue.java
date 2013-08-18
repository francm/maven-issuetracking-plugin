package cz.fg.maven.issuetracking.api;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 20:56
 */
public interface Issue {

    String getId();
    String getName();
    String getUrl();
    String getDeveloper();
    boolean isDone();

}
