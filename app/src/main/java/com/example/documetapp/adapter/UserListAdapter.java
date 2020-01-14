package com.example.documetapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.documetapp.R;
import com.example.documetapp.model.UserList;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private static String TAG = UserListAdapter.class.getName();
    public Context context;
    private List<UserList> modelArrayList;
    private ViewGroup viewGroup;
    private static ArrayList<View> mPrintView = new ArrayList<>();

    public UserListAdapter(Context context, List<UserList> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        mPrintView.add(holder.itemView);

        holder.tv_name.setText(modelArrayList.get(position).name);
        holder.tv_email.setText(modelArrayList.get(position).email);
        holder.tv_phone.setText(modelArrayList.get(position).mobile);
        holder.tv_detail.setText(modelArrayList.get(position).detail);
        holder.tv_address.setText(modelArrayList.get(position).address);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public ArrayList<View> getPrintView() {
        return mPrintView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name, tv_email, tv_phone, tv_detail, tv_address;

        public ViewHolder(View itemView) {
            super(itemView);
            viewGroup = itemView.findViewById(android.R.id.content);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }
}