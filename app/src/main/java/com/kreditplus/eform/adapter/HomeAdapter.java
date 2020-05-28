package com.kreditplus.eform.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kreditplus.eform.fragment.ListDraftAndSinkronisasiNonElcFragment;
import com.kreditplus.eform.fragment.ListPengajuanFragment;

/**
 * Created by Iwan Nurdesa on 02/08/16.
 */
public class HomeAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String cro;

    public HomeAdapter(FragmentManager fm, int mNumOfTabs, String cro) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
        this.cro = cro;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListPengajuanFragment();
            case 1:
                return new ListDraftAndSinkronisasiNonElcFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
