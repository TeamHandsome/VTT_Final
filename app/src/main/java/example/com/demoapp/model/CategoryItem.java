package example.com.demoapp.model;

/**
 * Created by Tony on 22/7/2015.
 */
public class CategoryItem {
    int id;
    String name;
    String image;
    String childImage;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getChildImage() {
        return childImage;
    }

    public void setChildImage(String childImage) {
        this.childImage = childImage;
    }

    public CategoryItem(int id, String name, String image, String childImage) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.childImage = childImage;
    }

    public CategoryItem() {
        this.id = 0;
        this.name ="";
        this.image = "";
        this.childImage = "";
    }
}
