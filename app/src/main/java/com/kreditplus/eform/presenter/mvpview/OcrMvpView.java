package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.OcrResponse;

/**
 * Created by Ignatius on 10/26/2017.
 */

public interface OcrMvpView extends BaseMvpView {
    void onPreSendOcr();
    void onSuccessSendOcr(OcrResponse ocrResponse);
    void onFailedSendOcr(String message);
    void onTokenSendOcrExpired();
}
