package example.com.demoapp.model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.utility.DbHelper;
import example.com.demoapp.model.TagItem;
import example.com.demoapp.utility.StringUtils;

/**
 * Created by Long on 7/8/2015.
 */
public class TagDAO extends BaseDAO {
    DbHelper mDbHelper;

    public TagDAO(Context context) {
        try {
            mDbHelper = new DbHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long countTags() {
        database = mDbHelper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM tags";
        statement = database.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }

    public int findLastIDTagNumber(){
        int number = 0;
        String query = "SELECT substr(_id,2) as last_id_num \n" +
                "FROM tags where tags._id like \"t%\" \n" +
                "ORDER By cast(last_id_num as unsigned) DESC \n" +
                "LIMIT 1";
        this.rawQueryReadonly(query);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(DbHelper.LAST_ID_NUM));
            number = Integer.parseInt(id);
        }
        return number;
    }

    public void addTagToTags(String sentences_id, List<String> tagNamelist) {
        SQLiteDatabase dbWriteAble = mDbHelper.getWritableDatabase();
        List<String> tagIdList = new ArrayList<>();

        int countId = this.findLastIDTagNumber() + 1;
        ContentValues values = new ContentValues();
        for (String tag_name : tagNamelist) {
            String query = "SELECT * FROM tags WHERE name ='" + tag_name + "'";
            this.rawQueryReadonly(query);
            Log.d("Cursor Count : ", cursor.getCount() + "");
            if (cursor.getCount() == 0) {
                String tag_id = "t"+(countId++);
                values.put(DbHelper.DB_TAGS_ID, tag_id);
                tagIdList.add(tag_id);
                values.put(DbHelper.DB_TAGS_NAME, tag_name);
                dbWriteAble.insert(DbHelper.DB_TABLE_TAGS, null, values);
            } else {
                while (cursor.moveToNext()) {
                    String tag_id = cursor.getString((cursor.getColumnIndex(DbHelper.DB_TAGS_ID)));
                    tagIdList.add(tag_id);
                }
            }
        }


        this.clearTags(sentences_id);
        Log.d("Number of Tag : ", tagIdList + "");
        ContentValues values1 = new ContentValues();
        for (String i : tagIdList) {
            values1.put(DbHelper.DB_TAGGING_TAG_ID, i);
            values1.put(DbHelper.DB_TAGGING_SENTENCES_ID, sentences_id);
            database.insert(DbHelper.DB_TABLE_TAGGING, null, values1);

        }

        close();
    }

    public void clearTags(String i) {
        database = mDbHelper.getWritableDatabase();
        statement = database.compileStatement("delete from tagging where sentences_id='"+ i + "'");
        statement.execute();
    }

    public ArrayList<String> getTagsFromTagging(String sentences_id) {
        ArrayList<String> tagList = new ArrayList<>();

        String query = "select * from tagging,tags " +
                "where tagging.sentences_id='" + sentences_id + "' and tags._id = tagging.tag_id";
        this.rawQueryReadonly(query);

        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_NAME));
            tagList.add(tag);
        }
        cursor.close();
        return tagList;

    }

    public ArrayList<TagItem> getAllTagFromTagsIgnoreItems(List<String> items) {

        ArrayList<TagItem> arrayList = null;
        //Build query
        String query = "select name from tags";
        if (items.size() > 0) {
            query += " where ";
            String condition = " And ";
            for (String i : items) {
                query += "name !='" + i + "'" + condition;
            }
            query = query.substring(0, query.length() - condition.length());
        }
        this.rawQueryReadonly(query);

        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                TagItem item = new TagItem();
                //item.setId(cursor.getInt((cursor.getColumnIndex(DbHelper.DB_TAGS_ID))));
                item.setNameTag(cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_NAME)));

                arrayList.add(item);
            } while (cursor.moveToNext());
        }
        close();
        return arrayList;
    }
    //////////LIST TAGS HERE!!!!!!!!!
    public ArrayList<TagItem> getAllTagFromTags(){
        ArrayList<TagItem> arrayTag = null;
        String query = "SELECT * FROM tags";
        this.rawQueryReadonly(query);
        if (cursor.moveToFirst()){
            arrayTag = new ArrayList<TagItem>();
            do
            {
                TagItem item = new TagItem();
                item.setId(cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_ID)));
                item.setNameTag(cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_NAME)));

                arrayTag.add(item);
            }while(cursor.moveToNext());
        }
        return arrayTag;
    }
    public ArrayList<String> getIdFromTags(){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM tags";
        this.rawQueryReadonly(query);
        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_ID));
            arrayList.add(tag);
        }
        cursor.close();
        return arrayList;

    }
    public ArrayList<String> getIdFromTagging(){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM tagging";
        this.rawQueryReadonly(query);
        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGGING_TAG_ID));
            arrayList.add(tag);
        }
        cursor.close();
        return arrayList;
    }
    public ArrayList<String> listSentencesFollowTagId(String tagId){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM tagging WHERE tag_id ='"+tagId+"'";
        this.rawQueryReadonly(query);
        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGGING_SENTENCES_ID));
            arrayList.add(tag);
        }
        cursor.close();
        return arrayList;
    }

    public void removeSentenceFromTag(String tag_id, String sentence_id){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DbHelper.DB_TABLE_TAGGING,
                DbHelper.DB_TAGGING_SENTENCES_ID + "='" + sentence_id + "'" +
                        " AND " + DbHelper.DB_TAGGING_TAG_ID + "='" + tag_id + "'", null);
        db.close();
    }

    public void removeTagByID(String tag_id){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //remove all tag match tag_id on tagging
        db.delete(DbHelper.DB_TABLE_TAGGING,
                        DbHelper.DB_TAGGING_TAG_ID + "='" + tag_id + "'", null);
        //remove tag match tag_id on tags
        db.delete(DbHelper.DB_TABLE_TAGS,
                DbHelper.DB_TAGS_ID + "='" + tag_id + "'", null);
        db.close();
    }
}
