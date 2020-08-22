package com.at2t.blipandroid.view.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.at2t.blipandroid.R;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.AddPostData;
import com.at2t.blipandroid.view.ui.fragments.AllPostsFragment;
import com.at2t.blipandroid.viewmodel.AddPostViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddPostActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    public static final String TAG = "AddPostActivity";
    public static final int GALLERY_REQUEST_CODE = 1;
    private EditText postTitle;
    private EditText postMessage;
    private ImageView ivAttachment;
    private String attachmentFilePath;
    private Button btnAddPost;
    LinearLayout calendarView;
    TextView tvDateSelected;
    private AddPostViewModel viewModel;
    private LiveData<Integer> liveData;
    String encodeString = null;
    private ApiInterface apiInterface;
    private Context application;
    private NetworkManager networkManager;
    String title;
    String postDescription;
    int postId;
    int sectionId;
    int relTenantInstitutionId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_post);
        initializeView();
    }

    public void addPostApiCall(String message, int postId, String title, int relTenantInstitutionId, int sectionId) {
        final AddPostData addPostData = new AddPostData(message, postId, title, relTenantInstitutionId, sectionId);
        Call<ResponseBody> responseBodyCall = apiInterface.addPostData(addPostData);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                if (response.code() == 200) {
                    Log.d(TAG, "onResponse id1 : " + response.toString());
                    if(response.body() != null)
                    Log.d(TAG, "onResponse id2 : " + response.body().toString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                if (NetworkManager.getInstance().isNetworkAvailable(application)) {

                    Log.d(TAG, "No internet connection");
                }
            }
        });

    }

    private void uploadToServer(String filePath) {
        networkManager = NetworkManager.getInstance();
        apiInterface = RetrofitManager.getInstance().getApiInterface();

        //Create a file object using file path
        File file = new File(filePath);

        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "file", fileReqBody);

        Call<AddPostData> call = apiInterface.uploadAttachmentFile(part);
        call.enqueue(new Callback<AddPostData>() {
            @Override
            public void onResponse(@NotNull Call<AddPostData> call, @NotNull Response<AddPostData> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse id : " + response.body().getPostId());
                    getPostId(response.body().getPostId());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddPostData> call, @NotNull Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
    }

    public int getPostId(int postId){
        return this.postId = postId;
    }

    private void initializeView() {
        postTitle = findViewById(R.id.etTitle);
        postMessage = findViewById(R.id.etMessage);
        ivAttachment = findViewById(R.id.ivAttachment);
        btnAddPost = findViewById(R.id.btnAddPost);

        onClickListener();
        addingPost();
    }

    public void addingPost() {
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = postTitle.getText().toString();
                postDescription = postMessage.getText().toString();
                addPostApiCall(postDescription, postId, title, relTenantInstitutionId, sectionId);
                Log.d("Post details: ", "postDescription : " +postDescription + " " +
                        "postId: "+postId + " title : "+ title + " relTenantInstitutionId : "
                        +relTenantInstitutionId + "sectionId: " + sectionId);
                Intent i = new Intent(AddPostActivity.this, AllPostsFragment.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());

        tvDateSelected.setText(currentDateString);
    }

    private void onClickListener() {
        ivAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadAttachmentClick();
            }
        });
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (this.RESULT_OK == resultCode)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:

                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    getFilePath(data);
                    ivAttachment.setImageURI(selectedImage);
                    uploadToServer(attachmentFilePath);
                    Log.d(TAG, "onChanged: filepart2: " + attachmentFilePath);
                    break;
            }
    }

    public String getFilePath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        // Get the cursor
        Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();
        //Get the column index of MediaStore.Images.Media.DATA
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //Gets the String value in the column
        attachmentFilePath = cursor.getString(columnIndex);
        cursor.close();
        // Set the Image in ImageView after decoding the String
        ivAttachment.setImageBitmap(BitmapFactory.decodeFile(attachmentFilePath));
        Log.d(TAG, "onChanged: filepart3: " + attachmentFilePath);
        return attachmentFilePath;
    }

    public void onUploadAttachmentClick() {
        if (hasPermission()) {
            pickFromGallery();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
    }

    private boolean hasPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFromGallery();
                Log.d(TAG, "onChanged: filepart4: " + attachmentFilePath);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
