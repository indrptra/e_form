package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by apc-lap012 on 11/09/17.
 */

public interface QrMvpView extends BaseMvpView {

    void onPreCheckQrScanner();
    void onSuccessCheckQrScanner(int checked);
    void onFailedCheckQrScanner(String message);
    void onTokenExpiredCheckQrScanner();
}
