package com.at2t.blipandroid.view.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.BannerDetailsDataModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CustomSwipeAdapter extends PagerAdapter {

    public static final String TAG = "CustomSwipeAdapter";

    private List<BannerDetailsDataModel> eventSlides;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int custom_pos = 0;

    public CustomSwipeAdapter(List<BannerDetailsDataModel> slidesList,Context mContext) {
        this.eventSlides = slidesList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(ConstraintLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int event_size = eventSlides.size() - 1;
        if(custom_pos > event_size)
            custom_pos = 0;

        BannerDetailsDataModel slides = eventSlides.get(custom_pos);
        custom_pos++;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.event_swipe_layout, container, false);

        ImageView imageView = itemView.findViewById(R.id.iv_swipe_images);

        try {
            if (slides.getBannerStream() != null) {
                imageView.setVisibility(View.VISIBLE);
                final String encodedString = slides.getBannerStream();
                final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
                final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                Glide.with(mContext).load(decodedBytes).into(imageView);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "onBindViewHolder: ", e.getCause());
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
