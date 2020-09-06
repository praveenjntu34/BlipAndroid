package com.at2t.blipandroid.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.data.repositories.PostDataRepository;
import com.at2t.blipandroid.data.repositories.UserLoginRepository;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.utils.BlipUtility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardViewModel extends AndroidViewModel {

    private static final String TAG = "DashBoardViewModel";
    private final ApiInterface apiInterface;
    public MutableLiveData<List<PostsData>> listMutableLiveData = new MutableLiveData<>();

    public DashBoardViewModel(@NonNull Application application) {
        super(application);
        apiInterface = RetrofitManager.getInstance().getApiInterface();
    }

    public LiveData<List<PostsData>> getLiveData() {
        return listMutableLiveData;
    }

    public void getPostData(int sectionId, String date) {
        Call<List<PostsData>> getListDataCall = apiInterface.getListOfPost(sectionId, date);
        getListDataCall.enqueue(new Callback<List<PostsData>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostsData>> call, @NotNull Response<List<PostsData>> response) {
                response.toString();
                call.request().url();
                if (!response.isSuccessful()) {
                    listMutableLiveData.setValue(null);
                    return;
                }

                List<PostsData> postDataResponse = response.body();
                if (postDataResponse != null && postDataResponse.size() > 0) {
                    listMutableLiveData.setValue(postDataResponse);
                } else {
                    listMutableLiveData.setValue(null);
                }
            }


            @Override
            public void onFailure(@NotNull Call<List<PostsData>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listMutableLiveData.setValue(null);
            }
        });
    }

    public PostsData getPostDetailData(int position) {
        if (listMutableLiveData.getValue() != null) {
            return listMutableLiveData.getValue().get(position);
        }
        return null;
    }
}
