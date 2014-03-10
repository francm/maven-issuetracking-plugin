package cz.fg.issuetracking.redmine;

import cz.fg.issuetracking.api.*;
import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.StringReportRender;
import cz.fg.issuetracking.procedure.report.ChangelogReport;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * Redmine issue manager test
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         22.8.13 17:03
 */
public class RedmineTest {

    @Test
    public void testShouldPrepareIssues() throws Exception {
        IssueManager issueManager = createManagerFactory().getIssueManager();
        Assert.assertNotNull(issueManager);
        Issue issue1 = issueManager.createIssue("First issue", "first content", "MFR");
        Issue issue2 = issueManager.createIssue("Second issue", "second content", "MFR");
        Issue issue3 = issueManager.createIssue("Third issue", "third content", "MFR");
        Issue issue4 = issueManager.createIssue("Fourth issue", "fourth content", "MFR");
        Issue issue5 = issueManager.createIssue("Fifth issue", "fifth content", "MFR");
        Issue issue6 = issueManager.createIssue("Sixth issue", "sixth content", "SCMCOMMITER");
        Issue issue7 = issueManager.createIssue("Seventh issue", "seventh content", "SCMCOMMITER");
        Issue issue8 = issueManager.createIssue("Eighth issue", "eighth content", "SCMCOMMITER");
        Issue issue9 = issueManager.createIssue("Ninth issue", "ninth content", "SCMCOMMITER");
        Issue issue10 = issueManager.createIssue("Tenth issue", "tenth content", "SCMCOMMITER");
        issueManager.setIssueState(IssueState.UNDER_DEVELOPMENT,issue2.getId(),issue3.getId(),issue4.getId());
        issueManager.setIssueState(IssueState.SOLVED,issue5.getId(),issue6.getId(),issue7.getId(),issue8.getId());
        issueManager.setIssueState(IssueState.RELEASED,issue9.getId(),issue10.getId(),issue1.getId());
        VersionManager versionManager = createManagerFactory().getVersionManager();
        versionManager.createVersion("1.0.0");
        versionManager.createVersion("1.1.0");
        versionManager.createVersion("2.0.0");
        versionManager.createVersion("3.0.0");

        issueManager.assignVersion("1.0.0",issue1.getId(),issue2.getId());
        issueManager.assignVersion("1.1.0",issue3.getId(),issue4.getId());
        issueManager.assignVersion("2.0.0",issue5.getId(),issue6.getId());
        issueManager.assignVersion("3.0.0",issue7.getId(),issue8.getId(),issue9.getId(),issue10.getId());
    }


    @Test
    @Ignore
    public void testShouldCreateManager() throws Exception {
        RedmineManagerFactory f = createManagerFactory();
        IssueManager issueManager = f.getIssueManager();
        VersionManager versionManager = f.getVersionManager();
        Assert.assertNotNull(issueManager);
        Assert.assertNotNull(versionManager);
    }

    @Test
    @Ignore
    public void testShouldGetIssues() throws Exception {
        IssueManager issueManager = createManagerFactory().getIssueManager();
        List<Issue> releasedIssues = issueManager.getReleasedIssues();
        List<Issue> sleepingIssues = issueManager.getSleepingIssues();
        List<Issue> solvedIssues = issueManager.getSolvedIssues();
        List<Issue> underDevelopmentIssues = issueManager.getUnderDevelopmentIssues();
        List<Issue> underDevelopmentSuspectsIssues = issueManager.getUnderDevelopmentSuspectsIssues();
    }

    @Test
    @Ignore
    public void testShouldGetVersions() throws Exception {
        VersionManager versionManager = createManagerFactory().getVersionManager();
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
    @Ignore
    public void testChangeLog() throws Exception {
        ChangelogReport report = new ChangelogReport();
        report.setVersions(createManagerFactory().getVersionManager());
        report.setIssues(createManagerFactory().getIssueManager());

        Report result = report.create();
        StringReportRender sr = new StringReportRender();
        sr.render(result);
        System.out.println(sr.getResult());
    }

    @Test
    @Ignore
    public void testCreate() throws Exception {
        RedmineManagerFactory f = createManagerFactory();
        IssueManager issueManager = f.getIssueManager();
        //Issue issue = issueManager.createIssue("Test", "content", "SCMCOMMITER");
        VersionManager versionManager = f.getVersionManager();
        //versionManager.createVersion("1.0.0");
        //issueManager.assignVersion("1.0.0",issue.getId());
        //issueManager.closeIssues("1.0.0");
        versionManager.releaseVersion("1.0.0");
    }

    private RedmineManagerFactory createManagerFactory() throws Exception {
        //Properties properties = System.getProperties();
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/META-INF/redmine.properties"));
        return new RedmineManagerFactory(properties);
    }


}
