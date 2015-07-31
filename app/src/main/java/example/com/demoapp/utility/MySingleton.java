package example.com.demoapp.utility;

import java.util.ArrayList;

import example.com.demoapp.model.SentenceItem;

/**
 * Created by Tony on 31/7/2015.
 */
public class MySingleton {
    private static MySingleton instance;

    private DbHelper db;
    private ArrayList<SentenceItem> sentenceList;

    public static void initInstance() {
        if (instance == null) {
            // Create the instance
            instance = new MySingleton();
        }
    }

    public static MySingleton getInstance() {
        // Return the instance
        return instance;
    }

    public void customSingletonMethod() {
        // Custom method

    }

    public DbHelper getDb() {
        return db;
    }

    public void setDb(DbHelper db) {
        this.db = db;
    }

    public ArrayList<SentenceItem> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(ArrayList<SentenceItem> sentenceList) {
        this.sentenceList = sentenceList;
    }
}
