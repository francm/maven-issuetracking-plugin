package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Version;
import cz.fg.issuetracking.api.IssueManager;
import cz.fg.issuetracking.api.ManagerFactory;
import cz.fg.issuetracking.api.VersionManager;

import java.util.List;
import java.util.Properties;

/**
 * Redmine manager factory
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         22.8.13 16:48
 */
public class RedmineMangerFactory implements ManagerFactory {

    RedmineManager redmineManager;
    Properties properties;

    public RedmineMangerFactory(Properties properties) {
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
        String currentVersion = getCurrentVersion();
        String projectId = getProjectId();
        RedmineManager redmineManager = getRedmineManager();
        Integer currentVersionId = getCurrentVersionId(redmineManager, projectId, currentVersion);
        return new RedmineIssueManager(redmineManager, projectId, currentVersionId==null?null:currentVersionId.toString());
    }

    @Override
    public VersionManager getVersionManager() {
        return new RedmineVersionManager(getRedmineManager(), getProjectId());
    }

    protected String getProjectId() {
        String property = properties.getProperty("redmine-projectId");
        if ( property==null ) {
            throw new IllegalArgumentException("Project id missing - property 'redmine-projectId'");
        }
        return property;
    }

    protected String getCurrentVersion() {
        String property = properties.getProperty("redmine-currentVersion");
        if ( property==null ) {
            throw new IllegalArgumentException("Current version missing - property 'redmine-currentVersion'");
        }
        return property;
    }

    protected Integer getCurrentVersionId(RedmineManager redmineManager,String projectId,String versionName) {
        try {
            List<Version> versions = redmineManager.getVersions(Integer.valueOf(projectId));
            for (Version version : versions) {
                if ( versionName.equals(version.getName()) ) {
                    return version.getId();
                }
            }
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
