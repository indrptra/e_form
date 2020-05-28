package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.DetailPerhitunganKendaraanResponse;

public interface DetailPerhitunganKendaraanMvpView extends BaseMvpView {
    void onPrePerhitunganKendaraan();

    void onSuccessPerhitunganKendaraan(DetailPerhitunganKendaraanResponse data, String statSinkron);

    void onFailedPerhitunganKendaraan(String message);
}
