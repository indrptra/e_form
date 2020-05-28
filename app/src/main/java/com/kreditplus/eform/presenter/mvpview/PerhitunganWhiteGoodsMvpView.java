package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.DetailPerhitunganWhiteGoodsResponse;

public interface PerhitunganWhiteGoodsMvpView extends BaseMvpView {
    void onPrePerhitunganWhiteGoods();

    void onSuccessGetPerhitunganWhiteGoods(DetailPerhitunganWhiteGoodsResponse data);

    void onFailedGetPerhitunganWhiteGoods(String message);
}
