package com.kreditplus.eform.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.kreditplus.eform.App;
import com.kreditplus.eform.R;
import com.kreditplus.eform.adapter.AttachmentDetailAdapter;
import com.kreditplus.eform.dialog.IgnorereasonDialog;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.bus.IgnoreResult;
import com.kreditplus.eform.model.bus.RefreshListKpm;
import com.kreditplus.eform.model.response.objecthelper.Application;
import com.kreditplus.eform.model.response.objecthelper.Asset;
import com.kreditplus.eform.presenter.PengajuanDetailPresenter;
import com.kreditplus.eform.presenter.ProcessIgnoreKpmPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.PengajuanDetailMvpView;
import com.kreditplus.eform.presenter.mvpview.ProcessignoreKpnMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 12/05/17.
 */

public class DetailKpmActivity extends BaseActivity implements PengajuanDetailMvpView, ProcessignoreKpnMvpView, RefreshTokenMvpView {

    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Data pribadi
    @BindView(R.id.txt_nama_lengkap)
    TextView txtNamaLengkap;
    @BindView(R.id.txt_nama_pada_identitas)
    TextView txtNamaPadaIdentitas;
    @BindView(R.id.txt_no_ktp)
    TextView txtNoKtp;
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
    //    @BindView(R.id.txt_counterpart_data_pekerjaan)
//    TextView txtCounterpartDataPekerjaan;
//    @BindView(R.id.txt_debt_business_scale_data_pekerjaan)
//    TextView txtDebtBusinessScaleDataPekerjaan;
//    @BindView(R.id.txt_debt_business_group_data_pekerjaan)
//    TextView txtDebtBusinessGroupDataPekerjaan;
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

    @BindView(R.id.view_perhitungan_elektronik)
    LinearLayout lnPerhitunganElektronik;
    @BindView(R.id.view_perhitungan_kendaraan)
    LinearLayout lnPerhitunganKendaraan;

    // Data Perhitungan kendaraan

    //Attachment

    // Detail product
    @BindView(R.id.txt_product_offering)
    TextView txtProductOffering;
    @BindView(R.id.txt_jumlah_asset)
    TextView txtJumlahAsset;
    @BindView(R.id.txt_tenor)
    TextView txtTenor;
    @BindView(R.id.txt_pos)
    TextView txtPos;
    @BindView(R.id.tl_pengajuan_detail_product)
    TableLayout tlPengajuanDetailProduct;
    @BindView(R.id.txt_error_api)
    TextView txtError;

    // Data Pasangan Kosong

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
    @BindView(R.id.txt_merk_kendaraan)
    TextView txtMerkKendaraan;
    @BindView(R.id.txt_tahun_kendaraan)
    TextView txtTahunKendaraan;
    @BindView(R.id.txt_warna_kendaraan)
    TextView txtWarnaKendaraan;
    @BindView(R.id.txt_isi_silinder)
    TextView txtIsiSilinder;
    @BindView(R.id.txt_no_polisi)
    TextView txtNoPolisi;
    @BindView(R.id.txt_no_rangka)
    TextView txtNoRangka;
    @BindView(R.id.txt_no_mesin)
    TextView txtNoMesin;
    @BindView(R.id.txt_bpkb_nama)
    TextView txtBpkbNama;

    //jadwal janji bertemu
    @BindView(R.id.txt_tempat_janji)
    TextView txtTempatJanji;
    @BindView(R.id.txt_waktu_janji)
    TextView txtWaktuJanji;
    @BindView(R.id.card_view_janji)
    CardView cv_janji;

    //frame button
    @BindView(R.id.frm_button_kpm)
    FrameLayout frmButtonKpm;

    //button
    @BindView(R.id.btn_process_kpm)
    Button btnProcessKpm;
    @BindView(R.id.btn_ignore_kpm)
    Button btnIgnoreKpm;

    private PengajuanDetailPresenter mPengajuanDetailPresenter;
    private ProcessIgnoreKpmPresenter mProcessIgnoreKpmPresenter;
    private RefreshTokenPresenter mRefreshTokenPresenter;
    private Bundle bundle;
    private String token;
    private ProgressDialog progressDialog;
    private ViewAnimator va;
    private String applicationID;
    private Application application;
    private AttachmentDetailAdapter attachmentDetailAdapter;
    private List<String> attachmentList = new ArrayList<>();
    private String statusPengajuan;
    private String appidKpm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        unbinder = ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        applicationID = bundle.getString("app_id");
        statusPengajuan = bundle.getString("status");
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");

        mPengajuanDetailPresenter = new PengajuanDetailPresenter();
        mPengajuanDetailPresenter.attachView(this);

        mProcessIgnoreKpmPresenter = new ProcessIgnoreKpmPresenter();
        mProcessIgnoreKpmPresenter.attachView(this);

        mRefreshTokenPresenter = new RefreshTokenPresenter();
        mRefreshTokenPresenter.attachView(this);

        attachmentDetailAdapter = new AttachmentDetailAdapter(this, attachmentList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        rvAttachment.setLayoutManager(linearLayoutManager);
//        rvAttachment.setAdapter(attachmentDetailAdapter);

        va = (ViewAnimator) findViewById(R.id.va_detail_kpm);

        EventBus.getDefault().register(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setTitle("");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DETAIL PENGAJUAN KPM");

        mPengajuanDetailPresenter.loadPengajuanDetail(token, applicationID);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPengajuanDetailPresenter.detachView();
        mProcessIgnoreKpmPresenter.detachView();
        mRefreshTokenPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail_kpm;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                EventBus.getDefault().post(new RefreshListKpm());
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_retry)
    public void retryKpm() {
        mPengajuanDetailPresenter.loadPengajuanDetail(token, applicationID);
    }

    @OnClick(R.id.btn_process_kpm)
    public void processKpm() {
        mProcessIgnoreKpmPresenter.processIgnore(token, applicationID, "t", "");
    }

    @OnClick(R.id.btn_ignore_kpm)
    public void ignoreKpm() {
        DialogFragment dialogFragment = new IgnorereasonDialog();
        dialogFragment.show(getSupportFragmentManager(), "IgnoreReasonDialog");
    }

    @Subscribe
    public void ignoreResult(IgnoreResult e) {
        mProcessIgnoreKpmPresenter.processIgnore(token, applicationID, "f", e.getResult());
    }

    @Override
    public void onBackPressed() {
        DetailKpmActivity.super.onBackPressed();
        EventBus.getDefault().post(new RefreshListKpm());
    }

    @Override
    public void onPreSubmitPengajuanEditLoad() {
        va.setDisplayedChild(2);

    }

    @Override
    public void onSuccessSubmitPengajuanEditLoad(Application application) {
        if ("on surveyed".equalsIgnoreCase(statusPengajuan)) {
            btnIgnoreKpm.setVisibility(View.GONE);
            btnProcessKpm.setVisibility(View.GONE);
            cv_janji.setVisibility(View.VISIBLE);

//            txtTempatJanji.setText(application.getPlaceOfReminder() == null ? "-" : application.getPlaceOfReminder());
            txtWaktuJanji.setText(application.getReminderDate() == null ? "-" : application.getReminderDate());
        }

        va.setDisplayedChild(3);

        this.application = application;
        //id kpm
//        appidKpm = application.getApplicationIdKpm();
        //DATA PRIBADI
        txtNamaLengkap.setText(application.getPersonalData().getFullName());
        txtNamaPadaIdentitas.setText(application.getPersonalData().getLegalName());
//        txtNoKtp.setText(application.getPersonalData().getIdNumber());
//        txtTanggalTerbitKtp.setText(application.getPersonalData().getIdTypeIssuedDate());
        txtTempatLahir.setText(application.getPersonalData().getBirthPlace());
        txtTanggalLahir.setText(application.getPersonalData().getBirthDate());
        txtNamaGadisIbuKandung.setText(application.getPersonalData().getSurgateMotherName());
        txtGender.setText(application.getPersonalData().getGenderName());
        txtStatusPernikahan.setText(application.getPersonalData().getMaritalStatusName());
        txtJumlahTanggungan.setText(application.getPersonalData().getNumOfDependence());
        txtStatusRumah.setText(application.getPersonalData().getHomeStatusName());
        txtTinggalSejakTahun.setText(application.getPersonalData().getStaySinceYear());
        txtTinggalSejakBulan.setText(application.getPersonalData().getStaySinceMonth());
        txtPendidikan.setText(application.getPersonalData().getEducationName());
        txtWargaNegara.setText(application.getPersonalData().getNationality());
        txtAgama.setText(application.getPersonalData().getReligionName());
        txtNoHandphone.setText(application.getPersonalData().getMobilePhone());
        txtEmail.setText(application.getPersonalData().getEmail());

        //Data Pasangan kosong

        //ALAMAT TINGGAL
        txtAlamatAlamatTinggal.setText(application.getResidance().getAddress());
//        txtRtAlamatTinggal.setText(application.getResidance().getRt());
//        txtRwAlamatTinggal.setText(application.getResidance().getRw());
        txtKotaAlamatTinggal.setText(application.getResidance().getCityName());
        txtKecamatanAlamatTinggal.setText(application.getResidance().getDistrictName());
        txtKelurahanAlamatTinggal.setText(application.getResidance().getVillageName());
        txtKodePosAlamatTinggal.setText(application.getResidance().getZipCode());
        txtAreaPhoneAlamatTinggal.setText(application.getResidance().getAreaPhone());
        txtPhoneAlamatTinggal.setText(application.getResidance().getPhone());

        //ALAMAT KTP
        txtAlamatAlamatKtp.setText(application.getLegal().getAddress());
//        txtRtAlamatKtp.setText(application.getLegal().getRt());
//        txtRwAlamatKtp.setText(application.getLegal().getRw());
        txtKotaAlamatKtp.setText(application.getLegal().getCityName());
        txtKecamatanAlamatKtp.setText(application.getLegal().getDistrictName());
        txtKelurahanAlamatKtp.setText(application.getLegal().getVillageName());
        txtKodePosAlamatKtp.setText(application.getLegal().getZipCode());
        txtAreaPhoneAlamatKtp.setText(application.getLegal().getAreaPhone());
        txtPhoneAlamatKtp.setText(application.getLegal().getPhone());

        //INFORMASI KONTAK DARURAT
        txtNamaLengkapInformasiKerabat.setText(application.getEmergencyContact().getName());
        txtHubunganDenganKerabat.setText(application.getEmergencyContact().getRelationship());
        txtAlamatInformasiKerabat.setText(application.getEmergencyContact().getAddress());
//        txtRtInformasiKerabat.setText(application.getEmergencyContact().getrT());
//        txtRwInformasiKerabat.setText(application.getEmergencyContact().getRw());
        txtKotaInformasiKerabat.setText(application.getEmergencyContact().getCityName());
        txtKecamatanInformasiKerabat.setText(application.getEmergencyContact().getDistrictName());
        txtKelurahanInformasiKerabat.setText(application.getEmergencyContact().getVillageName());
        txtKodePosInformasiKerabat.setText(application.getEmergencyContact().getZipCode());
        txtAreaPhoneRumahInformasiKerabat.setText(application.getEmergencyContact().getHomePhoneArea());
        txtPhoneRumahInformasiKerabat.setText(application.getEmergencyContact().getHomePhone());
        txtAreaPhoneKantorInformasiKerabat.setText(application.getEmergencyContact().getOfficePhoneArea());
        txtPhoneKantorInformasiKerabat.setText(application.getEmergencyContact().getOfficePhone());
        txtNomorHandphoneInformasiKerabat.setText(application.getEmergencyContact().getMobilePhone());

        //DATA PEKERJAAN
        txtProfesiDataPekerjaan.setText(application.getCompany().getProfessionName());
        txtJenisPekerjaanDataPekerjaan.setText(application.getCompany().getPersonalCustomerTypeName());
        txtNamaPerusahaanDataPekerjaan.setText(application.getCompany().getName());
        txtAlamatDataPekerjaan.setText(application.getCompany().getAddress());
//        txtRtDataPekerjaan.setText(application.getCompany().getRt());
//        txtRwDataPekerjaan.setText(application.getCompany().getRw());
        txtKotaDataPekerjaan.setText(application.getCompany().getCityName());
        txtKecamatanDataPekerjaan.setText(application.getCompany().getDistrictName());
        txtKelurahanDataPekerjaan.setText(application.getCompany().getVillageName());
        txtKodePosDataPekerjaan.setText(application.getCompany().getZipCode());
        txtAreaPhoneDataPekerjaan.setText(application.getCompany().getAreaPhone());
        txtPhoneDataPekerjaan.setText(application.getCompany().getPhone());
        txtPenghasilanTetapDataPekerjaan.setText(Util.formatNominal(application.getCompany().getMonthlyFixedIncome()));
        txtPenghasilanPasanganDataPekerjaan.setText(Util.formatNominal(application.getCompany().getSpouseIncome()));
        txtPenghasilanLainDataPekerjaan.setText(Util.formatNominal(application.getCompany().getMonthlyVariableIncome()));
        txtBiayaHidupDataPekerjaan.setText(Util.formatNominal(application.getCompany().getLivingCostAmount()));
        txtJobPositionDataPekerjaan.setText(application.getCompany().getJobPositionName());
        txtJobTypeDataPekerjaan.setText(application.getCompany().getJobTypeName());
        txtIndustriDataPekerjaan.setText(application.getCompany().getIndustryTypeName());
        txtBekerjaSejakDataPekerjaan.setText(application.getCompany().getEmploymentSinceYear());
//        txtCounterpartDataPekerjaan.setText(application.getCompany().getCounterpart());
//        txtDebtBusinessScaleDataPekerjaan.setText(application.getCompany().getDebtBusinessScale());
//        txtDebtBusinessGroupDataPekerjaan.setText(application.getCompany().getDebtGroup());
//        txtIsAffiliateWithPpDataPekerjaan.setText("0".equalsIgnoreCase(application.getCompany().getIsAffiliateWithPP()) ? "No" : "Yes");

        //Data Kartu Kredit
        txtNamaBankKartuKredit.setText(application.getDataCreditCard().getBankName());
//        txtNoKartuKredit.setText(application.getDataCreditCard().getIdCard());
        txtJenisKartuKredit.setText(application.getDataCreditCard().getCardType());
        txtLimitKartuKredit.setText(application.getDataCreditCard().getCardLimit());
        txtKadaluarsaTahunKartuKredit.setText(application.getDataCreditCard().getMembershipOldYear());
        txtKadaluarsaBulanKartuKredit.setText(application.getDataCreditCard().getMembershipOldMonth());

        // Data Kartu Membership
//        txtNoMembership.setText(application.getMembershipCard().getIdMember());
        txtTanggalEfektifMembership.setText(application.getMembershipCard().getEffectiveDate());
        txtTanggalExpiredMembership.setText(application.getMembershipCard().getExpiredDate());

        // Agunan
//        if (!"E".equalsIgnoreCase(application.getDataType())) {
//            txtMerkKendaraan.setText(application.getDataAgunan().getMerk());
//            txtTahunKendaraan.setText(application.getDataAgunan().getYear());
//            txtWarnaKendaraan.setText(application.getDataAgunan().getColour());
//            txtIsiSilinder.setText(application.getDataAgunan().getCc());
//            txtNoPolisi.setText(application.getDataAgunan().getNoPolisi());
//            txtNoRangka.setText(application.getDataAgunan().getNoRangka());
//            txtNoMesin.setText(application.getDataAgunan().getNoMesin());
//            txtBpkbNama.setText(application.getDataAgunan().getBpkbName());
//        }
      /*  if (!"E".equalsIgnoreCase(application.getDataType())) {
            txtMerkKendaraan.setText(application.getDataAgunan().getMerk());
            txtTahunKendaraan.setText(application.getDataAgunan().getYear());
            txtWarnaKendaraan.setText(application.getDataAgunan().getColour());
            txtIsiSilinder.setText(application.getDataAgunan().getCc());
            txtNoPolisi.setText(application.getDataAgunan().getNoPolisi());
            txtNoRangka.setText(application.getDataAgunan().getNoRangka());
            txtNoMesin.setText(application.getDataAgunan().getNoMesin());
            txtBpkbNama.setText(application.getDataAgunan().getBpkbName());
        }*/

        //SET DETAIL PRODUCT
        txtProductOffering.setText(application.getDetailProduct().getProductOfferingName());
        txtJumlahAsset.setText(application.getDetailProduct().getNumOfAssetUnit());
        txtTenor.setText(application.getDetailProduct().getTenor());
//        txtPos.setText(application.getDetailProduct().getPostName());

        //DATA ASURANSI
        txtAsuransiAgunan.setText(application.getInsurance().getCoverageType());
//        txtIsPersonalAccident.setText("0".equalsIgnoreCase(application.getInsurance().getIsPersonalAccident()) ? "No" : "Yes");
        if ("E".equalsIgnoreCase(application.getDataType())) {
            trPremiAsuransi.setVisibility(View.VISIBLE);
            trPremiAsuransiAgunan.setVisibility(View.GONE);
            trPremiAsuransiJiwa.setVisibility(View.GONE);
//            txtPremiAsuransi.setText(application.getInsurance().getPremiumAmountToCustomer());
        } else {
            trPremiAsuransi.setVisibility(View.GONE);
            trPremiAsuransiAgunan.setVisibility(View.VISIBLE);
            trPremiAsuransiJiwa.setVisibility(View.VISIBLE);
            txtPremiAsuransiAgunan.setText(application.getInsurance().getPremiAsuransiAgunan());
            txtPremiAsuransiJiwa.setText(application.getInsurance().getPremiAsuransiJiwa());
        }

        //DATA PERHITUNGAN
        if ("E".equalsIgnoreCase(application.getDataType())) {
            lnPerhitunganElektronik.setVisibility(View.VISIBLE);
            lnPerhitunganKendaraan.setVisibility(View.GONE);

            txtPurchasePriceDataPerhitunganElektronik.setText(Util.formatNominal
                    ("".equalsIgnoreCase(application.getDetailFinancing().getPurchasePrice()) ?
                            "0" : application.getDetailFinancing().getPurchasePrice()));
            txtDiscountDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getDiscount()) ? "0" : application.getDetailFinancing().getDiscount()));
            txtDpDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getDownPayment()) ? "0" : application.getDetailFinancing().getDownPayment()));
//            txtNtfDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getNTF()) ? "0" : application.getDetailFinancing().getNTF()));
            String flaterateString = application.getDetailFinancing().getFlatRate() + getResources().getString(R.string.percentSymbol);
            txtFlateRateDataPerhitunganElektronik.setText(flaterateString);
            txtBiayaLainnyaDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getOtherFee()) ? "0" : application.getDetailFinancing().getOtherFee()));
            txtJumlahPembiayaanDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getTotalFinancing()) ? "0" : application.getDetailFinancing().getTotalFinancing()));
            txtBungaPembiayaanDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getInterestFinancing()) ? "0" : application.getDetailFinancing().getInterestFinancing()));
            txtTotalPinjamanDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getTotalLoan()) ? "0" : application.getDetailFinancing().getTotalLoan()));
//            txtAngsuranDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getInstallmentAmount()) ? "0" : application.getDetailFinancing().getInstallmentAmount()));
            txtBiayaAdministrasiDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getAdminFee()) ? "0" : application.getDetailFinancing().getAdminFee()));
            txtSetorPertamaDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getFirstInstallmentAmmount()) ? "0" : application.getDetailFinancing().getFirstInstallmentAmmount()));
            txtAngsuranPertamaDataPerhitunganElektronik.setText(application.getDetailFinancing().getFirstInstallment());
            txtInRefundSubsidiDataPerhitunganElektronik.setText(Util.formatNominal("".equalsIgnoreCase(application.getDetailFinancing().getSubsidyRefund()) ? "0" : application.getDetailFinancing().getSubsidyRefund()));
        } else {
            lnPerhitunganElektronik.setVisibility(View.GONE);
            lnPerhitunganKendaraan.setVisibility(View.VISIBLE);

        }

        //DETAIL ASSET
        txtSupplier.setText(application.getAssetMaster().getSupplierName());
        txtBankAccount.setText(application.getAssetMaster().getSupplierBankAccountName());
        txtMarketingSupplier.setText(application.getAssetMaster().getSalesmanName());


        //SET ASSET DATA

//        if (application.getAssets() != null) {
//            if ("E".equalsIgnoreCase(application.getDataType())) {
//                List<Asset> listAsset = application.getAssets();
//                int idx = 1;
//                for (Asset a : listAsset) {
//                    View assetView = getLayoutInflater().inflate(R.layout.view_item_form_detail_asset, null);
//
//                    TextView tvAssetHeader = (TextView) assetView.findViewById(R.id.txt_asset_item_header);
//                    TextView tvNamaBarang = (TextView) assetView.findViewById(R.id.txt_nama_barang_asset_item);
//                    TextView tvPrice = (TextView) assetView.findViewById(R.id.txt_price_asset_item);
//                    TextView tvDP = (TextView) assetView.findViewById(R.id.txt_dp_asset_item);
//                    TextView tvDiscount = (TextView) assetView.findViewById(R.id.txt_discount_asset_item);
//
//                    tvAssetHeader.setText(tvAssetHeader.getText() + " " + idx);
//                    tvNamaBarang.setText(a.getAssetName());
//                    tvPrice.setText(Util.formatNominal(a.getOtrPrice()));
//                    tvDP.setText(Util.formatNominal(a.getDpAmount()));
//                    tvDiscount.setText(Util.formatNominal(a.getDiscount()));
//
//                    lnAssetDetailProductContainer.addView(assetView);
//                    idx++;
//                }
//            } else {
//                List<Asset> listAssetKendaraan = application.getAssets();
//                int idx = 1;
//                for (Asset a : listAssetKendaraan) {
//                    View assetViewKendaraan = getLayoutInflater().inflate(R.layout.view_item_form_detail_asset_kendaraan, null);
//
//                    TextView txtTitle = (TextView) assetViewKendaraan.findViewById(R.id.txt_asset_item_header);
//                    TextView txtMerk = (TextView) assetViewKendaraan.findViewById(R.id.txt_merk_kendaraan);
//                    TextView txtType = (TextView) assetViewKendaraan.findViewById(R.id.txt_type_model);
//                    TextView txtTahun = (TextView) assetViewKendaraan.findViewById(R.id.txt_tahun_kendaraan);
//                    TextView txtWarna = (TextView) assetViewKendaraan.findViewById(R.id.txt_warna_kendaraan);
//                    TextView txtIsiSilinder = (TextView) assetViewKendaraan.findViewById(R.id.txt_isi_silinder);
//                    TextView txtNoPolisi = (TextView) assetViewKendaraan.findViewById(R.id.txt_no_polisi);
//                    TextView txtNoRangka = (TextView) assetViewKendaraan.findViewById(R.id.txt_no_rangka);
//                    TextView txtNoMesin = (TextView) assetViewKendaraan.findViewById(R.id.txt_no_mesin);
//
////                    txtTitle.setText(txtTitle.getText() + " " + idx);
////                    txtMerk.setText(a.getMerk());
////                    txtType.setText(a.getType());
////                    txtTahun.setText(a.getManufacturingYear());
////                    txtWarna.setText(a.getColor());
////                    txtIsiSilinder.setText(a.getCylinder());
////                    txtNoPolisi.setText(a.getPoliceNo());
////                    txtNoRangka.setText(a.getFrameNo());
////                    txtNoMesin.setText(a.getMachineNo());
//
//                    lnAssetDetailProductContainer.addView(assetViewKendaraan);
//                    idx++;
//                }
//            }
//        }

        // SET ATTACHMENT
//        if (application.getAttachmentList() != null)
//            attachmentDetailAdapter.setAttachmentList(application.getAttachmentList());
    }

    @Override
    public void onFailedSubmitPengajuanEditLoad(String message) {
        if ("invalid id".equalsIgnoreCase(message)) {
            va.setDisplayedChild(0);
        } else {
            va.setDisplayedChild(1);
        }
    }

    @Override
    public void onDetailTokenExpired() {
        mRefreshTokenPresenter.refreshToken(token);
    }

    // PROCESS IGNORE

    @Override
    public void onPreLoadProcessIgnore() {
        progressDialog.show();
    }

    @Override
    public void onSuccessProcessIgnore() {
        progressDialog.dismiss();
        Toast.makeText(this, "Berhasil Update Pengajuan", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new RefreshListKpm());
        finish();
    }

    @Override
    public void onFailedProcessIgnore(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "Gagal Update Pengajuan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessIgnoreTokenExpired() {
        mRefreshTokenPresenter.refreshToken(token);
    }

    // REFRESH TOKEN

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        progressDialog.dismiss();
        this.token = token;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.apply();
        va.setDisplayedChild(1);
    }

    @Override
    public void onFailedRefreshToken(String message) {
        progressDialog.dismiss();
        txtError.setText(message);
        va.setDisplayedChild(1);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
