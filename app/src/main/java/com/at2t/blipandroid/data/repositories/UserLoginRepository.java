package com.at2t.blipandroid.data.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.InstructorLoginData;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginRepository {

    public static final int LOGIN_SUCCESSFULL = 1;
    public static final int LOGIN_FAILED = 2;
    public static final int LOGIN_FAILED_DUE_TO_INVALID_CREDENTIALS = 3;

    private ApiInterface apiService;
    private MutableLiveData<Integer> responseLiveData;
    private NetworkManager networkManager;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private Application application;

    public UserLoginRepository(@NonNull Application application) {
        this.application = application;
        responseLiveData = new MutableLiveData<>();

        networkManager = NetworkManager.getInstance();
        apiService = RetrofitManager.getInstance().getApiInterface();
        sharedPreferences = application.getApplicationContext().getSharedPreferences("app-pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void logout() {
        editor.putBoolean(Constants.IS_LOGGED_IN, false);
        editor.putBoolean(Constants.PARENTLOGIN, false);
        editor.putBoolean(Constants.INSTRUCTORLOGIN, false);
        editor.putInt(Constants.SECTION_ID, 0);
        editor.putString(Constants.ACCESS_TOKEN, null);
        editor.apply();
    }

    public boolean isAlreadyLogged() {
        return sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false);
    }

    public boolean isParentLoggedIn() {
        return sharedPreferences.getBoolean(Constants.PARENTLOGIN, false);
    }

    public boolean isInstructorLogin() {
        return sharedPreferences.getBoolean(Constants.INSTRUCTORLOGIN, false);
    }

    public void loginUsingMobileNumber(String phoneNumber) {
        Call<InstructorLoginData> mobileNumber = apiService.loginWithPhoneNumber(phoneNumber);
        mobileNumber.enqueue(new Callback<InstructorLoginData>() {
            @Override
            public void onResponse(@NotNull Call<InstructorLoginData> call, @NotNull Response<InstructorLoginData> response) {
                if (response.body() != null) {
                    BlipUtility.storeUserBasicInfoInSharedPref(application, response.body());
                    responseLiveData.postValue(UserLoginRepository.LOGIN_SUCCESSFULL);
                    responseLiveData.setValue(UserLoginRepository.LOGIN_SUCCESSFULL);
                    editor.putString(Constants.ACCESS_TOKEN, "1234");
                    editor.putInt(Constants.SECTION_ID, response.body().getSectionId());
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putBoolean(Constants.INSTRUCTORLOGIN, true);
                } else {
                    responseLiveData.setValue(UserLoginRepository.LOGIN_FAILED_DUE_TO_INVALID_CREDENTIALS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<InstructorLoginData> call, @NotNull Throwable t) {
                responseLiveData.setValue(UserLoginRepository.LOGIN_FAILED);
                responseLiveData.postValue(UserLoginRepository.LOGIN_FAILED);
            }
        });
    }

    public LiveData<Integer> getLiveData() {
        return responseLiveData;
    }


}
