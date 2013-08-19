package cz.fg.issuetracking.procedure.report;

import cz.fg.issuetracking.api.*;
import cz.fg.issuetracking.api.procedure.CreateReport;
import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.ReportImpl;

import java.util.*;

/**
 * Changelog report - only released versions and issues
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:23
 */
public class ChangelogReport implements CreateReport {

    IssueManager issues;
    VersionManager versions;

    @Override
    public Report create() {
        List<Issue> releasedIssues = getIssues();
        Map<VersionDescriptor, List<Issue>> changeMap = fillChangeMap(releasedIssues);
        List<Version> releasedVersions = getVersions();
        Map<VersionDescriptor, Version> versionMap = fillVersionMap(releasedVersions);
        ArrayList<VersionDescriptor> versions = getSortedVersions(changeMap.keySet());
        ReportImpl report = createReport(changeMap, versionMap, versions);
        return report;
    }

    protected ReportImpl createReport(Map<VersionDescriptor, List<Issue>> changeMap, Map<VersionDescriptor, Version> versionMap, ArrayList<VersionDescriptor> versions) {
        ReportImpl report = new ReportImpl();
        for (VersionDescriptor version : versions) {
            Version versionInfo = versionMap.get(version);
            report.addVersionSegment(versionInfo);
            List<Issue> changes = changeMap.get(version);
            for (Issue issue : changes) {
                report.addIssueSegment(issue);
            }
        }
        return report;
    }

    protected ArrayList<VersionDescriptor> getSortedVersions(Collection<VersionDescriptor> versionDescriptors) {
        ArrayList<VersionDescriptor> versions = new ArrayList<VersionDescriptor>(versionDescriptors);
        Collections.sort(versions, new VersionComparator());
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