package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.DetailPerhitunganKendaraanResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;
import com.kreditplus.eform.presenter.mvpview.DetailPerhitunganKendaraanMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPerhitunganKendaraanPresenter implements BasePresenter<DetailPerhitunganKendaraanMvpView> {

    private DetailPerhitunganKendaraanMvpView detailPerhitunganKendaraanMvpView;
    private Call<BaseResponse<DetailPerhitunganKendaraanResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public DetailPerhitunganKendaraanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(DetailPerhitunganKendaraanMvpView view) {
        detailPerhitunganKendaraanMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        detailPerhitunganKendaraanMvpView = null;
    }

    public void GetPerhitunganKendaraan(String token,
                                        String type,            /*psa/non-psa*/
                                        String branch_code,
                                        String market_price,    /*dari BE / confins*/
                                        String down_payment,    /*dari PO*/
                                        String status,          /*new/RO*/
                                        String same_name,       /*1 nama sama / 0 nama beda*/
                                        String tenor,
                                        String provisi_fee,
                                        String admin_fee,
                                        String stnk_fee,
                                        String fiduciary_fee,
                                        String survey_cost,
                                        String notary_fee,
                                        String consumer_loan,
                                        String effective_rate,
                                        final String statSinkron,
                                        String monthly_payment,
                                        String pinjaman) {
        count = 0;
        detailPerhitunganKendaraanMvpView.onPrePerhitunganKendaraan();
        callmaster = apiService.hitDetailPerhitunganKendaraan(token, type,
                branch_code,
                market_price,
                down_payment,
                status,
                same_name,
                tenor,
                provisi_fee,
                admin_fee,
                stnk_fee,
                fiduciary_fee,
                survey_cost,
                notary_fee,
                consumer_loan,
                effective_rate,
                monthly_payment,
                pinjaman);
        callmaster.enqueue(new Callback<BaseResponse<DetailPerhitunganKendaraanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<DetailPerhitunganKendaraanResponse>> call, Response<BaseResponse<DetailPerhitunganKendaraanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    detailPerhitunganKendaraanMvpView.onSuccessPerhitunganKendaraan(response.body().getData(), statSinkron);
                } else {
                    detailPerhitunganKendaraanMvpView.onFailedPerhitunganKendaraan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<DetailPerhitunganKendaraanResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (detailPerhitunganKendaraanMvpView != null) {
                            detailPerhitunganKendaraanMvpView.onFailedPerhitunganKendaraan(errorRetrofitUtil.responseFailedError(t));
                        }
                    }
                }
            }
        });
    }
}
