package com.at2t.blipandroid.data.network;

import com.at2t.blipandroid.model.AddPostData;
import com.at2t.blipandroid.model.InstructorLoginData;
import com.at2t.blipandroid.model.PostsData;

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

public interface ApiInterface {

    @GET("instructor/login/{phoneNumber}")
    Call<InstructorLoginData> loginWithPhoneNumber(@Path("phoneNumber") String phoneNumber);

    @Multipart
    @POST("post-file")
    Call<AddPostData> uploadAttachmentFile(@Part MultipartBody.Part file);

    @GET("all-post/{sectionId}")
    Call<List<PostsData>> getListOfPost(@Path("sectionId") Integer sectionId);

    @POST("post")
    Call<ResponseBody> addPostData(@Body AddPostData addPostData);

}
