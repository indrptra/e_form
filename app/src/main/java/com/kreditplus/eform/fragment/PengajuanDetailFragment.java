package com.kreditplus.eform.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.adapter.AttachmentDetailAdapter;
import com.kreditplus.eform.adapter.PoImageAdapter;
import com.kreditplus.eform.dialog.SendEmailPoDialog;
import com.kreditplus.eform.helper.Constant;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.bus.SendEmailPo;
import com.kreditplus.eform.model.response.objecthelper.Application;
import com.kreditplus.eform.model.response.objecthelper.Asset;
import com.kreditplus.eform.presenter.DownloadPdfPresenter;
import com.kreditplus.eform.presenter.PengajuanDetailPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.SendEmailPoPresenter;
import com.kreditplus.eform.presenter.mvpview.DownloadPdfMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanDetailMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.presenter.mvpview.SendEmailPoMvpView;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itsmagic.present.permissionhelper.util.PermissionHelper;

import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

/**
 * Created by Iwan Nurdesa on 26/05/16.
 */
public class PengajuanDetailFragment extends BaseFragment implements PengajuanDetailMvpView, SendEmailPoMvpView,
        DownloadPdfMvpView {

    @Inject
    PengajuanDetailPresenter pengajuanDetailPresenter;

    // Data pribadi
    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.trPerjanjianPisahHarta)
    TableRow trPerjanjianPisahHarta;
    @BindView(R.id.tvPerjanjianPisahHarta)
    TextView tvPerjanjianPisahHarta;
    @BindView(R.id.txt_nama_lengkap)
    TextView txtNamaLengkap;
    @BindView(R.id.txt_nama_pada_identitas)
    TextView txtNamaPadaIdentitas;
    @BindView(R.id.txt_no_ktp)
    TextView txtNoKtp;
    @BindView(R.id.txt_no_kk)
    TextView txtNoKk;
    @BindView(R.id.txt_no_npwp)
    TextView txtNoNpwp;
    @BindView(R.id.txt_tanggal_terbit_ktp)
    TextView txtTanggalTerbitKtp;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_tempat_lahir)
    TextView txtTempatLahir;
    @BindView(R.id.txt_tanggal_lahir)
    TextView txtTanggalLahir;
    @BindView(R.id.txt_nama_gadis_ibu_kandung)
    TextView txtNamaGadisIbuKandung;
    @BindView(R.id.txt_pendidikan)
    TextView txtPendidikan;
    @BindView(R.id.txt_warga_negara)
    TextView txtWargaNegara;
    @BindView(R.id.txt_status_rumah)
    TextView txtStatusRumah;
    @BindView(R.id.txt_tinggal_sejak_tahun)
    TextView txtTinggalSejakTahun;
    @BindView(R.id.txt_tinggal_sejak_bulan)
    TextView txtTinggalSejakBulan;
    @BindView(R.id.txt_agama)
    TextView txtAgama;
    @BindView(R.id.txt_status_pernikahan)
    TextView txtStatusPernikahan;
    @BindView(R.id.txt_jumlah_tanggungan)
    TextView txtJumlahTanggungan;
    @BindView(R.id.txt_no_handphone)
    TextView txtNoHandphone;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.tl_pengajuan_data_pribadi)
    TableLayout tlPengajuanDataPribadi;
    @BindView(R.id.ln_data_pribadi)
    LinearLayout lnDataPribadi;
    @BindView(R.id.llBtnRetry)
    LinearLayout llBtnRetry;
    @BindView(R.id.llSecondParent)
    LinearLayout llSecondParent;

    @BindView(R.id.includeDataPasangan)
    LinearLayout includeDataPasangan;

    // Alamat pemohon tinggal
    @BindView(R.id.txt_alamat_alamat_tinggal)
    TextView txtAlamatAlamatTinggal;
    @BindView(R.id.txt_rt_alamat_tinggal)
    TextView txtRtAlamatTinggal;
    @BindView(R.id.txt_rw_alamat_tinggal)
    TextView txtRwAlamatTinggal;
    @BindView(R.id.txt_kota_alamat_tinggal)
    TextView txtKotaAlamatTinggal;
    @BindView(R.id.txt_kecamatan_alamat_tinggal)
    TextView txtKecamatanAlamatTinggal;
    @BindView(R.id.txt_kelurahan_alamat_tinggal)
    TextView txtKelurahanAlamatTinggal;
    @BindView(R.id.txt_kode_pos_alamat_tinggal)
    TextView txtKodePosAlamatTinggal;
    @BindView(R.id.txt_area_phone_alamat_tinggal)
    TextView txtAreaPhoneAlamatTinggal;
    @BindView(R.id.txt_phone_alamat_tinggal)
    TextView txtPhoneAlamatTinggal;
    @BindView(R.id.tl_pengajuan_alamat_pemohon_tinggal)
    TableLayout tlPengajuanAlamatPemohonTinggal;

    // Alamat pemohon ktp
    @BindView(R.id.txt_alamat_alamat_ktp)
    TextView txtAlamatAlamatKtp;
    @BindView(R.id.txt_rt_alamat_ktp)
    TextView txtRtAlamatKtp;
    @BindView(R.id.txt_rw_alamat_ktp)
    TextView txtRwAlamatKtp;
    @BindView(R.id.txt_kota_alamat_ktp)
    TextView txtKotaAlamatKtp;
    @BindView(R.id.txt_kecamatan_alamat_ktp)
    TextView txtKecamatanAlamatKtp;
    @BindView(R.id.txt_kelurahan_alamat_ktp)
    TextView txtKelurahanAlamatKtp;
    @BindView(R.id.txt_kode_pos_alamat_ktp)
    TextView txtKodePosAlamatKtp;
    @BindView(R.id.txt_area_phone_alamat_ktp)
    TextView txtAreaPhoneAlamatKtp;
    @BindView(R.id.txt_phone_alamat_ktp)
    TextView txtPhoneAlamatKtp;
    @BindView(R.id.tl_pengajuan_alamat_pemohon_ktp)
    TableLayout tlPengajuanAlamatPemohonKtp;
    @BindView(R.id.ln_alamat_pemohon)
    LinearLayout lnAlamatPemohon;

    //Informasi kerabat
    @BindView(R.id.txt_nama_lengkap_informasi_kerabat)
    TextView txtNamaLengkapInformasiKerabat;
    @BindView(R.id.txt_hubungan_dengan_kerabat)
    TextView txtHubunganDenganKerabat;
    @BindView(R.id.txt_alamat_informasi_kerabat)
    TextView txtAlamatInformasiKerabat;
    @BindView(R.id.txt_rt_informasi_kerabat)
    TextView txtRtInformasiKerabat;
    @BindView(R.id.txt_rw_informasi_kerabat)
    TextView txtRwInformasiKerabat;
    @BindView(R.id.txt_kota_informasi_kerabat)
    TextView txtKotaInformasiKerabat;
    @BindView(R.id.txt_kecamatan_informasi_kerabat)
    TextView txtKecamatanInformasiKerabat;
    @BindView(R.id.txt_kelurahan_informasi_kerabat)
    TextView txtKelurahanInformasiKerabat;
    @BindView(R.id.txt_kode_pos_informasi_kerabat)
    TextView txtKodePosInformasiKerabat;
    @BindView(R.id.txt_area_phone_rumah_informasi_kerabat)
    TextView txtAreaPhoneRumahInformasiKerabat;
    @BindView(R.id.txt_phone_rumah_informasi_kerabat)
    TextView txtPhoneRumahInformasiKerabat;
    @BindView(R.id.txt_area_phone_kantor_informasi_kerabat)
    TextView txtAreaPhoneKantorInformasiKerabat;
    @BindView(R.id.txt_phone_kantor_informasi_kerabat)
    TextView txtPhoneKantorInformasiKerabat;
    @BindView(R.id.txt_nomor_handphone_informasi_kerabat)
    TextView txtNomorHandphoneInformasiKerabat;
    @BindView(R.id.tl_pengajuan_informasi_kerabat)
    TableLayout tlPengajuanInformasiKerabat;
    @BindView(R.id.ln_informasi_kerabat)
    LinearLayout lnInformasiKerabat;

    // Data Pekerjaan
    @BindView(R.id.txt_nama_perusahaan_data_pekerjaan)
    TextView txtNamaPerusahaanDataPekerjaan;
    @BindView(R.id.txt_alamat_data_pekerjaan)
    TextView txtAlamatDataPekerjaan;
    @BindView(R.id.txt_rt_data_pekerjaan)
    TextView txtRtDataPekerjaan;
    @BindView(R.id.txt_rw_data_pekerjaan)
    TextView txtRwDataPekerjaan;
    @BindView(R.id.txt_kota_data_pekerjaan)
    TextView txtKotaDataPekerjaan;
    @BindView(R.id.txt_kecamatan_data_pekerjaan)
    TextView txtKecamatanDataPekerjaan;
    @BindView(R.id.txt_kelurahan_data_pekerjaan)
    TextView txtKelurahanDataPekerjaan;
    @BindView(R.id.txt_kode_pos_data_pekerjaan)
    TextView txtKodePosDataPekerjaan;
    @BindView(R.id.txt_area_phone_data_pekerjaan)
    TextView txtAreaPhoneDataPekerjaan;
    @BindView(R.id.txt_phone_data_pekerjaan)
    TextView txtPhoneDataPekerjaan;
    @BindView(R.id.trPhoneNumberDataPekerjaan)
    TableRow trPhoneNumberDataPekerjaan;
    @BindView(R.id.txtPhoneNumberDataPekerjaan)
    TextView txtPhoneNumberDataPekerjaan;
    @BindView(R.id.txt_bekerja_sejak_data_pekerjaan)
    TextView txtBekerjaSejakDataPekerjaan;
    @BindView(R.id.txt_jenis_pekerjaan_data_pekerjaan)
    TextView txtJenisPekerjaanDataPekerjaan;
    @BindView(R.id.txt_profesi_data_pekerjaan)
    TextView txtProfesiDataPekerjaan;
    @BindView(R.id.txt_job_type_data_pekerjaan)
    TextView txtJobTypeDataPekerjaan;
    @BindView(R.id.txt_job_position_data_pekerjaan)
    TextView txtJobPositionDataPekerjaan;
    @BindView(R.id.txt_industri_data_pekerjaan)
    TextView txtIndustriDataPekerjaan;
    @BindView(R.id.txt_penghasilan_tetap_data_pekerjaan)
    TextView txtPenghasilanTetapDataPekerjaan;
    @BindView(R.id.txt_penghasilan_lain_data_pekerjaan)
    TextView txtPenghasilanLainDataPekerjaan;
    @BindView(R.id.txt_penghasilan_pasangan_data_pekerjaan)
    TextView txtPenghasilanPasanganDataPekerjaan;
    @BindView(R.id.txt_biaya_hidup_data_pekerjaan)
    TextView txtBiayaHidupDataPekerjaan;
    @BindView(R.id.txt_is_affiliate_with_pp_data_pekerjaan)
    TextView txtIsAffiliateWithPpDataPekerjaan;
    @BindView(R.id.tl_pengajuan_data_pekerjaan)
    TableLayout tlPengajuanDataPekerjaan;
    @BindView(R.id.ln_data_pekerjaan)
    LinearLayout lnDataPekerjaan;

    // Detail Asset
    @BindView(R.id.txt_supplier)
    TextView txtSupplier;
    @BindView(R.id.txt_bank_account)
    TextView txtBankAccount;
    @BindView(R.id.txt_marketing_supplier)
    TextView txtMarketingSupplier;
    @BindView(R.id.ln_asset_detail_product_container)
    LinearLayout lnAssetDetailProductContainer;
    @BindView(R.id.tl_pengajuan_detail_asset)
    TableLayout tlPengajuanDetailAsset;
    @BindView(R.id.ln_detail_asset)
    LinearLayout lnDetailAsset;

    // Data Asuransi
    @BindView(R.id.tr_premi_asuransi)
    TableRow trPremiAsuransi;
    @BindView(R.id.tr_premi_asuransi_agunan)
    TableRow trPremiAsuransiAgunan;
    @BindView(R.id.tr_premi_asuransi_jiwa)
    TableRow trPremiAsuransiJiwa;
    @BindView(R.id.tr_is_personal_accident)
    TableRow trIsPersonalAccident;
    @BindView(R.id.txt_asuransi_agunan)
    TextView txtAsuransiAgunan;
    @BindView(R.id.txt_premi_asuransi)
    TextView txtPremiAsuransi;
    @BindView(R.id.txt_premi_asuransi_agunan)
    TextView txtPremiAsuransiAgunan;
    @BindView(R.id.txt_premi_asuransi_jiwa)
    TextView txtPremiAsuransiJiwa;
    @BindView(R.id.txt_is_personal_accident)
    TextView txtIsPersonalAccident;
    @BindView(R.id.tl_pengajuan_data_asuransi)
    TableLayout tlPengajuanDataAsuransi;
    @BindView(R.id.card_view)
    CardView cardView;

    // Data Perhitungan elektronik
    @BindView(R.id.txt_purchase_price_data_perhitungan_elektronik)
    TextView txtPurchasePriceDataPerhitunganElektronik;
    @BindView(R.id.txt_discount_data_perhitungan_elektronik)
    TextView txtDiscountDataPerhitunganElektronik;
    @BindView(R.id.txt_dp_data_perhitungan_elektronik)
    TextView txtDpDataPerhitunganElektronik;
    @BindView(R.id.txt_ntf_data_perhitungan_elektronik)
    TextView txtNtfDataPerhitunganElektronik;
    @BindView(R.id.txt_flate_rate_data_perhitungan_elektronik)
    TextView txtFlateRateDataPerhitunganElektronik;
    @BindView(R.id.txt_biaya_lainnya_data_perhitungan_elektronik)
    TextView txtBiayaLainnyaDataPerhitunganElektronik;
    @BindView(R.id.txt_jumlah_pembiayaan_data_perhitungan_elektronik)
    TextView txtJumlahPembiayaanDataPerhitunganElektronik;
    @BindView(R.id.txt_bunga_pembiayaan_data_perhitungan_elektronik)
    TextView txtBungaPembiayaanDataPerhitunganElektronik;
    @BindView(R.id.txt_total_pinjaman_data_perhitungan_elektronik)
    TextView txtTotalPinjamanDataPerhitunganElektronik;
    @BindView(R.id.txt_angsuran_data_perhitungan_elektronik)
    TextView txtAngsuranDataPerhitunganElektronik;
    @BindView(R.id.txt_biaya_administrasi_data_perhitungan_elektronik)
    TextView txtBiayaAdministrasiDataPerhitunganElektronik;
    @BindView(R.id.txt_setor_pertama_data_perhitungan_elektronik)
    TextView txtSetorPertamaDataPerhitunganElektronik;
    @BindView(R.id.txt_angsuran_pertama_data_perhitungan_elektronik)
    TextView txtAngsuranPertamaDataPerhitunganElektronik;
    @BindView(R.id.txt_in_refund_subsidi_data_perhitungan_elektronik)
    TextView txtInRefundSubsidiDataPerhitunganElektronik;

    // Data Perhitungan kendaraan
    @BindView(R.id.trTenorKendaraan)
    TableRow trTenorKendaraan;
    @BindView(R.id.tvTenorKendaraan)
    TextView tvTenorKendaraan;
    @BindView(R.id.tvHargaAgunan)
    TextView tvHargaAgunan;
    @BindView(R.id.tvTitlePokokPembiayaan)
    TextView tvTitlePokokPembiayaan;
    @BindView(R.id.tvPinjamanKonsumen)
    TextView tvPinjamanKonsumen;
    @BindView(R.id.tvDownpayment)
    TextView tvDownpayment;
    @BindView(R.id.tvBiayaAdministrasi)
    TextView tvBiayaAdministrasi;
    @BindView(R.id.tvBiayaProvisi)
    TextView tvBiayaProvisi;
    @BindView(R.id.trNonPsaBiayaProvisi)
    TableRow trNonPsaBiayaProvisi;
    @BindView(R.id.trNonPsaDownpayment)
    TableRow trNonPsaDownpayment;
    @BindView(R.id.tvPremiAsuransiAgunan)
    TextView tvPremiAsuransiAgunan;
    @BindView(R.id.tvPremiAsuransiJiwa)
    TextView tvPremiAsuransiJiwa;
    @BindView(R.id.tvJumlahPembiayaan)
    TextView tvJumlahPembiayaan;
    @BindView(R.id.tvTotalBungaPembiayaan)
    TextView tvTotalBungaPembiayaan;
    @BindView(R.id.tvTotalPinjaman)
    TextView tvTotalPinjaman;
    @BindView(R.id.tvAngsuranPerbulan)
    TextView tvAngsuranPerbulan;
    @BindView(R.id.tvFlatTahun)
    TextView tvFlatTahun;
    @BindView(R.id.trPnbpFidusia)
    TableRow trPnbpFidusia;
    @BindView(R.id.tvPnbpFidusia)
    TextView tvPnbpFidusia;
    @BindView(R.id.tvBiayaPengurusanStnk)
    TextView tvBiayaPengurusanStnk;
    @BindView(R.id.trNonPsaBiayaPengurusanStnk)
    TableRow trNonPsaBiayaPengurusanStnk;
    @BindView(R.id.tvBiayaNotaris)
    TextView tvBiayaNotaris;
    @BindView(R.id.trNonPsaBiayaNotaris)
    TableRow trNonPsaBiayaNotaris;
    @BindView(R.id.tvBiayaSurvey)
    TextView tvBiayaSurvey;
    @BindView(R.id.trNonPsaBiayaSurvey)
    TableRow trNonPsaBiayaSurvey;
    @BindView(R.id.tl_pengajuan_data_perhitungan)
    TableLayout tlPengajuanDataPerhitungan;

    //Attachment
    @BindView(R.id.rv_po_attachment)
    RecyclerView rvPOAttachment;

    // Detail product
    @BindView(R.id.txt_product_offering)
    TextView txtProductOffering;
    @BindView(R.id.txt_jumlah_asset)
    TextView txtJumlahAsset;
    @BindView(R.id.trTenorElc)
    TableRow trTenorElc;
    @BindView(R.id.txt_tenor)
    TextView txtTenor;
    @BindView(R.id.txt_pos)
    TextView txtPos;
    @BindView(R.id.tl_pengajuan_detail_product)
    TableLayout tlPengajuanDetailProduct;
    @BindView(R.id.txt_error_api)
    TextView txtError;

    // Data Pasangan
    @BindView(R.id.txt_nama_pasangan)
    TextView txtNamaPasangan;
    @BindView(R.id.txt_no_ktp_pasangan)
    TextView txtNoKtpPasangan;
    @BindView(R.id.txt_hp_pasangan)
    TextView txtHpPasangan;
    @BindView(R.id.trHpPasangan)
    TableRow trHpPasangan;
    @BindView(R.id.txt_tempat_lahir_pasangan)
    TextView txtTempatLahirPasangan;
    @BindView(R.id.txt_tanggal_lahir_pasangan)
    TextView txtTanggalLahirPasangan;
    @BindView(R.id.tvPasanganTeleponRumah)
    TextView tvPasanganTeleponRumah;
    @BindView(R.id.tvPasanganTeleponPerusahaan)
    TextView tvPasanganTeleponPerusahaan;
    @BindView(R.id.tvPasanganAlamat)
    TextView tvPasanganAlamat;
    @BindView(R.id.tvPasanganKota)
    TextView tvPasanganKota;
    @BindView(R.id.tvPasanganProfesi)
    TextView tvPasanganProfesi;
    @BindView(R.id.tvPasanganJobType)
    TextView tvPasanganJobType;
    @BindView(R.id.tvPasanganJobPosition)
    TextView tvPasanganJobPosition;
    @BindView(R.id.tvPasanganIndustri)
    TextView tvPasanganIndustri;
    @BindView(R.id.tvPasanganPegawai)
    TextView tvPasanganPegawai;
    @BindView(R.id.tvPasanganNamaPerusahaan)
    TextView tvPasanganNamaPerusahaan;
    @BindView(R.id.tvPasanganNamaIbu)
    TextView tvPasanganNamaIbu;
    @BindView(R.id.maritalDetailNonELC)
    TableLayout maritalDetailNonELC;

    // Data Kartu Kredit
    @BindView(R.id.txt_nama_bank_kartu_kredit)
    TextView txtNamaBankKartuKredit;
    @BindView(R.id.txt_no_kartu_kredit)
    TextView txtNoKartuKredit;
    @BindView(R.id.txt_jenis_kartu_kredit)
    TextView txtJenisKartuKredit;
    @BindView(R.id.txt_limit_kartu_kredit)
    TextView txtLimitKartuKredit;
    @BindView(R.id.txt_kadaluarsa_kartu_tahun_kartu_kredit)
    TextView txtKadaluarsaTahunKartuKredit;
    @BindView(R.id.txt_kadaluarsa_kartu_bulan_kartu_kredit)
    TextView txtKadaluarsaBulanKartuKredit;

    // Data Kartu Membership
    @BindView(R.id.txt_no_membership)
    TextView txtNoMembership;
    @BindView(R.id.txt_tanggal_efektif_membership)
    TextView txtTanggalEfektifMembership;
    @BindView(R.id.txt_tanggal_expired_membership)
    TextView txtTanggalExpiredMembership;

    // Agunan
    @BindView(R.id.tvWilayahKendaraan)
    TextView tvWilayahKendaraan;
    @BindView(R.id.tvCabangKendaraan)
    TextView tvCabangKendaraan;
    @BindView(R.id.tvJenisKendaraan)
    TextView tvJenisKendaraan;
    @BindView(R.id.tvMerkKendaraan)
    TextView tvMerkKendaraan;
    @BindView(R.id.tvTipeKendaraan)
    TextView tvTipeKendaraan;
    @BindView(R.id.tvTahunKendaraan)
    TextView tvTahunKendaraan;
    @BindView(R.id.tvWarnaKendaraan)
    TextView tvWarnaKendaraan;
    @BindView(R.id.tvIsiSilinder)
    TextView tvIsiSilinder;
    @BindView(R.id.tvNoPolisi)
    TextView tvNoPolisi;
    @BindView(R.id.tvNoRangka)
    TextView tvNoRangka;
    @BindView(R.id.tvNoMesin)
    TextView tvNoMesin;
    @BindView(R.id.tvBpkbAtasNama)
    TextView tvBpkbAtasNama;
    @BindView(R.id.tvNamaBpkb)
    TextView tvNamaBpkb;
    @BindView(R.id.tvMasaBerlakuStnk)
    TextView tvMasaBerlakuStnk;
    @BindView(R.id.tvMasaBerlakuPajakStnk)
    TextView tvMasaBerlakuPajakStnk;

    //Keterangan
    @BindView(R.id.txt_ketrangan)
    TextView txtketerangan;

    //Keterangan PO
    @BindView(R.id.txt_keterangan_po)
    TextView txtKeteranganPo;
    @BindView(R.id.txt_tanggal_po)
    TextView txtTanggalPo;
    //    REKOMENDASI
    @BindView(R.id.view_recomendations)
    LinearLayout viewRecomendations;
    @BindView(R.id.txt_is_rekomendasi)
    TextView txtIsRekomendasi;
    @BindView(R.id.txt_rekomendasi)
    TextView txtRekomendasi;
    @BindView(R.id.txt_keterangan_rekomendasi)
    TextView txtKeteranganRekomendasi;

    //OVD
    @BindView(R.id.tv_ovd)
    TextView txtOvd;

    @BindView(R.id.txt_status_customer)
    TextView txtStatusCustomer;
    @BindView(R.id.tr_alasan)
    TableRow trAlasan;
    @BindView(R.id.txt_alasan_customer)
    TextView txtAlasanCustomer;
    @BindView(R.id.tl_status_customer)
    TableLayout tlStatusCustomer;
    @BindView(R.id.card_view_status_customer)
    CardView cardViewStatusCustomer;
    @BindView(R.id.ln_data_keterangan)
    LinearLayout lnDataKeterangan;
    @BindView(R.id.includeDataKartuKredit)
    LinearLayout includeDataKartuKredit;
    @BindView(R.id.includeDataKartuMembership)
    LinearLayout includeDataKartuMembership;
    @BindView(R.id.includeDataAsset)
    LinearLayout includeDataAsset;
    @BindView(R.id.includeDataAgunan)
    LinearLayout includeDataAgunan;
    @BindView(R.id.view_perhitungan_elektronik)
    LinearLayout lnPerhitunganElektronik;
    @BindView(R.id.view_perhitungan_kendaraan)
    LinearLayout lnPerhitunganKendaraan;

    @BindView(R.id.tvCamera1)
    TextView tvCamera1;
    @BindView(R.id.pb_image_1)
    ProgressBar pbImage1;
    @BindView(R.id.img_camera_1)
    ImageView imgCamera1;
    @BindView(R.id.img_take_picture_1)
    ImageView imgTakePicture1;
    @BindView(R.id.img_delete_picture_1)
    ImageView imgDeletePicture1;
    @BindView(R.id.btn_muat_ulang_1)
    Button btnMuatUlang1;
    @BindView(R.id.ln_take_foto_1)
    LinearLayout lnTakeFoto1;
    @BindView(R.id.tvCamera2)
    TextView tvCamera2;
    @BindView(R.id.pb_image_2)
    ProgressBar pbImage2;
    @BindView(R.id.img_camera_2)
    ImageView imgCamera2;
    @BindView(R.id.img_take_picture_2)
    ImageView imgTakePicture2;
    @BindView(R.id.img_delete_picture_2)
    ImageView imgDeletePicture2;
    @BindView(R.id.btn_muat_ulang_2)
    Button btnMuatUlang2;
    @BindView(R.id.ln_take_foto_2)
    LinearLayout lnTakeFoto2;
    @BindView(R.id.tvCamera3)
    TextView tvCamera3;
    @BindView(R.id.pb_image_3)
    ProgressBar pbImage3;
    @BindView(R.id.img_camera_3)
    ImageView imgCamera3;
    @BindView(R.id.img_take_picture_3)
    ImageView imgTakePicture3;
    @BindView(R.id.img_delete_picture_3)
    ImageView imgDeletePicture3;
    @BindView(R.id.btn_muat_ulang_3)
    Button btnMuatUlang3;
    @BindView(R.id.ln_take_foto_3)
    LinearLayout lnTakeFoto3;
    @BindView(R.id.tvCamera4)
    TextView tvCamera4;
    @BindView(R.id.pb_image_4)
    ProgressBar pbImage4;
    @BindView(R.id.img_camera_4)
    ImageView imgCamera4;
    @BindView(R.id.img_take_picture_4)
    ImageView imgTakePicture4;
    @BindView(R.id.img_delete_picture_4)
    ImageView imgDeletePicture4;
    @BindView(R.id.btn_muat_ulang_4)
    Button btnMuatUlang4;
    @BindView(R.id.ln_take_foto_4)
    LinearLayout lnTakeFoto4;
    @BindView(R.id.tvCamera5)
    TextView tvCamera5;
    @BindView(R.id.pb_image_5)
    ProgressBar pbImage5;
    @BindView(R.id.img_camera_5)
    ImageView imgCamera5;
    @BindView(R.id.img_take_picture_5)
    ImageView imgTakePicture5;
    @BindView(R.id.img_delete_picture_5)
    ImageView imgDeletePicture5;
    @BindView(R.id.btn_muat_ulang_5)
    Button btnMuatUlang5;
    @BindView(R.id.ln_take_foto_5)
    LinearLayout lnTakeFoto5;
    @BindView(R.id.tvCamera6)
    TextView tvCamera6;
    @BindView(R.id.pb_image_6)
    ProgressBar pbImage6;
    @BindView(R.id.img_camera_6)
    ImageView imgCamera6;
    @BindView(R.id.img_take_picture_6)
    ImageView imgTakePicture6;
    @BindView(R.id.img_delete_picture_6)
    ImageView imgDeletePicture6;
    @BindView(R.id.btn_muat_ulang_6)
    Button btnMuatUlang6;
    @BindView(R.id.ln_take_foto_6)
    LinearLayout lnTakeFoto6;
    @BindView(R.id.tvCamera7)
    TextView tvCamera7;
    @BindView(R.id.pb_image_7)
    ProgressBar pbImage7;
    @BindView(R.id.img_camera_7)
    ImageView imgCamera7;
    @BindView(R.id.img_take_picture_7)
    ImageView imgTakePicture7;
    @BindView(R.id.img_delete_picture_7)
    ImageView imgDeletePicture7;
    @BindView(R.id.btn_muat_ulang_7)
    Button btnMuatUlang7;
    @BindView(R.id.ln_take_foto_7)
    LinearLayout lnTakeFoto7;
    @BindView(R.id.tvCamera8)
    TextView tvCamera8;
    @BindView(R.id.pb_image_8)
    ProgressBar pbImage8;
    @BindView(R.id.img_camera_8)
    ImageView imgCamera8;
    @BindView(R.id.img_take_picture_8)
    ImageView imgTakePicture8;
    @BindView(R.id.img_delete_picture_8)
    ImageView imgDeletePicture8;
    @BindView(R.id.btn_muat_ulang_8)
    Button btnMuatUlang8;
    @BindView(R.id.ln_take_foto_8)
    LinearLayout lnTakeFoto8;
    @BindView(R.id.tvCamera9)
    TextView tvCamera9;
    @BindView(R.id.pb_image_9)
    ProgressBar pbImage9;
    @BindView(R.id.img_camera_9)
    ImageView imgCamera9;
    @BindView(R.id.img_take_picture_9)
    ImageView imgTakePicture9;
    @BindView(R.id.img_delete_picture_9)
    ImageView imgDeletePicture9;
    @BindView(R.id.btn_muat_ulang_9)
    Button btnMuatUlang9;
    @BindView(R.id.ln_take_foto_9)
    LinearLayout lnTakeFoto9;
    @BindView(R.id.tvCamera10)
    TextView tvCamera10;
    @BindView(R.id.pb_image_10)
    ProgressBar pbImage10;
    @BindView(R.id.img_camera_10)
    ImageView imgCamera10;
    @BindView(R.id.img_take_picture_10)
    ImageView imgTakePicture10;
    @BindView(R.id.img_delete_picture_10)
    ImageView imgDeletePicture10;
    @BindView(R.id.btn_muat_ulang_10)
    Button btnMuatUlang10;
    @BindView(R.id.ln_take_foto_10)
    LinearLayout lnTakeFoto10;
    @BindView(R.id.tvCamera11)
    TextView tvCamera11;
    @BindView(R.id.pb_image_11)
    ProgressBar pbImage11;
    @BindView(R.id.img_camera_11)
    ImageView imgCamera11;
    @BindView(R.id.img_take_picture_11)
    ImageView imgTakePicture11;
    @BindView(R.id.img_delete_picture_11)
    ImageView imgDeletePicture11;
    @BindView(R.id.btn_muat_ulang_11)
    Button btnMuatUlang11;
    @BindView(R.id.ln_take_foto_11)
    LinearLayout lnTakeFoto11;
    @BindView(R.id.tvCamera12)
    TextView tvCamera12;
    @BindView(R.id.pb_image_12)
    ProgressBar pbImage12;
    @BindView(R.id.img_camera_12)
    ImageView imgCamera12;
    @BindView(R.id.img_take_picture_12)
    ImageView imgTakePicture12;
    @BindView(R.id.img_delete_picture_12)
    ImageView imgDeletePicture12;
    @BindView(R.id.btn_muat_ulang_12)
    Button btnMuatUlang12;
    @BindView(R.id.ln_take_foto_12)
    LinearLayout lnTakeFoto12;
    @BindView(R.id.tvCamera13)
    TextView tvCamera13;
    @BindView(R.id.pb_image_13)
    ProgressBar pbImage13;
    @BindView(R.id.img_camera_13)
    ImageView imgCamera13;
    @BindView(R.id.img_take_picture_13)
    ImageView imgTakePicture13;
    @BindView(R.id.img_delete_picture_13)
    ImageView imgDeletePicture13;
    @BindView(R.id.btn_muat_ulang_13)
    Button btnMuatUlang13;
    @BindView(R.id.ln_take_foto_13)
    LinearLayout lnTakeFoto13;
    @BindView(R.id.tvCamera14)
    TextView tvCamera14;
    @BindView(R.id.pb_image_14)
    ProgressBar pbImage14;
    @BindView(R.id.img_camera_14)
    ImageView imgCamera14;
    @BindView(R.id.img_take_picture_14)
    ImageView imgTakePicture14;
    @BindView(R.id.img_delete_picture_14)
    ImageView imgDeletePicture14;
    @BindView(R.id.btn_muat_ulang_14)
    Button btnMuatUlang14;
    @BindView(R.id.ln_take_foto_14)
    LinearLayout lnTakeFoto14;
    @BindView(R.id.tvCamera15)
    TextView tvCamera15;
    @BindView(R.id.pb_image_15)
    ProgressBar pbImage15;
    @BindView(R.id.img_camera_15)
    ImageView imgCamera15;
    @BindView(R.id.img_take_picture_15)
    ImageView imgTakePicture15;
    @BindView(R.id.img_delete_picture_15)
    ImageView imgDeletePicture15;
    @BindView(R.id.btn_muat_ulang_15)
    Button btnMuatUlang15;
    @BindView(R.id.ln_take_foto_15)
    LinearLayout lnTakeFoto15;
    @BindView(R.id.tvCamera16)
    TextView tvCamera16;
    @BindView(R.id.pb_image_16)
    ProgressBar pbImage16;
    @BindView(R.id.img_camera_16)
    ImageView imgCamera16;
    @BindView(R.id.img_take_picture_16)
    ImageView imgTakePicture16;
    @BindView(R.id.img_delete_picture_16)
    ImageView imgDeletePicture16;
    @BindView(R.id.btn_muat_ulang_16)
    Button btnMuatUlang16;
    @BindView(R.id.ln_take_foto_16)
    LinearLayout lnTakeFoto16;
    @BindView(R.id.tvCamera17)
    TextView tvCamera17;
    @BindView(R.id.pb_image_17)
    ProgressBar pbImage17;
    @BindView(R.id.img_camera_17)
    ImageView imgCamera17;
    @BindView(R.id.img_take_picture_17)
    ImageView imgTakePicture17;
    @BindView(R.id.img_delete_picture_17)
    ImageView imgDeletePicture17;
    @BindView(R.id.btn_muat_ulang_17)
    Button btnMuatUlang17;
    @BindView(R.id.ln_take_foto_17)
    LinearLayout lnTakeFoto17;
    @BindView(R.id.tvCamera18)
    TextView tvCamera18;
    @BindView(R.id.pb_image_18)
    ProgressBar pbImage18;
    @BindView(R.id.img_camera_18)
    ImageView imgCamera18;
    @BindView(R.id.img_take_picture_18)
    ImageView imgTakePicture18;
    @BindView(R.id.img_delete_picture_18)
    ImageView imgDeletePicture18;
    @BindView(R.id.btn_muat_ulang_18)
    Button btnMuatUlang18;
    @BindView(R.id.ln_take_foto_18)
    LinearLayout lnTakeFoto18;
    @BindView(R.id.tvCamera19)
    TextView tvCamera19;
    @BindView(R.id.pb_image_19)
    ProgressBar pbImage19;
    @BindView(R.id.img_camera_19)
    ImageView imgCamera19;
    @BindView(R.id.img_take_picture_19)
    ImageView imgTakePicture19;
    @BindView(R.id.img_delete_picture_19)
    ImageView imgDeletePicture19;
    @BindView(R.id.btn_muat_ulang_19)
    Button btnMuatUlang19;
    @BindView(R.id.ln_take_foto_19)
    LinearLayout lnTakeFoto19;
    @BindView(R.id.tvCamera20)
    TextView tvCamera20;
    @BindView(R.id.pb_image_20)
    ProgressBar pbImage20;
    @BindView(R.id.img_camera_20)
    ImageView imgCamera20;
    @BindView(R.id.img_take_picture_20)
    ImageView imgTakePicture20;
    @BindView(R.id.img_delete_picture_20)
    ImageView imgDeletePicture20;
    @BindView(R.id.btn_muat_ulang_20)
    Button btnMuatUlang20;
    @BindView(R.id.ln_take_foto_20)
    LinearLayout lnTakeFoto20;
    @BindView(R.id.tvCamera21)
    TextView tvCamera21;
    @BindView(R.id.pb_image_21)
    ProgressBar pbImage21;
    @BindView(R.id.img_camera_21)
    ImageView imgCamera21;
    @BindView(R.id.img_take_picture_21)
    ImageView imgTakePicture21;
    @BindView(R.id.img_delete_picture_21)
    ImageView imgDeletePicture21;
    @BindView(R.id.btn_muat_ulang_21)
    Button btnMuatUlang21;
    @BindView(R.id.ln_take_foto_21)
    LinearLayout lnTakeFoto21;
    @BindView(R.id.tvCamera22)
    TextView tvCamera22;
    @BindView(R.id.pb_image_22)
    ProgressBar pbImage22;
    @BindView(R.id.img_camera_22)
    ImageView imgCamera22;
    @BindView(R.id.img_take_picture_22)
    ImageView imgTakePicture22;
    @BindView(R.id.img_delete_picture_22)
    ImageView imgDeletePicture22;
    @BindView(R.id.btn_muat_ulang_22)
    Button btnMuatUlang22;
    @BindView(R.id.ln_take_foto_22)
    LinearLayout lnTakeFoto22;
    @BindView(R.id.tvCamera23)
    TextView tvCamera23;
    @BindView(R.id.pb_image_23)
    ProgressBar pbImage23;
    @BindView(R.id.img_camera_23)
    ImageView imgCamera23;
    @BindView(R.id.img_take_picture_23)
    ImageView imgTakePicture23;
    @BindView(R.id.img_delete_picture_23)
    ImageView imgDeletePicture23;
    @BindView(R.id.btn_muat_ulang_23)
    Button btnMuatUlang23;
    @BindView(R.id.ln_take_foto_23)
    LinearLayout lnTakeFoto23;
    @BindView(R.id.tvCamera24)
    TextView tvCamera24;
    @BindView(R.id.pb_image_24)
    ProgressBar pbImage24;
    @BindView(R.id.img_camera_24)
    ImageView imgCamera24;
    @BindView(R.id.img_take_picture_24)
    ImageView imgTakePicture24;
    @BindView(R.id.img_delete_picture_24)
    ImageView imgDeletePicture24;
    @BindView(R.id.btn_muat_ulang_24)
    Button btnMuatUlang24;
    @BindView(R.id.ln_take_foto_24)
    LinearLayout lnTakeFoto24;
    @BindView(R.id.tvCamera25)
    TextView tvCamera25;
    @BindView(R.id.pb_image_25)
    ProgressBar pbImage25;
    @BindView(R.id.img_camera_25)
    ImageView imgCamera25;
    @BindView(R.id.img_take_picture_25)
    ImageView imgTakePicture25;
    @BindView(R.id.img_delete_picture_25)
    ImageView imgDeletePicture25;
    @BindView(R.id.btn_muat_ulang_25)
    Button btnMuatUlang25;
    @BindView(R.id.ln_take_foto_25)
    LinearLayout lnTakeFoto25;
    @BindView(R.id.ln_attachment)
    LinearLayout lnAttachment;
    @BindView(R.id.hsv)
    HorizontalScrollView hsv;
    @BindView(R.id.progress_bar)
    ProgressBar pBar;

    @BindViews({R.id.img_take_picture_1,
            R.id.img_take_picture_2,
            R.id.img_take_picture_3,
            R.id.img_take_picture_4,
            R.id.img_take_picture_5,
            R.id.img_take_picture_6,
            R.id.img_take_picture_7,
            R.id.img_take_picture_8,
            R.id.img_take_picture_9,
            R.id.img_take_picture_10,
            R.id.img_take_picture_11,
            R.id.img_take_picture_12,
            R.id.img_take_picture_13,
            R.id.img_take_picture_14,
            R.id.img_take_picture_15,
            R.id.img_take_picture_16,
            R.id.img_take_picture_17,
            R.id.img_take_picture_18,
            R.id.img_take_picture_19,
            R.id.img_take_picture_20,
            R.id.img_take_picture_21,
            R.id.img_take_picture_22,
            R.id.img_take_picture_23,
            R.id.img_take_picture_24,
            R.id.img_take_picture_25})
    List<View> viewTakeImages;

    @BindViews({
            R.id.img_take_picture_1,
            R.id.img_take_picture_2,
            R.id.img_take_picture_3,
            R.id.img_take_picture_4,
            R.id.img_take_picture_6,
            R.id.img_take_picture_7,
            R.id.img_take_picture_8,
            R.id.img_take_picture_9,
            R.id.img_take_picture_10,
            R.id.img_take_picture_11,
            R.id.img_take_picture_12,
            R.id.img_take_picture_13,
            R.id.img_take_picture_14,
            R.id.img_take_picture_15,
            R.id.img_take_picture_16,
            R.id.img_take_picture_17,
            R.id.img_take_picture_18,
            R.id.img_take_picture_19,
            R.id.img_take_picture_20,
            R.id.img_take_picture_21,
            R.id.img_take_picture_22,
            R.id.img_take_picture_23,
            R.id.img_take_picture_24,
            R.id.img_take_picture_25})
    List<View> viewTakeImagesVehicleFirst;

    @BindViews({R.id.img_delete_picture_1,
            R.id.img_delete_picture_2,
            R.id.img_delete_picture_3,
            R.id.img_delete_picture_4,
            R.id.img_delete_picture_5,
            R.id.img_delete_picture_6,
            R.id.img_delete_picture_7,
            R.id.img_delete_picture_8,
            R.id.img_delete_picture_9,
            R.id.img_delete_picture_10,
            R.id.img_delete_picture_11,
            R.id.img_delete_picture_12,
            R.id.img_delete_picture_13,
            R.id.img_delete_picture_14,
            R.id.img_delete_picture_15,
            R.id.img_delete_picture_16,
            R.id.img_delete_picture_17,
            R.id.img_delete_picture_18,
            R.id.img_delete_picture_19,
            R.id.img_delete_picture_20,
            R.id.img_delete_picture_21,
            R.id.img_delete_picture_22,
            R.id.img_delete_picture_23,
            R.id.img_delete_picture_24,
            R.id.img_delete_picture_25})
    List<View> viewDeleteImages;

    @BindViews({R.id.pb_image_1,
            R.id.pb_image_2,
            R.id.pb_image_3,
            R.id.pb_image_4,
            R.id.pb_image_5,
            R.id.pb_image_6,
            R.id.pb_image_7,
            R.id.pb_image_8,
            R.id.pb_image_9,
            R.id.pb_image_10,
            R.id.pb_image_11,
            R.id.pb_image_12,
            R.id.pb_image_13,
            R.id.pb_image_14,
            R.id.pb_image_15,
            R.id.pb_image_16,
            R.id.pb_image_17,
            R.id.pb_image_18,
            R.id.pb_image_19,
            R.id.pb_image_20,
            R.id.pb_image_21,
            R.id.pb_image_22,
            R.id.pb_image_23,
            R.id.pb_image_24,
            R.id.pb_image_25})
    List<View> viewPbImages;

    @BindViews({R.id.img_camera_1,
            R.id.img_camera_2,
            R.id.img_camera_3,
            R.id.img_camera_4,
            R.id.img_camera_5,
            R.id.img_camera_6,
            R.id.img_camera_7,
            R.id.img_camera_8,
            R.id.img_camera_9,
            R.id.img_camera_10,
            R.id.img_camera_11,
            R.id.img_camera_12,
            R.id.img_camera_13,
            R.id.img_camera_14,
            R.id.img_camera_15,
            R.id.img_camera_16,
            R.id.img_camera_17,
            R.id.img_camera_18,
            R.id.img_camera_19,
            R.id.img_camera_20,
            R.id.img_camera_21,
            R.id.img_camera_22,
            R.id.img_camera_23,
            R.id.img_camera_24,
            R.id.img_camera_25})
    List<View> viewCameras;

    @BindViews({R.id.btn_muat_ulang_1,
            R.id.btn_muat_ulang_2,
            R.id.btn_muat_ulang_3,
            R.id.btn_muat_ulang_4,
            R.id.btn_muat_ulang_5,
            R.id.btn_muat_ulang_6,
            R.id.btn_muat_ulang_7,
            R.id.btn_muat_ulang_8,
            R.id.btn_muat_ulang_9,
            R.id.btn_muat_ulang_10,
            R.id.btn_muat_ulang_11,
            R.id.btn_muat_ulang_12,
            R.id.btn_muat_ulang_13,
            R.id.btn_muat_ulang_14,
            R.id.btn_muat_ulang_15,
            R.id.btn_muat_ulang_16,
            R.id.btn_muat_ulang_17,
            R.id.btn_muat_ulang_18,
            R.id.btn_muat_ulang_19,
            R.id.btn_muat_ulang_20,
            R.id.btn_muat_ulang_21,
            R.id.btn_muat_ulang_22,
            R.id.btn_muat_ulang_23,
            R.id.btn_muat_ulang_24,
            R.id.btn_muat_ulang_25})
    List<View> viewRefreshImages;

    private String token;
    private String applicationID;
    private PoImageAdapter poImageAdapter;
    private List<String> attachmentList = new ArrayList<>();
    private List<String> poList = new ArrayList<>();
    private String cro;

    private File filePdf;
    private Application application;

    private PermissionHelper mPermissionHelper;
    private DownloadPdfPresenter mDownloadPdfPresenter;

    private ProgressDialog progressDialog;

    private SendEmailPoPresenter mSendEmailPoPresenter;
    private boolean isClickable;
    private boolean isApprove = false;
    private String typePdf;
    private String isRecomendation;
    private String pdfUrl, pdfName;
    private int numberOfImages;
    private int countImage;
    private boolean isImageError;
    private ArrayList<String> tmpAttachments;
    private Map<Integer, File> mHashMapAttachmentFiles = new LinkedHashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();

        cro = sharedPreferences.getString(getResources().getString(R.string.sharedpref_cro), "");

        if (bundle != null) {
            applicationID = bundle.getString("app_id");
            if (bundle.getString("app_status") != null && "approve".equalsIgnoreCase(bundle.getString("app_status")))
                isApprove = true;
        }

        mSendEmailPoPresenter = new SendEmailPoPresenter();
        mSendEmailPoPresenter.attachView(this);
        mDownloadPdfPresenter = new DownloadPdfPresenter();
        mDownloadPdfPresenter.attachView(this);

        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        poImageAdapter = new PoImageAdapter(getActivity(), poList);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait");
        progressDialog.setTitle("");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengajuan_detail, container, false);
        init(view);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("PENGAJUAN DETAIL");
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (cro.equals("cro")) {
            initTitleAttachmentELC();
            lnTakeFoto5.setVisibility(View.GONE);
            llSecondParent.setVisibility(View.GONE);
        } else {
            initTitleAttachment();
            llSecondParent.setVisibility(View.VISIBLE);
            lnTakeFoto15.setVisibility(View.GONE); // hide Slip gaji
            lnTakeFoto16.setVisibility(View.GONE); // hide asset tampak kanan
            lnTakeFoto17.setVisibility(View.GONE); // hide asset tampak kiri
        }


        if (cro.equalsIgnoreCase("cro")) {
            trPerjanjianPisahHarta.setVisibility(View.GONE);
            lnPerhitunganKendaraan.setVisibility(View.GONE);
        } else {
            includeDataKartuKredit.setVisibility(View.GONE);
            includeDataKartuMembership.setVisibility(View.GONE);
            includeDataAsset.setVisibility(View.GONE);
            trPremiAsuransi.setVisibility(View.GONE);
            trPremiAsuransiAgunan.setVisibility(View.GONE);
            trPremiAsuransiJiwa.setVisibility(View.GONE);
            trIsPersonalAccident.setVisibility(View.GONE);
            lnPerhitunganElektronik.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManagerPo = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvPOAttachment.setLayoutManager(linearLayoutManagerPo);
        rvPOAttachment.setAdapter(poImageAdapter);
        allPermission();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pengajuanDetailPresenter.detachView();
        mSendEmailPoPresenter.detachView();
        mDownloadPdfPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_retry)
    public void retry() {
        pengajuanDetailPresenter.loadPengajuanDetail(token, applicationID);
    }

    @OnClick({
            R.id.btn_muat_ulang_1,
            R.id.btn_muat_ulang_2,
            R.id.btn_muat_ulang_3,
            R.id.btn_muat_ulang_4,
            R.id.btn_muat_ulang_5,
            R.id.btn_muat_ulang_6,
            R.id.btn_muat_ulang_7,
            R.id.btn_muat_ulang_8,
            R.id.btn_muat_ulang_9,
            R.id.btn_muat_ulang_10,
            R.id.btn_muat_ulang_11,
            R.id.btn_muat_ulang_12,
            R.id.btn_muat_ulang_13,
            R.id.btn_muat_ulang_14,
            R.id.btn_muat_ulang_15,
            R.id.btn_muat_ulang_16,
            R.id.btn_muat_ulang_17,
            R.id.btn_muat_ulang_18,
            R.id.btn_muat_ulang_19,
            R.id.btn_muat_ulang_20,
            R.id.btn_muat_ulang_21,
            R.id.btn_muat_ulang_22,
            R.id.btn_muat_ulang_23,
            R.id.btn_muat_ulang_24,
            R.id.btn_muat_ulang_25})
    public void onClickRefreshAttachment(View view) {
        switch (view.getId()) {
            case R.id.btn_muat_ulang_1:
                refreshAttachment(0);
                break;
            case R.id.btn_muat_ulang_2:
                refreshAttachment(1);
                break;
            case R.id.btn_muat_ulang_3:
                refreshAttachment(2);
                break;
            case R.id.btn_muat_ulang_4:
                refreshAttachment(3);
                break;
            case R.id.btn_muat_ulang_5:
                refreshAttachment(4);
                break;
            case R.id.btn_muat_ulang_6:
                refreshAttachment(5);
                break;
            case R.id.btn_muat_ulang_7:
                refreshAttachment(6);
                break;
            case R.id.btn_muat_ulang_8:
                refreshAttachment(7);
                break;
            case R.id.btn_muat_ulang_9:
                refreshAttachment(8);
                break;
            case R.id.btn_muat_ulang_10:
                refreshAttachment(9);
                break;
            case R.id.btn_muat_ulang_11:
                refreshAttachment(10);
                break;
            case R.id.btn_muat_ulang_12:
                refreshAttachment(11);
                break;
            case R.id.btn_muat_ulang_13:
                refreshAttachment(12);
                break;
            case R.id.btn_muat_ulang_14:
                refreshAttachment(13);
                break;
            case R.id.btn_muat_ulang_15:
                refreshAttachment(14);
                break;
            case R.id.btn_muat_ulang_16:
                refreshAttachment(15);
                break;
            case R.id.btn_muat_ulang_17:
                refreshAttachment(16);
                break;
            case R.id.btn_muat_ulang_18:
                refreshAttachment(17);
                break;
            case R.id.btn_muat_ulang_19:
                refreshAttachment(18);
                break;
            case R.id.btn_muat_ulang_20:
                refreshAttachment(19);
                break;
            case R.id.btn_muat_ulang_21:
                refreshAttachment(20);
                break;
            case R.id.btn_muat_ulang_22:
                refreshAttachment(21);
                break;
            case R.id.btn_muat_ulang_23:
                refreshAttachment(22);
                break;
            case R.id.btn_muat_ulang_24:
                refreshAttachment(23);
                break;
            case R.id.btn_muat_ulang_25:
                refreshAttachment(24);
                break;
        }
    }

    private void refreshAttachment(int i) {
        viewPbImages.get(i).setVisibility(View.VISIBLE);
        viewCameras.get(i).setVisibility(View.GONE);
        viewRefreshImages.get(i).setVisibility(View.GONE);
        viewTakeImages.get(i).setVisibility(View.GONE);

        Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImages.get(i));

//        final int finalI = i;
//        Glide.with(getContext()).load(tmpAttachments.get(i)).asBitmap().into(new SimpleTarget<Bitmap>() {
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//
//                Toast.makeText(getContext(), R.string.text_error_loading_attachment, Toast.LENGTH_SHORT).show();
//                viewPbImages.get(finalI).setVisibility(View.GONE);
//                viewCameras.get(finalI).setVisibility(View.GONE);
//                viewDeleteImages.get(finalI).setVisibility(View.GONE);
//                viewRefreshImages.get(finalI).setVisibility(View.VISIBLE);
//                viewTakeImages.get(finalI).setVisibility(View.GONE);
//                if (BuildConfig.DEBUG) {
//                    Log.e("Error Load Image", String.valueOf(e));
//                }
//                Crashlytics.logException(e);
//                isImageError = true;
//            }
//
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                viewTakeImages.get(finalI).setVisibility(View.VISIBLE);
//                mHashMapAttachmentFiles.put(finalI, new File(Util.bitmapToFile(resource)));
//                viewPbImages.get(finalI).setVisibility(View.GONE);
//                viewCameras.get(finalI).setVisibility(View.GONE);
//                viewRefreshImages.get(finalI).setVisibility(View.GONE);
//                viewDeleteImages.get(finalI).setVisibility(View.GONE);
//                Toast.makeText(getContext(), R.string.warning_berhasil_load_gambar, Toast.LENGTH_SHORT).show();
//                isImageError = false;
//            }
//        });

    }

    @Subscribe
    public void SendEmailPo(SendEmailPo e) {
        mSendEmailPoPresenter.SendingEmail(token, applicationID, e.getEmail());
    }

    /**
     * Initialization
     *
     * @param view
     */
    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        pengajuanDetailPresenter.attachView(this);
    }

    @Override
    public void onPreSubmitPengajuanEditLoad() {
        isClickable = false;
        pBar.setVisibility(View.VISIBLE);
        llBtnRetry.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessSubmitPengajuanEditLoad(Application application) {
        isClickable = true;
        this.application = application;

        if (cro.equalsIgnoreCase("cro")) {
            if (!application.getPersonalData().getMaritalStatus().equalsIgnoreCase("MENIKAH")) {
                includeDataPasangan.setVisibility(View.GONE);
            }
        } else {
            if (!application.getPersonalData().getMaritalStatus().equalsIgnoreCase("MENIKAH")) {
                includeDataPasangan.setVisibility(View.GONE);
                lnTakeFoto5.setVisibility(View.GONE);
                trPerjanjianPisahHarta.setVisibility(View.GONE);
            }
        }
        if (application.getStatus().equals("Cancel") || application.getStatus().equals("Reject")
                || application.getStatus().equals("Canceled") || application.getStatus().equals("Rejected"))
            trAlasan.setVisibility(View.GONE);
        else txtAlasanCustomer.setText(application.getReason());

        txtStatusCustomer.setText(application.getStatus());

        String ovd = application.getoVD();
        if ("".equalsIgnoreCase(ovd)) {
            ovd = "-";
            txtOvd.setText(ovd);
        } else txtOvd.setText(ovd);

        editLoadTitleShowHide(application);
        editLoadDataPribadi(application);
        editLoadDataPasangan(application);
        editLoadDataAlamat(application);
        editLoadDataKerabat(application);
        editLoadDataPekerjaan(application);
        editLoadDataKartuKredit(application);
        editLoadDataKartuMembership(application);
        editLoadDataProduct(application);
        editLoadDataAsuransi(application);
        editLoadDataAgunan(application);
        editLoadDataPerhitunganElektronik(application);
        editLoadDataPerhitunganKendaraan(application);
        editLoadDataAssetElektronik(application);
        editLoadDataCaraPembayaran(application);
        editLoadDataKeterangan(application);
        editLoadDataRekomendasi(application);
        editLoadDataAttachmentKendaraan(application);
        editLoadDataAttachmentPo(application);
        pBar.setVisibility(View.GONE);
        llBtnRetry.setVisibility(View.GONE);
    }

    private void editLoadTitleShowHide(Application application) {
        if (!cro.equalsIgnoreCase("cro")) {
            String tipePengajuan = application.getKorpFormulir().getTypeForm();
            if (tipePengajuan.equalsIgnoreCase("PSA")) {
                tvTitlePokokPembiayaan.setText("Nilai Pembiayaan (Rp)");
                trNonPsaBiayaNotaris.setVisibility(View.GONE);
                trNonPsaBiayaPengurusanStnk.setVisibility(View.GONE);
                trNonPsaBiayaProvisi.setVisibility(View.GONE);
                trNonPsaBiayaSurvey.setVisibility(View.GONE);
                trPnbpFidusia.setVisibility(View.GONE);
            } else {
                tvTitlePokokPembiayaan.setText("Pokok Pembiayaan (Rp)");
            }
        }
    }

    private void editLoadDataAttachmentKendaraan(Application application) {
//        numberOfImages = application.getAttachmentMTR().size();
        countImage = 0;
        isImageError = false;
        tmpAttachments = new ArrayList<>();
        if ("M".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "MENIKAH".equalsIgnoreCase(application.getPersonalData().getMaritalStatus())) {
            setAllImageMarried(application);
        } else {
            setAllImageSingle(application);
        }
    }

    private void setAllImageMarried(Application application) {
        final int[] countErrorImage = {0};
        if (cro.equalsIgnoreCase("cro")) {
            for (int i = 0; i < application.getAttachmentElc().size(); i++) {
                if(application.getAttachmentElc().get(i).equals("")){
                    tmpAttachments.add(application.getAttachmentElc().get(i) + "null");
                }else{
                    tmpAttachments.add(application.getAttachmentElc().get(i));
                }
//
                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }

            for (int i = 0; i < tmpAttachments.size(); i++) {
                if(tmpAttachments.get(i).equals("null")){
//                    viewPbImages.get(i).setVisibility(View.GONE);
//                    Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_take_camera)).into((ImageView) viewTakeImagesVehicleFirst.get(i));
                    viewPbImages.get(i).setVisibility(View.GONE);
                    viewCameras.get(i).setVisibility(View.VISIBLE);

                }else{
                    Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImages.get(i));
//                    viewPbImages.get(i).setVisibility(ProgressBar.GONE);
//                    viewCameras.get(i).setVisibility(View.VISIBLE);
                }
                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }


        } else {
//            setImageAllMariage();

            for (int i = 0; i < application.getAttachmentMTR().size(); i++) {
                if(application.getAttachmentMTR().get(i).equals("")){
                    tmpAttachments.add(application.getAttachmentMTR().get(i) + "null");
                }else{
                    tmpAttachments.add(application.getAttachmentMTR().get(i));
                }
//
                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }

            for (int i = 0; i < tmpAttachments.size(); i++) {
                if(tmpAttachments.get(i).equals("null")){
//                    viewPbImages.get(i).setVisibility(View.GONE);
//                    Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_take_camera)).into((ImageView) viewTakeImagesVehicleFirst.get(i));
                    viewPbImages.get(i).setVisibility(View.GONE);
                    viewCameras.get(i).setVisibility(View.VISIBLE);

                }else{
                    Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImages.get(i));
//                    viewPbImages.get(i).setVisibility(ProgressBar.GONE);
//                    viewCameras.get(i).setVisibility(View.VISIBLE);
                }
                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }
        }

    }

    private void setAllImageSingle(Application application) {
        final int[] countErrorImage = {0};
        if (cro.equalsIgnoreCase("cro")) {
            for (int i = 0; i < application.getAttachmentElc().size(); i++) {
                if(i == 4 && application.getAttachmentElc().get(i).equals("")){

                }else if(application.getAttachmentElc().get(i).equals("")){
                    tmpAttachments.add(application.getAttachmentElc().get(i) + "null");
                }else{
                    tmpAttachments.add(application.getAttachmentElc().get(i));
                }
//
                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }

            for (int i = 0; i < tmpAttachments.size(); i++) {
                if(tmpAttachments.get(i).equals("null")){
//                    viewPbImages.get(i).setVisibility(View.GONE);
//                    Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_take_camera)).into((ImageView) viewTakeImagesVehicleFirst.get(i));
                    viewPbImages.get(i).setVisibility(View.GONE);
                    viewCameras.get(i).setVisibility(View.VISIBLE);

                }else{
                    Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImagesVehicleFirst.get(i));
//                    viewPbImages.get(i).setVisibility(ProgressBar.GONE);
//                    viewCameras.get(i).setVisibility(View.VISIBLE);
                }
                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }
        } else {
            for (int i = 0; i < application.getAttachmentMTR().size(); i++) {
                if(i == 4){

                }else if(application.getAttachmentMTR().get(i).equals("")){
                    tmpAttachments.add(application.getAttachmentMTR().get(i) + "null");
                }else{
                    tmpAttachments.add(application.getAttachmentMTR().get(i));
                }


                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }

            for (int i = 0; i < tmpAttachments.size(); i++) {
                if(tmpAttachments.get(i).equals("null")){
//                    viewPbImages.get(i).setVisibility(View.GONE);
//                    Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_take_camera)).into((ImageView) viewTakeImagesVehicleFirst.get(i));
                    viewPbImages.get(i).setVisibility(View.GONE);
                    viewCameras.get(i).setVisibility(View.VISIBLE);

                }else{
                    Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImagesVehicleFirst.get(i));
//                    viewPbImages.get(i).setVisibility(ProgressBar.GONE);
//                    viewCameras.get(i).setVisibility(View.VISIBLE);
                }
                viewDeleteImages.get(i).setVisibility(View.GONE);
                if (i == 24) break;
            }
        }

    }

    private int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());

        return drawableResourceId;
    }

    private void editLoadDataCaraPembayaran(Application application) {
        txtAsuransiAgunan.setText(application.getInsurance().getCoverageType());
    }

    private void editLoadDataRekomendasi(Application application) {
        if (application.getReasonRecomendationId().equals("0")) {
            isRecomendation = "Ya";
        } else {
            isRecomendation = "Tidak";
        }
        txtIsRekomendasi.setText(isRecomendation);
        txtRekomendasi.setText(application.getReasonRecomendation());
        txtKeteranganRekomendasi.setText(application.getReasonRecomendationNotes());
    }

    private void editLoadDataAssetElektronik(Application application) {
        if (cro.equalsIgnoreCase("cro")) {
            List<Asset> listAsset = application.getAsset();
            int idx = 1;
            for (Asset a : listAsset) {
                View assetView = getActivity().getLayoutInflater().inflate(R.layout.view_item_form_detail_asset, null);

                TextView tvAssetHeader = (TextView) assetView.findViewById(R.id.txt_asset_item_header);
                TextView tvNamaBarang = (TextView) assetView.findViewById(R.id.txt_nama_barang_asset_item);
                TextView tvPrice = (TextView) assetView.findViewById(R.id.txt_price_asset_item);
                TextView tvDP = (TextView) assetView.findViewById(R.id.txt_dp_asset_item);
                TextView tvDiscount = (TextView) assetView.findViewById(R.id.txt_discount_asset_item);

                tvAssetHeader.setText(tvAssetHeader.getText() + " " + idx);
                tvNamaBarang.setText(a.getAssetCode());
                tvPrice.setText(Util.formatNominal(a.getOTRPrice()));
                if (a.getDPAmount().isEmpty()) {
                    tvDP.setText(Util.formatNominal("0r"));
                } else {
                    tvDP.setText(Util.formatNominal(a.getDPAmount()));
                }
                if (a.getDiscount().isEmpty()) {
                    tvDiscount.setText(Util.formatNominal("0"));
                } else {
                    tvDiscount.setText(Util.formatNominal(a.getDiscount()));
                }


                lnAssetDetailProductContainer.addView(assetView);
                idx++;
            }
        }
    }

    private void editLoadDataPerhitunganElektronik(Application application) {
        if (cro.equalsIgnoreCase("cro")) {
            txtPurchasePriceDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getPurchasePrice()) ? "0" : application.getDetailFinancing().getPurchasePrice()));
            txtDiscountDataPerhitunganElektronik.setText(Util.formatNominal(application.getDetailFinancing().getDiscount()));
            txtDpDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getDownPayment()) ? "0" : application.getDetailFinancing().getDownPayment()));
            txtNtfDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getnTF()) ? "0" : application.getDetailFinancing().getnTF()));
            txtFlateRateDataPerhitunganElektronik.setText(application.getDetailFinancing().getFlatRate() + " %");
            txtBiayaLainnyaDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getOtherFee()) ? "0" : application.getDetailFinancing().getOtherFee()));
            txtJumlahPembiayaanDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getTotalFinancing()) ? "0" : application.getDetailFinancing().getTotalFinancing()));
            txtBungaPembiayaanDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getInterestFinancing()) ? "0" : application.getDetailFinancing().getInterestFinancing()));
            txtTotalPinjamanDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getTotalLoan()) ? "0" : application.getDetailFinancing().getTotalLoan()));
            txtAngsuranDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getInstallmentAmmount()) ? "0" : application.getDetailFinancing().getInstallmentAmmount()));
            txtBiayaAdministrasiDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getAdminFee()) ? "0" : application.getDetailFinancing().getAdminFee()));
            txtSetorPertamaDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getFirstPayment()) ? "0" : application.getDetailFinancing().getFirstPayment()));
            txtAngsuranPertamaDataPerhitunganElektronik.setText(application.getDetailFinancing().getFirstInstallment());
            txtInRefundSubsidiDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getSubsidyRefund()) ? "0" : application.getDetailFinancing().getSubsidyRefund()));
        }
    }

    private void editLoadDataPerhitunganKendaraan(Application application) {
        if (!cro.equalsIgnoreCase("cro")) {
            trTenorKendaraan.setVisibility(View.VISIBLE);
            tvTenorKendaraan.setText(String.valueOf(application.getDetailProduct().getTenor()));
            tvHargaAgunan.setText(Util.formatNominal(application.getDetailFinancing().getCollateralPrice()));
            tvPinjamanKonsumen.setText(Util.formatNominal(application.getDetailFinancing().getConsumerLoan()));
            tvDownpayment.setText(Util.formatNominal(application.getDetailFinancing().getDownPayment()));
            tvBiayaAdministrasi.setText(Util.formatNominal(application.getDetailFinancing().getAdminFee()));
            tvBiayaProvisi.setText(Util.formatNominal(application.getDetailFinancing().getProvisiFee()));
            tvPremiAsuransiAgunan.setText(Util.formatNominal(application.getDetailFinancing().getPremiumAmountToCustomer()));
            tvPremiAsuransiJiwa.setText(Util.formatNominal(application.getDetailFinancing().getLifeInsurancePremi()));
            tvJumlahPembiayaan.setText(Util.formatNominal(application.getDetailFinancing().getTotalFinancing()));
            tvTotalBungaPembiayaan.setText(Util.formatNominal(application.getDetailFinancing().getInterestFinancing()));
            tvTotalPinjaman.setText(Util.formatNominal(application.getDetailFinancing().getTotalLoan()));
            tvAngsuranPerbulan.setText(Util.formatNominal(application.getDetailFinancing().getInstallmentAmmount()));
            tvFlatTahun.setText(application.getDetailFinancing().getFlatRateYear() + " %");
            tvPnbpFidusia.setText(Util.formatNominal(application.getDetailFinancing().getFiduciaryFees()));
            tvBiayaPengurusanStnk.setText(Util.formatNominal(application.getDetailFinancing().getStnkFees()));
            tvBiayaNotaris.setText(Util.formatNominal(application.getDetailFinancing().getNotaryFees()));
            tvBiayaSurvey.setText(Util.formatNominal(application.getDetailFinancing().getCosOfSurvey()));
        } else {
            trTenorKendaraan.setVisibility(View.GONE);
        }
    }

    private void editLoadDataAttachmentPo(Application application) {
        poImageAdapter.setAttachmentList(application.getPoList());
        txtKeteranganPo.setText(application.getNomorPO());
        txtTanggalPo.setText(application.getDateSubmitPO());
    }

    private void editLoadDataKeterangan(Application application) {
        txtketerangan.setText(application.getKeterangan() == null ? "" : application.getKeterangan());
    }

    private void editLoadDataAgunan(Application application) {
        if (cro.equalsIgnoreCase("cro")) {
            includeDataAgunan.setVisibility(View.GONE);
        } else {
            tvWilayahKendaraan.setText(application.getBranchObjt().getRegion());
            tvCabangKendaraan.setText(application.getAsset().get(0).getBranch());
            tvJenisKendaraan.setText(application.getAsset().get(0).getCategory());
            tvMerkKendaraan.setText(application.getAsset().get(0).getMerk());
            tvTipeKendaraan.setText(application.getAsset().get(0).getAssetName());
            tvTahunKendaraan.setText(application.getAsset().get(0).getManufacturingYear());
            tvWarnaKendaraan.setText(application.getAsset().get(0).getColor());
            tvIsiSilinder.setText(application.getAsset().get(0).getCylinder());
            tvNoPolisi.setText(application.getAsset().get(0).getPoliceNo());
            tvNoRangka.setText(application.getAsset().get(0).getFrameNumber());
            tvNoMesin.setText(application.getAsset().get(0).getMachineNo());
            tvBpkbAtasNama.setText(application.getAsset().get(0).getBpkbOwnName());
            tvNamaBpkb.setText(application.getAsset().get(0).getBpkbName());
            tvMasaBerlakuStnk.setText(application.getAsset().get(0).getValidityPeriodStnk());
            tvMasaBerlakuPajakStnk.setText(application.getAsset().get(0).getValidityPeriodTaxStnk());
        }

    }

    private void editLoadDataAsuransi(Application application) {
        if (cro.equalsIgnoreCase("cro")) {
            txtIsPersonalAccident.setText("0".equalsIgnoreCase(application.getInsurance().getIsPersonalAccident()) ? "No" : "Yes");
            txtPremiAsuransi.setText(Util.formatNominal(application.getInsurance().getPremiumAmountToCustomer()));
        }
    }

    private void editLoadDataProduct(Application application) {
        txtSupplier.setText(application.getAssetMaster().getSupplierName());
        txtBankAccount.setText(application.getAssetMaster().getSupplierBankAccountName());
        txtMarketingSupplier.setText(application.getAssetMaster().getSalesmanName());
        txtProductOffering.setText(application.getDetailProduct().getProductOfferingName());
        txtJumlahAsset.setText(String.valueOf(application.getDetailProduct().getNumOfAssetUnit()));
        if (cro.equalsIgnoreCase("cro")) {
            trTenorElc.setVisibility(View.VISIBLE);
            txtTenor.setText(String.valueOf(application.getDetailProduct().getTenor()));
        } else {
            trTenorElc.setVisibility(View.GONE);
        }
        txtPos.setText(application.getDetailProduct().getpOSName());
    }

    private void editLoadDataKartuMembership(Application application) {
        if (cro.equalsIgnoreCase("cro")) {
            txtNoMembership.setText(application.getMembershipCard().getiDMember());
            txtTanggalEfektifMembership.setText(application.getMembershipCard().getEffectiveDate());
            txtTanggalExpiredMembership.setText(application.getMembershipCard().getExpiredDate());
        }
    }

    private void editLoadDataKartuKredit(Application application) {
        if (cro.equalsIgnoreCase("cro")) {
            txtNamaBankKartuKredit.setText(application.getDataCreditCard().getBankName());
            txtNoKartuKredit.setText(application.getDataCreditCard().getiDCard());
            txtJenisKartuKredit.setText(application.getDataCreditCard().getCardType());
            txtLimitKartuKredit.setText(Util.formatNominal(application.getDataCreditCard().getCardLimit()));
            txtKadaluarsaTahunKartuKredit.setText(application.getDataCreditCard().getMembershipOldYear());
            txtKadaluarsaBulanKartuKredit.setText(application.getDataCreditCard().getMembershipOldMonth());
        }
    }

    private void editLoadDataPekerjaan(Application application) {
        txtProfesiDataPekerjaan.setText(application.getCompany().getProfessionName());
        txtJenisPekerjaanDataPekerjaan.setText(application.getCompany().getPersonalCustomerTypeName());
        txtNamaPerusahaanDataPekerjaan.setText(application.getCompany().getName());
        txtAlamatDataPekerjaan.setText(application.getCompany().getAddress());
        txtRtDataPekerjaan.setText(application.getCompany().getrT());
        txtRwDataPekerjaan.setText(application.getCompany().getrW());
        txtKotaDataPekerjaan.setText(application.getCompany().getCityName());
        txtKecamatanDataPekerjaan.setText(application.getCompany().getDistrictName());
        txtKelurahanDataPekerjaan.setText(application.getCompany().getVillageName());
        txtKodePosDataPekerjaan.setText(application.getCompany().getZipCode());
        txtAreaPhoneDataPekerjaan.setText(application.getCompany().getAreaPhone());
        txtPhoneDataPekerjaan.setText(application.getCompany().getPhone());
        if (application.getCompany().getProfessionName().equalsIgnoreCase("WIRASWASTA")) {
            trPhoneNumberDataPekerjaan.setVisibility(View.VISIBLE);
            txtPhoneNumberDataPekerjaan.setText(application.getCompany().getPhoneNumber());
        } else {
            trPhoneNumberDataPekerjaan.setVisibility(View.GONE);
        }

        if (application.getCompany().getMonthlyFixedIncome().isEmpty()) {
            txtPenghasilanTetapDataPekerjaan.setText(Util.formatNominal("0"));
        } else {
            txtPenghasilanTetapDataPekerjaan.setText(Util.formatNominal(application.getCompany().getMonthlyFixedIncome()));
        }
        if (application.getCompany().getSpouseIncome().isEmpty()) {
            txtPenghasilanPasanganDataPekerjaan.setText(Util.formatNominal("0"));
        } else {
            txtPenghasilanPasanganDataPekerjaan.setText(Util.formatNominal(application.getCompany().getSpouseIncome()));
        }
        if (application.getCompany().getMonthlyVariableIncome().isEmpty()) {
            txtPenghasilanLainDataPekerjaan.setText(Util.formatNominal("0"));
        } else {
            txtPenghasilanLainDataPekerjaan.setText(Util.formatNominal(application.getCompany().getMonthlyVariableIncome()));
        }
        if (application.getCompany().getLivingCostAmount().isEmpty()) {
            txtBiayaHidupDataPekerjaan.setText(Util.formatNominal("0"));
        } else {
            txtBiayaHidupDataPekerjaan.setText(Util.formatNominal(application.getCompany().getLivingCostAmount()));
        }
        txtJobPositionDataPekerjaan.setText(application.getCompany().getJobPositionName());
        txtJobTypeDataPekerjaan.setText(application.getCompany().getJobTypeName());
        txtIndustriDataPekerjaan.setText(application.getCompany().getIndustryTypeName());
        txtBekerjaSejakDataPekerjaan.setText(application.getCompany().getEmploymentSinceYear());
        txtIsAffiliateWithPpDataPekerjaan.setText(application.getCompany().getIsAffiliateWithPP().equalsIgnoreCase("0") ? "No" : "Yes");
    }

    private void editLoadDataKerabat(Application application) {
        txtNamaLengkapInformasiKerabat.setText(application.getEmergencyContact().getName());
        txtHubunganDenganKerabat.setText(application.getEmergencyContact().getRelationship());
        txtAlamatInformasiKerabat.setText(application.getEmergencyContact().getAddress());
        txtRtInformasiKerabat.setText(application.getEmergencyContact().getrT());
        txtRwInformasiKerabat.setText(application.getEmergencyContact().getrW());
        txtKotaInformasiKerabat.setText(application.getEmergencyContact().getCityName());
        txtKecamatanInformasiKerabat.setText(application.getEmergencyContact().getDistrictName());
        txtKelurahanInformasiKerabat.setText(application.getEmergencyContact().getVillageName());
        txtKodePosInformasiKerabat.setText(application.getEmergencyContact().getZipCode());
        txtAreaPhoneRumahInformasiKerabat.setText(application.getEmergencyContact().getHomePhoneArea());
        txtPhoneRumahInformasiKerabat.setText(application.getEmergencyContact().getHomePhone());
        txtAreaPhoneKantorInformasiKerabat.setText(application.getEmergencyContact().getOfficePhoneArea());
        txtPhoneKantorInformasiKerabat.setText(application.getEmergencyContact().getOfficePhone());
        txtNomorHandphoneInformasiKerabat.setText(application.getEmergencyContact().getMobilePhone());
    }

    private void editLoadDataAlamat(Application application) {
        //ALAMAT TINGGAL
        txtAlamatAlamatTinggal.setText(application.getResidance().getAddress());
        txtRtAlamatTinggal.setText(application.getResidance().getrT());
        txtRwAlamatTinggal.setText(application.getResidance().getrW());
        txtKotaAlamatTinggal.setText(application.getResidance().getCityName());
        txtKecamatanAlamatTinggal.setText(application.getResidance().getDistrictName());
        txtKelurahanAlamatTinggal.setText(application.getResidance().getVillageName());
        txtKodePosAlamatTinggal.setText(application.getResidance().getZipCode());
        txtAreaPhoneAlamatTinggal.setText(application.getResidance().getAreaPhone());
        txtPhoneAlamatTinggal.setText(application.getResidance().getPhone());


        //ALAMAT KTP
        txtAlamatAlamatKtp.setText(application.getLegal().getAddress());
        txtRtAlamatKtp.setText(application.getLegal().getrT());
        txtRwAlamatKtp.setText(application.getLegal().getrW());
        txtKotaAlamatKtp.setText(application.getLegal().getCityName());
        txtKecamatanAlamatKtp.setText(application.getLegal().getDistrictName());
        txtKelurahanAlamatKtp.setText(application.getLegal().getVillageName());
        txtKodePosAlamatKtp.setText(application.getLegal().getZipCode());
        txtAreaPhoneAlamatKtp.setText(application.getLegal().getAreaPhone());
        txtPhoneAlamatKtp.setText(application.getLegal().getPhone());
    }

    private void editLoadDataPasangan(Application application) {
        txtNamaPasangan.setText(application.getFamilyData().get(0).getName());
        txtNoKtpPasangan.setText(application.getFamilyData().get(0).getiDNumber());
        if (application.getFamilyData().get(0).getProfesiName().equalsIgnoreCase("WIRASWASTA")) {
            trHpPasangan.setVisibility(View.VISIBLE);
            txtHpPasangan.setText(application.getFamilyData().get(0).getHandphone());
        } else {
            trHpPasangan.setVisibility(View.GONE);
        }
        txtTempatLahirPasangan.setText(application.getFamilyData().get(0).getBirthPlace());
        txtTanggalLahirPasangan.setText(application.getFamilyData().get(0).getBirthDate());
        if (!cro.equalsIgnoreCase("cro")) {
            tvPasanganTeleponRumah.setText(application.getFamilyData().get(0).getAreaPhoneHome() + application.getFamilyData().get(0).getPhoneHome());
            tvPasanganTeleponPerusahaan.setText(application.getFamilyData().get(0).getAreaPhoneCompany() + application.getFamilyData().get(0).getPhoneCompany());
            tvPasanganAlamat.setText(application.getFamilyData().get(0).getAddress());
            tvPasanganKota.setText(application.getFamilyData().get(0).getCity());
            tvPasanganProfesi.setText(application.getFamilyData().get(0).getProfesiName());
            tvPasanganJobType.setText(application.getFamilyData().get(0).getJobTypeName());
            tvPasanganJobPosition.setText(application.getFamilyData().get(0).getJobPositionName());
            tvPasanganIndustri.setText(application.getFamilyData().get(0).getIndustryName());
            tvPasanganPegawai.setText(application.getFamilyData().get(0).getStatusEmployee());
            tvPasanganNamaPerusahaan.setText(application.getFamilyData().get(0).getCompanyName());
            tvPasanganNamaIbu.setText(application.getFamilyData().get(0).getSurgateMotherName());
        } else {
            maritalDetailNonELC.setVisibility(View.GONE);
        }
    }

    private void editLoadDataPribadi(Application application) {
        txtNamaLengkap.setText(application.getPersonalData().getFullName());
        txtNamaPadaIdentitas.setText(application.getPersonalData().getLegalName());
        txtNoKtp.setText(application.getPersonalData().getiDNumber());
        txtNoKk.setText(application.getPersonalData().getKKno());
        if (application.getPersonalData().getPersonalNPWP().isEmpty()) {
            txtNoNpwp.setText("-");
        } else {
            txtNoNpwp.setText(application.getPersonalData().getPersonalNPWP());
        }
        txtTanggalTerbitKtp.setText(application.getPersonalData().getiDTypeIssuedDate());
        txtTempatLahir.setText(application.getPersonalData().getBirthPlace());
        txtTanggalLahir.setText(application.getPersonalData().getBirthDate());
        txtNamaGadisIbuKandung.setText(application.getPersonalData().getSurgateMotherName());
        txtGender.setText(application.getPersonalData().getGender());
        txtStatusPernikahan.setText(application.getPersonalData().getMaritalStatus());
        if (application.getPersonalData().getNumOfDependence().isEmpty()) {
            txtJumlahTanggungan.setText("0");
        } else {
            txtJumlahTanggungan.setText(application.getPersonalData().getNumOfDependence());
        }
        txtStatusRumah.setText(application.getPersonalData().getHomeStatus());
        txtTinggalSejakTahun.setText(application.getPersonalData().getStaySinceYear());
        txtTinggalSejakBulan.setText(application.getPersonalData().getStaySinceMonth());
        txtPendidikan.setText(application.getPersonalData().getEducationName());
        txtWargaNegara.setText(application.getPersonalData().getNationality());
        txtAgama.setText(application.getPersonalData().getReligion());
        txtNoHandphone.setText(application.getPersonalData().getMobilePhone());
        txtEmail.setText(application.getPersonalData().getEmail());
        if (!cro.equalsIgnoreCase("cro"))
            tvPerjanjianPisahHarta.setText(application.getPersonalData().getSeparateProperty());
    }

    private void initTitleAttachmentELC() {
        tvCamera1.setText("KTP");
        tvCamera2.setText("Close up Konsumen");
        tvCamera3.setText("Close up Konsumen dengan KTP");
        tvCamera4.setText("Konsumen dan CRO");
//        tvCamera5.setText("Slip Gaji");
        tvCamera6.setText("Slip Gaji");
        tvCamera7.setText("Dokumen Lainnya");
        tvCamera8.setText("Dokumen Lainnya");
        tvCamera9.setText("Dokumen Lainnya");
        tvCamera10.setText("Dokumen Lainnya");
        tvCamera11.setText("Dokumen Lainnya");

//        tvCamera11.setVisibility(View.GONE);
        tvCamera12.setVisibility(View.GONE);
        tvCamera13.setVisibility(View.GONE);
        tvCamera14.setVisibility(View.GONE);
        tvCamera15.setVisibility(View.GONE);
        tvCamera16.setVisibility(View.GONE);
        tvCamera17.setVisibility(View.GONE);
        tvCamera18.setVisibility(View.GONE);
        tvCamera19.setVisibility(View.GONE);
        tvCamera20.setVisibility(View.GONE);
        tvCamera21.setVisibility(View.GONE);
        tvCamera22.setVisibility(View.GONE);
        tvCamera23.setVisibility(View.GONE);
        tvCamera24.setVisibility(View.GONE);
        tvCamera25.setVisibility(View.GONE);


    }

    private void initTitleAttachment() {
        tvCamera1.setText(getString(R.string.txt_title_attachment_1));
        tvCamera2.setText(getString(R.string.txt_title_attachment_2));
        tvCamera3.setText(getString(R.string.txt_title_attachment_3));
        tvCamera4.setText(getString(R.string.txt_title_attachment_4));
        tvCamera5.setText(getString(R.string.txt_title_attachment_5));
        tvCamera6.setText(getString(R.string.txt_title_attachment_7));
        tvCamera7.setText(getString(R.string.txt_title_attachment_8));
        tvCamera8.setText(getString(R.string.txt_title_attachment_9));
        tvCamera9.setText(getString(R.string.txt_title_attachment_13));
        tvCamera10.setText(getString(R.string.txt_title_attachment_14));
        tvCamera11.setText(getString(R.string.txt_title_attachment_15));
        tvCamera12.setText(getString(R.string.txt_title_attachment_16));
        tvCamera13.setText(getString(R.string.txt_title_attachment_17));
        tvCamera14.setText(getString(R.string.txt_title_attachment_18));
        tvCamera15.setText(getString(R.string.txt_title_attachment_6));
        tvCamera16.setText(getString(R.string.txt_title_attachment_10));
        tvCamera17.setText(getString(R.string.txt_title_attachment_11));
        tvCamera18.setText(getString(R.string.txt_title_attachment_12));
        tvCamera19.setText(getString(R.string.txt_title_attachment_19));
        tvCamera20.setText(getString(R.string.txt_title_attachment_20));
        tvCamera21.setText(getString(R.string.txt_title_attachment_21));
        tvCamera22.setText(getString(R.string.txt_title_attachment_22));
        tvCamera23.setText(getString(R.string.txt_title_attachment_23));
        tvCamera24.setText(getString(R.string.txt_title_attachment_24));
        tvCamera25.setText(getString(R.string.txt_title_attachment_25));
    }

    private void setImageToImageView(List<View> viewTakeImages, String uriString) {
        Glide.with(this).load(uriString).centerCrop().into((ImageView) viewTakeImages);
    }

    @Override
    public void onFailedSubmitPengajuanEditLoad(String message) {
        txtError.setText(message);
        isClickable = false;
        pBar.setVisibility(View.GONE);
        llBtnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (isClickable) {
            View menu = getActivity().findViewById(R.id.menu_button);
            final PopupMenu popupMenu = new PopupMenu(getContext(), menu);
            MenuInflater menuInflater = popupMenu.getMenuInflater();
            if (cro.equalsIgnoreCase("cro")) {
                menuInflater.inflate(R.menu.menu_list_print, popupMenu.getMenu());
            } else {
                menuInflater.inflate(R.menu.menu_list_print_cmo, popupMenu.getMenu());
            }

            if (isApprove)
                popupMenu.getMenu().findItem(R.id.menu_list_perjanjian).setVisible(true);

            if (!application.getPoList().isEmpty()) {
                if (BuildConfig.DEBUG) {
                    Log.w("Size PO", String.valueOf(application.getPoList().size()));
                }
                if (cro.equalsIgnoreCase("cro")) {
                    popupMenu.getMenu().findItem(R.id.menu_list_send_email).setVisible(true);
                } else {
                    popupMenu.getMenu().findItem(R.id.menu_list_send_email).setVisible(false);
                }
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    item.setEnabled(false);
                    if (item.getItemId() == R.id.menu_list_pengajuan) {
                        typePdf = "pengajuan";
                        String namePdf = application.getPdfName();
                        String nameUrl = application.getPdfUrl();
                        mDownloadPdfPresenter.downloadPdfPengajuan(token, namePdf, applicationID);
                    } else if (item.getItemId() == R.id.menu_list_perjanjian) {
                        typePdf = "perjanjian";
//                        writeStoragePermission();
                    } else if (item.getItemId() == R.id.menu_list_send_email) {
                        DialogFragment dialogFragment = new SendEmailPoDialog();
                        dialogFragment.show(getActivity().getSupportFragmentManager(), "SendEmailPO");
                    }
                    return false;
                }
            });
            popupMenu.show();
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onDetailTokenExpired() {
        pBar.setVisibility(View.GONE);
        llBtnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPreLoadSendEmail() {
        progressDialog.show();
    }

    @Override
    public void onSuccessSendEmail() {
        progressDialog.dismiss();
        Toast.makeText(getContext(), "Berhasil Mengirim Email", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedSendEmail(String message) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessSendEmailTokenExpired() {
        String cektoken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
    }

    // Download PDF
    @Override
    public void onPreDownloadPdf() {
        progressDialog.show();
        progressDialog.setMessage(getActivity().getResources().getString(R.string.txt_generate_pdf));
    }

    @Override
    public void onSuccessDownloadPdf(String pdfUrl, String pdfName) {
        progressDialog.dismiss();
        this.pdfUrl = pdfUrl;
        this.pdfName = pdfName;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
        startActivity(browserIntent);

        DownloadTask downloadTask = new DownloadTask(getContext());
        downloadTask.execute(pdfUrl);
    }

    @Override
    public void onFailedDownloadPdf(String message) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private final Context context;
        private PowerManager.WakeLock mWakeLock;

        DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            if (result != null){ }
            else {Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();}
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                File myDir = new File(root + "/EFORM");
                myDir.mkdirs();

                File tmpFile = new File(myDir, pdfName);
                if (tmpFile.exists()) tmpFile.delete();

                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/EFORM/" + pdfName);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                Crashlytics.logException(e);
                return e.toString();
            } finally {
                try {
                    if (output != null) output.close();
                    if (input != null) input.close();
                } catch (IOException ignored) {
                    Crashlytics.logException(ignored);
                }

                if (connection != null) connection.disconnect();
            }
            return null;
        }
    }

    @Override
    public void onRefreshTokenPdf() {
        String cektoken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
    }

    public void allPermission() {
        mPermissionHelper = new PermissionHelper.Builder(getActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA)
                .withPermissionInfos(
                        "Location Permission Needed",
                        "Location Permission Needed",
                        "Camera Permission Needed")
                .withListener(new PermissionHelper.OnPermissionCheckedListener() {
                    @Override
                    public void onPermissionGranted(boolean isPermissionAlreadyGranted) {
                        pengajuanDetailPresenter.loadPengajuanDetail(token, applicationID);
                    }

                    @Override
                    public void onPermissionDenied() {
                    }
                })
                .build();
        mPermissionHelper.requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null)
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}