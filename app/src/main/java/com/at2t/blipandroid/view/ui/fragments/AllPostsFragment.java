package com.at2t.blipandroid.view.ui.fragments;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.at2t.blipandroid.utils.BaseFragment;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.utils.DatePickerFragment;
import com.at2t.blipandroid.view.ui.AddPostActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.view.adapters.PostsAdapter;
import com.at2t.blipandroid.viewmodel.DashBoardViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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
    private Button btnNewPost;
    LinearLayout calendarView;
    TextView tvDateSelected;
    private int sectionId;
    private TextView noDataTV;
    String currentDateString;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

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

        sectionId = BlipUtility.getSectionId(getContext());

        initializeUIView(view);
        recyclerView = view.findViewById(R.id.rv_posts);
        noDataTV = view.findViewById(R.id.noDataTV);
        noDataTV.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        viewModel.init(getContext());
        viewModel.getPostList(sectionId, null);

        viewModel.getResponseLiveData().observe(this, new
                Observer<List<PostsData>>() {
                    @Override
                    public void onChanged(List<PostsData> postsDataList) {
                        if (postsDataList != null && postsDataList.size() > 0) {

                            if (recyclerView != null) {
                                postsRecyclerViewAdapter = (PostsAdapter) recyclerView.getAdapter();
                                if (postsRecyclerViewAdapter == null) {
                                    postsRecyclerViewAdapter = new PostsAdapter(Objects.requireNonNull(getContext()), postsDataList);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(postsRecyclerViewAdapter);
                                } else {
                                    postsRecyclerViewAdapter.setdata(postsDataList);
                                }
                                noDataTV.setVisibility(View.GONE);
                            } else {
                                noDataTV.setVisibility(View.VISIBLE);
                            }
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            noDataTV.setVisibility(View.VISIBLE);
                            Log.d("Post failed: ", "Try again");
                        }
                    }
                });

    }

    private void initializeUIView(View view) {
        btnNewPost = view.findViewById(R.id.btnNewPost);
        calendarView = view.findViewById(R.id.llDateSelector);
        tvDateSelected = view.findViewById(R.id.tvDate);

        tvDateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(AllPostsFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
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

        currentDateString = (String) android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date());

        Log.e(TAG, "onDateSet: " + currentDateString);
        tvDateSelected.setText(currentDateString);
        viewModel.getPostList(sectionId, currentDateString);
    }

    private void goToAddPostActivity() {
        startActivity(new Intent(getActivity(), AddPostActivity.class));
    }
}
