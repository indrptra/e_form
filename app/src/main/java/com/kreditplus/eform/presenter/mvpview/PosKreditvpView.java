package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.PosListDownResponse;
import com.kreditplus.eform.model.response.PosResponse;

public interface PosKreditvpView {
    void onPreGetPos();

    void onSuccessGetPos(PosListDownResponse data);

    void onFailedGetPos(String message);
}
