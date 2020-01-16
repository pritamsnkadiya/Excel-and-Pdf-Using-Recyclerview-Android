package com.example.documetapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.documetapp.R;
import com.example.documetapp.model.BrandList;

import java.util.ArrayList;

import static com.example.documetapp.init.ApplicationAppContext.getAppContext;

public class MyAdapterBrand extends PagerAdapter {

    private ArrayList<BrandList> itemsList;
    private LayoutInflater inflater;
    public Context context;

    public MyAdapterBrand(Context context, ArrayList<BrandList> images) {
        this.context = context;
        this.itemsList = images;
        inflater = LayoutInflater.from(getAppContext());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        try {
            return itemsList.size();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        LayoutInflater li = LayoutInflater.from(context);
        View myImageLayout = li.inflate(R.layout.slide_brands, view, false);
        ImageView myImage = myImageLayout
                .findViewById(R.id.image);

        Glide.with(context).load("http://primezoneinfotech.com/aryaoptic/assests/pzi_public/brand/" +
                itemsList.get(position).image_name)
                .fitCenter()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myImage);

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}