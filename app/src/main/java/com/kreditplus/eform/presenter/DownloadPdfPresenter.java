package com.kreditplus.eform.presenter;

import com.kreditplus.eform.App;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.PdfPengajuanResponse;
import com.kreditplus.eform.presenter.mvpview.DownloadPdfMvpView;
import com.kreditplus.eform.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apc-lap012 on 28/07/17.
 */

public class DownloadPdfPresenter implements BasePresenter<DownloadPdfMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private DownloadPdfMvpView mPdfPengajuanMvpView;
    private Call<BaseResponse<PdfPengajuanResponse>> call;
    private int count;

    public DownloadPdfPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(DownloadPdfMvpView view) {
        mPdfPengajuanMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null)
            call.cancel();
        mPdfPengajuanMvpView = null;
    }

    public void downloadPdfPengajuan(String token, String pdfName, String id) {
        mPdfPengajuanMvpView.onPreDownloadPdf();
        count = 0;
        call = apiService.downloadPdfPengajuan(token, id, pdfName);
        call.enqueue(new Callback<BaseResponse<PdfPengajuanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<PdfPengajuanResponse>> call, Response<BaseResponse<PdfPengajuanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mPdfPengajuanMvpView.onSuccessDownloadPdf(response.body().getData().getPdfUrl(), response.body().getData().getPdfName());
                } else if (response.code() == 403) {
                    mPdfPengajuanMvpView.onRefreshTokenPdf();
                } else {
                    mPdfPengajuanMvpView.onFailedDownloadPdf(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PdfPengajuanResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (mPdfPengajuanMvpView != null) {
                        if (count < 3) {
                            call.clone().enqueue(this);
                            count += 1;
                        } else {
                            count = 0;
                            mPdfPengajuanMvpView.onFailedDownloadPdf(errorRetrofitUtil.responseFailedError(t));
                        }
                    }
                }
            }
        });
    }

    public void downloadPdfPerjanjian(String token, String id) {
        count = 0;
        mPdfPengajuanMvpView.onPreDownloadPdf();
        call = apiService.downloadPdfPerjanjian(token, id);
        call.enqueue(new Callback<BaseResponse<PdfPengajuanResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<PdfPengajuanResponse>> call, Response<BaseResponse<PdfPengajuanResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mPdfPengajuanMvpView.onSuccessDownloadPdf(response.body().getData().getPdfUrl(), response.body().getData().getPdfName());
                } else if (response.code() == 403) {
                    mPdfPengajuanMvpView.onRefreshTokenPdf();
                } else {
                    mPdfPengajuanMvpView.onFailedDownloadPdf(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PdfPengajuanResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (mPdfPengajuanMvpView != null) {
                        if (count < 3) {
                            call.clone().enqueue(this);
                            count += 1;
                        } else {
                            count = 0;
                            mPdfPengajuanMvpView.onFailedDownloadPdf(errorRetrofitUtil.responseFailedError(t));
                        }
                    }
                }
            }
        });
    }
}
