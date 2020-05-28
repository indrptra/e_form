package com.kreditplus.eform.fragment;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by Iwan Nurdesa on 24/05/16.
 */
public class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
