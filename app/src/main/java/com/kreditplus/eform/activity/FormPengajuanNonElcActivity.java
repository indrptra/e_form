package com.kreditplus.eform.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.dialog.BlacklistDialog;
import com.kreditplus.eform.dialog.CodePersetujuanDialog;
import com.kreditplus.eform.dialog.DialogPersyaratan;
import com.kreditplus.eform.dialog.PersetujuanTidakCancel;
import com.kreditplus.eform.dialog.YesNoDialog;
import com.kreditplus.eform.helper.CameraCustomFrame;
import com.kreditplus.eform.helper.Constant;
import com.kreditplus.eform.helper.EmailValidation;
import com.kreditplus.eform.helper.ImageViewRule;
import com.kreditplus.eform.helper.KodeAreaRule;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.helper.NoHpRule;
import com.kreditplus.eform.helper.KtpRule;
import com.kreditplus.eform.helper.NoMesinRule;
import com.kreditplus.eform.helper.NotEmptyRule;
import com.kreditplus.eform.helper.NotZeroRule;
import com.kreditplus.eform.helper.NotZeroRuleBiayaHidup;
import com.kreditplus.eform.helper.NotZeroRulePengahsilanTetap;
import com.kreditplus.eform.helper.NpwpRule;
import com.kreditplus.eform.helper.RTRWRule;
import com.kreditplus.eform.helper.RadioGroupRule;
import com.kreditplus.eform.helper.SpinnerRule;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.Aobranch;
import com.kreditplus.eform.model.ArrayList.AttachmentArrayList;
import com.kreditplus.eform.model.ArrayList.JenisKendaraanArrayList;
import com.kreditplus.eform.model.ArrayList.MarketingSupplierArrayList;
import com.kreditplus.eform.model.ArrayList.MerkKendaraanArrayList;
import com.kreditplus.eform.model.ArrayList.PosArrayList;
import com.kreditplus.eform.model.ArrayList.ProductOfferingArrayList;
import com.kreditplus.eform.model.ArrayList.RekomendasiArrayList;
import com.kreditplus.eform.model.ArrayList.SupplierMasterArrayList;
import com.kreditplus.eform.model.ArrayList.TahunKendaraanArrayList;
import com.kreditplus.eform.model.ArrayList.TenorArrayList;
import com.kreditplus.eform.model.ArrayList.TipeKendaraanArrayList;
import com.kreditplus.eform.model.ArrayList.TipePengajuanArrayList;
import com.kreditplus.eform.model.BranchMaster;
import com.kreditplus.eform.model.KelurahanResponse;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.ResultAobranch;
import com.kreditplus.eform.model.TahunProduksiResponse;
import com.kreditplus.eform.model.MerkKendaraanResponse;
import com.kreditplus.eform.model.bus.ConfirmCodeSignature;
import com.kreditplus.eform.model.bus.CropPhotoEvent;
import com.kreditplus.eform.model.bus.DialogSyaratBus;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.bus.ResendCodeSignatureEvent;
import com.kreditplus.eform.model.bus.SignatureEvent;
import com.kreditplus.eform.model.bus.StatusSyncKendaraan;
import com.kreditplus.eform.model.bus.SwitchCamera;
import com.kreditplus.eform.model.bus.SwitchCameraOther;
import com.kreditplus.eform.model.response.BlackListResponse;
import com.kreditplus.eform.model.response.CheckEfNumberResponse;
import com.kreditplus.eform.model.response.DetailPerhitunganKendaraanResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;
import com.kreditplus.eform.model.response.JenisKendaraanResponse;
import com.kreditplus.eform.model.response.MarketingSupplierResponse;
import com.kreditplus.eform.model.response.PosListDownResponse;
import com.kreditplus.eform.model.response.ProductOffTenorResponse;
import com.kreditplus.eform.model.response.ProductOfferingResponse;
import com.kreditplus.eform.model.response.RecomendationResponse;
import com.kreditplus.eform.model.response.ReferalCodeResponse;
import com.kreditplus.eform.model.response.SupplierResponse;
import com.kreditplus.eform.model.response.WilayahCabangResponse;
import com.kreditplus.eform.model.response.objecthelper.AoBranchObjt;
import com.kreditplus.eform.model.response.objecthelper.Application;
import com.kreditplus.eform.model.response.objecthelper.ApplicationBlacklist;
import com.kreditplus.eform.model.response.objecthelper.AssetToken;
import com.kreditplus.eform.model.response.objecthelper.BodyToken;
import com.kreditplus.eform.model.response.objecthelper.CekKodeProgramObjct;
import com.kreditplus.eform.model.response.objecthelper.FaqObjt;
import com.kreditplus.eform.model.response.objecthelper.Industri;
import com.kreditplus.eform.model.response.objecthelper.JobPosition;
import com.kreditplus.eform.model.response.objecthelper.JobType;
import com.kreditplus.eform.model.response.objecthelper.Profession;
import com.kreditplus.eform.model.response.objecthelper.Syarat;
import com.kreditplus.eform.model.response.objecthelper.TidakCancel;
import com.kreditplus.eform.presenter.AttachmentKendaraanPresenter;
import com.kreditplus.eform.presenter.BlackListPresenter;
import com.kreditplus.eform.presenter.CekKodeProgramPresenter;
import com.kreditplus.eform.presenter.CheckEfNumberPresenter;
import com.kreditplus.eform.presenter.CodeSignaturePresenter;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.DetailPerhitunganKendaraanPresenter;
import com.kreditplus.eform.presenter.GetReferalCodePresenter;
import com.kreditplus.eform.presenter.HargaAgunanKendaraanPresenter;
import com.kreditplus.eform.presenter.JenisKendaraanPresenter;
import com.kreditplus.eform.presenter.KelurahanPresenter;
import com.kreditplus.eform.presenter.PengajuanDetailPresenter;
import com.kreditplus.eform.presenter.PosKreditPresenter;
import com.kreditplus.eform.presenter.ProductOffTenorPresenter;
import com.kreditplus.eform.presenter.RecomendationPresenter;
import com.kreditplus.eform.presenter.SearchMarketingSupplierPresenter;
import com.kreditplus.eform.presenter.SearchProductOfferingPresenter;
import com.kreditplus.eform.presenter.SearchSupplierMasterPresenter;
import com.kreditplus.eform.presenter.SignoutPresenter;
import com.kreditplus.eform.presenter.SinkronisasiKendaraanPresenter;
import com.kreditplus.eform.presenter.SyaratDanKetentuanPresenter;
import com.kreditplus.eform.presenter.TahunProduksiKendaraanPresenter;
import com.kreditplus.eform.presenter.PilihKendaraanPresenter;
import com.kreditplus.eform.presenter.WilayahCabangPresenter;
import com.kreditplus.eform.presenter.mvpview.AttachmentKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.BlackListMvpView;
import com.kreditplus.eform.presenter.mvpview.CekKodeProgramMvpView;
import com.kreditplus.eform.presenter.mvpview.CheckEfNumberMvpView;
import com.kreditplus.eform.presenter.mvpview.CodeSignatureMvpView;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.DetailPerhitunganKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.HargaAgunanKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.JenisKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.KelurahanMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanDetailMvpView;
import com.kreditplus.eform.presenter.mvpview.PosKreditvpView;
import com.kreditplus.eform.presenter.mvpview.ProductOffTenorMvpView;
import com.kreditplus.eform.presenter.mvpview.RecomendationMvpView;
import com.kreditplus.eform.presenter.mvpview.ReferalCodeMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchMarketingSupplierMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchProductOfferingMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchSupplierMasterMvpView;
import com.kreditplus.eform.presenter.mvpview.SyaratDanKetentuanMvpView;
import com.kreditplus.eform.presenter.mvpview.SyncKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.TahunProduksiKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.MerkKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.WilayahCabangMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;
import com.kreditplus.eform.view.IndonesianCurrencyEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.santalu.maskedittext.MaskEditText;

import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import itsmagic.present.permissionhelper.util.PermissionHelper;
import okhttp3.RequestBody;

public class FormPengajuanNonElcActivity extends BaseActivity implements Validator.ValidationListener,
        BlackListMvpView,
        CodeSignatureMvpView,
        SyaratDanKetentuanMvpView,
        CoordinateMvpView,
        RecomendationMvpView,
        MerkKendaraanMvpView,
        TahunProduksiKendaraanMvpView,
        HargaAgunanKendaraanMvpView,
        DetailPerhitunganKendaraanMvpView,
        ReferalCodeMvpView,
        SearchSupplierMasterMvpView,
        SearchMarketingSupplierMvpView,
        SearchProductOfferingMvpView,
        ProductOffTenorMvpView,
        JenisKendaraanMvpView,
        WilayahCabangMvpView,
        SyncKendaraanMvpView,
        AttachmentKendaraanMvpView,
        PosKreditvpView,
        PengajuanDetailMvpView,
        CheckEfNumberMvpView,
        KelurahanMvpView, CekKodeProgramMvpView {

    public static final int SIGNATURE_PEMOHON = 501;
    public static final int SIGNATURE_PASANGAN = 502;
    public static final int TAKE_CAMERA_PENGAJUAN_1 = 101;
    public static final int TAKE_CAMERA_PENGAJUAN_2 = 102;
    public static final int TAKE_CAMERA_PENGAJUAN_3 = 103;
    public static final int TAKE_CAMERA_PENGAJUAN_4 = 104;
    public static final int TAKE_CAMERA_PENGAJUAN_5 = 105;
    public static final int TAKE_CAMERA_PENGAJUAN_6 = 106;
    public static final int TAKE_CAMERA_PENGAJUAN_7 = 107;
    public static final int TAKE_CAMERA_PENGAJUAN_8 = 108;
    public static final int TAKE_CAMERA_PENGAJUAN_9 = 109;
    public static final int TAKE_CAMERA_PENGAJUAN_10 = 110;
    public static final int TAKE_CAMERA_PENGAJUAN_11 = 111;
    public static final int TAKE_CAMERA_PENGAJUAN_12 = 112;
    public static final int TAKE_CAMERA_PENGAJUAN_13 = 113;
    public static final int TAKE_CAMERA_PENGAJUAN_14 = 114;
    public static final int TAKE_CAMERA_PENGAJUAN_15 = 115;
    public static final int TAKE_CAMERA_PENGAJUAN_16 = 116;
    public static final int TAKE_CAMERA_PENGAJUAN_17 = 117;
    public static final int TAKE_CAMERA_PENGAJUAN_18 = 118;
    public static final int TAKE_CAMERA_PENGAJUAN_19 = 119;
    public static final int TAKE_CAMERA_PENGAJUAN_20 = 120;
    public static final int TAKE_CAMERA_PENGAJUAN_21 = 121;
    public static final int TAKE_CAMERA_PENGAJUAN_22 = 122;
    public static final int TAKE_CAMERA_PENGAJUAN_23 = 123;
    public static final int TAKE_CAMERA_PENGAJUAN_24 = 124;
    public static final int TAKE_CAMERA_PENGAJUAN_25 = 125;
    public static final int TAKE_CAMERA_PENGAJUAN_26 = 126;
    public static final int TAKE_CAMERA_PENGAJUAN_27 = 127;
    public static final int TAKE_CAMERA_PENGAJUAN_28 = 128;
    public static final int TAKE_CAMERA_PENGAJUAN_29 = 129;

    @Inject
    ApiService apiService;
    @Inject
    DatabaseService databaseService;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SharedPreferences.Editor editor;

    @Inject
    SignoutPresenter mSignoutPresenter;

    @BindView(R.id.rl_header_validasi_pengajuan)
    RelativeLayout rlHeaderValidasiPengajuan;
    @NotEmpty
    @BindView(R.id.edtValidasiNoKtp)
    EditText edtValidasiNoKtp;
    @NotEmpty
    @BindView(R.id.edtValidasiTanggalLahir)
    EditText edtValidasiTanggalLahir;
    @NotEmpty
    @BindView(R.id.edtValidasiNoHp)
    EditText edtValidasiNoHp;
    //    @NotEmpty
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @BindView(R.id.edtValidasiNamaLegal)
    EditText edtValidasiNamaLegal;
    //    @NotEmpty
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @BindView(R.id.edtValidasiNamaIbuKandung)
    EditText edtValidasiNamaIbuKandung;
    @BindView(R.id.til_nama_legal)
    TextInputLayout tilNamaLegal;
    @BindView(R.id.til_nama_ibu)
    TextInputLayout tilNamaIbu;
    @BindView(R.id.spnValidasiCabang)
    Spinner spnValidasiCabang;
    @BindView(R.id.tvValidasiTipePengajuan)
    TextView tvValidasiTipePengajuan;
    @BindView(R.id.spnValidasiTipePengajuan)
    Spinner spnValidasiTipePengajuan;
    @BindView(R.id.ln_wrapper_data_validasi_awal)
    LinearLayout lnWrapperDataValidasiAwal;
    @BindView(R.id.img_master_header)
    ImageView imgMasterHeader;

    @BindView(R.id.img_drop_down_header_kop)
    ImageView imgDropDownHeaderKop;
    @BindView(R.id.rl_header_kop)
    RelativeLayout rlHeaderKop;
    @BindView(R.id.cvStatusPemohon)
    CardView cvStatusPemohon;
    @NotEmpty
    @BindView(R.id.edtKopStatusKreditmu)
    EditText edtKopStatusKreditmu;
    @NotEmpty
    @BindView(R.id.edtKopStatusPemohon)
    EditText edtKopStatusPemohon;
    @BindView(R.id.spnKopJenisPembiayaan)
    Spinner spnKopJenisPembiayaan;
    @BindView(R.id.llKopJenisPembiayaan)
    LinearLayout llKopJenisPembiayaan;
    @BindView(R.id.spnKopTujuanPenggunaanDana)
    Spinner spnKopTujuanPenggunaanDana;
    @BindView(R.id.llKopTujuanPenggunaanDana)
    LinearLayout llKopTujuanPenggunaanDana;
    @BindView(R.id.tilKopLainTujuanPenggunaanDana)
    TextInputLayout tilKopLainTujuanPenggunaanDana;
    @BindView(R.id.tvWarningMetodePenjualan)
    TextView tvWarningMetodePenjualan;
    @BindView(R.id.spnMetodePenjualan)
    Spinner spnMetodePenjualan;
    @BindView(R.id.lnMetodePenjualan)
    LinearLayout lnMetodePenjualan;
    @BindView(R.id.tilKopLainMetodePenjualan)
    TextInputLayout tilKopLainMetodePenjualan;
    @BindView(R.id.lnContenainerMasterSeader)
    LinearLayout lnContenainerMasterSeader;
    @BindView(R.id.ln_wrapper_master_header)
    LinearLayout lnWrapperMasterHeader;
    @BindView(R.id.img_nama_pemohon)
    ImageView imgNamaPemohon;

    @BindView(R.id.header_data_nama_pemohon)
    CardView rlHeaderDataNamaPemohon;
    @BindView(R.id.img_drop_down_nama_pemohon)
    ImageView imgDropDownNamaPemohon;
    @BindView(R.id.rl_header_nama_pemohon)
    RelativeLayout rlHeaderNamaPemohon;
    @BindView(R.id.tvNamaPemohon)
    TextView tvNamaPemohon;
    @BindView(R.id.spnNamaPemohon)
    Spinner spnNamaPemohon;
    @BindView(R.id.lnContentNamaPemohon)
    LinearLayout lnContentNamaPemohon;
    @BindView(R.id.ln_wrapper_data_nama_pemohon)
    LinearLayout lnWrapperDataNamaPemohon;

    @BindView(R.id.img_drop_down_pribadi)
    ImageView imgDropDownPribadi;
    @NotEmpty
    @BindView(R.id.edtPribadiNamaPemohon)
    EditText edtPribadiNamaPemohon;
//    @Length( max = 50, message = "Data yang diinput maksimal 50 karakter")
    @NotEmpty
    @BindView(R.id.edtPribadiNamaLengkapPemohon)
    EditText edtPribadiNamaLengkapPemohon;
    @NotEmpty
    @BindView(R.id.edtPribadiNoKtp)
    EditText edtPribadiNoKtp;
    @BindView(R.id.edtPribadiNoKK)
    EditText edtPribadiNoKK;
    @NotEmpty
    @BindView(R.id.edt_terbit_ktp_pribadi)
    EditText edtTerbitKtpPribadi;
    @NotEmpty
    @BindView(R.id.edtPribadiTempatLahir)
    EditText edtPribadiTempatLahir;
    @NotEmpty
    @BindView(R.id.edtPribadiTanggalLahirPribadi)
    EditText edtPribadiTanggalLahirPribadi;
    @BindView(R.id.rbPribadiLaki)
    RadioButton rbPribadiLaki;
    @BindView(R.id.rbPribadiPerempuan)
    RadioButton rbPribadiPerempuan;
    @BindView(R.id.rgPribadiJenisKelamin)
    RadioGroup rgPribadiJenisKelamin;
    @BindView(R.id.spnPribadiStatusPernikahan)
    Spinner spnPribadiStatusPernikahan;
    @BindView(R.id.rbPerjanjianPisahHartaAda)
    RadioButton rbPerjanjianPisahHartaAda;
    @BindView(R.id.rbPerjanjianPisahHartaTidakAda)
    RadioButton rbPerjanjianPisahHartaTidakAda;
    @BindView(R.id.llPerjanjianPisahHarta)
    LinearLayout llPerjanjianPisahHarta;
    @BindView(R.id.rgPerjanjianPisahHarta)
    RadioGroup rgPerjanjianPisahHarta;
    @NotEmpty
    @BindView(R.id.edt_jml_tanggungan_pribadi)
    EditText edtJmlTanggunganPribadi;
    @BindView(R.id.spn_status_rumah_pribadi)
    Spinner spnStatusRumahPribadi;
    @BindView(R.id.ln_status_rumah_pribadi)
    LinearLayout lnStatusRumahPribadi;
    @NotEmpty
    @BindView(R.id.edt_tinggal_sejak_tahun_pribadi)
    EditText edtTinggalSejakTahunPribadi;
    @NotEmpty
    @BindView(R.id.edt_tinggal_sejak_bulan_pribadi)
    EditText edtTinggalSejakBulanPribadi;
    @BindView(R.id.spn_pendidikan_pribadi)
    Spinner spnPendidikanPribadi;
    @BindView(R.id.ln_pendidikan)
    LinearLayout lnPendidikan;
    @BindView(R.id.ln_agama_pribadi)
    LinearLayout lnAgamaPribadi;
    @BindView(R.id.spn_agama_pribadi)
    Spinner spnAgamaPribadi;
    @BindView(R.id.tilNoNpwpPribadi)
    TextInputLayout tilNoNpwpPribadi;
    @BindView(R.id.til_no_npwp_detail)
    TextInputLayout tilNoNpwpDetail;
    @BindView(R.id.edt_no_npwp_pribadi)
    MaskEditText edtNoNpwpPribadi;
    @BindView(R.id.edt_no_npwp_detail)
    MaskEditText edtNoNpwpDetail;
    @BindView(R.id.img_drop_down_npwp)
    ImageView imgDropDownNpwp;
    @NotEmpty
    @BindView(R.id.edt_email_pribadi)
    EditText edtEmailPribadi;
    @NotEmpty
    @BindView(R.id.edt_handphone_pribadi)
    EditText edtHandphonePribadi;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @NotEmpty
    @BindView(R.id.edt_nama_ibu_pribadi)
    EditText edtNamaIbuPribadi;
    @BindView(R.id.lnContainerDataPribadi)
    LinearLayout lnContainerDataPribadi;
    @BindView(R.id.ln_wrapper_data_pribadi)
    LinearLayout lnWrapperDataPribadi;
    @BindView(R.id.img_alamat)
    ImageView imgAlamat;

    @BindView(R.id.img_drop_down_alamat)
    ImageView imgDropDownAlamat;
    @BindView(R.id.cv_alamat_tinggal)
    CardView cvAlamatTinggal;
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_tinggal)
    EditText edtAlamatTinggal;
    @BindView(R.id.tv_alamat_tinggal)
    TextView tvAlamatTinggal;
    @NotEmpty
    @BindView(R.id.edt_rt_tinggal)
    EditText edtRtTinggal;
    @BindView(R.id.tv_rt_tinggal)
    TextView tvRtTinggal;
    @NotEmpty
    @BindView(R.id.edt_rw_tinggal)
    EditText edtRwTinggal;
    @BindView(R.id.tv_rw_tinggal)
    TextView tvRwTinggal;
    @NotEmpty
    @BindView(R.id.act_auto_alamat_pemohon)
    NiceAutoCompleteTextView actAutoAlamatPemohon;
    @BindView(R.id.tv_auto_alamat_tinggal_pemohon)
    TextView tvAutoAlamatTinggalPemohon;
    //    @NotEmpty
    @BindView(R.id.edt_area_phone_tinggal)
    EditText edtAreaPhoneTinggal;
    @NotEmpty
    @BindView(R.id.edt_phone_tinggal)
    EditText edtPhoneTinggal;
    @BindView(R.id.ln_alamat_tinggal_phone)
    LinearLayout lnAlamatTinggalPhone;
    @BindView(R.id.cbx_alamat_ktp)
    CheckBox cbxAlamatKtp;
    @BindView(R.id.cv_alamat_ktp)
    CardView cvAlamatKtp;
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_ktp)
    EditText edtAlamatKtp;
    @BindView(R.id.tv_alamat_ktp)
    TextView tvAlamatKtp;
    @NotEmpty
    @BindView(R.id.edt_rt_ktp)
    EditText edtRtKtp;
    @BindView(R.id.tv_rt_ktp)
    TextView tvRtKtp;
    @NotEmpty
    @BindView(R.id.edt_rw_ktp)
    EditText edtRwKtp;
    @BindView(R.id.tv_rw_ktp)
    TextView tvRwKtp;
    @NotEmpty
    @BindView(R.id.act_auto_ktp_alamat_pemohon)
    NiceAutoCompleteTextView actAutoKtpAlamatPemohon;
    @BindView(R.id.tv_auto_ktp_alamat_pemohon)
    TextView tvAutoKtpAlamatPemohon;
    //    @NotEmpty
    @BindView(R.id.edt_area_phone_ktp)
    EditText edtAreaPhoneKtp;
    @NotEmpty
    @BindView(R.id.edt_phone_ktp)
    EditText edtPhoneKtp;
    @BindView(R.id.ln_alamat_ktp_phone)
    LinearLayout lnAlamatKtpPhone;
    @BindView(R.id.ln_visible_alamat_ktp)
    LinearLayout lnVisibleAlamatKtp;
    @BindView(R.id.ln_wrapper_data_alamat_pemohon)
    LinearLayout lnWrapperDataAlamatPemohon;
    @BindView(R.id.img_data_pasangan)
    ImageView imgDataPasangan;

    @BindView(R.id.img_drop_down_pasangan)
    ImageView imgDropDownPasangan;
    @BindView(R.id.rl_header_data_pasangan_suami_istri)
    RelativeLayout rlHeaderDataPasanganSuamiIstri;
    @NotEmpty
    @BindView(R.id.edtPasanganNama)
    EditText edtPasanganNama;
    @NotEmpty
    @BindView(R.id.edtPasanganNoKtp)
    EditText edtPasanganNoKtp;
    @NotEmpty
    @BindView(R.id.edtPasanganTempatLahir)
    EditText edtPasanganTempatLahir;
    @NotEmpty
    @BindView(R.id.edtPasanganTanggalLahir)
    EditText edtPasanganTanggalLahir;
    @NotEmpty
    @BindView(R.id.edtPasanganKodeAreaTeleponRumah)
    EditText edtPasanganKodeAreaTeleponRumah;
    @NotEmpty
    @BindView(R.id.edtPasanganNoTeleponRumah)
    EditText edtPasanganNoTeleponRumah;
    @BindView(R.id.lnPasanganNoTeleponRumah)
    LinearLayout lnPasanganNoTeleponRumah;
    //    @NotEmpty
    @BindView(R.id.edtPasanganKodeAreaTeleponPerusahaan)
    EditText edtPasanganKodeAreaTeleponPerusahaan;
    //    @NotEmpty
    @BindView(R.id.edtPasanganNoTeleponPerusahaan)
    EditText edtPasanganNoTeleponPerusahaan;
    @BindView(R.id.lnPasanganNoTelepon)
    LinearLayout lnPasanganNoTelepon;
    @BindView(R.id.tilPasanganNoHp)
    TextInputLayout tilPasanganNoHp;
    @NotEmpty
    @BindView(R.id.edtPasanganNoHp)
    EditText edtPasanganNoHp;
    @NotEmpty
    @BindView(R.id.edtPasanganAlamat)
    EditText edtPasanganAlamat;
    @NotEmpty
    @BindView(R.id.actPasanganKota)
    NiceAutoCompleteTextView actPasanganKota;
    //    @NotEmpty
    @BindView(R.id.actPasanganProfesi)
    NiceAutoCompleteTextView actPasanganProfesi;
    //    @NotEmpty
    @BindView(R.id.actPasanganJobType)
    NiceAutoCompleteTextView actPasanganJobType;
    //    @NotEmpty
    @BindView(R.id.actPasanganJobPosition)
    NiceAutoCompleteTextView actPasanganJobPosition;
    //    @NotEmpty
    @BindView(R.id.actPasanganIndustri)
    NiceAutoCompleteTextView actPasanganIndustri;
    @BindView(R.id.rbPasanganPegawaiTetap)
    RadioButton rbPasanganPegawaiTetap;
    @BindView(R.id.rbPasanganPegawaiTidakTetap)
    RadioButton rbPasanganPegawaiTidakTetap;
    @BindView(R.id.rgPasanganStatus)
    RadioGroup rgPasanganStatus;
    @BindView(R.id.lnPasanganStatus)
    LinearLayout lnPasanganStatus;
    //    @NotEmpty
    @BindView(R.id.edtPasanganNamaPerusahaan)
    EditText edtPasanganNamaPerusahaan;
    @NotEmpty
    @BindView(R.id.edtPasanganNamaIbu)
    EditText edtPasanganNamaIbu;
    @BindView(R.id.llPasanganNonElcData)
    LinearLayout llPasanganNonElcData;
    @BindView(R.id.ln_wrapper_data_pasangan)
    LinearLayout lnWrapperDataPasangan;
    @BindView(R.id.img_kerabat)
    ImageView imgKerabat;

    @BindView(R.id.img_drop_down_kerabat)
    ImageView imgDropDownKerabat;
    @BindView(R.id.rl_emergency_contact)
    RelativeLayout rlEmergencyContact;
    @NotEmpty
    @BindView(R.id.edt_nama_kerabat)
    EditText edtNamaKerabat;
    @BindView(R.id.tv_nama_kerabat)
    TextView tvNamaKerabat;
    @BindView(R.id.spn_hubungan_kerabat)
    Spinner spnHubunganKerabat;
    @BindView(R.id.ln_hubungan_kerabat)
    LinearLayout lnHubunganKerabat;
    @BindView(R.id.tv_hubungan_kerabat)
    TextView tvHubunganKerabat;
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_kerabat)
    EditText edtAlamatKerabat;
    @BindView(R.id.tv_alamat_kerabat)
    TextView tvAlamatKerabat;
    @NotEmpty
    @BindView(R.id.edt_rt_kerabat)
    EditText edtRtKerabat;
    @BindView(R.id.tv_rt_kerabat)
    TextView tvRtKerabat;
    @NotEmpty
    @BindView(R.id.edt_rw_kerabat)
    EditText edtRwKerabat;
    @BindView(R.id.tv_rw_kerabat)
    TextView tvRwKerabat;
    @NotEmpty
    @BindView(R.id.act_auto_alamat_kerabat)
    NiceAutoCompleteTextView actAutoAlamatKerabat;
    @BindView(R.id.tv_auto_alamat_kerabat)
    TextView tvAutoAlamatKerabat;
    //    @NotEmpty
    @BindView(R.id.edt_area_phone_rumah_kerabat)
    EditText edtAreaPhoneRumahKerabat;
    @NotEmpty
    @BindView(R.id.edt_phone_rumah_kerabat)
    EditText edtPhoneRumahKerabat;
    @BindView(R.id.ln_emergency_contact_phone_home)
    LinearLayout lnEmergencyContactPhoneHome;
    //    @NotEmpty
    @BindView(R.id.edt_area_phone_kantor_kerabat)
    EditText edtAreaPhoneKantorKerabat;
    @NotEmpty
    @BindView(R.id.edt_phone_kantor_kerabat)
    EditText edtPhoneKantorKerabat;
    @BindView(R.id.ln_emergency_contact_phone_office)
    LinearLayout lnEmergencyContactPhoneOffice;
    @NotEmpty
    @BindView(R.id.edt_hp_kerabat)
    EditText edtHpKerabat;
    @BindView(R.id.tv_hp_kerabat)
    TextView tvHpKerabat;
    @BindView(R.id.ln_wrapper_informasi_kerabat)
    LinearLayout lnWrapperInformasiKerabat;
    @BindView(R.id.img_pekerjaan)
    ImageView imgPekerjaan;

    @BindView(R.id.img_drop_down_pekerjaan)
    ImageView imgDropDownPekerjaan;
    @NotEmpty
    @BindView(R.id.edt_nama_perusahaan)
    EditText edtNamaPerusahaan;
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_perusahaan)
    EditText edtAlamatPerusahaan;
    @NotEmpty
    @BindView(R.id.edt_rt_perusahaan)
    EditText edtRtPerusahaan;
    @NotEmpty
    @BindView(R.id.edt_rw_perusahaan)
    EditText edtRwPerusahaan;
    @NotEmpty
    @BindView(R.id.act_auto_alamat_pekerjaan)
    NiceAutoCompleteTextView actAutoAlamatPekerjaan;
    //    @NotEmpty
    @BindView(R.id.edt_area_phone_perusahaan)
    EditText edtAreaPhonePerusahaan;
    @NotEmpty
    @BindView(R.id.edt_phone_perusahaan)
    EditText edtPhonePerusahaan;
    @BindView(R.id.tilPekerjaanNoHp)
    TextInputLayout tilPekerjaanNoHp;
    @NotEmpty
    @BindView(R.id.edtPekerjaanNoHp)
    EditText edtPekerjaanNoHp;
    @BindView(R.id.ln_data_pekerjaan_phone)
    LinearLayout lnDataPekerjaanPhone;
    @NotEmpty
    @BindView(R.id.edt_bekerja_sejak_perusahaan)
    EditText edtBekerjaSejakPerusahaan;
    @NotEmpty
    @BindView(R.id.act_profesi_perusahaan)
    NiceAutoCompleteTextView actProfesiPerusahaan;
    @NotEmpty
    @BindView(R.id.act_job_type_perusahaan)
    NiceAutoCompleteTextView actJobTypePerusahaan;
    @NotEmpty
    @BindView(R.id.act_job_position_perusahaan)
    NiceAutoCompleteTextView actJobPositionPerusahaan;
    @NotEmpty
    @BindView(R.id.act_industri_perusahaan)
    NiceAutoCompleteTextView actIndustriPerusahaan;
    //    @NotEmpty
    @BindView(R.id.edt_penghasilan_tetap_perusahaan)
    IndonesianCurrencyEditText edtPenghasilanTetapPerusahaan;
    //    @NotEmpty
    @BindView(R.id.edt_penghasilan_lain_perusahaan)
    IndonesianCurrencyEditText edtPenghasilanLainPerusahaan;
    @BindView(R.id.edt_penghasilan_pasangan_perusahaan)
    IndonesianCurrencyEditText edtPenghasilanPasanganPerusahaan;
    @NotEmpty
    @BindView(R.id.edt_biaya_hidup_perusahaan)
    IndonesianCurrencyEditText edtBiayaHidupPerusahaan;
    @BindView(R.id.ln_wrapper_data_pekerjaan)
    LinearLayout lnWrapperDataPekerjaan;
    @BindView(R.id.img_product)
    ImageView imgProduct;

    @BindView(R.id.img_drop_down_product)
    ImageView imgDropDownProduct;
    @BindView(R.id.rl_header_detai_product)
    RelativeLayout rlHeaderDetaiProduct;
    @NotEmpty
    @BindView(R.id.actDtProductSupplier)
    NiceAutoCompleteTextView actDtProductSupplier;
    @NotEmpty
    @BindView(R.id.actDtProductMarketingSupplier)
    NiceAutoCompleteTextView actDtProductMarketingSupplier;
    @NotEmpty
    @BindView(R.id.actDtProductOffering)
    NiceAutoCompleteTextView actDtProductOffering;
    @NotEmpty
    @BindView(R.id.actDtProductPos)
    NiceAutoCompleteTextView actDtProductPos;
    @BindView(R.id.ln_wrapper_detail_product)
    LinearLayout lnWrapperDetailProduct;
    @BindView(R.id.img_asuransi)
    ImageView imgAsuransi;

    @BindView(R.id.img_drop_down_asurani)
    ImageView imgDropDownAsurani;
    @BindView(R.id.ln_wrapper_data_asuransi)
    LinearLayout lnWrapperDataAsuransi;
    @BindView(R.id.img_agunan)
    ImageView imgAgunan;

    @BindView(R.id.img_drop_down_agunan)
    ImageView imgDropDownAgunan;
    @NotEmpty
    @BindView(R.id.act_wilayah_kendaraan)
    EditText actWilayahKendaraan;
    @NotEmpty
    @BindView(R.id.act_cabang_kendaraan)
    EditText actCabangKendaraan;
    @BindView(R.id.act_referal_code)
    EditText actReferalCode;
    @NotEmpty
    @BindView(R.id.act_type_kendaraan)
    NiceAutoCompleteTextView actTypeKendaraan;
    @BindView(R.id.spnJenisKendaraan)
    Spinner spnJenisKendaraan;
    @BindView(R.id.spnMerkKendaraan)
    Spinner spnMerkKendaraan;
    @BindView(R.id.spnTahunKendaraan)
    Spinner spnTahunKendaraan;
    @BindView(R.id.tvJenisKendaraan)
    TextView tvJenisKendaraan;
    @BindView(R.id.tvMerkKendaraan)
    TextView tvMerkKendaraan;
    @BindView(R.id.tvTahunKendaraan)
    TextView tvTahunKendaraan;

    @NotEmpty
    @BindView(R.id.edt_warna_kendaraan)
    EditText edtWarnaKendaraan;
    @BindView(R.id.rb_status_kendaraan_baru)
    RadioButton rbStatusKendaraanBaru;
    @BindView(R.id.rb_status_kendaraan_bekas)
    RadioButton rbStatusKendaraanBekas;
    @BindView(R.id.rg_status_kendaraan)
    RadioGroup rgStatusKendaraan;
    @BindView(R.id.ln_status_kendaraan)
    LinearLayout lnStatusKendaraan;
    @NotEmpty
    @BindView(R.id.edt_isi_silinder)
    EditText edtIsiSilinder;
    @NotEmpty
    @BindView(R.id.edt_no_polisi)
    EditText edtNoPolisi;
    @NotEmpty
    @BindView(R.id.edt_no_rangka)
    EditText edtNoRangka;
    //    @NotEmpty
    @BindView(R.id.edt_no_mesin)
    EditText edtNoMesin;
    @BindView(R.id.rb_bpkb_atas_nama_sendiri)
    RadioButton rbBpkbAtasNamaSendiri;
    @BindView(R.id.rb_bpkb_atas_nama_pasangan)
    RadioButton rbBpkbAtasNamaPasangan;
    @BindView(R.id.rb_bpkb_atas_nama_orang_lain)
    RadioButton rbBpkbAtasNamaOrangLain;
    @BindView(R.id.rg_bpkb_atas_nama)
    RadioGroup rgBpkbAtasNama;
    @BindView(R.id.ln_bpkb_atas_nama)
    LinearLayout lnBpkbAtasNama;
    @BindView(R.id.tilNamaBpkbSendiri)
    TextInputLayout tilNamaBpkbSendiri;
    @BindView(R.id.edtNamaBpkbSendiri)
    EditText edtNamaBpkbSendiri;
    @BindView(R.id.tilNamaBpkbPasangan)
    TextInputLayout tilNamaBpkbPasangan;
    @BindView(R.id.edtNamaBpkbPasangan)
    EditText edtNamaBpkbPasangan;
    @BindView(R.id.tilNamaBpkbOrangLain)
    TextInputLayout tilNamaBpkbOrangLain;
    @BindView(R.id.edtNamaBpkbOrangLain)
    EditText edtNamaBpkbOrangLain;
    @NotEmpty
    @BindView(R.id.edt_masa_berlaku_stnk)
    EditText edtMasaBerlakuStnk;
    @NotEmpty
    @BindView(R.id.edt_masa_berlaku_pajak_stnk)
    EditText edtMasaBerlakuPajakStnk;
    @BindView(R.id.rb_pemakaian_kendaraan_pribadi)
    RadioButton rbPemakaianKendaraanPribadi;
    @BindView(R.id.rb_pemakaian_kendaraan_usaha)
    RadioButton rbPemakaianKendaraanUsaha;
    @BindView(R.id.rg_pemakaian_kendaraan)
    RadioGroup rgPemakaianKendaraan;
    @BindView(R.id.ln_pemakaian_kendaraan)
    LinearLayout lnPemakaianKendaraan;
    @BindView(R.id.rb_negara_produksi_jepang)
    RadioButton rbNegaraProduksiJepang;
    @BindView(R.id.rb_negara_produksi_non_jepang)
    RadioButton rbNegaraProduksiNonJepang;
    @BindView(R.id.rg_negara_produksi)
    RadioGroup rgNegaraProduksi;
    @BindView(R.id.ln_negara_produksi)
    LinearLayout lnNegaraProduksi;
    @BindView(R.id.ln_wrapper_agunan)
    LinearLayout lnWrapperAgunan;
    @BindView(R.id.img_perhitungan)
    ImageView imgPerhitungan;

    @BindView(R.id.img_drop_down_perhitungan)
    ImageView imgDropDownPerhitungan;
    @BindView(R.id.cv_perincian_non_sale_leaseback)
    CardView cvPerincianNonSaleLeaseback;
    @NotEmpty
    @BindView(R.id.edt_harga_agunan)
    IndonesianCurrencyEditText edtHargaAgunan;
//    @NotEmpty
    @BindView(R.id.edt_pinjaman)
    IndonesianCurrencyEditText edtPinjaman;
    @NotEmpty
    @BindView(R.id.edtDownpayment)
    IndonesianCurrencyEditText edtDownpayment;
    @NotEmpty
    @BindView(R.id.edt_biaya_admin_perhitungan_kendaraan)
    IndonesianCurrencyEditText edtBiayaAdminPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edt_premi_asuransi_perhitungan_agunan_kendaraan)
    IndonesianCurrencyEditText edtPremiAsuransiPerhitunganAgunanKendaraan;
    @NotEmpty
    @BindView(R.id.edt_premi_asuransi_perhitungan_jiwa_kendaraan)
    IndonesianCurrencyEditText edtPremiAsuransiPerhitunganJiwaKendaraan;
    @NotEmpty
    @BindView(R.id.edt_jumlah_pembiayaan_perhitungan_kendaraan)
    IndonesianCurrencyEditText edtJumlahPembiayaanPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edt_bunga_pembiayaan_kendaraan)
    IndonesianCurrencyEditText edtBungaPembiayaanKendaraan;
    @NotEmpty
    @BindView(R.id.edt_total_pinjaman_perhitungan_kendaraan)
    IndonesianCurrencyEditText edtTotalPinjamanPerhitunganKendaraan;
    @BindView(R.id.cv_informasi_perincian_pembiayaan)
    CardView cvInformasiPerincianPembiayaan;
    @BindView(R.id.btn_refresh_calculate)
    Button btnRefreshCalculate;
    @BindView(R.id.tilPokokPembiayaan)
    TextInputLayout tilPokokPembiayaan;
    @NotEmpty
    @BindView(R.id.edtPokokPembiayaan)
    IndonesianCurrencyEditText edtPokokPembiayaan;
    @BindView(R.id.til_angsuran_perbulan_perhitungan_kendaraan)
    TextInputLayout tilAngsuranPerbulanPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edt_angsuran_perbulan_perhitungan_kendaraan)
    IndonesianCurrencyEditText edtAngsuranPerbulanPerhitunganKendaraan;
    @BindView(R.id.til_flat_pertahun_perhitungan_kendaraan)
    TextInputLayout tilFlatPertahunPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edt_flat_pertahun_perhitungan_kendaraan)
    EditText edtFlatPertahunPerhitunganKendaraan;
    @BindView(R.id.spn_tenor_perhitungan_kendaraan)
    Spinner spnTenorPerhitunganKendaraan;
    @BindView(R.id.ln_tenor_perhitungan_kendaraan)
    LinearLayout lnTenorPerhitunganKendaraan;
    @BindView(R.id.tilBiayaPnbpFidusiaPerhitunganKendaraan)
    TextInputLayout tilBiayaPnbpFidusiaPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edtBiayaPnbpFidusiaPerhitunganKendaraan)
    IndonesianCurrencyEditText edtBiayaPnbpFidusiaPerhitunganKendaraan;
    @BindView(R.id.til_stnk_perhitungan_kendaraan)
    TextInputLayout tilStnkPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edt_stnk_perhitungan_kendaraan)
    IndonesianCurrencyEditText edtStnkPerhitunganKendaraan;
    @BindView(R.id.tilBiayaNotarisPerhitunganKendaraan)
    TextInputLayout tilBiayaNotarisPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edtBiayaNotarisPerhitunganKendaraan)
    IndonesianCurrencyEditText edtBiayaNotarisPerhitunganKendaraan;
    @BindView(R.id.tilAngsuranPertamaPerhitunganKendaraan)
    TextInputLayout tilAngsuranPertamaPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edt_angsuran_pertama_perhitungan_kendaraan)
    IndonesianCurrencyEditText edtAngsuranPertamaPerhitunganKendaraan;
    @BindView(R.id.tilBiayaSurveyPerhitunganKendaraan)
    TextInputLayout tilBiayaSurveyPerhitunganKendaraan;
    @NotEmpty
    @BindView(R.id.edtBiayaSurveyPerhitunganKendaraan)
    IndonesianCurrencyEditText edtBiayaSurveyPerhitunganKendaraan;
    @BindView(R.id.ln_wrapper_data_perhitungan)
    LinearLayout lnWrapperDataPerhitungan;
    @BindView(R.id.img_keterangan)
    ImageView imgKeterangan;

    @BindView(R.id.spnDataAsuransi)
    Spinner spnDataAsuransi;

    @BindView(R.id.ln_wrapper_keleluasaan)
    LinearLayout lnWrapperKeleluasaan;
    @BindView(R.id.ln_wrapper_data_npwp)
    LinearLayout lnWrapperNpwp;
    @BindView(R.id.header_data_keleluasaan)
    CardView rlHeaderKeleluasaan;
    @BindView(R.id.content_data_keleluasaan)
    RelativeLayout rlDataKeleluasaan;
    @BindView(R.id.content_data_npwp)
    RelativeLayout rlDataNpwp;
    @BindView(R.id.img_keleluasaan)
    ImageView imgKeleluasaan;
    @BindView(R.id.img_drop_down_keleluasaan)
    ImageView imgDropDownKeleluasaan;

    @BindView(R.id.img_drop_down_keterangan)
    ImageView imgDropDownKeterangan;
    @BindView(R.id.edt_keterangan)
    EditText edtKeterangan;
    @BindView(R.id.ln_wrapper_keterangan)
    LinearLayout lnWrapperKeterangan;
    @BindView(R.id.img_attachment)
    ImageView imgAttachment;

    @BindView(R.id.img_drop_down_attachment)
    ImageView imgDropDownAttachment;
    @BindView(R.id.rl_header_image_attachment)
    RelativeLayout rlHeaderImageAttachment;
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
    @BindView(R.id.pb_image_25)
    ProgressBar pbImage25;
    @Nullable
    @BindView(R.id.pb_image_26)
    ProgressBar pbImage26;
    @Nullable
    @BindView(R.id.pb_image_27)
    ProgressBar pbImage27;
    @Nullable
    @BindView(R.id.pb_image_28)
    ProgressBar pbImage28;
    @Nullable
    @BindView(R.id.pb_image_29)
    ProgressBar pbImage29;
    @Nullable
    @BindView(R.id.img_camera_25)
    ImageView imgCamera25;
    @Nullable
    @BindView(R.id.img_camera_26)
    ImageView imgCamera26;
    @Nullable
    @BindView(R.id.img_camera_27)
    ImageView imgCamera27;
    @Nullable
    @BindView(R.id.img_camera_28)
    ImageView imgCamera28;
    @Nullable
    @BindView(R.id.img_camera_29)
    ImageView imgCamera29;
    @BindView(R.id.img_take_picture_25)
    ImageView imgTakePicture25;
    @BindView(R.id.img_delete_picture_25)
    ImageView imgDeletePicture25;
    @BindView(R.id.btn_muat_ulang_25)
    Button btnMuatUlang25;
    @Nullable
    @BindView(R.id.img_take_picture_26)
    ImageView imgTakePicture26;
    @Nullable
    @BindView(R.id.img_delete_picture_26)
    ImageView imgDeletePicture26;
    @Nullable
    @BindView(R.id.btn_muat_ulang_26)
    Button btnMuatUlang26;
    @Nullable
    @BindView(R.id.img_take_picture_27)
    ImageView imgTakePicture27;
    @Nullable
    @BindView(R.id.img_delete_picture_27)
    ImageView imgDeletePicture27;
    @Nullable
    @BindView(R.id.btn_muat_ulang_27)
    Button btnMuatUlang27;
    @Nullable
    @BindView(R.id.img_take_picture_28)
    ImageView imgTakePicture28;
    @Nullable
    @BindView(R.id.img_delete_picture_28)
    ImageView imgDeletePicture28;
    @Nullable
    @BindView(R.id.btn_muat_ulang_28)
    Button btnMuatUlang28;
    @Nullable
    @BindView(R.id.img_take_picture_29)
    ImageView imgTakePicture29;
    @Nullable
    @BindView(R.id.img_delete_picture_29)
    ImageView imgDeletePicture29;
    @Nullable
    @BindView(R.id.btn_muat_ulang_29)
    Button btnMuatUlang29;
    @BindView(R.id.ln_take_foto_25)
    LinearLayout lnTakeFoto25;
    @Nullable
    @BindView(R.id.ln_take_foto_26)
    LinearLayout lnTakeFoto26;
    @Nullable
    @BindView(R.id.ln_take_foto_27)
    LinearLayout lnTakeFoto27;
    @Nullable
    @BindView(R.id.ln_take_foto_28)
    LinearLayout lnTakeFoto28;
    @Nullable
    @BindView(R.id.ln_take_foto_29)
    LinearLayout lnTakeFoto29;
    @BindView(R.id.ln_attachment)
    LinearLayout lnAttachment;
    @BindView(R.id.hsv)
    HorizontalScrollView hsv;
    @BindView(R.id.txt_attachment_error)
    TextView txtAttachmentError;

    @BindView(R.id.tvCamera1)
    TextView tvCamera1;
    @BindView(R.id.tvCamera2)
    TextView tvCamera2;
    @BindView(R.id.tvCamera3)
    TextView tvCamera3;
    @BindView(R.id.tvCamera4)
    TextView tvCamera4;
    @BindView(R.id.tvCamera5)
    TextView tvCamera5;
    @BindView(R.id.tvCamera6)
    TextView tvCamera6;
    @BindView(R.id.tvCamera7)
    TextView tvCamera7;
    @BindView(R.id.tvCamera8)
    TextView tvCamera8;
    @BindView(R.id.tvCamera9)
    TextView tvCamera9;
    @BindView(R.id.tvCamera10)
    TextView tvCamera10;
    @BindView(R.id.tvCamera11)
    TextView tvCamera11;
    @BindView(R.id.tvCamera12)
    TextView tvCamera12;
    @BindView(R.id.tvCamera13)
    TextView tvCamera13;
    @BindView(R.id.tvCamera14)
    TextView tvCamera14;
    @BindView(R.id.tvCamera15)
    TextView tvCamera15;
    @BindView(R.id.tvCamera16)
    TextView tvCamera16;
    @BindView(R.id.tvCamera17)
    TextView tvCamera17;
    @BindView(R.id.tvCamera18)
    TextView tvCamera18;
    @BindView(R.id.tvCamera19)
    TextView tvCamera19;
    @BindView(R.id.tvCamera20)
    TextView tvCamera20;
    @BindView(R.id.tvCamera21)
    TextView tvCamera21;
    @BindView(R.id.tvCamera22)
    TextView tvCamera22;
    @BindView(R.id.tvCamera23)
    TextView tvCamera23;
    @BindView(R.id.tvCamera24)
    TextView tvCamera24;
    @BindView(R.id.tvCamera25)
    TextView tvCamera25;
    @BindView(R.id.tvCamera26)
    TextView tvCamera26;
    @BindView(R.id.tvCamera27)
    TextView tvCamera27;
    @BindView(R.id.tvCamera28)
    TextView tvCamera28;
    @BindView(R.id.tvCamera29)
    TextView tvCamera29;

    @BindView(R.id.ln_wrapper_image_pengajuan)
    LinearLayout lnWrapperImagePengajuan;
    @BindView(R.id.img_persetujuan)
    ImageView imgPersetujuan;

    @BindView(R.id.img_drop_down_persetujuan)
    ImageView imgDropDownPersetujuan;
    @BindView(R.id.txt_date_pemohon_persetujuan)
    TextView txtDatePemohonPersetujuan;
    @BindView(R.id.img_ttd_pemohon_persetujuan)
    ImageView imgTtdPemohonPersetujuan;
    @BindView(R.id.txt_ttd_pemohon_error)
    TextView txtTtdPemohonError;
    @BindView(R.id.ln_ttd_pemohon)
    LinearLayout lnTtdPemohon;
    @BindView(R.id.txt_date_pasangan_persetujuan)
    TextView txtDatePasanganPersetujuan;
    @BindView(R.id.img_ttd_pasangan_persetujuan)
    ImageView imgTtdPasanganPersetujuan;
    @BindView(R.id.txt_ttd_pasangan_error)
    TextView txtTtdPasanganError;
    @BindView(R.id.ln_ttd_pemohon_lain)
    LinearLayout lnTtdPemohonLain;
    @BindView(R.id.ln_wrapper_persetujuan)
    LinearLayout lnWrapperPersetujuan;
    @BindView(R.id.img_rekomendasi)
    ImageView imgRekomendasi;
    @BindView(R.id.img_drop_down_rekomendasi)
    ImageView imgDropDownRekomendasi;
    @BindView(R.id.rb_recomendation_yes)
    RadioButton rbRecomendationYes;
    @BindView(R.id.rb_recomendation_no)
    RadioButton rbRecomendationNo;
    @BindView(R.id.rb_group_recomendation)
    RadioGroup rbGroupRecomendation;
    @BindView(R.id.spn_recomendation)
    Spinner spnRecomendation;
    @BindView(R.id.ln_spn_recomendation)
    LinearLayout lnSpnRecomendation;
    @BindView(R.id.edt_desc_recomendation)
    EditText edtDescRecomendation;
    @BindView(R.id.ln_wrapper_recomendation)
    LinearLayout lnWrapperRecomendation;
    @BindView(R.id.main_layout)
    ScrollView mainLayout;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_save_draft)
    Button btnSaveDraft;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ln_button_wrapper)
    LinearLayout lnButtonWrapper;
    @BindView(R.id.frm_button_bottom)
    FrameLayout frmButtonBottom;
    @BindView(R.id.pb_main)
    ProgressBar pbMain;
    @BindView(R.id.tv_pbh)
    TextView tvPbh;
    @BindView(R.id.pb_horizontal)
    ProgressBar pbHorizontal;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    @BindView(R.id.header_master_header)
    CardView rlHeaderMasterHeader;
    @BindView(R.id.header_data_pribadi)
    CardView rlHeaderDataPribadi;
    @BindView(R.id.header_data_pasangan)
    CardView rlHeaderDataPasangan;
    @BindView(R.id.header_alamat_pemohon)
    CardView rlHeaderAlamatPemohon;
    @BindView(R.id.header_informasi_kerabat)
    CardView rlHeaderInformasiKerabat;
    @BindView(R.id.header_data_pekerjaan)
    CardView rlHeaderDataPekerjaan;
    @BindView(R.id.header_detail_product)
    CardView rlHeaderDetailProduct;
    @BindView(R.id.header_agunan)
    CardView rlHeaderAgunan;
    @BindView(R.id.header_data_asuransi)
    CardView rlHeaderDataAsuransi;
    @BindView(R.id.header_data_perhitungan)
    CardView rlHeaderDataPerhitungan;
    @BindView(R.id.header_data_keterangan)
    CardView rlHeaderKeterangan;
    @BindView(R.id.header_image_pengajuan)
    CardView rlHeaderImagePengajuan;
    @BindView(R.id.header_persetujuan)
    CardView rlHeaderPersetujuan;
    @BindView(R.id.header_data_npwp)
    CardView rlHeaderDataNpwp;
    @BindView(R.id.header_recomendation)
    CardView rlHeaderRecomendation;
    @BindView(R.id.header_informasi_penawaran)
    CardView rlHeaderInformasiPenawaran;
    @BindView(R.id.header_persetujuan_tambahan)
    CardView rlHeaderPersetujuanTambahan;

    // Content
    @BindView(R.id.content_master_header)
    RelativeLayout rlMasterHeader;
    @BindView(R.id.content_data_nama_pemohon)
    RelativeLayout rlDataNamaPemohon;
    @BindView(R.id.content_data_validasi_awal)
    RelativeLayout rlDataValidasiAwal;
    @BindView(R.id.content_data_pribadi)
    RelativeLayout rlDataPribadi;
    @BindView(R.id.content_data_pasangan)
    RelativeLayout rlDataPasangan;
    @BindView(R.id.content_alamat_pemohon)
    RelativeLayout rlAlamatPemohon;
    @BindView(R.id.content_informasi_kerabat)
    RelativeLayout rlInformasiKerabat;
    @BindView(R.id.content_data_pekerjaan)
    RelativeLayout rlDataPekerjaan;
    @BindView(R.id.content_detail_product)
    RelativeLayout rlDetailProduct;
    @BindView(R.id.content_data_asuransi)
    RelativeLayout rlDataAsuransi;
    @BindView(R.id.content_data_keterangan)
    RelativeLayout rlDataKeterangan;
    @BindView(R.id.content_image_pengajuan)
    RelativeLayout rlImagePengajuan;
    @BindView(R.id.content_persetujuan)
    RelativeLayout rlPersetujuan;
    @BindView(R.id.content_recomendation)
    RelativeLayout rlRecomendation;
    @BindView(R.id.content_informasi_penawaran)
    RelativeLayout rlInformasiPenawaran;
    @BindView(R.id.content_persetujuan_tambahan)
    RelativeLayout rlPersetujuanTambahan;
    @BindView(R.id.content_data_perhitungan_kendaraan)
    RelativeLayout rlDataPerhitunganKendaraan;
    @BindView(R.id.content_agunan)
    RelativeLayout rlAgunan;

    @BindView(R.id.spnKeleluasaan)
    Spinner spnKeleluasaan;

    //    tv Spinner
    @BindView(R.id.tvKopJenisPembiayaan)
    TextView tvKopJenisPembiayaan;
    @BindView(R.id.tvKopTujuanPenggunaanDana)
    TextView tvKopTujuanPenggunaanDana;
    @BindView(R.id.tvMetodePenjualan)
    TextView tvMetodePenjualan;
    @BindView(R.id.tvPribadiStatusPernikahan)
    TextView tvPribadiStatusPernikahan;
    @BindView(R.id.tvStatusRumahPribadi)
    TextView tvStatusRumahPribadi;
    @BindView(R.id.tvPendidikanPribadi)
    TextView tvPendidikanPribadi;
    @BindView(R.id.tvAgamaPribadi)
    TextView tvAgamaPribadi;
    @BindView(R.id.tvHubunganKerabatSpn)
    TextView tvHubunganKerabatSpn;
    @BindView(R.id.tvTenorPerhitunganKendaraan)
    TextView tvTenorPerhitunganKendaraan;
    @BindView(R.id.tvDataAsuransi)
    TextView tvDataAsuransi;
    @BindView(R.id.tvRekomendasi)
    TextView tvRekomendasi;
    @BindView(R.id.tvKeleluasaan)
    TextView tvKeleluasaan;
    @BindView(R.id.tv_kode_khusus)
    TextView tvKodeKhusus;

    //    tv radio group
    @BindView(R.id.tvPribadiJenisKelamin)
    TextView tvPribadiJenisKelamin;
    @BindView(R.id.tvPerjanjianPisahHarta)
    TextView tvPerjanjianPisahHarta;
    @BindView(R.id.tvPasanganStatus)
    TextView tvPasanganStatus;
    @BindView(R.id.tvStatusKendaraan)
    TextView tvStatusKendaraan;
    @BindView(R.id.tvBpkbAtasNama)
    TextView tvBpkbAtasNama;
    @BindView(R.id.tvPemakaianKendaraan)
    TextView tvPemakaianKendaraan;
    @BindView(R.id.tvGroupRekomendasi)
    TextView tvGroupRekomendasi;

    @BindView(R.id.rb_persetujuanPenawaranTrue)
    RadioButton rbPersetujuanPenawaranTrue;
    @BindView(R.id.rb_persetujuanPenawaranFalse)
    RadioButton rbPersetujuanPenawaranFalse;
    @BindView(R.id.rg_persetujuan_penawaran)
    RadioGroup rgPersetujuanPenawaran;
    @BindView(R.id.rgPersetujuanTambahan)
    RadioGroup rgPersetujuanTambahan;
    @BindView(R.id.ic_arrow_data_penawaran)
    ImageView imgDropDownDataPenawaran;
    @BindView(R.id.ic_arrow_data_persetujuan_tambahan)
    ImageView imgDropDownDataPersetujuanTambahan;

    @BindView(R.id.rb_persetujuanTambahanTrue)
    RadioButton rbPersetujuanTambahanTrue;
    @BindView(R.id.rb_persetujuanTambahanFalse)
    RadioButton rbPErsetujuanTambahanFalse;

    @BindViews({R.id.content_master_header,
            R.id.content_data_pribadi,
            R.id.content_alamat_pemohon,
            R.id.content_informasi_kerabat,
            R.id.content_data_pekerjaan,
            R.id.content_detail_product,
            R.id.content_data_asuransi,
            R.id.content_data_keterangan,
            R.id.content_image_pengajuan,
            R.id.content_persetujuan,
            R.id.content_data_pasangan,
            R.id.content_recomendation,
            R.id.content_agunan,
            R.id.content_data_perhitungan_kendaraan,
            R.id.content_data_keleluasaan,
            R.id.content_informasi_penawaran,
            R.id.content_persetujuan_tambahan})
    List<RelativeLayout> viewContents;

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
            R.id.img_take_picture_25,
            R.id.img_take_picture_26,
            R.id.img_take_picture_27,
            R.id.img_take_picture_28,
            R.id.img_take_picture_29})
    List<View> viewTakeImages;

    @BindViews({
            R.id.img_take_picture_1,
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
            R.id.img_take_picture_25,
            R.id.img_take_picture_26,
            R.id.img_take_picture_27,
            R.id.img_take_picture_28,
            R.id.img_take_picture_29})
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
//            R.id.pb_image_15,
//            R.id.pb_image_16,
//            R.id.pb_image_17,
            R.id.pb_image_18,
            R.id.pb_image_19,
            R.id.pb_image_20,
            R.id.pb_image_21,
            R.id.pb_image_22,
            R.id.pb_image_23,
            R.id.pb_image_24,
            R.id.pb_image_25,
            R.id.pb_image_26,
            R.id.pb_image_27,
            R.id.pb_image_28,
            R.id.pb_image_29})
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
            R.id.img_camera_25,
            R.id.img_camera_26,
            R.id.img_camera_27,
            R.id.img_camera_28,
            R.id.img_camera_29})
    List<View> viewCameras;

    @BindViews({
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
    List<View> viewRefreshImages;

    @BindViews({R.id.edtKopStatusPemohon})
    List<View> viewDataKop;

    @BindViews({R.id.img_ttd_pemohon_persetujuan})
    List<View> viewTtdPemohon;
    @BindViews({
            R.id.img_ttd_pemohon_persetujuan,
            R.id.img_ttd_pasangan_persetujuan})
    List<View> viewTtdPemohonDanPasangan;

    @BindViews({R.id.edtPribadiNamaPemohon,
            R.id.edtPribadiNamaLengkapPemohon,
            R.id.edtPribadiNoKtp,
            R.id.edt_terbit_ktp_pribadi,
            R.id.edtPribadiTempatLahir,
            R.id.edtPribadiTanggalLahirPribadi,
            R.id.edt_jml_tanggungan_pribadi,
            R.id.edt_tinggal_sejak_tahun_pribadi,
            R.id.edt_tinggal_sejak_bulan_pribadi,
            R.id.edt_no_npwp_pribadi,
            R.id.edtPribadiNoKK,
            R.id.edt_email_pribadi,
            R.id.edt_handphone_pribadi,
            R.id.edt_nama_ibu_pribadi})
    List<View> viewDataPribadiNpwp;

    @BindViews({
            R.id.img_take_picture_1,
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
            R.id.img_take_picture_18})
    List<View> viewPhotoMenikah;

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
            R.id.img_take_picture_18})
    List<View> viewPhotoTidakMenikah;

    @BindViews({
            R.id.edtPribadiNamaPemohon,
            R.id.edtPribadiNamaLengkapPemohon,
            R.id.edtPribadiNoKtp,
            R.id.edtPribadiTempatLahir,
            R.id.edtPribadiTanggalLahirPribadi,
            R.id.edt_terbit_ktp_pribadi,
            R.id.edt_handphone_pribadi})
    List<View> viewDataPribadiSaveDraft;

    @BindViews({
            R.id.edtKopStatusKreditmu,
            R.id.edtKopStatusPemohon})
    List<View> viewDataKopEditText;

    @BindViews({R.id.edt_alamat_ktp,
            R.id.edt_rt_ktp,
            R.id.edt_rw_ktp,
            R.id.act_auto_ktp_alamat_pemohon,
            R.id.edt_area_phone_ktp,
            R.id.edt_phone_ktp})
    List<View> viewDataAlamatTinggalSaveDraft;

    @BindViews({R.id.edt_alamat_tinggal,
            R.id.edt_rt_tinggal,
            R.id.edt_rw_tinggal,
            R.id.act_auto_alamat_pemohon,
            R.id.edt_area_phone_tinggal,
            R.id.edt_phone_tinggal,
            R.id.edt_alamat_ktp,
            R.id.edt_rt_ktp,
            R.id.edt_rw_ktp,
            R.id.act_auto_ktp_alamat_pemohon,
            R.id.edt_area_phone_ktp,
            R.id.edt_phone_ktp})
    List<View> viewAlamat;

    @BindViews({R.id.edtPasanganNama, R.id.edtPasanganNoKtp, R.id.edtPasanganTempatLahir, R.id.edtPasanganTanggalLahir, R.id.edtPasanganKodeAreaTeleponRumah,
            R.id.edtPasanganNoTeleponRumah, R.id.edtPasanganKodeAreaTeleponPerusahaan, R.id.edtPasanganNoTeleponPerusahaan, R.id.edtPasanganNoHp, R.id.edtPasanganAlamat,
            R.id.actPasanganKota, R.id.actPasanganProfesi, R.id.actPasanganJobType, R.id.actPasanganJobPosition, R.id.actPasanganIndustri,
            R.id.edtPasanganNamaPerusahaan, R.id.edtPasanganNamaIbu})
    List<View> viewDataPasangan;
    @BindViews({R.id.edt_nama_kerabat, R.id.edt_alamat_kerabat, R.id.edt_rt_kerabat, R.id.edt_rw_kerabat, R.id.act_auto_alamat_kerabat,
            R.id.edt_area_phone_rumah_kerabat, R.id.edt_phone_rumah_kerabat, R.id.edt_area_phone_kantor_kerabat, R.id.edt_phone_kantor_kerabat, R.id.edt_hp_kerabat})
    List<View> viewKerabat;
    @BindViews({R.id.edt_nama_perusahaan, R.id.edt_alamat_perusahaan, R.id.act_auto_alamat_pekerjaan, R.id.edt_rt_perusahaan, R.id.edt_rw_perusahaan,
            R.id.edt_area_phone_perusahaan, R.id.edt_phone_perusahaan, R.id.edt_bekerja_sejak_perusahaan, R.id.act_profesi_perusahaan, R.id.act_job_type_perusahaan,
            R.id.act_job_position_perusahaan, R.id.act_industri_perusahaan, R.id.edt_penghasilan_tetap_perusahaan, R.id.edt_penghasilan_lain_perusahaan,
            R.id.edt_penghasilan_pasangan_perusahaan, R.id.edt_biaya_hidup_perusahaan, R.id.edtPekerjaanNoHp})
    List<View> viewMenikahPekerjaan;
    @BindViews({R.id.edt_nama_perusahaan, R.id.edt_alamat_perusahaan, R.id.act_auto_alamat_pekerjaan, R.id.edt_rt_perusahaan, R.id.edt_rw_perusahaan,
            R.id.edt_area_phone_perusahaan, R.id.edt_phone_perusahaan, R.id.edt_bekerja_sejak_perusahaan, R.id.act_profesi_perusahaan, R.id.act_job_type_perusahaan,
            R.id.act_job_position_perusahaan, R.id.act_industri_perusahaan, R.id.edt_penghasilan_tetap_perusahaan, R.id.edt_penghasilan_lain_perusahaan,
            R.id.edt_biaya_hidup_perusahaan, R.id.edtPekerjaanNoHp})
    List<View> viewTidakMenikahPekerjaan;
    @BindViews({R.id.actDtProductSupplier, R.id.actDtProductMarketingSupplier, R.id.actDtProductOffering, R.id.actDtProductPos})
    List<View> viewDetailProduct;
    @BindViews({R.id.act_wilayah_kendaraan, R.id.act_cabang_kendaraan, R.id.act_type_kendaraan,
            R.id.edt_warna_kendaraan, R.id.edt_isi_silinder, R.id.edt_no_polisi, R.id.edt_no_rangka,
            R.id.edt_no_mesin, R.id.edt_masa_berlaku_stnk, R.id.edt_masa_berlaku_pajak_stnk,
            R.id.edtNamaBpkbSendiri, R.id.edtNamaBpkbPasangan, R.id.edtNamaBpkbOrangLain})
    List<View> viewAgunan;
    @BindViews({R.id.edt_angsuran_perbulan_perhitungan_kendaraan})
    List<View> viewDataSignaturePerhitungan;
    @BindViews({R.id.edt_harga_agunan,
            R.id.edt_biaya_admin_perhitungan_kendaraan,
            R.id.edt_premi_asuransi_perhitungan_agunan_kendaraan,
            R.id.edt_premi_asuransi_perhitungan_jiwa_kendaraan,
            R.id.edt_jumlah_pembiayaan_perhitungan_kendaraan,
            R.id.edt_bunga_pembiayaan_kendaraan,
            R.id.edt_total_pinjaman_perhitungan_kendaraan,
            R.id.edtPokokPembiayaan,
            R.id.edt_angsuran_perbulan_perhitungan_kendaraan,
            R.id.edt_flat_pertahun_perhitungan_kendaraan,
            R.id.edtBiayaPnbpFidusiaPerhitunganKendaraan,
            R.id.edt_stnk_perhitungan_kendaraan,
            R.id.edtBiayaNotarisPerhitunganKendaraan,
            R.id.edt_angsuran_pertama_perhitungan_kendaraan,
            R.id.edtBiayaSurveyPerhitunganKendaraan,})
    List<View> viewPerhitungan;

    //    radio group
    @BindViews({R.id.rgPribadiJenisKelamin})
    List<View> viewDataPribadiRG;
    @BindViews({R.id.rgPerjanjianPisahHarta})
    List<View> viewDataPribadiRGPisahHarta;
    @BindViews({R.id.rgPasanganStatus})
    List<View> viewDataPasanganRG;
    @BindViews({R.id.rg_bpkb_atas_nama})
    List<View> viewAgunanRG;
    @BindViews({R.id.rb_group_recomendation})
    List<View> viewRekomendasiRG;
    @BindViews({R.id.rg_persetujuan_penawaran})
    List<View> viewDataPenawaranRG;
    @BindViews({R.id.rgPersetujuanTambahan})
    List<View> viewDataPersetujuanTambahanRG;

    //    spinner
    @BindViews({R.id.spnValidasiCabang})
    List<View> viewValidasiAwalSpinner;
    @BindViews({R.id.spnValidasiTipePengajuan, R.id.spnKopJenisPembiayaan, R.id.spnMetodePenjualan})
    List<View> viewDataKopSpinnerPsa;
    @BindViews({R.id.spnValidasiTipePengajuan, R.id.spnKopJenisPembiayaan, R.id.spnKopTujuanPenggunaanDana, R.id.spnMetodePenjualan})
    List<View> viewDataKopSpinnerNonPsa;
    @BindViews({R.id.spnPribadiStatusPernikahan, R.id.spn_status_rumah_pribadi, R.id.spn_pendidikan_pribadi, R.id.spn_agama_pribadi})
    List<View> viewDataPribadiSpinner;
    @BindViews({R.id.spnJenisKendaraan, R.id.spnMerkKendaraan, R.id.spnTahunKendaraan})
    List<View> viewDataAgunanSpinner;
    @BindViews({R.id.spn_hubungan_kerabat})
    List<View> viewDataKerabatSpinner;
    @BindViews({R.id.spn_recomendation})
    List<View> viewDataRekomendasiSpinner;
    @BindViews({R.id.spnKeleluasaan})
    List<View> viewCaraPembayaranSpinner;
    @BindViews({R.id.spnPribadiStatusPernikahan})
    List<View> viewDataPernikahanSpinner;
    @BindViews({R.id.spnDataAsuransi})
    List<View> viewDataAsuransiSpinner;

    @BindArray(R.array.tipe_customer)
    String[] arrayTipeCustomer;

    //    kop
    @BindArray(R.array.jenis_pembiayaan)
    String[] jenisPembiayaanOri;
    @BindArray(R.array.tujuan_penggunaan_dana)
    String[] tujuanPenggunaanDanaOri;
    @BindArray(R.array.metode_penjualan_non_elc)
    String[] metodePenjualanOri;

    @BindArray(R.array.jenis_pembiayaan_value)
    String[] jenisPembiayaanValue;
    @BindArray(R.array.tujuan_penggunaan_dana_value)
    String[] tujuanPenggunaanDanaValue;
    @BindArray(R.array.metode_penjualan_non_elc_value)
    String[] metodePenjualanValue;
    //===============================================================
    @BindArray(R.array.gender)
    String[] genderOri;
    @BindArray(R.array.pendidikan)
    String[] pendidikanOri;
    @BindArray(R.array.status_rumah)
    String[] statusRumahOri;
    @BindArray(R.array.agama)
    String[] agamaOri;
    @BindArray(R.array.status_pernikahan)
    String[] statusPernikahanOri;
    @BindArray(R.array.hubungan_kerabat)
    String[] hubunganKerabatOri;
    @BindArray(R.array.cara_pembayaran)
    String[] caraPembayaranOri;
    @BindArray(R.array.asuransi_agunan)
    String[] asuransiAgunanOri;

    @BindArray(R.array.gender_value)
    String[] genderValue;
    @BindArray(R.array.pendidikan_value)
    String[] pendidikanValue;
    @BindArray(R.array.status_rumah_value)
    String[] statusRumahValue;
    @BindArray(R.array.agama_value)
    String[] agamaValue;
    @BindArray(R.array.status_pernikahan_value)
    String[] statusPernikahanValue;
    @BindArray(R.array.agama)
    String[] agamaName;
    @BindArray(R.array.hubungan_kerabat)
    String[] hubunganKerabatValue;
    @BindArray(R.array.cara_pembayaran_value)
    String[] caraPembayaranValue;
    @BindArray(R.array.asuransi_agunan_value)
    String[] asuransiAgunanValue;

    //    ArrayAdapter
    private ArrayAdapter<AoBranchObjt> cabangAdapter;
    private ArrayAdapter<JobType> jobTypeArrayAdapter;
    private ArrayAdapter<JobType> pasanganJobTypeAdapter;
    private ArrayAdapter<JobPosition> jobPositionArrayAdapter;
    private ArrayAdapter<JobPosition> pasanganJobPositionAdapter;

    //    Bitmap
    private Bitmap bitmapTtdPasangan;
    private Bitmap bitmapTtdPemohon;

    //    Presenter
    private SearchSupplierMasterPresenter mSearchSupplierMasterPresenter;
    private SearchMarketingSupplierPresenter mSearchMarketingSupplierPresenter;
    private SearchProductOfferingPresenter mSearchProductOfferingPresenter;
    private PosKreditPresenter mPosPresenter;
    private ProductOffTenorPresenter mProductOffTenorPresenter;
    private JenisKendaraanPresenter mJenisKendaraanPresenter;
    private PilihKendaraanPresenter mPilihKendaraanPresenter;
    private SyaratDanKetentuanPresenter mSyaratDanKetentuanPresenter;
    private TahunProduksiKendaraanPresenter mTahunProduksiKendaraanPresenter;
    private HargaAgunanKendaraanPresenter mHargaAgunanKendaraanPresenter;
    private DetailPerhitunganKendaraanPresenter mDetailPerhitunganKendaraanPresenter;
    private GetReferalCodePresenter getReferalCodePresenter;

    private CodeSignaturePresenter mCodeSignaturePresenter;
    private BlackListPresenter mBlackListPresenter;
    private WilayahCabangPresenter mWilayahCabangPresenter;
    private CoordinatePresenter mCoordinatePresenter;
    private RecomendationPresenter mRecomendationPresenter;
    private CekKodeProgramPresenter mCekKodeProgramPresenter;
    private SinkronisasiKendaraanPresenter mSinkronisasiKendaraanPresenter;
    private AttachmentKendaraanPresenter mAttachmentKendaraanPresenter;
    private CheckEfNumberPresenter mCheckEfNumberPresenter;
    private KelurahanPresenter mKelurahanPresenter;
    private PengajuanDetailPresenter mPengajuanDetailPresenter;

    private boolean isNowYearPribadi;
    private boolean isUserValid;
    private boolean isTakePict = false;
//    private boolean manualPremi;

    private double latitude;
    private double longitude;
    private int countSignature;
    private List<AoBranchObjt> aoBranchStrings;
    private List<BranchMaster> branchMasterList;
    private Map<Integer, File> mHashMapAttachmentFiles = new LinkedHashMap<>();

    private String action;
    private String aoBranch;
    private String assetTypeId;
    private String blackListDate;
    private String branchMaster;
    private String customerIdConfins;
    private String descriptionCancel;
    private String descriptionSyarat;
    private String form;
    private String statusKreditmu;
    private String industriKode;
    private String jobPositionKode;
    private String jobTypeKode;
    private String marketingKode;
    private String mobileSubmissionKey;
    private String posKode;
    private String productId;
    private String productOfferingId;
    private String professionKode;
    private String recomendationId;
    private String strStatusPengajuan;
    private String supplierKode;
    private String token;
    private String AOSalesStatus;
    private String uuid;
    private String typeFormCheckFpd;

    private boolean isNpwpShow = false;
    private boolean isBtnNext = false;
    private Validator validator;
    private ValidatorDataPribadi validatorDataPribadi;
    private ValidatorSaveDraft validatorSaveDraft;
    private ValidatorCodeSignature validatorCodeSignature;
    PermissionHelper mPermissionHelper;
    Handler handler;
    ExecutorService executorService;
    Future longRunningTaskFuture;
    private String branchId;
    private String typeDataOffering;
    private String pengajuanType;
    private String isPsa;
    private Calendar calendar;
    private Calendar calendarMasaBerlakuStnk;
    private Calendar calendarMasaBerlakuPajakStnk;
    private int nowYear;
    private int nowMonth;
    private int nowDayOfMonth;
    private AlertDialog.Builder builder;
    private String strEfNumber;
    private String strEfNumberResponse = "";
    private String formType;
    private String appId;
    private String isAssignEdit;
    private String applicationIdKpm;
    private int countImage;
    private int numberOfImages;
    private boolean isImageError;
    private boolean isHitPokokPembiayaan = false;
    private boolean isHitBiayaAdmin = false;
    private boolean isHit = false;
    private boolean isHitReferal = false;
    private boolean isEditedCalculate = false;
    private boolean isPokokPembiayaanChange = false;
    private boolean isConfirmSignature = false;
    private List<String> tmpAttachments;
    private BlackListResponse blackListResponse;
    private String bucketMessage;
    private String agreement;
    private String timeDelay;
    private String amountOfFines;
    private MasterFormPengajuan masterFormPengajuan, draftMasterFormPengajuan;
    private String draftEdit;
    private String msgNotifikasi;
    private int statusNpwp;
    private String branchNameId;
    private String idJenisKendaraan;
    private String namaJenisKendaraan;
    private String idMerkKendaraan;
    private String namaMerkKendaraan;
    private String idTipeKendaraan;
    private String namaTipeKendaraan;
    private String strTahunKendaraan;
    private SupplierResponse supplierResponse;
    private MarketingSupplierResponse marketingSupplierResponse;
    private ProductOfferingResponse productOfferingResponse;

    private JenisKendaraanResponse jenisKendaraanResponse;
    private MerkKendaraanResponse merkKendaraanResponse;
    private TahunProduksiResponse tahunProduksiResponse;
    private PosListDownResponse posListDownResponse;
    private ProductOffTenorResponse productOffTenorResponse;
    private String statusAutoComplete;
    private String kodePasanganProfesi;
    private String kodePasanganJobType;
    private String kodePasanganJobPosition;
    private String kodePasanganIndustri;
    private Map<String, RequestBody> map;
    public String tipeTtd;
    private String idSync;
    private String idFamily;
    private KelurahanResponse kelurahanResponse;
    private RecomendationResponse recomendationResponse;
    private String dateEfNumber;
    private int intMaxPokokPembiayaan;
    private int intMinDownPayment;
    private int intMinAngsuranPerBulan;
    private int intMinPokokPembiayaan;
    private Application application;
    private String strEffectiveRate;
    private String strDPPercentage = "0";
    private String statSinkron;
    private String statOpenForm = "0";
    private int edtValidasiTanggalLahirDay;
    private int edtValidasiTanggalLahirMonth;
    private int edtValidasiTanggalLahirYear;
    private int edtTerbitKtpPribadiDay;
    private int edtTerbitKtpPribadiMonth;
    private int edtTerbitKtpPribadiYear;
    private int edtPasanganTanggalLahirDay;
    private int edtPasanganTanggalLahirMonth;
    private int edtPasanganTanggalLahirYear;
    private int edtTinggalSejakTahunPribadiYear;
    private int edtTinggalSejakBulanPribadiMonth;
    private int edtBekerjaSejakPerusahaanYear;
    private int edtMasaBerlakuStnkYear;
    private int edtMasaBerlakuStnkMonth;
    private int edtMasaBerlakuStnkDay;
    private int edtMasaBerlakuPajakStnkYear;
    private int edtMasaBerlakuPajakStnkMonth;
    private int edtMasaBerlakuPajakStnkDay;
    private String typeDataOfferingBlackList;
    private String strCheckSupplierMaster = "";
    private String strCheckProductOffering = "";
    private String strCheckMarketingSupplier = "";
    private String strCheckPos = "";
    private String strCheckProductOffTenor = "";
    private String strCheckMerkKendaraan = "";
    private String strCheckTahunProduksiKendaraan = "";
    private String strCheckTipeKendaraan = "";
    private DatePickerDialog cdpTahunStnk, cdpTahunPajakStnk;
    private Date maxMasaBerlakuStnk, maxMasaBerlakuPajakStnk;

    private ArrayList<String> kelurahanArrayList = new ArrayList<>();
    private ArrayList<SupplierMasterArrayList> supplierNameArrayList = new ArrayList<>();
    private ArrayList<MarketingSupplierArrayList> marketingSupplierNameArrayList = new ArrayList<>();
    private ArrayList<ProductOfferingArrayList> productOfferingNameArrayList = new ArrayList<>();
    private ArrayList<PosArrayList> posNameArrayList = new ArrayList<>();
    private ArrayList<TenorArrayList> tenorArrayList = new ArrayList<>();
    private ArrayList<JenisKendaraanArrayList> jenisKendaraan = new ArrayList<>();
    private ArrayList<MerkKendaraanArrayList> merkKendaraanNameArrayList = new ArrayList<>();
    private ArrayList<TipeKendaraanArrayList> tipeKendaraanNameArrayList = new ArrayList<>();
    private ArrayList<TahunKendaraanArrayList> manufacturingYearArrayList = new ArrayList<>();
    private ArrayList<RekomendasiArrayList> rekomendasiArrayList = new ArrayList<>();
    private ArrayList<TipePengajuanArrayList> tipePengajuanArrayLists = new ArrayList<>();

    private ArrayAdapter<TipePengajuanArrayList> tipePengajuanAdapter;
    private ArrayAdapter<SupplierMasterArrayList> actSupplierAdapter;
    private ArrayAdapter<MarketingSupplierArrayList> actMarketingSupplierAdapter;
    private ArrayAdapter<ProductOfferingArrayList> actProductOfferingAdapter;
    private ArrayAdapter<PosArrayList> posAdapter;
    private ArrayAdapter<TenorArrayList> productOffTenorAdapter;
    private ArrayAdapter<JenisKendaraanArrayList> actJenisKendaraanAdapter;
    private ArrayAdapter<MerkKendaraanArrayList> actMmerkKendaraanAdapter;
    private ArrayAdapter<TipeKendaraanArrayList> actTipeKendaraanAdapter;
    private ArrayAdapter<TahunKendaraanArrayList> actMmanufacturingYearAdapter;
    private ArrayAdapter<String> kelurahanAdapter;
    private NotEmptyRule notEmptyRule;
    private NoHpRule noHpRule;
    private RTRWRule rtrwRule;
    private ImageViewRule imageViewRule;
    private RadioGroupRule radioGroupRule;
    private SpinnerRule spinnerRule;
    private NotZeroRule notZeroRule;
    private NoMesinRule noMesinRule;
    private NotZeroRuleBiayaHidup notZeroRuleBiayaHidup;
    private NotZeroRulePengahsilanTetap notZeroRulePengahsilanTetap;
    private KtpRule ktpRule;
    private NpwpRule npwpRule;
    private KodeAreaRule kodeAreaRule;
    private String statusButton = "0";
    private int intMinBiayaAdminPerhitunganKendaraan;
    private String statTipePengajuan = "";
    private String statusViewHitPerhitungan = "";
    private String statFocusFieldBiayaAdminPerhitunganKendaraan = "1";
    private String statFocusFieldPokokPembiayaan = "1";
    private String statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "1";
    private String statFocusFieldStnkPerhitunganKendaraan = "1";
    private String statFocusFieldBiayaNotarisPerhitunganKendaraan = "1";
    private String statFocusFieldBiayaPnbpFidusiaPerhitunganKendaraan = "1";
    private String statFocusFieldBiayaSurveyPerhitunganKendaraan = "1";
    private String perhitunganPokokPembiayaan = "0";
    private String consumerloanDefault = "0";
    private String monthlypaymentDefault = "0";
    private String usedOTP;
    private boolean isSelectedTenor = false;
    private boolean firstInitFlag = true;
    private boolean firstInitAssignEdit = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        App.appComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "form_pengajuan_motor_open");
        mFirebaseAnalytics.logEvent("form_pengajuan_motor_open", bundle);

        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        branchId = sharedPreferences.getString(getResources().getString(R.string.sharedpref_brachCode), "");
        AOSalesStatus = sharedPreferences.getString(getResources().getString(R.string.sharedpref_aosales_status), "");
        notEmptyRule = new NotEmptyRule();
        imageViewRule = new ImageViewRule();
        radioGroupRule = new RadioGroupRule();
        spinnerRule = new SpinnerRule();
        notZeroRule = new NotZeroRule();
        noMesinRule = new NoMesinRule();
        notZeroRuleBiayaHidup = new NotZeroRuleBiayaHidup();
        notZeroRulePengahsilanTetap = new NotZeroRulePengahsilanTetap();
        npwpRule = new NpwpRule();
        noHpRule = new NoHpRule();
        rtrwRule = new RTRWRule();
        ktpRule = new KtpRule();
        kodeAreaRule = new KodeAreaRule();

        pengajuanType = getIntent().getStringExtra("pengajuan_type");
        typeDataOffering = getIntent().getStringExtra("type_data_offering");
        form = getIntent().getStringExtra("form");
        appId = getIntent().getStringExtra("app_id"); /*Edit*/
        isAssignEdit = getIntent().getStringExtra("isAssignEdit");
        if (form.equalsIgnoreCase("Edit")) firstInitAssignEdit = true;

        longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
        latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));
        statSinkron = "0";
        statOpenForm = "0";

        setLayoutFirst();
        firstInit();

       /* if (!BuildConfig.FLAVOR.equals("staging") || BuildConfig.FLAVOR.equals("staging")) {
            edtValidasiNamaLegal.setVisibility(View.GONE);
//            edtValidasiNamaIbuKandung.setVisibility(View.GONE);
            tilNamaLegal.setVisibility(View.GONE);
//            tilNamaIbu.setVisibility(View.GONE);
            edtValidasiNamaLegal.setText("");
//            edtValidasiNamaIbuKandung.setText("");
        }*/
//        edtValidasiNamaIbuKandung.setVisibility(View.VISIBLE);


        if (form.equalsIgnoreCase("New")) {
            appId = "";
            pbMain.setVisibility(View.GONE);
            cekBranch();
            mainLayout.setVisibility(View.VISIBLE);
            rlHeaderValidasiPengajuan.setVisibility(View.VISIBLE);
            rlDataValidasiAwal.setVisibility(View.VISIBLE);
            lnWrapperDataValidasiAwal.setVisibility(View.VISIBLE);
        } else if (form.equalsIgnoreCase("Draft") || form.equalsIgnoreCase("Edit")) {
            if (form.equalsIgnoreCase("Edit")) {
                btnSaveDraft.setVisibility(View.GONE);
                lnWrapperImagePengajuan.setVisibility(View.GONE);
            }
            validator.removeRules(edtValidasiNoKtp);
            validator.removeRules(edtValidasiNamaIbuKandung);
            validator.removeRules(edtValidasiNamaLegal);
            validator.removeRules(edtValidasiNoHp);
            validator.removeRules(edtValidasiTanggalLahir);
            preLoading();
            hideValidasiAwal();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            pbMain.setVisibility(View.VISIBLE);
            mRecomendationPresenter.formRecomendation(token);
        }
    }

    private void setLayoutFirst() {
        tilNoNpwpPribadi.setVisibility(View.GONE);
        edtNoNpwpPribadi.setVisibility(View.GONE);
        rlDataNpwp.setVisibility(View.GONE);
        edtPekerjaanNoHp.setVisibility(View.GONE);
        lnTakeFoto5.setVisibility(View.GONE);
//        edtPhonePerusahaan.setVisibility(View.GONE);

    }

    private void hideValidasiAwal() {
        lnWrapperDataValidasiAwal.setVisibility(View.GONE);
        rlDataValidasiAwal.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }

    private void setDisableInputData() {
        spnMetodePenjualan.setEnabled(false);
//        spnValidasiTipePengajuan.setEnabled(false);
        edtStnkPerhitunganKendaraan.setEnabled(false);
        edtBiayaNotarisPerhitunganKendaraan.setEnabled(false);
        edtBiayaSurveyPerhitunganKendaraan.setEnabled(false);
        edtAngsuranPertamaPerhitunganKendaraan.setEnabled(false);
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setEnabled(false);
        edtKopStatusKreditmu.setEnabled(false);
        edtKopStatusPemohon.setEnabled(false);
        edtPribadiNoKtp.setEnabled(false);
        edtHandphonePribadi.setEnabled(false);
        edtPribadiTanggalLahirPribadi.setEnabled(false);
        actWilayahKendaraan.setEnabled(false);
        actCabangKendaraan.setEnabled(false);

        edtHargaAgunan.setEnabled(false);
        edtDownpayment.setEnabled(false);
        edtBiayaAdminPerhitunganKendaraan.setEnabled(false);
        edtPokokPembiayaan.setEnabled(false);
        edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(false);
        edtPremiAsuransiPerhitunganAgunanKendaraan.setEnabled(false);
        edtPremiAsuransiPerhitunganJiwaKendaraan.setEnabled(false);
        edtJumlahPembiayaanPerhitunganKendaraan.setEnabled(false);
        edtBungaPembiayaanKendaraan.setEnabled(false);
        edtTotalPinjamanPerhitunganKendaraan.setEnabled(false);
        edtFlatPertahunPerhitunganKendaraan.setEnabled(false);
        edtAngsuranPertamaPerhitunganKendaraan.setEnabled(false);

        edtStnkPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBiayaNotarisPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBiayaSurveyPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtAngsuranPertamaPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtPokokPembiayaan.setTextColor(Color.GRAY);
        edtKopStatusKreditmu.setTextColor(Color.GRAY);
        edtKopStatusPemohon.setTextColor(Color.GRAY);
        edtPribadiNoKtp.setTextColor(Color.GRAY);
        edtPribadiTanggalLahirPribadi.setTextColor(Color.GRAY);
        edtHandphonePribadi.setTextColor(Color.GRAY);
        actWilayahKendaraan.setTextColor(Color.GRAY);
        actCabangKendaraan.setTextColor(Color.GRAY);

        edtHargaAgunan.setTextColor(Color.GRAY);
        edtDownpayment.setTextColor(Color.GRAY);
        edtBiayaAdminPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtPremiAsuransiPerhitunganAgunanKendaraan.setTextColor(Color.GRAY);
        edtPremiAsuransiPerhitunganJiwaKendaraan.setTextColor(Color.GRAY);
        edtJumlahPembiayaanPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBungaPembiayaanKendaraan.setTextColor(Color.GRAY);
        edtTotalPinjamanPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtFlatPertahunPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtAngsuranPertamaPerhitunganKendaraan.setTextColor(Color.GRAY);
    }

    private void setDefaultCalculatingToZero() {
        if (edtHargaAgunan.getText().toString().trim().isEmpty())
            edtHargaAgunan.setText("0");
        if (edtPokokPembiayaan.getText().toString().trim().isEmpty())
            edtPokokPembiayaan.setText("0");
        if (edtDownpayment.getText().toString().trim().isEmpty())
            edtDownpayment.setText("0");
        if (edtBiayaAdminPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtBiayaAdminPerhitunganKendaraan.setText("0");
        if (edtPinjaman.getText().toString().trim().isEmpty())
            edtPinjaman.setText("0");
        if (edtPremiAsuransiPerhitunganAgunanKendaraan.getText().toString().trim().isEmpty())
            edtPremiAsuransiPerhitunganAgunanKendaraan.setText("0");
        if (edtPremiAsuransiPerhitunganJiwaKendaraan.getText().toString().trim().isEmpty())
            edtPremiAsuransiPerhitunganJiwaKendaraan.setText("0");
        if (edtJumlahPembiayaanPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtJumlahPembiayaanPerhitunganKendaraan.setText("0");
        if (edtBungaPembiayaanKendaraan.getText().toString().trim().isEmpty())
            edtBungaPembiayaanKendaraan.setText("0");
        if (edtTotalPinjamanPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtTotalPinjamanPerhitunganKendaraan.setText("0");
        if (edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtAngsuranPerbulanPerhitunganKendaraan.setText("0");
        if (edtFlatPertahunPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtFlatPertahunPerhitunganKendaraan.setText("0");
        if (edtStnkPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtStnkPerhitunganKendaraan.setText("0");
        if (edtBiayaNotarisPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtBiayaNotarisPerhitunganKendaraan.setText("0");
        if (edtBiayaPnbpFidusiaPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtBiayaPnbpFidusiaPerhitunganKendaraan.setText("0");
        if (edtBiayaSurveyPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtBiayaSurveyPerhitunganKendaraan.setText("0");
        if (edtAngsuranPertamaPerhitunganKendaraan.getText().toString().trim().isEmpty())
            edtAngsuranPertamaPerhitunganKendaraan.setText("0");
    }

    private void initAllConstructor() {
        validator = new Validator(this);
        validator.setValidationListener(this);
        validatorDataPribadi = new ValidatorDataPribadi();
        validatorSaveDraft = new ValidatorSaveDraft();
        validatorCodeSignature = new ValidatorCodeSignature();

        mBlackListPresenter = new BlackListPresenter();
        mCoordinatePresenter = new CoordinatePresenter();
        mSyaratDanKetentuanPresenter = new SyaratDanKetentuanPresenter();
        mRecomendationPresenter = new RecomendationPresenter();
        mCekKodeProgramPresenter = new CekKodeProgramPresenter();
        mPilihKendaraanPresenter = new PilihKendaraanPresenter();
        mTahunProduksiKendaraanPresenter = new TahunProduksiKendaraanPresenter();
        mHargaAgunanKendaraanPresenter = new HargaAgunanKendaraanPresenter();
        mDetailPerhitunganKendaraanPresenter = new DetailPerhitunganKendaraanPresenter();
        getReferalCodePresenter = new GetReferalCodePresenter();
        mSearchSupplierMasterPresenter = new SearchSupplierMasterPresenter();
        mSearchMarketingSupplierPresenter = new SearchMarketingSupplierPresenter();
        mSearchProductOfferingPresenter = new SearchProductOfferingPresenter();
        mProductOffTenorPresenter = new ProductOffTenorPresenter();
        mJenisKendaraanPresenter = new JenisKendaraanPresenter();
        mPosPresenter = new PosKreditPresenter();
        mWilayahCabangPresenter = new WilayahCabangPresenter();
        mSinkronisasiKendaraanPresenter = new SinkronisasiKendaraanPresenter();
        mAttachmentKendaraanPresenter = new AttachmentKendaraanPresenter();
        mCheckEfNumberPresenter = new CheckEfNumberPresenter();
        mKelurahanPresenter = new KelurahanPresenter();
        mPengajuanDetailPresenter = new PengajuanDetailPresenter();
        mCodeSignaturePresenter = new CodeSignaturePresenter();

        mBlackListPresenter.attachView(this);
        mSyaratDanKetentuanPresenter.attachView(this);
        mCoordinatePresenter.attachView(this);
        mRecomendationPresenter.attachView(this);
        mCekKodeProgramPresenter.attachView(this);
        mPilihKendaraanPresenter.attachView(this);
        mTahunProduksiKendaraanPresenter.attachView(this);
        mHargaAgunanKendaraanPresenter.attachView(this);
        mDetailPerhitunganKendaraanPresenter.attachView(this);
        getReferalCodePresenter.attachView(this);
        mSearchSupplierMasterPresenter.attachView(this);
        mSearchMarketingSupplierPresenter.attachView(this);
        mSearchProductOfferingPresenter.attachView(this);
        mProductOffTenorPresenter.attachView(this);
        mJenisKendaraanPresenter.attachView(this);
        mPosPresenter.attachView(this);
        mWilayahCabangPresenter.attachView(this);
        mSinkronisasiKendaraanPresenter.attachView(this);
        mAttachmentKendaraanPresenter.attachView(this);
        mCheckEfNumberPresenter.attachView(this);
        mKelurahanPresenter.attachView(this);
        mPengajuanDetailPresenter.attachView(this);
        mCodeSignaturePresenter.attachView(this);

        handler = new Handler();
        executorService = Executors.newSingleThreadExecutor();
//        longRunningTaskFuture = executorService.submit(runnable);
        builder = new AlertDialog.Builder(this);

        calendar = Calendar.getInstance();
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);
        nowDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        calendarMasaBerlakuStnk = Calendar.getInstance();
        calendarMasaBerlakuStnk.setTime(new Date());
        calendarMasaBerlakuStnk.add(Calendar.YEAR, +100);
        maxMasaBerlakuStnk = calendarMasaBerlakuStnk.getTime();

        calendarMasaBerlakuPajakStnk = Calendar.getInstance();
        calendarMasaBerlakuPajakStnk.setTime(new Date());
        calendarMasaBerlakuPajakStnk.add(Calendar.YEAR, +100);
        maxMasaBerlakuPajakStnk = calendarMasaBerlakuPajakStnk.getTime();

    }

    private void checkTipePengajuan() {
        if ("ELC".equalsIgnoreCase(typeDataOffering)) {
            assetTypeId = "04";
            typeDataOfferingBlackList = "ELC";
        } else if ("MTR".equalsIgnoreCase(typeDataOffering)) {
            assetTypeId = "11";
            typeDataOfferingBlackList = "MOT";
        } else if ("MBL".equalsIgnoreCase(typeDataOffering)) {
            assetTypeId = "1";
            typeDataOfferingBlackList = "CAR";
        } else if ("MBLBKS".equalsIgnoreCase(typeDataOffering)) assetTypeId = "2";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_form_pengajuan_non_elc;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBlackListPresenter.detachView();
        mCodeSignaturePresenter.detachView();
        mSyaratDanKetentuanPresenter.detachView();
        mCoordinatePresenter.detachView();
        mRecomendationPresenter.detachView();
        mCekKodeProgramPresenter.detachView();
        mPilihKendaraanPresenter.detachView();
        mTahunProduksiKendaraanPresenter.detachView();
        mHargaAgunanKendaraanPresenter.detachView();
        mDetailPerhitunganKendaraanPresenter.detachView();
        mSearchSupplierMasterPresenter.detachView();
        mSearchMarketingSupplierPresenter.detachView();
        mSearchProductOfferingPresenter.detachView();
        mProductOffTenorPresenter.detachView();
        mJenisKendaraanPresenter.detachView();
        mPosPresenter.detachView();
        mWilayahCabangPresenter.detachView();
        mSinkronisasiKendaraanPresenter.detachView();
        mAttachmentKendaraanPresenter.detachView();
        mCheckEfNumberPresenter.detachView();
        mKelurahanPresenter.detachView();
        mPengajuanDetailPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPreCoordinate() {

    }

    @Override
    public void onFailedCoordinate(String massage) {
        messageFailedApi(massage);
    }

    private void showButtonRetry() {
        llLoading.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTokenCoordinateExpired() {
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
    }

    @Override
    public void onPreRecomendation() {
        preLoading();
    }

    @Override
    public void onSuccessRecomendation(RecomendationResponse recomendationResponse) {
        this.recomendationResponse = recomendationResponse;
        statusAutoComplete = "rekomendasi";
        containerInitAutoComplete();
        if (form.equalsIgnoreCase("New")) {
            mBlackListPresenter.blackList(
                    token,
                    edtValidasiTanggalLahir.getText().toString(),
                    edtValidasiNoKtp.getText().toString(),
                    edtValidasiNoHp.getText().toString(),
                    typeDataOfferingBlackList,
                    aoBranch, edtValidasiNamaLegal.getText().toString(), edtValidasiNamaIbuKandung.getText().toString(), "");
        } else if (form.equalsIgnoreCase("Edit") || form.equalsIgnoreCase("Draft")) {
            mPengajuanDetailPresenter.loadPengajuanDetail(token, appId);
        }
    }

    @Override
    public void onFailedRecomendation(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecomendationPresenter.formRecomendation(token);
            }
        });
    }

    @Override
    public void onTokenExpiredRecomendation() {
        successAndFailedLoading();
    }

    @Override
    public void onCheckFpd() {
        successAndFailedLoading();
        if (typeFormCheckFpd.equals("next")) {
            mRecomendationPresenter.formRecomendation(token);
//            Toast.makeText(this, "berhasil check fpd", Toast.LENGTH_SHORT).show();
        } else if (typeFormCheckFpd.equals("draft") || typeFormCheckFpd.equals("submit")) {
            if (isEditedCalculate) ;
            else
                mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, statSinkron, appId); /*jika statSinkron = 3, maka save draft*///
        }
    }

    @Override
    public void onCheckFpdFailed(String message) {
        successAndFailedLoading();
        mSignoutPresenter.signout(token);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void cekBranch() {
        // ambil data asset master
        try {
            branchMasterList = databaseService.getBranchMasterDao().queryForAll();
            for (int i = 0; i < branchMasterList.size(); i++) {
                branchMaster = branchMasterList.get(i).getBranchPrimary();
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Chek branch", String.valueOf(e));
            Crashlytics.logException(e);
        }

        //cek apakah primary key di tabel ao branch false
        List<Aobranch> cekAobranch = new ArrayList<>();
        try {
            cekAobranch = databaseService.getAobranchDao().queryBuilder().where().eq("BranchIDAo", branchMaster).and().eq("IsActive", "False").query();
            if (!cekAobranch.isEmpty()) {
                UpdateBuilder<Aobranch, String> updateBuilder = databaseService.getAobranchDao().updateBuilder();
                updateBuilder.where().eq("BranchIDAo", branchMaster);
                updateBuilder.updateColumnValue("IsActive", "True");
                updateBuilder.update();
                // result branch
                UpdateBuilder<ResultAobranch, String> updateResult = databaseService.getResultAobranchDao().updateBuilder();
                updateResult.where().eq("Branchid", branchMaster);
                updateResult.updateColumnValue("isActive", "True");
                updateResult.update();
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Update active branch", String.valueOf(e));
            Crashlytics.logException(e);
        }

        // list ao branch
        aoBranchStrings = databaseService.getAllBranch();
        // swap branch master ke index 0
        for (int i = 0; i < aoBranchStrings.size(); i++) {
            if (branchMaster.equalsIgnoreCase(aoBranchStrings.get(i).getBranchId())) {
                Collections.swap(aoBranchStrings, i, 0);
            }
        }
        cabangAdapter = new ArrayAdapter<AoBranchObjt>(FormPengajuanNonElcActivity.this, R.layout.item_dropdown, R.id.id_item, aoBranchStrings);
        spnValidasiCabang.setAdapter(cabangAdapter);
        spnValidasiCabang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aoBranch = ((AoBranchObjt) spnValidasiCabang.getSelectedItem()).getBranchId();
                branchNameId = ((AoBranchObjt) spnValidasiCabang.getSelectedItem()).getBranchName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void firstInit() {
        initTitleAttachment();
        initFormatEfNumber();
        initAllConstructor();
        initAllCaps();
        initRadioGroup();
        initNotFocus();
        initTextWatcher();
        initSpinner();
        initValidator();
        initShowDropDown();
        initShowDate();
        setDefaultCalculatingToZero();
        setDisableInputData();
        checkTipePengajuan();

        txtDatePemohonPersetujuan.setText(Util.persetujuanTimeFormat(new DateTime()));
        txtDatePasanganPersetujuan.setText(Util.persetujuanTimeFormat(new DateTime()));
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
        tvCamera26.setText(getString(R.string.txt_title_attachment_22));
        tvCamera27.setText(getString(R.string.txt_title_attachment_23));
        tvCamera28.setText(getString(R.string.txt_title_attachment_24));
        tvCamera29.setText(getString(R.string.txt_title_attachment_25));
    }

    private void initFormatEfNumber() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHss");
        dateEfNumber = df.format(Calendar.getInstance().getTime());
    }

    private void initShowDate() {
        DatePickerListener datePickerListener = new DatePickerListener();
        edtValidasiTanggalLahir.setOnClickListener(datePickerListener);
        edtTerbitKtpPribadi.setOnClickListener(datePickerListener);
        edtTinggalSejakTahunPribadi.setOnClickListener(datePickerListener);
        edtTinggalSejakBulanPribadi.setOnClickListener(datePickerListener);
        edtPasanganTanggalLahir.setOnClickListener(datePickerListener);
        edtBekerjaSejakPerusahaan.setOnClickListener(datePickerListener);
        edtMasaBerlakuStnk.setOnClickListener(datePickerListener);
        edtMasaBerlakuPajakStnk.setOnClickListener(datePickerListener);
    }

    private void initShowDropDown() {
        actAutoAlamatPemohon.setOnFocusChangeListener(validateOnFocusChange);
        actAutoKtpAlamatPemohon.setOnFocusChangeListener(validateOnFocusChange);
        actAutoAlamatKerabat.setOnFocusChangeListener(validateOnFocusChange);
        actAutoAlamatPekerjaan.setOnFocusChangeListener(validateOnFocusChange);
        actProfesiPerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        actJobTypePerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        actJobPositionPerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        actIndustriPerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        actDtProductSupplier.setOnFocusChangeListener(validateOnFocusChange);
        actDtProductMarketingSupplier.setOnFocusChangeListener(validateOnFocusChange);
        actDtProductOffering.setOnFocusChangeListener(validateOnFocusChange);
        actDtProductPos.setOnFocusChangeListener(validateOnFocusChange);
        actTypeKendaraan.setOnFocusChangeListener(validateOnFocusChange);
        actPasanganKota.setOnFocusChangeListener(validateOnFocusChange);
        actPasanganProfesi.setOnFocusChangeListener(validateOnFocusChange);
        actPasanganJobType.setOnFocusChangeListener(validateOnFocusChange);
        actPasanganJobPosition.setOnFocusChangeListener(validateOnFocusChange);
        actPasanganIndustri.setOnFocusChangeListener(validateOnFocusChange);
    }

    private void initValidator() {
        //        image view
        if (!form.equalsIgnoreCase("Edit")) {
            validator.put(imgTakePicture1, imageViewRule);
            validator.put(imgTakePicture2, imageViewRule);
            validator.put(imgTakePicture3, imageViewRule);
            validator.put(imgTakePicture4, imageViewRule);
            validator.put(imgTakePicture6, imageViewRule);
            validator.put(imgTakePicture7, imageViewRule);
            validator.put(imgTakePicture8, imageViewRule);
            validator.put(imgTakePicture9, imageViewRule);
            validator.put(imgTakePicture10, imageViewRule);
            validator.put(imgTakePicture11, imageViewRule);
            validator.put(imgTakePicture12, imageViewRule);
            validator.put(imgTakePicture13, imageViewRule);
            validator.put(imgTakePicture14, imageViewRule);
//            validator.put(imgTakePicture15, imageViewRule);
//            validator.put(imgTakePicture16, imageViewRule);
//            validator.put(imgTakePicture17, imageViewRule);
//            validator.put(imgTakePicture18, imageViewRule);
        }

        validator.put(imgTtdPemohonPersetujuan, imageViewRule);

        validator.put(edtHandphonePribadi, noHpRule);
        validator.put(edtHpKerabat, noHpRule);
        validator.put(edtRtKtp, rtrwRule);
        validator.put(edtRtKerabat, rtrwRule);
        validator.put(edtRtPerusahaan, rtrwRule);
        validator.put(edtRtTinggal, rtrwRule);
        validator.put(edtRwKerabat, rtrwRule);
        validator.put(edtRwKtp, rtrwRule);
        validator.put(edtRwPerusahaan, rtrwRule);
        validator.put(edtRwTinggal, rtrwRule);
        validator.put(edtPribadiNoKtp, ktpRule);
        validator.put(edtPribadiNoKK, notEmptyRule);
        validator.put(edtAreaPhoneTinggal, kodeAreaRule);
        validator.put(edtAreaPhoneKtp, kodeAreaRule);
        validator.put(edtAreaPhoneRumahKerabat, kodeAreaRule);
        validator.put(edtAreaPhoneKantorKerabat, kodeAreaRule);
        validator.put(edtBiayaHidupPerusahaan, notZeroRuleBiayaHidup);
        validator.put(edtPenghasilanTetapPerusahaan, notZeroRulePengahsilanTetap);

//        radio group
        validator.put(rgPribadiJenisKelamin, radioGroupRule);

        validator.put(rgBpkbAtasNama, radioGroupRule);
        validator.put(rbGroupRecomendation, radioGroupRule);
        validator.put(rgPersetujuanPenawaran, radioGroupRule);
        validator.put(rgPersetujuanTambahan, radioGroupRule);
//        spinner
        if (isAssignEdit == null || isAssignEdit.isEmpty()) {
            validator.put(spnValidasiTipePengajuan, spinnerRule);        /*kop*/
            validator.put(spnKopJenisPembiayaan, spinnerRule);        /*kop*/
            validator.put(spnMetodePenjualan, spinnerRule);           /*kop*/
        }
        validator.put(spnPribadiStatusPernikahan, spinnerRule);   /*data pribadi*/
        validator.put(spnStatusRumahPribadi, spinnerRule);        /*data pribadi*/
        validator.put(spnPendidikanPribadi, spinnerRule);         /*data pribadi*/
        validator.put(spnAgamaPribadi, spinnerRule);              /*data pribadi*/
        validator.put(spnHubunganKerabat, spinnerRule);           /*informasi kerabat*/
        validator.put(spnDataAsuransi, spinnerRule);              /*data asuransi*/
        validator.put(spnKeleluasaan, spinnerRule);               /*cara pembayaran*/
        validator.put(spnJenisKendaraan, spinnerRule);               /*cara pembayaran*/
        validator.put(spnMerkKendaraan, spinnerRule);               /*cara pembayaran*/
        validator.put(spnTahunKendaraan, spinnerRule);               /*cara pembayaran*/


        validator.put(edtNoMesin, noMesinRule);
        validator.put(edtHargaAgunan, notZeroRule);
        validator.put(edtPokokPembiayaan, notZeroRule);
        validator.put(edtBiayaAdminPerhitunganKendaraan, notZeroRule);
        validator.put(edtPremiAsuransiPerhitunganAgunanKendaraan, notZeroRule);
        validator.put(edtPremiAsuransiPerhitunganJiwaKendaraan, notZeroRule);
        validator.put(edtJumlahPembiayaanPerhitunganKendaraan, notZeroRule);
        validator.put(edtBungaPembiayaanKendaraan, notZeroRule);
        validator.put(edtTotalPinjamanPerhitunganKendaraan, notZeroRule);
        validator.put(edtAngsuranPerbulanPerhitunganKendaraan, notZeroRule);
        validator.put(edtFlatPertahunPerhitunganKendaraan, notZeroRule);
//        validator.put(edtBiayaPnbpFidusiaPerhitunganKendaraan, notZeroRule);
//        validator.put(edtStnkPerhitunganKendaraan, notZeroRule);
//        validator.put(edtBiayaNotarisPerhitunganKendaraan, notZeroRule);
        validator.put(edtAngsuranPertamaPerhitunganKendaraan, notZeroRule);
//        validator.put(edtBiayaProvisiPerhitunganKendaraan, notZeroRule);
//        validator.put(edtBiayaSurveyPerhitunganKendaraan, notZeroRule);
        validator.put(edtDownpayment, notZeroRule);

        validator.put(edtEmailPribadi, new EmailValidation());
    }

    private void initNotFocus() {
        btnRefreshCalculate.setClickable(false);
        btnRefreshCalculate.setTextColor(getResources().getColor(R.color.bg_gray));
        btnRefreshCalculate.setBackgroundColor(getResources().getColor(R.color.colorSecondaryText));

        edtPokokPembiayaan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtPokokPembiayaan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            isPokokPembiayaanChange = true;
                            if (isEditedCalculate) {
//                                Toast.makeText(this, "Refresh kalkulasi terlebih dahulu di kolom Data Perhitungan Angsuran", Toast.LENGTH_SHORT).show();
                            } else {
                                isEditedCalculate = true;
//                                isChangePokokPembiayaan = true;
                                edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(false);
//                                edtAngsuranPerbulanPerhitunganKendaraan.setFocusableInTouchMode(false);
                                edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.GRAY);
                                btnRefreshCalculate.setClickable(true);
                                btnRefreshCalculate.setTextColor(getResources().getColor(R.color.bg_gray));
                                btnRefreshCalculate.setBackgroundColor(getResources().getColor(R.color.green));
                               /* setRemoveCallbacksRunnable();
                                if(!isHitPokokPembiayaan){

                                    runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            setDefaultCalculatingToZero();
                                            if (checkMinimalPokokPembiayaan()) {
                                                statFocusFieldPokokPembiayaan = "0";
                                                isHitPokokPembiayaan = true;
                                                hitPerhitunganKendaraanPokokPembiayaan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                            }
                                        }
                                    };
                                    handler.postDelayed(runnable, 2000);
                                }*/
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldPokokPembiayaan = "1";
                }
            }
        });
        edtBiayaAdminPerhitunganKendaraan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    statFocusFieldBiayaAdminPerhitunganKendaraan = "1";
                    statusViewHitPerhitungan = "edtBiayaAdminPerhitunganKendaraan";
                    edtBiayaAdminPerhitunganKendaraan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            isPokokPembiayaanChange = true;
                            if (isEditedCalculate) {
//                                Toast.makeText(this, "Refresh kalkulasi terlebih dahulu di kolom Data Perhitungan Angsuran", Toast.LENGTH_SHORT).show();
                            } else {
                                isEditedCalculate = true;
                                edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(false);
//                                edtAngsuranPerbulanPerhitunganKendaraan.setFocusableInTouchMode(false);
                                edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.GRAY);
                                btnRefreshCalculate.setClickable(true);
                                btnRefreshCalculate.setTextColor(getResources().getColor(R.color.bg_gray));
                                btnRefreshCalculate.setBackgroundColor(getResources().getColor(R.color.green));
                            }

                           /* setRemoveCallbacksRunnable();
                            if (!isHitBiayaAdmin) {
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        setDefaultCalculatingToZero();
                                        if (checkMinimalBiayaAdmin()) {
                                            isHitBiayaAdmin = true;
                                            statFocusFieldBiayaAdminPerhitunganKendaraan = "0";
                                            hitPerhitunganKendaraanPokokPembiayaan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                        }
                                    }
                                };
                                handler.postDelayed(runnable, 2000);
                            }*/
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldBiayaAdminPerhitunganKendaraan = "1";
                }
            }
        });
        edtPinjaman.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    statFocusFieldBiayaAdminPerhitunganKendaraan = "1";
                    statusViewHitPerhitungan = "edtBiayaAdminPerhitunganKendaraan";
                    edtPinjaman.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            isPokokPembiayaanChange = true;
                            if(charSequence.equals("0")){
                                Toast.makeText(FormPengajuanNonElcActivity.this, "Tidak boleh diisi Nol", Toast.LENGTH_SHORT).show();
                            }else{
                                edtPinjaman.setError(null);
                            }

                            if (isEditedCalculate) {
//                                Toast.makeText(this, "Refresh kalkulasi terlebih dahulu di kolom Data Perhitungan Angsuran", Toast.LENGTH_SHORT).show();
                            } else {
                                isEditedCalculate = true;
                                edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(false);
//                                edtAngsuranPerbulanPerhitunganKendaraan.setFocusableInTouchMode(false);
                                edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.GRAY);
                                btnRefreshCalculate.setClickable(true);
                                btnRefreshCalculate.setTextColor(getResources().getColor(R.color.bg_gray));
                                btnRefreshCalculate.setBackgroundColor(getResources().getColor(R.color.green));
                            }

                           /* setRemoveCallbacksRunnable();
                            if (!isHitBiayaAdmin) {
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        setDefaultCalculatingToZero();
                                        if (checkMinimalBiayaAdmin()) {
                                            isHitBiayaAdmin = true;
                                            statFocusFieldBiayaAdminPerhitunganKendaraan = "0";
                                            hitPerhitunganKendaraanPokokPembiayaan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                        }
                                    }
                                };
                                handler.postDelayed(runnable, 2000);
                            }*/
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldBiayaAdminPerhitunganKendaraan = "1";
                }
            }
        });
        edtAngsuranPerbulanPerhitunganKendaraan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtAngsuranPerbulanPerhitunganKendaraan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            isEditedCalculate = true;
                            btnRefreshCalculate.setClickable(true);
                            btnRefreshCalculate.setTextColor(getResources().getColor(R.color.bg_gray));
                            btnRefreshCalculate.setBackgroundColor(getResources().getColor(R.color.green));
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "1";
                }
            }
        });
        /*edtStnkPerhitunganKendaraan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    statFocusFieldStnkPerhitunganKendaraan = "1";
                    statusViewHitPerhitungan = "edtStnkPerhitunganKendaraan";
                    edtStnkPerhitunganKendaraan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            setRemoveCallbacksRunnable();

                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    setDefaultCalculatingToZero();
                                    if (checkMinimalKredit()) {
                                        statFocusFieldStnkPerhitunganKendaraan = "0";
                                        hitPerhitunganKendaraan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                    }
                                }
                            };
                            handler.postDelayed(runnable, 500);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldStnkPerhitunganKendaraan = "1";
                }
            }
        });
        edtBiayaNotarisPerhitunganKendaraan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    statFocusFieldBiayaNotarisPerhitunganKendaraan = "1";
                    statusViewHitPerhitungan = "edtBiayaNotarisPerhitunganKendaraan";
                    edtBiayaNotarisPerhitunganKendaraan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            setRemoveCallbacksRunnable();
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    setDefaultCalculatingToZero();
                                    if (checkMinimalKredit()) {
                                        statFocusFieldBiayaNotarisPerhitunganKendaraan = "0";
                                        hitPerhitunganKendaraan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                    }
                                }
                            };
                            handler.postDelayed(runnable, 500);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldBiayaNotarisPerhitunganKendaraan = "1";
                }
            }
        });
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    statFocusFieldBiayaPnbpFidusiaPerhitunganKendaraan = "1";
                    statusViewHitPerhitungan = "edtBiayaPnbpFidusiaPerhitunganKendaraan";
                    edtBiayaPnbpFidusiaPerhitunganKendaraan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            setRemoveCallbacksRunnable();
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    setDefaultCalculatingToZero();
                                    if (checkMinimalKredit()) {
                                        statFocusFieldBiayaPnbpFidusiaPerhitunganKendaraan = "0";
                                        hitPerhitunganKendaraan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                    }
                                }
                            };
                            handler.postDelayed(runnable, 500);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldBiayaPnbpFidusiaPerhitunganKendaraan = "1";
                }
            }
        });
        edtBiayaSurveyPerhitunganKendaraan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    statFocusFieldBiayaSurveyPerhitunganKendaraan = "1";
                    statusViewHitPerhitungan = "edtBiayaSurveyPerhitunganKendaraan";
                    edtBiayaSurveyPerhitunganKendaraan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            setRemoveCallbacksRunnable();
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    setDefaultCalculatingToZero();
                                    if (checkMinimalKredit()) {
                                        statFocusFieldBiayaSurveyPerhitunganKendaraan = "0";
                                        hitPerhitunganKendaraan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                    }
                                }
                            };
                            handler.postDelayed(runnable, 500);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldBiayaSurveyPerhitunganKendaraan = "1";
                }
            }
        });*/
        actReferalCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    actReferalCode.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if (!isHitReferal) {
                                isHitReferal = true;
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {

                                        getReferalCodePresenter.GetReferalCode(token, actReferalCode.getText().toString(), supplierKode);

                                    }
                                };
                                handler.postDelayed(runnable, 3000);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                } else {
                    statFocusFieldPokokPembiayaan = "1";
                }
            }
        });
        edtNamaBpkbSendiri.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    tilNamaBpkbSendiri.setError(null);
                    tilNamaBpkbSendiri.setErrorEnabled(false);
                }
            }
        });
        edtNamaBpkbPasangan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    tilNamaBpkbPasangan.setError(null);
                    tilNamaBpkbPasangan.setErrorEnabled(false);
                }
            }
        });
        edtNamaBpkbOrangLain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    tilNamaBpkbOrangLain.setError(null);
                    tilNamaBpkbOrangLain.setErrorEnabled(false);
                }
            }
        });
    }

    private boolean checkMinimalAngsuranPerbulan() {
        String statAngsuranPerbulan;
        int intAngsuranPerbulan = Integer.parseInt(edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().replace(",", ""));
        if (intAngsuranPerbulan < intMinAngsuranPerBulan) {
            edtAngsuranPerbulanPerhitunganKendaraan.setError("Minimal : " + Util.formatNominal(intMinAngsuranPerBulan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statAngsuranPerbulan = "0";
        } else {
            edtAngsuranPerbulanPerhitunganKendaraan.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statAngsuranPerbulan = "1";
        }
        return statAngsuranPerbulan.equalsIgnoreCase("1");
    }

    private boolean checkMaksimalPokokPembiayaan() {
        String statPokokPembiayaan;
        int intPokokPembiayaan = Integer.parseInt(edtPokokPembiayaan.getText().toString().replace(",", ""));
        if (intPokokPembiayaan > Integer.parseInt(consumerloanDefault)) {
            edtPokokPembiayaan.setError("Maksimal : " + Util.formatNominal(Integer.parseInt(consumerloanDefault)));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else {
            edtPokokPembiayaan.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statPokokPembiayaan = "1";
        }
        return statPokokPembiayaan.equalsIgnoreCase("1");
    }

    private boolean checkMaksimalPinjaman() {
        String statPokokPembiayaan;
        int intPokokPembiayaan = Integer.parseInt(edtPinjaman.getText().toString().replace(",", ""));
        if (intPokokPembiayaan > Integer.parseInt(consumerloanDefault)) {
            edtPinjaman.setError("Maksimal : " + Util.formatNominal(Integer.parseInt(consumerloanDefault)));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else {
            edtPinjaman.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statPokokPembiayaan = "1";
        }
        return statPokokPembiayaan.equalsIgnoreCase("1");
    }


    private boolean checkMinimalBiayaAdmin() {
        String statAdmin;
        int intBiayaAdmin = Integer.parseInt(edtBiayaAdminPerhitunganKendaraan.getText().toString().replace(",", ""));
        if (intBiayaAdmin < intMinBiayaAdminPerhitunganKendaraan) {
            edtBiayaAdminPerhitunganKendaraan.setError("Minimal : " + Util.formatNominal(intMinBiayaAdminPerhitunganKendaraan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statAdmin = "0";
        } else {
            edtBiayaAdminPerhitunganKendaraan.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statAdmin = "1";
        }
        return statAdmin.equalsIgnoreCase("1");
    }

    private boolean checkMinimalPokokPembiayaan() {
        String statPokokPembiayaan;
        int intPokokPembiayaan = Integer.parseInt(edtPokokPembiayaan.getText().toString().replace(",", ""));
        if (intPokokPembiayaan < intMinPokokPembiayaan) {
            edtPokokPembiayaan.setError("Minimal : " + Util.formatNominal(intMinPokokPembiayaan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else if (intPokokPembiayaan > intMaxPokokPembiayaan) {
            edtPokokPembiayaan.setError("Maximal : " + Util.formatNominal(intMaxPokokPembiayaan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else {
            edtPokokPembiayaan.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statPokokPembiayaan = "1";
        }
        return statPokokPembiayaan.equalsIgnoreCase("1");
    }

    private boolean checkMinimalPinjaman() {
        String statPokokPembiayaan;
        int intPokokPembiayaan = Integer.parseInt(edtPinjaman.getText().toString().replace(",", ""));
        if (intPokokPembiayaan < intMinPokokPembiayaan) {
            edtPinjaman.setError("Minimal : " + Util.formatNominal(intMinPokokPembiayaan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else if (intPokokPembiayaan > intMaxPokokPembiayaan) {
            edtPinjaman.setError("Maximal : " + Util.formatNominal(intMaxPokokPembiayaan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else {
            edtPinjaman.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statPokokPembiayaan = "1";
        }
        return statPokokPembiayaan.equalsIgnoreCase("1");
    }

    private void setRemoveCallbacksRunnable() {
        if (null != runnable) {
            handler.removeCallbacks(runnable);
        }
    }

    private boolean checkMinimalKredit() {
        int intPokokPembiayaan = Integer.parseInt(edtPokokPembiayaan.getText().toString().replace(",", ""));
        int intBiayaAdmin = Integer.parseInt(edtBiayaAdminPerhitunganKendaraan.getText().toString().replace(",", ""));
        int intDownpayment = Integer.parseInt(edtDownpayment.getText().toString().replace(",", ""));
        int intAngsuranPerbulan = Integer.parseInt(edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().replace(",", ""));
        String statAdmin;
        String statPokokPembiayaan;
        String statDownPayment;
        String statAngsuranPerbulan;

//        cek minimal angsuran perbulan
        if (intAngsuranPerbulan < intMinAngsuranPerBulan) {
            edtAngsuranPerbulanPerhitunganKendaraan.setError("Minimal : " + Util.formatNominal(intMinAngsuranPerBulan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statAngsuranPerbulan = "0";
        } else {
            edtAngsuranPerbulanPerhitunganKendaraan.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statAngsuranPerbulan = "1";
        }

//        cek minimal biaya admin
        if (intBiayaAdmin < intMinBiayaAdminPerhitunganKendaraan) {
            edtBiayaAdminPerhitunganKendaraan.setError("Minimal : " + Util.formatNominal(intMinBiayaAdminPerhitunganKendaraan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statAdmin = "0";
        } else {
            edtBiayaAdminPerhitunganKendaraan.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statAdmin = "1";
        }

//        cek minimal dp
        if (intDownpayment < intMinDownPayment) {
            edtDownpayment.setError("Minimal : " + Util.formatNominal(intMinDownPayment));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statDownPayment = "0";
        } else {
            edtDownpayment.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statDownPayment = "1";
        }

//        cek minimal pokok pembiayaan
        if (intPokokPembiayaan < intMinPokokPembiayaan) {
            edtPokokPembiayaan.setError("Minimal : " + Util.formatNominal(intMinPokokPembiayaan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else if (intPokokPembiayaan > intMaxPokokPembiayaan) {
            edtPokokPembiayaan.setError("Maximal : " + Util.formatNominal(intMaxPokokPembiayaan));
            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            statPokokPembiayaan = "0";
        } else {
            edtPokokPembiayaan.setError(null);
            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
            statPokokPembiayaan = "1";
        }
        return statPokokPembiayaan.equalsIgnoreCase("1")
                && statAdmin.equalsIgnoreCase("1")
                && statDownPayment.equalsIgnoreCase("1")
                && statAngsuranPerbulan.equalsIgnoreCase("1");
    }

    private void initAllCaps() {
        InputFilter inputFilterAllCaps = new InputFilter.AllCaps();
        InputFilter inputFilter4 = new InputFilter.LengthFilter(4);
        InputFilter inputFilter10 = new InputFilter.LengthFilter(10);
        InputFilter inputFilter11 = new InputFilter.LengthFilter(11);
        InputFilter inputFilter16 = new InputFilter.LengthFilter(16);
        InputFilter inputFilter20 = new InputFilter.LengthFilter(20);
        InputFilter inputFilter30 = new InputFilter.LengthFilter(30);
        InputFilter inputFilter25 = new InputFilter.LengthFilter(25);
        InputFilter inputFilter50 = new InputFilter.LengthFilter(50);
        InputFilter inputFilter100 = new InputFilter.LengthFilter(100);

        InputFilter inputFilterNoNumber = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

//        pribadi
        edtPribadiNamaPemohon.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtPribadiNoKK.setFilters(new InputFilter[]{inputFilter16});
        edtPribadiNamaLengkapPemohon.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtPribadiTempatLahir.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50, noNumberAndSymbol()});
        edtNamaIbuPribadi.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtValidasiNamaIbuKandung.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtValidasiNamaLegal.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtEmailPribadi.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter30});
//        alamat dan ktp
        edtAlamatTinggal.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter100});
        actAutoAlamatPemohon.setFilters(new InputFilter[]{inputFilterAllCaps});
        edtAlamatKtp.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter100});
        actAutoKtpAlamatPemohon.setFilters(new InputFilter[]{inputFilterAllCaps});
//        pasangan
        edtPasanganNama.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtPasanganNoKtp.setFilters(new InputFilter[]{inputFilter16});
        edtPasanganTempatLahir.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50, noNumberAndSymbol()});
        edtPasanganKodeAreaTeleponRumah.setFilters(new InputFilter[]{inputFilter4});
        edtPasanganNoTeleponRumah.setFilters(new InputFilter[]{inputFilter16});
        edtPasanganKodeAreaTeleponPerusahaan.setFilters(new InputFilter[]{inputFilter4});
        edtPasanganNoTeleponPerusahaan.setFilters(new InputFilter[]{inputFilter16});
        edtPasanganNoHp.setFilters(new InputFilter[]{inputFilter20});
        edtPasanganAlamat.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter100});
        actPasanganKota.setFilters(new InputFilter[]{inputFilterAllCaps});
        actPasanganProfesi.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50});
        actPasanganJobType.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50});
        actPasanganJobPosition.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50});
        actPasanganIndustri.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50});
        edtPasanganNamaPerusahaan.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50, noNumberAndSymbol()});
        edtPasanganNamaIbu.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
//        emergency kontak
        edtNamaKerabat.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtAlamatKerabat.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter100});
        edtHpKerabat.setFilters(new InputFilter[]{inputFilter20});
        actAutoAlamatKerabat.setFilters(new InputFilter[]{inputFilterAllCaps});
//        data pekerjaan
//        edtNamaPerusahaan.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50, noSymbol()});
        edtNamaPerusahaan.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50, noSymbol()});
        edtAlamatPerusahaan.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter100});
        actAutoAlamatPekerjaan.setFilters(new InputFilter[]{inputFilterAllCaps});
//        detail product
        actDtProductSupplier.setFilters(new InputFilter[]{inputFilterAllCaps});
        actDtProductMarketingSupplier.setFilters(new InputFilter[]{inputFilterAllCaps});
        actDtProductOffering.setFilters(new InputFilter[]{inputFilterAllCaps});
        actDtProductPos.setFilters(new InputFilter[]{inputFilterAllCaps});
//        agunan
        actTypeKendaraan.setFilters(new InputFilter[]{inputFilterAllCaps});
        edtWarnaKendaraan.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter25});
        edtNoPolisi.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter10, noSymbol()});
        edtNoRangka.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50});
        edtNoMesin.setFilters(new InputFilter[]{inputFilterAllCaps, inputFilter50});
        edtNamaBpkbSendiri.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtNamaBpkbPasangan.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
        edtNamaBpkbOrangLain.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
//        data perhitungan
        edtPokokPembiayaan.setFilters(new InputFilter[]{inputFilter11});
        edtBiayaAdminPerhitunganKendaraan.setFilters(new InputFilter[]{inputFilter11});
        edtAngsuranPerbulanPerhitunganKendaraan.setFilters(new InputFilter[]{inputFilter11});
//        keterangan
        edtKeterangan.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter50});
//        rekomendasi
        edtDescRecomendation.setFilters(new InputFilter[]{inputFilterAllCaps, noNumberAndSymbol(), inputFilter100});
    }

    private InputFilter noNumberAndSymbol() {
        return new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (src.equals("")) { // for backspace
                    return src;
                }
                if (src.toString().matches("[A-Z ]+")) {
                    return src;
                }
                return "";
            }
        };
    }

    private InputFilter noSymbol() {
        return new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (src.equals("")) { // for backspace
                    return src;
                }
                if (src.toString().matches("[A-Z0-9 ]+")) {
                    return src;
                }
                return "";
            }
        };
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

    @OnClick({R.id.btn_next, R.id.btn_save_draft, R.id.btn_submit, R.id.btn_refresh_calculate})
    public void onClickSaveData(View view) {
        Util.hideKeyboard(this, view);
        if (Util.checkActiveLocation(this)) {
            switch (view.getId()) {
                case R.id.btn_next:
                    statusButton = "1";
                    typeFormCheckFpd = "next";
                    validatorDataPribadi.validatePengajuan();
//                    mRecomendationPresenter.checkFpd(token);
                    break;
                case R.id.btn_save_draft:
                    statusButton = "2";
                    txtAttachmentError.setVisibility(View.GONE);
                    txtTtdPemohonError.setVisibility(View.GONE);
                    txtTtdPasanganError.setVisibility(View.GONE);
                    checkAutoCompleteTextView();
                    setHeaderIcArrow();
                    setColorTextViewDefault();

                    if (cbxAlamatKtp.isChecked()) {
                        edtAlamatTinggal.setText(edtAlamatKtp.getText().toString());
                        edtRtTinggal.setText(edtRtKtp.getText().toString());
                        edtRwTinggal.setText(edtRwKtp.getText().toString());
                        actAutoAlamatPemohon.setText(actAutoKtpAlamatPemohon.getText().toString());
                        actAutoAlamatPemohon.setSelectionFromPopUp(true);
                        edtAreaPhoneTinggal.setText(edtAreaPhoneKtp.getText().toString());
                        edtPhoneTinggal.setText(edtPhoneKtp.getText().toString());
                        actAutoAlamatPemohon.setSelectionFromPopUp(true);
                    }
                    typeFormCheckFpd = "draft";
                    validatorSaveDraft.validateSaveDraft();
//                    mRecomendationPresenter.checkFpd(token);
                    break;
                case R.id.btn_submit:
                    if (isEditedCalculate) {
                        Toast.makeText(this, "Refresh kalkulasi terlebih dahulu di kolom Data Perhitungan Angsuran", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("testNoHP", edtPasanganNoHp.getText() == null ? "0" : edtPasanganNoHp.getText().toString());
                        statusButton = "3";
                        txtAttachmentError.setVisibility(View.GONE);
                        txtTtdPemohonError.setVisibility(View.GONE);
                        txtTtdPasanganError.setVisibility(View.GONE);
                        if (!strStatusPengajuan.equalsIgnoreCase("RO")) checkAutoCompleteTextView();
                        setHeaderIcArrow();
                        setColorTextViewDefault();

                        if (cbxAlamatKtp.isChecked()) {
                            edtAlamatTinggal.setText(edtAlamatKtp.getText().toString());
                            edtRtTinggal.setText(edtRtKtp.getText().toString());
                            edtRwTinggal.setText(edtRwKtp.getText().toString());
                            actAutoAlamatPemohon.setText(actAutoKtpAlamatPemohon.getText().toString());
                            actAutoAlamatPemohon.setSelectionFromPopUp(true);
                            edtAreaPhoneTinggal.setText(edtAreaPhoneKtp.getText().toString());
                            edtPhoneTinggal.setText(edtPhoneKtp.getText().toString());
                        }
//                    Toast.makeText(this, "sebelum validasi submit", Toast.LENGTH_SHORT).show();
                        if (form.equalsIgnoreCase("Edit")) {
                            validator.removeRules(imgTakePicture5);
                        }
//                        Log.e("Loging profesi : ",actProfesiPerusahaan.getText().toString());
                        typeFormCheckFpd = "submit";
                        validator.validate();
//                    mRecomendationPresenter.checkFpd(token);
                    }
                    break;
                case R.id.btn_refresh_calculate:
                    if (spnTenorPerhitunganKendaraan.getSelectedItem() != null) {
                        if (!spnTenorPerhitunganKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                            if (isEditedCalculate) {
                                setRemoveCallbacksRunnable();
                                if (isPokokPembiayaanChange) {
                                    if (!isHit) {
                                        statFocusFieldPokokPembiayaan = "1";
                                        statusViewHitPerhitungan = "edtPokokPembiayaan";
                                        runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                setDefaultCalculatingToZero();
                                                if (checkMinimalBiayaAdmin() && checkMaksimalPinjaman() && checkMinimalPinjaman()) {
                                                    statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "0";
                                                    isHit = true;
                                                    hitPerhitunganKendaraanPokokPembiayaan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                                }
                                            }
                                        };
                                        handler.postDelayed(runnable, 1000);
                                    }
                                /*if(edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().replace(",", "").equalsIgnoreCase(monthlypaymentDefault)){
                                    if (!isHit) {
                                        statFocusFieldPokokPembiayaan = "1";
                                        statusViewHitPerhitungan = "edtPokokPembiayaan";
                                        runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                setDefaultCalculatingToZero();
                                                if (checkMinimalBiayaAdmin() && checkMaksimalPokokPembiayaan()) {
                                                    statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "0";
                                                    isHit = true;
                                                    hitPerhitunganKendaraanPokokPembiayaan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                                }
                                            }
                                        };
                                        handler.postDelayed(runnable, 1000);
                                    }
                                }else{
                                    if(edtPokokPembiayaan.equals(consumerloanDefault)){

                                    }else{

                                        if (!isHit) {
                                            statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "1";
                                            statusViewHitPerhitungan = "edtAngsuranPerbulanPerhitunganKendaraan";
                                            runnable = new Runnable() {
                                                @Override
                                                public void run() {
                                                    setDefaultCalculatingToZero();
                                                    if (checkMinimalBiayaAdmin() && checkMaksimalPokokPembiayaan()) {
                                                        statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "0";
                                                        isHit = true;
                                                        hitPerhitunganKendaraanAngsuranPerbulan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                                    }
                                                }
                                            };
                                            handler.postDelayed(runnable, 1000);
                                        }
                                    }
                                }*/
                                } else {
                                    if (!isHit) {
                                        statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "1";
                                        statusViewHitPerhitungan = "edtAngsuranPerbulanPerhitunganKendaraan";
                                        runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                setDefaultCalculatingToZero();
                                                if (checkMinimalAngsuranPerbulan() && checkMinimalBiayaAdmin() && checkMaksimalPokokPembiayaan()) {
                                                    statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "0";
//                                                isChangeAngsuran = true;
                                                    isHit = true;
                                                    hitPerhitunganKendaraanAngsuranPerbulan(statusViewHitPerhitungan, statSinkron, strEffectiveRate, strDPPercentage);
                                                }
                                            }
                                        };
                                        handler.postDelayed(runnable, 1000);
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Tidak ada perubahan perhitungan untuk dikalkulasi", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Harap isi Tenor terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Harap isi Tenor terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void setColorTextViewDefault() {
        tvValidasiTipePengajuan.setTextColor(Color.GRAY);
        tvKopJenisPembiayaan.setTextColor(Color.GRAY);
        tvKopTujuanPenggunaanDana.setTextColor(Color.GRAY);
        tvMetodePenjualan.setTextColor(Color.GRAY);
        tvPribadiJenisKelamin.setTextColor(Color.GRAY);
        tvPribadiStatusPernikahan.setTextColor(Color.GRAY);
        tvPerjanjianPisahHarta.setTextColor(Color.GRAY);
        tvStatusRumahPribadi.setTextColor(Color.GRAY);
        tvPendidikanPribadi.setTextColor(Color.GRAY);
        tvAgamaPribadi.setTextColor(Color.GRAY);
        tvHubunganKerabatSpn.setTextColor(Color.GRAY);
        tvTenorPerhitunganKendaraan.setTextColor(Color.GRAY);
        tvDataAsuransi.setTextColor(Color.GRAY);
        tvPasanganStatus.setTextColor(Color.GRAY);
        tvStatusKendaraan.setTextColor(Color.GRAY);
        tvBpkbAtasNama.setTextColor(Color.GRAY);
        tvKeleluasaan.setTextColor(Color.GRAY);
        tvGroupRekomendasi.setTextColor(Color.GRAY);
        tvRekomendasi.setTextColor(Color.GRAY);
        tvPemakaianKendaraan.setTextColor(Color.GRAY);
        tvJenisKendaraan.setTextColor(Color.GRAY);
        tvMerkKendaraan.setTextColor(Color.GRAY);
        tvTahunKendaraan.setTextColor(Color.GRAY);

        tvCamera1.setTextColor(Color.GRAY);
        tvCamera2.setTextColor(Color.GRAY);
        tvCamera3.setTextColor(Color.GRAY);
        tvCamera4.setTextColor(Color.GRAY);
        tvCamera5.setTextColor(Color.GRAY);
        tvCamera6.setTextColor(Color.GRAY);
        tvCamera7.setTextColor(Color.GRAY);
        tvCamera8.setTextColor(Color.GRAY);
        tvCamera9.setTextColor(Color.GRAY);
        tvCamera10.setTextColor(Color.GRAY);
        tvCamera11.setTextColor(Color.GRAY);
        tvCamera12.setTextColor(Color.GRAY);
        tvCamera13.setTextColor(Color.GRAY);
        tvCamera14.setTextColor(Color.GRAY);
        tvCamera15.setTextColor(Color.GRAY);
        tvCamera16.setTextColor(Color.GRAY);
        tvCamera17.setTextColor(Color.GRAY);
        tvCamera18.setTextColor(Color.GRAY);
        tvCamera19.setTextColor(Color.GRAY);
        tvCamera20.setTextColor(Color.GRAY);
        tvCamera21.setTextColor(Color.GRAY);
        tvCamera22.setTextColor(Color.GRAY);
        tvCamera23.setTextColor(Color.GRAY);
        tvCamera24.setTextColor(Color.GRAY);
        tvCamera25.setTextColor(Color.GRAY);
        tvCamera26.setTextColor(Color.GRAY);
        tvCamera27.setTextColor(Color.GRAY);
        tvCamera28.setTextColor(Color.GRAY);
        tvCamera29.setTextColor(Color.GRAY);
    }

    private void checkStatusPernikahan() {
        String strStatusPernikahan = spnPribadiStatusPernikahan.getSelectedItem().toString();
        if ("Menikah".equalsIgnoreCase(strStatusPernikahan) || "M".equalsIgnoreCase(strStatusPernikahan)) {
            lnWrapperDataPasangan.setVisibility(View.VISIBLE);
            lnTtdPemohonLain.setVisibility(View.VISIBLE);
            lnTakeFoto5.setVisibility(View.VISIBLE);
            llPerjanjianPisahHarta.setVisibility(View.VISIBLE);
            rbBpkbAtasNamaPasangan.setVisibility(View.VISIBLE);
            tilNamaBpkbPasangan.setVisibility(View.VISIBLE);
            edtPenghasilanPasanganPerusahaan.setVisibility(View.VISIBLE);
            if (form.equalsIgnoreCase("Edit")) {
                removeRulesDataPasangan();
            } else {
                validator.put(edtPasanganNoHp, noHpRule);
                removeRulesDataPasangan();
                putRulesDataPasangan();
            }
        } else {
            lnWrapperDataPasangan.setVisibility(View.GONE);
            lnTtdPemohonLain.setVisibility(View.GONE);
            lnTakeFoto5.setVisibility(View.GONE);
            llPerjanjianPisahHarta.setVisibility(View.GONE);
            rbBpkbAtasNamaPasangan.setVisibility(View.GONE);
            tilNamaBpkbPasangan.setVisibility(View.GONE);
            edtPenghasilanPasanganPerusahaan.setVisibility(View.GONE);
            setDataPasanganToEmptyString();
            putRulesDataPasangan();
            removeRulesDataPasangan();

            rbPerjanjianPisahHartaAda.setChecked(false);
            rbPerjanjianPisahHartaTidakAda.setChecked(false);

            imgTtdPasanganPersetujuan.setImageResource(0);
            imgTakePicture5.setImageResource(0);
        }
    }

    private void setDataPasanganToEmptyString() {
        edtPasanganNama.setText("");
        edtPasanganNoKtp.setText("");
        edtPasanganTempatLahir.setText("");
        edtPasanganTanggalLahir.setText("");
        edtPasanganKodeAreaTeleponRumah.setText("");
        edtPasanganNoTeleponRumah.setText("");
        edtPasanganKodeAreaTeleponPerusahaan.setText("");
        edtPasanganNoTeleponPerusahaan.setText("");
        edtPasanganNoHp.setText("");
        edtPasanganAlamat.setText("");
        actPasanganKota.setText("");
        actPasanganProfesi.setText("");
        actPasanganJobType.setText("");
        actPasanganJobPosition.setText("");
        actPasanganIndustri.setText("");
        edtPasanganNamaPerusahaan.setText("");
        edtPasanganNamaIbu.setText("");
    }

    private void putRulesDataPasangan() {
        validator.put(edtPasanganNama, notEmptyRule);
        validator.put(edtPasanganNoKtp, ktpRule);
        validator.put(edtPasanganTempatLahir, notEmptyRule);
        validator.put(edtPasanganTanggalLahir, notEmptyRule);
        validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
        validator.put(edtPasanganNoTeleponRumah, notEmptyRule);
//        validator.put(edtPasanganKodeAreaTeleponPerusahaan, kodeAreaRule);
//        validator.put(edtPasanganNoTeleponPerusahaan, notEmptyRule);
        validator.put(edtPasanganNoHp, notEmptyRule);
        validator.put(edtPasanganAlamat, notEmptyRule);
        validator.put(actPasanganKota, notEmptyRule);
//        validator.put(actPasanganProfesi, notEmptyRule);
//        validator.put(actPasanganJobType, notEmptyRule);
//        validator.put(actPasanganJobPosition, notEmptyRule);
//        validator.put(actPasanganIndustri, notEmptyRule);
//        validator.put(rgPasanganStatus, radioGroupRule);
//        validator.put(edtPasanganNamaPerusahaan, notEmptyRule);
        validator.put(edtPasanganNamaIbu, notEmptyRule);
//        validator.put(edtPenghasilanPasanganPerusahaan, notEmptyRule);

        validator.put(rgPerjanjianPisahHarta, radioGroupRule);
        validator.put(imgTtdPasanganPersetujuan, imageViewRule);
//        validator.put(imgTakePicture5, imageViewRule);
        if (!form.equalsIgnoreCase("Edit")) validator.put(imgTakePicture5, imageViewRule);
    }

    private void removeRulesDataPasangan() {
        validator.removeRules(edtPasanganNama);
        validator.removeRules(edtPasanganNoKtp);
        validator.removeRules(edtPasanganTempatLahir);
        validator.removeRules(edtPasanganTanggalLahir);
        validator.removeRules(edtPasanganKodeAreaTeleponRumah);
        validator.removeRules(edtPasanganNoTeleponRumah);
        validator.removeRules(edtPasanganKodeAreaTeleponPerusahaan);
        validator.removeRules(edtPasanganNoTeleponPerusahaan);
        validator.removeRules(edtPasanganNoHp);
        validator.removeRules(edtPasanganAlamat);
        validator.removeRules(actPasanganKota);
        validator.removeRules(actPasanganProfesi);
        validator.removeRules(actPasanganJobType);
        validator.removeRules(actPasanganJobPosition);
        validator.removeRules(actPasanganIndustri);
        validator.removeRules(rgPasanganStatus);
        validator.removeRules(edtPasanganNamaPerusahaan);
        validator.removeRules(edtPasanganNamaIbu);
        validator.removeRules(edtPenghasilanPasanganPerusahaan);

        validator.removeRules(imgTtdPasanganPersetujuan);
        validator.removeRules(imgTakePicture5);
        validator.removeRules(rgPerjanjianPisahHarta);
    }

    @Override
    public void onPreMerkKendaraan() {
        preLoading();
    }

    @Override
    public void onSuccessMerkKendaraan(MerkKendaraanResponse merkKendaraanResponse, String merk) {
        this.merkKendaraanResponse = merkKendaraanResponse;
        merkKendaraanNameArrayList.clear();
        setAdapterMerkKendaraan();
        statusAutoComplete = "merkKendaraan";
        strCheckMerkKendaraan = "1";
        successAndFailedLoading();

        /*Assign edit enable form*/
        if (!form.equalsIgnoreCase("Edit")) containerInitAutoComplete();
        else containerInitAutoCompleteAssignEdit();
    }

    @Override
    public void onSuccessTipeKendaraan(MerkKendaraanResponse merkKendaraanResponse, String merk) {
        this.merkKendaraanResponse = merkKendaraanResponse;
        tipeKendaraanNameArrayList.clear();
        setAdapterTipeKendaraan();
        statusAutoComplete = "tipeKendaraan";
        strCheckTipeKendaraan = "1";
        successAndFailedLoading();
        /*Assign edit enable form*/
        if (!form.equalsIgnoreCase("Edit")) containerInitAutoComplete();
        else containerInitAutoCompleteAssignEdit();

    }

    @Override
    public void onFailedMerkKendaraan(String message, String assetLevel) {
        if (assetLevel.equalsIgnoreCase("1")) {
            merkKendaraanNameArrayList.clear();
            merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
            setAdapterMerkKendaraan();
            strCheckMerkKendaraan = "0";
        } else {
            tipeKendaraanNameArrayList.clear();
            setAdapterTipeKendaraan();
            strCheckTipeKendaraan = "0";
        }

        messageFailedApi(message);
        successAndFailedLoading();
    }

    private void setAdapterTipeKendaraan() {
        actTipeKendaraanAdapter = new ArrayAdapter<TipeKendaraanArrayList>(this, R.layout.item_dropdown, R.id.id_item, tipeKendaraanNameArrayList);
        actTypeKendaraan.setAdapter(actTipeKendaraanAdapter);
    }

    private void setAdapterMerkKendaraan() {
        actMmerkKendaraanAdapter = new ArrayAdapter<MerkKendaraanArrayList>(this, R.layout.item_dropdown, R.id.id_item, merkKendaraanNameArrayList);
        spnMerkKendaraan.setAdapter(actMmerkKendaraanAdapter);
    }

    @Override
    public void onPreTahunProduksiKendaraan() {
        preLoading();
    }

    @Override
    public void onSuccessTahunProduksiKendaraan(TahunProduksiResponse tahunProduksiResponse) {
        this.tahunProduksiResponse = tahunProduksiResponse;
        manufacturingYearArrayList.clear();
        setAdapterTahunProduksi();
        statusAutoComplete = "tahunKendaraan";
        strCheckTahunProduksiKendaraan = "1";
        /*Assign edit enable form*/
        if (!form.equalsIgnoreCase("Edit")) containerInitAutoComplete();
        else containerInitAutoCompleteAssignEdit();
        successAndFailedLoading();
    }

    @Override
    public void onFailedTahunProduksiKendaraan(String message) {
        manufacturingYearArrayList.clear();
        setAdapterTahunProduksi();
        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
        setAdapterTahunProduksi();
        strCheckTahunProduksiKendaraan = "0";
        messageFailedApi(message);
        successAndFailedLoading();
    }

    private void setAdapterTahunProduksi() {
        actMmanufacturingYearAdapter = new ArrayAdapter<TahunKendaraanArrayList>(this, R.layout.item_dropdown, R.id.id_item, manufacturingYearArrayList);
        spnTahunKendaraan.setAdapter(actMmanufacturingYearAdapter);
    }

    @Override
    public void onPreHargaAgunanKendaraan() {
        preLoading();
    }

    @Override
    public void onSuccessHargaAgunanKendaraan(HargaAgunanResponse hargaAgunanResponse) {
        edtHargaAgunan.setText(hargaAgunanResponse.getMarketPriceValue());
        hitPerhitunganKendaraan("onSuccessHargaAgunanKendaraan", statSinkron, strEffectiveRate, strDPPercentage);
        successAndFailedLoading();
    }

    @Override
    public void onFailedHargaAgunanKendaraan(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
    }

    @Override
    public void onPrePerhitunganKendaraan() {
        preLoading();
    }

    @Override
    public void onSuccessPerhitunganKendaraan(DetailPerhitunganKendaraanResponse data, String statSinkron) {
        /*jika pokok pembiayaan lebih dari 50 jt, maka wajib isi npwp*/


        isHitPokokPembiayaan = false;
        isHitBiayaAdmin = false;
        isHit = false;


        isEditedCalculate = false;
        isPokokPembiayaanChange = false;
        btnRefreshCalculate.setClickable(false);
        btnRefreshCalculate.setTextColor(getResources().getColor(R.color.bg_gray));
        btnRefreshCalculate.setBackgroundColor(getResources().getColor(R.color.colorSecondaryText));

        if(data.getPinjaman() == null){

            runnable = new Runnable() {
                @Override
                public void run() {
//                edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(true);
//                edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.BLACK);
                    edtPinjaman.setError("Pinjaman tidak boleh 0");
                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                }
            };
            handler.postDelayed(runnable, 2000);
        }else if(data.getPinjaman().equalsIgnoreCase("0")){
            runnable = new Runnable() {
                @Override
                public void run() {
//                edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(true);
//                edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.BLACK);
                    edtPinjaman.setError("Pinjaman tidak boleh 0");
                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                }
            };
            handler.postDelayed(runnable, 2000);
        }

        if (Integer.parseInt(data.getPokokPembiayaan()) >= 50000000) {
            if (!isNpwpShow) {
//                validator.put(edtNoNpwpDetail, npwpRule);
                isNpwpShow = true;
                rlHeaderDataNpwp.setVisibility(View.VISIBLE);
                if (edtNoNpwpDetail.getText().toString().isEmpty()) {
                    edtNoNpwpDetail.setError("Wajib Di isi");
                    tilNoNpwpDetail.setError(getString(R.string.text_error_invalid_length));
                    imgDropDownNpwp.setImageResource(android.R.drawable.ic_dialog_alert);
                    rlDataNpwp.setVisibility(View.VISIBLE);
                }
                validator.put(edtNoNpwpDetail, npwpRule);
                edtNoNpwpDetail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        final String result = s.toString();
                        if (result.length() > 1) {
                            edtNoNpwpDetail.setError(null);
                            tilNoNpwpDetail.setError(null);
                            imgDropDownNpwp.setImageResource(R.drawable.ic_arrow);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        } else {
            isNpwpShow = false;
            tilNoNpwpPribadi.setVisibility(View.GONE);
            edtNoNpwpPribadi.setVisibility(View.GONE);
            rlHeaderDataNpwp.setVisibility(View.GONE);
            rlDataNpwp.setVisibility(View.GONE);
            edtNoNpwpPribadi.getText().clear();
            validator.removeRules(edtNoNpwpDetail);
        }

        if (statSinkron.equalsIgnoreCase("0")) {
//            hanya hitung
            if (data.getPokokPembiayaanLama().equalsIgnoreCase("0")) {
                intMaxPokokPembiayaan = Integer.parseInt(data.getPokokPembiayaan());
            } else {
                intMaxPokokPembiayaan = Integer.parseInt(data.getPokokPembiayaanLama());
            }
            if (data.getDownPaymentLama().equalsIgnoreCase("0")) {
                intMinDownPayment = Integer.parseInt(data.getDownPayment());
            } else {
                intMinDownPayment = Integer.parseInt(data.getDownPaymentLama());
            }
            if (data.getAngsuranPerbulanLama().equalsIgnoreCase("0")) {
                intMinAngsuranPerBulan = Integer.parseInt(data.getAngsuranPerbulan());
            } else {
                intMinAngsuranPerBulan = Integer.parseInt(data.getAngsuranPerbulanLama());
            }

            edtHargaAgunan.setText(data.getHargaAgunan());
            edtDownpayment.setText(data.getDownPayment());
//            if (statFocusFieldPokokPembiayaan.equals("1")) {
            edtPokokPembiayaan.setText(String.valueOf(data.getPokokPembiayaan()));
//                perhitunganPokokPembiayaan = String.valueOf(data.getPokokPembiayaan());
//            }
//            if (statFocusFieldAngsuranPerbulanPerhitunganKendaraan.equals("1")) {
            edtAngsuranPerbulanPerhitunganKendaraan.setText(data.getAngsuranPerbulan());
//            }
//            if (statFocusFieldBiayaAdminPerhitunganKendaraan.equals("1")) {
            edtBiayaAdminPerhitunganKendaraan.setText(data.getBiayaAdministrasi());
//            }
            edtPremiAsuransiPerhitunganAgunanKendaraan.setText(String.valueOf(data.getAsuransiAgunan()));
            edtPremiAsuransiPerhitunganJiwaKendaraan.setText(data.getAsuransiJiwa());
            edtJumlahPembiayaanPerhitunganKendaraan.setText(data.getJumlahPembiayaan());
            edtBungaPembiayaanKendaraan.setText(data.getBungaPembiayaan());
            edtTotalPinjamanPerhitunganKendaraan.setText(data.getTotalPinjaman());
            edtFlatPertahunPerhitunganKendaraan.setText(data.getBungaFlatPerTahun());
            edtAngsuranPertamaPerhitunganKendaraan.setText(data.getSetorPertama());
            if (isSelectedTenor) {
                consumerloanDefault = data.getPokokPembiayaan();
                monthlypaymentDefault = data.getAngsuranPerbulan();
                isSelectedTenor = false;
            }
            successAndFailedLoading();
        } else {
//            hitung lalu sinkron untuk pengajuan baru dan draft
            setValueToHashMap();
            preLoading();
            mRecomendationPresenter.checkFpd(token);
//            mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
        }
    }

    @Override
    public void onFailedPerhitunganKendaraan(String message) {
        isHitPokokPembiayaan = false;
        isHitBiayaAdmin = false;
        isHit = false;
        messageFailedApi(message);
        successAndFailedLoading();
    }

    @Override
    public void onPreProductOffTenor() {
        preLoading();
    }

    @Override
    public void onSuccessProductOffTenor(ProductOffTenorResponse productOffTenorResponse) {
        this.productOffTenorResponse = productOffTenorResponse;
        tenorArrayList.clear();
        setAdapterTenor();
        statusAutoComplete = "tenor";
        strCheckProductOffTenor = "1";
        /*Assign edit enable form*/
        if (!form.equalsIgnoreCase("Edit")) containerInitAutoComplete();
        else containerInitAutoCompleteAssignEdit();
        successAndFailedLoading();
    }

    @Override
    public void onFailedProductOffTenor(String message) {
        tenorArrayList.clear();
        setAdapterTenor();
        tenorArrayList.add(new TenorArrayList(
                "PILIH",
                "",
                "",
                "",
                ""));
        setAdapterTenor();

        strCheckProductOffTenor = "0";
        successAndFailedLoading();
        messageFailedApi(message);
    }

    private void setAdapterTenor() {
        productOffTenorAdapter = new ArrayAdapter<TenorArrayList>(this, R.layout.item_dropdown, R.id.id_item, tenorArrayList);
        spnTenorPerhitunganKendaraan.setAdapter(productOffTenorAdapter);
    }

    @Override
    public void onPreSearchProductOffering() {
        preLoading();
    }

    @Override
    public void onSuccessSearchProductOffering(ProductOfferingResponse productOfferingResponse) {
        this.productOfferingResponse = productOfferingResponse;
        statusAutoComplete = "productOffering";
        strCheckProductOffering = "1";
        productOfferingNameArrayList.clear();

        setAdapterProductOffering();
        /*Assign edit enable form*/
//        containerInitAutoComplete();
        containerInitAutoComplete();
        successAndFailedLoading();
    }

    @Override
    public void onFailedSearchProductOffering(String message) {
        strCheckProductOffering = "0";
        productOfferingNameArrayList.clear();
        setAdapterProductOffering();

        successAndFailedLoading();
        messageFailedApi(message);
    }

    private void setAdapterProductOffering() {
        actProductOfferingAdapter = new ArrayAdapter<ProductOfferingArrayList>(this, R.layout.item_dropdown, R.id.id_item, productOfferingNameArrayList);
        actDtProductOffering.setAdapter(actProductOfferingAdapter);
    }

    private void messageFailedApi(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreSearchSupplierMaster() {
        preLoading();
    }

    private void preLoading() {
        btnRetry.setVisibility(View.GONE);
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessSearchSupplierMaster(SupplierResponse supplierResponse) {
        this.supplierResponse = supplierResponse;
        statusAutoComplete = "supplier";
        strCheckSupplierMaster = "1";
        containerInitAutoComplete();
        /*Assign edit enable form*/
        if (!form.equalsIgnoreCase("Edit")) {
//            containerInitAutoComplete();
            if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                actDtProductSupplier.getText().clear();
                supplierNameArrayList.clear();
                setAdapterSupplierName();
                /*set Default Detail Product*/
                actDtProductMarketingSupplier.setSelectionFromPopUp(false);
                actDtProductMarketingSupplier.getText().clear();
                marketingSupplierNameArrayList.clear();
                setAdapterMarketingSupplier();

                actDtProductOffering.setSelectionFromPopUp(false);
                actDtProductOffering.getText().clear();
                productOfferingNameArrayList.clear();
                setAdapterProductOffering();

                actDtProductPos.setSelectionFromPopUp(false);
                actDtProductPos.getText().clear();
                posNameArrayList.clear();
                setAdapterPos();
                /*set Default Agunan*/
                spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                merkKendaraanNameArrayList.clear();
                setAdapterMerkKendaraan();
                merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                setAdapterMerkKendaraan();

                actTypeKendaraan.setSelectionFromPopUp(false);
                actTypeKendaraan.getText().clear();
                tipeKendaraanNameArrayList.clear();
                setAdapterTipeKendaraan();

                manufacturingYearArrayList.clear();
                setAdapterTahunProduksi();
                manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                setAdapterTahunProduksi();

                edtWarnaKendaraan.getText().clear();
                edtIsiSilinder.getText().clear();
                edtNoPolisi.getText().clear();
                edtNoRangka.getText().clear();
                edtNoMesin.getText().clear();
                edtNamaBpkbSendiri.getText().clear();
                edtNamaBpkbPasangan.getText().clear();
                edtNamaBpkbOrangLain.getText().clear();
                edtNamaBpkbSendiri.setEnabled(true);
                edtNamaBpkbPasangan.setEnabled(true);
                edtNamaBpkbOrangLain.setEnabled(true);
                rbBpkbAtasNamaSendiri.setChecked(false);
                rbBpkbAtasNamaPasangan.setChecked(false);
                rbBpkbAtasNamaOrangLain.setChecked(false);
                edtMasaBerlakuStnk.getText().clear();
                edtMasaBerlakuPajakStnk.getText().clear();
                /*set Default Data Perhitungan*/
                tenorArrayList.clear();
                setAdapterTenor();
                tenorArrayList.add(new TenorArrayList(
                        "PILIH",
                        "",
                        "",
                        "",
                        ""));
                setAdapterTenor();
                setDefaultCalculatingToZeroNotValidate();
            }
        }
        /*else {
            containerInitAutoComplete();
//            if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                setAdapterSupplierName();
                *//*set Default Detail Product*//*
                actDtProductMarketingSupplier.setSelectionFromPopUp(false);
                setAdapterMarketingSupplier();

                actDtProductOffering.setSelectionFromPopUp(false);
                setAdapterProductOffering();

                actDtProductPos.setSelectionFromPopUp(false);
                setAdapterPos();

                setAdapterMerkKendaraan();

                *//*set Default Agunan*//*
//                spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));
//
//                setAdapterMerkKendaraan();
//                merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
//                setAdapterMerkKendaraan();
//
//                actTypeKendaraan.setSelectionFromPopUp(false);
//                setAdapterTipeKendaraan();
//
//                setAdapterTahunProduksi();
//                manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
//                setAdapterTahunProduksi();
//
//                edtNamaBpkbSendiri.setEnabled(true);
//                edtNamaBpkbPasangan.setEnabled(true);
//                edtNamaBpkbOrangLain.setEnabled(true);
//                rbBpkbAtasNamaSendiri.setChecked(false);
//                rbBpkbAtasNamaPasangan.setChecked(false);
//                rbBpkbAtasNamaOrangLain.setChecked(false);

                *//*set Default Data Perhitungan*//*

//                setAdapterTenor();
//                tenorArrayList.add(new TenorArrayList(
//                        "PILIH",
//                        "",
//                        "",
//                        "",
//                        ""));
//                setAdapterTenor();
//                setDefaultCalculatingToZeroNotValidate();

//            }
        }*/


        if (statOpenForm.equalsIgnoreCase("0")) {
            showFormPengajuan();
        }
        pbMain.setVisibility(View.GONE);
        statOpenForm = "1";
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void initAutoComplete(String statusAutoComplete, SupplierResponse supplierResponse, MarketingSupplierResponse marketingSupplierResponse, ProductOfferingResponse productOfferingResponse,
                                  PosListDownResponse posListDownResponse, ProductOffTenorResponse productOffTenorResponse,
                                  JenisKendaraanResponse jenisKendaraanResponse, MerkKendaraanResponse merkKendaraanResponse,
                                  TahunProduksiResponse tahunProduksiResponse,
                                  KelurahanResponse kelurahanResponse, RecomendationResponse recomendationResponse) {
//============================================================================================================================================
//        Kota
        if (statusAutoComplete.equalsIgnoreCase("actPasanganKota")
                || statusAutoComplete.equalsIgnoreCase("actAutoAlamatPemohon")
                || statusAutoComplete.equalsIgnoreCase("actAutoKtpAlamatPemohon")
                || statusAutoComplete.equalsIgnoreCase("actAutoAlamatKerabat")
                || statusAutoComplete.equalsIgnoreCase("actAutoAlamatPekerjaan")) {
            if (statusAutoComplete.equalsIgnoreCase("actPasanganKota")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actPasanganKota.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPemohon")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoAlamatPemohon.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoKtpAlamatPemohon")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoKtpAlamatPemohon.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatKerabat")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoAlamatKerabat.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPekerjaan")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoAlamatPekerjaan.setAdapter(kelurahanAdapter);
            }

            if (kelurahanResponse != null) {
                for (int j = 0; j < kelurahanResponse.getKelurahanFilter().size(); j++) {
                    kelurahanArrayList.add(kelurahanResponse.getKelurahanFilter().get(j).getValue());
                }
                setAdapterKelurahan();
                if (statusAutoComplete.equalsIgnoreCase("actPasanganKota")) {
                    actPasanganKota.setAdapter(kelurahanAdapter);
                    actPasanganKota.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPemohon")) {
                    actAutoAlamatPemohon.setAdapter(kelurahanAdapter);
                    actAutoAlamatPemohon.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoKtpAlamatPemohon")) {
                    actAutoKtpAlamatPemohon.setAdapter(kelurahanAdapter);
                    actAutoKtpAlamatPemohon.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatKerabat")) {
                    actAutoAlamatKerabat.setAdapter(kelurahanAdapter);
                    actAutoAlamatKerabat.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPekerjaan")) {
                    actAutoAlamatPekerjaan.setAdapter(kelurahanAdapter);
                    actAutoAlamatPekerjaan.showDropDown();
                }
            }
        }
//============================================================================================================================================
        if (statusAutoComplete.equalsIgnoreCase("supplier")) {
//        Supplier
            if (supplierResponse != null) {
                supplierNameArrayList.clear();
                for (int j = 0; j < supplierResponse.getSuppliers().size(); j++) {
                    supplierNameArrayList.add(new SupplierMasterArrayList(supplierResponse.getSuppliers().get(j).getSupplierId(),
                            supplierResponse.getSuppliers().get(j).getName()));
                }
                setAdapterSupplierName();
                actDtProductSupplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Detail Product*/
                        actDtProductMarketingSupplier.setSelectionFromPopUp(false);
                        actDtProductMarketingSupplier.getText().clear();
                        marketingSupplierNameArrayList.clear();
                        setAdapterMarketingSupplier();

                        actDtProductOffering.setSelectionFromPopUp(false);
                        actDtProductOffering.getText().clear();
                        productOfferingNameArrayList.clear();
                        setAdapterProductOffering();

                        actDtProductPos.setSelectionFromPopUp(false);
                        actDtProductPos.getText().clear();
                        posNameArrayList.clear();
                        setAdapterPos();
                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        supplierKode = actSupplierAdapter.getItem(i).getSupplierID();
                        mSearchMarketingSupplierPresenter.getSearchMarketingSupplier(token, supplierKode, aoBranch, assetTypeId);
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("marketingSupplier")) {
//        Marketing Supplier
            if (marketingSupplierResponse != null) {
                for (int j = 0; j < marketingSupplierResponse.getMarketingSupplierList().size(); j++) {
                    marketingSupplierNameArrayList.add(new MarketingSupplierArrayList(marketingSupplierResponse.getMarketingSupplierList().get(j).getSupplierEmployeeID(),
                            marketingSupplierResponse.getMarketingSupplierList().get(j).getSupplierEmployeeName()));
                }
                setAdapterMarketingSupplier();
                actDtProductMarketingSupplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Detail Product*/
                        actDtProductOffering.setSelectionFromPopUp(false);
                        actDtProductOffering.getText().clear();
                        productOfferingNameArrayList.clear();
                        setAdapterProductOffering();

                        actDtProductPos.setSelectionFromPopUp(false);
                        actDtProductPos.getText().clear();
                        posNameArrayList.clear();
                        setAdapterPos();
                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        marketingKode = actMarketingSupplierAdapter.getItem(i).getSupplierEmployeeID();
                        if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA")) {
                            statTipePengajuan = "1";
                        } else {
                            statTipePengajuan = "2";
                        }
                        mSearchProductOfferingPresenter.getSearchProductOffering(token, "", assetTypeId, supplierKode, aoBranch, statusKreditmu, statTipePengajuan, AOSalesStatus); /*product offering*/
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("productOffering")) {
            //        Product Offering
            if (productOfferingResponse != null) {
                for (int j = 0; j < productOfferingResponse.getProductOfferings().size(); j++) {
                    productOfferingNameArrayList.add(new ProductOfferingArrayList(productOfferingResponse.getProductOfferings().get(j).getProductID(),
                            productOfferingResponse.getProductOfferings().get(j).getProductOfferingID(),
                            productOfferingResponse.getProductOfferings().get(j).getProductOfferingIDDescription()));
                }
                setAdapterProductOffering();
                actDtProductOffering.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Detail Product*/
                        actDtProductPos.setSelectionFromPopUp(false);
                        actDtProductPos.getText().clear();
                        posNameArrayList.clear();
                        setAdapterPos();

                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        productId = actProductOfferingAdapter.getItem(i).getProductID();
                        productOfferingId = actProductOfferingAdapter.getItem(i).getProductOfferingID();
                        mCekKodeProgramPresenter.checkKodeProgram(token, productOfferingId);
                        mPosPresenter.getPosMaster(token, aoBranch);

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("pos")) {
            //        pos
            if (posListDownResponse != null) {
                for (int j = 0; j < posListDownResponse.getPosMasters().size(); j++) {
                    posNameArrayList.add(new PosArrayList(posListDownResponse.getPosMasters().get(j).getPOSID(),
                            posListDownResponse.getPosMasters().get(j).getPOSName()));
                }
                setAdapterPos();
                actDtProductPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        posKode = posAdapter.getItem(i).getPOSID();
                        mProductOffTenorPresenter.getProductOffTenor(token, productOfferingId, aoBranch);
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("tenor")) {
            if (productOffTenorResponse != null) {
                tenorArrayList.add(new TenorArrayList(
                        "PILIH",
                        "",
                        "",
                        "",
                        ""));
                for (int j = 0; j < productOffTenorResponse.getProductOfTenorObjts().size(); j++) {
                    tenorArrayList.add(new TenorArrayList(
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getTenor(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getAdminFee(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getNtf(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getEffectiveRate(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getdPPercentage()));
                }
                setAdapterTenor();
                spnTenorPerhitunganKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        if (!form.equalsIgnoreCase("Edit")) {
                            if (!spnTenorPerhitunganKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                                setDefaultCalculatingToZeroNotValidateTenor();
                                strDPPercentage = productOffTenorAdapter.getItem(i).getDPPercentage();
                                strEffectiveRate = productOffTenorAdapter.getItem(i).getEffectiveRate();
                                intMinBiayaAdminPerhitunganKendaraan = Integer.parseInt(productOffTenorAdapter.getItem(i).getAdminFee());
                                edtBiayaAdminPerhitunganKendaraan.setText(productOffTenorAdapter.getItem(i).getAdminFee());
                                intMinPokokPembiayaan = Integer.parseInt(productOffTenorAdapter.getItem(i).getNtf());
                                ///////ini tambahin default
                                edtPokokPembiayaan.setText(perhitunganPokokPembiayaan);
                                edtAngsuranPerbulanPerhitunganKendaraan.setText("0");
                                statFocusFieldPokokPembiayaan = "1";
                                statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "1";
                                statFocusFieldBiayaAdminPerhitunganKendaraan = "1";
                                isSelectedTenor = true;

                                hitPerhitunganKendaraan("spnTenorPerhitunganKendaraan", "0", strEffectiveRate, strDPPercentage);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("jenisKendaraan")) {
//        Jenis Kendaraan
            if (jenisKendaraanResponse != null) {
                jenisKendaraan.add(new JenisKendaraanArrayList("", "PILIH"));
                for (int j = 0; j < jenisKendaraanResponse.getAssetCategoryMasterSyncList().size(); j++) {
                    jenisKendaraan.add(new JenisKendaraanArrayList(jenisKendaraanResponse.getAssetCategoryMasterSyncList().get(j).getCategoryID(),
                            jenisKendaraanResponse.getAssetCategoryMasterSyncList().get(j).getDescription()));
                }
                setAdapterJenisKendaraan();
                spnJenisKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        if (!form.equalsIgnoreCase("Edit")) {
                            /*set Default Agunan*/
                            merkKendaraanNameArrayList.clear();
                            setAdapterMerkKendaraan();
                            merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                            setAdapterMerkKendaraan();

                            actTypeKendaraan.setSelectionFromPopUp(false);
                            actTypeKendaraan.getText().clear();
                            tipeKendaraanNameArrayList.clear();
                            setAdapterTipeKendaraan();

                            manufacturingYearArrayList.clear();
                            setAdapterTahunProduksi();
                            manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                            setAdapterTahunProduksi();

                            edtWarnaKendaraan.getText().clear();
                            edtIsiSilinder.getText().clear();
                            edtNoPolisi.getText().clear();
                            edtNoRangka.getText().clear();
                            edtNoMesin.getText().clear();
                            edtNamaBpkbSendiri.getText().clear();
                            edtNamaBpkbPasangan.getText().clear();
                            edtNamaBpkbOrangLain.getText().clear();
                           /* edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);*/
                            edtMasaBerlakuStnk.getText().clear();
                            edtMasaBerlakuPajakStnk.getText().clear();
                            rgBpkbAtasNama.clearCheck();
                            /*set Default Data Perhitungan*/
                            setDefaultCalculatingToZeroNotValidate();

                            idJenisKendaraan = actJenisKendaraanAdapter.getItem(i).getCategoryID();
                            namaJenisKendaraan = actJenisKendaraanAdapter.getItem(i).getDescription();
                            if (!spnJenisKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                                mPilihKendaraanPresenter.GetPilihKendaraan(token, assetTypeId, "1", idJenisKendaraan, "", aoBranch);  /*mencari merk kendaraan*/
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("merkKendaraan")) {
//        Merk Kendaraan
            if (merkKendaraanResponse != null) {
                merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                for (int j = 0; j < merkKendaraanResponse.getAssetMasterFilterList().size(); j++) {
                    merkKendaraanNameArrayList.add(new MerkKendaraanArrayList(merkKendaraanResponse.getAssetMasterFilterList().get(j).getMerk()));
                }
                setAdapterMerkKendaraan();
                spnMerkKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        if (!form.equalsIgnoreCase("Edit")) {
                            /*set Default Agunan*/
                            actTypeKendaraan.setSelectionFromPopUp(false);
                            actTypeKendaraan.getText().clear();
                            tipeKendaraanNameArrayList.clear();
                            setAdapterTipeKendaraan();

                            manufacturingYearArrayList.clear();
                            setAdapterTahunProduksi();
                            manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                            setAdapterTahunProduksi();

                            edtWarnaKendaraan.getText().clear();
                            edtIsiSilinder.getText().clear();
                            edtNoPolisi.getText().clear();
                            edtNoRangka.getText().clear();
                            edtNoMesin.getText().clear();
                            edtNamaBpkbSendiri.getText().clear();
                            edtNamaBpkbPasangan.getText().clear();
                            edtNamaBpkbOrangLain.getText().clear();
                            /*edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);
                            rgBpkbAtasNama.setEnabled(true);*/
                            edtMasaBerlakuStnk.getText().clear();
                            edtMasaBerlakuPajakStnk.getText().clear();
                            rgBpkbAtasNama.clearCheck();
                            /*set Default Data Perhitungan*/
                            setDefaultCalculatingToZeroNotValidate();

                            idMerkKendaraan = actMmerkKendaraanAdapter.getItem(i).getMerk();
                            namaMerkKendaraan = actMmerkKendaraanAdapter.getItem(i).getMerk();
                            if (!spnMerkKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                                mPilihKendaraanPresenter.GetPilihKendaraan(token, assetTypeId, "3", idJenisKendaraan, idMerkKendaraan, aoBranch); /* mencari tipe kendaraan*/
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("tipeKendaraan")) {
//        Tipe kendaraan
            if (merkKendaraanResponse != null) {
                for (int j = 0; j < merkKendaraanResponse.getAssetMasterFilterList().size(); j++) {
                    tipeKendaraanNameArrayList.add(new TipeKendaraanArrayList(merkKendaraanResponse.getAssetMasterFilterList().get(j).getAssetCode(),
                            merkKendaraanResponse.getAssetMasterFilterList().get(j).getDescription()));
                }
                setAdapterTipeKendaraan();
                actTypeKendaraan.showDropDown();
                actTypeKendaraan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (!form.equalsIgnoreCase("Edit")) {
                            /*set Default Agunan*/
                            manufacturingYearArrayList.clear();
                            setAdapterTahunProduksi();
                            manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                            setAdapterTahunProduksi();

                            edtWarnaKendaraan.getText().clear();
                            edtIsiSilinder.getText().clear();
                            edtNoPolisi.getText().clear();
                            edtNoRangka.getText().clear();
                            edtNoMesin.getText().clear();
                            edtNamaBpkbSendiri.getText().clear();
                            edtNamaBpkbPasangan.getText().clear();
                            edtNamaBpkbOrangLain.getText().clear();
                            /*edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);*/
                            edtMasaBerlakuStnk.getText().clear();
                            edtMasaBerlakuPajakStnk.getText().clear();
                            rgBpkbAtasNama.clearCheck();
                            /*set Default Data Perhitungan*/
                            setDefaultCalculatingToZeroNotValidate();

                            idTipeKendaraan = actTipeKendaraanAdapter.getItem(i).getAssetCode();

                            mTahunProduksiKendaraanPresenter.GetTahunKendaraan(token, idTipeKendaraan, aoBranch);
                        }
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("tahunKendaraan")) {
//        Tahun Kendaraan
            if (tahunProduksiResponse != null) {
                manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                for (int j = 0; j < tahunProduksiResponse.getTahunKendaraanObjts().size(); j++) {
                    manufacturingYearArrayList.add(new TahunKendaraanArrayList(tahunProduksiResponse.getTahunKendaraanObjts().get(j).getManufacturingYear()));
                }
                setAdapterTahunProduksi();
                spnTahunKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        if (!form.equalsIgnoreCase("Edit")) {
                            /*set Default Agunan*/
                            edtWarnaKendaraan.getText().clear();
                            edtIsiSilinder.getText().clear();
                            edtNoPolisi.getText().clear();
                            edtNoRangka.getText().clear();
                            edtNoMesin.getText().clear();
                            edtNamaBpkbSendiri.getText().clear();
                            edtNamaBpkbPasangan.getText().clear();
                            edtNamaBpkbOrangLain.getText().clear();
                            /*edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);*/
                            rgBpkbAtasNama.clearCheck();
                            edtMasaBerlakuStnk.getText().clear();
                            edtMasaBerlakuPajakStnk.getText().clear();

                            /*set Default Data Perhitungan*/
                            setDefaultCalculatingToZeroNotValidate();

                            strTahunKendaraan = actMmanufacturingYearAdapter.getItem(i).getManufacturingYear();
                            if (!spnTahunKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                                mHargaAgunanKendaraanPresenter.GetHargaAgunanKendaraan(token, aoBranch, idTipeKendaraan, strTahunKendaraan);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }

//        perusahaan profesi, job tipe, job position, industri
//        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        List<Profession> labels = databaseService.getAllProfession();
        final ArrayAdapter<Profession> professionArrayAdapter = new ArrayAdapter<Profession>(getContext(), R.layout.item_dropdown, R.id.id_item, labels);
        actProfesiPerusahaan.setAdapter(professionArrayAdapter);
        actProfesiPerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                professionKode = professionArrayAdapter.getItem(position).getId();

                actJobTypePerusahaan.getText().clear();
                actJobPositionPerusahaan.getText().clear();

                if (actProfesiPerusahaan.getText().toString().equalsIgnoreCase("WIRASWASTA")) {
                    lnDataPekerjaanPhone.setVisibility(View.GONE);
                    edtAreaPhonePerusahaan.setText("");
//                    edtPhonePerusahaan.setVisibility(View.VISIBLE);
                    edtPekerjaanNoHp.setVisibility(View.VISIBLE);

                    edtPhonePerusahaan.setText("");
                    edtPekerjaanNoHp.setText("");

                    validator.removeRules(edtAreaPhonePerusahaan);
                    validator.removeRules(edtPhonePerusahaan);

                    tilPekerjaanNoHp.setVisibility(View.VISIBLE);
                    edtPekerjaanNoHp.setVisibility(View.VISIBLE);
                    validator.put(edtPekerjaanNoHp, noHpRule);
                } else {
                    lnDataPekerjaanPhone.setVisibility(View.VISIBLE);
                    validator.put(edtAreaPhonePerusahaan, kodeAreaRule);
                    validator.put(edtPhonePerusahaan, notEmptyRule);
                    validator.removeRules(edtPekerjaanNoHp);
                    edtPekerjaanNoHp.setText("");
                    tilPekerjaanNoHp.setVisibility(View.GONE);
                    edtPekerjaanNoHp.setVisibility(View.GONE);
                }

                List<JobType> labeljob = databaseService.getAllJobType(professionKode);
                jobTypeArrayAdapter = new ArrayAdapter<JobType>(getContext(), R.layout.item_dropdown, R.id.id_item, labeljob);
                actJobTypePerusahaan.setAdapter(jobTypeArrayAdapter);
            }
        });

        actJobTypePerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jobTypeKode = jobTypeArrayAdapter.getItem(position).getJobTypeID();

                actJobPositionPerusahaan.getText().clear();

                List<JobPosition> labelposition = databaseService.getAllJObPosition(jobTypeKode);
                jobPositionArrayAdapter = new ArrayAdapter<JobPosition>(getContext(), R.layout.item_dropdown, R.id.id_item, labelposition);
                actJobPositionPerusahaan.setAdapter(jobPositionArrayAdapter);
            }
        });

        actJobPositionPerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jobPositionKode = jobPositionArrayAdapter.getItem(position).getJobPositionID();
            }
        });

        List<Industri> labelin = databaseService.getAllIndustry();
        final ArrayAdapter<Industri> industriArrayAdapter = new ArrayAdapter<Industri>(getContext(), R.layout.item_dropdown, R.id.id_item, labelin);
        actIndustriPerusahaan.setAdapter(industriArrayAdapter);
        actIndustriPerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                industriKode = industriArrayAdapter.getItem(position).getId();
            }
        });

        //        pasangan profesi, job tipe, job position, industri
//        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        List<Profession> listPasanganProfesi = databaseService.getAllProfession();
        final ArrayAdapter<Profession> pasanganProfesiAdapter = new ArrayAdapter<Profession>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganProfesi);
        actPasanganProfesi.setAdapter(pasanganProfesiAdapter);
        actPasanganProfesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganProfesi = pasanganProfesiAdapter.getItem(position).getId();

                actPasanganJobType.getText().clear();
                actPasanganJobPosition.getText().clear();

                /*if (actPasanganProfesi.getText().toString().equalsIgnoreCase("WIRASWASTA")) {
                    lnPasanganNoTeleponRumah.setVisibility(View.VISIBLE);
//                    edtPasanganKodeAreaTeleponRumah.setText("");
//                    edtPasanganNoTeleponRumah.setText("");
//                    edtPasanganNoHp.setText("");
                    validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
                    validator.put(edtPasanganNoTeleponRumah, notEmptyRule);
//                    tilPasanganNoHp.setVisibility(View.VISIBLE);
//                    edtPasanganNoHp.setVisibility(View.VISIBLE);
                    validator.put(edtPasanganNoHp, noHpRule);
                } else {
                    lnPasanganNoTeleponRumah.setVisibility(View.VISIBLE);
                    validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
                    validator.put(edtPasanganNoTeleponRumah, notEmptyRule);
                    validator.removeRules(edtPasanganNoHp);
//                    edtPasanganNoHp.setText("");
                    validator.put(edtPasanganNoHp, noHpRule);
//                    tilPasanganNoHp.setVisibility(View.GONE);
//                    edtPasanganNoHp.setVisibility(View.GONE);
                }*/


                if(actPasanganProfesi.getText().toString().equalsIgnoreCase("WIRASWASTA") || actPasanganProfesi.getText().toString().equalsIgnoreCase("PROFESSIONAL")){
                    lnPasanganStatus.setVisibility(View.GONE);
                }else{
                    lnPasanganStatus.setVisibility(View.VISIBLE);
                }

                lnPasanganNoTeleponRumah.setVisibility(View.VISIBLE);
                validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
                validator.put(edtPasanganNoTeleponRumah, notEmptyRule);
                validator.put(edtPasanganNoHp, noHpRule);

                List<JobType> listPasanganJobType = databaseService.getAllJobType(kodePasanganProfesi);
                pasanganJobTypeAdapter = new ArrayAdapter<JobType>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganJobType);
                actPasanganJobType.setAdapter(pasanganJobTypeAdapter);
            }
        });

        actPasanganJobType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganJobType = pasanganJobTypeAdapter.getItem(position).getJobTypeID();

                actPasanganJobPosition.getText().clear();

                List<JobPosition> listPasanganJobPosition = databaseService.getAllJObPosition(kodePasanganJobType);
                pasanganJobPositionAdapter = new ArrayAdapter<JobPosition>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganJobPosition);
                actPasanganJobPosition.setAdapter(pasanganJobPositionAdapter);
            }
        });

        actPasanganJobPosition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganJobPosition = pasanganJobPositionAdapter.getItem(position).getJobPositionID();
            }
        });

        List<Industri> listPasanganIndustri = databaseService.getAllIndustry();
        final ArrayAdapter<Industri> pasanganIndustriAdapter = new ArrayAdapter<Industri>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganIndustri);
        actPasanganIndustri.setAdapter(pasanganIndustriAdapter);
        actPasanganIndustri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganIndustri = pasanganIndustriAdapter.getItem(position).getId();
            }
        });
//        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if (statusAutoComplete.equalsIgnoreCase("rekomendasi")) {
            if (recomendationResponse != null) {
                for (int j = 0; j < recomendationResponse.getRecomendationObjtList().size(); j++) {
                    rekomendasiArrayList.add(new RekomendasiArrayList(recomendationResponse.getRecomendationObjtList().get(j).getId(),
                            recomendationResponse.getRecomendationObjtList().get(j).getRecomendation()));
                }
                final ArrayAdapter<RekomendasiArrayList> rekomendasiAdapter = new ArrayAdapter<RekomendasiArrayList>(this, R.layout.item_dropdown, R.id.id_item, rekomendasiArrayList);
                spnRecomendation.setAdapter(rekomendasiAdapter);
                spnRecomendation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        recomendationId = rekomendasiAdapter.getItem(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }

    private void initAutoCompleteAssignEdit(String statusAutoComplete, SupplierResponse supplierResponse, MarketingSupplierResponse marketingSupplierResponse, ProductOfferingResponse productOfferingResponse,
                                            PosListDownResponse posListDownResponse, ProductOffTenorResponse productOffTenorResponse,
                                            JenisKendaraanResponse jenisKendaraanResponse, MerkKendaraanResponse merkKendaraanResponse,
                                            TahunProduksiResponse tahunProduksiResponse,
                                            KelurahanResponse kelurahanResponse, RecomendationResponse recomendationResponse) {
//============================================================================================================================================
//        Kota
        if (statusAutoComplete.equalsIgnoreCase("actPasanganKota")
                || statusAutoComplete.equalsIgnoreCase("actAutoAlamatPemohon")
                || statusAutoComplete.equalsIgnoreCase("actAutoKtpAlamatPemohon")
                || statusAutoComplete.equalsIgnoreCase("actAutoAlamatKerabat")
                || statusAutoComplete.equalsIgnoreCase("actAutoAlamatPekerjaan")) {
            if (statusAutoComplete.equalsIgnoreCase("actPasanganKota")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actPasanganKota.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPemohon")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoAlamatPemohon.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoKtpAlamatPemohon")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoKtpAlamatPemohon.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatKerabat")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoAlamatKerabat.setAdapter(kelurahanAdapter);
            }
            if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPekerjaan")) {
                kelurahanArrayList.clear();
                setAdapterKelurahan();
                actAutoAlamatPekerjaan.setAdapter(kelurahanAdapter);
            }

            if (kelurahanResponse != null) {
                for (int j = 0; j < kelurahanResponse.getKelurahanFilter().size(); j++) {
                    kelurahanArrayList.add(kelurahanResponse.getKelurahanFilter().get(j).getValue());
                }
                setAdapterKelurahan();
                if (statusAutoComplete.equalsIgnoreCase("actPasanganKota")) {
                    actPasanganKota.setAdapter(kelurahanAdapter);
                    actPasanganKota.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPemohon")) {
                    actAutoAlamatPemohon.setAdapter(kelurahanAdapter);
                    actAutoAlamatPemohon.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoKtpAlamatPemohon")) {
                    actAutoKtpAlamatPemohon.setAdapter(kelurahanAdapter);
                    actAutoKtpAlamatPemohon.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatKerabat")) {
                    actAutoAlamatKerabat.setAdapter(kelurahanAdapter);
                    actAutoAlamatKerabat.showDropDown();
                }
                if (statusAutoComplete.equalsIgnoreCase("actAutoAlamatPekerjaan")) {
                    actAutoAlamatPekerjaan.setAdapter(kelurahanAdapter);
                    actAutoAlamatPekerjaan.showDropDown();
                }
            }
        }
//============================================================================================================================================
        if (statusAutoComplete.equalsIgnoreCase("supplier")) {
//        Supplier
            if (supplierResponse != null) {
                supplierNameArrayList.clear();
                for (int j = 0; j < supplierResponse.getSuppliers().size(); j++) {
                    supplierNameArrayList.add(new SupplierMasterArrayList(supplierResponse.getSuppliers().get(j).getSupplierId(),
                            supplierResponse.getSuppliers().get(j).getName()));
                }
                setAdapterSupplierName();
                actDtProductSupplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Detail Product*/
                        actDtProductMarketingSupplier.setSelectionFromPopUp(false);
                        actDtProductMarketingSupplier.getText().clear();
                        marketingSupplierNameArrayList.clear();
                        setAdapterMarketingSupplier();

                        actDtProductOffering.setSelectionFromPopUp(false);
                        actDtProductOffering.getText().clear();
                        productOfferingNameArrayList.clear();
                        setAdapterProductOffering();

                        actDtProductPos.setSelectionFromPopUp(false);
                        actDtProductPos.getText().clear();
                        posNameArrayList.clear();
                        setAdapterPos();
                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        supplierKode = actSupplierAdapter.getItem(i).getSupplierID();
                        mSearchMarketingSupplierPresenter.getSearchMarketingSupplier(token, supplierKode, aoBranch, assetTypeId);
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("marketingSupplier")) {
//        Marketing Supplier
            if (marketingSupplierResponse != null) {
                for (int j = 0; j < marketingSupplierResponse.getMarketingSupplierList().size(); j++) {
                    marketingSupplierNameArrayList.add(new MarketingSupplierArrayList(marketingSupplierResponse.getMarketingSupplierList().get(j).getSupplierEmployeeID(),
                            marketingSupplierResponse.getMarketingSupplierList().get(j).getSupplierEmployeeName()));
                }
                setAdapterMarketingSupplier();
                actDtProductMarketingSupplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Detail Product*/
                        actDtProductOffering.setSelectionFromPopUp(false);
                        actDtProductOffering.getText().clear();
                        productOfferingNameArrayList.clear();
                        setAdapterProductOffering();

                        actDtProductPos.setSelectionFromPopUp(false);
                        actDtProductPos.getText().clear();
                        posNameArrayList.clear();
                        setAdapterPos();
                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        marketingKode = actMarketingSupplierAdapter.getItem(i).getSupplierEmployeeID();
                        if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA")) {
                            statTipePengajuan = "1";
                        } else {
                            statTipePengajuan = "2";
                        }
                        mSearchProductOfferingPresenter.getSearchProductOffering(token, "", assetTypeId, supplierKode, aoBranch, statusKreditmu, statTipePengajuan, AOSalesStatus); /*product offering*/
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("productOffering")) {
            //        Product Offering
            if (productOfferingResponse != null) {
                for (int j = 0; j < productOfferingResponse.getProductOfferings().size(); j++) {
                    productOfferingNameArrayList.add(new ProductOfferingArrayList(productOfferingResponse.getProductOfferings().get(j).getProductID(),
                            productOfferingResponse.getProductOfferings().get(j).getProductOfferingID(),
                            productOfferingResponse.getProductOfferings().get(j).getProductOfferingIDDescription()));
                }
                setAdapterProductOffering();
                actDtProductOffering.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Detail Product*/
                        actDtProductPos.setSelectionFromPopUp(false);
                        actDtProductPos.getText().clear();
                        posNameArrayList.clear();
                        setAdapterPos();

                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        productId = actProductOfferingAdapter.getItem(i).getProductID();
                        productOfferingId = actProductOfferingAdapter.getItem(i).getProductOfferingID();
                        mCekKodeProgramPresenter.checkKodeProgram(token, productOfferingId);
                        mPosPresenter.getPosMaster(token, aoBranch);

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("pos")) {
            //        pos
            if (posListDownResponse != null) {
                for (int j = 0; j < posListDownResponse.getPosMasters().size(); j++) {
                    posNameArrayList.add(new PosArrayList(posListDownResponse.getPosMasters().get(j).getPOSID(),
                            posListDownResponse.getPosMasters().get(j).getPOSName()));
                }
                setAdapterPos();
                actDtProductPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        /*set Default Agunan*/
                        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                        edtNamaBpkbSendiri.setEnabled(true);
                        edtNamaBpkbPasangan.setEnabled(true);
                        edtNamaBpkbOrangLain.setEnabled(true);
                        rbBpkbAtasNamaSendiri.setChecked(false);
                        rbBpkbAtasNamaPasangan.setChecked(false);
                        rbBpkbAtasNamaOrangLain.setChecked(false);
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        /*set Default Data Perhitungan*/
                        tenorArrayList.clear();
                        setAdapterTenor();
                        tenorArrayList.add(new TenorArrayList(
                                "PILIH",
                                "",
                                "",
                                "",
                                ""));
                        setAdapterTenor();
                        setDefaultCalculatingToZeroNotValidate();

                        posKode = posAdapter.getItem(i).getPOSID();
                        mProductOffTenorPresenter.getProductOffTenor(token, productOfferingId, aoBranch);
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("tenor")) {
            if (productOffTenorResponse != null) {
                tenorArrayList.add(new TenorArrayList(
                        "PILIH",
                        "",
                        "",
                        "",
                        ""));
                for (int j = 0; j < productOffTenorResponse.getProductOfTenorObjts().size(); j++) {
                    tenorArrayList.add(new TenorArrayList(
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getTenor(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getAdminFee(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getNtf(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getEffectiveRate(),
                            productOffTenorResponse.getProductOfTenorObjts().get(j).getdPPercentage()));
                }
                setAdapterTenor();
                spnTenorPerhitunganKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
//                        if (!form.equalsIgnoreCase("Edit")) {
                        if (!spnTenorPerhitunganKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                            setDefaultCalculatingToZeroNotValidateTenor();
                            strDPPercentage = productOffTenorAdapter.getItem(i).getDPPercentage();
                            strEffectiveRate = productOffTenorAdapter.getItem(i).getEffectiveRate();
                            intMinBiayaAdminPerhitunganKendaraan = Integer.parseInt(productOffTenorAdapter.getItem(i).getAdminFee());
                            edtBiayaAdminPerhitunganKendaraan.setText(productOffTenorAdapter.getItem(i).getAdminFee());
                            intMinPokokPembiayaan = Integer.parseInt(productOffTenorAdapter.getItem(i).getNtf());
                            ///////ini tambahin default
                            edtPokokPembiayaan.setText(perhitunganPokokPembiayaan);
                            edtAngsuranPerbulanPerhitunganKendaraan.setText("0");
                            statFocusFieldPokokPembiayaan = "1";
                            statFocusFieldAngsuranPerbulanPerhitunganKendaraan = "1";
                            statFocusFieldBiayaAdminPerhitunganKendaraan = "1";
                            isSelectedTenor = true;

//                                Toast.makeText(FormPengajuanNonElcActivity.this, "tenor masuk", Toast.LENGTH_SHORT).show();
                            hitPerhitunganKendaraan("spnTenorPerhitunganKendaraan", "0", strEffectiveRate, strDPPercentage);
                        }
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("jenisKendaraan")) {
//        Jenis Kendaraan
            if (jenisKendaraanResponse != null) {
                jenisKendaraan.clear();
                Log.d("jenis kendaraan : ", jenisKendaraan.toString());
                jenisKendaraan.add(new JenisKendaraanArrayList("", "PILIH"));
                for (int j = 0; j < jenisKendaraanResponse.getAssetCategoryMasterSyncList().size(); j++) {
                    jenisKendaraan.add(new JenisKendaraanArrayList(jenisKendaraanResponse.getAssetCategoryMasterSyncList().get(j).getCategoryID(),
                            jenisKendaraanResponse.getAssetCategoryMasterSyncList().get(j).getDescription()));
                }
                setAdapterJenisKendaraan();
                spnJenisKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
//                        if (!form.equalsIgnoreCase("Edit")) {
                        /*set Default Agunan*/
                        merkKendaraanNameArrayList.clear();
                        setAdapterMerkKendaraan();
                        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                        setAdapterMerkKendaraan();

                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                           /* edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);*/
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        rgBpkbAtasNama.clearCheck();
                        /*set Default Data Perhitungan*/
                        setDefaultCalculatingToZeroNotValidate();

                        idJenisKendaraan = actJenisKendaraanAdapter.getItem(i).getCategoryID();
                        namaJenisKendaraan = actJenisKendaraanAdapter.getItem(i).getDescription();
//                        Toast.makeText(FormPengajuanNonElcActivity.this, spnJenisKendaraan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        if (!spnJenisKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                            mPilihKendaraanPresenter.GetPilihKendaraan(token, assetTypeId, "1", idJenisKendaraan, "", aoBranch);  /*mencari merk kendaraan*/
                        }
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("merkKendaraan")) {
//        Merk Kendaraan
            if (merkKendaraanResponse != null) {
                merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
                for (int j = 0; j < merkKendaraanResponse.getAssetMasterFilterList().size(); j++) {
                    merkKendaraanNameArrayList.add(new MerkKendaraanArrayList(merkKendaraanResponse.getAssetMasterFilterList().get(j).getMerk()));
                }
                setAdapterMerkKendaraan();
                spnMerkKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
//                        if (!form.equalsIgnoreCase("Edit")) {
                        /*set Default Agunan*/
                        actTypeKendaraan.setSelectionFromPopUp(false);
                        actTypeKendaraan.getText().clear();
                        tipeKendaraanNameArrayList.clear();
                        setAdapterTipeKendaraan();

                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                            /*edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);
                            rgBpkbAtasNama.setEnabled(true);*/
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        rgBpkbAtasNama.clearCheck();
                        /*set Default Data Perhitungan*/
                        setDefaultCalculatingToZeroNotValidate();

                        idMerkKendaraan = actMmerkKendaraanAdapter.getItem(i).getMerk();
                        namaMerkKendaraan = actMmerkKendaraanAdapter.getItem(i).getMerk();
                        if (!spnMerkKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {
                            mPilihKendaraanPresenter.GetPilihKendaraan(token, assetTypeId, "3", idJenisKendaraan, idMerkKendaraan, aoBranch); /* mencari tipe kendaraan*/
                        }
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("tipeKendaraan")) {
//        Tipe kendaraan
            if (merkKendaraanResponse != null) {
                for (int j = 0; j < merkKendaraanResponse.getAssetMasterFilterList().size(); j++) {
                    tipeKendaraanNameArrayList.add(new TipeKendaraanArrayList(merkKendaraanResponse.getAssetMasterFilterList().get(j).getAssetCode(),
                            merkKendaraanResponse.getAssetMasterFilterList().get(j).getDescription()));
                }
                setAdapterTipeKendaraan();
                actTypeKendaraan.showDropDown();
                actTypeKendaraan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        if (!form.equalsIgnoreCase("Edit")) {
                        /*set Default Agunan*/
                        manufacturingYearArrayList.clear();
                        setAdapterTahunProduksi();
                        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                        setAdapterTahunProduksi();

                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                            /*edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);*/
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();
                        rgBpkbAtasNama.clearCheck();
                        /*set Default Data Perhitungan*/
                        setDefaultCalculatingToZeroNotValidate();

                        idTipeKendaraan = actTipeKendaraanAdapter.getItem(i).getAssetCode();

                        mTahunProduksiKendaraanPresenter.GetTahunKendaraan(token, idTipeKendaraan, aoBranch);
//                        }
                    }
                });
            }
        }

        if (statusAutoComplete.equalsIgnoreCase("tahunKendaraan")) {
//        Tahun Kendaraan
            if (tahunProduksiResponse != null) {
                manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
                for (int j = 0; j < tahunProduksiResponse.getTahunKendaraanObjts().size(); j++) {
                    manufacturingYearArrayList.add(new TahunKendaraanArrayList(tahunProduksiResponse.getTahunKendaraanObjts().get(j).getManufacturingYear()));
                }
                setAdapterTahunProduksi();
                spnTahunKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
//                        if (!form.equalsIgnoreCase("Edit")) {
                        /*set Default Agunan*/
                        edtWarnaKendaraan.getText().clear();
                        edtIsiSilinder.getText().clear();
                        edtNoPolisi.getText().clear();
                        edtNoRangka.getText().clear();
                        edtNoMesin.getText().clear();
                        edtNamaBpkbSendiri.getText().clear();
                        edtNamaBpkbPasangan.getText().clear();
                        edtNamaBpkbOrangLain.getText().clear();
                            /*edtNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            rbBpkbAtasNamaSendiri.setChecked(false);
                            rbBpkbAtasNamaPasangan.setChecked(false);
                            rbBpkbAtasNamaOrangLain.setChecked(false);*/
                        rgBpkbAtasNama.clearCheck();
                        edtMasaBerlakuStnk.getText().clear();
                        edtMasaBerlakuPajakStnk.getText().clear();

                        /*set Default Data Perhitungan*/
                        setDefaultCalculatingToZeroNotValidate();

                        strTahunKendaraan = actMmanufacturingYearAdapter.getItem(i).getManufacturingYear();
//                            Toast.makeText(FormPengajuanNonElcActivity.this, spnTahunKendaraan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        if (spnTahunKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH")) {

                        } else {
                            mHargaAgunanKendaraanPresenter.GetHargaAgunanKendaraan(token, aoBranch, idTipeKendaraan, strTahunKendaraan);
                        }
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }

//        perusahaan profesi, job tipe, job position, industri
//        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        List<Profession> labels = databaseService.getAllProfession();
        final ArrayAdapter<Profession> professionArrayAdapter = new ArrayAdapter<Profession>(getContext(), R.layout.item_dropdown, R.id.id_item, labels);
        actProfesiPerusahaan.setAdapter(professionArrayAdapter);
        actProfesiPerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                professionKode = professionArrayAdapter.getItem(position).getId();

                actJobTypePerusahaan.getText().clear();
                actJobPositionPerusahaan.getText().clear();

                if (actProfesiPerusahaan.getText().toString().equalsIgnoreCase("WIRASWASTA")) {
                    lnDataPekerjaanPhone.setVisibility(View.GONE);
                    edtAreaPhonePerusahaan.setText("");
//                    edtPhonePerusahaan.setVisibility(View.VISIBLE);
                    edtPekerjaanNoHp.setVisibility(View.VISIBLE);

                    edtPhonePerusahaan.setText("");
                    edtPekerjaanNoHp.setText("");

                    validator.removeRules(edtAreaPhonePerusahaan);
                    validator.removeRules(edtPhonePerusahaan);

                    tilPekerjaanNoHp.setVisibility(View.VISIBLE);
                    edtPekerjaanNoHp.setVisibility(View.VISIBLE);
                    validator.put(edtPekerjaanNoHp, noHpRule);
                } else {
                    lnDataPekerjaanPhone.setVisibility(View.VISIBLE);
                    validator.put(edtAreaPhonePerusahaan, kodeAreaRule);
                    validator.put(edtPhonePerusahaan, notEmptyRule);
                    validator.removeRules(edtPekerjaanNoHp);
                    edtPekerjaanNoHp.setText("");
                    tilPekerjaanNoHp.setVisibility(View.GONE);
                    edtPekerjaanNoHp.setVisibility(View.GONE);
                }

                List<JobType> labeljob = databaseService.getAllJobType(professionKode);
                jobTypeArrayAdapter = new ArrayAdapter<JobType>(getContext(), R.layout.item_dropdown, R.id.id_item, labeljob);
                actJobTypePerusahaan.setAdapter(jobTypeArrayAdapter);
            }
        });

        actJobTypePerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jobTypeKode = jobTypeArrayAdapter.getItem(position).getJobTypeID();

                actJobPositionPerusahaan.getText().clear();

                List<JobPosition> labelposition = databaseService.getAllJObPosition(jobTypeKode);
                jobPositionArrayAdapter = new ArrayAdapter<JobPosition>(getContext(), R.layout.item_dropdown, R.id.id_item, labelposition);
                actJobPositionPerusahaan.setAdapter(jobPositionArrayAdapter);
            }
        });

        actJobPositionPerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jobPositionKode = jobPositionArrayAdapter.getItem(position).getJobPositionID();
            }
        });

        List<Industri> labelin = databaseService.getAllIndustry();
        final ArrayAdapter<Industri> industriArrayAdapter = new ArrayAdapter<Industri>(getContext(), R.layout.item_dropdown, R.id.id_item, labelin);
        actIndustriPerusahaan.setAdapter(industriArrayAdapter);
        actIndustriPerusahaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                industriKode = industriArrayAdapter.getItem(position).getId();
            }
        });

        //        pasangan profesi, job tipe, job position, industri
//        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        List<Profession> listPasanganProfesi = databaseService.getAllProfession();
        final ArrayAdapter<Profession> pasanganProfesiAdapter = new ArrayAdapter<Profession>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganProfesi);
        actPasanganProfesi.setAdapter(pasanganProfesiAdapter);
        actPasanganProfesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganProfesi = pasanganProfesiAdapter.getItem(position).getId();

                actPasanganJobType.getText().clear();
                actPasanganJobPosition.getText().clear();

                /*if (actPasanganProfesi.getText().toString().equalsIgnoreCase("WIRASWASTA")) {
                    lnPasanganNoTeleponRumah.setVisibility(View.VISIBLE);
//                    edtPasanganKodeAreaTeleponRumah.setText("");
//                    edtPasanganNoTeleponRumah.setText("");
//                    edtPasanganNoHp.setText("");
                    validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
                    validator.put(edtPasanganNoTeleponRumah, notEmptyRule);
//                    tilPasanganNoHp.setVisibility(View.VISIBLE);
//                    edtPasanganNoHp.setVisibility(View.VISIBLE);
                    validator.put(edtPasanganNoHp, noHpRule);
                } else {
                    lnPasanganNoTeleponRumah.setVisibility(View.VISIBLE);
                    validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
                    validator.put(edtPasanganNoTeleponRumah, notEmptyRule);
                    validator.removeRules(edtPasanganNoHp);
//                    edtPasanganNoHp.setText("");
                    validator.put(edtPasanganNoHp, noHpRule);
//                    tilPasanganNoHp.setVisibility(View.GONE);
//                    edtPasanganNoHp.setVisibility(View.GONE);
                }*/

                lnPasanganNoTeleponRumah.setVisibility(View.VISIBLE);
                validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
                validator.put(edtPasanganNoTeleponRumah, notEmptyRule);
                validator.put(edtPasanganNoHp, noHpRule);

                List<JobType> listPasanganJobType = databaseService.getAllJobType(kodePasanganProfesi);
                pasanganJobTypeAdapter = new ArrayAdapter<JobType>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganJobType);
                actPasanganJobType.setAdapter(pasanganJobTypeAdapter);
            }
        });

        actPasanganJobType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganJobType = pasanganJobTypeAdapter.getItem(position).getJobTypeID();

                actPasanganJobPosition.getText().clear();

                List<JobPosition> listPasanganJobPosition = databaseService.getAllJObPosition(kodePasanganJobType);
                pasanganJobPositionAdapter = new ArrayAdapter<JobPosition>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganJobPosition);
                actPasanganJobPosition.setAdapter(pasanganJobPositionAdapter);
            }
        });

        actPasanganJobPosition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganJobPosition = pasanganJobPositionAdapter.getItem(position).getJobPositionID();
            }
        });

        List<Industri> listPasanganIndustri = databaseService.getAllIndustry();
        final ArrayAdapter<Industri> pasanganIndustriAdapter = new ArrayAdapter<Industri>(getContext(), R.layout.item_dropdown, R.id.id_item, listPasanganIndustri);
        actPasanganIndustri.setAdapter(pasanganIndustriAdapter);
        actPasanganIndustri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kodePasanganIndustri = pasanganIndustriAdapter.getItem(position).getId();
            }
        });
//        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if (statusAutoComplete.equalsIgnoreCase("rekomendasi")) {
            if (recomendationResponse != null) {
                for (int j = 0; j < recomendationResponse.getRecomendationObjtList().size(); j++) {
                    rekomendasiArrayList.add(new RekomendasiArrayList(recomendationResponse.getRecomendationObjtList().get(j).getId(),
                            recomendationResponse.getRecomendationObjtList().get(j).getRecomendation()));
                }
                final ArrayAdapter<RekomendasiArrayList> rekomendasiAdapter = new ArrayAdapter<RekomendasiArrayList>(this, R.layout.item_dropdown, R.id.id_item, rekomendasiArrayList);
                spnRecomendation.setAdapter(rekomendasiAdapter);
                spnRecomendation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        recomendationId = rekomendasiAdapter.getItem(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }

    private void setDefaultCalculatingToZeroNotValidate() {
        edtHargaAgunan.setText("0");
        edtPokokPembiayaan.setText("0");
        edtDownpayment.setText("0");
        edtBiayaAdminPerhitunganKendaraan.setText("0");
        edtPremiAsuransiPerhitunganAgunanKendaraan.setText("0");
        edtPremiAsuransiPerhitunganJiwaKendaraan.setText("0");
        edtJumlahPembiayaanPerhitunganKendaraan.setText("0");
        edtBungaPembiayaanKendaraan.setText("0");
        edtTotalPinjamanPerhitunganKendaraan.setText("0");
        edtAngsuranPerbulanPerhitunganKendaraan.setText("0");
        edtFlatPertahunPerhitunganKendaraan.setText("0");
        edtStnkPerhitunganKendaraan.setText("0");
        edtBiayaNotarisPerhitunganKendaraan.setText("0");
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setText("0");
        edtBiayaSurveyPerhitunganKendaraan.setText("0");
        edtAngsuranPertamaPerhitunganKendaraan.setText("0");
    }

    private void setDefaultCalculatingToZeroNotValidateTenor() {
        edtPokokPembiayaan.setText("0");
        edtDownpayment.setText("0");
        edtPremiAsuransiPerhitunganAgunanKendaraan.setText("0");
        edtPremiAsuransiPerhitunganJiwaKendaraan.setText("0");
        edtJumlahPembiayaanPerhitunganKendaraan.setText("0");
        edtBungaPembiayaanKendaraan.setText("0");
        edtTotalPinjamanPerhitunganKendaraan.setText("0");
        edtAngsuranPerbulanPerhitunganKendaraan.setText("0");
        edtFlatPertahunPerhitunganKendaraan.setText("0");
        edtStnkPerhitunganKendaraan.setText("0");
        edtBiayaNotarisPerhitunganKendaraan.setText("0");
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setText("0");
        edtBiayaSurveyPerhitunganKendaraan.setText("0");
        edtAngsuranPertamaPerhitunganKendaraan.setText("0");
    }

    private void setAdapterKelurahan() {
        kelurahanAdapter = new ArrayAdapter<String>(this, R.layout.item_dropdown, R.id.id_item, kelurahanArrayList);
    }

    private void hitPerhitunganKendaraanAngsuranPerbulan(String viewId, String statSinkron, String effectiveRate, String strDPPercentage) {
        Log.i("checkHit", viewId);
//            bpkb name = 0 -> orang lain || 1 -> nama sendiri
        String sameName;
        String statusKendaraanPerhitungan;
        String strTipePengajuan;
        String conloanDefault = "0";

        if (edtPokokPembiayaan.getText().toString().replace(",", "").equalsIgnoreCase(consumerloanDefault)) {
            conloanDefault = "0";
        } else {
            conloanDefault = edtPokokPembiayaan.getText().toString().replace(",", "");
        }

        if (rbBpkbAtasNamaSendiri.isChecked() || rbBpkbAtasNamaPasangan.isChecked())
            sameName = "1";
        else sameName = "0";

        if (strStatusPengajuan.equalsIgnoreCase("RO")) statusKendaraanPerhitungan = "RO";
        else statusKendaraanPerhitungan = "new";

        if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA"))
            strTipePengajuan = "psa";
        else strTipePengajuan = "non-psa";

        if (!actDtProductSupplier.getText().toString().isEmpty() &&
                actDtProductSupplier.isSelectionFromPopUp() &&
                !actDtProductMarketingSupplier.getText().toString().isEmpty() &&
                actDtProductMarketingSupplier.isSelectionFromPopUp() &&
                !actDtProductOffering.getText().toString().isEmpty() &&
                actDtProductOffering.isSelectionFromPopUp() &&
                spnTenorPerhitunganKendaraan.getSelectedItem() != null &&
                !spnTenorPerhitunganKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !spnJenisKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !spnMerkKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !actTypeKendaraan.getText().toString().isEmpty() &&
                actTypeKendaraan.isSelectionFromPopUp() &&
                !spnTahunKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                rbBpkbAtasNamaSendiri.isChecked() || rbBpkbAtasNamaOrangLain.isChecked() || rbBpkbAtasNamaPasangan.isChecked()) {

            mDetailPerhitunganKendaraanPresenter.GetPerhitunganKendaraan(
                    token,
                    strTipePengajuan,
                    aoBranch,
                    edtHargaAgunan.getText().toString().replace(",", ""),
                    strDPPercentage,
                    statusKendaraanPerhitungan,
                    sameName,
                    spnTenorPerhitunganKendaraan.getSelectedItem().toString(),
                    "0",
                    edtBiayaAdminPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtStnkPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaPnbpFidusiaPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaSurveyPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaNotarisPerhitunganKendaraan.getText().toString().replace(",", ""),
                    conloanDefault,
                    effectiveRate,
                    statSinkron,
                    edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtPinjaman.getText().toString().replace(",", ""));
        }
    }

    private void hitPerhitunganKendaraanPokokPembiayaan(String viewId, String statSinkron, String effectiveRate, String strDPPercentage) {
        Log.i("checkHit", viewId);
//            bpkb name = 0 -> orang lain || 1 -> nama sendiri
        String sameName;
        String statusKendaraanPerhitungan;
        String strTipePengajuan;

        if (rbBpkbAtasNamaSendiri.isChecked() || rbBpkbAtasNamaPasangan.isChecked())
            sameName = "1";
        else sameName = "0";

        if (strStatusPengajuan.equalsIgnoreCase("RO")) statusKendaraanPerhitungan = "RO";
        else statusKendaraanPerhitungan = "new";

        if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA"))
            strTipePengajuan = "psa";
        else strTipePengajuan = "non-psa";

        if (!actDtProductSupplier.getText().toString().isEmpty() &&
                actDtProductSupplier.isSelectionFromPopUp() &&
                !actDtProductMarketingSupplier.getText().toString().isEmpty() &&
                actDtProductMarketingSupplier.isSelectionFromPopUp() &&
                !actDtProductOffering.getText().toString().isEmpty() &&
                actDtProductOffering.isSelectionFromPopUp() &&
                spnTenorPerhitunganKendaraan.getSelectedItem() != null &&
                !spnTenorPerhitunganKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !spnJenisKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !spnMerkKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !actTypeKendaraan.getText().toString().isEmpty() &&
                actTypeKendaraan.isSelectionFromPopUp() &&
                !spnTahunKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                rbBpkbAtasNamaSendiri.isChecked() || rbBpkbAtasNamaOrangLain.isChecked() || rbBpkbAtasNamaPasangan.isChecked()) {

            mDetailPerhitunganKendaraanPresenter.GetPerhitunganKendaraan(
                    token,
                    strTipePengajuan,
                    aoBranch,
                    edtHargaAgunan.getText().toString().replace(",", ""),
                    strDPPercentage,
                    statusKendaraanPerhitungan,
                    sameName,
                    spnTenorPerhitunganKendaraan.getSelectedItem().toString(),
                    "0",
                    edtBiayaAdminPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtStnkPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaPnbpFidusiaPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaSurveyPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaNotarisPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtPokokPembiayaan.getText().toString().replace(",", ""),
                    effectiveRate,
                    statSinkron,
                    "0",
                    edtPinjaman.getText().toString().replace(",", ""));
        }
    }

    private void hitPerhitunganKendaraan(String viewId, String statSinkron, String effectiveRate, String strDPPercentage) {
        Log.i("checkHit", viewId);
//            bpkb name = 0 -> orang lain || 1 -> nama sendiri
        String sameName;
        String statusKendaraanPerhitungan;
        String strTipePengajuan;


//        if(viewId.equals("spnTenorPerhitunganKendaraan")){
//            setDefaultCalculatingToZeroNotValidate();
//        }

        if (rbBpkbAtasNamaSendiri.isChecked() || rbBpkbAtasNamaPasangan.isChecked())
            sameName = "1";
        else sameName = "0";

        if (strStatusPengajuan.equalsIgnoreCase("RO")) statusKendaraanPerhitungan = "RO";
        else statusKendaraanPerhitungan = "new";

        if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA"))
            strTipePengajuan = "psa";
        else strTipePengajuan = "non-psa";

        if (!actDtProductSupplier.getText().toString().isEmpty() &&
                actDtProductSupplier.isSelectionFromPopUp() &&
                !actDtProductMarketingSupplier.getText().toString().isEmpty() &&
                actDtProductMarketingSupplier.isSelectionFromPopUp() &&
                !actDtProductOffering.getText().toString().isEmpty() &&
                actDtProductOffering.isSelectionFromPopUp() &&
                spnTenorPerhitunganKendaraan.getSelectedItem() != null &&
                !spnTenorPerhitunganKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !spnJenisKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !spnMerkKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                !actTypeKendaraan.getText().toString().isEmpty() &&
                actTypeKendaraan.isSelectionFromPopUp() &&
                !spnTahunKendaraan.getSelectedItem().toString().equalsIgnoreCase("PILIH") &&
                rbBpkbAtasNamaSendiri.isChecked() || rbBpkbAtasNamaOrangLain.isChecked() || rbBpkbAtasNamaPasangan.isChecked()) {

            mDetailPerhitunganKendaraanPresenter.GetPerhitunganKendaraan(
                    token,
                    strTipePengajuan,
                    aoBranch,
                    edtHargaAgunan.getText().toString().replace(",", ""),
                    strDPPercentage,
                    statusKendaraanPerhitungan,
                    sameName,
                    spnTenorPerhitunganKendaraan.getSelectedItem().toString(),
                    "0",
                    edtBiayaAdminPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtStnkPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaPnbpFidusiaPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaSurveyPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtBiayaNotarisPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtPokokPembiayaan.getText().toString().replace(",", ""),
                    effectiveRate,
                    statSinkron,
                    edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().replace(",", ""),
                    edtPinjaman.getText().toString().replace(",", ""));
        }
    }

    private void successAndFailedLoading() {
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onFailedSearchSupplierMaster(String message) {
        supplierNameArrayList.clear();
        setAdapterSupplierName();

        strCheckSupplierMaster = "0";
        successAndFailedLoading();
        messageFailedApi(message);
    }

    private void setAdapterSupplierName() {
        actSupplierAdapter = new ArrayAdapter<SupplierMasterArrayList>(this, R.layout.item_dropdown, R.id.id_item, supplierNameArrayList);
        actDtProductSupplier.setAdapter(actSupplierAdapter);
    }

    @Override
    public void onPreJenisKendaraan() {
        preLoading();
    }

    @Override
    public void onSuccessJenisKendaraan(JenisKendaraanResponse jenisKendaraanResponse) {
        this.jenisKendaraanResponse = jenisKendaraanResponse;
        String statTipePengajuan = "1";
        if (!form.equalsIgnoreCase("Edit")) {
            jenisKendaraan.clear();
            setAdapterJenisKendaraan();

            statusAutoComplete = "jenisKendaraan";
            containerInitAutoComplete();
        }

        if (spnValidasiTipePengajuan.getSelectedItem() != null) {
            if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA")) {
                statTipePengajuan = "1";
            } else {
                statTipePengajuan = "2";
            }
        }

        if (!isBtnNext) {

            if (form.equalsIgnoreCase("Edit") || form.equalsIgnoreCase("Draft")) {
                if (isPsa.equalsIgnoreCase("PSA")) {
                    statTipePengajuan = "1";
                } else {
                    statTipePengajuan = "2";
                }
            }
            mSearchSupplierMasterPresenter.getSearchSupplierMaster(token, "", assetTypeId, statTipePengajuan, aoBranch);
        } else {
            showFormPengajuan();
//            pbMain.setVisibility(View.GONE);
            statOpenForm = "1";
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        isBtnNext = false;
        successAndFailedLoading();
    }

    private void showFormPengajuan() {
        if (!form.equalsIgnoreCase("new")) {
            /*pindah kesini*/
            spnValidasiTipePengajuan.setSelection(getIndex(spnValidasiTipePengajuan, application.getKorpFormulir().getTypeForm()));
            spnKopJenisPembiayaan.setSelection(getIndex(spnKopJenisPembiayaan, application.getKorpFormulir().getTypeOfFinancing()));
        }
        showHeader();
        mainLayout.setVisibility(View.VISIBLE);
        lnButtonWrapper.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
        lnWrapperDataValidasiAwal.setVisibility(View.GONE);
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        if (bucketMessage != null) {
            if (!bucketMessage.isEmpty())
                createDialog(bucketMessage, agreement, timeDelay, amountOfFines);
        }
    }

    @Override
    public void onFailedJenisKendaraan(String message) {
        jenisKendaraan.clear();
        setAdapterJenisKendaraan();
        jenisKendaraan.add(new JenisKendaraanArrayList("", "PILIH"));
        setAdapterJenisKendaraan();
        successAndFailedLoading();
        messageFailedApi(message);
    }

    private void setAdapterJenisKendaraan() {
        actJenisKendaraanAdapter = new ArrayAdapter<JenisKendaraanArrayList>(this, R.layout.item_dropdown, R.id.id_item, jenisKendaraan);
        spnJenisKendaraan.setAdapter(actJenisKendaraanAdapter);
    }

    @Override
    public void onPreWilayahCabang() {
        preLoading();
    }

    @Override
    public void onSuccessWilayahCabang(WilayahCabangResponse wilayahCabangResponse) {
        if (form.equalsIgnoreCase("New")) {
            actWilayahKendaraan.setText(wilayahCabangResponse.getName());
            actCabangKendaraan.setText(spnValidasiCabang.getSelectedItem().toString());
        }
        mJenisKendaraanPresenter.HitJenisKendaraan(token, assetTypeId);
    }

    @Override
    public void onFailedWilayahCabang(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
    }

    @Override
    public void onPreSynchKendaraan() {
        preLoading();
    }

    @Override
    public void onSuccessSynchKendaraan(String idSync, String statusSinkron) {
        this.idSync = idSync;
        if (statusSinkron.equalsIgnoreCase("1")) {
            /*statusSinkron -> sinkronisasi pengajuan*/
            uploadAttachment(idSync, false);
        } else {
            if (form.equalsIgnoreCase("Edit")) {
                Toast.makeText(this, "Pengajuan Berhasil Disubmit", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new RefreshPengajuanEvent());
                EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
                finish();
            } else {
//                Toast.makeText(this, "Berhasil Simpan Draft", Toast.LENGTH_SHORT).show();
                uploadAttachment(idSync, true);
            }
        }
    }

    @Override
    public void onEfNumberTaken() {

    }

    private void uploadAttachment(String idSync, boolean isDraft) {
        List<AttachmentArrayList> attachments = new ArrayList<>();
        String type = "";
        for (Object o : mHashMapAttachmentFiles.keySet()) {
            int i = (int) o;
            AttachmentArrayList attachment = new AttachmentArrayList();
            attachment.setNamaAttachment("eform" + System.currentTimeMillis());
            attachment.setPath(mHashMapAttachmentFiles.get(i).getAbsolutePath());
            attachment.setPath2(mHashMapAttachmentFiles.get(i).getPath());
            attachment.setKey(i);

            attachments.add(attachment);
        }
        successAndFailedLoading();
        if (isDraft) type = "isDraft";

        if (attachments.size() == 0) {
            Toast.makeText(this, "Berhasil Simpan Draft", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new RefreshPengajuanEvent());
            EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
            finish();

        } else {
            mAttachmentKendaraanPresenter.SyncAttachmentKendaraan(token, idSync, attachments, type, typeDataOffering);
        }
    }

    @Override
    public void onFailedSynchKendaraan(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
        showAlertYesNo("onFailedSynchKendaraan", "Pemberitahuan", message, "Save as Draft", "Coba Lagi", form);
    }

    @Subscribe
    public void doStatusSyncKendaraan(StatusSyncKendaraan e) {
        if (e.getStrstatus().equalsIgnoreCase("onFailedSynchKendaraan")
                && e.getStrbtnLeft().equalsIgnoreCase("Save as Draft")) {

        } else if (e.getStrstatus().equalsIgnoreCase("onFailedSynchKendaraan")
                && e.getStrbtnLeft().equalsIgnoreCase("Coba Lagi")) {
            if (checkFieldNpwp())
                mRecomendationPresenter.checkFpd(token);
//                mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, statSinkron, appId);
        } else if (e.getStrstatus().equalsIgnoreCase("onFailedSubmitAttachment")
                && e.getStrbtnLeft().equalsIgnoreCase("Save as Draft")) {

        } else if (e.getStrstatus().equalsIgnoreCase("onFailedSubmitAttachment")
                && e.getStrbtnLeft().equalsIgnoreCase("Coba Lagi")) {

            List<AttachmentArrayList> attachments = new ArrayList<>();
            for (Object o : mHashMapAttachmentFiles.keySet()) {
                int i = (int) o;
                AttachmentArrayList attachment = new AttachmentArrayList();
                attachment.setNamaAttachment("eform" + System.currentTimeMillis());
                attachment.setPath(mHashMapAttachmentFiles.get(i).getAbsolutePath());
                attachment.setPath2(mHashMapAttachmentFiles.get(i).getPath());
                attachment.setKey(i);

                attachments.add(attachment);
            }
            mAttachmentKendaraanPresenter.SyncAttachmentKendaraan(token, idSync, attachments, "", typeDataOffering);
        } else if (e.getStrstatus().equalsIgnoreCase("onEditKOP")) {
            if (e.getStrbtnLeft().equalsIgnoreCase("Ya")) {
                firstInitAssignEdit = false;
                enableFieldAssignEdit();
                jenisKendaraan.clear();
                setAdapterJenisKendaraan();
                statusAutoComplete = "jenisKendaraan";
                spnValidasiTipePengajuan.setSelection(0);
                spnKopJenisPembiayaan.setSelection(0);
                setAllDefaultArrayList();
                containerInitAutoComplete();
                initAutoCompleteAssignEdit(
                        statusAutoComplete,
                        supplierResponse,
                        marketingSupplierResponse,
                        productOfferingResponse,
                        posListDownResponse,
                        productOffTenorResponse,
                        jenisKendaraanResponse,
                        merkKendaraanResponse,
                        tahunProduksiResponse,
                        kelurahanResponse,
                        recomendationResponse);
            } else {

            }
        }
    }

    private void showAlertYesNo(String status, String tvTitle, String tvDescription, String btnLeft, String btnRight, String form) {
        DialogFragment dialogFragment = new YesNoDialog();
        Bundle args = new Bundle();
        args.putString("status", status);
        args.putString("tvTitle", tvTitle);
        args.putString("tvDescription", tvDescription);
        args.putString("btnLeft", btnLeft);
        args.putString("btnRight", btnRight);
        args.putString("form", form);
        if (firstInitAssignEdit) {
            args.putString("editKop", "isEdit");
        }
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "YesNoDialog");
    }

    @Override
    public void onPreSubmitAttachment() {
        preLoading();
    }

    @Override
    public void onSuccessSubmitAttachment() {
        successAndFailedLoading();
        if (statSinkron.equalsIgnoreCase("1")) {
            Toast.makeText(this, "Pengajuan Berhasil Disubmit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Berhasil Simpan Draft", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new RefreshPengajuanEvent());
            EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        }

//        EventBus.getDefault().post(new RefreshPengajuanEvent());
//        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        startActivity(new Intent(FormPengajuanNonElcActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onFailedSubmitAttachment(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
        showAlertYesNo("onFailedSubmitAttachment", "Pemberitahuan", message, "Save as Draft", "Coba Lagi", form);
    }

    @Override
    public void onPreGetPos() {
        preLoading();
    }

    @Override
    public void onSuccessGetPos(PosListDownResponse posListDownResponse) {
        this.posListDownResponse = posListDownResponse;
        posNameArrayList.clear();
        setAdapterPos();
        statusAutoComplete = "pos";
        strCheckPos = "1";
        containerInitAutoComplete();
        successAndFailedLoading();
    }

    @Override
    public void onFailedGetPos(String message) {
        posNameArrayList.clear();
        setAdapterPos();
        strCheckPos = "0";
        successAndFailedLoading();
        messageFailedApi(message);
    }

    private void setAdapterPos() {
        posAdapter = new ArrayAdapter<PosArrayList>(this, R.layout.item_dropdown, R.id.id_item, posNameArrayList);
        actDtProductPos.setAdapter(posAdapter);
    }

    @Override
    public void onPreSubmitPengajuanEditLoad() {
        preLoading();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessSubmitPengajuanEditLoad(Application application) {
        this.application = application;

        if (form.equalsIgnoreCase("Edit")) {
            firstInitAssignEdit = true;
            disableFieldAssignEdit();
        }

        typeDataOffering = application.getOfferingType();
        isPsa = application.getKorpFormulir().getTypeForm();
        pengajuanType = application.getDataType();
        mobileSubmissionKey = application.getMobileSubmissionKey();
        blackListDate = application.getDateStart();
        aoBranch = application.getBranchObjt().getAoBranch();
        branchMaster = application.getBranchObjt().getMasterBranch();
        strStatusPengajuan = application.getKorpFormulir().getFinancingPurpose();
        applicationIdKpm = "00".equalsIgnoreCase(application.getApplicationIdKPM()) ? "" : application.getApplicationIdKPM();
        customerIdConfins = String.valueOf(application.getCustomerId());
        msgNotifikasi = application.getDescriptionEdit();
        strEfNumberResponse = application.geteFNumber();
        intMaxPokokPembiayaan = Integer.parseInt(application.getDetailFinancing().getMaxConsumerLoan());
        intMinPokokPembiayaan = Integer.parseInt(application.getDetailFinancing().getMinConsumerLoan());
        strEffectiveRate = application.getDetailFinancing().getEffectiveRate();
        strDPPercentage = application.getDetailFinancing().getDownPaymentPercentage();

        if (application.getKorpFormulir().getTypeForm().equalsIgnoreCase("PSA")) {
            validator.removeRules(edtStnkPerhitunganKendaraan);
            validator.removeRules(edtBiayaNotarisPerhitunganKendaraan);
            validator.removeRules(edtBiayaSurveyPerhitunganKendaraan);
            validator.removeRules(edtAngsuranPertamaPerhitunganKendaraan);
        } else {
            validator.removeRules(edtDownpayment);
        }

        countSignature = 0;
        uuid = Util.RandomDateNumber();
        checkTipePengajuan();

        /*Enable edit data assign*/
//        if (form.equalsIgnoreCase("Edit")) disableInputFieldEdit();

        editLoadTitle(application);
        editLoadNamaPemohon(application);
        editLoadKop(application);
        editLoadDataPribadi(application);
        editLoadDataPasangan(application);
        editLoadDataAlamat(application);
        editLoadDataKerabat(application);
        editLoadDataPekerjaan(application);
        editLoadDataProduct(application);
        editLoadDataAsuransi(application);
        editLoadDataAgunan(application);
        editLoadDataPerhitungan(application);
        editLoadDataCaraPembayaran(application);
        editLoadDataKeterangan(application);
        editLoadDataRekomendasi(application);
        editLoadDataAttachment(application);
        editLoadDataPenawaran(application);
        editLoadDataPersetujuanTambahan(application);

        String type;
        if (form.equalsIgnoreCase("Edit")) type = "assign";
        else type = "draft";

        mBlackListPresenter.blackList(
                token,
                application.getPersonalData().getBirthDate(),
                application.getPersonalData().getiDNumber(),
                application.getPersonalData().getMobilePhone(),
                typeDataOfferingBlackList,
                aoBranch, edtValidasiNamaLegal.getText().toString(), edtValidasiNamaIbuKandung.getText().toString(), type);
    }

    private void editLoadDataPersetujuanTambahan(Application application) {
        if (!application.getAdvanceCustomerAgreement().isEmpty()) {
            if (application.getAdvanceCustomerAgreement().equals("1")) {
                rbPersetujuanTambahanTrue.setChecked(true);
            } else {
                rbPErsetujuanTambahanFalse.setChecked(false);
            }
        }
    }

    private void editLoadTitle(Application application) {
        if (application.getKorpFormulir().getTypeForm().equalsIgnoreCase("PSA")) {
            tilPokokPembiayaan.setHint("Nilai Pembiayaan (Rp)");
        } else {
            tilPokokPembiayaan.setHint("Pokok Pembiayaan (Rp)");
        }
    }

    private void editLoadDataPenawaran(Application application) {
        if (application.getAgreetoAcceptOtherOffering().equalsIgnoreCase("0"))
            rbPersetujuanPenawaranFalse.setChecked(true);
        else rbPersetujuanPenawaranTrue.setChecked(true);
    }

    private void editLoadDataAttachment(Application application) {
        if (!form.equals("Edit")) {
            numberOfImages = application.getAttachmentMTR().size();
            countImage = 0;
            isImageError = false;
            tmpAttachments = new ArrayList<>();

            if ("M".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "MENIKAH".equalsIgnoreCase(application.getPersonalData().getMaritalStatus())) {
                setAllImageMarried();
            } else {
                setAllImageSingle();
            }
        }
    }

    private void setAllImageSingle() {
        final int[] countErrorImage = {0};
        for (int i = 0; i < application.getAttachmentMTR().size(); i++) {
            tmpAttachments.add(application.getAttachmentMTR().get(i));
            Glide.with(getContext()).load(application.getAttachmentMTR().get(i)).centerCrop().into((ImageView) viewTakeImagesVehicleFirst.get(i));
            viewPbImages.get(i).setVisibility(View.VISIBLE);
            viewCameras.get(i).setVisibility(View.GONE);

//            final int finalI = i;
//            Glide.with(getContext()).load(application.getAttachmentMTR().get(i)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                @Override
//                public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                    super.onLoadFailed(e, errorDrawable);
//                    if (countErrorImage[0] < 1)
//                        Toast.makeText(getContext(), R.string.text_error_loading_attachment, Toast.LENGTH_SHORT).show();
//                    viewPbImages.get(finalI).setVisibility(View.GONE);
//                    viewCameras.get(finalI).setVisibility(View.GONE);
//                    viewDeleteImages.get(finalI).setVisibility(View.GONE);
//                    viewRefreshImages.get(finalI).setVisibility(View.VISIBLE);
//                    Crashlytics.logException(e);
//                    countImage += 1;
//                    countErrorImage[0] += 1;
//                    isImageError = true;
//                }
//
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    mHashMapAttachmentFiles.put(finalI, new File(Util.bitmapToFile(resource)));
//                    viewPbImages.get(finalI).setVisibility(View.GONE);
//                    countImage += 1;
//                }
//            });
            viewPbImages.get(i).setVisibility(View.GONE);
//            viewDeleteImages.get(i).setVisibility(View.VISIBLE);
            if (i == 24) break;
        }
    }

    private void setAllImageMarried() {
        final int[] countErrorImage = {0};
        for (int i = 0; i < application.getAttachmentMTR().size(); i++) {
            tmpAttachments.add(application.getAttachmentMTR().get(i));
            Glide.with(getContext()).load(application.getAttachmentMTR().get(i)).centerCrop().into((ImageView) viewTakeImages.get(i));
            viewPbImages.get(i).setVisibility(View.VISIBLE);
            viewCameras.get(i).setVisibility(View.GONE);
//            final int finalI = i;
//            Glide.with(getContext()).load(application.getAttachmentMTR().get(i)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                @Override
//                public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                    super.onLoadFailed(e, errorDrawable);
//                    if (countErrorImage[0] < 1)
//                        Toast.makeText(getContext(), R.string.text_error_loading_attachment, Toast.LENGTH_SHORT).show();
//                    viewPbImages.get(finalI).setVisibility(View.GONE);
//                    viewCameras.get(finalI).setVisibility(View.GONE);
//                    viewDeleteImages.get(finalI).setVisibility(View.GONE);
//                    viewRefreshImages.get(finalI).setVisibility(View.VISIBLE);
//                    Crashlytics.logException(e);
//                    countImage += 1;
//                    countErrorImage[0] += 1;
//                    isImageError = true;
//                }
//
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    mHashMapAttachmentFiles.put(finalI, new File(Util.bitmapToFile(resource)));
//                    viewPbImages.get(finalI).setVisibility(View.GONE);
//                    countImage += 1;
//                }
//            });
            // attachment dari kpm tidak bisa dihapus
            viewPbImages.get(i).setVisibility(View.GONE);
//            viewDeleteImages.get(i).setVisibility(View.VISIBLE);
            if (i == 24) break;
        }
    }

    private void disableInputFieldEdit() {
        /*data pribadi*/
        edtPribadiNamaPemohon.setEnabled(false);
        edtPribadiNamaPemohon.setTextColor(Color.GRAY);
        edtPribadiNamaLengkapPemohon.setEnabled(false);
        edtPribadiNamaLengkapPemohon.setTextColor(Color.GRAY);
        edtPribadiNoKtp.setEnabled(false);
        edtPribadiNoKtp.setTextColor(Color.GRAY);
        edtPribadiTanggalLahirPribadi.setEnabled(false);
        edtPribadiTanggalLahirPribadi.setTextColor(Color.GRAY);

        /*data product*/
        actDtProductSupplier.setEnabled(false);
        actDtProductSupplier.setTextColor(Color.GRAY);
        actDtProductMarketingSupplier.setEnabled(false);
        actDtProductMarketingSupplier.setTextColor(Color.GRAY);
        actDtProductOffering.setEnabled(false);
        actDtProductOffering.setTextColor(Color.GRAY);
        actDtProductPos.setEnabled(false);
        actDtProductPos.setTextColor(Color.GRAY);
        spnTenorPerhitunganKendaraan.setEnabled(false);

        /*data agunan*/
        actWilayahKendaraan.setEnabled(false);
        actWilayahKendaraan.setTextColor(Color.GRAY);
        actCabangKendaraan.setEnabled(false);
        actCabangKendaraan.setTextColor(Color.GRAY);
        spnJenisKendaraan.setEnabled(false);
        spnMerkKendaraan.setEnabled(false);
        actTypeKendaraan.setEnabled(false);
        actTypeKendaraan.setTextColor(Color.GRAY);
        spnTahunKendaraan.setEnabled(false);
        edtWarnaKendaraan.setEnabled(false);
        edtWarnaKendaraan.setTextColor(Color.GRAY);
        edtIsiSilinder.setEnabled(false);
        edtIsiSilinder.setTextColor(Color.GRAY);
        edtNoPolisi.setEnabled(false);
        edtNoPolisi.setTextColor(Color.GRAY);
        edtNoRangka.setEnabled(false);
        edtNoRangka.setTextColor(Color.GRAY);
        edtNoMesin.setEnabled(false);
        edtNoMesin.setTextColor(Color.GRAY);
        rbBpkbAtasNamaSendiri.setTextColor(Color.GRAY);
        rbBpkbAtasNamaPasangan.setTextColor(Color.GRAY);
        rbBpkbAtasNamaOrangLain.setTextColor(Color.GRAY);
        edtNamaBpkbSendiri.setEnabled(false);
        edtNamaBpkbSendiri.setTextColor(Color.GRAY);
        edtNamaBpkbPasangan.setEnabled(false);
        edtNamaBpkbPasangan.setTextColor(Color.GRAY);
        edtNamaBpkbOrangLain.setEnabled(false);
        edtNamaBpkbOrangLain.setTextColor(Color.GRAY);
        edtMasaBerlakuStnk.setEnabled(false);
        edtMasaBerlakuStnk.setTextColor(Color.GRAY);
        edtMasaBerlakuPajakStnk.setEnabled(false);
        edtMasaBerlakuPajakStnk.setTextColor(Color.GRAY);
        rbBpkbAtasNamaSendiri.setEnabled(false);
        rbBpkbAtasNamaPasangan.setEnabled(false);
        rbBpkbAtasNamaOrangLain.setEnabled(false);

        /*perhitungan*/
        edtHargaAgunan.setEnabled(false);
        edtPokokPembiayaan.setEnabled(false);
        edtBiayaAdminPerhitunganKendaraan.setEnabled(false);
        edtPremiAsuransiPerhitunganAgunanKendaraan.setEnabled(false);
        edtPremiAsuransiPerhitunganJiwaKendaraan.setEnabled(false);
        edtJumlahPembiayaanPerhitunganKendaraan.setEnabled(false);
        edtBungaPembiayaanKendaraan.setEnabled(false);
        edtTotalPinjamanPerhitunganKendaraan.setEnabled(false);
        edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(false);
        edtFlatPertahunPerhitunganKendaraan.setEnabled(false);
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setEnabled(false);
        edtStnkPerhitunganKendaraan.setEnabled(false);
        edtBiayaNotarisPerhitunganKendaraan.setEnabled(false);
        edtAngsuranPertamaPerhitunganKendaraan.setEnabled(false);
        edtBiayaSurveyPerhitunganKendaraan.setEnabled(false);
        edtDownpayment.setEnabled(false);
        spnMetodePenjualan.setEnabled(false);

        edtHargaAgunan.setTextColor(Color.GRAY);
        edtPokokPembiayaan.setTextColor(Color.GRAY);
        edtBiayaAdminPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtPremiAsuransiPerhitunganAgunanKendaraan.setTextColor(Color.GRAY);
        edtPremiAsuransiPerhitunganJiwaKendaraan.setTextColor(Color.GRAY);
        edtJumlahPembiayaanPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBungaPembiayaanKendaraan.setTextColor(Color.GRAY);
        edtTotalPinjamanPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtFlatPertahunPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtStnkPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBiayaNotarisPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtAngsuranPertamaPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtBiayaSurveyPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtDownpayment.setTextColor(Color.GRAY);


    }

    private void editLoadNamaPemohon(Application application) {
        tvNamaPemohon.setText(application.getPersonalData().getFullName());
    }

    private void editLoadDataRekomendasi(Application application) {
        if (application.getReasonRecomendationId().equalsIgnoreCase("0")) {
            lnSpnRecomendation.setVisibility(View.GONE);
            rbRecomendationYes.setChecked(true);
            recomendationId = "0";
        } else if (!application.getReasonRecomendationId().equalsIgnoreCase("0")) {
            lnSpnRecomendation.setVisibility(View.VISIBLE);
            rbRecomendationNo.setChecked(true);
            recomendationId = application.getReasonRecomendationId();
            spnRecomendation.setSelection(getIndex(spnRecomendation, application.getReasonRecomendation()));
        }
        edtDescRecomendation.setText("".equalsIgnoreCase(application.getReasonRecomendationNotes()) ? "" : application.getReasonRecomendationNotes());
    }

    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }

    private void editLoadDataKeterangan(Application application) {
        edtKeterangan.setText(application.getKeterangan() == null ? "" : application.getKeterangan());
    }

    private void editLoadDataCaraPembayaran(Application application) {
        for (int h = 0; h < caraPembayaranOri.length; h++) {
            if (caraPembayaranOri[h].equalsIgnoreCase(application.getDetailProduct().getPaymentMethod())) {
                spnKeleluasaan.setSelection(h);
                break;
            }
        }
    }

    private void editLoadDataPerhitungan(Application application) {
        if (form.equalsIgnoreCase("Draft")) {
            spnTenorPerhitunganKendaraan.setSelection(getIndex(spnTenorPerhitunganKendaraan, String.valueOf(application.getDetailProduct().getTenor())));
        } else {
            String[] itemTenor = new String[]{String.valueOf(application.getDetailProduct().getTenor())};
            ArrayAdapter<String> adapterTenor = new ArrayAdapter<String>(FormPengajuanNonElcActivity.this, R.layout.item_dropdown_grey, R.id.id_item, itemTenor);
            spnTenorPerhitunganKendaraan.setAdapter(adapterTenor);
        }
        edtHargaAgunan.setText(application.getDetailFinancing().getCollateralPrice() == null ? "0" : application.getDetailFinancing().getCollateralPrice());
        edtPokokPembiayaan.setText(application.getDetailFinancing().getConsumerLoan() == null ? "0" : application.getDetailFinancing().getConsumerLoan());
        edtBiayaAdminPerhitunganKendaraan.setText(application.getDetailFinancing().getAdminFee() == null ? "0" : application.getDetailFinancing().getAdminFee());
        edtPremiAsuransiPerhitunganAgunanKendaraan.setText(application.getInsurance().getPremiAsuransiAgunan() == null ? "0" : application.getInsurance().getPremiAsuransiAgunan());
        edtPremiAsuransiPerhitunganJiwaKendaraan.setText(application.getInsurance().getPremiAsuransiJiwa() == null ? "0" : application.getInsurance().getPremiAsuransiJiwa());
        edtJumlahPembiayaanPerhitunganKendaraan.setText(application.getDetailFinancing().getTotalFinancing() == null ? "0" : application.getDetailFinancing().getTotalFinancing());
        edtBungaPembiayaanKendaraan.setText(application.getDetailFinancing().getInterestFinancing());
        edtTotalPinjamanPerhitunganKendaraan.setText(application.getDetailFinancing().getTotalLoan() == null ? "0" : application.getDetailFinancing().getTotalLoan());
        edtAngsuranPerbulanPerhitunganKendaraan.setText(application.getDetailFinancing().getInstallmentAmmount() == null ? "0" : application.getDetailFinancing().getInstallmentAmmount());
        edtFlatPertahunPerhitunganKendaraan.setText(application.getDetailFinancing().getFlatRateYear() == null ? "0" : application.getDetailFinancing().getFlatRateYear());
        edtBiayaPnbpFidusiaPerhitunganKendaraan.setText(application.getDetailFinancing().getFiduciaryFees() == null ? "0" : application.getDetailFinancing().getFiduciaryFees());
        edtStnkPerhitunganKendaraan.setText(application.getDetailFinancing().getStnkFees() == null ? "0" : application.getDetailFinancing().getStnkFees());
        edtBiayaNotarisPerhitunganKendaraan.setText(application.getDetailFinancing().getNotaryFees() == null ? "0" : application.getDetailFinancing().getNotaryFees());
        edtAngsuranPertamaPerhitunganKendaraan.setText(application.getDetailFinancing().getFirstPayment() == null ? "0" : application.getDetailFinancing().getFirstPayment());
        edtBiayaSurveyPerhitunganKendaraan.setText(application.getDetailFinancing().getCosOfSurvey() == null ? "0" : application.getDetailFinancing().getCosOfSurvey());
        edtDownpayment.setText(application.getDetailFinancing().getDownPayment() == null ? "0" : application.getDetailFinancing().getDownPayment());
    }

    private void editLoadDataAgunan(Application application) {
        actWilayahKendaraan.setText(application.getBranchObjt().getRegion() == null ? "" : application.getBranchObjt().getRegion());
        actCabangKendaraan.setText(application.getBranchObjt().getBranchCodeName() == null ? "" : application.getBranchObjt().getBranchCodeName());
        if (!application.getAsset().isEmpty()) {
            if (form.equalsIgnoreCase("Draft")) {
                spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, application.getAsset().get(0).getCategory()));
                spnMerkKendaraan.setSelection(getIndex(spnMerkKendaraan, application.getAsset().get(0).getMerk()));
                spnTahunKendaraan.setSelection(getIndex(spnTahunKendaraan, application.getAsset().get(0).getManufacturingYear()));
            } else {
                String[] itemJenisKendaraan = new String[]{String.valueOf(application.getAsset().get(0).getCategory())};
                ArrayAdapter<String> adapteritemJenisKendaraan = new ArrayAdapter<String>(FormPengajuanNonElcActivity.this, R.layout.item_dropdown_grey, R.id.id_item, itemJenisKendaraan);
                spnJenisKendaraan.setAdapter(adapteritemJenisKendaraan);

                String[] itemMerkKendaraan = new String[]{String.valueOf(application.getAsset().get(0).getMerk())};
                ArrayAdapter<String> adapterMerkKendaraan = new ArrayAdapter<String>(FormPengajuanNonElcActivity.this, R.layout.item_dropdown_grey, R.id.id_item, itemMerkKendaraan);
                spnMerkKendaraan.setAdapter(adapterMerkKendaraan);

                String[] itemTahunKendaraan = new String[]{String.valueOf(application.getAsset().get(0).getManufacturingYear())};
                ArrayAdapter<String> adapterTahunKendaraan = new ArrayAdapter<String>(FormPengajuanNonElcActivity.this, R.layout.item_dropdown_grey, R.id.id_item, itemTahunKendaraan);
                spnTahunKendaraan.setAdapter(adapterTahunKendaraan);
            }
            idJenisKendaraan = application.getAsset().get(0).getCategoryId();
            idMerkKendaraan = application.getAsset().get(0).getMerkId();
            idTipeKendaraan = application.getAsset().get(0).getAssetCode();
            actTypeKendaraan.setText(application.getAsset().get(0).getAssetName() == null ? "" : application.getAsset().get(0).getAssetName());
            edtWarnaKendaraan.setText(application.getAsset().get(0).getColor() == null ? "" : application.getAsset().get(0).getColor());
            edtIsiSilinder.setText(application.getAsset().get(0).getCylinder() == null ? "" : application.getAsset().get(0).getCylinder());
            edtNoPolisi.setText(application.getAsset().get(0).getPoliceNo() == null ? "" : application.getAsset().get(0).getPoliceNo());
            edtNoRangka.setText(application.getAsset().get(0).getFrameNumber() == null ? "" : application.getAsset().get(0).getFrameNumber());
            edtNoMesin.setText(application.getAsset().get(0).getMachineNo() == null ? "" : application.getAsset().get(0).getMachineNo());
            if (application.getAsset().get(0).getBpkbOwnName().equalsIgnoreCase("Sendiri")) {
                rbBpkbAtasNamaSendiri.setChecked(true);
                edtNamaBpkbSendiri.setText(application.getAsset().get(0).getBpkbName() == null ? "" : application.getAsset().get(0).getBpkbName());
                validator.put(edtNamaBpkbSendiri, notEmptyRule);
                validator.removeRules(edtNamaBpkbPasangan);
                validator.removeRules(edtNamaBpkbOrangLain);
                tilNamaBpkbSendiri.setEnabled(true);
                tilNamaBpkbPasangan.setEnabled(false);
                tilNamaBpkbOrangLain.setEnabled(false);
                edtNamaBpkbSendiri.setEnabled(true);
                edtNamaBpkbPasangan.setEnabled(false);
                edtNamaBpkbOrangLain.setEnabled(false);
            } else if (application.getAsset().get(0).getBpkbOwnName().equalsIgnoreCase("Pasangan")) {
                rbBpkbAtasNamaPasangan.setChecked(true);
                edtNamaBpkbPasangan.setText(application.getAsset().get(0).getBpkbName() == null ? "" : application.getAsset().get(0).getBpkbName());
                validator.put(edtNamaBpkbPasangan, notEmptyRule);
                validator.removeRules(edtNamaBpkbSendiri);
                validator.removeRules(edtNamaBpkbOrangLain);
                tilNamaBpkbSendiri.setEnabled(false);
                tilNamaBpkbPasangan.setEnabled(true);
                tilNamaBpkbOrangLain.setEnabled(false);
                edtNamaBpkbSendiri.setEnabled(false);
                edtNamaBpkbPasangan.setEnabled(true);
                edtNamaBpkbOrangLain.setEnabled(false);
            } else {
                rbBpkbAtasNamaOrangLain.setChecked(true);
                edtNamaBpkbOrangLain.setText(application.getAsset().get(0).getBpkbName() == null ? "" : application.getAsset().get(0).getBpkbName());
                validator.put(edtNamaBpkbOrangLain, notEmptyRule);
                validator.removeRules(edtNamaBpkbSendiri);
                validator.removeRules(edtNamaBpkbPasangan);
                tilNamaBpkbSendiri.setEnabled(false);
                tilNamaBpkbPasangan.setEnabled(false);
                tilNamaBpkbOrangLain.setEnabled(true);
                edtNamaBpkbSendiri.setEnabled(false);
                edtNamaBpkbPasangan.setEnabled(false);
                edtNamaBpkbOrangLain.setEnabled(true);
            }
            edtMasaBerlakuStnk.setText(application.getAsset().get(0).getValidityPeriodStnk() == null ? "" : application.getAsset().get(0).getValidityPeriodStnk());
            edtMasaBerlakuPajakStnk.setText(application.getAsset().get(0).getValidityPeriodTaxStnk() == null ? "" : application.getAsset().get(0).getValidityPeriodTaxStnk());

            actTypeKendaraan.setSelectionFromPopUp(true);
        }
    }

    private void editLoadDataAsuransi(Application application) {
        for (int h = 0; h < asuransiAgunanOri.length; h++) {
            if (asuransiAgunanOri[h].equalsIgnoreCase(application.getInsurance().getCoverageType())) {
                spnDataAsuransi.setSelection(h);
                break;
            }
        }
    }

    private void editLoadDataProduct(Application application) {
        supplierKode = application.getAssetMaster().getSupplierID();
        actDtProductSupplier.setText(application.getAssetMaster().getSupplierName());
        marketingKode = application.getAssetMaster().getSalesmanID();
        actDtProductMarketingSupplier.setText(application.getAssetMaster().getSalesmanName());
        productId = application.getDetailProduct().getProductID();
        productOfferingId = application.getDetailProduct().getProductOfferingID();
        actDtProductOffering.setText(application.getDetailProduct().getProductOfferingName());
        posKode = application.getDetailProduct().getpOSID();
        actDtProductPos.setText(application.getDetailProduct().getpOSName() == null ? "" : application.getDetailProduct().getpOSName());

        actDtProductSupplier.setSelectionFromPopUp(true);
        actDtProductMarketingSupplier.setSelectionFromPopUp(true);
        actDtProductOffering.setSelectionFromPopUp(true);
        actDtProductPos.setSelectionFromPopUp(true);
    }

    private void editLoadDataPekerjaan(Application application) {
        edtNamaPerusahaan.setText(application.getCompany().getName());
        edtAlamatPerusahaan.setText(application.getCompany().getAddress());
        edtRtPerusahaan.setText(application.getCompany().getrT().replaceAll(" ", ""));
        edtRwPerusahaan.setText(application.getCompany().getrW().replaceAll(" ", ""));
        actAutoAlamatPekerjaan.setText(application.getCompany().getCity());
        edtAreaPhonePerusahaan.setText(application.getCompany().getAreaPhone());
        edtPhonePerusahaan.setText(application.getCompany().getPhone());
        edtBekerjaSejakPerusahaan.setText(application.getCompany().getEmploymentSinceYear().isEmpty() ? "0" : application.getCompany().getEmploymentSinceYear());
        if (application.getCompany().getProfessionID() != null) {
            professionKode = application.getCompany().getProfessionID();
            List<JobType> labeljob = databaseService.getAllJobType(professionKode);
            jobTypeArrayAdapter = new ArrayAdapter<JobType>(getContext(), R.layout.item_dropdown, R.id.id_item, labeljob);
            actJobTypePerusahaan.setAdapter(jobTypeArrayAdapter);
        }
        actProfesiPerusahaan.setText(application.getCompany().getProfessionName());
        if (application.getCompany().getJobTypeID() != null) {
            jobTypeKode = application.getCompany().getJobTypeID();
            List<JobPosition> labelposition = databaseService.getAllJObPosition(jobTypeKode);
            jobPositionArrayAdapter = new ArrayAdapter<JobPosition>(getContext(), R.layout.item_dropdown, R.id.id_item, labelposition);
            actJobPositionPerusahaan.setAdapter(jobPositionArrayAdapter);
        }
        actJobTypePerusahaan.setText(application.getCompany().getJobTypeName());
        jobPositionKode = application.getCompany().getJobPositionID();
        actJobPositionPerusahaan.setText(application.getCompany().getJobPositionName());
        industriKode = String.valueOf(application.getCompany().getIndustryTypeID());
        actIndustriPerusahaan.setText(application.getCompany().getIndustryTypeName() == null ? "" : application.getCompany().getIndustryTypeName());
        edtPenghasilanTetapPerusahaan.setText(application.getCompany().getMonthlyFixedIncome().isEmpty() ? "0" : application.getCompany().getMonthlyFixedIncome());
        edtPenghasilanLainPerusahaan.setText(application.getCompany().getMonthlyVariableIncome().isEmpty() ? "0" : application.getCompany().getMonthlyVariableIncome());
        edtPenghasilanPasanganPerusahaan.setText(application.getCompany().getSpouseIncome().isEmpty() ? "0" : application.getCompany().getSpouseIncome());
        edtBiayaHidupPerusahaan.setText(application.getCompany().getLivingCostAmount().isEmpty() ? "0" : application.getCompany().getLivingCostAmount());
        if (application.getCompany().getProfessionName().equalsIgnoreCase("WIRASWASTA")) {
            lnDataPekerjaanPhone.setVisibility(View.GONE);
            validator.removeRules(edtAreaPhonePerusahaan);
            validator.removeRules(edtPhonePerusahaan);
            tilPekerjaanNoHp.setVisibility(View.VISIBLE);
            edtPekerjaanNoHp.setVisibility(View.VISIBLE);
            validator.put(edtPekerjaanNoHp, noHpRule);
            edtPekerjaanNoHp.setText(application.getCompany().getPhoneNumber().isEmpty() ? "0" : application.getCompany().getPhoneNumber());
        } else {
            lnDataPekerjaanPhone.setVisibility(View.VISIBLE);
            validator.put(edtAreaPhonePerusahaan, kodeAreaRule);
            validator.put(edtPhonePerusahaan, notEmptyRule);
            validator.removeRules(edtPekerjaanNoHp);
            tilPekerjaanNoHp.setVisibility(View.GONE);
            edtPekerjaanNoHp.setVisibility(View.GONE);
        }
        actAutoAlamatPekerjaan.setSelectionFromPopUp(true);
        actProfesiPerusahaan.setSelectionFromPopUp(true);
        actJobTypePerusahaan.setSelectionFromPopUp(true);
        actJobPositionPerusahaan.setSelectionFromPopUp(true);
        actIndustriPerusahaan.setSelectionFromPopUp(true);
    }

    private void editLoadDataKerabat(Application application) {
        edtNamaKerabat.setText(application.getEmergencyContact().getName());
        for (int h = 0; h < hubunganKerabatOri.length; h++) {
            if (hubunganKerabatOri[h].equalsIgnoreCase(application.getEmergencyContact().getRelationship())) {
                spnHubunganKerabat.setSelection(h);
                break;
            }
        }
        edtAlamatKerabat.setText(application.getEmergencyContact().getAddress());
        edtRtKerabat.setText(application.getEmergencyContact().getrT().replaceAll(" ", ""));
        edtRwKerabat.setText(application.getEmergencyContact().getrW().replaceAll(" ", ""));
        actAutoAlamatKerabat.setText(application.getEmergencyContact().getCity());
        edtAreaPhoneRumahKerabat.setText(application.getEmergencyContact().getHomePhoneArea());
        edtPhoneRumahKerabat.setText(application.getEmergencyContact().getHomePhone());
        edtAreaPhoneKantorKerabat.setText(application.getEmergencyContact().getOfficePhoneArea());
        edtPhoneKantorKerabat.setText(application.getEmergencyContact().getOfficePhone());
        edtHpKerabat.setText(application.getEmergencyContact().getMobilePhone());

        actAutoAlamatKerabat.setSelectionFromPopUp(true);
    }

    private void editLoadDataAlamat(Application application) {
        edtAlamatTinggal.setText(application.getResidance().getAddress());
        edtRtTinggal.setText(application.getResidance().getrT().isEmpty() ? "0" : application.getResidance().getrT().replaceAll(" ", ""));
        edtRwTinggal.setText(application.getResidance().getrW().isEmpty() ? "0" : application.getResidance().getrW().replaceAll(" ", ""));
        actAutoAlamatPemohon.setText(application.getResidance().getCity());
        edtAreaPhoneTinggal.setText(application.getResidance().getAreaPhone());
        edtPhoneTinggal.setText(application.getResidance().getPhone());
        edtAlamatKtp.setText(application.getLegal().getAddress());
        edtRtKtp.setText(application.getLegal().getrT().isEmpty() ? "0" : application.getLegal().getrT().replaceAll(" ", ""));
        edtRwKtp.setText(application.getLegal().getrW().isEmpty() ? "0" : application.getLegal().getrW().replaceAll(" ", ""));
        actAutoKtpAlamatPemohon.setText(application.getLegal().getCity());
        edtAreaPhoneKtp.setText(application.getLegal().getAreaPhone());
        edtPhoneKtp.setText(application.getLegal().getPhone());
        Log.i("TestTKtp", application.getResidance().getrT());
        Log.i("TestTKtp", application.getResidance().getrW());
        Log.i("TestTKtp", application.getLegal().getrT());
        Log.i("TestTKtp", application.getLegal().getrW());

//        Log.i("seta_alamat",application.getResidance().getCity());
//        Log.i("seta_ktp",application.getLegal().getCity());
        actAutoAlamatPemohon.setSelectionFromPopUp(true);
        actAutoKtpAlamatPemohon.setSelectionFromPopUp(true);
    }

    private void editLoadDataPasangan(Application application) {
        if (!application.getFamilyData().isEmpty()) {
            idFamily = String.valueOf(application.getFamilyData().get(0).getId());
            if ("M".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "MENIKAH".equalsIgnoreCase(application.getPersonalData().getMaritalStatus())) {
                lnWrapperDataPasangan.setVisibility(View.VISIBLE);
                lnTtdPemohonLain.setVisibility(View.VISIBLE);
                lnTakeFoto5.setVisibility(View.VISIBLE);
                llPerjanjianPisahHarta.setVisibility(View.VISIBLE);
                rbBpkbAtasNamaPasangan.setVisibility(View.VISIBLE);
                edtPenghasilanPasanganPerusahaan.setVisibility(View.VISIBLE);

                validator.put(rgPerjanjianPisahHarta, radioGroupRule);
                validator.put(imgTtdPasanganPersetujuan, new ImageViewRule());
                if (!form.equalsIgnoreCase("Edit"))
                    validator.removeRules(imgTakePicture5);

                edtPasanganNama.setText(application.getFamilyData().get(0).getName());
                edtPasanganNoKtp.setText(application.getFamilyData().get(0).getiDNumber());
                edtPasanganNoKtp.setText(application.getFamilyData().get(0).getiDNumber());
                edtPasanganTempatLahir.setText(application.getFamilyData().get(0).getBirthPlace().isEmpty() ? "0" : application.getFamilyData().get(0).getBirthPlace());
                edtPasanganTanggalLahir.setText(application.getFamilyData().get(0).getBirthDate());
                edtPasanganKodeAreaTeleponRumah.setText(application.getFamilyData().get(0).getAreaPhoneHome().isEmpty() ? "0" : application.getFamilyData().get(0).getAreaPhoneHome());
                edtPasanganNoTeleponRumah.setText(application.getFamilyData().get(0).getPhoneHome().isEmpty() ? "0" : application.getFamilyData().get(0).getPhoneHome());
                edtPasanganKodeAreaTeleponPerusahaan.setText(application.getFamilyData().get(0).getAreaPhoneCompany().isEmpty() ? "0" : application.getFamilyData().get(0).getAreaPhoneCompany());
                edtPasanganNoTeleponPerusahaan.setText(application.getFamilyData().get(0).getPhoneCompany().isEmpty() ? "0" : application.getFamilyData().get(0).getPhoneCompany());
                edtPasanganNoHp.setText(application.getFamilyData().get(0).getHandphone().isEmpty() ? "0" : application.getFamilyData().get(0).getHandphone());

                validator.put(edtPasanganNoHp, noHpRule);
                validator.put(edtPasanganKodeAreaTeleponRumah, kodeAreaRule);
                validator.put(edtPasanganNoTeleponRumah, notEmptyRule);

                edtPasanganAlamat.setText(application.getFamilyData().get(0).getAddress());
                actPasanganKota.setText(application.getFamilyData().get(0).getCity());
                kodePasanganProfesi = application.getFamilyData().get(0).getProfesiId();
                actPasanganProfesi.setText(application.getFamilyData().get(0).getProfesiName());
                kodePasanganJobType = application.getFamilyData().get(0).getJobTypeId();
                actPasanganJobType.setText(application.getFamilyData().get(0).getJobTypeName());
                kodePasanganJobPosition = application.getFamilyData().get(0).getJobPositionId();
                actPasanganJobPosition.setText(application.getFamilyData().get(0).getJobPositionName());
                kodePasanganIndustri = application.getFamilyData().get(0).getIndustryId();
                actPasanganIndustri.setText(application.getFamilyData().get(0).getIndustryName());
                if (application.getFamilyData().get(0).getStatusEmployee().equalsIgnoreCase("Pegawai Tetap"))
                    rbPasanganPegawaiTetap.setChecked(true);
                else rbPasanganPegawaiTidakTetap.setChecked(true);
                edtPasanganNamaPerusahaan.setText(application.getFamilyData().get(0).getCompanyName());
                edtPasanganNamaIbu.setText(application.getFamilyData().get(0).getSurgateMotherName());

                actPasanganKota.setSelectionFromPopUp(true);
                actPasanganProfesi.setSelectionFromPopUp(true);
                actPasanganJobType.setSelectionFromPopUp(true);
                actPasanganJobPosition.setSelectionFromPopUp(true);
                actPasanganIndustri.setSelectionFromPopUp(true);
            } else {
                lnWrapperDataPasangan.setVisibility(View.GONE);
                lnTtdPemohonLain.setVisibility(View.GONE);
                lnTakeFoto5.setVisibility(View.GONE);
                llPerjanjianPisahHarta.setVisibility(View.GONE);
                rbBpkbAtasNamaPasangan.setVisibility(View.GONE);
                edtPenghasilanPasanganPerusahaan.setVisibility(View.GONE);

                edtPasanganNama.setText("");
                edtPasanganNoKtp.setText("");
                edtPasanganTempatLahir.setText("");
                edtPasanganTanggalLahir.setText("");
                edtPasanganKodeAreaTeleponRumah.setText("");
                edtPasanganNoTeleponRumah.setText("");
                edtPasanganKodeAreaTeleponPerusahaan.setText("");
                edtPasanganNoTeleponPerusahaan.setText("");
                edtPasanganNoHp.setText("");
                edtPasanganAlamat.setText("");
                actPasanganKota.setText("");
                actPasanganProfesi.setText("");
                actPasanganJobType.setText("");
                actPasanganJobPosition.setText("");
                actPasanganIndustri.setText("");
                edtPasanganNamaPerusahaan.setText("");
                edtPasanganNamaIbu.setText("");

                validator.removeRules(edtPasanganNama);
                validator.removeRules(edtPasanganNoKtp);
                validator.removeRules(edtPasanganTempatLahir);
                validator.removeRules(edtPasanganTanggalLahir);
                validator.removeRules(edtPasanganKodeAreaTeleponRumah);
                validator.removeRules(edtPasanganNoTeleponRumah);
                validator.removeRules(edtPasanganKodeAreaTeleponPerusahaan);
                validator.removeRules(edtPasanganNoTeleponPerusahaan);
                validator.removeRules(edtPasanganNoHp);
                validator.removeRules(edtPasanganAlamat);
                validator.removeRules(actPasanganKota);
                validator.removeRules(actPasanganProfesi);
                validator.removeRules(actPasanganJobType);
                validator.removeRules(actPasanganJobPosition);
                validator.removeRules(actPasanganIndustri);
                validator.removeRules(rgPasanganStatus);
                validator.removeRules(edtPasanganNamaPerusahaan);
                validator.removeRules(edtPasanganNamaIbu);
                validator.removeRules(edtPenghasilanPasanganPerusahaan);

                validator.removeRules(imgTtdPasanganPersetujuan);
                validator.removeRules(imgTakePicture5);
                validator.removeRules(rgPerjanjianPisahHarta);
                rbPerjanjianPisahHartaAda.setChecked(false);
                rbPerjanjianPisahHartaTidakAda.setChecked(false);

                imgTtdPasanganPersetujuan.setImageResource(0);
                imgTakePicture5.setImageResource(0);
            }
        }
    }

    private void editLoadDataPribadi(Application application) {
        edtPribadiNamaPemohon.setText(application.getPersonalData().getLegalName());
        edtPribadiNamaLengkapPemohon.setText(application.getPersonalData().getFullName());
        edtPribadiNoKtp.setText(application.getPersonalData().getiDNumber());
        edtPribadiNoKK.setText(application.getPersonalData().getKKno());
        edtTerbitKtpPribadi.setText(application.getPersonalData().getiDTypeIssuedDate());
        edtPribadiTempatLahir.setText(application.getPersonalData().getBirthPlace());
        edtPribadiTanggalLahirPribadi.setText(application.getPersonalData().getBirthDate());

        if (application.getPersonalData().getGender().equalsIgnoreCase("Laki-laki") || application.getPersonalData().getGender().equalsIgnoreCase("M"))
            rbPribadiLaki.setChecked(true);
        else if (application.getPersonalData().getGender().equalsIgnoreCase("Perempuan") || application.getPersonalData().getGender().equalsIgnoreCase("F"))
            rbPribadiPerempuan.setChecked(true);

        for (int h = 0; h < statusPernikahanValue.length; h++) {
            if (statusPernikahanValue[h].equalsIgnoreCase(application.getPersonalData().getMaritalStatus())) {
                spnPribadiStatusPernikahan.setSelection(h);
                break;
            } else if (statusPernikahanOri[h].equalsIgnoreCase(application.getPersonalData().getMaritalStatus())) {
                spnPribadiStatusPernikahan.setSelection(h);
                break;
            } else if (application.getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda") || application.getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda / Duda")) {
                spnPribadiStatusPernikahan.setSelection(4);
                break;
            }
        }

        if (application.getPersonalData().getSeparateProperty().equalsIgnoreCase("Ada"))
            rbPerjanjianPisahHartaAda.setChecked(true);
        else if (application.getPersonalData().getSeparateProperty().equalsIgnoreCase("Tidak Ada"))
            rbPerjanjianPisahHartaTidakAda.setChecked(true);

        edtJmlTanggunganPribadi.setText(application.getPersonalData().getNumOfDependence().isEmpty() ? "0" : application.getPersonalData().getNumOfDependence());

        for (int h = 0; h < statusRumahOri.length; h++) {
            if (statusRumahOri[h].equalsIgnoreCase(application.getPersonalData().getHomeStatus())) {
                spnStatusRumahPribadi.setSelection(h);
                break;
            }
        }
        edtTinggalSejakTahunPribadi.setText(application.getPersonalData().getStaySinceYear());
        edtTinggalSejakBulanPribadi.setText(application.getPersonalData().getStaySinceMonth());

        for (int h = 0; h < pendidikanOri.length; h++) {
            if (pendidikanOri[h].equalsIgnoreCase(application.getPersonalData().getEducation())) {
                spnPendidikanPribadi.setSelection(h);
                break;
            }
        }
        for (int h = 0; h < agamaOri.length; h++) {
            if (agamaOri[h].equalsIgnoreCase(application.getPersonalData().getReligion())) {
                spnAgamaPribadi.setSelection(h);
                break;
            }
        }
        edtNoNpwpDetail.setText(application.getPersonalData().getPersonalNPWP());
        edtEmailPribadi.setText(application.getPersonalData().getEmail());
        edtHandphonePribadi.setText(application.getPersonalData().getMobilePhone());
        edtNamaIbuPribadi.setText(application.getPersonalData().getSurgateMotherName());
    }

    private void editLoadKop(Application application) {
        if (application.getKorpFormulir().getCreditStatus().equalsIgnoreCase("Non Kreditmu")) {
            statusKreditmu = "NONKREDITMU";
        } else {
            statusKreditmu = "KREDITMU";
        }
        edtKopStatusKreditmu.setText(application.getKorpFormulir().getCreditStatus());
        strStatusPengajuan = application.getKorpFormulir().getFinancingPurpose();
        edtKopStatusPemohon.setText(strStatusPengajuan);
        String[] itemjenisPembiayaan = new String[]{application.getKorpFormulir().getTypeOfFinancing()};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormPengajuanNonElcActivity.this, R.layout.item_dropdown, R.id.id_item, itemjenisPembiayaan);
        spnKopJenisPembiayaan.setAdapter(adapter);

        if (!application.getKorpFormulir().getPurposeOfUseFunds().isEmpty()) {
            for (int h = 0; h < tujuanPenggunaanDanaOri.length; h++) {
                if (tujuanPenggunaanDanaOri[h].equalsIgnoreCase(application.getKorpFormulir().getPurposeOfUseFunds())) {
                    spnKopTujuanPenggunaanDana.setSelection(h);
                    break;
                }
            }
        }
        for (int h = 0; h < metodePenjualanOri.length; h++) {
            if (metodePenjualanOri[h].equalsIgnoreCase(application.getKorpFormulir().getSalesMethod())) {
                spnMetodePenjualan.setSelection(h);
                break;
            }
        }
    }

    @Override
    public void onFailedSubmitPengajuanEditLoad(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
    }

    @Override
    public void onDetailTokenExpired() {

    }

    @Override
    public void onPreCheckEFNumber() {
        preLoading();
    }

    @Override
    public void onSuccessCheckEFNumber(CheckEfNumberResponse mCheckEfNumberResponse, MasterFormPengajuan mMasterFormPengajuan) {
        if (mCheckEfNumberResponse.getStatus().equals("1")) {
            if (checkFieldNpwp())
//                mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, statSinkron, appId);
                mRecomendationPresenter.checkFpd(token);
        } else {
            initFormatEfNumber();
            strEfNumber = "EFM" + assetTypeId + branchId + dateEfNumber;
            mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
        }
    }

    @Override
    public void onFailedCheckEFNumber(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
        mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);

    }

    @Override
    public void onTokenCheckEFNumber() {
        mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
    }

    private void containerInitAutoComplete() {
//        if(form.equalsIgnoreCase("Edit")){
//            initAutoCompleteAssignEdit(
//                    statusAutoComplete,
//                    supplierResponse,
//                    marketingSupplierResponse,
//                    productOfferingResponse,
//                    posListDownResponse,
//                    productOffTenorResponse,
//                    jenisKendaraanResponse,
//                    merkKendaraanResponse,
//                    tahunProduksiResponse,
//                    kelurahanResponse,
//                    recomendationResponse);
//        }else{
        initAutoComplete(
                statusAutoComplete,
                supplierResponse,
                marketingSupplierResponse,
                productOfferingResponse,
                posListDownResponse,
                productOffTenorResponse,
                jenisKendaraanResponse,
                merkKendaraanResponse,
                tahunProduksiResponse,
                kelurahanResponse,
                recomendationResponse);
//        }

    }

    private void containerInitAutoCompleteAssignEdit() {
        initAutoCompleteAssignEdit(
                statusAutoComplete,
                supplierResponse,
                marketingSupplierResponse,
                productOfferingResponse,
                posListDownResponse,
                productOffTenorResponse,
                jenisKendaraanResponse,
                merkKendaraanResponse,
                tahunProduksiResponse,
                kelurahanResponse,
                recomendationResponse);


    }

    @Override
    public void onPreKelurahan() {
        preLoading();
    }

    @Override
    public void onSuccessKelurahan(String status, KelurahanResponse kelurahanResponse) {
        this.kelurahanResponse = kelurahanResponse;
        statusAutoComplete = status;
        containerInitAutoComplete();
        successAndFailedLoading();
    }

    @Override
    public void onFailedKelurahan(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
    }

    @Override
    public void onPreSearchMarketingSupplier() {
        preLoading();
    }

    @Override
    public void onSuccessSearchMarketingSupplier(MarketingSupplierResponse marketingSupplierResponse) {
        this.marketingSupplierResponse = marketingSupplierResponse;
        marketingSupplierNameArrayList.clear();
        setAdapterMarketingSupplier();
        statusAutoComplete = "marketingSupplier";
        strCheckMarketingSupplier = "1";
        containerInitAutoComplete();
        successAndFailedLoading();
    }

    @Override
    public void onFailedMarketingSupplier(String message) {
        marketingSupplierNameArrayList.clear();
        setAdapterMarketingSupplier();

        strCheckMarketingSupplier = "0";
        successAndFailedLoading();
        messageFailedApi(message);
    }

    private void setAdapterMarketingSupplier() {
        actMarketingSupplierAdapter = new ArrayAdapter<MarketingSupplierArrayList>(this, R.layout.item_dropdown, R.id.id_item, marketingSupplierNameArrayList);
        actDtProductMarketingSupplier.setAdapter(actMarketingSupplierAdapter);
    }

    @Override
    public void onPreReferalCode() {
        
    }

    @Override
    public void onSuccessReferalCode(ReferalCodeResponse data) {
        isHitReferal = false;
        Log.d("succes get Referral : ", data.getFullname());
    }

    @Override
    public void onFailedReferalCode(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreCekKodeProgram() {

    }

    @Override
    public void onSuccessCekKodeProgram(CekKodeProgramObjct data) {
        if(data.isSpecialProdOff()){
            tvKodeKhusus.setVisibility(View.VISIBLE);
        }else{
            tvKodeKhusus.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailedCekKodeProgram(String message) {

    }

    private class ValidatorDataPribadi implements Validator.ValidationListener {
        public void validatePengajuan() {
            Validator validatorPribadi = new Validator(this);
            validatorPribadi.setValidationListener(this);

            validatorPribadi.put(edtValidasiNoKtp, ktpRule);
            validatorPribadi.put(edtValidasiTanggalLahir, notEmptyRule);

            /*Dihide untuk Beberapa waktu karena belum siap pada masa development Juli 2019*/

//            if(BuildConfig.FLAVOR.equals("staging")){
//                validatorPribadi.put(edtValidasiNamaIbuKandung, notEmptyRule);
//                validatorPribadi.put(edtValidasiNamaLegal, notEmptyRule);
//            }
            validatorPribadi.put(edtValidasiNamaLegal, notEmptyRule);
            validatorPribadi.put(edtValidasiNamaIbuKandung, notEmptyRule);
            validatorPribadi.put(edtValidasiNoHp, noHpRule);
            validatorPribadi.put(spnValidasiCabang, spinnerRule);
//            validatorPribadi.put(spnAgamaPribadi, spinnerRule);
            validatorPribadi.validate();
        }

        @Override
        public void onValidationSucceeded() {
//            Toast.makeText(FormPengajuanNonElcActivity.this, "ini masuk ke validasi", Toast.LENGTH_SHORT).show();
            String leftPhoneNumber;
            if (edtValidasiNoHp.getText().toString().length() < 9) {
                edtValidasiNoHp.setError("Minimal 9 Digit");
            } else if (edtValidasiNoHp.getText().toString().length() >= 9) {
                edtValidasiNoHp.setError(null);
                leftPhoneNumber = edtValidasiNoHp.getText().toString().substring(0, 2);
                if (!leftPhoneNumber.equals("08"))
                    edtValidasiNoHp.setError("Format Nomor Handphone Tidak Sesuai");
                else {
                    edtValidasiNoHp.setError(null);
                    setColorTextViewDefault();

                    strEfNumber = "EFM" + assetTypeId + branchId + dateEfNumber;
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "form_pengajuan_motor_submit");
                    mFirebaseAnalytics.logEvent("form_pengajuan_motor_submit", bundle);
                    preLoading();
                    isBtnNext = true;
                    mRecomendationPresenter.checkFpd(token);
                }
            }
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
//                Toast.makeText(FormPengajuanNonElcActivity.this, "ini gagal validasi", Toast.LENGTH_SHORT).show();
                String message = error.getCollatedErrorMessage(FormPengajuanNonElcActivity.this);
                Toast.makeText(FormPengajuanNonElcActivity.this, message, Toast.LENGTH_SHORT).show();

                // Display error messages ;)
                if (view instanceof EditText) ((EditText) view).setError(message);
            }
        }
    }

    private class ValidatorSaveDraft implements Validator.ValidationListener {
        void validateSaveDraft() {
            Validator onValidationSaveDraft = new Validator(this);
            onValidationSaveDraft.setValidationListener(this);

//            kop
            onValidationSaveDraft.put(spnValidasiTipePengajuan, spinnerRule);
            onValidationSaveDraft.put(edtKopStatusKreditmu, notEmptyRule);
            onValidationSaveDraft.put(edtKopStatusPemohon, notEmptyRule);
            onValidationSaveDraft.put(spnKopJenisPembiayaan, spinnerRule);
            if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("NON PSA"))
                onValidationSaveDraft.put(spnKopTujuanPenggunaanDana, spinnerRule);
            else onValidationSaveDraft.removeRules(spnKopTujuanPenggunaanDana);
            onValidationSaveDraft.put(spnMetodePenjualan, spinnerRule);

//            data pribadi
            onValidationSaveDraft.put(edtPribadiNamaPemohon, notEmptyRule);
            onValidationSaveDraft.put(edtPribadiNamaLengkapPemohon, notEmptyRule);
            onValidationSaveDraft.put(edtPribadiNoKtp, ktpRule);
            onValidationSaveDraft.put(edtPribadiTempatLahir, notEmptyRule);
            onValidationSaveDraft.put(edtPribadiTanggalLahirPribadi, notEmptyRule);
            onValidationSaveDraft.put(edtTerbitKtpPribadi, notEmptyRule);
            onValidationSaveDraft.put(edtHandphonePribadi, noHpRule);

            onValidationSaveDraft.put(spnPribadiStatusPernikahan, spinnerRule);   /*data pribadi*/
            onValidationSaveDraft.put(spnStatusRumahPribadi, spinnerRule);        /*data pribadi*/
            onValidationSaveDraft.put(spnPendidikanPribadi, spinnerRule);         /*data pribadi*/
            onValidationSaveDraft.put(spnAgamaPribadi, spinnerRule);              /*data pribadi*/

            onValidationSaveDraft.put(rgPribadiJenisKelamin, radioGroupRule);

//            alamat ktp
            onValidationSaveDraft.put(edtAlamatKtp, notEmptyRule);
            onValidationSaveDraft.put(edtRtKtp, notEmptyRule);
            onValidationSaveDraft.put(edtRwKtp, notEmptyRule);
            onValidationSaveDraft.put(actAutoKtpAlamatPemohon, notEmptyRule);
            onValidationSaveDraft.put(edtAreaPhoneKtp, kodeAreaRule);
            onValidationSaveDraft.put(edtPhoneKtp, notEmptyRule);

//            rekomendasi
//            onValidationSaveDraft.put(rbGroupRecomendation, radioGroupRule);
//            if (recomendationId != null) {
//                if (recomendationId.equalsIgnoreCase("0"))
//                    onValidationSaveDraft.removeRules(spnRecomendation);
//                else if (!recomendationId.equalsIgnoreCase("0"))
//                    onValidationSaveDraft.put(spnRecomendation, spinnerRule);
//            }

            onValidationSaveDraft.validate();
        }

        @Override
        public void onValidationSucceeded() {
            statSinkron = "3";
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "form_pengajuan_motor_savedraft");
            mFirebaseAnalytics.logEvent("form_pengajuan_motor_savedraft", bundle);
            setValueToHashMap();
            mRecomendationPresenter.checkFpd(token);
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(FormPengajuanNonElcActivity.this);

                // Display error messages ;)
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                    if (viewDataKopEditText.contains(view))
                        imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewDataPribadiSaveDraft.contains(view))
                        imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewDataAlamatTinggalSaveDraft.contains(view))
                        imgDropDownAlamat.setImageResource(android.R.drawable.ic_dialog_alert);
//                    if (viewDetailProduct.contains(view))
//                        imgDropDownProduct.setImageResource(android.R.drawable.ic_dialog_alert);
//                    if (viewAgunan.contains(view))
//                        imgDropDownAgunan.setImageResource(android.R.drawable.ic_dialog_alert);
                }
                if (view instanceof Spinner) {
                    if (viewDataKopSpinnerNonPsa.contains(view))
                        imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewDataKopSpinnerPsa.contains(view))
                        imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (view == spnValidasiTipePengajuan)
                        tvValidasiTipePengajuan.setTextColor(Color.RED);
                    if (view == spnKopJenisPembiayaan)
                        tvKopJenisPembiayaan.setTextColor(Color.RED);
                    if (view == spnKopTujuanPenggunaanDana)
                        tvKopTujuanPenggunaanDana.setTextColor(Color.RED);
                    if (view == spnMetodePenjualan)
                        tvMetodePenjualan.setTextColor(Color.RED);
                    if (view == spnAgamaPribadi)
                        tvAgamaPribadi.setTextColor(Color.RED);
                    if (view == spnPribadiStatusPernikahan)
                        tvPribadiStatusPernikahan.setTextColor(Color.RED);
                    if (view == spnStatusRumahPribadi)
                        tvStatusRumahPribadi.setTextColor(Color.RED);
                    if (view == spnPendidikanPribadi)
                        tvPendidikanPribadi.setTextColor(Color.RED);

                    if (viewDataRekomendasiSpinner.contains(view)) {
                        imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                        tvGroupRekomendasi.setTextColor(Color.RED);
                    }
                    if (view == spnRecomendation) tvRekomendasi.setTextColor(Color.RED);
                }
                if (view instanceof RadioGroup) {
                    if (viewRekomendasiRG.contains(view)) {
                        imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                        tvGroupRekomendasi.setTextColor(Color.RED);
                    }

                    if (viewDataPribadiRG.contains(view)) {
                        imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                        tvPribadiJenisKelamin.setTextColor(Color.RED);
                    }

                    if (view == rbGroupRecomendation) tvGroupRekomendasi.setTextColor(Color.RED);
                }
            }
        }
    }

    private void setValueToField(String typeCustomer) {
        if (typeCustomer.equals("TEST STEVE")) {
            edtValidasiNoKtp.setText("9000000101900005");
            edtValidasiTanggalLahir.setText("1990-01-01");
            edtValidasiNoHp.setText("082118303880");
        }
        if (typeCustomer.equals("Toni Wibawa, S.Kon : RO || S. Kre : Kreditmu")) {
            edtValidasiNoKtp.setText("9534008705308880");
            edtValidasiTanggalLahir.setText("1971-06-12");
            edtValidasiNoHp.setText("082118303880");
        }
        if (typeCustomer.equals("Test Kreditplus, S.Kon : RO || S. Kre : Kreditmu")) {
            edtValidasiNoKtp.setText("9000000101900005");
            edtValidasiTanggalLahir.setText("1990-01-01");
            edtValidasiNoHp.setText("082118303880");
        }
        if (typeCustomer.equals("MAGDALENA FREDRIKA MAAIL, S.Kon : RO || S. Kre : Non Kreditmu")) {
            edtValidasiNoKtp.setText("1030804083388887");
            edtValidasiTanggalLahir.setText("1970-11-07");
            edtValidasiNoHp.setText("082118303880");
        }
        if (typeCustomer.equals("TEST JERRY, S.Kon : New || S. Kre : Non Kreditmu (Kreditmu Tidak Aktif)")) {
            edtValidasiNoKtp.setText("9000000101900050");
            edtValidasiTanggalLahir.setText("1990-01-01");
            edtValidasiNoHp.setText("082118303880");
        }
        if (typeCustomer.equals("RAMDONA, BLACKLIST")) {
            edtValidasiNoKtp.setText("3216060407830012");
            edtValidasiTanggalLahir.setText("1983-04-07");
            edtValidasiNoHp.setText("082118303880");
        }
        if (typeCustomer.equals("TEST KREDITMU, S.Kon : New || S. Kre : Kreditmu")) {
            edtValidasiNoKtp.setText("9000000101900006");
            edtValidasiTanggalLahir.setText("1990-01-01");
            edtValidasiNoHp.setText("082118303880");
        }
        if (typeCustomer.equals("Autofill Draft")) {
            autoFillFormEmpty();
        }
        if (typeCustomer.equals("Save Draft")) {
            edtValidasiNoKtp.setText("1111" + dateEfNumber);
            edtValidasiTanggalLahir.setText("1990-01-01");
            edtValidasiNoHp.setText("082118303880");
//            kop

//            spnMetodePenjualan.setSelection(1);
//            data pribadi
            edtTerbitKtpPribadi.setText("2011-01-01");
            edtPribadiTempatLahir.setText("edtPribadiTempatLahir");
            edtValidasiNoKtp.setText("1111" + dateEfNumber);
            edtValidasiTanggalLahir.setText("2001-09-17");
            edtValidasiNoHp.setText("082118303880");
            edtTerbitKtpPribadi.setText("2011-01-01");
//            alamat tinggal
            edtAlamatKtp.setText("edtAlamatTinggal");
            edtRtKtp.setText("9");
            edtRwKtp.setText("10");
            edtAreaPhoneKtp.setText("11");
            edtPhoneKtp.setText("12");

//            AGUNAN
            edtWarnaKendaraan.setText("edtWarnaKendaraan");
            rbStatusKendaraanBekas.setChecked(true);
            edtIsiSilinder.setText("1000");
            edtNoPolisi.setText("edtNoPolisi");
            edtNoRangka.setText("edtNoRangka");
            edtNoMesin.setText("edtNoMesin");
//            rbBpkbAtasNamaOrangLain.setChecked(true);
//            edtMasaBerlakuStnk.setText("2020");
//            edtMasaBerlakuPajakStnk.setText("2020");
        }

        if (typeCustomer.equals("Reguler")) {
            edtValidasiNoKtp.setText("1111" + dateEfNumber);
            edtValidasiTanggalLahir.setText("2001-01-01");
            edtValidasiNoHp.setText("082118303880");
////        KOP
            spnKopJenisPembiayaan.setSelection(1);
            spnKopTujuanPenggunaanDana.setSelection(1);
//            spnMetodePenjualan.setSelection(1);
            autoFillForm("Reguler");
        }
    }

    private void autoFillFormEmpty() {
        autofillDataPribadi();
        autofillDataKerabat();
        autofillDataPekerjaan();
        autofillDataAsuransi();
        autofillDataPembayaran();
        autofillDataPenawaran();
    }

    private void autofillDataPenawaran() {
        rbPersetujuanPenawaranTrue.setChecked(true);
    }

    private void autofillDataPembayaran() {
        spnKeleluasaan.setSelection(2);
    }

    private void autofillDataAsuransi() {
        spnDataAsuransi.setSelection(1);
    }

    private void autofillDataPekerjaan() {
        edtNamaPerusahaan.setText("10");
        edtAlamatPerusahaan.setText("10");
        edtRtPerusahaan.setText("10");
        edtRwPerusahaan.setText("10");
        edtAreaPhonePerusahaan.setText("10");
        edtPhonePerusahaan.setText("10");
        edtBekerjaSejakPerusahaan.setText("1973");
        edtPenghasilanTetapPerusahaan.setText("10");
        edtPenghasilanLainPerusahaan.setText("10");
        edtPenghasilanPasanganPerusahaan.setText("10");
        edtBiayaHidupPerusahaan.setText("10");
    }

    private void autofillDataKerabat() {
        edtNamaKerabat.setText("C");
        spnHubunganKerabat.setSelection(2);
        edtAlamatKerabat.setText("10");
        edtRtKerabat.setText("10");
        edtRwKerabat.setText("10");
        edtAreaPhoneRumahKerabat.setText("10");
        edtPhoneRumahKerabat.setText("10");
        edtAreaPhoneKantorKerabat.setText("10");
        edtPhoneKantorKerabat.setText("10");
        edtHpKerabat.setText("08118303880");
    }

    private void autofillDataPribadi() {
        rbPribadiPerempuan.setChecked(true);
        edtJmlTanggunganPribadi.setText("10");
        spnStatusRumahPribadi.setSelection(2);
        edtTinggalSejakTahunPribadi.setText("2019");
        edtTinggalSejakBulanPribadi.setText("3");
        spnPendidikanPribadi.setSelection(2);
        spnAgamaPribadi.setSelection(2);
        edtNoNpwpPribadi.setText("123456789012345");
        edtEmailPribadi.setText("nurirppan@gmail.com");
        edtNamaIbuPribadi.setText("C");
    }

    private void autoFillForm(String tipeCustomer) {
        if (tipeCustomer.equalsIgnoreCase("Autofill Draft") || tipeCustomer.equalsIgnoreCase("Reguler")) {
////        DATA PRIBADI
            edtPribadiNamaPemohon.setText("IRFAN " + " " + spnValidasiTipePengajuan.getSelectedItem().toString() + " " + spnPribadiStatusPernikahan.getSelectedItem().toString());
            edtPribadiNamaLengkapPemohon.setText(edtPribadiNamaPemohon.getText().toString());
            edtTerbitKtpPribadi.setText("2011-01-01");
            edtPribadiTempatLahir.setText("SUBANG");
//            edtPribadiTanggalLahirPribadi.setText("2011-01-02");
            rbPribadiPerempuan.setChecked(true);
//            spnPribadiStatusPernikahan.setSelection(1);
            spnAgamaPribadi.setSelection(1);
//            rbPerjanjianPisahHartaTidakAda.setChecked(true);
            edtJmlTanggunganPribadi.setText("10");
            spnStatusRumahPribadi.setSelection(1);
            edtTinggalSejakTahunPribadi.setText("2011");
            edtTinggalSejakBulanPribadi.setText("1");
            spnPendidikanPribadi.setSelection(1);
            edtNoNpwpPribadi.setText("123456789012345");
            edtEmailPribadi.setText("0@gmail.com");
//            edtHandphonePribadi.setText("082118303880");
            edtNamaIbuPribadi.setText("IRFAN");

            //        ALAMAT PEMOHON DAN KTP
            edtAlamatTinggal.setText("10");
            edtRtTinggal.setText("10");
            edtRwTinggal.setText("10");
            edtAreaPhoneTinggal.setText("10");
            edtPhoneTinggal.setText("10");
            edtAlamatKtp.setText("10");
            edtRtKtp.setText("10");
            edtRwKtp.setText("10");
            edtAreaPhoneKtp.setText("10");
            edtPhoneKtp.setText("10");

//        DATA PASANGAN
            edtPasanganNama.setText("C");
            edtPasanganNoKtp.setText("1111" + dateEfNumber);
            edtPasanganTempatLahir.setText("10");
            edtPasanganTanggalLahir.setText("2001-09-17");
            edtPasanganKodeAreaTeleponRumah.setText("10");
            edtPasanganNoTeleponRumah.setText("10");
            edtPasanganKodeAreaTeleponPerusahaan.setText("10");
            edtPasanganNoTeleponPerusahaan.setText("10");
            edtPasanganNoHp.setText("08118303880");
            edtPasanganAlamat.setText("C");
            rbPasanganPegawaiTidakTetap.setChecked(true);
            edtPasanganNamaPerusahaan.setText("C");
            edtPasanganNamaIbu.setText("C");

//        INFORMASI KERABAT
            edtNamaKerabat.setText("C");
            spnHubunganKerabat.setSelection(1);
            edtAlamatKerabat.setText("10");
            edtRtKerabat.setText("10");
            edtRwKerabat.setText("10");
            edtAreaPhoneRumahKerabat.setText("10");
            edtPhoneRumahKerabat.setText("10");
            edtAreaPhoneKantorKerabat.setText("10");
            edtPhoneKantorKerabat.setText("10");
            edtHpKerabat.setText("082118303880");

//        DATA PEKERJAAN
            edtNamaPerusahaan.setText("10");
            edtAlamatPerusahaan.setText("10");
            edtRtPerusahaan.setText("10");
            edtRwPerusahaan.setText("10");
            edtAreaPhonePerusahaan.setText("10");
            edtPhonePerusahaan.setText("10");
            edtBekerjaSejakPerusahaan.setText("10");
            edtPenghasilanTetapPerusahaan.setText("10");
            edtPenghasilanLainPerusahaan.setText("10");
            edtPenghasilanPasanganPerusahaan.setText("10");
            edtBiayaHidupPerusahaan.setText("10");
//            AGUNAN
            edtWarnaKendaraan.setText("10");
            rbStatusKendaraanBekas.setChecked(true);
            edtIsiSilinder.setText("10");
            edtNoPolisi.setText("10");
            edtNoRangka.setText("10");
            edtNoMesin.setText("10");
//            rbBpkbAtasNamaOrangLain.setChecked(true);
//            edtMasaBerlakuStnk.setText("2020");
//            edtMasaBerlakuPajakStnk.setText("2020");
//            CARA PEMBAYARAN
            spnKeleluasaan.setSelection(1);
//            KETERANGAN
            edtKeterangan.setText("10");
//            REKOMENDASI
//            edtDescRecomendation.setText("10");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pemberitahuan");
        alert.setMessage("Apakah Anda Yakin Keluar Pengajuan");
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert.show();
    }

    @Subscribe
    public void setSignatureEvent(SignatureEvent e) {
        if (e.getTipeTtd() == SIGNATURE_PEMOHON) {
            imgTtdPemohonPersetujuan.setImageBitmap(e.getBitmap());
            bitmapTtdPemohon = e.getBitmap();
            action = getString(R.string.action_successful_signature_submission_applicant_signature);
            sendCoordinate();
        } else if (e.getTipeTtd() == SIGNATURE_PASANGAN) {
            imgTtdPasanganPersetujuan.setImageBitmap(e.getBitmap());
            bitmapTtdPasangan = e.getBitmap();
            action = getString(R.string.action_successful_signature_submission_partner_signature);
            sendCoordinate();
        }
        if (tvNamaPemohon.getText().toString().isEmpty())
            lnWrapperDataNamaPemohon.setVisibility(View.GONE);
        else lnWrapperDataNamaPemohon.setVisibility(View.VISIBLE);
        hideValidasiAwal();
    }

    @OnClick({R.id.img_ttd_pemohon_persetujuan, R.id.img_ttd_pasangan_persetujuan})
    public void onClickSignature(View view) {
        Util.hideKeyboard(this, view);
        if (Util.checkActiveLocation(this)) {
            switch (view.getId()) {
                case R.id.img_ttd_pemohon_persetujuan:
                    tipeTtd = "img_ttd_pemohon_persetujuan";
                    if (isConfirmSignature) {

                    } else {
                        if (Constant.Flag.IS_DIRECT_SIGNATURE) {
                            Intent intent = new Intent(this, SignatureActivity.class);
                            intent.putExtra("tipe_ttd", SIGNATURE_PEMOHON);
                            startActivity(intent);
                        } else {
                            validatorCodeSignature.validateSignature();
                        }
                    }

                    break;
                case R.id.img_ttd_pasangan_persetujuan:
                    tipeTtd = "img_ttd_pasangan_persetujuan";
                    if (isConfirmSignature) {

                    } else {
                        if (Constant.Flag.IS_DIRECT_SIGNATURE) {
                            Intent intent = new Intent(this, SignatureActivity.class);
                            intent.putExtra("tipe_ttd", SIGNATURE_PASANGAN);
                            startActivity(intent);
                        } else {
                            validatorCodeSignature.validateSignature();
                        }
                    }
                    break;
            }
        }
    }

    private class ValidatorCodeSignature implements Validator.ValidationListener {
        void validateSignature() {
            Validator onValidationSignature = new Validator(this);
            onValidationSignature.setValidationListener(this);

            if (isAssignEdit == null || isAssignEdit.isEmpty()) {
//            kop
                if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("NON PSA")) {
                    onValidationSignature.put(spnKopTujuanPenggunaanDana, spinnerRule);   /*kop*/
                }
                onValidationSignature.put(spnKopJenisPembiayaan, spinnerRule);        /*kop*/
                onValidationSignature.put(spnMetodePenjualan, spinnerRule);           /*kop*/
            }

//            data pribadi
            onValidationSignature.put(edtPribadiNamaPemohon, notEmptyRule);
            onValidationSignature.put(edtPribadiNamaLengkapPemohon, notEmptyRule);
            onValidationSignature.put(edtPribadiNoKtp, ktpRule);
            onValidationSignature.put(edtPribadiTempatLahir, notEmptyRule);
            onValidationSignature.put(edtPribadiTanggalLahirPribadi, notEmptyRule);
            onValidationSignature.put(edtTerbitKtpPribadi, notEmptyRule);
            onValidationSignature.put(edtHandphonePribadi, noHpRule);
            onValidationSignature.put(spnPribadiStatusPernikahan, spinnerRule);

//            detail product
            onValidationSignature.put(actDtProductSupplier, notEmptyRule);
            onValidationSignature.put(actDtProductMarketingSupplier, notEmptyRule);
            onValidationSignature.put(actDtProductOffering, notEmptyRule);
            onValidationSignature.put(actDtProductPos, notEmptyRule);

//            data asuransi
            onValidationSignature.put(spnDataAsuransi, spinnerRule);

//            agunan
            onValidationSignature.put(actWilayahKendaraan, notEmptyRule);
            onValidationSignature.put(actCabangKendaraan, notEmptyRule);
            onValidationSignature.put(spnJenisKendaraan, spinnerRule);
            onValidationSignature.put(spnMerkKendaraan, spinnerRule);
            onValidationSignature.put(actTypeKendaraan, notEmptyRule);
            onValidationSignature.put(spnTahunKendaraan, spinnerRule);
            onValidationSignature.put(edtWarnaKendaraan, notEmptyRule);
            onValidationSignature.put(edtIsiSilinder, notEmptyRule);
            onValidationSignature.put(edtNoPolisi, notEmptyRule);
            onValidationSignature.put(edtNoRangka, notEmptyRule);
            onValidationSignature.put(edtNoMesin, notEmptyRule);
            onValidationSignature.put(rgBpkbAtasNama, radioGroupRule);
            onValidationSignature.put(edtMasaBerlakuStnk, notEmptyRule);
            onValidationSignature.put(edtMasaBerlakuPajakStnk, notEmptyRule);

//            detail perhitungan
            onValidationSignature.put(edtAngsuranPerbulanPerhitunganKendaraan, notZeroRule);

            onValidationSignature.validate();
        }

        @Override
        public void onValidationSucceeded() {
            if (isUserValid) {
                Intent intent = new Intent(FormPengajuanNonElcActivity.this, SignatureActivity.class);
                if (tipeTtd.equalsIgnoreCase("img_ttd_pemohon_persetujuan"))
                    intent.putExtra("tipe_ttd", SIGNATURE_PEMOHON);
                else intent.putExtra("tipe_ttd", SIGNATURE_PASANGAN);
                startActivity(intent);
            } else {
                mSyaratDanKetentuanPresenter.syaratLoad(token);
            }
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(FormPengajuanNonElcActivity.this);

                // Display error messages ;)
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                    if (viewDataPribadiSaveDraft.contains(view))
                        imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewAgunan.contains(view))
                        imgDropDownAgunan.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewDataSignaturePerhitungan.contains(view))
                        imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                }
                if (view instanceof RadioGroup) {
                    if (viewAgunanRG.contains(view))
                        imgDropDownAgunan.setImageResource(android.R.drawable.ic_dialog_alert);
                }
                if (view instanceof Spinner) {
                    if (viewDataKopSpinnerNonPsa.contains(view))
                        imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewDataKopSpinnerPsa.contains(view))
                        imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewDataAsuransiSpinner.contains(view))
                        imgDropDownAsurani.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewDataPernikahanSpinner.contains(view))
                        imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                    if (viewRekomendasiRG.contains(view)) {
                        imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                        tvGroupRekomendasi.setTextColor(Color.RED);
                    }

                    if (view == rbGroupRecomendation) tvGroupRekomendasi.setTextColor(Color.RED);
                }

                if (viewDataRekomendasiSpinner.contains(view)) {
                    imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                    tvGroupRekomendasi.setTextColor(Color.RED);
                }
                if (view == spnRecomendation) tvRekomendasi.setTextColor(Color.RED);
            }

        }
    }

//    private void getValueFieldDataProduct() {
//        strFieldActDtProductSupplier = actDtProductSupplier.getText().toString();
//        strFieldActDtProductMarketingSupplier = actDtProductMarketingSupplier.getText().toString();
//        strFieldActDtProductOffering = actDtProductOffering.getText().toString();
//        strFieldActDtProductPos = actDtProductPos.getText().toString();
//    }
//
//    private void getValueFieldDataAsuransi() {
//        strFieldActDtProductSupplier = spnDataAsuransi.getSelectedItem().toString();
//    }
//
//    private void getValueFieldDataAgunan() {
//        strFieldActWilayahKendaraan = actWilayahKendaraan.getText().toString();
//        strFieldActCabangKendaraan = actCabangKendaraan.getText().toString();
//        strFieldJenisKendaraan = spnJenisKendaraan.getSelectedItem().toString();
//        strFieldMerkKendaraan = spnMerkKendaraan.getSelectedItem().toString();
//        strFieldActTypeKendaraan = actTypeKendaraan.getText().toString();
//        strFieldTahunKendaraan = spnTahunKendaraan.getSelectedItem().toString();
//        strFieldWarnaKendaraan = edtWarnaKendaraan.getText().toString();
//        strFieldIsiSilinder = edtIsiSilinder.getText().toString();
//        strFieldNoPolisi = edtNoPolisi.getText().toString();
//        strFieldNoRangka = edtNoRangka.getText().toString();
//        strFieldNoMesin = edtNoMesin.getText().toString();
//        int intFieldBpkbAtasNama = rgBpkbAtasNama.getCheckedRadioButtonId();
//        strFieldA = rgBpkbAtasNama.getText().toString();
//        strFieldMasaBerlakuStnk = edtMasaBerlakuStnk.getText().toString();
//        strFieldMasaBerlakuPajakStnk = edtMasaBerlakuPajakStnk.getText().toString();
//    }

    @OnClick({R.id.header_data_validasi_awal, R.id.header_master_header, R.id.header_data_pribadi, R.id.header_data_pasangan, R.id.header_alamat_pemohon,
            R.id.header_informasi_kerabat, R.id.header_data_pekerjaan, R.id.header_detail_product, R.id.header_data_asuransi, R.id.header_data_perhitungan,
            R.id.header_data_keterangan, R.id.header_image_pengajuan, R.id.header_persetujuan, R.id.header_recomendation, R.id.header_agunan,
            R.id.header_data_keleluasaan, R.id.header_informasi_penawaran, R.id.header_persetujuan_tambahan, R.id.header_data_npwp})
    public void onClickHeader(View view) {
        switch (view.getId()) {
            case R.id.header_data_validasi_awal:
                if (Constant.Flag.IS_DEVELOPER) {
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialog_type_customer);
                    final Spinner spnTipeCustomer = dialog.findViewById(R.id.spnTipeCustomer);
                    final Button btnSubmit = dialog.findViewById(R.id.btn_submit);
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setValueToField(spnTipeCustomer.getSelectedItem().toString());
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;
            case R.id.header_master_header:
                showHideContent(rlMasterHeader, true);
                break;
            case R.id.header_data_pribadi:
                showHideContent(rlDataPribadi, false);
                break;
            case R.id.header_data_pasangan:
                showHideContent(rlDataPasangan, false);
                break;
            case R.id.header_alamat_pemohon:
                showHideContent(rlAlamatPemohon, false);
                break;
            case R.id.header_informasi_kerabat:
                showHideContent(rlInformasiKerabat, false);
                break;
            case R.id.header_data_pekerjaan:
                showHideContent(rlDataPekerjaan, false);
                break;
            case R.id.header_detail_product:
                showHideContent(rlDetailProduct, true);
                break;
            case R.id.header_data_asuransi:
                showHideContent(rlDataAsuransi, false);
                break;
            case R.id.header_data_perhitungan:
                showHideContent(rlDataPerhitunganKendaraan, true);
                break;
            case R.id.header_data_keleluasaan:
                showHideContent(rlDataKeleluasaan, false);
                break;
            case R.id.header_data_keterangan:
                if (Constant.Flag.IS_DEVELOPER) {
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialog_type_customer);
                    final Spinner spnTipeCustomer = dialog.findViewById(R.id.spnTipeCustomer);
                    final Button btnSubmit = dialog.findViewById(R.id.btn_submit);
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setValueToField(spnTipeCustomer.getSelectedItem().toString());
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                showHideContent(rlDataKeterangan, false);
                break;
            case R.id.header_image_pengajuan:
                showHideContent(rlImagePengajuan, false);
                break;
            case R.id.header_persetujuan:
                showHideContent(rlPersetujuan, false);
                break;
            case R.id.header_recomendation:
                showHideContent(rlRecomendation, false);
                break;
            case R.id.header_agunan:
                showHideContent(rlAgunan, true);
                break;
            case R.id.header_informasi_penawaran:
                showHideContent(rlInformasiPenawaran, false);
                break;
            case R.id.header_persetujuan_tambahan:
                showHideContent(rlPersetujuanTambahan, false);
                break;
            case R.id.header_data_npwp:
                showHideContent(rlDataNpwp, false);
                break;
        }
    }

    @OnClick({R.id.img_take_picture_1,
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
            R.id.img_take_picture_25,
            R.id.img_take_picture_26,
            R.id.img_take_picture_27,
            R.id.img_take_picture_28,
            R.id.img_take_picture_29})
    public void onClickTakePhoto(View view) {
        if (Util.checkActiveLocation(this)) {
            if (!form.equalsIgnoreCase("Edit")) {
                switch (view.getId()) {
                    case R.id.img_take_picture_1:
                        initPermission(TAKE_CAMERA_PENGAJUAN_1);
                        break;
                    case R.id.img_take_picture_2:
                        if (isTakePict) {

                        } else {
                            isTakePict = true;
                            final Dialog dialog = new Dialog(this);
                            dialog.setContentView(R.layout.dialog_terms_photo_customer);
                            final Button btnSubmit = dialog.findViewById(R.id.btnTakePhotoCustomer);
                            btnSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isTakePict = false;
                                    dialog.dismiss();
                                    initPermission(TAKE_CAMERA_PENGAJUAN_2);
                                }
                            });
                            dialog.show();

                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    isTakePict = false;
                                }
                            };
                            handler.postDelayed(runnable, 1000);
                        }
                        break;
                    case R.id.img_take_picture_3:
                        initPermission(TAKE_CAMERA_PENGAJUAN_3);
                        break;
                    case R.id.img_take_picture_4:
                        initPermission(TAKE_CAMERA_PENGAJUAN_4);
                        break;
                    case R.id.img_take_picture_5:
                        initPermission(TAKE_CAMERA_PENGAJUAN_5);
                        break;
                    case R.id.img_take_picture_6:
                        initPermission(TAKE_CAMERA_PENGAJUAN_6);
                        break;
                    case R.id.img_take_picture_7:
                        initPermission(TAKE_CAMERA_PENGAJUAN_7);
                        break;
                    case R.id.img_take_picture_8:
                        initPermission(TAKE_CAMERA_PENGAJUAN_8);
                        break;
                    case R.id.img_take_picture_9:
                        initPermission(TAKE_CAMERA_PENGAJUAN_9);
                        break;
                    case R.id.img_take_picture_10:
                        initPermission(TAKE_CAMERA_PENGAJUAN_10);
                        break;
                    case R.id.img_take_picture_11:
                        initPermission(TAKE_CAMERA_PENGAJUAN_11);
                        break;
                    case R.id.img_take_picture_12:
                        initPermission(TAKE_CAMERA_PENGAJUAN_12);
                        break;
                    case R.id.img_take_picture_13:
                        initPermission(TAKE_CAMERA_PENGAJUAN_13);
                        break;
                    case R.id.img_take_picture_14:
                        initPermission(TAKE_CAMERA_PENGAJUAN_14);
                        break;
                    case R.id.img_take_picture_15:
                        initPermission(TAKE_CAMERA_PENGAJUAN_15);
                        break;
                    case R.id.img_take_picture_16:
                        initPermission(TAKE_CAMERA_PENGAJUAN_16);
                        break;
                    case R.id.img_take_picture_17:
                        initPermission(TAKE_CAMERA_PENGAJUAN_17);
                        break;
                    case R.id.img_take_picture_18:
                        initPermission(TAKE_CAMERA_PENGAJUAN_18);
                        break;
                    case R.id.img_take_picture_19:
                        initPermission(TAKE_CAMERA_PENGAJUAN_19);
                        break;
                    case R.id.img_take_picture_20:
                        initPermission(TAKE_CAMERA_PENGAJUAN_20);
                        break;
                    case R.id.img_take_picture_21:
                        initPermission(TAKE_CAMERA_PENGAJUAN_21);
                        break;
                    case R.id.img_take_picture_22:
                        initPermission(TAKE_CAMERA_PENGAJUAN_22);
                        break;
                    case R.id.img_take_picture_23:
                        initPermission(TAKE_CAMERA_PENGAJUAN_23);
                        break;
                    case R.id.img_take_picture_24:
                        initPermission(TAKE_CAMERA_PENGAJUAN_24);
                        break;
                    case R.id.img_take_picture_25:
                        initPermission(TAKE_CAMERA_PENGAJUAN_25);
                        break;
                    case R.id.img_take_picture_26:
                        initPermission(TAKE_CAMERA_PENGAJUAN_26);
                        break;
                    case R.id.img_take_picture_27:
                        initPermission(TAKE_CAMERA_PENGAJUAN_27);
                        break;
                    case R.id.img_take_picture_28:
                        initPermission(TAKE_CAMERA_PENGAJUAN_28);
                        break;
                    case R.id.img_take_picture_29:
                        initPermission(TAKE_CAMERA_PENGAJUAN_29);
                        break;
                }
            }
        }
    }

    @OnClick({R.id.img_delete_picture_1,
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
            R.id.img_delete_picture_25,
            R.id.img_delete_picture_26,
            R.id.img_delete_picture_27,
            R.id.img_delete_picture_28,
            R.id.img_delete_picture_29})
    public void onClickDeletePhoto(View view) {
        if (!form.equalsIgnoreCase("Edit")) {
            switch (view.getId()) {
                case R.id.img_delete_picture_1:
                    imgTakePicture1.setImageResource(0);
                    imgDeletePicture1.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(0);
                    break;
                case R.id.img_delete_picture_2:
                    imgTakePicture2.setImageResource(0);
                    imgDeletePicture2.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(1);
                    break;
                case R.id.img_delete_picture_3:
                    imgTakePicture3.setImageResource(0);
                    imgDeletePicture3.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(2);
                    break;
                case R.id.img_delete_picture_4:
                    imgTakePicture4.setImageResource(0);
                    imgDeletePicture4.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(3);
                    break;
                case R.id.img_delete_picture_5:
                    imgTakePicture5.setImageResource(0);
                    imgDeletePicture5.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(4);
                    break;
                case R.id.img_delete_picture_6:
                    imgTakePicture6.setImageResource(0);
                    imgDeletePicture6.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(5);
                    break;
                case R.id.img_delete_picture_7:
                    imgTakePicture7.setImageResource(0);
                    imgDeletePicture7.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(6);
                    break;
                case R.id.img_delete_picture_8:
                    imgTakePicture8.setImageResource(0);
                    imgDeletePicture8.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(7);
                    break;
                case R.id.img_delete_picture_9:
                    imgTakePicture9.setImageResource(0);
                    imgDeletePicture9.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(8);
                    break;
                case R.id.img_delete_picture_10:
                    imgTakePicture10.setImageResource(0);
                    imgDeletePicture10.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(9);
                    break;
                case R.id.img_delete_picture_11:
                    imgTakePicture11.setImageResource(0);
                    imgDeletePicture11.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(10);
                    break;
                case R.id.img_delete_picture_12:
                    imgTakePicture12.setImageResource(0);
                    imgDeletePicture12.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(11);
                    break;
                case R.id.img_delete_picture_13:
                    imgTakePicture13.setImageResource(0);
                    imgDeletePicture13.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(12);
                    break;
                case R.id.img_delete_picture_14:
                    imgTakePicture14.setImageResource(0);
                    imgDeletePicture14.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(13);
                    break;
                case R.id.img_delete_picture_15:
                    imgTakePicture15.setImageResource(0);
                    imgDeletePicture15.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(14);
                    break;
                case R.id.img_delete_picture_16:
                    imgTakePicture16.setImageResource(0);
                    imgDeletePicture16.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(15);
                    break;
                case R.id.img_delete_picture_17:
                    imgTakePicture17.setImageResource(0);
                    imgDeletePicture17.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(16);
                    break;
                case R.id.img_delete_picture_18:
                    imgTakePicture18.setImageResource(0);
                    imgDeletePicture18.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(17);
                    break;
                case R.id.img_delete_picture_19:
                    imgTakePicture19.setImageResource(0);
                    imgDeletePicture19.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(18);
                    break;
                case R.id.img_delete_picture_20:
                    imgTakePicture20.setImageResource(0);
                    imgDeletePicture20.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(19);
                    break;
                case R.id.img_delete_picture_21:
                    imgTakePicture21.setImageResource(0);
                    imgDeletePicture21.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(20);
                    break;
                case R.id.img_delete_picture_22:
                    imgTakePicture22.setImageResource(0);
                    imgDeletePicture22.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(21);
                    break;
                case R.id.img_delete_picture_23:
                    imgTakePicture23.setImageResource(0);
                    imgDeletePicture23.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(22);
                    break;
                case R.id.img_delete_picture_24:
                    imgTakePicture24.setImageResource(0);
                    imgDeletePicture24.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(23);
                    break;
                case R.id.img_delete_picture_25:
                    imgTakePicture25.setImageResource(0);
                    imgDeletePicture25.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(24);
                    break;
                case R.id.img_delete_picture_26:
                    imgTakePicture26.setImageResource(0);
                    imgDeletePicture26.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(24);
                    break;
                case R.id.img_delete_picture_27:
                    imgTakePicture27.setImageResource(0);
                    imgDeletePicture27.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(24);
                    break;
                case R.id.img_delete_picture_28:
                    imgTakePicture28.setImageResource(0);
                    imgDeletePicture28.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(24);
                    break;
                case R.id.img_delete_picture_29:
                    imgTakePicture29.setImageResource(0);
                    imgDeletePicture29.setVisibility(View.GONE);
                    mHashMapAttachmentFiles.remove(24);
                    break;
            }
        }
    }

    private void showTokenDialog() {
        DialogFragment dialog = new CodePersetujuanDialog();
        Bundle bundle = new Bundle();
        String statSensorPhoneNumber;
        if (edtHandphonePribadi.getVisibility() == View.GONE) statSensorPhoneNumber = "1";
        else statSensorPhoneNumber = "0";
        bundle.putString("NOMER_HANDPHONE", edtHandphonePribadi.getText().toString());
        bundle.putInt("COUNT", countSignature);
        bundle.putString("STAT_PHONE_NUMBER", statSensorPhoneNumber);
        dialog.setArguments(bundle);
        dialog.setCancelable(true);
        dialog.show(getSupportFragmentManager(), "CodePersetujuanDialog");
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
//                if ("".equalsIgnoreCase(applicationIdKpm)) {
//                    viewDeleteImages.get(finalI).setVisibility(View.VISIBLE);
//                } else {
//                    viewDeleteImages.get(finalI).setVisibility(View.GONE);
//                }
//                Toast.makeText(getContext(), R.string.warning_berhasil_load_gambar, Toast.LENGTH_SHORT).show();
//                isImageError = false;
//            }
//        });

    }

    @Override
    public void onPreBlackList() {
        preLoading();
    }

    @Override
    public void onSuccessBlackList(final BlackListResponse blackListResponse, List<String> fullNameList, List<ApplicationBlacklist> applicationBlacklist) {
        this.blackListResponse = blackListResponse;

        bucketMessage = blackListResponse.getBucketMessage();
        agreement = blackListResponse.getNoAgreement();
        timeDelay = blackListResponse.getTimeDelay();
        amountOfFines = blackListResponse.getAmountOfFines();
        AOSalesStatus = blackListResponse.getAOSalesStatus();

        if (blackListResponse.getIsBlackList() == 0) {
            if (form.equalsIgnoreCase("New")) {
                if (blackListResponse.getApplicationType() != null) {
                    if (tipePengajuanArrayLists != null && !tipePengajuanArrayLists.isEmpty()) {
                        tipePengajuanArrayLists.clear();
                        tipePengajuanAdapter = new ArrayAdapter<TipePengajuanArrayList>(this, R.layout.item_dropdown, R.id.id_item, tipePengajuanArrayLists);
                        spnValidasiTipePengajuan.setAdapter(tipePengajuanAdapter);
                    }
                    tipePengajuanArrayLists.add(new TipePengajuanArrayList("PILIH"));
                    for (int j = 0; j < blackListResponse.getApplicationType().size(); j++) {
                        tipePengajuanArrayLists.add(new TipePengajuanArrayList(blackListResponse.getApplicationType().get(j).getType()));
                    }
                    tipePengajuanAdapter = new ArrayAdapter<TipePengajuanArrayList>(this, R.layout.item_dropdown, R.id.id_item, tipePengajuanArrayLists);
                    spnValidasiTipePengajuan.setAdapter(tipePengajuanAdapter);
//                    spnValidasiTipePengajuan.setSelection(getIndex(spnValidasiTipePengajuan, application.getKorpFormulir().getTypeForm()));
//                    spnKopJenisPembiayaan.setSelection(getIndex(spnKopJenisPembiayaan, application.getKorpFormulir().getTypeOfFinancing()));
                }
            }

            if (!form.equalsIgnoreCase("Edit")) {
                blackListDate = blackListResponse.getDateStart();
                mobileSubmissionKey = blackListResponse.getMobileSubmissionKey();

                countSignature = 0;
                uuid = Util.RandomDateNumber();

                if (blackListResponse.getIsEmptyData() == 1 || blackListResponse.getIsNew() == 1) {
                    strStatusPengajuan = "Baru";
                    spnMetodePenjualan.setSelection(1);
                } else {
                    strStatusPengajuan = "RO";
                    spnMetodePenjualan.setSelection(2);
                }

                if (AOSalesStatus.equals("D")) {
                    spnMetodePenjualan.setSelection(1);
                } else {
                    spnMetodePenjualan.setSelection(2);
                }

                edtKopStatusPemohon.setText(strStatusPengajuan);

                if (blackListResponse.getIsEmptyData() == 0 && blackListResponse.getIsNew() == 1) {
                    dataBlackList(blackListResponse, fullNameList, applicationBlacklist);
                } else if (blackListResponse.getIsEmptyData() == 0 && blackListResponse.getIsNew() == 0) {
                    dataBlackList(blackListResponse, fullNameList, applicationBlacklist);
                } else {
                    edtKopStatusKreditmu.setTextColor(Color.GRAY);
                    customerIdConfins = "";
                }
                edtKopStatusKreditmu.setText(blackListResponse.getStatusKreditmu());
                if (blackListResponse.getStatusKreditmu().equalsIgnoreCase("Non Kreditmu")) {
                    statusKreditmu = "NONKREDITMU";
                } else {
                    statusKreditmu = "KREDITMU";
                }

                if (form.equalsIgnoreCase("New")) {
                    if (blackListResponse.getStatusKreditmu().equalsIgnoreCase("Non Kreditmu")) {
                        edtPribadiNoKtp.setText(edtValidasiNoKtp.getText().toString());
                        edtPribadiTanggalLahirPribadi.setText(edtValidasiTanggalLahir.getText().toString());
                        edtHandphonePribadi.setText(edtValidasiNoHp.getText().toString());
                        edtNamaIbuPribadi.setText(edtValidasiNamaIbuKandung.getText().toString());
                        edtPribadiNamaPemohon.setText(edtValidasiNamaLegal.getText().toString());

                        edtNamaIbuPribadi.setEnabled(false);
                        edtNamaIbuPribadi.setTextColor(Color.GRAY);

                        edtPribadiNamaPemohon.setEnabled(false);
                        edtPribadiNamaPemohon.setTextColor(Color.GRAY);
                    } else {
                        edtPribadiNoKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getiDNumber());
                        edtPribadiTanggalLahirPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getBirthDate());
                        edtHandphonePribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMobilePhone().trim());
                        edtNamaIbuPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getSurgateMotherName());
                        edtPribadiNamaPemohon.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getLegalName());
                    }
                }
            }else{
                if (AOSalesStatus.equals("D")) {
                    spnMetodePenjualan.setSelection(1);
                } else {
                    spnMetodePenjualan.setSelection(2);
                }
            }

            mWilayahCabangPresenter.GetWilayahCabang(token, aoBranch);
        } else {
            successAndFailedLoading();

            DialogFragment dialogFragment = new BlacklistDialog();
            Bundle args = new Bundle();
            args.putString("bucketMessage", bucketMessage);
            args.putString("agreement", agreement);
            args.putString("timeDelay", timeDelay);
            args.putString("amountOfFines", amountOfFines);
            dialogFragment.setArguments(args);
            dialogFragment.show(getSupportFragmentManager(), "blacklistDialog");
        }
    }

    @Override
    public void onFailedBlackList(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
    }

    @Override
    public void onTokenBlackListExpired() {
        successAndFailedLoading();
    }


    private void dataBlackList(final BlackListResponse blackListResponse, List<String> fullNameList, List<ApplicationBlacklist> applicationBlacklist) {
        edtHandphonePribadi.setEnabled(false);
        edtPribadiNamaPemohon.setEnabled(false);
        edtPribadiNamaLengkapPemohon.setEnabled(false);
        edtPribadiNoKtp.setEnabled(false);
        edtNamaIbuPribadi.setEnabled(false);
        edtPribadiTanggalLahirPribadi.setEnabled(false);
        edtPribadiTempatLahir.setEnabled(false);
        rgPribadiJenisKelamin.setEnabled(false);
        rbPribadiLaki.setEnabled(false);
        rbPribadiPerempuan.setEnabled(false);
//        spnPribadiStatusPernikahan.setEnabled(false);
        edtJmlTanggunganPribadi.setEnabled(false);
        spnPendidikanPribadi.setEnabled(false);
        checkStatusPernikahan();


        edtPribadiNamaPemohon.setTextColor(Color.GRAY);
        edtPribadiNamaLengkapPemohon.setTextColor(Color.GRAY);
        edtPribadiNoKtp.setTextColor(Color.GRAY);
        edtNamaIbuPribadi.setTextColor(Color.GRAY);
        edtPribadiTanggalLahirPribadi.setTextColor(Color.GRAY);
        edtPribadiTempatLahir.setTextColor(Color.GRAY);
        edtHandphonePribadi.setTextColor(Color.GRAY);
        edtJmlTanggunganPribadi.setTextColor(Color.GRAY);

        customerIdConfins = blackListResponse.getApplicationBlacklists().get(0).getCustomerIdConfins();

        //Data Pribadi
        if (form.equalsIgnoreCase("New")) {
            edtPribadiNamaPemohon.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getLegalName().replace(".", " ").replace("\"", " ").replace(",", " ").replace("'", " ").replace("-", " "));
            edtPribadiNamaLengkapPemohon.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getFullName().replace(".", " ").replace("\"", " ").replace(",", " ").replace("'", " ").replace("-", " "));
            edtPribadiNoKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getiDNumber());
            edtNoNpwpDetail.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getPersonalNPWP());
            edtPribadiTempatLahir.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getBirthPlace());
            edtHandphonePribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMobilePhone().trim());
            edtNamaIbuPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getSurgateMotherName());
            edtTinggalSejakTahunPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getStaySinceYear());
            edtTinggalSejakBulanPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getStaySinceMonth());
            edtPribadiTanggalLahirPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getBirthDate());
            edtEmailPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getEmail());
            edtTerbitKtpPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getiDTypeIssuedDate());
            edtJmlTanggunganPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getNumOfDependence().trim().isEmpty() ? "0" :
                    blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getNumOfDependence());

            String strJenisKelaminBlackList = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getGender();
            if (strJenisKelaminBlackList.equalsIgnoreCase("M")) rbPribadiLaki.setChecked(true);
            else rbPribadiPerempuan.setChecked(true);

            for (int h = 0; h < statusPernikahanValue.length; h++) {
                if (statusPernikahanValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPribadiStatusPernikahan.setSelection(h);
                    break;
                } else if (statusPernikahanOri[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPribadiStatusPernikahan.setSelection(h);
                    break;
                } else if (blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda") || blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda / Duda")) {
                    spnPribadiStatusPernikahan.setSelection(4);
                    break;
                }
            }

            for (int h = 0; h < pendidikanValue.length; h++) {
                if (pendidikanValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getEducation().replaceAll("\\s", ""))) {
                    spnPendidikanPribadi.setSelection(h);
                    break;
                }
            }

            for (int h = 0; h < statusRumahValue.length; h++) {
                if (statusRumahValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getHomeStatus())) {
                    spnStatusRumahPribadi.setSelection(h);
                    break;
                }
            }

            for (int h = 0; h < agamaValue.length; h++) {
                if (agamaValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getReligion())) {
                    spnAgamaPribadi.setSelection(h);
                    break;
                }
            }

            //Alamat pribadi
            edtAlamatTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAddress());

            edtAreaPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAreaPhone());
            edtPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getPhone());

            //Alamat Tinggal
            edtAlamatTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAddress());
            if (!blackListResponse.getApplicationBlacklists().get(0).getResidance().getrT().isEmpty()) {
                String valueRt = blackListResponse.getApplicationBlacklists().get(0).getResidance().getrT();
                String replaceRt = "";
                String replaceRt2 = "";

                if (valueRt.startsWith("0")) {
                    replaceRt = valueRt.substring(1);
                    if (replaceRt.startsWith("0")) {
                        replaceRt2 = replaceRt.substring(1);
                    } else {
                        replaceRt2 = replaceRt;
                    }

//                    edtRtKtp.setText(replaceRt2);
                    edtRtTinggal.setText(replaceRt2.replaceAll(" ", ""));
                } else {
//                    edtRtKtp.setText(valueRt);
                    edtRtTinggal.setText(valueRt.replaceAll(" ", ""));
                }
            }

            if (!blackListResponse.getApplicationBlacklists().get(0).getLegal().getrT().isEmpty()) {
                String valueRt = blackListResponse.getApplicationBlacklists().get(0).getLegal().getrT();
                String replaceRt = "";
                String replaceRt2 = "";

                if (valueRt.startsWith("0")) {
                    replaceRt = valueRt.substring(1);
                    if (replaceRt.startsWith("0")) {
                        replaceRt2 = replaceRt.substring(1);
                    } else {
                        replaceRt2 = replaceRt;
                    }

                    edtRtKtp.setText(replaceRt2.replaceAll(" ", ""));
//                    edtRtTinggal.setText(replaceRt2);
                } else {
                    edtRtKtp.setText(valueRt.replaceAll(" ", ""));
//                    edtRtTinggal.setText(valueRt);
                }
            }

            if (!blackListResponse.getApplicationBlacklists().get(0).getResidance().getrW().isEmpty()) {
                String valueRw = blackListResponse.getApplicationBlacklists().get(0).getResidance().getrW();
                String replaceRw = "";
                String replaceRw2 = "";
                if (valueRw.startsWith("0")) {
                    replaceRw = valueRw.substring(1);
                    if (replaceRw.startsWith("0")) {
                        replaceRw2 = replaceRw.substring(1);
                    } else {
                        replaceRw2 = replaceRw;
                    }
                    edtRwTinggal.setText(replaceRw2.replaceAll(" ", ""));
                } else {
                    edtRwTinggal.setText(valueRw.replaceAll(" ", ""));
                }

//                edtRwKtp.setText(replaceRw2);

            }

            if (!blackListResponse.getApplicationBlacklists().get(0).getLegal().getrW().isEmpty()) {
                String valueRw = blackListResponse.getApplicationBlacklists().get(0).getLegal().getrW();
                String replaceRw = "";
                String replaceRw2 = "";
                if (valueRw.startsWith("0")) {
                    replaceRw = valueRw.substring(1);
                    if (replaceRw.startsWith("0")) {
                        replaceRw2 = replaceRw.substring(1);
                    } else {
                        replaceRw2 = replaceRw;
                    }
                    edtRwKtp.setText(replaceRw2.replaceAll(" ", ""));
                } else {
                    edtRwKtp.setText(valueRw.replaceAll(" ", ""));
                }

//                edtRwKtp.setText(replaceRw2);

            }

            edtAreaPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAreaPhone());
            edtPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getPhone());

            //Alamat Ktp
            edtAlamatKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getAddress());
            edtAreaPhoneKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getAreaPhone());
            edtPhoneKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getPhone());
            actAutoAlamatPemohon.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getCity());
            actAutoKtpAlamatPemohon.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getCity());
            actAutoAlamatKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getCity());
            actAutoAlamatPemohon.setSelectionFromPopUp(true);
            actAutoKtpAlamatPemohon.setSelectionFromPopUp(true);
            //Informasi contact darurat
            edtNamaKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getName());
            edtAlamatKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getAddress());
            edtRwKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getrW().replaceAll(" ", ""));
            edtRtKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getrT().replaceAll(" ", ""));
//            spnHubunganKerabat
            /*edtNamaKerabat.setText(application.getEmergencyContact().getName());
            for (int h = 0; h < hubunganKerabatOri.length; h++) {
                if (hubunganKerabatOri[h].equalsIgnoreCase(application.getEmergencyContact().getRelationship())) {
                    spnHubunganKerabat.setSelection(h);
                    break;
                }
            }*/
            edtAreaPhoneRumahKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getHomePhoneArea());
            edtPhoneRumahKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getHomePhone());
            edtAreaPhoneKantorKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getOfficePhoneArea());
            edtPhoneKantorKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getOfficePhone());
            edtHpKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getMobilePhone());
            actAutoAlamatKerabat.setSelectionFromPopUp(true);

            //Data Pekerjaan
            edtNamaPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getName());
            edtAlamatPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getAddress());
            edtRtPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getrT().replaceAll(" ", ""));
            edtRwPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getrW().replaceAll(" ", ""));
            edtBekerjaSejakPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getEmploymentSinceYear());
            professionKode = blackListResponse.getApplicationBlacklists().get(0).getCompany().getProfessionID();
            jobTypeKode = blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobTypeID();
            jobPositionKode = blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobPositionID();

            actAutoAlamatPekerjaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getCity());
            actProfesiPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getProfessionName());
            if (actProfesiPerusahaan.getText().toString().equalsIgnoreCase("WIRASWASTA")) {
                lnDataPekerjaanPhone.setVisibility(View.GONE);
//                edtAreaPhonePerusahaan.setText("");
//                    edtPhonePerusahaan.setVisibility(View.VISIBLE);
                edtPekerjaanNoHp.setVisibility(View.VISIBLE);

//                edtPhonePerusahaan.setText("");
//                edtPekerjaanNoHp.setText("");

                validator.removeRules(edtAreaPhonePerusahaan);
                validator.removeRules(edtPhonePerusahaan);

                tilPekerjaanNoHp.setVisibility(View.VISIBLE);
                edtPekerjaanNoHp.setVisibility(View.VISIBLE);
                validator.put(edtPekerjaanNoHp, noHpRule);
            } else {
                lnDataPekerjaanPhone.setVisibility(View.VISIBLE);
                validator.put(edtAreaPhonePerusahaan, kodeAreaRule);
                validator.put(edtPhonePerusahaan, notEmptyRule);
                validator.removeRules(edtPekerjaanNoHp);
//                edtPekerjaanNoHp.setText("");
                tilPekerjaanNoHp.setVisibility(View.GONE);
                edtPekerjaanNoHp.setVisibility(View.GONE);
            }

            actJobTypePerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobTypeName());
            actJobPositionPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobPositionName());
            actIndustriPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getIndustryTypeName());
            String areaPhonePerusahaan = blackListResponse.getApplicationBlacklists().get(0).getCompany().getAreaPhone().replaceAll(" ", "");
            String phonePerusahaan = blackListResponse.getApplicationBlacklists().get(0).getCompany().getPhone().replaceAll(" ", "");

            for (int h = 0; h < statusPernikahanValue.length; h++) {
                if (statusPernikahanValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPribadiStatusPernikahan.setSelection(h);
                    break;
                } else if (statusPernikahanOri[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPribadiStatusPernikahan.setSelection(h);
                    break;
                }
            }

            int zipCodePerusahaan = Integer.parseInt((blackListResponse.getApplicationBlacklists().get(0).getCompany().getZipCode().replace(" ", "0")).replaceAll(" ", ""));
            edtAreaPhonePerusahaan.setText(String.valueOf(areaPhonePerusahaan));
            edtPhonePerusahaan.setText(String.valueOf(phonePerusahaan));

            industriKode = String.valueOf(blackListResponse.getApplicationBlacklists().get(0).getCompany().getIndustryTypeID());
            edtPenghasilanTetapPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getMonthlyFixedIncome());
            edtPenghasilanLainPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getMonthlyVariableIncome());
            edtPenghasilanPasanganPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getSpouseIncome());
            edtBiayaHidupPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getLivingCostAmount());
            actAutoAlamatPekerjaan.setSelectionFromPopUp(true);
            actProfesiPerusahaan.setSelectionFromPopUp(true);
            actJobTypePerusahaan.setSelectionFromPopUp(true);
            actJobPositionPerusahaan.setSelectionFromPopUp(true);
            actIndustriPerusahaan.setSelectionFromPopUp(true);
        }
    }

    private void initTextWatcher() {
        initTextWatcherDataPribadi();
        initTextWatcherDataAlamat();
        initTextWatcherDataPasangan();
        initTextWatcherDataKerabat();
        initTextWatcherDataPekerjaan();
        initTextWatcherDataProduct();
        initTextWatcherDataAgunan();
        initTextWatcherDataKeterangan();
        initTextWatcherDataRekomendasi();
        firstInitFlag = false;
    }

    private void initTextWatcherDataRekomendasi() {
        edtDescRecomendation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtDescRecomendation.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initTextWatcherDataKeterangan() {
        edtKeterangan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtKeterangan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initTextWatcherDataAgunan() {
        actTypeKendaraan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actTypeKendaraan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtWarnaKendaraan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtWarnaKendaraan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtIsiSilinder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtIsiSilinder.setError("Format tidak sesuai");
                } else {
                    edtIsiSilinder.setError(null);
                }
            }
        });
        edtNoPolisi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNoPolisi.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtNoRangka.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNoRangka.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtNoMesin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNoMesin.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtNamaBpkbSendiri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNamaBpkbSendiri.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtNamaBpkbPasangan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNamaBpkbPasangan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtNamaBpkbOrangLain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNamaBpkbOrangLain.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initTextWatcherDataProduct() {
        actDtProductSupplier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actDtProductSupplier.getText().clear();
                }

                if (strDescription.length() >= 2) {
                    if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                        if (isAssignEdit == null || isAssignEdit.isEmpty()) {
                            Log.d("KOP :", "1");
                            Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
//                if(supplierNameArrayList.get(0).getSupplierName().isEmpty() || supplierNameArrayList.get(0).getSupplierName().equals("")){
//                    Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actDtProductMarketingSupplier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actDtProductMarketingSupplier.getText().clear();
                }
                if (strDescription.length() >= 2) {
                    if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                        Log.d("KOP :", "2");
                        Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actDtProductOffering.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actDtProductOffering.getText().clear();
                }

                if (strDescription.length() >= 2) {
                    if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                        Log.d("KOP :", "3");
                        Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }

                if (!firstInitFlag) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actDtProductPos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actDtProductPos.getText().clear();
                }

                if (strDescription.length() >= 2) {
                    if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                        Log.d("KOP :", "4");
                        Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void initTextWatcherDataPekerjaan() {
        edtNamaPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNamaPerusahaan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtAlamatPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtAlamatPerusahaan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtRtPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRtPerusahaan.setError("Format tidak sesuai");
                } else {
                    edtRtPerusahaan.setError(null);
                }
            }
        });
        edtRwPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRwPerusahaan.setError("Format tidak sesuai");
                } else {
                    edtRwPerusahaan.setError(null);
                }
            }
        });
        actAutoAlamatPekerjaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPasanganNama.getText().clear();
                } else if (strDescription.length() >= 3) {
                    setRemoveCallbacksRunnable();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!actAutoAlamatPekerjaan.isSelectionFromPopUp())
                                mKelurahanPresenter.GetKelurahanFilter("actAutoAlamatPekerjaan", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 1500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        actProfesiPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actProfesiPerusahaan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actJobTypePerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actJobTypePerusahaan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actJobPositionPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actJobPositionPerusahaan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actIndustriPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actIndustriPerusahaan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPenghasilanTetapPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtPenghasilanTetapPerusahaan.setError("Format tidak sesuai");
                } else {
                    edtPenghasilanTetapPerusahaan.setError(null);
                }
            }
        });
        edtPenghasilanLainPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtPenghasilanLainPerusahaan.setError("Format tidak sesuai");
                } else {
                    edtPenghasilanLainPerusahaan.setError(null);
                }
            }
        });
        edtPenghasilanPasanganPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtPenghasilanPasanganPerusahaan.setError("Format tidak sesuai");
                } else {
                    edtPenghasilanPasanganPerusahaan.setError(null);
                }
            }
        });
        edtBiayaHidupPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtBiayaHidupPerusahaan.setError("Format tidak sesuai");
                } else {
                    edtBiayaHidupPerusahaan.setError(null);
                }
            }
        });
    }

    private void initTextWatcherDataKerabat() {
        edtNamaKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNamaKerabat.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtAlamatKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtAlamatKerabat.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtRtKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRtKerabat.setError("Format tidak sesuai");
                } else {
                    edtRtKerabat.setError(null);
                }
            }
        });
        edtRwKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRwKerabat.setError("Format tidak sesuai");
                } else {
                    edtRwKerabat.setError(null);
                }
            }
        });
        actAutoAlamatKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPasanganNama.getText().clear();
                } else if (strDescription.length() >= 3) {
                    setRemoveCallbacksRunnable();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!actAutoAlamatKerabat.isSelectionFromPopUp())
                                mKelurahanPresenter.GetKelurahanFilter("actAutoAlamatKerabat", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 1500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initTextWatcherDataPasangan() {
        edtPasanganNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPasanganNama.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPasanganTempatLahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPasanganTempatLahir.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPasanganAlamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPasanganAlamat.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actPasanganKota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actPasanganKota.getText().clear();
                } else if (strDescription.length() >= 3) {
                    setRemoveCallbacksRunnable();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!actPasanganKota.isSelectionFromPopUp())
                                mKelurahanPresenter.GetKelurahanFilter("actPasanganKota", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 1500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        actPasanganProfesi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actPasanganProfesi.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        actPasanganJobType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actPasanganJobType.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        actPasanganJobPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actPasanganJobPosition.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        actPasanganIndustri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actPasanganIndustri.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtPasanganNamaPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPasanganNamaPerusahaan.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtPasanganNamaIbu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPasanganNamaIbu.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initTextWatcherDataAlamat() {
        edtAlamatKtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtAlamatKtp.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtRtKtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRtKtp.setError("Format tidak sesuai");
                } else {
                    edtRtKtp.setError(null);
                }
            }
        });
        edtRwKtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRwKtp.setError("Format tidak sesuai");
                } else {
                    edtRwKtp.setError(null);
                }
            }
        });
        actAutoKtpAlamatPemohon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actAutoKtpAlamatPemohon.getText().clear();
                } else if (strDescription.length() >= 3) {
                    setRemoveCallbacksRunnable();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!actAutoKtpAlamatPemohon.isSelectionFromPopUp())
                                mKelurahanPresenter.GetKelurahanFilter("actAutoKtpAlamatPemohon", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 1500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
//        data tinggal
        edtAlamatTinggal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtAlamatTinggal.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtRtTinggal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRtTinggal.setError("Format tidak sesuai");
                } else {
                    edtRtTinggal.setError(null);
                }
            }
        });
        edtRwTinggal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtRwTinggal.setError("Format tidak sesuai");
                } else {
                    edtRwTinggal.setError(null);
                }
            }
        });
        actAutoAlamatPemohon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    actAutoAlamatPemohon.getText().clear();
                } else if (strDescription.length() >= 3) {
                    setRemoveCallbacksRunnable();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (actAutoAlamatPemohon != null) {
                                if (!actAutoAlamatPemohon.isSelectionFromPopUp()) {
                                    mKelurahanPresenter.GetKelurahanFilter("actAutoAlamatPemohon", token, strDescription);
                                }
                            }
                        }
                    };
                    handler.postDelayed(runnable, 1500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initTextWatcherDataPribadi() {
        edtPribadiNamaPemohon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPribadiNamaPemohon.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPribadiNamaLengkapPemohon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPribadiNamaLengkapPemohon.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPribadiTempatLahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtPribadiTempatLahir.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtJmlTanggunganPribadi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strDescription = s.toString();
                if (strDescription.startsWith("0") && strDescription.length() >= 2) {
                    s.clear();
                    edtJmlTanggunganPribadi.setError("Format tidak sesuai");
                } else {
                    edtJmlTanggunganPribadi.setError(null);
                }
            }
        });
        edtEmailPribadi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtEmailPribadi.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtNamaIbuPribadi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String strDescription = charSequence.toString();
                if (strDescription.startsWith(" ")) {
                    edtNamaIbuPribadi.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onPreSendCodeSignature() {
    }

    @Override
    public void onSuccessSendCodeSignature(int count) {
        if (isFinishing()) return;
    }

    @Override
    public void onFailedSendCodeSignature(String message) {
        messageFailedApi(message);
    }

    @Override
    public void onPreSendConfirmSignature() {
    }

    @Override
    public void onSuccessConfirmCodeSignature(int isValid, String message, String usedOTP) {
        successAndFailedLoading();
        if (isValid == 1) {
            isUserValid = true;
            messageFailedApi(message);
            this.usedOTP = usedOTP;
        } else {
            isUserValid = false;
            messageFailedApi(message);
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                isConfirmSignature = false;
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onFailedConfirmCodeSignature(String message) {
        successAndFailedLoading();
        isUserValid = false;
        messageFailedApi(message);
        runnable = new Runnable() {
            @Override
            public void run() {
                isConfirmSignature = false;
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onTokenSendCodeSignatureExpired() {
    }

    @Override
    public void onTokenConfirmCodeSignatureExpired() {
        successAndFailedLoading();
        runnable = new Runnable() {
            @Override
            public void run() {
                isConfirmSignature = false;
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    @Subscribe
    public void confirmCodeSignature(ConfirmCodeSignature e) {
        preLoading();
        isConfirmSignature = true;
        mCodeSignaturePresenter.confirmCodeSignature(token, "signature", e.getCode(), mobileSubmissionKey, uuid);
    }

    @Subscribe
    public void dialogSayaratBus(DialogSyaratBus e) {
        DialogFragment dialogCancel = new DialogPersyaratan();
        Bundle bundleSyarat2 = new Bundle();
        bundleSyarat2.putString("DESCRIPTION_SYARAT", descriptionSyarat);
        dialogCancel.setArguments(bundleSyarat2);
        dialogCancel.show(getSupportFragmentManager(), "DialogSyarat");
    }

    @Subscribe
    public void onResendCodeSignatureEvent(ResendCodeSignatureEvent e) {
        showTokenDialog();
        sendPriceToGetToken();
        countSignature += 1;
    }

    private void sendPriceToGetToken() {
        BodyToken bodyToken = new BodyToken();

        List<AssetToken> assetTokenList = new ArrayList<>();
        AssetToken assetToken = new AssetToken();
        assetToken.setAssetKode(idTipeKendaraan);
        assetToken.setType(spnJenisKendaraan.getSelectedItem().toString());
        assetTokenList.add(assetToken);

        bodyToken.setMobileSubmision(mobileSubmissionKey);
        bodyToken.setType("signature");
        bodyToken.setHandphone(edtHandphonePribadi.getText().toString());
        bodyToken.setFullName(edtPribadiNamaLengkapPemohon.getText().toString());
        bodyToken.setInstallmentAmount(edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().replace(",", ""));
        bodyToken.setApplicationPid(uuid);
        bodyToken.setProcessType(isAssignEdit != null && isAssignEdit.equals("isAssignEdit") ? "edit" : "create");
        bodyToken.setAssetTokenList(assetTokenList);
        mCodeSignaturePresenter.sendCodeSignature(token, bodyToken);
    }

    @Subscribe
    public void setCropPhotoEvent(CropPhotoEvent e) {
        Uri tempUri = Util.getImageUri(this, e.getBitmap());
        File fileUri = new File(Util.getRealPathFromURI(this, tempUri));
        long fileLength = fileUri.length() / 1024;
        Util.checkSizeImage(e.getTipeImg(), tempUri);
        Log.i("Photo_Bitmap", String.valueOf(e.getBitmap()));
        Log.i("Photo_Uri", String.valueOf(tempUri));
        Log.i("Photo_File", String.valueOf(fileUri));
        if (Constant.Flag.IS_IMAGE_TO_ALL) setImageToImageView(e);
        else {
            switch (e.getTipeImg()) {
                case TAKE_CAMERA_PENGAJUAN_1:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture1);
                    imgDeletePicture1.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(0, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_1", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_2:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture2);
                    imgDeletePicture2.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(1, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_2", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_3:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture3);
                    imgDeletePicture3.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(2, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_3", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_4:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture4);
                    imgDeletePicture4.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(3, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_4", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_5:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture5);
                    imgDeletePicture5.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(4, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_5", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_6:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture6);
                    imgDeletePicture6.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(5, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_6", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_7:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture7);
                    imgDeletePicture7.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(6, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("TAKE_CAMERA_PENGAJUAN_7", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_8:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture8);
                    imgDeletePicture8.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(7, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_8", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_9:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture9);
                    imgDeletePicture9.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(8, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_9", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_10:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture10);
                    imgDeletePicture10.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(9, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_10", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_11:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture11);
                    imgDeletePicture11.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(10, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_11", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_12:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture12);
                    imgDeletePicture12.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(11, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_12", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_13:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture13);
                    imgDeletePicture13.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(12, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_13", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_14:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture14);
                    imgDeletePicture14.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(13, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_14", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_15:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture15);
                    imgDeletePicture15.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(14, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_15", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_16:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture16);
                    imgDeletePicture16.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(15, fileUri);
                    Util.saveGallery(this, fileUri);

                    Log.d("size_img_16", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_17:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture17);
                    imgDeletePicture17.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(16, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_17", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_18:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture18);
                    imgDeletePicture18.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(17, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_18", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_19:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture19);
                    imgDeletePicture19.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(18, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_19", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_20:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture20);
                    imgDeletePicture20.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(19, fileUri);
                    Util.saveGallery(this, fileUri);

                    Log.d("size_img_20", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_21:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture21);
                    imgDeletePicture21.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(20, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_21", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_22:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture22);
                    imgDeletePicture22.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(21, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_22", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_23:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture23);
                    imgDeletePicture23.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(22, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_23", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_24:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture24);
                    imgDeletePicture24.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(23, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_24", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_25:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture25);
                    imgDeletePicture25.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(24, fileUri);
                    Util.saveGallery(this, fileUri);
                    Log.d("size_img_25", String.valueOf(fileLength));
                    break;

            }
        }
        hideValidasiAwal();
    }

    private void ImageCompressor(File fileUri, int requestcode) {
//        Uri tempUri = imageUri;
//        String fileUriImg = Util.getRealPathFromURI(this, tempUri).replace("file://", "");
//        File fileUri = new File(fileUriImg);
//        long fileLength = fileUri.length() / 1024;
//        Util.checkSizeImage(requestcode, tempUri);
//        Log.i("Photo_Bitmap", String.valueOf(e.getBitmap()));
//        Log.i("Photo_Uri", String.valueOf(tempUri));
//        Log.i("Photo_File", String.valueOf(fileUri));
        if (Constant.Flag.IS_IMAGE_TO_ALL) setImageToImageView(fileUri, requestcode);
        else {
            switch (requestcode) {
                case TAKE_CAMERA_PENGAJUAN_1:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture1);
                    imgDeletePicture1.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(0, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_1", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_2:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture2);
                    imgDeletePicture2.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(1, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_2", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_3:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture3);
                    imgDeletePicture3.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(2, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_3", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_4:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture4);
                    imgDeletePicture4.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(3, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_4", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_5:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture5);
                    imgDeletePicture5.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(4, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_5", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_6:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture6);
                    imgDeletePicture6.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(5, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_6", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_7:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture7);
                    imgDeletePicture7.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(6, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("TAKE_CAMERA_PENGAJUAN_7", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_8:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture8);
                    imgDeletePicture8.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(7, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_8", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_9:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture9);
                    imgDeletePicture9.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(8, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_9", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_10:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture10);
                    imgDeletePicture10.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(9, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_10", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_11:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture11);
                    imgDeletePicture11.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(10, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_11", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_12:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture12);
                    imgDeletePicture12.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(11, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_12", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_13:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture13);
                    imgDeletePicture13.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(12, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_13", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_14:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture14);
                    imgDeletePicture14.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(13, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_14", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_15:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture15);
                    imgDeletePicture15.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(14, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_15", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_16:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture16);
                    imgDeletePicture16.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(15, fileUri);
                    Util.saveGallery(this, fileUri);

//                    Log.d("size_img_16", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_17:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture17);
                    imgDeletePicture17.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(16, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_17", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_18:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture18);
                    imgDeletePicture18.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(17, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_18", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_19:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture19);
                    imgDeletePicture19.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(18, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_19", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_20:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture20);
                    imgDeletePicture20.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(19, fileUri);
                    Util.saveGallery(this, fileUri);

//                    Log.d("size_img_20", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_21:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture21);
                    imgDeletePicture21.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(20, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_21", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_22:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture22);
                    imgDeletePicture22.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(21, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_22", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_23:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture23);
                    imgDeletePicture23.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(22, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_23", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_24:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture24);
                    imgDeletePicture24.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(23, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_24", String.valueOf(fileLength));
                    break;
                case TAKE_CAMERA_PENGAJUAN_25:
                    Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture25);
                    imgDeletePicture25.setVisibility(View.VISIBLE);
                    mHashMapAttachmentFiles.put(24, fileUri);
                    Util.saveGallery(this, fileUri);
//                    Log.d("size_img_25", String.valueOf(fileLength));
                    break;

            }
        }
        hideValidasiAwal();
    }

    private void setImageToImageView(CropPhotoEvent e) {
        Uri tempUri = Util.getImageUri(this, e.getBitmap());
        File fileUri = new File(Util.getRealPathFromURI(this, tempUri));
        long fileLength = fileUri.length() / 1024;
        Util.checkSizeImage(e.getTipeImg(), tempUri);

        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture1);
        imgDeletePicture1.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(0, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture2);
        imgDeletePicture2.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(1, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture3);
        imgDeletePicture3.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(2, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture4);
        imgDeletePicture4.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(3, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture5);
        imgDeletePicture5.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(4, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture6);
        imgDeletePicture6.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(5, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture7);
        imgDeletePicture7.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(6, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture8);
        imgDeletePicture8.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(7, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture9);
        imgDeletePicture9.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(8, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture10);
        imgDeletePicture10.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(9, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture11);
        imgDeletePicture11.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(10, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture12);
        imgDeletePicture12.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(11, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture13);
        imgDeletePicture13.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(12, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture14);
        imgDeletePicture14.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(13, fileUri);
    }

    private void setImageToImageView(File fileUri, int requestcode) {
////        Uri tempUri = imageUri;
////        File fileUri = new File(Util.getRealPathFromURI(this, tempUri));
//        long fileLength = fileUri.length() / 1024;
//        Util.checkSizeImage(requestcode, tempUri);

        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture1);
        imgDeletePicture1.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(0, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture2);
        imgDeletePicture2.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(1, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture3);
        imgDeletePicture3.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(2, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture4);
        imgDeletePicture4.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(3, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture5);
        imgDeletePicture5.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(4, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture6);
        imgDeletePicture6.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(5, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture7);
        imgDeletePicture7.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(6, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture8);
        imgDeletePicture8.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(7, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture9);
        imgDeletePicture9.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(8, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture10);
        imgDeletePicture10.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(9, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture11);
        imgDeletePicture11.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(10, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture12);
        imgDeletePicture12.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(11, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture13);
        imgDeletePicture13.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(12, fileUri);
        Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture14);
        imgDeletePicture14.setVisibility(View.VISIBLE);
        mHashMapAttachmentFiles.put(13, fileUri);
    }


    private void showHeader() {
        rlHeaderMasterHeader.setVisibility(View.VISIBLE);
        rlHeaderDataPribadi.setVisibility(View.VISIBLE);
        rlHeaderDataPasangan.setVisibility(View.VISIBLE);
        rlHeaderInformasiKerabat.setVisibility(View.VISIBLE);
        rlHeaderAlamatPemohon.setVisibility(View.VISIBLE);
        rlHeaderInformasiKerabat.setVisibility(View.VISIBLE);
        rlHeaderDataPekerjaan.setVisibility(View.VISIBLE);
        rlHeaderDetailProduct.setVisibility(View.VISIBLE);
        rlHeaderDataAsuransi.setVisibility(View.VISIBLE);
        rlHeaderAgunan.setVisibility(View.VISIBLE);
        rlHeaderDataPerhitungan.setVisibility(View.VISIBLE);
        rlHeaderKeleluasaan.setVisibility(View.VISIBLE);
        rlHeaderKeterangan.setVisibility(View.VISIBLE);
        rlHeaderImagePengajuan.setVisibility(View.VISIBLE);
        rlHeaderPersetujuan.setVisibility(View.VISIBLE);
        rlHeaderRecomendation.setVisibility(View.VISIBLE);
        rlHeaderInformasiPenawaran.setVisibility(View.VISIBLE);
        rlHeaderPersetujuanTambahan.setVisibility(View.VISIBLE);
    }

    private void hideAllContent(View view) {
        for (int i = 0; i < viewContents.size(); i++)
            if (view.getId() != viewContents.get(i).getId())
                viewContents.get(i).setVisibility(View.GONE);
    }

    private void showHideContent(RelativeLayout v, boolean isEditKop) {
        hideAllContent(v); // hide content layout
        if (v.getVisibility() == View.GONE) {
            if (firstInitAssignEdit && isEditKop) {
                showAlertYesNo("onEditKOP", "Pemberitahuan", "Apakah anda ingin merubah kolom ini? Jika Ya maka data yang terkait kolom ini akan dihapus ulang dan harus input kembali", "Ya", "Tidak", form);
            }

            v.setVisibility(View.VISIBLE);
            v.getChildAt(0).requestFocus();
        } else {
            v.setVisibility(View.GONE);
        }
    }

    @OnCheckedChanged(R.id.cbx_alamat_ktp)
    public void checkedAlamatKtp(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            lnVisibleAlamatKtp.setVisibility(View.GONE);
            edtAlamatTinggal.setText(edtAlamatKtp.getText().toString());
            edtRtTinggal.setText(edtRtKtp.getText().toString());
            edtRwTinggal.setText(edtRwKtp.getText().toString());
            actAutoAlamatPemohon.setText(actAutoKtpAlamatPemohon.getText().toString());
            actAutoAlamatPemohon.setSelectionFromPopUp(true);
            edtAreaPhoneTinggal.setText(edtAreaPhoneKtp.getText().toString());
            edtPhoneTinggal.setText(edtPhoneKtp.getText().toString());
        } else {
            lnVisibleAlamatKtp.setVisibility(View.VISIBLE);
            edtAlamatTinggal.setText("");
            edtRtTinggal.setText("");
            edtRwTinggal.setText("");
            actAutoAlamatPemohon.setText("");
            actAutoAlamatPemohon.setSelectionFromPopUp(true);
            edtAreaPhoneTinggal.setText("");
            edtPhoneTinggal.setText("");
        }
    }

    @Override
    public void onValidationSucceeded() {
//        Toast.makeText(FormPengajuanNonElcActivity.this, "ini masuk ke validasi submit", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_yes_no);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        Button btnLeft = dialog.findViewById(R.id.btnLeft);
        Button btnRight = dialog.findViewById(R.id.btnRight);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvDescription = dialog.findViewById(R.id.tvDescription);

        if (cbxAlamatKtp.isChecked()) {
            edtAlamatTinggal.setText(edtAlamatKtp.getText().toString());
            edtRtTinggal.setText(edtRtKtp.getText().toString());
            edtRwTinggal.setText(edtRwKtp.getText().toString());
            actAutoAlamatPemohon.setText(actAutoKtpAlamatPemohon.getText().toString());
            actAutoAlamatPemohon.setSelectionFromPopUp(true);
            edtAreaPhoneTinggal.setText(edtAreaPhoneKtp.getText().toString());
            edtPhoneTinggal.setText(edtPhoneKtp.getText().toString());
        }

        setDefaultCalculatingToZero();
        if (form.equalsIgnoreCase("New") || form.equalsIgnoreCase("Draft")) {
            if (checkMinimalKredit()) {
                /*sinkron*/
                statSinkron = "1";
                if (checkDataPerhitungan(rlDataPerhitunganKendaraan)) {
                    tvTitle.setText("PEMBERITAHUAN");
                    tvDescription.setText("Apakah Anda yakin untuk submit data ?");
                    btnLeft.setText("TIDAK");
                    btnRight.setText("YA");
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            hitPerhitunganKendaraan("btnRightSikron", statSinkron, strEffectiveRate, strDPPercentage); /*jika statSinkron = 1, maka sinkronsasi pengajuan baru kredit*/
                        }
                    });
                    dialog.show();
                }
            }
        } else {
//            Toast.makeText(FormPengajuanNonElcActivity.this, "ini masuk ke validasi submit else", Toast.LENGTH_SHORT).show();
            statSinkron = "2"; /*jika statSinkron = 2, maka sinkronsasi assign edit*/
            setValueToHashMap();
            if (checkDataPerhitungan(rlDataPerhitunganKendaraan)) {
                tvTitle.setText("PEMBERITAHUAN");
                tvDescription.setText("Apakah Anda yakin untuk submit data ?");
                btnLeft.setText("TIDAK");
                btnRight.setText("YA");
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (checkFieldNpwp()) {
                            mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, statSinkron, appId);
                        }
                    }
                });
                dialog.show();

            }
        }
    }

    private boolean checkFieldNpwp() {
        String x = edtPokokPembiayaan.getText() == null ? "0" : edtPokokPembiayaan.getText().toString().replace(",", "");
        if (Integer.parseInt(x) > 50000000) {
            return true;
        } else {
            if (!edtNoNpwpDetail.getText().toString().isEmpty() && edtNoNpwpDetail.getText().length() != 20) {
                edtNoNpwpDetail.setError("Invalid ength");
                return false;
            } else {
                edtNoNpwpDetail.setError(null);
                return true;
            }
        }
    }

    private void setValueToHashMap() {
        map = new HashMap<>();
        if (!form.equalsIgnoreCase("New")) {
//            jika pengajuan baru dan langsung klik submit maka tidak akan kirim ApplicationID (save draft)
            map.put("Application[ApplicationID]", Util.toTextRequestBody(appId));
        }

        setMapDataApplication();
        setMapDataKop();
        setMapDataPribadi();
        setMapDataAlamat();
        setMapDataPasangan();
        setMapDataKerabat();
        setMapDataPekerjaan();

//        if (!statusButton.equalsIgnoreCase("2")) {
        setMapDataProduct();
        setMapDataAsuransi();
        setMapDataAgunan();
        setMapDataPerhitungan();
//        }
        setMapDataPembayaran();
        setMapDataKeterangan();
        setMapDataTandaTangan();
        setMapDataRekomendasi();
        setMapDataLokasi();
    }

    private void setMapDataApplication() {
        String statusInformasiPenawaran;
        String statusInformasiSMS;

        if (rbPersetujuanPenawaranTrue.isChecked()) statusInformasiPenawaran = "1";
        else statusInformasiPenawaran = "0";
        if (rbPersetujuanTambahanTrue.isChecked()) statusInformasiSMS = "1";
        else statusInformasiSMS = "0";

        map.put("KorpFormulir[DateStart]", Util.toTextRequestBody(blackListDate));
        map.put("KorpFormulir[BranchIdPrimary]", Util.toTextRequestBody(branchMaster));
        map.put("KorpFormulir[BranchIdLintasCabang]", Util.toTextRequestBody(aoBranch));
        map.put("KorpFormulir[ApplicationIdKPM]", Util.toTextRequestBody(applicationIdKpm));
        map.put("KorpFormulir[PID]", Util.toTextRequestBody(uuid));
        map.put("KorpFormulir[CustomerIdConfins]", Util.toTextRequestBody(customerIdConfins));
        map.put("Application[DataType]", Util.toTextRequestBody(pengajuanType));
        map.put("Application[OfferingType]", Util.toTextRequestBody(typeDataOffering));
        map.put("mobile_submission_key", Util.toTextRequestBody(mobileSubmissionKey));
        map.put("Application[EFNumber]", Util.toTextRequestBody(strEfNumberResponse));
        map.put("Application[AgreetoAcceptOtherOffering]", Util.toTextRequestBody(statusInformasiPenawaran));
        map.put("Application[AdvanceCustomerAgreement]", Util.toTextRequestBody(statusInformasiSMS));
        map.put("Application[UsedOTP]", Util.toTextRequestBody(usedOTP));
//        map.put("Application[AdvanceCustomerAgreement]", Util.toTextRequestBody());
    }

    private void setMapDataKop() {
        map.put("KorpFormulir[CreditStatus]", Util.toTextRequestBody(edtKopStatusKreditmu.getText().toString()));
        map.put("KorpFormulir[FinancingPurpose]", Util.toTextRequestBody(strStatusPengajuan));
        map.put("KorpFormulir[TypeOfFinancing]", Util.toTextRequestBody(spnKopJenisPembiayaan.getSelectedItem().toString())); /*jenis pembiayaan*/
        map.put("KorpFormulir[TypeOfFinancingID]", Util.toTextRequestBody(jenisPembiayaanValue[spnKopJenisPembiayaan.getSelectedItemPosition()])); /*jenis pembiayaan*/
        map.put("KorpFormulir[PurposeOfUseFunds]", Util.toTextRequestBody(spnKopTujuanPenggunaanDana.getSelectedItem().toString().equalsIgnoreCase("pilih") ? "" : spnKopTujuanPenggunaanDana.getSelectedItem().toString())); /*tujuan penggunaan dana*/
        map.put("KorpFormulir[PurposeOfUseFundsID]", Util.toTextRequestBody(tujuanPenggunaanDanaValue[spnKopTujuanPenggunaanDana.getSelectedItemPosition()])); /*tujuan penggunaan dana*/
//        map.put("KorpFormulir[PurposeOfUseFundsDescriptioin]", Util.toTextRequestBody("")); /*tujuan penggunaan dana*/
        map.put("KorpFormulir[SalesMethod]", Util.toTextRequestBody(AOSalesStatus)); /*metode penjualan*/
        map.put("KorpFormulir[SalesMethodID]", Util.toTextRequestBody(AOSalesStatus)); /*metode penjualan*/
//        map.put("KorpFormulir[SalesMethodDescription]", Util.toTextRequestBody("")); /*metode penjualan*/
        map.put("KorpFormulir[TypeForm]", Util.toTextRequestBody(spnValidasiTipePengajuan.getSelectedItem().toString())); /*tipe pengajuan*/
    }

    private void setMapDataPribadi() {
        String strJenisKelamin;
        String strPisahHarta;

        if (rbPribadiLaki.isChecked()) strJenisKelamin = "Laki-laki";
        else if (rbPribadiPerempuan.isChecked()) strJenisKelamin = "Perempuan";
        else strJenisKelamin = "";

        if (rbPerjanjianPisahHartaAda.isChecked()) strPisahHarta = "Ada";
        else if (rbPerjanjianPisahHartaTidakAda.isChecked()) strPisahHarta = "Tidak Ada";
        else strPisahHarta = "";
        map.put("PersonalData[LegalName]", Util.toTextRequestBody(edtPribadiNamaPemohon.getText() == null ? "" : edtPribadiNamaPemohon.getText().toString()));
        map.put("PersonalData[FullName]", Util.toTextRequestBody(edtPribadiNamaLengkapPemohon.getText() == null ? "" : edtPribadiNamaLengkapPemohon.getText().toString()));
        map.put("PersonalData[IDNumber]", Util.toTextRequestBody(edtPribadiNoKtp.getText() == null ? "" : edtPribadiNoKtp.getText().toString()));
        map.put("PersonalData[KKNo]", Util.toTextRequestBody(edtPribadiNoKK.getText() == null ? "" : edtPribadiNoKK.getText().toString()));
        map.put("PersonalData[IDTypeIssuedDate]", Util.toTextRequestBody(edtTerbitKtpPribadi.getText() == null ? "" : edtTerbitKtpPribadi.getText().toString()));
        map.put("PersonalData[BirthPlace]", Util.toTextRequestBody(edtPribadiTempatLahir.getText() == null ? "" : edtPribadiTempatLahir.getText().toString()));
        map.put("PersonalData[BirthDate]", Util.toTextRequestBody(edtPribadiTanggalLahirPribadi.getText() == null ? "" : edtPribadiTanggalLahirPribadi.getText().toString()));
        map.put("PersonalData[Gender]", Util.toTextRequestBody(strJenisKelamin));
        map.put("PersonalData[MaritalStatus]", Util.toTextRequestBody(statusPernikahanValue[spnPribadiStatusPernikahan.getSelectedItemPosition()]));
        map.put("PersonalData[MaritalStatusID]", Util.toTextRequestBody(statusPernikahanValue[spnPribadiStatusPernikahan.getSelectedItemPosition()]));
        map.put("PersonalData[SeparateProperty]", Util.toTextRequestBody(strPisahHarta));
        map.put("PersonalData[NumOfDependence]", Util.toTextRequestBody(edtJmlTanggunganPribadi.getText() == null ? "" : edtJmlTanggunganPribadi.getText().toString()));
        map.put("PersonalData[HomeStatus]", Util.toTextRequestBody(spnStatusRumahPribadi.getSelectedItem().toString()));
        map.put("PersonalData[HomeStatusID]", Util.toTextRequestBody(statusRumahValue[spnStatusRumahPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[StaySinceYear]", Util.toTextRequestBody(edtTinggalSejakTahunPribadi.getText() == null ? "" : edtTinggalSejakTahunPribadi.getText().toString()));
        map.put("PersonalData[StaySinceMonth]", Util.toTextRequestBody(edtTinggalSejakBulanPribadi.getText() == null ? "" : edtTinggalSejakBulanPribadi.getText().toString()));
        map.put("PersonalData[Education]", Util.toTextRequestBody(spnPendidikanPribadi.getSelectedItem().toString()));
        map.put("PersonalData[EducationID]", Util.toTextRequestBody(pendidikanValue[spnPendidikanPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[Religion]", Util.toTextRequestBody(spnAgamaPribadi.getSelectedItem().toString()));
        map.put("PersonalData[ReligionID]", Util.toTextRequestBody(agamaValue[spnAgamaPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[PersonalNPWP]", Util.toTextRequestBody(edtNoNpwpDetail.getText() == null ? "" : String.format("%s%s", "", edtNoNpwpDetail.getRawText())));
        map.put("PersonalData[Email]", Util.toTextRequestBody(edtEmailPribadi.getText() == null ? "" : edtEmailPribadi.getText().toString()));
        map.put("PersonalData[MobilePhone]", Util.toTextRequestBody(edtHandphonePribadi.getText() == null ? "" : edtHandphonePribadi.getText().toString()));
        map.put("PersonalData[SurgateMotherName]", Util.toTextRequestBody(edtNamaIbuPribadi.getText() == null ? "" : edtNamaIbuPribadi.getText().toString()));

    }

    private void setMapDataLokasi() {
        map.put("Location[ValidateAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[ValidateLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[ValidateLatitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeKtpAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeKtpLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeKtpLatitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeCustomerAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeCustomerLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeCustomerLatitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakePaycheckAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakePaycheckLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakePaycheckLatitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeAdditionalDocumentsAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeAdditionalDocumentsLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeAdditionalDocumentsLatitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeSignatureAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeSignatureLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[TakeSignatureLatitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[SubmitAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[SubmitLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[SubmitLatitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[SyncAction]", Util.toTextRequestBody("0.0"));
        map.put("Location[SyncLongitude]", Util.toTextRequestBody("0.0"));
        map.put("Location[SyncLatitude]", Util.toTextRequestBody("0.0"));
    }

    private void setMapDataRekomendasi() {
        map.put("Application[IsJabarRecomendation]", Util.toTextRequestBody("0"));
        map.put("Application[ReasonRecomendationId]", Util.toTextRequestBody(recomendationId == null ? "" : recomendationId));
        map.put("Application[ReasonRecomendation]", Util.toTextRequestBody(spnRecomendation.getSelectedItem() == null ? "" : spnRecomendation.getSelectedItem().toString()));
        map.put("Application[ReasonRecomendationNotes]", Util.toTextRequestBody(edtDescRecomendation.getText() == null ? "" : edtDescRecomendation.getText().toString()));
    }

    private void setMapDataTandaTangan() {
        map.put("Signature[Applicant]", Util.toTextRequestBody(bitmapTtdPemohon == null ? "" : Util.bitmapToBase64(bitmapTtdPemohon)));
        map.put("Signature[ApplicantHusbandWife]", Util.toTextRequestBody(bitmapTtdPasangan == null ? "" : Util.bitmapToBase64(bitmapTtdPasangan)));
    }

    private void setMapDataKeterangan() {
        map.put("KorpFormulir[Keterangan]", Util.toTextRequestBody(edtKeterangan.getText() == null ? "" : edtKeterangan.getText().toString()));
    }

    private void setMapDataPembayaran() {
        map.put("DetailProduct[PaymentMethod]", Util.toTextRequestBody(spnKeleluasaan.getSelectedItem().toString()));
        map.put("DetailProduct[PaymentMethodID]", Util.toTextRequestBody(caraPembayaranValue[spnKeleluasaan.getSelectedItemPosition()]));
    }

    private void setMapDataAlamat() {
        map.put("Residance[Address]", Util.toTextRequestBody(edtAlamatTinggal.getText().toString()
                + "|" + actAutoAlamatPemohon.getText().toString()));
        map.put("Residance[RT]", Util.toTextRequestBody(edtRtTinggal.getText().toString()));
        map.put("Residance[RW]", Util.toTextRequestBody(edtRwTinggal.getText().toString()));
        map.put("Residance[VillageCode]", Util.toTextRequestBody("1221"));
        map.put("Residance[AreaPhone]", Util.toTextRequestBody(edtAreaPhoneTinggal.getText().toString()));
        map.put("Residance[Phone]", Util.toTextRequestBody(edtPhoneTinggal.getText().toString()));

        map.put("Legal[Address]", Util.toTextRequestBody(edtAlamatKtp.getText().toString()
                + "|" + actAutoKtpAlamatPemohon.getText().toString()));
        map.put("Legal[RT]", Util.toTextRequestBody(edtRtKtp.getText().toString()));
        map.put("Legal[RW]", Util.toTextRequestBody(edtRwKtp.getText().toString()));
        map.put("Legal[VillageCode]", Util.toTextRequestBody("1221"));
        map.put("Legal[AreaPhone]", Util.toTextRequestBody(edtAreaPhoneKtp.getText().toString()));
        map.put("Legal[Phone]", Util.toTextRequestBody(edtPhoneKtp.getText().toString()));
    }

    private void setMapDataPasangan() {
        String strStatusPegawai;
        if (rbPasanganPegawaiTetap.isChecked()) strStatusPegawai = "Pegawai Tetap";
        else if (rbPasanganPegawaiTidakTetap.isChecked()) strStatusPegawai = "Pegawai Tidak Tetap";
        else strStatusPegawai = "-";

        map.put("FamilyData[0][SeqNo]", Util.toTextRequestBody("1"));
        map.put("FamilyData[0][Id]", Util.toTextRequestBody(idFamily == null ? "" : idFamily));
        map.put("FamilyData[0][Name]", Util.toTextRequestBody(edtPasanganNama.getText() == null ? "" : edtPasanganNama.getText().toString()));
        map.put("FamilyData[0][IDNumber]", Util.toTextRequestBody(edtPasanganNoKtp.getText() == null ? "" : edtPasanganNoKtp.getText().toString()));
        map.put("FamilyData[0][BirthPlace]", Util.toTextRequestBody(edtPasanganTempatLahir.getText() == null ? "" : edtPasanganTempatLahir.getText().toString()));
        map.put("FamilyData[0][BirthDate]", Util.toTextRequestBody(edtPasanganTanggalLahir.getText() == null ? "" : edtPasanganTanggalLahir.getText().toString()));
        map.put("FamilyData[0][AreaPhoneHome]", Util.toTextRequestBody(edtPasanganKodeAreaTeleponRumah.getText() == null ? "" : edtPasanganKodeAreaTeleponRumah.getText().toString()));
        map.put("FamilyData[0][PhoneHome]", Util.toTextRequestBody(edtPasanganNoTeleponRumah.getText() == null ? "" : edtPasanganNoTeleponRumah.getText().toString()));
        map.put("FamilyData[0][AreaPhoneCompany]", Util.toTextRequestBody(edtPasanganKodeAreaTeleponPerusahaan.getText() == null ? "" : edtPasanganKodeAreaTeleponPerusahaan.getText().toString()));
        map.put("FamilyData[0][PhoneCompany]", Util.toTextRequestBody(edtPasanganNoTeleponPerusahaan.getText() == null ? "" : edtPasanganNoTeleponPerusahaan.getText().toString()));
        map.put("FamilyData[0][Handphone]", Util.toTextRequestBody(edtPasanganNoHp.getText() == null ? "" : edtPasanganNoHp.getText().toString()));
        map.put("FamilyData[0][Address]", Util.toTextRequestBody(edtPasanganAlamat.getText().toString() + "|" + actPasanganKota.getText().toString()));
        map.put("FamilyData[0][ProfesiId]", Util.toTextRequestBody(kodePasanganProfesi == null ? "" : kodePasanganProfesi));
//        map.put("FamilyData[0][Profesi]", Util.toTextRequestBody(actPasanganProfesi.getText() == null ? "" : actPasanganProfesi.getText().toString()));
        map.put("FamilyData[0][JobTypeId]", Util.toTextRequestBody(kodePasanganJobType == null ? "" : kodePasanganJobType));
//        map.put("FamilyData[0][JobType]", Util.toTextRequestBody(actPasanganJobType.getText() == null ? "" : actPasanganJobType.getText().toString()));
        map.put("FamilyData[0][JobPositionId]", Util.toTextRequestBody(kodePasanganJobPosition == null ? "" : kodePasanganJobPosition));
//        map.put("FamilyData[0][JobPosition]", Util.toTextRequestBody(actPasanganJobPosition.getText() == null ? "" : actPasanganJobPosition.getText().toString()));
        map.put("FamilyData[0][IndustryId]", Util.toTextRequestBody(kodePasanganIndustri == null ? "" : kodePasanganIndustri));
//        map.put("FamilyData[0][Industry]", Util.toTextRequestBody(actPasanganIndustri.getText() == null ? "" : actPasanganIndustri.getText().toString()));
        map.put("FamilyData[0][StatusEmployee]", Util.toTextRequestBody(strStatusPegawai));
        map.put("FamilyData[0][CompanyName]", Util.toTextRequestBody(edtPasanganNamaPerusahaan.getText() == null ? "" : edtPasanganNamaPerusahaan.getText().toString()));
        map.put("FamilyData[0][SurgateMotherName]", Util.toTextRequestBody(edtPasanganNamaIbu.getText() == null ? "" : edtPasanganNamaIbu.getText().toString()));
    }

    private void setMapDataKerabat() {
        map.put("EmergencyContact[Name]", Util.toTextRequestBody(edtNamaKerabat.getText() == null ? "" : edtNamaKerabat.getText().toString()));
        map.put("EmergencyContact[Relationship]", Util.toTextRequestBody(spnHubunganKerabat.getSelectedItem().toString()));
//        map.put("EmergencyContact[RelationshipId]", Util.toTextRequestBody(hubunganKerabatValue[spnHubunganKerabat.getSelectedItemPosition()]));
        map.put("EmergencyContact[Address]", Util.toTextRequestBody(edtAlamatKerabat.getText().toString()
                + "|" + actAutoAlamatKerabat.getText().toString()));
        map.put("EmergencyContact[RT]", Util.toTextRequestBody(edtRtKerabat.getText() == null ? "" : edtRtKerabat.getText().toString()));
        map.put("EmergencyContact[RW]", Util.toTextRequestBody(edtRwKerabat.getText() == null ? "" : edtRwKerabat.getText().toString()));
        map.put("EmergencyContact[VillageCode]", Util.toTextRequestBody("1221"));
        map.put("EmergencyContact[HomePhoneArea]", Util.toTextRequestBody(edtAreaPhoneRumahKerabat.getText() == null ? "" : edtAreaPhoneRumahKerabat.getText().toString()));
        map.put("EmergencyContact[HomePhone]", Util.toTextRequestBody(edtPhoneRumahKerabat.getText() == null ? "" : edtPhoneRumahKerabat.getText().toString()));
        map.put("EmergencyContact[OfficePhoneArea]", Util.toTextRequestBody(edtAreaPhoneKantorKerabat.getText() == null ? "" : edtAreaPhoneKantorKerabat.getText().toString()));
        map.put("EmergencyContact[OfficePhone]", Util.toTextRequestBody(edtPhoneKantorKerabat.getText() == null ? "" : edtPhoneKantorKerabat.getText().toString()));
        map.put("EmergencyContact[MobilePhone]", Util.toTextRequestBody(edtHpKerabat.getText() == null ? "" : edtHpKerabat.getText().toString()));
    }

    private void setMapDataPekerjaan() {
        map.put("Company[Name]", Util.toTextRequestBody(edtNamaPerusahaan.getText() == null ? "" : edtNamaPerusahaan.getText().toString()));
        map.put("Company[Address]", Util.toTextRequestBody(edtAlamatPerusahaan.getText().toString()
                + "|" + actAutoAlamatPekerjaan.getText().toString()));
        map.put("Company[RT]", Util.toTextRequestBody(edtRtPerusahaan.getText() == null ? "" : edtRtPerusahaan.getText().toString()));
        map.put("Company[RW]", Util.toTextRequestBody(edtRwPerusahaan.getText() == null ? "" : edtRwPerusahaan.getText().toString()));
        map.put("Company[VillageCode]", Util.toTextRequestBody("1221"));
        map.put("Company[AreaPhone]", Util.toTextRequestBody(edtAreaPhonePerusahaan.getText() == null ? "" : edtAreaPhonePerusahaan.getText().toString()));
        map.put("Company[Phone]", Util.toTextRequestBody(edtPhonePerusahaan.getText() == null ? "" : edtPhonePerusahaan.getText().toString()));
        map.put("Company[EmploymentSinceYear]", Util.toTextRequestBody(edtBekerjaSejakPerusahaan.getText() == null ? "" : edtBekerjaSejakPerusahaan.getText().toString()));
        map.put("Company[ProfessionID]", Util.toTextRequestBody(professionKode == null ? "" : professionKode));
//        map.put("Company[ProfessionName]", Util.toTextRequestBody(actProfesiPerusahaan.getText() == null ? "" : actProfesiPerusahaan.getText().toString()));
        map.put("Company[JobType]", Util.toTextRequestBody(jobTypeKode == null ? "" : jobTypeKode));
//        map.put("Company[JobTypeName]", Util.toTextRequestBody(actJobTypePerusahaan.getText() == null ? "" : actJobTypePerusahaan.getText().toString()));
        map.put("Company[JobPosition]", Util.toTextRequestBody(jobPositionKode == null ? "" : jobPositionKode));
//        map.put("Company[JobPositionName]", Util.toTextRequestBody(actJobPositionPerusahaan.getText() == null ? "" : actJobPositionPerusahaan.getText().toString()));
        map.put("Company[IndustryTypeID]", Util.toTextRequestBody(industriKode == null ? "" : industriKode));
//        map.put("Company[IndustryTypeName]", Util.toTextRequestBody(actIndustriPerusahaan.getText() == null ? "" : actIndustriPerusahaan.getText().toString()));
        map.put("Company[MonthlyFixedIncome]", Util.toTextRequestBody(edtPenghasilanTetapPerusahaan.getText() == null ? "" : edtPenghasilanTetapPerusahaan.getText().toString().replace(",", "")));
        map.put("Company[MonthlyVariableIncome]", Util.toTextRequestBody(edtPenghasilanLainPerusahaan.getText() == null ? "" : edtPenghasilanLainPerusahaan.getText().toString().replace(",", "")));

        if (edtPenghasilanPasanganPerusahaan == null) {
            map.put("Company[SpouseIncome]", Util.toTextRequestBody(""));
        } else {
            map.put("Company[SpouseIncome]", Util.toTextRequestBody(edtPenghasilanPasanganPerusahaan.getText() == null ? "" : edtPenghasilanPasanganPerusahaan.getText().toString().replace(",", "")));
        }

        map.put("Company[LivingCostAmount]", Util.toTextRequestBody(edtBiayaHidupPerusahaan.getText() == null ? "" : edtBiayaHidupPerusahaan.getText().toString().replace(",", "")));
        map.put("Company[PhoneNumber]", Util.toTextRequestBody(edtPekerjaanNoHp.getText() == null ? "" : edtPekerjaanNoHp.getText().toString()));
    }

    private void setMapDataPerhitungan() {
        map.put("DetailProduct[Tenor]", Util.toTextRequestBody(spnTenorPerhitunganKendaraan.getSelectedItem() == null ? "" : spnTenorPerhitunganKendaraan.getSelectedItem().toString()));
        map.put("DetailFinancing[CollateralPrice]", Util.toTextRequestBody(edtHargaAgunan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[ConsumerLoan]", Util.toTextRequestBody(edtPokokPembiayaan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[AdminFee]", Util.toTextRequestBody(edtBiayaAdminPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[PremiumAmountToCustomer]", Util.toTextRequestBody(edtPremiAsuransiPerhitunganAgunanKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[LifeInsurancePremi]", Util.toTextRequestBody(edtPremiAsuransiPerhitunganJiwaKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[TotalFinancing]", Util.toTextRequestBody(edtJumlahPembiayaanPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[InterestFinancing]", Util.toTextRequestBody(edtBungaPembiayaanKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[TotalLoan]", Util.toTextRequestBody(edtTotalPinjamanPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[InstallmentAmount]", Util.toTextRequestBody(edtAngsuranPerbulanPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[FlatRateYear]", Util.toTextRequestBody(edtFlatPertahunPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[FiduciaryFees]", Util.toTextRequestBody(edtBiayaPnbpFidusiaPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[StnkFees]", Util.toTextRequestBody(edtStnkPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[NotaryFees]", Util.toTextRequestBody(edtBiayaNotarisPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[FirstPayment]", Util.toTextRequestBody(edtAngsuranPertamaPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[ProvisiFee]", Util.toTextRequestBody("0"));
        map.put("DetailFinancing[CosOfSurvey]", Util.toTextRequestBody(edtBiayaSurveyPerhitunganKendaraan.getText().toString().replace(",", "")));
        map.put("DetailFinancing[DownPayment]", Util.toTextRequestBody(edtDownpayment.getText().toString().replace(",", "")));
        map.put("DetailFinancing[DownPaymentPercentage]", Util.toTextRequestBody(strDPPercentage == null ? "" : strDPPercentage));
        map.put("DetailFinancing[EffectiveRate]", Util.toTextRequestBody(strEffectiveRate == null ? "" : strEffectiveRate));
        map.put("DetailFinancing[MinConsumerLoan]", Util.toTextRequestBody(String.valueOf(intMinPokokPembiayaan)));
    }

    private void setMapDataAgunan() {
        String strBpkbAtasNamaSendiri;
        String strNamaBpkb;

        if (rbBpkbAtasNamaSendiri.isChecked()) strBpkbAtasNamaSendiri = "Sendiri";
        else if (rbBpkbAtasNamaPasangan.isChecked()) strBpkbAtasNamaSendiri = "Pasangan";
        else strBpkbAtasNamaSendiri = "Orang Lain";

        if (strBpkbAtasNamaSendiri.equalsIgnoreCase("Sendiri"))
            strNamaBpkb = edtNamaBpkbSendiri.getText().toString();
        else if (strBpkbAtasNamaSendiri.equalsIgnoreCase("Pasangan"))
            strNamaBpkb = edtNamaBpkbPasangan.getText().toString();
        else strNamaBpkb = edtNamaBpkbOrangLain.getText().toString();

        map.put("Collateral[Region]", Util.toTextRequestBody(actWilayahKendaraan.getText().toString()));
        map.put("Collateral[Branch]", Util.toTextRequestBody(actCabangKendaraan.getText().toString()));
        map.put("Collateral[KindId]", Util.toTextRequestBody(idJenisKendaraan));
        map.put("Collateral[KindName]", Util.toTextRequestBody(spnJenisKendaraan.getSelectedItem().toString()));
        map.put("Collateral[MerkId]", Util.toTextRequestBody(idMerkKendaraan));
        map.put("Collateral[MerkName]", Util.toTextRequestBody(spnMerkKendaraan.getSelectedItem().toString()));
        map.put("Collateral[TypeId]", Util.toTextRequestBody(idTipeKendaraan));
        map.put("Collateral[TypeName]", Util.toTextRequestBody(actTypeKendaraan.getText().toString()));
        map.put("Collateral[Year]", Util.toTextRequestBody(spnTahunKendaraan.getSelectedItem().toString()));
        map.put("Collateral[Colour]", Util.toTextRequestBody(edtWarnaKendaraan.getText().toString()));
        map.put("Collateral[StatusVehicle]", Util.toTextRequestBody("-"));
        map.put("Collateral[Cylinder]", Util.toTextRequestBody(edtIsiSilinder.getText().toString()));
        map.put("Collateral[NoPolice]", Util.toTextRequestBody(edtNoPolisi.getText().toString()));
        map.put("Collateral[FrameNumber]", Util.toTextRequestBody(edtNoRangka.getText().toString()));
        map.put("Collateral[NoMachine]", Util.toTextRequestBody(edtNoMesin.getText().toString()));
        map.put("Collateral[BpkbOwnName]", Util.toTextRequestBody(strBpkbAtasNamaSendiri));
        map.put("Collateral[BpkbName]", Util.toTextRequestBody(strNamaBpkb));
        map.put("Collateral[ValidityPeriodStnk]", Util.toTextRequestBody(edtMasaBerlakuStnk.getText().toString()));
        map.put("Collateral[ValidityPeriodTaxStnk]", Util.toTextRequestBody(edtMasaBerlakuPajakStnk.getText().toString()));
    }

    private void setMapDataAsuransi() {
        map.put("Insurance[CoverageType]", Util.toTextRequestBody(spnDataAsuransi.getSelectedItem().toString()));
    }

    private void setMapDataProduct() {
        map.put("AssetMaster[SupplierID]", Util.toTextRequestBody(supplierKode == null ? "" : supplierKode));
        map.put("AssetMaster[RefferalCode]", Util.toTextRequestBody((actReferalCode.getText() == null) ? "" : actReferalCode.getText().toString()));
        map.put("AssetMaster[SupplierName]", Util.toTextRequestBody(actDtProductSupplier.getText().toString()));
        map.put("AssetMaster[SalesmanID]", Util.toTextRequestBody(marketingKode == null ? "" : marketingKode));
        map.put("AssetMaster[SalesmanName]", Util.toTextRequestBody(actDtProductMarketingSupplier.getText().toString()));
        map.put("DetailProduct[ProductID]", Util.toTextRequestBody(productId == null ? "" : productId));
        map.put("DetailProduct[ProductOfferingID]", Util.toTextRequestBody(productOfferingId == null ? "" : productOfferingId));
        map.put("DetailProduct[ProductOfferingName]", Util.toTextRequestBody(actDtProductOffering.getText().toString()));
        map.put("DetailProduct[POS]", Util.toTextRequestBody(posKode == null ? "" : posKode));
        map.put("DetailProduct[POSName]", Util.toTextRequestBody(actDtProductPos.getText().toString()));
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message;
            try {
                message = error.getCollatedErrorMessage(this);
            } catch (Exception e) {
                message = "Tidak boleh kosong";
            }

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                if (viewDataKop.contains(view))
                    imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDataPribadiNpwp.contains(view))
                    imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewAlamat.contains(view))
                    imgDropDownAlamat.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDataPasangan.contains(view))
                    imgDropDownPasangan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewKerabat.contains(view))
                    imgDropDownKerabat.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewMenikahPekerjaan.contains(view))
                    imgDropDownPekerjaan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewTidakMenikahPekerjaan.contains(view))
                    imgDropDownPekerjaan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDetailProduct.contains(view))
                    imgDropDownProduct.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewAgunan.contains(view))
                    imgDropDownAgunan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewPerhitungan.contains(view))
                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
            }
            if (view instanceof ImageView) {
                if (spnPribadiStatusPernikahan.getSelectedItem().toString().equalsIgnoreCase("menikah")) {
                    if (viewPhotoMenikah.contains(view))
                        imgDropDownAttachment.setImageResource(android.R.drawable.ic_dialog_alert);
                } else {
                    if (viewPhotoTidakMenikah.contains(view))
                        imgDropDownAttachment.setImageResource(android.R.drawable.ic_dialog_alert);
                }
//                        TANDA TANGAN PEMOHON DAN PASANGAN
                if (viewTtdPemohonDanPasangan.contains(view))
                    imgDropDownPersetujuan.setImageResource(android.R.drawable.ic_dialog_alert);
//                        TANDA TANGAN PEMOHON
                if (viewTtdPemohon.contains(view))
                    imgDropDownPersetujuan.setImageResource(android.R.drawable.ic_dialog_alert);

                if (view == imgTtdPemohonPersetujuan)
                    txtTtdPemohonError.setVisibility(View.VISIBLE);
                if (view == imgTtdPemohonPersetujuan)
                    txtTtdPemohonError.setVisibility(View.VISIBLE);
                if (view == imgTtdPasanganPersetujuan)
                    txtTtdPasanganError.setVisibility(View.VISIBLE);

                if (view == imgTakePicture1) tvCamera1.setTextColor(Color.RED);
                if (view == imgTakePicture2) tvCamera2.setTextColor(Color.RED);
                if (view == imgTakePicture3) tvCamera3.setTextColor(Color.RED);
                if (view == imgTakePicture4) tvCamera4.setTextColor(Color.RED);
                if (view == imgTakePicture5) tvCamera5.setTextColor(Color.RED);/*pasangan*/
                if (view == imgTakePicture6) tvCamera6.setTextColor(Color.RED);
                if (view == imgTakePicture7) tvCamera7.setTextColor(Color.RED);
                if (view == imgTakePicture8) tvCamera8.setTextColor(Color.RED);
                if (view == imgTakePicture9) tvCamera9.setTextColor(Color.RED);
                if (view == imgTakePicture10) tvCamera10.setTextColor(Color.RED);
                if (view == imgTakePicture11) tvCamera11.setTextColor(Color.RED);
                if (view == imgTakePicture12) tvCamera12.setTextColor(Color.RED);
                if (view == imgTakePicture13) tvCamera13.setTextColor(Color.RED);
                if (view == imgTakePicture14) tvCamera14.setTextColor(Color.RED);
//                if (view == imgTakePicture15) tvCamera15.setTextColor(Color.RED);
//                if (view == imgTakePicture16) tvCamera16.setTextColor(Color.RED);
//                if (view == imgTakePicture17) tvCamera17.setTextColor(Color.RED);
//                if (view == imgTakePicture18) tvCamera18.setTextColor(Color.RED);
            }
            if (view instanceof RadioGroup) {
                if (viewDataPribadiRG.contains(view))
                    imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDataPribadiRGPisahHarta.contains(view))
                    imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == rgPribadiJenisKelamin) tvPribadiJenisKelamin.setTextColor(Color.RED);
                if (view == rgPerjanjianPisahHarta) tvPerjanjianPisahHarta.setTextColor(Color.RED);

                if (viewDataPasanganRG.contains(view))
                    imgDropDownPasangan.setImageResource(android.R.drawable.ic_dialog_alert);
//                if (view == rgPasanganStatus) tvPasanganStatus.setTextColor(Color.RED);

                if (viewAgunanRG.contains(view))
                    imgDropDownAgunan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == rgBpkbAtasNama) tvBpkbAtasNama.setTextColor(Color.RED);

                if (viewRekomendasiRG.contains(view)) {
                    tvGroupRekomendasi.setTextColor(Color.RED);
                    imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                }


                if (view == rbGroupRecomendation) tvRekomendasi.setTextColor(Color.RED);
                if (viewDataPenawaranRG.contains(view))
                    imgDropDownDataPenawaran.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDataPersetujuanTambahanRG.contains(view))
                    imgDropDownDataPersetujuanTambahan.setImageResource(android.R.drawable.ic_dialog_alert);
            }
            if (view instanceof Spinner) {
//                kop============================================
                if (viewDataKopSpinnerNonPsa.contains(view))
                    imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDataKopSpinnerPsa.contains(view))
                    imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == spnValidasiTipePengajuan)
                    tvValidasiTipePengajuan.setTextColor(Color.RED);
                if (view == spnKopJenisPembiayaan)
                    tvKopJenisPembiayaan.setTextColor(Color.RED);
                if (view == spnKopTujuanPenggunaanDana)
                    tvKopTujuanPenggunaanDana.setTextColor(Color.RED);
                if (view == spnMetodePenjualan)
                    tvMetodePenjualan.setTextColor(Color.RED);
//                data pribadi============================================viewDataAgunanSpinner
                if (viewDataPribadiSpinner.contains(view))
                    imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == spnPribadiStatusPernikahan)
                    tvPribadiStatusPernikahan.setTextColor(Color.RED);
                if (view == spnStatusRumahPribadi) tvStatusRumahPribadi.setTextColor(Color.RED);
                if (view == spnPendidikanPribadi) tvPendidikanPribadi.setTextColor(Color.RED);
                if (view == spnAgamaPribadi) tvAgamaPribadi.setTextColor(Color.RED);
//                informasi kerabat============================================
                if (viewDataKerabatSpinner.contains(view))
                    imgDropDownKerabat.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == spnHubunganKerabat) tvHubunganKerabatSpn.setTextColor(Color.RED);
//                data agunan============================================
                if (viewDataAgunanSpinner.contains(view))
                    imgDropDownAgunan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == spnJenisKendaraan) tvJenisKendaraan.setTextColor(Color.RED);
                if (view == spnMerkKendaraan) tvMerkKendaraan.setTextColor(Color.RED);
                if (view == spnTahunKendaraan) tvTahunKendaraan.setTextColor(Color.RED);
//                data asuransi============================================
                if (viewDataAsuransiSpinner.contains(view))
                    imgDropDownAsurani.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == spnDataAsuransi) tvDataAsuransi.setTextColor(Color.RED);
//                cara pembayaran============================================
                if (viewCaraPembayaranSpinner.contains(view))
                    imgDropDownKeleluasaan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == spnKeleluasaan) tvKeleluasaan.setTextColor(Color.RED);
//                rekomendasi============================================
                if (viewDataRekomendasiSpinner.contains(view)) {
                    imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                    tvRekomendasi.setTextColor(Color.RED);
                }

                if (view == spnRecomendation) tvRekomendasi.setTextColor(Color.RED);
            }
        }
    }

    //1
    private View.OnFocusChangeListener validateOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                switch (v.getId()) {
                    case R.id.act_auto_alamat_pemohon:
                        actAutoAlamatPemohon.showDropDown();
                        break;
                    case R.id.act_auto_ktp_alamat_pemohon:
                        actAutoKtpAlamatPemohon.showDropDown();
                        break;
                    case R.id.act_auto_alamat_kerabat:
                        actAutoAlamatKerabat.showDropDown();
                        break;
                    case R.id.act_auto_alamat_pekerjaan:
                        actAutoAlamatPekerjaan.showDropDown();
                        break;
                    case R.id.act_profesi_perusahaan:
                        actProfesiPerusahaan.showDropDown();
                        break;
                    case R.id.act_job_type_perusahaan:
                        actJobTypePerusahaan.showDropDown();
                        break;
                    case R.id.act_job_position_perusahaan:
                        actJobPositionPerusahaan.showDropDown();
                        break;
                    case R.id.act_industri_perusahaan:
                        actIndustriPerusahaan.showDropDown();
                        break;
                    case R.id.actDtProductSupplier:
                        if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                            Log.d("KOP :", "5");
                            Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                        actDtProductSupplier.showDropDown();
                        break;
                    case R.id.actDtProductMarketingSupplier:
                        if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                            Log.d("KOP :", "6");
                            Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                        actDtProductMarketingSupplier.showDropDown();
                        break;
                    case R.id.actDtProductOffering:
                        if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                            Log.d("KOP :", "7");
                            Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                        actDtProductOffering.showDropDown();
                        break;
                    case R.id.actDtProductPos:
                        if (supplierNameArrayList == null || supplierNameArrayList.isEmpty()) {
                            Log.d("KOP :", "8");
                            Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                        actDtProductPos.showDropDown();
                        break;
                    case R.id.act_type_kendaraan:
                        actTypeKendaraan.showDropDown();
                        break;
                    case R.id.actPasanganKota:
                        actPasanganKota.showDropDown();
                        break;
                    case R.id.actPasanganProfesi:
                        actPasanganProfesi.showDropDown();
                        break;
                    case R.id.actPasanganJobType:
                        actPasanganJobType.showDropDown();
                        break;
                    case R.id.actPasanganJobPosition:
                        actPasanganJobPosition.showDropDown();
                        break;
                    case R.id.actPasanganIndustri:
                        actPasanganIndustri.showDropDown();
                        break;
                }
            }
        }
    };

    @Override
    public void onPreLoadSyarat() {
        preLoading();
    }

    @Override
    public void onSuccessSyarat(Syarat syarats, TidakCancel tidakCancels, FaqObjt Faq) {
        successAndFailedLoading();
        descriptionCancel = syarats.getDescription();
        descriptionSyarat = tidakCancels.getDescription();

        if (isAssignEdit == null || isAssignEdit.isEmpty()) {
            if (!spnKopJenisPembiayaan.getSelectedItem().toString().equalsIgnoreCase("pilih")
                    && !spnMetodePenjualan.getSelectedItem().toString().equalsIgnoreCase("pilih")) {
                imgDropDownHeaderKop.setImageResource(R.drawable.ic_arrow);

                if ("e-Commerce".equalsIgnoreCase(spnMetodePenjualan.getSelectedItem().toString())) {
                    if (isFinishing()) return;
                    DialogFragment dialogCancel = new PersetujuanTidakCancel();
                    Bundle bundleSyarat = new Bundle();
                    bundleSyarat.putString("DESCRIPTION_CANCEL", descriptionCancel);
                    bundleSyarat.putString("DESCRIPTION_SYARAT", descriptionSyarat);
                    dialogCancel.setArguments(bundleSyarat);
                    dialogCancel.show(getSupportFragmentManager(), "DialogTidakCancel");
                } else {
                    if (isFinishing()) return;
                    DialogFragment dialogCancel = new DialogPersyaratan();
                    Bundle bundleSyarat2 = new Bundle();
                    bundleSyarat2.putString("DESCRIPTION_SYARAT", descriptionSyarat);
                    dialogCancel.setArguments(bundleSyarat2);
                    dialogCancel.show(getSupportFragmentManager(), "DialogSyarat");
                }
            } else {
                imgDropDownHeaderKop.setImageResource(android.R.drawable.ic_dialog_alert);
                Toast.makeText(this, "Silahkan Lengkapi Data KOP", Toast.LENGTH_SHORT).show();
            }
        } else {
            if ("e-Commerce".equalsIgnoreCase(spnMetodePenjualan.getSelectedItem().toString())) {
                if (isFinishing()) return;
                DialogFragment dialogCancel = new PersetujuanTidakCancel();
                Bundle bundleSyarat = new Bundle();
                bundleSyarat.putString("DESCRIPTION_CANCEL", descriptionCancel);
                bundleSyarat.putString("DESCRIPTION_SYARAT", descriptionSyarat);
                dialogCancel.setArguments(bundleSyarat);
                dialogCancel.show(getSupportFragmentManager(), "DialogTidakCancel");
            } else {
                if (isFinishing()) return;
                DialogFragment dialogCancel = new DialogPersyaratan();
                Bundle bundleSyarat2 = new Bundle();
                bundleSyarat2.putString("DESCRIPTION_SYARAT", descriptionSyarat);
                dialogCancel.setArguments(bundleSyarat2);
                dialogCancel.show(getSupportFragmentManager(), "DialogSyarat");
            }
        }
    }

    @Override
    public void onFailedLoadSyarat(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
    }

    @Override
    public void onSyaratTokenExpired() {
    }

    private void hideAllLoading() {
        llLoading.setVisibility(View.GONE);
        tvPbh.setVisibility(View.GONE);
        pbHorizontal.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
    }

    private class DatePickerListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ((EditText) v).setError(null);
            switch (v.getId()) {
                case R.id.edt_terbit_ktp_pribadi:
                    DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                        int monthKtp;

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            monthKtp = month + 1;
                            if (year >= 2011) {
                                String monthString = Integer.toString(monthKtp);
                                String dayString = Integer.toString(day);
                                String sMonth = monthKtp < 10 ? "0" + monthString : "" + monthString;
                                String sDay = day < 10 ? "0" + dayString : "" + dayString;
                                edtTerbitKtpPribadi.setText(year + "-" + sMonth + "-" + sDay);
                                edtTerbitKtpPribadiDay = Integer.parseInt(sDay);
                                edtTerbitKtpPribadiMonth = month;
                                edtTerbitKtpPribadiYear = year;
                            } else {
                                Toast.makeText(FormPengajuanNonElcActivity.this, R.string.warning_tanggal_ktp, Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    calendar.set(2011, 0, 1);
                    Date terbitKtpMin = calendar.getTime();
                    DatePickerDialog customDatePicker;

                    if (edtTerbitKtpPribadi.getText().toString().isEmpty()) {
                        customDatePicker = customDatePicker(myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
                    } else {
                        customDatePicker = customDatePicker(myDateListener, edtTerbitKtpPribadiYear, edtTerbitKtpPribadiMonth, edtTerbitKtpPribadiDay, false);
                    }

                    customDatePicker.getDatePicker().setMinDate(terbitKtpMin.getTime());
                    customDatePicker.show();
                    break;
                case R.id.edt_tanggal_lahir_pribadi:
                    DatePickerDialog.OnDateSetListener myBirthDay = new DatePickerDialog.OnDateSetListener() {
                        int monthLahir;

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            monthLahir = month + 1;
                            String monthString = Integer.toString(monthLahir);
                            String dayString = Integer.toString(day);
                            String sMonth = monthLahir < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            edtPribadiTanggalLahirPribadi.setText(year + "-" + sMonth + "-" + sDay);
                        }
                    };

                    //Set max year to 12 years ago
                    calendar.setTime(new Date());
                    if ("Lajang".equalsIgnoreCase(spnPribadiStatusPernikahan.getSelectedItem().toString()))
                        calendar.add(Calendar.YEAR, -21);
                    else calendar.add(Calendar.YEAR, -17);

                    Date birthdayMaxDate = calendar.getTime();
                    DatePickerDialog cdpBirdhDay = customDatePicker(myBirthDay, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
                    cdpBirdhDay.getDatePicker().setMaxDate(birthdayMaxDate.getTime());
                    cdpBirdhDay.show();

                    break;
                case R.id.edtPasanganTanggalLahir:
                    DatePickerDialog.OnDateSetListener pasanganBirthDay = new DatePickerDialog.OnDateSetListener() {
                        int monthLahirPasangan;

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            monthLahirPasangan = month + 1;
                            String monthString = Integer.toString(monthLahirPasangan);
                            String dayString = Integer.toString(day);
                            String sMonth = monthLahirPasangan < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            edtPasanganTanggalLahir.setText(year + "-" + sMonth + "-" + sDay);
                            edtPasanganTanggalLahirDay = Integer.parseInt(sDay);
                            edtPasanganTanggalLahirMonth = month;
                            edtPasanganTanggalLahirYear = year;
                        }
                    };

                    //Set max year to 12 years ago
                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR, -17); // pasangan minimal berumur 17 tahun
                    Date birthdayPasanganMaxDate = calendar.getTime();
                    DatePickerDialog cdpBirdhDayPasangan;

                    if (edtPasanganTanggalLahir.getText().toString().isEmpty()) {
                        cdpBirdhDayPasangan = customDatePicker(pasanganBirthDay, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
                    } else {
                        cdpBirdhDayPasangan = customDatePicker(pasanganBirthDay, edtPasanganTanggalLahirYear, edtPasanganTanggalLahirMonth, edtPasanganTanggalLahirDay, false);
                    }

                    cdpBirdhDayPasangan.getDatePicker().setMaxDate(birthdayPasanganMaxDate.getTime());
                    cdpBirdhDayPasangan.show();
                    break;
                case R.id.edt_tinggal_sejak_tahun_pribadi:
                    DatePickerDialog.OnDateSetListener myTahunTinggal = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            if (year <= nowYear) {
                                String yearString = Integer.toString(year) + "";
                                edtTinggalSejakTahunPribadi.setText(yearString);
                                if (nowYear == year) isNowYearPribadi = true;
                                else isNowYearPribadi = false;
                                edtTinggalSejakTahunPribadiYear = Integer.parseInt(yearString);
                            } else {
                                Toast.makeText(FormPengajuanNonElcActivity.this, R.string.warning_tahun_lebih, Toast.LENGTH_SHORT).show();
                            }
                            edtTinggalSejakBulanPribadi.getText().clear();
                        }
                    };

                    calendar.add(Calendar.YEAR, 0);
//                    Date maxDateYear = calendar.getTime();
                    DatePickerDialog cdpTahunTinggal;

                    if (edtTinggalSejakTahunPribadi.getText().toString().isEmpty()) {
                        cdpTahunTinggal = customDatePicker(myTahunTinggal, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, true);
                    } else {
                        cdpTahunTinggal = customDatePicker(myTahunTinggal, edtTinggalSejakTahunPribadiYear, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, false);
                    }
//                    cdpTahunTinggal.getDatePicker().setMaxDate(maxDateYear.getTime());
                    cdpTahunTinggal.show();
                    break;
                case R.id.edt_tinggal_sejak_bulan_pribadi:
                    DatePickerDialog.OnDateSetListener myBulanTinggal = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            if (isNowYearPribadi && month + 1 > nowMonth + 1) {
                                Toast.makeText(FormPengajuanNonElcActivity.this, R.string.warning_bulan_lebih, Toast.LENGTH_SHORT).show();
                            } else {
                                String monthString = Integer.toString(month + 1) + "";
                                edtTinggalSejakBulanPribadi.setText(monthString);
                                edtTinggalSejakBulanPribadiMonth = Integer.parseInt(monthString);
                            }
                        }
                    };

                    if (isNowYearPribadi) calendar.add(Calendar.YEAR, 0);
                    else calendar.add(Calendar.YEAR, -1);
                    DatePickerDialog cdpBulanTinggal;

                    if (edtTinggalSejakBulanPribadi.getText().toString().isEmpty()) {
                        cdpBulanTinggal = customDatePicker(myBulanTinggal, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.GONE, View.VISIBLE, View.GONE, true);
                    } else {
                        cdpBulanTinggal = customDatePicker(myBulanTinggal, calendar.get(Calendar.YEAR), edtTinggalSejakBulanPribadiMonth, calendar.get(Calendar.DAY_OF_MONTH), View.GONE, View.VISIBLE, View.GONE, false);
                    }
                    cdpBulanTinggal.show();
                    break;
                case R.id.edt_bekerja_sejak_perusahaan:
                    DatePickerDialog.OnDateSetListener myTahunBekerja = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            String yearString = Integer.toString(year) + "";
                            edtBekerjaSejakPerusahaan.setText(yearString);
                            edtBekerjaSejakPerusahaanYear = Integer.parseInt(yearString);
                        }
                    };

                    calendar.add(Calendar.YEAR, -5);
                    DatePickerDialog cdpTahunBekerja;

                    if (edtBekerjaSejakPerusahaan.getText().toString().isEmpty()) {
                        cdpTahunBekerja = customDatePicker(myTahunBekerja, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, true);
                    } else {
                        cdpTahunBekerja = customDatePicker(myTahunBekerja, edtBekerjaSejakPerusahaanYear, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, false);
                    }
                    cdpTahunBekerja.show();
                    break;
                case R.id.edt_month_expired_kartu_kredit:
                    DatePickerDialog.OnDateSetListener myBulanKadaluarsaKartu = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            String monthString = Integer.toString(month + 1) + "";
                            // edtBulanKadaluarsaKartuKredit.setText(monthString);
                        }
                    };

                    DatePickerDialog cdpBulanKadaluarsa = customDatePicker(myBulanKadaluarsaKartu, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.GONE, View.VISIBLE, View.GONE, false);
                    cdpBulanKadaluarsa.show();
                    break;
                case R.id.edt_year_expired_kartu_kredit:
                    DatePickerDialog.OnDateSetListener myTahunKadaluarsaKartu = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            String yearString = Integer.toString(year) + "";
                            // edtTahunKadaluarsaKartuKredit.setText(yearString);
                        }
                    };

                    DatePickerDialog cdpTahunKadaluarsa = customDatePicker(myTahunKadaluarsaKartu, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, false);
                    cdpTahunKadaluarsa.show();
                    break;
                case R.id.edt_tanggal_efektif:
                    DatePickerDialog.OnDateSetListener myTanggalEfektif = new DatePickerDialog.OnDateSetListener() {
                        int monthEfektif;

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            monthEfektif = month + 1;
                            String monthString = Integer.toString(monthEfektif);
                            String dayString = Integer.toString(day);
                            String sMonth = monthEfektif < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            // // edtTanggalEfektif.setText(year + "-" + sMonth + "-" + sDay);
                        }
                    };
                    calendar.add(Calendar.YEAR, -1);

                    DatePickerDialog cdpTanggalEfektif = customDatePicker(myTanggalEfektif, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
                    cdpTanggalEfektif.show();
                    break;
                case R.id.edt_tanggal_expired:
                    DatePickerDialog.OnDateSetListener myTanggalExpired = new DatePickerDialog.OnDateSetListener() {
                        int monthExpired;

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            monthExpired = month + 1;
                            String monthString = Integer.toString(monthExpired);
                            String dayString = Integer.toString(day);
                            String sMonth = monthExpired < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            // edtTanggalExpired.setText(year + "-" + sMonth + "-" + sDay);
                        }
                    };
                    calendar.add(Calendar.YEAR, +1);

                    DatePickerDialog cdpTanggalExpired = customDatePicker(myTanggalExpired, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
                    cdpTanggalExpired.show();
                    break;
                case R.id.edtValidasiTanggalLahir:
                    DatePickerDialog.OnDateSetListener myBirthDayVal = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            int monthTanggalLahir = month + 1;
                            String monthString = Integer.toString(monthTanggalLahir);
                            String dayString = Integer.toString(day);
                            String sMonth = monthTanggalLahir < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            edtValidasiTanggalLahir.setText(year + "-" + sMonth + "-" + sDay);


                            edtValidasiTanggalLahirYear = year;
                            edtValidasiTanggalLahirMonth = month;
                            edtValidasiTanggalLahirDay = Integer.parseInt(sDay);
                        }
                    };

                    //Set max year to 12 years ago
                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR, -17); // tidak bisa memilih tanggal lebih dari 17 tahun
                    Date birthdayMaxDateVal = calendar.getTime();
                    DatePickerDialog cdpBirdhDayVal;

                    if (edtValidasiTanggalLahir.getText().toString().isEmpty()) {
                        cdpBirdhDayVal = customDatePicker(myBirthDayVal, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
                    } else {
                        cdpBirdhDayVal = customDatePicker(myBirthDayVal, edtValidasiTanggalLahirYear, edtValidasiTanggalLahirMonth, edtValidasiTanggalLahirDay, false);
                    }
                    cdpBirdhDayVal.getDatePicker().setMaxDate(birthdayMaxDateVal.getTime());
                    cdpBirdhDayVal.show();
                    break;
                case R.id.edt_masa_berlaku_stnk:
                    DatePickerDialog.OnDateSetListener masaBerlakuStnk = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            int monthTanggalLahir = month + 1;
                            String monthString = Integer.toString(monthTanggalLahir);
                            String dayString = Integer.toString(day);
                            String sMonth = monthTanggalLahir < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;

                            edtMasaBerlakuStnk.setText(year + "-" + sMonth + "-" + sDay);
                            edtMasaBerlakuStnkYear = year;
                            edtMasaBerlakuStnkMonth = month;
                            edtMasaBerlakuStnkDay = Integer.parseInt(sDay);
                        }
                    };
                    if (edtMasaBerlakuStnk.getText().toString().isEmpty()) {
                        cdpTahunStnk = customDatePicker(masaBerlakuStnk, calendarMasaBerlakuStnk.get(Calendar.YEAR), calendarMasaBerlakuStnk.get(Calendar.MONTH), calendarMasaBerlakuStnk.get(Calendar.DAY_OF_MONTH), true);
                    } else {
                        cdpTahunStnk = customDatePicker(masaBerlakuStnk, edtMasaBerlakuStnkYear, edtMasaBerlakuStnkMonth, edtMasaBerlakuStnkDay, false);
                    }
                    cdpTahunStnk.getDatePicker().setMaxDate(maxMasaBerlakuStnk.getTime());
                    cdpTahunStnk.show();
                    break;
                case R.id.edt_masa_berlaku_pajak_stnk:
                    DatePickerDialog.OnDateSetListener masaBerlakuPajakStnk = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            int monthTanggalLahir = month + 1;
                            String monthString = Integer.toString(monthTanggalLahir);
                            String dayString = Integer.toString(day);
                            String sMonth = monthTanggalLahir < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;

                            edtMasaBerlakuPajakStnk.setText(year + "-" + sMonth + "-" + sDay);
                            edtMasaBerlakuPajakStnkYear = year;
                            edtMasaBerlakuPajakStnkMonth = month;
                            edtMasaBerlakuPajakStnkDay = Integer.parseInt(sDay);
                        }
                    };
                    if (edtMasaBerlakuPajakStnk.getText().toString().isEmpty()) {
                        cdpTahunPajakStnk = customDatePicker(masaBerlakuPajakStnk, calendarMasaBerlakuPajakStnk.get(Calendar.YEAR), calendarMasaBerlakuPajakStnk.get(Calendar.MONTH), calendarMasaBerlakuPajakStnk.get(Calendar.DAY_OF_MONTH), true);
                    } else {
                        cdpTahunPajakStnk = customDatePicker(masaBerlakuPajakStnk, edtMasaBerlakuPajakStnkYear, edtMasaBerlakuPajakStnkMonth, edtMasaBerlakuPajakStnkDay, false);
                    }
                    cdpTahunPajakStnk.getDatePicker().setMaxDate(maxMasaBerlakuPajakStnk.getTime());
                    cdpTahunPajakStnk.show();
                    break;
            }
        }

    }

    private DatePickerDialog customDatePicker(DatePickerDialog.OnDateSetListener mDateSetListner, int mYear, int mMonth, int mDay, boolean isMaxNowDate) {
        return customDatePicker(mDateSetListner, mYear, mMonth, mDay, View.VISIBLE, View.VISIBLE, View.VISIBLE, isMaxNowDate);
    }

    private DatePickerDialog customDatePicker(DatePickerDialog.OnDateSetListener mDateSetListner, int mYear, int mMonth, int mDay, final int mModeViewYear, final int mModeViewMonth, final int mvModeViewDay, boolean isMaxNowDate) {
        DatePickerDialog dpd = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, mDateSetListner, mYear, mMonth, mDay) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                int day = getContext().getResources().getIdentifier("android:id/day", null, null);
                if (day != 0) {
                    View dayPicker = findViewById(day);
                    if (dayPicker != null)
                        dayPicker.setVisibility(mvModeViewDay);/*Set Day view visibility Off/Gone*/
                }
                int month = getContext().getResources().getIdentifier("android:id/month", null, null);
                if (month != 0) {
                    View dayPicker = findViewById(month);
                    if (dayPicker != null)
                        dayPicker.setVisibility(mModeViewMonth);/*Set Day view visibility Off/Gone*/
                }

                int year = getContext().getResources().getIdentifier("android:id/year", null, null);
                if (year != 0) {
                    View dayPicker = findViewById(year);
                    if (dayPicker != null)
                        dayPicker.setVisibility(mModeViewYear);/*Set Day view visibility Off/Gone*/
                }
            }
        };
        dpd.getDatePicker().setCalendarViewShown(false);
        dpd.getDatePicker().setSpinnersShown(true);

        //Set max year to current year
        if (isMaxNowDate) dpd.getDatePicker().setMaxDate(new Date().getTime());
        dpd.setTitle("");

        return dpd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null)
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initPermission(final int requestCode) {
        mPermissionHelper = new PermissionHelper.Builder(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA).withPermissionInfos(
                getString(R.string.warning_permission_location),
                getString(R.string.warning_permission_location),
                getString(R.string.warning_permission_camera)).withListener(new PermissionHelper.OnPermissionCheckedListener() {
            @Override
            public void onPermissionGranted(boolean isPermissionAlreadyGranted) {
                if (requestCode == 101) {

                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraLandscapeActivity.class);
                    intent.putExtra(CameraLandscapeActivity.FRAME, CameraLandscapeActivity.FRAME_KTP);
                    intent.putExtra(CameraLandscapeActivity.IS_OCR, true);
                    startActivityForResult(intent, requestCode);

                } else if (requestCode == 102) {
//                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraCustomFrame.class);
//                    startActivity(intent);

                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
                    intent.putExtra(CameraActivity.IS_OCR, false);
                    intent.putExtra(CameraActivity.ISFRONT, true);
                    startActivityForResult(intent, requestCode);
                } else if (requestCode == 103) {
//                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraCustomFrame.class);
//                    startActivity(intent);

                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraOtherActivity.class);
                    intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
                    intent.putExtra(CameraOtherActivity.IS_OCR, false);
                    intent.putExtra(CameraOtherActivity.USESWITCH, true);
                    intent.putExtra(CameraOtherActivity.ISFRONT, false);
                    startActivityForResult(intent, requestCode);
                } else {

                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraOtherActivity.class);
                    intent.putExtra(CameraOtherActivity.FRAME, CameraActivity.FRAME_OTHER);
                    intent.putExtra(CameraOtherActivity.IS_OCR, false);
                    intent.putExtra(CameraOtherActivity.USESWITCH, false);
                    intent.putExtra(CameraOtherActivity.ISFRONT, false);
                    startActivityForResult(intent, requestCode);
                }
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(FormPengajuanNonElcActivity.this, R.string.txt_tidak_memiliki_permissions_camera_storage, Toast.LENGTH_SHORT).show();
            }
        }).build();
        mPermissionHelper.requestPermission();
    }

    private void setHeaderIcArrow() {
        imgDropDownHeaderKop.setImageResource(R.drawable.ic_arrow);
        imgDropDownPribadi.setImageResource(R.drawable.ic_arrow);
        imgDropDownAlamat.setImageResource(R.drawable.ic_arrow);
        imgDropDownPasangan.setImageResource(R.drawable.ic_arrow);
        imgDropDownKerabat.setImageResource(R.drawable.ic_arrow);
        imgDropDownPekerjaan.setImageResource(R.drawable.ic_arrow);
        imgDropDownProduct.setImageResource(R.drawable.ic_arrow);
        imgDropDownAsurani.setImageResource(R.drawable.ic_arrow);
        imgDropDownAgunan.setImageResource(R.drawable.ic_arrow);
        imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
        imgDropDownKeleluasaan.setImageResource(R.drawable.ic_arrow);
        imgDropDownAttachment.setImageResource(R.drawable.ic_arrow);
        imgDropDownPersetujuan.setImageResource(R.drawable.ic_arrow);
        imgDropDownRekomendasi.setImageResource(R.drawable.ic_arrow);
        imgDropDownDataPenawaran.setImageResource(R.drawable.ic_arrow);
        imgDropDownDataPersetujuanTambahan.setImageResource(R.drawable.ic_arrow);
    }

    private boolean checkDataPerhitungan(RelativeLayout v) {
        hideAllContent(v);
        if (v.getVisibility() == View.GONE) {
            if (rlDataPerhitunganKendaraan.getVisibility() == View.GONE) {
                imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                Toast.makeText(this, "Mohon Periksa Kembali Data Perhitungan", Toast.LENGTH_SHORT).show();
                return false;
            }
            return false;
        }
        return true;
    }

    private void sendCoordinate() {
        try {
            Util.saveCoordinate(mCoordinatePresenter, token, latitude, longitude, action);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e("GPS", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    private void initRadioGroup() {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        rbPersetujuanPenawaranTrue.setChecked(true);
        rbPersetujuanTambahanTrue.setChecked(true);
        rbGroupRecomendation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View rbRecomendation = rbGroupRecomendation.findViewById(checkedId);
                switch (rbRecomendation.getId()) {
                    case R.id.rb_recomendation_yes:
                        lnSpnRecomendation.setVisibility(View.GONE);
                        validator.removeRules(spnRecomendation);
                        recomendationId = "0";
                        break;
                    case R.id.rb_recomendation_no:
                        lnSpnRecomendation.setVisibility(View.VISIBLE);
                        validator.put(spnRecomendation, new SpinnerRule());
                        break;
                }
            }
        });

        rgBpkbAtasNama.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View rbBpkb = rgBpkbAtasNama.findViewById(checkedId);
                if (rbBpkb != null) {
                    switch (rbBpkb.getId()) {
                        case R.id.rb_bpkb_atas_nama_sendiri:
                            edtNamaBpkbSendiri.setText(edtPribadiNamaLengkapPemohon.getText().toString());
                            edtNamaBpkbPasangan.setText("");
                            edtNamaBpkbOrangLain.setText("");
                            validator.put(edtNamaBpkbSendiri, notEmptyRule);
                            validator.removeRules(edtNamaBpkbPasangan);
                            validator.removeRules(edtNamaBpkbOrangLain);
                            disableInputNamaBpkb();
                            tilNamaBpkbSendiri.setEnabled(true);
                            edtNamaBpkbSendiri.setEnabled(true);
                            setErrorNamaBpkb();
                            tilNamaBpkbSendiri.setError("Silahkan Periksa Kembali Nama BPKB Anda");
                            edtNamaBpkbSendiri.requestFocus();
//                            setHideKeyboardNamaBpkb();
//                            imm.showSoftInput(edtNamaBpkbSendiri, InputMethodManager.SHOW_IMPLICIT);
//                            if (spnTenorPerhitunganKendaraan.getSelectedItem() != null && strEffectiveRate != null)
//                                hitPerhitunganKendaraan("rb_bpkb_atas_nama_sendiri", "0", strEffectiveRate, strDPPercentage);
                            break;
                        case R.id.rb_bpkb_atas_nama_pasangan:
                            edtNamaBpkbPasangan.setText(edtPasanganNama.getText().toString());
                            edtNamaBpkbSendiri.setText("");
                            edtNamaBpkbOrangLain.setText("");
                            validator.put(edtNamaBpkbPasangan, notEmptyRule);
                            validator.removeRules(edtNamaBpkbSendiri);
                            validator.removeRules(edtNamaBpkbOrangLain);
                            disableInputNamaBpkb();
                            tilNamaBpkbPasangan.setEnabled(true);
                            edtNamaBpkbPasangan.setEnabled(true);
                            setErrorNamaBpkb();
                            tilNamaBpkbPasangan.setError("Silahkan Periksa Kembali Nama BPKB Anda");
                            edtNamaBpkbPasangan.requestFocus();
//                            setHideKeyboardNamaBpkb();
//                            imm.showSoftInput(edtNamaBpkbPasangan, InputMethodManager.SHOW_IMPLICIT);
//                            if (spnTenorPerhitunganKendaraan.getSelectedItem() != null && strEffectiveRate != null)
//                                hitPerhitunganKendaraan("rb_bpkb_atas_nama_pasangan", "0", strEffectiveRate, strDPPercentage);
                            break;
                        case R.id.rb_bpkb_atas_nama_orang_lain:
                            edtNamaBpkbSendiri.setText("");
                            edtNamaBpkbPasangan.setText("");
                            edtNamaBpkbOrangLain.setText("");
                            validator.put(edtNamaBpkbOrangLain, notEmptyRule);
                            validator.removeRules(edtNamaBpkbSendiri);
                            validator.removeRules(edtNamaBpkbPasangan);
                            disableInputNamaBpkb();
                            tilNamaBpkbOrangLain.setEnabled(true);
                            edtNamaBpkbOrangLain.setEnabled(true);
                            setErrorNamaBpkb();
                            tilNamaBpkbOrangLain.setError("Silahkan Periksa Kembali Nama BPKB Anda");
                            edtNamaBpkbOrangLain.requestFocus();
//                            setHideKeyboardNamaBpkb();
//                            imm.showSoftInput(edtNamaBpkbOrangLain, InputMethodManager.SHOW_IMPLICIT);
//                            if (spnTenorPerhitunganKendaraan.getSelectedItem() != null && strEffectiveRate != null)
//                                hitPerhitunganKendaraan("rb_bpkb_atas_nama_orang_lain", "0", strEffectiveRate, strDPPercentage);
                            break;
                    }
                }

            }
        });
    }

    private void setHideKeyboardNamaBpkb() {
        edtNamaBpkbSendiri.setInputType(InputType.TYPE_NULL);
        edtNamaBpkbPasangan.setInputType(InputType.TYPE_NULL);
        edtNamaBpkbOrangLain.setInputType(InputType.TYPE_NULL);
    }

    private void setErrorNamaBpkb() {
        tilNamaBpkbSendiri.setError(null);
        tilNamaBpkbPasangan.setError(null);
        tilNamaBpkbOrangLain.setError(null);
    }

    private void disableInputNamaBpkb() {
        tilNamaBpkbSendiri.setEnabled(false);
        tilNamaBpkbPasangan.setEnabled(false);
        tilNamaBpkbOrangLain.setEnabled(false);
        edtNamaBpkbSendiri.setEnabled(false);
        edtNamaBpkbPasangan.setEnabled(false);
        edtNamaBpkbOrangLain.setEnabled(false);
    }

    private void initSpinner() {
//        if (!form.equalsIgnoreCase("Edit")) {
        spnValidasiTipePengajuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] itemJenisPembiayaan;
                if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA")) {
                    itemJenisPembiayaan = new String[]{
                            "PILIH",
                            "MULTIGUNA - PEMBELIAN DENGAN PEMBAYARAN SECARA ANGSURAN"};

                    validator.removeRules(spnKopTujuanPenggunaanDana);
                    llKopTujuanPenggunaanDana.setVisibility(View.GONE);
                    tilStnkPerhitunganKendaraan.setVisibility(View.GONE);
                    edtStnkPerhitunganKendaraan.setVisibility(View.GONE);
                    tilBiayaNotarisPerhitunganKendaraan.setVisibility(View.GONE);
                    edtBiayaNotarisPerhitunganKendaraan.setVisibility(View.GONE);
                    tilBiayaSurveyPerhitunganKendaraan.setVisibility(View.GONE);
                    edtBiayaSurveyPerhitunganKendaraan.setVisibility(View.GONE);
                    tilAngsuranPertamaPerhitunganKendaraan.setVisibility(View.GONE);
                    edtAngsuranPertamaPerhitunganKendaraan.setVisibility(View.GONE);
                    tilBiayaPnbpFidusiaPerhitunganKendaraan.setVisibility(View.GONE);
                    edtBiayaPnbpFidusiaPerhitunganKendaraan.setVisibility(View.GONE);
                    tilPokokPembiayaan.setHint("Nilai Pembiayaan (Rp)");

                    validator.removeRules(edtStnkPerhitunganKendaraan);
                    validator.removeRules(edtBiayaNotarisPerhitunganKendaraan);
                    validator.removeRules(edtBiayaSurveyPerhitunganKendaraan);
                    validator.removeRules(edtAngsuranPertamaPerhitunganKendaraan);
                    if (!form.equalsIgnoreCase("Edit")) setAllDefaultArrayList();
                    Log.d("Supplier Master Hit : ", "2");
                    mSearchSupplierMasterPresenter.getSearchSupplierMaster(token, "", assetTypeId, "1", aoBranch);
                } else if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("NON PSA")) {
                    itemJenisPembiayaan = new String[]{
                            "PILIH",
                            "MULTIGUNA - PEMBELIAN DENGAN PEMBAYARAN SECARA ANGSURAN",
                            "MODAL KERJA - FASILITAS MODAL USAHA",
                            "INVESTASI - PEMBELIAN DENGAN PEMBAYARAN SECARA ANGSURAN"};

                    validator.put(spnKopTujuanPenggunaanDana, spinnerRule);   /*kop*/
                    validator.put(edtStnkPerhitunganKendaraan, notEmptyRule);
                    validator.put(edtBiayaNotarisPerhitunganKendaraan, notEmptyRule);
                    validator.put(edtBiayaSurveyPerhitunganKendaraan, notEmptyRule);
                    validator.put(edtAngsuranPertamaPerhitunganKendaraan, notEmptyRule);

                    llKopTujuanPenggunaanDana.setVisibility(View.VISIBLE);
                    tilStnkPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    edtStnkPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    tilBiayaNotarisPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    edtBiayaNotarisPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    tilBiayaSurveyPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    edtBiayaSurveyPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    tilAngsuranPertamaPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    edtAngsuranPertamaPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    tilBiayaPnbpFidusiaPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    edtBiayaPnbpFidusiaPerhitunganKendaraan.setVisibility(View.VISIBLE);
                    tilPokokPembiayaan.setHint("Pokok Pembiayaan (Rp)");
                    if (!form.equalsIgnoreCase("Edit")) setAllDefaultArrayList();
                    Log.d("Supplier Master Hit : ", "3");
                    mSearchSupplierMasterPresenter.getSearchSupplierMaster(token, "", assetTypeId, "2", aoBranch);
                } else {
                    itemJenisPembiayaan = new String[]{"PILIH"};
                    validator.removeRules(spnKopTujuanPenggunaanDana);
                    validator.removeRules(edtStnkPerhitunganKendaraan);
                    validator.removeRules(edtBiayaNotarisPerhitunganKendaraan);
                    validator.removeRules(edtBiayaSurveyPerhitunganKendaraan);
                    validator.removeRules(edtAngsuranPertamaPerhitunganKendaraan);
                    if (!form.equalsIgnoreCase("Edit")) setAllDefaultArrayList();
//                        Toast.makeText(FormPengajuanNonElcActivity.this, "Harap isi KOP terlebih dahulu", Toast.LENGTH_SHORT).show();

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormPengajuanNonElcActivity.this, R.layout.item_dropdown, R.id.id_item, itemJenisPembiayaan);
                spnKopJenisPembiayaan.setAdapter(adapter);
                if (form.equalsIgnoreCase("Draft") || form.equalsIgnoreCase("Edit")) {
                    if (application.getKorpFormulir().getTypeOfFinancing() != null) {
                        if (spnValidasiTipePengajuan.getSelectedItem().toString().equalsIgnoreCase("PSA")) {
                            spnKopJenisPembiayaan.setSelection(getIndex(spnKopJenisPembiayaan, application.getKorpFormulir().getTypeOfFinancing()));
                        } else {

                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        }

        spnPribadiStatusPernikahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkStatusPernikahan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnDataAsuransi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnDataAsuransi.setEnabled(false);
        autofillDataAsuransi();
    }

    private void setAllDefaultArrayList() {
        /*set Default Detail Product*/
        actDtProductSupplier.setSelectionFromPopUp(false);
        actDtProductSupplier.getText().clear();
        supplierNameArrayList.clear();
        setAdapterSupplierName();

        actDtProductMarketingSupplier.setSelectionFromPopUp(false);
        actDtProductMarketingSupplier.getText().clear();
        marketingSupplierNameArrayList.clear();
        setAdapterMarketingSupplier();

        actDtProductOffering.setSelectionFromPopUp(false);
        actDtProductOffering.getText().clear();
        productOfferingNameArrayList.clear();
        setAdapterProductOffering();

        actDtProductPos.setSelectionFromPopUp(false);
        actDtProductPos.getText().clear();
        posNameArrayList.clear();
        setAdapterPos();
        /*set Default Agunan*/
        spnJenisKendaraan.setSelection(getIndex(spnJenisKendaraan, "PILIH"));

        merkKendaraanNameArrayList.clear();
        setAdapterMerkKendaraan();
        merkKendaraanNameArrayList.add(new MerkKendaraanArrayList("PILIH"));
        setAdapterMerkKendaraan();

        actTypeKendaraan.setSelectionFromPopUp(false);
        actTypeKendaraan.getText().clear();
        tipeKendaraanNameArrayList.clear();
        setAdapterTipeKendaraan();

        manufacturingYearArrayList.clear();
        setAdapterTahunProduksi();
        manufacturingYearArrayList.add(new TahunKendaraanArrayList("PILIH"));
        setAdapterTahunProduksi();

        edtWarnaKendaraan.getText().clear();
        edtIsiSilinder.getText().clear();
        edtNoPolisi.getText().clear();
        edtNoRangka.getText().clear();
        edtNoMesin.getText().clear();
        edtNamaBpkbSendiri.getText().clear();
        edtNamaBpkbPasangan.getText().clear();
        edtNamaBpkbOrangLain.getText().clear();
        edtNamaBpkbSendiri.setEnabled(true);
        edtNamaBpkbPasangan.setEnabled(true);
        edtNamaBpkbOrangLain.setEnabled(true);
        rbBpkbAtasNamaSendiri.setChecked(false);
        rbBpkbAtasNamaPasangan.setChecked(false);
        rbBpkbAtasNamaOrangLain.setChecked(false);
        edtMasaBerlakuStnk.getText().clear();
        edtMasaBerlakuPajakStnk.getText().clear();
        /*set Default Data Perhitungan*/
        tenorArrayList.clear();
        setAdapterTenor();
        tenorArrayList.add(new TenorArrayList(
                "PILIH",
                "",
                "",
                "",
                ""));
        setAdapterTenor();
        setDefaultCalculatingToZeroNotValidate();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode + ", Intent" + data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 101) {
                Bundle extras = data.getExtras();
                Uri uriFile = (Uri) extras.get(CameraLandscapeActivity.URIFILE);
                File imageFile = new File(uriFile.getPath());
                ImageCompressor(imageFile, requestCode);
            } else if (requestCode == 102) {
                Bundle extras = data.getExtras();
                Uri uriFile = (Uri) extras.get(CameraActivity.URIFILE);
                File imageFile = new File(uriFile.getPath());
                ImageCompressor(imageFile, requestCode);
            } else {
                Bundle extras = data.getExtras();
                Uri uriFile = (Uri) extras.get(CameraOtherActivity.URIFILE);
                File imageFile = new File(uriFile.getPath());
                ImageCompressor(imageFile, requestCode);
            }
        }

       /* if(requestCode == 102){
            if (requestCode == requestCode && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                File imageFile = (File) extras.get(CameraActivity.COMPRESSED_FILE);
                Uri imageUri = Util.getPickImageResultUri(imageFile, getContext());
                Intent intent = new Intent(this, CropPhotoActivity.class);
                intent.putExtra("requestCode", requestCode);
                intent.setData(imageUri);
                startActivity(intent);
            }
        }*/
    }

    private void checkAutoCompleteTextView() {
//        alamat dan ktp
        if (!actAutoAlamatPemohon.isSelectionFromPopUp())
            actAutoAlamatPemohon.getText().clear();
        if (!actAutoKtpAlamatPemohon.isSelectionFromPopUp())
            actAutoKtpAlamatPemohon.getText().clear();
//        pasangan
        if (!actPasanganKota.isSelectionFromPopUp())
            actPasanganKota.getText().clear();
        if (!actPasanganProfesi.isSelectionFromPopUp())
            actPasanganProfesi.getText().clear();
        if (!actPasanganJobType.isSelectionFromPopUp())
            actPasanganJobType.getText().clear();
        if (!actPasanganJobPosition.isSelectionFromPopUp())
            actPasanganJobPosition.getText().clear();
        if (!actPasanganIndustri.isSelectionFromPopUp())
            actPasanganIndustri.getText().clear();
//        informasi kerabat
        if (!actAutoAlamatKerabat.isSelectionFromPopUp())
            actAutoAlamatKerabat.getText().clear();
//        alamat pekerjaan

        if (!actAutoAlamatPekerjaan.isSelectionFromPopUp())
            actAutoAlamatPekerjaan.getText().clear();
        if (!actProfesiPerusahaan.isSelectionFromPopUp())
            actProfesiPerusahaan.getText().clear();
        if (!actJobTypePerusahaan.isSelectionFromPopUp())
            actJobTypePerusahaan.getText().clear();
        if (!actJobPositionPerusahaan.isSelectionFromPopUp())
            actJobPositionPerusahaan.getText().clear();
        if (!actIndustriPerusahaan.isSelectionFromPopUp())
            actIndustriPerusahaan.getText().clear();
//        detail product
        if (!actDtProductSupplier.isSelectionFromPopUp())
            actDtProductSupplier.getText().clear();
        if (!actDtProductMarketingSupplier.isSelectionFromPopUp())
            actDtProductMarketingSupplier.getText().clear();
        if (!actDtProductOffering.isSelectionFromPopUp())
            actDtProductOffering.getText().clear();
        if (!actDtProductPos.isSelectionFromPopUp())
            actDtProductPos.getText().clear();
//        agunan
        if (!actTypeKendaraan.isSelectionFromPopUp())
            actTypeKendaraan.getText().clear();
    }

    private void createDialog(String bucketMessage, String agreement, String timeDelay, String amountOfFines) {
        LayoutInflater inflater = getLayoutInflater();
        View alertMessageBlacklist = inflater.inflate(R.layout.dialog_message_blacklist, null);
        builder.setView(alertMessageBlacklist);
        builder.setCancelable(false);

        final TextView tvBucketMessage = alertMessageBlacklist.findViewById(R.id.tv_bucket_message);
        final TextView tvNoAgreement = alertMessageBlacklist.findViewById(R.id.tv_no_agreement);
        final TextView tvTimeDelay = alertMessageBlacklist.findViewById(R.id.tv_time_delay);
        final TextView tvAmountOfFines = alertMessageBlacklist.findViewById(R.id.tv_amount_of_fines);
        final TextView tvMessageNotifikasi = alertMessageBlacklist.findViewById(R.id.tv_message_notifikasi);
        final LinearLayout lnMessageNotifikasi = alertMessageBlacklist.findViewById(R.id.ln_message_notifikasi);

        if (form.equalsIgnoreCase("Edit")) {
            if (msgNotifikasi != null) {
                tvMessageNotifikasi.setText(": " + msgNotifikasi);
                lnMessageNotifikasi.setVisibility(View.VISIBLE);
            }
        }

        tvBucketMessage.setText(": " + bucketMessage);
        tvNoAgreement.setText(": " + agreement);
        tvTimeDelay.setText(": " + timeDelay);
        tvAmountOfFines.setText(": " + amountOfFines);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, 0);
        }
    };

    @Subscribe
    public void onEventSwitchCamera(SwitchCamera e) {
        if (e.isFront()) {
            Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraActivity.class);
            if (e.isOther()) intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_OTHER);
            else intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
            intent.putExtra(CameraActivity.IS_OCR, false);
            intent.putExtra(CameraActivity.ISFRONT, true);
            startActivityForResult(intent, 102);
        } else {
            Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraActivity.class);
            if (e.isOther()) intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_OTHER);
            else intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
            intent.putExtra(CameraActivity.IS_OCR, false);
            intent.putExtra(CameraActivity.ISFRONT, false);
            startActivityForResult(intent, 102);
        }
    }

    @Subscribe
    public void onEventSwitchCameraOther(SwitchCameraOther e) {
        if (e.isFront()) {
            Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraOtherActivity.class);
            intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
            intent.putExtra(CameraOtherActivity.IS_OCR, false);
            intent.putExtra(CameraOtherActivity.ISFRONT, true);
            startActivityForResult(intent, 103);
        } else {
            Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraOtherActivity.class);
            intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
            intent.putExtra(CameraOtherActivity.IS_OCR, false);
            intent.putExtra(CameraOtherActivity.ISFRONT, false);
            startActivityForResult(intent, 103);
        }
    }

    private void ResetKOP() {
        initAutoCompleteAssignEdit(
                statusAutoComplete,
                supplierResponse,
                marketingSupplierResponse,
                productOfferingResponse,
                posListDownResponse,
                productOffTenorResponse,
                jenisKendaraanResponse,
                merkKendaraanResponse,
                tahunProduksiResponse,
                kelurahanResponse,
                recomendationResponse);
    }

    private void disableFieldAssignEdit() {
        edtNamaIbuPribadi.setEnabled(false);
        edtPribadiNamaPemohon.setEnabled(false);
        actTypeKendaraan.setEnabled(false);
        edtWarnaKendaraan.setEnabled(false);
        edtIsiSilinder.setEnabled(false);
        edtNoPolisi.setEnabled(false);
        edtNoRangka.setEnabled(false);
        edtNoMesin.setEnabled(false);
        rbBpkbAtasNamaSendiri.setEnabled(false);
        rbBpkbAtasNamaOrangLain.setEnabled(false);
        rbBpkbAtasNamaPasangan.setEnabled(false);
        edtMasaBerlakuPajakStnk.setEnabled(false);
        edtMasaBerlakuStnk.setEnabled(false);
        edtPokokPembiayaan.setEnabled(false);
        edtBiayaAdminPerhitunganKendaraan.setEnabled(false);
        edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(false);
        edtNamaBpkbSendiri.setEnabled(false);
        edtNamaBpkbOrangLain.setEnabled(false);
        edtNamaBpkbPasangan.setEnabled(false);
        actDtProductSupplier.setEnabled(false);
        actDtProductMarketingSupplier.setEnabled(false);
        actDtProductOffering.setEnabled(false);
        actDtProductPos.setEnabled(false);

        edtNamaIbuPribadi.setTextColor(Color.GRAY);
        edtPribadiNamaPemohon.setTextColor(Color.GRAY);
        actTypeKendaraan.setTextColor(Color.GRAY);
        edtWarnaKendaraan.setTextColor(Color.GRAY);
        edtIsiSilinder.setTextColor(Color.GRAY);
        edtNoMesin.setTextColor(Color.GRAY);
        edtNoPolisi.setTextColor(Color.GRAY);
        edtNoRangka.setTextColor(Color.GRAY);
        edtNamaBpkbSendiri.setTextColor(Color.GRAY);
        edtNamaBpkbOrangLain.setTextColor(Color.GRAY);
        edtNamaBpkbPasangan.setTextColor(Color.GRAY);
        edtMasaBerlakuStnk.setTextColor(Color.GRAY);
        edtMasaBerlakuPajakStnk.setTextColor(Color.GRAY);
        edtPokokPembiayaan.setTextColor(Color.GRAY);
        edtBiayaAdminPerhitunganKendaraan.setTextColor(Color.GRAY);
        edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.GRAY);
        actDtProductSupplier.setTextColor(Color.GRAY);
        actDtProductMarketingSupplier.setTextColor(Color.GRAY);
        actDtProductOffering.setTextColor(Color.GRAY);
        actDtProductPos.setTextColor(Color.GRAY);
    }

    private void enableFieldAssignEdit() {
//        edtNamaIbuPribadi.setEnabled(true);
//        edtPribadiNamaPemohon.setEnabled(true);
        actTypeKendaraan.setEnabled(true);
        edtWarnaKendaraan.setEnabled(true);
        edtIsiSilinder.setEnabled(true);
        edtNoPolisi.setEnabled(true);
        edtNoRangka.setEnabled(true);
        edtNoMesin.setEnabled(true);
        rbBpkbAtasNamaSendiri.setEnabled(true);
        rbBpkbAtasNamaOrangLain.setEnabled(true);
        rbBpkbAtasNamaPasangan.setEnabled(true);
        edtMasaBerlakuPajakStnk.setEnabled(true);
        edtMasaBerlakuStnk.setEnabled(true);
        edtPokokPembiayaan.setEnabled(false);
        edtBiayaAdminPerhitunganKendaraan.setEnabled(false);
        edtAngsuranPerbulanPerhitunganKendaraan.setEnabled(false);
        edtNamaBpkbSendiri.setEnabled(true);
        edtNamaBpkbOrangLain.setEnabled(true);
        edtNamaBpkbPasangan.setEnabled(true);
        actDtProductSupplier.setEnabled(true);
        actDtProductMarketingSupplier.setEnabled(true);
        actDtProductOffering.setEnabled(true);
        actDtProductPos.setEnabled(true);

//        edtNamaIbuPribadi.setTextColor(Color.BLACK);
//        edtPribadiNamaPemohon.setTextColor(Color.BLACK);
        actTypeKendaraan.setTextColor(Color.BLACK);
        edtWarnaKendaraan.setTextColor(Color.BLACK);
        edtIsiSilinder.setTextColor(Color.BLACK);
        edtNoMesin.setTextColor(Color.BLACK);
        edtNoPolisi.setTextColor(Color.BLACK);
        edtNoRangka.setTextColor(Color.BLACK);
        edtNamaBpkbSendiri.setTextColor(Color.BLACK);
        edtNamaBpkbOrangLain.setTextColor(Color.BLACK);
        edtNamaBpkbPasangan.setTextColor(Color.BLACK);
        edtMasaBerlakuStnk.setTextColor(Color.BLACK);
        edtMasaBerlakuPajakStnk.setTextColor(Color.BLACK);
        edtPokokPembiayaan.setTextColor(Color.BLACK);
        edtBiayaAdminPerhitunganKendaraan.setTextColor(Color.BLACK);
        edtAngsuranPerbulanPerhitunganKendaraan.setTextColor(Color.BLACK);
        actDtProductSupplier.setTextColor(Color.BLACK);
        actDtProductMarketingSupplier.setTextColor(Color.BLACK);
        actDtProductOffering.setTextColor(Color.BLACK);
        actDtProductPos.setTextColor(Color.BLACK);
    }
}
