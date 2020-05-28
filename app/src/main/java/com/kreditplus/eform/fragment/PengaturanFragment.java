package com.kreditplus.eform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Iwan Nurdesa on 26/05/16.
 */
public class PengaturanFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coming_soon, container, false);
        init(view);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("PENGATURAN");
        return view;
    }

    /**
     * Initialization
     *
     * @param view
     */
    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
    }
}
