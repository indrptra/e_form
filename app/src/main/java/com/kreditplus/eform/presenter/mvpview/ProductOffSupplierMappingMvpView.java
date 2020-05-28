package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.ProdOfSuppMapping;

import java.util.List;

public interface ProductOffSupplierMappingMvpView extends BaseMvpView {
    void onPreProdOfSupplier();

    void onSuccessProdOfSupplier(List<ProdOfSuppMapping> prodOfSuppMappings);

    void onFailedProdOfSupplier(String message);

    void onTokenProdOfSupplier();
}
