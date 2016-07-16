package co.go.pokemon.model;

/**
 * Created by fahim on 7/16/16.
 */

public class BannerImage {
    private String url;

    private String aspect_ratio;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    @Override
    public String toString() {
        return "ClassPojo [url = " + url + ", aspect_ratio = " + aspect_ratio + "]";
    }
}
