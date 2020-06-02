package com.at2t.blipandroid.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.PostsData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataRepository {

    public static final String TAG = "PostDataRepository";
    public MutableLiveData<List<PostsData>> listMutableLiveData;
    private ApiInterface apiInterface;
    private Application application;
    public static PostDataRepository postDataRepository;

    public PostDataRepository(@NonNull Application application) {
        this.application = application;
        listMutableLiveData = new MutableLiveData<>();

        apiInterface = RetrofitManager.getInstance().getApiInterface();
    }

    public void getListofPost(Integer sectionId) {
        Call<List<PostsData>> postsDataCall = apiInterface.getListOfPost(sectionId);
        postsDataCall.enqueue(new Callback<List<PostsData>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostsData>> call, @NotNull Response<List<PostsData>> response) {
                if(response.body() != null) {
                    listMutableLiveData.setValue(response.body());
                    Log.d("Post data : ", response.toString());
                    Log.d("Post data : ", response.body().get(0).getTitle());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<PostsData>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listMutableLiveData.setValue(null);
            }
        });

    }

    public LiveData<List<PostsData>> getLiveData() {
        return listMutableLiveData;
    }

}
