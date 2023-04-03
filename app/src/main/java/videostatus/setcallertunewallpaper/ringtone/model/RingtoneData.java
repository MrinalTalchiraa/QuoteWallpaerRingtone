package videostatus.setcallertunewallpaper.ringtone.model;

import java.io.Serializable;

public class RingtoneData implements Serializable {

    private String uri, name;

    public RingtoneData() {

    }

    public RingtoneData(String uri, String name) {

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

