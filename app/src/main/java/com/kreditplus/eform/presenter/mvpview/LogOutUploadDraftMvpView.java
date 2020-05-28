package com.kreditplus.eform.presenter.mvpview;

public interface LogOutUploadDraftMvpView extends BaseMvpView {
    void onPreLogOutUploadDraft();

    void onSuccessLogOutUploadDraft();

    void onFailedLogOutUploadDraft(String message);

    void onTokenLogOutUploadDraft();
}
