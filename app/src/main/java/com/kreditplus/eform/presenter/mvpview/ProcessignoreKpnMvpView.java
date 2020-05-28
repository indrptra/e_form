package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by apc-lap012 on 13/05/17.
 */

public interface ProcessignoreKpnMvpView extends BaseMvpView {
    void onPreLoadProcessIgnore();
    void onSuccessProcessIgnore();
    void onFailedProcessIgnore(String message);
    void onProcessIgnoreTokenExpired();
}
