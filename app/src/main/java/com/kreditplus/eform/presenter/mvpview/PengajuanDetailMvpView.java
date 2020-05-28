package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.objecthelper.Application;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface PengajuanDetailMvpView extends BaseMvpView {
    void onPreSubmitPengajuanEditLoad();
    void onSuccessSubmitPengajuanEditLoad(Application application);
    void onFailedSubmitPengajuanEditLoad(String message);
    void onDetailTokenExpired();
}
