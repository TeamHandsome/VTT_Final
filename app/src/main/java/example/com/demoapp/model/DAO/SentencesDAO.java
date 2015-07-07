package example.com.demoapp.model.DAO;

import java.util.ArrayList;

import example.com.demoapp.common.DbHelper;
import example.com.demoapp.model.DisplaySentencesItem;

/**
 * Created by Tony on 6/7/2015.
 */
public class SentencesDAO extends BaseDAO {

    public ArrayList<DisplaySentencesItem> getAllSentenceBySub(int subcategories_id){
        ArrayList<DisplaySentencesItem> arrayList = null;
        String query ="SELECT * FROM sentences INNER JOIN subcategories" +
                " ON sentences.subcategories_id = subcategories._id" +
                " WHERE sentences.subcategories_id='"+subcategories_id+"'";
        this.rawQueryReadonly(query);

        if(cursor.moveToFirst())
        {
            arrayList = new ArrayList<DisplaySentencesItem>();
            do
            {
                DisplaySentencesItem item = new DisplaySentencesItem();
                item.setId(cursor.getInt((cursor.getColumnIndex(DbHelper.DB_SENTENCES_ID))));
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
}
