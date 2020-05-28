package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.CheckVersionResponse;
import com.kreditplus.eform.presenter.mvpview.CheckVersionMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 15/06/17.
 */

public class CheckVersionPresenter implements BasePresenter<CheckVersionMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private CheckVersionMvpView mCheckVersionMvpView;
    private Call<BaseResponse<CheckVersionResponse>> call;
    private int count;

    public CheckVersionPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(CheckVersionMvpView view) {
        mCheckVersionMvpView = view;
    }

    @Override
    public void detachView() {
        if (call !=null) {
            call.cancel();
        }
        mCheckVersionMvpView = null;
    }

    public void checkingVersion(String token, String version){
        mCheckVersionMvpView.onPreCheckVersion();
        count = 0;
        call = mApiService.versionCheck(token,version);
        call.enqueue(new Callback<BaseResponse<CheckVersionResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<CheckVersionResponse>> call, Response<BaseResponse<CheckVersionResponse>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mCheckVersionMvpView.onSuccessCheckVersion(response.body().getMeta().getMessage());
                }else if (response.code() == 403){
                    mCheckVersionMvpView.onTokenCheckVersionExpired();
                }else {
                    mCheckVersionMvpView.onFailedCheckVersion(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CheckVersionResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else{
                        if (mCheckVersionMvpView != null){
                            mCheckVersionMvpView.onFailedCheckVersion(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }
                }
            }
        });

    }
}
