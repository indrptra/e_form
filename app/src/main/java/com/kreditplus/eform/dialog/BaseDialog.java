package com.kreditplus.eform.dialog;

import android.support.v4.app.DialogFragment;

import butterknife.Unbinder;

/**
 * Created by Iwan Nurdesa on 24/05/16.
 */
public class BaseDialog extends DialogFragment {
    protected Unbinder unbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
