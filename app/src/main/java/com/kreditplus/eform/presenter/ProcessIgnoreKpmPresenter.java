package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.ProcessignoreKpnMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 13/05/17.
 */

public class ProcessIgnoreKpmPresenter implements BasePresenter<ProcessignoreKpnMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private ProcessignoreKpnMvpView mProcessignoreKpnMvpView;
    private Call<BaseResponse<Object>> call;
    private int count;

    public ProcessIgnoreKpmPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(ProcessignoreKpnMvpView view) {
        mProcessignoreKpnMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null)
            call.cancel();
        mProcessignoreKpnMvpView = null;
    }

    public void processIgnore(String token, String appId, String answer, String reason){
        mProcessignoreKpnMvpView.onPreLoadProcessIgnore();
        count = 0;
        Map<String, RequestBody> map = new HashMap<>();

        map.put("applicationId", Util.toTextRequestBody(appId));
        map.put("answer",Util.toTextRequestBody(answer));
        map.put("reason",Util.toTextRequestBody(reason));

        call = mApiService.processIgnore(token,map);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mProcessignoreKpnMvpView.onSuccessProcessIgnore();
                }else  if (response.code() == 403){
                    mProcessignoreKpnMvpView.onProcessIgnoreTokenExpired();
                }else {
                    mProcessignoreKpnMvpView.onFailedProcessIgnore(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else{
                        if (mProcessignoreKpnMvpView != null){
                            mProcessignoreKpnMvpView.onFailedProcessIgnore(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }
                }
            }
        });


    }
}
