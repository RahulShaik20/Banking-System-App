package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Homescreen extends AppCompatActivity {
    TextView tvBankTitle;
    Button btnAllUsers, btnAllTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreeen);
        btnAllUsers = findViewById(R.id.all_users);
        btnAllTransactions = findViewById(R.id.all_transactions);
        tvBankTitle = findViewById(R.id.bank_title);

    }

    public void showAllUsers(View view) {
        Intent intent=new Intent(Homescreen.this,Userlist.class);
        startActivity(intent);
    }

    public void showAlltransaction(View view) {
        Intent intent = new Intent(this, TransactionHistory.class);
        startActivity(intent);
    }
}