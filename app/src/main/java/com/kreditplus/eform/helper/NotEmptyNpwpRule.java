package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

/**
 * Created by nurirppan on 12/21/2017.
 */

public class NotEmptyNpwpRule extends QuickRule<EditText> {

    private String message;

    @Override
    public boolean isValid(EditText view) {
        if (view.getText() != null) {
            String sNpwp = view.getText().toString();
            if (sNpwp.isEmpty()) {
                message = "The field is required";
                return false;
            } else if (sNpwp.length() != 20) {
                message = "Invalid length";
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getMessage(Context context) {
        return message;
    }
}
