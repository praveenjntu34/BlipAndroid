package com.at2t.blipandroid.utils;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class MyTextWatcher implements TextWatcher {
    private TextInputLayout textInputLayout;


    public MyTextWatcher(TextInputLayout editTextView) {
        this.textInputLayout = editTextView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        textInputLayout.setError(null);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
