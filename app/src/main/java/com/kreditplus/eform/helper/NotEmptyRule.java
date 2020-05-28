package com.kreditplus.eform.helper;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

/**
 * Created by Iwan Nurdesa on 25/08/16.
 */
public class NotEmptyRule extends QuickRule<EditText> {

    @Override
    public boolean isValid(EditText view) {
        if (view.getText() != null) {
            String tmpString = view.getText().toString();
            return !tmpString.isEmpty();
        }
        return false;
    }

    @Override
    public String getMessage(Context context) {
        return "This field is required";
    }
}
