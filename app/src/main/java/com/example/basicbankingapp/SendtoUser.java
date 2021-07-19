package com.example.basicbankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import com.example.basicbankingapp.DB.TransactionHelper;
import com.example.basicbankingapp.DB.UserHelper;
import com.example.basicbankingapp.Data.User;
import com.example.basicbankingapp.ListAdapter.SendtoAdapter;

import java.util.ArrayList;

import static android.app.DownloadManager.COLUMN_STATUS;
import static com.example.basicbankingapp.DB.TransactionHelper.COLUMN_AMOUNT;
import static com.example.basicbankingapp.DB.TransactionHelper.COLUMN_FROM_NAME;
import static com.example.basicbankingapp.DB.TransactionHelper.COLUMN_TO_NAME;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_ACCOUNT_BALANCE;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_ACCOUNT_NUMBER;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_EMAIL;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_IFSC_CODE;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_NAME;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_PHONE_NO;
import static com.example.basicbankingapp.DB.UserHelper.TABLE_NAME;

public class SendtoUser extends AppCompatActivity implements SendtoAdapter.OnUserListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> dataholder;
    int fromUserAccountNo, toUserAccountNo, toUserAccountBalance;
    // Database
    private UserHelper dbHelper;
    String fromUserAccountName, fromUserAccountBalance, transferAmount, toUserAccountName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendto_user);
        //Get Intent
        Bundle bundle= getIntent().getExtras();
        if (bundle!=null){
            fromUserAccountName = bundle.getString("FROM_USER_NAME");
            fromUserAccountNo = bundle.getInt("FROM_USER_ACCOUNT_NO");
            fromUserAccountBalance = bundle.getString("FROM_USER_ACCOUNT_BALANCE");
            transferAmount = bundle.getString("TRANSFER_AMOUNT");
        }
        // Create ArrayList of Users
        dataholder = new ArrayList<User>();

        // Create Table in the Database
         dbHelper = new UserHelper(this);

        // Show list of items
        recyclerView = findViewById(R.id.send_to_user_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new SendtoAdapter(dataholder,this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        displayDatabaseInfo();
        super.onStart();
    }

    @Override
    public void onUserClick(int position) {
        // Insert data into transactions table
        toUserAccountNo = dataholder.get(position).getAccountNumber();
        toUserAccountName = dataholder.get(position).getName();
        toUserAccountBalance = dataholder.get(position).getBalance();

        calculateAmount();

        new TransactionHelper(this).insertTransferData(fromUserAccountName, toUserAccountName, transferAmount, 1);
        Toast.makeText(this, "Transaction Successful!!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(SendtoUser.this, Homescreen.class));
        finish();

    }


    private void calculateAmount() {
        Integer currentAmount = Integer.parseInt(fromUserAccountBalance);
        Integer transferAmountInt = Integer.parseInt(transferAmount);
        Integer remainingAmount = currentAmount - transferAmountInt;
        Integer increasedAmount = transferAmountInt + toUserAccountBalance;

        // Update amount in the dataBase
        new UserHelper(this).updateAmount(fromUserAccountNo, remainingAmount);
        new UserHelper(this).updateAmount(toUserAccountNo, increasedAmount);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder_exitButton = new AlertDialog.Builder(SendtoUser.this);
        builder_exitButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton ("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        // Transactions Cancelled
                        TransactionHelper dbHelper = new TransactionHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.put(COLUMN_FROM_NAME, fromUserAccountName);
                        values.put(COLUMN_TO_NAME, toUserAccountName);
                        values.put(COLUMN_STATUS,0 );
                        values.put(COLUMN_AMOUNT, transferAmount);

                        db.insert(TABLE_NAME, null, values);

                        Toast.makeText(SendtoUser.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SendtoUser.this, Userlist.class));
                        finish();
                    }
                }).setNegativeButton("No", null);
        AlertDialog alertExit = builder_exitButton.create();
        alertExit.show();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_USER_NAME,
                COLUMN_USER_ACCOUNT_BALANCE,
                COLUMN_USER_ACCOUNT_NUMBER,
                COLUMN_USER_PHONE_NO,
                COLUMN_USER_EMAIL,
                COLUMN_USER_IFSC_CODE,
        };

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,                          // The columns to return
                null,                        // The columns for the WHERE clause
                null,                     // The values for the WHERE clause
                null,                        // Don't group the rows
                null,                         // Don't filter by row groups
                null);                       // The sort order

        try {
            // Figure out the index of each column
            int phoneNoColumnIndex = cursor.getColumnIndex(COLUMN_USER_PHONE_NO);
            int emailColumnIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL);
            int ifscCodeColumnIndex = cursor.getColumnIndex(COLUMN_USER_IFSC_CODE);
            int accountNumberColumnIndex = cursor.getColumnIndex(COLUMN_USER_ACCOUNT_NUMBER);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_USER_NAME);
            int accountBalanceColumnIndex = cursor.getColumnIndex(COLUMN_USER_ACCOUNT_BALANCE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                String currentName = cursor.getString(nameColumnIndex);
                int accountNumber = cursor.getInt(accountNumberColumnIndex);
                String email = cursor.getString(emailColumnIndex);
                String phoneNumber = cursor.getString(phoneNoColumnIndex);
                String ifscCode = cursor.getString(ifscCodeColumnIndex);
                int accountBalance = cursor.getInt(accountBalanceColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                dataholder.add(new User(currentName, accountNumber, phoneNumber, ifscCode, accountBalance, email));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}