package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

public class RTRWRule extends QuickRule<EditText> {

    private String message = "";

    @Override
    public boolean isValid(EditText view) {
        if (view.getText() != null) {
            String strLengthValue = view.getText().toString();
            if (strLengthValue.isEmpty()) {
//                message = "This field is required";
                return false;
            }else{
                    if (strLengthValue.startsWith("0")) {
                        message = "Tidak boleh di isi 0";
                        return false;
                    } else {
                        return true;
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
