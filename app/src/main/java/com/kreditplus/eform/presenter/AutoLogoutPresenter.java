package com.kreditplus.eform.presenter;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.LogoutResponse;
import com.kreditplus.eform.presenter.mvpview.AutoLogoutMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 02/06/17.
 */

public class AutoLogoutPresenter implements BasePresenter<AutoLogoutMvpView> {


    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private AutoLogoutMvpView mAutoLogoutMvpView;
    private Call<BaseResponse<LogoutResponse>> call;
    private int count;

    public AutoLogoutPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(AutoLogoutMvpView view) {
        mAutoLogoutMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null)
            call.cancel();
        mAutoLogoutMvpView = null;
    }

    public void expiredLogin(String token, String type){
        mAutoLogoutMvpView.onPreAutomaticLogout();
        count = 0;
        call = mApiService.ExpiredLogin(token,type);
        call.enqueue(new Callback<BaseResponse<LogoutResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<LogoutResponse>> call, Response<BaseResponse<LogoutResponse>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mAutoLogoutMvpView.onSuccessSendAutomaticLogout(response.body().getData().getStatus());
                }else if (response.code() == 403){
                    mAutoLogoutMvpView.onTokenAutomaticLogoutExpired();
                }else{
                    mAutoLogoutMvpView.onFailedSendAutomaticLogout(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LogoutResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else{
                        if (mAutoLogoutMvpView != null){
                            mAutoLogoutMvpView.onFailedSendAutomaticLogout(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                            Crashlytics.logException(t);
                        }
                    }
                }
            }
        });
    }
}
