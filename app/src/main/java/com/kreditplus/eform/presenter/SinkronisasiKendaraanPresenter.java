package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.AddApplicationResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.objecthelper.BodySyncApplication;
import com.kreditplus.eform.presenter.mvpview.SyncKendaraanMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinkronisasiKendaraanPresenter implements BasePresenter<SyncKendaraanMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private SyncKendaraanMvpView mSyncKendaraanMvpView;
    private Call<BaseResponse<AddApplicationResponse>> call;

    public SinkronisasiKendaraanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SyncKendaraanMvpView view) {
        mSyncKendaraanMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mSyncKendaraanMvpView = null;
    }
//    hitSaveDraft

    public void SyncKendaraan(String token, Map<String, RequestBody> map, final String statusSinkron, String idAssignEdit) {
        mSyncKendaraanMvpView.onPreSynchKendaraan();
        if (statusSinkron.equalsIgnoreCase("1")) {
            call = apiService.syncKendaraan(token, map); /*sinkron pengajuan baru*/
        } else if (statusSinkron.equalsIgnoreCase("2")) {
            call = apiService.addApplicationEdit(token, map, idAssignEdit); /*sinkron assign edit*/
        } else {
            call = apiService.hitSaveDraft(token, map); /*save draft*/
        }

        call.enqueue(new Callback<BaseResponse<AddApplicationResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AddApplicationResponse>> call, Response<BaseResponse<AddApplicationResponse>> response) {
                if (mSyncKendaraanMvpView != null) {
                    if (response.isSuccessful()) {
                        mSyncKendaraanMvpView.onSuccessSynchKendaraan(response.body().getData().getApplicationId(), statusSinkron);
                    } else {
                        mSyncKendaraanMvpView.onFailedSynchKendaraan(errorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AddApplicationResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (mSyncKendaraanMvpView != null)
                        mSyncKendaraanMvpView.onFailedSynchKendaraan(errorRetrofitUtil.responseFailedError(t));
                }
            }
        });
    }
}
