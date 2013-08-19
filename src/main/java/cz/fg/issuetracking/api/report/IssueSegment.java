package cz.fg.issuetracking.api.report;

import cz.fg.issuetracking.api.Issue;

/**
 * Issue report segment
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:59
 */
public class IssueSegment implements ReportSegment {

    Issue issue;

    public IssueSegment(Issue issue) {
        this.issue = issue;
    }

    public Issue getIssue() {
        return issue;
    }

    @Override
    public String getAsString() {
        StringBuilder r = new StringBuilder();
        r.append("#").append(issue.getId()).append(" ").append(issue.getName());
        r.append(" - ").append(issue.getUrl()).append(" (").append(issue.getDeveloper()).append(")");
        return r.toString();
    }
}
