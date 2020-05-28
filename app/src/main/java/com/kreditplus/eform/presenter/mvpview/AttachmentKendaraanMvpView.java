package com.kreditplus.eform.presenter.mvpview;

public interface AttachmentKendaraanMvpView extends BaseMvpView {
    void onPreSubmitAttachment();
    void onSuccessSubmitAttachment();
    void onFailedSubmitAttachment(String message);
}