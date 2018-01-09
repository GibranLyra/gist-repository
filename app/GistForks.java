public class GistForks {
    private String updated_at;
    private String created_at;
    private String id;
    private GistForksUser user;
    private String url;

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GistForksUser getUser() {
        return this.user;
    }

    public void setUser(GistForksUser user) {
        this.user = user;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
