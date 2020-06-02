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

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.PostsData;
import com.bumptech.glide.Glide;

import java.util.List;

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

        postsViewHolder.txtView_title.setText(postsData.getTitle());
        postsViewHolder.txtView_description.setText(postsData.getMessage());
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
            txtView_description = itemView.findViewById(R.id.tv_post_description);
        }
    }
}


