package com.kreditplus.eform.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.adapter.PengajuanUnsyncAdapter;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetKendaraan;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.TblLokasi;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.response.CoordinateResponse;
import com.kreditplus.eform.presenter.AttachmentPresenter;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.PengajuanAddPresenter;
import com.kreditplus.eform.presenter.PengajuanDraftPresenter;
import com.kreditplus.eform.presenter.PengajuanUnsyncPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.AttachmentMvpView;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanAddMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanDraftMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanUnsyncMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.service.DatabaseService;
import com.kreditplus.eform.service.GPSTrackerService;

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

/**
 * Created by Iwan Nurdesa on 02/08/16.
 */
public class PengajuanUnsyncFragment extends BaseFragment implements PengajuanUnsyncMvpView, PengajuanAddMvpView, AttachmentMvpView, RefreshTokenMvpView, CoordinateMvpView, PengajuanDraftMvpView {

    @Inject
    PengajuanUnsyncPresenter pengajuanUnsyncPresenter;

    @Inject
    PengajuanAddPresenter pengajuanAddPresenter;

    @Inject
    AttachmentPresenter attachmentPresenter;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    DatabaseService databaseService;


    @BindView(R.id.rv_pengajuan_unsync)
    RecyclerView rvPengajuanUnsync;

    private View view;

    private PengajuanUnsyncAdapter adapter;
    private List<PengajuanBaru> pengajuanBaruList = new ArrayList<>();
    private PengajuanBaru pengajuanBaru;
    private PengajuanUnsyncAdapter.PengajuanUnsyncVH holder;
    private boolean isSubmitting;
    private String token;
    private String applicationId;
    private ViewAnimator va;
    private int typeExpired;
    private RefreshTokenPresenter mRefreshTokenPresenter;
    private GPSTrackerService gps;
    private ProgressDialog progressDialog;
    private int pengajuanId;
    private String formType;

    private int tokenExpiredType;
    private final int submitPengajuan = 1;
    private final int tokenCoordinate = 2;

    // Location
    private CoordinatePresenter mCoordinatePresenter;
    private CoordinateResponse mCoordinateResponse;
    private String action;
    private double longitude;
    private double latitude;

    //    Draft
    private PengajuanDraftPresenter mPengajuanDraftPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        adapter = new PengajuanUnsyncAdapter(getActivity(), pengajuanBaruList, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pengajuan_unsync, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        progressDialog = new ProgressDialog(getContext());
        mRefreshTokenPresenter = new RefreshTokenPresenter();
        mCoordinatePresenter = new CoordinatePresenter();
        mPengajuanDraftPresenter = new PengajuanDraftPresenter();

        pengajuanUnsyncPresenter.attachView(this);
        pengajuanAddPresenter.attachView(this);
        attachmentPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        mCoordinatePresenter.attachView(this);
        mPengajuanDraftPresenter.attachView(this);
        va = (ViewAnimator) view.findViewById(R.id.va);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPengajuanUnsync.setLayoutManager(linearLayoutManager);
        rvPengajuanUnsync.setAdapter(adapter);
        pengajuanUnsyncPresenter.pengajuanUnsyncList();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        pengajuanUnsyncPresenter.detachView();
        pengajuanAddPresenter.detachView();
        attachmentPresenter.detachView();
        mRefreshTokenPresenter.detachView();
        mCoordinatePresenter.detachView();
        mPengajuanDraftPresenter.detachView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSavePengajuan(RefreshPengajuanUnsyncEvent e) {
        pengajuanUnsyncPresenter.pengajuanUnsyncList();
    }

    @Override
    public void onPreLoadPengajuanUnsync() {
    }

    @Override
    public void onSuccessLoadPengajuanUnsync(List<MasterFormPengajuan> masterFormPengajuanList, List<PengajuanBaru> pengajuanBaruList) {
        this.pengajuanBaruList = pengajuanBaruList;
        progressDialog.dismiss();
        if (!pengajuanBaruList.isEmpty()) {
            va.setDisplayedChild(1);
            adapter.setPengajuanBaruList(pengajuanBaruList);
        } else {
            va.setDisplayedChild(0);
        }
    }

    @Override
    public void onFailedLoadPengajuanUnsync(String message) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreSubmitPengajuan() {
        holder.getVaDataText().setDisplayedChild(1);
        progressDialog.show();
        progressDialog.setMessage("Submit Pengajuan");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onSuccessSubmitPengajuan(String applicationId) {

        progressDialog.dismiss();
        isSubmitting = false;
        holder.getVaDataText().setDisplayedChild(0);
        action = getString(R.string.action_submit_submission);
        sendLocations();
        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        this.applicationId = applicationId;
        addApplicationId(applicationId);
        submitAttachment(holder);
    }

    @Override
    public void onFailedSubmitPengajuan(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        progressDialog.dismiss();
        holder.getVaDataText().setDisplayedChild(0);
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

    private void addApplicationId(String applicaitonId) {
        int appIdDb;
        if (pengajuanBaru == null) {
            pengajuanBaru = pengajuanBaruList.get(holder.getAdapterPosition());
            appIdDb = pengajuanBaru.getId();
        } else {
            appIdDb = pengajuanBaru.getId();
        }

        try {
            UpdateBuilder builder = databaseService.getPengajuanBaruDao().updateBuilder();
            builder.where().eq("id", appIdDb);
            builder.updateColumnValue("app_id_backend", applicaitonId);
            builder.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG)
                Log.e("Query pengajuan", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    public void submitPengajuan(PengajuanUnsyncAdapter.PengajuanUnsyncVH holder) {
        this.holder = holder;
        if (!isSubmitting) {
            isSubmitting = true;
            pengajuanBaru = pengajuanBaruList.get(holder.getAdapterPosition());
            pengajuanAddPresenter.submitPengajuan(token, pengajuanBaru);
        } else {
            Toast.makeText(getActivity(), "Please finish another application first!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void submitAttachment(PengajuanUnsyncAdapter.PengajuanUnsyncVH holder, int appId) {
        if (BuildConfig.DEBUG) {
            Log.e("submit attachment", "From Adapter");
        }
        List<PengajuanBaru> newlist = new ArrayList<>();
        this.holder = holder;
        String type = "";
        pengajuanBaru = pengajuanBaruList.get(holder.getAdapterPosition());
        try {
            newlist = databaseService.getPengajuanBaruDao().queryBuilder().where().eq("id", appId).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e("Get application Id", "Failed");
            }
            Crashlytics.logException(e);
            return;
        }
        if (newlist != null && !newlist.isEmpty()) {
            applicationId = newlist.get(0).getAppIdBackend();
        } else {
            Crashlytics.log(1, "Sinkron", "List get app id backend kosong");
            return;
        }

        if ("isAssignEdit".equalsIgnoreCase(pengajuanBaru.getIsAssignedit())) {
            type = "edit";
        }
//        attachmentPresenter.submitAttachment(token, applicationId, pengajuanBaru, type);
    }

    public void submitAttachment(PengajuanUnsyncAdapter.PengajuanUnsyncVH holder) {
        if (BuildConfig.DEBUG) {
            Log.e("submit attachment", "From Fragment");
        }
        this.holder = holder;
        String type = "";

        if ("isAssignEdit".equalsIgnoreCase(pengajuanBaru.getIsAssignedit())) {
            type = "edit";
        }
//        attachmentPresenter.submitAttachment(token, applicationId, pengajuanBaru, type);
    }

    @Override
    public void onPreSubmitAttachment() {
        holder.getVaDataImage().setDisplayedChild(1);
        progressDialog.show();
        progressDialog.setMessage("Submit Attachment");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onSuccessSubmitAttachment() {
         holder.getVaDataImage().setDisplayedChild(0);
        progressDialog.dismiss();
        action = getString(R.string.action_submit_attachment);
        sendLocations();
        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        EventBus.getDefault().post(new RefreshPengajuanEvent());
    }

    @Override
    public void onFailedSubmitAttachment(String message) {
        holder.getVaDataImage().setDisplayedChild(0);
        progressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenPengajuanExpired() {
        typeExpired = 1;
        String checktoken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(checktoken);
    }

    @Override
    public void onTokenAttachmentExpired() {
        typeExpired = 2;
        String checktoken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(checktoken);
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.apply();
        if (typeExpired == 1) {
            submitPengajuan(holder);
        } else if (typeExpired == 2) {
            submitAttachment(holder);
        } else if (typeExpired == 3) {
            action = getString(R.string.action_refresh_token);
            sendLocations();
        }
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreCoordinate() {

    }

    @Override
    public void onFailedCoordinate(String massage) {
        Toast.makeText(getContext(), massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenCoordinateExpired() {
        typeExpired = 3;
        String checktoken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(checktoken);
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
    public void onPreLoadPengajuanDraft() {

    }

    @Override
    public void onSuccessLoadPengajuanDraft(MasterFormPengajuan mMasterFormPengajuan, List<TblLokasi> tblLokasiList, List<AssetElektronik> assetElektronikList, List<Attachment> attachmentList, List<MaskingTenor> maskingTenorList, List<MaskingRate> maskingRateList) {
        this.pengajuanBaru = pengajuanBaru;
        pengajuanBaru = pengajuanBaruList.get(holder.getAdapterPosition());
        longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
        latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));
        pengajuanBaru.setSyncAction14("Successful Synchronization");
        pengajuanBaru.setSyncLongitude14(String.valueOf(longitude));
        pengajuanBaru.setSyncLatitude14(String.valueOf(latitude));
    }

    @Override
    public void onFailedLoadPengajuanDraft(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void saveSyncCoordidate(PengajuanUnsyncAdapter.PengajuanUnsyncVH holder, Integer formPengajuanId) {
        this.holder = holder;
        mPengajuanDraftPresenter.getPengajuanBaru(formPengajuanId);
    }
}

