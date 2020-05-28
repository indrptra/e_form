package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.model.response.AssetMasterResponse;
import com.kreditplus.eform.view.IndonesianCurrencyEditText;

public interface SearchAssetMasterMvpView extends BaseMvpView {

    void onPreSearchAssetMaster();

    void onSuccessSearchAssetMaster(AssetMasterResponse data, NiceAutoCompleteTextView actNamaBarangAsset, IndonesianCurrencyEditText edtPrice);

    void onFailedSearchAssetMaster(String message);
}

