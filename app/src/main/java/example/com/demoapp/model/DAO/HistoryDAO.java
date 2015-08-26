package example.com.demoapp.model.DAO;

import android.content.ContentValues;

import java.util.ArrayList;

import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.DbHelper;

/**
 * Created by dmonkey on 7/24/2015.
 */
public class HistoryDAO extends BaseDAO {
    public HistoryDAO() {
        super();
    }

    public ArrayList<SentenceItem> getAllHistory(){
        ArrayList<SentenceItem> arrayList = null;
        String query ="SELECT * FROM sentences, history WHERE sentences._ids = history.sentences_id ORDER BY _id DESC";
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

    public void removeFromHistory(String id){
        String whereClause = DbHelper.DB_HISTORY_SENTENCES_ID + "='" + id + "'";
        this.delete(DbHelper.DB_TABLE_HISTORY, whereClause);
    }

    public void addHistory(String sentences_id){
        ContentValues values = new ContentValues();
        String query = "SELECT * FROM history WHERE sentences_id = '" + sentences_id + "'";
        this.query(query);

        if (cursor.getCount() == 0){
            values.put(DbHelper.DB_HISTORY_SENTENCES_ID, sentences_id);
            this.insert(DbHelper.DB_TABLE_HISTORY, values);
        }else {
            removeHistoryBySenID(sentences_id);
            values.put(DbHelper.DB_HISTORY_SENTENCES_ID, sentences_id);
            this.insert(DbHelper.DB_TABLE_HISTORY, values);
        }
        if (countId() > Consts.MAX_HISTORY_LENGTH){
            String whereClause =  DbHelper.DB_HISTORY_ID + "=" + findLastIDHistory();
            this.delete(DbHelper.DB_TABLE_HISTORY, whereClause);
        }
        this.closeCursor();
    }

    public void removeHistoryBySenID(String sentences_id){
        String whereClause =  DbHelper.DB_HISTORY_SENTENCES_ID + "='" + sentences_id+"'";
        this.delete(DbHelper.DB_TABLE_HISTORY, whereClause);
    }

    public long countId(){
        String query = "SELECT COUNT(*) FROM "+DbHelper.DB_TABLE_HISTORY;
        long count = this.simpleQueryForLong(query);
        return count;
    }

    public int findLastIDHistory() {
        int number = 0;
        String query = "SELECT _id FROM history ORDER BY _id ASC LIMIT 0 , 1";
        this.query(query);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(DbHelper.DB_HISTORY_ID));
            number = Integer.parseInt(id);
        }
        this.closeCursor();
        return number;
    }
}
