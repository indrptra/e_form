package com.kreditplus.eform.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by ricky on 25/07/16.
 */
public class TextWatcherCustome implements TextWatcher {

    View view;
    AfterTextChange interFaceAfterChange;

    public TextWatcherCustome(View view, AfterTextChange afterTextChange) {
        this.view = view;
        this.interFaceAfterChange = afterTextChange;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public final void afterTextChanged(Editable s) {
        if (interFaceAfterChange != null) {
            interFaceAfterChange.afterTextChanged(s, view);
        }
    }

    public interface AfterTextChange {
        public abstract void afterTextChanged(Editable s, View v);
    }
}
