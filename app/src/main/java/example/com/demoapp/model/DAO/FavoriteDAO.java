package example.com.demoapp.model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.DbHelper;

/**
 * Created by dmonkey on 7/15/2015.
 */
public class FavoriteDAO extends BaseDAO {
    DbHelper mDbHelper;
    public FavoriteDAO(Context context) {
        try {
            mDbHelper = new DbHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<SentenceItem> getAllFavorite(){
        ArrayList<SentenceItem> arrayList = null;
        String query ="SELECT * FROM sentences, favorite WHERE sentences._ids = favorite.sentences_id";
        this.rawQueryReadonly(query);

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
        close();
        return arrayList;
    }

    public long countFav() {
        database = mDbHelper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM favorite";
        statement = database.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }

    public void addToFavorite(String sentences_id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long countId = countFav() + 1;
        Log.d("count: ",countId+"");
        ContentValues values = new ContentValues();
        values.put(DbHelper.DB_FAVORITE_ID, countId);
        values.put(DbHelper.DB_FAVORITE_SENTENCES_ID, sentences_id);
        db.insert(DbHelper.DB_TABLE_FAVORITE, null, values);
        db.close();
    }

    public void removeFromFavorite(String id){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DbHelper.DB_TABLE_FAVORITE,DbHelper.DB_FAVORITE_SENTENCES_ID+"='"+id+"'",null);
        db.close();
    }
}
