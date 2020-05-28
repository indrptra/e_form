package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.JenisKendaraanResponse;
import com.kreditplus.eform.model.response.WilayahCabangResponse;

public interface WilayahCabangMvpView extends BaseMvpView {
    void onPreWilayahCabang();

    void onSuccessWilayahCabang(WilayahCabangResponse wilayahCabangResponse);

    void onFailedWilayahCabang(String message);
}
