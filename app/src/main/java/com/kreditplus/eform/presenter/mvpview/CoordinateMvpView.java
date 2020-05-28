package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by nurirppan on 12/27/2017.
 */

public interface CoordinateMvpView extends BaseMvpView{
    void onPreCoordinate();
    void onFailedCoordinate(String massage);
    void onTokenCoordinateExpired();
}
