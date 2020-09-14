package com.at2t.blipandroid.view.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.utils.ItemSelectedListener;
import com.at2t.blipandroid.view.ui.fragments.PostItemDetailFragment;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private static final String TAG = "PostListAdapter";
    private Context mContext;
    private List<? extends PostsData> postsDataList;
    private LayoutInflater layoutInflater;
    private int auditCreatedid;
    private String firstName;
    private String lasttName;
    private PostsData postsData;

    public PostsAdapter(Context context, List<PostsData> postsDataList) {
        this.mContext = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.postsDataList = postsDataList;
    }

    @NonNull
    @Override
    public PostsAdapter.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_card_item, viewGroup, false);
        return new PostsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int i) {
         postsData = postsDataList.get(i);

        long epochDate = postsData.getPostCreatedDate();
        String dateOfPost = convertEpochtoDateFormat(epochDate);

        postsViewHolder.txtView_title.setText(postsData.getTitle());
        postsViewHolder.txtView_description.setText(postsData.getMessage());
        postsViewHolder.tvDate.setText(dateOfPost);

        setPostsCreatorName(postsData, postsViewHolder, i);

        if (i % 3 == 2) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.man);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.blue_card_header_gradient);
        } else if (i % 3 == 1) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.user_avatar);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.orange_card_header_gradient);
        } else {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.nutritionist);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.card_header_gradient);
        }

        try {
            if (postsData.getPostAttachmentId() != null) {
                postsViewHolder.postImg.setVisibility(View.VISIBLE);
                final String encodedString = postsData.getPostAttachmentId();
                final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
                final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                Glide.with(mContext).load(decodedBytes).into(postsViewHolder.postImg);
            } else {
                postsViewHolder.postImg.setVisibility(View.GONE);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "onBindViewHolder: ", e.getCause());
        }

        postsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJump(postsData, view);
            }
        });
    }

    private void fragmentJump(PostsData postsData, View view) {
        PostItemDetailFragment postItemDetailFragment = new PostItemDetailFragment();
        String firstName = postsData.getFirstname();
        String lastName = postsData.getLastName();
        String fullName;
        if(firstName == null && lastName == null) {
            fullName = postsData.getInstitutionName();
        } else {
            fullName = firstName + " " + lastName;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Constants.POSTS_USER_NAME, fullName);
        bundle.putString(Constants.POSTS_TITLE, postsData.getTitle());
        bundle.putString(Constants.POSTS_DESCRIPTION, postsData.getMessage());
        bundle.putString(Constants.POSTS_ATTACHMENT, postsData.getPostAttachmentId());

        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, postItemDetailFragment, PostItemDetailFragment.TAG).addToBackStack(null).commit();

    }

    private void setPostsCreatorName(PostsData postsData, PostsViewHolder postsViewHolder, int i) {
        auditCreatedid = postsData.getAuditCreatedBy();
        if (auditCreatedid != 0) {
            firstName = postsData.getFirstname();
            lasttName = postsData.getLastName();
            String fullName = firstName + " " + lasttName;
            postsViewHolder.txtView_name.setText(fullName);
        } else {
            postsViewHolder.txtView_name.setText(postsData.getInstitutionName());
        }
    }

    private String convertEpochtoDateFormat(long epochDate) {
        Date date = new Date(epochDate);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("IST/UTC"));
        String formatted = format.format(date);
        System.out.println(formatted);

        return formatted;
    }

    @Override
    public int getItemCount() {
        return postsDataList.size();
    }

    public void setdata(List<PostsData> postsData) {
        this.postsDataList = postsData;
        notifyDataSetChanged();
    }


    class PostsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_icon;
        TextView txtView_name;
        TextView txtView_title;
        TextView tvDate;
        ImageView ivTime;
        TextView txtView_description;
        ImageView postImg;
        ConstraintLayout cardTopHeader;
        CardView cardView;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            postImg = itemView.findViewById(R.id.iv_event_img);
            imgView_icon = itemView.findViewById(R.id.person_image);
            txtView_name = itemView.findViewById(R.id.tv_full_name);
            txtView_title = itemView.findViewById(R.id.tv_post_information);
            ivTime = itemView.findViewById(R.id.iv_time);
            tvDate = itemView.findViewById(R.id.tv_date);
            txtView_description = itemView.findViewById(R.id.tv_post_description);
            cardTopHeader = itemView.findViewById(R.id.rl_top_header);
            cardView = itemView.findViewById(R.id.posts_cardView);
        }

    }
}


