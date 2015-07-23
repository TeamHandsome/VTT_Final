package example.com.demoapp.model;

/**
 * Created by Long on 6/21/2015.
 */
public class SubCategoriesItem {
    int id;
    String name;
    String image_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubCategoriesItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubCategoriesItem(String image_url, int id, String name) {
        this.image_url = image_url;
        this.id = id;
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public SubCategoriesItem() {
        this.id = id;
        this.name ="";
    }
}
