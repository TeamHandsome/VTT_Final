package example.com.demoapp.model.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import example.com.demoapp.utility.DbHelper;
import example.com.demoapp.utility.MySingleton;

/**
 * Created by Tony on 6/7/2015.
 */
public class BaseDAO {
    private DbHelper dbHelper = null;
    protected Cursor cursor = null;
    private SQLiteDatabase db = null;

    public BaseDAO() {
        dbHelper = MySingleton.getInstance().getDb();
        dbHelper.opendatabase();
        db = dbHelper.getMyDataBase();
    }

    /*Insert into DB
    */
    protected long insert(String tableName, ContentValues values){
        return db.insert(tableName,null,values);
    }
    protected long insert(String tableName, ContentValues values, @Nullable String nullColumnHack){
        return db.insert(tableName,nullColumnHack,values);
    }

    /*Update DB
    */
    protected long update(String tableName, ContentValues values, @Nullable String whereClause){
        return db.update(tableName, values, whereClause, null);
    }
    protected long update(String tableName, ContentValues values, @Nullable String whereClause,
                          @Nullable String[] whereArgs){
        return db.update(tableName, values, whereClause,whereArgs);
    }

    /*Delete from DB
    */
    protected long delete(String tableName, @Nullable String whereClause){
        return db.delete(tableName, whereClause, null);
    }
    protected long delete(String tableName, String whereClause,@Nullable String[] whereArgs){
        return db.delete(tableName, whereClause, whereArgs);
    }

    /*Raw query
    */
    protected Cursor query(String query){
        return cursor = db.rawQuery(query, null);
    }

    /*Simple query for long
    */
    protected long simpleQueryForLong(String query){
        return db.compileStatement(query).simpleQueryForLong();
    }

    /*Simple query for string
    */
    protected String simpleQueryForString(String query){
        return db.compileStatement(query).simpleQueryForString();
    }

    /*Close cursor only
        */
    protected void closeCursor(){
        cursor.close();
    }
}
