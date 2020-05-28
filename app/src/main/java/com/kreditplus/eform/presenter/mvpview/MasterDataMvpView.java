package com.kreditplus.eform.presenter.mvpview;

public interface MasterDataMvpView extends BaseMvpView {

    void onPreMasterData();

    void onSuccessMasterData();

    void onFailedMasterData(String message);

    void onTokenMasterDataExpired();

    void onUpdateCountMasterData(int countMasterData);
}
