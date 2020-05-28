package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.presenter.mvpview.PengajuanUnsyncMvpView;
import com.kreditplus.eform.service.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class PengajuanUnsyncPresenter implements BasePresenter<PengajuanUnsyncMvpView> {

    @Inject
    DatabaseService databaseService;

    private PengajuanUnsyncMvpView mPengajuanUnsyncMvpView;
    private List<PengajuanBaru> mPengajuanBaruList = new ArrayList<>();
    private List<MasterFormPengajuan> mMasterFormPengajuanList = new ArrayList<>();

    public PengajuanUnsyncPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(PengajuanUnsyncMvpView view) {
        mPengajuanUnsyncMvpView = view;
    }

    @Override
    public void detachView() {
        mPengajuanUnsyncMvpView = null;
    }

    public void pengajuanUnsyncList() {
        mPengajuanUnsyncMvpView.onPreLoadPengajuanUnsync();
        try {
            mPengajuanBaruList = databaseService.getPengajuanBaruDao().queryBuilder().orderBy("id", false).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Query pBaru", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            mMasterFormPengajuanList = databaseService.getMasterFormPengajuanDao().queryBuilder().orderBy("id", false).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Query mBaru", String.valueOf(e));
            Crashlytics.logException(e);
        }
        mPengajuanUnsyncMvpView.onSuccessLoadPengajuanUnsync(mMasterFormPengajuanList, mPengajuanBaruList);

    }
}
