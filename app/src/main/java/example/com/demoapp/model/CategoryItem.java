package example.com.demoapp.model;

/**
 * Created by Tony on 22/7/2015.
 */
public class CategoryItem {
    int id;
    String name;

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

    public CategoryItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryItem() {
        this.id = id;
        this.name ="";
    }
}
