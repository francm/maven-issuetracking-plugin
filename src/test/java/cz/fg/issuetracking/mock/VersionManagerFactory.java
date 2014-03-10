package cz.fg.issuetracking.mock;

import cz.fg.issuetracking.api.Version;
import cz.fg.issuetracking.api.VersionImpl;
import cz.fg.issuetracking.api.VersionManager;
import org.joda.time.DateTime;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Version manager mock factory
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         19.8.13 9:00
 */
public class VersionManagerFactory {

    public static VersionManager getInstance() {
        List<Version> released = new ArrayList<Version>();
        released.add(createReleasedVersion("1.0",   1969,1,1));
        released.add(createReleasedVersion("1.0.1", 1999,12,24));
        released.add(createReleasedVersion("1.1",   2008,1,20));
        released.add(createReleasedVersion("1.2",   2010,2,28));
        released.add(createReleasedVersion("2.0.0-RC",2011,6,30));
        released.add(createReleasedVersion("2.0.0", 2013,12,25));

        List<Version> unreleased = new ArrayList<Version>();
        unreleased.add(createUnreleasedVersion("2.1.0"));
        unreleased.add(createUnreleasedVersion("2.1.4"));
        unreleased.add(createUnreleasedVersion("2.2"));
        unreleased.add(createUnreleasedVersion("3.0"));

        VersionManager versionManager = Mockito.mock(VersionManager.class);
        Mockito.doReturn(released).when(versionManager).getReleasedVersions();
        Mockito.doReturn(unreleased).when(versionManager).getUnreleasedVersions();
        return versionManager;
    }

    static Version createReleasedVersion(String versionNumber,int year, int month, int day) {
        Date released = new DateTime().withDate(year, month, day).withTimeAtStartOfDay().toDate();
        Version v = new VersionImpl(versionNumber,"Description of version "+versionNumber,released,true);
        return v;
    }

    static Version createUnreleasedVersion(String versionNumber) {
        Version v = new VersionImpl(versionNumber,"Description of version "+versionNumber,null,false);
        return v;
    }

}