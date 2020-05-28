package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.FinancingObjectResponse;

public interface TujuanPembiayaanWGMvpView {
    void onPreTujuanPembiayaan();
    void onSuccessTujuanPembiayaan(FinancingObjectResponse response);
    void onFailedTujuanPembiayaan(String message);
}
