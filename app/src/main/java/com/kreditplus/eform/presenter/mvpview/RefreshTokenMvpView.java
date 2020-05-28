package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface RefreshTokenMvpView extends BaseMvpView {
    void onPreRefreshToken();
    void onSuccessRefreshToken(String token);
    void onFailedRefreshToken(String message);
}
