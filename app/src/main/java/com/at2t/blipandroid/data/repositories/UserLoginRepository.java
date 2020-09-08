package com.at2t.blipandroid.data.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.BannerDetailsDataModel;
import com.at2t.blipandroid.model.BranchSectionData;
import com.at2t.blipandroid.model.FcmTokenModel;
import com.at2t.blipandroid.model.InstructorLoginData;
import com.at2t.blipandroid.model.ParentDataModel;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.model.UserProfileData;
import com.at2t.blipandroid.model.UserProfileDetails;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.utils.Enums;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginRepository {

    public static final String TAG = "UserLoginRepository";
    private ApiInterface apiService;
    private MutableLiveData<Integer> responseLiveData;
    private List<BranchSectionData> branchListLiveData;
    private List<BannerDetailsDataModel> bannerDetailsDataModelList;
    private NetworkManager networkManager;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private Application application;

    public UserLoginRepository(@NonNull Application application) {
        this.application = application;
        responseLiveData = new MutableLiveData<>();

        networkManager = NetworkManager.getInstance();
        apiService = RetrofitManager.getInstance().getApiInterface();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());
        editor = sharedPreferences.edit();
    }

    public void loginUsingMobileNumber(String phoneNumber) {
        Call<InstructorLoginData> mobileNumber = apiService.loginWithPhoneNumber(phoneNumber);
        mobileNumber.enqueue(new Callback<InstructorLoginData>() {
            @Override
            public void onResponse(@NotNull Call<InstructorLoginData> call, @NotNull Response<InstructorLoginData> response) {
                if (response.body() != null) {
                    if (response.body().getInstructorUserId() != 0) {
                        BlipUtility.storeInstructorBasicInfoInSharedPref(application, response.body());
                        responseLiveData.setValue(Enums.LoginStatus.INSTRUCTOR_LOGIN_SUCCESSFULL);
                        BlipUtility.setSharedPrefInteger(application.getApplicationContext(), Constants.INSTRUCTOR_SECTION_ID, response.body().getSectionId());
                        BlipUtility.setSharedPrefInteger(application.getApplicationContext(), Constants.INSTRUCTOR_ID, response.body().getInstructorUserId());
                        BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.ROLE, response.body().getRole());
                        BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.EMAIL_ID, response.body().getEmail());
                        BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.USER_FIRST_NAME, response.body().getFirstName());
                        BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.USER_LAST_NAME, response.body().getLastName());
                        BlipUtility.setSharedPrefInteger(application.getApplicationContext(), Constants.INSTITUTE_ID, response.body().getRelTenantInstitutionId());
                        editor.putString(Constants.ACCESS_TOKEN, "1234");
                        editor.putInt(Constants.INSTRUCTOR_SECTION_ID, response.body().getSectionId());

                        editor.putBoolean(Constants.IS_LOGGED_IN, true);
                        editor.putBoolean(Constants.INSTRUCTORLOGIN, true);
                        editor.apply();
                    } else {
                        responseLiveData.setValue(Enums.LoginStatus.INSTRUCTOR_DOES_NOT_EXIST);
                    }
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.INSTRUCTOR_DOES_NOT_EXIST);
                }
            }

            @Override
            public void onFailure(@NotNull Call<InstructorLoginData> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.INSTRUCTOR_LOGIN_FAILED);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }

    public void parentLoginUsingPhone(String phoneNumber) {
        final Call<ParentDataModel> mobileNumber = apiService.userLoginUsingMobile(phoneNumber);
        mobileNumber.enqueue(new Callback<ParentDataModel>() {
            @Override
            public void onResponse(@NotNull Call<ParentDataModel> call, @NotNull Response<ParentDataModel> response) {
                if (response.body() != null) {
                    if (response.body().getParentLoginDataList() != null) {
                        BlipUtility.storeUserBasicInfoInSharedPref(application, response.body().getParentLoginDataList().get(0));

                        responseLiveData.setValue(Enums.LoginStatus.USER_LOGIN_SUCCESSFULL);
                        BlipUtility.setSharedPrefInteger(application.getApplicationContext(), Constants.PARENT_SECTION_ID, response.body().getParentLoginDataList().get(0).getSectionId());
                        BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.ROLE, response.body().getParentLoginDataList().get(0).getRole());
                        BlipUtility.setSharedPrefBoolean(application.getApplicationContext(), Constants.IS_PARENT_FIRST_LOGIN, response.body().getParentLoginDataList().get(0).isFirstLogin());
                        editor.putString(Constants.ACCESS_TOKEN, "1234");
                        editor.putInt(Constants.PARENT_SECTION_ID, response.body().getParentLoginDataList().get(0).getSectionId());
                        editor.putBoolean(Constants.IS_LOGGED_IN, true);
                        editor.putBoolean(Constants.PARENTLOGIN, true);
                    } else {
                        responseLiveData.setValue(Enums.LoginStatus.USER_DOES_NOT_EXIST);
                    }
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.USER_DOES_NOT_EXIST);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ParentDataModel> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.USER_LOGIN_FAILED);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }

    public void saveFcmTokenForInstructor(int instructorId, String fcmToken) {
        FcmTokenModel fcmTokenModel = new FcmTokenModel(instructorId, fcmToken);
        final Call<ResponseBody> fcmTokenForInstructor = apiService.postFcmTokenForInstructor(fcmTokenModel);
        fcmTokenForInstructor.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    responseLiveData.setValue(Enums.LoginStatus.FCM_TOKEN_SAVED_FOR_INSTRUCTOR);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.FCM_TOKEN_SAVING_FAILED);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.FCM_TOKEN_SAVING_FAILED);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }

    public void saveFcmTokenForParent(int parentId, String fcmToken) {
        FcmTokenModel fcmTokenModel = new FcmTokenModel(parentId, fcmToken);
        final Call<ResponseBody> fcmTokenForParent = apiService.postFcmTokenForParent(fcmTokenModel);
        fcmTokenForParent.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    responseLiveData.setValue(Enums.LoginStatus.FCM_TOKEN_SAVED_FOR_PARENT);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.FCM_TOKEN_SAVING_FAILED);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.FCM_TOKEN_SAVING_FAILED);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }

    public void updateUserProfileDetails(String admissionId, Integer childId, String childrenName, String email, String firstName, String lastName, Integer parentId, Integer personId, String phoneNumber, Integer relTenantInstitutionId, String secondaryParentName, String secondaryPhoneNumber, Integer sectionId,
                                         String institutionName, String gender, String dateOfBirth) {
        UserProfileDetails userProfileDetails = new UserProfileDetails(admissionId, childId, childrenName,
                email, firstName, lastName, parentId, personId, phoneNumber, relTenantInstitutionId,
                secondaryParentName, secondaryPhoneNumber, sectionId, institutionName, gender, dateOfBirth);

        Call<ResponseBody> userProfileDetailsCall = apiService.updateUserProfile(userProfileDetails);
        userProfileDetailsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    responseLiveData.setValue(Enums.LoginStatus.PROFILE_UPDATED_SUCCESSFULLY);
                    editor.putString(Constants.ACCESS_TOKEN, "1234");
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putBoolean(Constants.PARENTLOGIN, true);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.PROFILE_UPDATED_SUCCESSFULLY);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.PROFILE_UPDATED_SUCCESSFULLY);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }

    public void getUserProfileDetails(int parentId) {
        Call<UserProfileData> userProfileDetailsCall = apiService.getUserProfileDetails(parentId);
        userProfileDetailsCall.enqueue(new Callback<UserProfileData>() {
            @Override
            public void onResponse(@NotNull Call<UserProfileData> call, @NotNull Response<UserProfileData> response) {
                if (response.body() != null) {
                    BlipUtility.storeParentProfileDetailsSharedPref(application, response.body().getUserProfileDetails());

                    BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.MOBILE_NUMBER, response.body().getUserProfileDetails().getPhoneNumber());
                    BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.ADMISSION_ID, response.body().getUserProfileDetails().getAdmissionId());
                    BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.INSTITUTE_NAME, response.body().getUserProfileDetails().getInstitutionName());
                    BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.GENDER, response.body().getUserProfileDetails().getGender());
                    BlipUtility.setSharedPrefString(application.getApplicationContext(), Constants.DATE_OF_BIRTH, response.body().getUserProfileDetails().getDateOfBirth());


                    responseLiveData.setValue(Enums.LoginStatus.GET_USER_PROFILE_DETAILS_SUCCESSFULLY);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.GET_USER_PROFILE_DETAILS_FAILED);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserProfileData> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.GET_USER_PROFILE_DETAILS_FAILED);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }

    public void getBranchSectionDetails(Integer relTenantInstitutionId) {
        final Call<List<BranchSectionData>> branchSectionDetails = apiService.getBranchSectionDetails(relTenantInstitutionId);
        branchSectionDetails.enqueue(new Callback<List<BranchSectionData>>() {
            @Override
            public void onResponse(@NotNull Call<List<BranchSectionData>> call, @NotNull Response<List<BranchSectionData>> response) {
                if (response.body() != null) {
                        branchListLiveData = response.body();
                        if (branchListLiveData == null) {
                            branchListLiveData = new ArrayList<>();
                        }
                    responseLiveData.setValue(Enums.LoginStatus.GET_BRANCH_DETAILS_SUCCESSFULLY);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.GET_BRANCH_DETAILS_FAILED);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BranchSectionData>> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.GET_BRANCH_DETAILS_FAILED);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }



    public void getBannerDetails(Integer relTenantInstitutionId) {
        final Call<List<BannerDetailsDataModel>> bannerDetails = apiService.getBannerDetails(relTenantInstitutionId);
        bannerDetails.enqueue(new Callback<List<BannerDetailsDataModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<BannerDetailsDataModel>> call, @NotNull Response<List<BannerDetailsDataModel>> response) {
                if (response.body() != null) {
                    bannerDetailsDataModelList = response.body();
                    if (bannerDetailsDataModelList == null) {
                        bannerDetailsDataModelList = new ArrayList<>();
                    }
                    responseLiveData.setValue(Enums.LoginStatus.GET_BRANCH_DETAILS_SUCCESSFULLY);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.GET_BRANCH_DETAILS_FAILED);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BannerDetailsDataModel>> call, @NotNull Throwable t) {
                if (networkManager.isNetworkAvailable(application)) {
                    responseLiveData.setValue(Enums.LoginStatus.GET_BRANCH_DETAILS_FAILED);
                } else {
                    responseLiveData.setValue(Enums.LoginStatus.NO_INTERNET_CONNECTION);
                }
            }
        });
    }

    public LiveData<Integer> getLiveData() {
        return responseLiveData;
    }

    public List<BranchSectionData> getBranchesLiveData() {
        return branchListLiveData;
    }

    public List<BannerDetailsDataModel> getBannerDetailsDataModelList() {
        return bannerDetailsDataModelList;
    }

}
