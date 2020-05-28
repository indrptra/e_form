package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

public class NoHpRule extends QuickRule<EditText> {

    private String message = "";

    @Override
    public boolean isValid(EditText view) {
        if (view.getText() != null) {
            String strLengthValue = view.getText().toString();
            if (strLengthValue.isEmpty()) {
                message = "This field is required";
                return false;
            }else{
                if (strLengthValue.length() < 9) {
                    message = "Minimal 9 Digit";
                    return false;
                } else {
                    String strValue = strLengthValue.substring(0, 2);
                    if (strValue.equals("08")) {
                        return true;
                    } else {
                        message = "Format No HP : 08xxxxxxx";
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getMessage(Context context) {
        return message;
    }
}