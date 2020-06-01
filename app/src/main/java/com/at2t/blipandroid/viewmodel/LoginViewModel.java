package com.at2t.blipandroid.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.view.View;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.data.repositories.UserLoginRepository;

public class LoginViewModel extends ViewModel {

    public String phoneNumber;
    private UserLoginRepository userLoginRepository;
    private LiveData<Integer> integerLiveDataResponse;
    private Application application;
    private NetworkManager networkManager;
    private RetrofitManager retrofitManager;
    private ApiInterface loginApiService;


    public LoginViewModel() {
        networkManager = NetworkManager.getInstance();
        loginApiService = RetrofitManager.getInstance().getApiInterface();
    }

    public void init(Application application) {
        userLoginRepository = new UserLoginRepository(application);
        integerLiveDataResponse = userLoginRepository.getLiveData();
    }

    public void onLoginClick(View view) {
        loginUserUsingMobileNumber(phoneNumber);
    }

    public void loginUserUsingMobileNumber(String phoneNumber) {
        userLoginRepository.loginUsingMobileNumber(phoneNumber);
    }

    public LiveData<Integer> getResponseLiveData() {
        return integerLiveDataResponse;
    }
}
