package com.at2t.blipandroid.data.network;

import com.at2t.blipandroid.model.InstructorLoginData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("instructor/login/{phoneNumber}")
    Call<InstructorLoginData> loginWithPhoneNumber(@Path("phoneNumber") String phoneNumber);

    @Multipart
    @POST("post-file")
    Call<RequestBody> uploadAttachmentFile(@Part MultipartBody.Part part, @Part("blip") RequestBody requestBody );

    @GET("all-post/{sectionId}")
    Call<ResponseBody> getListOfPost(@Path("sectionId") Integer sectionId);

}
