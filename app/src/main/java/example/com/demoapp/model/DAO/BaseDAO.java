package example.com.demoapp.model.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import example.com.demoapp.common.DbHelper;

/**
 * Created by Tony on 6/7/2015.
 */
public class BaseDAO {
    public SQLiteDatabase database = null;
    public Cursor cursor = null;

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    /*Run a sql query on readonly mode of database
    @query String The sql query will be execute
     */
    public void rawQueryReadonly(String query){
        database = SQLiteDatabase.openDatabase(DbHelper.DB_PATH + DbHelper.DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        cursor = database.rawQuery(query, null);
    }

    /*Run a sql query on readwrite mode of database
    @query String The sql query will be execute
     */
    public void rawQueryReadwrite(String query){
        database = SQLiteDatabase.openDatabase(DbHelper.DB_PATH + DbHelper.DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        cursor = database.rawQuery(query, null);
    }

    /*Close database and cursor
    */
    public void close(){
        cursor.close();
        database.close();
    }
}
