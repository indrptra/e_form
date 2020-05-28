package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.MasterDataResponse;
import com.kreditplus.eform.presenter.mvpview.MasterDataMvpView;
import com.kreditplus.eform.presenter.mvpview.SendFcmIdMvpView;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendFcmIdPresenter implements BasePresenter<SendFcmIdMvpView>{
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SendFcmIdMvpView sendFcmIdMvpView;
    private Call<BaseResponse<Object>> callBack;
    private int count;

    public SendFcmIdPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SendFcmIdMvpView view) {
        sendFcmIdMvpView = view;
    }

    @Override
    public void detachView() {
        if (callBack != null) callBack.cancel();
        sendFcmIdMvpView = null;
    }

    public void SendFcmId(String token, String fcmId){
        count = 0;
        sendFcmIdMvpView.onPreSendFcmId();
        callBack = apiService.updateFCM(token, fcmId);
        callBack.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    sendFcmIdMvpView.onSuccessSendFcmId();
                } else {
                    sendFcmIdMvpView.onFailedSendFcmId(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (!callBack.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (sendFcmIdMvpView != null) {
                            sendFcmIdMvpView.onFailedSendFcmId(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }
}
