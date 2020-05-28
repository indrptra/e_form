package com.kreditplus.eform.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.kreditplus.eform.R;
import com.kreditplus.eform.fragment.PengaturanFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class PengaturanActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().add(R.id.container,
                new PengaturanFragment(), "PengaturanFragment").commit();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pengajuan_edit;
    }
}
