package com.at2t.blipandroid.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.data.repositories.AddPostDataRepository;


public class AddPostViewModel extends ViewModel {
    private LiveData<Integer> addPostsLiveData;
    private Application application;
    private AddPostDataRepository addPostDataRepository;
    private NetworkManager networkManager;
    private RetrofitManager retrofitManager;
    private ApiInterface apiInterface;
    public String filePath;

    public AddPostViewModel() {
        networkManager = NetworkManager.getInstance();
        apiInterface = RetrofitManager.getInstance().getApiInterface();

    }

    public void init(Application application) {
        addPostDataRepository = new AddPostDataRepository(application);
        addPostsLiveData = addPostDataRepository.getLiveData();
        uploadPostAttachmentFile(filePath);
//        addPostApiCall();
    }

////    public void addPostApiCall(){
////        addPostDataRepository.addPostApiCall();
////    }

    public void uploadPostAttachmentFile(String filePath) {
        if (filePath != null) {
            addPostDataRepository.uploadAttachmentFile(filePath);
        }
    }

    public LiveData<Integer> getPostLiveDataObservables() {
        return addPostsLiveData;
    }
}
