package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.ProductOffTenorResponse;

public interface ProductOffTenorMvpView extends BaseMvpView {
    void onPreProductOffTenor();

    void onSuccessProductOffTenor(ProductOffTenorResponse data);

    void onFailedProductOffTenor(String message);
}
