package cz.fg.issuetracking.procedure.report;

import cz.fg.issuetracking.api.*;
import cz.fg.issuetracking.api.procedure.ReportProcedure;
import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.ReportImpl;

import java.util.*;

/**
 * Changelog report - only released versions and issues
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:23
 */
public class ChangelogReport implements ReportProcedure {

    IssueManager issues;
    VersionManager versions;

    public ChangelogReport() {
    }

    public ChangelogReport(ManagerFactory managerFactory) {
        this.issues = managerFactory.getIssueManager();
        this.versions = managerFactory.getVersionManager();
    }

    @Override
    public Report create() {
        List<Issue> issues = getIssues();
        Map<VersionDescriptor, List<Issue>> changeMap = fillChangeMap(issues);
        List<Version> versions = getVersions();
        Map<VersionDescriptor, Version> versionMap = fillVersionMap(versions);
        ArrayList<VersionDescriptor> sortedVersions = getSortedVersions(changeMap.keySet());
        ReportImpl report = createReport(changeMap, versionMap, sortedVersions);
        return report;
    }

    protected ReportImpl createReport(Map<VersionDescriptor, List<Issue>> changeMap, Map<VersionDescriptor, Version> versionMap, ArrayList<VersionDescriptor> versions) {
        ReportImpl report = new ReportImpl();
        for (VersionDescriptor version : versions) {
            Version versionInfo = versionMap.get(version);
            if ( versionInfo==null  ) continue;
            report.addVersionSegment(versionInfo);
            List<Issue> changes = getSortedIssues(changeMap.get(version));
            for (Issue issue : changes) {
                report.addIssueSegment(issue);
            }
        }
        return report;
    }

    protected List<Issue> getSortedIssues(List<Issue> issues) {
        List<Issue> result = new ArrayList<Issue>(issues);
        Collections.sort(result,new Comparator<Issue>() {
            @Override
            public int compare(Issue o1, Issue o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return result;
    }

    protected ArrayList<VersionDescriptor> getSortedVersions(Collection<VersionDescriptor> versionDescriptors) {
        ArrayList<VersionDescriptor> versions = new ArrayList<VersionDescriptor>(versionDescriptors);
        Comparator comparator = Collections.reverseOrder(new VersionComparator());
        Collections.sort(versions, comparator);
        return versions;
    }

    protected List<Issue> getIssues() {
        return issues.getReleasedIssues();
    }

    protected List<Version> getVersions() {
        return versions.getReleasedVersions();
    }

    protected Map<VersionDescriptor, Version> fillVersionMap(List<Version> releasedVersions) {
        Map<VersionDescriptor,Version> versionMap = new HashMap<VersionDescriptor, Version>();
        for (Version version : releasedVersions) {
            versionMap.put(version.getVersionDescriptor(),version);
        }
        return versionMap;
    }

    protected Map<VersionDescriptor, List<Issue>> fillChangeMap(List<Issue> releasedIssues) {
        Map<VersionDescriptor,List<Issue>> changeMap = new HashMap<VersionDescriptor, List<Issue>>();
        for (Issue issue : releasedIssues) {
            VersionDescriptor version = issue.getVersionDescriptor();
            List<Issue> versionIssues = changeMap.get(version);
            if (versionIssues==null) {
                versionIssues = new ArrayList<Issue>();
                changeMap.put(version, versionIssues);
            }
            versionIssues.add(issue);
        }
        return changeMap;
    }

    public void setIssues(IssueManager issues) {
        this.issues = issues;
    }

    public void setVersions(VersionManager versions) {
        this.versions = versions;
    }

}