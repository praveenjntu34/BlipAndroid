package com.at2t.blipandroid.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.model.UserProfileData;

import java.util.List;

public class PostDataRepository {

    public static final String TAG = "PostDataRepository";
    public MutableLiveData<List<PostsData>> listMutableLiveData;
    private Context application;
    public static PostDataRepository postDataRepository;
    private ApiInterface apiService;
    private MutableLiveData<UserProfileData> profileDetailsMutableLiveData;
    private NetworkManager networkManager;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public PostDataRepository(@NonNull Context application) {
        this.application = application;
        listMutableLiveData = new MutableLiveData<>();

        networkManager = NetworkManager.getInstance();
        apiService = RetrofitManager.getInstance().getApiInterface();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());
        editor = sharedPreferences.edit();
    }

}
