package com.kreditplus.eform.presenter;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.widget.AppCompatButton;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.SendEmailPoMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 23/05/17.
 */

public class SendEmailPoPresenter implements BasePresenter<SendEmailPoMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private SendEmailPoMvpView mSendEmailPoMvpView;
    private Call<BaseResponse<Object>> call;
    private int count;

    public SendEmailPoPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SendEmailPoMvpView view) {
        mSendEmailPoMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null)
            call.cancel();
        mSendEmailPoMvpView = null;
    }

    public void SendingEmail(String token, String appId, String email){
        mSendEmailPoMvpView.onPreLoadSendEmail();
        count = 0;
        Map<String, RequestBody> map = new HashMap<>();

        map.put("applicationId", Util.toTextRequestBody(appId));
        map.put("emailReceiver",Util.toTextRequestBody(email));

        call = mApiService.sendEmail(token,map);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mSendEmailPoMvpView.onSuccessSendEmail();
                }else if (response.code() == 403){
                    mSendEmailPoMvpView.onProcessSendEmailTokenExpired();
                }else {
                    mSendEmailPoMvpView.onFailedSendEmail(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count +=1;
                    }else{
                        if (mSendEmailPoMvpView != null){
                            mSendEmailPoMvpView.onFailedSendEmail(mErrorRetrofitUtil.responseFailedError(t));
                            count =0;
                        }
                    }
                }
            }
        });

    }
}
