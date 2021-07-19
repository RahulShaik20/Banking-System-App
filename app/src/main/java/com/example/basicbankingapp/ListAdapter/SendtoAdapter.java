package com.example.basicbankingapp.ListAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.Data.User;
import com.example.basicbankingapp.R;
import com.example.basicbankingapp.SendtoUser;

import java.util.ArrayList;

public class SendtoAdapter extends RecyclerView.Adapter<SendtoAdapter.viewHolder> {
    private ArrayList<User> dataholder;
    private OnUserListener onUserListener;

    public SendtoAdapter(ArrayList<User> dataholder, SendtoUser onUserListener) {
        this.dataholder = dataholder;
        this.onUserListener = (OnUserListener) onUserListener;
    }

    @NonNull
    @Override
    public SendtoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from (viewGroup.getContext()).inflate(R.layout.user_list_item, viewGroup, false);
        return new SendtoAdapter.viewHolder(view, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.itemView.setTag(dataholder.get(position));
        holder.userName.setText(dataholder.get(position).getName());
        holder.userAccountBalance.setText(String.format("%d", dataholder.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName, userAccountBalance;
        OnUserListener onUserListener;

        public viewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            userAccountBalance = itemView.findViewById(R.id.amount);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onUserListener.onUserClick(getAdapterPosition());
        }
    }

    public interface OnUserListener {
        void onUserClick(int position);

    }
}
