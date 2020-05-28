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
import com.kreditplus.eform.model.ArrayList.AttachmentArrayList;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.AttachmentKendaraanMvpView;
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

public class AttachmentKendaraanPresenter implements BasePresenter<AttachmentKendaraanMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private AttachmentKendaraanMvpView mAttachmentKendaraanMvpView;
    private Call<BaseResponse<Object>> call;
    private File compres;

    public AttachmentKendaraanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(AttachmentKendaraanMvpView view) {
        mAttachmentKendaraanMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mAttachmentKendaraanMvpView = null;
    }

    public void SyncAttachmentKendaraan(String token, String applicationId, List<AttachmentArrayList> attachments, String type,String offeringType) {
        mAttachmentKendaraanMvpView.onPreSubmitAttachment();

        List<MultipartBody.Part> attachmentPart = new ArrayList<>();
        for (int i = 0; i < attachments.size(); i++) {
            File fileTmp1 = FileUtils.getFile(attachments.get(i).getPath());
            File fileTmp2 = FileUtils.getFile(attachments.get(i).getPath2());

            //di urutkan berdasarkan key
            try {
                compres = new Compressor(mAttachmentKendaraanMvpView.getContext())
                        .setMaxHeight(720)
                        .setMaxWidth(1080)
                        .setQuality(25)
                        .compressToFile(fileTmp1 == null ? fileTmp2 : fileTmp1);
            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.e("Gagal Kompress", e.getMessage());
                Crashlytics.logException(e);
                mAttachmentKendaraanMvpView.onFailedSubmitAttachment(mAttachmentKendaraanMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
                return;
            }
            attachmentPart.add(prepareFilePart("Attachment[" + attachments.get(i).getKey() + "]", compres));
        }
        call = apiService.syncAttachmentKendaraan(token, applicationId, type, attachmentPart, offeringType);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.isSuccessful()) {
                    mAttachmentKendaraanMvpView.onSuccessSubmitAttachment();
                } else {
                    mAttachmentKendaraanMvpView.onFailedSubmitAttachment(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (mAttachmentKendaraanMvpView != null)
                    mAttachmentKendaraanMvpView.onFailedSubmitAttachment(errorRetrofitUtil.responseFailedError(t));
                Crashlytics.logException(t);
            }
        });
    }

    public void SyncAttachmentElc(String token, String applicationId, List<AttachmentArrayList> attachments, String type) {
        mAttachmentKendaraanMvpView.onPreSubmitAttachment();

        List<MultipartBody.Part> attachmentPart = new ArrayList<>();
        for (int i = 0; i < attachments.size(); i++) {
            File fileTmp1 = FileUtils.getFile(attachments.get(i).getPath());
            File fileTmp2 = FileUtils.getFile(attachments.get(i).getPath2());

            //di urutkan berdasarkan key
            try {
                compres = new Compressor(mAttachmentKendaraanMvpView.getContext())
                        .setMaxHeight(720)
                        .setMaxWidth(1080)
                        .setQuality(25)
                        .compressToFile(fileTmp1 == null ? fileTmp2 : fileTmp1);
            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.e("Gagal Kompress", e.getMessage());
                Crashlytics.logException(e);
                mAttachmentKendaraanMvpView.onFailedSubmitAttachment(mAttachmentKendaraanMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
                return;
            }
            attachmentPart.add(prepareFilePart("Attachment[" + attachments.get(i).getKey() + "]", compres));
        }
        call = apiService.syncAttachmentElektronik(token, applicationId, type, attachmentPart);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (mAttachmentKendaraanMvpView != null) {
                    if (response.isSuccessful()) {
                        mAttachmentKendaraanMvpView.onSuccessSubmitAttachment();
                    } else {
                        mAttachmentKendaraanMvpView.onFailedSubmitAttachment(errorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (mAttachmentKendaraanMvpView != null)
                    mAttachmentKendaraanMvpView.onFailedSubmitAttachment(errorRetrofitUtil.responseFailedError(t));
                Crashlytics.logException(t);
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

