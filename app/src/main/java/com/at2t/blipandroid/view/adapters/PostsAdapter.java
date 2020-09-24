package com.at2t.blipandroid.view.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.view.ui.PostItemDetailsActivity;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private static final String TAG = "PostListAdapter";
    public static final int MAX_LINES = 3;
    private Context mContext;
    private List<? extends PostsData> postsDataList;
    private LayoutInflater layoutInflater;
    private int auditCreatedid;
    private String firstName;
    private String lasttName;
    private PostsData postsData;
    private String dateOfPost;
    private byte[] decodedBytes;
    private boolean postImageExists = false;

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
    public void onBindViewHolder(@NonNull final PostsViewHolder postsViewHolder, final int i) {
        postsData = postsDataList.get(i);

        long epochDate = postsData.getPostCreatedDate();
        dateOfPost = convertEpochtoDateFormat(epochDate);

        postsViewHolder.txtView_title.setText(postsData.getTitle());
        postsViewHolder.txtView_description.setText(postsData.getMessage());
        postsViewHolder.txtView_description.post(new Runnable() {
            @Override
            public void run() {
                // Past the maximum number of lines we want to display.
                if (postsViewHolder.txtView_description.getLineCount() > MAX_LINES) {
                    int lastCharShown = postsViewHolder.txtView_description.getLayout().getLineVisibleEnd(MAX_LINES - 1);

                    postsViewHolder.txtView_description.setMaxLines(MAX_LINES);

                    String moreString = mContext.getString(R.string.view_more);
                    String suffix = "  " + moreString;

                    // 3 is a "magic number" but it's just basically the length of the ellipsis we're going to insert
                    String actionDisplayText = postsDataList.get(i).getMessage().substring(0, lastCharShown - suffix.length() - 3) + "..." + suffix;

                    SpannableString truncatedSpannableString = new SpannableString(actionDisplayText);
                    int startIndex = actionDisplayText.indexOf(moreString);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        truncatedSpannableString.setSpan(new ForegroundColorSpan(mContext.getColor(android.R.color.holo_blue_light)), startIndex, startIndex + moreString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    postsViewHolder.txtView_description.setText(truncatedSpannableString);
                }
            }
        });
        postsViewHolder.tvDate.setText(dateOfPost);

        setPostsCreatorName(postsData, postsViewHolder, i);

        addCardHeaderColor(postsViewHolder, i);

        try {
            if (postsData.getPostAttachmentId() != null) {
                postsViewHolder.postImg.setVisibility(View.VISIBLE);
                final String encodedString = postsData.getPostAttachmentId();
                final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
                decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                BlipUtility.setSharedPrefString(mContext, Constants.POSTS_ATTACHMENT, postsData.getPostAttachmentId());
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

                fragmentJump(postsDataList, i);
            }
        });
    }

    private void addCardHeaderColor(PostsViewHolder postsViewHolder, int i) {
        if (i % 8 == 7) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.man);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.blue_card_header_gradient);
        } else if (i % 8 == 6) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.user_avatar);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.mojito_card_header_gradient);
        } else if (i % 8 == 5) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.nutritionist);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.red_cherry_card_header_gradient);
        } else if (i % 8 == 4) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.man);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.orange_card_header_gradient);
        } else if (i % 8 == 3) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.user_avatar);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.lake_view_card_header_gradient);
        } else if (i % 8 == 2) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.nutritionist);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.dusk_card_header_gradient);
        } else if (i % 8 == 1) {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.man);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.dimigo_card_header_gradient);
        } else {
            postsViewHolder.imgView_icon.setImageResource(R.drawable.user_avatar);
            postsViewHolder.cardTopHeader.setBackgroundResource(R.drawable.red_card_header_gradient);
        }
    }

    private void fragmentJump(List<? extends PostsData> postsDataList, int i) {
        String firstName = postsDataList.get(i).getFirstname();
        String lastName = postsDataList.get(i).getLastName();
        String fullName;
        if (firstName == null && lastName == null) {
            fullName = postsDataList.get(i).getInstitutionName();
        } else {
            fullName = firstName + " " + lastName;
        }

        if (postsDataList.get(i).getPostAttachmentId() != null) {
            postImageExists = true;
        } else {
            postImageExists = false;
        }

        Intent intent = new Intent(mContext, PostItemDetailsActivity.class);
        intent.putExtra("full_name", fullName);
        intent.putExtra("attachment_url_exists", postImageExists);
        intent.putExtra("post_title", postsDataList.get(i).getTitle());
        intent.putExtra("post_message", postsDataList.get(i).getMessage());
        intent.putExtra("post_timestamp", postsDataList.get(i).getPostCreatedDate());
        mContext.startActivity(intent);

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
        date.setHours(date.getHours() + 5);
        date.setMinutes(date.getMinutes() + 30);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
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
        TextView txtView_view_more;
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
            txtView_view_more = itemView.findViewById(R.id.textViewMore);
        }

    }
}


