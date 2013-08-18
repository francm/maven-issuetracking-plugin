package cz.fg.maven.issuetracking.api;

import java.util.List;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 21:34
 */
public interface VersionManager {

    List<Version> getSleepingVersions();
    List<Version> getUnderDevelopmentVersions();
    List<Version> getReleasedVersions();

}
