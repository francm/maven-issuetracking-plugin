package cz.fg.issuetracking.redmine;

import cz.fg.issuetracking.api.*;
import cz.fg.issuetracking.api.report.Report;
import cz.fg.issuetracking.api.report.StringReportRender;
import cz.fg.issuetracking.procedure.report.ChangelogReport;
import org.junit.Assert;
import org.junit.BeforeClass;
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
@Ignore
public class RedmineTest {

    static RedmineManagerFactory managerFactory;

    static Issue issue1,
                issue2,
                issue3,
                issue4,
                issue5,
                issue6,
                issue7,
                issue8,
                issue9,
                issue10;

    /*@BeforeClass*/
    public static void prepareTestProjectState() throws Exception {
        IssueManager issueManager = createManagerFactory().getIssueManager();
        Assert.assertNotNull(issueManager);
        issue1 = issueManager.createIssue("First issue", "first content", "MFR");
        issue2 = issueManager.createIssue("Second issue", "second content", "MFR");
        issue3 = issueManager.createIssue("Third issue", "third content", "MFR");
        issue4 = issueManager.createIssue("Fourth issue", "fourth content", "MFR");
        issue5 = issueManager.createIssue("Fifth issue", "fifth content", "MFR");
        issue6 = issueManager.createIssue("Sixth issue", "sixth content", "SCMCOMMITER");
        issue7 = issueManager.createIssue("Seventh issue", "seventh content", "SCMCOMMITER");
        issue8 = issueManager.createIssue("Eighth issue", "eighth content", "SCMCOMMITER");
        issue9 = issueManager.createIssue("Ninth issue", "ninth content", "SCMCOMMITER");
        issue10 = issueManager.createIssue("Tenth issue", "tenth content", "SCMCOMMITER");
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
    public void testShouldCreateManager() throws Exception {
        RedmineManagerFactory f = createManagerFactory();
        IssueManager issueManager = f.getIssueManager();
        VersionManager versionManager = f.getVersionManager();
        Assert.assertNotNull(issueManager);
        Assert.assertNotNull(versionManager);
    }

    @Test
    public void testShouldGetIssues() throws Exception {
        IssueManager issueManager = createManagerFactory().getIssueManager();
        List<Issue> releasedIssues = issueManager.getReleasedIssues();
        List<Issue> sleepingIssues = issueManager.getSleepingIssues();
        List<Issue> solvedIssues = issueManager.getSolvedIssues();
        List<Issue> underDevelopmentIssues = issueManager.getUnderDevelopmentIssues();
        List<Issue> underDevelopmentSuspectsIssues = issueManager.getUnderDevelopmentSuspectsIssues();
        Assert.assertEquals(3,releasedIssues.size());
        Assert.assertEquals("First issue",releasedIssues.get(0).getName());
        Assert.assertEquals(4,solvedIssues.size());
        Assert.assertEquals("Fifth issue",solvedIssues.get(0).getName());
        Assert.assertEquals(3,underDevelopmentIssues.size());
        Assert.assertEquals("Second issue",underDevelopmentIssues.get(0).getName());
        Assert.assertEquals(0,sleepingIssues.size());
        Assert.assertEquals(0,underDevelopmentSuspectsIssues.size());
    }

    @Test
    public void testShouldGetVersions() throws Exception {
        VersionManager versionManager = createManagerFactory().getVersionManager();
        List<Version> releasedVersions = versionManager.getReleasedVersions();
        List<Version> unreleasedVersions = versionManager.getUnreleasedVersions();
        int releasedBefore = releasedVersions.size();
        int unreleasedBefore = unreleasedVersions.size();

        versionManager.createVersion("10.0.0");
        unreleasedVersions = versionManager.getUnreleasedVersions();
        releasedVersions = versionManager.getReleasedVersions();
        Assert.assertEquals(releasedBefore,releasedVersions.size());
        Assert.assertEquals(unreleasedBefore+1,unreleasedVersions.size());

        versionManager.releaseVersion("10.0.0");
        unreleasedVersions = versionManager.getUnreleasedVersions();
        releasedVersions = versionManager.getReleasedVersions();
        Assert.assertEquals(releasedBefore+1,releasedVersions.size());
        Assert.assertEquals(unreleasedBefore, unreleasedVersions.size());

        for (Version version : releasedVersions) {
            Assert.assertTrue(version.isReleased());
        }
        for (Version version : unreleasedVersions) {
            Assert.assertTrue(!version.isReleased());
        }
    }

    @Test
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

    private static RedmineManagerFactory createManagerFactory() throws Exception {
        if ( managerFactory==null) {
            //Properties properties = System.getProperties();
            Properties properties = new Properties();
            properties.load(properties.getClass().getResourceAsStream("/META-INF/redmine.properties"));
            managerFactory = new RedmineManagerFactory(properties);
        }
        return managerFactory;
    }


}
