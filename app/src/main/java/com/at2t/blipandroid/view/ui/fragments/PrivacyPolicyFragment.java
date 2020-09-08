package com.at2t.blipandroid.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;

public class PrivacyPolicyFragment extends BaseFragment {

    public static final String TAG = "PrivacyPolicyFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_privacy_policy, container, false);
    }
}
