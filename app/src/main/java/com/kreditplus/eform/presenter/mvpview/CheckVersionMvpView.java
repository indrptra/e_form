package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by apc-lap012 on 15/06/17.
 */

public interface CheckVersionMvpView extends BaseMvpView {

    void onPreCheckVersion();
    void onSuccessCheckVersion(String check);
    void onFailedCheckVersion(String message);
    void onTokenCheckVersionExpired();
}
