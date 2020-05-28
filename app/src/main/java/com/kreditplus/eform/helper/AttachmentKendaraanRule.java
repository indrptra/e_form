package com.kreditplus.eform.helper;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobsandgeeks.saripaar.QuickRule;

import java.util.ArrayList;
import java.util.List;

public class AttachmentKendaraanRule extends QuickRule<LinearLayout> {

    @Override
    public boolean isValid(LinearLayout view) {
        return isValidAttachment(view);
    }

    @Override
    public String getMessage(Context context) {
        return "Minimal photo = 2";
    }

    private boolean isValidAttachment(LinearLayout view) {
        int j = 0;
        List<ViewGroup> parentPhotos = new ArrayList<>();
        for (int i = 0; i < view.getChildCount(); i++) {
            parentPhotos.add((ViewGroup) view.getChildAt(i));
        }
        for (int i = 0; i < parentPhotos.size(); i++) {
            ViewGroup viewGroup2 = (RelativeLayout) parentPhotos.get(i).getChildAt(1);
            if (viewGroup2.getChildAt(2) instanceof ImageView) {
                ImageView imageView = (ImageView) viewGroup2.getChildAt(1)  ;
                if (imageView.getDrawable() != null && i < 2 ){
                    j++;
                    if (j == 2)
                        return true;
                }

            }
        }
        return false;
    }

}
