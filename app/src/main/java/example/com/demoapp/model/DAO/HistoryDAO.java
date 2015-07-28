package example.com.demoapp.model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.DbHelper;

/**
 * Created by dmonkey on 7/24/2015.
 */
public class HistoryDAO extends BaseDAO {
    DbHelper mDbHelper;
    public HistoryDAO(Context context) {
        try {
            mDbHelper = new DbHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<SentenceItem> getAllHistory(){
        ArrayList<SentenceItem> arrayList = null;
        String query ="SELECT * FROM sentences, history WHERE sentences._ids = history.sentences_id ORDER BY _id DESC";
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
    public void removeFromHistory(String id){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DbHelper.DB_TABLE_HISTORY, DbHelper.DB_HISTORY_SENTENCES_ID + "='" + id + "'", null);
        db.close();
    }
    public void addHistory(String sentences_id){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT * FROM history WHERE sentences_id = '" + sentences_id + "'";
        this.rawQueryReadonly(query);
        if (cursor.getCount() == 0){
            values.put(DbHelper.DB_HISTORY_SENTENCES_ID, sentences_id);
            db.insert(DbHelper.DB_TABLE_HISTORY, null, values);
        }else {
            removeIdhistory(sentences_id);
            values.put(DbHelper.DB_HISTORY_SENTENCES_ID, sentences_id);
            db.insert(DbHelper.DB_TABLE_HISTORY, null, values);
        }
        if (countId()==51){
            statement = database.compileStatement("delete from history where _id = '"+ findLastIDHistory() + "'");
            statement.execute();
        }
        cursor.close();
        db.close();

    }
    public void removeIdhistory(String sentences_id){
        database = mDbHelper.getWritableDatabase();
        statement = database.compileStatement("delete from history where sentences_id='"+ sentences_id + "'");
        statement.execute();
    }
    public long countId(){
        database = mDbHelper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM history";
        statement = database.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }
    public int findLastIDHistory() {
        int number = 0;
        String query = "SELECT _id FROM history ORDER BY _id ASC LIMIT 0 , 1";
        this.rawQueryReadonly(query);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(DbHelper.DB_HISTORY_ID));
            number = Integer.parseInt(id);
        }
        return number;
    }
}
