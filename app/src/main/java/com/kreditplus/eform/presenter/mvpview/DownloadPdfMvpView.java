package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by apc-lap012 on 28/07/17.
 */

public interface DownloadPdfMvpView extends BaseMvpView {
    void onPreDownloadPdf();
    void onSuccessDownloadPdf(String pdfUrl, String pdfName);
    void onFailedDownloadPdf(String message);
    void onRefreshTokenPdf();
}
