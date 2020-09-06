package com.at2t.blipandroid.view.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public PostsAdapter(Context context, List<PostsData> postsDataList) {
        this.mContext = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.postsDataList = postsDataList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.post_card_item, viewGroup,false);
        return new PostsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int i) {
        PostsData postsData = postsDataList.get(i);

        long epochDate = postsData.getPostCreatedDate();
        String dateOfPost = convertEpochtoDateFormat(epochDate);

        postsViewHolder.txtView_title.setText(postsData.getTitle());
        postsViewHolder.txtView_description.setText(postsData.getMessage());
        postsViewHolder.imgView_icon.setImageResource(R.drawable.active_dot);
        postsViewHolder.txtView_name.setText(BlipUtility.getFirstName(mContext));
        postsViewHolder.tvDate.setText(dateOfPost);
        try {
            if (postsData.getPostAttachmentId() != null) {
                final String encodedString = postsData.getPostAttachmentId();
                final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
                final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                Glide.with(mContext).load(decodedBytes).into(postsViewHolder.postImg);


            }
        } catch (ArrayIndexOutOfBoundsException e){
            Log.e(TAG, "onBindViewHolder: ", e.getCause());
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

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            postImg = itemView.findViewById(R.id.iv_event_img);
            imgView_icon = itemView.findViewById(R.id.person_image);
            txtView_name = itemView.findViewById(R.id.tv_full_name);
            txtView_title = itemView.findViewById(R.id.tv_post_information);
            ivTime = itemView.findViewById(R.id.iv_time);
            tvDate = itemView.findViewById(R.id.tv_date);
            txtView_description = itemView.findViewById(R.id.tv_post_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Position of the selected post is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });

            //Long Press
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "Position of the selected post is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }
}


