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
    private LiveData<List<PostsData>> liveData;

    private Observer<List<PostsData>> observer = new Observer<List<PostsData>>() {
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
                }
            } else {
                Log.d("Post failed: ", "Try again");
            }
        }
    };

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
        if(getArguments() != null) {
            sectionId = getArguments().getInt(Constants.SECTION_ID);
        }
        intializeNavigationView(view);
        initializeUIView(view);
        recyclerView = view.findViewById(R.id.rv_posts);
        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        viewModel.getPostList(sectionId);
        viewModel.getPostLiveDataObservables().observe(this, new
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
                            }
                        } else {
                            Log.d("Post failed: ", "Try again");
                        }
                    }
                });

    }

    public void observerViewModel(DashBoardViewModel dashBoardViewModel){
        dashBoardViewModel.getPostLiveDataObservables().observe(this, new
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
                            }
                        } else {
                            Log.d("Post failed: ", "Try again");
                        }
                    }
                });
    }

    private void initializeUIView(View view) {
        btnNewPost = view.findViewById(R.id.btnNewPost);
        calendarView = view.findViewById(R.id.llDateSelector);
        tvDateSelected = view.findViewById(R.id.tvDate);

        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                if (getFragmentManager() != null) {
                    datePicker.show(getFragmentManager(), "date picker");
                }
            }
        });

        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddPostActivity();
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

    private void goToAddPostActivity() {
        startActivity(new Intent(getActivity(), AddPostActivity.class));
    }

    public void intializeNavigationView(View view) {
        dl = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        t = new ActionBarDrawerToggle(getActivity(), dl, toolbar, R.string.app_name, R.string.Close);
        t.syncState();
        dl.addDrawerListener(t);

        nv = (NavigationView) view.findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(getContext(), "My Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mycart:
                        Toast.makeText(getContext(), "My Cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        context = this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            dl.openDrawer(Gravity.LEFT);

        return super.onOptionsItemSelected(item);
    }
}
