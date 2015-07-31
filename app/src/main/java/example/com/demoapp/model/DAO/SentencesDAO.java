package example.com.demoapp.model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.utility.DbHelper;
import example.com.demoapp.model.SentenceItem;

/**
 * Created by Tony on 6/7/2015.
 */
public class SentencesDAO extends BaseDAO {

    public SentencesDAO() {
        super();
    }

    public ArrayList<SentenceItem> getAllSentenceBySub(int subcategories_id) {
        ArrayList<SentenceItem> arrayList = new ArrayList<>();
        ArrayList<String> favoriteList = getAllFavoriteSentenceID();
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_SENTENCES +
                " INNER JOIN "+DbHelper.DB_TABLE_SUBCATEGORY +
                " ON "+DbHelper.DB_TABLE_SENTENCES+"."+DbHelper.DB_SENTENCES_SUBCAT+" =" +
                " "+DbHelper.DB_TABLE_SUBCATEGORY+"."+DbHelper.DB_SUBCATEGORIES_ID +
                " WHERE "+DbHelper.DB_TABLE_SENTENCES+"."+DbHelper.DB_SENTENCES_SUBCAT+"=" +
                "'" + subcategories_id + "'";
        this.query(query);

        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                SentenceItem item = new SentenceItem();
                item.setId(cursor.getString((cursor.getColumnIndex(DbHelper.DB_SENTENCES_ID))));
                item.setNameJp(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JP)));
                item.setNameVn(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_VN)));
                item.setSound(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_SOUND)));
                if (cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)) != null) {
                    item.setImage(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)));
                }
                item.setFavorite(this.isFavoriteSentence(favoriteList, item.getId()));

                arrayList.add(item);
            } while (cursor.moveToNext());
        }
        this.closeCursor();
        return arrayList;
    }

    public ArrayList<String> getAllFavoriteSentenceID() {
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SElECT " + DbHelper.DB_FAVORITE_SENTENCES_ID + " " +
                "From "+DbHelper.DB_TABLE_FAVORITE;
        this.query(query);
        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                String id = cursor.getString((cursor.getColumnIndex(DbHelper.DB_FAVORITE_SENTENCES_ID)));
                arrayList.add(id);
            } while (cursor.moveToNext());
        }
        this.closeCursor();
        return arrayList;
    }

    public boolean isFavoriteSentence(ArrayList<String> arrayList, String sentence_id) {
        for (String id : arrayList) {
            if (id.compareToIgnoreCase(sentence_id) == 0) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<SentenceItem> getAllSentences() {
        ArrayList<SentenceItem> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_SENTENCES;
        this.query(query);

        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                SentenceItem item = new SentenceItem();
                item.setId(cursor.getString((cursor.getColumnIndex(DbHelper.DB_SENTENCES_ID))));
                item.setNameJp(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JP)));
                item.setNameJpHira(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JPHIRA)));
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

    public ArrayList<SentenceItem> getAllMySentence() {
        ArrayList<String> favoriteList = getAllFavoriteSentenceID();
        ArrayList<SentenceItem> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_SENTENCES+" "+
                "WHERE "+DbHelper.DB_SENTENCES_ID+" like 's%'";
        this.query(query);

        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                SentenceItem item = new SentenceItem();
                item.setId(cursor.getString((cursor.getColumnIndex(DbHelper.DB_SENTENCES_ID))));
                item.setNameJp(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JP)));
                item.setNameJpHira(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JPHIRA)));
                item.setNameVn(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_VN)));
                item.setSound(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_SOUND)));
                if (cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)) != null) {
                    item.setImage(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)));
                }
                item.setFavorite(this.isFavoriteSentence(favoriteList, item.getId()));
                arrayList.add(item);
            } while (cursor.moveToNext());
        }
        this.closeCursor();
        return arrayList;
    }

    public void removeSentence(String id) {
        String whereClause = DbHelper.DB_SENTENCES_ID + "='" + id + "'";
        this.delete(DbHelper.DB_TABLE_SENTENCES, whereClause);
    }

    public int findLastIDMySenNumber() {
        int number = 0;
        String query = "SELECT substr(_ids,2) as "+DbHelper.LAST_ID_NUM+" \n" +
                "FROM "+DbHelper.DB_TABLE_SENTENCES+"\n" +
                "where "+DbHelper.DB_SENTENCES_ID+" like \"s%\" \n" +
                "ORDER By cast("+DbHelper.LAST_ID_NUM+" as unsigned) DESC \n" +
                "LIMIT 1";
        this.query(query);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(DbHelper.LAST_ID_NUM));
            number = Integer.parseInt(id);
        }
        this.closeCursor();
        return number;
    }

    public void addSentences(String id, String vn, String jp, String audio, String image_d) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.DB_SENTENCES_ID, id);
        values.put(DbHelper.DB_SENTENCES_VN, vn);
        values.put(DbHelper.DB_SENTENCES_SOUND, audio);
        values.put(DbHelper.DB_SENTENCES_IMAGE, image_d);
        values.put(DbHelper.DB_SENTENCES_JP, jp);
        this.insert(DbHelper.DB_TABLE_SENTENCES, values);
    }

    public void updateSentences(String id, String vn, String jp, String audio, String image_d) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.DB_SENTENCES_VN, vn);
        values.put(DbHelper.DB_SENTENCES_SOUND, audio);
        values.put(DbHelper.DB_SENTENCES_IMAGE, image_d);
        values.put(DbHelper.DB_SENTENCES_JP, jp);
        this.update(DbHelper.DB_TABLE_SENTENCES, values, DbHelper.DB_SENTENCES_ID + "='" + id + "'");
    }

    public ArrayList getAllSentenceByTagID(String tag_id) {
        ArrayList<String> favoriteList = getAllFavoriteSentenceID();
        ArrayList<SentenceItem> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_SENTENCES+" " +
                "INNER JOIN "+DbHelper.DB_TABLE_TAGGING +
                " ON "+DbHelper.DB_TABLE_SENTENCES+"." + DbHelper.DB_SENTENCES_ID + " = " +
                DbHelper.DB_TABLE_TAGGING+"." + DbHelper.DB_TAGGING_SENTENCES_ID +
                " WHERE "+DbHelper.DB_TABLE_TAGGING+"." + DbHelper.DB_TAGGING_TAG_ID + "='" + tag_id + "'";
        this.query(query);

        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                SentenceItem item = new SentenceItem();
                item.setId(cursor.getString((cursor.getColumnIndex(DbHelper.DB_SENTENCES_ID))));
                item.setNameJp(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JP)));
                item.setNameVn(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_VN)));
                item.setSound(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_SOUND)));
                if (cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)) != null) {
                    item.setImage(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)));
                }
                item.setFavorite(this.isFavoriteSentence(favoriteList, item.getId()));
                arrayList.add(item);
            } while (cursor.moveToNext());
        }
        this.closeCursor();
        return arrayList;
    }

}
