package example.com.demoapp.model;

/**
 * Created by Tony on 22/7/2015.
 */
public class CategoryItem {
    int id;
    String name;
    String image;

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

    public CategoryItem(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public CategoryItem() {
        this.id = 0;
        this.name ="";
        this.image = "";
    }
}
