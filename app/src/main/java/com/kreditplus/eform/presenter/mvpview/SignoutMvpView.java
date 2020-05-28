package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface SignoutMvpView extends BaseMvpView {
    void onPreSignout();
    void onSuccessSignout();
    void onFailedSignout(String message);
    void onTokenExpired();
}
