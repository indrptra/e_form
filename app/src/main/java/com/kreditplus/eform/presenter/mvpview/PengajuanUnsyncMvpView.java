package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface PengajuanUnsyncMvpView extends BaseMvpView {
    void onPreLoadPengajuanUnsync();

    void onSuccessLoadPengajuanUnsync(List<MasterFormPengajuan> masterFormPengajuanList, List<PengajuanBaru> pengajuanBaruList);

    void onFailedLoadPengajuanUnsync(String message);
}
