package com.kreditplus.eform.presenter.mvpview;

public interface SynchronizationMvpView {
    void onPreSynchronization();
    void onSuccessSynchronization(String applicationId);
    void onFailedSynchronization(String message);
    void onTokenSynchronization();
}
