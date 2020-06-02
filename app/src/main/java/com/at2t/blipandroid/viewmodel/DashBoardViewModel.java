package com.at2t.blipandroid.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.data.repositories.PostDataRepository;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.utils.BlipUtility;

import java.util.List;

public class DashBoardViewModel extends ViewModel {

    private LiveData<List<PostsData>> postsLiveData;
    public Integer sectionId;
    private Application application;
    private PostDataRepository postDataRepository;
    private NetworkManager networkManager;
    private RetrofitManager retrofitManager;
    private ApiInterface apiInterface;

    public DashBoardViewModel() {
        networkManager = NetworkManager.getInstance();
        apiInterface = RetrofitManager.getInstance().getApiInterface();
    }

    public void init(Application application) {
        postDataRepository = new PostDataRepository(application);
        postsLiveData = postDataRepository.getLiveData();
        getPostList(BlipUtility.getSectionId(application));
    }

    public void getPostList(Integer sectionId){
        postDataRepository.getListofPost(sectionId);
    }

    public LiveData<List<PostsData>> getPostLiveDataObservables() {
        return postsLiveData;
    }

}
