package cz.fg.issuetracking.api.report;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * Report implementation
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:54
 */
public class ReportImpl implements Report {

    List<ReportSegment> segments;

    public ReportImpl() {
        this.segments = new ArrayList<ReportSegment>();
    }

    public void addVersionSegment(Version version) {
        segments.add(new VersionSegment(version));
    }

    public void addIssueSegment(Issue issue) {
        segments.add(new IssueSegment(issue));
    }

    public void addSegment(ReportSegment segment) {
        segments.add(segment);
    }

    @Override
    public List<ReportSegment> getSegments() {
        return segments;
    }

}
