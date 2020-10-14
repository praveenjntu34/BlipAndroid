package com.at2t.blipandroid.view.ui;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PostItemDetailsActivity extends AppCompatActivity {

    public static final String TAG = "PostItemDetailsActivity";

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_posts_item_detail);

        initializeUIView();
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("attachment_url_exists")
                && getIntent().hasExtra("full_name")
                && getIntent().hasExtra("post_title") && getIntent().hasExtra("post_message")
                && getIntent().hasExtra("post_timestamp")) {

            postImageExists = getIntent().getBooleanExtra("attachment_url_exists", false);
            posterNameStr = getIntent().getStringExtra("full_name");
            posterTitleStr = getIntent().getStringExtra("post_title");
            posterDescriptionStr = getIntent().getStringExtra("post_message");
            posterTimeStamp = getIntent().getLongExtra("post_timestamp", 0);
            long epochDate = posterTimeStamp;
            dateOfPost = convertEpochtoDateFormat(epochDate);

            tvPosterName.setText(posterNameStr);
            tvPosterTitle.setText(posterTitleStr);
            tvPosterMesssageDesc.setText(posterDescriptionStr);
            tvPostTime.setText(dateOfPost);

            try {
                if (postImageExists) {
                    ivPosterImg.setVisibility(View.VISIBLE);
                    posterAttachment = BlipUtility.getPostAttachmentImage(getApplicationContext());
                    final String encodedString = posterAttachment;
                    final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
                    final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                    Glide.with(getApplicationContext()).load(decodedBytes).into(ivPosterImg);
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

    private void initializeUIView() {
        tvPosterName = findViewById(R.id.tvUserName);
        tvPosterMessage = findViewById(R.id.tvMessage);
        tvPosterTitle = findViewById(R.id.tvTitle);
        tvPosterMesssageDesc = findViewById(R.id.tvMessageDesc);
        ivPosterImg = findViewById(R.id.ivAttachment);
        tvPostTime = findViewById(R.id.tvPostTimeStamp);
    }
}
