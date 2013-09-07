package cz.fg.issuetracking.api;

import java.util.List;

/**
 * Issue manager - provide list of versions from issue tracking system
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 21:34
 */
public interface VersionManager {

    /**
     * Sleeping versions
     * @return list of versions or empty list
     */
    public List<Version> getUnreleasedVersions();

    /**
     * Released versions
     * @return list of versions or empty list
     */
    public List<Version> getReleasedVersions();

    /**
     * Create new version for next development iteration
     * @param versionValue version number
     */
    public Version createVersion(String versionValue);

    /**
     * Close version (set as released)
     * @param versionValue version number
     */
    public void closeVersion(String versionValue);

}