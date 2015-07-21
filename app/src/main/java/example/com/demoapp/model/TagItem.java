package example.com.demoapp.model;

/**
 * Created by Long on 7/8/2015.
 */
public class TagItem {
    String id;
    String nameTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public TagItem(String id, String nameTag) {
        this.id = id;
        this.nameTag = nameTag;
    }
    public TagItem(){
        this.id = id;
        this.nameTag = "";
    }
}
