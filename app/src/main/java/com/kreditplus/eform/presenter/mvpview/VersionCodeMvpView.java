package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.VersionCodeResponse;

public interface VersionCodeMvpView extends BaseMvpView{
    void onPreCheckVersionCode();
    void onSuccessCheckVersionCode(VersionCodeResponse versionCodeResponse);
    void onFailedCheckVersionCode(String message);
}
