package cz.fg.issuetracking.api;

import java.util.Date;

/**
 * Version object
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 21:34
 */
public interface Version {

    /**
     * Version number descriptor
     * @return version descriptor object
     */
    public VersionDescriptor getVersionDescriptor();

    /**
     * Version description
     * @return version description
     */
    public String getDescription();

    /**
     * Get version date
     * @return release date
     */
    public Date getDate();

    /**
     * Get version state
     * @return true if version is released
     */
    public boolean isReleased();

}
