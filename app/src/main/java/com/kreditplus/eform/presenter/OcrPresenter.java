package com.kreditplus.eform.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.OcrResponse;
import com.kreditplus.eform.presenter.mvpview.OcrMvpView;
import com.kreditplus.eform.service.ApiService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ignatius on 10/26/2017.
 */

public class OcrPresenter implements BasePresenter<OcrMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private OcrMvpView mOcrMvpView;
    private Call<BaseResponse<OcrResponse>> call;
    private File compres;
    private int index;

    public OcrPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(OcrMvpView view) {
        mOcrMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null)
            call.cancel();
        mOcrMvpView = null;
    }

    public void SendOcr(String token, File file){
        mOcrMvpView.onPreSendOcr();
        List<MultipartBody.Part> imageKtp = new ArrayList<>();
        index = 0;
        try {
            compres = new Compressor(mOcrMvpView.getContext())
                    .setMaxHeight(720)
                    .setMaxWidth(1080)
                    .setQuality(80)
                    .compressToFile(file);
        } catch (IOException e) {
            if (BuildConfig.DEBUG)
                Log.e("Gagal Kompress Retake",e.getMessage());
            Crashlytics.logException(e);
            mOcrMvpView.onFailedSendOcr(mOcrMvpView.getContext().getString(R.string.warning_failed_compress));
            return;
        }

        imageKtp.add(prepareFilePart("image",compres));

        call = apiService.SendImageKrp(token,imageKtp);
        call.enqueue(new Callback<BaseResponse<OcrResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OcrResponse>> call, Response<BaseResponse<OcrResponse>> response) {
                index = 0;
                if (response.isSuccessful()){
                    mOcrMvpView.onSuccessSendOcr(response.body().getData());
                }else if (response.code() == 403){
                    mOcrMvpView.onTokenSendOcrExpired();
                }else{
                    mOcrMvpView.onFailedSendOcr(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<OcrResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (index < 3){
                        call.clone().enqueue(this);
                        index += 1;
                    }else{
                        if (mOcrMvpView != null)
                            mOcrMvpView.onFailedSendOcr(errorRetrofitUtil.responseFailedError(t));
                        index = 0;
                    }
                }
            }
        });
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        RequestBody requestFile = Util.toImageRequestBody(file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
