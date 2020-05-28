package com.kreditplus.eform.presenter.mvpview;

import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetKendaraan;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.TblLokasi;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public interface PengajuanDraftMvpView extends BaseMvpView {
    void onPreLoadPengajuanDraft();

    void onSuccessLoadPengajuanDraft(MasterFormPengajuan masterFormPengajuan,
                                     List<TblLokasi> tblLokasiList,
                                     List<AssetElektronik> assetElektronikList,
                                     List<Attachment> attachmentList,
                                     List<MaskingTenor> maskingTenorList,
                                     List<MaskingRate> maskingRateList);

    void onFailedLoadPengajuanDraft(String message);
}
