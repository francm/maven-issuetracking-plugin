package cz.fg.issuetracking.redmine;

import com.taskadapter.redmineapi.RedmineManager;
import cz.fg.issuetracking.api.Version;
import cz.fg.issuetracking.api.VersionManager;

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

    @Override
    public List<Version> getUnreleasedVersions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Version> getReleasedVersions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

