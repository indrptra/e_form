package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.JenisKendaraanResponse;
import com.kreditplus.eform.model.response.ProductOffTenorResponse;

public interface JenisKendaraanMvpView extends BaseMvpView{
    void onPreJenisKendaraan();

    void onSuccessJenisKendaraan(JenisKendaraanResponse jenisKendaraanResponse);

    void onFailedJenisKendaraan(String message);
}
