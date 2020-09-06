package com.at2t.blipandroid.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.data.repositories.AddPostDataRepository;
import com.at2t.blipandroid.data.repositories.UserLoginRepository;
import com.at2t.blipandroid.model.AddPostData;
import com.at2t.blipandroid.model.PostsData;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.at2t.blipandroid.data.repositories.AddPostDataRepository.ADDING_DATA_SUCCESS;


public class AddPostViewModel extends AndroidViewModel {

    private static final String TAG = "AddPostViewModel";
    public static final int ADDED_DATA_SUCCESSFULLY = 1;
    public static final int ADDING_DATA_FAILED = 2;
    public static final int MULTIPART_FILE_UPLOAD_SUCCESS = 3;
    public static final int MULTIPART_FILE_UPLOAD_FAILED = 4;
    public static final int FAILED_UNKNOWN_REASON = 5;
    public static final int NO_INTERNET_CONNECTION = 6;

    private final ApiInterface apiInterface;
    private final NetworkManager networkManager;
    private Application application;
    private MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();

    public AddPostViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        networkManager = NetworkManager.getInstance();
        apiInterface = RetrofitManager.getInstance().getApiInterface();
    }

    public LiveData<Integer> getLiveData() {
        return mutableLiveData;
    }

    public void addPostApiCall(String message, int postId, String title, int relTenantInstitutionId, int sectionId) {
        final AddPostData addPostData = new AddPostData(message, postId, title, relTenantInstitutionId, sectionId);
        Call<ResponseBody> responseBodyCall = apiInterface.addPostData(addPostData);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    mutableLiveData.setValue(ADDED_DATA_SUCCESSFULLY);
                } else {
                    mutableLiveData.setValue(ADDED_DATA_SUCCESSFULLY);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    mutableLiveData.setValue(AddPostViewModel.FAILED_UNKNOWN_REASON);
                } else {
                    mutableLiveData.setValue(AddPostViewModel.NO_INTERNET_CONNECTION);
                }
            }
        });

    }
}
