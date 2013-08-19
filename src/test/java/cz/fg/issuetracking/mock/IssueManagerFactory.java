package cz.fg.issuetracking.mock;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.IssueImpl;
import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.VersionDescriptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Create IssueManager mock
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         19.8.13 8:57
 */
public class IssueManagerFactory {

    public static IssueManager getInstance() {
        List<Issue> released = new ArrayList<Issue>();
        released.add(createIssue("1","Initial issue","1.0",true));
        released.add(createIssue("389","Project reborn","1.0.1",true));
        released.add(createIssue("9473","Reborn again","1.1",true));
        released.add(createIssue("9475","Big and ugly bug","1.1",true));
        released.add(createIssue("12988","Rewrite project from scratch","1.2",true));
        released.add(createIssue("12989","New bug","1.2",true));
        released.add(createIssue("12991","Another new bug","1.2",true));
        released.add(createIssue("13463", "Ready to release", "2.0.0-RC", true));
        released.add(createIssue("13467", "Minor bug fix", "2.0.0", true));
        released.add(createIssue("13474", "Huge bug", "2.0.0", true));
        released.add(createIssue("13495", "Another huge bug", "2.0.0", true));
        released.add(createIssue("13500", "Change project scope", "2.0.0", true));
        released.add(createIssue("13666", "Devil bug", "2.0.0", true));

        List<Issue> sleeping = new ArrayList<Issue>();
        sleeping.add(createIssue("2","Nice to have issue",null,false));
        sleeping.add(createIssue("12343","Architecture change",null,false));

        List<Issue> solved = new ArrayList<Issue>();
        solved.add(createIssue("20328","Bad name","3.0.0-SNAPSHOT",true));

        List<Issue> underDevelopment = new ArrayList<Issue>();
        underDevelopment.add(createIssue("20329", "Hard to fix bug", "3.0.0-SNAPSHOT", false));

        List<Issue> suspects = new ArrayList<Issue>();
        suspects.add(createIssue("20206", "Unable to reproduce bug", null, false));

        IssueManager issueManager = Mockito.mock(IssueManager.class);
        Mockito.doReturn(released).when(issueManager).getReleasedIssues();
        Mockito.doReturn(sleeping).when(issueManager).getSleepingIssues();
        Mockito.doReturn(solved).when(issueManager).getSolvedIssues();
        Mockito.doReturn(underDevelopment).when(issueManager).getUnderDevelopmentIssues();
        Mockito.doReturn(suspects).when(issueManager).getUnderDevelopmentSuspectsIssues();

        return issueManager;
    }

    static Issue createIssue(String id, String name, String version, boolean done) {
        Issue issue = new IssueImpl(id,name,"http://localhost/redmine/issue/"+id,"unknown",version,done);
        return issue;
    }

}