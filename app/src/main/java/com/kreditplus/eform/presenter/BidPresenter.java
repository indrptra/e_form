package com.kreditplus.eform.presenter;

import android.content.SharedPreferences;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.BidResponse;
import com.kreditplus.eform.model.response.LoginResponse;
import com.kreditplus.eform.presenter.mvpview.BidMvpView;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class BidPresenter implements BasePresenter<BidMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private BidMvpView mBidMvpView;
    private Call<BaseResponse<BidResponse>> call;

    public BidPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(BidMvpView view) {
        mBidMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mBidMvpView = null;
    }

    public void bid(String token, String appID, int isAccept) {
        mBidMvpView.onPreBid();
        call = mApiService.bid(token, appID, isAccept);
        call.enqueue(new Callback<BaseResponse<BidResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<BidResponse>> call, Response<BaseResponse<BidResponse>> response) {
                if (response.code() == 201) {
                    mBidMvpView.onSuccessBid(response.body().getData().getApplicationID());
                } else if (response.code() == 422) {
                    mBidMvpView.onFailedBid();
                } else if (response.code() == 403) {
                    mBidMvpView.onTokenExpired();
                } else {
                    mBidMvpView.onErrorBid(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BidResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (mBidMvpView != null)
                        mBidMvpView.onErrorBid(mErrorRetrofitUtil.responseFailedError(t));
                }
            }
        });
    }
}
