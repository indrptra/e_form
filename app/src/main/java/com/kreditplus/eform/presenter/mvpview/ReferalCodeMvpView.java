package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.ReferalCodeResponse;

public interface ReferalCodeMvpView extends BaseMvpView {
        void onPreReferalCode();

        void onSuccessReferalCode(ReferalCodeResponse data);

        void onFailedReferalCode(String message);
}
