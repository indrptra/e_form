package com.kreditplus.eform.dagger.applicationscope;

import com.kreditplus.eform.App;
import com.kreditplus.eform.activity.AttachmentZoomPagerActivity;
import com.kreditplus.eform.activity.CameraKTP;
import com.kreditplus.eform.activity.ChangePasswordActivity;
import com.kreditplus.eform.activity.DetailKpmActivity;
import com.kreditplus.eform.activity.EditProfileActivity;
import com.kreditplus.eform.activity.FormPengajuanActivity;
import com.kreditplus.eform.activity.FormPengajuanNonElcActivity;
import com.kreditplus.eform.activity.HomeActivity;
import com.kreditplus.eform.activity.PushNotifActivity;
import com.kreditplus.eform.activity.RetakePhotoActivity;
import com.kreditplus.eform.activity.SigninActivity;
import com.kreditplus.eform.activity.SplashActivity;
import com.kreditplus.eform.backup.ListDraftAndSinkronisasiAdapter;
import com.kreditplus.eform.adapter.PengajuanUnsyncAdapter;
import com.kreditplus.eform.fragment.FaqFragment;
import com.kreditplus.eform.fragment.HomeFragment;
import com.kreditplus.eform.fragment.KreditmuFragment;
import com.kreditplus.eform.backup.ListDraftAndSinkronisasiFragment;
import com.kreditplus.eform.fragment.ListDraftAndSinkronisasiNonElcFragment;
import com.kreditplus.eform.fragment.ListJanjiBertemuFragment;
import com.kreditplus.eform.fragment.ListKpmFragment;
import com.kreditplus.eform.fragment.ListKreditmuFragment;
import com.kreditplus.eform.fragment.ListPengajuanFragment;
import com.kreditplus.eform.fragment.NotifikasiFragment;
import com.kreditplus.eform.fragment.PengajuanDetailFragment;
import com.kreditplus.eform.fragment.PengajuanUnsyncFragment;
import com.kreditplus.eform.fragment.ProfilFragment;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.presenter.AttachmentKendaraanPresenter;
import com.kreditplus.eform.presenter.AttachmentPresenter;
import com.kreditplus.eform.presenter.AutoLogoutPresenter;
import com.kreditplus.eform.presenter.BidPresenter;
import com.kreditplus.eform.presenter.BlackListPresenter;
import com.kreditplus.eform.presenter.CekKodeProgramPresenter;
import com.kreditplus.eform.presenter.ChangePasswordPresenter;
import com.kreditplus.eform.presenter.CheckEfNumberPresenter;
import com.kreditplus.eform.presenter.CheckLocationPresenter;
import com.kreditplus.eform.presenter.CheckVersionPresenter;
import com.kreditplus.eform.presenter.CodeSignaturePresenter;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.DeleteDraftPresenter;
import com.kreditplus.eform.presenter.DetailPerhitunganKendaraanPresenter;
import com.kreditplus.eform.presenter.DownloadPdfPresenter;
import com.kreditplus.eform.presenter.ForceUpdateApkPresenter;
import com.kreditplus.eform.presenter.FormPresenter;
import com.kreditplus.eform.presenter.GetReferalCodePresenter;
import com.kreditplus.eform.presenter.HargaAgunanElcPresenter;
import com.kreditplus.eform.presenter.HargaAgunanKendaraanPresenter;
import com.kreditplus.eform.presenter.JenisKendaraanPresenter;
import com.kreditplus.eform.presenter.JenisPembiayaanPresenter;
import com.kreditplus.eform.presenter.KelurahanPresenter;
import com.kreditplus.eform.presenter.KreditmuListPresenter;
import com.kreditplus.eform.presenter.KreditmuPresenter;
import com.kreditplus.eform.presenter.LogoutUploadDraftPresenter;
import com.kreditplus.eform.presenter.MaskingPresenter;
import com.kreditplus.eform.presenter.MasterDataPresenter;
import com.kreditplus.eform.presenter.MasterSyncNewPresenter;
import com.kreditplus.eform.presenter.PilihKendaraanPresenter;
import com.kreditplus.eform.presenter.NotifikasiListPresenter;
import com.kreditplus.eform.presenter.OcrPresenter;
import com.kreditplus.eform.presenter.PengajuanAddPresenter;
import com.kreditplus.eform.presenter.PengajuanDetailPresenter;
import com.kreditplus.eform.presenter.PengajuanDraftPresenter;
import com.kreditplus.eform.presenter.PengajuanListPresenter;
import com.kreditplus.eform.presenter.PengajuanUnsyncPresenter;
import com.kreditplus.eform.presenter.PerhitunganWhiteGoodsPresenter;
import com.kreditplus.eform.presenter.PosKreditPresenter;
import com.kreditplus.eform.presenter.ProcessIgnoreKpmPresenter;
import com.kreditplus.eform.presenter.ProductOffSupplierMappingPresenter;
import com.kreditplus.eform.presenter.ProductOffTenorPresenter;
import com.kreditplus.eform.presenter.ProfilPresenter;
import com.kreditplus.eform.presenter.QrPresenter;
import com.kreditplus.eform.presenter.RecomendationPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.ReturnRatePresenter;
import com.kreditplus.eform.presenter.SaldoKreditmuPresenter;
import com.kreditplus.eform.presenter.SearchAssetMasterPresenter;
import com.kreditplus.eform.presenter.SearchMarketingSupplierPresenter;
import com.kreditplus.eform.presenter.SearchProductOfferingPresenter;
import com.kreditplus.eform.presenter.SearchSupplierMasterPresenter;
import com.kreditplus.eform.presenter.SendEmailPoPresenter;
import com.kreditplus.eform.presenter.SendFcmIdPresenter;
import com.kreditplus.eform.presenter.SendSaveDraftPresenter;
import com.kreditplus.eform.presenter.SigninPresenter;
import com.kreditplus.eform.presenter.SignoutPresenter;
import com.kreditplus.eform.presenter.SinkronisasiKendaraanPresenter;
import com.kreditplus.eform.presenter.SinkronisasiPresenter;
import com.kreditplus.eform.presenter.SyaratDanKetentuanPresenter;
import com.kreditplus.eform.presenter.TahunProduksiKendaraanPresenter;
import com.kreditplus.eform.presenter.TujuanPembiayaanPresenter;
import com.kreditplus.eform.presenter.WilayahCabangPresenter;

import dagger.Component;

/**
 * Created by Iwan Nurdesa on 07/11/15.
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(App app);

    void inject(SigninActivity signinActivity);

    void inject(PushNotifActivity pushNotifActivity);

    void inject(ForceUpdateApkPresenter forceUpdateApkPresenter);

    void inject(SearchAssetMasterPresenter searchAssetMasterPresenter);

    void inject(ProfilFragment profilFragment);

    void inject(SigninPresenter signinPresenter);

    void inject(SendFcmIdPresenter sendFcmIdPresenter);

    void inject(ErrorRetrofitUtil errorRetrofitUtil);

    void inject(PengajuanAddPresenter pengajuanAddPresenter);

    void inject(SinkronisasiPresenter sinkronisasiPresenter);

    void inject(PengajuanDetailPresenter pengajuanDetailPresenter);

    void inject(LogoutUploadDraftPresenter logoutUploadDraft);

    void inject(PengajuanListPresenter pengajuanListPresenter);

    void inject(ListPengajuanFragment listPengajuanFragment);

    void inject(SignoutPresenter signoutPresenter);

    void inject(HomeActivity homeActivity);

    void inject(HomeFragment homeFragment);

    void inject(SplashActivity splashActivity);

    void inject(NotifikasiListPresenter notifikasiListPresenter);

    void inject(NotifikasiFragment notifikasiFragment);

    void inject(PengajuanDetailFragment pengajuanDetailFragment);

    void inject(BidPresenter bidPresenter);

    void inject(PengajuanUnsyncPresenter pengajuanUnsyncPresenter);

    void inject(PengajuanUnsyncAdapter pengajuanUnsyncAdapter);

    void inject(ListDraftAndSinkronisasiAdapter listDraftAndSinkronisasiAdapter);

    void inject(PengajuanUnsyncFragment pengajuanUnsyncFragment);

    void inject(ListDraftAndSinkronisasiFragment listDraftAndSinkronisasiFragment);

    void inject(AttachmentPresenter attachmentPresenter);

    void inject(ChangePasswordActivity changePasswordActivity);

    void inject(ChangePasswordPresenter changePasswordPresenter);

    void inject(RetakePhotoActivity retakePhotoActivity);

    void inject(BlackListPresenter blackListPresenter);

    void inject(RefreshTokenPresenter refreshTokenPresenter);

    void inject(CodeSignaturePresenter codeSignaturePresenter);

    void inject(PengajuanDraftPresenter pengajuanDraftPresenter);

    void inject(SendSaveDraftPresenter sendSaveDraftPresenter);

    void inject(SyaratDanKetentuanPresenter syaratDanKetentuanPresenter);

    void inject(FaqFragment faqFragment);

    void inject(EditProfileActivity editProfileActivity);

    void inject(ProfilPresenter profilPresenter);

    void inject(AttachmentZoomPagerActivity attachmentZoomPagerActivity);

    void inject(ListJanjiBertemuFragment listJanjiBertemuFragment);

    void inject(ListKpmFragment listKpmFragment);

    void inject(DetailKpmActivity detailKpmActivity);

    void inject(ProcessIgnoreKpmPresenter processIgnoreKpmPresenter);

    void inject(SendEmailPoPresenter sendEmailPoPresenter);

    void inject(AutoLogoutPresenter autoLogoutPresenter);

    void inject(CheckVersionPresenter checkVersionPresenter);

    void inject(KreditmuPresenter kreditmuPresenter);

    void inject(KreditmuFragment kreditmuFragment);

    void inject(DownloadPdfPresenter pdfPoPresenter);

    void inject(SaldoKreditmuPresenter saldoKreditmuPresenter);

    void inject(ReturnRatePresenter returnRatePresenter);

    void inject(ListKreditmuFragment listKreditmuFragment);

    void inject(KreditmuListPresenter kreditmuListPresenter);

    void inject(QrPresenter qrPresenter);

    void inject(OcrPresenter ocrPresenter);

    void inject(MaskingPresenter maskingPresenter);

    void inject(CoordinatePresenter coordinatePresenter);

    void inject(FormPresenter formPresenter);

    void inject(RecomendationPresenter recomendationPresenter);

    void inject(MasterSyncNewPresenter masterSyncNewPresenter);

    void inject(CheckEfNumberPresenter checkEfNumberPresenter);

    void inject(MasterDataPresenter masterDataPresenter);

    void inject(ProductOffSupplierMappingPresenter productOffSupplierMappingPresenter);

    void inject(PilihKendaraanPresenter pilihKendaraanPresenter);

    void inject(TahunProduksiKendaraanPresenter tahunProduksiKendaraanPresenter);

    void inject(HargaAgunanKendaraanPresenter hargaAgunanKendaraanPresenter);

    void inject(HargaAgunanElcPresenter hargaAgunanElcPresenter);

    void inject(FormPengajuanNonElcActivity formPengajuanNonElcActivity);

    void inject(DetailPerhitunganKendaraanPresenter detailPerhitunganKendaraanPresenter);

    void inject(FormPengajuanActivity formPengajuanActivity);

    void inject(SearchSupplierMasterPresenter searchSupplierMasterPresenter);

    void inject(SearchMarketingSupplierPresenter searchMarketingSupplierPresenter);

    void inject(SearchProductOfferingPresenter searchProductOfferingPresenter);

    void inject(ProductOffTenorPresenter productOffTenorPresenter);

    void inject(JenisKendaraanPresenter jenisKendaraanPresenter);

    void inject(WilayahCabangPresenter wilayahCabangPresenter);

    void inject(SinkronisasiKendaraanPresenter sinkronisasiKendaraanPresenter);

    void inject(AttachmentKendaraanPresenter attachmentKendaraanPresenter);

    void inject(PerhitunganWhiteGoodsPresenter perhitunganWhiteGoodsPresenter);

    void inject(PosKreditPresenter posKreditPresenter);

    void inject(CekKodeProgramPresenter cekKodeProgramPresenter);

    void inject(KelurahanPresenter kelurahanPresenter);

    void inject(GetReferalCodePresenter getReferalCodePresenter);

    void inject(JenisPembiayaanPresenter jenisPembiayaanPresenter);

    void inject(TujuanPembiayaanPresenter tujuanPembiayaanPresenter);

    void inject(ListDraftAndSinkronisasiNonElcFragment listDraftAndSinkronisasiFragmentNonElc);

    void inject(DeleteDraftPresenter deleteDraftPresenter);

    void inject(CheckLocationPresenter checkLocationPresenter);

    void inject(CameraKTP cameraKTP);
}
