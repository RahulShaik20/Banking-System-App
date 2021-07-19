package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;

import com.example.basicbankingapp.DB.UserHelper;
import com.example.basicbankingapp.Data.User;
import com.example.basicbankingapp.ListAdapter.UserAdapter;

import java.util.ArrayList;

import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_ACCOUNT_BALANCE;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_ACCOUNT_NUMBER;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_EMAIL;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_IFSC_CODE;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_NAME;
import static com.example.basicbankingapp.DB.UserHelper.COLUMN_USER_PHONE_NO;

public class Userlist extends AppCompatActivity {
    UserHelper userHelper;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;

    ArrayList<User> dataholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        userHelper=new UserHelper(this);

        // Create ArrayList of Users
        dataholder=new ArrayList<User>();
        // Create Table in the Database
        userHelper=new UserHelper(this);
        // Read Data from DataBase
        displaydatabase();
        // Show list of items
        recyclerView=(RecyclerView)findViewById(R.id.all_users_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new UserAdapter(this, dataholder);
        recyclerView.setAdapter(myAdapter);


    }
    public void displaydatabase(){
        dataholder.clear();
        Cursor cursor=new UserHelper(this).readAlldata();
        int phoneNoind = cursor.getColumnIndex(COLUMN_USER_PHONE_NO);
        int emailind = cursor.getColumnIndex(COLUMN_USER_EMAIL);
        int ifscCodeind = cursor.getColumnIndex(COLUMN_USER_IFSC_CODE);
        int accountNumind = cursor.getColumnIndex(COLUMN_USER_ACCOUNT_NUMBER);
        int nameind= cursor.getColumnIndex(COLUMN_USER_NAME);
        int accountBalanceind= cursor.getColumnIndex(COLUMN_USER_ACCOUNT_BALANCE);


        while (cursor.moveToNext()){
            String currentName = cursor.getString(nameind);
            int accountNumber = cursor.getInt(accountNumind);
            String email = cursor.getString(emailind);
            String phoneNumber = cursor.getString(phoneNoind);
            String ifscCode = cursor.getString(ifscCodeind);
            int accountBalance = cursor.getInt(accountBalanceind);
            // Display the values from each column of the current row in the cursor in the TextView
            dataholder.add(new User(currentName, accountNumber, phoneNumber, ifscCode, accountBalance, email));
        }


    }


}