package com.example.documetapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.documetapp.R;
import com.example.documetapp.model.AidsList;
import com.example.documetapp.model.BrandList;
import com.example.documetapp.model.UserList;
import com.example.documetapp.utils.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class ParantListAdapter extends RecyclerView.Adapter<ParantListAdapter.ViewHolder> {

    private static String TAG = ParantListAdapter.class.getName();
    public Context context;
    private List<AidsList> modelArrayList;
    private ViewGroup viewGroup;

    public ParantListAdapter(Context context, List<AidsList> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_parant_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tv_name.setText(modelArrayList.get(position).aids);

        //  SectionListBrandsDataAdapter itemListDataAdapter = new SectionListBrandsDataAdapter(context, modelArrayList.get(position).brand2);
        MyAdapterBrand itemDataAdapter = new MyAdapterBrand(context, (ArrayList<BrandList>) modelArrayList.get(position).brand1);

        holder.mPager.setAdapter(itemDataAdapter);
        holder.indicator.setViewPager(holder.mPager);

        holder.recycler_view_list.addItemDecoration(new ItemOffsetDecoration(10));
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        //  holder.recycler_view_list.setAdapter(itemListDataAdapter);
        holder.recycler_view_list.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public ImageView p_image;

        protected ImageView itemTitle;
        protected RecyclerView recycler_view_list;
        CircleIndicator indicator;
        public ViewPager mPager;
        public TextView next, prev;

        public ViewHolder(View itemView) {
            super(itemView);
            viewGroup = itemView.findViewById(android.R.id.content);

            tv_name = itemView.findViewById(R.id.tv_name);
            p_image = itemView.findViewById(R.id.p_image);

            itemTitle = itemView.findViewById(R.id.itemTitle);
            recycler_view_list = itemView.findViewById(R.id.recycler_view_list);
            indicator = itemView.findViewById(R.id.indicator);
            mPager = itemView.findViewById(R.id.pager);
            next = itemView.findViewById(R.id.next);
            prev = itemView.findViewById(R.id.prev);
        }
    }
}