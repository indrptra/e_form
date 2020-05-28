package com.kreditplus.eform.presenter;

import android.content.Intent;
import android.content.SharedPreferences;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.SigninActivity;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.LoginResponse;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.presenter.mvpview.SignoutMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import java.sql.SQLException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class SignoutPresenter implements BasePresenter<SignoutMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private SignoutMvpView mSignoutMvpView;
    private Call<BaseResponse<Object>> call;
    private int count;

    public SignoutPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SignoutMvpView view) {
        mSignoutMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mSignoutMvpView = null;
    }

    public void signout(String token) {
        if (mSignoutMvpView != null) {
            mSignoutMvpView.onPreSignout();
        }
        count = 0;
        call = mApiService.logout(token);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mSignoutMvpView.onSuccessSignout();
                } else if (response.code() == 403) {
                    mSignoutMvpView.onTokenExpired();
                } else {
                    mSignoutMvpView.onFailedSignout(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mSignoutMvpView != null) {
                            mSignoutMvpView.onFailedSignout(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });

    }
}
