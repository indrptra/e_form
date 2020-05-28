package com.kreditplus.eform.presenter.mvpview;


import android.content.Context;

import com.kreditplus.eform.model.response.objecthelper.Application;

import java.util.List;

/**
 * Created by apc-lap012 on 09/02/17.
 */

public interface DetailSaveDraftMvpView extends BaseMvpView {
    void onPreSubmitDetailSaveDraft();
    void onSuccessSubmitDetailSaveDraft(List<Application> applicationList);
    void onFailedSubmitDetailSaveDraft();
    void onTokenExpired();
}
