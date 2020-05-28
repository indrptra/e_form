package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.ImageView;

import com.mobsandgeeks.saripaar.QuickRule;

/**
 * Created by Iwan Nurdesa on 25/08/16.
 */
public class ImageViewRule extends QuickRule<ImageView> {
    @Override
    public boolean isValid(ImageView view) {
        return view.getDrawable() != null;
    }

    @Override
    public String getMessage(Context context) {
        return "Take Photo";
    }
}
