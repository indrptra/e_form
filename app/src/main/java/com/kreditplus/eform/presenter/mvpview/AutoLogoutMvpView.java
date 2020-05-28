package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by apc-lap012 on 02/06/17.
 */

public interface AutoLogoutMvpView extends BaseMvpView {

    void onPreAutomaticLogout();
    void onSuccessSendAutomaticLogout(String message);
    void onFailedSendAutomaticLogout(String message);
    void onTokenAutomaticLogoutExpired();
}
