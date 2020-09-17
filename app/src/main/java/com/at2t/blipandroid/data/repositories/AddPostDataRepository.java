package com.at2t.blipandroid.data.repositories;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.AddPostData;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostDataRepository {
    public static final String TAG = "AddPostDataRepository";

    public static final int ADDING_DATA_SUCCESS = 1;

    public MutableLiveData<Integer> mutableLiveData;
    private ApiInterface apiInterface;
    private Context application;
    private NetworkManager networkManager;
    public static AddPostDataRepository addPostDataRepository;

    public AddPostDataRepository(@NonNull Application application) {
        this.application = application;
        mutableLiveData = new MutableLiveData<>();

        networkManager = NetworkManager.getInstance();
        apiInterface = RetrofitManager.getInstance().getApiInterface();
    }

}
