package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.MerkKendaraanResponse;

public interface MerkKendaraanMvpView extends BaseMvpView {

    void onPreMerkKendaraan();

    void onSuccessMerkKendaraan(MerkKendaraanResponse data, String merk);

    void onSuccessTipeKendaraan(MerkKendaraanResponse data, String merk);

    void onFailedMerkKendaraan(String message, String assetLevel);

}
