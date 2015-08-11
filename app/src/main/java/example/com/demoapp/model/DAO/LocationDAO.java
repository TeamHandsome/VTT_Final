package example.com.demoapp.model.DAO;

import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.DbHelper;

/**
 * Created by Long on 8/8/2015.
 */
public class LocationDAO extends BaseDAO{
    int type_id;

    public LocationDAO() {
        super();
    }
    public List<Integer> getLocation_id(){
        List<Integer> list = null;
        String query = "SELECT location_id FROM locating";
        this.query(query);
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DbHelper.DB_LOCATING_LOCATION_ID));
                list.add(id);
            } while (cursor.moveToNext());
        }
        this.closeCursor();
        return list;
    }
    public ArrayList<SentenceItem> getSentencesByLocation(int i) {
        ArrayList<SentenceItem> arrayList = new ArrayList<>();

        String query1 = "SELECT * FROM locating, sentences WHERE locating.sentence_id = sentences._ids " +
                "AND locating.location_id ='"+i+"'";

        this.query(query1);
        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                SentenceItem item = new SentenceItem();
                item.setId(cursor.getString((cursor.getColumnIndex(DbHelper.DB_SENTENCES_ID))));
                item.setNameJp(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JP)));
                item.setNameFull(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JPHIRA)));
                item.setNameVn(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_VN)));
                item.setSound(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_SOUND)));
                if (cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)) != null) {
                    item.setImage(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)));
                }

                arrayList.add(item);
            } while (cursor.moveToNext());
        }
        this.closeCursor();
        return arrayList;
    }
}
