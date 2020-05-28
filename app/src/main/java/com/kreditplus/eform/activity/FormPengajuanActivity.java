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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.query.In;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.dialog.BlacklistDialog;
import com.kreditplus.eform.dialog.CodePersetujuanDialog;
import com.kreditplus.eform.dialog.DialogPersyaratan;
import com.kreditplus.eform.dialog.PersetujuanTidakCancel;
import com.kreditplus.eform.dialog.YesNoDialog;
import com.kreditplus.eform.helper.Constant;
import com.kreditplus.eform.helper.EmailValidation;
import com.kreditplus.eform.helper.ImageViewRule;
import com.kreditplus.eform.helper.KodeAreaRule;
import com.kreditplus.eform.helper.KtpRule;
import com.kreditplus.eform.helper.MinPriceDataAsset;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.helper.NoHpRule;
import com.kreditplus.eform.helper.NotEmptyRule;
import com.kreditplus.eform.helper.NotZeroRule;
import com.kreditplus.eform.helper.NotZeroRulePengahsilanTetap;
import com.kreditplus.eform.helper.NpwpRule;
import com.kreditplus.eform.helper.OnItemSelectedListenerCustome;
import com.kreditplus.eform.helper.TextWatcherCustome;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.Aobranch;
import com.kreditplus.eform.model.ArrayList.AttachmentArrayList;
import com.kreditplus.eform.model.ArrayList.NamaBarangArrayList;
import com.kreditplus.eform.model.ArrayList.RekomendasiArrayList;
import com.kreditplus.eform.model.ArrayList.SupplierMasterArrayList;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetMaster;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.BranchMaster;
import com.kreditplus.eform.model.KelurahanResponse;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PosMaster;
import com.kreditplus.eform.model.ProductOfTenor;
import com.kreditplus.eform.model.ProductOfferingMaster;
import com.kreditplus.eform.model.ResultAobranch;
import com.kreditplus.eform.model.SupplierEmp;
import com.kreditplus.eform.model.SupplierMaster;
import com.kreditplus.eform.model.TblAgunan;
import com.kreditplus.eform.model.TblAlamat;
import com.kreditplus.eform.model.TblAsuransi;
import com.kreditplus.eform.model.TblDataKartuKredit;
import com.kreditplus.eform.model.TblDataPasangan;
import com.kreditplus.eform.model.TblDataPekerjaan;
import com.kreditplus.eform.model.TblDataPerhitungan;
import com.kreditplus.eform.model.TblDataPribadi;
import com.kreditplus.eform.model.TblDetailProduct;
import com.kreditplus.eform.model.TblKartuMembership;
import com.kreditplus.eform.model.TblKeterangan;
import com.kreditplus.eform.model.TblKontakDarurat;
import com.kreditplus.eform.model.TblKop;
import com.kreditplus.eform.model.TblLokasi;
import com.kreditplus.eform.model.TblRekomendasi;
import com.kreditplus.eform.model.TblTandaTangan;
import com.kreditplus.eform.model.bus.ConfirmCodeSignature;
import com.kreditplus.eform.model.bus.CropPhotoEvent;
import com.kreditplus.eform.model.bus.DialogSyaratBus;
import com.kreditplus.eform.model.bus.OcrBus;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.bus.ResendCodeSignatureEvent;
import com.kreditplus.eform.model.bus.SignatureEvent;
import com.kreditplus.eform.model.bus.SwitchCamera;
import com.kreditplus.eform.model.bus.SwitchCameraOther;
import com.kreditplus.eform.model.response.AssetMasterResponse;
import com.kreditplus.eform.model.response.BlackListResponse;
import com.kreditplus.eform.model.response.CheckEfNumberResponse;
import com.kreditplus.eform.model.response.DetailPerhitunganWhiteGoodsResponse;
import com.kreditplus.eform.model.response.FinancingObjectResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;
import com.kreditplus.eform.model.response.MarketingSupplierResponse;
import com.kreditplus.eform.model.response.MaskingResponse;
import com.kreditplus.eform.model.response.PosListDownResponse;
import com.kreditplus.eform.model.response.ProductOffTenorResponse;
import com.kreditplus.eform.model.response.ProductOfferingResponse;
import com.kreditplus.eform.model.response.RecomendationResponse;
import com.kreditplus.eform.model.response.ReferalCodeResponse;
import com.kreditplus.eform.model.response.SaldoKreditmuResponse;
import com.kreditplus.eform.model.response.SupplierResponse;
import com.kreditplus.eform.model.response.objecthelper.AoBranchObjt;
import com.kreditplus.eform.model.response.objecthelper.Application;
import com.kreditplus.eform.model.response.objecthelper.ApplicationBlacklist;
import com.kreditplus.eform.model.response.objecthelper.Asset;
import com.kreditplus.eform.model.response.objecthelper.AssetToken;
import com.kreditplus.eform.model.response.objecthelper.BodyToken;
import com.kreditplus.eform.model.response.objecthelper.CekKodeProgramObjct;
import com.kreditplus.eform.model.response.objecthelper.FaqObjt;
import com.kreditplus.eform.model.response.objecthelper.Industri;
import com.kreditplus.eform.model.response.objecthelper.JobPosition;
import com.kreditplus.eform.model.response.objecthelper.JobType;
import com.kreditplus.eform.model.response.objecthelper.MarketingSupplierObjt;
import com.kreditplus.eform.model.response.objecthelper.MaskingObjt;
import com.kreditplus.eform.model.response.objecthelper.ProductOfTenorObjt;
import com.kreditplus.eform.model.response.objecthelper.ProductOfferingObjt;
import com.kreditplus.eform.model.response.objecthelper.Profession;
import com.kreditplus.eform.model.response.objecthelper.SupplierMasterObjt;
import com.kreditplus.eform.model.response.objecthelper.Syarat;
import com.kreditplus.eform.model.response.objecthelper.TidakCancel;
import com.kreditplus.eform.presenter.AttachmentKendaraanPresenter;
import com.kreditplus.eform.presenter.BlackListPresenter;
import com.kreditplus.eform.presenter.CekKodeProgramPresenter;
import com.kreditplus.eform.presenter.CheckEfNumberPresenter;
import com.kreditplus.eform.presenter.CodeSignaturePresenter;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.GetReferalCodePresenter;
import com.kreditplus.eform.presenter.HargaAgunanElcPresenter;
import com.kreditplus.eform.presenter.KelurahanPresenter;
import com.kreditplus.eform.presenter.MaskingPresenter;
import com.kreditplus.eform.presenter.PengajuanDetailPresenter;
import com.kreditplus.eform.presenter.PengajuanDraftPresenter;
import com.kreditplus.eform.presenter.PerhitunganWhiteGoodsPresenter;
import com.kreditplus.eform.presenter.PosKreditPresenter;
import com.kreditplus.eform.presenter.ProductOffTenorPresenter;
import com.kreditplus.eform.presenter.RecomendationPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.SaldoKreditmuPresenter;
import com.kreditplus.eform.presenter.SearchAssetMasterPresenter;
import com.kreditplus.eform.presenter.SearchMarketingSupplierPresenter;
import com.kreditplus.eform.presenter.SearchProductOfferingPresenter;
import com.kreditplus.eform.presenter.SearchSupplierMasterPresenter;
import com.kreditplus.eform.presenter.SinkronisasiKendaraanPresenter;
import com.kreditplus.eform.presenter.SyaratDanKetentuanPresenter;
import com.kreditplus.eform.presenter.TujuanPembiayaanPresenter;
import com.kreditplus.eform.presenter.mvpview.AttachmentKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.BlackListMvpView;
import com.kreditplus.eform.presenter.mvpview.CekKodeProgramMvpView;
import com.kreditplus.eform.presenter.mvpview.CheckEfNumberMvpView;
import com.kreditplus.eform.presenter.mvpview.CodeSignatureMvpView;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.HargaAgunanElcMvpView;
import com.kreditplus.eform.presenter.mvpview.KelurahanMvpView;
import com.kreditplus.eform.presenter.mvpview.MaskingMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanDetailMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanDraftMvpView;
import com.kreditplus.eform.presenter.mvpview.PerhitunganWhiteGoodsMvpView;
import com.kreditplus.eform.presenter.mvpview.PosKreditvpView;
import com.kreditplus.eform.presenter.mvpview.ProductOffTenorMvpView;
import com.kreditplus.eform.presenter.mvpview.RecomendationMvpView;
import com.kreditplus.eform.presenter.mvpview.ReferalCodeMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.presenter.mvpview.SaldoKreditmuMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchAssetMasterMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchMarketingSupplierMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchProductOfferingMvpView;
import com.kreditplus.eform.presenter.mvpview.SearchSupplierMasterMvpView;
import com.kreditplus.eform.presenter.mvpview.SyaratDanKetentuanMvpView;
import com.kreditplus.eform.presenter.mvpview.SyncKendaraanMvpView;
import com.kreditplus.eform.presenter.mvpview.TujuanPembiayaanWGMvpView;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import itsmagic.present.permissionhelper.util.PermissionHelper;
import okhttp3.RequestBody;

import static android.graphics.Color.GRAY;

//import com.bumptech.glide.request.animation.GlideAnimation;

public class FormPengajuanActivity extends BaseActivity implements Validator.ValidationListener,
        RefreshTokenMvpView, BlackListMvpView, CodeSignatureMvpView, ReferalCodeMvpView, CekKodeProgramMvpView,
        SyaratDanKetentuanMvpView, SaldoKreditmuMvpView, MaskingMvpView, CoordinateMvpView, RecomendationMvpView,
        PengajuanDraftMvpView, PengajuanDetailMvpView, SearchAssetMasterMvpView, SearchSupplierMasterMvpView,
        SearchMarketingSupplierMvpView, SearchProductOfferingMvpView, ProductOffTenorMvpView, PerhitunganWhiteGoodsMvpView,HargaAgunanElcMvpView,
        PosKreditvpView, KelurahanMvpView, TujuanPembiayaanWGMvpView, SyncKendaraanMvpView, AttachmentKendaraanMvpView, CheckEfNumberMvpView {

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

    @Inject
    ApiService apiService;
    @Inject
    DatabaseService databaseService;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SharedPreferences.Editor editor;
    @Inject
    RefreshTokenPresenter mRefreshTokenPresenter;
    @Inject
    CodeSignaturePresenter mCodeSignaturePresenter;
    @Inject
    PengajuanDraftPresenter mPengajuanDraftPresenter;
    @Inject
    PengajuanDetailPresenter mPengajuanDetailPresenter;

    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
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
    @BindView(R.id.main_layout)
    ScrollView scrMainLayout;

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


    /*First Validiation*/
    @BindView(R.id.rl_camera_1)
    RelativeLayout rlCameraOne;

    @BindArray(R.array.gender_value)
    String[] genderValue;
    @BindArray(R.array.pendidikan_value)
    String[] pendidikanValue;
    @BindArray(R.array.warga_negara_value)
    String[] wargaNegaraValue;
    @BindArray(R.array.status_rumah)
    String[] statusRumahValue;
    @BindArray(R.array.agama_value)
    String[] agamaValue;
    @BindArray(R.array.status_pernikahan_value)
    String[] statusPernikahanValue;
    @BindArray(R.array.status_pernikahan)
    String[] statusPernikahanOri;
    @BindArray(R.array.jenis_kb_value)
    String[] jenisKbValue;
    @BindArray(R.array.metode_penjualan_value)
    String[] metodePenjualanValue;
    @BindArray(R.array.metode_penjualan)
    String[] metodePenjualan;
    @BindArray(R.array.type_master_sync)
    String[] typeMasterSyncs;
    @BindArray(R.array.agama)
    String[] agamaName;
    @BindArray(R.array.hubungan_kerabat)
    String[] hubunganKerabatValue;

    @BindViews({R.id.img_take_picture_1, R.id.img_take_picture_2, R.id.img_take_picture_3, R.id.img_take_picture_4, R.id.img_take_picture_5, R.id.img_take_picture_6, R.id.img_take_picture_7, R.id.img_take_picture_8, R.id.img_take_picture_9, R.id.img_take_picture_10, R.id.img_take_picture_11})
    List<View> viewTakeImages;
    @BindViews({R.id.img_delete_picture_1, R.id.img_delete_picture_2, R.id.img_delete_picture_3, R.id.img_delete_picture_4, R.id.img_delete_picture_5, R.id.img_delete_picture_6, R.id.img_delete_picture_7, R.id.img_delete_picture_8, R.id.img_delete_picture_9, R.id.img_delete_picture_10, R.id.img_take_picture_11})
    List<View> viewDeleteImages;
    @BindViews({R.id.pb_image_1, R.id.pb_image_2, R.id.pb_image_3, R.id.pb_image_4, R.id.pb_image_5, R.id.pb_image_6, R.id.pb_image_7, R.id.pb_image_8, R.id.pb_image_9, R.id.pb_image_10, R.id.img_take_picture_11})
    List<View> viewPbImages;
    @BindViews({R.id.img_camera_1, R.id.img_camera_2, R.id.img_camera_3, R.id.img_camera_4, R.id.img_camera_5, R.id.img_camera_6, R.id.img_camera_7, R.id.img_camera_8, R.id.img_camera_9, R.id.img_camera_10, R.id.img_take_picture_11})
    List<View> viewCameras;
    @BindViews({R.id.btn_muat_ulang_1, R.id.btn_muat_ulang_2, R.id.btn_muat_ulang_3, R.id.btn_muat_ulang_4, R.id.btn_muat_ulang_5, R.id.btn_muat_ulang_6, R.id.btn_muat_ulang_7, R.id.btn_muat_ulang_8, R.id.btn_muat_ulang_9, R.id.btn_muat_ulang_10, R.id.img_take_picture_11})
    List<View> viewRefreshImages;

    @BindViews({
            R.id.img_take_picture_1,
            R.id.img_take_picture_2,
            R.id.img_take_picture_3,
            R.id.img_take_picture_4})
    List<View> viewAttachment;

    @BindViews({
            R.id.img_ttd_pemohon_persetujuan})
    List<View> viewSignature;

    // Header
    @BindView(R.id.header_master_header)
    CardView rlHeaderMasterHeader;
    @BindView(R.id.header_data_nama_pemohon)
    CardView rlHeaderDataNamaPemohon;
    @BindView(R.id.header_data_validasi_awal)
    CardView rlHeaderDataValidasiAwal;
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
    @BindView(R.id.header_data_kartu_kredit)
    CardView rlHeaderDataKartuKredit;
    @BindView(R.id.header_data_kartu_membership)
    CardView rlHeaderDataKartuMembership;
    @BindView(R.id.header_detail_product)
    CardView rlHeaderDetailProduct;
    @BindView(R.id.header_detail_asset)
    CardView rlHeaderDetailAsset;
    @BindView(R.id.header_detail_npwp)
    CardView rlHeaderDetailNpwp;
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
    @BindView(R.id.header_informasi_penawaran)
    CardView rlHeaderInfromasiPenawaran;
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
    @BindView(R.id.content_data_kartu_kredit)
    RelativeLayout rlDataKartuKredit;
    @BindView(R.id.content_data_kartu_membership)
    RelativeLayout rlDataKartuMembership;
    @BindView(R.id.content_detail_product)
    RelativeLayout rlDetailProduct;
    @BindView(R.id.content_detail_asset)
    RelativeLayout rlDetailAsset;
    @BindView(R.id.content_detail_npwp)
    RelativeLayout rlDetailNpwp;
    @BindView(R.id.content_data_asuransi)
    RelativeLayout rlDataAsuransi;
    @BindView(R.id.content_data_perhitungan)
    RelativeLayout rlDataPerhitungan;
    @BindView(R.id.content_data_keterangan)
    RelativeLayout rlDataKeterangan;
    @BindView(R.id.content_image_pengajuan)
    RelativeLayout rlImagePengajuan;
    @BindView(R.id.content_persetujuan)
    RelativeLayout rlPersetujuan;
    @BindView(R.id.content_informasi_penawaran)
    RelativeLayout rlInformasiPenawaran;
    @BindView(R.id.content_persetujuan_tambahan)
    RelativeLayout rlPersetujuanTambahan;

    // Wrapper section
    @BindView(R.id.ln_wrapper_master_header)
    LinearLayout lnWrapperMasterHeader;
    @BindView(R.id.ln_wrapper_data_nama_pemohon)
    LinearLayout lnWrapperDataNamaPemohon;
    @BindView(R.id.ln_wrapper_data_validasi_awal)
    LinearLayout lnWrapperDataValidasiAwal;
    @BindView(R.id.ln_wrapper_data_pribadi)
    LinearLayout lnWrapperDataPribadi;
    @BindView(R.id.ln_wrapper_data_pasangan)
    LinearLayout lnWrapperDataPasangan;
    @BindView(R.id.ln_wrapper_data_alamat_pemohon)
    LinearLayout lnWrapperDataAlamatPemohon;
    @BindView(R.id.ln_wrapper_informasi_kerabat)
    LinearLayout lnWrapperInformasiKerabat;
    @BindView(R.id.ln_wrapper_data_pekerjaan)
    LinearLayout lnWrapperDataPekerjaan;
    @BindView(R.id.ln_wrapper_data_kartu_kredit)
    LinearLayout lnWrapperDataKartuKredit;
    @BindView(R.id.ln_wrapper_data_kartu_membership)
    LinearLayout lnWrapperDataKartuMembership;
    @BindView(R.id.ln_wrapper_detail_product)
    LinearLayout lnWrapperDetailProduct;
    @BindView(R.id.ln_wrapper_detail_asset)
    LinearLayout lnWrapperDetailAsset;
    @BindView(R.id.ln_wrapper_data_asuransi)
    LinearLayout lnWrapperDataAsuransi;
    @BindView(R.id.ln_wrapper_data_perhitungan)
    LinearLayout lnWrapperDataPerhitungan;
    @BindView(R.id.ln_wrapper_keterangan)
    LinearLayout lnWrapperKeterangan;
    @BindView(R.id.ln_wrapper_image_pengajuan)
    LinearLayout lnWrapperImagePengajuan;
    @BindView(R.id.ln_wrapper_persetujuan)
    LinearLayout lnWrapperPersetujuan;
    @BindView(R.id.ln_wrapper_persetujuan_tambahan)
    LinearLayout lnWrapperPErsetujuanTambahan;


    // Master Header
    @BindView(R.id.ln_contenainer_master_header)
    LinearLayout lnContainerMasterHeader;
    @BindView(R.id.spn_jenis_metode_penjualan)
    Spinner spnMetodePenjualan;
    @BindView(R.id.edt_status_kreditmu)
    EditText edtStatusKreditmu;

    //Validasi Awal
    @BindView(R.id.cv_take_camera)
    CardView cvTakeKtp;
    @BindView(R.id.ln_take_ktp)
    LinearLayout lnTakeKtp;
    @BindView(R.id.edt_no_ktp_pribadi_validasi)
    EditText edtNoKtpPibadiValidasi;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @BindView(R.id.edt_nama_legal_validasi)
    EditText edtNamaLegalValidasi;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @BindView(R.id.edt_nama_ibu_validasi)
    EditText edtIbuValidasi;
    @BindView(R.id.til_nama_legal)
    TextInputLayout tilNamaLegal;
    @BindView(R.id.til_nama_ibu)
    TextInputLayout tilNamaIbu;
    @BindView(R.id.edt_tanggal_lahir_pribadi_validasi)
    EditText edtTanggalLahirPribadiValidasi;
    @BindView(R.id.edt_handphone_pribadi_validasi)
    EditText edtHandphonePribadiValidasi;
    @BindView(R.id.spn_pilihan_cabang)
    Spinner spnPilihanCabang;

    // KOP
    @BindView(R.id.edt_tujuan_pembiyaan_lain)
    TextInputEditText edtTujuanPembiyaanLain;
    @BindView(R.id.img_drop_down_header_kop)
    ImageView imgDropDownKop;
    @BindView(R.id.spn_jenis_tujuan_pembiyaan)
    Spinner spnJenisTujuanPembiyaan;
    @BindView(R.id.edt_status_konsumen)
    TextInputEditText edtStatusKonsumen;
    @BindView(R.id.img_drop_down_nama_pemohon)
    ImageView imgDropDownNamaPemohon;

    //Nama Pemohon
    @BindView(R.id.spn_nama_pemohon)
    Spinner spnNamaPemohon;
    @BindView(R.id.tv_nama_pemohon)
    TextView tvNamaPemohon;

    // Data Pribadi
    @BindView(R.id.ln_container_data_pribadi)
    LinearLayout lnContainerDataPribadi;
    @BindView(R.id.ln_ttd_pemohon_lain)
    LinearLayout inTtdPemohonLain;
    @BindView(R.id.edtPribadiNoKK)
    EditText edtPribadiNoKK;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @NotEmpty
    @BindView(R.id.edt_nama_pribadi)
    EditText edtNamaPribadi;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @NotEmpty
    @BindView(R.id.edt_nama_ktp_pribadi)
    EditText edtNamaKtpPribadi;
    @NotEmpty
    @BindView(R.id.edt_no_ktp_pribadi)
    EditText edtNoKtpPribadi;
    @NotEmpty
    @BindView(R.id.edt_terbit_ktp_pribadi)
    EditText edtTerbitKtpPribadi;
    @BindView(R.id.spn_gender_pribadi)
    Spinner spnGenderPribadi;
    @Length(max = 20, message = "Data yang diinput maksimal 20 huruf")
    @NotEmpty
    @BindView(R.id.edt_tempat_lahir_pribadi)
    EditText edtTempatLahirPribadi;
    @NotEmpty
    @BindView(R.id.edt_tanggal_lahir_pribadi)
    EditText edtTanggalLahirPribadi;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @NotEmpty
    @BindView(R.id.edt_nama_ibu_pribadi)
    EditText edtNamaIbuPribadi;
    @BindView(R.id.ln_pendidikan)
    LinearLayout lnPendidikan;
    @BindView(R.id.spn_pendidikan_pribadi)
    Spinner spnPendidikanPribadi;
    @BindView(R.id.spn_warga_negara_pribadi)
    Spinner spnWargaNegaraPribadi;
    @BindView(R.id.spn_status_rumah_pribadi)
    Spinner spnStatusRumahPribadi;
    @NotEmpty
    @BindView(R.id.edt_tinggal_sejak_tahun_pribadi)
    EditText edtTinggalSejakTahunPribadi;
    @NotEmpty
    @BindView(R.id.edt_tinggal_sejak_bulan_pribadi)
    EditText edtTinggalSejakBulanPribadi;
    @BindView(R.id.ln_agama_pribadi)
    LinearLayout lnAgamaPribadi;
    @BindView(R.id.spn_agama_pribadi)
    Spinner spnAgamaPribadi;
    @BindView(R.id.ln_status_pernikahan)
    LinearLayout lnStatusPernikahan;
    @BindView(R.id.spn_status_pernikahan_pribadi)
    Spinner spnPernikahanPribadi;
    @NotEmpty
    @BindView(R.id.edt_jml_tanggungan_pribadi)
    EditText edtJumlahTanggunganPribadi;
    @NotEmpty
    @BindView(R.id.edt_handphone_pribadi)
    EditText edtHandphonePribadi;
    @Length(max = 30, message = "Invalid Length")
    @BindView(R.id.edt_email_pribadi)
    EditText edtEmailPribadi;
    @BindView(R.id.img_drop_down_pribadi)
    ImageView imgDropDownPribadi;
    @BindView(R.id.img_drop_down_npwp)
    ImageView imgDropDownNpwp;
    @BindView(R.id.til_no_npwp_pribadi)
    TextInputLayout tilNoNpwpPribadi;
    @BindView(R.id.til_no_npwp_detail)
    TextInputLayout tilNoNpwpDetail;
    @BindView(R.id.edt_handphone_pribadi_hide)
    EditText edtHandphonePribadiHide;
    @BindView(R.id.edt_pendidikan_pribadi_hide)
    EditText edtPendidikanPribadiHide;
    @BindView(R.id.edt_status_pernikahan_pribadi_hide)
    EditText edtStatusPernikahanPribadiHide;
    @BindView(R.id.edt_jml_tanggungan_pribadi_hide)
    EditText edtJmlTanggunganPribadiHide;
    @BindView(R.id.edt_nama_ibu_pribadi_hide)
    EditText edtNamaIbuPribadiHide;
    //    @Length(min = 15, max = 15, message = "Invalid Length")
    @BindView(R.id.edt_no_npwp_pribadi)
    MaskEditText edtNoNpwpPribadi;
    //    @Length(min = 15, max = 15, message = "Invalid Length")
    @BindView(R.id.edt_no_npwp_detail)
    MaskEditText edtNoNpwpDetail;

    @BindViews({R.id.edt_nama_pribadi, R.id.edt_nama_ktp_pribadi, R.id.edt_no_ktp_pribadi, R.id.edt_terbit_ktp_pribadi, R.id.edt_tempat_lahir_pribadi, R.id.edt_tanggal_lahir_pribadi, R.id.edt_nama_ibu_pribadi, R.id.edt_tinggal_sejak_tahun_pribadi, R.id.edt_tinggal_sejak_bulan_pribadi, R.id.edt_jml_tanggungan_pribadi, R.id.edt_handphone_pribadi, R.id.edt_email_pribadi})
    List<View> viewDataPribadi;

    @BindViews({R.id.edt_nama_pribadi, R.id.edt_nama_ktp_pribadi, R.id.edt_no_ktp_pribadi, R.id.edt_terbit_ktp_pribadi, R.id.edt_tempat_lahir_pribadi, R.id.edt_tanggal_lahir_pribadi, R.id.edt_nama_ibu_pribadi, R.id.edt_tinggal_sejak_tahun_pribadi, R.id.edt_tinggal_sejak_bulan_pribadi, R.id.edt_jml_tanggungan_pribadi, R.id.edt_handphone_pribadi, R.id.edt_email_pribadi, R.id.edt_no_npwp_pribadi, R.id.edtPribadiNoKK})
    List<View> viewDataPribadiWithNpwp;

    // Data Pribadi detail
    @BindView(R.id.tl_pengajuan_data_pribadi)
    TableLayout tlPengajuanDataPribadi;
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
    @BindView(R.id.tv_statusPernikahan)
    TextView tvStatusPernikahan;
    @BindView(R.id.tv_jenisKelamin)
    TextView tvJenisKelamin;
    @BindView(R.id.tv_jenisMetode)
    TextView tvJenisMetode;
    @BindView(R.id.tv_jenisTujuanPembiayaan)
    TextView tvJenisTujuanPembiayaan;
    @BindView(R.id.tv_statusRumah)
    TextView tvStatusRumah;
    @BindView(R.id.tv_Agama)
    TextView tvAgama;
    @BindView(R.id.tv_pendidikan)
    TextView tvPendidikan;
    @BindView(R.id.tv_hubunganKerabat)
    TextView tvHubunganKerabat;


    // Data pasangan
    @BindViews({R.id.edt_nama_pasangan, R.id.edt_no_ktp_pasangan, R.id.edt_tempat_lahir_pasangan, R.id.edt_tanggal_lahir_pasangan, R.id.edt_no_hp_pasangan})
    List<View> viewDataPasanganElc;

    @BindView(R.id.img_drop_down_pasangan)
    ImageView imgDropDownPasangan;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @BindView(R.id.edt_nama_pasangan)
    EditText edtNamaPasangan;
    @BindView(R.id.edt_no_ktp_pasangan)
    EditText edtNoKtpPasangan;
    @Length(max = 20, message = "Data yang diinput maksimal 20 huruf")
    @BindView(R.id.edt_tempat_lahir_pasangan)
    EditText edtTempatLahirPasangan;
    @BindView(R.id.edt_tanggal_lahir_pasangan)
    EditText edtTanggalLahirPasangan;
    @BindView(R.id.edt_no_hp_pasangan)
    EditText edtNoHpPasangan;

    // Alamat Pemohon Tinggal
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_tinggal)
    EditText edtAlamatTinggal;
    @NotEmpty
    @BindView(R.id.act_auto_alamat_pemohon)
    NiceAutoCompleteTextView actAutoAlamatPemohon;
    @NotEmpty
    @BindView(R.id.edt_rt_tinggal)
    EditText edtRtTinggal;
    @NotEmpty
    @BindView(R.id.edt_rw_tinggal)
    EditText edtRwTinggal;
    @BindView(R.id.pb_kota_tinggal)
    ProgressBar pbKotaTinggal;
    @BindView(R.id.edt_area_phone_tinggal)
    EditText edtAreaPhoneTinggal;
    @BindView(R.id.edt_phone_tinggal)
    EditText edtPhoneTinggal;

    // Alamat Pemohon Ktp
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_ktp)
    EditText edtAlamatKtp;
    @NotEmpty
    @BindView(R.id.act_auto_ktp_alamat_pemohon)
    NiceAutoCompleteTextView actAutoKtpAlamatPemohon;
    @NotEmpty
    @BindView(R.id.edt_rt_ktp)
    EditText edtRtKtp;
    @NotEmpty
    @BindView(R.id.edt_rw_ktp)
    EditText edtRwKtp;
    @BindView(R.id.pb_kota_kerabat)
    ProgressBar pbKotaKerabat;
    @BindView(R.id.pb_kelurahan_ktp)
    ProgressBar pbKelurahanKtp;
    @BindView(R.id.edt_area_phone_ktp)
    EditText edtAreaPhoneKtp;
    @BindView(R.id.edt_phone_ktp)
    EditText edtPhoneKtp;
    @BindView(R.id.img_drop_down_alamat)
    ImageView imgDropDownAlamat;
    @BindViews({R.id.edt_alamat_tinggal, R.id.act_auto_alamat_pemohon, R.id.edt_rt_tinggal, R.id.edt_rw_tinggal, R.id.edt_area_phone_tinggal, R.id.edt_phone_tinggal, R.id.edt_alamat_ktp, R.id.act_auto_ktp_alamat_pemohon, R.id.edt_rt_ktp, R.id.edt_rw_ktp, R.id.edt_area_phone_ktp, R.id.edt_phone_ktp})
    List<View> viewAlamat;
    @BindView(R.id.ln_visible_alamat_ktp)
    LinearLayout lnVisibleAlamatKtp;
    @BindView(R.id.cbx_alamat_ktp)
    CheckBox cbxAlamatKtp;

    // Informasi Kerabat
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @NotEmpty
    @BindView(R.id.edt_nama_kerabat)
    EditText edtNamaKerabat;
    @BindView(R.id.spn_hubungan_kerabat)
    Spinner spnHubunganKerabat;
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_kerabat)
    EditText edtAlamatKerabat;
    @NotEmpty
    @BindView(R.id.act_auto_alamat_kerabat)
    NiceAutoCompleteTextView actAutoAlamatKerabat;
    @NotEmpty
    @BindView(R.id.edt_rt_kerabat)
    EditText edtRtKerabat;
    @NotEmpty
    @BindView(R.id.edt_rw_kerabat)
    EditText edtRwKerabat;
    @BindView(R.id.edt_area_phone_rumah_kerabat)
    EditText edtAreaPhoneRumahKerabat;
    @BindView(R.id.edt_phone_rumah_kerabat)
    EditText edtPhoneRumahKerabat;
    @BindView(R.id.edt_area_phone_kantor_kerabat)
    EditText edtAreaPhoneKantorKerabat;
    @BindView(R.id.edt_phone_kantor_kerabat)
    EditText edtPhoneKantorKerabat;
    @NotEmpty
    @BindView(R.id.edt_hp_kerabat)
    EditText edtHpKerabat;
    @BindView(R.id.img_drop_down_kerabat)
    ImageView imgDropDownKerabat;
    @BindViews({R.id.edt_nama_kerabat, R.id.edt_alamat_kerabat, R.id.act_auto_alamat_kerabat, R.id.edt_rt_kerabat, R.id.edt_rw_kerabat, R.id.edt_area_phone_rumah_kerabat, R.id.edt_phone_rumah_kerabat, R.id.edt_area_phone_kantor_kerabat, R.id.edt_phone_kantor_kerabat, R.id.edt_hp_kerabat})
    List<View> viewKerabat;

    // Data Pekerjaan
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @NotEmpty
    @BindView(R.id.edt_nama_perusahaan)
    EditText edtNamaPerusahaan;
    @Length(min = 3, max = 100, message = "Data yang diinput minimal 3 karakter dan maksimal 100 karakter")
    @NotEmpty
    @BindView(R.id.edt_alamat_perusahaan)
    EditText edtAlamatPerusahaan;
    @NotEmpty
    @BindView(R.id.act_auto_alamat_pekerjaan)
    NiceAutoCompleteTextView actAutoAlamatPekerjaan;
    @NotEmpty
    @BindView(R.id.edt_rt_perusahaan)
    EditText edtRtPerusahaan;
    @NotEmpty
    @BindView(R.id.edt_rw_perusahaan)
    EditText edtRwPerusahaan;
    @BindView(R.id.pb_kelurahan_perusahaan)
    ProgressBar pbKelurahanPerusahaan;
    @NotEmpty
    @BindView(R.id.edt_area_phone_perusahaan)
    EditText edtAreaPhonePerusahaan;
    @NotEmpty
    @BindView(R.id.edt_phone_perusahaan)
    EditText edtPhonePerusahaan;
    @BindView(R.id.edtPekerjaanNoHp)
    EditText edtPekerjaanNoHp;
    @BindView(R.id.tilPekerjaanNoHp)
    TextInputLayout tilPekerjaanNoHp;
    @NotEmpty
    @BindView(R.id.edt_bekerja_sejak_perusahaan)
    EditText edtBekerjaSejakPerusahaan;
    @NotEmpty
    @BindView(R.id.act_profesi_perusahaan)
    NiceAutoCompleteTextView actProfesiPerusahaan;
    @BindView(R.id.ln_data_pekerjaan_phone)
    LinearLayout lnDataPekerjaanPhone;
    @BindView(R.id.pb_profesi_perusahaan)
    ProgressBar pbProfesiPerusahaan;
    @NotEmpty
    @BindView(R.id.act_job_type_perusahaan)
    NiceAutoCompleteTextView actJobTypePerusahaan;
    @BindView(R.id.pb_jobtype_perusahaan)
    ProgressBar pbJobtypePerusahaan;
    @NotEmpty
    @BindView(R.id.act_job_position_perusahaan)
    NiceAutoCompleteTextView actJobPositionPerusahaan;
    @BindView(R.id.pb_job_position_perusahaan)
    ProgressBar pbJobPositionPerusahaan;
    @NotEmpty
    @BindView(R.id.act_industri_perusahaan)
    NiceAutoCompleteTextView actIndustriPerusahaan;
    @NotEmpty
    @BindView(R.id.edt_penghasilan_tetap_perusahaan)
    EditText edtPenghasilanTetapPerusahaan;
    @BindView(R.id.edt_penghasilan_lain_perusahaan)
    EditText edtPenghasilanLainPerusahaan;
    @BindView(R.id.edt_penghasilan_pasangan_perusahaan)
    EditText edtPenghasilanPasanganPerusahaan;
    @NotEmpty
    @BindView(R.id.edt_biaya_hidup_perusahaan)
    EditText edtBiayaHidupPerusahaan;
    @BindView(R.id.rb_group_isaffiliate_perusahaan)
    RadioGroup rbGroupIsAffiliatePerusahaan;
    @BindView(R.id.rb_yes)
    RadioButton rbYesPerusahaan;
    @BindView(R.id.rb_no)
    RadioButton rbNoPerusahaan;
    @BindView(R.id.img_drop_down_pekerjaan)
    ImageView imgDropDownPekerjaan;
    @BindViews({R.id.edt_nama_perusahaan, R.id.edt_alamat_perusahaan, R.id.act_auto_alamat_pekerjaan, R.id.edt_rt_perusahaan, R.id.edt_rw_perusahaan, R.id.edt_area_phone_perusahaan, R.id.edt_phone_perusahaan, R.id.edt_bekerja_sejak_perusahaan, R.id.act_profesi_perusahaan, R.id.act_job_type_perusahaan, R.id.act_job_position_perusahaan, R.id.act_industri_perusahaan, R.id.edt_penghasilan_tetap_perusahaan, R.id.edt_penghasilan_lain_perusahaan, R.id.edt_penghasilan_pasangan_perusahaan, R.id.edt_biaya_hidup_perusahaan, R.id.act_counterpart_perusahaan, R.id.edt_debt_business_perusahaan, R.id.edt_debt_group_perusahaan})
    List<View> viewPekerjaan;

    // Data kartu kredit
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @BindView(R.id.edt_nama_bank)
    EditText edtNamaBankKartuKredit;
    @BindView(R.id.edt_no_kartu_kredit)
    EditText edtNoKartuKredit;
    @Length(max = 50, message = "Data yang diinput maksimal 50 huruf")
    @BindView(R.id.edt_jenis_kartu_kredit)
    EditText edtJenisKartuKredit;
    @BindView(R.id.edt_limit_kartu_kredit)
    EditText edtLimitKartuKredit;
    @BindView(R.id.edt_month_expired_kartu_kredit)
    EditText edtBulanKadaluarsaKartuKredit;
    @BindView(R.id.edt_year_expired_kartu_kredit)
    EditText edtTahunKadaluarsaKartuKredit;
    @BindView(R.id.img_drop_down_data_kartu_kredit)
    ImageView imgDropDownDataKartuKredit;
    @BindViews({R.id.edt_nama_bank, R.id.edt_no_kartu_kredit, R.id.edt_jenis_kartu_kredit, R.id.edt_limit_kartu_kredit, R.id.edt_month_expired_kartu_kredit, R.id.edt_year_expired_kartu_kredit})
    List<View> viewKartuKredit;

    // Data Membership
    @BindView(R.id.edt_no_membership)
    EditText edtNoMembership;
    @BindView(R.id.edt_tanggal_efektif)
    EditText edtTanggalEfektif;
    @BindView(R.id.edt_tanggal_expired)
    EditText edtTanggalExpired;
    @BindView(R.id.img_drop_down_data_kartu_membership)
    ImageView imgDropDownDataKartuMembership;
    @BindViews({R.id.edt_no_membership, R.id.edt_tanggal_efektif, R.id.edt_tanggal_expired})
    List<View> viewDataMembership;

    // Detail Product
    @NotEmpty
    @BindView(R.id.act_product_offering_product)
    NiceAutoCompleteTextView actProductOfferingProduct;
    @BindView(R.id.act_pos_product)
    NiceAutoCompleteTextView actPosProduct;
    @BindView(R.id.img_drop_down_product)
    ImageView imgDropDownProduct;
    @BindViews({R.id.act_supplier_asset, R.id.act_marketing_supplier_asset, R.id.act_product_offering_product, R.id.act_pos_product})
    List<View> viewDetailProductElc;
    @BindView(R.id.tv_kode_khusus)
    TextView tvKodeKhusus;

    @BindView(R.id.tv_jumlah_asset)
    TextView tvJumlahAsset;
    @BindView(R.id.btn_add_asset)
    ImageView btnAddAsset;
    @BindView(R.id.ll_parent_total_asset)
    LinearLayout llParentTotalAsset;

    @NotEmpty
    @BindView(R.id.act_supplier_asset)
    NiceAutoCompleteTextView actSupplierAsset;
    @BindView(R.id.pb_supplier)
    ProgressBar pbSupplier;
    @NotEmpty
    @BindView(R.id.act_marketing_supplier_asset)
    NiceAutoCompleteTextView actMarketingSupplierAsset;
    @BindView(R.id.act_referal_code)
    EditText actReferalCode;
    @BindView(R.id.pb_marketing)
    ProgressBar pbMarketing;
    @BindView(R.id.img_drop_down_asset)
    ImageView imgDropDownAsset;

    //    locations
    @BindView(R.id.validate_action)
    EditText validateAction;
    @BindView(R.id.validate_longitude)
    EditText validateLongitude;
    @BindView(R.id.validate_latitude)
    EditText validateLatitude;
    @BindView(R.id.take_ktp_action)
    EditText takeKtpAction;
    @BindView(R.id.take_ktp_longitude)
    EditText takeKtpLongitude;
    @BindView(R.id.take_ktp_latitude)
    EditText takeKtpLatitude;
    @BindView(R.id.take_customer_action)
    EditText takeCustomerAction;
    @BindView(R.id.take_customer_longitude)
    EditText takeCustomerLongitude;
    @BindView(R.id.take_customer_latitude)
    EditText takeCustomerLatitude;
    @BindView(R.id.take_signature_action)
    EditText takeSignatureAction;
    @BindView(R.id.take_signature_longitude)
    EditText takeSignatureLongitude;
    @BindView(R.id.take_signature_latitude)
    EditText takeSignatureLatitude;
    @BindView(R.id.submit_action)
    EditText submitAction;
    @BindView(R.id.submit_longitude)
    EditText submitLongitude;
    @BindView(R.id.submit_latitude)
    EditText submitLatitude;
    @BindView(R.id.sync_action)
    EditText syncAction;
    @BindView(R.id.sync_longitude)
    EditText syncLongitude;
    @BindView(R.id.sync_latitude)
    EditText syncLatitude;
    @BindView(R.id.take_paycheck_action)
    EditText takePaycheckAction;
    @BindView(R.id.take_paycheck_longitude)
    EditText takePaycheckLongitude;
    @BindView(R.id.take_paycheck_latitude)
    EditText takePaycheckLatitude;
    @BindView(R.id.take_additional_documents_action)
    EditText takeAdditionalDocumentsAction;
    @BindView(R.id.take_additional_documents_longitude)
    EditText takeAdditionalDocumentsLongitude;
    @BindView(R.id.take_additional_documents_latitude)
    EditText takeAdditionalDocumentsLatitude;

    private List<View> viewsAssetNamaBarang = new ArrayList<>();
    private List<View> viewsAssetTipeBarang = new ArrayList<>();
    private List<View> viewsAssetHarga = new ArrayList<>();
    private List<View> viewsAssetDp = new ArrayList<>();
    private List<View> viewsAssetDiscount = new ArrayList<>();

    // Data Asuransi
    @BindView(R.id.cv_manual_premi_asuransi)
    CardView cvManualPremiAsuransi;
    @BindView(R.id.rb_group_asuransi_agunan)
    RadioGroup rbGroupAsuransiAgunan;
    @BindView(R.id.rb_group_personal_accident_asuransi)
    RadioGroup rbGroupPersonalAccidentAsuransi;
    @BindView(R.id.img_drop_down_asurani)
    ImageView imgDropDownAsuransi;
    @BindView(R.id.rb_group_manual_asuransi)
    RadioGroup rbGroupManualAsuransi;
    @BindView(R.id.rb_manual_yes)
    RadioButton rbManualYes;
    @BindView(R.id.rb_manual_no)
    RadioButton rbManualNo;
    @BindView(R.id.edt_manual_premi_asuransi)
    EditText edtPremiAsuransiManual;
    @BindView(R.id.til_premi_manual)
    TextInputLayout tilPremiManual;
    @BindView(R.id.rb_asuransi_agunan_yes)
    RadioButton rbAsuransiAgunanYes;
    @BindView(R.id.rb_asuransi_agunan_no)
    RadioButton rbAsuransiAgunanNo;

    @BindArray(R.array.tenor)
    String[] tenors;

    @BindArray(R.array.pilihan_cabang)
    String[] cabangs;

    // Data Perhitungan Elektronik
    @NotEmpty
    @BindView(R.id.edt_purchase_price_perhitungan)
    IndonesianCurrencyEditText edtPurchasePricePerhitungan;
    @NotEmpty
    @BindView(R.id.edt_discount_perhitungan)
    IndonesianCurrencyEditText edtDiscountPerhitungan;
    @NotEmpty
    @BindView(R.id.edt_dp_perhitungan)
    TextInputEditText edtDpPerhitungan;
    @NotEmpty
    @BindView(R.id.edt_ntf_perhitungan)
    IndonesianCurrencyEditText edtNtfPerhitungan;
    @BindView(R.id.edt_bebas_bunga_perhitungan)
    IndonesianCurrencyEditText edtBebasBungaPerhitungan;
    @BindView(R.id.edt_bunga_pembiayaan_bulan)
    IndonesianCurrencyEditText edtBungaPembiayaanBulan;
    @BindView(R.id.edt_pembayaran_delaer)
    IndonesianCurrencyEditText edtPembayaranDelaer;
    @NotEmpty
    @BindView(R.id.edt_premi_asuransi)
    IndonesianCurrencyEditText edtPremiAsuransi;
    @NotEmpty
    @BindView(R.id.edt_biaya_admin_perhitungan)
    IndonesianCurrencyEditText edtBiayaAdminPerhitungan;
    @NotEmpty
    @BindView(R.id.edt_biaya_lainnya)
    IndonesianCurrencyEditText edtBiayaLainnya;
    @NotEmpty
    @BindView(R.id.edt_jumlah_pembiayaan)
    IndonesianCurrencyEditText edtJumlahPembiayaan;
    @NotEmpty
    @BindView(R.id.edt_total_bunga_pembiayaan)
    IndonesianCurrencyEditText edtTotalBungaPembiayaan;
    @NotEmpty
    @BindView(R.id.edt_total_pinjaman)
    IndonesianCurrencyEditText edtTotalPinjaman;
    @BindView(R.id.spn_tenor_product)
    Spinner spnTenorProduct;
    @BindView(R.id.tvTenorELC)
    TextView tvTenorELC;
    @NotEmpty
    @BindView(R.id.edt_flat_rate_perhitungan)
    EditText edtFlatRatePerhitungan;
    @NotEmpty
    @BindView(R.id.edt_effective_rate_perhitungan)
    IndonesianCurrencyEditText edtEffectiveRatePerhitungan;
    @BindView(R.id.edt_angsuran_perbulan_bebas_bunga)
    IndonesianCurrencyEditText edtAngsuranPerbulanBebasBunga;
    @NotEmpty
    @BindView(R.id.edt_angsuran_perbulan)
    IndonesianCurrencyEditText edtAngsuranPerbulan;
    @NotEmpty
    @BindView(R.id.edt_pembayaran_pertama)
    IndonesianCurrencyEditText edtPembayaranPertama;
    @NotEmpty
    @BindView(R.id.edt_refund_subsidi_perhitungan)
    IndonesianCurrencyEditText edtRefundSubsidiPerhitungan;
    @BindView(R.id.img_drop_down_perhitungan)
    ImageView imgDropDownPerhitungan;
    @BindView(R.id.edt_tipe_pembayaran)
    EditText edtTipePembayaran;
    @BindView(R.id.tilNtf)
    TextInputLayout tilNtf;
    @BindView(R.id.til_dp)
    TextInputLayout tilDp;
    @BindViews({R.id.edt_purchase_price_perhitungan, R.id.edt_discount_perhitungan, R.id.edt_dp_perhitungan, R.id.edt_ntf_perhitungan, R.id.edt_biaya_admin_perhitungan, R.id.edt_flat_rate_perhitungan, R.id.edt_angsuran_perbulan, R.id.edt_refund_subsidi_perhitungan, R.id.edt_premi_asuransi})
    List<View> viewPerhitungan;

    // Data Perhitungan Elektronik Container
    @BindView(R.id.et_tenor_product_container)
    EditText etTenorProductContainer;
    @BindView(R.id.et_flat_rate_perhitungan_container)
    EditText etFlatRatePerhitunganContainer;
    @BindView(R.id.et_biaya_admin_perhitungan_container)
    EditText etBiayaAdminPerhitunganContainer;
    @BindView(R.id.et_tipe_pembayaran_container)
    EditText etTipePembayaranContainer;
    @BindView(R.id.et_biaya_lainnya_container)
    EditText etBiayaLainnyaContainer;
    @BindView(R.id.et_refund_subsidi_perhitungan_container)
    EditText etRefundSubsidiPerhitunganContainer;
    @BindView(R.id.et_purchase_price_perhitungan_container)
    EditText etPurchasePricePerhitunganContainer;
    @BindView(R.id.et_discount_perhitungan_container)
    EditText etDiscountPerhitunganContainer;
    @BindView(R.id.et_dp_perhitungan_container)
    EditText etDpPerhitunganContainer;
    @BindView(R.id.et_ntf_perhitungan_container)
    EditText etNtfPerhitunganContainer;
    @BindView(R.id.et_premi_asuransi_container)
    EditText etPremiAsuransiContainer;
    @BindView(R.id.et_jumlah_pembiayaan_container)
    EditText etJumlahPembiayaanContainer;
    @BindView(R.id.et_bunga_pembiayaan_container)
    EditText etBungaPembiayaanContainer;
    @BindView(R.id.et_total_pinjaman_container)
    EditText etTotalPinjamanContainer;
    @BindView(R.id.et_angsuran_perhitungan_container)
    EditText etAngsuranPerhitunganContainer;
    @BindView(R.id.et_angsuran_pertama_perhitungan_container)
    EditText etAngsuranPertamaPerhitunganContainer;

    @BindView(R.id.edt_keterangan)
    EditText edtKeterangan;

    @BindView(R.id.img_drop_down_attachment)
    ImageView imgDropDownAttachment;
    @BindView(R.id.txt_attachment_error)
    TextView txtAttachmentError;
    @BindView(R.id.img_take_picture_1)
    ImageView imgTakePicture1;
    @BindView(R.id.img_delete_picture_1)
    ImageView imgDeletePicture1;
    @BindView(R.id.img_take_picture_2)
    ImageView imgTakePicture2;
    @BindView(R.id.img_delete_picture_2)
    ImageView imgDeletePicture2;
    @BindView(R.id.img_take_picture_3)
    ImageView imgTakePicture3;
    @BindView(R.id.img_delete_picture_3)
    ImageView imgDeletePicture3;
    @BindView(R.id.img_take_picture_4)
    ImageView imgTakePicture4;
    @BindView(R.id.img_delete_picture_4)
    ImageView imgDeletePicture4;
    @BindView(R.id.img_take_picture_5)
    ImageView imgTakePicture5;
    @BindView(R.id.img_delete_picture_5)
    ImageView imgDeletePicture5;
    @BindView(R.id.img_take_picture_6)
    ImageView imgTakePicture6;
    @BindView(R.id.img_delete_picture_6)
    ImageView imgDeletePicture6;
    @BindView(R.id.img_take_picture_7)
    ImageView imgTakePicture7;
    @BindView(R.id.img_delete_picture_7)
    ImageView imgDeletePicture7;
    @BindView(R.id.img_take_picture_8)
    ImageView imgTakePicture8;
    @BindView(R.id.img_delete_picture_8)
    ImageView imgDeletePicture8;
    @BindView(R.id.img_take_picture_9)
    ImageView imgTakePicture9;
    @BindView(R.id.img_delete_picture_9)
    ImageView imgDeletePicture9;
    @BindView(R.id.img_take_picture_10)
    ImageView imgTakePicture10;
    @BindView(R.id.img_delete_picture_10)
    ImageView imgDeletePicture10;
    @BindView(R.id.img_take_picture_11)
    ImageView imgTakePicture11;
    @BindView(R.id.img_delete_picture_11)
    ImageView imgDeletePicture11;
    @BindView(R.id.ln_attachment)
    LinearLayout lnAttachment;

    // Persetujuan
    @BindView(R.id.img_ttd_pasangan_persetujuan)
    ImageView imgTtdPasanganPersetujuan;
    @BindView(R.id.img_ttd_pemohon_persetujuan)
    ImageView imgTtdPemohonPersetujuan;
    @BindView(R.id.txt_date_pasangan_persetujuan)
    TextView txtDatePasanganPersetujuan;
    @BindView(R.id.txt_date_pemohon_persetujuan)
    TextView txtDatePemohonPersetujuan;
    @BindView(R.id.img_drop_down_persetujuan)
    ImageView imgDropDownPersetujuan;
    @BindView(R.id.txt_ttd_pemohon_error)
    TextView txtTtdPemohonError;
    @BindView(R.id.txt_ttd_pasangan_error)
    TextView txtTtdPasanganError;

    @BindViews({R.id.content_master_header, R.id.content_data_pribadi, R.id.content_alamat_pemohon, R.id.content_informasi_kerabat, R.id.content_data_pekerjaan, R.id.content_detail_product, R.id.content_detail_asset, R.id.content_data_asuransi, R.id.content_data_perhitungan, R.id.content_data_keterangan, R.id.content_image_pengajuan, R.id.content_persetujuan, R.id.content_data_pasangan, R.id.content_data_kartu_kredit, R.id.content_data_kartu_membership, R.id.content_recomendation, R.id.content_informasi_penawaran, R.id.content_persetujuan_tambahan})
    List<RelativeLayout> viewContents;

    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_save_draft)
    Button btnSaveDraft;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ln_button_wrapper)
    LinearLayout lnButtonWrapper;

    @BindView(R.id.header_recomendation)
    CardView rlHeaderRecomendation;
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
    @BindView(R.id.content_recomendation)
    RelativeLayout rlRecomendation;

    // Informasi Penawaran
    @BindView(R.id.rg_persetujuan_penawaran)
    RadioGroup rgPersetujuanPenawaran;
    @BindView(R.id.ic_arrow_data_penawaran)
    ImageView imgDropDownDataPenawaran;
    @BindView(R.id.ic_arrow_data_persetujuan_tambahan)
    ImageView imgDropDownDataPersetujuan;
    @BindView(R.id.rb_persetujuanPenawaranTrue)
    RadioButton rbPersetujuanPenawaranTrue;
    @BindView(R.id.rb_persetujuanPenawaranFalse)
    RadioButton rbPersetujuanPenawaranFalse;
    @BindView(R.id.rb_persetujuanTambahanTrue)
    RadioButton rbPersetujuanTambahanTrue;
    @BindView(R.id.rb_persetujuanTambahanFalse)
    RadioButton rbPErsetujuanTambahanFalse;
    @BindView(R.id.rb_persetujuanPenawaranSmsTrue)
    RadioButton rbPersetujuanPenawaranSmsTrue;
    @BindView(R.id.rb_persetujuanPenawaranSmsFalse)
    RadioButton getRbPersetujuanPenawaranSmsFalse;
    @BindView(R.id.tvGroupRekomendasi)
    TextView tvGroupRekomendasi;


    private ArrayAdapter<AoBranchObjt> cabangAdapter;
    private ArrayAdapter<MarketingSupplierObjt> marketingArrayAdapterObj;
    private ArrayAdapter<MaskingObjt> adapterMasking;
    private ArrayAdapter<ProductOfferingObjt> productArrayAdapter;
    private ArrayAdapter<JobType> jobTypeArrayAdapter;
    private ArrayAdapter<JobPosition> jobPositionArrayAdapter;
    private Bitmap bitmapTtdPasangan;
    private Bitmap bitmapTtdPemohon;
    private BlackListPresenter mBlackListPresenter;
    private GetReferalCodePresenter getReferalCodePresenter;
    private boolean isHaveSignature;
    private boolean isMaskingOn;
    private boolean isNowYearPribadi;
    private boolean isUserValid;
    private boolean isHitReferal;
    private boolean manualPremi;
    private CoordinatePresenter mCoordinatePresenter;
    private double latitude;
    private double longitude;
    private double totalInsurancePersen;
    private File fileOcr;
    private final int tokenCoordinate = 9;
    private final int tokenFormShowHide = 10;
    private final int tokenRecomendation = 11;
    private final int tokenProdOfSuppMapping = 12;
    private final int tokenTypeBlacklist = 1;
    private final int tokenTypeConfirmCode = 3;
    private final int tokenTypeMasking = 8;
    private final int tokenTypeMaster = 5;
    private final int tokenTypeOcr = 7;
    private final int tokenTypeSaldo = 4;
    private final int tokenTypeSendCode = 2;
    private final int tokenTypeSyarat = 6;
    private int countSignature;
    private int nominalAdminLain;
    private int tokenExpiredType;
    private List<AoBranchObjt> aoBranchStrings;
    private List<BranchMaster> branchMasterList;
    private List<MaskingObjt> listMasking;
    private List<ProductOfferingObjt> labelProduct;
    private List<ProductOfTenorObjt> labelprooftenor;
    private List<String> fullnamelist;
    private Map<Integer, File> mHashMapAttachmentFiles = new LinkedHashMap<>();
    private MaskingPresenter mMaskingPresenter;
    private SearchAssetMasterPresenter mSearchAssetMasterPresenter;
    private SearchSupplierMasterPresenter mSearchSupplierMasterPresenter;
    private SearchMarketingSupplierPresenter mSearchMarketingSupplierPresenter;
    private SearchProductOfferingPresenter mSearchProductOfferingPresenter;
    private PerhitunganWhiteGoodsPresenter mPerhitunganWhiteGoodsPresenter;
    private RecomendationPresenter mRecomendationPresenter;
    private SaldoKreditmuPresenter mSaldoKreditmuPresenter;
    private ProductOffTenorPresenter mProductOffTenorPresenter;
    private CekKodeProgramPresenter mCekKodeProgramPresenter;
    private PosKreditPresenter mPosPresenter;
    private KelurahanPresenter mKelurahanPresenter;
    private TujuanPembiayaanPresenter mTujuanPembiyaanPresenter;
    private SinkronisasiKendaraanPresenter mSinkronisasiKendaraanPresenter;
    private AttachmentKendaraanPresenter mAttachmentKendaraanPresenter;
    private CheckEfNumberPresenter mCheckEfNumberPresenter;
    private HargaAgunanElcPresenter mHargaAgunanElcPresenter;
    private NoHpRule noHpRule;
    private KodeAreaRule kodeAreaRule;
    private int maxCityString = 50;
    private String action;
    private String aoBranch;
    private String assetTypeId;
    private String blackListDate;
    private String branchMaster;
    private String customerIdConfins;
    private String dateSaldo;
    private String descriptionCancel;
    private String descriptionSyarat;
    private String strDPPercentage = "0";
    private String form;
    private String fsInstallment;
    private String industriKode;
    private String installmentSaldo;
    private String jobPositionKode;
    private String jobTypeKode;
    private String ktpSaldo;
    private String manualAgunan;
    private String marketingKode;
    private String mobileSubmissionKey;
    private String phoneNumberBlacklist;
    private String posKode;
    private String productId;
    private String productOfferingId;
    private String professionKode;
    private String recomendationId;
    private String strStatusPengajuan;
    private String supplierKode;
    private String token;
    private String uuid;
    private SyaratDanKetentuanPresenter mSyaratDanKetentuanPresenter;
    private Validator validator;
    private ValidatorDataPribadi validatorDataPribadi;
    private View view;
    PermissionHelper mPermissionHelper;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;
    private String branchId;
    private NotEmptyRule notEmptyRule;
    private NpwpRule npwpRule;
    private MinPriceDataAsset minPriceDataAsset;
    private NotZeroRule notZeroRule;
    private NotZeroRulePengahsilanTetap notZeroRulePengahsilanTetap;
    private String typeDataOffering;
    private String typeDataOfferingBlackList;
    private String pengajuanType;
    private Calendar calendar;
    private int nowYear;
    private int nowMonth;
    private int intMaxPembiayaan;
    private AlertDialog.Builder builder;
    private String strEfNumber;
    private String strEfNumberResponse;
    private Boolean isAssignEdit = false;
    private String formType;
    private String applicationId;
    private int pengajuanBaruId;
    private String appId;
    private String appIdNotif;
    private String applicationIdKpm;
    private int countImage;
    private int numberOfImages;
    private String strJumlahAsset;
    private boolean isImageError;
    private List<String> tmpAttachments;
    private BlackListResponse blackListResponse;
    private String bucketMessage;
    private String agreement;
    private String timeDelay;
    private String amountOfFines;
    private MasterFormPengajuan masterFormPengajuan, draftMasterFormPengajuan;
    private String draftEdit;
    private String idFamily;
    private String strCityAlamatPemohon = "";
    private String strKecamatanAlamatPemohon;
    private String strKelurahanAlamatPemohon;
    private String strZipCodeAlamatPemohon;
    private String strCityKtpAlamatPemohon = "";
    private String strKecamatanKtpAlamatPemohon;
    private String strKelurahanKtpAlamatPemohon;
    private String strZipCodeKtpAlamatPemohon;
    private String strCityAlamatKerabat = "";
    private String strKecamatanAlamatKerabat;
    private String strKelurahanAlamatKerabat;
    private String strZipCodeAlamatKerabat;
    private String strCityAlamatPekerjaan = "";
    private String strKecamatanAlamatPekerjaan;
    private String strKelurahanAlamatPekerjaan;
    private String strZipCodeAlamatPekerjaan;
    private String strCityPasangan = "";
    private String strKecamatanPasangan;
    private String strKelurahanPasangan;
    private String strZipCodePasangan;
    private String msgNotifikasi;
    private String typeEdit = "";
    private int statusNpwp;
    private ArrayList<String> jsonDescAssetCategory = new ArrayList<>();
    private ArrayList<SupplierMasterArrayList> supplierNameArrayList = new ArrayList<>();
    private ArrayList<NamaBarangArrayList> namaBarangArrayLists = new ArrayList<>();
    private ArrayList<String> assetMasterArrayList = new ArrayList<>();
    private ArrayList<String> tipeKendaraaanArrayList = new ArrayList<>();
    private String branchNameId;
    private ArrayAdapter<String> assetMasterAdapter;
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
    private boolean sensorData = false;


    // Untuk data perhitungan
    private long otr_price, down_payment, discount, admin_fee, adminFeeMinimum;
    private float interest;
    private int tenor = 0, dpMinimum;
    private String type, dp;
    // untuk save Asset Type
    private String categoryId, flatRate, flatRateMinimum, premiAsuransiManual, tujuanPembiayaan, bebasBunga, statusInformasiPenawaran, statusSmsInformasi;
    private int minimalNTF, currDP;
    String date, custType;
    HashMap<String, RequestBody> map;
    final ArrayList<String> valuesKelurahaan = new ArrayList<>();
    private ArrayAdapter<String> actProdoctTennorAdapter;
    private ArrayList<String> tenorArrayList = new ArrayList<>();
    private ArrayList<String> categoriIdList = new ArrayList<>();
    private ArrayAdapter<SupplierMasterArrayList> actSupplierAdapter;
    private ArrayAdapter<NamaBarangArrayList> actNamaBarangAdapter;
    private DecimalFormat numberFormat;
    private String usedOTP;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        App.appComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "form_pengajuan_elc_open");
        mFirebaseAnalytics.logEvent("form_pengajuan_elc_open", bundle);

        long x = (long) 12345.56;
        int y = (int) x;
        Log.i("TestingA", String.valueOf(y));
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        branchId = sharedPreferences.getString(getResources().getString(R.string.sharedpref_brachCode), "");
        notEmptyRule = new NotEmptyRule();
        npwpRule = new NpwpRule();
        notZeroRule = new NotZeroRule();
        noHpRule = new NoHpRule();
        notZeroRulePengahsilanTetap = new NotZeroRulePengahsilanTetap();

        kodeAreaRule = new KodeAreaRule();
        minPriceDataAsset = new MinPriceDataAsset();
        pengajuanType = getIntent().getStringExtra("pengajuan_type");
        typeDataOffering = getIntent().getStringExtra("type_data_offering");
        form = getIntent().getStringExtra("form");
        formType = getIntent().getStringExtra("form_type");
        pengajuanBaruId = getIntent().getIntExtra("pengajuan_baru_id", 0);
        applicationId = getIntent().getStringExtra("application_id");
        appId = getIntent().getStringExtra("app_id"); /*Edit*/
        appIdNotif = getIntent().getStringExtra("id"); /*Edit*/
        if (appIdNotif != null) {
            appId = appIdNotif;
        }
        idFamily = appId;

        longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
        latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));

        /*if (!BuildConfig.FLAVOR.equals("staging") || BuildConfig.FLAVOR.equals("staging")) {
//            edtIbuValidasi.setVisibility(View.GONE);
            edtNamaLegalValidasi.setVisibility(View.GONE);
            tilNamaLegal.setVisibility(View.GONE);
            tilNamaIbu.setVisibility(View.GONE);
//            edtIbuValidasi.setText("");
            edtNamaLegalValidasi.setText("");
        }*/
        initAllConstructor();
        firstInit();
        setDefaultCalculate();

        if (form.equals("New")) {
            pbMain.setVisibility(View.GONE);
            checkTipePengajuan();
            cekBranch();
            scrMainLayout.setVisibility(View.VISIBLE);
            rlHeaderDataValidasiAwal.setVisibility(View.VISIBLE);
            rlDataValidasiAwal.setVisibility(View.VISIBLE);
            lnWrapperDataValidasiAwal.setVisibility(View.VISIBLE);
        } else if (form.equals("Draft")) {
            hideFormValidasiAwal();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            pbMain.setVisibility(View.VISIBLE);
            mRecomendationPresenter.formRecomendation(token);
        } else if (form.equals("Edit")) {
            btnSaveDraft.setVisibility(View.GONE);
            hideFormValidasiAwal();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            pbMain.setVisibility(View.VISIBLE);
            mRecomendationPresenter.formRecomendation(token);
        }
    }

    private void hideFormValidasiAwal() {
        rlHeaderDataValidasiAwal.setVisibility(View.GONE);
        rlDataValidasiAwal.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }

    private void initAllConstructor() {
        validator = new Validator(this);
        validator.setValidationListener(this);
        validatorDataPribadi = new ValidatorDataPribadi();
        validator.put(edtPenghasilanTetapPerusahaan, notZeroRulePengahsilanTetap);
        validator.put(edtPenghasilanTetapPerusahaan, notZeroRulePengahsilanTetap);
        validator.put(edtAreaPhoneTinggal, notEmptyRule);
        validator.put(edtAreaPhoneKtp, notEmptyRule);
        validator.put(edtPribadiNoKK,notEmptyRule);

        mSearchAssetMasterPresenter = new SearchAssetMasterPresenter();
        mSearchSupplierMasterPresenter = new SearchSupplierMasterPresenter();
        mSearchMarketingSupplierPresenter = new SearchMarketingSupplierPresenter();
        mSearchProductOfferingPresenter = new SearchProductOfferingPresenter();
        mBlackListPresenter = new BlackListPresenter();
        mRefreshTokenPresenter = new RefreshTokenPresenter();
        getReferalCodePresenter = new GetReferalCodePresenter();
        mCoordinatePresenter = new CoordinatePresenter();
        mSyaratDanKetentuanPresenter = new SyaratDanKetentuanPresenter();
        mSaldoKreditmuPresenter = new SaldoKreditmuPresenter();
        mMaskingPresenter = new MaskingPresenter();
        mRecomendationPresenter = new RecomendationPresenter();
        mProductOffTenorPresenter = new ProductOffTenorPresenter();
        mCekKodeProgramPresenter = new CekKodeProgramPresenter();
        mPerhitunganWhiteGoodsPresenter = new PerhitunganWhiteGoodsPresenter();
        mPosPresenter = new PosKreditPresenter();
        mKelurahanPresenter = new KelurahanPresenter();
        mTujuanPembiyaanPresenter = new TujuanPembiayaanPresenter();
        mSinkronisasiKendaraanPresenter = new SinkronisasiKendaraanPresenter();
        mAttachmentKendaraanPresenter = new AttachmentKendaraanPresenter();
        mCheckEfNumberPresenter = new CheckEfNumberPresenter();
        mHargaAgunanElcPresenter = new HargaAgunanElcPresenter();

        mBlackListPresenter.attachView(this);
        getReferalCodePresenter.attachView(this);
        mCodeSignaturePresenter.attachView(this);
        mHargaAgunanElcPresenter.attachView(this);
        mSearchAssetMasterPresenter.attachView(this);
        mSearchSupplierMasterPresenter.attachView(this);
        mSearchMarketingSupplierPresenter.attachView(this);
        mSearchProductOfferingPresenter.attachView(this);
        mSyaratDanKetentuanPresenter.attachView(this);
        mSaldoKreditmuPresenter.attachView(this);
        mMaskingPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        mCoordinatePresenter.attachView(this);
        mRecomendationPresenter.attachView(this);
        mPengajuanDraftPresenter.attachView(this);
        mPengajuanDetailPresenter.attachView(this);
        mProductOffTenorPresenter.attachView(this);
        mCekKodeProgramPresenter.attachView(this);
        mPerhitunganWhiteGoodsPresenter.attachView(this);
        mPosPresenter.attachView(this);
        mKelurahanPresenter.attachView(this);
        mTujuanPembiyaanPresenter.attachView(this);
        mSinkronisasiKendaraanPresenter.attachView(this);
        mAttachmentKendaraanPresenter.attachView(this);
        mCheckEfNumberPresenter.attachView(this);

        handler = new Handler();
        builder = new AlertDialog.Builder(this);

        calendar = Calendar.getInstance();
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);
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
        return R.layout.activity_form_pengajuan;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBlackListPresenter.detachView();
        mCodeSignaturePresenter.detachView();
        mSearchAssetMasterPresenter.detachView();
        mSearchSupplierMasterPresenter.detachView();
        mHargaAgunanElcPresenter.detachView();
        mSearchProductOfferingPresenter.detachView();
        mSyaratDanKetentuanPresenter.detachView();
        mSaldoKreditmuPresenter.detachView();
        mMaskingPresenter.detachView();
        mCoordinatePresenter.detachView();
        mRecomendationPresenter.detachView();
        mPengajuanDraftPresenter.detachView();
        mPengajuanDetailPresenter.detachView();
        mProductOffTenorPresenter.detachView();
        mCekKodeProgramPresenter.detachView();
        mPerhitunganWhiteGoodsPresenter.detachView();
        mPosPresenter.detachView();
        mKelurahanPresenter.detachView();
        mTujuanPembiyaanPresenter.detachView();
        mSinkronisasiKendaraanPresenter.detachView();
        mAttachmentKendaraanPresenter.detachView();
        mCheckEfNumberPresenter.detachView();
        deleteDataConfins();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPreLoadMasking() {
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessLoadMasking(MaskingResponse maskingResponse) {
        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (!maskingResponse.getMaskingValues().isEmpty()) {
            /*List<MaskingObjt>*/
            listMasking = maskingResponse.getMaskingValues();
            /*ArrayAdapter<MaskingObjt>*/
            adapterMasking = new ArrayAdapter<MaskingObjt>(this, R.layout.item_dropdown, R.id.id_item, listMasking);
            spnTenorProduct.setAdapter(adapterMasking);
            isMaskingOn = true;
        } else {
            isMaskingOn = false;
        }
    }

    @Override
    public void onFailedLoadMasking(String message) {
        isMaskingOn = false;
        actProductOfferingProduct.getText().clear();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenMaskingExpired() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        String tokenStr = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(tokenStr);
        tokenExpiredType = tokenTypeMasking;
    }

    @Override
    public void onPreCoordinate() {

    }

    @Override
    public void onFailedCoordinate(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenCoordinateExpired() {
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(token);
        tokenExpiredType = tokenCoordinate;
    }

    @Override
    public void onPreRecomendation() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessRecomendation(RecomendationResponse recomendationResponse) {
        if (recomendationResponse != null) {
            final ArrayList<RekomendasiArrayList> rekomendasiArrayList = new ArrayList<>();

            for (int j = 0; j < recomendationResponse.getRecomendationObjtList().size(); j++) {
                rekomendasiArrayList.add(new RekomendasiArrayList(recomendationResponse.getRecomendationObjtList().get(j).getId(),
                        recomendationResponse.getRecomendationObjtList().get(j).getRecomendation()));
            }
            final ArrayAdapter<RekomendasiArrayList> productOffTenorAdapter = new ArrayAdapter<RekomendasiArrayList>(this, R.layout.item_dropdown, R.id.id_item, rekomendasiArrayList);
            spnRecomendation.setAdapter(productOffTenorAdapter);
            spnRecomendation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    recomendationId = productOffTenorAdapter.getItem(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        if (form.equals("Draft"))
            mPengajuanDetailPresenter.loadPengajuanDetail(token, appId);
        if (form.equals("Edit"))
            mPengajuanDetailPresenter.loadPengajuanDetail(token, appId);
        if (form.equals("New"))
            mBlackListPresenter.blackList(token,
                    edtTanggalLahirPribadi.getText().toString(),
                    edtNoKtpPribadi.getText().toString(),
                    edtHandphonePribadi.getText().toString(),
                    typeDataOfferingBlackList,
                    aoBranch, edtNamaLegalValidasi.getText().toString(), edtIbuValidasi.getText().toString(), "");
    }

    @Override
    public void onFailedRecomendation(String message) {
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (form.equals("Draft") || form.equals("Edit")) {
            llLoading.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecomendationPresenter.formRecomendation(token);
                }
            });
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTokenExpiredRecomendation() {
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(token);
        tokenExpiredType = tokenRecomendation;
    }

    @Override
    public void onCheckFpd() {

    }

    @Override
    public void onCheckFpdFailed(String message) {

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
        cabangAdapter = new ArrayAdapter<AoBranchObjt>(FormPengajuanActivity.this, R.layout.item_dropdown, R.id.id_item, aoBranchStrings);
        spnPilihanCabang.setAdapter(cabangAdapter);
        spnPilihanCabang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aoBranch = ((AoBranchObjt) spnPilihanCabang.getSelectedItem()).getBranchId();
                branchNameId = ((AoBranchObjt) spnPilihanCabang.getSelectedItem()).getBranchName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initAutoComplete() {

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

               /* if (actProfesiPerusahaan.getText().toString().equalsIgnoreCase("WIRASWASTA")) {
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
                }*/

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
    }


    private void firstInit() {
        InputFilter inputFilter50 = new InputFilter.LengthFilter(50);
        final String blockCharacterSet = "~#^|$%&*!+_-:;?()@=<>'{}[]///\\\"";
        InputFilter noSpecialCharacter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };
        InputFilter noNumber = new InputFilter() {
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

        InputFilter noNumberAndSymbol = new InputFilter() {
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

        InputFilter inputFilter16 = new InputFilter.LengthFilter(16);
        InputFilter inputFilter8 = new InputFilter.LengthFilter(8);

        edtNamaPribadi.setFilters(new InputFilter[]{new InputFilter.AllCaps(), noNumberAndSymbol});
//        edtRefundSubsidiPerhitungan.setFilters(new InputFilter[]{inputFilter});
        edtNamaKtpPribadi.setFilters(new InputFilter[]{new InputFilter.AllCaps(), noNumberAndSymbol});
        edtTempatLahirPribadi.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtNamaIbuPribadi.setFilters(new InputFilter[]{new InputFilter.AllCaps(), noNumberAndSymbol});
        edtNamaLegalValidasi.setFilters(new InputFilter[]{new InputFilter.AllCaps(), noNumberAndSymbol, inputFilter50});
        edtIbuValidasi.setFilters(new InputFilter[]{new InputFilter.AllCaps(), noNumberAndSymbol, inputFilter50});
        edtPribadiNoKK.setFilters(new InputFilter[]{inputFilter16});
        edtNamaPasangan.setFilters(new InputFilter[]{new InputFilter.AllCaps(), noNumberAndSymbol});
        edtTempatLahirPasangan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtAlamatTinggal.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtAlamatKtp.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtNamaKerabat.setFilters(new InputFilter[]{new InputFilter.AllCaps(), noNumberAndSymbol});
        edtAlamatKerabat.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtNamaPerusahaan.setFilters(new InputFilter[]{new InputFilter.AllCaps(), inputFilter50});
        edtAlamatPerusahaan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtNamaBankKartuKredit.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtJenisKartuKredit.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtKeterangan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        actSupplierAsset.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        actProductOfferingProduct.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        actAutoAlamatPemohon.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        actAutoKtpAlamatPemohon.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        actAutoAlamatKerabat.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        actAutoAlamatPekerjaan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtEmailPribadi.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtHandphonePribadi.setTextColor(GRAY);
        edtNoKtpPribadi.setTextColor(GRAY);
        edtTanggalLahirPribadi.setTextColor(GRAY);
        edtTipePembayaran.setTextColor(GRAY);
        edtBebasBungaPerhitungan.setTextColor(GRAY);
        edtPurchasePricePerhitungan.setTextColor(GRAY);
        edtDiscountPerhitungan.setTextColor(GRAY);
        edtNtfPerhitungan.setTextColor(GRAY);
        edtJumlahPembiayaan.setTextColor(GRAY);
        edtTotalBungaPembiayaan.setTextColor(GRAY);
        edtAngsuranPerbulan.setTextColor(GRAY);
        edtTotalPinjaman.setTextColor(GRAY);
        edtPembayaranPertama.setTextColor(GRAY);
        edtAngsuranPerbulanBebasBunga.setTextColor(GRAY);
        edtBungaPembiayaanBulan.setTextColor(GRAY);
        edtPremiAsuransi.setTextColor(GRAY);


        actProfesiPerusahaan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    actProfesiPerusahaan.getText().clear();
                    return true;
                }
                return false;
            }
        });
        actIndustriPerusahaan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    actIndustriPerusahaan.getText().clear();
                    return true;
                }
                return false;
            }
        });
        actJobTypePerusahaan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    actJobTypePerusahaan.getText().clear();
                    return true;
                }
                return false;
            }
        });
        actJobPositionPerusahaan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    actJobPositionPerusahaan.getText().clear();
                    return true;
                }
                return false;
            }
        });


        tvCamera1.setText("KTP");
        tvCamera2.setText("Close up Konsumen");
        tvCamera3.setText("Close Up Konsumen dengan KTP");
        tvCamera4.setText("Konsumen dan CRO");
        tvCamera5.setText("Slip Gaji");
        tvCamera6.setText("Dokumen Lainnya");
        tvCamera7.setText("Dokumen Lainnya");
        tvCamera8.setText("Dokumen Lainnya");
        tvCamera9.setText("Dokumen Lainnya");
        tvCamera10.setText("Dokumen Lainnya");
        tvCamera11.setText("Dokumen Lainnya");

        txtDatePemohonPersetujuan.setText(Util.persetujuanTimeFormat(new DateTime()));
        txtDatePasanganPersetujuan.setText(Util.persetujuanTimeFormat(new DateTime()));
        ImageViewRule imageViewRule = new ImageViewRule();
        validator.put(imgTtdPemohonPersetujuan, imageViewRule);
        validator.put(imgTakePicture1, imageViewRule);
        validator.put(imgTakePicture2, imageViewRule);
        validator.put(imgTakePicture3, imageViewRule);
        validator.put(imgTakePicture4, imageViewRule);

        edtNoKtpPibadiValidasi.addTextChangedListener(new TextWatcherCustome(edtNoKtpPibadiValidasi, implementAfterChange));
        edtNamaLegalValidasi.addTextChangedListener(new TextWatcherCustome(edtNamaLegalValidasi, implementAfterChange));
        edtIbuValidasi.addTextChangedListener(new TextWatcherCustome(edtIbuValidasi, implementAfterChange));
        edtTanggalLahirPribadiValidasi.addTextChangedListener(new TextWatcherCustome(edtTanggalLahirPribadiValidasi, implementAfterChange));
        edtHandphonePribadiValidasi.addTextChangedListener(new TextWatcherCustome(edtHandphonePribadiValidasi, implementAfterChange));
        edtFlatRatePerhitungan.addTextChangedListener(new TextWatcherCustome(edtFlatRatePerhitungan, implementAfterChange));
        actAutoAlamatPemohon.setOnFocusChangeListener(validateOnFocusChange);
        actAutoAlamatPemohon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String strDescription = s.toString();
                if (strDescription.length() >= 3) {
                    if (null != runnable) {
                        handler.removeCallbacks(runnable);
                    }
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!strDescription.contains("|") && !strDescription.equals(""))
                                mKelurahanPresenter.GetKelurahanFilter("Alamat Pemohon", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 2000);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtDpPerhitungan.setOnFocusChangeListener(validateOnFocusChange);
        edtDpPerhitungan.addTextChangedListener(new TextWatcher() {
            private static final char M_GROUP_DIVIDER = ',';
            private static final char M_DECIMAL_SEPARATOR = '.';
            int dpAlreadyHit;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                numberFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                if (s.length() != 0) {
                    edtDpPerhitungan.removeTextChangedListener(this);
                    String text = s.toString();
                    text = text.replace(M_GROUP_DIVIDER + "", "").trim();
                    text = format(text);
                    edtDpPerhitungan.setText(text);
                    edtDpPerhitungan.setSelection(text.length());
                    edtDpPerhitungan.addTextChangedListener(this);

                    if (dp != null) {
                        if (!text.isEmpty()) {
                            final int dpCurrent = Integer.parseInt(text.replace(",", ""));

                            if (dpCurrent != dpMinimum) {
                                if (Integer.parseInt(text.replace(",", "")) < dpMinimum) {
                                    tilDp.setError("Minimum DP :" + format(String.valueOf(dpMinimum)));
                                    edtDpPerhitungan.setError("Minimum DP tidak sesuai");
                                } else {
                                    tilDp.setError("");
                                    edtDpPerhitungan.setError(null);
                                    if (null != runnable) {
                                        handler.removeCallbacks(runnable);
                                    }
                                    runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            // Option List ['AssetMaster','SupplierMaster','ProductOffMaster','SupplierEmp','ProductOffTenor']
                                            if (rbAsuransiAgunanYes.isChecked()) {
                                                if (rbManualYes.isChecked()) {
                                                    premiAsuransiManual = edtPremiAsuransiManual.getText().toString().replace(",", "");
                                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, dpCurrent, discount, admin_fee, interest, type, Integer.parseInt(premiAsuransiManual), bebasBunga, Integer.parseInt(dp));
                                                    dpAlreadyHit = dpCurrent;
                                                    currDP = dpCurrent;
                                                } else {
                                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiOtomatis(token, otr_price, tenor, dpCurrent, discount, admin_fee, interest, type, bebasBunga, Integer.parseInt(dp));
                                                    dpAlreadyHit = dpCurrent;
                                                    currDP = dpCurrent;
                                                }
                                            } else {
                                                mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, dpCurrent, discount, admin_fee, interest, type, 0, bebasBunga, Integer.parseInt(dp));
                                                dpAlreadyHit = dpCurrent;
                                                currDP = dpCurrent;
                                            }
                                        }
                                    };
                                    if (dpCurrent != dpAlreadyHit)
                                        handler.postDelayed(runnable, 3000);
                                }
                            }

                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString();

            }
        });

        actReferalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
            public void afterTextChanged(Editable s) {

            }
        });


        actAutoKtpAlamatPemohon.setOnFocusChangeListener(validateOnFocusChange);
        actAutoKtpAlamatPemohon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String strDescription = s.toString();
                if (strDescription.length() >= 3) {
                    if (null != runnable) {
                        handler.removeCallbacks(runnable);
                    }
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!strDescription.contains("|") && !strDescription.equals(""))
                                mKelurahanPresenter.GetKelurahanFilter("Alamat KTP", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        actAutoAlamatKerabat.setOnFocusChangeListener(validateOnFocusChange);
        actAutoAlamatKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String strDescription = s.toString();
                if (strDescription.length() >= 3) {
                    if (null != runnable) {
                        handler.removeCallbacks(runnable);
                    }
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!strDescription.contains("|") && !strDescription.equals(""))
                                mKelurahanPresenter.GetKelurahanFilter("Alamat Kerabat", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actAutoAlamatPekerjaan.setOnFocusChangeListener(validateOnFocusChange);
        actAutoAlamatPekerjaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String strDescription = s.toString();
                if (strDescription.length() >= 3) {
                    if (null != runnable) {
                        handler.removeCallbacks(runnable);
                    }
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!strDescription.contains("|") && !strDescription.equals(""))
                                mKelurahanPresenter.GetKelurahanFilter("Alamat Pekerjaan", token, strDescription);
                        }
                    };
                    handler.postDelayed(runnable, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        actProfesiPerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        actJobTypePerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        actJobPositionPerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        actIndustriPerusahaan.setOnFocusChangeListener(validateOnFocusChange);
        /*actSupplierAsset.setOnFocusChangeListener(validateOnFocusChange);*/
        actSupplierAsset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String strDescription = s.toString();
                if (strDescription.length() >= 3) {
                    if (null != runnable) {
                        handler.removeCallbacks(runnable);
                    }
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (!actSupplierAsset.isSelectionFromPopUp())
                                // Option List ['AssetMaster','SupplierMaster','ProductOffMaster','SupplierEmp','ProductOffTenor']
                                mSearchSupplierMasterPresenter.getSearchSupplierMaster(token, strDescription, assetTypeId, "", aoBranch);
                        }
                    };
                    handler.postDelayed(runnable, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        actProductOfferingProduct.setOnFocusChangeListener(validateOnFocusChange);
        actPosProduct.setOnFocusChangeListener(validateOnFocusChange);
        actMarketingSupplierAsset.setOnFocusChangeListener(validateOnFocusChange);
        DatePickerListener datePickerListener = new DatePickerListener();
        edtTanggalLahirPribadiValidasi.setOnClickListener(datePickerListener);
        edtTerbitKtpPribadi.setOnClickListener(datePickerListener);
        edtTinggalSejakTahunPribadi.setOnClickListener(datePickerListener);
        edtTinggalSejakBulanPribadi.setOnClickListener(datePickerListener);
        edtTanggalLahirPasangan.setOnClickListener(datePickerListener);
        edtBulanKadaluarsaKartuKredit.setOnClickListener(datePickerListener);
        edtTahunKadaluarsaKartuKredit.setOnClickListener(datePickerListener);
        edtTanggalEfektif.setOnClickListener(datePickerListener);
        edtTanggalExpired.setOnClickListener(datePickerListener);
        edtBekerjaSejakPerusahaan.setOnClickListener(datePickerListener);
        validator.put(edtEmailPribadi, new EmailValidation());
        validator.put(edtEmailPribadi, notEmptyRule);
        validator.put(edtFlatRatePerhitungan, notEmptyRule);
//        validator.put(actPosProduct, notEmptyRule);
        edtHpKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (!result.startsWith("08")) {
                    edtHpKerabat.setError("Format Hp tidak sesuai, harus di awali 08");
                } else {
                    edtHpKerabat.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtAreaPhoneTinggal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (result.equals("0")) {
                    edtAreaPhoneTinggal.setError("Nomor Area Tidak Sesuai");
                } else {
                    edtAreaPhoneTinggal.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtAreaPhoneKantorKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (result.equals("0")) {
                    edtAreaPhoneKantorKerabat.setError("Nomor Area Tidak Sesuai");
                } else {
                    edtAreaPhoneKantorKerabat.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAreaPhoneKtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (result.equals("0")) {
                    edtAreaPhoneKtp.setError("Nomor Area Tidak Sesuai");
                } else {
                    edtAreaPhoneKtp.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtAreaPhoneRumahKerabat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (result.equals("0")) {
                    edtAreaPhoneRumahKerabat.setError("Nomor Area Tidak Sesuai");
                } else {
                    edtAreaPhoneRumahKerabat.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtAreaPhonePerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (result.equals("0")) {
                    edtAreaPhoneRumahKerabat.setError("Nomor Area Tidak Sesuai");
                } else {
                    edtAreaPhoneRumahKerabat.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtNoHpPasangan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (!result.startsWith("08")) {
                    edtHpKerabat.setError("Format Hp tidak sesuai, harus di awali 08");
                } else {
                    edtHpKerabat.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPenghasilanTetapPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (result.equalsIgnoreCase("0")) {
                    edtPenghasilanTetapPerusahaan.setError("Penghasilan tetap tidak boleh 0");
                } else {
                    edtPenghasilanTetapPerusahaan.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtBiayaHidupPerusahaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String result = s.toString();
                if (result.equalsIgnoreCase("0")) {
                    edtBiayaHidupPerusahaan.setError("Biaya Hidup tidak boleh 0");
                } else {
                    edtBiayaHidupPerusahaan.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.btn_muat_ulang_1, R.id.btn_muat_ulang_2, R.id.btn_muat_ulang_3, R.id.btn_muat_ulang_4, R.id.btn_muat_ulang_5, R.id.btn_muat_ulang_6, R.id.btn_muat_ulang_7, R.id.btn_muat_ulang_8, R.id.btn_muat_ulang_9, R.id.btn_muat_ulang_10, R.id.btn_muat_ulang_11})
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
        }
    }

    @OnClick(R.id.btn_camera_ktp)
    public void takeCameraKtp() {
    }

    @OnClick(R.id.btn_add_asset)
    public void onViewClicked() {
        if (actSupplierAsset.isSelectionFromPopUp()) actSupplierAsset.setError(null);
        else actSupplierAsset.setError("Wajib Di Isi");
        if (actMarketingSupplierAsset.isSelectionFromPopUp())
            actMarketingSupplierAsset.setError(null);
        else actMarketingSupplierAsset.setError("Wajib Di Isi");
        if (actProductOfferingProduct.isSelectionFromPopUp())
            actProductOfferingProduct.setError(null);
        else actProductOfferingProduct.setError("Wajib Di Isi");
        if (!actSupplierAsset.isSelectionFromPopUp() || !actMarketingSupplierAsset.isSelectionFromPopUp() || !actProductOfferingProduct.isSelectionFromPopUp() || actSupplierAsset.getText().toString().isEmpty() || actMarketingSupplierAsset.getText().toString().isEmpty() || actProductOfferingProduct.getText().toString().isEmpty()) {
            Toast.makeText(this, "Mohon Lengkapi Data Detail Product", Toast.LENGTH_SHORT).show();
            imgDropDownProduct.setImageResource(android.R.drawable.ic_dialog_alert);
        } else {
            imgDropDownProduct.setImageResource(R.drawable.ic_arrow);

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vAddAsset = inflater.inflate(R.layout.content_add_asset, null);
            llParentTotalAsset.addView(vAddAsset, llParentTotalAsset.getChildCount());

            Button btnDeleteAsset = vAddAsset.findViewById(R.id.btn_delete_asset);
            final TextView txtTitleAsset = vAddAsset.findViewById(R.id.txt_title_asset);
            final NiceAutoCompleteTextView actNamaBarangAsset = vAddAsset.findViewById(R.id.act_nama_barang_asset);
            ProgressBar pbNamaBarangElektronik = vAddAsset.findViewById(R.id.pb_nama_barang_elektronik);
            final EditText edtTypeAsset = vAddAsset.findViewById(R.id.edt_type_asset);
            final IndonesianCurrencyEditText edtPriceAsset = vAddAsset.findViewById(R.id.edt_price_asset);
            final TextView txtWarningPrice = vAddAsset.findViewById(R.id.txt_warning_price);
            final IndonesianCurrencyEditText edtDpAsset = vAddAsset.findViewById(R.id.edt_dp_asset);
            final TextView txtWarningDownpayment = vAddAsset.findViewById(R.id.txt_warning_downpayment);
            final IndonesianCurrencyEditText edtDiscountAsset = vAddAsset.findViewById(R.id.edt_discount_asset);
            final TextView txtWarningDiscount = vAddAsset.findViewById(R.id.txt_warning_discount);

            actNamaBarangAsset.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            edtTypeAsset.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(50)});

            edtDpAsset.setText("0");
            edtDiscountAsset.setText("0");
            actNamaBarangAsset.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    final String strDescription = charSequence.toString();
                    if (strDescription.length() >= 3) {
                        if (null != runnable) {
                            handler.removeCallbacks(runnable);
                        }
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!actNamaBarangAsset.isSelectionFromPopUp())
                                    // Option List ['AssetMaster','SupplierMaster','ProductOffMaster','SupplierEmp','ProductOffTenor']
                                    mSearchAssetMasterPresenter.getSearchDataMaster(token, aoBranch, productOfferingId, "AssetMaster", strDescription, assetTypeId, actNamaBarangAsset, edtPriceAsset);
                                edtTypeAsset.getText().clear();
                                edtPriceAsset.getText().clear();
                                edtDpAsset.setText("0");
                                edtDiscountAsset.setText("0");
                            }
                        };
                        handler.postDelayed(runnable, 1000);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


            edtPriceAsset.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (edtPriceAsset.getText().toString().isEmpty())
                            edtPriceAsset.setText("0");
                        if (edtDpAsset.getText().toString().isEmpty()) edtDpAsset.setText("0");
                        if (edtDiscountAsset.getText().toString().isEmpty())
                            edtDiscountAsset.setText("0");

                        int currentPrice = Integer.parseInt(edtPriceAsset.getText().toString().replace(",", ""));
                        int currentDp = Integer.parseInt(edtDpAsset.getText().toString().replace(",", ""));
                        int currentDiscount = Integer.parseInt(edtDiscountAsset.getText().toString().replace(",", ""));

                        if (currentPrice <= currentDp + currentDiscount) {
                            edtPriceAsset.setText("0");
                            edtDpAsset.setText("0");
                            edtDiscountAsset.setText("0");

                            txtWarningPrice.setText(getString(R.string.warning_total_price));
                            txtWarningDownpayment.setText(getString(R.string.warning_total_price));
                            txtWarningDiscount.setText(getString(R.string.warning_total_price));

                            txtWarningPrice.setVisibility(View.VISIBLE);
                            txtWarningDownpayment.setVisibility(View.VISIBLE);
                            txtWarningDiscount.setVisibility(View.VISIBLE);
                        } else {
                            txtWarningPrice.setVisibility(View.GONE);
                            txtWarningDownpayment.setVisibility(View.GONE);
                            txtWarningDiscount.setVisibility(View.GONE);
                        }
                        prepareDataCalculating();
                        checkNpwp();
                    }
                }
            });
            edtDpAsset.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (edtPriceAsset.getText().toString().isEmpty())
                            edtPriceAsset.setText("0");
                        if (edtDpAsset.getText().toString().isEmpty()) edtDpAsset.setText("0");
                        if (edtDiscountAsset.getText().toString().isEmpty())
                            edtDiscountAsset.setText("0");

                        int currentPrice = Integer.parseInt(edtPriceAsset.getText().toString().replace(",", ""));
                        int currentDp = Integer.parseInt(edtDpAsset.getText().toString().replace(",", ""));
                        int currentDiscount = Integer.parseInt(edtDiscountAsset.getText().toString().replace(",", ""));

                        if (currentPrice <= currentDp + currentDiscount) {
                            edtPriceAsset.setText("0");
                            edtDpAsset.setText("0");
                            edtDiscountAsset.setText("0");

                            txtWarningPrice.setText(getString(R.string.warning_total_price));
                            txtWarningDownpayment.setText(getString(R.string.warning_total_price));
                            txtWarningDiscount.setText(getString(R.string.warning_total_price));

                            txtWarningPrice.setVisibility(View.VISIBLE);
                            txtWarningDownpayment.setVisibility(View.VISIBLE);
                            txtWarningDiscount.setVisibility(View.VISIBLE);
                        } else {
                            txtWarningPrice.setVisibility(View.GONE);
                            txtWarningDownpayment.setVisibility(View.GONE);
                            txtWarningDiscount.setVisibility(View.GONE);
                        }
                        prepareDataCalculating();
                    }
                }
            });
            edtDpAsset.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (tenor != 0 && !spnTenorProduct.getSelectedItem().toString().equalsIgnoreCase("Pilih")) {
                        if (null != runnable) {
                            handler.removeCallbacks(runnable);
                        }
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                prepareDataCalculating();
                                edtDpPerhitungan.setText(down_payment + "");
                            }
                        };
                        handler.postDelayed(runnable, 500);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtDiscountAsset.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (edtPriceAsset.getText().toString().isEmpty())
                            edtPriceAsset.setText("0");
                        if (edtDpAsset.getText().toString().isEmpty()) edtDpAsset.setText("0");
                        if (edtDiscountAsset.getText().toString().isEmpty())
                            edtDiscountAsset.setText("0");

                        int currentPrice = Integer.parseInt(edtPriceAsset.getText().toString().replace(",", ""));
                        int currentDp = Integer.parseInt(edtDpAsset.getText().toString().replace(",", ""));
                        int currentDiscount = Integer.parseInt(edtDiscountAsset.getText().toString().replace(",", ""));

                        if (currentPrice <= currentDp + currentDiscount) {
                            edtPriceAsset.setText("0");
                            edtDpAsset.setText("0");
                            edtDiscountAsset.setText("0");

                            txtWarningPrice.setText(getString(R.string.warning_total_price));
                            txtWarningDownpayment.setText(getString(R.string.warning_total_price));
                            txtWarningDiscount.setText(getString(R.string.warning_total_price));

                            txtWarningPrice.setVisibility(View.VISIBLE);
                            txtWarningDownpayment.setVisibility(View.VISIBLE);
                            txtWarningDiscount.setVisibility(View.VISIBLE);
                        } else {
                            txtWarningPrice.setVisibility(View.GONE);
                            txtWarningDownpayment.setVisibility(View.GONE);
                            txtWarningDiscount.setVisibility(View.GONE);
                        }
                        prepareDataCalculating();
                    }
                }
            });
            btnDeleteAsset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validator.removeRules(actNamaBarangAsset);
                    validator.removeRules(edtTypeAsset);
                    validator.removeRules(edtPriceAsset);
                    validator.removeRules(edtDpAsset);
                    validator.removeRules(edtDiscountAsset);

                    llParentTotalAsset.removeView((View) v.getParent());
                    tvJumlahAsset.setText("Asset : " + String.valueOf(llParentTotalAsset.getChildCount()));
                    prepareDataCalculating();
                }
            });

            viewsAssetNamaBarang.add(actNamaBarangAsset);
            viewsAssetTipeBarang.add(edtTypeAsset);
            viewsAssetHarga.add(edtPriceAsset);
            viewsAssetDp.add(edtDpAsset);
            viewsAssetDiscount.add(edtDiscountAsset);

            validator.put(actNamaBarangAsset, notEmptyRule);
            validator.put(edtTypeAsset, notEmptyRule);
            validator.put(edtPriceAsset, minPriceDataAsset);
            validator.put(edtDpAsset, notEmptyRule);
            validator.put(edtDiscountAsset, notEmptyRule);

            txtTitleAsset.setText("Asset");
            tvJumlahAsset.setText("Asset : " + String.valueOf(llParentTotalAsset.getChildCount()));
        }
    }

    @OnClick({R.id.btn_next, R.id.btn_save_draft, R.id.btn_submit})
    public void onClickSaveData(View view) {
        Util.hideKeyboard(this, view);
        if (Util.checkActiveLocation(this)) {
            switch (view.getId()) {
                case R.id.btn_next:
                    validatorDataPribadi.validatePengajuan();
                    break;
                case R.id.btn_save_draft:
                    setSectionHeadersToDefault();
//                    if (checkCompleteDropDownKop()) {
                    if (checkMandatorySaveDraft()) {
                        if (form.equalsIgnoreCase("New")) {
                            setValueSaveAsDraft("Draft");
                            mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, "3", "");
                        }
                        /*saveData("draft_baru");*/
                        if (form.equalsIgnoreCase("Draft")) {
                            setValueSaveAsDraft("Draft");
                            mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, "3", "");
                            //updateData("draft_baru");
                        }
                    }
//                    } else {
//                        Toast.makeText(this, "Bagian Kop harap di lengkapi", Toast.LENGTH_SHORT).show();
//                    }

                    break;
                case R.id.btn_submit:
//                    checkNpwp();
                    validator.put(edtAreaPhoneTinggal, notEmptyRule);
                    validator.put(edtAreaPhoneKtp, notEmptyRule);
                    validator.put(edtPhoneKtp, notEmptyRule);
                    validator.put(edtPhoneTinggal, notEmptyRule);
                    setSectionHeadersToDefault();
                    txtAttachmentError.setVisibility(View.GONE);
                    txtTtdPemohonError.setVisibility(View.GONE);
                    txtTtdPasanganError.setVisibility(View.GONE);
                    if (validasiKota()) {
                        if (cbxAlamatKtp.isChecked()) {
                            edtAlamatKtp.setText(edtAlamatTinggal.getText().toString());
                            edtRtKtp.setText(edtRtTinggal.getText().toString());
                            edtRwKtp.setText(edtRwTinggal.getText().toString());
                            if (strCityKtpAlamatPemohon != null && strKecamatanKtpAlamatPemohon != null && strKelurahanKtpAlamatPemohon != null && strZipCodeKtpAlamatPemohon != null) {
                                strCityKtpAlamatPemohon = strCityAlamatPemohon;
                                strKecamatanKtpAlamatPemohon = strKecamatanAlamatPemohon;
                                strKelurahanKtpAlamatPemohon = strKelurahanAlamatPemohon;
                                strZipCodeKtpAlamatPemohon = strZipCodeAlamatPemohon;
                                actAutoKtpAlamatPemohon.setText(strCityKtpAlamatPemohon + "|" + strKecamatanKtpAlamatPemohon + "|" + strKelurahanKtpAlamatPemohon + "|" + strZipCodeKtpAlamatPemohon);
                                actAutoKtpAlamatPemohon.setSelectionFromPopUp(true);
                            }
                            edtAreaPhoneKtp.setText(edtAreaPhoneTinggal.getText().toString());
                            edtPhoneKtp.setText(edtPhoneTinggal.getText().toString());

                        }
                    }
                    if (checkCompleteDropDownKop()) {
                        if (checkDataRekomendasi()) {
                            String strJumlahAsset = String.valueOf(llParentTotalAsset.getChildCount());
                            if (!strJumlahAsset.equals("0")) {
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View vAddAsset = inflater.inflate(R.layout.content_add_asset, null);
                                NiceAutoCompleteTextView actNamaBarangAsset = vAddAsset.findViewById(R.id.act_nama_barang_asset);
                                actNamaBarangAsset.setAdapter(assetMasterAdapter);
                            }
                        }
                    }
                    validator.validate();
                    break;
            }
        }
    }

    private void checkStatusPernikahan() {
        if (!"Menikah".equalsIgnoreCase(spnPernikahanPribadi.getSelectedItem().toString())) {
            lnWrapperDataPasangan.setVisibility(View.GONE);
            edtNamaPasangan.setText("");
            edtNoKtpPasangan.setText("");
            edtTempatLahirPasangan.setText("");
            edtTanggalLahirPasangan.setText("");
            edtPenghasilanPasanganPerusahaan.setVisibility(View.GONE);
            inTtdPemohonLain.setVisibility(View.INVISIBLE);
        } else {
            lnWrapperDataPasangan.setVisibility(View.VISIBLE);
            inTtdPemohonLain.setVisibility(View.VISIBLE);
            edtPenghasilanPasanganPerusahaan.setVisibility(View.VISIBLE);
        }
    }

    private void setValueToField(String typeCustomer) {
        if (typeCustomer.equals("Toni Wibawa, S.Kon : RO || S. Kre : Kreditmu")) {
            edtNoKtpPibadiValidasi.setText("3172025507860001");
            edtTanggalLahirPribadiValidasi.setText("1986-07-15");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("Test Kreditplus, S.Kon : RO || S. Kre : Kreditmu")) {
            edtNoKtpPibadiValidasi.setText("9000000101900005");
            edtTanggalLahirPribadiValidasi.setText("1990-01-01");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("MURNIATI, S.Kon : RO || S. Kre : Non Kreditmu")) {
            edtNoKtpPibadiValidasi.setText("3175070911880003");
            edtTanggalLahirPribadiValidasi.setText("1988-11-09");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("TEST JERRY, S.Kon : New || S. Kre : Non Kreditmu (Kreditmu Tidak Aktif)")) {
            edtNoKtpPibadiValidasi.setText("9000000101900050");
            edtTanggalLahirPribadiValidasi.setText("1990-01-01");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("RAMDONA, BLACKLIST")) {
            edtNoKtpPibadiValidasi.setText("3216060407830012");
            edtTanggalLahirPribadiValidasi.setText("1983-07-04");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("TEST KREDITMU, S.Kon : New || S. Kre : Kreditmu")) {
            edtNoKtpPibadiValidasi.setText("9000000101900006");
            edtTanggalLahirPribadiValidasi.setText("1990-01-01");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("TEST STEVE")) {
            edtNoKtpPibadiValidasi.setText("9000000101900006");
            edtTanggalLahirPribadiValidasi.setText("1991-01-01");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("Data Production Kreditmu")) {
            edtNoKtpPibadiValidasi.setText("3374061804800003");
            edtTanggalLahirPribadiValidasi.setText("1980-04-18");
            edtHandphonePribadiValidasi.setText("0821262372721");
        }
        if (typeCustomer.equals("Reguler")) {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHss");
            date = df.format(Calendar.getInstance().getTime());
            edtNoKtpPibadiValidasi.setText("11" + date);
            edtTanggalLahirPribadiValidasi.setText("2001-09-17");
            edtHandphonePribadiValidasi.setText("0821262372721");
////        KOP
            spnJenisTujuanPembiyaan.setSelection(1);
            spnMetodePenjualan.setSelection(1);

////        DATA PRIBADI
            edtNamaPribadi.setText("Kain HighWind");
            edtTerbitKtpPribadi.setText("2011-01-01");
            edtTempatLahirPribadi.setText("edtPribadiTempatLahir");
            edtNamaKtpPribadi.setText("Kain Highwind");
            spnAgamaPribadi.setSelection(5);
            edtJmlTanggunganPribadiHide.setText("3");
            spnStatusRumahPribadi.setSelection(1);
            edtTinggalSejakTahunPribadi.setText("2011");
            edtTinggalSejakBulanPribadi.setText("1");
            spnPendidikanPribadi.setSelection(1);
            edtNoNpwpPribadi.setText("123456789012345");
            edtEmailPribadi.setText("edtEmailPribadi@gmail.com");
            edtNamaIbuPribadi.setText("edtNamaIbu");

            //        ALAMAT PEMOHON DAN KTP
            edtAlamatTinggal.setText("edtAlamatTinggal");
            edtRtTinggal.setText("9");
            edtRwTinggal.setText("10");
            edtAreaPhoneTinggal.setText("11");
            edtPhoneTinggal.setText("12");
            edtAlamatKtp.setText("edtAlamatKtp");
            edtRtKtp.setText("14");
            edtRwKtp.setText("15");
            edtAreaPhoneKtp.setText("16");
            edtPhoneKtp.setText("17");


//        INFORMASI KERABAT
            edtNamaKerabat.setText("edtNamaKerabat");
            spnHubunganKerabat.setSelection(1);
            edtAlamatKerabat.setText("edtAlamatKerabat");
            edtRtKerabat.setText("20");
            edtRwKerabat.setText("21");
            edtAreaPhoneRumahKerabat.setText("22");
            edtPhoneRumahKerabat.setText("23");
            edtAreaPhoneKantorKerabat.setText("24");
            edtPhoneKantorKerabat.setText("25");
            edtHpKerabat.setText("089612123434");

//        DATA PEKERJAAN
            edtNamaPerusahaan.setText("edtNamaPerusahaan");
            edtAlamatPerusahaan.setText("edtAlamatPerusahaan");
            edtRtPerusahaan.setText("28");
            edtRwPerusahaan.setText("29");
            edtAreaPhonePerusahaan.setText("30");
            edtPhonePerusahaan.setText("31");
            edtBekerjaSejakPerusahaan.setText("1996");
            edtPenghasilanTetapPerusahaan.setText("10000000");
            edtPenghasilanLainPerusahaan.setText("1000000");
            edtPenghasilanPasanganPerusahaan.setText("5000000");
            edtBiayaHidupPerusahaan.setText("100000");
//            AGUNAN
//            KETERANGAN
            edtKeterangan.setText("edtKeterangan");
//            REKOMENDASI
            edtDescRecomendation.setText("edtDescRecomendation");
        }
    }

    @Override
    public void onPreSearchAssetMaster() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessSearchAssetMaster(final AssetMasterResponse data, final NiceAutoCompleteTextView actNamaBarangAsset, final IndonesianCurrencyEditText edtPriceAsset) {
        if (!namaBarangArrayLists.isEmpty() && namaBarangArrayLists != null) {
            namaBarangArrayLists.clear();
            actNamaBarangAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, namaBarangArrayLists);
            actNamaBarangAsset.setAdapter(actNamaBarangAdapter);
        }
        if (data != null) {
            for (int j = 0; j < data.getAssetMasters().size(); j++) {
                namaBarangArrayLists.add(new NamaBarangArrayList(data.getAssetMasters().get(j).getAssetCode(),
                        data.getAssetMasters().get(j).getDescription(),
                        data.getAssetMasters().get(j).getCategoryID()));
            }
            actNamaBarangAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, namaBarangArrayLists);
            actNamaBarangAsset.setAdapter(actNamaBarangAdapter);
            actNamaBarangAsset.showDropDown();
            actNamaBarangAsset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    actNamaBarangAsset.setTag(actNamaBarangAdapter.getItem(i).getAssetCode());
                    categoryId = actNamaBarangAdapter.getItem(i).getCategoryID();
                    categoriIdList.add(categoryId);
                    mHargaAgunanElcPresenter.GetHargaAgunan(token, aoBranch, actNamaBarangAdapter.getItem(i).getAssetCode(), "", edtPriceAsset);

                }
            });
            actNamaBarangAsset.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        actNamaBarangAsset.getText().clear();
                        return true;
                    }
                    return false;
                }
            });

        }
        pbMain.setVisibility(View.GONE);
    }

    @Override
    public void onFailedSearchAssetMaster(String message) {
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (actNamaBarangAdapter != null)
            actNamaBarangAdapter.clear();
    }

    // Todo: Implementasi Supplier Search
    @Override
    public void onPreSearchSupplierMaster() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        supplierNameArrayList.clear();
    }

    @Override
    public void onSuccessSearchSupplierMaster(SupplierResponse supplierResponse) {
        if (supplierResponse != null) {
            for (int j = 0; j < supplierResponse.getSuppliers().size(); j++) {
                supplierNameArrayList.add(new SupplierMasterArrayList(supplierResponse.getSuppliers().get(j).getSupplierId(),
                        supplierResponse.getSuppliers().get(j).getName()));
            }
            actSupplierAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, supplierNameArrayList);
            actSupplierAsset.setAdapter(actSupplierAdapter);
            actSupplierAsset.showDropDown();
            actSupplierAsset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    actProductOfferingProduct.getText().clear();
                    actPosProduct.getText().clear();
                    actMarketingSupplierAsset.getText().clear();
                    llParentTotalAsset.removeAllViews();
                    productId = actSupplierAdapter.getItem(i).getSupplierID();
                    supplierKode = actSupplierAdapter.getItem(i).getSupplierID();
                    mSearchMarketingSupplierPresenter.getSearchMarketingSupplier(token, supplierKode, aoBranch, assetTypeId);
                }
            });
        }

        actSupplierAsset.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    actSupplierAsset.getText().clear();
                    return true;
                }
                return false;
            }
        });
        actProductOfferingProduct.getText().clear();
        actPosProduct.getText().clear();
        actMarketingSupplierAsset.getText().clear();
        llParentTotalAsset.removeAllViews();
        tvJumlahAsset.setText("Asset : " + "0");
        pbMain.setVisibility(View.GONE);
    }

    @Override
    public void onFailedSearchSupplierMaster(String message) {
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (actSupplierAdapter != null) {
            actSupplierAdapter.clear();
        }

    }

    // TODO: Implementasi Marketing Supplier
    @Override
    public void onPreSearchMarketingSupplier() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);

    }

    ArrayAdapter<String> actSupplierMasterAdapter;

    @Override
    public void onSuccessSearchMarketingSupplier(MarketingSupplierResponse data) {
        final ArrayList<String> marketingSupplierIdArrayList = new ArrayList<>();
        final ArrayList<String> marketingSupplierNameArrayList = new ArrayList<>();

        for (int j = 0; j < data.getMarketingSupplierList().size(); j++) {
            marketingSupplierIdArrayList.add(data.getMarketingSupplierList().get(j).getSupplierEmployeeID());
            marketingSupplierNameArrayList.add(data.getMarketingSupplierList().get(j).getSupplierEmployeeName());
        }
        actSupplierMasterAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, marketingSupplierNameArrayList);
        actMarketingSupplierAsset.setAdapter(actSupplierMasterAdapter);
        actMarketingSupplierAsset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                marketingKode = marketingSupplierIdArrayList.get(i);
            }
        });
        pbMain.setVisibility(View.GONE);
        mSearchProductOfferingPresenter.getSearchProductOffering(token, "", assetTypeId, supplierKode, aoBranch, custType, "", "");
    }

    @Override
    public void onFailedMarketingSupplier(String message) {
        pbMain.setVisibility(View.GONE);
        if (actSupplierMasterAdapter != null)
            actSupplierMasterAdapter.clear();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    // Todo: Product Offering implement
    @Override
    public void onPreSearchProductOffering() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessSearchProductOffering(ProductOfferingResponse data) {
        final ArrayList<String> productIdArrayList = new ArrayList<>();
        final ArrayList<String> productOfferingIdDescArrayList = new ArrayList<>();

        for (int j = 0; j < data.getProductOfferings().size(); j++) {
            String id = data.getProductOfferings().get(j).getProductOfferingID();
            String namaProductOff = id + " - " + data.getProductOfferings().get(j).getProductOfferingIDDescription();
            productIdArrayList.add(id);
            productOfferingIdDescArrayList.add(namaProductOff);
        }

        ArrayAdapter<String> actSupplierMasterAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, productOfferingIdDescArrayList);
        actProductOfferingProduct.setAdapter(actSupplierMasterAdapter);
        actProductOfferingProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hasil[] = actProductOfferingProduct.getText().toString().split(" ");
                productOfferingId = hasil[0];
                mProductOffTenorPresenter.getProductOffTenor(token, productOfferingId, aoBranch);
                mCekKodeProgramPresenter.checkKodeProgram(token, productOfferingId);
                mPosPresenter.getPosMaster(token, aoBranch);
                actPosProduct.getText().clear();
                llParentTotalAsset.removeAllViews();
            }
        });
        actProductOfferingProduct.getText().clear();
        pbMain.setVisibility(View.GONE);
    }

    @Override
    public void onFailedSearchProductOffering(String message) {
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // TODO:Product Tennor implement
    @Override
    public void onPreProductOffTenor() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
    }

    @Override

    public void onSuccessProductOffTenor(final ProductOffTenorResponse data) {
        clearDataPerhitungan();
        tenorArrayList.clear();
        setTenorAdapter();
        tenorArrayList.add("Pilih");
        for (int j = 0; j < data.getProductOfTenorObjts().size(); j++) {
            tenorArrayList.add(data.getProductOfTenorObjts().get(j).getTenor());
        }
        setTenorAdapter();
        //actProductOffTenor.showDropDown();
        spnTenorProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                if (position != 0) {
                    int cur = position - 1;
                    tenor = Integer.parseInt(data.getProductOfTenorObjts().get(cur).getTenor());
                    bebasBunga = data.getProductOfTenorObjts().get(cur).getDiscountRateTimes();
                    strDPPercentage = data.getProductOfTenorObjts().get(cur).getdPPercentage();
                    dp = data.getProductOfTenorObjts().get(cur).getdPPercentage();
                    adminFeeMinimum = Long.parseLong(data.getProductOfTenorObjts().get(cur).getAdminFee());
                    admin_fee = adminFeeMinimum;
                    flatRateMinimum = data.getProductOfTenorObjts().get(cur).getFlatRate();
                    flatRate = flatRateMinimum;
                    minimalNTF = Integer.parseInt(data.getProductOfTenorObjts().get(cur).getNtf());
                    String tipePembayaran = getTipePembayaran(data.getProductOfTenorObjts().get(cur).getFirstInstallment());
                    edtTipePembayaran.setText(tipePembayaran);
                    fsInstallment = tipePembayaran;
                    edtNtfPerhitungan.setText(data.getProductOfTenorObjts().get(cur).getNtf());
                    interest = Float.parseFloat(data.getProductOfTenorObjts().get(cur).getFlatRate());
                    type = tipePembayaran;
                    edtBebasBungaPerhitungan.setText(bebasBunga);
                    edtFlatRatePerhitungan.setText(flatRate);
                    edtBiayaAdminPerhitungan.setText(data.getProductOfTenorObjts().get(cur).getAdminFee());
//                    disini
                    edtBiayaAdminPerhitungan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!s.toString().isEmpty()) {
                                final Long strDescription = Long.parseLong(s.toString().replace(",", ""));
                                if (strDescription < adminFeeMinimum) {
                                    edtBiayaAdminPerhitungan.setError("Biaya Administrasi minimal Rp. " + adminFeeMinimum);
                                } else {
                                    admin_fee = strDescription;
                                    if (null != runnable) {
                                        handler.removeCallbacks(runnable);
                                    }
                                    runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            if (rbAsuransiAgunanYes.isChecked()) {
                                                if (rbManualYes.isChecked()) {
                                                    premiAsuransiManual = edtPremiAsuransiManual.getText().toString().replace(",", "");
                                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, Integer.parseInt(premiAsuransiManual), bebasBunga, Integer.parseInt(dp));
                                                } else {
                                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiOtomatis(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, bebasBunga, Integer.parseInt(dp));
                                                }
                                            } else {
                                                mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, 0, bebasBunga, Integer.parseInt(dp));
                                            }
                                        }
                                    };
                                    handler.postDelayed(runnable, 1500);

                                }
                            } else {
                                edtBiayaAdminPerhitungan.setError("Biaya Administrasi minimal Rp. " + adminFeeMinimum);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    edtPurchasePricePerhitungan.setText(otr_price + "");
                    edtDiscountPerhitungan.setText(discount + "");
                    edtDpPerhitungan.setText(down_payment + "");
                    if (rbAsuransiAgunanYes.isChecked()) {
                        if (rbManualYes.isChecked()) {
                            premiAsuransiManual = edtPremiAsuransiManual.getText().toString().replace(",", "");
                            mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, Integer.parseInt(premiAsuransiManual), bebasBunga, Integer.parseInt(dp));
                        } else {
                            mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiOtomatis(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, bebasBunga, Integer.parseInt(dp));
                        }
                    } else {
                        mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, 0, bebasBunga, Integer.parseInt(dp));
                    }
                    edtFlatRatePerhitungan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            final float minimum = Float.parseFloat(flatRateMinimum);
                            if (!s.toString().isEmpty()) {
                                final float rate = Float.parseFloat(s.toString());
                                if (rate < minimum) {
                                    edtFlatRatePerhitungan.setError("Nilai rate minimal adalah " + minimum);
                                } else {
                                    interest = Float.parseFloat(s.toString());
                                    if (null != runnable) {
                                        handler.removeCallbacks(runnable);
                                    }
                                    runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            if (rbAsuransiAgunanYes.isChecked()) {
                                                if (rbManualYes.isChecked()) {
                                                    premiAsuransiManual = edtPremiAsuransiManual.getText().toString().replace(",", "");
                                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, Integer.parseInt(premiAsuransiManual), bebasBunga, Integer.parseInt(dp));
                                                } else {
                                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiOtomatis(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, bebasBunga, Integer.parseInt(dp));
                                                }
                                            } else {
                                                mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, 0, bebasBunga, Integer.parseInt(dp));
                                            }
                                        }
                                    };
                                    handler.postDelayed(runnable, 1500);

                                }
                            } else {
                                edtFlatRatePerhitungan.setError("Nilai rate minimal adalah " + flatRateMinimum);
                            }

                        }


                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pbMain.setVisibility(View.GONE);
    }

    private void setTenorAdapter() {
        actProdoctTennorAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, tenorArrayList);
        spnTenorProduct.setAdapter(actProdoctTennorAdapter);
    }

    private void clearDataPerhitungan() {
        edtTipePembayaran.getText().clear();
        edtNtfPerhitungan.getText().clear();
        edtBebasBungaPerhitungan.getText().clear();
        edtFlatRatePerhitungan.getText().clear();
        edtBiayaAdminPerhitungan.getText().clear();
        edtPurchasePricePerhitungan.getText().clear();
        edtDiscountPerhitungan.getText().clear();
        edtDpPerhitungan.getText().clear();
        tvJumlahAsset.setText("Asset : " + String.valueOf(llParentTotalAsset.getChildCount()));
        edtPurchasePricePerhitungan.getText().clear();
        edtJumlahPembiayaan.getText().clear();
        edtTotalBungaPembiayaan.getText().clear();
        edtAngsuranPerbulan.getText().clear();
        edtTotalPinjaman.getText().clear();
        edtPembayaranPertama.getText().clear();
        edtBebasBungaPerhitungan.getText().clear();
        edtAngsuranPerbulanBebasBunga.getText().clear();
        edtBungaPembiayaanBulan.getText().clear();
        edtPremiAsuransi.getText().clear();
    }

    @Override
    public void onFailedProductOffTenor(String message) {
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPreGetPos() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessGetPos(PosListDownResponse data) {
        final ArrayList<String> posArrayList = new ArrayList<>();
        final ArrayList<String> posIdArrayList = new ArrayList<>();

        for (int j = 0; j < data.getPosMasters().size(); j++) {
            posArrayList.add(data.getPosMasters().get(j).getPOSName());
            posIdArrayList.add(data.getPosMasters().get(j).getPOSID());
        }

        ArrayAdapter<String> actProdoctTennorAdapter = new ArrayAdapter<String>(this, R.layout.item_dropdown, R.id.id_item, posArrayList);
        actPosProduct.setAdapter(actProdoctTennorAdapter);
        actPosProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posKode = posIdArrayList.get(i);
            }
        });
        pbMain.setVisibility(View.GONE);

    }

    @Override
    public void onFailedGetPos(String message) {
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // TODO: Perhitungan Calculasi
    @Override
    public void onPrePerhitunganWhiteGoods() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccessGetPerhitunganWhiteGoods(DetailPerhitunganWhiteGoodsResponse data) {
        checkNpwp();
        dpMinimum = data.getDp_minimum();
        if (rbAsuransiAgunanYes.isChecked()) {
            if (rbManualYes.isChecked()) {
                edtPremiAsuransiManual.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO: Validasi Minimal Premi Asuransi
                        final String strDescription = s.toString();
                        if (strDescription.length() >= 3) {
                            if (null != runnable) {
                                handler.removeCallbacks(runnable);
                            }
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    // Option List ['AssetMaster','SupplierMaster','ProductOffMaster','SupplierEmp','ProductOffTenor']
                                    if (rbAsuransiAgunanYes.isChecked()) {
                                        if (rbManualYes.isChecked()) {
                                            premiAsuransiManual = edtPremiAsuransiManual.getText().toString().replace(",", "");
                                            mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, Integer.parseInt(premiAsuransiManual), bebasBunga, Integer.parseInt(dp));
                                        } else {
                                            mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiOtomatis(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, bebasBunga, Integer.parseInt(dp));
                                        }
                                    } else {
                                        mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, currDP, discount, admin_fee, interest, type, 0, bebasBunga, Integer.parseInt(dp));
                                    }
                                }
                            };
                            handler.postDelayed(runnable, 1500);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                premiAsuransiManual = edtPremiAsuransiManual.getText().toString().replace(",", "");
                ;
                edtPremiAsuransi.setText(premiAsuransiManual);
            } else {
                edtPremiAsuransi.setText(data.getAsuransi() + "");
            }
        } else {
            edtPremiAsuransi.setText(0 + "");
        }
        edtPurchasePricePerhitungan.setText(otr_price + "");
        edtJumlahPembiayaan.setText(data.getJumlah_pembiayaan() + "");
        edtTotalBungaPembiayaan.setText(data.getBunga_pembiayaan() + "");
        edtAngsuranPerbulan.setText(data.getAngsuran() + "");
        edtTotalPinjaman.setText(data.getTotal_pinjaman() + "");
        edtPembayaranPertama.setText(data.getPembayaran_pertama() + "");
        edtBebasBungaPerhitungan.setText(data.getJumlah_bebas_bunga() + "");
        edtAngsuranPerbulanBebasBunga.setText(data.getAngsuran_bebas_bunga() + "");
        edtBungaPembiayaanBulan.setText(data.getBunga_pembiayaan_perbulan() + "");
        edtDpPerhitungan.setText(data.getDp() + "");


        pbMain.setVisibility(View.GONE);
        checkMinimumPrice();
    }

    @Override
    public void onFailedGetPerhitunganWhiteGoods(String message) {
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String format(String text) {
        return numberFormat.format(Integer.parseInt(text));
    }

    @Override
    public void onPreKelurahan() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        valuesKelurahaan.clear();
    }

    ArrayAdapter<String> kelurahanAdapter;

    @Override
    public void onSuccessKelurahan(String status, KelurahanResponse data) {

        for (int i = 0; i < data.getKelurahanFilter().size(); i++) {
            valuesKelurahaan.add(data.getKelurahanFilter().get(i).getValue());
        }
        kelurahanAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, valuesKelurahaan);
        if (status.equals("Alamat Pemohon")) {
//            actAutoAlamatPemohon.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            actAutoAlamatPemohon.setAdapter(kelurahanAdapter);
            actAutoAlamatPemohon.showDropDown();
            actAutoAlamatPemohon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (valuesKelurahaan.size() - 1 >= position) {
                        strCityAlamatPemohon = valuesKelurahaan.get(position);
                        maxCityString = strCityAlamatPemohon.length();
//                        Toast.makeText(FormPengajuanActivity.this, strCityAlamatPemohon.length(), Toast.LENGTH_SHORT).show();
                        actAutoAlamatPemohon.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(strCityAlamatPemohon.length())});
                    }
                }
            });
            actAutoAlamatPemohon.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        actAutoAlamatPemohon.getText().clear();
                        return true;
                    }
                    return false;
                }
            });
        }
        if (status.equals("Alamat KTP")) {
//            actAutoKtpAlamatPemohon.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            actAutoKtpAlamatPemohon.setAdapter(kelurahanAdapter);
            actAutoKtpAlamatPemohon.showDropDown();
            actAutoKtpAlamatPemohon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    strCityKtpAlamatPemohon = valuesKelurahaan.get(position);
                    maxCityString = strCityKtpAlamatPemohon.length();
                    actAutoKtpAlamatPemohon.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(strCityKtpAlamatPemohon.length())});
                }
            });
            actAutoKtpAlamatPemohon.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        actAutoKtpAlamatPemohon.getText().clear();
                        return true;
                    }
                    return false;
                }
            });
        }
        if (status.equals("Alamat Kerabat")) {
            actAutoAlamatKerabat.setAdapter(kelurahanAdapter);
            actAutoAlamatKerabat.showDropDown();
            actAutoAlamatKerabat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (valuesKelurahaan.size() - 1 >= position) {
                        strCityAlamatKerabat = valuesKelurahaan.get(position);
                        maxCityString = strCityAlamatKerabat.length();
                        actAutoAlamatKerabat.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(strCityAlamatKerabat.length())});
                    }
                }
            });
            actAutoAlamatKerabat.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        actAutoAlamatKerabat.getText().clear();
                        return true;
                    }
                    return false;
                }
            });
        }
        if (status.equals("Alamat Pekerjaan")) {
            actAutoAlamatPekerjaan.setAdapter(kelurahanAdapter);
            actAutoAlamatPekerjaan.showDropDown();
            actAutoAlamatPekerjaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (valuesKelurahaan.size() - 1 >= position) {
                        strCityAlamatPekerjaan = valuesKelurahaan.get(position);
                        maxCityString = strCityAlamatPekerjaan.length();
                        actAutoAlamatPekerjaan.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(strCityAlamatPekerjaan.length())});
                    }
                }
            });
            actAutoAlamatPekerjaan.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        actAutoAlamatPekerjaan.getText().clear();
                        return true;
                    }
                    return false;
                }
            });
        }
        pbMain.setVisibility(View.GONE);
    }

    @Override
    public void onFailedKelurahan(String message) {
        pbMain.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (kelurahanAdapter != null)
            kelurahanAdapter.clear();
    }

    @Override
    public void onPreTujuanPembiayaan() {

    }

    @Override
    public void onSuccessTujuanPembiayaan(FinancingObjectResponse response) {
        final ArrayList<String> tujuanPembiaans = new ArrayList<>();
        for (int i = 0; i < response.getProductOfTenorObjts().size(); i++) {
            tujuanPembiaans.add(response.getProductOfTenorObjts().get(i).getDescription());
        }
        ArrayAdapter<String> tujuanPebiayaanAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, R.id.id_item, tujuanPembiaans);
        spnJenisTujuanPembiyaan.setAdapter(tujuanPebiayaanAdapter);
        spnJenisTujuanPembiyaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilihan = tujuanPembiaans.get(position);
                if (pilihan.equals("Other")) {
                    edtTujuanPembiyaanLain.setVisibility(View.VISIBLE);
                    edtTujuanPembiyaanLain.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            tujuanPembiayaan = s.toString();
                        }
                    });
                } else {
                    tujuanPembiayaan = pilihan;
                    edtTujuanPembiyaanLain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onFailedTujuanPembiayaan(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    // TODO: Save Draft
    @Override
    public void onPreSynchKendaraan() {
        preLoading();
    }

    @Override
    public void onSuccessSynchKendaraan(String applicationId, String statusSinkron) {
        if (statusSinkron.equalsIgnoreCase("1")) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "form_pengajuan_elc_submit");
            mFirebaseAnalytics.logEvent("form_pengajuan_elc_submit", bundle);
            uploadAttachment(applicationId);
        } else {
//            Toast.makeText(this, "Berhasil Simpan Draft", Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "form_pengajuan_elc_savedraft");
            mFirebaseAnalytics.logEvent("form_pengajuan_elc_savedraft", bundle);
            EventBus.getDefault().post(new RefreshPengajuanEvent());
            EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
            startActivity(new Intent(this, HomeActivity.class));

            if (form.equalsIgnoreCase("Edit")) {
                Toast.makeText(this, "Pengajuan Berhasil Disubmit", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Berhasil Simpan Draft", Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new RefreshPengajuanEvent());
                EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
                startActivity(new Intent(this, HomeActivity.class));
            }
        }
    }

    @Override
    public void onEfNumberTaken() {
        successAndFailedLoading();
        Toast.makeText(FormPengajuanActivity.this, "EF Number sudah ada, Harap submit ulang", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onFailedSynchKendaraan(String message) {

        successAndFailedLoading();
        messageFailedApi(message);
    }

    //
    @Override
    public void onPreSubmitAttachment() {
        preLoading();
    }

    @Override
    public void onSuccessSubmitAttachment() {
        successAndFailedLoading();
        Toast.makeText(this, "Pengajuan Berhasil Disubmit", Toast.LENGTH_LONG).show();
//        EventBus.getDefault().post(new RefreshPengajuanEvent());
//        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        Intent i = new Intent(FormPengajuanActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onFailedSubmitAttachment(String message) {
        successAndFailedLoading();
        messageFailedApi(message);
        showAlertYesNo("onFailedSubmitAttachment", "Pemberitahuan", message, "Save as Draft", "Coba Lagi");
    }

    @Override
    public void onPreCheckEFNumber() {

    }

    @Override
    public void onSuccessCheckEFNumber(CheckEfNumberResponse mCheckEfNumberResponse, MasterFormPengajuan mMasterFormPengajuan) {
        if (mCheckEfNumberResponse.getStatus().equals("1")) {
            mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, "1", "");
        } else {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHss");
            date = df.format(Calendar.getInstance().getTime());
            strEfNumber = "EFM" + assetTypeId + branchId + date;
            setValueSaveAsDraft("Submit");
            mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
        }
    }

    @Override
    public void onFailedCheckEFNumber(String message) {
        mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
    }

    @Override
    public void onTokenCheckEFNumber() {
        mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
    }

    @Override
    public void onPreReferalCode() {

    }

    @Override
    public void onSuccessReferalCode(ReferalCodeResponse data) {
        isHitReferal = false;
    }

    @Override
    public void onFailedReferalCode(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreHargaAgunan() {

    }

    @Override
    public void onSuccessHargaAgunan(HargaAgunanResponse data, final IndonesianCurrencyEditText edtPriceAsset) {


        try {
            if(data.getMarketPriceValue() != null){
                final int intMaxPembiayaan = Integer.parseInt(data.getMarketPriceValue());
                edtPriceAsset.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(final CharSequence s, int start, int before, int count) {
                        final String inputString = s.toString().replace(",","");
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                if(inputString.equals("")){
                                    edtPriceAsset.setError(null);
                                }else{
                                    if(Integer.parseInt(inputString) >= intMaxPembiayaan){
                                        edtPriceAsset.setError("Maks : " + Integer.toString(intMaxPembiayaan));
                                    }else{
                                        edtPriceAsset.setError(null);
                                    }
                                }
                            }
                        };
                        handler.postDelayed(runnable, 1000);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }catch (Exception e){

        }

    }

    @Override
    public void onFailedHargaAgunan(String message) {

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
            clearAlerts();
            Validator validatorPribadi = new Validator(this);
            validatorPribadi.setValidationListener(this);
            NotEmptyRule notEmptyRuleFirst = new NotEmptyRule();
            KtpRule notEmptyKtpRule = new KtpRule();

            validatorPribadi.put(edtNoKtpPibadiValidasi, notEmptyKtpRule);

            /*Dihide untuk Beberapa waktu karena belum siap pada masa development Juli 2019*/

/*
            if(BuildConfig.FLAVOR.equals("staging")){
                validatorPribadi.put(edtNamaLegalValidasi, notEmptyRuleFirst);
                validatorPribadi.put(edtIbuValidasi, notEmptyRuleFirst);
            }
*/

            validatorPribadi.put(edtIbuValidasi, notEmptyRuleFirst);
            validatorPribadi.put(edtNamaLegalValidasi, notEmptyRuleFirst);
            validatorPribadi.put(edtTanggalLahirPribadiValidasi, notEmptyRuleFirst);
            validatorPribadi.put(edtHandphonePribadiValidasi, notEmptyRuleFirst);
            validatorPribadi.validate();
        }

        private void clearAlerts() {
            edtNamaPribadi.setError(null);
            edtNamaKtpPribadi.setError(null);
            edtNoKtpPribadi.setError(null);
            edtNoNpwpPribadi.setError(null);
            edtNoNpwpDetail.setError(null);
            edtTerbitKtpPribadi.setError(null);
            edtTempatLahirPribadi.setError(null);
            edtTanggalLahirPribadi.setError(null);
            edtNamaIbuPribadi.setError(null);
            edtTinggalSejakTahunPribadi.setError(null);
            edtTinggalSejakBulanPribadi.setError(null);
            edtJumlahTanggunganPribadi.setError(null);
            edtHandphonePribadi.setError(null);
            edtEmailPribadi.setError(null);
            edtNoKtpPibadiValidasi.setError(null);
            edtIbuValidasi.setError(null);
            edtNamaLegalValidasi.setError(null);
            edtTanggalLahirPribadiValidasi.setError(null);
            edtHandphonePribadiValidasi.setError(null);
        }

        @Override
        public void onValidationSucceeded() {
            String leftPhoneNumber;
            if (edtHandphonePribadiValidasi.getText().toString().length() < 8) {
                edtHandphonePribadiValidasi.setError("Minimal 8 Digit");
            } else if (edtHandphonePribadiValidasi.getText().toString().length() >= 8) {
                edtHandphonePribadiValidasi.setError(null);
                leftPhoneNumber = edtHandphonePribadiValidasi.getText().toString().substring(0, 2);
                if (!leftPhoneNumber.equals("08"))
                    edtHandphonePribadiValidasi.setError("Format Nomor Handphone Tidak Sesuai");
                else {
                    checkStatusPernikahan();
                    edtHandphonePribadiValidasi.setError(null);
                    edtHandphonePribadi.setText(edtHandphonePribadiValidasi.getText().toString());
                    mRecomendationPresenter.formRecomendation(token);
                }
            }
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(FormPengajuanActivity.this);

                // Display error messages ;)
                if (view instanceof EditText) ((EditText) view).setError(message);
            }
        }
    }

    private void preLoading() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private boolean checkMandatorySaveDraft() {
        checkAutoCompleteTextView();
        validator.removeRules(edtAreaPhoneTinggal);
        validator.removeRules(edtAreaPhoneKtp);
        validator.removeRules(edtPhoneKtp);
        validator.removeRules(edtPhoneTinggal);

        String strNoKtp = edtNoKtpPribadi.getText().toString();
        String strTglLahir = edtTanggalLahirPribadi.getText().toString();
        String strNoHp = edtHandphonePribadi.getText().toString();
        String strNamaPribadi = edtNamaPribadi.getText().toString();
        String strNamaKtp = edtNamaKtpPribadi.getText().toString();
        String agama = spnAgamaPribadi.getSelectedItem().toString();
        String pernihkahan = spnPernikahanPribadi.getSelectedItem().toString();
        String jenisKelamin = spnGenderPribadi.getSelectedItem().toString();
        String alamatPemohon = actAutoAlamatPemohon.getText().toString();
        String alamatKtp = actAutoKtpAlamatPemohon.getText().toString();
        String namaDarurat = edtNamaKerabat.getText().toString();
        String hubunganKerabat = spnHubunganKerabat.getSelectedItem().toString();
        String alamatKerabat = edtAlamatKerabat.getText().toString();
        String rtKerabat = edtRtKerabat.getText().toString();
        String rwKerabat = edtRwKerabat.getText().toString();
        String kotaKerabat = actAutoAlamatKerabat.getText().toString();
        String noHpKerabat = edtHpKerabat.getText().toString();
        String metodePenjualan = spnMetodePenjualan.getSelectedItem().toString();
        String pendidikan = spnPendidikanPribadi.getSelectedItem().toString();
        String statusRumah = spnStatusRumahPribadi.getSelectedItem().toString();

        boolean kerabat = true;
        boolean rekomendasi = true;

        if (strNoKtp.equals("")) edtNoKtpPribadi.setError("Wajib Di Isi");
        else edtNoKtpPribadi.setError(null);
        if (strTglLahir.equals("")) edtHandphonePribadi.setError("Wajib Di Isi");
        else edtHandphonePribadi.setError(null);
        if (strNoHp.equals("")) edtNoKtpPribadi.setError("Wajib Di Isi");
        else edtNoKtpPribadi.setError(null);
        if (strNamaPribadi.equals("")) edtNamaPribadi.setError("Wajib Di Isi");
        else edtNamaPribadi.setError(null);
        if (strNamaKtp.equals("")) edtNamaKtpPribadi.setError("Wajib Di Isi");
        else edtNamaKtpPribadi.setError(null);
        if (pernihkahan.equals("PILIH")) tvStatusPernikahan.setTextColor(Color.RED);
        else tvStatusPernikahan.setTextColor(Color.BLACK);
        if (jenisKelamin.equals("PILIH")) tvJenisKelamin.setTextColor(Color.RED);
        else tvJenisKelamin.setTextColor(Color.BLACK);
        if (agama.equals("PILIH")) tvAgama.setTextColor(Color.RED);
        else tvAgama.setTextColor(Color.BLACK);
        if (pendidikan.equals("PILIH")) tvPendidikan.setTextColor(Color.RED);
        else tvPendidikan.setTextColor(Color.BLACK);
        if (statusRumah.equals("PILIH")) tvStatusRumah.setTextColor(Color.RED);
        else tvStatusRumah.setTextColor(Color.BLACK);


        if (alamatPemohon.isEmpty()) actAutoAlamatPemohon.setError("Wajib di isi");
        else actAutoAlamatPemohon.setError(null);
        if (alamatKtp.isEmpty()) actAutoKtpAlamatPemohon.setError("Wajib di isi");
        else actAutoKtpAlamatPemohon.setError(null);

        if (alamatPemohon.isEmpty() || alamatKtp.isEmpty()) {
            imgDropDownAlamat.setImageResource(android.R.drawable.ic_dialog_alert);
        } else {
            imgDropDownAlamat.setImageResource(R.drawable.ic_arrow);
        }

        if (metodePenjualan.equals("PILIH")) {
            imgDropDownKop.setImageResource(android.R.drawable.ic_dialog_alert);
            tvJenisMetode.setTextColor(Color.RED);
//            return false;
        } else {
            imgDropDownKop.setImageResource(R.drawable.ic_arrow);
            tvJenisMetode.setTextColor(Color.BLACK);
//            return true;
        }

        tvHubunganKerabat.setTextColor(Color.BLACK);
        edtAlamatKerabat.setError(null);
        edtRtKerabat.setError(null);
        edtRwKerabat.setError(null);
        actAutoAlamatKerabat.setError(null);
        edtHpKerabat.setError(null);
        edtNamaKerabat.setError(null);

        if (!namaDarurat.isEmpty() || !hubunganKerabat.equalsIgnoreCase("Pilih")
                || !alamatKerabat.isEmpty() || !rtKerabat.isEmpty() || !rwKerabat.isEmpty()
                || !kotaKerabat.isEmpty() || !noHpKerabat.isEmpty()) {
            if (namaDarurat.isEmpty()) {
                edtNamaKerabat.setError("Wajib di isi");
                kerabat = false;
            }
            if (hubunganKerabat.equalsIgnoreCase("Pilih")) {
                tvHubunganKerabat.setTextColor(Color.RED);
            }
            if (alamatKerabat.isEmpty()) {
                edtAlamatKerabat.setError("Wajib di isi");
                kerabat = false;
            }
            if (rtKerabat.isEmpty()) {
                edtRtKerabat.setError("Wajib di isi");
                kerabat = false;
            }
            if (rwKerabat.isEmpty()) {
                edtRwKerabat.setError("Wajib di isi");
                kerabat = false;
            }
            if (kotaKerabat.isEmpty()) {
                actAutoAlamatKerabat.setError("Wajib di isi");
                kerabat = false;
            }
            if (!noHpKerabat.startsWith("08")) {
                edtHpKerabat.setError("Format no hp tidak sesuai");
                kerabat = false;
            }
            if (noHpKerabat.isEmpty()) {
                edtHpKerabat.setError("Wajib di isi");
                kerabat = false;
            }
        }
        if (!kerabat) {
            imgDropDownKerabat.setImageResource(android.R.drawable.ic_dialog_alert);
        } else imgDropDownKerabat.setImageResource(R.drawable.ic_arrow);


        if (strNoKtp.equals("") || strTglLahir.equals("") || strNoHp.equals("") || strNamaPribadi.equals("") || strNamaKtp.equals("") || agama.equals("PILIH") || pernihkahan.equals("PILIH") || jenisKelamin.equals("PILIH") || statusRumah.equals("PILIH") || pendidikan.equals("PILIH")) {
            imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);

        } else imgDropDownPribadi.setImageResource(R.drawable.ic_arrow);

        if (!rbRecomendationYes.isChecked() && !rbRecomendationNo.isChecked()) {
            imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
            tvGroupRekomendasi.setTextColor(Color.RED);
            rekomendasi = false;
        } else if (rbRecomendationYes.isChecked()) {
            imgDropDownRekomendasi.setImageResource(R.drawable.ic_arrow);
            tvGroupRekomendasi.setTextColor(Color.BLACK);
            rekomendasi = true;
        } else if (rbRecomendationNo.isChecked()) {
            rekomendasi = true;
            if ("Pilih".equalsIgnoreCase(spnRecomendation.getSelectedItem().toString())) {
                imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                tvGroupRekomendasi.setTextColor(Color.RED);
            } else imgDropDownRekomendasi.setImageResource(R.drawable.ic_arrow);
        }

        return !strNoKtp.equals("") && !strTglLahir.equals("") && !strNoHp.equals("") && !strNamaPribadi.equals("") && !strNamaKtp.equals("") && !pernihkahan.equals("PILIH") && !metodePenjualan.equals("PILIH")
                && !agama.equals("PILIH") && !jenisKelamin.equals("PILIH") && !pendidikan.equals("PILIH") && !statusRumah.equals("PILIH") && !alamatPemohon.isEmpty() && !alamatKtp.isEmpty() && kerabat && rekomendasi;
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
                deleteDataConfins();
                dialog.dismiss();
                finish();
            }
        });
        alert.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
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
        tlPengajuanDataPribadi.setVisibility(View.GONE);
        hideFormValidasiAwal();
        isHaveSignature = true;
    }

    @OnClick({R.id.img_ttd_pemohon_persetujuan, R.id.img_ttd_pasangan_persetujuan})
    public void onClickSignature(View view) {
        int statusAsset;
        int statusSignature;

        Util.hideKeyboard(this, view);
        if (Util.checkActiveLocation(this)) {
            if (checkCompleteDropDownKop()) {
                checkAutoCompleteTextView();

                if (actSupplierAsset.isSelectionFromPopUp())
                    actSupplierAsset.setError(null);
                else actSupplierAsset.setError("Wajib Pilih Supplier");
                if (actMarketingSupplierAsset.isSelectionFromPopUp())
                    actMarketingSupplierAsset.setError(null);
                else
                    actMarketingSupplierAsset.setError("Wajib Pilih Marketing Supplier");
                if (actProductOfferingProduct.isSelectionFromPopUp())
                    actProductOfferingProduct.setError(null);
                else actProductOfferingProduct.setError("Wajib Pilih Product Offering");

                if (actSupplierAsset.isSelectionFromPopUp() && actMarketingSupplierAsset.isSelectionFromPopUp() && actProductOfferingProduct.isSelectionFromPopUp()) {
                    imgDropDownProduct.setImageResource(R.drawable.ic_arrow);

                    if (typeDataOffering.equalsIgnoreCase("ELC")) {
                        String strJumlahAsset = String.valueOf(llParentTotalAsset.getChildCount());
                        if (strJumlahAsset.equals("0")) {
                            statusAsset = 0;
                            Toast.makeText(this, "Silahkan Lengkapi Detail Asset", Toast.LENGTH_SHORT).show();
                            imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                        } else {
                            if (validasiDataPerhitungan()) {
                                if (edtAngsuranPerbulan.getText().toString() == null || edtAngsuranPerbulan.getText().toString().isEmpty()) {
                                    statusAsset = 0;
                                    Toast.makeText(this, "Silahkan Lengkapi Data Perhitungan", Toast.LENGTH_SHORT).show();
                                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                                } else {
                                    statusAsset = 1;
                                    imgDropDownAsset.setImageResource(R.drawable.ic_arrow);
                                }

                            } else {
                                statusAsset = 0;
                                Toast.makeText(this, "Silahkan Lengkapi Detail Asset", Toast.LENGTH_SHORT).show();
                                imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                            }
                        }
                    } else {
                        statusAsset = 2;
                        /*KMB KMOB*/
                    }
                    /*Lanjutan*/
                    if (statusAsset == 1 || statusAsset == 2) {
                        if (spnTenorProduct.getSelectedItem() != null || !edtPurchasePricePerhitungan.getText().toString().equals("0")) {
                            imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
                            imgDropDownAsset.setImageResource(R.drawable.ic_arrow);
                            edtPurchasePricePerhitungan.setError(null);

                            if (form.equals("Edit")) statusSignature = 1;
                            else {
                                int dblCurrentNTF = (edtJumlahPembiayaan.getText().toString().isEmpty()) ? 0 : Integer.parseInt(edtJumlahPembiayaan.getText().toString().replace(",", ""));
                                // minimal NTF
                                int dblMinNTF = minimalNTF;
                                tilNtf.setError(null);
                                if (dblCurrentNTF < dblMinNTF) {
                                    statusSignature = 0;
                                    Toast.makeText(this, "Minimal NTF : " + dblMinNTF, Toast.LENGTH_SHORT).show();
                                    edtNtfPerhitungan.setError("Minimal NTF : " + dblMinNTF);
                                    //edtNtfPerhitungan.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                                    tilNtf.setError("Minimal NTF : " + dblMinNTF);
                                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                                    imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                                } else {
                                    statusSignature = 2;
                                    imgDropDownAsset.setImageResource(R.drawable.ic_arrow);
                                }
                            }

                            if (statusSignature == 1 || statusSignature == 2) {
                                imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
                                imgDropDownAsset.setImageResource(R.drawable.ic_arrow);
                                edtNtfPerhitungan.setError(null);
                                switch (view.getId()) {
                                    case R.id.img_ttd_pemohon_persetujuan:
                                        action = getString(R.string.signature_approval_of_the_applicant);
                                        takeSignatureAction.setText(action);
                                        takeSignatureLongitude.setText(String.valueOf(longitude));
                                        takeSignatureLatitude.setText(String.valueOf(latitude));

                                        if (Constant.Flag.IS_DEVELOPER && Constant.Flag.IS_DIRECT_SIGNATURE) {
                                            Intent intent = new Intent(this, SignatureActivity.class);
                                            intent.putExtra("tipe_ttd", SIGNATURE_PEMOHON);
                                            startActivity(intent);
                                        } else {
                                            if (isUserValid) {
                                                Intent intent = new Intent(this, SignatureActivity.class);
                                                intent.putExtra("tipe_ttd", SIGNATURE_PEMOHON);
                                                startActivity(intent);
                                            } else {
                                                mSyaratDanKetentuanPresenter.syaratLoad(token);
                                            }
                                        }
                                        break;
                                    case R.id.img_ttd_pasangan_persetujuan:
                                        if (Constant.Flag.IS_DEVELOPER && Constant.Flag.IS_DIRECT_SIGNATURE) {
                                            Intent intent = new Intent(this, SignatureActivity.class);
                                            intent.putExtra("tipe_ttd", SIGNATURE_PASANGAN);
                                            startActivity(intent);
                                        } else {
                                            if (isUserValid) {
                                                Intent intent = new Intent(this, SignatureActivity.class);
                                                intent.putExtra("tipe_ttd", SIGNATURE_PASANGAN);
                                                startActivity(intent);
                                            } else {
                                                mSyaratDanKetentuanPresenter.syaratLoad(token);
                                            }
                                        }
                                        break;
                                }
                            }
                        } else {
                            imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                            imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                            edtPurchasePricePerhitungan.setError("Harga Barang Tidak Boleh Rp 0");
                        }
                    }
                } else
                    imgDropDownProduct.setImageResource(android.R.drawable.ic_dialog_alert);

            }
        }
    }

    @OnClick({R.id.header_data_validasi_awal, R.id.header_master_header, R.id.header_data_pribadi, R.id.header_data_pasangan, R.id.header_alamat_pemohon, R.id.header_informasi_kerabat, R.id.header_data_pekerjaan, R.id.header_data_kartu_kredit, R.id.header_data_kartu_membership, R.id.header_detail_product, R.id.header_detail_npwp, R.id.header_detail_asset, R.id.header_data_asuransi, R.id.header_data_perhitungan, R.id.header_data_keterangan, R.id.header_image_pengajuan, R.id.header_persetujuan, R.id.header_recomendation, R.id.header_informasi_penawaran, R.id.header_persetujuan_tambahan})
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
                showHideContent(rlMasterHeader);
                break;
            case R.id.header_data_pribadi:
                showHideContent(rlDataPribadi);
                break;
            case R.id.header_data_pasangan:
                showHideContent(rlDataPasangan);
                break;
            case R.id.header_alamat_pemohon:
                showHideContent(rlAlamatPemohon);
                break;
            case R.id.header_informasi_kerabat:
                showHideContent(rlInformasiKerabat);
                break;
            case R.id.header_data_pekerjaan:
                showHideContent(rlDataPekerjaan);
                break;
            case R.id.header_data_kartu_kredit:
                showHideContent(rlDataKartuKredit);
                break;
            case R.id.header_data_kartu_membership:
                showHideContent(rlDataKartuMembership);
                break;
            case R.id.header_detail_product:
                showHideContent(rlDetailProduct);
                break;
            case R.id.header_detail_asset:
                showHideContent(rlDetailAsset);
                break;
            case R.id.header_detail_npwp:
                showHideContent(rlDetailNpwp);
                break;
            case R.id.header_data_asuransi:
                String strJumlahAsset = String.valueOf(llParentTotalAsset.getChildCount());
                if (form.equals("Edit")) {//|| form.equals("Draft") && draftEdit.equals("draft_edit")
                    rbGroupAsuransiAgunan.setEnabled(false);
                    rbGroupManualAsuransi.setEnabled(false);
                    rbAsuransiAgunanYes.setEnabled(false);
                    rbAsuransiAgunanNo.setEnabled(false);
                    rbManualYes.setEnabled(false);
                    rbManualNo.setEnabled(false);
                } else {
                    if (!actSupplierAsset.getText().toString().equals("") &&
                            !actMarketingSupplierAsset.getText().toString().equals("") &&
                            !actProductOfferingProduct.getText().toString().equals("") &&
                            !strJumlahAsset.equals("0")) {
                        rbGroupAsuransiAgunan.setEnabled(true);
                        rbGroupManualAsuransi.setEnabled(true);
                        rbAsuransiAgunanYes.setEnabled(true);
                        rbAsuransiAgunanNo.setEnabled(true);
                        rbManualYes.setEnabled(true);
                        rbManualNo.setEnabled(true);
                        imgDropDownProduct.setImageResource(R.drawable.ic_arrow);
                    } else {
                        rbGroupAsuransiAgunan.setEnabled(false);
                        rbGroupManualAsuransi.setEnabled(false);
                        rbAsuransiAgunanYes.setEnabled(false);
                        rbAsuransiAgunanNo.setEnabled(false);
                        rbManualYes.setEnabled(false);
                        rbManualNo.setEnabled(false);
                        imgDropDownProduct.setImageResource(android.R.drawable.ic_dialog_alert);
                        Toast.makeText(this, "Harap isi Data Product dan Aset terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }
                showHideContent(rlDataAsuransi);
                break;
            case R.id.header_data_perhitungan:
                if (validasiDataPerhitungan()) {
                    prepareDataCalculating();
                    showHideContent(rlDataPerhitungan);
                    if (form.equalsIgnoreCase("Draft") || form.equalsIgnoreCase("New")) {
                        if (tenor != 0 && !spnTenorProduct.getSelectedItem().toString().equalsIgnoreCase("Pilih")) {
                            if (rbAsuransiAgunanYes.isChecked()) {
                                String dpCurrentString = edtDpPerhitungan.getText().toString().replace(",", "");
                                int dpCurrent = !dpCurrentString.equals("") ? Integer.parseInt(dpCurrentString) : 0;
                                if (rbManualYes.isChecked()) {
                                    premiAsuransiManual = edtPremiAsuransiManual.getText().toString().replace(",", "");
                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, dpCurrent, discount, admin_fee, interest, type, Integer.parseInt(premiAsuransiManual), bebasBunga, Integer.parseInt(dp));
                                } else {
                                    mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiOtomatis(token, otr_price, tenor, dpCurrent, discount, admin_fee, interest, type, bebasBunga, Integer.parseInt(dp));
                                }
                            } else {
                                mPerhitunganWhiteGoodsPresenter.getPerhitunganWGPremiManual(token, otr_price, tenor, down_payment, discount, admin_fee, interest, type, 0, bebasBunga, Integer.parseInt(dp));
                            }
                        }
                    }

                } else {
                    Toast.makeText(this, "Asset barang harus di isi terlebih dahulu", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.header_data_keterangan:
                showHideContent(rlDataKeterangan);
                break;
            case R.id.header_image_pengajuan:
                showHideContent(rlImagePengajuan);
                break;
            case R.id.header_persetujuan:
                showHideContent(rlPersetujuan);
                break;
            case R.id.header_recomendation:
                showHideContent(rlRecomendation);
                break;
            case R.id.header_informasi_penawaran:
                showHideContent(rlInformasiPenawaran);
                break;
            case R.id.header_persetujuan_tambahan:
                showHideContent(rlPersetujuanTambahan);
                break;
        }
    }


    @OnClick({R.id.img_take_picture_1, R.id.img_take_picture_2, R.id.img_take_picture_3, R.id.img_take_picture_4, R.id.img_take_picture_5, R.id.img_take_picture_6, R.id.img_take_picture_7, R.id.img_take_picture_8, R.id.img_take_picture_9, R.id.img_take_picture_10, R.id.img_take_picture_11})
    public void onClickTakePhoto(View view) {
        if (Util.checkActiveLocation(this)) {
            if (!form.equals("Edit") && bitmapTtdPemohon != null) {
                switch (view.getId()) {
                    case R.id.img_take_picture_1:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_1);
                        action = getString(R.string.action_take_ktp);
                        sendCoordinate();
                        takeKtpAction.setText(action);
                        takeKtpLongitude.setText(String.valueOf(longitude));
                        takeKtpLatitude.setText(String.valueOf(latitude));
                        break;
                    case R.id.img_take_picture_2:
//                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_2);
                        final Dialog dialog = new Dialog(this);
                        dialog.setContentView(R.layout.dialog_terms_photo_customer);
                        final Button btnSubmit = dialog.findViewById(R.id.btnTakePhotoCustomer);
                        btnSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_2);
                                action = getString(R.string.action_take_customer);
                                sendCoordinate();
                                takeCustomerAction.setText(action);
                                takeCustomerLongitude.setText(String.valueOf(longitude));
                                takeCustomerLatitude.setText(String.valueOf(latitude));
                            }
                        });
                        dialog.show();
                        break;
                    case R.id.img_take_picture_3:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_3);
                        action = getString(R.string.action_take_paycheck);
                        sendCoordinate();
                        takePaycheckAction.setText(action);
                        takePaycheckLongitude.setText(String.valueOf(longitude));
                        takePaycheckLatitude.setText(String.valueOf(latitude));
                        break;
                    case R.id.img_take_picture_4:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_4);
                        action = getString(R.string.action_take_additional_documents);
                        sendCoordinate();
                        takeAdditionalDocumentsAction.setText(action);
                        takeAdditionalDocumentsLongitude.setText(String.valueOf(longitude));
                        takeAdditionalDocumentsLatitude.setText(String.valueOf(latitude));
                        break;
                    case R.id.img_take_picture_5:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_5);
                        action = getString(R.string.action_take_more_photos_five);
                        sendCoordinate();
                        break;
                    case R.id.img_take_picture_6:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_6);
                        action = getString(R.string.action_take_more_photos_six);
                        sendCoordinate();
                        break;
                    case R.id.img_take_picture_7:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_7);
                        action = getString(R.string.action_take_more_photos_seven);
                        sendCoordinate();
                        break;
                    case R.id.img_take_picture_8:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_8);
                        action = getString(R.string.action_take_more_photos_eight);
                        sendCoordinate();
                        break;
                    case R.id.img_take_picture_9:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_9);
                        action = getString(R.string.action_take_more_photos_nine);
                        sendCoordinate();
                        break;
                    case R.id.img_take_picture_10:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_10);
                        action = getString(R.string.action_take_more_photos_ten);
                        sendCoordinate();
                        break;
                    case R.id.img_take_picture_11:
                        permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_11);
                        action = getString(R.string.action_take_more_photos_eleven);
                        sendCoordinate();
                        break;
                }
            } else if (bitmapTtdPemohon == null) {
                Toast.makeText(this, "Harap Lengkapi tanda tangan terlebih dahulu", Toast.LENGTH_LONG).show();

            }
        }
    }

    @OnClick({R.id.img_delete_picture_1, R.id.img_delete_picture_2, R.id.img_delete_picture_3, R.id.img_delete_picture_4, R.id.img_delete_picture_5, R.id.img_delete_picture_6, R.id.img_delete_picture_7, R.id.img_delete_picture_8, R.id.img_delete_picture_9, R.id.img_delete_picture_10, R.id.img_delete_picture_11})
    public void onClickDeletePhoto(View view) {
        if (!form.equals("Edit")) {
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

    public void sendktp(String path) {
        fileOcr = new File(path);
    }

    private void refreshAttachment(int i) {
        viewPbImages.get(i).setVisibility(View.VISIBLE);
        viewCameras.get(i).setVisibility(View.GONE);
        viewRefreshImages.get(i).setVisibility(View.GONE);
        viewTakeImages.get(i).setVisibility(View.GONE);

        Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImages.get(i));

    }

    private void createDialogSavedata(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(R.string.title_permberitahuan);
        builder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onPreBlackList() {
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
        tvPbh.setVisibility(View.VISIBLE);
        pbHorizontal.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessBlackList(final BlackListResponse blackListResponse, List<
            String> fullNameList, List<ApplicationBlacklist> applicationBlacklist) {
        this.blackListResponse = blackListResponse;
        DateFormat df = new SimpleDateFormat("yyyyMMddHHss");// kurangin EF numbernya
        date = df.format(Calendar.getInstance().getTime());
        custType = blackListResponse.getStatusKreditmu().replace(" ", "").toUpperCase();
        bucketMessage = blackListResponse.getBucketMessage();
        agreement = blackListResponse.getNoAgreement();
        timeDelay = blackListResponse.getTimeDelay();
        amountOfFines = blackListResponse.getAmountOfFines();

        if (blackListResponse.getIsBlackList() == 0) {
            blackListDate = blackListResponse.getDateStart();
            if (blackListResponse.getIsEmptyData() == 1 || blackListResponse.getIsNew() == 1)
                strStatusPengajuan = "Baru";
            else strStatusPengajuan = "RO";
            edtStatusKonsumen.setText(strStatusPengajuan);
            edtStatusKonsumen.setTextColor(GRAY);
            uuid = Util.RandomDateNumber();

            if (blackListResponse.getIsEmptyData() == 0) {
                showDataBlackList(blackListResponse);
            } else {
                edtStatusKreditmu.setTextColor(GRAY);
                edtStatusKreditmu.setText(R.string.text_non_kreditmu);
                customerIdConfins = "";
            }

            DateFormat currentDf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = currentDf.format(Calendar.getInstance().getTime());

            StartTime = SystemClock.uptimeMillis();
            Log.i("stopWatch_start", String.valueOf("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds)));

            if (form.equals("Edit")) {
                initAutoComplete();
                initRadioGroup();
                initSpinner();
                showAllSection();
                hideAllLoading();
                tlPengajuanDataPribadi.setVisibility(View.GONE);
                hideFormValidasiAwal();
                pbMain.setVisibility(View.GONE);
                lnButtonWrapper.setVisibility(View.VISIBLE);
                rlDataNamaPemohon.setVisibility(View.VISIBLE);
                rlHeaderDataNamaPemohon.setVisibility(View.VISIBLE);
                scrMainLayout.setVisibility(View.VISIBLE);
                if (!"".equalsIgnoreCase(blackListResponse.getBucketMessage()))
                    createDialog(bucketMessage, agreement, timeDelay, amountOfFines);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } else {
//                strEfNumber = "EFM" + assetTypeId + branchId + date;
//                strEfNumberResponse = blackListResponse.getE
                initAutoComplete();
                initRadioGroup();
                initSpinner();
                showAllSection();
                rlHeaderDataNamaPemohon.setVisibility(View.VISIBLE);
                rlDataNamaPemohon.setVisibility(View.VISIBLE);
                scrMainLayout.setVisibility(View.VISIBLE);
                lnButtonWrapper.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
                hideAllLoading();
                pbMain.setVisibility(View.GONE);
                tlPengajuanDataPribadi.setVisibility(View.GONE);
                lnWrapperDataValidasiAwal.setVisibility(View.GONE);
                if (!bucketMessage.isEmpty())
                    createDialog(bucketMessage, agreement, timeDelay, amountOfFines);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                TimeBuff += MillisecondTime;
                Log.i("stopWatch_stop", String.valueOf("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds)));
            }

            mobileSubmissionKey = blackListResponse.getMobileSubmissionKey();
            totalInsurancePersen = blackListResponse.getTotalInsurancePersen();
            nominalAdminLain = blackListResponse.getFeeAdminlain();
            countSignature = 0;
            if (fullNameList.isEmpty()) lnWrapperDataNamaPemohon.setVisibility(View.GONE);
            else {
                tvNamaPemohon.setEnabled(false);
                tvNamaPemohon.setTextColor(Color.GRAY);
                tvNamaPemohon.setText(String.valueOf(fullNameList.get(0)).replace(".", " ").replace("\"", " ").replace(",", " ").replace("'", " ").replace("-", " "));
                lnWrapperDataNamaPemohon.setVisibility(View.VISIBLE);
            }
        } else {
            hideAllLoading();
            pbMain.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            DialogFragment dialogFragment = new BlacklistDialog();
            Bundle args = new Bundle();
            args.putString("bucketMessage", bucketMessage);
            args.putString("agreement", agreement);
            args.putString("timeDelay", timeDelay);
            args.putString("amountOfFines", amountOfFines);
            dialogFragment.setArguments(args);
            dialogFragment.show(getSupportFragmentManager(), "blacklistDialog");
        }
        mTujuanPembiyaanPresenter.getTujuanPembiyaan(token);
    }

    @Override
    public void onFailedBlackList(String message) {
        hideAllLoading();
        if (form.equalsIgnoreCase("Edit")) typeEdit = "assign";
        else typeEdit = "draft";

        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        if (form.equals("Draft") || form.equals("Edit")) {

            llLoading.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBlackListPresenter.blackList(token,
                            edtTanggalLahirPribadi.getText().toString(),
                            edtNoKtpPribadi.getText().toString(),
                            edtHandphonePribadi.getText().toString(),
                            typeDataOfferingBlackList,
                            aoBranch, edtNamaLegalValidasi.getText().toString(), edtIbuValidasi.getText().toString(), typeEdit);
                }
            });
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDataBlackList(BlackListResponse blackListResponse) {
        edtHandphonePribadi.setEnabled(false);
        edtNamaIbuPribadi.setEnabled(false);
        edtNamaKtpPribadi.setEnabled(false);
        edtNamaPribadi.setEnabled(false);

        edtNamaKtpPribadi.setTextColor(Color.GRAY);
        edtNamaPribadi.setTextColor(Color.GRAY);
        edtNamaIbuPribadi.setTextColor(Color.GRAY);
        edtHandphonePribadi.setTextColor(Color.GRAY);

        customerIdConfins = blackListResponse.getApplicationBlacklists().get(0).getCustomerIdConfins();
        String tlgktp = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getiDTypeIssuedDate();
        phoneNumberBlacklist = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMobilePhone();
        StringBuilder builderMobilePhone = new StringBuilder();
        StringBuilder builderNumOfDependence = new StringBuilder();
        StringBuilder builderSurgateMotherName = new StringBuilder();
        int lengthMobilePhone = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMobilePhone().length();
        String lengthEducation = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getEducation().trim();
        String lengthMaritalStatus = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus().trim();
        int lengthNumOfDependence = String.valueOf(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getNumOfDependence()).length();
        int lengthSurgateMotherName = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getSurgateMotherName().length();
        String sensorEducation;
        String sensorMaritalStatus;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy h:mm:ss:SSSa", Locale.US);

        if (blackListResponse.getStatusKreditmu().equalsIgnoreCase("Kreditmu")) {
            initKreditmu();
            for (int i = 0; i < lengthMobilePhone; i++)
                builderMobilePhone.append("*");
            for (int l = 0; l < lengthNumOfDependence; l++)
                builderNumOfDependence.append("*");
            for (int m = 0; m < lengthSurgateMotherName; m++)
                builderSurgateMotherName.append("*");

            if (lengthEducation.equals("SD") || lengthEducation.equals("D1") || lengthEducation.equals("D2") || lengthEducation.equals("D3"))
                sensorEducation = "**";
            else if (lengthEducation.equals("SLTP") || lengthEducation.equals("SLTA"))
                sensorEducation = "****";
            else sensorEducation = "*************";

            if (lengthMaritalStatus.equals("M")) sensorMaritalStatus = "****";
            else sensorMaritalStatus = "******";

            edtPendidikanPribadiHide.setText(sensorEducation);
            edtStatusPernikahanPribadiHide.setText(sensorMaritalStatus);
            String nope = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMobilePhone();
            edtHandphonePribadiHide.setText("".equalsIgnoreCase(nope) ? "************" : builderMobilePhone.toString());
            edtHandphonePribadi.setText(nope);
            if (edtJumlahTanggunganPribadi.getText().toString().isEmpty() || edtJumlahTanggunganPribadi.getText().toString().equals("0")) {
                edtJumlahTanggunganPribadi.setText("0");
                edtJmlTanggunganPribadiHide.setText("*");
                edtJmlTanggunganPribadiHide.setTextColor(GRAY);
            } else {
                edtJmlTanggunganPribadiHide.setText(builderNumOfDependence.toString());
                edtJmlTanggunganPribadiHide.setTextColor(GRAY);
            }
            edtNamaIbuPribadiHide.setText(builderSurgateMotherName.toString());
        } else {
            edtStatusKreditmu.setTextColor(GRAY);
            edtStatusKreditmu.setText(R.string.text_non_kreditmu);
            edtHandphonePribadi.setText(edtHandphonePribadiValidasi.getText().toString() + "");
            if (isAssignEdit) {
                String nope = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMobilePhone();
                edtHandphonePribadi.setText(nope);
            }
        }
        try {
            SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date datektp = null;
            datektp = dateFormat.parse(tlgktp);
            String newDateKtp = newformat.format(datektp);
            if (!form.equals("Edit")) edtTerbitKtpPribadi.setText(newDateKtp);
        } catch (ParseException e) {
            if (BuildConfig.DEBUG) Log.e("Convert format date", String.valueOf(e));
        }

        sensorData = true;
        lnWrapperDataPasangan.setVisibility(View.VISIBLE);
        //Data Pribadi
        if (form.equals("New")) {
            String blackListJumlahTanggungan = blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getNumOfDependence();

            edtNoKtpPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getiDNumber());
//            edtNoNpwpPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getPersonalNPWP());
            edtNamaPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getFullName().replace(".", " ").replace("\"", " ").replace(",", " ").replace("'", " ").replace("-", " "));
            edtNamaKtpPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getLegalName().replace(".", " ").replace("\"", " ").replace(",", " ").replace("'", " ").replace("-", " "));
            edtNoNpwpDetail.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getPersonalNPWP());
            edtTempatLahirPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getBirthPlace());
            edtNamaIbuPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getSurgateMotherName());
            edtTinggalSejakTahunPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getStaySinceYear());
            edtTinggalSejakBulanPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getStaySinceMonth());
            edtJumlahTanggunganPribadi.setText(blackListJumlahTanggungan.isEmpty() ? "0" : blackListJumlahTanggungan);
            edtEmailPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getEmail());
            edtTerbitKtpPribadi.setText(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getiDTypeIssuedDate());
            for (int h = 0; h < genderValue.length; h++) {
                if (genderValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getGender())) {
                    spnGenderPribadi.setSelection(h);
                    break;
                }
            }

            for (int h = 0; h < pendidikanValue.length; h++) {
                if (pendidikanValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getEducation().replaceAll("\\s", ""))) {
                    spnPendidikanPribadi.setSelection(h);
                    break;
                }
            }

            for (int h = 0; h < wargaNegaraValue.length; h++) {
                if (wargaNegaraValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getNationality())) {
                    spnWargaNegaraPribadi.setSelection(h);
                    break;
                }
            }

            for (int h = 0; h < statusRumahValue.length; h++)
                if (statusRumahValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getHomeStatus())) {
                    spnStatusRumahPribadi.setSelection(h);
                    break;
                }
            for (int h = 0; h < agamaValue.length; h++)
                if (agamaValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getReligion())) {
                    spnAgamaPribadi.setSelection(h);
                    break;
                }


            for (int h = 0; h < statusPernikahanValue.length; h++) {
                if (statusPernikahanValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPernikahanPribadi.setSelection(h);
                    break;
                } else if (statusPernikahanOri[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPernikahanPribadi.setSelection(h);
                } else if (blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda") || blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda / Duda")) {
                    spnPernikahanPribadi.setSelection(4);
                    break;
                }
            }

            //Alamat pribadi
            edtAlamatTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAddress());
            edtRtTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getrT().replace(" ", ""));
            actAutoAlamatPemohon.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getCity());
            strCityAlamatKerabat = blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getCity();
            strCityAlamatPekerjaan = blackListResponse.getApplicationBlacklists().get(0).getCompany().getCity();
            strCityAlamatPemohon = blackListResponse.getApplicationBlacklists().get(0).getResidance().getCity();
            strCityKtpAlamatPemohon = blackListResponse.getApplicationBlacklists().get(0).getLegal().getCity();


            if (blackListResponse.getApplicationBlacklists().get(0).getResidance().getrT() != null) {
                edtRwTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getrW().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getResidance().getAreaPhone() != null) {
                edtAreaPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAreaPhone().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getResidance().getPhone() != null) {
                edtPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getPhone().replace(" ", ""));
            }


            //Alamat Tinggal
            actAutoKtpAlamatPemohon.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getCity());
            edtAlamatTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAddress());
            if (blackListResponse.getApplicationBlacklists().get(0).getResidance().getrT() != null) {
                edtRtTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getrT().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getResidance().getrW() != null) {
                edtRwTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getrW().replace(" ", ""));
            }
            //int zipCodeAlamat = Integer.parseInt((blackListResponse.getApplicationBlacklists().get(0).getResidance().getZipCode()).replaceAll(" ", ""));
            if (blackListResponse.getApplicationBlacklists().get(0).getResidance().getAreaPhone() != null) {
                edtAreaPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getAreaPhone().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getResidance().getPhone() != null) {
                edtPhoneTinggal.setText(blackListResponse.getApplicationBlacklists().get(0).getResidance().getPhone().replace(" ", ""));
            }


            //Alamat Ktp
            edtAlamatKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getAddress());
            if (blackListResponse.getApplicationBlacklists().get(0).getLegal().getrT() != null) {
                edtRtKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getrT().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getLegal().getrW() != null) {
                edtRwKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getrW().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getLegal().getrW() != null) {
                edtRwKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getrW().replace(" ", ""));
            }
            int zipCodeAlamatKtp = Integer.parseInt((blackListResponse.getApplicationBlacklists().get(0).getLegal().getZipCode()).replaceAll(" ", ""));
            if (blackListResponse.getApplicationBlacklists().get(0).getLegal().getAreaPhone() != null) {
                edtAreaPhoneKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getAreaPhone().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getLegal().getPhone() != null) {
                edtPhoneKtp.setText(blackListResponse.getApplicationBlacklists().get(0).getLegal().getPhone().replace(" ", ""));
            }

            //Data Pasangan
//            actPasanganKota.setText(application.getFamilyData().get(0).getCity());


            //Informasi contact darurat
            edtNamaKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getName());
            edtAlamatKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getAddress());
            actAutoAlamatKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getCity());
            if (blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getrW() != null) {
                edtRwKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getrW().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getrT() != null) {
                edtRtKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getrT().replace(" ", ""));
            }
            int zipCodeEmergencyContact = Integer.parseInt((blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getZipCode()).replaceAll(" ", ""));
            if (blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getHomePhoneArea() != null) {
                edtAreaPhoneRumahKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getHomePhoneArea().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getHomePhone() != null) {
                edtPhoneRumahKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getHomePhone().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getOfficePhoneArea().isEmpty()) {
                edtAreaPhoneKantorKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getOfficePhoneArea().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getOfficePhone() != null) {
                edtPhoneKantorKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getOfficePhone().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getMobilePhone() != null) {
                edtHpKerabat.setText(blackListResponse.getApplicationBlacklists().get(0).getEmergencyContact().getMobilePhone().replace(" ", ""));
            }

            //Data Pekerjaan
            edtNamaPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getName());
            edtAlamatPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getAddress());
            actAutoAlamatPekerjaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getCity());
            strCityAlamatPekerjaan = blackListResponse.getApplicationBlacklists().get(0).getCompany().getCity();
            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getrT() != null) {
                edtRtPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getrT().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getrW() != null) {
                edtRwPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getrW().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getAreaPhone() != null) {
                edtAreaPhonePerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getAreaPhone().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getPhone() != null) {
                edtPhonePerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getPhone().replace(" ", ""));
            }
            edtBekerjaSejakPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getEmploymentSinceYear());
            professionKode = blackListResponse.getApplicationBlacklists().get(0).getCompany().getProfessionID();
            jobTypeKode = blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobTypeID();
            jobPositionKode = blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobPositionID();
            industriKode = String.valueOf(blackListResponse.getApplicationBlacklists().get(0).getCompany().getIndustryTypeID());


            actAutoAlamatKerabat.setSelectionFromPopUp(true);
            actAutoAlamatPekerjaan.setSelectionFromPopUp(true);
            actAutoKtpAlamatPemohon.setSelectionFromPopUp(true);
            actAutoAlamatPemohon.setSelectionFromPopUp(true);
            actIndustriPerusahaan.setSelectionFromPopUp(true);
            actJobPositionPerusahaan.setSelectionFromPopUp(true);
            actJobTypePerusahaan.setSelectionFromPopUp(true);
            actProfesiPerusahaan.setSelectionFromPopUp(true);

            /*if (actProfesiPerusahaan.getText().toString().equalsIgnoreCase("WIRASWASTA")) {
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
            }*/
//            actAutoAlamatPekerjaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getCityName());
            actProfesiPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getProfessionName());
            actJobTypePerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobTypeName());
            actJobPositionPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getJobPositionName());
            actIndustriPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getIndustryTypeName());
            String areaPhonePerusahaan = blackListResponse.getApplicationBlacklists().get(0).getCompany().getAreaPhone().replaceAll(" ", "");
            String phonePerusahaan = blackListResponse.getApplicationBlacklists().get(0).getCompany().getPhone().replaceAll(" ", "");

            for (int h = 0; h < statusPernikahanValue.length; h++) {
                if (statusPernikahanValue[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPernikahanPribadi.setSelection(h);
                    break;
                } else if (statusPernikahanOri[h].equalsIgnoreCase(blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus())) {
                    spnPernikahanPribadi.setSelection(h);
                    break;
                } else if (blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda") || blackListResponse.getApplicationBlacklists().get(0).getPersonalData().getMaritalStatus().equalsIgnoreCase("Janda / Duda")) {
                    spnPernikahanPribadi.setSelection(4);
                    break;
                }
            }

            int zipCodePerusahaan = Integer.parseInt((blackListResponse.getApplicationBlacklists().get(0).getCompany().getZipCode().replace(" ", "0")).replaceAll(" ", ""));
            edtAreaPhonePerusahaan.setText(String.valueOf(areaPhonePerusahaan));
            edtPhonePerusahaan.setText(String.valueOf(phonePerusahaan));

            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getMonthlyFixedIncome() != null) {
                edtPenghasilanTetapPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getMonthlyFixedIncome().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getMonthlyVariableIncome() != null) {
                edtPenghasilanLainPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getMonthlyVariableIncome().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getSpouseIncome() != null) {
                edtPenghasilanPasanganPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getSpouseIncome().replace(" ", ""));
            }
            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getLivingCostAmount() != null) {
                edtBiayaHidupPerusahaan.setText(blackListResponse.getApplicationBlacklists().get(0).getCompany().getLivingCostAmount().replace(" ", ""));
            }

            if (blackListResponse.getApplicationBlacklists().get(0).getCompany().getIsAffiliateWithPP().equalsIgnoreCase("0"))
                rbNoPerusahaan.setChecked(true);
            else rbYesPerusahaan.setChecked(true);
        }
    }

    private void initKreditmu() {
        edtHandphonePribadi.setEnabled(false);
        edtPendidikanPribadiHide.setEnabled(false);
        edtNamaPribadi.setEnabled(false);
        edtNamaKtpPribadi.setEnabled(false);
        edtNoKtpPribadi.setEnabled(false);
        edtNamaIbuPribadi.setEnabled(false);
        edtTanggalLahirPribadi.setEnabled(false);
        edtTempatLahirPribadi.setEnabled(false);
        edtHandphonePribadiHide.setEnabled(false);
        edtStatusPernikahanPribadiHide.setEnabled(false);
        /*edtJmlTanggunganPribadiHide.setEnabled(false);*/
        edtNamaIbuPribadiHide.setEnabled(false);

        edtNamaPribadi.setTextColor(GRAY);
        edtNamaKtpPribadi.setTextColor(GRAY);
        edtNoKtpPribadi.setTextColor(GRAY);
        edtNamaIbuPribadi.setTextColor(GRAY);
        edtTanggalLahirPribadi.setTextColor(GRAY);
        edtTempatLahirPribadi.setTextColor(GRAY);
        edtHandphonePribadi.setTextColor(GRAY);
        edtHandphonePribadiHide.setTextColor(GRAY);
        edtPendidikanPribadiHide.setTextColor(GRAY);
        edtStatusPernikahanPribadiHide.setTextColor(GRAY);
        /*edtJmlTanggunganPribadiHide.setTextColor(Color.GRAY);*/
        edtNamaIbuPribadiHide.setTextColor(GRAY);
        edtHandphonePribadi.setVisibility(View.GONE);
        lnPendidikan.setVisibility(View.GONE);
        lnStatusPernikahan.setVisibility(View.GONE);
        edtJumlahTanggunganPribadi.setVisibility(View.GONE);
        edtNamaIbuPribadi.setVisibility(View.GONE);

        edtHandphonePribadiHide.setVisibility(View.VISIBLE);
        edtPendidikanPribadiHide.setVisibility(View.VISIBLE);
        edtStatusPernikahanPribadiHide.setVisibility(View.VISIBLE);
        edtJmlTanggunganPribadiHide.setVisibility(View.VISIBLE);
        edtNamaIbuPribadiHide.setVisibility(View.VISIBLE);

        edtStatusKreditmu.setText(R.string.text_kreditmu);
        edtStatusKreditmu.setTextColor(GRAY);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreSendConfirmSignature() {
        // progressDialog.show();
        // progressDialog.setMessage("Authenticating...");
    }

    @Override
    public void onSuccessConfirmCodeSignature(int isValid, String message, String usedOTP) {
        // progressDialog.dismiss();
        if (isValid == 1) {
            isUserValid = true;
            this.usedOTP = usedOTP;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            isUserValid = false;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailedConfirmCodeSignature(String message) {
        // progressDialog.dismiss();
        isUserValid = false;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreRefreshToken() {
    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.commit();
        switch (tokenExpiredType) {
            case tokenTypeBlacklist:
                mBlackListPresenter.blackList(token,
                        edtTanggalLahirPribadi.getText().toString(),
                        edtNoKtpPribadi.getText().toString(),
                        edtHandphonePribadi.getText().toString(),
                        typeDataOfferingBlackList,
                        aoBranch, edtNamaLegalValidasi.getText().toString(), edtIbuValidasi.getText().toString(), "");
                break;
            case tokenTypeSendCode:
                break;
            case tokenTypeSaldo:
                mSaldoKreditmuPresenter.cekSaldoKreditmu(token, ktpSaldo, dateSaldo, edtAngsuranPerbulan.getText().toString().replaceAll(",", ""));
                break;
            case tokenTypeMaster:
//                firstSync();disini
                break;
            case tokenTypeSyarat:
                mSyaratDanKetentuanPresenter.syaratLoad(token);
                break;
            case tokenTypeMasking:
                mMaskingPresenter.getDataMasking(token, productId, productOfferingId);
                break;
            case tokenTypeOcr:
                break;
            case tokenCoordinate:
                mCoordinatePresenter.coordinate(token, latitude, longitude, action);
                break;
            case tokenFormShowHide:
                break;
            case tokenRecomendation:
                mRecomendationPresenter.formRecomendation(token);
                break;
            case tokenProdOfSuppMapping:
                break;
        }
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenBlackListExpired() {
        String tokenStr = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(tokenStr);
        tokenExpiredType = tokenTypeBlacklist;
    }

    @Override
    public void onTokenSendCodeSignatureExpired() {
        String tokenStr = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(tokenStr);
        tokenExpiredType = tokenTypeSendCode;
    }

    @Override
    public void onTokenConfirmCodeSignatureExpired() {
        String tokenStr = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(tokenStr);
        tokenExpiredType = tokenTypeConfirmCode;
    }

    @Override
    public void onPreLoadSaldoKredimu() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessSaldoKreditmu(SaldoKreditmuResponse data) {
        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Status Kreditmu");
        alert.setMessage(data.getSaldoKreditmuObjt().getMessage());
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (form.equals("New") || form.equals("Draft")) {
                    setValueSaveAsDraft("Submit");
//                    mCheckEfNumberPresenter.efNumber(token, strEfNumber, masterFormPengajuan);
                    mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, "1", "");
                }
                if (form.equals("Edit")) {
                    setValueSaveAsDraft("Submit");
                    mSinkronisasiKendaraanPresenter.SyncKendaraan(token, map, "2", appId);
                }
                /*saveData("isAssignEdit"); */         /*submit assign edit*/
               /* if (form.equals("Draft") && draftEdit.equals("draft_edit"))
                    updateData("edit");*/
            }
        });
        alert.show();
    }

    @Override
    public void onFailedSaldoKreditmu(String message) {
        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaldoKreditmuTokenExpired() {
        String cekToken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
        tokenExpiredType = tokenTypeSaldo;
    }

    @Subscribe
    public void confirmCodeSignature(ConfirmCodeSignature e) {
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
        assets();
        countSignature += 1;
    }

    public void imageCompressor(File fileUri, int requestCode) {
//        Uri tempUri = imageUri;
//        File fileUri = new File(Util.getRealPathFromURI(this, tempUri));
//        long fileLength = fileUri.length() / 1024;
//        Util.checkSizeImage(requestCode, tempUri);
////        Log.i("Photo_Bitmap", String.valueOf(e.getBitmap()));
//        Log.i("Photo_Uri", String.valueOf(tempUri));
//        Log.i("Photo_File", String.valueOf(fileUri));

        switch (requestCode) {
            case TAKE_CAMERA_PENGAJUAN_1:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture1);
                imgDeletePicture1.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(0, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_1", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_2:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture2);
                imgDeletePicture2.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(1, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_2", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_3:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture3);
                imgDeletePicture3.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(2, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_3", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_4:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture4);
                imgDeletePicture4.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(3, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_4", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_5:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture5);
                imgDeletePicture5.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(4, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_5", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_6:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture6);
                imgDeletePicture6.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(5, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_6", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_7:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture7);
                imgDeletePicture7.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(6, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("TAKE_CAMERA_PENGAJUAN_7", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_8:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture8);
                imgDeletePicture8.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(7, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_8", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_9:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture9);
                imgDeletePicture9.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(8, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_9", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_10:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture10);
                imgDeletePicture10.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(9, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_10", String.valueOf(fileLength));
                break;
                case TAKE_CAMERA_PENGAJUAN_11:
                Glide.with(this).load(fileUri).centerCrop().into(imgTakePicture11);
                imgDeletePicture11.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(10, fileUri);
                Util.saveGallery(this, fileUri);
//                Log.d("size_img_10", String.valueOf(fileLength));
                break;
        }
        hideFormValidasiAwal();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCropPhotoEvent(CropPhotoEvent e) {
        Uri tempUri = Util.getImageUri(this, e.getBitmap());
        File fileUri = new File(Util.getRealPathFromURI(this, tempUri));
        long fileLength = fileUri.length() / 1024;
        Util.checkSizeImage(e.getTipeImg(), tempUri);
        Log.i("Photo_Bitmap", String.valueOf(e.getBitmap()));
        Log.i("Photo_Uri", String.valueOf(tempUri));
        Log.i("Photo_File", String.valueOf(fileUri));

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
        }
        hideFormValidasiAwal();
    }


    public void assets() {
        BodyToken bodyToken = new BodyToken();
        List<AssetToken> assetTokenList = new ArrayList<>();


        for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
            View view = llParentTotalAsset.getChildAt(i);
            AssetToken assetToken = new AssetToken();

            NiceAutoCompleteTextView actNamaBarang = (NiceAutoCompleteTextView) view.findViewById(R.id.act_nama_barang_asset);
            EditText actTypeKendaraan = (EditText) view.findViewById(R.id.edt_type_asset);

            assetToken.setAssetKode((String) actNamaBarang.getTag() == null ? "" : (String) actNamaBarang.getTag());
            assetToken.setType(actTypeKendaraan.getText().toString() == null ? "" : actTypeKendaraan.getText().toString());
            assetTokenList.add(assetToken);
        }

        bodyToken.setMobileSubmision(mobileSubmissionKey);
        bodyToken.setType("signature");
        bodyToken.setHandphone(edtHandphonePribadi.getText().toString());
        bodyToken.setFullName(edtNamaKtpPribadi.getText().toString());
        bodyToken.setInstallmentAmount(edtAngsuranPerbulan.getText().toString() == null ? "" : edtAngsuranPerbulan.getText().toString().replace(",", ""));
        bodyToken.setApplicationPid(uuid);
        bodyToken.setAssetTokenList(assetTokenList);
        bodyToken.setProcessType(isAssignEdit ? "edit" : "create");

        mCodeSignaturePresenter.sendCodeSignature(token, bodyToken);
    }

    private void showAllSection() {
        if (form.equals("New") || form.equals("Draft"))
            rlHeaderMasterHeader.setVisibility(View.VISIBLE);
        rlHeaderDataPribadi.setVisibility(View.VISIBLE);
        rlHeaderDataPasangan.setVisibility(View.VISIBLE);
        rlHeaderAlamatPemohon.setVisibility(View.VISIBLE);
        rlHeaderInformasiKerabat.setVisibility(View.VISIBLE);
        rlHeaderDataPekerjaan.setVisibility(View.VISIBLE);
//        rlHeaderDataKartuKredit.setVisibility(View.VISIBLE);
//        rlHeaderDataKartuMembership.setVisibility(View.VISIBLE);
        rlHeaderDetailProduct.setVisibility(View.VISIBLE);
        rlHeaderDetailAsset.setVisibility(View.VISIBLE);
        rlHeaderDataAsuransi.setVisibility(View.VISIBLE);
        rlHeaderDataPerhitungan.setVisibility(View.VISIBLE);
        rlHeaderKeterangan.setVisibility(View.VISIBLE);
        rlHeaderImagePengajuan.setVisibility(View.VISIBLE);
        rlHeaderPersetujuan.setVisibility(View.VISIBLE);
        rlHeaderRecomendation.setVisibility(View.VISIBLE);
        rlHeaderInfromasiPenawaran.setVisibility(View.VISIBLE);
        rlHeaderPersetujuanTambahan.setVisibility(View.VISIBLE);
    }

    private void hideAllContent(View view) {
        for (int i = 0; i < viewContents.size(); i++)
            if (view.getId() != viewContents.get(i).getId())
                viewContents.get(i).setVisibility(View.GONE);
    }

    private void showHideContent(RelativeLayout v) {
        hideAllContent(v); // hide content layout
        if (v.getVisibility() == View.GONE) {
            v.setVisibility(View.VISIBLE);
            v.getChildAt(0).requestFocus();
        } else {
            v.setVisibility(View.GONE);
        }
    }

    //TODO: untuk save as draft online,  new REALM REBORN
    private void setValueSaveAsDraft(String mode) {
        map = new HashMap<>();
        if (!form.equalsIgnoreCase("New")) {
//            jika pengajuan baru dan langsung klik submit maka tidak akan kirim ApplicationID (save draft)
            map.put("Application[ApplicationID]", Util.toTextRequestBody(appId));
        }

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
        map.put("Application[AdvanceCustomerAgreement]", Util.toTextRequestBody(statusSmsInformasi));
        map.put("Application[UsedOTP]", Util.toTextRequestBody(usedOTP));

//        KOP
        map.put("KorpFormulir[CreditStatus]", Util.toTextRequestBody(edtStatusKreditmu.getText().toString()));
        map.put("KorpFormulir[FinancingPurpose]", Util.toTextRequestBody(strStatusPengajuan));
        map.put("KorpFormulir[TypeOfFinancing]", Util.toTextRequestBody(spnJenisTujuanPembiyaan.getSelectedItem().toString())); /*jenis pembiayaan*/
        map.put("KorpFormulir[TypeOfFinancingID]", Util.toTextRequestBody("5")); /*jenis pembiayaan*/

        map.put("KorpFormulir[SalesMethod]", Util.toTextRequestBody(spnMetodePenjualan.getSelectedItem().toString())); /*metode penjualan*/
        map.put("KorpFormulir[SalesMethodID]", Util.toTextRequestBody(metodePenjualanValue[spnMetodePenjualan.getSelectedItemPosition()])); /*metode penjualan*/


//        DATA PRIBADI

        map.put("PersonalData[FullName]", Util.toTextRequestBody(edtNamaPribadi.getText() == null ? "" : edtNamaPribadi.getText().toString()));
        map.put("PersonalData[LegalName]", Util.toTextRequestBody(edtNamaKtpPribadi.getText() == null ? "" : edtNamaKtpPribadi.getText().toString()));
        map.put("PersonalData[KKNo]", Util.toTextRequestBody(edtPribadiNoKK.getText() == null ? "" : edtPribadiNoKK.getText().toString()));
        map.put("PersonalData[IDNumber]", Util.toTextRequestBody(edtNoKtpPribadi.getText() == null ? "" : edtNoKtpPribadi.getText().toString()));
        map.put("PersonalData[IDTypeIssuedDate]", Util.toTextRequestBody(edtTerbitKtpPribadi.getText() == null ? "" : edtTerbitKtpPribadi.getText().toString()));
        map.put("PersonalData[BirthPlace]", Util.toTextRequestBody(edtTempatLahirPribadi.getText() == null ? "" : edtTempatLahirPribadi.getText().toString()));
        map.put("PersonalData[BirthDate]", Util.toTextRequestBody(edtTanggalLahirPribadi.getText() == null ? "" : edtTanggalLahirPribadi.getText().toString()));
        map.put("PersonalData[Gender]", Util.toTextRequestBody(genderValue[spnGenderPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[MaritalStatus]", Util.toTextRequestBody(statusPernikahanValue[spnPernikahanPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[MaritalStatusID]", Util.toTextRequestBody(statusPernikahanValue[spnPernikahanPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[NumOfDependence]", Util.toTextRequestBody(edtJumlahTanggunganPribadi.getText() == null ? "" : edtJumlahTanggunganPribadi.getText().toString()));
        map.put("PersonalData[HomeStatus]", Util.toTextRequestBody(spnStatusRumahPribadi.getSelectedItem().toString()));
        map.put("PersonalData[HomeStatusID]", Util.toTextRequestBody(statusRumahValue[spnStatusRumahPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[StaySinceYear]", Util.toTextRequestBody(edtTinggalSejakTahunPribadi.getText() == null ? "" : edtTinggalSejakTahunPribadi.getText().toString()));
        map.put("PersonalData[StaySinceMonth]", Util.toTextRequestBody(edtTinggalSejakBulanPribadi.getText() == null ? "" : edtTinggalSejakBulanPribadi.getText().toString()));
        map.put("PersonalData[Education]", Util.toTextRequestBody(spnPendidikanPribadi.getSelectedItem().toString()));
        map.put("PersonalData[EducationID]", Util.toTextRequestBody(pendidikanValue[spnPendidikanPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[Religion]", Util.toTextRequestBody(agamaValue[spnAgamaPribadi.getSelectedItemPosition()]));
        // map.put("PersonalData[ReligionID]", Util.toTextRequestBody(agamaValue[spnAgamaPribadi.getSelectedItemPosition()]));
        map.put("PersonalData[PersonalNPWP]", Util.toTextRequestBody(edtNoNpwpDetail.getText() == null ? "" : String.format("%s%s", "", edtNoNpwpDetail.getRawText())));
        map.put("PersonalData[Email]", Util.toTextRequestBody(edtEmailPribadi.getText() == null ? "" : edtEmailPribadi.getText().toString()));
        map.put("PersonalData[MobilePhone]", Util.toTextRequestBody(edtHandphonePribadi.getText() == null ? "" : edtHandphonePribadi.getText().toString()));
        map.put("PersonalData[SurgateMotherName]", Util.toTextRequestBody(edtNamaIbuPribadi.getText() == null ? "" : edtNamaIbuPribadi.getText().toString()));
        map.put("PersonalData[Nationality]", Util.toTextRequestBody("WNI"));

        //        ALAMAT TINGGAL DAN KTP
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

        //EMERGENCY CONTACT
        map.put("EmergencyContact[Name]", Util.toTextRequestBody(edtNamaKerabat.getText().toString()));
        map.put("EmergencyContact[Relationship]", Util.toTextRequestBody(spnHubunganKerabat.getSelectedItem().toString()));
        if (!edtAlamatKerabat.getText().toString().isEmpty() || !strCityAlamatKerabat.isEmpty()) {
            map.put("EmergencyContact[Address]", Util.toTextRequestBody(edtAlamatKerabat.getText().toString() + "|" + strCityAlamatKerabat));
        } else {
            map.put("EmergencyContact[Address]", Util.toTextRequestBody(""));
        }

        map.put("EmergencyContact[RT]", Util.toTextRequestBody(edtRtKerabat.getText().toString()));
        map.put("EmergencyContact[RW]", Util.toTextRequestBody(edtRwKerabat.getText().toString()));
        map.put("EmergencyContact[VillageCode]", Util.toTextRequestBody("1221"));
        map.put("EmergencyContact[HomePhoneArea]", Util.toTextRequestBody(edtAreaPhoneRumahKerabat.getText().toString()));
        map.put("EmergencyContact[HomePhone]", Util.toTextRequestBody(edtPhoneRumahKerabat.getText().toString()));
        map.put("EmergencyContact[OfficePhoneArea]", Util.toTextRequestBody(edtAreaPhoneKantorKerabat.getText().toString()));
        map.put("EmergencyContact[OfficePhone]", Util.toTextRequestBody(edtPhoneKantorKerabat.getText().toString()));
        map.put("EmergencyContact[MobilePhone]", Util.toTextRequestBody(edtHpKerabat.getText().toString()));

        map.put("FamilyData[0][SeqNo]", Util.toTextRequestBody("1"));
        map.put("FamilyData[0][Id]", Util.toTextRequestBody(idFamily));
        map.put("FamilyData[0][Name]", Util.toTextRequestBody((edtNamaPasangan.getText() == null) ? "" : edtNamaPasangan.getText().toString()));
        map.put("FamilyData[0][IDNumber]", Util.toTextRequestBody((edtNoKtpPasangan.getText() == null) ? "" : edtNoKtpPasangan.getText().toString()));
        map.put("FamilyData[0][BirthPlace]", Util.toTextRequestBody((edtTempatLahirPasangan.getText() == null) ? "" : edtTempatLahirPasangan.getText().toString()));
        map.put("FamilyData[0][BirthDate]", Util.toTextRequestBody((edtTanggalLahirPasangan.getText() == null) ? "" : edtTanggalLahirPasangan.getText().toString()));
        map.put("FamilyData[0][Handphone]", Util.toTextRequestBody((edtNoHpPasangan.getText() == null) ? "" : edtNoHpPasangan.getText().toString()));

        //PERUSAHAAN
        map.put("Company[Name]", Util.toTextRequestBody(edtNamaPerusahaan.getText().toString()));
        if (!edtAlamatPerusahaan.getText().toString().isEmpty() || strCityAlamatPekerjaan.isEmpty()) {
            map.put("Company[Address]", Util.toTextRequestBody(edtAlamatPerusahaan.getText().toString() + "|" + strCityAlamatPekerjaan));
        } else
            map.put("Company[Address]", Util.toTextRequestBody(""));
        map.put("Company[RT]", Util.toTextRequestBody(edtRtPerusahaan.getText().toString()));
        map.put("Company[RW]", Util.toTextRequestBody(edtRwPerusahaan.getText().toString()));
        map.put("Company[AreaPhone]", Util.toTextRequestBody(edtAreaPhonePerusahaan.getText().toString()));
        map.put("Company[Phone]", Util.toTextRequestBody(edtPhonePerusahaan.getText().toString()));
        map.put("Company[EmploymentSinceYear]", Util.toTextRequestBody(edtBekerjaSejakPerusahaan.getText().toString()));
        map.put("Company[ProfessionID]", Util.toTextRequestBody((professionKode == null) ? "" : professionKode));
//            map.put("Company[Profession]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getProfesi()));
        map.put("Company[JobType]", Util.toTextRequestBody((jobTypeKode == null) ? "" : jobTypeKode));
//            map.put("Company[JobType]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getTipePekerjaan()));
        map.put("Company[JobPosition]", Util.toTextRequestBody((jobPositionKode == null) ? "" : jobPositionKode));
//            map.put("Company[JobPosition]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPosisiPekerjaan()));
        map.put("Company[IndustryTypeID]", Util.toTextRequestBody((industriKode == null) ? "" : industriKode));
//            map.put("Company[IndustryTypeID]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getIndustri()));
        map.put("Company[MonthlyFixedIncome]", Util.toTextRequestBody((edtPenghasilanTetapPerusahaan.getText() == null) ? "" : edtPenghasilanTetapPerusahaan.getText().toString().replace(",", "")));
        map.put("Company[MonthlyVariableIncome]", Util.toTextRequestBody((edtPenghasilanLainPerusahaan.getText() == null) ? "" : edtPenghasilanLainPerusahaan.getText().toString().replace(",", "")));
        map.put("Company[SpouseIncome]", Util.toTextRequestBody((edtPenghasilanPasanganPerusahaan.getText() == null) ? "" : edtPenghasilanPasanganPerusahaan.getText().toString().replace(",", "")));
        map.put("Company[LivingCostAmount]", Util.toTextRequestBody((edtBiayaHidupPerusahaan.getText() == null) ? "" : edtBiayaHidupPerusahaan.getText().toString().replace(",", "")));
        map.put("Company[VillageCode]", Util.toTextRequestBody("1221"));
        map.put("Company[IsAffiliateWithPP]", Util.toTextRequestBody("0"));

        //KARTU KREDIT
        map.put("DataCreditCard[BankName]", Util.toTextRequestBody(edtNamaBankKartuKredit.getText().toString()));
        map.put("DataCreditCard[IDCard]", Util.toTextRequestBody(edtNoKartuKredit.getText().toString()));
        map.put("DataCreditCard[CardType]", Util.toTextRequestBody(edtJenisKartuKredit.getText().toString()));
        map.put("DataCreditCard[CardLimit]", Util.toTextRequestBody(edtLimitKartuKredit.getText().toString().replace(",", "")));
        map.put("DataCreditCard[MembershipOldYear]", Util.toTextRequestBody(edtTahunKadaluarsaKartuKredit.getText().toString()));
        map.put("DataCreditCard[MembershipOldMonth]", Util.toTextRequestBody(edtBulanKadaluarsaKartuKredit.getText().toString()));

        // KARTU MEMBERSHIP
        map.put("MembershipCard[IDMember]", Util.toTextRequestBody(edtNoMembership.getText().toString()));
        map.put("MembershipCard[EffectiveDate]", Util.toTextRequestBody(edtTanggalEfektif.getText().toString()));
        map.put("MembershipCard[ExpiredDate]", Util.toTextRequestBody(edtTanggalExpired.getText().toString()));

        if (mode.equals("Submit")) {
            //ASSETMASTER
            map.put("AssetMaster[SupplierID]", Util.toTextRequestBody(supplierKode));
            map.put("AssetMaster[RefferalCode]", Util.toTextRequestBody((actReferalCode.getText() == null) ? "" : actReferalCode.getText().toString()));
            map.put("AssetMaster[SupplierName]", Util.toTextRequestBody(actSupplierAsset.getText().toString()));
            map.put("AssetMaster[SalesmanID]", Util.toTextRequestBody(marketingKode));
            map.put("AssetMaster[SalesmanName]", Util.toTextRequestBody(actMarketingSupplierAsset.getText().toString()));
            map.put("DetailProduct[ProductID]", Util.toTextRequestBody(productId));
            map.put("DetailProduct[ProductOfferingID]", Util.toTextRequestBody(productOfferingId));
            map.put("DetailProduct[ProductOfferingName]", Util.toTextRequestBody(actProductOfferingProduct.getText().toString()));
            map.put("DetailProduct[POS]", Util.toTextRequestBody(posKode));
//            map.put("DetailProduct[POS]", Util.toTextRequestBody(tblDetailProducts.get(i).getPos()));
            map.put("DetailProduct[NumOfAssetUnit]", Util.toTextRequestBody(String.valueOf(llParentTotalAsset.getChildCount())));
            map.put("AssetMaster[SupplierBankAccountID]", Util.toTextRequestBody(""));

            // ASURANSI
            map.put("Insurance[CoverageType]", Util.toTextRequestBody(manualAgunan));
            String premiManualString;
            if (rbManualYes.isChecked()) premiManualString = "1";
            else premiManualString = "0";
            map.put("Insurance[IsPremiManual]", Util.toTextRequestBody(premiManualString));
            map.put("Insurance[IsPersonalAccident]", Util.toTextRequestBody("0"));

            //DATA PERHITUNGAN
            map.put("DetailProduct[Tenor]", Util.toTextRequestBody(String.valueOf(tenor)));
            map.put("DetailFinancing[FlatRate]", Util.toTextRequestBody(edtFlatRatePerhitungan.getText().toString()));
            map.put("DetailFinancing[AdminFee]", Util.toTextRequestBody(String.valueOf(admin_fee)));
            map.put("DetailFinancing[OtherFee]", Util.toTextRequestBody(edtBiayaLainnya.getText().toString().replace(",", "")));
//            map.put("DetailFinancing[TypePayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTipePembayaran()));
            String refund = edtRefundSubsidiPerhitungan.getText().toString().replace(",","");
            map.put("DetailFinancing[SubsidyRefund]", Util.toTextRequestBody(refund));
            map.put("DetailFinancing[DiscountRateTimes]", Util.toTextRequestBody("".equalsIgnoreCase(edtBebasBungaPerhitungan.getText().toString()) ? "0" : edtBebasBungaPerhitungan.getText().toString()));
            map.put("DetailFinancing[PurchasePrice]", Util.toTextRequestBody("".equalsIgnoreCase(edtPurchasePricePerhitungan.getText().toString()) ? "0" : edtPurchasePricePerhitungan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[Discount]", Util.toTextRequestBody("".equalsIgnoreCase(edtDiscountPerhitungan.getText().toString()) ? "0" : edtDiscountPerhitungan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[DownPayment]", Util.toTextRequestBody("".equalsIgnoreCase(edtDpPerhitungan.getText().toString()) ? "0" : edtDpPerhitungan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[DownPaymentPercentage]", Util.toTextRequestBody(strDPPercentage == null ? "" : strDPPercentage));
            map.put("Insurance[PremiumAmountToCustomer]", Util.toTextRequestBody("".equalsIgnoreCase(edtPremiAsuransi.getText().toString()) ? "0" : edtPremiAsuransi.getText().toString().replace(",", "")));
            map.put("DetailFinancing[NTF]", Util.toTextRequestBody("".equalsIgnoreCase(edtNtfPerhitungan.getText().toString()) ? "0" : edtNtfPerhitungan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[TotalFinancing]", Util.toTextRequestBody("".equalsIgnoreCase(edtJumlahPembiayaan.getText().toString()) ? "0" : edtJumlahPembiayaan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[InterestFinancing]", Util.toTextRequestBody("".equalsIgnoreCase(edtTotalBungaPembiayaan.getText().toString()) ? "0" : edtTotalBungaPembiayaan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[MonthFinancingInterest]", Util.toTextRequestBody("".equalsIgnoreCase(edtBungaPembiayaanBulan.getText().toString()) ? "0" : edtBungaPembiayaanBulan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[TotalLoan]", Util.toTextRequestBody("".equalsIgnoreCase(edtTotalPinjaman.getText().toString()) ? "0" : edtTotalPinjaman.getText().toString().replace(",", "")));

            map.put("DetailFinancing[InstallmentAmount]", Util.toTextRequestBody("".equalsIgnoreCase(edtAngsuranPerbulan.getText().toString()) ? "0" : edtAngsuranPerbulan.getText().toString().replace(",", "")));
            map.put("DetailFinancing[FirstPayment]", Util.toTextRequestBody("".equalsIgnoreCase(edtPembayaranPertama.getText().toString()) ? "0" : edtPembayaranPertama.getText().toString().replace(",", "")));
            map.put("DetailFinancing[InterestFreeDealerPayment]", Util.toTextRequestBody("".equalsIgnoreCase(edtPembayaranDelaer.getText().toString()) ? "0" : edtPembayaranDelaer.getText().toString().replace(",", "")));
            map.put("DetailFinancing[FirstInstallment]", Util.toTextRequestBody(fsInstallment));
            map.put("DetailFinancing[EffectiveRate]", Util.toTextRequestBody("".equalsIgnoreCase(edtEffectiveRatePerhitungan.getText().toString()) ? "0" : edtEffectiveRatePerhitungan.getText().toString()));

            map.put("KorpFormulir[Keterangan]", Util.toTextRequestBody(edtKeterangan.getText().toString()));

            map.put("Application[IsJabarRecomendation]", Util.toTextRequestBody("0"));
            map.put("Application[ReasonRecomendationId]", Util.toTextRequestBody(recomendationId));
            map.put("Application[ReasonRecomendationNotes]", Util.toTextRequestBody((edtDescRecomendation.getText() == null) ? "" : edtDescRecomendation.getText().toString()));


            map.put("Location[ValidateAction]", Util.toTextRequestBody((validateAction.getText() == null) ? "" : validateAction.getText().toString()));
            map.put("Location[ValidateLongitude]", Util.toTextRequestBody((validateLongitude.getText() == null) ? "" : validateLongitude.getText().toString()));
            map.put("Location[ValidateLatitude]", Util.toTextRequestBody((validateLatitude.getText() == null) ? "" : validateLatitude.getText().toString()));
            map.put("Location[TakeKtpAction]", Util.toTextRequestBody((takeKtpAction.getText() == null) ? "" : takeKtpAction.getText().toString()));
            map.put("Location[TakeKtpLongitude]", Util.toTextRequestBody((takeKtpLongitude.getText() == null) ? "" : takeKtpLongitude.getText().toString()));
            map.put("Location[TakeKtpLatitude]", Util.toTextRequestBody((takeKtpLatitude.getText() == null) ? "" : takeKtpLatitude.getText().toString()));
            map.put("Location[TakeCustomerAction]", Util.toTextRequestBody((takeCustomerAction.getText() == null) ? "" : takeCustomerAction.getText().toString()));
            map.put("Location[TakeCustomerLongitude]", Util.toTextRequestBody((takeCustomerLongitude.getText() == null) ? "" : takeCustomerLongitude.getText().toString()));
            map.put("Location[TakeCustomerLatitude]", Util.toTextRequestBody((takeCustomerLatitude.getText() == null) ? "" : takeCustomerLatitude.getText().toString()));
            map.put("Location[TakePaycheckAction]", Util.toTextRequestBody((takePaycheckAction.getText() == null) ? "" : takePaycheckAction.getText().toString()));
            map.put("Location[TakePaycheckLongitude]", Util.toTextRequestBody((takePaycheckLongitude.getText() == null) ? "" : takePaycheckLongitude.getText().toString()));
            map.put("Location[TakePaycheckLatitude]", Util.toTextRequestBody((takePaycheckLatitude.getText() == null) ? "" : takePaycheckLatitude.getText().toString()));
            map.put("Location[TakeAdditionalDocumentsAction]", Util.toTextRequestBody((takeAdditionalDocumentsAction.getText() == null) ? "" : takeAdditionalDocumentsAction.getText().toString()));
            map.put("Location[TakeAdditionalDocumentsLongitude]", Util.toTextRequestBody((takeAdditionalDocumentsLongitude.getText() == null) ? "" : takeAdditionalDocumentsLongitude.getText().toString()));
            map.put("Location[TakeAdditionalDocumentsLatitude]", Util.toTextRequestBody((takeAdditionalDocumentsLatitude.getText() == null) ? "" : takeAdditionalDocumentsLatitude.getText().toString()));
            map.put("Location[TakeSignatureAction]", Util.toTextRequestBody((takeSignatureAction.getText() == null) ? "" : takeSignatureAction.getText().toString()));
            map.put("Location[TakeSignatureLongitude]", Util.toTextRequestBody((takeSignatureLongitude.getText() == null) ? "" : takeSignatureLongitude.getText().toString()));
            map.put("Location[TakeSignatureLatitude]", Util.toTextRequestBody((takeSignatureLatitude.getText() == null) ? "" : takeSignatureLatitude.getText().toString()));
            map.put("Location[SubmitAction]", Util.toTextRequestBody((submitAction.getText() == null) ? "" : submitAction.getText().toString()));
            map.put("Location[SubmitLongitude]", Util.toTextRequestBody((submitLongitude.getText() == null) ? "" : submitLongitude.getText().toString()));
            map.put("Location[SubmitLatitude]", Util.toTextRequestBody((submitLatitude.getText() == null) ? "" : submitLatitude.getText().toString()));
            map.put("Location[SyncAction]", Util.toTextRequestBody(""));
            map.put("Location[SyncLongitude]", Util.toTextRequestBody(""));
            map.put("Location[SyncLatitude]", Util.toTextRequestBody(""));

            //TANDA TANGAN SUAMI ISTRI
            map.put("Signature[Applicant]", Util.toTextRequestBody(Util.bitmapToBase64(bitmapTtdPemohon)));
            map.put("Signature[ApplicantHusbandWife]", Util.toTextRequestBody(bitmapTtdPasangan == null ? "" : Util.bitmapToBase64(bitmapTtdPasangan)));

            //ASSET INPUT
            if (form.equalsIgnoreCase("Edit")) {
                List<AssetElektronik> assetElektronikList = new ArrayList<>();
                for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
                    View view = llParentTotalAsset.getChildAt(i);

                    NiceAutoCompleteTextView actNamaBarang = view.findViewById(R.id.act_nama_barang_asset);
                    EditText edtPrice = view.findViewById(R.id.edt_price_asset);
                    EditText edtDp = view.findViewById(R.id.edt_dp_asset);
                    EditText edtDiscount = view.findViewById(R.id.edt_discount_asset);
                    EditText edtTypeAset = view.findViewById(R.id.edt_type_asset);
                    AssetElektronik assetElektronik = new AssetElektronik();

                    assetElektronik.setMasterFormPengajuan(masterFormPengajuan);
                    assetElektronik.setKodeBarang((String) actNamaBarang.getTag());/*assetCode*/
                    assetElektronik.setNamaBarang(actNamaBarang.getText().toString());
                    assetElektronik.setCategory(categoriIdList.get(i));
                    assetElektronik.setType(edtTypeAset.getText().toString());
                    assetElektronik.setPrice((edtPrice.getText() == null) ? "" : edtPrice.getText().toString().replace(",", ""));
                    assetElektronik.setDp((edtDp.getText() == null) ? "" : edtDp.getText().toString().replace(",", ""));
                    assetElektronik.setDiscount((edtDiscount.getText() == null) ? "" : edtDiscount.getText().toString().replace(",", ""));
                    assetElektronikList.add(assetElektronik);

                    map.put("Asset[" + i + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(i + 1)));
                    map.put("Asset[" + i + "][AssetCode]", Util.toTextRequestBody(assetElektronikList.get(i).getKodeBarang()));
                    map.put("Asset[" + i + "][Type]", Util.toTextRequestBody(assetElektronikList.get(i).getType()));
                    map.put("Asset[" + i + "][OTRPrice]", Util.toTextRequestBody(assetElektronikList.get(i).getPrice()));
                    map.put("Asset[" + i + "][DPAmount]", Util.toTextRequestBody(assetElektronikList.get(i).getDp().isEmpty() ? "0" : assetElektronikList.get(i).getDp()));
                    map.put("Asset[" + i + "][Discount]", Util.toTextRequestBody(assetElektronikList.get(i).getDiscount().isEmpty() ? "0" : assetElektronikList.get(i).getDiscount()));
                    map.put("Asset[" + i + "][Description]", Util.toTextRequestBody(assetElektronikList.get(i).getNamaBarang()));
                }
            } else {
                List<AssetElektronik> assetElektronikList = new ArrayList<>();
                for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
                    View view = llParentTotalAsset.getChildAt(i);

                    NiceAutoCompleteTextView actNamaBarang = view.findViewById(R.id.act_nama_barang_asset);
                    EditText edtPrice = view.findViewById(R.id.edt_price_asset);
                    EditText edtDp = view.findViewById(R.id.edt_dp_asset);
                    EditText edtDiscount = view.findViewById(R.id.edt_discount_asset);
                    EditText edtTypeAset = view.findViewById(R.id.edt_type_asset);
                    AssetElektronik assetElektronik = new AssetElektronik();

                    assetElektronik.setMasterFormPengajuan(masterFormPengajuan);
                    assetElektronik.setKodeBarang((String) actNamaBarang.getTag());/*assetCode*/
                    assetElektronik.setNamaBarang(actNamaBarang.getText().toString());
                    assetElektronik.setCategory(categoryId);
                    assetElektronik.setType(edtTypeAset.getText().toString());
                    assetElektronik.setPrice((edtPrice.getText() == null) ? "" : edtPrice.getText().toString().replace(",", ""));
                    assetElektronik.setDp((edtDp.getText() == null) ? "" : edtDp.getText().toString().replace(",", ""));
                    assetElektronik.setDiscount((edtDiscount.getText() == null) ? "" : edtDiscount.getText().toString().replace(",", ""));
                    assetElektronikList.add(assetElektronik);

                    map.put("Asset[" + i + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(i + 1)));
                    map.put("Asset[" + i + "][Category]", Util.toTextRequestBody(assetElektronikList.get(i).getCategory()));
                    map.put("Asset[" + i + "][AssetCode]", Util.toTextRequestBody(assetElektronikList.get(i).getKodeBarang()));
                    map.put("Asset[" + i + "][Type]", Util.toTextRequestBody(assetElektronikList.get(i).getType()));
                    map.put("Asset[" + i + "][OTRPrice]", Util.toTextRequestBody(assetElektronikList.get(i).getPrice()));
                    map.put("Asset[" + i + "][DPAmount]", Util.toTextRequestBody(assetElektronikList.get(i).getDp().isEmpty() ? "0" : assetElektronikList.get(i).getDp()));
                    map.put("Asset[" + i + "][Discount]", Util.toTextRequestBody(assetElektronikList.get(i).getDiscount().isEmpty() ? "0" : assetElektronikList.get(i).getDiscount()));
                    map.put("Asset[" + i + "][Description]", Util.toTextRequestBody(assetElektronikList.get(i).getNamaBarang()));
                }
            }
        }

    }

    private void saveData(String formType) {
        MasterFormPengajuan masterFormPengajuan = new MasterFormPengajuan();
        this.masterFormPengajuan = masterFormPengajuan;
        if (form.equals("Edit")) {
            if (countImage + 1 < numberOfImages) {
                Toast.makeText(getContext(), "Load Data Attachment Belum Selesai", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        strJumlahAsset = String.valueOf(llParentTotalAsset.getChildCount());
        if (isMaskingOn) masterFormPengajuan.setHaveMasking(1);
        else masterFormPengajuan.setHaveMasking(0);
//        KorpFormulir valiabel back end
        masterFormPengajuan.setApplicationId((appId == null) ? "" : appId);
        masterFormPengajuan.setTipeSaveData(formType);
        masterFormPengajuan.setBlacklistDate((blackListDate == null) ? "" : blackListDate);
        masterFormPengajuan.setUuid((uuid == null) ? "" : uuid);
        masterFormPengajuan.setCustomerIdConfins((customerIdConfins == null) ? "" : customerIdConfins);
        masterFormPengajuan.setIdKpm((applicationIdKpm == null) ? "" : applicationIdKpm);
        masterFormPengajuan.setTipeDataOffering((typeDataOffering == null) ? "" : typeDataOffering);
        masterFormPengajuan.setBranch((aoBranch == null) ? "" : aoBranch);
        masterFormPengajuan.setMasterBranch((branchMaster == null) ? "" : branchMaster);
        masterFormPengajuan.setTipePengajuan((pengajuanType == null) ? "" : pengajuanType);
        masterFormPengajuan.setMobileSubmissionKey((mobileSubmissionKey == null) ? "" : mobileSubmissionKey);
        masterFormPengajuan.setEfNumber((strEfNumber == null) ? "" : strEfNumber);
        masterFormPengajuan.setCreatedAt(Util.listPengajuanTimeFormat(new DateTime()));

        try {
            databaseService.getMasterFormPengajuanDao().create(masterFormPengajuan);
        } catch (SQLException mfp) {
            if (BuildConfig.DEBUG) Log.d("MasterFormPengajuan", String.valueOf(mfp));
            Crashlytics.logException(mfp);
        }

//        KOP
        TblKop tblKop = new TblKop();
        tblKop.setMasterFormPengajuan(masterFormPengajuan);
        tblKop.setMetodePenjualan(metodePenjualanValue[spnMetodePenjualan.getSelectedItemPosition()]);
        tblKop.setMetodePenjualanPosition(spnMetodePenjualan.getSelectedItemPosition());
        tblKop.setStatusCustomer((strStatusPengajuan == null) ? "" : strStatusPengajuan);
        tblKop.setJenisPembiayaan(tujuanPembiayaan);
        try {
            databaseService.getTblKopDao().create(tblKop);
        } catch (SQLException msgTblKop) {
            if (BuildConfig.DEBUG) Log.e("TblKop", String.valueOf(msgTblKop));
            Crashlytics.log(String.valueOf(msgTblKop));
        }

//        DATA PRIBADI
        TblDataPribadi tblDataPribadi = new TblDataPribadi();
        tblDataPribadi.setMasterFormPengajuan(masterFormPengajuan);
        tblDataPribadi.setNoKtp((edtNoKtpPribadi.getText() == null) ? "" : edtNoKtpPribadi.getText().toString());
        if (!formType.equals("draft_baru"))
            tblDataPribadi.setNoNpwp((edtNoNpwpDetail.getText() == null) ? "" : String.format("%s%s", "", edtNoNpwpDetail.getRawText()));
        tblDataPribadi.setStatusPernikahan(statusPernikahanValue[spnPernikahanPribadi.getSelectedItemPosition()]);
        tblDataPribadi.setStatusPernikahanPosition(spnPernikahanPribadi.getSelectedItemPosition());
        tblDataPribadi.setTanggalLahir((edtTanggalLahirPribadi.getText() == null) ? "" : edtTanggalLahirPribadi.getText().toString());
        tblDataPribadi.setNomorHandphone((edtHandphonePribadi.getText() == null) ? "" : edtHandphonePribadi.getText().toString());
        tblDataPribadi.setNamaLengkap((edtNamaPribadi.getText() == null) ? "" : edtNamaPribadi.getText().toString());
        tblDataPribadi.setNamaKtp((edtNamaKtpPribadi.getText() == null) ? "" : edtNamaKtpPribadi.getText().toString());
        tblDataPribadi.setTanngalTerbitKtp((edtTerbitKtpPribadi.getText() == null) ? "" : edtTerbitKtpPribadi.getText().toString());
        tblDataPribadi.setJenisKelamin(genderValue[spnGenderPribadi.getSelectedItemPosition()]);
        tblDataPribadi.setJenisKelaminPosition(spnGenderPribadi.getSelectedItemPosition());
        tblDataPribadi.setTempatLahir((edtTempatLahirPribadi.getText() == null) ? "" : edtTempatLahirPribadi.getText().toString());
        tblDataPribadi.setNamaIbuKandung((edtNamaIbuPribadi.getText() == null) ? "" : edtNamaIbuPribadi.getText().toString());
        tblDataPribadi.setStatusPendidikan(pendidikanValue[spnPendidikanPribadi.getSelectedItemPosition()]);
        tblDataPribadi.setStatusPendidikanPosition(spnPendidikanPribadi.getSelectedItemPosition());
        tblDataPribadi.setStatusRumah(statusRumahValue[spnStatusRumahPribadi.getSelectedItemPosition()]);
        tblDataPribadi.setStatusRumahPosition(spnStatusRumahPribadi.getSelectedItemPosition());
        tblDataPribadi.setTinggalSejakTahun((edtTinggalSejakTahunPribadi.getText() == null) ? "" : edtTinggalSejakTahunPribadi.getText().toString());
        tblDataPribadi.setTinggalSejakBulan((edtTinggalSejakBulanPribadi.getText() == null) ? "" : edtTinggalSejakBulanPribadi.getText().toString());
        tblDataPribadi.setAgama(agamaValue[spnAgamaPribadi.getSelectedItemPosition()]);
        tblDataPribadi.setAgamaPosition(spnAgamaPribadi.getSelectedItemPosition());
        tblDataPribadi.setJumlahTanggungan((edtJumlahTanggunganPribadi.getText() == null) ? "" : edtJumlahTanggunganPribadi.getText().toString());
        tblDataPribadi.setEmail((edtEmailPribadi.getText() == null) ? "" : edtEmailPribadi.getText().toString());
        tblDataPribadi.setWargaNegara(wargaNegaraValue[spnWargaNegaraPribadi.getSelectedItemPosition()]);
        tblDataPribadi.setWargaNegaraPosition(spnWargaNegaraPribadi.getSelectedItemPosition());
        try {
            databaseService.getTblDataPribadiDao().create(tblDataPribadi);
        } catch (SQLException msgTblDataPribadi) {
            if (BuildConfig.DEBUG)
                Log.e("TblDataPribadi", String.valueOf(msgTblDataPribadi));
            Crashlytics.log(String.valueOf(msgTblDataPribadi));
        }

//        DATA PASANGAN
        TblDataPasangan tblDataPasangan = new TblDataPasangan();
        tblDataPasangan.setMasterFormPengajuan(masterFormPengajuan);
        if (form.equals("New") || form.equals("Draft")) tblDataPasangan.setIdPasangan("");
        else tblDataPasangan.setIdPasangan(idFamily);
        tblDataPasangan.setNamaLengkap((edtNamaPasangan.getText() == null) ? "" : edtNamaPasangan.getText().toString());
        tblDataPasangan.setNomorKtp((edtNoKtpPasangan.getText() == null) ? "" : edtNoKtpPasangan.getText().toString());
        tblDataPasangan.setTempatLahir((edtTempatLahirPasangan.getText() == null) ? "" : edtTempatLahirPasangan.getText().toString());
        tblDataPasangan.setTanggalLahir((edtTanggalLahirPasangan.getText() == null) ? "" : edtTanggalLahirPasangan.getText().toString());
        tblDataPasangan.setKotaPasangan((strCityPasangan == null) ? "" : strCityPasangan);
        tblDataPasangan.setKecamatanPasangan((strKecamatanPasangan == null) ? "" : strKecamatanPasangan);
        tblDataPasangan.setKelurahanPasangan((strKelurahanPasangan == null) ? "" : strKelurahanPasangan);
        tblDataPasangan.setZipcodePasangan((strZipCodePasangan == null) ? "" : strZipCodePasangan);
        try {
            databaseService.getTblDataPasanganDao().create(tblDataPasangan);
        } catch (SQLException msgTblDataPasangan) {
            if (BuildConfig.DEBUG)
                Log.e("TblDataPasangan", String.valueOf(msgTblDataPasangan));
            Crashlytics.log(String.valueOf(msgTblDataPasangan));
        }

//        ALAMAT PEMOHON
        TblAlamat tblAlamat = new TblAlamat();
        tblAlamat.setMasterFormPengajuan(masterFormPengajuan);
        tblAlamat.setAlamatTinggal(edtAlamatTinggal.getText().toString());
        tblAlamat.setRtTinggal((edtRtTinggal.getText() == null) ? "" : edtRtTinggal.getText().toString());
        tblAlamat.setRwTinggal((edtRwTinggal.getText() == null) ? "" : edtRwTinggal.getText().toString());
        tblAlamat.setKotaTinggal((strCityAlamatPemohon == null) ? "" : strCityAlamatPemohon);
        tblAlamat.setKecamatanTinggal((strKecamatanAlamatPemohon == null) ? "" : strKecamatanAlamatPemohon);
        tblAlamat.setKelurahanTinggal((strKelurahanAlamatPemohon == null) ? "" : strKelurahanAlamatPemohon);
        tblAlamat.setZipcodeTinggal((strZipCodeAlamatPemohon == null) ? "" : strZipCodeAlamatPemohon);
        tblAlamat.setKodeAreaTeleponTinggal((edtAreaPhoneTinggal.getText() == null) ? "" : edtAreaPhoneTinggal.getText().toString());
        tblAlamat.setNomorTeleponTinggal((edtPhoneTinggal.getText() == null) ? "" : edtPhoneTinggal.getText().toString());

        tblAlamat.setAlamatKtp(edtAlamatKtp.getText().toString());
        tblAlamat.setRtKtp((edtRtKtp.getText() == null) ? "" : edtRtKtp.getText().toString());
        tblAlamat.setRwKtp((edtRwKtp.getText() == null) ? "" : edtRwKtp.getText().toString());
        tblAlamat.setKotaKtp((strCityKtpAlamatPemohon == null) ? "" : strCityKtpAlamatPemohon);
        tblAlamat.setKecamatanKtp((strKecamatanKtpAlamatPemohon == null) ? "" : strKecamatanKtpAlamatPemohon);
        tblAlamat.setKelurahanKtp((strKelurahanKtpAlamatPemohon == null) ? "" : strKelurahanKtpAlamatPemohon);
        tblAlamat.setZipcodeKtp((strZipCodeKtpAlamatPemohon == null) ? "" : strZipCodeKtpAlamatPemohon);
        tblAlamat.setKodeAreaTeleponKtp((edtAreaPhoneKtp.getText() == null) ? "" : edtAreaPhoneKtp.getText().toString());
        tblAlamat.setNomorTeleponKtp((edtPhoneKtp.getText() == null) ? "" : edtPhoneKtp.getText().toString());
        try {
            databaseService.getTblAlamatDao().create(tblAlamat);
        } catch (SQLException msgTblAlamat) {
            if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(msgTblAlamat));
            Crashlytics.log(String.valueOf(msgTblAlamat));
        }

//        KONTAK DARURAT
        TblKontakDarurat tblKontakDarurat = new TblKontakDarurat();
        tblKontakDarurat.setMasterFormPengajuan(masterFormPengajuan);
        tblKontakDarurat.setNamaLengkap((edtNamaKerabat.getText() == null) ? "" : edtNamaKerabat.getText().toString());
        tblKontakDarurat.setHubunganKerabat(spnHubunganKerabat.getSelectedItem().toString());
        tblKontakDarurat.setHubunganKerabatPosition(spnHubunganKerabat.getSelectedItemPosition());
        tblKontakDarurat.setAlamat(edtAlamatKerabat.getText().toString());
        tblKontakDarurat.setRt((edtRtKerabat.getText() == null) ? "" : edtRtKerabat.getText().toString());
        tblKontakDarurat.setRw((edtRwKerabat.getText() == null) ? "" : edtRwKerabat.getText().toString());
        tblKontakDarurat.setKota((strCityAlamatKerabat == null) ? "" : strCityAlamatKerabat);
        tblKontakDarurat.setKecamatan((strKecamatanAlamatKerabat == null) ? "" : strKecamatanAlamatKerabat);
        tblKontakDarurat.setKelurahan((strKelurahanAlamatKerabat == null) ? "" : strKelurahanAlamatKerabat);
        tblKontakDarurat.setZipcode((strZipCodeAlamatKerabat == null) ? "" : strZipCodeAlamatKerabat);
        tblKontakDarurat.setKodeAreaTeleponRumah((edtAreaPhoneRumahKerabat.getText() == null) ? "" : edtAreaPhoneRumahKerabat.getText().toString());
        tblKontakDarurat.setNomorTeleponRumah((edtPhoneRumahKerabat.getText() == null) ? "" : edtPhoneRumahKerabat.getText().toString());
        tblKontakDarurat.setKodeAreaTeleponKantor((edtAreaPhoneKantorKerabat.getText() == null) ? "" : edtAreaPhoneKantorKerabat.getText().toString());
        tblKontakDarurat.setNomorTeleponKantor((edtPhoneKantorKerabat.getText() == null) ? "" : edtPhoneKantorKerabat.getText().toString());
        tblKontakDarurat.setNomorHandphone((edtHpKerabat.getText() == null) ? "" : edtHpKerabat.getText().toString());
        try {
            databaseService.getTblKontakDaruratDao().create(tblKontakDarurat);
        } catch (SQLException msgTblKontakDarurat) {
            if (BuildConfig.DEBUG)
                Log.e("TblKontakDarurat", String.valueOf(msgTblKontakDarurat));
            Crashlytics.log(String.valueOf(msgTblKontakDarurat));
        }

//        DATA PEKERJAAN
        TblDataPekerjaan tblDataPekerjaan = new TblDataPekerjaan();
        tblDataPekerjaan.setMasterFormPengajuan(masterFormPengajuan);
        tblDataPekerjaan.setNamaPerubahaan((edtNamaPerusahaan.getText() == null) ? "" : edtNamaPerusahaan.getText().toString());
        tblDataPekerjaan.setAlamat(edtAlamatPerusahaan.getText().toString());
        tblDataPekerjaan.setRt((edtRtPerusahaan.getText() == null) ? "" : edtRtPerusahaan.getText().toString());
        tblDataPekerjaan.setRw((edtRwPerusahaan.getText() == null) ? "" : edtRwPerusahaan.getText().toString());
        tblDataPekerjaan.setKota((strCityAlamatPekerjaan == null) ? "" : strCityAlamatPekerjaan);
        tblDataPekerjaan.setKecamatan((strKecamatanAlamatPekerjaan == null) ? "" : strKecamatanAlamatPekerjaan);
        tblDataPekerjaan.setKelurahan((strKelurahanAlamatPekerjaan == null) ? "" : strKelurahanAlamatPekerjaan);
        tblDataPekerjaan.setZipcode((strZipCodeAlamatPekerjaan == null) ? "" : strZipCodeAlamatPekerjaan);
        tblDataPekerjaan.setKodeAreaTelepon((edtAreaPhonePerusahaan.getText() == null) ? "" : edtAreaPhonePerusahaan.getText().toString());
        tblDataPekerjaan.setNomorTelepon((edtPhonePerusahaan.getText() == null) ? "" : edtPhonePerusahaan.getText().toString());
        tblDataPekerjaan.setBekerjaSejak((edtBekerjaSejakPerusahaan.getText() == null) ? "" : edtBekerjaSejakPerusahaan.getText().toString());
        tblDataPekerjaan.setKodeProfesi((professionKode == null) ? "" : professionKode);
        tblDataPekerjaan.setProfesi((actProfesiPerusahaan.getText() == null) ? "" : actProfesiPerusahaan.getText().toString());
        tblDataPekerjaan.setKodeTipePekerjaan((jobTypeKode == null) ? "" : jobTypeKode);
        tblDataPekerjaan.setTipePekerjaan((actJobTypePerusahaan.getText() == null) ? "" : actJobTypePerusahaan.getText().toString());
        tblDataPekerjaan.setKodePosisiPekerjaan((jobPositionKode == null) ? "" : jobPositionKode);
        tblDataPekerjaan.setPosisiPekerjaan((actJobPositionPerusahaan.getText() == null) ? "" : actJobPositionPerusahaan.getText().toString());
        tblDataPekerjaan.setKodeIndustri((industriKode == null) ? "" : industriKode);
        tblDataPekerjaan.setIndustri((actIndustriPerusahaan.getText() == null) ? "" : actIndustriPerusahaan.getText().toString());
        tblDataPekerjaan.setPenghasilanTetap((edtPenghasilanTetapPerusahaan.getText() == null) ? "" : edtPenghasilanTetapPerusahaan.getText().toString().replace(",", ""));
        tblDataPekerjaan.setPenghasilanLain("".equalsIgnoreCase((edtPenghasilanLainPerusahaan.getText() == null) ? "" : edtPenghasilanLainPerusahaan.getText().toString()) ? "0" : edtPenghasilanLainPerusahaan.getText().toString().replace(",", ""));
        tblDataPekerjaan.setPenghasilanPasangan("".equalsIgnoreCase((edtPenghasilanPasanganPerusahaan.getText() == null) ? "" : edtPenghasilanPasanganPerusahaan.getText().toString()) ? "0" : edtPenghasilanPasanganPerusahaan.getText().toString().replace(",", ""));
        tblDataPekerjaan.setBiayaHidup((edtBiayaHidupPerusahaan.getText() == null) ? "" : edtBiayaHidupPerusahaan.getText().toString().replace(",", ""));
        try {
            databaseService.getTblDataPekerjaanDao().create(tblDataPekerjaan);
        } catch (SQLException msgTblDataPekerjaan) {
            if (BuildConfig.DEBUG)
                Log.e("TblDataPekerjaan", String.valueOf(msgTblDataPekerjaan));
            Crashlytics.log(String.valueOf(msgTblDataPekerjaan));
        }

//        DATA KARTU KREDIT
        TblDataKartuKredit tblDataKartuKredit = new TblDataKartuKredit();
        tblDataKartuKredit.setMasterFormPengajuan(masterFormPengajuan);
        tblDataKartuKredit.setNamaBank((edtNamaBankKartuKredit.getText() == null) ? "" : edtNamaBankKartuKredit.getText().toString());
        tblDataKartuKredit.setNoKartuKredit((edtNoKartuKredit.getText() == null) ? "" : edtNoKartuKredit.getText().toString());
        tblDataKartuKredit.setJenisKartuKredit((edtJenisKartuKredit.getText() == null) ? "" : edtJenisKartuKredit.getText().toString());
        tblDataKartuKredit.setLimitKartuKredit((edtLimitKartuKredit.getText() == null) ? "" : edtLimitKartuKredit.getText().toString().replace(",", ""));
        tblDataKartuKredit.setTahunKadaluarsaKartuKredit((edtTahunKadaluarsaKartuKredit.getText() == null) ? "" : edtTahunKadaluarsaKartuKredit.getText().toString());
        tblDataKartuKredit.setBulanKadaluarsaKartuKredit((edtBulanKadaluarsaKartuKredit.getText() == null) ? "" : edtBulanKadaluarsaKartuKredit.getText().toString());
        try {
            databaseService.getTblDataKartuKreditDao().create(tblDataKartuKredit);
        } catch (SQLException msgTblDataKartuKredit) {
            if (BuildConfig.DEBUG)
                Log.e("TblDataKartuKredit", String.valueOf(msgTblDataKartuKredit));
            Crashlytics.log(String.valueOf(msgTblDataKartuKredit));
        }

//        DATA KARTU MEMBERSHIP
        TblKartuMembership tblKartuMembership = new TblKartuMembership();
        tblKartuMembership.setMasterFormPengajuan(masterFormPengajuan);
        tblKartuMembership.setNoMembership((edtNoMembership.getText() == null) ? "" : edtNoMembership.getText().toString());
        tblKartuMembership.setTanggalEfektif((edtTanggalEfektif.getText() == null) ? "" : edtTanggalEfektif.getText().toString());
        tblKartuMembership.setTanggalExipred((edtTanggalExpired.getText() == null) ? "" : edtTanggalExpired.getText().toString());
        try {
            databaseService.getTblKartuMembershipDao().create(tblKartuMembership);
        } catch (SQLException msgTblKartuMembership) {
            if (BuildConfig.DEBUG)
                Log.e("TblKartuMembership", String.valueOf(msgTblKartuMembership));
            Crashlytics.log(String.valueOf(msgTblKartuMembership));
        }

//        DETAIL PRODUCT
        TblDetailProduct tblDetailProduct = new TblDetailProduct();
        tblDetailProduct.setMasterFormPengajuan(masterFormPengajuan);
        tblDetailProduct.setIdBank("");
        if (!formType.equals("draft_baru")) {
            tblDetailProduct.setKodeSupplier((supplierKode == null) ? "" : supplierKode);
            tblDetailProduct.setSupplier((actSupplierAsset.getText() == null) ? "" : actSupplierAsset.getText().toString());
            tblDetailProduct.setKodeMarketingSupplier((marketingKode == null) ? "" : marketingKode);
            tblDetailProduct.setMarketingSupplier((actMarketingSupplierAsset.getText() == null) ? "" : actMarketingSupplierAsset.getText().toString());
            tblDetailProduct.setKodeProductId((productId == null) ? "" : productId);
            tblDetailProduct.setKodeProductOfferingId((productOfferingId == null) ? "" : productOfferingId);
            tblDetailProduct.setProductOffering((actProductOfferingProduct.getText() == null) ? "" : actProductOfferingProduct.getText().toString());
            tblDetailProduct.setPosId((posKode == null) ? "" : posKode);
            tblDetailProduct.setPos((actPosProduct.getText() == null) ? "" : actPosProduct.getText().toString());
            tblDetailProduct.setJumlahAsset((strJumlahAsset == null) ? "" : strJumlahAsset);
        }
        try {
            databaseService.getTblDetailProductDao().create(tblDetailProduct);
        } catch (SQLException msgTblDetailProduct) {
            if (BuildConfig.DEBUG)
                Log.e("TblDetailProduct", String.valueOf(msgTblDetailProduct));
            Crashlytics.log(String.valueOf(msgTblDetailProduct));
        }

//        DATA ASURANSI
        TblAsuransi tblAsuransi = new TblAsuransi();
        tblAsuransi.setMasterFormPengajuan(masterFormPengajuan);
        tblAsuransi.setManualAgunan(manualAgunan);
        tblAsuransi.setManualPremi(String.valueOf(manualPremi));
        try {
            databaseService.getTblAsuransiDao().create(tblAsuransi);
        } catch (SQLException msgTblAsuransi) {
            if (BuildConfig.DEBUG) Log.e("TblAsuransi", String.valueOf(msgTblAsuransi));
            Crashlytics.log(String.valueOf(msgTblAsuransi));
        }

//        DATA PERHITUNGAN ELEKTRONIK
        TblDataPerhitungan tblDataPerhitungan = new TblDataPerhitungan();
        tblDataPerhitungan.setMasterFormPengajuan(masterFormPengajuan);
        if (!formType.equals("draft_baru")) {
            tblDataPerhitungan.setTenor(spnTenorProduct.getSelectedItem() == null ? "" : spnTenorProduct.getSelectedItem().toString());
            tblDataPerhitungan.setPosisiTenor(spnTenorProduct.getSelectedItemPosition());
            tblDataPerhitungan.setFlateRate("".equalsIgnoreCase(edtFlatRatePerhitungan.getText().toString()) ? "0" : edtFlatRatePerhitungan.getText().toString());
            tblDataPerhitungan.setBiayaAdministrasi("".equalsIgnoreCase(edtBiayaAdminPerhitungan.getText().toString()) ? "0" : edtBiayaAdminPerhitungan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setBiayaLainnya("".equalsIgnoreCase(edtBiayaLainnya.getText().toString()) ? "0" : edtBiayaLainnya.getText().toString().replace(",", ""));
            tblDataPerhitungan.setRefundSubsidi("".equalsIgnoreCase(edtRefundSubsidiPerhitungan.getText().toString()) ? "0" : edtRefundSubsidiPerhitungan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setBebasBunga("".equalsIgnoreCase(edtBebasBungaPerhitungan.getText().toString()) ? "0" : edtBebasBungaPerhitungan.getText().toString());
            tblDataPerhitungan.setTotalPrice("".equalsIgnoreCase(edtPurchasePricePerhitungan.getText().toString()) ? "0" : edtPurchasePricePerhitungan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setTotalDiscount("".equalsIgnoreCase(edtDiscountPerhitungan.getText().toString()) ? "0" : edtDiscountPerhitungan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setTotalDp("".equalsIgnoreCase(edtDpPerhitungan.getText().toString()) ? "0" : edtDpPerhitungan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setPremi("".equalsIgnoreCase(edtPremiAsuransi.getText().toString()) ? "0" : edtPremiAsuransi.getText().toString().replace(",", ""));
            tblDataPerhitungan.setNtf("".equalsIgnoreCase(edtNtfPerhitungan.getText().toString()) ? "0" : edtNtfPerhitungan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setJumlahPembiayaan("".equalsIgnoreCase(edtJumlahPembiayaan.getText().toString()) ? "0" : edtJumlahPembiayaan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setTotalBungaPembiayaan("".equalsIgnoreCase(edtTotalBungaPembiayaan.getText().toString()) ? "0" : edtTotalBungaPembiayaan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setBulanBungaPembiayaan("".equalsIgnoreCase(edtBungaPembiayaanBulan.getText().toString()) ? "0" : edtBungaPembiayaanBulan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setTotalPinjaman("".equalsIgnoreCase(edtTotalPinjaman.getText().toString()) ? "0" : edtTotalPinjaman.getText().toString().replace(",", ""));
            if (edtBebasBungaPerhitungan.getText().toString().equals("0"))
                tblDataPerhitungan.setAngsuranPrebulanBebasBunga("".equalsIgnoreCase(edtAngsuranPerbulan.getText().toString()) ? "0" : edtAngsuranPerbulan.getText().toString().replace(",", ""));
            else
                tblDataPerhitungan.setAngsuranPrebulanBebasBunga("".equalsIgnoreCase(edtAngsuranPerbulanBebasBunga.getText().toString()) ? "0" : edtAngsuranPerbulanBebasBunga.getText().toString().replace(",", ""));
            tblDataPerhitungan.setAngsuranPerbulan("".equalsIgnoreCase(edtAngsuranPerbulan.getText().toString()) ? "0" : edtAngsuranPerbulan.getText().toString().replace(",", ""));
            tblDataPerhitungan.setPembayaranAwal("".equalsIgnoreCase(edtPembayaranPertama.getText().toString()) ? "0" : edtPembayaranPertama.getText().toString().replace(",", ""));
            tblDataPerhitungan.setPembayaranDelaer("".equalsIgnoreCase(edtPembayaranDelaer.getText().toString()) ? "0" : edtPembayaranDelaer.getText().toString().replace(",", ""));
            tblDataPerhitungan.setEffectiveRate("".equalsIgnoreCase(edtEffectiveRatePerhitungan.getText().toString()) ? "0" : edtEffectiveRatePerhitungan.getText().toString());
            tblDataPerhitungan.setAdminFeeLainnya(String.valueOf(nominalAdminLain));
            tblDataPerhitungan.setTipePembayaran(fsInstallment);
        }
        try {
            databaseService.getTblDataPerhitunganDao().create(tblDataPerhitungan);
        } catch (SQLException msgTblDataPerhitungan) {
            if (BuildConfig.DEBUG)
                Log.e("TblDataPerhitungan", String.valueOf(msgTblDataPerhitungan));
            Crashlytics.log(String.valueOf(msgTblDataPerhitungan));
        }

//        KETERANGAN
        TblKeterangan tblKeterangan = new TblKeterangan();
        tblKeterangan.setMasterFormPengajuan(masterFormPengajuan);
        tblKeterangan.setKeterangan((edtKeterangan.getText() == null) ? "" : edtKeterangan.getText().toString());
        try {
            databaseService.getTblKeteranganDao().create(tblKeterangan);
        } catch (SQLException msgTblKeterangan) {
            if (BuildConfig.DEBUG) Log.e("TblKeterangan", String.valueOf(msgTblKeterangan));
            Crashlytics.log(String.valueOf(msgTblKeterangan));
        }

//        MASKING RATE DAN TENOR
        if (isMaskingOn) {
            for (int i = 0; i < listMasking.size(); i++) {
                MaskingRate mRate = new MaskingRate();
                MaskingTenor mTenor = new MaskingTenor();

                mRate.setMasterFormPengajuan(masterFormPengajuan);
                mRate.setRate(String.valueOf(listMasking.get(i).getReturnRate()));
                mTenor.setMasterFormPengajuan(masterFormPengajuan);
                mTenor.setTenor(listMasking.get(i).getTenor());

                try {
                    databaseService.getMaskingRateDao().create(mRate);
                } catch (SQLException e) {
                    if (BuildConfig.DEBUG) Log.e("mRate", String.valueOf(e));
                    Crashlytics.log(String.valueOf(e));
                }

                try {
                    databaseService.getMaskingTenorDao().create(mTenor);
                } catch (SQLException e) {
                    if (BuildConfig.DEBUG) Log.e("mTenor", String.valueOf(e));
                    Crashlytics.log(String.valueOf(e));
                }
            }
        }

//        DATA ASSET

        if (!formType.equals("draft_baru")) {
            List<AssetElektronik> assetElektronikList = new ArrayList<>();
            for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
                View view = llParentTotalAsset.getChildAt(i);

                NiceAutoCompleteTextView actNamaBarang = view.findViewById(R.id.act_nama_barang_asset);
                EditText edtPrice = view.findViewById(R.id.edt_price_asset);
                EditText edtDp = view.findViewById(R.id.edt_dp_asset);
                EditText edtDiscount = view.findViewById(R.id.edt_discount_asset);
                EditText edtTypeAset = view.findViewById(R.id.edt_type_asset);
                AssetElektronik assetElektronik = new AssetElektronik();

                assetElektronik.setMasterFormPengajuan(masterFormPengajuan);
                assetElektronik.setKodeBarang((String) actNamaBarang.getTag());/*assetCode*/
                assetElektronik.setNamaBarang(actNamaBarang.getText().toString());
                assetElektronik.setCategory(categoryId);
                assetElektronik.setType(edtTypeAset.getText().toString());
                assetElektronik.setPrice((edtPrice.getText() == null) ? "" : edtPrice.getText().toString().replace(",", ""));
                assetElektronik.setDp((edtDp.getText() == null) ? "" : edtDp.getText().toString().replace(",", ""));
                assetElektronik.setDiscount((edtDiscount.getText() == null) ? "" : edtDiscount.getText().toString().replace(",", ""));
                assetElektronikList.add(assetElektronik);

                try {
                    databaseService.getAssetDao().create(assetElektronik);
                } catch (SQLException e) {
                    if (BuildConfig.DEBUG) Log.e("Create asset", String.valueOf(e));
                    Crashlytics.logException(e);
                }
            }
        }

//        FOTO PENGAJUAN
        for (Object o : mHashMapAttachmentFiles.keySet()) {
            int i = (int) o;
            Attachment attachment = new Attachment();
            attachment.setMasterFormPengajuan(masterFormPengajuan);
            attachment.setNamaAttachment("eform" + System.currentTimeMillis());
            attachment.setPath(mHashMapAttachmentFiles.get(i).getAbsolutePath());
            attachment.setPath2(mHashMapAttachmentFiles.get(i).getPath());
            attachment.setKey(i);

            try {
                databaseService.getAttachmentDao().create(attachment);
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("Create attachment", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }

//        TANDA TANGAN
        TblTandaTangan tblTandaTangan = new TblTandaTangan();
        tblTandaTangan.setMasterFormPengajuan(masterFormPengajuan);
        if (!formType.equals("draft_edit")) {
            tblTandaTangan.setTtdPemohon(Util.bitmapToBase64(bitmapTtdPemohon));
            tblTandaTangan.setTtdPasangan(bitmapTtdPasangan == null ? "" : Util.bitmapToBase64(bitmapTtdPasangan));
            tblTandaTangan.setJumlahTtd(countSignature);
        }
        try {
            databaseService.getTblTandaTanganDao().create(tblTandaTangan);
        } catch (SQLException msgTblTandaTangan) {
            if (BuildConfig.DEBUG)
                Log.e("TblTandaTangan", String.valueOf(msgTblTandaTangan));
            Crashlytics.logException(msgTblTandaTangan);
        }

//        REKOMENDASI
        TblRekomendasi tblRekomendasi = new TblRekomendasi();
        tblRekomendasi.setMasterFormPengajuan(masterFormPengajuan);
        tblRekomendasi.setJabar(0);
        tblRekomendasi.setIdRekomendasi(recomendationId);
        tblRekomendasi.setCatatan((edtDescRecomendation.getText() == null) ? "" : edtDescRecomendation.getText().toString());
        try {
            databaseService.getTblRekomendasiDao().create(tblRekomendasi);
        } catch (SQLException msgTblRekomendasi) {
            if (BuildConfig.DEBUG)
                Log.e("TblRekomendasi", String.valueOf(msgTblRekomendasi));
            Crashlytics.log(String.valueOf(msgTblRekomendasi));
        }

//        LOKASI
        TblLokasi tblLokasi = new TblLokasi();
        tblLokasi.setMasterFormPengajuan(masterFormPengajuan);
        tblLokasi.setValidationAction((validateAction.getText() == null) ? "" : validateAction.getText().toString());
        tblLokasi.setValidationLongitude((validateLongitude.getText() == null) ? "" : validateLongitude.getText().toString());
        tblLokasi.setValidationLatitude((validateLatitude.getText() == null) ? "" : validateLatitude.getText().toString());
        tblLokasi.setKtpAction((takeKtpAction.getText() == null) ? "" : takeKtpAction.getText().toString());
        tblLokasi.setKtpLongitude((takeKtpLongitude.getText() == null) ? "" : takeKtpLongitude.getText().toString());
        tblLokasi.setKtpLatitude((takeKtpLatitude.getText() == null) ? "" : takeKtpLatitude.getText().toString());
        tblLokasi.setCustomerAction((takeCustomerAction.getText() == null) ? "" : takeCustomerAction.getText().toString());
        tblLokasi.setCustomerLongitude((takeCustomerLongitude.getText() == null) ? "" : takeCustomerLongitude.getText().toString());
        tblLokasi.setCustomerLatitude((takeCustomerLatitude.getText() == null) ? "" : takeCustomerLatitude.getText().toString());
        tblLokasi.setPaycheckAction((takePaycheckAction.getText() == null) ? "" : takePaycheckAction.getText().toString());
        tblLokasi.setPaycheckLongitude((takePaycheckLongitude.getText() == null) ? "" : takePaycheckLongitude.getText().toString());
        tblLokasi.setPaycheckLatitude((takePaycheckLatitude.getText() == null) ? "" : takePaycheckLatitude.getText().toString());
        tblLokasi.setAddtionalDocumentsAction((takeAdditionalDocumentsAction.getText() == null) ? "" : takeAdditionalDocumentsAction.getText().toString());
        tblLokasi.setAddtionalDocumentsLongitude((takeAdditionalDocumentsLongitude.getText() == null) ? "" : takeAdditionalDocumentsLongitude.getText().toString());
        tblLokasi.setAddtionalDocumentsLatitude((takeAdditionalDocumentsLatitude.getText() == null) ? "" : takeAdditionalDocumentsLatitude.getText().toString());
        tblLokasi.setSignatureAction((takeSignatureAction.getText() == null) ? "" : takeSignatureAction.getText().toString());
        tblLokasi.setSignatureLongitude((takeSignatureLongitude.getText() == null) ? "" : takeSignatureLongitude.getText().toString());
        tblLokasi.setSignatureLatitude((takeSignatureLatitude.getText() == null) ? "" : takeSignatureLatitude.getText().toString());
        tblLokasi.setSubmitAction((submitAction.getText() == null) ? "" : submitAction.getText().toString());
        tblLokasi.setSubmitLongitude((submitLongitude.getText() == null) ? "" : submitLongitude.getText().toString());
        tblLokasi.setSubmitLatitude((submitLatitude.getText() == null) ? "" : submitLatitude.getText().toString());
        tblLokasi.setSyncAction("");
        tblLokasi.setSyncLongitude("");
        tblLokasi.setSyncLatitude("");
        try {
            databaseService.getTblLokasiDao().create(tblLokasi);
        } catch (SQLException msgTblLokasi) {
            if (BuildConfig.DEBUG) Log.e("TblLokasi", String.valueOf(msgTblLokasi));
            Crashlytics.log(String.valueOf(msgTblLokasi));
        }

        deleteDataConfins();
        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        finish();
    }

    private void deleteDataConfins() {
        try {
            DeleteBuilder<SupplierMaster, String> dltSupplierMaster = databaseService.getSupplierMasterDao().deleteBuilder();
            dltSupplierMaster.delete();

            DeleteBuilder<SupplierEmp, String> dltSupplierEmp = databaseService.getSupplierEmpDao().deleteBuilder();
            dltSupplierEmp.delete();

            DeleteBuilder<ProductOfferingMaster, String> dltProductOfferingMaster = databaseService.getProductOfferingMasterDao().deleteBuilder();
            dltProductOfferingMaster.delete();

            DeleteBuilder<ProductOfTenor, String> dltProductOfTenor = databaseService.getProductOfTenorDao().deleteBuilder();
            dltProductOfTenor.delete();

            DeleteBuilder<PosMaster, String> dltPosMaster = databaseService.getPosMasterDao().deleteBuilder();
            dltPosMaster.delete();

            DeleteBuilder<AssetMaster, String> dltAssetMaster = databaseService.getAssetMastersDao().deleteBuilder();
            dltAssetMaster.delete();
        } catch (java.sql.SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Del Data Confins", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    @OnCheckedChanged(R.id.cbx_alamat_ktp)
    public void checkedAlamatKtp(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            lnVisibleAlamatKtp.setVisibility(View.GONE);
            edtAlamatKtp.setText(edtAlamatTinggal.getText().toString());
            edtRtKtp.setText(edtRtTinggal.getText().toString());
            edtRwKtp.setText(edtRwTinggal.getText().toString());
            actAutoKtpAlamatPemohon.setText(actAutoAlamatPemohon.getText().toString());
            strCityKtpAlamatPemohon = strCityAlamatPemohon;
            strKecamatanKtpAlamatPemohon = strKecamatanAlamatPemohon;
            strKelurahanKtpAlamatPemohon = strKelurahanAlamatPemohon;
            strZipCodeKtpAlamatPemohon = strZipCodeAlamatPemohon;
            actAutoKtpAlamatPemohon.setSelectionFromPopUp(true);
            edtAreaPhoneKtp.setText(edtAreaPhoneTinggal.getText().toString());
            edtPhoneKtp.setText(edtPhoneTinggal.getText().toString());
//            validator.removeRules(edtAlamatKtp);
//            validator.removeRules(edtRtKtp);
//            validator.removeRules(edtRwKtp);
//            validator.removeRules(actAutoKtpAlamatPemohon);
//            validator.removeRules(edtAreaPhoneKtp);
//            validator.removeRules(edtPhoneKtp);
        } else {
            lnVisibleAlamatKtp.setVisibility(View.VISIBLE);
//            validator.put(edtAlamatKtp, notEmptyRule);
//            validator.put(edtRtKtp, notEmptyRule);
//            validator.put(edtRwKtp, notEmptyRule);
//            validator.put(actAutoKtpAlamatPemohon, notEmptyRule);
//            validator.put(edtAreaPhoneKtp, notEmptyRule);
//            validator.put(edtPhoneKtp, notEmptyRule);
        }
    }

    private boolean checkMinimumPrice() {
        if (form.equals("Edit")) {
            return true;
        } else {
            if (tenor != 0 && !spnTenorProduct.getSelectedItem().toString().equalsIgnoreCase("Pilih")) {
                String dblCurrentRateString = edtFlatRatePerhitungan.getText().toString();
                double dblCurrentRate = !dblCurrentRateString.equals("") ? Double.parseDouble(dblCurrentRateString) : 0;
                double dblMinRate = Double.parseDouble(flatRate);
                int dblCurrentAdminFee = (edtBiayaAdminPerhitungan.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtBiayaAdminPerhitungan.getText().toString().replace(",", "")));
                int dblMinAdminFee = (int) admin_fee;
                int dblCurrentNTF = (edtJumlahPembiayaan.getText().toString().isEmpty()) ? 0 : Integer.parseInt(edtJumlahPembiayaan.getText().toString().replace(",", ""));
                int dblMinNTF = minimalNTF;
                int intCurrentBiayaAdmin = Integer.parseInt(edtBiayaAdminPerhitungan.getText().toString().replace(",", ""));
                int dblMinBiayaAdmin = (int) admin_fee;
                tilNtf.setError(null);
                if (dblCurrentRate < dblMinRate || dblCurrentAdminFee < dblMinAdminFee || dblCurrentNTF < dblMinNTF)
                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                else imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
                if (dblCurrentRate < dblMinRate)
                    edtFlatRatePerhitungan.setError("Minimal Flate Rate " + dblMinRate + "%");
                else edtFlatRatePerhitungan.setError(null);
                if (dblCurrentAdminFee < dblMinAdminFee)
                    edtBiayaAdminPerhitungan.setError("Minimal Biaya Admin : Rp " + dblMinAdminFee);
                else edtBiayaAdminPerhitungan.setError(null);
                if (dblCurrentNTF < dblMinNTF) {
                    Toast.makeText(this, "Minimal NTF : " + dblMinNTF, Toast.LENGTH_SHORT).show();
                    edtJumlahPembiayaan.setError("Minimal NTF : " + dblMinNTF);
                    tilNtf.setError("Minimal NTF : " + dblMinNTF);
                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                    imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                } else {
                    edtJumlahPembiayaan.setError(null);
                    imgDropDownAsset.setImageResource(R.drawable.ic_arrow);
                    imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
                }
                if (intCurrentBiayaAdmin < dblMinBiayaAdmin)
                    edtBiayaAdminPerhitungan.setError("Minimal Biaya Admin Rp " + dblMinBiayaAdmin);
                else edtBiayaAdminPerhitungan.setError(null);

                return dblCurrentRate >= dblMinRate && dblCurrentAdminFee >= dblMinAdminFee && dblCurrentNTF >= dblMinNTF && intCurrentBiayaAdmin >= dblMinBiayaAdmin;
            }
            return false;
        }
    }

    @Override
    public void onValidationSucceeded() {
        ktpSaldo = edtNoKtpPribadi.getText().toString();
        dateSaldo = edtTanggalLahirPribadi.getText().toString();
        imgDropDownNpwp.setImageResource(R.drawable.ic_arrow);
        edtNoNpwpDetail.setError(null);
        if (rbAsuransiAgunanYes.isChecked()) {
            manualAgunan = "All Risk";
            manualPremi = false;
        } else {
            manualAgunan = "No Assurance";
            manualPremi = true;
        }

        if (typeDataOffering.equalsIgnoreCase("ELC")) {
            String strJumlahAsset = String.valueOf(llParentTotalAsset.getChildCount());
            if (strJumlahAsset.equals("0")) {
                Toast.makeText(this, "Data Asset Wajib Di Isi", Toast.LENGTH_SHORT).show();
                imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
            } else {
                imgDropDownAsset.setImageResource(R.drawable.ic_arrow);
                imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
                imgDropDownPribadi.setImageResource(R.drawable.ic_arrow);
                tvTenorELC.setTextColor(Color.BLACK);
                if (spnTenorProduct.getSelectedItem().toString().equals("Pilih")) {
                    Toast.makeText(this, "Tenor Harap di lengkapi", Toast.LENGTH_SHORT).show();
                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                    tvTenorELC.setTextColor(Color.RED);
                }
                if (validasiKota()) {
                    if (checkMinimumPrice()) {
                        if (!edtPurchasePricePerhitungan.getText().toString().isEmpty()) {
                            prepareDataCalculating();
                            if (statusNpwp == 1 || statusNpwp == 2) {
                                if (checkDataPerhitungan(rlDataPerhitungan)) {
                                    mSaldoKreditmuPresenter.cekSaldoKreditmu(token, ktpSaldo, dateSaldo, edtAngsuranPerbulan.getText().toString().replaceAll(",", ""));
                                }
                            } else {
                                if (checkDataPerhitungan(rlDataPerhitungan)) {
                                    mSaldoKreditmuPresenter.cekSaldoKreditmu(token, ktpSaldo, dateSaldo, edtAngsuranPerbulan.getText().toString().replaceAll(",", ""));
                                }
                            }
                        }
                    }
                }

            }
        } else {
            if (checkMinimumPrice()) {
                if (statusNpwp == 1 || statusNpwp == 2) {
                    if (checkDataPerhitungan(rlDataPerhitungan)) {
                        mSaldoKreditmuPresenter.cekSaldoKreditmu(token, ktpSaldo, dateSaldo, edtAngsuranPerbulan.getText().toString().replaceAll(",", ""));
                    }
                }
            }
        }
    }

    // TODO:Upload Attachment
    private void uploadAttachment(String idSync) {
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
        successAndFailedLoading();
        mAttachmentKendaraanPresenter.SyncAttachmentElc(token, idSync, attachments, "");
    }

    private boolean checkDataSpinner() {
        //imgDropDownKerabat.setImageResource(R.drawable.ic_arrow);
        tvStatusPernikahan.setTextColor(Color.BLACK);
        tvAgama.setTextColor(Color.BLACK);
        tvJenisKelamin.setTextColor(Color.BLACK);
        tvStatusRumah.setTextColor(Color.BLACK);
        tvPendidikan.setTextColor(Color.BLACK);
        tvHubunganKerabat.setTextColor(Color.BLACK);
        imgDropDownPribadi.setImageResource(R.drawable.ic_arrow);
        String value = ""; // untuk tambahan kalau
        boolean status = false;
        imgDropDownPribadi.setImageResource(R.drawable.ic_arrow);
        if (!sensorData) {
            if (spnPernikahanPribadi.getSelectedItem().toString().equals("Pilih") || spnPernikahanPribadi.getSelectedItem().toString().equals("PILIH")) {
                value = "Pernikahan";
                status = true;
                tvStatusPernikahan.setTextColor(Color.RED);
                imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
            }
            if (spnPendidikanPribadi.getSelectedItem().toString().equals("Pilih") || spnPendidikanPribadi.getSelectedItem().toString().equals("PILIH")) {
                value = value + " Pendidikan";
                status = true;
                tvPendidikan.setTextColor(Color.RED);
                imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
            }
        }
        if (spnAgamaPribadi.getSelectedItem().toString().equals("Pilih") || spnAgamaPribadi.getSelectedItem().toString().equals("PILIH")) {
            value = "Agama";
            status = true;
            tvAgama.setTextColor(Color.RED);
            imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
        }
        if (spnGenderPribadi.getSelectedItem().toString().equals("Pilih") || spnGenderPribadi.getSelectedItem().toString().equals("PILIH")) {
            value = value + " Jenis Kelamin";
            status = true;
            tvJenisKelamin.setTextColor(Color.RED);
            imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
        }
        if (spnStatusRumahPribadi.getSelectedItem().toString().equals("Pilih") || spnStatusRumahPribadi.getSelectedItem().toString().equals("PILIH")) {
            value = value + " Status Rumah";
            status = true;
            tvStatusRumah.setTextColor(Color.RED);
            imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
        }
        if (spnHubunganKerabat.getSelectedItem().toString().equals("Pilih") || spnHubunganKerabat.getSelectedItem().toString().equals("PILIH")) {
            value = value + " Hubungan Kerabat";
            status = true;
            tvHubunganKerabat.setTextColor(Color.RED);
            imgDropDownKerabat.setImageResource(android.R.drawable.ic_dialog_alert);
        }

//        if (status)
//            Toast.makeText(this, value + " Harap di lengkapi", Toast.LENGTH_SHORT).show();

        return status;

    }

    private void checkNpwp() {
        if (!edtPurchasePricePerhitungan.getText().toString().isEmpty()) {
            int netPriceAsset = 50000000;
            // TODO: Comment Calculating
            prepareDataCalculating();
            if (otr_price > netPriceAsset) {
                rlHeaderDetailNpwp.setVisibility(View.VISIBLE);
                if (edtNoNpwpDetail.getText().toString().isEmpty()) {
                    edtNoNpwpDetail.setError("Wajib Di isi");
                    tilNoNpwpDetail.setError(getString(R.string.text_error_invalid_length));
                    imgDropDownNpwp.setImageResource(android.R.drawable.ic_dialog_alert);
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
            } else {
                statusNpwp = 2; /*Tidak Punya NPWP*/
                imgDropDownPribadi.setImageResource(R.drawable.ic_arrow);
                tilNoNpwpPribadi.setError(null);
                tilNoNpwpPribadi.setVisibility(View.GONE);
                edtNoNpwpPribadi.setVisibility(View.GONE);
                edtNoNpwpPribadi.getText().clear();
                edtNoNpwpPribadi.setError(null);

                rlHeaderDetailNpwp.setVisibility(View.GONE);
                imgDropDownNpwp.setImageResource(R.drawable.ic_arrow);
                tilNoNpwpDetail.setError(null);
                edtNoNpwpDetail.getText().clear();
                edtNoNpwpDetail.setError(null);
//                    validator.removeRules(edtNoNpwpPribadi);
                validator.removeRules(edtNoNpwpDetail);
            }
        }

    }

    private void updateData(String formType) {
        try {
            UpdateBuilder<MasterFormPengajuan, Integer> updtMasterFormPengajuan = databaseService.getMasterFormPengajuanDao().updateBuilder();
            if (isMaskingOn) updtMasterFormPengajuan.updateColumnValue("have_masking", 1);
            else updtMasterFormPengajuan.updateColumnValue("have_masking", 0);
            updtMasterFormPengajuan.updateColumnValue("application_id", (appId == null) ? "" : appId);
            updtMasterFormPengajuan.updateColumnValue("blacklist_date", (blackListDate == null) ? "" : blackListDate);
            updtMasterFormPengajuan.updateColumnValue("uuid", (uuid == null) ? "" : uuid);
            updtMasterFormPengajuan.updateColumnValue("customer_id_confins", (customerIdConfins == null) ? "" : customerIdConfins);
            updtMasterFormPengajuan.updateColumnValue("id_kpm", (applicationIdKpm == null) ? "" : applicationIdKpm);
            updtMasterFormPengajuan.updateColumnValue("branch", (aoBranch == null) ? "" : aoBranch);
            updtMasterFormPengajuan.updateColumnValue("master_branch", (branchMaster == null) ? "" : branchMaster);
            updtMasterFormPengajuan.updateColumnValue("tipe_data_offering", (typeDataOffering == null) ? "" : typeDataOffering);
            updtMasterFormPengajuan.updateColumnValue("tipe_pengajuan", (pengajuanType == null) ? "" : pengajuanType);
            updtMasterFormPengajuan.updateColumnValue("mobile_submission_key", (mobileSubmissionKey == null) ? "" : mobileSubmissionKey);
            updtMasterFormPengajuan.updateColumnValue("ef_number", (strEfNumber == null) ? "" : strEfNumber);
            updtMasterFormPengajuan.updateColumnValue("tipe_save_data", (formType == null) ? "" : formType);
            updtMasterFormPengajuan.updateColumnValue("created_at", Util.listPengajuanTimeFormat(new DateTime()));
            updtMasterFormPengajuan.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblKop", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblKop, String> updtTblKop = databaseService.getTblKopDao().updateBuilder();
            updtTblKop.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblKop.updateColumnValue("metode_penjualan", metodePenjualanValue[spnMetodePenjualan.getSelectedItemPosition()]);
            updtTblKop.updateColumnValue("metode_penjualan_position", spnMetodePenjualan.getSelectedItemPosition());
            updtTblKop.updateColumnValue("status_customer", (strStatusPengajuan == null) ? "" : strStatusPengajuan);
            updtTblKop.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblKop", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblDataPribadi, String> updtTblDataPribadi = databaseService.getTblDataPribadiDao().updateBuilder();
            updtTblDataPribadi.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblDataPribadi.updateColumnValue("no_ktp", (edtNoKtpPribadi.getText() == null) ? "" : edtNoKtpPribadi.getText().toString());
            if (!formType.equals("draft_baru"))
                updtTblDataPribadi.updateColumnValue("no_npwp", String.format("%s%s", "", edtNoNpwpPribadi.getRawText()));
            updtTblDataPribadi.updateColumnValue("status_pernikahan", statusPernikahanValue[spnPernikahanPribadi.getSelectedItemPosition()]);
            updtTblDataPribadi.updateColumnValue("status_pernikahan_position", spnPernikahanPribadi.getSelectedItemPosition());
            updtTblDataPribadi.updateColumnValue("tanggal_lahir", (edtTanggalLahirPribadi.getText() == null) ? "" : edtTanggalLahirPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("nomor_handphone", (edtHandphonePribadi.getText() == null) ? "" : edtHandphonePribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("nama_lengkap", (edtNamaPribadi.getText() == null) ? "" : edtNamaPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("nama_ktp", (edtNamaKtpPribadi.getText() == null) ? "" : edtNamaKtpPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("tanngal_terbit_ktp", (edtTerbitKtpPribadi.getText() == null) ? "" : edtTerbitKtpPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("jenis_kelamin", genderValue[spnGenderPribadi.getSelectedItemPosition()]);
            updtTblDataPribadi.updateColumnValue("jenis_kelamin_position", spnGenderPribadi.getSelectedItemPosition());
            updtTblDataPribadi.updateColumnValue("tempat_lahir", (edtTempatLahirPribadi.getText() == null) ? "" : edtTempatLahirPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("nama_ibu_kandung", (edtNamaIbuPribadi.getText() == null) ? "" : edtNamaIbuPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("status_pendidikan", pendidikanValue[spnPendidikanPribadi.getSelectedItemPosition()]);
            updtTblDataPribadi.updateColumnValue("status_pendidikan_position", spnPendidikanPribadi.getSelectedItemPosition());
            updtTblDataPribadi.updateColumnValue("status_rumah", statusRumahValue[spnStatusRumahPribadi.getSelectedItemPosition()]);
            updtTblDataPribadi.updateColumnValue("status_rumah_position", spnStatusRumahPribadi.getSelectedItemPosition());
            updtTblDataPribadi.updateColumnValue("tinggal_sejak_tahun", (edtTinggalSejakTahunPribadi.getText() == null) ? "" : edtTinggalSejakTahunPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("tinggal_sejak_bulan", (edtTinggalSejakBulanPribadi.getText() == null) ? "" : edtTinggalSejakBulanPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("agama", agamaValue[spnAgamaPribadi.getSelectedItemPosition()]);
            updtTblDataPribadi.updateColumnValue("agama_position", spnAgamaPribadi.getSelectedItemPosition());
            updtTblDataPribadi.updateColumnValue("jumlah_tanggungan", (edtJumlahTanggunganPribadi.getText() == null) ? "" : edtJumlahTanggunganPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("email", (edtEmailPribadi.getText() == null) ? "" : edtEmailPribadi.getText().toString());
            updtTblDataPribadi.updateColumnValue("warga_negara", wargaNegaraValue[spnWargaNegaraPribadi.getSelectedItemPosition()]);
            updtTblDataPribadi.updateColumnValue("warga_negara_position", spnWargaNegaraPribadi.getSelectedItemPosition());
            updtTblDataPribadi.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblDataPribadi", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblDataPasangan, String> updtTblDataPasangan = databaseService.getTblDataPasanganDao().updateBuilder();
            updtTblDataPasangan.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblDataPasangan.updateColumnValue("id_pasangan", (""));
            updtTblDataPasangan.updateColumnValue("nama_lengkap", (edtNamaPasangan.getText() == null) ? "" : edtNamaPasangan.getText().toString());
            updtTblDataPasangan.updateColumnValue("nomor_ktp", (edtNoKtpPasangan.getText() == null) ? "" : edtNoKtpPasangan.getText().toString());
            updtTblDataPasangan.updateColumnValue("tempat_lahir", (edtTempatLahirPasangan.getText() == null) ? "" : edtTempatLahirPasangan.getText().toString());
            updtTblDataPasangan.updateColumnValue("tanggal_lahir", (edtTanggalLahirPasangan.getText() == null) ? "" : edtTanggalLahirPasangan.getText().toString());
            updtTblDataPasangan.updateColumnValue("kota_pasangan", (strCityPasangan == null) ? "" : strCityPasangan);
            updtTblDataPasangan.updateColumnValue("kecamatan_pasangan", (strKecamatanPasangan == null) ? "" : strKecamatanPasangan);
            updtTblDataPasangan.updateColumnValue("kelurahan_pasangan", (strKelurahanPasangan == null) ? "" : strKelurahanPasangan);
            updtTblDataPasangan.updateColumnValue("zipcode_pasangan", (strZipCodePasangan == null) ? "" : strZipCodePasangan);
            updtTblDataPasangan.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblDataPasangan", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblAlamat, String> updtTblAlamat = databaseService.getTblAlamatDao().updateBuilder();
            updtTblAlamat.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblAlamat.updateColumnValue("alamat_tinggal", (edtAlamatTinggal.getText() == null) ? "" : edtAlamatTinggal.getText().toString());
            updtTblAlamat.updateColumnValue("rt_tinggal", (edtRtTinggal.getText() == null) ? "" : edtRtTinggal.getText().toString());
            updtTblAlamat.updateColumnValue("rw_tinggal", (edtRwTinggal.getText() == null) ? "" : edtRwTinggal.getText().toString());
            updtTblAlamat.updateColumnValue("kota_tinggal", (strCityAlamatPemohon == null) ? "" : strCityAlamatPemohon);
            updtTblAlamat.updateColumnValue("kecamatan_tinggal", (strKecamatanAlamatPemohon == null) ? "" : strKecamatanAlamatPemohon);
            updtTblAlamat.updateColumnValue("kelurahan_tinggal", (strKelurahanAlamatPemohon == null) ? "" : strKelurahanAlamatPemohon);
            updtTblAlamat.updateColumnValue("zipcode_tinggal", (strZipCodeAlamatPemohon == null) ? "" : strZipCodeAlamatPemohon);
            updtTblAlamat.updateColumnValue("kode_area_telepon_tinggal", (edtAreaPhoneTinggal.getText() == null) ? "" : edtAreaPhoneTinggal.getText().toString());
            updtTblAlamat.updateColumnValue("nomor_telepon_tinggal", (edtPhoneTinggal.getText() == null) ? "" : edtPhoneTinggal.getText().toString());
            updtTblAlamat.updateColumnValue("alamat_ktp", (edtAlamatKtp.getText() == null) ? "" : edtAlamatKtp.getText().toString());
            updtTblAlamat.updateColumnValue("rt_ktp", (edtRtKtp.getText() == null) ? "" : edtRtKtp.getText().toString());
            updtTblAlamat.updateColumnValue("rw_ktp", (edtRwKtp.getText() == null) ? "" : edtRwKtp.getText().toString());
            updtTblAlamat.updateColumnValue("kota_ktp", (strCityKtpAlamatPemohon == null) ? "" : strCityKtpAlamatPemohon);
            updtTblAlamat.updateColumnValue("kecamatan_ktp", (strKecamatanKtpAlamatPemohon == null) ? "" : strKecamatanKtpAlamatPemohon);
            updtTblAlamat.updateColumnValue("kelurahan_ktp", (strKelurahanKtpAlamatPemohon == null) ? "" : strKelurahanKtpAlamatPemohon);
            updtTblAlamat.updateColumnValue("zipcode_ktp", (strZipCodeKtpAlamatPemohon == null) ? "" : strZipCodeKtpAlamatPemohon);
            updtTblAlamat.updateColumnValue("kode_area_telepon_ktp", (edtAreaPhoneKtp.getText() == null) ? "" : edtAreaPhoneKtp.getText().toString());
            updtTblAlamat.updateColumnValue("nomor_telepon_ktp", (edtPhoneKtp.getText() == null) ? "" : edtPhoneKtp.getText().toString());
            updtTblAlamat.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblAlamat", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblKontakDarurat, String> updtTblKontakDarurat = databaseService.getTblKontakDaruratDao().updateBuilder();
            updtTblKontakDarurat.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblKontakDarurat.updateColumnValue("nama_lengkap", (edtNamaKerabat.getText() == null) ? "" : edtNamaKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("hubungan_kerabat", spnHubunganKerabat.getSelectedItem().toString());
            updtTblKontakDarurat.updateColumnValue("hubungan_kerabat_position", spnHubunganKerabat.getSelectedItemPosition());
            updtTblKontakDarurat.updateColumnValue("alamat", (edtAlamatKerabat.getText() == null) ? "" : edtAlamatKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("rt", (edtRtKerabat.getText() == null) ? "" : edtRtKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("rw", (edtRwKerabat.getText() == null) ? "" : edtRwKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("kota", (strCityAlamatKerabat == null) ? "" : strCityAlamatKerabat);
            updtTblKontakDarurat.updateColumnValue("kecamatan", (strKecamatanAlamatKerabat == null) ? "" : strKecamatanAlamatKerabat);
            updtTblKontakDarurat.updateColumnValue("kelurahan", (strKelurahanAlamatKerabat == null) ? "" : strKelurahanAlamatKerabat);
            updtTblKontakDarurat.updateColumnValue("zipcode", (strZipCodeAlamatKerabat == null) ? "" : strZipCodeAlamatKerabat);
            updtTblKontakDarurat.updateColumnValue("kode_area_telepon_rumah", (edtAreaPhoneRumahKerabat.getText() == null) ? "" : edtAreaPhoneRumahKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("nomor_telepon_rumah", (edtPhoneRumahKerabat.getText() == null) ? "" : edtPhoneRumahKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("kode_area_telepon_kantor", (edtAreaPhoneKantorKerabat.getText() == null) ? "" : edtAreaPhoneKantorKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("nomor_telepon_kantor", (edtPhoneKantorKerabat.getText() == null) ? "" : edtPhoneKantorKerabat.getText().toString());
            updtTblKontakDarurat.updateColumnValue("nomor_handphone", (edtHpKerabat.getText() == null) ? "" : edtHpKerabat.getText().toString());
            updtTblKontakDarurat.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblKontakDarurat", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblDataPekerjaan, String> updtTblDataPekerjaan = databaseService.getTblDataPekerjaanDao().updateBuilder();
            updtTblDataPekerjaan.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblDataPekerjaan.updateColumnValue("nama_perubahaan", (edtNamaPerusahaan.getText() == null) ? "" : edtNamaPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("alamat", (edtAlamatPerusahaan.getText() == null) ? "" : edtAlamatPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("rt", (edtRtPerusahaan.getText() == null) ? "" : edtRtPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("rw", (edtRwPerusahaan.getText() == null) ? "" : edtRwPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("kota", (strCityAlamatPekerjaan == null) ? "" : strCityAlamatPekerjaan);
            updtTblDataPekerjaan.updateColumnValue("kecamatan", (strKecamatanAlamatPekerjaan == null) ? "" : strKecamatanAlamatPekerjaan);
            updtTblDataPekerjaan.updateColumnValue("kelurahan", (strKelurahanAlamatPekerjaan == null) ? "" : strKelurahanAlamatPekerjaan);
            updtTblDataPekerjaan.updateColumnValue("zipcode", (strZipCodeAlamatPekerjaan == null) ? "" : strZipCodeAlamatPekerjaan);
            updtTblDataPekerjaan.updateColumnValue("kode_area_telepon", (edtAreaPhonePerusahaan.getText() == null) ? "" : edtAreaPhonePerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("nomor_telepon", (edtPhonePerusahaan.getText() == null) ? "" : edtPhonePerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("bekerja_sejak", (edtBekerjaSejakPerusahaan.getText() == null) ? "" : edtBekerjaSejakPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("kode_profesi", (professionKode == null) ? "" : professionKode);
            updtTblDataPekerjaan.updateColumnValue("profesi", (actProfesiPerusahaan.getText() == null) ? "" : actProfesiPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("kode_tipe_pekerjaan", (jobTypeKode == null) ? "" : jobTypeKode);
            updtTblDataPekerjaan.updateColumnValue("tipe_pekerjaan", (actJobTypePerusahaan.getText() == null) ? "" : actJobTypePerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("kode_posisi_pekerjaan", (jobPositionKode == null) ? "" : jobPositionKode);
            updtTblDataPekerjaan.updateColumnValue("posisi_pekerjaan", (actJobPositionPerusahaan.getText() == null) ? "" : actJobPositionPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("kode_industri", (industriKode == null) ? "" : industriKode);
            updtTblDataPekerjaan.updateColumnValue("industri", (actIndustriPerusahaan.getText() == null) ? "" : actIndustriPerusahaan.getText().toString());
            updtTblDataPekerjaan.updateColumnValue("penghasilan_tetap", (edtPenghasilanTetapPerusahaan.getText() == null) ? "" : edtPenghasilanTetapPerusahaan.getText().toString().replace(",", ""));
            updtTblDataPekerjaan.updateColumnValue("penghasilan_lain", (edtPenghasilanLainPerusahaan.getText() == null) ? "" : edtPenghasilanLainPerusahaan.getText().toString().replace(",", ""));
            updtTblDataPekerjaan.updateColumnValue("penghasilan_pasangan", (edtPenghasilanPasanganPerusahaan.getText() == null) ? "" : edtPenghasilanPasanganPerusahaan.getText().toString().replace(",", ""));
            updtTblDataPekerjaan.updateColumnValue("biaya_hidup", (edtBiayaHidupPerusahaan.getText() == null) ? "" : edtBiayaHidupPerusahaan.getText().toString().replace(",", ""));
            updtTblDataPekerjaan.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblDataPekerjaan", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblDataKartuKredit, String> updtTblDataKartuKredit = databaseService.getTblDataKartuKreditDao().updateBuilder();
            updtTblDataKartuKredit.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblDataKartuKredit.updateColumnValue("nama_bank", (edtNamaBankKartuKredit.getText() == null) ? "" : edtNamaBankKartuKredit.getText().toString());
            updtTblDataKartuKredit.updateColumnValue("no_kartu_kredit", (edtNoKartuKredit.getText() == null) ? "" : edtNoKartuKredit.getText().toString());
            updtTblDataKartuKredit.updateColumnValue("jenis_kartu_kredit", (edtJenisKartuKredit.getText() == null) ? "" : edtJenisKartuKredit.getText().toString());
            updtTblDataKartuKredit.updateColumnValue("limit_kartu_kredit", (edtLimitKartuKredit.getText() == null) ? "" : edtLimitKartuKredit.getText().toString().replace(",", ""));
            updtTblDataKartuKredit.updateColumnValue("tahun_kadaluarsa_kartu_kredit", (edtTahunKadaluarsaKartuKredit.getText() == null) ? "" : edtTahunKadaluarsaKartuKredit.getText().toString());
            updtTblDataKartuKredit.updateColumnValue("bulan_kadaluarsa_kartu_kredit", (edtBulanKadaluarsaKartuKredit.getText() == null) ? "" : edtBulanKadaluarsaKartuKredit.getText().toString());
            updtTblDataKartuKredit.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblDataKartuKredit", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblKartuMembership, String> updtTblKartuMembership = databaseService.getTblKartuMembershipDao().updateBuilder();
            updtTblKartuMembership.where().eq("pengajuan_id", draftMasterFormPengajuan);

            updtTblKartuMembership.updateColumnValue("no_membership", (edtNoMembership.getText() == null) ? "" : edtNoMembership.getText().toString());
            updtTblKartuMembership.updateColumnValue("tanggal_efektif", (edtTanggalEfektif.getText() == null) ? "" : edtTanggalEfektif.getText().toString());
            updtTblKartuMembership.updateColumnValue("tanggal_exipred", (edtTanggalExpired.getText() == null) ? "" : edtTanggalExpired.getText().toString());
            updtTblKartuMembership.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblKartuMembership", String.valueOf(e));
            Crashlytics.logException(e);
        }

        if (!formType.equals("draft_baru")) {
            try {
                strJumlahAsset = String.valueOf(llParentTotalAsset.getChildCount());
                UpdateBuilder<TblDetailProduct, String> updtTblDetailProduct = databaseService.getTblDetailProductDao().updateBuilder();
                updtTblDetailProduct.where().eq("pengajuan_id", draftMasterFormPengajuan);
                updtTblDetailProduct.updateColumnValue("kode_supplier", (supplierKode == null) ? "" : supplierKode);
                updtTblDetailProduct.updateColumnValue("supplier", (actSupplierAsset.getText() == null) ? "" : actSupplierAsset.getText().toString());
                updtTblDetailProduct.updateColumnValue("kode_marketing_supplier", (marketingKode == null) ? "" : marketingKode);
                updtTblDetailProduct.updateColumnValue("marketing_supplier", (actMarketingSupplierAsset.getText() == null) ? "" : actMarketingSupplierAsset.getText().toString());
                updtTblDetailProduct.updateColumnValue("kode_product_id", (productId == null) ? "" : productId);
                updtTblDetailProduct.updateColumnValue("kode_product_offering_id", (productOfferingId == null) ? "" : productOfferingId);
                updtTblDetailProduct.updateColumnValue("product_offering", (actProductOfferingProduct.getText() == null) ? "" : actProductOfferingProduct.getText().toString());
                updtTblDetailProduct.updateColumnValue("pos_id", (posKode == null) ? "" : posKode);
                updtTblDetailProduct.updateColumnValue("pos", (actPosProduct.getText() == null) ? "" : actPosProduct.getText().toString());
                updtTblDetailProduct.updateColumnValue("jumlah_asset", (strJumlahAsset == null) ? "" : strJumlahAsset);
                updtTblDetailProduct.update();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("updtTblDetailProduct", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }

        if (!formType.equals("draft_baru")) {
            List<AssetElektronik> assetElektronikList = new ArrayList<>();
            for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
                View view = llParentTotalAsset.getChildAt(i);

                NiceAutoCompleteTextView actNamaBarang = view.findViewById(R.id.act_nama_barang_asset);
                EditText actTypeKendaraan = view.findViewById(R.id.edt_type_asset);
                EditText edtPrice = view.findViewById(R.id.edt_price_asset);
                EditText edtDp = view.findViewById(R.id.edt_dp_asset);
                EditText edtDiscount = view.findViewById(R.id.edt_discount_asset);

                AssetElektronik assetElektronik = new AssetElektronik();
                assetElektronik.setMasterFormPengajuan(draftMasterFormPengajuan);
                assetElektronik.setKodeBarang((actNamaBarang.getTag() == null) ? "" : (String) actNamaBarang.getTag());
                assetElektronik.setNamaBarang((actNamaBarang.getText() == null) ? "" : actNamaBarang.getText().toString());
                assetElektronik.setType((actTypeKendaraan.getText() == null) ? "" : actTypeKendaraan.getText().toString());
                assetElektronik.setPrice((edtPrice.getText() == null) ? "" : edtPrice.getText().toString().replace(",", ""));
                assetElektronik.setDp((edtDp.getText() == null) ? "" : edtDp.getText().toString().replace(",", ""));
                assetElektronik.setDiscount((edtDiscount.getText() == null) ? "" : edtDiscount.getText().toString().replace(",", ""));
                assetElektronikList.add(assetElektronik);

                try {
                    databaseService.getAssetDao().create(assetElektronik);
                } catch (SQLException e) {
                    if (BuildConfig.DEBUG) Log.e("Create asset", String.valueOf(e));
                    Crashlytics.logException(e);
                }
            }
        }

        try {
            UpdateBuilder<TblAsuransi, String> updtTblAsuransi = databaseService.getTblAsuransiDao().updateBuilder();
            updtTblAsuransi.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblAsuransi.updateColumnValue("manual_agunan", manualAgunan);
            updtTblAsuransi.updateColumnValue("manual_premi", String.valueOf(manualPremi));
            updtTblAsuransi.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblAsuransi", String.valueOf(e));
            Crashlytics.logException(e);
        }

        if (!formType.equals("draft_baru")) {
            try {
                UpdateBuilder<TblDataPerhitungan, String> updtTblDataPerhitungan = databaseService.getTblDataPerhitunganDao().updateBuilder();
                updtTblDataPerhitungan.where().eq("pengajuan_id", draftMasterFormPengajuan);
                updtTblDataPerhitungan.updateColumnValue("tenor", spnTenorProduct.getSelectedItem().toString());
                updtTblDataPerhitungan.updateColumnValue("posisi_tenor", spnTenorProduct.getSelectedItemPosition());
                updtTblDataPerhitungan.updateColumnValue("flate_rate", (edtFlatRatePerhitungan.getText() == null) ? "" : edtFlatRatePerhitungan.getText().toString());
                updtTblDataPerhitungan.updateColumnValue("biaya_administrasi", (edtBiayaAdminPerhitungan.getText() == null) ? "" : edtBiayaAdminPerhitungan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("biaya_lainnya", (edtBiayaLainnya.getText() == null) ? "" : edtBiayaLainnya.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("refund_subsidi", (edtRefundSubsidiPerhitungan.getText() == null) ? "" : edtRefundSubsidiPerhitungan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("bebas_bunga", (edtBebasBungaPerhitungan.getText() == null) ? "" : edtBebasBungaPerhitungan.getText().toString());
                updtTblDataPerhitungan.updateColumnValue("total_price", (edtPurchasePricePerhitungan.getText() == null) ? "" : edtPurchasePricePerhitungan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("total_discount", (edtDiscountPerhitungan.getText() == null) ? "" : edtDiscountPerhitungan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("total_dp", (edtDpPerhitungan.getText() == null) ? "" : edtDpPerhitungan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("premi", (edtPremiAsuransi.getText() == null) ? "" : edtPremiAsuransi.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("ntf", (edtNtfPerhitungan.getText() == null) ? "" : edtNtfPerhitungan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("jumlah_pembiayaan", (edtJumlahPembiayaan.getText() == null) ? "" : edtJumlahPembiayaan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("total_bunga_pembiayaan", (edtTotalBungaPembiayaan.getText() == null) ? "" : edtTotalBungaPembiayaan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("bulan_bunga_pembiayaan", (edtBungaPembiayaanBulan.getText() == null) ? "" : edtBungaPembiayaanBulan.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("total_pinjaman", (edtTotalPinjaman.getText() == null) ? "" : edtTotalPinjaman.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("angsuran_perbulan", (edtAngsuranPerbulan.getText() == null) ? "" : edtAngsuranPerbulan.getText().toString().replace(",", ""));
                if (edtBebasBungaPerhitungan.getText().toString().equals("0"))
                    updtTblDataPerhitungan.updateColumnValue("angsuran_prebulan_bebas_bunga", (edtAngsuranPerbulan.getText() == null) ? "" : edtAngsuranPerbulan.getText().toString().replace(",", ""));
                else
                    updtTblDataPerhitungan.updateColumnValue("angsuran_prebulan_bebas_bunga", (edtAngsuranPerbulanBebasBunga.getText() == null) ? "" : edtAngsuranPerbulanBebasBunga.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("pembayaran_awal", (edtPembayaranPertama.getText() == null) ? "" : edtPembayaranPertama.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("pembayaran_delaer", (edtPembayaranDelaer.getText() == null) ? "" : edtPembayaranDelaer.getText().toString().replace(",", ""));
                updtTblDataPerhitungan.updateColumnValue("effective_rate", 0);
                updtTblDataPerhitungan.updateColumnValue("admin_fee_lainnya", String.valueOf(nominalAdminLain));
                updtTblDataPerhitungan.updateColumnValue("tipe_pembayaran", fsInstallment);
                updtTblDataPerhitungan.update();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("updtTblDataPerhitungan", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }

        try {
            UpdateBuilder<TblKeterangan, String> updtTblKeterangan = databaseService.getTblKeteranganDao().updateBuilder();
            updtTblKeterangan.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblKeterangan.updateColumnValue("keterangan", (edtKeterangan.getText() == null) ? "" : edtKeterangan.getText().toString());
            updtTblKeterangan.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblKeterangan", String.valueOf(e));
            Crashlytics.logException(e);
        }

        for (Object o : mHashMapAttachmentFiles.keySet()) {
            int i = (int) o;
            Attachment attachment = new Attachment();
            attachment.setMasterFormPengajuan(draftMasterFormPengajuan);
            attachment.setNamaAttachment("eform" + System.currentTimeMillis());
            attachment.setPath(mHashMapAttachmentFiles.get(i).getAbsolutePath());
            attachment.setPath2(mHashMapAttachmentFiles.get(i).getPath());
            attachment.setKey(i);

            try {
                databaseService.getAttachmentDao().create(attachment);
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("updtAttachment", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }

        if (!formType.equals("draft_baru")) {
            try {
                UpdateBuilder<TblTandaTangan, String> updtTblTandaTangan = databaseService.getTblTandaTanganDao().updateBuilder();
                updtTblTandaTangan.where().eq("pengajuan_id", draftMasterFormPengajuan);
                updtTblTandaTangan.updateColumnValue("ttd_pemohon", Util.bitmapToBase64(bitmapTtdPemohon));
                updtTblTandaTangan.updateColumnValue("ttd_pasangan", Util.bitmapToBase64(bitmapTtdPasangan));
                updtTblTandaTangan.updateColumnValue("jumlah_ttd", countSignature);
                updtTblTandaTangan.update();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("updtTblTandaTangan", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }

        try {
            UpdateBuilder<TblRekomendasi, String> updtTblRekomendasi = databaseService.getTblRekomendasiDao().updateBuilder();
            updtTblRekomendasi.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtTblRekomendasi.updateColumnValue("jabar", 0);
            updtTblRekomendasi.updateColumnValue("id_rekomendasi", recomendationId);
            updtTblRekomendasi.updateColumnValue("catatan", (edtDescRecomendation.getText() == null) ? "" : edtDescRecomendation.getText().toString());
            updtTblRekomendasi.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtTblRekomendasi", String.valueOf(e));
            Crashlytics.logException(e);
        }
        try {
            UpdateBuilder<TblLokasi, String> updtLokasi = databaseService.getTblLokasiDao().updateBuilder();
            updtLokasi.where().eq("pengajuan_id", draftMasterFormPengajuan);
            updtLokasi.updateColumnValue("validation_action", (validateAction.getText() == null) ? "" : validateAction.getText().toString());
            updtLokasi.updateColumnValue("validation_longitude", (validateLongitude.getText() == null) ? "" : validateLongitude.getText().toString());
            updtLokasi.updateColumnValue("validation_latitude", (validateLatitude.getText() == null) ? "" : validateLatitude.getText().toString());
            updtLokasi.updateColumnValue("ktp_action", (takeKtpAction.getText() == null) ? "" : takeKtpAction.getText().toString());
            updtLokasi.updateColumnValue("ktp_longitude", (takeKtpLongitude.getText() == null) ? "" : takeKtpLongitude.getText().toString());
            updtLokasi.updateColumnValue("ktp_latitude", (takeKtpLatitude.getText() == null) ? "" : takeKtpLatitude.getText().toString());
            updtLokasi.updateColumnValue("customer_action", (takeCustomerAction.getText() == null) ? "" : takeCustomerAction.getText().toString());
            updtLokasi.updateColumnValue("customer_longitude", (takeCustomerLongitude.getText() == null) ? "" : takeCustomerLongitude.getText().toString());
            updtLokasi.updateColumnValue("customer_latitude", (takeCustomerLatitude.getText() == null) ? "" : takeCustomerLatitude.getText().toString());
            updtLokasi.updateColumnValue("paycheck_action", (takePaycheckAction.getText() == null) ? "" : takePaycheckAction.getText().toString());
            updtLokasi.updateColumnValue("paycheck_longitude", (takePaycheckLongitude.getText() == null) ? "" : takePaycheckLongitude.getText().toString());
            updtLokasi.updateColumnValue("paycheck_latitude", (takePaycheckLatitude.getText() == null) ? "" : takePaycheckLatitude.getText().toString());
            updtLokasi.updateColumnValue("addtional_documents_action", (takeAdditionalDocumentsAction.getText() == null) ? "" : takeAdditionalDocumentsAction.getText().toString());
            updtLokasi.updateColumnValue("addtional_documents_longitude", (takeAdditionalDocumentsLongitude.getText() == null) ? "" : takeAdditionalDocumentsLongitude.getText().toString());
            updtLokasi.updateColumnValue("addtional_documents_latitude", (takeAdditionalDocumentsLatitude.getText() == null) ? "" : takeAdditionalDocumentsLatitude.getText().toString());
            updtLokasi.updateColumnValue("signature_action", (takeSignatureAction.getText() == null) ? "" : takeSignatureAction.getText().toString());
            updtLokasi.updateColumnValue("signature_longitude", (takeSignatureLongitude.getText() == null) ? "" : takeSignatureLongitude.getText().toString());
            updtLokasi.updateColumnValue("signature_latitude", (takeSignatureLatitude.getText() == null) ? "" : takeSignatureLatitude.getText().toString());
            updtLokasi.updateColumnValue("submit_action", (submitAction.getText() == null) ? "" : submitAction.getText().toString());
            updtLokasi.updateColumnValue("submit_longitude", (submitLongitude.getText() == null) ? "" : submitLongitude.getText().toString());
            updtLokasi.updateColumnValue("submit_latitude", (submitLatitude.getText() == null) ? "" : submitLatitude.getText().toString());
            updtLokasi.update();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("updtLokasi", String.valueOf(e));
            Crashlytics.logException(e);
        }

        deleteDataConfins();
        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                if (viewDataPribadi.contains(view))
                    imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDataPasanganElc.contains(view))
                    imgDropDownPasangan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewKartuKredit.contains(view))
                    imgDropDownDataKartuKredit.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDataMembership.contains(view))
                    imgDropDownDataKartuMembership.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewDetailProductElc.contains(view))
                    imgDropDownProduct.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewPerhitungan.contains(view))
                    imgDropDownPerhitungan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewsAssetNamaBarang.contains(view))
                    imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewsAssetTipeBarang.contains(view))
                    imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewsAssetHarga.contains(view))
                    imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewsAssetDp.contains(view))
                    imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewsAssetDiscount.contains(view))
                    imgDropDownAsset.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewKerabat.contains(view))
                    imgDropDownKerabat.setImageResource(android.R.drawable.ic_dialog_alert);
                if (viewAlamat.contains(view)) {
                    imgDropDownAlamat.setImageResource(android.R.drawable.ic_dialog_alert);
//                    Toast.makeText(this, error.getView().toString(), Toast.LENGTH_SHORT).show();
                }

                if (viewPekerjaan.contains(view))
                    imgDropDownPekerjaan.setImageResource(android.R.drawable.ic_dialog_alert);

                if (otr_price >= 50000000) {
                   /* if(viewDataPribadiWithNpwp.contains(view)){
                        imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
                    }*/

                    if (view == edtNoNpwpDetail) {
                        imgDropDownNpwp.setImageResource(android.R.drawable.ic_dialog_alert);
                    }
                }
            }
            if (view instanceof ImageView) {
                if (viewSignature.contains(view))
                    imgDropDownPersetujuan.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == imgTtdPemohonPersetujuan)
                    txtTtdPemohonError.setVisibility(View.VISIBLE);
                else if (view == imgTtdPasanganPersetujuan)
                    txtTtdPasanganError.setVisibility(View.VISIBLE);

                if (viewAttachment.contains(view))
                    imgDropDownAttachment.setImageResource(android.R.drawable.ic_dialog_alert);
                if (view == imgTakePicture1) tvCamera1.setTextColor(Color.RED);
                if (view == imgTakePicture2) tvCamera2.setTextColor(Color.RED);
                if (view == imgTakePicture3) tvCamera3.setTextColor(Color.RED);
                if (view == imgTakePicture4) tvCamera4.setTextColor(Color.RED);
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
                    case R.id.act_product_offering_product:
                        actProductOfferingProduct.showDropDown();
                        break;
                   /*case R.id.act_supplier_asset:
                        actSupplierAsset.showDropDown();
                        break;*/
                    case R.id.act_marketing_supplier_asset:
                        actMarketingSupplierAsset.showDropDown();
                        break;
                    case R.id.act_pos_product:
                        actPosProduct.showDropDown();
                        break;
                }
            }
        }
    };

    private OnItemSelectedListenerCustome.CustomeOnItemSelected implementOnItemSelected = new OnItemSelectedListenerCustome.CustomeOnItemSelected() {
        @Override
        public void onItemSelected(View parentView, AdapterView<?> parent, View view, int position, long id) {
            switch (parentView.getId()) {
                case R.id.spn_tenor_product:
                    // TODO: Comment Calculating
                    prepareDataCalculating();
                    /*calculating();*/
                    break;
            }
        }
    };

    @Override
    public void onPreLoadSyarat() {
        preLoading();
        // progressDialog.show();
        // progressDialog.setMessage("Please wait ...");
    }

    @Override
    public void onSuccessSyarat(Syarat syarats, TidakCancel tidakCancels, FaqObjt Faq) {
        // progressDialog.dismiss();
        successAndFailedLoading();
        descriptionCancel = syarats.getDescription();
        descriptionSyarat = tidakCancels.getDescription();
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

    @Override
    public void onFailedLoadSyarat(String message) {
        // progressDialog.dismiss();
        Toast.makeText(this, "Sorry Please Try Again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSyaratTokenExpired() {
        String tokenStr = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(tokenStr);
        tokenExpiredType = tokenTypeSyarat;
    }

    @Override
    public void onPreLoadPengajuanDraft() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideAllLoading() {
        llLoading.setVisibility(View.GONE);
        tvPbh.setVisibility(View.GONE);
        pbHorizontal.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessLoadPengajuanDraft(MasterFormPengajuan
                                                    mMasterFormPengajuan, List<TblLokasi> tblLokasiList, List<AssetElektronik> assetElektronikList, List<Attachment> attachmentList, List<MaskingTenor> maskingTenorList, List<MaskingRate> maskingRateList) {
        String draftEdit = mMasterFormPengajuan.getTipeSaveData();
        this.draftEdit = draftEdit;
        this.draftMasterFormPengajuan = mMasterFormPengajuan;
        if (mMasterFormPengajuan.getTipeSaveData().equals("draft_edit"))
            disAbleFieldAssignEdit();

        List<TblKop> tblKops = new ArrayList<>();
        List<TblDataPribadi> tblDataPribadis = new ArrayList<>();
        List<TblDataPasangan> tblDataPasangans = new ArrayList<>();
        List<TblAlamat> tblAlamats = new ArrayList<>();
        List<TblKontakDarurat> tblKontakDarurats = new ArrayList<>();
        List<TblDataPekerjaan> tblDataPekerjaans = new ArrayList<>();
        List<TblDataKartuKredit> tblDataKartuKredits = new ArrayList<>();
        List<TblKartuMembership> tblKartuMemberships = new ArrayList<>();
        List<TblDetailProduct> tblDetailProducts = new ArrayList<>();
        List<TblAsuransi> tblAsuransis = new ArrayList<>();
        List<TblDataPerhitungan> tblDataPerhitungans = new ArrayList<>();
        List<TblKeterangan> tblKeterangans = new ArrayList<>();
        List<TblRekomendasi> tblRekomendasis = new ArrayList<>();
        List<TblLokasi> tblLokasis = new ArrayList<>();
        List<TblAgunan> tblAgunans = new ArrayList<>();
        List<TblTandaTangan> tblTandaTangans = new ArrayList<>();
        List<AssetElektronik> assetElektroniks = new ArrayList<>();
        List<Attachment> attachments = new ArrayList<>();

        try {
            tblKops = databaseService.getTblKopDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblDataPribadis = databaseService.getTblDataPribadiDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblDataPasangans = databaseService.getTblDataPasanganDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblAlamats = databaseService.getTblAlamatDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblKontakDarurats = databaseService.getTblKontakDaruratDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblDataPekerjaans = databaseService.getTblDataPekerjaanDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblDataKartuKredits = databaseService.getTblDataKartuKreditDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblKartuMemberships = databaseService.getTblKartuMembershipDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblDetailProducts = databaseService.getTblDetailProductDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblAsuransis = databaseService.getTblAsuransiDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblDataPerhitungans = databaseService.getTblDataPerhitunganDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblKeterangans = databaseService.getTblKeteranganDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblRekomendasis = databaseService.getTblRekomendasiDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblLokasis = databaseService.getTblLokasiDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblAgunans = databaseService.getTblAgunanDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            tblTandaTangans = databaseService.getTblTandaTanganDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            assetElektroniks = databaseService.getAssetDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
            attachments = databaseService.getAttachmentDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuan).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("tblRelasi", String.valueOf(e));
            Crashlytics.logException(e);
        }

        pengajuanType = mMasterFormPengajuan.getTipePengajuan();
        mobileSubmissionKey = mMasterFormPengajuan.getMobileSubmissionKey();
        uuid = mMasterFormPengajuan.getUuid();
        blackListDate = mMasterFormPengajuan.getBlacklistDate();
        aoBranch = "00".equalsIgnoreCase(mMasterFormPengajuan.getBranch()) ? mMasterFormPengajuan.getMasterBranch() : mMasterFormPengajuan.getBranch(); // handle pengajuan yang belum ada lintas cabang
        branchMaster = mMasterFormPengajuan.getMasterBranch();
        customerIdConfins = mMasterFormPengajuan.getCustomerIdConfins();
        typeDataOffering = mMasterFormPengajuan.getTipeDataOffering();
        strEfNumber = mMasterFormPengajuan.getEfNumber();
        applicationIdKpm = mMasterFormPengajuan.getIdKpm();
        appId = mMasterFormPengajuan.getApplicationId();
        checkTipePengajuan();

//        KOP
       /* metodePenjualan = tblKops.get(0).getMetodePenjualan();
        for (int h = 0; h < metodePenjualanValue.length; h++) {
            if (metodePenjualanValue[h].equalsIgnoreCase(tblKops.get(0).getMetodePenjualan())) {
                spnMetodePenjualan.setSelection(h);
                break;
            }
        }*/

//        DATA PRIBADI
        edtNoKtpPribadi.setText(tblDataPribadis.get(0).getNoKtp() == null ? "" : tblDataPribadis.get(0).getNoKtp());
//        edtNoNpwpPribadi.setText(tblDataPribadis.get(0).getNoNpwp() == null ? "-" : tblDataPribadis.get(0).getNoNpwp());
        edtNoNpwpDetail.setText(tblDataPribadis.get(0).getNoNpwp() == null ? "-" : tblDataPribadis.get(0).getNoNpwp());
        spnPernikahanPribadi.setSelection(tblDataPribadis.get(0).getStatusPernikahanPosition());

        if (tblDataPribadis.get(0).getStatusPernikahanPosition() == 0)
            lnWrapperDataPasangan.setVisibility(View.VISIBLE);
        else {
            lnWrapperDataPasangan.setVisibility(View.GONE);

            edtNamaPasangan.setText("");
            edtNoKtpPasangan.setText("");
            edtTempatLahirPasangan.setText("");
            edtTanggalLahirPasangan.setText("");
            edtNoHpPasangan.setText("");
        }
        isAssignEdit = true;
        edtTanggalLahirPribadi.setText(tblDataPribadis.get(0).getTanggalLahir() == null ? "" : tblDataPribadis.get(0).getTanggalLahir());
        edtHandphonePribadi.setText(tblDataPribadis.get(0).getNomorHandphone() == null ? "" : tblDataPribadis.get(0).getNomorHandphone());
        edtNamaPribadi.setText(tblDataPribadis.get(0).getNamaLengkap() == null ? "" : tblDataPribadis.get(0).getNamaLengkap());
        edtNamaKtpPribadi.setText(tblDataPribadis.get(0).getNamaKtp() == null ? "" : tblDataPribadis.get(0).getNamaKtp());
        edtTerbitKtpPribadi.setText(tblDataPribadis.get(0).getTanngalTerbitKtp() == null ? "" : tblDataPribadis.get(0).getTanngalTerbitKtp());
        spnGenderPribadi.setSelection(tblDataPribadis.get(0).getJenisKelaminPosition());
        edtTempatLahirPribadi.setText(tblDataPribadis.get(0).getTempatLahir() == null ? "" : tblDataPribadis.get(0).getTempatLahir());
        edtNamaIbuPribadi.setText(tblDataPribadis.get(0).getNamaIbuKandung() == null ? "" : tblDataPribadis.get(0).getNamaIbuKandung());
        spnPendidikanPribadi.setSelection(tblDataPribadis.get(0).getStatusPendidikanPosition());
        spnWargaNegaraPribadi.setSelection(tblDataPribadis.get(0).getWargaNegaraPosition());
        spnStatusRumahPribadi.setSelection(tblDataPribadis.get(0).getStatusRumahPosition());
        edtTinggalSejakTahunPribadi.setText(tblDataPribadis.get(0).getTinggalSejakTahun() == null ? "" : tblDataPribadis.get(0).getTinggalSejakTahun());
        edtTinggalSejakBulanPribadi.setText(tblDataPribadis.get(0).getTinggalSejakBulan() == null ? "" : tblDataPribadis.get(0).getTinggalSejakBulan());
        spnAgamaPribadi.setSelection(tblDataPribadis.get(0).getAgamaPosition());
        edtJumlahTanggunganPribadi.setText(tblDataPribadis.get(0).getJumlahTanggungan() == null ? "" : tblDataPribadis.get(0).getJumlahTanggungan());
        edtEmailPribadi.setText(tblDataPribadis.get(0).getEmail() == null ? "" : tblDataPribadis.get(0).getEmail());
        spnWargaNegaraPribadi.setSelection(tblDataPribadis.get(0).getWargaNegaraPosition());

//        DATA PASANGAN
        edtNamaPasangan.setText(tblDataPasangans.get(0).getNamaLengkap() == null ? "" : tblDataPasangans.get(0).getNamaLengkap());
        edtNoKtpPasangan.setText(tblDataPasangans.get(0).getNomorKtp() == null ? "" : tblDataPasangans.get(0).getNomorKtp());
        edtTempatLahirPasangan.setText(tblDataPasangans.get(0).getTempatLahir() == null ? "" : tblDataPasangans.get(0).getTempatLahir());
        edtTanggalLahirPasangan.setText(tblDataPasangans.get(0).getTanggalLahir() == null ? "" : tblDataPasangans.get(0).getTanggalLahir());
        edtNoHpPasangan.setText(tblDataPasangans.get(0).getNoTeleponPerusahaan() == null ? "" : tblDataPasangans.get(0).getNoTeleponPerusahaan());
        strCityPasangan = tblDataPasangans.get(0).getKotaPasangan() == null ? "" : tblDataPasangans.get(0).getKotaPasangan();
        strKecamatanPasangan = tblDataPasangans.get(0).getKecamatanPasangan() == null ? "" : tblDataPasangans.get(0).getKecamatanPasangan();
        strKelurahanPasangan = tblDataPasangans.get(0).getKelurahanPasangan() == null ? "" : tblDataPasangans.get(0).getKelurahanPasangan();
        strZipCodePasangan = tblDataPasangans.get(0).getZipcodePasangan() == null ? "" : tblDataPasangans.get(0).getZipcodePasangan();

//        ALAMAT PEMOHON DAN KTP
        edtAlamatTinggal.setText(tblAlamats.get(0).getAlamatTinggal() == null ? "" : tblAlamats.get(0).getAlamatTinggal());
        edtRtTinggal.setText(tblAlamats.get(0).getRtTinggal() == null ? "" : tblAlamats.get(0).getRtTinggal());
        edtRwTinggal.setText(tblAlamats.get(0).getRwTinggal() == null ? "" : tblAlamats.get(0).getRwTinggal());
        if (tblAlamats.get(0).getKotaTinggal() != null) {
            strCityAlamatPemohon = tblAlamats.get(0).getKotaTinggal();
            strKecamatanAlamatPemohon = tblAlamats.get(0).getKecamatanTinggal();
            strKelurahanAlamatPemohon = tblAlamats.get(0).getKelurahanTinggal();
            strZipCodeAlamatPemohon = tblAlamats.get(0).getZipcodeTinggal();
            actAutoAlamatPemohon.setText(strCityAlamatPemohon + " , " + strKecamatanAlamatPemohon + " , " + strKelurahanAlamatPemohon + " , " + strZipCodeAlamatPemohon);
            actAutoAlamatPemohon.setSelectionFromPopUp(true);
        } else {
            actAutoAlamatPemohon.setText("");
            actAutoAlamatPemohon.setSelectionFromPopUp(false);
        }
        edtAreaPhoneTinggal.setText(tblAlamats.get(0).getKodeAreaTeleponTinggal() == null ? "" : tblAlamats.get(0).getKodeAreaTeleponTinggal());
        edtPhoneTinggal.setText(tblAlamats.get(0).getNomorTeleponTinggal() == null ? "" : tblAlamats.get(0).getNomorTeleponTinggal());
        edtAlamatKtp.setText(tblAlamats.get(0).getAlamatKtp() == null ? "" : tblAlamats.get(0).getAlamatKtp());
        edtRtKtp.setText(tblAlamats.get(0).getRtKtp() == null ? "" : tblAlamats.get(0).getRtKtp());
        edtRwKtp.setText(tblAlamats.get(0).getRwKtp() == null ? "" : tblAlamats.get(0).getRwKtp());
        if (tblAlamats.get(0).getKotaKtp() != null) {
            strCityKtpAlamatPemohon = tblAlamats.get(0).getKotaKtp();
            strKecamatanKtpAlamatPemohon = tblAlamats.get(0).getKecamatanKtp();
            strKelurahanKtpAlamatPemohon = tblAlamats.get(0).getKelurahanKtp();
            strZipCodeKtpAlamatPemohon = tblAlamats.get(0).getZipcodeKtp();
            actAutoKtpAlamatPemohon.setText(strCityKtpAlamatPemohon + " , " + strKecamatanKtpAlamatPemohon + " , " + strKelurahanKtpAlamatPemohon + " , " + strZipCodeKtpAlamatPemohon);
            actAutoKtpAlamatPemohon.setSelectionFromPopUp(true);
        } else actAutoKtpAlamatPemohon.setSelectionFromPopUp(false);
        edtAreaPhoneKtp.setText(tblAlamats.get(0).getKodeAreaTeleponKtp() == null ? "" : tblAlamats.get(0).getKodeAreaTeleponKtp());
        edtPhoneKtp.setText(tblAlamats.get(0).getNomorTeleponKtp() == null ? "" : tblAlamats.get(0).getNomorTeleponKtp());

//        INFORMASI KERABAT
        edtNamaKerabat.setText(tblKontakDarurats.get(0).getNamaLengkap() == null ? "" : tblKontakDarurats.get(0).getNamaLengkap());
        spnHubunganKerabat.setSelection(tblKontakDarurats.get(0).getHubunganKerabatPosition());
        edtAlamatKerabat.setText(tblKontakDarurats.get(0).getAlamat() == null ? "" : tblKontakDarurats.get(0).getAlamat());
        edtRtKerabat.setText(tblKontakDarurats.get(0).getRt() == null ? "" : tblKontakDarurats.get(0).getRt());
        edtRwKerabat.setText(tblKontakDarurats.get(0).getRw() == null ? "" : tblKontakDarurats.get(0).getRw());
        if (tblKontakDarurats.get(0).getKota() != null) {
            strCityAlamatKerabat = tblKontakDarurats.get(0).getKota();
            strKecamatanAlamatKerabat = tblKontakDarurats.get(0).getKecamatan();
            strKelurahanAlamatKerabat = tblKontakDarurats.get(0).getKelurahan();
            strZipCodeAlamatKerabat = tblKontakDarurats.get(0).getZipcode();
            actAutoAlamatKerabat.setText(strCityAlamatKerabat + " , " + strKecamatanAlamatKerabat + " , " + strKelurahanAlamatKerabat + " , " + strZipCodeAlamatKerabat);
            actAutoAlamatKerabat.setSelectionFromPopUp(true);
        } else actAutoAlamatKerabat.setSelectionFromPopUp(false);
        edtAreaPhoneRumahKerabat.setText(tblKontakDarurats.get(0).getKodeAreaTeleponRumah() == null ? "" : tblKontakDarurats.get(0).getKodeAreaTeleponRumah());
        edtPhoneRumahKerabat.setText(tblKontakDarurats.get(0).getNomorTeleponRumah() == null ? "" : tblKontakDarurats.get(0).getNomorTeleponRumah());
        edtAreaPhoneKantorKerabat.setText(tblKontakDarurats.get(0).getKodeAreaTeleponKantor() == null ? "" : tblKontakDarurats.get(0).getKodeAreaTeleponKantor());
        edtPhoneKantorKerabat.setText(tblKontakDarurats.get(0).getNomorTeleponKantor() == null ? "" : tblKontakDarurats.get(0).getNomorTeleponKantor());
        edtHpKerabat.setText(tblKontakDarurats.get(0).getNomorHandphone() == null ? "" : tblKontakDarurats.get(0).getNomorHandphone());

//        DATA PEKERJAAN
        edtNamaPerusahaan.setText(tblDataPekerjaans.get(0).getNamaPerubahaan() == null ? "" : tblDataPekerjaans.get(0).getNamaPerubahaan());
        edtAlamatPerusahaan.setText(tblDataPekerjaans.get(0).getAlamat() == null ? "" : tblDataPekerjaans.get(0).getAlamat());
        edtRtPerusahaan.setText(tblDataPekerjaans.get(0).getRt() == null ? "" : tblDataPekerjaans.get(0).getRt());
        edtRwPerusahaan.setText(tblDataPekerjaans.get(0).getRw() == null ? "" : tblDataPekerjaans.get(0).getRw());
        if (tblDataPekerjaans.get(0).getKota() != null) {
            strCityAlamatPekerjaan = tblDataPekerjaans.get(0).getKota();
            strKecamatanAlamatPekerjaan = tblDataPekerjaans.get(0).getKecamatan();
            strKelurahanAlamatPekerjaan = tblDataPekerjaans.get(0).getKelurahan();
            strZipCodeAlamatPekerjaan = tblDataPekerjaans.get(0).getZipcode();
            actAutoAlamatPekerjaan.setText(strCityAlamatPekerjaan + " , " + strKecamatanAlamatPekerjaan + " , " + strKelurahanAlamatPekerjaan + " , " + strZipCodeAlamatPekerjaan);
            actAutoAlamatPekerjaan.setSelectionFromPopUp(true);
        } else actAutoAlamatPekerjaan.setSelectionFromPopUp(false);
        edtAreaPhonePerusahaan.setText(tblDataPekerjaans.get(0).getKodeAreaTelepon() == null ? "" : tblDataPekerjaans.get(0).getKodeAreaTelepon());
        edtPhonePerusahaan.setText(tblDataPekerjaans.get(0).getNomorTelepon() == null ? "" : tblDataPekerjaans.get(0).getNomorTelepon());
        edtBekerjaSejakPerusahaan.setText(tblDataPekerjaans.get(0).getBekerjaSejak() == null ? "" : tblDataPekerjaans.get(0).getBekerjaSejak());
        if (tblDataPekerjaans.get(0).getKodeProfesi() != null) {
            professionKode = tblDataPekerjaans.get(0).getKodeProfesi();
            List<JobType> labeljob = databaseService.getAllJobType(professionKode);
            jobTypeArrayAdapter = new ArrayAdapter<JobType>(getContext(), R.layout.item_dropdown, R.id.id_item, labeljob);
            actJobTypePerusahaan.setAdapter(jobTypeArrayAdapter);
        }
        if (tblDataPekerjaans.get(0).getProfesi() != null) {
            actProfesiPerusahaan.setText(tblDataPekerjaans.get(0).getProfesi());
            actProfesiPerusahaan.setSelectionFromPopUp(true);
        } else actProfesiPerusahaan.setSelectionFromPopUp(false);
        if (tblDataPekerjaans.get(0).getKodeTipePekerjaan() != null) {
            jobTypeKode = tblDataPekerjaans.get(0).getKodeTipePekerjaan();
            List<JobPosition> labelposition = databaseService.getAllJObPosition(jobTypeKode);
            jobPositionArrayAdapter = new ArrayAdapter<JobPosition>(getContext(), R.layout.item_dropdown, R.id.id_item, labelposition);
            actJobPositionPerusahaan.setAdapter(jobPositionArrayAdapter);
        }
        if (tblDataPekerjaans.get(0).getTipePekerjaan() != null) {
            actJobTypePerusahaan.setText(tblDataPekerjaans.get(0).getTipePekerjaan());
            actJobTypePerusahaan.setSelectionFromPopUp(true);
        } else actJobTypePerusahaan.setSelectionFromPopUp(false);
        if (tblDataPekerjaans.get(0).getKodePosisiPekerjaan() != null)
            jobPositionKode = tblDataPekerjaans.get(0).getKodePosisiPekerjaan();
        if (tblDataPekerjaans.get(0).getPosisiPekerjaan() != null) {
            actJobPositionPerusahaan.setText(tblDataPekerjaans.get(0).getPosisiPekerjaan());
            actJobPositionPerusahaan.setSelectionFromPopUp(true);
        } else actJobPositionPerusahaan.setSelectionFromPopUp(false);
        if (tblDataPekerjaans.get(0).getKodeIndustri() != null)
            industriKode = tblDataPekerjaans.get(0).getKodeIndustri();
        if (tblDataPekerjaans.get(0).getIndustri() != null) {
            actIndustriPerusahaan.setText(tblDataPekerjaans.get(0).getIndustri());
            actIndustriPerusahaan.setSelectionFromPopUp(true);
        } else actIndustriPerusahaan.setSelectionFromPopUp(false);
        edtPenghasilanTetapPerusahaan.setText(tblDataPekerjaans.get(0).getPenghasilanTetap() == null ? "" : tblDataPekerjaans.get(0).getPenghasilanTetap());
        edtPenghasilanLainPerusahaan.setText(tblDataPekerjaans.get(0).getPenghasilanLain() == null ? "" : tblDataPekerjaans.get(0).getPenghasilanLain());
        edtPenghasilanPasanganPerusahaan.setText(tblDataPekerjaans.get(0).getPenghasilanPasangan() == null ? "" : tblDataPekerjaans.get(0).getPenghasilanPasangan());
        edtBiayaHidupPerusahaan.setText(tblDataPekerjaans.get(0).getBiayaHidup() == null ? "" : tblDataPekerjaans.get(0).getBiayaHidup());


//        DATA KARTU KREDIT
        edtNamaBankKartuKredit.setText(tblDataKartuKredits.get(0).getNamaBank() == null ? "" : tblDataKartuKredits.get(0).getNamaBank());
        edtNoKartuKredit.setText(tblDataKartuKredits.get(0).getNoKartuKredit() == null ? "" : tblDataKartuKredits.get(0).getNoKartuKredit());
        edtJenisKartuKredit.setText(tblDataKartuKredits.get(0).getJenisKartuKredit() == null ? "" : tblDataKartuKredits.get(0).getJenisKartuKredit());
        edtLimitKartuKredit.setText(tblDataKartuKredits.get(0).getLimitKartuKredit() == null ? "" : tblDataKartuKredits.get(0).getLimitKartuKredit());
        edtTahunKadaluarsaKartuKredit.setText(tblDataKartuKredits.get(0).getTahunKadaluarsaKartuKredit() == null ? "" : tblDataKartuKredits.get(0).getTahunKadaluarsaKartuKredit());
        edtBulanKadaluarsaKartuKredit.setText(tblDataKartuKredits.get(0).getBulanKadaluarsaKartuKredit() == null ? "" : tblDataKartuKredits.get(0).getBulanKadaluarsaKartuKredit());

//        DATA KARTU MEMBERSHIP
        edtNoMembership.setText(tblKartuMemberships.get(0).getNoMembership() == null ? "" : tblKartuMemberships.get(0).getNoMembership());
        edtTanggalEfektif.setText(tblKartuMemberships.get(0).getTanggalEfektif() == null ? "" : tblKartuMemberships.get(0).getTanggalEfektif());
        edtTanggalExpired.setText(tblKartuMemberships.get(0).getTanggalExipred() == null ? "" : tblKartuMemberships.get(0).getTanggalExipred());

//        DETAIL PRODUCT
        aoBranch = mMasterFormPengajuan.getBranch();

        /*List<AssetMasterObjt> labelAssetMaster = databaseService.getAllAssetMaster();
        assetMasterAdapter = new ArrayAdapter<AssetMasterObjt>(getContext(), android.R.layout.simple_spinner_dropdown_item, labelAssetMaster);*/
        // TODO: Comment Asset data master
       /* assetMasterCategories = new ArrayList<>();
        for (int i = 0; i < masterSyncResponse.getAssetMasterSyncList().size(); i++) {
            AssetMasterCategory assetMasterCategory = new AssetMasterCategory();
            assetMasterCategory.setAssetCode(masterSyncResponse.getAssetMasterSyncList().get(i).getAssetCode());
            assetMasterCategory.setDescription(masterSyncResponse.getAssetMasterSyncList().get(i).getDescription());
            assetMasterCategory.setCategoryID(masterSyncResponse.getAssetMasterSyncList().get(i).getCategoryId());
            assetMasterCategories.add(assetMasterCategory);
            assetMasterArrayList.add(masterSyncResponse.getAssetCategoryMasterSyncList().get(i).getDescription());
        }*/
        assetMasterAdapter = new ArrayAdapter<String>(this, R.layout.item_dropdown, R.id.id_item, assetMasterArrayList);

        if (tblDetailProducts.get(0).getSupplier() != null) {
            actSupplierAsset.setText(tblDetailProducts.get(0).getSupplier());
            actSupplierAsset.setSelectionFromPopUp(true);
        } else actSupplierAsset.setSelectionFromPopUp(false);
        if (tblDetailProducts.get(0).getKodeSupplier() != null) {
            supplierKode = tblDetailProducts.get(0).getKodeSupplier();
            labelProduct = databaseService.getAllProductOfering(assetTypeId, aoBranch, supplierKode);
            productArrayAdapter = new ArrayAdapter<ProductOfferingObjt>(FormPengajuanActivity.this, R.layout.item_dropdown, R.id.id_item, labelProduct);
            actProductOfferingProduct.setAdapter(productArrayAdapter);
        }
        if (tblDetailProducts.get(0).getMarketingSupplier() != null) {
            actMarketingSupplierAsset.setText(tblDetailProducts.get(0).getMarketingSupplier());
            actMarketingSupplierAsset.setSelectionFromPopUp(true);
        } else actMarketingSupplierAsset.setSelectionFromPopUp(false);
        if (tblDetailProducts.get(0).getKodeMarketingSupplier() != null) {
            marketingKode = tblDetailProducts.get(0).getKodeMarketingSupplier();
            List<MarketingSupplierObjt> labelMar = databaseService.getAllSupplierMarketing(supplierKode, aoBranch);
            marketingArrayAdapterObj = new ArrayAdapter<MarketingSupplierObjt>(FormPengajuanActivity.this, R.layout.item_dropdown, R.id.id_item, labelMar);
            actMarketingSupplierAsset.setAdapter(marketingArrayAdapterObj);
        }
        if (tblDetailProducts.get(0).getKodeProductId() != null)
            productId = tblDetailProducts.get(0).getKodeProductId();
        if (tblDetailProducts.get(0).getKodeProductOfferingId() != null)
            productOfferingId = tblDetailProducts.get(0).getKodeProductOfferingId();
        if (tblDetailProducts.get(0).getProductOffering() != null) {
            actProductOfferingProduct.setText(tblDetailProducts.get(0).getProductOffering());
            actProductOfferingProduct.setSelectionFromPopUp(true);
        } else actProductOfferingProduct.setSelectionFromPopUp(false);
        if (tblDetailProducts.get(0).getPosId() != null)
            posKode = tblDetailProducts.get(0).getPosId();
        if (tblDetailProducts.get(0).getPos() != null) {
            actPosProduct.setText(tblDetailProducts.get(0).getPos());
            actPosProduct.setSelectionFromPopUp(true);
        } else actPosProduct.setSelectionFromPopUp(false);
        strJumlahAsset = String.valueOf(assetElektroniks.size());
        tvJumlahAsset.setText("Asset : " + String.valueOf(assetElektroniks.size()));
        setFieldDataProductDraft(assetElektroniks);

        List<SupplierMasterObjt> supplierMasterObjts = databaseService.getAllSupplierMaster(aoBranch);
        final ArrayAdapter<SupplierMasterObjt> supplierMasterObjtArrayAdapter = new ArrayAdapter<SupplierMasterObjt>(this, R.layout.item_dropdown, R.id.id_item, supplierMasterObjts);
        actSupplierAsset.setAdapter(supplierMasterObjtArrayAdapter);

        if (tblDetailProducts.get(0).getKodeProductId() != null && tblDetailProducts.get(0).getKodeProductOfferingId() != null) {
            labelprooftenor = databaseService.getAllProdOfTenor(productId, productOfferingId, aoBranch);
            ArrayAdapter<ProductOfTenorObjt> tenorAdapterObj = new ArrayAdapter<ProductOfTenorObjt>(FormPengajuanActivity.this, R.layout.item_dropdown, R.id.id_item, labelprooftenor);
            if (labelprooftenor.size() != 0) { // Cek hasil query. Bila hasil query kosong, list tenor tidak di masukkan kedalam adapter dan akan menggunakan adapter tenor default
                spnTenorProduct.setAdapter(tenorAdapterObj);
            }
        }

        actSupplierAsset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                supplierKode = supplierMasterObjtArrayAdapter.getItem(position).getSupplierID();

                List<MarketingSupplierObjt> labelMar = databaseService.getAllSupplierMarketing(supplierKode, aoBranch);
                marketingArrayAdapterObj = new ArrayAdapter<MarketingSupplierObjt>(FormPengajuanActivity.this, R.layout.item_dropdown, R.id.id_item, labelMar);
                actMarketingSupplierAsset.setAdapter(marketingArrayAdapterObj);
                actMarketingSupplierAsset.getText().clear();
                actProductOfferingProduct.getText().clear();

                labelProduct = databaseService.getAllProductOfering(assetTypeId, aoBranch, supplierKode);
                productArrayAdapter = new ArrayAdapter<ProductOfferingObjt>(FormPengajuanActivity.this, R.layout.item_dropdown, R.id.id_item, labelProduct);
                actProductOfferingProduct.setAdapter(productArrayAdapter);
            }
        });

        actProductOfferingProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productId = productArrayAdapter.getItem(position).getProductId();
                productOfferingId = productArrayAdapter.getItem(position).getProductOfferingId();
                mCekKodeProgramPresenter.checkKodeProgram(token, productOfferingId);
                mMaskingPresenter.getDataMasking(token, productId, productOfferingId);

                // ------- adapter spn tenor
                labelprooftenor = databaseService.getAllProdOfTenor(productId, productOfferingId, aoBranch);
                ArrayAdapter<ProductOfTenorObjt> tenorAdapterObj = new ArrayAdapter<ProductOfTenorObjt>(FormPengajuanActivity.this, R.layout.item_dropdown, R.id.id_item, labelprooftenor);
                if (labelprooftenor.size() != 0) { // Cek hasil query. Bila hasil query kosong, list tenor tidak di masukkan kedalam adapter dan akan menggunakan adapter tenor default
                    spnTenorProduct.setAdapter(tenorAdapterObj);
                    spnTenorProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            fsInstallment = "";
                            if (isMaskingOn) {
                                double selectedFlateRateMasking = ((MaskingObjt) spnTenorProduct.getSelectedItem()).getReturnRate();
                                edtFlatRatePerhitungan.setText(String.valueOf(selectedFlateRateMasking));
                                fsInstallment = ((MaskingObjt) spnTenorProduct.getSelectedItem()).getFsInstallment();
                                if ("AD".equalsIgnoreCase(fsInstallment) || "".equalsIgnoreCase(fsInstallment)) {
                                    edtTipePembayaran.setText(R.string.text_addm);
                                } else if ("AR".equalsIgnoreCase(fsInstallment)) {
                                    edtTipePembayaran.setText(R.string.text_addb);
                                }
                            } else {
                                if (labelprooftenor.size() != 0) {
                                    edtFlatRatePerhitungan.setText(((ProductOfTenorObjt) spnTenorProduct.getSelectedItem()).getFlatRate());
                                    edtBiayaAdminPerhitungan.setText(((ProductOfTenorObjt) spnTenorProduct.getSelectedItem()).getAdminFee());
                                    edtBebasBungaPerhitungan.setText(((ProductOfTenorObjt) spnTenorProduct.getSelectedItem()).getDiscountRateTimes());
                                    fsInstallment = ((ProductOfTenorObjt) spnTenorProduct.getSelectedItem()).getFirstInstallment();
                                } else {
                                    edtFlatRatePerhitungan.setText(R.string.txt_flatrateperhitungan);
                                    edtBiayaAdminPerhitungan.setText(R.string.txt_biayaperhitunganadmin);
                                }

                                if ("AD".equalsIgnoreCase(fsInstallment) || "".equalsIgnoreCase(fsInstallment)) {
                                    edtTipePembayaran.setText(R.string.text_addm);
                                } else if ("AR".equalsIgnoreCase(fsInstallment)) {
                                    edtTipePembayaran.setText(R.string.text_addb);
                                }
                            }
                            //fungsi perhitungan elektronik
                            // TODO: Comment Calculating
                            /*calculating();*/
                            prepareDataCalculating();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        });

        actMarketingSupplierAsset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                marketingKode = marketingArrayAdapterObj.getItem(position).getSupplierEmployeeID();
            }
        });

//        DATA ASURANSI
        if (tblAsuransis.get(0).getManualAgunan().equalsIgnoreCase("All Risk")) {
            rbAsuransiAgunanYes.setChecked(true);
            rbGroupManualAsuransi.setVisibility(View.VISIBLE);
            cvManualPremiAsuransi.setVisibility(View.VISIBLE);
        } else if (tblAsuransis.get(0).getManualAgunan().equalsIgnoreCase("No Assurance")) {
            rbAsuransiAgunanNo.setChecked(true);
//            edtPremiAsuransi.setText(); disini
            rbGroupManualAsuransi.setVisibility(View.GONE);
            cvManualPremiAsuransi.setVisibility(View.GONE);
            tilPremiManual.setVisibility(View.GONE);
            edtPremiAsuransiManual.setVisibility(View.GONE);
        }

//        DATA PERHITUNGAN
        spnTenorProduct.setSelection(tblDataPerhitungans.get(0).getPosisiTenor());
        edtFlatRatePerhitungan.setText(tblDataPerhitungans.get(0).getFlateRate());
        edtBiayaAdminPerhitungan.setText(tblDataPerhitungans.get(0).getBiayaAdministrasi());
        if (tblDataPerhitungans.get(0).getTipePembayaran() != null) {
            if (tblDataPerhitungans.get(0).getTipePembayaran().equalsIgnoreCase("AD")) {
                fsInstallment = "AD";
                edtTipePembayaran.setText(R.string.text_addm);
            } else if (tblDataPerhitungans.get(0).getTipePembayaran().equalsIgnoreCase("AR")) {
                fsInstallment = "AR";
                edtTipePembayaran.setText(R.string.text_addb);
            } else {
                edtTipePembayaran.setText("");
            }
        }
        edtBiayaLainnya.setText(tblDataPerhitungans.get(0).getBiayaLainnya());
        edtRefundSubsidiPerhitungan.setText(tblDataPerhitungans.get(0).getRefundSubsidi());
        edtBebasBungaPerhitungan.setText(tblDataPerhitungans.get(0).getBebasBunga());
        edtPurchasePricePerhitungan.setText(tblDataPerhitungans.get(0).getTotalPrice());
        edtDiscountPerhitungan.setText(tblDataPerhitungans.get(0).getTotalDiscount());
        edtDpPerhitungan.setText(tblDataPerhitungans.get(0).getTotalDp());
        edtPremiAsuransi.setText(tblDataPerhitungans.get(0).getPremi());
        edtNtfPerhitungan.setText(tblDataPerhitungans.get(0).getNtf());
        edtJumlahPembiayaan.setText(tblDataPerhitungans.get(0).getJumlahPembiayaan());
        edtTotalBungaPembiayaan.setText(tblDataPerhitungans.get(0).getTotalBungaPembiayaan());
        edtBungaPembiayaanBulan.setText(tblDataPerhitungans.get(0).getBulanBungaPembiayaan());
        edtTotalPinjaman.setText(tblDataPerhitungans.get(0).getTotalPinjaman());
        if (tblDataPerhitungans.get(0).getBebasBunga() != null && tblDataPerhitungans.get(0).getBebasBunga().equals("0"))
            edtAngsuranPerbulan.setText(tblDataPerhitungans.get(0).getAngsuranPerbulan());
        else
            edtAngsuranPerbulanBebasBunga.setText(tblDataPerhitungans.get(0).getAngsuranPerbulan());
        edtAngsuranPerbulan.setText(tblDataPerhitungans.get(0).getAngsuranPerbulan());
        edtPembayaranPertama.setText(tblDataPerhitungans.get(0).getPembayaranAwal());
        edtEffectiveRatePerhitungan.setText(tblDataPerhitungans.get(0).getEffectiveRate());

//        KETERANGAN
        edtKeterangan.setText(tblKeterangans.get(0).getKeterangan());

//        ATTACHMENT
        for (int i = 0; i < attachments.size(); i++) {
            Glide.with(this).load(attachments.get(i).getPath()).centerCrop().into((ImageView) viewTakeImages.get(attachments.get(i).getKey()));
            mHashMapAttachmentFiles.put(attachments.get(i).getKey(), new File(attachments.get(i).getPath2()));
            // attachment dari kpm tidak bisa dihapus
            if (!"".equalsIgnoreCase(mMasterFormPengajuan.getIdKpm()) && mMasterFormPengajuan.getIdKpm() != null) {
                if (attachments.get(i).getKey() == 0) {
                    viewDeleteImages.get(attachments.get(i).getKey()).setVisibility(View.GONE);
                    imgTakePicture1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {
                    viewDeleteImages.get(attachments.get(i).getKey()).setVisibility(View.VISIBLE);
                }
            } else {
                viewDeleteImages.get(attachments.get(i).getKey()).setVisibility(View.VISIBLE);
            }
        }

//        REKOMENDASI
        if (tblRekomendasis.get(0).getIdRekomendasi().isEmpty() || tblRekomendasis.get(0).getIdRekomendasi().equals("0") || tblRekomendasis.get(0).getIdRekomendasi() == null) {
            lnSpnRecomendation.setVisibility(View.GONE);
            recomendationId = "0";
            rbRecomendationYes.setChecked(true);
        } else {
            rbRecomendationNo.setChecked(true);
            lnSpnRecomendation.setVisibility(View.VISIBLE);
            int valueRekomendasi = Integer.parseInt(tblRekomendasis.get(0).getIdRekomendasi()) - 1;
            spnRecomendation.setSelection(valueRekomendasi);
            recomendationId = tblRekomendasis.get(0).getIdRekomendasi();
        }
        edtDescRecomendation.setText(tblRekomendasis.get(0).getCatatan());

//        LOCATIONS
        validateAction.setText(tblLokasis.get(0).getValidationAction());
        validateLongitude.setText(tblLokasis.get(0).getValidationLongitude());
        validateLatitude.setText(tblLokasis.get(0).getValidationLatitude());
        takeKtpAction.setText(tblLokasis.get(0).getKtpAction());
        takeKtpLongitude.setText(tblLokasis.get(0).getKtpLongitude());
        takeKtpLatitude.setText(tblLokasis.get(0).getKtpLatitude());
        takeCustomerAction.setText(tblLokasis.get(0).getCustomerAction());
        takeCustomerLongitude.setText(tblLokasis.get(0).getCustomerLongitude());
        takeCustomerLatitude.setText(tblLokasis.get(0).getCustomerLatitude());
        takePaycheckAction.setText(tblLokasis.get(0).getPaycheckAction());
        takePaycheckLongitude.setText(tblLokasis.get(0).getPaycheckLongitude());
        takePaycheckLatitude.setText(tblLokasis.get(0).getPaycheckLatitude());
        takeAdditionalDocumentsAction.setText(tblLokasis.get(0).getAddtionalDocumentsAction());
        takeAdditionalDocumentsLongitude.setText(tblLokasis.get(0).getAddtionalDocumentsLongitude());
        takeAdditionalDocumentsLatitude.setText(tblLokasis.get(0).getAddtionalDocumentsLongitude());
        takeSignatureAction.setText(tblLokasis.get(0).getSignatureAction());
        takeSignatureLongitude.setText(tblLokasis.get(0).getSignatureLongitude());
        takeSignatureLatitude.setText(tblLokasis.get(0).getSignatureLatitude());
        submitAction.setText(tblLokasis.get(0).getSubmitAction());
        submitLongitude.setText(tblLokasis.get(0).getSubmitLongitude());
        submitLatitude.setText(tblLokasis.get(0).getSubmitLatitude());
        syncAction.setText(tblLokasis.get(0).getSyncAction());
        syncLongitude.setText(tblLokasis.get(0).getSyncLongitude());
        syncLatitude.setText(tblLokasis.get(0).getSyncLatitude());

        setDefaultCalculate();
        mBlackListPresenter.blackList(token,
                edtTanggalLahirPribadi.getText().toString(),
                edtNoKtpPribadi.getText().toString(),
                edtHandphonePribadi.getText().toString(),
                typeDataOfferingBlackList,
                aoBranch, edtNamaLegalValidasi.getText().toString(), edtIbuValidasi.getText().toString(), typeEdit);
    }

    @Override
    public void onFailedLoadPengajuanDraft(String message) {
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        tvMessage.setText(message);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPengajuanDraftPresenter.getPengajuanBaru(pengajuanBaruId);
            }
        });
    }

    @Override
    public void onPreSubmitPengajuanEditLoad() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    // TODO : Untuk lihat data assign Edit
    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessSubmitPengajuanEditLoad(Application application) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = df.format(Calendar.getInstance().getTime());


//        if (application.getCountEdit() >= 3) {
//            String countEdit = Integer.toString(application.getCountEdit());
//            Toast.makeText(getContext(), "Pengajuan sudah di edit sebanyak " + countEdit + " kali", Toast.LENGTH_SHORT).show();
//        }
        typeDataOffering = application.getOfferingType();
        pengajuanType = application.getDataType();
        mobileSubmissionKey = application.getMobileSubmissionKey();
        totalInsurancePersen = application.getTotalInsurancePersen();
        nominalAdminLain = Integer.parseInt(application.getAdminFeeLainya());
        blackListDate = application.getDateStart();
        aoBranch = "00".equalsIgnoreCase(application.getBranchObjt().getAoBranch()) ? application.getBranchObjt().getMasterBranch() : application.getBranchObjt().getAoBranch();
        branchMaster = application.getBranchObjt().getMasterBranch();
        strStatusPengajuan = application.getKorpFormulir().getFinancingPurpose();
        applicationIdKpm = "00".equalsIgnoreCase(application.getApplicationIdKPM()) ? "" : application.getApplicationIdKPM();
        customerIdConfins = String.valueOf(application.getCustomerId());
        msgNotifikasi = application.getDescriptionEdit();

        countSignature = 0;
        uuid = Util.RandomDateNumber();
        checkTipePengajuan();

//        KOP
        String metodePenjualanTemp;
        metodePenjualanTemp = application.getKorpFormulir().getSalesMethod();
        for (int h = 0; h < metodePenjualan.length; h++) {
            if (metodePenjualan[h].equalsIgnoreCase(application.getKorpFormulir().getSalesMethod())) {
                spnMetodePenjualan.setSelection(h);
                break;
            }
        }

//        DATA PRIBADI
        edtNoKtpPribadi.setText(application.getPersonalData().getiDNumber());
        int priceCredit = Integer.parseInt(application.getDetailFinancing().getPurchasePrice());
        int netPriceAsset = 50000000;
        if (priceCredit > netPriceAsset) {
//            edtNoNpwpPribadi.setText(application.getPersonalData().getPersonalNPWP());
            edtNoNpwpDetail.setText(application.getPersonalData().getPersonalNPWP());
            edtNoNpwpDetail.setTextColor(GRAY);
//            edtNoNpwpPribadi.setEnabled(false);
//            edtNoNpwpPribadi.setTextColor(GRAY);
//            tilNoNpwpPribadi.setVisibility(View.VISIBLE);
//            edtNoNpwpPribadi.setVisibility(View.VISIBLE);
            rlHeaderDetailNpwp.setVisibility(View.VISIBLE);
        } else {
            tilNoNpwpPribadi.setVisibility(View.GONE);
            edtNoNpwpPribadi.setVisibility(View.GONE);
            rlHeaderDetailNpwp.setVisibility(View.GONE);
            edtNoNpwpPribadi.setText("");
        }
        if ("M".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "Menikah".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()))
            spnPernikahanPribadi.setSelection(1);
        else if ("S".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "Lajang".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()))
            spnPernikahanPribadi.setSelection(2);
        else if ("D".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "Cerai".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()))
            spnPernikahanPribadi.setSelection(3);
        else if ("W".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "Janda".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "Janda / Duda".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()))
            spnPernikahanPribadi.setSelection(4);

        if ("M".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "Menikah".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()))
            lnWrapperDataPasangan.setVisibility(View.VISIBLE);
        else {
            lnWrapperDataPasangan.setVisibility(View.GONE);
            edtNamaPasangan.setText("");
            edtNoKtpPasangan.setText("");
            edtTempatLahirPasangan.setText("");
            edtTanggalLahirPasangan.setText("");
            edtNoHpPasangan.setText("");
        }
        isAssignEdit = true;
        edtTanggalLahirPribadi.setText(application.getPersonalData().getBirthDate());
        String nope = application.getPersonalData().getMobilePhone();
        edtHandphonePribadi.setText(nope);
        edtNamaPribadi.setText(application.getPersonalData().getFullName());
        edtPribadiNoKK.setText(application.getPersonalData().getKKno());
        edtNamaKtpPribadi.setText(application.getPersonalData().getLegalName());
        edtTerbitKtpPribadi.setText(application.getPersonalData().getiDTypeIssuedDate());
        if (application.getPersonalData().getGender().equals("M"))
            spnGenderPribadi.setSelection(1);
        else spnGenderPribadi.setSelection(2);

        edtTempatLahirPribadi.setText(application.getPersonalData().getBirthPlace());
        edtNamaIbuPribadi.setText(application.getPersonalData().getSurgateMotherName());
        for (int h = 0; h < pendidikanValue.length; h++) {
            if (pendidikanValue[h].equalsIgnoreCase(application.getPersonalData().getEducation().replaceAll("\\s", ""))) {
                spnPendidikanPribadi.setSelection(h);
                break;
            }
        }
        for (int h = 0; h < wargaNegaraValue.length; h++) {
            if (wargaNegaraValue[h].equalsIgnoreCase(application.getPersonalData().getNationality())) {
                spnWargaNegaraPribadi.setSelection(h);
                break;
            }
        }
        for (int h = 0; h < statusRumahValue.length; h++) {
            if (statusRumahValue[h].equalsIgnoreCase(application.getPersonalData().getHomeStatus())) {
                spnStatusRumahPribadi.setSelection(h);
                break;
            }
        }
        edtTinggalSejakTahunPribadi.setText(application.getPersonalData().getStaySinceYear());
        edtTinggalSejakBulanPribadi.setText(application.getPersonalData().getStaySinceMonth());
        for (int h = 0; h < agamaValue.length; h++) {
            if (agamaValue[h].equalsIgnoreCase(application.getPersonalData().getReligion())) {
                spnAgamaPribadi.setSelection(h);
                break;
            }
        }
        String jumlahTanggunganPribadi = application.getPersonalData().getNumOfDependence();
        if (jumlahTanggunganPribadi.isEmpty()) {
            edtJumlahTanggunganPribadi.setText("0");
        } else {
            edtJumlahTanggunganPribadi.setText(jumlahTanggunganPribadi);
        }

        edtEmailPribadi.setText(application.getPersonalData().getEmail());

//        DATA PASANGAN
        if ("M".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "".equalsIgnoreCase(application.getPersonalData().getMaritalStatus())) {
            lnWrapperDataPasangan.setVisibility(View.VISIBLE);
            if (!application.getFamilyData().isEmpty()) {
                idFamily = String.valueOf(application.getFamilyData().get(0).getId());
                edtNamaPasangan.setText(application.getFamilyData().get(0).getName());
                edtNoKtpPasangan.setText(application.getFamilyData().get(0).getiDNumber());
                edtTempatLahirPasangan.setText(application.getFamilyData().get(0).getBirthPlace());
                edtTanggalLahirPasangan.setText(application.getFamilyData().get(0).getBirthDate());
                edtNoHpPasangan.setText(application.getFamilyData().get(0).getHandphone());
            }
        }

//        ALAMAT PEMOHON dan KTP
        edtAlamatTinggal.setText(application.getResidance().getAddress());
        edtRtTinggal.setText(application.getResidance().getrT());
        edtRwTinggal.setText(application.getResidance().getrW());
        strCityAlamatPemohon = application.getResidance().getCity();
        strKecamatanAlamatPemohon = application.getResidance().getDistrictName();
        strKelurahanAlamatPemohon = application.getResidance().getVillageName();
        strZipCodeAlamatPemohon = application.getResidance().getZipCode();
        if (!strCityAlamatPemohon.isEmpty()) {
            actAutoAlamatPemohon.setText(strCityAlamatPemohon);
        }

        actAutoAlamatPemohon.setSelectionFromPopUp(true);
        edtAreaPhoneTinggal.setText(application.getResidance().getAreaPhone());
        edtPhoneTinggal.setText(application.getResidance().getPhone());
        edtAlamatKtp.setText(application.getLegal().getAddress());
        edtRtKtp.setText(application.getLegal().getrT());
        edtRwKtp.setText(application.getLegal().getrW());
        strCityKtpAlamatPemohon = application.getLegal().getCity();
        strKecamatanKtpAlamatPemohon = application.getLegal().getDistrictName();
        strKelurahanKtpAlamatPemohon = application.getLegal().getVillageName();
        strZipCodeKtpAlamatPemohon = application.getLegal().getZipCode();
        if (!strCityKtpAlamatPemohon.isEmpty()) {
            actAutoKtpAlamatPemohon.setText(strCityKtpAlamatPemohon);
        }

        actAutoKtpAlamatPemohon.setSelectionFromPopUp(true);
        edtAreaPhoneKtp.setText(application.getLegal().getAreaPhone());
        edtPhoneKtp.setText(application.getLegal().getPhone());

//        INFORMASI KERABAT
        edtNamaKerabat.setText(application.getEmergencyContact().getName());
        for (int h = 0; h < hubunganKerabatValue.length; h++) {
            if (hubunganKerabatValue[h].equalsIgnoreCase(application.getEmergencyContact().getRelationship())) {
                spnHubunganKerabat.setSelection(h);
                break;
            }
        }
        edtRtKerabat.setText(application.getEmergencyContact().getrT());
        edtRwKerabat.setText(application.getEmergencyContact().getrW());
        strCityAlamatKerabat = application.getEmergencyContact().getCity();
        strKecamatanAlamatKerabat = application.getEmergencyContact().getDistrictName();
        strKelurahanAlamatKerabat = application.getEmergencyContact().getVillageName();
        strZipCodeAlamatKerabat = application.getEmergencyContact().getZipCode();
        if (!strCityAlamatKerabat.isEmpty()) {
            actAutoAlamatKerabat.setText(strCityAlamatKerabat);
        }
        actAutoAlamatKerabat.setSelectionFromPopUp(true);
        edtAlamatKerabat.setText(application.getEmergencyContact().getAddress());
        edtAreaPhoneRumahKerabat.setText(application.getEmergencyContact().getHomePhoneArea());
        edtPhoneRumahKerabat.setText(application.getEmergencyContact().getHomePhone());
        edtAreaPhoneKantorKerabat.setText(application.getEmergencyContact().getOfficePhoneArea());
        edtPhoneKantorKerabat.setText(application.getEmergencyContact().getOfficePhone());
        edtHpKerabat.setText(application.getEmergencyContact().getMobilePhone());

//        DATA PEKERJAAN
        edtNamaPerusahaan.setText(application.getCompany().getName());
        edtAlamatPerusahaan.setText(application.getCompany().getAddress());
        edtRtPerusahaan.setText(application.getCompany().getrT());
        edtRwPerusahaan.setText(application.getCompany().getrW());
        strCityAlamatPekerjaan = application.getCompany().getCity();
        strKecamatanAlamatPekerjaan = application.getCompany().getDistrictName();
        strKelurahanAlamatPekerjaan = application.getCompany().getVillageName();
        strZipCodeAlamatPekerjaan = application.getCompany().getZipCode();
        if (!strCityAlamatPekerjaan.isEmpty()) {
            actAutoAlamatPekerjaan.setText(strCityAlamatPekerjaan);
        }


        actAutoAlamatPekerjaan.setSelectionFromPopUp(true);
        edtAreaPhonePerusahaan.setText(application.getCompany().getAreaPhone());
        edtPhonePerusahaan.setText(application.getCompany().getPhone());
        edtBekerjaSejakPerusahaan.setText(application.getCompany().getEmploymentSinceYear());
        actProfesiPerusahaan.setText(application.getCompany().getProfessionName());
        actProfesiPerusahaan.setSelectionFromPopUp(true);
        actJobTypePerusahaan.setText(application.getCompany().getJobTypeName());
        actJobTypePerusahaan.setSelectionFromPopUp(true);
        if (application.getCompany().getProfessionID() != null) {
            professionKode = application.getCompany().getProfessionID();
            List<JobType> labeljob = databaseService.getAllJobType(professionKode);
            jobTypeArrayAdapter = new ArrayAdapter<JobType>(getContext(), R.layout.item_dropdown, R.id.id_item, labeljob);
            actJobTypePerusahaan.setAdapter(jobTypeArrayAdapter);
        }
        jobPositionKode = application.getCompany().getJobPositionID();
        actJobPositionPerusahaan.setText(application.getCompany().getJobPositionName());
        actJobPositionPerusahaan.setSelectionFromPopUp(true);
        if (application.getCompany().getJobTypeID() != null) {
            jobTypeKode = application.getCompany().getJobTypeID();
            List<JobPosition> labelposition = databaseService.getAllJObPosition(jobTypeKode);
            jobPositionArrayAdapter = new ArrayAdapter<JobPosition>(getContext(), R.layout.item_dropdown, R.id.id_item, labelposition);
            actJobPositionPerusahaan.setAdapter(jobPositionArrayAdapter);
        }
        industriKode = String.valueOf(application.getCompany().getIndustryTypeID());
        actIndustriPerusahaan.setText(application.getCompany().getIndustryTypeName() == null ? "" : application.getCompany().getIndustryTypeName());
        actIndustriPerusahaan.setSelectionFromPopUp(true);
        industriKode = String.valueOf(application.getCompany().getIndustryTypeID());
        if (application.getCompany().getMonthlyFixedIncome().isEmpty()) {
            edtPenghasilanTetapPerusahaan.setText("0");
        } else {
            edtPenghasilanTetapPerusahaan.setText(application.getCompany().getMonthlyFixedIncome());
        }
        if (application.getCompany().getMonthlyVariableIncome().isEmpty()) {
            edtPenghasilanLainPerusahaan.setText("0");
        } else {
            edtPenghasilanLainPerusahaan.setText(application.getCompany().getMonthlyVariableIncome());
        }
        if (application.getCompany().getSpouseIncome().isEmpty()) {
            edtPenghasilanPasanganPerusahaan.setText("0");
        } else {
            edtPenghasilanPasanganPerusahaan.setText(application.getCompany().getSpouseIncome());
        }
        if (application.getCompany().getLivingCostAmount().isEmpty()) {
            edtBiayaHidupPerusahaan.setText("0");
        } else {
            edtBiayaHidupPerusahaan.setText(application.getCompany().getLivingCostAmount());
        }


//        DATA KARTU KREDIT
        edtNamaBankKartuKredit.setText(application.getDataCreditCard().getBankName() == null ? "" : application.getDataCreditCard().getBankName());
        edtNoKartuKredit.setText(application.getDataCreditCard().getiDCard() == null ? "" : application.getDataCreditCard().getiDCard());
        edtJenisKartuKredit.setText(application.getDataCreditCard().getCardType() == null ? "" : application.getDataCreditCard().getCardType());
        edtLimitKartuKredit.setText(application.getDataCreditCard().getCardLimit() == null ? "" : application.getDataCreditCard().getCardLimit());
        edtTahunKadaluarsaKartuKredit.setText(application.getDataCreditCard().getMembershipOldYear() == null ? "" : application.getDataCreditCard().getMembershipOldYear());
        edtBulanKadaluarsaKartuKredit.setText(application.getDataCreditCard().getMembershipOldMonth() == null ? "" : application.getDataCreditCard().getMembershipOldMonth());

//        DATA KARTU MEMBERSHIP
        edtNoMembership.setText(application.getMembershipCard().getiDMember() == null ? "" : application.getMembershipCard().getiDMember());
        edtTanggalEfektif.setText(application.getMembershipCard().getEffectiveDate() == null ? "" : application.getMembershipCard().getEffectiveDate());
        edtTanggalExpired.setText(application.getMembershipCard().getExpiredDate() == null ? "" : application.getMembershipCard().getExpiredDate());

//        DETAIL PRODUCT
        if (!form.equalsIgnoreCase("Draft")) {
            supplierKode = application.getAssetMaster().getSupplierID();
            actSupplierAsset.setText(application.getAssetMaster().getSupplierName() == null ? "" : application.getAssetMaster().getSupplierName());
            marketingKode = application.getAssetMaster().getSalesmanID();
            actMarketingSupplierAsset.setText(application.getAssetMaster().getSalesmanName() == null ? "" : application.getAssetMaster().getSalesmanName());
            productId = application.getDetailProduct().getProductID();
            productOfferingId = application.getDetailProduct().getProductOfferingID();
            actProductOfferingProduct.setText(application.getDetailProduct().getProductOfferingName());
            posKode = application.getDetailProduct().getpOSID();
            actPosProduct.setText(application.getDetailProduct().getpOSName() == null ? "" : application.getDetailProduct().getpOSName());
            strJumlahAsset = (application.getAsset() == null) ? "0" : String.valueOf(application.getAsset().size());
            actSupplierAsset.setSelectionFromPopUp(true);
            actMarketingSupplierAsset.setSelectionFromPopUp(true);
            actProductOfferingProduct.setSelectionFromPopUp(true);
            actPosProduct.setSelectionFromPopUp(true);

//        DETAIL ASSET
            tvJumlahAsset.setText("Asset : " + strJumlahAsset);
            setFieldDataProduct(application.getAsset());

//        DATA ASURANSI
            if ("ALL RISK".equalsIgnoreCase(application.getInsurance().getCoverageType())) {
                rbAsuransiAgunanYes.setChecked(true);
                rbGroupManualAsuransi.setVisibility(View.VISIBLE);
                cvManualPremiAsuransi.setVisibility(View.VISIBLE);
            } else {
                rbAsuransiAgunanNo.setChecked(true);
                rbGroupManualAsuransi.setVisibility(View.GONE);
                cvManualPremiAsuransi.setVisibility(View.GONE);
                tilPremiManual.setVisibility(View.GONE);
                edtPremiAsuransiManual.setVisibility(View.GONE);
            }

            if (application.getInsurance().getIsPremiManual().equalsIgnoreCase("1")) {
                rbManualYes.setChecked(true);
                edtPremiAsuransiManual.setVisibility(View.VISIBLE);
                tilPremiManual.setVisibility(View.VISIBLE);
                edtPremiAsuransiManual.setText(application.getInsurance().getPremiAsuransiAgunan());
            } else {
                rbManualNo.setChecked(true);
            }

//        DATA PERHITUNGAN
            List<ProductOfTenorObjt> labelprooftenot = databaseService.getAllProdOfTenor(productId, productOfferingId, aoBranch);
            ArrayAdapter<ProductOfTenorObjt> tenorAdapterObj = new ArrayAdapter<ProductOfTenorObjt>(getContext(), R.layout.item_dropdown, R.id.id_item, labelprooftenot);
            if (!labelprooftenot.isEmpty()) {
                spnTenorProduct.setAdapter(tenorAdapterObj);
                for (int i = 0; i < labelprooftenot.size(); i++) {
                    if (labelprooftenot.get(i).getTenor().equalsIgnoreCase(String.valueOf(application.getDetailProduct().getTenor()))) {
                        spnTenorProduct.setSelection(i);
                        break;
                    }
                }
            } else {
                String detailTenor = String.valueOf(application.getDetailProduct().getTenor());
                if (!detailTenor.isEmpty()) {
                    tenor = Integer.parseInt(detailTenor);
                }
                String[] arraySpinner = new String[]{detailTenor};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_dropdown, R.id.id_item, arraySpinner);
                spnTenorProduct.setAdapter(adapter);
                spnTenorProduct.setSelection(0);
            }
            flatRate = application.getDetailFinancing().getFlatRate() == null ? "0" : application.getDetailFinancing().getFlatRate();
            edtFlatRatePerhitungan.setText(flatRate);
            admin_fee = Long.parseLong(application.getDetailFinancing().getAdminFee() == null ? "0" : application.getDetailFinancing().getAdminFee());
            edtBiayaAdminPerhitungan.setText(application.getDetailFinancing().getAdminFee() == null ? "0" : application.getDetailFinancing().getAdminFee());
            if (application.getDetailFinancing().getFirstInstallment().equalsIgnoreCase("AD")) {
                fsInstallment = "AD";
                edtTipePembayaran.setText(R.string.text_addm);
            } else {
                fsInstallment = "AR";
                edtTipePembayaran.setText(R.string.text_addb);
            }
//        edtTipePembayaran.setText(application.getDetailFinancing().getTypePayment());
            edtBiayaLainnya.setText(application.getDetailFinancing().getOtherFee() == null ? "0" : application.getDetailFinancing().getOtherFee());
            edtRefundSubsidiPerhitungan.setText(application.getDetailFinancing().getSubsidyRefund() == null ? "0" : application.getDetailFinancing().getSubsidyRefund());
            edtBebasBungaPerhitungan.setText(application.getDetailFinancing().getDiscountRateTimes() == null ? "0" : application.getDetailFinancing().getDiscountRateTimes());
            edtPurchasePricePerhitungan.setText(application.getDetailFinancing().getPurchasePrice() == null ? "0" : application.getDetailFinancing().getPurchasePrice());
            edtDiscountPerhitungan.setText(application.getDetailFinancing().getDiscount());
            edtDpPerhitungan.setText(application.getDetailFinancing().getDownPayment() == null ? "0" : application.getDetailFinancing().getDownPayment());
            edtPremiAsuransi.setText(application.getInsurance().getPremiumAmountToCustomer() == null ? "0" : application.getInsurance().getPremiumAmountToCustomer());
            edtNtfPerhitungan.setText(application.getDetailFinancing().getnTF() == null ? "0" : application.getDetailFinancing().getnTF());
            edtJumlahPembiayaan.setText(application.getDetailFinancing().getTotalFinancing() == null ? "0" : application.getDetailFinancing().getTotalFinancing());
            edtTotalBungaPembiayaan.setText(application.getDetailFinancing().getInterestFinancing() == null ? "0" : application.getDetailFinancing().getInterestFinancing());
            edtBungaPembiayaanBulan.setText(application.getDetailFinancing().getMonthFinancingInterest() == null ? "0" : application.getDetailFinancing().getMonthFinancingInterest());
            edtTotalPinjaman.setText(application.getDetailFinancing().getTotalLoan() == null ? "0" : application.getDetailFinancing().getTotalLoan());
            if (application.getDetailFinancing().getDiscountRateTimes().equals("0"))
                edtAngsuranPerbulan.setText(application.getDetailFinancing().getFirstInstallmentAmmount());
            else
                edtAngsuranPerbulanBebasBunga.setText(application.getDetailFinancing().getFirstInstallmentAmmount());

            edtAngsuranPerbulan.setText(application.getDetailFinancing().getInstallmentAmmount() == null ? "0" : application.getDetailFinancing().getInstallmentAmmount());
            edtPembayaranPertama.setText(application.getDetailFinancing().getFirstPayment() == null ? "0" : application.getDetailFinancing().getFirstPayment());
//        edtPembayaranDelaer.setText(application.getDetailFinancing().getInterestFreeDealerPayment() == null ? "0" : application.getDetailFinancing().getInterestFreeDealerPayment());
            edtEffectiveRatePerhitungan.setText(application.getDetailFinancing().getEffectiveRate() == null ? "0" : application.getDetailFinancing().getEffectiveRate());

        }

//        KETERANGAN
        edtKeterangan.setText(application.getKeterangan() == null ? "" : application.getKeterangan());

//        ATTACHMENT
        final int[] countErrorImage = {0};
        tmpAttachments = new ArrayList<>();
        for (int i = 0; i < application.getAttachmentElc().size(); i++) {
            tmpAttachments.add(application.getAttachmentElc().get(i));
            Glide.with(getContext()).load(application.getAttachmentElc().get(i)).centerCrop().into((ImageView) viewTakeImages.get(i));
            viewPbImages.get(i).setVisibility(View.VISIBLE);
            viewCameras.get(i).setVisibility(View.GONE);

            viewDeleteImages.get(i).setVisibility(View.GONE);
            if (i == 24) break;
        }

//        REKOMENDASI
        if (Integer.parseInt(application.getReasonRecomendationId()) != 0) {
            lnSpnRecomendation.setVisibility(View.VISIBLE);
            rbRecomendationNo.setChecked(true);
            int valueRekomendasi = Integer.parseInt(application.getReasonRecomendationId()) - 1;
            spnRecomendation.setSelection(valueRekomendasi);
            recomendationId = application.getReasonRecomendationId();
        } else if (application.getReasonRecomendationId().isEmpty() || application.getReasonRecomendationId().equals("0") || application.getReasonRecomendationId() == null) {
            lnSpnRecomendation.setVisibility(View.GONE);
            rbRecomendationYes.setChecked(true);
            recomendationId = "0";
        }
        edtDescRecomendation.setText("".equalsIgnoreCase(application.getReasonRecomendationNotes()) ? "" : application.getReasonRecomendationNotes());

        // Persetujuan Penawaran
        if (!application.getAgreetoAcceptOtherOffering().isEmpty()) {
            if (application.getAgreetoAcceptOtherOffering().equals("1")) {
                rbPersetujuanPenawaranTrue.setChecked(true);
            } else {
                rbPersetujuanPenawaranFalse.setChecked(true);
            }
        }
        if (!application.getAdvanceCustomerAgreement().isEmpty()) {
            if (application.getAdvanceCustomerAgreement().equals("1")) {
                rbPersetujuanTambahanTrue.setChecked(true);
            } else {
                rbPErsetujuanTambahanFalse.setChecked(false);
            }
        }


        //LOCATIONS
        validateAction.setText(application.getLocation().getValidateAction() == null ? "" : application.getLocation().getValidateAction());
        validateLongitude.setText(application.getLocation().getValidateLongitude() == null ? "" : application.getLocation().getValidateLongitude());
        validateLatitude.setText(application.getLocation().getValidateLatitude() == null ? "" : application.getLocation().getValidateLatitude());
        takeKtpAction.setText(application.getLocation().getTakeKtpAction() == null ? "" : application.getLocation().getTakeKtpAction());
        takeKtpLongitude.setText(application.getLocation().getTakeKtpLongitude() == null ? "" : application.getLocation().getTakeKtpLongitude());
        takeKtpLatitude.setText(application.getLocation().getTakeKtpLatitude() == null ? "" : application.getLocation().getTakeKtpLatitude());
        takeCustomerAction.setText(application.getLocation().getTakeCustomerAction() == null ? "" : application.getLocation().getTakeCustomerAction());
        takeCustomerLongitude.setText(application.getLocation().getTakeCustomerLongitude() == null ? "" : application.getLocation().getTakeCustomerLongitude());
        takeCustomerLatitude.setText(application.getLocation().getTakeCustomerLatitude() == null ? "" : application.getLocation().getTakeCustomerLatitude());
        takePaycheckAction.setText(application.getLocation().getTakePaycheckAction() == null ? "" : application.getLocation().getTakePaycheckAction());
        takePaycheckLongitude.setText(application.getLocation().getTakePaycheckLongitude() == null ? "" : application.getLocation().getTakePaycheckLongitude());
        takePaycheckLatitude.setText(application.getLocation().getTakePaycheckLatitude() == null ? "" : application.getLocation().getTakePaycheckLatitude());
        takeAdditionalDocumentsAction.setText(application.getLocation().getTakeAdditionalDocumentsAction() == null ? "" : application.getLocation().getTakeAdditionalDocumentsAction());
        takeAdditionalDocumentsLongitude.setText(application.getLocation().getTakeAdditionalDocumentsLongitude() == null ? "" : application.getLocation().getTakeAdditionalDocumentsLongitude());
        takeAdditionalDocumentsLatitude.setText(application.getLocation().getTakeAdditionalDocumentsLatitude() == null ? "" : application.getLocation().getTakeAdditionalDocumentsLatitude());
        takeSignatureAction.setText(application.getLocation().getTakeSignatureAction() == null ? "" : application.getLocation().getTakeSignatureAction());
        takeSignatureLongitude.setText(application.getLocation().getTakeSignatureLongitude() == null ? "" : application.getLocation().getTakeSignatureLongitude());
        takeSignatureLatitude.setText(application.getLocation().getTakeSignatureLatitude() == null ? "" : application.getLocation().getTakeSignatureLatitude());
        submitAction.setText(application.getLocation().getSubmitAction() == null ? "" : application.getLocation().getSubmitAction());
        submitLongitude.setText(application.getLocation().getSubmitLongitude() == null ? "" : application.getLocation().getSubmitLongitude());
        submitLatitude.setText(application.getLocation().getSubmitLatitude() == null ? "" : application.getLocation().getSubmitLatitude());
        syncAction.setText(application.getLocation().getSyncAction() == null ? "" : application.getLocation().getSyncAction());
        syncLongitude.setText(application.getLocation().getSyncLongitude() == null ? "" : application.getLocation().getSyncLongitude());
        syncLatitude.setText(application.getLocation().getSyncLatitude() == null ? "" : application.getLocation().getSyncLatitude());
        strEfNumberResponse = application.geteFNumber();

        String type;
        if (form.equalsIgnoreCase("Edit")) typeEdit = "assign";
        else typeEdit = "draft";

        mBlackListPresenter.blackList(token,
                edtTanggalLahirPribadi.getText().toString(),
                edtNoKtpPribadi.getText().toString(),
                edtHandphonePribadi.getText().toString(),
                typeDataOfferingBlackList,
                aoBranch, edtNamaLegalValidasi.getText().toString(), edtIbuValidasi.getText().toString(), typeEdit);
        checkStatusPernikahan();
        if (!form.equals("Draft"))
            disAbleFieldAssignEdit();
    }

    //TODO: untuk asign edit cek disini
    private void disAbleFieldAssignEdit() {
        btnAddAsset.setVisibility(View.GONE);
        edtNoKtpPribadi.setEnabled(false);
        edtNamaPribadi.setEnabled(false);
        edtNamaKtpPribadi.setEnabled(false);
        if (!edtNamaIbuPribadi.getText().toString().equals("")) {
            edtNamaIbuPribadi.setEnabled(false);
            edtNamaIbuPribadi.setTextColor(GRAY);
        }
        edtHandphonePribadi.setEnabled(false);
        edtTanggalLahirPribadi.setEnabled(false);
        edtTempatLahirPribadi.setEnabled(false);
        edtPenghasilanPasanganPerusahaan.setEnabled(false);
        edtPenghasilanLainPerusahaan.setEnabled(false);
        edtPenghasilanTetapPerusahaan.setEnabled(false);
        edtPremiAsuransiManual.setEnabled(false);
        rbGroupAsuransiAgunan.setEnabled(false);
        rbGroupManualAsuransi.setEnabled(false);
        rbAsuransiAgunanYes.setEnabled(false);
        rbAsuransiAgunanNo.setEnabled(false);
        rbManualYes.setEnabled(false);
        rbManualNo.setEnabled(false);
        spnTenorProduct.setEnabled(false);
        edtFlatRatePerhitungan.setEnabled(false);
        edtTipePembayaran.setEnabled(false);
        edtBiayaAdminPerhitungan.setEnabled(false);
        edtBiayaLainnya.setEnabled(false);
        edtRefundSubsidiPerhitungan.setEnabled(false);
        edtPurchasePricePerhitungan.setEnabled(false);
        edtDiscountPerhitungan.setEnabled(false);
        edtNtfPerhitungan.setEnabled(false);
        edtEffectiveRatePerhitungan.setEnabled(false);
        edtPremiAsuransi.setEnabled(false);
        edtJumlahPembiayaan.setEnabled(false);
        edtTotalBungaPembiayaan.setEnabled(false);
        edtTotalPinjaman.setEnabled(false);
        edtBebasBungaPerhitungan.setEnabled(false);
        edtBungaPembiayaanBulan.setEnabled(false);
        edtPembayaranDelaer.setEnabled(false);
        edtAngsuranPerbulan.setEnabled(false);
        edtPembayaranPertama.setEnabled(false);
        actSupplierAsset.setEnabled(false);
        actMarketingSupplierAsset.setEnabled(false);
        actProductOfferingProduct.setEnabled(false);
        actPosProduct.setEnabled(false);
        rbAsuransiAgunanYes.setEnabled(false);
        rbAsuransiAgunanNo.setEnabled(false);
        rbManualNo.setEnabled(false);
        rbManualYes.setEnabled(false);
        edtDpPerhitungan.setEnabled(false);
        edtNamaPribadi.setTextColor(GRAY);
        edtNamaKtpPribadi.setTextColor(GRAY);
        edtNoKtpPribadi.setTextColor(GRAY);
        edtTempatLahirPribadi.setTextColor(GRAY);

        edtHandphonePribadi.setTextColor(GRAY);
        edtTanggalLahirPribadi.setTextColor(GRAY);
        edtTempatLahirPribadi.setTextColor(GRAY);
        edtBebasBungaPerhitungan.setTextColor(GRAY);
        edtBungaPembiayaanBulan.setTextColor(GRAY);
        edtPembayaranDelaer.setTextColor(GRAY);
        edtPenghasilanPasanganPerusahaan.setTextColor(GRAY);
        edtPenghasilanLainPerusahaan.setTextColor(GRAY);
        edtPenghasilanTetapPerusahaan.setTextColor(GRAY);
        edtPremiAsuransiManual.setTextColor(GRAY);
        edtTipePembayaran.setTextColor(GRAY);
        edtFlatRatePerhitungan.setTextColor(GRAY);
        edtBiayaAdminPerhitungan.setTextColor(GRAY);
        edtBiayaLainnya.setTextColor(GRAY);
        edtRefundSubsidiPerhitungan.setTextColor(GRAY);
        edtPurchasePricePerhitungan.setTextColor(GRAY);
        edtDiscountPerhitungan.setTextColor(GRAY);
        edtDpPerhitungan.setTextColor(GRAY);
        edtNtfPerhitungan.setTextColor(GRAY);
        edtEffectiveRatePerhitungan.setTextColor(GRAY);
        edtPremiAsuransi.setTextColor(GRAY);
        edtJumlahPembiayaan.setTextColor(GRAY);
        edtTotalBungaPembiayaan.setTextColor(GRAY);
        edtTotalPinjaman.setTextColor(GRAY);
        edtAngsuranPerbulan.setTextColor(GRAY);
        edtPembayaranPertama.setTextColor(GRAY);
        actSupplierAsset.setTextColor(GRAY);
        actMarketingSupplierAsset.setTextColor(GRAY);
        actProductOfferingProduct.setTextColor(GRAY);
        actPosProduct.setTextColor(GRAY);
        edtAngsuranPerbulanBebasBunga.setTextColor(GRAY);

    }

    @Override
    public void onFailedSubmitPengajuanEditLoad(String message) {
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        tvMessage.setText(message);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPengajuanDetailPresenter.loadPengajuanDetail(token, appId);
            }
        });
    }

    @Override
    public void onDetailTokenExpired() {
        pbMain.setVisibility(View.GONE);
        mRefreshTokenPresenter.refreshToken(token);
    }

    private void setFieldDataProductDraft(List<AssetElektronik> assetElektroniks) {
        for (int i = 0; i < assetElektroniks.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewAsset = inflater.inflate(R.layout.view_content_detail_asset_asset, null);

            TextView txtTitle = viewAsset.findViewById(R.id.txt_title_asset);
            final NiceAutoCompleteTextView actNamaBarang = viewAsset.findViewById(R.id.act_nama_barang_asset);
            EditText actTypeKendaraan = viewAsset.findViewById(R.id.edt_type_asset);
            final EditText edtPrice = viewAsset.findViewById(R.id.edt_price_asset);
            final EditText edtDp = viewAsset.findViewById(R.id.edt_dp_asset);
            final EditText edtDiscount = viewAsset.findViewById(R.id.edt_discount_asset);
            final TextView warningPriceLess = viewAsset.findViewById(R.id.txt_warning_price);
            final TextView warningDpLessThan = viewAsset.findViewById(R.id.txt_warning_dp_less_than_10);

            actNamaBarang.setAdapter(assetMasterAdapter);
            actNamaBarang.setTag(assetElektroniks.get(i).getKodeBarang() == null ? "" : assetElektroniks.get(i).getKodeBarang());
            if (assetElektroniks.get(i).getNamaBarang() != null) {
                actNamaBarang.setText(assetElektroniks.get(i).getNamaBarang());
                actNamaBarang.setSelectionFromPopUp(true);
            } else actNamaBarang.setSelectionFromPopUp(false);
            actTypeKendaraan.setText(assetElektroniks.get(i).getType() == null ? "" : assetElektroniks.get(i).getType());
            edtPrice.setText(assetElektroniks.get(i).getPrice() == null ? "" : assetElektroniks.get(i).getPrice());
            edtDiscount.setText(assetElektroniks.get(i).getDiscount() == null ? "" : assetElektroniks.get(i).getDiscount());
            edtDp.setText(assetElektroniks.get(i).getDp() == null ? "" : assetElektroniks.get(i).getDp());

            if (form.equals("Draft") && draftEdit.equals("draft_edit")) {
                actNamaBarang.setEnabled(false);
                actTypeKendaraan.setEnabled(false);
                edtPrice.setEnabled(false);
                edtDp.setEnabled(false);
                edtDiscount.setEnabled(false);
                actNamaBarang.setTextColor(GRAY);
                actTypeKendaraan.setTextColor(GRAY);
                edtPrice.setTextColor(GRAY);
                edtDp.setTextColor(GRAY);
                edtDiscount.setTextColor(GRAY);
            }
            llParentTotalAsset.addView(viewAsset, i);
        }
    }

    private void setFieldDataProduct(List<Asset> assets) {
        for (int i = 0; i < assets.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewAsset = inflater.inflate(R.layout.view_content_detail_asset_asset, null);

            TextView txtTitle = (TextView) viewAsset.findViewById(R.id.txt_title_asset);
            final NiceAutoCompleteTextView actNamaBarang = (NiceAutoCompleteTextView) viewAsset.findViewById(R.id.act_nama_barang_asset);
            EditText actTypeKendaraan = (EditText) viewAsset.findViewById(R.id.edt_type_asset);
            final EditText edtPrice = (EditText) viewAsset.findViewById(R.id.edt_price_asset);
            final EditText edtDp = (EditText) viewAsset.findViewById(R.id.edt_dp_asset);
            final EditText edtDiscount = (EditText) viewAsset.findViewById(R.id.edt_discount_asset);
            final TextView warningPriceLess = (TextView) viewAsset.findViewById(R.id.txt_warning_price);
            final TextView warningDpLessThan = (TextView) viewAsset.findViewById(R.id.txt_warning_dp_less_than_10);

            actNamaBarang.setTag(assets.get(i).getAssetCode() == null ? "" : assets.get(i).getAssetCode());
            actNamaBarang.setText(assets.get(i).getAssetName() == null ? "" : assets.get(i).getAssetName());
            actTypeKendaraan.setText(assets.get(i).getType() == null ? "" : assets.get(i).getType());
            edtPrice.setText(assets.get(i).getOTRPrice());
            edtDp.setText(assets.get(i).getDPAmount());
            edtDiscount.setText(assets.get(i).getDiscount());
            categoriIdList.add(assets.get(i).getTypeAsset());

            actNamaBarang.setEnabled(false);
            actTypeKendaraan.setEnabled(false);
            edtPrice.setEnabled(false);
            edtDp.setEnabled(false);
            edtDiscount.setEnabled(false);
            actNamaBarang.setTextColor(GRAY);
            actTypeKendaraan.setTextColor(GRAY);
            edtPrice.setTextColor(GRAY);
            edtDp.setTextColor(GRAY);
            edtDiscount.setTextColor(GRAY);
            actNamaBarang.setSelectionFromPopUp(true);

            llParentTotalAsset.addView(viewAsset, i);
        }
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
                                Toast.makeText(FormPengajuanActivity.this, R.string.warning_tanggal_ktp, Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    calendar.set(2011, 0, 1);
                    Date terbitKtpMin = calendar.getTime();
                    DatePickerDialog customDatePicker;

                    if (edtTerbitKtpPribadi.getText().toString().isEmpty()) {
                        customDatePicker = customDatePicker(myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
                    } else {
                        customDatePicker = customDatePicker(myDateListener, edtTerbitKtpPribadiYear, edtTerbitKtpPribadiMonth, edtTerbitKtpPribadiDay, true);
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
                            edtTanggalLahirPribadi.setText(year + "-" + sMonth + "-" + sDay);
                        }
                    };

                    //Set max year to 12 years ago
                    calendar.setTime(new Date());
                    if ("Lajang".equalsIgnoreCase(spnPernikahanPribadi.getSelectedItem().toString()))
                        calendar.add(Calendar.YEAR, -21);
                    else calendar.add(Calendar.YEAR, -17);

                    Date birthdayMaxDate = calendar.getTime();
                    DatePickerDialog cdpBirdhDay = customDatePicker(myBirthDay, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
                    cdpBirdhDay.getDatePicker().setMaxDate(birthdayMaxDate.getTime());
                    cdpBirdhDay.show();

                    break;
                case R.id.edt_tanggal_lahir_pasangan:
                    DatePickerDialog.OnDateSetListener pasanganBirthDay = new DatePickerDialog.OnDateSetListener() {
                        int monthLahirPasangan;

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            monthLahirPasangan = month + 1;
                            String monthString = Integer.toString(monthLahirPasangan);
                            String dayString = Integer.toString(day);
                            String sMonth = monthLahirPasangan < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            edtTanggalLahirPasangan.setText(year + "-" + sMonth + "-" + sDay);
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

                    if (edtTanggalLahirPasangan.getText().toString().isEmpty()) {
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
                                Toast.makeText(FormPengajuanActivity.this, R.string.warning_tahun_lebih, Toast.LENGTH_SHORT).show();
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
                        cdpTahunTinggal = customDatePicker(myTahunTinggal, edtTinggalSejakTahunPribadiYear, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, true);
                    }


//                    cdpTahunTinggal.getDatePicker().setMaxDate(maxDateYear.getTime());
                    cdpTahunTinggal.show();
                    break;
                case R.id.edt_tinggal_sejak_bulan_pribadi:
                    DatePickerDialog.OnDateSetListener myBulanTinggal = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            if (isNowYearPribadi && month + 1 > nowMonth + 1) {
                                Toast.makeText(FormPengajuanActivity.this, R.string.warning_bulan_lebih, Toast.LENGTH_SHORT).show();
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
                        cdpBulanTinggal = customDatePicker(myBulanTinggal, calendar.get(Calendar.YEAR), edtTinggalSejakBulanPribadiMonth, calendar.get(Calendar.DAY_OF_MONTH), View.GONE, View.VISIBLE, View.GONE, true);
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
                        cdpTahunBekerja = customDatePicker(myTahunBekerja, edtBekerjaSejakPerusahaanYear, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, true);
                    }
                    cdpTahunBekerja.show();
                    break;
                case R.id.edt_month_expired_kartu_kredit:
                    DatePickerDialog.OnDateSetListener myBulanKadaluarsaKartu = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            String monthString = Integer.toString(month + 1) + "";
                            edtBulanKadaluarsaKartuKredit.setText(monthString);
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
                            edtTahunKadaluarsaKartuKredit.setText(yearString);
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
                            edtTanggalEfektif.setText(year + "-" + sMonth + "-" + sDay);
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
                            edtTanggalExpired.setText(year + "-" + sMonth + "-" + sDay);
                        }
                    };
                    calendar.add(Calendar.YEAR, +1);

                    DatePickerDialog cdpTanggalExpired = customDatePicker(myTanggalExpired, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
                    cdpTanggalExpired.show();
                    break;
                case R.id.edt_tanggal_lahir_pribadi_validasi:
                    DatePickerDialog.OnDateSetListener myBirthDayVal = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            int monthTanggalLahir = month + 1;
                            String monthString = Integer.toString(monthTanggalLahir);
                            String dayString = Integer.toString(day);
                            String sMonth = monthTanggalLahir < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            edtTanggalLahirPribadiValidasi.setText(year + "-" + sMonth + "-" + sDay);


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

                    if (edtTanggalLahirPribadiValidasi.getText().toString().isEmpty()) {
                        cdpBirdhDayVal = customDatePicker(myBirthDayVal, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
                    } else {
                        cdpBirdhDayVal = customDatePicker(myBirthDayVal, edtValidasiTanggalLahirYear, edtValidasiTanggalLahirMonth, edtValidasiTanggalLahirDay, false);
                    }
                    cdpBirdhDayVal.getDatePicker().setMaxDate(birthdayMaxDateVal.getTime());
                    cdpBirdhDayVal.show();
                    break;

            }
        }
    }

    private DatePickerDialog customDatePicker(DatePickerDialog.OnDateSetListener
                                                      mDateSetListner, int mYear, int mMonth, int mDay, boolean isMaxNowDate) {
        return customDatePicker(mDateSetListner, mYear, mMonth, mDay, View.VISIBLE, View.VISIBLE, View.VISIBLE, isMaxNowDate);
    }

    private DatePickerDialog customDatePicker(DatePickerDialog.OnDateSetListener
                                                      mDateSetListner, int mYear, int mMonth, int mDay, final int mModeViewYear,
                                              final int mModeViewMonth, final int mvModeViewDay, boolean isMaxNowDate) {
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

    private TextWatcherCustome.AfterTextChange implementAfterChange = new TextWatcherCustome.AfterTextChange() {
        @Override
        public void afterTextChanged(Editable s, View v) {
            String x = s.toString();
            if (x.startsWith("00")) x = "0";
            switch (v.getId()) {
                case R.id.edt_flat_rate_perhitungan:
                    //dirubah biar bisa di input angka 0
                    if (x.startsWith(".")) s.clear();
                    break;
                case R.id.edt_no_ktp_pribadi_validasi:
                    edtNoKtpPribadi.setText(s);
                    break;
                case R.id.edt_tanggal_lahir_pribadi_validasi:
                    edtTanggalLahirPribadi.setText(s);
                    break;
                case R.id.edt_handphone_pribadi_validasi:
                    edtHandphonePribadi.setText(s);
                    break;
                case R.id.edt_nama_legal_validasi:
                    edtNamaKtpPribadi.setText(s);
                    edtNamaKtpPribadi.setText(s);
                    edtNamaKtpPribadi.setEnabled(false);
                    edtNamaKtpPribadi.setTextColor(Color.GRAY);
                    break;
                case R.id.edt_nama_ibu_validasi:
                    edtNamaIbuPribadi.setText(s);
                    edtNamaIbuPribadi.setEnabled(false);
                    edtNamaIbuPribadi.setTextColor(Color.GRAY);
                    break;
            }
        }
    };

    private boolean checkCompleteDropDownKop() {
        if (spnMetodePenjualan.getSelectedItem().toString().equals("Pilih") || spnMetodePenjualan.getSelectedItem().toString().equals("PILIH")) {
            imgDropDownKop.setImageResource(android.R.drawable.ic_dialog_alert);
            tvJenisMetode.setTextColor(Color.RED);
            return false;
        } else {
            imgDropDownKop.setImageResource(R.drawable.ic_arrow);
            tvJenisMetode.setTextColor(Color.BLACK);
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null)
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void permissionCameraStorage(final int requestCode) {
        mPermissionHelper = new PermissionHelper.Builder(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA).withPermissionInfos(
                getString(R.string.warning_permission_location),
                getString(R.string.warning_permission_location),
                getString(R.string.warning_permission_camera)).withListener(new PermissionHelper.OnPermissionCheckedListener() {
            @Override
            public void onPermissionGranted(boolean isPermissionAlreadyGranted) {
                //TODO:Untuk intent ke Custom Camera

                if (requestCode == 101) {

                    Intent intent = new Intent(FormPengajuanActivity.this, CameraLandscapeActivity.class);
                    intent.putExtra(CameraActivity.FRAME, CameraLandscapeActivity.FRAME_KTP);
                    intent.putExtra(CameraActivity.IS_OCR, true);
                    startActivityForResult(intent, requestCode);

                } else if (requestCode == 102) {
//                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraCustomFrame.class);
//                    startActivity(intent);

                    Intent intent = new Intent(FormPengajuanActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
                    intent.putExtra(CameraActivity.IS_OCR, false);
                    startActivityForResult(intent, requestCode);
                } else if (requestCode == 103) {
//                    Intent intent = new Intent(FormPengajuanNonElcActivity.this, CameraCustomFrame.class);
//                    startActivity(intent);

                    Intent intent = new Intent(FormPengajuanActivity.this, CameraOtherActivity.class);
                    intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
                    intent.putExtra(CameraOtherActivity.IS_OCR, false);
                    intent.putExtra(CameraOtherActivity.USESWITCH, true);
                    intent.putExtra(CameraOtherActivity.ISFRONT, false);
                    startActivityForResult(intent, requestCode);
                } else {

                    Intent intent = new Intent(FormPengajuanActivity.this, CameraOtherActivity.class);
                    intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_OTHER);
                    intent.putExtra(CameraActivity.IS_OCR, false);
                    intent.putExtra(CameraOtherActivity.USESWITCH, false);
                    intent.putExtra(CameraOtherActivity.ISFRONT, false);
                    startActivityForResult(intent, requestCode);
                }

            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(FormPengajuanActivity.this, R.string.txt_tidak_memiliki_permissions_camera_storage, Toast.LENGTH_SHORT).show();
            }
        }).build();
        mPermissionHelper.requestPermission();
    }

    private void setSectionHeadersToDefault() {
        imgDropDownPribadi.setImageResource(R.drawable.ic_arrow);
        imgDropDownAlamat.setImageResource(R.drawable.ic_arrow);
        imgDropDownKerabat.setImageResource(R.drawable.ic_arrow);
        imgDropDownPekerjaan.setImageResource(R.drawable.ic_arrow);
        imgDropDownDataKartuKredit.setImageResource(R.drawable.ic_arrow);
        imgDropDownDataKartuMembership.setImageResource(R.drawable.ic_arrow);
        imgDropDownProduct.setImageResource(R.drawable.ic_arrow);
        imgDropDownAsset.setImageResource(R.drawable.ic_arrow);
        imgDropDownAsuransi.setImageResource(R.drawable.ic_arrow);
        imgDropDownPerhitungan.setImageResource(R.drawable.ic_arrow);
        imgDropDownAttachment.setImageResource(R.drawable.ic_arrow);
        imgDropDownPersetujuan.setImageResource(R.drawable.ic_arrow);
    }

    private boolean checkDataRekomendasi() {
        if (!rbRecomendationYes.isChecked() && !rbRecomendationNo.isChecked()) {
            imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
            tvGroupRekomendasi.setTextColor(Color.RED);
        } else if (rbRecomendationYes.isChecked()) {
            imgDropDownRekomendasi.setImageResource(R.drawable.ic_arrow);
            tvGroupRekomendasi.setTextColor(Color.BLACK);
        } else if (rbRecomendationNo.isChecked()) {
            if ("Pilih".equalsIgnoreCase(spnRecomendation.getSelectedItem().toString())) {
                imgDropDownRekomendasi.setImageResource(android.R.drawable.ic_dialog_alert);
                tvGroupRekomendasi.setTextColor(Color.RED);
            } else imgDropDownRekomendasi.setImageResource(R.drawable.ic_arrow);
        }
        return rbRecomendationYes.isChecked() || rbRecomendationNo.isChecked() && !"Pilih Data".equalsIgnoreCase(spnRecomendation.getSelectedItem().toString());
    }

    private boolean checkDataPerhitungan(RelativeLayout v) {
        hideAllContent(v);
        if (v.getVisibility() == View.GONE) {
            if (rlDataPerhitungan.getVisibility() == View.GONE) {
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

    private void setDefaultCalculate() {
        if (edtFlatRatePerhitungan.getText().toString().isEmpty())
            edtFlatRatePerhitungan.setText("0");
        if (edtBiayaAdminPerhitungan.getText().toString().isEmpty())
            edtBiayaAdminPerhitungan.setText("0");
        if (edtTipePembayaran.getText().toString().isEmpty())
            edtTipePembayaran.setText("-");
        if (edtBiayaLainnya.getText().toString().isEmpty()) edtBiayaLainnya.setText("0");
        if (edtRefundSubsidiPerhitungan.getText().toString().isEmpty())
            edtRefundSubsidiPerhitungan.setText("0");
        if (edtBebasBungaPerhitungan.getText().toString().isEmpty())
            edtBebasBungaPerhitungan.setText("0");
        if (edtPurchasePricePerhitungan.getText().toString().isEmpty())
            edtPurchasePricePerhitungan.setText("0");
        if (edtDiscountPerhitungan.getText().toString().isEmpty())
            edtDiscountPerhitungan.setText("0");
        if (edtDpPerhitungan.getText().toString().isEmpty()) edtDpPerhitungan.setText("0");
        if (edtPremiAsuransi.getText().toString().isEmpty()) edtPremiAsuransi.setText("0");
        if (edtNtfPerhitungan.getText().toString().isEmpty())
            edtNtfPerhitungan.setText("0");
        if (edtJumlahPembiayaan.getText().toString().isEmpty())
            edtJumlahPembiayaan.setText("0");
        if (edtTotalBungaPembiayaan.getText().toString().isEmpty())
            edtTotalBungaPembiayaan.setText("0");
        if (edtBungaPembiayaanBulan.getText().toString().isEmpty())
            edtBungaPembiayaanBulan.setText("0");
        if (edtTotalPinjaman.getText().toString().isEmpty()) edtTotalPinjaman.setText("0");
        if (edtAngsuranPerbulanBebasBunga.getText().toString().isEmpty())
            edtAngsuranPerbulanBebasBunga.setText("0");
        if (edtAngsuranPerbulan.getText().toString().isEmpty())
            edtAngsuranPerbulan.setText("0");
        if (edtPembayaranPertama.getText().toString().isEmpty())
            edtPembayaranPertama.setText("0");

        edtFlatRatePerhitungan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edtFlatRatePerhitungan.getText().toString().isEmpty())
                        edtFlatRatePerhitungan.setText("0");
                    // TODO:Comment Calculating
                    /*calculating();*/
                    prepareDataCalculating();
                }
            }
        });
        edtBiayaAdminPerhitungan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edtBiayaAdminPerhitungan.getText().toString().isEmpty())
                        edtBiayaAdminPerhitungan.setText("0");
                    prepareDataCalculating();
                }
            }
        });
        edtBiayaLainnya.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edtBiayaLainnya.getText().toString().isEmpty())
                        edtBiayaLainnya.setText("0");
                    prepareDataCalculating();
                }
            }
        });
        edtRefundSubsidiPerhitungan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (edtRefundSubsidiPerhitungan.getText().toString().isEmpty())
                    edtRefundSubsidiPerhitungan.setText("0");
            }
        });
        edtAngsuranPerbulan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
        edtAngsuranPerbulan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    long pinjamanLama = Long.parseLong(etTotalPinjamanContainer.getText().toString().replaceAll(",", ""));
                    long angsuranPerbulanBaru = Long.parseLong(edtAngsuranPerbulan.getText().toString().replaceAll(",", ""));
                    long angsuranPerbulanLama = Long.parseLong(etAngsuranPerhitunganContainer.getText().toString().replaceAll(",", ""));

                    long jumlahPembiayaanBaru = Long.parseLong(edtJumlahPembiayaan.getText().toString().replaceAll(",", ""));
                    long jumlahPembiayaanLama = Long.parseLong(etJumlahPembiayaanContainer.getText().toString().replaceAll(",", ""));

                    double tenorBaru = Double.valueOf(spnTenorProduct.getSelectedItem().toString());
                    double downpaymentBaru = Double.valueOf(edtDpPerhitungan.getText().toString().replaceAll(",", ""));
                    double biayaAdminPerhitunganLama = Double.valueOf(etBiayaAdminPerhitunganContainer.getText().toString().replaceAll(",", ""));
                    double angsuranPertamaPerhitunganBaru = Double.valueOf(edtPembayaranPertama.getText().toString().replaceAll(",", ""));

                    if (!edtAngsuranPerbulan.getText().toString().isEmpty()) {
                        long totalPinjamanBaru = (pinjamanLama * angsuranPerbulanBaru) / angsuranPerbulanLama;
                        long totalBungaPembiayaanBaru = totalPinjamanBaru - jumlahPembiayaanLama;
                        long totalFlatRateBaru = (long) (jumlahPembiayaanBaru * tenorBaru) / totalBungaPembiayaanBaru;
                        long totalSetorPertamaBaru = (long) (downpaymentBaru + biayaAdminPerhitunganLama + angsuranPertamaPerhitunganBaru);

                        edtTotalPinjaman.setText(String.valueOf(totalPinjamanBaru));
                        edtTotalBungaPembiayaan.setText(String.valueOf(totalBungaPembiayaanBaru));
                        edtFlatRatePerhitungan.setText(String.valueOf(totalFlatRateBaru));
                        edtPembayaranPertama.setText(String.valueOf(totalSetorPertamaBaru));
                    }
                }
            }
        });
    }

    private void prepareDataCalculating() {
        edtEffectiveRatePerhitungan.setText(0 + "");
        otr_price = 0;
        for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
            View view = llParentTotalAsset.getChildAt(i);
            IndonesianCurrencyEditText edtPriceAsset = view.findViewById(R.id.edt_price_asset);

            otr_price += edtPriceAsset.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtPriceAsset.getText().toString().replace(",", ""));
        }
        down_payment = 0;
        for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
            View view = llParentTotalAsset.getChildAt(i);
            EditText edtDp = view.findViewById(R.id.edt_dp_asset);

            down_payment += edtDp.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtDp.getText().toString().replace(",", ""));
        }
        discount = 0;
        for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
            View view = llParentTotalAsset.getChildAt(i);

            EditText edtDiscount = view.findViewById(R.id.edt_discount_asset);
            discount += edtDiscount.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtDiscount.getText().toString().replace(",", ""));
        }


    }


    // TODO: Calculasi untuk perhitungan
    private void calculating() {
        long totalHargaBarang;
        long totalDiscountBarang;
        long totalDownPaymentBarang;
        long replacePremiAsuransi;
        long replaceJumlahPembiayaanValue;
        long replaceBungaPembiayaan;
        long replaceTotalPinjamanValue;
        long replaceAngsuranPerbulan;
        long replaceAngsuranPertama;
        long replaceAngsuranPerbulanBebasBunga;
        long bungaPembiayaanPerbulan;
        long totalPriceAsset = 0;
        long dpAsset = 0;
        long discountAsset = 0;
        long replaceNtfValue;
        long biayaLainnyaValue = "".equalsIgnoreCase(edtBiayaLainnya.getText().toString()) ? 0 : Long.parseLong(edtBiayaLainnya.getText().toString().replace(",", ""));
        long biayaAdminValue = "".equalsIgnoreCase(edtBiayaAdminPerhitungan.getText().toString()) ? 0 : Long.parseLong(edtBiayaAdminPerhitungan.getText().toString().replace(",", ""));
        // TODO: Comment untuk tenor
        //int tenor = Integer.parseInt((spnTenorProduct.getSelectedItem() == null) ? "0" : spnTenorProduct.getSelectedItem().toString());*/
        int intBebasBunga = Integer.parseInt((edtBebasBungaPerhitungan.getText().toString().isEmpty()) ? "0" : edtBebasBungaPerhitungan.getText().toString());
        double flatRateValue = "".equalsIgnoreCase(edtFlatRatePerhitungan.getText().toString()) ? 0 : ((Double.parseDouble(edtFlatRatePerhitungan.getText().toString())) / 100);
        long angsuranPertamaBebasBunga;
        long pembayaranDealer;

        if (typeDataOffering.equalsIgnoreCase("ELC")) {
//        BEBAS BUNGA
            edtBebasBungaPerhitungan.setText(String.valueOf(intBebasBunga));

//        HITUNG HARGA BARANG
            for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
                View view = llParentTotalAsset.getChildAt(i);
                IndonesianCurrencyEditText edtPriceAsset = view.findViewById(R.id.edt_price_asset);

                totalPriceAsset += edtPriceAsset.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtPriceAsset.getText().toString().replace(",", ""));
            }
            // TODO: Ambil total Harga disini
            totalHargaBarang = totalPriceAsset;
            edtPurchasePricePerhitungan.setText(String.valueOf(totalPriceAsset));

//        HITUNG DP
            for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
                View view = llParentTotalAsset.getChildAt(i);
                EditText edtDp = (EditText) view.findViewById(R.id.edt_dp_asset);

                dpAsset += edtDp.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtDp.getText().toString().replace(",", ""));
            }
            // TODO: Ambil DP disini
            totalDownPaymentBarang = dpAsset;
            edtDpPerhitungan.setText(String.valueOf(dpAsset));

//        HITUNG DISCOUNT
            for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
                View view = llParentTotalAsset.getChildAt(i);

                EditText edtDiscount = (EditText) view.findViewById(R.id.edt_discount_asset);
                discountAsset += edtDiscount.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtDiscount.getText().toString().replace(",", ""));
            }
            // TODO: Total diskon barang
            totalDiscountBarang = discountAsset;
            edtDiscountPerhitungan.setText(String.valueOf(discountAsset));

//        HITUNG PREMI
            if (rbAsuransiAgunanNo.isChecked()) {
                tilPremiManual.setVisibility(View.GONE);
                edtPremiAsuransiManual.setVisibility(View.GONE);
                replacePremiAsuransi = 0;
                edtPremiAsuransi.setText(String.valueOf(replacePremiAsuransi));
            } else {
                if (!manualPremi) {
                    double premiAsuransiValue = (totalInsurancePersen / 100) * totalHargaBarang * tenor;
                    replacePremiAsuransi = (long) Math.ceil(premiAsuransiValue);
                    edtPremiAsuransi.setText(String.valueOf(replacePremiAsuransi));
                } else {
                    replacePremiAsuransi = Long.parseLong(edtPremiAsuransiManual.getText().toString().replace(",", ""));
                    edtPremiAsuransi.setText("".equalsIgnoreCase(edtPremiAsuransiManual.getText().toString()) ? "0" : String.valueOf(replacePremiAsuransi));
                }
            }

//        HITUNG NTF
            /*replaceNtfValue = totalHargaBarang - (totalDiscountBarang + totalDownPaymentBarang);*/
            replaceNtfValue = (long) (Math.ceil((totalHargaBarang - (totalDiscountBarang + totalDownPaymentBarang) + replacePremiAsuransi + biayaLainnyaValue) / 1000.0) * 1000.0);
            edtNtfPerhitungan.setText(String.valueOf(replaceNtfValue));

//        HITUNG JUMLAH PEMBIAYAAN
            /*replaceJumlahPembiayaanValue = (long) (Math.ceil((replaceNtfValue + replacePremiAsuransi + biayaLainnyaValue) / 1000.0) * 1000.0);*/
            replaceJumlahPembiayaanValue = (long) (Math.ceil((totalHargaBarang - (totalDiscountBarang + totalDownPaymentBarang) + replacePremiAsuransi + biayaLainnyaValue) / 1000.0) * 1000.0);
            edtJumlahPembiayaan.setText(String.valueOf(replaceJumlahPembiayaanValue));

//        HITUNG TOTAL BUNGA PEMBIAYAAN
            replaceBungaPembiayaan = (long) (Math.ceil((replaceJumlahPembiayaanValue * flatRateValue * tenor) / 1000.0) * 1000.0);
            edtTotalBungaPembiayaan.setText(String.valueOf(replaceBungaPembiayaan));

//        HITUNG BUNGA PEMBIAYAAN PERBULAN
            if (intBebasBunga == 0) bungaPembiayaanPerbulan = 0;
            else
                bungaPembiayaanPerbulan = (long) (Math.ceil((replaceBungaPembiayaan / tenor) / 1000.0) * 1000.0);
            edtBungaPembiayaanBulan.setText(String.valueOf((bungaPembiayaanPerbulan)));

//        HITUNG TOTAL PEMBIAYAAN
            replaceTotalPinjamanValue = (long) (Math.ceil((replaceJumlahPembiayaanValue + replaceBungaPembiayaan) / 1000.0) * 1000.0);
            edtTotalPinjaman.setText(String.valueOf(replaceTotalPinjamanValue));


//        HITUNG ANGSURAN PERBULAN
            long tmpValue = replaceTotalPinjamanValue / 3;
            replaceAngsuranPerbulan = (long) (Math.ceil(tmpValue / 1000.0) * 1000.0);
            edtAngsuranPerbulan.setText(String.valueOf(replaceAngsuranPerbulan));

//        HITUNG ANGSURAN PERBULAN BEBAS BUNGA
            if (intBebasBunga == 0)
                replaceAngsuranPerbulanBebasBunga = 0;
            else
                replaceAngsuranPerbulanBebasBunga = (long) (Math.ceil((replaceAngsuranPerbulan - bungaPembiayaanPerbulan) / 1000.0) * 1000.0);
            edtAngsuranPerbulanBebasBunga.setText(String.valueOf(replaceAngsuranPerbulanBebasBunga));

//        HITUNG PEMBAYARAN PERTAMA
            if (intBebasBunga == 0) {
                if (fsInstallment == null) fsInstallment = "";
                if ("AR".equalsIgnoreCase(fsInstallment))
                    replaceAngsuranPertama = (long) (Math.ceil((totalDownPaymentBarang + biayaAdminValue) / 1000.0) * 1000.0);
                else
                    replaceAngsuranPertama = (long) (Math.ceil((totalDownPaymentBarang + biayaAdminValue + replaceAngsuranPerbulan) / 1000.0) * 1000.0);
            } else {
                replaceAngsuranPertama = (long) (Math.ceil((totalDownPaymentBarang + biayaAdminValue + replaceAngsuranPerbulanBebasBunga) / 1000.0) * 1000.0);
            }
            edtPembayaranPertama.setText(String.valueOf(replaceAngsuranPertama));

//        HITUNG EFECTIVE RATE
            double effectiveRateValue = tenor * flatRateValue;
            edtEffectiveRatePerhitungan.setText(String.valueOf((long) Math.floor(effectiveRateValue)));

//        HITUNG PEMBAYARAN DEALER
            pembayaranDealer = totalPriceAsset - replaceAngsuranPertama;
            edtPembayaranDelaer.setText(String.valueOf(pembayaranDealer));
        } else {
            /*perhitungan kmb kmob*/
        }
    }

    private boolean validasiDataPerhitungan() {
        long totalPriceAsset = 0;
        for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
            View view = llParentTotalAsset.getChildAt(i);
            IndonesianCurrencyEditText edtPriceAsset = view.findViewById(R.id.edt_price_asset);

            totalPriceAsset += edtPriceAsset.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtPriceAsset.getText().toString().replace(",", ""));
        }
        return totalPriceAsset != 0;
    }

    private void setPerhitunganElektronikToContainer() {
        if (!spnTenorProduct.getSelectedItem().toString().isEmpty())
            etTenorProductContainer.setText(spnTenorProduct.getSelectedItem().toString());
        if (!edtFlatRatePerhitungan.getText().toString().isEmpty())
            etFlatRatePerhitunganContainer.setText(edtFlatRatePerhitungan.getText().toString());
        if (!edtBiayaAdminPerhitungan.getText().toString().isEmpty())
            etBiayaAdminPerhitunganContainer.setText(edtBiayaAdminPerhitungan.getText().toString());
        if (!edtTipePembayaran.getText().toString().isEmpty())
            etTipePembayaranContainer.setText(edtTipePembayaran.getText().toString());
        if (!edtBiayaLainnya.getText().toString().isEmpty())
            etBiayaLainnyaContainer.setText(edtBiayaLainnya.getText().toString());
        if (!edtRefundSubsidiPerhitungan.getText().toString().isEmpty())
            etRefundSubsidiPerhitunganContainer.setText(edtRefundSubsidiPerhitungan.getText().toString());
        if (!edtPurchasePricePerhitungan.getText().toString().isEmpty())
            etPurchasePricePerhitunganContainer.setText(edtPurchasePricePerhitungan.getText().toString());
        if (!edtDiscountPerhitungan.getText().toString().isEmpty())
            etDiscountPerhitunganContainer.setText(edtDiscountPerhitungan.getText().toString());
        if (!edtDpPerhitungan.getText().toString().isEmpty())
            etDpPerhitunganContainer.setText(edtDpPerhitungan.getText().toString());
        if (!edtNtfPerhitungan.getText().toString().isEmpty())
            etNtfPerhitunganContainer.setText(edtNtfPerhitungan.getText().toString());
        if (!edtPremiAsuransi.getText().toString().isEmpty())
            etPremiAsuransiContainer.setText(edtPremiAsuransi.getText().toString());
        if (!edtJumlahPembiayaan.getText().toString().isEmpty())
            etJumlahPembiayaanContainer.setText(edtJumlahPembiayaan.getText().toString());
        if (!edtTotalBungaPembiayaan.getText().toString().isEmpty())
            etBungaPembiayaanContainer.setText(edtTotalBungaPembiayaan.getText().toString());
        if (!edtTotalPinjaman.getText().toString().isEmpty())
            etTotalPinjamanContainer.setText(edtTotalPinjaman.getText().toString());
        if (!edtAngsuranPerbulan.getText().toString().isEmpty())
            etAngsuranPerhitunganContainer.setText(edtAngsuranPerbulan.getText().toString());
        if (!edtPembayaranPertama.getText().toString().isEmpty())
            etAngsuranPertamaPerhitunganContainer.setText(edtPembayaranPertama.getText().toString());
    }

    private void initRadioGroup() {
        if (!form.equals("Edit")) {
            rbGroupAsuransiAgunan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    View rb = rbGroupAsuransiAgunan.findViewById(checkedId);
                    switch (rb.getId()) {
                        case R.id.rb_asuransi_agunan_yes:
                            rbManualNo.setChecked(true);
                            cvManualPremiAsuransi.setVisibility(View.VISIBLE);
                            rbGroupManualAsuransi.setVisibility(View.VISIBLE);
                            manualPremi = false;
                            /*calculating();*/
                            prepareDataCalculating();
                            manualAgunan = "All Risk";
                            break;
                        case R.id.rb_asuransi_agunan_no:
                            cvManualPremiAsuransi.setVisibility(View.GONE);
                            rbGroupManualAsuransi.setVisibility(View.GONE);
                            tilPremiManual.setVisibility(View.GONE);
                            edtPremiAsuransiManual.setVisibility(View.GONE);
                            /*calculating();*/
                            prepareDataCalculating();
                            manualAgunan = "No Assurance";
                            break;
                    }
                }
            });
            rbPersetujuanPenawaranTrue.setChecked(true);
            rbPersetujuanTambahanTrue.setChecked(true);
            rbPersetujuanPenawaranSmsTrue.setChecked(true);
            rbGroupManualAsuransi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    View rb = rbGroupManualAsuransi.findViewById(checkedId);
                    switch (rb.getId()) {
                        case R.id.rb_manual_yes:
                            manualPremi = true;
                            tilPremiManual.setVisibility(View.VISIBLE);
                            edtPremiAsuransiManual.setVisibility(View.VISIBLE);
                            if (edtPremiAsuransiManual.getText().toString().isEmpty()) {
                                edtPremiAsuransiManual.setText("0");
                                edtPremiAsuransi.setText(edtPremiAsuransiManual.getText().toString());
                                /*calculating();*/
                                prepareDataCalculating();
                            }
                            edtPremiAsuransiManual.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (!hasFocus) {
                                        if (edtPremiAsuransiManual.getText().toString().isEmpty()) {
                                            edtPremiAsuransiManual.setText("0");
                                        }
                                        edtPremiAsuransi.setText(edtPremiAsuransiManual.getText().toString());
                                        /*calculating();*/
                                        prepareDataCalculating();
                                    }
                                }
                            });
                            break;
                        case R.id.rb_manual_no:
                            manualPremi = false;
                            edtPremiAsuransiManual.getText().clear();
                            tilPremiManual.setVisibility(View.GONE);
                            edtPremiAsuransiManual.setVisibility(View.GONE);
                            rbManualNo.setChecked(true);
                            manualPremi = false;
                            /* calculating();*/
                            prepareDataCalculating();
                            break;
                    }
                }
            });
        } else {

        }

        rbGroupRecomendation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View rbRecomendation = rbGroupRecomendation.findViewById(checkedId);
                switch (rbRecomendation.getId()) {
                    case R.id.rb_recomendation_yes:
                        lnSpnRecomendation.setVisibility(View.GONE);
                        recomendationId = "0";
                        break;
                    case R.id.rb_recomendation_no:
                        lnSpnRecomendation.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void initSpinner() {
        spnPernikahanPribadi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkStatusPernikahan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Subscribe
    public void fromOcr(OcrBus ocrBus) {
        sendktp(ocrBus.getString());
    }

    private void showCamera(final int requestCode) {
        if (isHaveSignature)
            startActivityForResult(Util.getPickImageChooserIntent(this), requestCode);
        else
            Toast.makeText(this, R.string.warning_signature_first, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode + ", Intent" + data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 101) {
                Bundle extras = data.getExtras();
                Uri uriFile = (Uri) extras.get(CameraLandscapeActivity.URIFILE);
                File imageFile = new File(uriFile.getPath());
                imageCompressor(imageFile, requestCode);
            } else if (requestCode == 102) {
                Bundle extras = data.getExtras();
                Uri uriFile = (Uri) extras.get(CameraActivity.URIFILE);
                File imageFile = new File(uriFile.getPath());
                imageCompressor(imageFile, requestCode);
            } else {
                Bundle extras = data.getExtras();
                Uri uriFile = (Uri) extras.get(CameraOtherActivity.URIFILE);
                File imageFile = new File(uriFile.getPath());
                imageCompressor(imageFile, requestCode);
            }
        }
    }

    private void checkAutoCompleteTextView() {
        if (!actAutoAlamatPemohon.isSelectionFromPopUp())
            actAutoAlamatPemohon.getText().clear();
        if (!actAutoKtpAlamatPemohon.isSelectionFromPopUp())
            actAutoKtpAlamatPemohon.getText().clear();
        if (!actAutoAlamatKerabat.isSelectionFromPopUp())
            actAutoAlamatKerabat.getText().clear();
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
        if (!actSupplierAsset.isSelectionFromPopUp()) actSupplierAsset.getText().clear();
        if (!actMarketingSupplierAsset.isSelectionFromPopUp())
            actMarketingSupplierAsset.getText().clear();
        if (!actProductOfferingProduct.isSelectionFromPopUp())
            actProductOfferingProduct.getText().clear();
        if (!actPosProduct.isSelectionFromPopUp()) actPosProduct.getText().clear();

        for (int i = 0; i < llParentTotalAsset.getChildCount(); i++) {
            View viewLn = llParentTotalAsset.getChildAt(i);
            NiceAutoCompleteTextView actNamaBarang = (NiceAutoCompleteTextView) viewLn.findViewById(R.id.act_nama_barang_asset);
            if (!actNamaBarang.isSelectionFromPopUp()) actNamaBarang.getText().clear();
        }
    }

    private void createDialog(String bucketMessage, String agreement, String
            timeDelay, String amountOfFines) {
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

        if (form.equals("Edit")) {
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
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            handler.postDelayed(this, 0);
        }
    };

    private String getTipePembayaran(String tipe) {
        if (tipe.equals("AR")) {
            return "ARREAR";
        } else {
            return "ADVANCE";
        }
    }

    private boolean validasiKota() {
        boolean result = true;
        if (!strCityAlamatKerabat.contains("|")) {
            actAutoAlamatKerabat.setError("Harap pilih kota sesuai yang di sediakan");
            imgDropDownKerabat.setImageResource(android.R.drawable.ic_dialog_alert);
            result = false;
        }
        if (!strCityAlamatPekerjaan.contains("|")) {
            actAutoAlamatPekerjaan.setError("Harap pilih kota sesuai yang di sediakan");
            imgDropDownPekerjaan.setImageResource(android.R.drawable.ic_dialog_alert);
            result = false;
        }
        if (!strCityAlamatPemohon.contains("|")) {
            actAutoAlamatPemohon.setError("Harap pilih kota sesuai yang di sediakan");
            imgDropDownAlamat.setImageResource(android.R.drawable.ic_dialog_alert);
            result = false;
        }
        if (!strCityKtpAlamatPemohon.contains("|")) {
            actAutoKtpAlamatPemohon.setError("Harap pilih kota sesuai yang di sediakan");
            imgDropDownAlamat.setImageResource(android.R.drawable.ic_dialog_alert);
            result = false;
            cbxAlamatKtp.setChecked(false);
        }
        if (edtPenghasilanTetapPerusahaan.getText().toString().equals("0")) {
            edtPenghasilanTetapPerusahaan.setError("Penghasilan tetap tidak boleh 0");
            result = false;
            imgDropDownPekerjaan.setImageResource(android.R.drawable.ic_dialog_alert);
        }
        if (edtBiayaHidupPerusahaan.getText().toString().equals("0")) {
            edtBiayaHidupPerusahaan.setError("Biaya hidup tidak boleh 0");
            result = false;
            imgDropDownPekerjaan.setImageResource(android.R.drawable.ic_dialog_alert);
        }
        if (!edtHpKerabat.getText().toString().startsWith("08")) {
            result = false;
            imgDropDownKerabat.setImageResource(android.R.drawable.ic_dialog_alert);
            edtHpKerabat.setError("Format no hp tidak sesuai, harus diawali dengan 08");
        }
        if (spnPernikahanPribadi.getSelectedItem().toString().equalsIgnoreCase("Menikah")) {
            if (!edtNoHpPasangan.getText().toString().isEmpty()) {
                if (!edtNoHpPasangan.getText().toString().startsWith("08")) {
                    edtNoHpPasangan.setError("Format no hp tidak sesuai, harus diawali dengan 08");
                    imgDropDownPasangan.setImageResource(android.R.drawable.ic_dialog_alert);
                    result = false;
                }
            }
        }
        if (checkDataSpinner()) {
            result = false;
            imgDropDownPribadi.setImageResource(android.R.drawable.ic_dialog_alert);
        }

        if (rbPersetujuanPenawaranTrue.isChecked()) {
            statusInformasiPenawaran = "1";
        } else {
            statusInformasiPenawaran = "0";
        }
        if (rbPersetujuanTambahanTrue.isChecked()) {
            statusSmsInformasi = "1";
        } else {
            statusSmsInformasi = "0";
        }

        return result;
    }


    private void successAndFailedLoading() {
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void messageFailedApi(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showAlertYesNo(String status, String tvTitle, String tvDescription, String btnLeft, String btnRight) {
        DialogFragment dialogFragment = new YesNoDialog();
        Bundle args = new Bundle();
        args.putString("status", status);
        args.putString("tvTitle", tvTitle);
        args.putString("tvDescription", tvDescription);
        args.putString("btnLeft", btnLeft);
        args.putString("btnRight", btnRight);
        args.putString("form", form);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "YesNoDialog");
    }

    @Subscribe
    public void onEventSwitchCamera(SwitchCamera e) {
        if (e.isFront()) {
            Intent intent = new Intent(FormPengajuanActivity.this, CameraActivity.class);
            if (e.isOther) intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_OTHER);
            else intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
            intent.putExtra(CameraActivity.IS_OCR, false);
            intent.putExtra(CameraActivity.ISFRONT, true);
            startActivityForResult(intent, 102);
        } else {
            Intent intent = new Intent(FormPengajuanActivity.this, CameraActivity.class);
            if (e.isOther) intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_OTHER);
            else intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
            intent.putExtra(CameraActivity.IS_OCR, false);
            intent.putExtra(CameraActivity.ISFRONT, false);
            startActivityForResult(intent, 102);
        }
    }

    @Subscribe
    public void onEventSwitchCameraOther(SwitchCameraOther e) {
        if (e.isFront()) {
            Intent intent = new Intent(FormPengajuanActivity.this, CameraOtherActivity.class);
            intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
            intent.putExtra(CameraOtherActivity.IS_OCR, false);
            intent.putExtra(CameraOtherActivity.ISFRONT, true);
            startActivityForResult(intent, 103);
        } else {
            Intent intent = new Intent(FormPengajuanActivity.this, CameraOtherActivity.class);
            intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
            intent.putExtra(CameraOtherActivity.IS_OCR, false);
            intent.putExtra(CameraOtherActivity.ISFRONT, false);
            startActivityForResult(intent, 103);
        }
    }
}
