package com.kreditplus.eform.backup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.fragment.BaseFragment;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.TblLokasi;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.response.CheckEfNumberResponse;
import com.kreditplus.eform.presenter.AttachmentPresenter;
import com.kreditplus.eform.presenter.CheckEfNumberPresenter;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.PengajuanDraftPresenter;
import com.kreditplus.eform.presenter.PengajuanUnsyncPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.SinkronisasiPresenter;
import com.kreditplus.eform.presenter.mvpview.AttachmentMvpView;
import com.kreditplus.eform.presenter.mvpview.CheckEfNumberMvpView;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanDraftMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanUnsyncMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.presenter.mvpview.SynchronizationMvpView;
import com.kreditplus.eform.service.DatabaseService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDraftAndSinkronisasiFragment extends BaseFragment implements PengajuanUnsyncMvpView, SynchronizationMvpView,
        AttachmentMvpView, RefreshTokenMvpView, CoordinateMvpView, PengajuanDraftMvpView,
        CheckEfNumberMvpView {

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    DatabaseService databaseService;
    @BindView(R.id.tv_list_kosong)
    TextView tvListKosong;
    private String token;
    @Inject
    PengajuanUnsyncPresenter pengajuanUnsyncPresenter;
    @Inject
    SinkronisasiPresenter sinkronisasiPresenter;
    @Inject
    AttachmentPresenter attachmentPresenter;

    @BindView(R.id.rv_pengajuan_unsync)
    RecyclerView rvPengajuanUnsync;

    private List<MasterFormPengajuan> masterFormPengajuanList = new ArrayList<>();
    private ListDraftAndSinkronisasiAdapter sinkronisasiAdapter;
    private RefreshTokenPresenter mRefreshTokenPresenter;
    private CoordinatePresenter mCoordinatePresenter;
    private CheckEfNumberPresenter mCheckEfNumberPresenter;
    private PengajuanDraftPresenter mPengajuanDraftPresenter;
    private ProgressDialog progressDialog;
    private ListDraftAndSinkronisasiAdapter.PengajuanUnsyncVH holder;
    private boolean isSubmitting;
    private String action;
    private String applicationId;
    private double longitude;
    private double latitude;
    private int typeExpired;
    private PengajuanBaru pengajuanBaru;
    private MasterFormPengajuan masterFormPengajuan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        sinkronisasiAdapter = new ListDraftAndSinkronisasiAdapter(getActivity(), masterFormPengajuan, masterFormPengajuanList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_draft_and_sinkronisasi, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        mRefreshTokenPresenter = new RefreshTokenPresenter();
        mCoordinatePresenter = new CoordinatePresenter();
        mCheckEfNumberPresenter = new CheckEfNumberPresenter();
        mPengajuanDraftPresenter = new PengajuanDraftPresenter();

        pengajuanUnsyncPresenter.attachView(this);
        sinkronisasiPresenter.attachView(this);
        attachmentPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        mCoordinatePresenter.attachView(this);
        mCheckEfNumberPresenter.attachView(this);
        mPengajuanDraftPresenter.attachView(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPengajuanUnsync.setLayoutManager(linearLayoutManager);
        rvPengajuanUnsync.setAdapter(sinkronisasiAdapter);
        pengajuanUnsyncPresenter.pengajuanUnsyncList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        pengajuanUnsyncPresenter.detachView();
        sinkronisasiPresenter.detachView();
        attachmentPresenter.detachView();
        mRefreshTokenPresenter.detachView();
        mCoordinatePresenter.detachView();
        mCheckEfNumberPresenter.detachView();
        mPengajuanDraftPresenter.detachView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSavePengajuan(RefreshPengajuanUnsyncEvent e) {
        pengajuanUnsyncPresenter.pengajuanUnsyncList();
    }


    public void saveSyncCoordidate(ListDraftAndSinkronisasiAdapter.PengajuanUnsyncVH holder, Integer formPengajuanId) {
        this.holder = holder;
        mPengajuanDraftPresenter.getPengajuanBaru(formPengajuanId);
    }

    @Override
    public void onPreSubmitAttachment() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.show();
        progressDialog.setMessage("Submit Attachment");
    }

    @Override
    public void onSuccessSubmitAttachment() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.dismiss();
        action = getString(R.string.action_submit_attachment);
        sendLocations();
        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        EventBus.getDefault().post(new RefreshPengajuanEvent());
    }

    @Override
    public void onFailedSubmitAttachment(String message) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenAttachmentExpired() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        typeExpired = 2;
        String checktoken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(checktoken);
    }

    @Override
    public void onPreCoordinate() {

    }

    @Override
    public void onFailedCoordinate(String massage) {

    }

    @Override
    public void onTokenCoordinateExpired() {

    }

    @Override
    public void onPreLoadPengajuanDraft() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.show();
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onSuccessLoadPengajuanDraft(MasterFormPengajuan masterFormPengajuan, List<TblLokasi> tblLokasiList, List<AssetElektronik> assetElektronikList, List<Attachment> attachmentList, List<MaskingTenor> maskingTenorList, List<MaskingRate> maskingRateList) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
        latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));
        String strEfNumber = masterFormPengajuan.getEfNumber();

        try {
            UpdateBuilder<TblLokasi, String> updateBuilder = databaseService.getTblLokasiDao().updateBuilder();
            updateBuilder.where().eq("pengajuan_id", masterFormPengajuan.getId());
            updateBuilder.updateColumnValue("sync_action", "Successful Synchronization");
            updateBuilder.updateColumnValue("sync_longitude", longitude);
            updateBuilder.updateColumnValue("sync_latitude", latitude);
            updateBuilder.update();
        } catch (SQLException e) {
            progressDialog.dismiss();
            if (BuildConfig.DEBUG) Log.e("update_sync", String.valueOf(e));
            Crashlytics.logException(e);
        }
        progressDialog.dismiss();
        if (masterFormPengajuan.getTipeSaveData().equals("isAssignEdit")) {
            this.masterFormPengajuan = masterFormPengajuan;
            if (!isSubmitting) {
                isSubmitting = true;
                progressDialog.dismiss();
                sinkronisasiPresenter.sinkronisasiPengajuan(token, masterFormPengajuan);
            } else
                Toast.makeText(getActivity(), "Please finish another application first!", Toast.LENGTH_SHORT).show();
        } else mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
    }

    @Override
    public void onFailedLoadPengajuanDraft(String message) {
        progressDialog.dismiss();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreLoadPengajuanUnsync() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessLoadPengajuanUnsync(List<MasterFormPengajuan> masterFormPengajuanList, List<PengajuanBaru> pengajuanBaruList) {
        this.masterFormPengajuanList = masterFormPengajuanList;
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (masterFormPengajuanList.isEmpty()) tvListKosong.setVisibility(View.VISIBLE);
        else {
            tvListKosong.setVisibility(View.GONE);
            sinkronisasiAdapter.setMasterFormPengajuanList(masterFormPengajuanList);
        }
    }

    @Override
    public void onFailedLoadPengajuanUnsync(String message) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreSynchronization() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.show();
        progressDialog.setMessage("Submit Pengajuan");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onSuccessSynchronization(String applicationId) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.dismiss();
        isSubmitting = false;
        action = getString(R.string.action_submit_submission);
        sendLocations();
        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        this.applicationId = applicationId;
        addApplicationId(applicationId);
        uploadAttachment(holder);
    }

    private void uploadAttachment(ListDraftAndSinkronisasiAdapter.PengajuanUnsyncVH holder) {
        if (BuildConfig.DEBUG) Log.e("submit attachment", "From Fragment");
        this.holder = holder;
        String type;

        if ("isAssignEdit".equalsIgnoreCase(masterFormPengajuan.getTipeSaveData())) type = "edit";
        else type = "";

        attachmentPresenter.submitAttachment(token, applicationId, pengajuanBaru, masterFormPengajuan, type, "ELC");
    }

    private void addApplicationId(String applicationId) {
        int appIdDb;
        if (masterFormPengajuan == null) {
            masterFormPengajuan = masterFormPengajuanList.get(holder.getAdapterPosition());
            appIdDb = masterFormPengajuan.getId();
        } else {
            appIdDb = masterFormPengajuan.getId();
        }

        try {
            UpdateBuilder builder = databaseService.getMasterFormPengajuanDao().updateBuilder();
            builder.where().eq("id", appIdDb);
            builder.updateColumnValue("app_id_backend", applicationId);
            builder.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Query pengajuan", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    private void sendLocations() {
        try {
            longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
            latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));
            Util.saveCoordinate(mCoordinatePresenter, token, latitude, longitude, action);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("GPS", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    @Override
    public void onFailedSynchronization(String message) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        progressDialog.dismiss();
        isSubmitting = false;

        builder.setTitle("Refresh");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onTokenSynchronization() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        typeExpired = 1;
        String checktoken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(checktoken);
    }

    public void submitAttachment(ListDraftAndSinkronisasiAdapter.PengajuanUnsyncVH holder, Integer appId) {
        if (BuildConfig.DEBUG) Log.e("submit attachment", "From Adapter");
        List<MasterFormPengajuan> newlist = new ArrayList<>();
        this.holder = holder;
        String type;
        masterFormPengajuan = masterFormPengajuanList.get(holder.getAdapterPosition());
        try {
            newlist = databaseService.getMasterFormPengajuanDao().queryBuilder().where().eq("id", appId).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Get application Id", "Failed");
            Crashlytics.logException(e);
            return;
        }
        if (newlist != null && !newlist.isEmpty()) {
            applicationId = newlist.get(0).getAppIdBackend();
        } else {
            Crashlytics.log(1, "Sinkron", "List get app id backend kosong");
            return;
        }

        if ("isAssignEdit".equalsIgnoreCase(masterFormPengajuan.getTipeSaveData())) type = "edit";
        else type = "";
        attachmentPresenter.submitAttachment(token, applicationId, pengajuanBaru, masterFormPengajuan, type, "ELC");
    }

    @Override
    public void onPreCheckEFNumber() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.show();
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onSuccessCheckEFNumber(CheckEfNumberResponse mCheckEfNumberResponse, MasterFormPengajuan mMasterFormPengajuan) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (mCheckEfNumberResponse.getStatus().equals("1")) {
            this.masterFormPengajuan = mMasterFormPengajuan;
            if (!isSubmitting) {
                isSubmitting = true;
                progressDialog.dismiss();
                sinkronisasiPresenter.sinkronisasiPengajuan(token, masterFormPengajuan);
            } else
                Toast.makeText(getActivity(), "Please finish another application first!", Toast.LENGTH_SHORT).show();
        } else {
            String branchId = sharedPreferences.getString(getResources().getString(R.string.sharedpref_brachCode), "");
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(Calendar.getInstance().getTime());
            String strEfNumber = "EFM" + branchId + date;

            try {
                UpdateBuilder<MasterFormPengajuan, Integer> updtMasterFormPengajuan = databaseService.getMasterFormPengajuanDao().updateBuilder();
                updtMasterFormPengajuan.where().eq("id", mMasterFormPengajuan.getId());
                updtMasterFormPengajuan.updateColumnValue("ef_number", strEfNumber);
                updtMasterFormPengajuan.update();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("updtTblKop", String.valueOf(e));
                Crashlytics.logException(e);
            }

            try {
                MasterFormPengajuan newMasterFormPengajuan = databaseService.getMasterFormPengajuanDao().queryForId(mMasterFormPengajuan.getId());
                this.masterFormPengajuan = newMasterFormPengajuan;
                mCheckEfNumberPresenter.efNumber(token, newMasterFormPengajuan.getEfNumber(), newMasterFormPengajuan);
                Log.i("Ef_Number 0", newMasterFormPengajuan.getEfNumber());
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("updtMasterFormPengajuan", String.valueOf(e));
                Crashlytics.logException(e);
            }
            progressDialog.dismiss();
        }
    }

    @Override
    public void onFailedCheckEFNumber(String message) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenCheckEFNumber() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.dismiss();
    }
}
