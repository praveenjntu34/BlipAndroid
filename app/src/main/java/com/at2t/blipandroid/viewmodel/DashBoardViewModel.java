package com.at2t.blipandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.at2t.blipandroid.model.PostsData;

import java.util.ArrayList;

public class DashBoardViewModel extends ViewModel {

    MutableLiveData<ArrayList<PostsData>> postsLiveData;
    ArrayList<PostsData> postsDataArrayList;

    public DashBoardViewModel() {
        postsLiveData = new MutableLiveData<>();
        init();
    }

    public MutableLiveData<ArrayList<PostsData>> getUserMutableLiveData() {
        return postsLiveData;
    }

    public void init() {
        populateList();
        postsLiveData.setValue(postsDataArrayList);
    }

    public void populateList() {
        PostsData postsData = new PostsData();
        postsData.setTitle("Darknight");
        postsData.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit.Proin nunc mi,\n" +
                "        rhoncus sit amet lacus sed, eleifend faucibus ligula. Ut ullamcorper vehicular quam,\n" +
                "        sed ultrices mauris ultricies a.Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        postsDataArrayList = new ArrayList<>();
        postsDataArrayList.add(postsData);
        postsDataArrayList.add(postsData);
        postsDataArrayList.add(postsData);
        postsDataArrayList.add(postsData);
        postsDataArrayList.add(postsData);
        postsDataArrayList.add(postsData);
    }
}
