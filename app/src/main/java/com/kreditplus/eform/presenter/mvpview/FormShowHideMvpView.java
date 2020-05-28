package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.objecthelper.FormDinamic;

import java.util.List;

/**
 * Created by nurirppan on 04-Jan-18.
 */

public interface FormShowHideMvpView extends BaseMvpView {
    void onPreLoadFromShowHide();
    void onSuccessLoadFromShowHide(List<FormDinamic> formDinamic);
    void onFailedLoadFromShowHide(String message);
    void onTokenExpiredFromShowHide();
}
