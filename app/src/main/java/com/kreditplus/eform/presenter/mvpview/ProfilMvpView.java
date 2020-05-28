package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface ProfilMvpView extends BaseMvpView {
    void onPreLoadProfil();
    void onSuccessLoadProfil(String message);
    void onFailedLoadProfil(String massage);
    void onTokenProfilExpired();
}
