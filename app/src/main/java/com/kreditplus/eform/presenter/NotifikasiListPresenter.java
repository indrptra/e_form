package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.NotifikasiResponse;
import com.kreditplus.eform.model.response.PengajuanResponse;
import com.kreditplus.eform.presenter.mvpview.NotifikasiListMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanListMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class NotifikasiListPresenter implements BasePresenter<NotifikasiListMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private NotifikasiListMvpView mNotifikasiListMvpView;
    private Call<BaseResponse<NotifikasiResponse>> call;
    private int count;

    public NotifikasiListPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(NotifikasiListMvpView view) {
        mNotifikasiListMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mNotifikasiListMvpView = null;
    }

    public void notifikasiList(String token) {
        mNotifikasiListMvpView.onPreLoadNotifikasi();
        count = 0;
        call = apiService.notifikasiList(token);
        call.enqueue(new Callback<BaseResponse<NotifikasiResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<NotifikasiResponse>> call, Response<BaseResponse<NotifikasiResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mNotifikasiListMvpView.onSuccessLoadNotifikasi(response.body().getData().getNotifikasiList());
                } else if (response.code() == 403) {
                    mNotifikasiListMvpView.onTokenExpired();
                } else {
                    mNotifikasiListMvpView.onFailedLoadNotifikasi(errorRetrofitUtil.
                            parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<NotifikasiResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else if (mNotifikasiListMvpView != null){
                        mNotifikasiListMvpView.onFailedLoadNotifikasi(errorRetrofitUtil.responseFailedError(t));
                        count = 0;
                    }

                }
            }
        });
    }
}
