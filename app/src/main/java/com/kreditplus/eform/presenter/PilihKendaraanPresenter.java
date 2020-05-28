package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.MerkKendaraanResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.MerkKendaraanMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihKendaraanPresenter implements BasePresenter<MerkKendaraanMvpView> {

    private MerkKendaraanMvpView merkKendaraanMvpView;
    private Call<BaseResponse<MerkKendaraanResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public PilihKendaraanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(MerkKendaraanMvpView view) {
        merkKendaraanMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        merkKendaraanMvpView = null;
    }

    public void GetPilihKendaraan(String token, String assetTypeID, final String assetLevel, String category, final String merk, String branchID) {
        count = 0;
        merkKendaraanMvpView.onPreMerkKendaraan();
        callmaster = apiService.pilihKendaraan(token, assetTypeID, assetLevel, category, merk, branchID);
        callmaster.enqueue(new Callback<BaseResponse<MerkKendaraanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<MerkKendaraanResponse>> call, Response<BaseResponse<MerkKendaraanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    if (merk.isEmpty())
                        merkKendaraanMvpView.onSuccessMerkKendaraan(response.body().getData(), merk);
                    else
                        merkKendaraanMvpView.onSuccessTipeKendaraan(response.body().getData(), merk);
                } else {
                    merkKendaraanMvpView.onFailedMerkKendaraan(errorRetrofitUtil.parseError(response), assetLevel);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MerkKendaraanResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (merkKendaraanMvpView != null) {
                            merkKendaraanMvpView.onFailedMerkKendaraan(errorRetrofitUtil.responseFailedError(t), assetLevel);
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
