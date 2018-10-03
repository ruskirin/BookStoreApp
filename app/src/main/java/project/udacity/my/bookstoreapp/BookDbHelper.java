package project.udacity.my.bookstoreapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import project.udacity.my.bookstoreapp.BookContract.BookEntry;


public class BookDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bookstore.db";
    public static final int DATABASE_VERSION = 1;

    public BookDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COL_NAME + " TEXT NOT NULL, "
                + BookEntry.COL_GENRE + " INTEGER DEFAULT " + BookEntry.GENRE_UNKNOWN + ", "
                + BookEntry.COL_PRICE + " REAL NOT NULL, "
                + BookEntry.COL_QUANTITY + " INTEGER NOT NULL, "
                + BookEntry.COL_SUPP_NAME + " TEXT DEFAULT " + BookEntry.SUPP_UNKNOWN + ", "
                + BookEntry.COL_SUPP_PHONE + " TEXT DEFAULT " + BookEntry.SUPP_UNKNOWN
                + ");");
    }

    //TODO: address the possible issue with this method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME);
        onCreate(db);
    }
}
