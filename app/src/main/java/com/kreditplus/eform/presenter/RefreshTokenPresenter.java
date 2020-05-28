package com.kreditplus.eform.presenter;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.LoginResponse;
import com.kreditplus.eform.model.response.RefreshTokenResponse;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class RefreshTokenPresenter implements BasePresenter<RefreshTokenMvpView> {

    @Inject
    SharedPreferences.Editor editor;

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private RefreshTokenMvpView mRefreshTokenMvpView;
    private Call<BaseResponse<RefreshTokenResponse>> call;
    private int count;

    public RefreshTokenPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(RefreshTokenMvpView view) {
        mRefreshTokenMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mRefreshTokenMvpView = null;
    }

    @Nullable
    public void refreshToken(String token) {
        mRefreshTokenMvpView.onPreRefreshToken();
        count = 0;
        call = mApiService.refreshToken(token);
        call.enqueue(new Callback<BaseResponse<RefreshTokenResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<RefreshTokenResponse>> call, Response<BaseResponse<RefreshTokenResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    editor.putString(mRefreshTokenMvpView.getContext().getResources().
                            getString(R.string.sharedpref_token), response.body().getData().getToken());
                    editor.commit();
                    mRefreshTokenMvpView.onSuccessRefreshToken(response.body().getData().getToken());
                } else {
                    mRefreshTokenMvpView.onFailedRefreshToken(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RefreshTokenResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count +=1;
                    }else{
                        if (mRefreshTokenMvpView != null) {
                            mRefreshTokenMvpView.onFailedRefreshToken(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }
                }
            }
        });
    }


}
