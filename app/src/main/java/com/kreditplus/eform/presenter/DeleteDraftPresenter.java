package com.kreditplus.eform.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.ArrayList.AttachmentArrayList;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.SupplierResponse;
import com.kreditplus.eform.presenter.mvpview.DeleteDraftMvpView;
import com.kreditplus.eform.presenter.mvpview.DeleteDraftMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteDraftPresenter implements BasePresenter<DeleteDraftMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private DeleteDraftMvpView mDeleteDraftMvpView;
    private Call<BaseResponse<Object>> call;

    public DeleteDraftPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(DeleteDraftMvpView view) {
        mDeleteDraftMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mDeleteDraftMvpView = null;
    }

    public void DeleteDraft(String token, String id) {
        mDeleteDraftMvpView.onPreDeleteDraft();
        call = apiService.getDeleteDraft(token, id);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.isSuccessful()) {
                    mDeleteDraftMvpView.onSuccessDeleteDraft();
                } else {
                    mDeleteDraftMvpView.onFailedDeleteDraft(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (mDeleteDraftMvpView != null)
                    mDeleteDraftMvpView.onFailedDeleteDraft(errorRetrofitUtil.responseFailedError(t));
                Crashlytics.logException(t);
            }
        });
    }
}

