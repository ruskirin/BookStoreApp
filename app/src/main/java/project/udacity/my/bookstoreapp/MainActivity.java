package project.udacity.my.bookstoreapp;

import project.udacity.my.bookstoreapp.BookContract.BookEntry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BookDbHelper dbHelper;

    private String[] testProjection = {
                BookEntry._ID,
                BookEntry.COL_NAME,
                BookEntry.COL_GENRE,
                BookEntry.COL_PRICE,
                BookEntry.COL_QUANTITY,
                BookEntry.COL_SUPP_NAME,
                BookEntry.COL_SUPP_PHONE
    };

    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Type "mainBookData" in Log Info to verify
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    @Override
    protected void onStart() {
        super.onStart();
        putBookData("Cat in the Hat",
                BookEntry.GENRE_FICTION,
                20.00,
                30,
                "Cat",
                "1800CATIHAT");
        getBookData(testProjection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new BookDbHelper(this);
    }

    //TODO: call getWritableDatabase on different thread
    private void putBookData(@NonNull String name,
                                          int genre,
                                          double price,
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

            /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             * Here is the log to verify everything functions
             * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             */
            Log.i("mainBookData", "Row registered: " + rowId);

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

        while(cursor.moveToNext()) {
            int id = 0, genre = 0, quant = 0;
            double price = 0.0;
            String name = "", suppName = "", suppPhone = "";

            if(idIndex != -1)
                id = cursor.getInt(idIndex);
            if(nameIndex != -1)
                name = cursor.getString(nameIndex);
            if(genreIndex != -1)
                genre = cursor.getInt(genreIndex);
            if(priceIndex != -1)
                price = cursor.getDouble(priceIndex);
            if(quantIndex != -1)
                quant = cursor.getInt(quantIndex);
            if(suppNameIndex != -1)
                suppName = cursor.getString(suppNameIndex);
            if(suppPhoneIndex != -1)
                suppPhone = cursor.getString(suppPhoneIndex);


            /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             * Here is the log to verify everything functions
             * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             */
            Log.i("mainBookData", "At ID: " + id
                            + ", NAME: " + name
                            + ", GENRE: " + genre
                            + ", PRICE: " + price
                            + ", QUANTITY: " + quant
                            + ", SUPPLIER NAME: " + suppName
                            + ", SUPPLIER PHONE: " + suppPhone);
        }

        cursor.close();
        db.close();
    }
}
