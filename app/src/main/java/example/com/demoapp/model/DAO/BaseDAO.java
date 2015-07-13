package example.com.demoapp.model.DAO;

import android.database.Cursor;
import android.database.SQLException;
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
    protected String myPath = DbHelper.DB_PATH + DbHelper.DB_NAME;

    protected SQLiteDatabase getDatabase() {
        return database;
    }

    protected void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    protected Cursor getCursor() {
        return cursor;
    }

    protected void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    protected SQLiteStatement getStatement() {
        return statement;
    }

    protected void setStatement(SQLiteStatement statement) {
        this.statement = statement;
    }

    /*Run a sql query on readonly mode of database
        @query String The sql query will be execute
         */
    protected void rawQueryReadonly(String query){
        this.opendatabase(0);
        cursor = database.rawQuery(query, null);
    }

    /*Run a sql query on readwrite mode of database
    @query String The sql query will be execute
     */
    protected void rawQueryReadwrite(String query){
        this.opendatabase(1);
        cursor = database.rawQuery(query, null);
    }

    //Open the database
    protected void opendatabase(int type) throws SQLException {
        //Open the database
        switch (type) {
            case 1://READWRITE
                database= SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            default://READONLY
                database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
    }
    /*Close database and cursor
    */
    public void close(){
        cursor.close();
        database.close();
    }
}
