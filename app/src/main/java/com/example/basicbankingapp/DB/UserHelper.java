package com.example.basicbankingapp.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.renderscript.Sampler;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.os.Build.ID;

public class UserHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="User.db";
    public final static String TABLE_NAME = "user";

    //Table Fields

    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_USER_NAME ="name";
    public final static String COLUMN_USER_ACCOUNT_NUMBER ="accountNo";
    public final static String COLUMN_USER_EMAIL ="email";
    public final static String COLUMN_USER_IFSC_CODE ="ifscCode";
    public final static String COLUMN_USER_PHONE_NO ="phoneNo";
    public final static String COLUMN_USER_ACCOUNT_BALANCE ="balance";
    public UserHelper( Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_USER_ACCOUNT_NUMBER + " INTEGER, "
                + COLUMN_USER_NAME + " VARCHAR, "
                + COLUMN_USER_EMAIL + " VARCHAR, "
                + COLUMN_USER_IFSC_CODE + " VARCHAR, "
                + COLUMN_USER_PHONE_NO + " VARCHAR, "
                + COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);";

        // it Execute what ever queries we pass in side this method as an argument  the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL("insert into "+TABLE_NAME+ " values(6974,'Rahul shaik', 'rahulshaik@gmail.com','8512','858-684-0086', 1500)");
        db.execSQL("insert into "+TABLE_NAME+ " values(6936,'segu Hema', 'seguHema@gmail.com','8012','617-947-7155 25896', 2000)");
        db.execSQL("insert into "+TABLE_NAME+ " values(4981,'pasam Puneeth', 'pasampuneeeth@gmail.com','2045','830-420-1189', 3000)");
        db.execSQL("insert into "+TABLE_NAME+ " values(8537,'praveen chandu', 'chadu@gmail.com','7563','636-233-3832', 1500)");
        db.execSQL("insert into "+TABLE_NAME+ " values(5005,'hari uttej', 'rahulshaik@gmail.com','6465','989-672-5456', 2500)");
        db.execSQL("insert into "+TABLE_NAME+ " values(5345,'sufiyan', 'sufiyan@gmail.com','5378','908-582-0957', 4000)");
        db.execSQL("insert into "+TABLE_NAME+ " values(5678,'karim shaik', 'karimshaik@gmail.com','8643','936-600-3802', 5000)");
        db.execSQL("insert into "+TABLE_NAME+ " values(5097,'staya ram', 'staryaram@gmail.com','8325','760-359-5530', 6000)");
        db.execSQL("insert into "+TABLE_NAME+ " values(5542,'pasam purandar', 'purandarrahulshaik@gmail.com','8912','609-401-6773', 2000)");
        db.execSQL("insert into "+TABLE_NAME+ " values(5741,'Arun', 'Arun@gmail.com','8209','910-447-4903', 7500)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);
        }

    }
    //This interface provides random read-write access to the result set returned
    public Cursor readAlldata() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ TABLE_NAME,null);
        return  res;
        }

    public void updateAmount(int accountNo, int amount) {
        Log.d ("TAG", "update Amount");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + TABLE_NAME + " set " + COLUMN_USER_ACCOUNT_BALANCE + " = " + amount + " where " +
                COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo);
    }
}
