package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.FinancingObjectResponse;
import com.kreditplus.eform.presenter.mvpview.TujuanPembiayaanWGMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TujuanPembiayaanPresenter implements BasePresenter<TujuanPembiayaanWGMvpView>{
    private TujuanPembiayaanWGMvpView mvpView;
    private Call<BaseResponse<FinancingObjectResponse>> callmaster;
    private int count;

    public TujuanPembiayaanPresenter() {
        App.appComponent().inject(this);
    }
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;
    @Override
    public void attachView(TujuanPembiayaanWGMvpView view) {
        mvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        mvpView = null;
    }
    public void getTujuanPembiyaan(String token){
        count = 0;
        mvpView.onPreTujuanPembiayaan();
        callmaster = apiService.getTujuanPembiayaan(token,"FinancingObject");
        callmaster.enqueue(new Callback<BaseResponse<FinancingObjectResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<FinancingObjectResponse>> call, Response<BaseResponse<FinancingObjectResponse>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mvpView.onSuccessTujuanPembiayaan(response.body().getData());
                }else {
                    mvpView.onFailedTujuanPembiayaan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<FinancingObjectResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (mvpView != null) {
                            mvpView.onFailedTujuanPembiayaan(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });

    }
}
