package example.com.demoapp.model;

/**
 * Created by Long on 7/8/2015.
 */
public class TagItem {
    int id;
    String nameTag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public TagItem(int id, String nameTag) {
        this.id = id;
        this.nameTag = nameTag;
    }
    public TagItem(){
        this.id = id;
        this.nameTag = "";
    }
}
