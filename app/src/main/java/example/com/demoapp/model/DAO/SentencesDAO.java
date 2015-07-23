package example.com.demoapp.model.DAO;

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
    DbHelper mDbHelper;

    public SentencesDAO() {
    }

    public SentencesDAO(Context context) {
        try {
            mDbHelper = new DbHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SentenceItem> getAllSentenceBySub(int subcategories_id){
        ArrayList<SentenceItem> arrayList = null;
        ArrayList<String> favoriteList= getAllFavoriteSentenceID();
        String query ="SELECT * FROM sentences INNER JOIN subcategories" +
                " ON sentences.subcategories_id = subcategories._id" +
                " WHERE sentences.subcategories_id='"+subcategories_id+"'";
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
                item.setFavorite(this.isFavoriteSentence(favoriteList,item.getId()));

                arrayList.add(item);
            }while(cursor.moveToNext());
        }
        close();
        return arrayList;
    }

    public void addUri(List<Uri> arrayNewSen){

    }

    public ArrayList getAllFavoriteSentenceID(){
        ArrayList<String> arrayList = null;
        String query ="SElECT "+DbHelper.DB_FAVORITE_SENTENCES_ID+" From favorite";
        this.rawQueryReadonly(query);
        if(cursor.moveToFirst())
        {
            arrayList = new ArrayList<>();
            do
            {
                String id = cursor.getString((cursor.getColumnIndex(DbHelper.DB_FAVORITE_SENTENCES_ID)));
                arrayList.add(id);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }
    public boolean isFavoriteSentence(ArrayList<String> arrayList, String sentence_id){
        for (String id : arrayList){
            if (id.compareToIgnoreCase(sentence_id)==0){
                return true;
            }
        }
        return false;
    }
}
