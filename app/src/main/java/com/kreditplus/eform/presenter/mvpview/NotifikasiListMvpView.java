package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.response.objecthelper.Notifikasi;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface NotifikasiListMvpView extends BaseMvpView {
    void onPreLoadNotifikasi();
    void onSuccessLoadNotifikasi(List<Notifikasi> pengajuanList);
    void onFailedLoadNotifikasi(String message);
    void onTokenExpired();
}
