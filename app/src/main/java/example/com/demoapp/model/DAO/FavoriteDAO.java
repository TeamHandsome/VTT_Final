package example.com.demoapp.model.DAO;

import android.content.ContentValues;

import java.util.ArrayList;

import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.DbHelper;

/**
 * Created by dmonkey on 7/15/2015.
 */
public class FavoriteDAO extends BaseDAO {
    public FavoriteDAO() {
        super();
    }
    public ArrayList<SentenceItem> getAllFavorite(){
        ArrayList<SentenceItem> arrayList = null;
        String query ="SELECT * FROM "+DbHelper.DB_TABLE_SENTENCES+", "+DbHelper.DB_TABLE_FAVORITE+
                " WHERE "+DbHelper.DB_TABLE_SENTENCES+"."+DbHelper.DB_SENTENCES_ID+" = " +
                " "+DbHelper.DB_TABLE_FAVORITE+"."+DbHelper.DB_FAVORITE_SENTENCES_ID;
        this.query(query);

        if(cursor.moveToFirst())
        {
            arrayList = new ArrayList<>();
            do
            {
                SentenceItem item = new SentenceItem();
                item.setId(cursor.getString((cursor.getColumnIndex(DbHelper.DB_SENTENCES_ID))));
                item.setNameJp(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_JP)));
                item.setNameVn(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_VN)));
                item.setSound(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_SOUND)));
                if (cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE))!=null){
                    item.setImage(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SENTENCES_IMAGE)));
                }
                arrayList.add(item);
            }while(cursor.moveToNext());
        }
        this.closeCursor();
        return arrayList;
    }

    public void addToFavorite(String sentences_id) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.DB_FAVORITE_SENTENCES_ID, sentences_id);
        this.insert(DbHelper.DB_TABLE_FAVORITE, values);
    }

    public void removeFromFavorite(String id){
        String whereClause =  DbHelper.DB_FAVORITE_SENTENCES_ID + "='" + id + "'";
        this.delete(DbHelper.DB_TABLE_FAVORITE,whereClause);
    }
}
