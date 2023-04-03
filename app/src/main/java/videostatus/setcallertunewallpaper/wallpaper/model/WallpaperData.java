package videostatus.setcallertunewallpaper.wallpaper.model;

import java.io.Serializable;

public class WallpaperData implements Serializable {

    private String uri, name;

    public WallpaperData() {

    }

    public WallpaperData(String uri, String name) {

        this.uri = uri;
        this.name = name;

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

