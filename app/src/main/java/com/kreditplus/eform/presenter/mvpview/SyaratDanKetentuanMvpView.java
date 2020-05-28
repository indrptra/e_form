package com.kreditplus.eform.presenter.mvpview;


import com.kreditplus.eform.model.response.objecthelper.FaqObjt;
import com.kreditplus.eform.model.response.objecthelper.Syarat;
import com.kreditplus.eform.model.response.objecthelper.TidakCancel;

import java.util.List;

/**
 * Created by apc-lap012 on 01/03/17.
 */

public interface SyaratDanKetentuanMvpView extends BaseMvpView {
    void onPreLoadSyarat();
    void onSuccessSyarat(Syarat syarats, TidakCancel tidakCancels, FaqObjt faq);
    void onFailedLoadSyarat(String message);
    void onSyaratTokenExpired();
}
