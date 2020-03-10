package com.at2t.blipandroid.view.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.PostsData;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private Context mContext;
    private List<PostsData> postsDataList;

    public PostsAdapter(Context mContext, List<PostsData> postsDataList) {
        this.mContext = mContext;
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
        PostsViewHolder viewHolder= (PostsViewHolder) postsViewHolder;

        viewHolder.txtView_name.setText(postsData.getName());
        viewHolder.txtView_description.setText(postsData.getDescription());
        viewHolder.txtView_time.setText(postsData.getTime());
    }

    @Override
    public int getItemCount() {
        return postsDataList.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_icon;
        TextView txtView_name;
        TextView txtView_description;
        TextView txtView_time;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView_icon = itemView.findViewById(R.id.person_image);
            txtView_name = itemView.findViewById(R.id.tv_full_name);
            txtView_description = itemView.findViewById(R.id.tv_post_information);
            txtView_time = itemView.findViewById(R.id.tv_time);
        }
    }
}


