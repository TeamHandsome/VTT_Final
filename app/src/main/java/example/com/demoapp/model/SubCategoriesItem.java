package example.com.demoapp.model;

/**
 * Created by Long on 6/21/2015.
 */
public class SubCategoriesItem {
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

    public SubCategoriesItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubCategoriesItem() {
        this.id = id;
        this.name ="";
    }
}
