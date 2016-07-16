package co.go.pokemon.model;

/**
 * Created by fahim on 7/16/16.
 */

public class Deals {
    private String uid;

    private String name;


    private BannerImage banner_image;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BannerImage getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(BannerImage banner_image) {
        this.banner_image = banner_image;
    }

    @Override
    public String toString() {
        return "ClassPojo [uid = " + uid + ", name = " + name + ", banner_image = " + banner_image + "]";
    }
}

