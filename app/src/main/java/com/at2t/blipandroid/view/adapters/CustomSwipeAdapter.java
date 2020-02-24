package com.at2t.blipandroid.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.EventSlides;

import java.util.List;

public class CustomSwipeAdapter extends PagerAdapter {

    private List<EventSlides> eventSlides;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int custom_position = 0;

    public CustomSwipeAdapter(List<EventSlides> slidesList,Context mContext) {
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
        if(custom_position > 3)
            custom_position = 0;
        EventSlides slides = eventSlides.get(custom_position);
        custom_position++;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.event_swipe_layout, container, false);

        ImageView imageView = itemView.findViewById(R.id.iv_swipe_images);
        imageView.setImageResource(slides.getEventImages());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
