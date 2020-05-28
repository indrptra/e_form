package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface CodeSignatureMvpView extends BaseMvpView {
    void onPreSendCodeSignature();
    void onSuccessSendCodeSignature(int count);
    void onFailedSendCodeSignature(String message);
    void onTokenSendCodeSignatureExpired();

    void onPreSendConfirmSignature();
    void onSuccessConfirmCodeSignature(int status,String message, String usedOTP);
    void onFailedConfirmCodeSignature(String message);
    void onTokenConfirmCodeSignatureExpired();
}
