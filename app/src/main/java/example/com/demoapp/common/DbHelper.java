package example.com.demoapp.common;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.model.DisplaySentencesItem;
import example.com.demoapp.model.SubCategoriesItem;

/**
 * Created by Long on 6/21/2015.
 */
public class DbHelper extends SQLiteOpenHelper{

    public static String DB_PATH = "/data/data/example.com.demoapp/databases/";
    public static String DB_NAME = "dbtts.sqlite";
    public static String DB_SUBCATEGORIES_ID = "_id";
    public static String DB_SUBCATEGORIES_NAME = "name";
    public static String DB_SENTENCES_ID = "_id";
    public static String DB_SENTENCES_JP = "jp";
    public static String DB_SENTENCES_SOUND = "audio";
    public static String DB_SENTENCES_IMAGE = "image_d";
    public static String DB_SENTENCES_VN = "vn";

    private Context context;
    private SQLiteDatabase myDataBase;

    public DbHelper(Context context) throws IOException{
        super( context , DB_NAME , null , 1);
        this.context = context;
    }



    // Creates a empty database on the system and rewrites it with your own database.
    public void createdatabase() throws IOException {

        boolean dbexist = checkdatabase();
        if(dbexist)
        {
            //System.out.println(" Database exists.");
        }
        else{
            this.getReadableDatabase();
            try{
                copydatabase();
            }
            catch(IOException e){
                throw new Error("Error copying database");
            }
        }
    }
    // Check if the database exist to avoid re-copy the data
    private boolean checkdatabase(){

        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try{
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        }
        catch(SQLiteException e){
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }
    // copy your assets db to the new system DB
    public void copydatabase() throws IOException{
        //Open your local db as the input stream
        InputStream myinput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream("/data/data/example.com.demoapp/databases/dbtts.sqlite");

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0)
        {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }
    //Open the database
    public void opendatabase() throws SQLException{
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
