package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.TahunProduksiResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;
import com.kreditplus.eform.presenter.mvpview.HargaAgunanKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.TahunProduksiKendaraanMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HargaAgunanKendaraanPresenter implements BasePresenter<HargaAgunanKendaraanMvpView> {

    private HargaAgunanKendaraanMvpView hargaAgunanKendaraanMvpView;
    private Call<BaseResponse<HargaAgunanResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public HargaAgunanKendaraanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(HargaAgunanKendaraanMvpView view) {
        hargaAgunanKendaraanMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        hargaAgunanKendaraanMvpView = null;
    }

    public void GetHargaAgunanKendaraan(String token, String branchID, String assetCode, String manufacturingYear) {
        count = 0;
        hargaAgunanKendaraanMvpView.onPreHargaAgunanKendaraan();
        callmaster = apiService.hargaAgunanKendaraan(token, branchID, assetCode, manufacturingYear);
        callmaster.enqueue(new Callback<BaseResponse<HargaAgunanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<HargaAgunanResponse>> call, Response<BaseResponse<HargaAgunanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    hargaAgunanKendaraanMvpView.onSuccessHargaAgunanKendaraan(response.body().getData());
                } else {
                    hargaAgunanKendaraanMvpView.onFailedHargaAgunanKendaraan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<HargaAgunanResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (hargaAgunanKendaraanMvpView != null) {
                            hargaAgunanKendaraanMvpView.onFailedHargaAgunanKendaraan(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }
}
