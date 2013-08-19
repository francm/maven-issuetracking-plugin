package cz.fg.issuetracking.api.report;

import cz.fg.issuetracking.api.Version;

/**
 * Version report segment
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:58
 */
public class VersionSegment implements ReportSegment {

    Version version;

    public VersionSegment(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String getAsString() {
        StringBuilder r = new StringBuilder();
        r.append("===================================================\n")
                .append(version.getVersionDescriptor()).append(" - ")
                .append(version.getDate()).append("\n")
                .append(version.getDescription()).append("\n");
        return r.toString();
    }
}
