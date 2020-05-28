package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.WilayahCabangResponse;
import com.kreditplus.eform.presenter.mvpview.WilayahCabangMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WilayahCabangPresenter implements BasePresenter<WilayahCabangMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private WilayahCabangMvpView wilayahCabangMvpView;
    private Call<BaseResponse<WilayahCabangResponse>> call;
    private int count;

    public WilayahCabangPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(WilayahCabangMvpView view) {
        wilayahCabangMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        wilayahCabangMvpView = null;
    }

    public void GetWilayahCabang(String token, String branchId) {
        count = 0;
        wilayahCabangMvpView.onPreWilayahCabang();
        call = apiService.getWilayahCabang(token, branchId);
        call.enqueue(new Callback<BaseResponse<WilayahCabangResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<WilayahCabangResponse>> call, Response<BaseResponse<WilayahCabangResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    wilayahCabangMvpView.onSuccessWilayahCabang(response.body().getData());
                } else {
                    wilayahCabangMvpView.onFailedWilayahCabang(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<WilayahCabangResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (wilayahCabangMvpView != null) {
                            wilayahCabangMvpView.onFailedWilayahCabang(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }

                }
            }
        });
    }
}

