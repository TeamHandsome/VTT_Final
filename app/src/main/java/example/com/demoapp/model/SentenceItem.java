package example.com.demoapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Long on 6/22/2015.
 */
public class SentenceItem implements Parcelable{
    String id;
    private String nameJp;
    private String nameFull;
    private String nameVn;
    private String sound;
    private String image;
    private ArrayList tag_list;
    private boolean favorite;

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

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

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public SentenceItem(Parcel source) {
        this.id = source.readString();
        this.nameJp = source.readString();
        this.nameVn = source.readString();
        this.nameFull = source.readString();
        this.sound = source.readString();
        this.image = source.readString();
    }

    public SentenceItem(String id, String nameJp, String nameFull, String nameVn, String sound, String image) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameFull = nameFull;
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

    public SentenceItem(String id, String nameJp, String nameVn, String sound,
                        String image, boolean favorite) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameVn = nameVn;
        this.sound = sound;
        this.image = image;
        this.favorite = favorite;
    }

    public SentenceItem() {
        this.id = "";
        this.nameJp = "";
        this.nameJp = "";
        this.sound = "";
        this.image = "";
        this.nameFull = "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nameJp);
        parcel.writeString(nameVn);
        parcel.writeString(nameFull);
        parcel.writeString(sound);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<SentenceItem> CREATOR
            = new Parcelable.Creator<SentenceItem>() {

        public SentenceItem createFromParcel(Parcel in) {
            return new SentenceItem(in);
        }

        public SentenceItem[] newArray(int size) {
            return new SentenceItem[size];
        }
    };
}
