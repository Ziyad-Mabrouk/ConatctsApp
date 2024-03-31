package com.example.conatcts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ContactViewHolder> {


    List<Contact> contactsList;
    Context context;

    public RecyclerViewAdapter(List<Contact> contactsList, Context context) {
        this.contactsList = contactsList;
        this.context = context;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber, tvEmail;
        ConstraintLayout parentLayout;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            parentLayout = itemView.findViewById(R.id.contactViewLayout);
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view, parent, false);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvName.setText(contactsList.get(position).getName());
        holder.tvNumber.setText(String.valueOf(contactsList.get(position).getPhoneNumber()));
        holder.tvEmail.setText(contactsList.get(position).getEmailAdress());
        holder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("id", contactsList.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}
