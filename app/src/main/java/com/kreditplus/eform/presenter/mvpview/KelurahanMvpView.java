package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.KelurahanResponse;

public interface KelurahanMvpView extends BaseMvpView {
    void onPreKelurahan();

    void onSuccessKelurahan(String status, KelurahanResponse data);

    void onFailedKelurahan(String message);
}
