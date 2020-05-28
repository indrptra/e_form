package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.TahunProduksiResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;
import com.kreditplus.eform.presenter.mvpview.HargaAgunanElcMvpView;
import com.kreditplus.eform.presenter.mvpview.HargaAgunanKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.TahunProduksiKendaraanMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.view.IndonesianCurrencyEditText;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HargaAgunanElcPresenter implements BasePresenter<HargaAgunanElcMvpView> {

    private HargaAgunanElcMvpView hargaAgunanElcMvpView;
    private Call<BaseResponse<HargaAgunanResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public HargaAgunanElcPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(HargaAgunanElcMvpView view) {
        hargaAgunanElcMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        hargaAgunanElcMvpView = null;
    }

    public void GetHargaAgunan(String token, String branchID, String assetCode, String manufacturingYear, final IndonesianCurrencyEditText edtPriceAsset) {
        count = 0;
        hargaAgunanElcMvpView.onPreHargaAgunan();
        callmaster = apiService.hargaAgunanKendaraan(token, branchID, assetCode, manufacturingYear);
        callmaster.enqueue(new Callback<BaseResponse<HargaAgunanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<HargaAgunanResponse>> call, Response<BaseResponse<HargaAgunanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    hargaAgunanElcMvpView.onSuccessHargaAgunan(response.body().getData(), edtPriceAsset);
                } else {
                    hargaAgunanElcMvpView.onFailedHargaAgunan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<HargaAgunanResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (hargaAgunanElcMvpView != null) {
                            hargaAgunanElcMvpView.onFailedHargaAgunan(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }
}
