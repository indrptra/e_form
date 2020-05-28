package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface AttachmentMvpView extends BaseMvpView {
    void onPreSubmitAttachment();
    void onSuccessSubmitAttachment();
    void onFailedSubmitAttachment(String message);
    void onTokenAttachmentExpired();
}
