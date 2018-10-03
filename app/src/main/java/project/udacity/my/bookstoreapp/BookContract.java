package project.udacity.my.bookstoreapp;

import android.provider.BaseColumns;

public final class BookContract {

    private BookContract() {}

    public static final class BookEntry {

        public static final String TABLE_NAME = "books";

        public static final String _ID = BaseColumns._ID;

        //TODO: Add links to the appropriate variable entries in the columns

        public static final String COL_NAME = "name";
        public static final String COL_GENRE = "genre";
        public static final String COL_PRICE = "price";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_SUPP_NAME = "supplier_name";
        public static final String COL_SUPP_PHONE = "supplier_phone";

        public static final String SUPP_UNKNOWN = "NA";
        public static final int GENRE_UNKNOWN = 0;
        public static final int GENRE_NONFICTION = 1;
        public static final int GENRE_FICTION = 2;

    }
}
