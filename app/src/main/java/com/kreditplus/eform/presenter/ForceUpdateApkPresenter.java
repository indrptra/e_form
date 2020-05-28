package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.LoginResponse;
import com.kreditplus.eform.model.response.VersionCodeResponse;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.presenter.mvpview.VersionCodeMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForceUpdateApkPresenter implements BasePresenter<VersionCodeMvpView> {


    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private VersionCodeMvpView mVersionCodeMvpView;
    private Call<BaseResponse<VersionCodeResponse>> call;
    private int count;

    public ForceUpdateApkPresenter() {
        App.appComponent().inject(this);
    }


    @Override
    public void attachView(VersionCodeMvpView view) {mVersionCodeMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mVersionCodeMvpView = null;
    }

    public void CheckVersionAPk(String versionCode) {
        mVersionCodeMvpView.onPreCheckVersionCode();
        count = 0;
        call = mApiService.checkVersionCode(versionCode);
        call.enqueue(new Callback<BaseResponse<VersionCodeResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<VersionCodeResponse>> call, Response<BaseResponse<VersionCodeResponse>> response) {
                if (mVersionCodeMvpView != null) {
                    count = 0;
                    if (response.isSuccessful()) {
                        mVersionCodeMvpView.onSuccessCheckVersionCode(response.body().getData());
                    } else {
                        mVersionCodeMvpView.onFailedCheckVersionCode(mErrorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<VersionCodeResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mVersionCodeMvpView != null) {
                            mVersionCodeMvpView.onFailedCheckVersionCode(mErrorRetrofitUtil.responseFailedError(t));
                        }
                    }
                }
            }
        });
    }
}
