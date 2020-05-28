package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.CodePersetujuanResponse;
import com.kreditplus.eform.model.response.SendCodeResponse;
import com.kreditplus.eform.model.response.objecthelper.AssetToken;
import com.kreditplus.eform.model.response.objecthelper.BodyToken;
import com.kreditplus.eform.presenter.mvpview.CodeSignatureMvpView;
import com.kreditplus.eform.presenter.mvpview.SignoutMvpView;
import com.kreditplus.eform.service.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class CodeSignaturePresenter implements BasePresenter<CodeSignatureMvpView> {

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    private CodeSignatureMvpView mCodeSignatureMvpView;
    private Call<BaseResponse<SendCodeResponse>> callSend;
    private Call<BaseResponse<CodePersetujuanResponse>> callConfirm;
    private List<AssetToken> assetTokenLIst = new ArrayList<>();
    private int count;

    public CodeSignaturePresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(CodeSignatureMvpView view) {
        mCodeSignatureMvpView = view;
    }

    @Override
    public void detachView() {
        if (callSend != null) callSend.cancel();
        if (callConfirm != null) callConfirm.cancel();
        mCodeSignatureMvpView = null;
    }

    public void sendCodeSignature(String authorization, BodyToken bodyToken) {
        mCodeSignatureMvpView.onPreSendCodeSignature();
        callSend = mApiService.sendCodeSignature(authorization, bodyToken);
        callSend.enqueue(new Callback<BaseResponse<SendCodeResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<SendCodeResponse>> call, Response<BaseResponse<SendCodeResponse>> response) {
                if (response.isSuccessful()) {
                    mCodeSignatureMvpView.onSuccessSendCodeSignature(response.body().getData().getCount());
                } else if (response.code() == 403) {
                    mCodeSignatureMvpView.onTokenSendCodeSignatureExpired();
                } else {
                    mCodeSignatureMvpView.onFailedSendCodeSignature(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SendCodeResponse>> call, Throwable t) {
                if (mCodeSignatureMvpView != null){
//                    mCodeSignatureMvpView.onFailedSendCodeSignature(mErrorRetrofitUtil.responseFailedError(t));
                }
            }
        });
    }

    public void confirmCodeSignature(String authorization, String type, final String token, String mobileSubmissionKey, String pid) {
        mCodeSignatureMvpView.onPreSendConfirmSignature();
        count = 0;
        callConfirm = mApiService.confirmCodeSignature(authorization, type, token,pid, mobileSubmissionKey);
        callConfirm.enqueue(new Callback<BaseResponse<CodePersetujuanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<CodePersetujuanResponse>> call, Response<BaseResponse<CodePersetujuanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mCodeSignatureMvpView.onSuccessConfirmCodeSignature(response.body().getData().getIsValid(),
                            response.body().getMeta().getMessage(), token);
                } else if (response.code() == 403) {
                    mCodeSignatureMvpView.onTokenConfirmCodeSignatureExpired();
                } else {
                    mCodeSignatureMvpView.onFailedConfirmCodeSignature(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CodePersetujuanResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (mCodeSignatureMvpView != null){
                        if (count < 3){
                            callConfirm.clone().enqueue(this);
                            count++;
                        }else{
                            count = 0;
                            mCodeSignatureMvpView.onFailedConfirmCodeSignature(mErrorRetrofitUtil.responseFailedError(t));
                        }
                    }
                }
            }
        });
    }

}
