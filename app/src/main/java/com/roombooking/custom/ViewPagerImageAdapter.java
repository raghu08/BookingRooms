package com.roombooking.custom;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roombooking.R;
import com.roombooking.repository.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerImageAdapter extends PagerAdapter {
 
    private List<String> urls;
    private LayoutInflater inflater;
    private Context context;
 
    public ViewPagerImageAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls=urls;
        inflater = LayoutInflater.from(context);
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
 
    @Override
    public int getCount() {
        return urls.size();
    }
 
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.image_frame, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Picasso.with(context).load(RetrofitClient.API_BASE_URL+ urls.get(position)).into(myImage);

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}