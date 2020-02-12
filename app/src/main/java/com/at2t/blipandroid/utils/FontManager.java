package com.at2t.blipandroid.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.at2t.blipandroid.R;

import java.util.HashMap;


public class FontManager {

    /**
     * FontManager class is used to apply the custom fonts dynamically.
     */
    private static final String ROBOTO_REGULAR_PATH = "fonts/Roboto-Regular.ttf";
    private static final String ROBOTO_LIGHT_PATH = "fonts/Roboto-Light.ttf";
    private static final String ROBOTO_MEDIUM_PATH = "fonts/Roboto-Medium.ttf";
    private static final String ROBOTO_BOLD_PATH = "fonts/Roboto-Bold.ttf";
    private static final String ROBOTO_CONDENSED_REGULAR_PATH = "fonts/RobotoCondensed-Regular.ttf";
    private static final String WORKSANS_SEMIBOLD_PATH = "fonts/WorkSans-SemiBold.ttf";
    private static final String WORKSANS_REGULAR_PATH = "fonts/WorkSans-Regular.ttf";
    private static final String WORKSANS_LIGHT_PATH = "fonts/WorkSans-Light.ttf";
    private static final String WORKSANS_MEDIUM_PATH = "fonts/WorkSans-Medium.ttf";


    public static void setFontFromAttributeSet(Context context, AttributeSet attrs, TextView tv) {
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LEARNWISETEXT);
//        FontManager.setFont(context, tv, array.getInteger(R.styleable.LEARNWISETEXT_fontType, 0));
//        array.recycle();
    }

    public static void setFont(Context context, TextView tv, int type) {
        String fontFile = null;
        switch (type) {
            case Constants.FONT_ROBOTO_REGULAR:
                fontFile = ROBOTO_REGULAR_PATH;
                break;

            case Constants.FONT_ROBOTO_THIN:
                fontFile = ROBOTO_MEDIUM_PATH;
                break;

            case Constants.FONT_ROBOTO_BOLD:
                fontFile = ROBOTO_BOLD_PATH;
                break;

            case Constants.FONT_ROBOTO_LIGHT:
                fontFile = ROBOTO_LIGHT_PATH;
                break;

            case Constants.FONT_CONDENSED_REGULAR:
                fontFile = ROBOTO_CONDENSED_REGULAR_PATH;
                break;

            case Constants.FONT_WORKSANS_SEMIBOLD:
                fontFile = WORKSANS_SEMIBOLD_PATH;
                break;

            case Constants.FONT_WORKSANS_REGULAR:
                fontFile = WORKSANS_REGULAR_PATH;
                break;

            case Constants.FONT_WORKSANS_LIGHT:
                fontFile = WORKSANS_LIGHT_PATH;
                break;

            case Constants.FONT_WORKSANS_MEDIUM:
                fontFile = WORKSANS_MEDIUM_PATH;
                break;
            default:
                break;
        }

        if (fontFile != null) {
            Typeface typeface = TypeFaceProvider.getTypeFace(tv.getContext(), fontFile);
            tv.setTypeface(typeface);
        }
    }
}

class TypeFaceProvider {

    public static final String TYPEFACE_FOLDER = "fonts";
    public static final String TYPEFACE_EXTENSION = ".ttf";

    private static HashMap<String, Typeface> sTypeFaces = new HashMap<>(
            4);

    public static Typeface getTypeFace(Context context, String fileName) {
        Typeface tempTypeface = sTypeFaces.get(fileName);

        if (tempTypeface == null) {
            tempTypeface = Typeface.createFromAsset(context.getAssets(), fileName);
            sTypeFaces.put(fileName, tempTypeface);
        }

        return tempTypeface;
    }
}


