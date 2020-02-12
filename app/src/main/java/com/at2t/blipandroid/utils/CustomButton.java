package com.at2t.blipandroid.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            FontManager.setFontFromAttributeSet(context, attrs, this);
        }
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            FontManager.setFontFromAttributeSet(context, attrs, this);
        }
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }
}
