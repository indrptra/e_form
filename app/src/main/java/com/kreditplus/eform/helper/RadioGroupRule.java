package com.kreditplus.eform.helper;

import android.content.Context;
import android.widget.RadioGroup;

import com.mobsandgeeks.saripaar.QuickRule;

public class RadioGroupRule extends QuickRule<RadioGroup> {
    @Override
    public boolean isValid(RadioGroup view) {
        return isValidRadioGroup(view);
    }

    @Override
    public String getMessage(Context context) {
        return "Silahkan Pilih Radio Group";
    }

    private boolean isValidRadioGroup(RadioGroup view) {
        return view.getCheckedRadioButtonId() != -1;
    }
}
