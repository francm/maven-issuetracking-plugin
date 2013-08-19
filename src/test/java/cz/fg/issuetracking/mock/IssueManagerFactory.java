package cz.fg.issuetracking.mock;

import cz.fg.issuetracking.api.Issue;
import cz.fg.issuetracking.api.IssueImpl;
import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.VersionDescriptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
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
        released.add(createIssue("13463","Ready to release","2.0.0-RC",true));
        released.add(createIssue("13467","Minor bug fix","2.0.0",true));
        released.add(createIssue("13474","Huge bug","2.0.0",true));
        released.add(createIssue("13495","Another huge bug","2.0.0",true));
        released.add(createIssue("13500","Change project scope","2.0.0",true));
        released.add(createIssue("13666","Devil bug","2.0.0",true));


        IssueManager issueManager = Mockito.mock(IssueManager.class);
        Mockito.doReturn(released).when(issueManager).getReleasedIssues();

        return issueManager;
    }

    static Issue createIssue(String id, String name, String version, boolean done) {
        VersionDescriptor vd = new VersionDescriptor(version);
        Issue issue = new IssueImpl(id,name,"http://localhost/redmine/issue/"+id,"unknown",vd,done);
        return issue;
    }

}