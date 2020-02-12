package com.at2t.blipandroid.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEdittext extends EditText {

    public CustomEdittext(Context context) {
        super(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.setFontFromAttributeSet(context, attrs, this);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontManager.setFontFromAttributeSet(context, attrs, this);
    }
}
