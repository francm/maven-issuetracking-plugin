package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.User;
import com.taskadapter.redmineapi.bean.Version;

import java.util.List;

/**
 * Redmine project - base properties
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         7.9.13 19:58
 */
public class RedmineProject {

    RedmineManager redmineManager;

    /** Redmine internal project id */
    String projectId;

    /** Version currently developed */
    String currentVersion;

    /** SCM commiter user name */
    String scmUsername;

    /** List of default custom field values */
    List<CustomField> customFields;

    /* Redmine state ids - defaults */
    String stateNew = "1";
    String stateInProgress = "2";
    String stateResolved = "3";
    String stateClosed = "5";

    // cached data
    String cachedCurrentVersionId;

    public RedmineProject(RedmineManager redmineManager) {
        this.redmineManager = redmineManager;
    }

    public RedmineManager getRedmineManager() {
        return redmineManager;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getCurrentVersionId() {
        if (currentVersion!=null && cachedCurrentVersionId==null) {
            Integer versionId = getVersionIdByName(currentVersion);
            cachedCurrentVersionId = versionId==null?null:versionId.toString();
        }
        return cachedCurrentVersionId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getScmUsername() {
        return scmUsername;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public String getStateNew() {
        return stateNew;
    }

    public String getStateInProgress() {
        return stateInProgress;
    }

    public String getStateResolved() {
        return stateResolved;
    }

    public String getStateClosed() {
        return stateClosed;
    }

    public void setStateNew(String stateNew) {
        this.stateNew = stateNew;
    }

    public void setStateInProgress(String stateInProgress) {
        this.stateInProgress = stateInProgress;
    }

    public void setStateResolved(String stateResolved) {
        this.stateResolved = stateResolved;
    }

    public void setStateClosed(String stateClosed) {
        this.stateClosed = stateClosed;
    }

    public void setScmUsername(String scmUsername) {
        this.scmUsername = scmUsername;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void addCustomFieldValue(Integer id,String name, String value) {
        customFields.add(new CustomField(id,name,value));
    }

    protected Integer getVersionIdByName(String name) {
        try {
            List<Version> versions = redmineManager.getVersions(Integer.valueOf(projectId));
            for (Version version : versions) {
                if ( name.equals(version.getName()) ) {
                    return version.getId();
                }
            }
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    protected User getUserByName(String name) {
        try {
            List<User> users = redmineManager.getUsers();
            for (User user : users) {
                if ( name.equals(user.getLogin()) ) {
                    return user;
                }
            }
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}