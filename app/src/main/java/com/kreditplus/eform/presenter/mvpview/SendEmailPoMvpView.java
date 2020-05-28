package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by apc-lap012 on 23/05/17.
 */

public interface SendEmailPoMvpView extends BaseMvpView {
    void onPreLoadSendEmail();
    void onSuccessSendEmail();
    void onFailedSendEmail(String message);
    void onProcessSendEmailTokenExpired();
}
