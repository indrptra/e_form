package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.RateResponse;
import com.kreditplus.eform.presenter.mvpview.ReturnRateMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 07/08/17.
 */

public class ReturnRatePresenter implements BasePresenter<ReturnRateMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private ReturnRateMvpView mReturnRateMvpView;
    private Call<BaseResponse<RateResponse>> call;
    private int count;

    public ReturnRatePresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(ReturnRateMvpView view) {
        mReturnRateMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) {
            call.cancel();
        }
        mReturnRateMvpView = null;
    }

    public void returnRateData(String token){
        count = 0;
        mReturnRateMvpView.onPreLoadRaturnRate();
        call = apiService.retrunRateData(token);
        call.enqueue(new Callback<BaseResponse<RateResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<RateResponse>> call, Response<BaseResponse<RateResponse>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mReturnRateMvpView.onSuccessRaturnRate(response.body().getData().getRate(),response.body().getData().getPenggunaan(),
                            response.body().getData().getDate());
                }else if (response.code() == 403){
                    mReturnRateMvpView.onReturnRateTokenExpired();
                }else {
                    mReturnRateMvpView.onFailedReturnRate(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RateResponse>> call, Throwable t) {
                if (mReturnRateMvpView != null){
                    if (count < 3){
                        count+= 1;
                        call.clone().enqueue(this);
                    }else {
                        mReturnRateMvpView.onFailedReturnRate(errorRetrofitUtil.responseFailedError(t));
                    }
                }
            }
        });
    }
}
