package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.objecthelper.KreditmuList;

import java.util.List;

/**
 * Created by apc-lap012 on 23/08/17.
 */

public interface KreditmuListMvpView extends BaseMvpView {
    void onPreLoadKreditmuList();
    void onSuccessLoadKreditmuList(List<KreditmuList> dataKreditmus);
    void onFailedLoadKreditmuList(String message);
    void onTokenKreditmuListExpired();
}
