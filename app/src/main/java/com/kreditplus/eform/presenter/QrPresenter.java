package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.QrResponse;
import com.kreditplus.eform.presenter.mvpview.QrMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 11/09/17.
 */

public class QrPresenter implements BasePresenter<QrMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private QrMvpView mQrMvpView;
    private Call<BaseResponse<QrResponse>> call;
    private int count;

    public QrPresenter() {
        App.appComponent().inject(this);
    }


    @Override
    public void attachView(QrMvpView view) {
        mQrMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null)
            call.cancel();
        mQrMvpView = null;
    }

    public void qrResultValidaiton(String token, String appId, String qrResult){
        Map<String, RequestBody> map = new HashMap<>();
        count = 0;
        mQrMvpView.onPreCheckQrScanner();
        map.put("SupplierID",Util.toTextRequestBody(qrResult));

        call = apiService.checkingQrResult(token,appId,map);
        call.enqueue(new Callback<BaseResponse<QrResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<QrResponse>> call, Response<BaseResponse<QrResponse>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mQrMvpView.onSuccessCheckQrScanner(response.body().getData().getIsMatch());
                }else if (response.code() == 403){
                    mQrMvpView.onTokenExpiredCheckQrScanner();
                }else{
                    mQrMvpView.onFailedCheckQrScanner(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<QrResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else{
                        mQrMvpView.onFailedCheckQrScanner(errorRetrofitUtil.responseFailedError(t));
                        count = 0;
                    }
                }
            }
        });

    }
}
