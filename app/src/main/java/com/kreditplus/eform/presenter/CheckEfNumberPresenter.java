package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.CheckEfNumberResponse;
import com.kreditplus.eform.presenter.mvpview.CheckEfNumberMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import java.sql.SQLException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckEfNumberPresenter implements BasePresenter<CheckEfNumberMvpView> {
    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private CheckEfNumberMvpView mCheckEfNumberMvpView;
    private Call<BaseResponse<CheckEfNumberResponse>> call;
    private int count;

    public CheckEfNumberPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(CheckEfNumberMvpView view) {
        mCheckEfNumberMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mCheckEfNumberMvpView = null;
    }

    public void efNumber(String token, String efNumber, final MasterFormPengajuan masterFormPengajuan) {
        mCheckEfNumberMvpView.onPreCheckEFNumber();
        count = 0;
//        efNumber = "EFM11403201906270957";
        call = mApiService.checkEfNumber(token, efNumber);
        call.enqueue(new Callback<BaseResponse<CheckEfNumberResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<CheckEfNumberResponse>> call, Response<BaseResponse<CheckEfNumberResponse>> response) {
                if (mCheckEfNumberMvpView != null) {
                    count = 0;
                    if (response.isSuccessful()) {
                        if (response.body().getMeta().getCode().equals("200")) {
                            mCheckEfNumberMvpView.onSuccessCheckEFNumber(response.body().getData(), masterFormPengajuan);
                        } else if (response.body().getMeta().getCode().equals("403")) {
                            mCheckEfNumberMvpView.onTokenCheckEFNumber();
                        } else {
                            mCheckEfNumberMvpView.onFailedCheckEFNumber(mErrorRetrofitUtil.parseError(response));
                        }
                    } else if (response.code() == 403) {
                        mCheckEfNumberMvpView.onTokenCheckEFNumber();
                    } else {
                        mCheckEfNumberMvpView.onFailedCheckEFNumber(mErrorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CheckEfNumberResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mCheckEfNumberMvpView != null) {
                            mCheckEfNumberMvpView.onFailedCheckEFNumber(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
