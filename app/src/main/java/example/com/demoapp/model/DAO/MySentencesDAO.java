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
 * Created by dmonkey on 7/24/2015.
 */
public class MySentencesDAO extends BaseDAO {
    DbHelper mDbHelper;
    public MySentencesDAO(Context context) {
        try {
            mDbHelper = new DbHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<SentenceItem> getAllMS(){
        ArrayList<SentenceItem> arrayList = null;
        String query ="SELECT * FROM sentences WHERE sentences._ids like 's%'";
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

    public void removeFromMS(String id){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DbHelper.DB_TABLE_SENTENCES,DbHelper.DB_TABLE_SENTENCES_ID+"="+id,null);
        db.close();
    }
}
