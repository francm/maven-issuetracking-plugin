package cz.fg.issuetracking.mock;

import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.ManagerFactory;
import cz.fg.issuetracking.api.VersionManager;

import java.util.Properties;

/**
 * Mock manager factory
 * @author Michal Franc, FG Forrest a.s. (c) 2014
 *         10.3.14 20:15
 */
public class MockManagerFactory implements ManagerFactory {

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public IssueManager getIssueManager() {
        return IssueManagerFactory.getInstance();
    }

    @Override
    public VersionManager getVersionManager() {
        return VersionManagerFactory.getInstance();
    }
}
