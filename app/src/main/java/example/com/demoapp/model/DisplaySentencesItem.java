package example.com.demoapp.model;

/**
 * Created by Long on 6/22/2015.
 */
public class DisplaySentencesItem {
    int id;
    String nameJp;
    String nameVn;
    String sound;
    String image;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public DisplaySentencesItem(int id, String nameJp, String nameVn, String sound,String image) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameVn = nameVn;
        this.sound = sound;
        this.image = image;
    }
    public DisplaySentencesItem() {
        this.id = id;
        this.nameJp = "";
        this.nameJp = "";
        this.sound = "";
        this.image = "";
    }

}
