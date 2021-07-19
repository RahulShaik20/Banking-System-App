package com.example.basicbankingapp.ListAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.Data.User;
import com.example.basicbankingapp.R;
import com.example.basicbankingapp.UserData;
import com.example.basicbankingapp.Userlist;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.myviewholder> {
    ArrayList<User> dataholder;

    public UserAdapter(Userlist userlist, ArrayList<User> dataholder) {
        this.dataholder = dataholder;
    }




    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.itemView.setTag(dataholder.get(position));
        holder.UserName.setText(dataholder.get(position).getName());
        holder.UserBalance.setText(String.format("%d", dataholder.get(position).getBalance()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), UserData.class);
                intent.putExtra("ACCOUNT_NO",dataholder.get(position).getAccountNumber());
                intent.putExtra("NAME", dataholder.get(position).getName());
                intent.putExtra("EMAIL", dataholder.get(position).getEmail());
                intent.putExtra("IFSC_CODE", dataholder.get(position).getIfscCode());
                intent.putExtra("PHONE_NO", dataholder.get(position).getPhoneNumber());
                intent.putExtra("BALANCE", String.valueOf(dataholder.get(position).getBalance()));
                v.getContext().startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);
        return new myviewholder(view);
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView UserName,UserBalance;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            UserName=itemView.findViewById(R.id.username);
            UserBalance=itemView.findViewById(R.id.amount);
        }
    }
}
