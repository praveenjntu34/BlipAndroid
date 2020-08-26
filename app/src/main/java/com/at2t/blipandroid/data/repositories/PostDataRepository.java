package com.at2t.blipandroid.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.model.UserProfileData;
import com.at2t.blipandroid.model.UserProfileDetails;
import com.at2t.blipandroid.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataRepository {

    public static final String TAG = "PostDataRepository";
    public MutableLiveData<List<PostsData>> listMutableLiveData;
    private Context application;
    public static PostDataRepository postDataRepository;
    private ApiInterface apiService;
    private MutableLiveData<UserProfileData> profileDetailsMutableLiveData;
    private NetworkManager networkManager;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public PostDataRepository(@NonNull Context application) {
        this.application = application;
        listMutableLiveData = new MutableLiveData<>();

        networkManager = NetworkManager.getInstance();
        apiService = RetrofitManager.getInstance().getApiInterface();
        sharedPreferences = application.getApplicationContext().getSharedPreferences("app-pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void getListofPost(Integer sectionId, String date) {
        Call<List<PostsData>> postsDataCall = apiService.getListOfPost(sectionId, date);
        postsDataCall.enqueue(new Callback<List<PostsData>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostsData>> call, @NotNull Response<List<PostsData>> response) {
                if(response.body() != null) {
                    listMutableLiveData.setValue(response.body());
                    Log.d("Post data : ", response.toString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<PostsData>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listMutableLiveData.setValue(null);
            }
        });

    }

    public void getUserDetails(Integer parentId) {
        Call<UserProfileData> userProfileDetailsCall = apiService.getUserProfileDetails(parentId);
        userProfileDetailsCall.enqueue(new Callback<UserProfileData>() {
            @Override
            public void onResponse(@NotNull Call<UserProfileData> call, @NotNull Response<UserProfileData> response) {
                if (response.body() != null) {
                    profileDetailsMutableLiveData.setValue(response.body());
                    editor.putString(Constants.ACCESS_TOKEN, "1234");
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putBoolean(Constants.PARENTLOGIN, true);
                } else {
                    profileDetailsMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserProfileData> call, @NotNull Throwable t) {
                profileDetailsMutableLiveData.setValue(null);
                profileDetailsMutableLiveData.postValue(null);
            }
        });
    }

    public void updateUserProfileDetails(String admissionId, Integer childId, String childrenName, String email, String firstName, String lastName, Integer parentId, Integer personId, String phoneNumber, Integer relTenantInstitutionId, String secondaryParentName, String secondaryPhoneNumber, Integer sectionId) {
        UserProfileDetails userProfileDetails = new UserProfileDetails(admissionId, childId, childrenName,
                email, firstName, lastName, parentId, personId, phoneNumber, relTenantInstitutionId,
                secondaryParentName, secondaryPhoneNumber, sectionId);

        Call<ResponseBody> userProfileDetailsCall = apiService.updateUserProfile(userProfileDetails);
        userProfileDetailsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
//                    profileDetailsMutableLiveData.setValue(r);
                    editor.putString(Constants.ACCESS_TOKEN, "1234");
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putBoolean(Constants.PARENTLOGIN, true);
                } else {
                    profileDetailsMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                profileDetailsMutableLiveData.setValue(null);
                profileDetailsMutableLiveData.postValue(null);
            }
        });
    }

    public LiveData<UserProfileData> getUserProfileData() {
        return profileDetailsMutableLiveData;
    }

    public LiveData<List<PostsData>> getLiveData() {
        return listMutableLiveData;
    }

}
