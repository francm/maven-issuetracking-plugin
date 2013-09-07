package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Project;
import cz.fg.issuetracking.api.Version;
import cz.fg.issuetracking.api.VersionImpl;
import cz.fg.issuetracking.api.VersionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Version manager Redmine implementation
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 22:11
 */
public class RedmineVersionManager implements VersionManager {

    RedmineProject project;

    public RedmineVersionManager(RedmineProject project) {
        this.project = project;
    }

    protected List<Version> getVersions(boolean requireReleased) {
        try {
            List<com.taskadapter.redmineapi.bean.Version> versions = project.getRedmineManager()
                    .getVersions(Integer.valueOf(project.getProjectId()));
            List<Version> result = new ArrayList<Version>(versions.size());
            for (com.taskadapter.redmineapi.bean.Version version : versions) {
                Version convert = convert(version);
                if ( convert.isReleased()==requireReleased ) {
                    result.add(convert);
                }
            }
            return result;
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
    }

    protected Version convert(com.taskadapter.redmineapi.bean.Version version) {
        boolean released = isVersionReleased(version);
        return new VersionImpl(version.getName(),version.getDescription(),version.getDueDate(), released);
    }

    protected boolean isVersionReleased(com.taskadapter.redmineapi.bean.Version version) {
        String status = version.getStatus();
        return status.equals("closed");
    }

    @Override
    public List<Version> getUnreleasedVersions() {
        return getVersions(false);
    }

    @Override
    public List<Version> getReleasedVersions() {
        return getVersions(true);
    }

    @Override
    public Version createVersion(String versionValue) {
        try {
            Project redmineProject = project.getRedmineManager().getProjectByKey(project.getProjectId());
            com.taskadapter.redmineapi.bean.Version version =
                    new com.taskadapter.redmineapi.bean.Version(redmineProject,versionValue);
            com.taskadapter.redmineapi.bean.Version created = project.getRedmineManager().createVersion(version);
            return convert(created);
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeVersion(String versionValue) {
        try {
            List<com.taskadapter.redmineapi.bean.Version> versions = project.getRedmineManager()
                    .getVersions(Integer.valueOf(project.getProjectId()));
            for (com.taskadapter.redmineapi.bean.Version version : versions) {
                if ( versionValue.equals(version.getName()) ) {
                    // redmine-java-api/issues/97
                    //version.setDueDate(new Date());
                    version.setDueDate(null);
                    version.setStatus("closed");
                    project.getRedmineManager().update(version);
                    return;
                }
            }
        } catch (RedmineException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Version not found");
    }

}