package videostatus.setcallertunewallpaper.quotestatus.model;

public class QuotesCategoryData {

    private String catename ;
    ///int size;

    public QuotesCategoryData(){

    }

    public QuotesCategoryData(String catename) {
        this.catename = catename;
        //this.size = size;
    }


 /*   public void setSize(int size) {
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
