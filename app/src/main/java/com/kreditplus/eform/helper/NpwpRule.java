package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

public class NpwpRule extends QuickRule<EditText> {

    private String message = "";

    @Override
    public boolean isValid(EditText view) {
        if (view.getText() != null) {
            String tmpString = view.getText().toString();
            if (tmpString.isEmpty()) {
                message = "Invalid length";
                return false;
            } else if (tmpString.length() != 20) {
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
