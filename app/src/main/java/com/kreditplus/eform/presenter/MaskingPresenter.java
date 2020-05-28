package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.MaskingResponse;
import com.kreditplus.eform.presenter.mvpview.MaskingMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ignatius on 10/30/2017.
 */

public class MaskingPresenter implements BasePresenter<MaskingMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private MaskingMvpView mMaskingMvpView;
    private Call<BaseResponse<MaskingResponse>> call;
    private int count;


    public MaskingPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(MaskingMvpView view) {
        mMaskingMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null)
            call.cancel();
        mMaskingMvpView = null;
    }

    public void getDataMasking(String token,String productId, String ProductOffId){
        mMaskingMvpView.onPreLoadMasking();
        count = 0;
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("product_id", Util.toTextRequestBody(productId));
        bodyMap.put("product_offering_id",Util.toTextRequestBody(ProductOffId));

        call = apiService.getMaskingValue(token,bodyMap);
        call.enqueue(new Callback<BaseResponse<MaskingResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<MaskingResponse>> call, Response<BaseResponse<MaskingResponse>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mMaskingMvpView.onSuccessLoadMasking(response.body().getData());
                }else if (response.code() == 403){
                    mMaskingMvpView.onTokenMaskingExpired();
                }else{
                    mMaskingMvpView.onFailedLoadMasking(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MaskingResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else{
                        mMaskingMvpView.onFailedLoadMasking(errorRetrofitUtil.responseFailedError(t));
                    }
                }
            }
        });
    }


}
