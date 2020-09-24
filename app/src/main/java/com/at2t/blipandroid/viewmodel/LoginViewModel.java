package com.at2t.blipandroid.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.data.repositories.UserLoginRepository;
import com.at2t.blipandroid.model.BannerDetailsDataModel;
import com.at2t.blipandroid.model.BranchSectionData;

import java.util.List;

public class LoginViewModel extends ViewModel {

    public String phoneNumber;
    private UserLoginRepository userLoginRepository;
    private LiveData<Integer> integerLiveDataResponse;
    private List<BranchSectionData> branchSectionDataList;
    private List<BannerDetailsDataModel> bannerDetailsDataModelList;
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

    public void updateUserProfileDetails(int branchId, String branchName, String branchSectionName,
                                         String admissionId, Integer childId, String childrenName,
                                         String email, String firstName, String lastName,
                                         Integer parentId, Integer personId, String phoneNumber,
                                         Integer relTenantInstitutionId, String secondaryParentName,
                                         String secondaryPhoneNumber, Integer sectionId,
                                         String instituteName, String gender, String dob) {

        userLoginRepository.updateUserProfileDetails(branchId, branchName, branchSectionName, admissionId, childId, childrenName,
                email, firstName, lastName, parentId, personId, phoneNumber, relTenantInstitutionId,
                secondaryParentName, secondaryPhoneNumber, sectionId, instituteName, gender, dob);
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

    public void getBranchDetails(int relTenantInstitutionId) {
        userLoginRepository.getBranchSectionDetails(relTenantInstitutionId);
    }

    public void getBannerDetails(int relTenantInstitutionId) {
        userLoginRepository.getBannerDetails(relTenantInstitutionId);
    }

    public void loginParentUsingAdmissionId(String admissionId) {
        userLoginRepository.loginParentUsingAdmissionId(admissionId);
    }

    public List<BranchSectionData> getBranchData() {
       branchSectionDataList =  userLoginRepository.getBranchesLiveData();
        return branchSectionDataList;
    }

    public List<BannerDetailsDataModel> getBannerDetails() {
        bannerDetailsDataModelList =  userLoginRepository.getBannerDetailsDataModelList();
        return bannerDetailsDataModelList;
    }


    public LiveData<Integer> getResponseLiveData() {
        return integerLiveDataResponse;
    }
}
