package com.kreditplus.eform.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.AttachmentMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class AttachmentPresenter implements BasePresenter<AttachmentMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private AttachmentMvpView mAttachmentMvpView;
    private Call<BaseResponse<Object>> call;
    private File compres;

    public AttachmentPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(AttachmentMvpView view) {
        mAttachmentMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mAttachmentMvpView = null;
    }

    public void submitAttachment(String token, String applicationId, final PengajuanBaru pengajuanBaru, final MasterFormPengajuan masterFormPengajuan, String type, String offeringType) {
        mAttachmentMvpView.onPreSubmitAttachment();
        List<Attachment> attachments = new ArrayList<>();
        try {
            attachments = databaseService.getAttachmentDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Query attachment", String.valueOf(e));
            Crashlytics.logException(e);
        }

        List<MultipartBody.Part> attachmentPart = new ArrayList<>();
        for (int i = 0; i < attachments.size(); i++) {
            File fileTmp1 = FileUtils.getFile(attachments.get(i).getPath());
            File fileTmp2 = FileUtils.getFile(attachments.get(i).getPath2());

            //di urutkan berdasarkan key
            try {
                compres = new Compressor(mAttachmentMvpView.getContext())
                        .setMaxHeight(720)
                        .setMaxWidth(1080)
                        .setQuality(25)
                        .compressToFile(fileTmp1 == null ? fileTmp2 : fileTmp1);
            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.e("Gagal Kompress", e.getMessage());
                Crashlytics.logException(e);
                mAttachmentMvpView.onFailedSubmitAttachment(mAttachmentMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
                return;
            }
            attachmentPart.add(prepareFilePart("Attachment[" + attachments.get(i).getKey() + "]", compres));
        }
        call = apiService.addAttachment(token, applicationId, type, attachmentPart, offeringType);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.isSuccessful()) {
                    try {
                        UpdateBuilder<MasterFormPengajuan, Integer> updateBuilder = databaseService.getMasterFormPengajuanDao().updateBuilder();
                        updateBuilder.where().eq("id", masterFormPengajuan.getId());
                        updateBuilder.updateColumnValue("status_sync", 2);
                        updateBuilder.update();
                    } catch (SQLException e) {
                        if (BuildConfig.DEBUG)
                            Log.e("Update status pengajuan", String.valueOf(e));
                        Crashlytics.logException(e);
                    }
                    mAttachmentMvpView.onSuccessSubmitAttachment();
                } else if (response.code() == 403) {
                    mAttachmentMvpView.onTokenAttachmentExpired();
                } else {
                    mAttachmentMvpView.onFailedSubmitAttachment(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (mAttachmentMvpView != null)
                    mAttachmentMvpView.onFailedSubmitAttachment(errorRetrofitUtil.responseFailedError(t));
                Crashlytics.logException(t);
            }
        });
    }

    public void retakePhoto(String token, String applicationId, HashMap<Integer, File> map, String offeringType, boolean isUncompleted) {
        mAttachmentMvpView.onPreSubmitAttachment();
        List<MultipartBody.Part> attachmentPart = new ArrayList<>();
        String type = "";

        for (Object o : map.keySet()) {
            int i = (int) o;
            File fileTmp1 = FileUtils.getFile(map.get(i).getAbsolutePath());
            File fileTmp2 = FileUtils.getFile(map.get(i).getPath());

            try {
                compres = new Compressor(mAttachmentMvpView.getContext())
                        .setMaxHeight(720)
                        .setMaxWidth(1080)
                        .setQuality(25)
                        .compressToFile(fileTmp1 == null ? fileTmp2 : fileTmp1);
            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.e("Gagal Kompress", e.getMessage());
                Crashlytics.logException(e);
                mAttachmentMvpView.onFailedSubmitAttachment(mAttachmentMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
                return;
            }
            attachmentPart.add(prepareFilePart("Attachment[" + i + "]", compres));
        }

        if(!isUncompleted) type = "retake";
        call = apiService.addAttachment(token, applicationId, type, attachmentPart, offeringType);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.isSuccessful()) {
                    mAttachmentMvpView.onSuccessSubmitAttachment();
                } else if (response.code() == 403) {
                    mAttachmentMvpView.onTokenAttachmentExpired();
                } else {
                    mAttachmentMvpView.onFailedSubmitAttachment(errorRetrofitUtil.
                            parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (mAttachmentMvpView != null)
                        mAttachmentMvpView.onFailedSubmitAttachment(errorRetrofitUtil.responseFailedError(t));
                }
            }
        });
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        RequestBody requestFile = Util.toImageRequestBody(file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
