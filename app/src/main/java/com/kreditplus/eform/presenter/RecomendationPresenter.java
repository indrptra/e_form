package com.kreditplus.eform.presenter;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.Constant;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.Recomendation;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.RecomendationResponse;
import com.kreditplus.eform.model.response.objecthelper.RecomendationObjt;
import com.kreditplus.eform.presenter.mvpview.RecomendationMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nurirppan on 08-Mar-18.
 */

public class RecomendationPresenter implements BasePresenter<RecomendationMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private RecomendationMvpView mRecomendationMvpView;
    private Call<BaseResponse<RecomendationResponse>> call;
    private Call<BaseResponse> callBase;
    private int count;

    public RecomendationPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(RecomendationMvpView view) {
        mRecomendationMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mRecomendationMvpView = null;
    }

    public void formRecomendation(String token) {
        mRecomendationMvpView.onPreRecomendation();
        count = 0;
        call = apiService.recomendationMessage(token);
        call.enqueue(new Callback<BaseResponse<RecomendationResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<RecomendationResponse>> call, Response<BaseResponse<RecomendationResponse>> response) {
                if (mRecomendationMvpView != null) {
                    count = 0;
                    if (response.isSuccessful()) {
                        mRecomendationMvpView.onSuccessRecomendation(response.body().getData());
                    } else if (response.code() == 403) {
                        mRecomendationMvpView.onTokenExpiredRecomendation();
                    } else {
                        mRecomendationMvpView.onFailedRecomendation(errorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RecomendationResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else if (mRecomendationMvpView != null) {
                        mRecomendationMvpView.onFailedRecomendation(errorRetrofitUtil.responseFailedError(t));
                        count = 0;
                    }
                }
            }
        });
    }

    public void checkFpd(String token){
        mRecomendationMvpView.onPreRecomendation();
        count = 0;
        callBase = apiService.CheckFpd(token);
        callBase.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getMeta().isSucceded()){
                        mRecomendationMvpView.onCheckFpd();
                    }else{
                        mRecomendationMvpView.onCheckFpdFailed(response.body().getMeta().getMessage());
                    }
                }else if (response.code() == 401) {
                    mRecomendationMvpView.onCheckFpdFailed("Anda dalam kondisi lock FPD sehingga submit pengajuan tidak dapat dilakukan");
                }else{
                    mRecomendationMvpView.onCheckFpdFailed("Anda dalam kondisi lock FPD sehingga submit pengajuan tidak dapat dilakukan");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                mRecomendationMvpView.onCheckFpdFailed("Anda dalam kondisi lock FPD sehingga submit pengajuan tidak dapat dilakukan");
            }
        });
    }
}
