package com.kreditplus.eform.presenter.mvpview;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface PengajuanAddMvpView extends BaseMvpView {
    void onPreSubmitPengajuan();
    void onSuccessSubmitPengajuan(String applicationId);
    void onFailedSubmitPengajuan(String message);
    void onTokenPengajuanExpired();
}
