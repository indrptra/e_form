package com.kreditplus.eform.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobsandgeeks.saripaar.QuickRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/08/16.
 */
public class AttachmentRule extends QuickRule<LinearLayout> {

    @Override
    public boolean isValid(LinearLayout view) {
        return isValidAttachment(view);
    }

    @Override
    public String getMessage(Context context) {
        return "Minimal photo = 3";
    }

    private boolean isValidAttachment(LinearLayout view) {
        int j = 0;
        List<ViewGroup> parentPhotos = new ArrayList<>();
        for (int i = 0; i < view.getChildCount(); i++) {
            parentPhotos.add((ViewGroup) view.getChildAt(i));
        }
        for (int i = 0; i < parentPhotos.size(); i++) {
            ViewGroup viewGroup2 = (RelativeLayout) parentPhotos.get(i).getChildAt(1);
            if (viewGroup2.getChildAt(3) instanceof ImageView) {
                ImageView imageView = (ImageView) viewGroup2.getChildAt(3)  ;
                if (imageView.getDrawable() != null && i < 3 ){
                    j++;
                    if (j == 3)
                        return true;
                }

            }
        }
        return false;
    }

}
