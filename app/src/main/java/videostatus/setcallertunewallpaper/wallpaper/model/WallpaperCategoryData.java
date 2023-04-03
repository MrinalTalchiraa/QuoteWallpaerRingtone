package videostatus.setcallertunewallpaper.wallpaper.model;

import java.io.Serializable;

public class WallpaperCategoryData implements Serializable {

    private String catename;
    //int size;

    public WallpaperCategoryData(){

    }

    public WallpaperCategoryData(String catename) {
        this.catename = catename;
        //this.size = size;
    }


   /* public void setSize(int size) {
        this.size = size;
    }*/

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

  /*  public int getSize() {
        return size;
    }*/

}
