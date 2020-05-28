package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.QuickRule;

public class SpinnerRule extends QuickRule<Spinner> {
    @Override
    public boolean isValid(Spinner view) {
        return isValidSpinner(view);
    }

    @Override
    public String getMessage(Context context) {
        return "Silahkan Pilih Spinner";
    }

    private boolean isValidSpinner(Spinner view) {
        if (view.getSelectedItem() != null) {
            if (view.getSelectedItem().toString().equalsIgnoreCase("Pilih")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
