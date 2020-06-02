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
    public static final int ADDING_DATA_FAILED = 2;
    public static final int MULTIPART_FILE_UPLOAD_SUCCESS = 3;
    public static final int MULTIPART_FILE_UPLOAD_FAILED = 4;

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

    public void addPostApiCall(String message, int postId, String title, int relTenantInstitutionId, int sectionId) {
        final AddPostData addPostData = new AddPostData(message, postId, title, relTenantInstitutionId, sectionId);
        Call<ResponseBody> responseBodyCall = apiInterface.addPostData(addPostData);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    mutableLiveData.postValue(AddPostDataRepository.ADDING_DATA_SUCCESS);
                    mutableLiveData.setValue(AddPostDataRepository.ADDING_DATA_SUCCESS);
                    return;
                }

                if (response.code() == 200) {
                    mutableLiveData.setValue(ADDING_DATA_SUCCESS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (NetworkManager.getInstance().isNetworkAvailable(application)) {
                    mutableLiveData.setValue(ADDING_DATA_FAILED);
                    Log.d(TAG, "No internet connection");
                }
            }
        });

    }

    public void uploadAttachmentFile(String filePath){
            //Create a file object using file path
             Log.d(TAG, "uploadAttachmentFile: " + filePath);
            File file = new File(filePath);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",
                    file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

            Call<AddPostData> call= apiInterface.uploadAttachmentFile(filePart);
            call.enqueue(new Callback<AddPostData>() {
                @Override
                public void onResponse(@NotNull Call<AddPostData> call, @NotNull Response<AddPostData> response) {
                    if(response.isSuccessful()){
                        mutableLiveData.postValue(AddPostDataRepository.MULTIPART_FILE_UPLOAD_SUCCESS);
                        mutableLiveData.setValue(AddPostDataRepository.MULTIPART_FILE_UPLOAD_SUCCESS);
                        Log.d("onResponse: filePart1: ", response.toString());
//                        Log.d("onResponse: filePart2: ", response.body().toString());
                    } else {
                        Log.d("Failed: filePart1: ", response.toString());
                        mutableLiveData.setValue(AddPostDataRepository.MULTIPART_FILE_UPLOAD_FAILED);
                        mutableLiveData.postValue(AddPostDataRepository.MULTIPART_FILE_UPLOAD_FAILED);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddPostData> call, @NotNull Throwable t) {
                    mutableLiveData.setValue(AddPostDataRepository.MULTIPART_FILE_UPLOAD_FAILED);
                }
            });
    }

    public String getFileToByte(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = android.util.Base64.encodeToString(bt, Base64.DEFAULT);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }

    public LiveData<Integer> getLiveData() {
        return mutableLiveData;
    }

}
