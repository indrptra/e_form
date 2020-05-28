package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by apc-lap012 on 07/08/17.
 */

public interface ReturnRateMvpView extends BaseMvpView{

    void onPreLoadRaturnRate();
    void onSuccessRaturnRate(double rate, int penggunaan,String date);
    void onFailedReturnRate(String message);
    void onReturnRateTokenExpired();
}
