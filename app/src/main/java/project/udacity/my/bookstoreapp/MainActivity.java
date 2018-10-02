package project.udacity.my.bookstoreapp;

import project.udacity.my.bookstoreapp.BookContract.BookEntry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BookDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new BookDbHelper(this);
    }

    //TODO: call getWritableDatabase on different thread
    private void insertBookData(@NonNull String name,
                                int genre,
                                float price,
                                int quantity,
                                @Nullable String suppName,
                                @Nullable String suppNum) {

        //No price, no sale
        if (price > 0.0 && price < 200) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(BookEntry.COL_NAME, name);
            if (genre == BookEntry.GENRE_NONFICTION || genre == BookEntry.GENRE_FICTION) {
                contentValues.put(BookEntry.COL_GENRE, genre);
            } else {
                contentValues.put(BookEntry.COL_GENRE, BookEntry.GENRE_UNKNOWN);
            }
            //TODO: truncate price to within 2 decimal places
            contentValues.put(BookEntry.COL_PRICE, price);
            contentValues.put(BookEntry.COL_QUANTITY, quantity);
            contentValues.put(BookEntry.COL_SUPP_NAME, suppName);
            contentValues.put(BookEntry.COL_SUPP_PHONE, suppNum);

            long rowId = db.insert(BookEntry.TABLE_NAME, null, contentValues);

            //TODO: change this to an image confirmation
            if(rowId == -1)
                Toast.makeText(this, "Error returned, entry not registered", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, name + " successfully registered!", Toast.LENGTH_LONG).show();

            db.close();
        }
    }

    //TODO: call getReadableDatabase on different thread

    //Meant to allow for custom data retrieval
    private void getBookData(String[] projection) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(BookEntry.TABLE_NAME,
                projection,
                null, null, null, null, null);

        //Check what was requested in the projection and retrieve its index in the table
        final int NA = -1;
        int idIndex = NA,
                nameIndex = NA,
                genreIndex = NA,
                priceIndex = NA,
                quantIndex = NA,
                suppNameIndex = NA,
                suppPhoneIndex = NA;

        for(int i = 0; i < projection.length; i++) {

            switch(projection[i]) {
                case BookEntry._ID:
                    idIndex = cursor.getColumnIndex(BookEntry._ID);
                    break;
                case BookEntry.COL_NAME:
                    nameIndex = cursor.getColumnIndex(BookEntry.COL_NAME);
                    break;
                case BookEntry.COL_GENRE:
                    genreIndex = cursor.getColumnIndex(BookEntry.COL_GENRE);
                    break;
                case BookEntry.COL_PRICE:
                    priceIndex = cursor.getColumnIndex(BookEntry.COL_PRICE);
                    break;
                case BookEntry.COL_QUANTITY:
                    quantIndex = cursor.getColumnIndex(BookEntry.COL_QUANTITY);
                    break;
                case BookEntry.COL_SUPP_NAME:
                    suppNameIndex = cursor.getColumnIndex(BookEntry.COL_SUPP_NAME);
                    break;
                case BookEntry.COL_SUPP_PHONE:
                    suppPhoneIndex = cursor.getColumnIndex(BookEntry.COL_SUPP_PHONE);
                    break;
                default:
                    break;
            }
        }

        /**
         * TODO: Do whatever with requested data
         */

        cursor.close();
        db.close();
    }
}
