package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface BidMvpView extends BaseMvpView {
    void onPreBid();
    void onSuccessBid(String applicationId);
    void onFailedBid();
    void onErrorBid(String message);
    void onTokenExpired();
}
