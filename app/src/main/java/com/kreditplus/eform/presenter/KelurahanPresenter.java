package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.KelurahanResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.KelurahanMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelurahanPresenter implements BasePresenter<KelurahanMvpView> {

    private KelurahanMvpView kelurahanMvpView;
    private Call<BaseResponse<KelurahanResponse>> callmaster;
    private int count;

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    public KelurahanPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(KelurahanMvpView view) {
        kelurahanMvpView = view;
    }

    @Override
    public void detachView() {
        if (callmaster != null) callmaster.cancel();
        kelurahanMvpView = null;
    }

    public void GetKelurahanFilter(final String status, String token, String value) {
        count = 0;
        kelurahanMvpView.onPreKelurahan();
        callmaster = apiService.getSearchKelurahan(token, value);
        callmaster.enqueue(new Callback<BaseResponse<KelurahanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<KelurahanResponse>> call, Response<BaseResponse<KelurahanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    kelurahanMvpView.onSuccessKelurahan(status, response.body().getData());
                } else {
                    kelurahanMvpView.onFailedKelurahan(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<KelurahanResponse>> call, Throwable t) {
                if (!callmaster.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (kelurahanMvpView != null) {
                            kelurahanMvpView.onFailedKelurahan(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}
