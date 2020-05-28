package com.kreditplus.eform.presenter.mvpview;

public interface SendFcmIdMvpView extends BaseMvpView{
    void onPreSendFcmId();

    void onSuccessSendFcmId();

    void onFailedSendFcmId(String message);
}
