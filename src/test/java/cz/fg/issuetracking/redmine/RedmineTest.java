package cz.fg.issuetracking.redmine;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.Version;
import cz.fg.issuetracking.api.VersionManager;
import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.StringReportRender;
import cz.fg.issuetracking.procedure.report.ChangelogReport;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * TODO
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         22.8.13 17:03
 */

public class RedmineTest {

    @Test
    public void testShouldCreateManager() {
        RedmineMangerFactory f = createManager();
        IssueManager issueManager = f.getIssueManager();
        VersionManager versionManager = f.getVersionManager();
        Assert.assertNotNull(issueManager);
        Assert.assertNotNull(versionManager);
    }

    @Test
    @Ignore
    public void testShouldGetIssues() {
        IssueManager issueManager = createManager().getIssueManager();
        List<Issue> releasedIssues = issueManager.getReleasedIssues();
        List<Issue> sleepingIssues = issueManager.getSleepingIssues();
        List<Issue> solvedIssues = issueManager.getSolvedIssues();
        List<Issue> underDevelopmentIssues = issueManager.getUnderDevelopmentIssues();
        List<Issue> underDevelopmentSuspectsIssues = issueManager.getUnderDevelopmentSuspectsIssues();
    }

    @Test
    @Ignore
    public void testShouldGetVersions() {
        VersionManager versionManager = createManager().getVersionManager();
        List<Version> releasedVersions = versionManager.getReleasedVersions();
        for (Version version : releasedVersions) {
            Assert.assertTrue(version.isReleased());
        }
        List<Version> unreleasedVersions = versionManager.getUnreleasedVersions();
        for (Version version : unreleasedVersions) {
            Assert.assertTrue(!version.isReleased());
        }
    }

    @Test
    public void testChangeLog() {
        ChangelogReport report = new ChangelogReport();
        report.setVersions(createManager().getVersionManager());
        report.setIssues(createManager().getIssueManager());

        Report result = report.create();
        StringReportRender sr = new StringReportRender();
        sr.render(result);
        System.out.println(sr.getResult());
    }

    private RedmineMangerFactory createManager() {
        Properties properties = System.getProperties();
        return new RedmineMangerFactory(properties);
    }


}
