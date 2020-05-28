package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.PosListDownResponse;
import com.kreditplus.eform.presenter.mvpview.PosKreditvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosKreditPresenter implements BasePresenter<PosKreditvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private PosKreditvpView posMvpView;
    private Call<BaseResponse<PosListDownResponse>> call;
    private int count;

    public PosKreditPresenter() {
        App.appComponent().inject(this);
    }


    @Override
    public void attachView(PosKreditvpView view) {
        posMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        posMvpView = null;
    }

    public void getPosMaster(String token,
                             String branchId) {
        count = 0;
        posMvpView.onPreGetPos();
        call = apiService.getListdownPos(token, "POSMaster", branchId);
        call.enqueue(new Callback<BaseResponse<PosListDownResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<PosListDownResponse>> call, Response<BaseResponse<PosListDownResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    posMvpView.onSuccessGetPos(response.body().getData());
                } else {
                    posMvpView.onFailedGetPos(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PosListDownResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (posMvpView != null) {
                            posMvpView.onFailedGetPos(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }
}
