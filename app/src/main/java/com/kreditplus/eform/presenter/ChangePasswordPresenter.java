package com.kreditplus.eform.presenter;

import android.content.SharedPreferences;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.ChangePasswordMVPView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daniel on 8/12/2016.
 */
public class ChangePasswordPresenter implements BasePresenter<ChangePasswordMVPView> {

    @Inject
    SharedPreferences.Editor editor;

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private ChangePasswordMVPView mChangePasswordMVPView;
    private Call<BaseResponse<Object>> call;


    public ChangePasswordPresenter() {
        App.appComponent().inject(this);
    }


    @Override
    public void attachView(ChangePasswordMVPView view) {
        mChangePasswordMVPView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mChangePasswordMVPView = null;
    }

    public void changePassword(String token, String oldPassword, String newPassword) {
        mChangePasswordMVPView.onPreChangePassword();
        call = mApiService.changePassword(token, oldPassword, newPassword);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<Object> changePasswordResponse = response.body();
                    mChangePasswordMVPView.onSuccessChangePassword(changePasswordResponse.getMeta().getMessage());
                } else if (response.code() == 403) {
                    mChangePasswordMVPView.onTokenExpired();
                } else {
                    mChangePasswordMVPView.onFailedChangePassword(mErrorRetrofitUtil.parseError(response));
                }
            }


            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (mChangePasswordMVPView != null)
                        mChangePasswordMVPView.onFailedChangePassword(mErrorRetrofitUtil.responseFailedError(t));
                }
            }
        });
    }

    public boolean validateNewPAssword(String newPassword, String confirmNewPassword) {
        return newPassword.equals(confirmNewPassword);
    }
}
