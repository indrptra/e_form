package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.MasterSyncResponse;

/**
 * Created by apc-lap012 on 17/02/17.
 */

public interface MasterSyncMvpView extends BaseMvpView {

    void onPreLoadmasterSync();

    void onSuccessLoadmasterSync(MasterSyncResponse masterSyncResponse);

    void onFailedLoadLoadmasterSync(String message);

    void onTokenMasterSyncExpired();

    void onUpdateCountMasterSync(int countMasterSync);
}
