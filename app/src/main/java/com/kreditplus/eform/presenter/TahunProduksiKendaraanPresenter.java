package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.TahunProduksiResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.TahunProduksiKendaraanMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TahunProduksiKendaraanPresenter implements BasePresenter<TahunProduksiKendaraanMvpView> {

    private TahunProduksiKendaraanMvpView tahunProduksiKendaraanMvpView;
    private Call<BaseResponse<TahunProduksiResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public TahunProduksiKendaraanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(TahunProduksiKendaraanMvpView view) {
        tahunProduksiKendaraanMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        tahunProduksiKendaraanMvpView = null;
    }

    public void GetTahunKendaraan(String token, String assetCode, String branchID) {
        count = 0;
        tahunProduksiKendaraanMvpView.onPreTahunProduksiKendaraan();
        callmaster = apiService.tahunProduksiKendaraan(token, assetCode, branchID);
        callmaster.enqueue(new Callback<BaseResponse<TahunProduksiResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<TahunProduksiResponse>> call, Response<BaseResponse<TahunProduksiResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    tahunProduksiKendaraanMvpView.onSuccessTahunProduksiKendaraan(response.body().getData());
                } else {
                    tahunProduksiKendaraanMvpView.onFailedTahunProduksiKendaraan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<TahunProduksiResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (tahunProduksiKendaraanMvpView != null) {
                            tahunProduksiKendaraanMvpView.onFailedTahunProduksiKendaraan(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });

    }
}
