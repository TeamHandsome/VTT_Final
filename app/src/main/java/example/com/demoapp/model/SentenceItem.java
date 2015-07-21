package example.com.demoapp.model;

import java.util.ArrayList;

/**
 * Created by Long on 6/22/2015.
 */
public class SentenceItem {
    String id;
    private String nameJp;
    private String nameVn;
    private String sound;
    private String image;
    private ArrayList tag_list;

    public String getNameJp() {
        return nameJp;
    }

    public void setNameJp(String nameJp) {
        this.nameJp = nameJp;
    }

    public String getNameVn() {
        return nameVn;
    }

    public void setNameVn(String nameVn) {
        this.nameVn = nameVn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList getTag_list() {
        return tag_list;
    }

    public void setTag_list(ArrayList tag_list) {
        this.tag_list = tag_list;
    }

    public SentenceItem(String id, String nameJp, String nameVn, String sound, String image) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameVn = nameVn;
        this.sound = sound;
        this.image = image;
    }

    public SentenceItem(String id, String nameJp, String nameVn, String sound, String image,
                        ArrayList tag_list) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameVn = nameVn;
        this.sound = sound;
        this.image = image;
        this.tag_list = tag_list;
    }

    public SentenceItem() {
        this.id = id;
        this.nameJp = "";
        this.nameJp = "";
        this.sound = "";
        this.image = "";
    }

}
