package com.kreditplus.eform.view;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Alvin Rusli on 06/14/2017.
 * <p/>
 * Customized and overly simplified version of https://github.com/faranjit/currency_edittext.
 * Locale causes too much trouble on multiple devices, and since this app uses
 * fixed configuration, just use this class instead.
 */
public class IndonesianCurrencyEditText extends TextInputEditText {

    private static final char M_GROUP_DIVIDER = ',';
    private static final char M_DECIMAL_SEPARATOR = '.';
    private DecimalFormat numberFormat;

    public IndonesianCurrencyEditText(Context context) {
        this(context, null);
    }

    public IndonesianCurrencyEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndonesianCurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        numberFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);

        TextWatcher onTextChangeListener = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    return;

                removeTextChangedListener(this);
                /***
                 * Clear input to get clean text before format
                 */
                String text = s.toString();
                text = text.replace(M_GROUP_DIVIDER + "", "").trim();
                text = format(text);

                setText(text);
                setSelection(text.length());

                addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        addTextChangedListener(onTextChangeListener);
    }

    private String format(String text) {

        return numberFormat.format(Integer.parseInt(text));
    }

    /**
     * @return int value for current text
     */
    public int getCurrencyInt() {
        String text = getText().toString();
        text = text.replace(M_GROUP_DIVIDER + "", "").trim();
        return Integer.parseInt(text);
    }

    /**
     * @return String value for current text
     */
    public String getCurrencyText() {
        return getText().toString();
    }
}
