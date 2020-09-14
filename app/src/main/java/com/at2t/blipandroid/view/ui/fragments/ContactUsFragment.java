package com.at2t.blipandroid.view.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;

public class ContactUsFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "ContactUsFragment";
    private Button whatsAppBtn;
    private TextView contactEmail;
    private TextView contactAddress;
    private TextView tvSendEmail;

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
        contactEmail = view.findViewById(R.id.tv_email);
        tvSendEmail = view.findViewById(R.id.tv_send_email);
        whatsAppBtn.setOnClickListener(this);
    }

    public void connectWithWhatsApp() {
        boolean installed = checkWhatsAppInstalledOrNot("com.whatsapp");
        if (installed) {
            String phone = "+91 8125125895";
            String message = "Hi there, how may I help you?";
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setPackage("com.whatsapp");
            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + message;
            sendIntent.setData(Uri.parse(url));
            if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        } else {
            Toast.makeText(getContext(), "WhatsApp is not installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWhatsAppInstalledOrNot(String url) {
        PackageManager packageManager = getActivity().getPackageManager();
        boolean appInstalled;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == whatsAppBtn.getId()) {
            connectWithWhatsApp();
        }
    }
}
