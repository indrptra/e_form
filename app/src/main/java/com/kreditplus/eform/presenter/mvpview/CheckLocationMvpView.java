package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.BaseResponse;

public interface CheckLocationMvpView {
    void onPreCheckLocation();
    void onSuccessCheckLocation(BaseResponse baseResponse);
    void onFailedCheckLocation(String message);
}
