package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Daniel on 8/12/2016.
 */
public interface ChangePasswordMVPView extends BaseMvpView {
    void onPreChangePassword();
    void onSuccessChangePassword(String message);
    void onFailedChangePassword(String message);
    void onTokenExpired();
}
