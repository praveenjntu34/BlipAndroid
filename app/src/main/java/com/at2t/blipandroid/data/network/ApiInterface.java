package com.at2t.blipandroid.data.network;

import com.at2t.blipandroid.model.AddPostData;
import com.at2t.blipandroid.model.AdmissionIdModel;
import com.at2t.blipandroid.model.BannerDetailsDataModel;
import com.at2t.blipandroid.model.BranchSectionData;
import com.at2t.blipandroid.model.FcmTokenModel;
import com.at2t.blipandroid.model.InstructorLoginData;
import com.at2t.blipandroid.model.ParentDataModel;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.model.UserProfileData;
import com.at2t.blipandroid.model.UserProfileDetails;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("instructor/login/{phoneNumber}")
    Call<InstructorLoginData> loginWithPhoneNumber(@Path("phoneNumber") String phoneNumber);

    @GET("parent/login/{phoneNumber}")
    Call<ParentDataModel> userLoginUsingMobile(@Path("phoneNumber") String phoneNumber);

    @GET("parent/profile/{parentId}")
    Call<UserProfileData> getUserProfileDetails(@Path("parentId") Integer parentId);

    @POST("parent/profile")
    Call<ResponseBody> updateUserProfile(@Body UserProfileDetails userProfileDetails);

    @Multipart
    @POST("post-file")
    Call<AddPostData> uploadAttachmentFile(@Part MultipartBody.Part file);

    @GET("all-post/{sectionId}")
    Call<List<PostsData>> getListOfPost(@Path("sectionId") Integer sectionId, @Query("date") String date);

    @POST("post")
    Call<ResponseBody> addPostData(@Body AddPostData addPostData);

    @POST("fcm/instructor")
    Call<ResponseBody> postFcmTokenForInstructor(@Body FcmTokenModel fcmTokenModel);

    @POST("fcm/parent")
    Call<ResponseBody> postFcmTokenForParent(@Body FcmTokenModel fcmTokenModel);

    @GET("banner/{relTenantInstitutionId}")
    Call<List<BannerDetailsDataModel>> getBannerDetails(@Path("relTenantInstitutionId") Integer relTenantInstitutionId);

    @GET("/institution/branch/{relTenantInstitutionId}")
    Call<List<BranchSectionData>> getBranchSectionDetails(@Path("relTenantInstitutionId") Integer relTenantInstitutionId);

    @GET("parent/adm-login/{admissionId}")
    Call<AdmissionIdModel> loginUsingAdmissionId(@Path("admissionId") String admissionId);
}
