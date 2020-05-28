package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.JenisPembiayaanResponse;
import com.kreditplus.eform.presenter.mvpview.JenisPembiayaanMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisPembiayaanPresenter implements BasePresenter<JenisPembiayaanMvpView> {

    private JenisPembiayaanMvpView jenisPembiayaanMvpView;
    private Call<BaseResponse<JenisPembiayaanResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public JenisPembiayaanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(JenisPembiayaanMvpView view) {
        jenisPembiayaanMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        jenisPembiayaanMvpView = null;
    }

    public void GetJenisPembiayaan(String token, String tipePengajuan) {
        count = 0;
        jenisPembiayaanMvpView.onPreJenisPembiayaan();
        callmaster = apiService.getJenisPembiayaan(token, tipePengajuan);
        callmaster.enqueue(new Callback<BaseResponse<JenisPembiayaanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<JenisPembiayaanResponse>> call, Response<BaseResponse<JenisPembiayaanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    jenisPembiayaanMvpView.onSuccessJenisPembiayaan(response.body().getData());
                } else {
                    jenisPembiayaanMvpView.onFailedJenisPembiayaan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<JenisPembiayaanResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (jenisPembiayaanMvpView != null) {
                            jenisPembiayaanMvpView.onFailedJenisPembiayaan(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}

