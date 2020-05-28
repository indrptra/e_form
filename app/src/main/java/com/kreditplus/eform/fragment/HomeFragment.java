package com.kreditplus.eform.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.adapter.HomeAdapter;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.response.CoordinateResponse;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import itsmagic.present.permissionhelper.util.PermissionHelper;

/**
 * Created by Iwan Nurdesa on 02/08/16.
 */
public class HomeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener,
        RefreshTokenMvpView, Validator.ValidationListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;

    @Inject
    SharedPreferences.Editor editor;
    @Inject
    SharedPreferences sharedPreferences;

    private RefreshTokenPresenter mRefreshTokenPresenter;
    private String token;
    private double longitude;
    private double latitude;
    private PermissionHelper mPermissionHelper;

    private ProgressDialog progressDialog;

    private int tokenExpiredType;

    private String action;
    private final int tokenCoordinate = 1;
    private Context mContext;
    private String cro;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);

        mRefreshTokenPresenter = new RefreshTokenPresenter();
        mRefreshTokenPresenter.attachView(this);
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        cro = sharedPreferences.getString(getResources().getString(R.string.sharedpref_cro), "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("EFORM");
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRefreshTokenPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSavePengajuan(RefreshPengajuanUnsyncEvent e) {
        viewPager.setCurrentItem(1, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTab();
        setupPager();
    }

    private void setupTab() {
        tabLayout.addTab(tabLayout.newTab().setText("List Pengajuan"));
        tabLayout.addTab(tabLayout.newTab().setText("List Draft"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(this);
    }

    private void setupPager() {
        HomeAdapter adapter = new HomeAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), cro);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if (Util.checkActiveLocation(getContext())) {
                    menuRefresh();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.commit();
        switch (tokenExpiredType) {
            case tokenCoordinate:
                break;
        }
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    //===========================================================================================================================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null)
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onValidationSucceeded() {
        allpermission();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        getActivity().finish();
    }

    public void allpermission() {
        mPermissionHelper = new PermissionHelper.Builder(getActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA)
                .withPermissionInfos(
                        "Location Permission Needed",
                        "Location Permission Needed",
                        "Camera Permission Needed")
                .withListener(new PermissionHelper.OnPermissionCheckedListener() {
                    @Override
                    public void onPermissionGranted(boolean isPermissionAlreadyGranted) {
                        Toast.makeText(getContext(), "permission granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(getContext(), "You don't have the permissions", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        mPermissionHelper.requestPermission();
    }

    private void menuRefresh() {
        if (tabLayout.getSelectedTabPosition() == 0) {
            EventBus.getDefault().post(new RefreshPengajuanEvent());
        } else if (tabLayout.getSelectedTabPosition() == 1) {
            EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        }
    }
}
