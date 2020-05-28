package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.TahunProduksiResponse;

public interface TahunProduksiKendaraanMvpView extends BaseMvpView {
    void onPreTahunProduksiKendaraan();

    void onSuccessTahunProduksiKendaraan(TahunProduksiResponse data);

    void onFailedTahunProduksiKendaraan(String message);
}
