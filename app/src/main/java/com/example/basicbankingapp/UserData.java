package com.example.basicbankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserData extends AppCompatActivity {
    TextView name, email, accountNo, balance, ifscCode, phoneNo;
    Button transferMoney;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email_id);
        accountNo = findViewById(R.id.account_no);
        balance = findViewById(R.id.avail_balance);
        ifscCode = findViewById(R.id.ifsc_id);
        phoneNo = findViewById(R.id.phone_no);
        transferMoney = findViewById(R.id.transfer_money);

        Intent intent=getIntent();//it retrive the data
        Bundle extra=intent.getExtras();// fetches data which was added

        if(extra!=null) {
            name.setText(extra.getString("NAME"));
            accountNo.setText(String.valueOf(extra.getInt("ACCOUNT_NO")));
            email.setText(extra.getString("EMAIL"));
            phoneNo.setText(extra.getString("PHONE_NO"));
            ifscCode.setText(extra.getString("IFSC_CODE"));
            balance.setText(extra.getString("BALANCE"));

        }else{
            Log.d("TAG","Empty Intent");
        }
        transferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAmount();

            }
        });

    }

    private void enterAmount() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(UserData.this);
        View view=getLayoutInflater().inflate(R.layout.dialogbox,null);
        builder.setTitle("Enter Amount").setView(view).setCancelable(false);

        final EditText mAmount = (EditText) view.findViewById(R.id.enter_money);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                transactionCancel();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking whether amount entered is correct or not
                int currentBalance = Integer.parseInt(String.valueOf(balance.getText()));

                if (mAmount.getText().toString().isEmpty()) {
                    mAmount.setError("Amount cannot be empty");
                } else if (Integer.parseInt(mAmount.getText().toString()) > currentBalance){
                    mAmount.setError("Your account don't have enough balance");
                } else {
                    Intent intent = new Intent(UserData.this, SendtoUser.class);
                    intent.putExtra("FROM_USER_ACCOUNT_NO", Integer.parseInt(accountNo.getText().toString()));    // PRIMARY_KEY
                    intent.putExtra("FROM_USER_NAME", name.getText());
                    intent.putExtra("FROM_USER_ACCOUNT_BALANCE", balance.getText());
                    intent.putExtra("TRANSFER_AMOUNT", mAmount.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void transactionCancel() {
        AlertDialog.Builder builder_exit=new AlertDialog.Builder(UserData.this);
        builder_exit.setTitle("Do you want to cancel the transaction?").setCancelable(false);
        builder_exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UserData.this,"transaction cancelled",Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterAmount();
            }
        });
        AlertDialog alertexit = builder_exit.create();
        alertexit.show();
    }
}