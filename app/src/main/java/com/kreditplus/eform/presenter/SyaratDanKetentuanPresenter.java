package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.SyaratDanKetentuanResponse;
import com.kreditplus.eform.presenter.mvpview.SyaratDanKetentuanMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 01/03/17.
 */

public class SyaratDanKetentuanPresenter implements BasePresenter<SyaratDanKetentuanMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private SyaratDanKetentuanMvpView mSyaratDanKetentuanMvpView;
    private Call<BaseResponse<SyaratDanKetentuanResponse>> callSyarat;
    private int count;

    public SyaratDanKetentuanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SyaratDanKetentuanMvpView view) {
        mSyaratDanKetentuanMvpView = view;
    }

    @Override
    public void detachView() {
        if (callSyarat != null) callSyarat.isCanceled();
        mSyaratDanKetentuanMvpView = null;
    }


    public void syaratLoad(String token) {
        mSyaratDanKetentuanMvpView.onPreLoadSyarat();
        count = 0;
        callSyarat = apiService.syaratLoad(token);
        callSyarat.enqueue(new Callback<BaseResponse<SyaratDanKetentuanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<SyaratDanKetentuanResponse>> call, Response<BaseResponse<SyaratDanKetentuanResponse>> response) {
                if (mSyaratDanKetentuanMvpView != null) {
                    count = 0;
                    if (response.isSuccessful()) {
                        mSyaratDanKetentuanMvpView.onSuccessSyarat(response.body().getData().getSyarats(), response.body().getData().getTidakCancels(),
                                response.body().getData().getFaqObjt());
                    } else if (response.code() == 403) {
                        mSyaratDanKetentuanMvpView.onSyaratTokenExpired();
                    } else {
                        mSyaratDanKetentuanMvpView.onFailedLoadSyarat(errorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SyaratDanKetentuanResponse>> call, Throwable t) {
                if (!callSyarat.isCanceled()) {
                    if (count < 3) {
                        callSyarat.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mSyaratDanKetentuanMvpView != null) {
                            mSyaratDanKetentuanMvpView.onFailedLoadSyarat(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }


}
