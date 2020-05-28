package com.kreditplus.eform.presenter.mvpview;

public interface SyncKendaraanMvpView extends BaseMvpView {
    void onPreSynchKendaraan();

    void onSuccessSynchKendaraan(String applicationId, String statusSinkron);

    void onEfNumberTaken();

    void onFailedSynchKendaraan(String message);
}