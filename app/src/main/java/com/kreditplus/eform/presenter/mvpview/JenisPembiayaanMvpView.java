package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.response.CheckEfNumberResponse;
import com.kreditplus.eform.model.response.JenisPembiayaanResponse;

public interface JenisPembiayaanMvpView extends BaseMvpView {
    void onPreJenisPembiayaan();

    void onSuccessJenisPembiayaan(JenisPembiayaanResponse data);

    void onFailedJenisPembiayaan(String message);
}