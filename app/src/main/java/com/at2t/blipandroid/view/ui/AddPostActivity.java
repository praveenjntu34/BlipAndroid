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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.at2t.blipandroid.R;

import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.AddPostData;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.CustomEdittext;
import com.at2t.blipandroid.viewmodel.AddPostViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddPostActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    public static final String TAG = "AddPostActivity";
    public static final int GALLERY_REQUEST_CODE = 1;
    private CustomEdittext postTitle;
    private TextInputEditText postMessage;
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
    String postDescription;
    String title;
    int postId;
    int sectionId;
    int relTenantInstitutionId;
    private AddPostViewModel addPostViewModel;
    private int instructorId;
    private int userId;
    private NetworkManager networkManager;
    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutMessage;

    private Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            if (integer != null) {
                switch (integer) {
                    case AddPostViewModel.ADDED_DATA_SUCCESSFULLY:
                        Toast.makeText(getApplicationContext()
                                , "Added data successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPostActivity.this, MainDashboardActivity.class);
                        startActivity(intent);
                        break;
                    case AddPostViewModel.ADDING_DATA_FAILED:
                        Toast.makeText(getApplicationContext(), "Failed adding the data", Toast.LENGTH_SHORT).show();
                        break;
                    case AddPostViewModel.MULTIPART_FILE_UPLOAD_SUCCESS:
                        Toast.makeText(getApplicationContext(), "Attachment uploaded successfully", Toast.LENGTH_SHORT).show();
                        break;
                    case AddPostViewModel.MULTIPART_FILE_UPLOAD_FAILED:
                        Toast.makeText(getApplicationContext(), "Failed uploading the attachment", Toast.LENGTH_SHORT).show();
                        break;
                    case AddPostViewModel.NO_INTERNET_CONNECTION:
                        Toast.makeText(getApplicationContext(), "No internet! please try again", Toast.LENGTH_SHORT).show();
                        break;
                    case AddPostViewModel.FAILED_UNKNOWN_REASON:
                        Toast.makeText(getApplicationContext(), "Some error occurred! please try again", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_post);
        addPostViewModel = ViewModelProviders.of(this).get(AddPostViewModel.class);
        liveData = addPostViewModel.getLiveData();
        liveData.observe(this, observer);

        initializeView();
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
                    addingPost(postId);
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
        textInputLayoutTitle = findViewById(R.id.text_input_title);
        textInputLayoutMessage = findViewById(R.id.text_input_message);
        onClickListener();
        addingPost(postId);
    }

    private void validatingFields() {
        if (postTitle.getText() != null)
            title = postTitle.getText().toString();

        if (postMessage.getText() != null)
            postDescription = postMessage.getText().toString();

        if (TextUtils.isEmpty(title)) {
            textInputLayoutTitle.setError("Please enter your title for the post.");
            textInputLayoutTitle.requestFocus();
        } else if (TextUtils.isEmpty(postDescription)) {
            textInputLayoutMessage.setError("Please enter your message for the post.");
            textInputLayoutMessage.requestFocus();
        } else {
            title = postTitle.getText().toString();
            postDescription = postMessage.getText().toString();
            relTenantInstitutionId = BlipUtility.getInstituteId(getApplicationContext());
            instructorId = BlipUtility.getInstructorId(getApplicationContext());
            userId = BlipUtility.getParentId(getApplicationContext());
            int instructorSectionId = BlipUtility.getInstructorSectionId(getApplicationContext());
            int personId = BlipUtility.getPersonId(getApplicationContext());

            if(instructorId != 0) {
                if(postId != 0) {
                    addPostViewModel.addPostApiCall(postDescription, postId, title, relTenantInstitutionId,
                            instructorSectionId, personId);
                } else {
                    addPostViewModel.addPostApiCall(postDescription, 0, title, relTenantInstitutionId,
                            instructorSectionId, personId);
                }
            }
        }
    }

    public void addingPost(final int postId) {
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatingFields();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
