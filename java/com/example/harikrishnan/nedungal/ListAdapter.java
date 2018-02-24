package com.example.harikrishnan.nedungal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    List<Bookie> list_data= Collections.emptyList();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_list, address_list, mobile_list;

        public MyViewHolder(View view) {
            super(view);
            name_list = (TextView) view.findViewById(R.id.name_list);
            address_list= (TextView) view.findViewById(R.id.address_list);
            mobile_list= (TextView) view.findViewById(R.id.mobile_list);
        }
    }


    public ListAdapter(  List<Bookie> vazhipadulist) {

        this.list_data=vazhipadulist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vazhipadulist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Bookie current=list_data.get(position);
        holder.name_list.setText(current.nameBooked);
        holder.address_list.setText(current.address);
        holder.mobile_list.setText(current.mobile);
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }
}
