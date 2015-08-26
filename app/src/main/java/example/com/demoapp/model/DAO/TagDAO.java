package example.com.demoapp.model.DAO;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.model.TagItem;
import example.com.demoapp.utility.DbHelper;

/**
 * Created by Long on 7/8/2015.
 */
public class TagDAO extends BaseDAO {

    public TagDAO() {
        super();
    }

    public long countTags() {
        String query = "SELECT COUNT(*) FROM "+DbHelper.DB_TABLE_TAGS;
        long count = this.simpleQueryForLong(query);
        return count;
    }

    public int findLastIDTagNumber(){
        int number = 0;
        String query = "SELECT substr(_id,2) as "+DbHelper.LAST_ID_NUM+" \n" +
                "FROM "+DbHelper.DB_TABLE_TAGS+" " +
                "where "+DbHelper.DB_TAGS_ID+" like \"t%\" \n" +
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

    public void addTagToTags(String sentences_id, List<String> tagNamelist) {
        List<String> tagIdList = new ArrayList<>();

        int countId = this.findLastIDTagNumber() + 1;
        ContentValues values = new ContentValues();
        for (String tag_name : tagNamelist) {
            String query = "SELECT * FROM "+DbHelper.DB_TABLE_TAGS+"" +
                    " WHERE "+DbHelper.DB_TAGS_NAME+" ='" + tag_name + "'";
            this.query(query);
            Log.d("Cursor Count : ", cursor.getCount() + "");
            if (cursor.getCount() == 0) {
                String tag_id = "t"+(countId++);
                values.put(DbHelper.DB_TAGS_ID, tag_id);
                tagIdList.add(tag_id);
                values.put(DbHelper.DB_TAGS_NAME, tag_name);
                this.insert(DbHelper.DB_TABLE_TAGS, values);
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
            this.insert(DbHelper.DB_TABLE_TAGGING, values1);

        }

        this.closeCursor();
    }

    public void clearTags(String sentence_id) {
        String whereClause = DbHelper.DB_TAGGING_SENTENCES_ID+"='"+sentence_id+"'";
        this.delete(DbHelper.DB_TABLE_TAGGING, whereClause);
    }

    public ArrayList<String> getTagsFromTagging(String sentences_id) {
        ArrayList<String> tagList = new ArrayList<>();

        String query = "select * " +
                " from "+DbHelper.DB_TABLE_TAGGING+","+DbHelper.DB_TABLE_TAGS+
                " where "+DbHelper.DB_TABLE_TAGGING+".sentences_id ='" + sentences_id + "' " +
                " and "+DbHelper.DB_TABLE_TAGS+"._id = "+DbHelper.DB_TABLE_TAGGING+".tag_id";
        this.query(query);

        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_NAME));
            tagList.add(tag);
        }
        this.closeCursor();
        return tagList;

    }

    public ArrayList<TagItem> getAllTagFromTagsIgnoreItems(List<String> items) {

        ArrayList<TagItem> arrayList = null;
        //Build query
        String query = "select "+DbHelper.DB_TAGS_NAME+" from "+DbHelper.DB_TABLE_TAGS;
        if (items.size() > 0) {
            query += " where ";
            String condition = " And ";
            for (String i : items) {
                query += DbHelper.DB_TAGS_NAME+" !='" + i + "'" + condition;
            }
            query = query.substring(0, query.length() - condition.length());
        }
        this.query(query);

        if (cursor.moveToFirst()) {
            arrayList = new ArrayList<>();
            do {
                TagItem item = new TagItem();
                item.setNameTag(cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_NAME)));

                arrayList.add(item);
            } while (cursor.moveToNext());
        }

        this.closeCursor();
        return arrayList;
    }
    //////////LIST TAGS HERE!!!!!!!!!
    public ArrayList<TagItem> getAllTagFromTags(){
        ArrayList<TagItem> arrayTag = null;
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_TAGS;
        this.query(query);
        if (cursor.moveToFirst()){
            arrayTag = new ArrayList<>();
            do
            {
                TagItem item = new TagItem();
                item.setId(cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_ID)));
                item.setNameTag(cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_NAME)));

                arrayTag.add(item);
            }while(cursor.moveToNext());
        }
        this.closeCursor();
        return arrayTag;
    }
    public ArrayList<String> getIdFromTags(){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_TAGS;
        this.query(query);
        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGS_ID));
            arrayList.add(tag);
        }
        this.closeCursor();
        return arrayList;

    }
    public ArrayList<String> getIdFromTagging(){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_TAGGING;
        this.query(query);
        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGGING_TAG_ID));
            arrayList.add(tag);
        }
        this.closeCursor();
        return arrayList;
    }
    public ArrayList<String> listSentencesFollowTagId(String tagId){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DbHelper.DB_TABLE_TAGGING+
                " WHERE "+DbHelper.DB_TAGGING_TAG_ID+" ='"+tagId+"'";
        this.query(query);
        while (cursor.moveToNext()) {
            String tag = cursor.getString(cursor.getColumnIndex(DbHelper.DB_TAGGING_SENTENCES_ID));
            arrayList.add(tag);
        }
        this.closeCursor();
        return arrayList;
    }

    public void removeSentenceFromTag(String tag_id, String sentence_id){
        String whereClause = DbHelper.DB_TAGGING_SENTENCES_ID + "='" + sentence_id + "'" +
                " AND " + DbHelper.DB_TAGGING_TAG_ID + "='" + tag_id + "'";
        this.delete(DbHelper.DB_TABLE_TAGGING, whereClause);
    }

    public void removeTagByID(String tag_id){
        //remove all tag match tag_id on tagging
        this.delete(DbHelper.DB_TABLE_TAGGING,
                DbHelper.DB_TAGGING_TAG_ID + "='" + tag_id + "'");
        //remove tag match tag_id on tags
        this.delete(DbHelper.DB_TABLE_TAGS,
                DbHelper.DB_TAGS_ID + "='" + tag_id + "'");
    }

    public void updateTagName(String tag_id,String tag_name){
        ContentValues values = new ContentValues();
        values.put(DbHelper.DB_TAGS_NAME, tag_name);
        this.update(DbHelper.DB_TABLE_TAGS, values, DbHelper.DB_TAGS_ID + "='" + tag_id + "'");
    }
}
