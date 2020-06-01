package com.at2t.blipandroid.view.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.at2t.blipandroid.R;

import com.at2t.blipandroid.data.network.NetworkManager;
import com.at2t.blipandroid.data.network.RetrofitManager;
import com.at2t.blipandroid.model.ResponseData;
import com.at2t.blipandroid.data.network.ApiInterface;
import com.at2t.blipandroid.utils.DatePickerFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPostFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener {

    public static final String TAG = "AddPostFragment";
    public static final int GALLERY_REQUEST_CODE = 1;
    private EditText postTitle;
    private EditText postMessage;
    private ImageView ivAttachment;
    private String attachmentFilePath;
    private ApiInterface postApiService;
    private NetworkManager networkManager;
    private byte[] filePathByteArray;
    CardView calendarView;
    TextView tvDateSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView();
    }

    private void initializeView() {
        postTitle = getActivity().findViewById(R.id.etTitle);
        postMessage = getActivity().findViewById(R.id.etMessage);
        ivAttachment = getActivity().findViewById(R.id.ivAttachment);
        calendarView = getActivity().findViewById(R.id.cv_calender);
        tvDateSelected = getActivity().findViewById(R.id.tvDate);

        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                if (getFragmentManager() != null) {
                    datePicker.show(getFragmentManager(), "date picker");
                }
            }
        });
        onClickListener();
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

    private void onClickListener(){
        ivAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadAttachmentClick();
            }
        });
    }

    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == getActivity().RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:

                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    getFilePath(data);
                    ivAttachment.setImageURI(selectedImage);
                    convertFileToByteArray();
//                    uploadImageToServer(attachmentFilePath);
                    break;
            }
    }

    public String getFilePath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        // Get the cursor
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();
        //Get the column index of MediaStore.Images.Media.DATA
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //Gets the String value in the column
        attachmentFilePath = cursor.getString(columnIndex);
        cursor.close();
        // Set the Image in ImageView after decoding the String
        ivAttachment.setImageBitmap(BitmapFactory.decodeFile(attachmentFilePath));
        Log.d("Filepath : ", attachmentFilePath);
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
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
    }

    private boolean hasPermission() {
        int result = ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFromGallery();
            }
            else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] convertFileToByteArray(){
        try {
            File file = new File(attachmentFilePath);
//init array with file length
            byte[] bytesArray = new byte[(int) file.length()];

            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            filePathByteArray = bytesArray;
            Log.d("filePathByteArray : ", Arrays.toString(filePathByteArray));
            return filePathByteArray;
        }catch(Exception e){
            e.printStackTrace();

        }
        return new byte[0];
    }

//    private void uploadImageToServer(String filePath) {
//        Retrofit retrofit = RetrofitManager.getInstance().getApiInterface();
//        postApiService = retrofit.create(ApiInterface.class);
//        //Create a file object using file path
//        File file = new File(filePath);
//        // Create a request body with file and image media type
//        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
//        // Create MultipartBody.Part using file request-body,file name and part name
//        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);
//        //Create request body with text description and text media type
//        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
//        //
//        Call<ResponseData> postFileCall = postApiService.uploadAttachmentFile(part, description);
//        postFileCall.enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseData> call, @NotNull Response<ResponseData> response) {
//                if (!response.isSuccessful()) {
//                    Log.d("Response", response.message());
////                    Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//            ResponseData responseData =response.body();
//                if (responseData != null) {
//                if (responseData.getStatus().equals("success")) {
//                    Toast.makeText(getActivity(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
////                } else {
////                    if (responseData.getMessage().equalsIgnoreCase("Username or mobile number already exists")) {
////                        mutableLiveData.setValue(LoginViewModel.SIGN_UP_FAILED_DUE_TO_SAME_USER_NAME);
////                    }
//                }
//
//            }
//
//        }
//
//                @Override
//                public void onFailure(@NotNull Call<ResponseData> call, @NotNull Throwable t) {
//                    if (networkManager.isNetworkAvailable(getActivity())) {
//                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        });
//    }
}
