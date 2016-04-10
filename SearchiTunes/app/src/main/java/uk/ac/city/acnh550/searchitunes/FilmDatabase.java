package uk.ac.city.acnh550.searchitunes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jaden on 23/11/2015.
 */
public class FilmDatabase extends SQLiteOpenHelper {

    public FilmDatabase(Context context){
        super(context, "film.db", null, 1);
    }

    public static final String DB_NAME = "film.db";
    public static final int DB_VERSION = 1;
    final String TABLE_NAME = "films";
    public static final String COL_ID = "keyword";
    public static final String COL_JSON = "json";

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME +
                " ( " + COL_ID + " TEXT, " + COL_JSON + " TEXT) ";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //adds new data to table
    public void insertData(String keyword, String result){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("keyword", keyword);
        cv.put("json",result);
        db.insert("films", null, cv);
        db.close();
    }

    //gets a result from the database
    public String getResult(String keyword){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT json FROM "+ TABLE_NAME + " WHERE keyword='" + keyword + "'";
        Cursor cursor = db.rawQuery(query, null);
        String result = null;
        while (cursor.moveToNext()){
            result = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return result;
    }
}
