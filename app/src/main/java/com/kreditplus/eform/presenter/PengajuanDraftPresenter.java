package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetKendaraan;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.TblLokasi;
import com.kreditplus.eform.presenter.mvpview.PengajuanDraftMvpView;
import com.kreditplus.eform.service.DatabaseService;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class PengajuanDraftPresenter implements BasePresenter<PengajuanDraftMvpView> {

    @Inject
    DatabaseService databaseService;

    private PengajuanDraftMvpView mPengajuanDraftMvpView;
    private MasterFormPengajuan mMasterFormPengajuan;
    private List<TblLokasi> tblLokasiList;
    private List<AssetElektronik> assetElektronikList;
    private List<Attachment> attachmentList;
    private List<MaskingRate> rateMaskingList;
    private List<MaskingTenor> tenorMaskingList;

    public PengajuanDraftPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(PengajuanDraftMvpView view) {
        mPengajuanDraftMvpView = view;
    }

    @Override
    public void detachView() {
        mPengajuanDraftMvpView = null;
    }

    public void getPengajuanBaru(int pengajuanId) {
        mPengajuanDraftMvpView.onPreLoadPengajuanDraft();
        try {
            mMasterFormPengajuan = databaseService.getMasterFormPengajuanDao().queryForId(pengajuanId);
            assetElektronikList = databaseService.getAssetDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblLokasiList = databaseService.getTblLokasiDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            attachmentList = databaseService.getAttachmentDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tenorMaskingList = databaseService.getMaskingTenorDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            rateMaskingList = databaseService.getMaskingRateDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Query all", String.valueOf(e));
            Crashlytics.logException(e);
        }
        mPengajuanDraftMvpView.onSuccessLoadPengajuanDraft(
                mMasterFormPengajuan,
                tblLokasiList,
                assetElektronikList,
                attachmentList,
                tenorMaskingList,
                rateMaskingList);

    }
}
