package com.kreditplus.eform.helper;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

/**
 * Created by Ignatius on 11/7/2017.
 */

public class EmailValidation extends QuickRule<EditText> {
    @Override
    public boolean isValid(EditText view) {
        if (view.getText() != null) {
            String text = view.getText().toString();
            return text.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(text).matches();
        }
        return false;
    }

    @Override
    public String getMessage(Context context) {
        return "Format email tidak sesuai";
    }
}
