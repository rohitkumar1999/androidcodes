package me.kumarrohit.planetinfo;

public class planet {

    String name ;
    String dfe ;
    String dfs ;
    String url ;

    public planet(String name, String dfe, String dfs, String url) {
        this.name = name;
        this.dfe = dfe;
        this.dfs = dfs;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDfe() {
        return dfe;
    }

    public void setDfe(String dfe) {
        this.dfe = dfe;
    }

    public String getDfs() {
        return dfs;
    }

    public void setDfs(String dfs) {
        this.dfs = dfs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
