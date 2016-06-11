package com.example.macbook.getref;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by macbook on 11.04.16.
 */
public class DBMovies {
    private static final String DATABASE_NAME = "MoviesDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String MOVIES_TABLE_NAME = "moviesTable";

    //constants about movies table
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Title";
    private static final String COLUMN_DESCRIPTION = "Description";
    private static final String COLUMN_ACTORS = "Actors";
    private static final String COLUMN_PICTURE = "Picture";


    private SQLiteDatabase mMoviesDataBase;


    public DBMovies(Context context) {
        OpenHelperMovies mMovieHelper = new OpenHelperMovies(context);
        mMoviesDataBase = mMovieHelper.getWritableDatabase();

    }

    public long insert(String title, String description, String actors, String picture) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, title);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_ACTORS, actors);
        cv.put(COLUMN_PICTURE, picture);
        long curID = mMoviesDataBase.insert(MOVIES_TABLE_NAME, null, cv);

        return curID;
    }
    //put null values if it's not changing

    public int updateMovie(long targetID, String title, String description, String actors, String picture){
            ContentValues cv = new ContentValues();
            if(title!=null)
                cv.put(COLUMN_NAME,title);
            if(description!=null)
                cv.put(COLUMN_DESCRIPTION,description);
            if(actors!=null)
                cv.put(COLUMN_ACTORS,actors);
            if(picture!=null)
                cv.put(COLUMN_PICTURE,picture);
            return mMoviesDataBase.update(MOVIES_TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(targetID)});
    }

    void deleteAll(){
        mMoviesDataBase.delete(MOVIES_TABLE_NAME, null, null);


    }
    public void deleteMovie(long id){
        mMoviesDataBase.delete(MOVIES_TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }


    public String[] select(long id){
        Cursor mCursor = mMoviesDataBase.query(MOVIES_TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        return new String[]{mCursor.getString(1),mCursor.getString(2),mCursor.getString(3),mCursor.getString(4)};
    }
    public ArrayList<String[]> selectAllMovies(){
        Cursor mCursor = mMoviesDataBase.query(MOVIES_TABLE_NAME, null,null,null,null,null,null);
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        mCursor.moveToFirst();
        if(!mCursor.isAfterLast()){
            do{
                long id = mCursor.getLong(0);
                arrayList.add(new String[]{
                        String.valueOf(id),
                        mCursor.getString(1),
                        mCursor.getString(2),
                        mCursor.getString(3),
                        mCursor.getString(4),
                });
            }
            while (mCursor.moveToNext());
        }
        return arrayList;
    }

    private class OpenHelperMovies extends SQLiteOpenHelper {


        OpenHelperMovies(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE "+ MOVIES_TABLE_NAME+" ("+
                    COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_NAME+" TEXT, "+
                    COLUMN_DESCRIPTION+" TEXT, "+
                    COLUMN_ACTORS+" TEXT, "+
                    COLUMN_PICTURE+" TEXT);";

            db.execSQL(query);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MOVIES_TABLE_NAME);
        }
    }

}