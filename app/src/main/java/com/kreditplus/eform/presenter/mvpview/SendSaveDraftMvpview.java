package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.PengajuanBaru;

import java.util.List;


/**
 * Created by apc-lap012 on 07/02/17.
 */

public interface SendSaveDraftMvpview extends BaseMvpView {
    void onPreSubmitSaveDraft();
    void onSuccessSubmitSaveDraft();
    void onFailedSubmitSaveDraft(String message);
    void onTokenPengajuanExpired();

}
