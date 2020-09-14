package com.at2t.blipandroid.view.ui.fragments;

import android.os.Build;
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
import com.bumptech.glide.Glide;

import java.util.Objects;

public class PostItemDetailFragment extends BaseFragment {

    public static final String TAG = "PostItemDetailFragment";

    private TextView tvPosterName;
    private TextView tvPosterTitle;
    private TextView tvPosterMessage;
    private TextView tvPosterMesssageDesc;
    private ImageView ivPosterImg;

    String posterNameStr;
    String posterTitleStr;
    String posterDescriptionStr;
    String posterAttachment;
    int posterPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            posterNameStr = bundle.getString(Constants.POSTS_USER_NAME);
            posterTitleStr = bundle.getString(Constants.POSTS_TITLE);
            posterDescriptionStr = bundle.getString(Constants.POSTS_DESCRIPTION);
            posterAttachment = bundle.getString(Constants.POSTS_ATTACHMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_posts_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUIView(view);
    }

    private void initializeUIView(View view) {
        tvPosterName = view.findViewById(R.id.tvUserName);
        tvPosterMessage = view.findViewById(R.id.tvMessage);
        tvPosterTitle = view.findViewById(R.id.tvTitle);
        tvPosterMesssageDesc = view.findViewById(R.id.tvMessageDesc);
        ivPosterImg = view.findViewById(R.id.ivAttachment);

        tvPosterName.setText(posterNameStr);
        tvPosterTitle.setText(posterTitleStr);
        tvPosterMesssageDesc.setText(posterDescriptionStr);

        try {
            if (posterAttachment != null) {
                final String encodedString = posterAttachment;
                final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
                final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                Glide.with(Objects.requireNonNull(getActivity())).load(decodedBytes).into(ivPosterImg);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            Log.e(TAG, "postsDetail: ", e.getCause());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
