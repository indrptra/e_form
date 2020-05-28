package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.JenisKendaraanResponse;
import com.kreditplus.eform.presenter.mvpview.JenisKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.JenisKendaraanMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisKendaraanPresenter implements BasePresenter<JenisKendaraanMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private JenisKendaraanMvpView jenisKendaraanMvpView;
    private Call<BaseResponse<JenisKendaraanResponse>> call;
    private int count;

    public JenisKendaraanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(JenisKendaraanMvpView view) {
        jenisKendaraanMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        jenisKendaraanMvpView = null;
    }

    public void HitJenisKendaraan(String token,String assetTypeID) {
        count = 0;
        jenisKendaraanMvpView.onPreJenisKendaraan();
        call = apiService.getJenisKendaraan(token, assetTypeID);
        call.enqueue(new Callback<BaseResponse<JenisKendaraanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<JenisKendaraanResponse>> call, Response<BaseResponse<JenisKendaraanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    jenisKendaraanMvpView.onSuccessJenisKendaraan(response.body().getData());
                } else {
                    jenisKendaraanMvpView.onFailedJenisKendaraan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<JenisKendaraanResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (jenisKendaraanMvpView != null) {
                            jenisKendaraanMvpView.onFailedJenisKendaraan(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
