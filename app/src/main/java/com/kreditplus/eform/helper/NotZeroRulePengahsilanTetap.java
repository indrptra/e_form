package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

/**
 * Created by apc-lap012 on 05/05/17.
 */

public class NotZeroRulePengahsilanTetap extends QuickRule<EditText> {

    private String message="";
    private String numberStr;

    @Override
    public boolean isValid(EditText view) {

        if (view.getText()!=null ){
            if (!view.getText().toString().isEmpty()) {
                numberStr = view.getText().toString().replaceAll(",", "");
                if (numberStr.equalsIgnoreCase("0")){
                    message = "Penghasilan Tetap Tidak Boleh 0";
                }

                if(Integer.parseInt(numberStr) >= 1000000){
                    return true;
                }else{
                    message = "Minimal pendapatan 1 Juta";
                    return false;
                }

            }else{
                message = "This field is required";
            }

            if(numberStr == null){
                return false;
            }else {
                return !numberStr.equalsIgnoreCase("0");
            }
        }else{
            message = "This field is required";
            return false;
        }
    }

    @Override
    public String getMessage(Context context) {
        return message;
    }
}
