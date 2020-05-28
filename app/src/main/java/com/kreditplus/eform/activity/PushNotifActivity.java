package com.kreditplus.eform.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.Aobranch;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.BranchMaster;
import com.kreditplus.eform.model.DataFinansial;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PosMaster;
import com.kreditplus.eform.model.ProductOfTenor;
import com.kreditplus.eform.model.ProductOfferingMaster;
import com.kreditplus.eform.model.Recomendation;
import com.kreditplus.eform.model.ResultAobranch;
import com.kreditplus.eform.model.SupplierEmp;
import com.kreditplus.eform.model.SupplierMaster;
import com.kreditplus.eform.model.SyncDateDump;
import com.kreditplus.eform.model.TblAgunan;
import com.kreditplus.eform.model.TblAlamat;
import com.kreditplus.eform.model.TblAssetMasterMerkKendaraan;
import com.kreditplus.eform.model.TblAssetMasterTypeKendaraan;
import com.kreditplus.eform.model.TblAsuransi;
import com.kreditplus.eform.model.TblCaraPembayaran;
import com.kreditplus.eform.model.TblDataKartuKredit;
import com.kreditplus.eform.model.TblDataPasangan;
import com.kreditplus.eform.model.TblDataPekerjaan;
import com.kreditplus.eform.model.TblDataPerhitungan;
import com.kreditplus.eform.model.TblDataPerhitunganKendaraan;
import com.kreditplus.eform.model.TblDataPribadi;
import com.kreditplus.eform.model.TblDetailProduct;
import com.kreditplus.eform.model.TblKartuMembership;
import com.kreditplus.eform.model.TblKeterangan;
import com.kreditplus.eform.model.TblKontakDarurat;
import com.kreditplus.eform.model.TblKop;
import com.kreditplus.eform.model.TblLokasi;
import com.kreditplus.eform.model.TblNewIndustryTypeMaster;
import com.kreditplus.eform.model.TblNewProfJobPosition;
import com.kreditplus.eform.model.TblNewProfJobType;
import com.kreditplus.eform.model.TblNewProfession;
import com.kreditplus.eform.model.TblRekomendasi;
import com.kreditplus.eform.model.TblTandaTangan;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.sql.SQLException;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class PushNotifActivity extends BaseActivity implements CoordinateMvpView {

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SharedPreferences.Editor editor;
    @Inject
    DatabaseService databaseService;
    private String token;
    private CoordinatePresenter mCoordinatePresenter;
    private AlertDialog dialogLoading;

    @Override
    protected int getContentView() {
        return R.layout.activity_transparent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        ButterKnife.bind(this);

        mCoordinatePresenter = new CoordinatePresenter();
        mCoordinatePresenter.attachView(this);

        AlertDialog.Builder builderLoading = new AlertDialog.Builder(this);
        builderLoading.setView(R.layout.dialog_loading);
        builderLoading.setCancelable(false);
        dialogLoading = builderLoading.create();

        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");

        String strActionDestination = getIntent().getStringExtra("action_destination");
        Log.i("getStringExtra", strActionDestination);

        if (strActionDestination.equalsIgnoreCase("SigninActivity")) {
            String action = getString(R.string.action_successful_logout);
            double longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
            double latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));
            try {
                Util.saveCoordinate(mCoordinatePresenter, token, latitude, longitude, action);
            } catch (Exception e) {
                if (BuildConfig.DEBUG) Log.e("GPS", String.valueOf(e));
                Crashlytics.logException(e);
            }

            try {
                /*API MASTER DATA*/
                DeleteBuilder<TblNewIndustryTypeMaster, String> dltTblNewIndustryTypeMaster = databaseService.getIndustryTypeMasterDao().deleteBuilder();
                dltTblNewIndustryTypeMaster.delete();

                DeleteBuilder<TblNewProfession, String> dltTblNewProfession = databaseService.getProfessionDao().deleteBuilder();
                dltTblNewProfession.delete();

                DeleteBuilder<TblNewProfJobPosition, String> dltTblNewProfJobPosition = databaseService.getProfJobPositionDao().deleteBuilder();
                dltTblNewProfJobPosition.delete();

                DeleteBuilder<TblNewProfJobType, String> dltTblNewProfJobType = databaseService.getProfJobTypeDao().deleteBuilder();
                dltTblNewProfJobType.delete();

                /*API MASTER SYNC*/
                DeleteBuilder<PosMaster, String> dltPosMaster = databaseService.getPosMasterDao().deleteBuilder();
                dltPosMaster.delete();

                DeleteBuilder<ProductOfferingMaster, String> dltProductOfferingMaster = databaseService.getProductOfferingMasterDao().deleteBuilder();
                dltProductOfferingMaster.delete();

                DeleteBuilder<ProductOfTenor, String> dltProductOfTenor = databaseService.getProductOfTenorDao().deleteBuilder();
                dltProductOfTenor.delete();

                DeleteBuilder<SupplierEmp, String> dltSupplierEmp = databaseService.getSupplierEmpDao().deleteBuilder();
                dltSupplierEmp.delete();

                DeleteBuilder<SupplierMaster, String> dltSupplierMaster = databaseService.getSupplierMasterDao().deleteBuilder();
                dltSupplierMaster.delete();

                DeleteBuilder<Aobranch, String> dltAobranch = databaseService.getAobranchDao().deleteBuilder();
                dltAobranch.delete();

                DeleteBuilder<BranchMaster, String> dltBranchMaster = databaseService.getBranchMasterDao().deleteBuilder();
                dltBranchMaster.delete();

                DeleteBuilder<ResultAobranch, String> dltResultAobranch = databaseService.getResultAobranchDao().deleteBuilder();
                dltResultAobranch.delete();

                DeleteBuilder<DataFinansial, String> dltDataFinansial = databaseService.getDataFinansialDao().deleteBuilder();
                dltDataFinansial.delete();

                DeleteBuilder<TblAssetMasterMerkKendaraan, String> dltTblAssetMasterMerkKendaraan = databaseService.getTblAssetMasterMerkKendaraanDao().deleteBuilder();
                dltTblAssetMasterMerkKendaraan.delete();

                DeleteBuilder<TblAssetMasterTypeKendaraan, String> dltTblAssetMasterTypeKendaraan = databaseService.getTblAssetMasterTypeKendaraanDao().deleteBuilder();
                dltTblAssetMasterTypeKendaraan.delete();

                /*DB LOKAL*/
                DeleteBuilder<MasterFormPengajuan, Integer> dltMasterFormPengajuan = databaseService.getMasterFormPengajuanDao().deleteBuilder();
                dltMasterFormPengajuan.delete();

                DeleteBuilder<TblKop, String> dltTblKop = databaseService.getTblKopDao().deleteBuilder();
                dltTblKop.delete();

                DeleteBuilder<TblDataPribadi, String> dltTblDataPribadi = databaseService.getTblDataPribadiDao().deleteBuilder();
                dltTblDataPribadi.delete();

                DeleteBuilder<TblDataPasangan, String> dltTblDataPasangan = databaseService.getTblDataPasanganDao().deleteBuilder();
                dltTblDataPasangan.delete();

                DeleteBuilder<TblAlamat, String> dltTblAlamat = databaseService.getTblAlamatDao().deleteBuilder();
                dltTblAlamat.delete();

                DeleteBuilder<TblKontakDarurat, String> dltTblKontakDarurat = databaseService.getTblKontakDaruratDao().deleteBuilder();
                dltTblKontakDarurat.delete();

                DeleteBuilder<TblDataPekerjaan, String> dltTblDataPekerjaan = databaseService.getTblDataPekerjaanDao().deleteBuilder();
                dltTblDataPekerjaan.delete();

                DeleteBuilder<TblDataKartuKredit, String> dltTblDataKartuKredit = databaseService.getTblDataKartuKreditDao().deleteBuilder();
                dltTblDataKartuKredit.delete();

                DeleteBuilder<TblKartuMembership, String> dltTblKartuMembership = databaseService.getTblKartuMembershipDao().deleteBuilder();
                dltTblKartuMembership.delete();

                DeleteBuilder<TblDetailProduct, String> dltTblDetailProduct = databaseService.getTblDetailProductDao().deleteBuilder();
                dltTblDetailProduct.delete();

                DeleteBuilder<TblAsuransi, String> dltTblAsuransi = databaseService.getTblAsuransiDao().deleteBuilder();
                dltTblAsuransi.delete();

                DeleteBuilder<TblDataPerhitungan, String> dltTblDataPerhitungan = databaseService.getTblDataPerhitunganDao().deleteBuilder();
                dltTblDataPerhitungan.delete();

                DeleteBuilder<TblKeterangan, String> dltTblKeterangan = databaseService.getTblKeteranganDao().deleteBuilder();
                dltTblKeterangan.delete();

                DeleteBuilder<TblRekomendasi, String> dltTblRekomendasi = databaseService.getTblRekomendasiDao().deleteBuilder();
                dltTblRekomendasi.delete();

                DeleteBuilder<TblLokasi, String> dltTblLokasi = databaseService.getTblLokasiDao().deleteBuilder();
                dltTblLokasi.delete();

                DeleteBuilder<TblTandaTangan, String> dltTblTandaTangan = databaseService.getTblTandaTanganDao().deleteBuilder();
                dltTblTandaTangan.delete();

                DeleteBuilder<Attachment, Integer> deleteAttachmentBuilder = databaseService.getAttachmentDao().deleteBuilder();
                deleteAttachmentBuilder.delete();

                DeleteBuilder<AssetElektronik, String> deleteAssetBuilder = databaseService.getAssetDao().deleteBuilder();
                deleteAssetBuilder.delete();

                DeleteBuilder<MaskingRate, String> rateStringDeleteBuilder = databaseService.getMaskingRateDao().deleteBuilder();
                rateStringDeleteBuilder.delete();

                DeleteBuilder<MaskingTenor, String> tenorStringDeleteBuilder = databaseService.getMaskingTenorDao().deleteBuilder();
                tenorStringDeleteBuilder.delete();

                DeleteBuilder<SyncDateDump, String> dltSyncDateDump = databaseService.getSyncDateDumpDao().deleteBuilder();
                dltSyncDateDump.delete();

                DeleteBuilder<Recomendation, String> dltRecomendation = databaseService.getRecomendationDao().deleteBuilder();
                dltRecomendation.delete();

                DeleteBuilder<TblAgunan, String> deleteTblAgunan = databaseService.getTblAgunanDao().deleteBuilder();
                deleteTblAgunan.delete();

                DeleteBuilder<TblDataPerhitunganKendaraan, String> deleteTblDataPerhitunganKendaraan = databaseService.getTblDataPerhitunganKendaraanDao().deleteBuilder();
                deleteTblDataPerhitunganKendaraan.delete();

                DeleteBuilder<TblCaraPembayaran, String> deleteTblCaraPembayaran = databaseService.getTblCaraPembayaranDao().deleteBuilder();
                deleteTblCaraPembayaran.delete();

                editor.clear();
                editor.commit();

                try {
                    FileUtils.deleteDirectory(new File(Util.getEasyImageDir(this)));
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) Log.e("Delete dir photo", String.valueOf(e));
                    Crashlytics.logException(e);
                }

                dialogLoading.dismiss();
                Intent intent = new Intent(this, SigninActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (java.sql.SQLException e) {
                if (BuildConfig.DEBUG) Log.e("Del Draft Logout", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCoordinatePresenter.detachView();
    }

    @Override
    public void onPreCoordinate() {

    }

    @Override
    public void onFailedCoordinate(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenCoordinateExpired() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
