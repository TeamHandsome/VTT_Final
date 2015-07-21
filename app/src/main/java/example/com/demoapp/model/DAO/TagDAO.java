package example.com.demoapp.model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.adapter.SentencesAdapter;
import example.com.demoapp.utility.DbHelper;
import example.com.demoapp.model.TagItem;

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

    public void addTagToTags(String sentences_id, List<String> tag) {
        database = mDbHelper.getWritableDatabase();
        List<Integer> arrayList = new ArrayList<>();

        long countId = countTags() + 1;
        ContentValues values = new ContentValues();
        for (String i : tag) {
            String sql = "SELECT * FROM tags WHERE name ='" + i + "'";
            Cursor cursor = database.rawQuery(sql, null);
            Log.d("Cursor Count : ", cursor.getCount() + "");
            if (cursor.getCount() == 0) {
                values.put(DbHelper.DB_TAGS_ID, countId++);
                arrayList.add((int) countId - 1);
                values.put(DbHelper.DB_TAGS_NAME, i);
                database.insert(DbHelper.DB_TABLE_TAGS, null, values);

            } else {
                while (cursor.moveToNext()) {
                    int tag_id = cursor.getInt((cursor.getColumnIndex(DbHelper.DB_TAGS_ID)));
                    arrayList.add(tag_id);
                }
            }
        }


        this.clearTags(sentences_id);
        Log.d("Number of Tag : ", arrayList + "");
        ContentValues values1 = new ContentValues();
        for (int i : arrayList) {
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
}
