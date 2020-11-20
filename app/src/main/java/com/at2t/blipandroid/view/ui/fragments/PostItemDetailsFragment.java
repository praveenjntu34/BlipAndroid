package com.at2t.blipandroid.view.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.viewmodel.LoginViewModel;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PostItemDetailsFragment extends BaseFragment {
    public static final String TAG = "PostItemDetailsFragment";

    private TextView tvPosterName;
    private TextView tvPosterTitle;
    private TextView tvPosterMessage;
    private TextView tvPosterMesssageDesc;
    private TextView tvPostTime;
    private ImageView ivPosterImg;

    String posterNameStr;
    String posterTitleStr;
    String posterDescriptionStr;
    String posterAttachment;
    long posterTimeStamp;
    boolean postImageExists;
    private String dateOfPost;
    private LoginViewModel loginViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts_item_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        getIncomingBundle();
    }

    private void getIncomingBundle() {
        if (getArguments() != null) {
            if(getArguments().get(Constants.POSTS_ATTACHMENT) != null) {
                postImageExists = true;
                posterAttachment = getArguments().getString(Constants.POSTS_ATTACHMENT);
            } else {
                postImageExists = false;
            }
            if(getArguments().getString(Constants.POSTS_USER_NAME) != null) {
                posterNameStr = getArguments().getString(Constants.POSTS_USER_NAME);
            } else {
                posterNameStr = getArguments().getString(Constants.POSTS_INSTITUTE);
            }
            posterTitleStr = getArguments().getString(Constants.POSTS_TITLE);
            posterDescriptionStr = getArguments().getString(Constants.POSTS_DESCRIPTION);
            posterTimeStamp = getArguments().getLong(Constants.POSTS_TIME);
            long epochDate = posterTimeStamp;
            dateOfPost = convertEpochtoDateFormat(epochDate);

            tvPosterName.setText(posterNameStr);
            tvPosterTitle.setText(posterTitleStr);
            tvPosterMesssageDesc.setText(posterDescriptionStr);
            tvPostTime.setText(dateOfPost);

            try {
                if (postImageExists) {
                    ivPosterImg.setVisibility(View.VISIBLE);

                    final String encodedString = posterAttachment;
                    final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
                    final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                    Glide.with(getContext()).load(decodedBytes).into(ivPosterImg);
                } else {
                    ivPosterImg.setVisibility(View.GONE);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "postsDetail: ", e.getCause());
            }
        }

    }

    private String convertEpochtoDateFormat(long epochDate) {
        Date date = new Date(epochDate);
        date.setHours(date.getHours() + 5);
        date.setMinutes(date.getMinutes() + 30);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        format.setTimeZone(TimeZone.getTimeZone("IST/UTC"));
        String formatted = format.format(date);
        System.out.println(formatted);

        return formatted;
    }

    private void initializeViews(View view) {
        tvPosterName = view.findViewById(R.id.tvUserName);
        tvPosterMessage = view.findViewById(R.id.tvMessage);
        tvPosterTitle = view.findViewById(R.id.tvTitle);
        tvPosterMesssageDesc = view.findViewById(R.id.tvMessageDesc);
        ivPosterImg = view.findViewById(R.id.ivAttachment);
        tvPostTime = view.findViewById(R.id.tvPostTimeStamp);
    }

}

