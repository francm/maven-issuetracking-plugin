package cz.fg.issuetracking.api;

/**
 * Issue implementation
 *
 * @author Michal Franc, FG Forrest a.s. (c) 2013
 *         18.8.13 23:38
 */
public class IssueImpl implements Issue {

    String id;
    String name;
    String url;
    String developer;
    VersionDescriptor versionDescriptor;
    boolean done;

    public IssueImpl(String id, String name, String url, String developer, String version, boolean done) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.developer = developer;
        this.versionDescriptor = version==null?null:new VersionDescriptor(version);
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDeveloper() {
        return developer;
    }

    public VersionDescriptor getVersionDescriptor() {
        return versionDescriptor;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IssueImpl");
        sb.append("{id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", developer='").append(developer).append('\'');
        sb.append(", versionDescriptor=").append(versionDescriptor);
        sb.append(", done=").append(done);
        sb.append('}');
        return sb.toString();
    }
}
