package cz.fg.issuetracking.api;

import java.util.Date;

/**
 * Version implementation
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 23:14
 */
public class VersionImpl implements Version {

    VersionDescriptor versionDescriptor;
    String description;
    Date date;

    public VersionImpl(VersionDescriptor versionDescriptor, String description, Date date) {
        this.versionDescriptor = versionDescriptor;
        this.description = description;
        this.date = date;
    }

    public VersionDescriptor getVersionDescriptor() {
        return versionDescriptor;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

}
