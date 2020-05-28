package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.ReferalCodeResponse;
import com.kreditplus.eform.presenter.mvpview.ReferalCodeMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetReferalCodePresenter implements BasePresenter<ReferalCodeMvpView> {

    private ReferalCodeMvpView referalCodeMvpView;
    private Call<BaseResponse<ReferalCodeResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public GetReferalCodePresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(ReferalCodeMvpView view) {
        referalCodeMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        referalCodeMvpView = null;
    }

    public void GetReferalCode(String token, String tipePengajuan, String supplierId) {
        count = 0;
        referalCodeMvpView.onPreReferalCode();
        callmaster = apiService.getReferalCode(token, tipePengajuan, supplierId);
        callmaster.enqueue(new Callback<BaseResponse<ReferalCodeResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ReferalCodeResponse>> call, Response<BaseResponse<ReferalCodeResponse>> response) {
                if (response.code() == 200){
                    referalCodeMvpView.onSuccessReferalCode(response.body().getData());
                }else{
                    referalCodeMvpView.onFailedReferalCode("Data Refferal tidak tersedia");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ReferalCodeResponse>> call, Throwable t) {
                referalCodeMvpView.onFailedReferalCode(t.getMessage());
            }
        });
    }
}



