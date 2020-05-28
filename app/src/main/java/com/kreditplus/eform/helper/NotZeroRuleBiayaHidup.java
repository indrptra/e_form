package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

/**
 * Created by apc-lap012 on 05/05/17.
 */

public class NotZeroRuleBiayaHidup extends QuickRule<EditText> {

    private String message = "";
    private String numberStr;

    @Override
    public boolean isValid(EditText view) {

        if (view.getText()!=null){
            if (!view.getText().toString().isEmpty()) {
                numberStr = view.getText().toString().replaceAll(",", "");
                if (numberStr.equalsIgnoreCase("0")) {
                    message = "Biaya Hidup Tidak Boleh 0";
                }
            }

            if (numberStr == null) {
                return false;
            }else {
                return !numberStr.equalsIgnoreCase("0");
            }
        }
        message = "Inputan tidak boleh kosong";
        return false;
    }

    @Override
    public String getMessage(Context context) {
        return message;
    }
}
