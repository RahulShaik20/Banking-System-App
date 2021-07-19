package com.example.basicbankingapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class TransactionHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "transaction.db";
    public final static String TABLE_NAME = "Transaction_table";

    /**Table Fields*/
    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_FROM_NAME = "from_name";
    public final static String COLUMN_TO_NAME = "to_name";
    public final static String COLUMN_AMOUNT = "amount";
    public final static String COLUMN_STATUS = "status";
    public TransactionHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_TRANSACTION_TABLE =  "CREATE TABLE " +TABLE_NAME + " ("
                +COLUMN_FROM_NAME + " VARCHAR, "
                +COLUMN_TO_NAME + " VARCHAR, "
                +COLUMN_AMOUNT + " INTEGER, "
                +COLUMN_STATUS + " INTEGER);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);
        }
    }
    public boolean insertTransferData (String fromName, String toName, String amount, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FROM_NAME, fromName);
        contentValues.put(COLUMN_TO_NAME, toName);
        contentValues.put(COLUMN_AMOUNT, amount);
        contentValues.put(COLUMN_STATUS, status);
        Long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}
