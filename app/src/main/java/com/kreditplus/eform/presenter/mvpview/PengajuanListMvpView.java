package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.objecthelper.Industri;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;
import com.kreditplus.eform.model.response.objecthelper.ProductOffering;
import com.kreditplus.eform.model.response.objecthelper.Propinsi;
import com.kreditplus.eform.model.response.objecthelper.Supplier;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface PengajuanListMvpView extends BaseMvpView {
    void onPreLoadPengajuan();
    void onSuccessLoadPengajuan(int totalData, List<Pengajuan> pengajuanList);
    void onFailedLoadPengajuan(String message);
    void onTokenExpired();
}
