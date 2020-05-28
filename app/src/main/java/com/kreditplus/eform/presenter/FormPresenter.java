package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.FormResponse;
import com.kreditplus.eform.model.response.NotifikasiResponse;
import com.kreditplus.eform.presenter.mvpview.FormShowHideMvpView;
import com.kreditplus.eform.presenter.mvpview.NotifikasiListMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nurirppan on 04-Jan-18.
 */

public class FormPresenter implements BasePresenter<FormShowHideMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private FormShowHideMvpView mFormShowHideMvpView;
    private Call<BaseResponse<FormResponse>> call;
    private int count;

    public FormPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(FormShowHideMvpView view) {
        mFormShowHideMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mFormShowHideMvpView = null;
    }

    public void formShowHide(String token) {
        mFormShowHideMvpView.onPreLoadFromShowHide();
        count = 0;
        call = apiService.formShowHide(token);
        call.enqueue(new Callback<BaseResponse<FormResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<FormResponse>> call, Response<BaseResponse<FormResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mFormShowHideMvpView.onSuccessLoadFromShowHide(response.body().getData().getFormDinamic());
                } else if (response.code() == 403) {
                    mFormShowHideMvpView.onTokenExpiredFromShowHide();
                } else {
                    mFormShowHideMvpView.onFailedLoadFromShowHide(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<FormResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else if (mFormShowHideMvpView != null) {
                        mFormShowHideMvpView.onFailedLoadFromShowHide(errorRetrofitUtil.responseFailedError(t));
                        count = 0;
                    }

                }
            }
        });

    }
}

