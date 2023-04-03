package videostatus.setcallertunewallpaper.ringtone.model;

public class RingtoneCategoryData {

    private String catename;
    int size;

    public RingtoneCategoryData(){

    }

    public RingtoneCategoryData(String catename) {
        this.catename = catename;
        //this.size = size;
    }


    /*public void setSize(int size) {
        this.size = size;
    }*/

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

   /* public int getSize() {
        return size;
    }*/

}
