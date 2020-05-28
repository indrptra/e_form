package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.SaldoKreditmuResponse;

/**
 * Created by apc-lap012 on 02/08/17.
 */

public interface SaldoKreditmuMvpView extends BaseMvpView {

    void onPreLoadSaldoKredimu();
    void onSuccessSaldoKreditmu(SaldoKreditmuResponse data);
    void onFailedSaldoKreditmu(String message);
    void onSaldoKreditmuTokenExpired();

}
