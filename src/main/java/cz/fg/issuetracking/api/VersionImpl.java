package cz.fg.issuetracking.api;

import java.util.Date;

/**
 * Version implementation
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 23:14
 */
public class VersionImpl implements Version {

    String versionValue;
    VersionDescriptor versionDescriptor;
    String description;
    Date date;
    boolean released;

    public VersionImpl(String versionValue, String description, Date date, boolean released) {
        this.versionValue = versionValue;
        this.versionDescriptor = versionValue==null?null:new VersionDescriptor(versionValue);
        this.description = description;
        this.date = date;
        this.released = released;
    }

    @Override
    public String getVersionValue() {
        return versionValue;
    }

    @Override
    public VersionDescriptor getVersionDescriptor() {
        return versionDescriptor;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public boolean isReleased() {
        return released;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Version");
        sb.append("{versionDescriptor=").append(versionDescriptor.toString());
        sb.append(", description='").append(description).append('\'');
        sb.append(", date=").append(date);
        sb.append(", released=").append(released);
        sb.append('}');
        return sb.toString();
    }
}
