package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.SaldoKreditmuResponse;
import com.kreditplus.eform.presenter.mvpview.SaldoKreditmuMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 02/08/17.
 */

public class SaldoKreditmuPresenter implements BasePresenter<SaldoKreditmuMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SaldoKreditmuMvpView mSaldoKreditmuMvpView;
    private Call<BaseResponse<SaldoKreditmuResponse>> call;
    private int count;

    public SaldoKreditmuPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SaldoKreditmuMvpView view) {
        mSaldoKreditmuMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) {
            call.cancel();
        }
        mSaldoKreditmuMvpView = null;
    }

    public void cekSaldoKreditmu(String token, String ktp, String birthDate, String installmentamount) {
        mSaldoKreditmuMvpView.onPreLoadSaldoKredimu();
        count = 0;
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option", Util.toTextRequestBody("checkPlafon"));
        map.put("idNumber", Util.toTextRequestBody(ktp));
        map.put("birthDate", Util.toTextRequestBody(birthDate));
        map.put("InstallmentAmount", Util.toTextRequestBody(installmentamount));

        call = apiService.saldoKreditmu(token, map);
        call.enqueue(new Callback<BaseResponse<SaldoKreditmuResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<SaldoKreditmuResponse>> call, Response<BaseResponse<SaldoKreditmuResponse>> response) {
                if (mSaldoKreditmuMvpView != null) {
                    count = 0;
                    if (response.isSuccessful()) {
                        mSaldoKreditmuMvpView.onSuccessSaldoKreditmu(response.body().getData());
                    } else if (response.code() == 403) {
                        mSaldoKreditmuMvpView.onSaldoKreditmuTokenExpired();
                    } else {
                        mSaldoKreditmuMvpView.onFailedSaldoKreditmu(errorRetrofitUtil.parseError(response));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SaldoKreditmuResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                    } else {
                        count = 0;
                        mSaldoKreditmuMvpView.onFailedSaldoKreditmu(errorRetrofitUtil.responseFailedError(t));
                    }
                }
            }
        });
    }
}
