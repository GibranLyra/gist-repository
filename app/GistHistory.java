public class GistHistory {
    private String committed_at;
    private GistHistoryChange_status change_status;
    private String version;
    private GistHistoryUser user;
    private String url;

    public String getCommitted_at() {
        return this.committed_at;
    }

    public void setCommitted_at(String committed_at) {
        this.committed_at = committed_at;
    }

    public GistHistoryChange_status getChange_status() {
        return this.change_status;
    }

    public void setChange_status(GistHistoryChange_status change_status) {
        this.change_status = change_status;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public GistHistoryUser getUser() {
        return this.user;
    }

    public void setUser(GistHistoryUser user) {
        this.user = user;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
