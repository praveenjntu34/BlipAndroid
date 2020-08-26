package com.at2t.blipandroid.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
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

    public void loginUserUsingMobileNumber(String phoneNumber) {
        userLoginRepository.loginUsingMobileNumber(phoneNumber);
    }

    public void loginParentUsingMobileNumber(String phoneNumber) {
        userLoginRepository.parentLoginUsingPhone(phoneNumber);
    }

    public void updateUserProfileDetails(String admissionId, Integer childId, String childrenName, String email, String firstName, String lastName, Integer parentId, Integer personId, String phoneNumber, Integer relTenantInstitutionId, String secondaryParentName, String secondaryPhoneNumber, Integer sectionId) {
        userLoginRepository.updateUserProfileDetails(admissionId, childId, childrenName,
                email, firstName, lastName, parentId, personId, phoneNumber, relTenantInstitutionId,
                secondaryParentName, secondaryPhoneNumber, sectionId);
    }

    public void saveFcmTokenInstructor(int instructorId, String fcmToken) {
        userLoginRepository.saveFcmTokenForInstructor(instructorId, fcmToken);
    }

    public void saveFcmTokenParent(int parentId, String fcmToken) {
        userLoginRepository.saveFcmTokenForParent(parentId, fcmToken);
    }

    public void getParentProfileDetails(int parentId) {
        userLoginRepository.getUserProfileDetails(parentId);
    }

    public void getAllPostsForUsers(int sectionId, String date) {
        userLoginRepository.getListofPost(sectionId, date);
    }


    public LiveData<Integer> getResponseLiveData() {
        return integerLiveDataResponse;
    }
}
