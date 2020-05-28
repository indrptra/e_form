package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.MaskingResponse;

import java.util.List;

/**
 * Created by Ignatius on 10/30/2017.
 */

public interface MaskingMvpView extends BaseMvpView {
    void onPreLoadMasking();
    void onSuccessLoadMasking(MaskingResponse maskingResponse);
    void onFailedLoadMasking(String message);
    void onTokenMaskingExpired();
}
