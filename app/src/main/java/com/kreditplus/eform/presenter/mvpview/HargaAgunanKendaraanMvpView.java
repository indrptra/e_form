package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.TahunProduksiResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;

public interface HargaAgunanKendaraanMvpView extends BaseMvpView {
    void onPreHargaAgunanKendaraan();

    void onSuccessHargaAgunanKendaraan(HargaAgunanResponse data);

    void onFailedHargaAgunanKendaraan(String message);
}
