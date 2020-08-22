package com.at2t.blipandroid.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;

public class ContactUsFragment extends BaseFragment implements View.OnClickListener{
    public static final String TAG = "ContactUsFragment";
    private Button whatsAppBtn;
    private Button submitQueryBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        whatsAppBtn = view.findViewById(R.id.whatsapp);
        submitQueryBtn = view.findViewById(R.id.submitQuery);

        whatsAppBtn.setOnClickListener(this);
        submitQueryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == whatsAppBtn.getId()) {
            //TODO chatting
        } else {
            //TODO call
        }
    }
}
