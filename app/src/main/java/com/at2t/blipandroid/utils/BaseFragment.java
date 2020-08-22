package com.at2t.blipandroid.utils;

import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BaseFragment extends Fragment {

    final protected void showMessage(String message) {
        Toast.makeText(getContext(),
                message,
                Toast.LENGTH_SHORT).show();
    }

    final protected void showMessage(@StringRes int message) {
        Toast.makeText(getContext(),
                message,
                Toast.LENGTH_SHORT).show();
    }

    final protected void switchToFragment(Fragment fragment, @IdRes int containerId, String fragmentTag) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment, fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commit();
    }

}
