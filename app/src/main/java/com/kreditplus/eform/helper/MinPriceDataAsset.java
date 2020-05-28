package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

public class MinPriceDataAsset extends QuickRule<EditText> {

    @Override
    public boolean isValid(EditText view) {
        if (!view.getText().toString().isEmpty()) {
            int tmpInt = Integer.parseInt(view.getText().toString().replace(",", ""));
            String tmpString = view.getText().toString();
            return tmpInt >= 250000 && !tmpString.isEmpty();
        }
        return false;
    }

    @Override
    public String getMessage(Context context) {
        return "Minimal Rp 250.000";
    }
}
