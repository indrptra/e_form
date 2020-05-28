package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.TahunProduksiResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;
import com.kreditplus.eform.view.IndonesianCurrencyEditText;

public interface HargaAgunanElcMvpView extends BaseMvpView {
    void onPreHargaAgunan();

    void onSuccessHargaAgunan(HargaAgunanResponse data, final IndonesianCurrencyEditText edtPrice);

    void onFailedHargaAgunan(String message);
}
