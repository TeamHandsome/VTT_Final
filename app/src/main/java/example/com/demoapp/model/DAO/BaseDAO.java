package example.com.demoapp.model.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import example.com.demoapp.utility.DbHelper;

/**
 * Created by Tony on 6/7/2015.
 */
public class BaseDAO {
    protected SQLiteDatabase database = null;
    protected Cursor cursor = null;
    protected SQLiteStatement statement = null;

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

    public SQLiteStatement getStatement() {
        return statement;
    }

    public void setStatement(SQLiteStatement statement) {
        this.statement = statement;
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
