package com.at2t.blipandroid.view.ui.fragments;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.at2t.blipandroid.utils.BaseFragment;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.DatePickerFragment;
import com.at2t.blipandroid.view.ui.AddPostActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.view.adapters.PostsAdapter;
import com.at2t.blipandroid.viewmodel.DashBoardViewModel;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AllPostsFragment extends BaseFragment implements
        DatePickerDialog.OnDateSetListener {
    public static final String TAG = "AllPostsFragment";

    AllPostsFragment context;
    DashBoardViewModel viewModel;
    RecyclerView recyclerView;
    PostsAdapter postsRecyclerViewAdapter;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private ImageButton btnNewPost;
    private Button btnSeeAllPost;
    LinearLayout calendarView;
    private static TextView tvDateSelected;
    private int instructorSectionId;
    private int parentSectionId;
    private TextView noDataTV;
    String currentDateString;
    private int instructorId;
    private int userId;
    String todaysDateString;

    private List<PostsData> postsDataModelList;
    private LiveData<List<PostsData>> liveData;
    private Observer<List<PostsData>> observer = new Observer<List<PostsData>>() {
        @Override
        public void onChanged(List<PostsData> postsDataList) {
                        if (postsDataList != null && postsDataList.size() > 0) {
                            if(recyclerView.getVisibility() == View.GONE) {
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            if (recyclerView != null) {
                                postsDataModelList = postsDataList;
                                setRecyclerView(postsDataList);
                            } else {
                                noDataTV.setVisibility(View.VISIBLE);
                            }
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            noDataTV.setVisibility(View.VISIBLE);
                            Log.d("Post failed: ", "Try again");
                        }
                    }

    };

    private void setRecyclerView(List<PostsData> postsDataList) {
        postsRecyclerViewAdapter = (PostsAdapter) recyclerView.getAdapter();
        if (postsRecyclerViewAdapter == null) {
            postsRecyclerViewAdapter = new PostsAdapter(getContext(), postsDataList);
            Collections.reverse(postsDataList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(postsRecyclerViewAdapter);
        } else {
            Collections.reverse(postsDataList);
            postsRecyclerViewAdapter.setdata(postsDataList);
        }
        noDataTV.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        liveData = viewModel.getLiveData();
        liveData.observe(this, observer);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_posts_page, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUIView(view);
        recyclerView = view.findViewById(R.id.rv_posts);
        noDataTV = view.findViewById(R.id.noDataTV);
        noDataTV.setVisibility(View.GONE);
        instructorId = BlipUtility.getInstructorId(getContext());
        userId = BlipUtility.getParentId(getContext());

        if(instructorId != 0) {
            instructorSectionId = BlipUtility.getInstructorSectionId(getContext());
            viewModel.getPostData(instructorSectionId, "ALL");
        } else if(userId != 0) {
            parentSectionId = BlipUtility.getParentSectionId(getContext());
            viewModel.getPostData(parentSectionId, "ALL");
        }
    }

    private void initializeUIView(View view) {
        btnNewPost = view.findViewById(R.id.btnNewPost);
        btnSeeAllPost = view.findViewById(R.id.btnAllPost);
        calendarView = view.findViewById(R.id.llDateSelector);
        tvDateSelected = view.findViewById(R.id.tvDate);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        todaysDateString = (year + "-" + (month  +1) + "-" + day  );
        tvDateSelected.setText(todaysDateString);
        tvDateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(AllPostsFragment.this, 0);
                if (getFragmentManager() != null) {
                    datePicker.show(getFragmentManager(), "date picker");
                }

            }
        });

        btnSeeAllPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorId = BlipUtility.getInstructorId(getContext());
                userId = BlipUtility.getParentId(getContext());

                if(instructorId != 0) {
                    instructorSectionId = BlipUtility.getInstructorSectionId(getContext());
                    viewModel.getPostData(instructorSectionId, "ALL");
                } else if(userId != 0) {
                    parentSectionId = BlipUtility.getParentSectionId(getContext());
                    viewModel.getPostData(parentSectionId, "ALL");
                }
            }
        });

        int parentId = BlipUtility.getParentId(getContext());
        if (parentId != 0) {
            btnNewPost.setVisibility(View.GONE);
            btnNewPost.setEnabled(false);
        } else {
            btnNewPost.setEnabled(true);
            btnNewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAddPostActivity();
                }
            });
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        currentDateString = (year + "-" + (month  +1) + "-" + dayOfMonth  );

        Log.e(TAG, "onDateSet: " + currentDateString);
        tvDateSelected.setText(currentDateString);
        instructorId = BlipUtility.getInstructorId(getContext());
        userId = BlipUtility.getParentId(getContext());

        if(instructorId != 0) {
            instructorSectionId = BlipUtility.getInstructorSectionId(getContext());
            viewModel.getPostData(instructorSectionId, currentDateString);
        } else if(userId != 0) {
            parentSectionId = BlipUtility.getParentSectionId(getContext());
            viewModel.getPostData(parentSectionId, currentDateString);
        }

    }

    private void goToAddPostActivity() {
        startActivity(new Intent(getActivity(), AddPostActivity.class));
    }

//    @Override
//    public void onItemClicked(int position) {
//        PostsData postsData = postsDataModelList.get(position);
//        PostItemDetailFragment postItemDetailFragment = new PostItemDetailFragment();
//        String firstName = postsData.getFirstname();
//        String lastName = postsData.getLastName();
//        String fullName = firstName + " " + lastName;
//
//        Bundle bundle = new Bundle();
//        bundle.putInt(Constants.POSITION, position);
//        bundle.putString(Constants.POSTS_USER_NAME, fullName);
//        bundle.putString(Constants.POSTS_TITLE, postsData.getTitle());
//        bundle.putString(Constants.POSTS_DESCRIPTION, postsData.getMessage());
//        bundle.putString(Constants.POSTS_ATTACHMENT, postsData.getPostAttachmentId());
//        bundle.putString(Constants.POSTS_INSTITUTE, postsData.getInstitutionName());
//
//        postItemDetailFragment.setArguments(bundle);
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.container, postItemDetailFragment, PostItemDetailFragment.TAG);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}
