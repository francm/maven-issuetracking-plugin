package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.ManagerFactory;
import cz.fg.issuetracking.api.VersionManager;

import java.util.Properties;

/**
 * Redmine manager factory
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         22.8.13 16:48
 */
public class RedmineManagerFactory implements ManagerFactory {

    RedmineManager redmineManager;
    Properties properties;

    public RedmineManagerFactory(Properties properties) {
        this.properties = properties;
    }

    public RedmineManager getRedmineManager(Properties properties) {
        String uri = properties.getProperty("redmine-uri");
        String login = properties.getProperty("redmine-login");
        String password = properties.getProperty("redmine-password");
        String apiAccessKey = properties.getProperty("redmine-apiAccessKey");

        if ( apiAccessKey!=null ) {
            return new RedmineManager(uri,apiAccessKey);
        }
        else {
            return new RedmineManager(uri,login,password);
        }
    }

    public RedmineManager getRedmineManager() {
        if ( redmineManager==null ) {
            redmineManager = getRedmineManager(properties);
            redmineManager.setObjectsPerPage(200);
        }
        return redmineManager;
    }

    @Override
    public IssueManager getIssueManager() {
        RedmineManager redmineManager = getRedmineManager();
        RedmineProject project = createRedmineProject(redmineManager);
        return new RedmineIssueManager(project);
    }

    private RedmineProject createRedmineProject(RedmineManager redmineManager) {
        RedmineProject project = new RedmineProject(redmineManager);
        String projectId = getProjectId();
        project.setProjectId(projectId);
        String currentVersion = getCurrentVersion();
        project.setCurrentVersion(currentVersion);
        project.addCustomFieldValue(87,"Specializace","Java Developer");
        project.setScmUsername("SCM");
        project.setStateNew("1");
        project.setStateInProgress("4");
        project.setStateResolved("6");
        project.setStateClosed("7");
        /* FG statuses
        <option value="1">Nový</option>
        <option value="2">Reakce</option>
        <option value="3">Ke korektuře</option>
        <option value="4" selected="selected">Přiřazený</option>
        <option value="5">Schválený</option>
        <option value="6">Vyřešený</option>
        <option value="7">Uzavřený</option>
        */
        return project;
    }

    @Override
    public VersionManager getVersionManager() {
        RedmineProject project = createRedmineProject(getRedmineManager());
        return new RedmineVersionManager(project);
    }

    protected String getProjectId() {
        String projectId = properties.getProperty("redmine-projectId");
        if ( projectId==null ) {
            throw new IllegalArgumentException("Project id missing - property 'redmine-projectId'");
        }
        // if key is provided get projectId
        try {
            Integer.valueOf(projectId);
        }
        catch (NumberFormatException e) {
            try {
                projectId = redmineManager.getProjectByKey(projectId).getId().toString();
            } catch (RedmineException e1) {
                throw new RuntimeException(e1);
            }
        }
        return projectId;
    }

    protected String getCurrentVersion() {
        String property = properties.getProperty("redmine-currentVersion");
        if ( property==null ) {
            throw new IllegalArgumentException("Current version missing - property 'redmine-currentVersion'");
        }
        return property;
    }

}
