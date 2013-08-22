package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
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

    RedmineManager redmine;
    String project;

    public RedmineVersionManager(RedmineManager redmine, String project) {
        this.redmine = redmine;
        this.project = project;
    }

    protected List<Version> getVersions(boolean requireReleased) {
        try {
            List<com.taskadapter.redmineapi.bean.Version> versions = redmine.getVersions(Integer.valueOf(project));
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

    private Version convert(com.taskadapter.redmineapi.bean.Version version) {
        boolean released = isVersionReleased(version);
        return new VersionImpl(version.getName(),version.getDescription(),version.getDueDate(), released);
    }

    private boolean isVersionReleased(com.taskadapter.redmineapi.bean.Version version) {
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
}

