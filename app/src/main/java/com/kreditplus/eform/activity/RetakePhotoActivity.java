package com.kreditplus.eform.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.adapter.AttachmentDetailAdapter;
import com.kreditplus.eform.dagger.applicationscope.ApplicationModule_ProvideApiServiceFactory;
import com.kreditplus.eform.helper.ImageViewRule;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.bus.CropPhotoEvent;
import com.kreditplus.eform.model.bus.RefreshNotification;
import com.kreditplus.eform.model.bus.SwitchCamera;
import com.kreditplus.eform.model.bus.SwitchCameraOther;
import com.kreditplus.eform.model.response.objecthelper.Application;
import com.kreditplus.eform.model.response.objecthelper.Asset;
import com.kreditplus.eform.presenter.AttachmentPresenter;
import com.kreditplus.eform.presenter.PengajuanDetailPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.mvpview.AttachmentMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanDetailMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itsmagic.present.permissionhelper.util.PermissionHelper;

/**
 * Created by Iwan Nurdesa on 23/08/16.
 */
public class RetakePhotoActivity extends BaseActivity implements Validator.ValidationListener, AttachmentMvpView, RefreshTokenMvpView, PengajuanDetailMvpView {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    RefreshTokenPresenter mRefreshTokenPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_nama_lengkap)
    TextView txtNamaLengkap;
    @BindView(R.id.txt_no_handphone)
    TextView txtNoHandphone;
    @BindView(R.id.txt_alamat_tinggal)
    TextView txtAlamatTinggal;
    @BindView(R.id.tl_pengajuan_data_pribadi)
    TableLayout tlPengajuanDataPribadi;
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
    @BindView(R.id.cvAgunan)
    CardView cvAgunan;
    @BindView(R.id.txt_purchase_price)
    TextView txtPurchasePrice;
    @BindView(R.id.txt_discount)
    TextView txtDiscount;
    @BindView(R.id.txt_dp)
    TextView txtDp;
    @BindView(R.id.txt_ntf)
    TextView txtNtf;
    @BindView(R.id.txt_angsuran_pertama)
    TextView txtAngsuranPertama;
    @BindView(R.id.txt_biaya_administrasi)
    TextView txtBiayaAdministrasi;
    @BindView(R.id.txt_flate_rate)
    TextView txtFlateRate;
    @BindView(R.id.txt_angsuran)
    TextView txtAngsuran;
    @BindView(R.id.txt_in_refund)
    TextView txtInRefund;
    @BindView(R.id.tl_pengajuan_data_perhitungan)
    TableLayout tlPengajuanDataPerhitungan;
    @BindView(R.id.ln_asset_detail_product_container)
    LinearLayout lnAssetDetailProductContainer;
    @BindView(R.id.cvDetailAsset)
    CardView cvDetailAsset;
    @BindView(R.id.txt_keterangan_retake)
    TextView txtKeteranganRetake;
    @BindView(R.id.tl_keterangan_retake)
    TableLayout tlKeteranganRetake;
    @BindView(R.id.tvCamera1)
    TextView tvCamera1;

    @BindView(R.id.img_take_picture_1)
    ImageView imgTakePicture1;
    @BindView(R.id.img_delete_picture_1)
    ImageView imgDeletePicture1;
    @BindView(R.id.tvCamera2)
    TextView tvCamera2;
    @BindView(R.id.img_take_picture_2)
    ImageView imgTakePicture2;
    @BindView(R.id.img_delete_picture_2)
    ImageView imgDeletePicture2;
    @BindView(R.id.tvCamera3)
    TextView tvCamera3;
    @BindView(R.id.img_take_picture_3)
    ImageView imgTakePicture3;
    @BindView(R.id.img_delete_picture_3)
    ImageView imgDeletePicture3;
    @BindView(R.id.tvCamera4)
    TextView tvCamera4;
    @BindView(R.id.img_take_picture_4)
    ImageView imgTakePicture4;
    @BindView(R.id.img_delete_picture_4)
    ImageView imgDeletePicture4;
    @BindView(R.id.llContainerCamera5)
    LinearLayout llContainerCamera5;
    @BindView(R.id.tvCamera5)
    TextView tvCamera5;
    @BindView(R.id.img_take_picture_5)
    ImageView imgTakePicture5;
    @BindView(R.id.img_delete_picture_5)
    ImageView imgDeletePicture5;
    @BindView(R.id.tvCamera6)
    TextView tvCamera6;
    @BindView(R.id.img_take_picture_6)
    ImageView imgTakePicture6;
    @BindView(R.id.img_delete_picture_6)
    ImageView imgDeletePicture6;
    @BindView(R.id.tvCamera7)
    TextView tvCamera7;
    @BindView(R.id.img_take_picture_7)
    ImageView imgTakePicture7;
    @BindView(R.id.img_delete_picture_7)
    ImageView imgDeletePicture7;
    @BindView(R.id.tvCamera8)
    TextView tvCamera8;
    @BindView(R.id.img_take_picture_8)
    ImageView imgTakePicture8;
    @BindView(R.id.img_delete_picture_8)
    ImageView imgDeletePicture8;
    @BindView(R.id.tvCamera9)
    TextView tvCamera9;
    @BindView(R.id.img_take_picture_9)
    ImageView imgTakePicture9;
    @BindView(R.id.img_delete_picture_9)
    ImageView imgDeletePicture9;
    @BindView(R.id.tvCamera10)
    TextView tvCamera10;
    @BindView(R.id.img_take_picture_10)
    ImageView imgTakePicture10;
    @BindView(R.id.img_delete_picture_10)
    ImageView imgDeletePicture10;
    @BindView(R.id.tvCamera11)
    TextView tvCamera11;
    @BindView(R.id.img_take_picture_11)
    ImageView imgTakePicture11;
    @BindView(R.id.img_delete_picture_11)
    ImageView imgDeletePicture11;
    @BindView(R.id.tvCamera12)
    TextView tvCamera12;
    @BindView(R.id.img_take_picture_12)
    ImageView imgTakePicture12;
    @BindView(R.id.img_delete_picture_12)
    ImageView imgDeletePicture12;
    @BindView(R.id.tvCamera13)
    TextView tvCamera13;
    @BindView(R.id.img_take_picture_13)
    ImageView imgTakePicture13;
    @BindView(R.id.img_delete_picture_13)
    ImageView imgDeletePicture13;
    @BindView(R.id.tvCamera14)
    TextView tvCamera14;
    @BindView(R.id.img_take_picture_14)
    ImageView imgTakePicture14;
    @BindView(R.id.img_delete_picture_14)
    ImageView imgDeletePicture14;
    @BindView(R.id.tvCamera15)
    TextView tvCamera15;
    @BindView(R.id.img_take_picture_15)
    ImageView imgTakePicture15;
    @BindView(R.id.img_delete_picture_15)
    ImageView imgDeletePicture15;
    @BindView(R.id.tvCamera16)
    TextView tvCamera16;
    @BindView(R.id.img_take_picture_16)
    ImageView imgTakePicture16;
    @BindView(R.id.img_delete_picture_16)
    ImageView imgDeletePicture16;
    @BindView(R.id.tvCamera17)
    TextView tvCamera17;
    @BindView(R.id.img_take_picture_17)
    ImageView imgTakePicture17;
    @BindView(R.id.img_delete_picture_17)
    ImageView imgDeletePicture17;
    @BindView(R.id.tvCamera18)
    TextView tvCamera18;
    @BindView(R.id.img_take_picture_18)
    ImageView imgTakePicture18;
    @BindView(R.id.img_delete_picture_18)
    ImageView imgDeletePicture18;
    @BindView(R.id.tvCamera19)
    TextView tvCamera19;
    @BindView(R.id.img_take_picture_19)
    ImageView imgTakePicture19;
    @BindView(R.id.img_delete_picture_19)
    ImageView imgDeletePicture19;
    @BindView(R.id.tvCamera20)
    TextView tvCamera20;
    @BindView(R.id.img_take_picture_20)
    ImageView imgTakePicture20;
    @BindView(R.id.img_delete_picture_20)
    ImageView imgDeletePicture20;
    @BindView(R.id.tvCamera21)
    TextView tvCamera21;
    @BindView(R.id.img_take_picture_21)
    ImageView imgTakePicture21;
    @BindView(R.id.img_delete_picture_21)
    ImageView imgDeletePicture21;
    @BindView(R.id.tvCamera22)
    TextView tvCamera22;
    @BindView(R.id.img_take_picture_22)
    ImageView imgTakePicture22;
    @BindView(R.id.img_delete_picture_22)
    ImageView imgDeletePicture22;
    @BindView(R.id.tvCamera23)
    TextView tvCamera23;
    @BindView(R.id.img_take_picture_23)
    ImageView imgTakePicture23;
    @BindView(R.id.img_delete_picture_23)
    ImageView imgDeletePicture23;
    @BindView(R.id.tvCamera24)
    TextView tvCamera24;
    @BindView(R.id.img_take_picture_24)
    ImageView imgTakePicture24;
    @BindView(R.id.img_delete_picture_24)
    ImageView imgDeletePicture24;
    @BindView(R.id.tvCamera25)
    TextView tvCamera25;
    @BindView(R.id.img_take_picture_25)
    ImageView imgTakePicture25;
    @BindView(R.id.img_delete_picture_25)
    ImageView imgDeletePicture25;
    @BindView(R.id.llContainerPhotoNonElc)
    LinearLayout llContainerPhotoNonElc;
    @BindView(R.id.llContainerPhotoNonElcOriImg)
    LinearLayout llContainerPhotoNonElcOriImg;
    @BindView(R.id.ln_attachment)
    LinearLayout lnAttachment;
    @BindView(R.id.hsv)
    HorizontalScrollView hsv;
    @BindView(R.id.txt_attachment_error)
    TextView txtAttachmentError;
    @BindView(R.id.ln_data_pribadi)
    LinearLayout lnDataPribadi;
    @BindView(R.id.main_layout)
    ScrollView mainLayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.OritvCamera1)
    TextView OritvCamera1;
    @BindView(R.id.Oripb_image_1)
    ProgressBar OripbImage1;
    @BindView(R.id.Oriimg_camera_1)
    ImageView OriimgCamera1;
    @BindView(R.id.Oriimg_take_picture_1)
    ImageView OriimgTakePicture1;
    @BindView(R.id.Oriimg_delete_picture_1)
    ImageView OriimgDeletePicture1;
    @BindView(R.id.Oribtn_muat_ulang_1)
    Button OribtnMuatUlang1;
    @BindView(R.id.Oriln_take_foto_1)
    LinearLayout OrilnTakeFoto1;
    @BindView(R.id.OritvCamera2)
    TextView OritvCamera2;
    @BindView(R.id.Oripb_image_2)
    ProgressBar OripbImage2;
    @BindView(R.id.Oriimg_camera_2)
    ImageView OriimgCamera2;
    @BindView(R.id.Oriimg_take_picture_2)
    ImageView OriimgTakePicture2;
    @BindView(R.id.Oriimg_delete_picture_2)
    ImageView OriimgDeletePicture2;
    @BindView(R.id.Oribtn_muat_ulang_2)
    Button OribtnMuatUlang2;
    @BindView(R.id.Oriln_take_foto_2)
    LinearLayout OrilnTakeFoto2;
    @BindView(R.id.OritvCamera3)
    TextView OritvCamera3;
    @BindView(R.id.Oripb_image_3)
    ProgressBar OripbImage3;
    @BindView(R.id.Oriimg_camera_3)
    ImageView OriimgCamera3;
    @BindView(R.id.Oriimg_take_picture_3)
    ImageView OriimgTakePicture3;
    @BindView(R.id.Oriimg_delete_picture_3)
    ImageView OriimgDeletePicture3;
    @BindView(R.id.Oribtn_muat_ulang_3)
    Button OribtnMuatUlang3;
    @BindView(R.id.Oriln_take_foto_3)
    LinearLayout OrilnTakeFoto3;
    @BindView(R.id.OritvCamera4)
    TextView OritvCamera4;
    @BindView(R.id.Oripb_image_4)
    ProgressBar OripbImage4;
    @BindView(R.id.Oriimg_camera_4)
    ImageView OriimgCamera4;
    @BindView(R.id.Oriimg_take_picture_4)
    ImageView OriimgTakePicture4;
    @BindView(R.id.Oriimg_delete_picture_4)
    ImageView OriimgDeletePicture4;
    @BindView(R.id.Oribtn_muat_ulang_4)
    Button OribtnMuatUlang4;
    @BindView(R.id.Oriln_take_foto_4)
    LinearLayout OrilnTakeFoto4;
    @BindView(R.id.OritvCamera5)
    TextView OritvCamera5;
    @BindView(R.id.Oripb_image_5)
    ProgressBar OripbImage5;
    @BindView(R.id.Oriimg_camera_5)
    ImageView OriimgCamera5;
    @BindView(R.id.Oriimg_take_picture_5)
    ImageView OriimgTakePicture5;
    @BindView(R.id.Oriimg_delete_picture_5)
    ImageView OriimgDeletePicture5;
    @BindView(R.id.Oribtn_muat_ulang_5)
    Button OribtnMuatUlang5;
    @BindView(R.id.Oriln_take_foto_5)
    LinearLayout OrilnTakeFoto5;
    @BindView(R.id.OritvCamera6)
    TextView OritvCamera6;
    @BindView(R.id.Oripb_image_6)
    ProgressBar OripbImage6;
    @BindView(R.id.Oriimg_camera_6)
    ImageView OriimgCamera6;
    @BindView(R.id.Oriimg_take_picture_6)
    ImageView OriimgTakePicture6;
    @BindView(R.id.Oriimg_delete_picture_6)
    ImageView OriimgDeletePicture6;
    @BindView(R.id.Oribtn_muat_ulang_6)
    Button OribtnMuatUlang6;
    @BindView(R.id.Oriln_take_foto_6)
    LinearLayout OrilnTakeFoto6;
    @BindView(R.id.OritvCamera7)
    TextView OritvCamera7;
    @BindView(R.id.Oripb_image_7)
    ProgressBar OripbImage7;
    @BindView(R.id.Oriimg_camera_7)
    ImageView OriimgCamera7;
    @BindView(R.id.Oriimg_take_picture_7)
    ImageView OriimgTakePicture7;
    @BindView(R.id.Oriimg_delete_picture_7)
    ImageView OriimgDeletePicture7;
    @BindView(R.id.Oribtn_muat_ulang_7)
    Button OribtnMuatUlang7;
    @BindView(R.id.Oriln_take_foto_7)
    LinearLayout OrilnTakeFoto7;
    @BindView(R.id.OritvCamera8)
    TextView OritvCamera8;
    @BindView(R.id.Oripb_image_8)
    ProgressBar OripbImage8;
    @BindView(R.id.Oriimg_camera_8)
    ImageView OriimgCamera8;
    @BindView(R.id.Oriimg_take_picture_8)
    ImageView OriimgTakePicture8;
    @BindView(R.id.Oriimg_delete_picture_8)
    ImageView OriimgDeletePicture8;
    @BindView(R.id.Oribtn_muat_ulang_8)
    Button OribtnMuatUlang8;
    @BindView(R.id.Oriln_take_foto_8)
    LinearLayout OrilnTakeFoto8;
    @BindView(R.id.OritvCamera9)
    TextView OritvCamera9;
    @BindView(R.id.Oripb_image_9)
    ProgressBar OripbImage9;
    @BindView(R.id.Oriimg_camera_9)
    ImageView OriimgCamera9;
    @BindView(R.id.Oriimg_take_picture_9)
    ImageView OriimgTakePicture9;
    @BindView(R.id.Oriimg_delete_picture_9)
    ImageView OriimgDeletePicture9;
    @BindView(R.id.Oribtn_muat_ulang_9)
    Button OribtnMuatUlang9;
    @BindView(R.id.Oriln_take_foto_9)
    LinearLayout OrilnTakeFoto9;
    @BindView(R.id.OritvCamera10)
    TextView OritvCamera10;
    @BindView(R.id.Oripb_image_10)
    ProgressBar OripbImage10;
    @BindView(R.id.Oriimg_camera_10)
    ImageView OriimgCamera10;
    @BindView(R.id.Oriimg_take_picture_10)
    ImageView OriimgTakePicture10;
    @BindView(R.id.Oriimg_delete_picture_10)
    ImageView OriimgDeletePicture10;
    @BindView(R.id.Oribtn_muat_ulang_10)
    Button OribtnMuatUlang10;
    @BindView(R.id.Oriln_take_foto_10)
    LinearLayout OrilnTakeFoto10;
    @BindView(R.id.OritvCamera11)
    TextView OritvCamera11;
    @BindView(R.id.Oripb_image_11)
    ProgressBar OripbImage11;
    @BindView(R.id.Oriimg_camera_11)
    ImageView OriimgCamera11;
    @BindView(R.id.Oriimg_take_picture_11)
    ImageView OriimgTakePicture11;
    @BindView(R.id.Oriimg_delete_picture_11)
    ImageView OriimgDeletePicture11;
    @BindView(R.id.Oribtn_muat_ulang_11)
    Button OribtnMuatUlang11;
    @BindView(R.id.Oriln_take_foto_11)
    LinearLayout OrilnTakeFoto11;
    @BindView(R.id.OritvCamera12)
    TextView OritvCamera12;
    @BindView(R.id.Oripb_image_12)
    ProgressBar OripbImage12;
    @BindView(R.id.Oriimg_camera_12)
    ImageView OriimgCamera12;
    @BindView(R.id.Oriimg_take_picture_12)
    ImageView OriimgTakePicture12;
    @BindView(R.id.Oriimg_delete_picture_12)
    ImageView OriimgDeletePicture12;
    @BindView(R.id.Oribtn_muat_ulang_12)
    Button OribtnMuatUlang12;
    @BindView(R.id.Oriln_take_foto_12)
    LinearLayout OrilnTakeFoto12;
    @BindView(R.id.OritvCamera13)
    TextView OritvCamera13;
    @BindView(R.id.Oripb_image_13)
    ProgressBar OripbImage13;
    @BindView(R.id.Oriimg_camera_13)
    ImageView OriimgCamera13;
    @BindView(R.id.Oriimg_take_picture_13)
    ImageView OriimgTakePicture13;
    @BindView(R.id.Oriimg_delete_picture_13)
    ImageView OriimgDeletePicture13;
    @BindView(R.id.Oribtn_muat_ulang_13)
    Button OribtnMuatUlang13;
    @BindView(R.id.Oriln_take_foto_13)
    LinearLayout OrilnTakeFoto13;
    @BindView(R.id.OritvCamera14)
    TextView OritvCamera14;
    @BindView(R.id.Oripb_image_14)
    ProgressBar OripbImage14;
    @BindView(R.id.Oriimg_camera_14)
    ImageView OriimgCamera14;
    @BindView(R.id.Oriimg_take_picture_14)
    ImageView OriimgTakePicture14;
    @BindView(R.id.Oriimg_delete_picture_14)
    ImageView OriimgDeletePicture14;
    @BindView(R.id.Oribtn_muat_ulang_14)
    Button OribtnMuatUlang14;
    @BindView(R.id.Oriln_take_foto_14)
    LinearLayout OrilnTakeFoto14;
    @BindView(R.id.OritvCamera15)
    TextView OritvCamera15;
    @BindView(R.id.Oripb_image_15)
    ProgressBar OripbImage15;
    @BindView(R.id.Oriimg_camera_15)
    ImageView OriimgCamera15;
    @BindView(R.id.Oriimg_take_picture_15)
    ImageView OriimgTakePicture15;
    @BindView(R.id.Oriimg_delete_picture_15)
    ImageView OriimgDeletePicture15;
    @BindView(R.id.Oribtn_muat_ulang_15)
    Button OribtnMuatUlang15;
    @BindView(R.id.Oriln_take_foto_15)
    LinearLayout OrilnTakeFoto15;
    @BindView(R.id.OritvCamera16)
    TextView OritvCamera16;
    @BindView(R.id.Oripb_image_16)
    ProgressBar OripbImage16;
    @BindView(R.id.Oriimg_camera_16)
    ImageView OriimgCamera16;
    @BindView(R.id.Oriimg_take_picture_16)
    ImageView OriimgTakePicture16;
    @BindView(R.id.Oriimg_delete_picture_16)
    ImageView OriimgDeletePicture16;
    @BindView(R.id.Oribtn_muat_ulang_16)
    Button OribtnMuatUlang16;
    @BindView(R.id.Oriln_take_foto_16)
    LinearLayout OrilnTakeFoto16;
    @BindView(R.id.OritvCamera17)
    TextView OritvCamera17;
    @BindView(R.id.Oripb_image_17)
    ProgressBar OripbImage17;
    @BindView(R.id.Oriimg_camera_17)
    ImageView OriimgCamera17;
    @BindView(R.id.Oriimg_take_picture_17)
    ImageView OriimgTakePicture17;
    @BindView(R.id.Oriimg_delete_picture_17)
    ImageView OriimgDeletePicture17;
    @BindView(R.id.Oribtn_muat_ulang_17)
    Button OribtnMuatUlang17;
    @BindView(R.id.Oriln_take_foto_17)
    LinearLayout OrilnTakeFoto17;
    @BindView(R.id.OritvCamera18)
    TextView OritvCamera18;
    @BindView(R.id.Oripb_image_18)
    ProgressBar OripbImage18;
    @BindView(R.id.Oriimg_camera_18)
    ImageView OriimgCamera18;
    @BindView(R.id.Oriimg_take_picture_18)
    ImageView OriimgTakePicture18;
    @BindView(R.id.Oriimg_delete_picture_18)
    ImageView OriimgDeletePicture18;
    @BindView(R.id.Oribtn_muat_ulang_18)
    Button OribtnMuatUlang18;
    @BindView(R.id.Oriln_take_foto_18)
    LinearLayout OrilnTakeFoto18;
    @BindView(R.id.OritvCamera19)
    TextView OritvCamera19;
    @BindView(R.id.Oripb_image_19)
    ProgressBar OripbImage19;
    @BindView(R.id.Oriimg_camera_19)
    ImageView OriimgCamera19;
    @BindView(R.id.Oriimg_take_picture_19)
    ImageView OriimgTakePicture19;
    @BindView(R.id.Oriimg_delete_picture_19)
    ImageView OriimgDeletePicture19;
    @BindView(R.id.Oribtn_muat_ulang_19)
    Button OribtnMuatUlang19;
    @BindView(R.id.Oriln_take_foto_19)
    LinearLayout OrilnTakeFoto19;
    @BindView(R.id.OritvCamera20)
    TextView OritvCamera20;
    @BindView(R.id.Oripb_image_20)
    ProgressBar OripbImage20;
    @BindView(R.id.Oriimg_camera_20)
    ImageView OriimgCamera20;
    @BindView(R.id.Oriimg_take_picture_20)
    ImageView OriimgTakePicture20;
    @BindView(R.id.Oriimg_delete_picture_20)
    ImageView OriimgDeletePicture20;
    @BindView(R.id.Oribtn_muat_ulang_20)
    Button OribtnMuatUlang20;
    @BindView(R.id.Oriln_take_foto_20)
    LinearLayout OrilnTakeFoto20;
    @BindView(R.id.OritvCamera21)
    TextView OritvCamera21;
    @BindView(R.id.Oripb_image_21)
    ProgressBar OripbImage21;
    @BindView(R.id.Oriimg_camera_21)
    ImageView OriimgCamera21;
    @BindView(R.id.Oriimg_take_picture_21)
    ImageView OriimgTakePicture21;
    @BindView(R.id.Oriimg_delete_picture_21)
    ImageView OriimgDeletePicture21;
    @BindView(R.id.Oribtn_muat_ulang_21)
    Button OribtnMuatUlang21;
    @BindView(R.id.Oriln_take_foto_21)
    LinearLayout OrilnTakeFoto21;
    @BindView(R.id.OritvCamera22)
    TextView OritvCamera22;
    @BindView(R.id.Oripb_image_22)
    ProgressBar OripbImage22;
    @BindView(R.id.Oriimg_camera_22)
    ImageView OriimgCamera22;
    @BindView(R.id.Oriimg_take_picture_22)
    ImageView OriimgTakePicture22;
    @BindView(R.id.Oriimg_delete_picture_22)
    ImageView OriimgDeletePicture22;
    @BindView(R.id.Oribtn_muat_ulang_22)
    Button OribtnMuatUlang22;
    @BindView(R.id.Oriln_take_foto_22)
    LinearLayout OrilnTakeFoto22;
    @BindView(R.id.OritvCamera23)
    TextView OritvCamera23;
    @BindView(R.id.Oripb_image_23)
    ProgressBar OripbImage23;
    @BindView(R.id.Oriimg_camera_23)
    ImageView OriimgCamera23;
    @BindView(R.id.Oriimg_take_picture_23)
    ImageView OriimgTakePicture23;
    @BindView(R.id.Oriimg_delete_picture_23)
    ImageView OriimgDeletePicture23;
    @BindView(R.id.Oribtn_muat_ulang_23)
    Button OribtnMuatUlang23;
    @BindView(R.id.Oriln_take_foto_23)
    LinearLayout OrilnTakeFoto23;
    @BindView(R.id.OritvCamera24)
    TextView OritvCamera24;
    @BindView(R.id.Oripb_image_24)
    ProgressBar OripbImage24;
    @BindView(R.id.Oriimg_camera_24)
    ImageView OriimgCamera24;
    @BindView(R.id.Oriimg_take_picture_24)
    ImageView OriimgTakePicture24;
    @BindView(R.id.Oriimg_delete_picture_24)
    ImageView OriimgDeletePicture24;
    @BindView(R.id.Oribtn_muat_ulang_24)
    Button OribtnMuatUlang24;
    @BindView(R.id.Oriln_take_foto_24)
    LinearLayout OrilnTakeFoto24;
    @BindView(R.id.OritvCamera25)
    TextView OritvCamera25;
    @BindView(R.id.Oripb_image_25)
    ProgressBar OripbImage25;
    @BindView(R.id.Oriimg_camera_25)
    ImageView OriimgCamera25;
    @BindView(R.id.Oriimg_take_picture_25)
    ImageView OriimgTakePicture25;
    @BindView(R.id.Oriimg_delete_picture_25)
    ImageView OriimgDeletePicture25;
    @BindView(R.id.Oribtn_muat_ulang_25)
    Button OribtnMuatUlang25;
    @BindView(R.id.Oriln_take_foto_25)
    LinearLayout OrilnTakeFoto25;
    @BindView(R.id.Oriln_attachment)
    LinearLayout OrilnAttachment;
    @BindView(R.id.Orihsv)
    HorizontalScrollView Orihsv;
    @BindView(R.id.tvExistingPhoto)
    TextView tvExistingPhoto;

    @BindViews({R.id.Oriimg_take_picture_1,
            R.id.Oriimg_take_picture_2,
            R.id.Oriimg_take_picture_3,
            R.id.Oriimg_take_picture_4,
            R.id.Oriimg_take_picture_5,
            R.id.Oriimg_take_picture_6,
            R.id.Oriimg_take_picture_7,
            R.id.Oriimg_take_picture_8,
            R.id.Oriimg_take_picture_9,
            R.id.Oriimg_take_picture_10,
            R.id.Oriimg_take_picture_11,
            R.id.Oriimg_take_picture_12,
            R.id.Oriimg_take_picture_13,
            R.id.Oriimg_take_picture_14,
            R.id.Oriimg_take_picture_15,
            R.id.Oriimg_take_picture_16,
            R.id.Oriimg_take_picture_17,
            R.id.Oriimg_take_picture_18,
            R.id.Oriimg_take_picture_19,
            R.id.Oriimg_take_picture_20,
            R.id.Oriimg_take_picture_21,
            R.id.Oriimg_take_picture_22,
            R.id.Oriimg_take_picture_23,
            R.id.Oriimg_take_picture_24,
            R.id.Oriimg_take_picture_25})
    List<View> viewTakeImages;

    @BindViews({R.id.Oriimg_take_picture_1,
            R.id.Oriimg_take_picture_2,
            R.id.Oriimg_take_picture_3,
            R.id.Oriimg_take_picture_4,
            R.id.Oriimg_take_picture_5,
            R.id.Oriimg_take_picture_6,
            R.id.Oriimg_take_picture_7,
            R.id.Oriimg_take_picture_8,
            R.id.Oriimg_take_picture_9,
            R.id.Oriimg_take_picture_10,
            R.id.Oriimg_take_picture_11})
    List<View> viewTakeImagesELC;

    @BindViews({
            R.id.Oriimg_take_picture_1,
            R.id.Oriimg_take_picture_2,
            R.id.Oriimg_take_picture_3,
            R.id.Oriimg_take_picture_4,
            R.id.Oriimg_take_picture_5,
            R.id.Oriimg_take_picture_6,
            R.id.Oriimg_take_picture_7,
            R.id.Oriimg_take_picture_8,
            R.id.Oriimg_take_picture_9,
            R.id.Oriimg_take_picture_10,
            R.id.Oriimg_take_picture_11,
            R.id.Oriimg_take_picture_12,
            R.id.Oriimg_take_picture_13,
            R.id.Oriimg_take_picture_14,
            R.id.Oriimg_take_picture_15,
            R.id.Oriimg_take_picture_16,
            R.id.Oriimg_take_picture_17,
            R.id.Oriimg_take_picture_18,
            R.id.Oriimg_take_picture_19,
            R.id.Oriimg_take_picture_20,
            R.id.Oriimg_take_picture_21,
            R.id.Oriimg_take_picture_22,
            R.id.Oriimg_take_picture_23,
            R.id.Oriimg_take_picture_24,
            R.id.Oriimg_take_picture_25})
    List<View> viewTakeImagesVehicleFirst;

    @BindViews({R.id.Oriimg_delete_picture_1,
            R.id.Oriimg_delete_picture_2,
            R.id.Oriimg_delete_picture_3,
            R.id.Oriimg_delete_picture_4,
            R.id.Oriimg_delete_picture_5,
            R.id.Oriimg_delete_picture_6,
            R.id.Oriimg_delete_picture_7,
            R.id.Oriimg_delete_picture_8,
            R.id.Oriimg_delete_picture_9,
            R.id.Oriimg_delete_picture_10,
            R.id.Oriimg_delete_picture_11,
            R.id.Oriimg_delete_picture_12,
            R.id.Oriimg_delete_picture_13,
            R.id.Oriimg_delete_picture_14,
            R.id.Oriimg_delete_picture_15,
            R.id.Oriimg_delete_picture_16,
            R.id.Oriimg_delete_picture_17,
            R.id.Oriimg_delete_picture_18,
            R.id.Oriimg_delete_picture_19,
            R.id.Oriimg_delete_picture_20,
            R.id.Oriimg_delete_picture_21,
            R.id.Oriimg_delete_picture_22,
            R.id.Oriimg_delete_picture_23,
            R.id.Oriimg_delete_picture_24,
            R.id.Oriimg_delete_picture_25})
    List<View> viewDeleteImages;

    @BindViews({R.id.Oriimg_delete_picture_1,
            R.id.Oriimg_delete_picture_2,
            R.id.Oriimg_delete_picture_3,
            R.id.Oriimg_delete_picture_4,
            R.id.Oriimg_delete_picture_5,
            R.id.Oriimg_delete_picture_6,
            R.id.Oriimg_delete_picture_7,
            R.id.Oriimg_delete_picture_8,
            R.id.Oriimg_delete_picture_9,
            R.id.Oriimg_delete_picture_10,
            R.id.Oriimg_delete_picture_11})
    List<View> viewDeleteImagesELC;

    @BindViews({R.id.Oripb_image_1,
            R.id.Oripb_image_2,
            R.id.Oripb_image_3,
            R.id.Oripb_image_4,
            R.id.Oripb_image_5,
            R.id.Oripb_image_6,
            R.id.Oripb_image_7,
            R.id.Oripb_image_8,
            R.id.Oripb_image_9,
            R.id.Oripb_image_10,
            R.id.Oripb_image_11,
            R.id.Oripb_image_12,
            R.id.Oripb_image_13,
            R.id.Oripb_image_14,
            R.id.Oripb_image_15,
            R.id.Oripb_image_16,
            R.id.Oripb_image_17,
            R.id.Oripb_image_18,
            R.id.Oripb_image_19,
            R.id.Oripb_image_20,
            R.id.Oripb_image_21,
            R.id.Oripb_image_22,
            R.id.Oripb_image_23,
            R.id.Oripb_image_24,
            R.id.Oripb_image_25})
    List<View> viewPbImages;

    @BindViews({R.id.Oripb_image_1,
            R.id.Oripb_image_2,
            R.id.Oripb_image_3,
            R.id.Oripb_image_4,
            R.id.Oripb_image_5,
            R.id.Oripb_image_6,
            R.id.Oripb_image_7,
            R.id.Oripb_image_8,
            R.id.Oripb_image_9,
            R.id.Oripb_image_10,
            R.id.Oripb_image_11})
    List<View> viewPbImagesELC;

    @BindViews({R.id.Oriimg_camera_1,
            R.id.Oriimg_camera_2,
            R.id.Oriimg_camera_3,
            R.id.Oriimg_camera_4,
            R.id.Oriimg_camera_5,
            R.id.Oriimg_camera_6,
            R.id.Oriimg_camera_7,
            R.id.Oriimg_camera_8,
            R.id.Oriimg_camera_9,
            R.id.Oriimg_camera_10,
            R.id.Oriimg_camera_11,
            R.id.Oriimg_camera_12,
            R.id.Oriimg_camera_13,
            R.id.Oriimg_camera_14,
            R.id.Oriimg_camera_15,
            R.id.Oriimg_camera_16,
            R.id.Oriimg_camera_17,
            R.id.Oriimg_camera_18,
            R.id.Oriimg_camera_19,
            R.id.Oriimg_camera_20,
            R.id.Oriimg_camera_21,
            R.id.Oriimg_camera_22,
            R.id.Oriimg_camera_23,
            R.id.Oriimg_camera_24,
            R.id.Oriimg_camera_25})
    List<View> viewCameras;
    @BindViews({R.id.Oriimg_camera_1,
            R.id.Oriimg_camera_2,
            R.id.Oriimg_camera_3,
            R.id.Oriimg_camera_4,
            R.id.Oriimg_camera_5,
            R.id.Oriimg_camera_6,
            R.id.Oriimg_camera_7,
            R.id.Oriimg_camera_8,
            R.id.Oriimg_camera_9,
            R.id.Oriimg_camera_10,
            R.id.Oriimg_camera_11})
    List<View> viewCamerasELC;

    @BindViews({
            R.id.Oribtn_muat_ulang_1,
            R.id.Oribtn_muat_ulang_2,
            R.id.Oribtn_muat_ulang_3,
            R.id.Oribtn_muat_ulang_4,
            R.id.Oribtn_muat_ulang_5,
            R.id.Oribtn_muat_ulang_6,
            R.id.Oribtn_muat_ulang_7,
            R.id.Oribtn_muat_ulang_8,
            R.id.Oribtn_muat_ulang_9,
            R.id.Oribtn_muat_ulang_10,
            R.id.Oribtn_muat_ulang_11,
            R.id.Oribtn_muat_ulang_12,
            R.id.Oribtn_muat_ulang_13,
            R.id.Oribtn_muat_ulang_14,
            R.id.Oribtn_muat_ulang_15,
            R.id.Oribtn_muat_ulang_16,
            R.id.Oribtn_muat_ulang_17,
            R.id.Oribtn_muat_ulang_18,
            R.id.Oribtn_muat_ulang_19,
            R.id.Oribtn_muat_ulang_20,
            R.id.Oribtn_muat_ulang_21,
            R.id.Oribtn_muat_ulang_22,
            R.id.Oribtn_muat_ulang_23,
            R.id.Oribtn_muat_ulang_24,
            R.id.Oribtn_muat_ulang_25})
    List<View> viewRefreshImages;

    @BindViews({
            R.id.Oribtn_muat_ulang_1,
            R.id.Oribtn_muat_ulang_2,
            R.id.Oribtn_muat_ulang_3,
            R.id.Oribtn_muat_ulang_4,
            R.id.Oribtn_muat_ulang_5,
            R.id.Oribtn_muat_ulang_6,
            R.id.Oribtn_muat_ulang_7,
            R.id.Oribtn_muat_ulang_8,
            R.id.Oribtn_muat_ulang_9,
            R.id.Oribtn_muat_ulang_10})
    List<View> viewRefreshImagesELC;

    List<File> listRetakeAttachment = new ArrayList<>();
    private ProgressDialog progressDialog;
    private AttachmentPresenter attachmentPresenter;
    private String token;
    private Validator validator;
    private ImageViewRule imageViewRule;
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

    final String[] items = {"Camera", "From Gallery"};
    private HashMap<Integer, File> mHashMapAttachmentFiles = new HashMap<>();

    private PermissionHelper mPermissionHelper;
    private PengajuanDetailPresenter mPengajuanDetailPresenter;
    private String strAppId;
    private boolean isUncompleted;
    private String cro;
    private int numberOfImages;
    private int countImage;
    private boolean isImageError;
    private ArrayList<String> tmpAttachments;
    private String offeringType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        cro = sharedPreferences.getString(getResources().getString(R.string.sharedpref_cro), "");

        attachmentPresenter = new AttachmentPresenter();
        mPengajuanDetailPresenter = new PengajuanDetailPresenter();
        attachmentPresenter.attachView(this);
        mPengajuanDetailPresenter.attachView(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu . . .");
        progressDialog.setTitle("");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        strAppId = getIntent().getStringExtra("app_id");
        isUncompleted = getIntent().getBooleanExtra("is_uncompleted", false);
        mPengajuanDetailPresenter.loadPengajuanDetail(token, strAppId);

        if (isUncompleted) {
            getSupportActionBar().setTitle("Pengajuan Uncompleted");
            OrilnAttachment.setVisibility(View.GONE);
            tvExistingPhoto.setVisibility(View.GONE);
        }

        if(isUncompleted){
            initValidatorImage();
        }else{
            if (cro.equalsIgnoreCase("cro")) {
                offeringType = "ELC";
            } else {
                offeringType = "MTR";
            }
        }
    }

    private void initValidatorImage() {
        validator = new Validator(this);
        validator.setValidationListener(this);
        imageViewRule = new ImageViewRule();
        if (cro.equalsIgnoreCase("cro")) {
            offeringType = "ELC";
            validator.put(imgTakePicture1, imageViewRule);
            validator.put(imgTakePicture2, imageViewRule);
            validator.put(imgTakePicture3, imageViewRule);
        } else {
            offeringType = "MTR";
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
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_retake_photo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        attachmentPresenter.detachView();
        mPengajuanDetailPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = Util.getPickImageResultUri(data, getContext());
            Intent intent = new Intent(this, CropPhotoActivity.class);
            intent.putExtra("requestCode", requestCode);
            intent.setData(imageUri);
            startActivity(intent);
        }*/

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().post(new RefreshNotification());
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        txtAttachmentError.setVisibility(View.GONE);
        if(isUncompleted){
            validator.validate();
        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Apakah Anda Yakin ?");
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
                    listRetakeAttachment.addAll(mHashMapAttachmentFiles.values());
                    if (mHashMapAttachmentFiles.isEmpty()) {
                        txtAttachmentError.setVisibility(View.VISIBLE);
                    } else {
                        txtAttachmentError.setVisibility(View.GONE);
                        attachmentPresenter.retakePhoto(token, strAppId, mHashMapAttachmentFiles, offeringType, isUncompleted);
                    }
                }
            });
            alert.show();
        }
    }

    @OnClick({
            R.id.Oribtn_muat_ulang_1,
            R.id.Oribtn_muat_ulang_2,
            R.id.Oribtn_muat_ulang_3,
            R.id.Oribtn_muat_ulang_4,
            R.id.Oribtn_muat_ulang_5,
            R.id.Oribtn_muat_ulang_6,
            R.id.Oribtn_muat_ulang_7,
            R.id.Oribtn_muat_ulang_8,
            R.id.Oribtn_muat_ulang_9,
            R.id.Oribtn_muat_ulang_10,
            R.id.Oribtn_muat_ulang_11,
            R.id.Oribtn_muat_ulang_12,
            R.id.Oribtn_muat_ulang_13,
            R.id.Oribtn_muat_ulang_14,
            R.id.Oribtn_muat_ulang_15,
            R.id.Oribtn_muat_ulang_16,
            R.id.Oribtn_muat_ulang_17,
            R.id.Oribtn_muat_ulang_18,
            R.id.Oribtn_muat_ulang_19,
            R.id.Oribtn_muat_ulang_20,
            R.id.Oribtn_muat_ulang_21,
            R.id.Oribtn_muat_ulang_22,
            R.id.Oribtn_muat_ulang_23,
            R.id.Oribtn_muat_ulang_24,
            R.id.Oribtn_muat_ulang_25})
    public void onClickRefreshAttachment(View view) {
        switch (view.getId()) {
            case R.id.Oribtn_muat_ulang_1:
                refreshAttachment(0);
                break;
            case R.id.Oribtn_muat_ulang_2:
                refreshAttachment(1);
                break;
            case R.id.Oribtn_muat_ulang_3:
                refreshAttachment(2);
                break;
            case R.id.Oribtn_muat_ulang_4:
                refreshAttachment(3);
                break;
            case R.id.Oribtn_muat_ulang_5:
                refreshAttachment(4);
                break;
            case R.id.Oribtn_muat_ulang_6:
                refreshAttachment(5);
                break;
            case R.id.Oribtn_muat_ulang_7:
                refreshAttachment(6);
                break;
            case R.id.Oribtn_muat_ulang_8:
                refreshAttachment(7);
                break;
            case R.id.Oribtn_muat_ulang_9:
                refreshAttachment(8);
                break;
            case R.id.Oribtn_muat_ulang_10:
                refreshAttachment(9);
                break;
            case R.id.Oribtn_muat_ulang_11:
                refreshAttachment(10);
                break;
            case R.id.Oribtn_muat_ulang_12:
                refreshAttachment(11);
                break;
            case R.id.Oribtn_muat_ulang_13:
                refreshAttachment(12);
                break;
            case R.id.Oribtn_muat_ulang_14:
                refreshAttachment(13);
                break;
            case R.id.Oribtn_muat_ulang_15:
                refreshAttachment(14);
                break;
            case R.id.Oribtn_muat_ulang_16:
                refreshAttachment(15);
                break;
            case R.id.Oribtn_muat_ulang_17:
                refreshAttachment(16);
                break;
            case R.id.Oribtn_muat_ulang_18:
                refreshAttachment(17);
                break;
            case R.id.Oribtn_muat_ulang_19:
                refreshAttachment(18);
                break;
            case R.id.Oribtn_muat_ulang_20:
                refreshAttachment(19);
                break;
            case R.id.Oribtn_muat_ulang_21:
                refreshAttachment(20);
                break;
            case R.id.Oribtn_muat_ulang_22:
                refreshAttachment(21);
                break;
            case R.id.Oribtn_muat_ulang_23:
                refreshAttachment(22);
                break;
            case R.id.Oribtn_muat_ulang_24:
                refreshAttachment(23);
                break;
            case R.id.Oribtn_muat_ulang_25:
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

    @Override
    public void onPreSubmitAttachment() {
        progressDialog.show();
    }

    @Override
    public void onSuccessSubmitAttachment() {
        progressDialog.dismiss();
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new RefreshNotification());
        finish();
    }

    @Override
    public void onFailedSubmitAttachment(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick({
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
            R.id.img_take_picture_25})
    public void onClickTakePhoto(View view) {
        switch (view.getId()) {
            case R.id.img_take_picture_1:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_1);
                break;
            case R.id.img_take_picture_2:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_2);
                break;
            case R.id.img_take_picture_3:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_3);
                break;
            case R.id.img_take_picture_4:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_4);
                break;
            case R.id.img_take_picture_5:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_5);
                break;
            case R.id.img_take_picture_6:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_6);
                break;
            case R.id.img_take_picture_7:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_7);
                break;
            case R.id.img_take_picture_8:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_8);
                break;
            case R.id.img_take_picture_9:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_9);
                break;
            case R.id.img_take_picture_10:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_10);
                break;
            case R.id.img_take_picture_11:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_11);
                break;
            case R.id.img_take_picture_12:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_12);
                break;
            case R.id.img_take_picture_13:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_13);
                break;
            case R.id.img_take_picture_14:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_14);
                break;
            case R.id.img_take_picture_15:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_15);
                break;
            case R.id.img_take_picture_16:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_16);
                break;
            case R.id.img_take_picture_17:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_17);
                break;
            case R.id.img_take_picture_18:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_18);
                break;
            case R.id.img_take_picture_19:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_19);
                break;
            case R.id.img_take_picture_20:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_20);
                break;
            case R.id.img_take_picture_21:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_21);
                break;
            case R.id.img_take_picture_22:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_22);
                break;
            case R.id.img_take_picture_23:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_23);
                break;
            case R.id.img_take_picture_24:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_24);
                break;
            case R.id.img_take_picture_25:
                permissionCameraStorage(TAKE_CAMERA_PENGAJUAN_25);
                break;
        }
    }

    @OnClick({
            R.id.img_delete_picture_1,
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
            R.id.img_delete_picture_25
    })
    public void onClickDeletePhoto(View view) {
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
                imgTakePicture1.setImageResource(0);
                imgDeletePicture1.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(10);
                break;
            case R.id.img_delete_picture_12:
                imgTakePicture2.setImageResource(0);
                imgDeletePicture2.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(11);
                break;
            case R.id.img_delete_picture_13:
                imgTakePicture3.setImageResource(0);
                imgDeletePicture3.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(12);
                break;
            case R.id.img_delete_picture_14:
                imgTakePicture4.setImageResource(0);
                imgDeletePicture4.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(13);
                break;
            case R.id.img_delete_picture_15:
                imgTakePicture5.setImageResource(0);
                imgDeletePicture5.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(14);
                break;
            case R.id.img_delete_picture_16:
                imgTakePicture6.setImageResource(0);
                imgDeletePicture6.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(15);
                break;
            case R.id.img_delete_picture_17:
                imgTakePicture7.setImageResource(0);
                imgDeletePicture7.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(16);
                break;
            case R.id.img_delete_picture_18:
                imgTakePicture8.setImageResource(0);
                imgDeletePicture8.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(17);
                break;
            case R.id.img_delete_picture_19:
                imgTakePicture9.setImageResource(0);
                imgDeletePicture9.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(18);
                break;
            case R.id.img_delete_picture_20:
                imgTakePicture10.setImageResource(0);
                imgDeletePicture10.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(19);
                break;
            case R.id.img_delete_picture_21:
                imgTakePicture1.setImageResource(0);
                imgDeletePicture1.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(20);
                break;
            case R.id.img_delete_picture_22:
                imgTakePicture2.setImageResource(0);
                imgDeletePicture2.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(21);
                break;
            case R.id.img_delete_picture_23:
                imgTakePicture3.setImageResource(0);
                imgDeletePicture3.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(22);
                break;
            case R.id.img_delete_picture_24:
                imgTakePicture4.setImageResource(0);
                imgDeletePicture4.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(23);
                break;
            case R.id.img_delete_picture_25:
                imgTakePicture5.setImageResource(0);
                imgDeletePicture5.setVisibility(View.GONE);
                mHashMapAttachmentFiles.remove(24);
                break;
        }
    }

    private void showPictureChooser(final int requestCode) {
        startActivityForResult(Util.getPickImageChooserIntent(getContext()), requestCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCropPhotoEvent(CropPhotoEvent e) {
        Uri tempUri = Util.getImageUri(getContext(), e.getBitmap());
        File fileUri = new File(Util.getRealPathFromURI(this, tempUri));
        long fileLength = fileUri.length() / 1024;
        switch (e.getTipeImg()) {
            case TAKE_CAMERA_PENGAJUAN_1:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture1);
                imgDeletePicture1.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(0, fileUri);
                Log.d("size_img_1", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_2:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture2);
                imgDeletePicture2.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(1, fileUri);
                Log.d("size_img_2", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_3:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture3);
                imgDeletePicture3.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(2, fileUri);
                Log.d("size_img_3", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_4:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture4);
                imgDeletePicture4.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(3, fileUri);
                Log.d("size_img_4", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_5:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture5);
                imgDeletePicture5.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(4, fileUri);
                Log.d("size_img_5", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_6:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture6);
                imgDeletePicture6.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(5, fileUri);
                Log.d("size_img_6", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_7:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture7);
                imgDeletePicture7.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(6, fileUri);
                Log.d("TAKE_CAMERA_PENGAJUAN_7", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_8:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture8);
                imgDeletePicture8.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(7, fileUri);
                Log.d("size_img_8", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_9:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture9);
                imgDeletePicture9.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(8, fileUri);
                Log.d("size_img_9", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_10:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture10);
                imgDeletePicture10.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(9, fileUri);
                Log.d("size_img_10", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_11:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture1);
                imgDeletePicture1.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(10, fileUri);
                Log.d("size_img_1", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_12:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture2);
                imgDeletePicture2.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(11, fileUri);
                Log.d("size_img_2", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_13:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture3);
                imgDeletePicture3.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(12, fileUri);
                Log.d("size_img_3", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_14:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture4);
                imgDeletePicture4.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(13, fileUri);
                Log.d("size_img_4", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_15:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture5);
                imgDeletePicture5.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(14, fileUri);
                Log.d("size_img_5", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_16:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture6);
                imgDeletePicture6.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(15, fileUri);
                Log.d("size_img_6", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_17:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture7);
                imgDeletePicture7.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(16, fileUri);
                Log.d("TAKE_CAMERA_PENGAJUAN_7", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_18:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture8);
                imgDeletePicture8.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(17, fileUri);
                Log.d("size_img_8", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_19:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture9);
                imgDeletePicture9.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(18, fileUri);
                Log.d("size_img_9", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_20:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture10);
                imgDeletePicture10.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(19, fileUri);
                Log.d("size_img_10", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_21:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture1);
                imgDeletePicture1.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(20, fileUri);
                Log.d("size_img_1", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_22:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture2);
                imgDeletePicture2.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(21, fileUri);
                Log.d("size_img_2", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_23:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture3);
                imgDeletePicture3.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(22, fileUri);
                Log.d("size_img_3", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_24:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture4);
                imgDeletePicture4.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(23, fileUri);
                Log.d("size_img_4", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_25:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture5);
                imgDeletePicture5.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(24, fileUri);
                Log.d("size_img_5", String.valueOf(fileLength));
        }
    }

    @Override
    public void onTokenAttachmentExpired() {
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

   /* public void permissionCameraStorage(final int camera) {
        mPermissionHelper = new PermissionHelper.Builder(this).withPermissions(Manifest.permission.CAMERA).withPermissionInfos("Camera Permission Needed").withListener(new PermissionHelper.OnPermissionCheckedListener() {
            @Override
            public void onPermissionGranted(boolean isPermissionAlreadyGranted) {
                showPictureChooser(camera);
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(RetakePhotoActivity.this, R.string.txt_tidak_memiliki_permissions_camera_storage, Toast.LENGTH_SHORT).show();
            }
        }).build();
        mPermissionHelper.requestPermission();
    }*/

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

                    Intent intent = new Intent(RetakePhotoActivity.this, CameraLandscapeActivity.class);
                    intent.putExtra(CameraActivity.FRAME, CameraLandscapeActivity.FRAME_KTP);
                    intent.putExtra(CameraActivity.IS_OCR, true);
                    startActivityForResult(intent, requestCode);

                } else if (requestCode == 102) {

                    Intent intent = new Intent(RetakePhotoActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
                    intent.putExtra(CameraActivity.IS_OCR, false);
                    startActivityForResult(intent, requestCode);
                } else if (requestCode == 103) {

                    Intent intent = new Intent(RetakePhotoActivity.this, CameraOtherActivity.class);
                    intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
                    intent.putExtra(CameraOtherActivity.IS_OCR, false);
                    intent.putExtra(CameraOtherActivity.USESWITCH, true);
                    intent.putExtra(CameraOtherActivity.ISFRONT, false);
                    startActivityForResult(intent, requestCode);
                } else {

                    Intent intent = new Intent(RetakePhotoActivity.this, CameraOtherActivity.class);
                    intent.putExtra(CameraOtherActivity.FRAME, CameraActivity.FRAME_OTHER);
                    intent.putExtra(CameraOtherActivity.IS_OCR, false);
                    intent.putExtra(CameraOtherActivity.USESWITCH, false);
                    intent.putExtra(CameraOtherActivity.ISFRONT, false);
                    startActivityForResult(intent, requestCode);
                }

            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(RetakePhotoActivity.this, R.string.txt_tidak_memiliki_permissions_camera_storage, Toast.LENGTH_SHORT).show();
            }
        }).build();
        mPermissionHelper.requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null)
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPreSubmitPengajuanEditLoad() {
        progressDialog.show();
    }

    @Override
    public void onSuccessSubmitPengajuanEditLoad(Application application) {
        if (application.getOfferingType().equalsIgnoreCase("MTR")) {
            OritvCamera1.setText(getString(R.string.txt_title_attachment_1));
            OritvCamera2.setText(getString(R.string.txt_title_attachment_2));
            OritvCamera3.setText(getString(R.string.txt_title_attachment_3));
            OritvCamera4.setText(getString(R.string.txt_title_attachment_4));
            OritvCamera5.setText(getString(R.string.txt_title_attachment_5));
            OritvCamera6.setText(getString(R.string.txt_title_attachment_7));
            OritvCamera7.setText(getString(R.string.txt_title_attachment_8));
            OritvCamera8.setText(getString(R.string.txt_title_attachment_9));
            OritvCamera9.setText(getString(R.string.txt_title_attachment_13));
            OritvCamera10.setText(getString(R.string.txt_title_attachment_14));
            OritvCamera11.setText(getString(R.string.txt_title_attachment_15));
            OritvCamera12.setText(getString(R.string.txt_title_attachment_16));
            OritvCamera13.setText(getString(R.string.txt_title_attachment_17));
            OritvCamera14.setText(getString(R.string.txt_title_attachment_18));
            OritvCamera19.setText(getString(R.string.txt_title_attachment_19));
            OritvCamera15.setText(getString(R.string.txt_title_attachment_6));
            OritvCamera16.setText(getString(R.string.txt_title_attachment_10));
            OritvCamera17.setText(getString(R.string.txt_title_attachment_11));
            OritvCamera18.setText(getString(R.string.txt_title_attachment_12));
            OritvCamera20.setText(getString(R.string.txt_title_attachment_20));
            OritvCamera21.setText(getString(R.string.txt_title_attachment_21));
            OritvCamera22.setText(getString(R.string.txt_title_attachment_22));
            OritvCamera23.setText(getString(R.string.txt_title_attachment_23));
            OritvCamera24.setText(getString(R.string.txt_title_attachment_24));
            OritvCamera25.setText(getString(R.string.txt_title_attachment_25));

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
        } else {
            llContainerPhotoNonElc.setVisibility(View.GONE);
            llContainerPhotoNonElcOriImg.setVisibility(View.GONE);
            OritvCamera1.setText(getString(R.string.txt_title_attachment_1));
            OritvCamera2.setText(getString(R.string.txt_title_attachment_2));
            OritvCamera3.setText("Close up Konsumen dengan KTP*");
            OritvCamera4.setText("Konsumen dan CRO");
            OritvCamera5.setText("Slip Gaji");
            OritvCamera6.setText("Dokumen Tambahan");
            OritvCamera7.setText("Lain - lain");
            OritvCamera8.setText("Lain - lain");
            OritvCamera9.setText("Lain - lain");
            OritvCamera10.setText("Lain - lain");
            OritvCamera11.setText("Lain - lain");

            tvCamera1.setText(getString(R.string.txt_title_attachment_1));
            tvCamera2.setText(getString(R.string.txt_title_attachment_2));
            tvCamera3.setText("Close up Konsumen dengan KTP*");
            tvCamera4.setText("Konsumen dan CRO");
            tvCamera5.setText("Slip Gaji");
            tvCamera6.setText("Dokumen Tambahan");
            tvCamera7.setText("Lain - lain");
            tvCamera8.setText("Lain - lain");
            tvCamera9.setText("Lain - lain");
            tvCamera10.setText("Lain - lain");
            tvCamera11.setText("Lain - lain");
        }

        editLoadDataPribadi(application);
        editLoadDataAssetOrAgunan(application);
        if (cro.equals("cro")) {
            editLoadDataPerhitungan(application);
        } else {
            editLoadDataPerhitunganKendaraan(application);
        }

        editLoadDataKeterangan(application);
        editLoadDataAttachment(application);

        progressDialog.dismiss();
    }

    private void editLoadDataAttachment(Application application) {

        countImage = 0;
        isImageError = false;
        tmpAttachments = new ArrayList<>();

        if (cro.equalsIgnoreCase("cro")) {
            numberOfImages = application.getAttachmentElc().size();
            setAllImageELC(application);
        } else {
            numberOfImages = application.getAttachmentMTR().size();
            if ("M".equalsIgnoreCase(application.getPersonalData().getMaritalStatus()) || "MENIKAH".equalsIgnoreCase(application.getPersonalData().getMaritalStatus())) {
                setAllImageMarried(application);
            } else {
                setAllImageSingle(application);
            }
        }
    }

    private void setAllImageMarried(Application application) {
        final int[] countErrorImage = {0};

        for (int i = 0; i < application.getAttachmentMTR().size(); i++) {
            if (application.getAttachmentMTR().get(i).equals("")) {
                tmpAttachments.add(application.getAttachmentMTR().get(i) + "null");
            } else {
                tmpAttachments.add(application.getAttachmentMTR().get(i));
            }
//
            viewDeleteImages.get(i).setVisibility(View.GONE);
            if (i == 24) break;
        }

        for (int i = 0; i < tmpAttachments.size(); i++) {
            if (tmpAttachments.get(i).equals("null")) {
//                    viewPbImages.get(i).setVisibility(View.GONE);
//                    Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_take_camera)).into((ImageView) viewTakeImagesVehicleFirst.get(i));
                viewPbImages.get(i).setVisibility(View.GONE);
                viewCameras.get(i).setVisibility(View.VISIBLE);

            } else {
                Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImages.get(i));
//                    viewPbImages.get(i).setVisibility(ProgressBar.GONE);
//                    viewCameras.get(i).setVisibility(View.VISIBLE);
            }
            viewDeleteImages.get(i).setVisibility(View.GONE);
            if (i == 24) break;
        }
    }

    private void setAllImageELC(Application application) {
        final int[] countErrorImage = {0};

        for (int i = 0; i < application.getAttachmentElc().size(); i++) {
            if (application.getAttachmentElc().get(i).equals("")) {
                tmpAttachments.add(application.getAttachmentElc().get(i) + "null");
            } else {
                tmpAttachments.add(application.getAttachmentElc().get(i));
            }
//
            viewDeleteImagesELC.get(i).setVisibility(View.GONE);
            if (i == 24) break;
        }

        for (int i = 0; i < tmpAttachments.size(); i++) {
            if (tmpAttachments.get(i).equals("null")) {
//                    viewPbImages.get(i).setVisibility(View.GONE);
//                    Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_take_camera)).into((ImageView) viewTakeImagesVehicleFirst.get(i));
                viewPbImagesELC.get(i).setVisibility(View.GONE);
                viewCamerasELC.get(i).setVisibility(View.VISIBLE);

            } else {
                Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImagesELC.get(i));
                viewPbImagesELC.get(i).setVisibility(ProgressBar.GONE);
//                    viewCameras.get(i).setVisibility(View.VISIBLE);
            }
            viewDeleteImagesELC.get(i).setVisibility(View.GONE);
            if (i == 24) break;
        }
    }

    private void setAllImageSingle(Application application) {
        final int[] countErrorImage = {0};
        OrilnTakeFoto5.setVisibility(View.GONE);
        llContainerCamera5.setVisibility(View.GONE);

        for (int i = 0; i < application.getAttachmentMTR().size(); i++) {
            if (application.getAttachmentMTR().get(i).equals("")) {
                tmpAttachments.add(application.getAttachmentMTR().get(i) + "null");
            } else {
                tmpAttachments.add(application.getAttachmentMTR().get(i));
            }
//
            viewDeleteImages.get(i).setVisibility(View.GONE);
            if (i == 24) break;
        }

        for (int i = 0; i < tmpAttachments.size(); i++) {
            if (tmpAttachments.get(i).equals("null")) {
//                    viewPbImages.get(i).setVisibility(View.GONE);
//                    Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_take_camera)).into((ImageView) viewTakeImagesVehicleFirst.get(i));
                viewPbImages.get(i).setVisibility(View.GONE);
                viewCameras.get(i).setVisibility(View.VISIBLE);

            } else {
                Glide.with(getContext()).load(tmpAttachments.get(i)).centerCrop().into((ImageView) viewTakeImagesVehicleFirst.get(i));
                viewPbImages.get(i).setVisibility(ProgressBar.GONE);
//                    viewCameras.get(i).setVisibility(View.VISIBLE);
            }
            viewDeleteImages.get(i).setVisibility(View.GONE);
            if (i == 24) break;
        }
    }

    private void editLoadDataAssetOrAgunan(Application application) {
        if (cro.equalsIgnoreCase("cro")) {
            cvAgunan.setVisibility(View.GONE);
            List<Asset> listAsset = application.getAsset();
            int idx = 1;
            for (Asset a : listAsset) {

                View assetView = getLayoutInflater().inflate(R.layout.view_item_form_detail_asset, null);

                TextView tvAssetHeader = (TextView) assetView.findViewById(R.id.txt_asset_item_header);
                TextView tvNamaBarang = (TextView) assetView.findViewById(R.id.txt_nama_barang_asset_item);
                TextView tvPrice = (TextView) assetView.findViewById(R.id.txt_price_asset_item);
                TextView tvDP = (TextView) assetView.findViewById(R.id.txt_dp_asset_item);
                TextView tvDiscount = (TextView) assetView.findViewById(R.id.txt_discount_asset_item);

                tvAssetHeader.setText(tvAssetHeader.getText() + " " + idx);
                tvNamaBarang.setText(a.getAssetCode());
                tvPrice.setText(Util.formatNominal(a.getOTRPrice()));
                tvDP.setText(Util.formatNominal(a.getDPAmount()));
                tvDiscount.setText(Util.formatNominal(a.getDiscount()));

                lnAssetDetailProductContainer.addView(assetView);
                idx++;
            }
        } else {
            cvDetailAsset.setVisibility(View.GONE);
            tvWilayahKendaraan.setText(application.getAsset().get(0).getRegion());
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

    private void editLoadDataKeterangan(Application application) {
        txtKeteranganRetake.setText(application.getDescriptionRetake());
    }

    private void editLoadDataPerhitungan(Application application) {
        txtPurchasePrice.setText(Util.formatNominal(application.getDetailFinancing().getPurchasePrice()));
        txtDiscount.setText(Util.formatNominal(application.getDetailFinancing().getDiscount()));
        txtDp.setText(Util.formatNominal(application.getDetailFinancing().getDownPayment()));
        txtNtf.setText(Util.formatNominal(application.getDetailFinancing().getnTF()));
        String tipePembayaran = application.getDetailFinancing().getFirstInstallment();
        if ("AD".equalsIgnoreCase(tipePembayaran) || "".equalsIgnoreCase(tipePembayaran)) {
            txtAngsuranPertama.setText(R.string.text_addm);
        } else if ("AR".equalsIgnoreCase(tipePembayaran)) {
            txtAngsuranPertama.setText(R.string.text_addb);
        } else {
            txtAngsuranPertama.setText(tipePembayaran);
        }
        // txtAngsuranPertama.setText(application.getDetailFinancing().getFirstPayment());
        txtBiayaAdministrasi.setText(Util.formatNominal(application.getDetailFinancing().getAdminFee()));
        txtFlateRate.setText(application.getDetailFinancing().getFlatRate() + " %");
        txtAngsuran.setText(Util.formatNominal(application.getDetailFinancing().getFirstInstallmentAmmount()));
        txtInRefund.setText(Util.formatNominal(application.getDetailFinancing().getSubsidyRefund()));
    }

    private void editLoadDataPerhitunganKendaraan(Application application) {
        txtPurchasePrice.setText(Util.formatNominal(application.getDetailFinancing().getCollateralPrice()));
        txtDiscount.setText(Util.formatNominal(application.getDetailFinancing().getDiscount()));
        txtDp.setText(Util.formatNominal(application.getDetailFinancing().getDownPayment()));
        txtNtf.setText(Util.formatNominal(application.getDetailFinancing().getnTF()));
        String tipePembayaran = application.getDetailFinancing().getFirstInstallment();
        if ("AD".equalsIgnoreCase(tipePembayaran) || "".equalsIgnoreCase(tipePembayaran)) {
            txtAngsuranPertama.setText(R.string.text_addm);
        } else if ("AR".equalsIgnoreCase(tipePembayaran)) {
            txtAngsuranPertama.setText(R.string.text_addb);
        } else {
            txtAngsuranPertama.setText(tipePembayaran);
        }
        // txtAngsuranPertama.setText(application.getDetailFinancing().getFirstPayment());
        txtBiayaAdministrasi.setText(Util.formatNominal(application.getDetailFinancing().getAdminFee()));
        txtFlateRate.setText(application.getDetailFinancing().getFlatRateYear() + " %");
        txtAngsuran.setText(Util.formatNominal(application.getDetailFinancing().getInstallmentAmmount()));
        txtInRefund.setText(Util.formatNominal(application.getDetailFinancing().getSubsidyRefund()));
    }

    private void editLoadDataPribadi(Application application) {
        txtNamaLengkap.setText(application.getPersonalData().getLegalName());
        txtAlamatTinggal.setText(application.getResidance().getAddress());
        txtNoHandphone.setText(application.getPersonalData().getMobilePhone());
    }

    @Override
    public void onFailedSubmitPengajuanEditLoad(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetailTokenExpired() {
        progressDialog.dismiss();
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
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture11);
                imgDeletePicture11.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(10, fileUri);
//                Log.d("size_img_1", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_12:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture12);
                imgDeletePicture12.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(11, fileUri);
//                Log.d("size_img_2", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_13:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture13);
                imgDeletePicture13.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(12, fileUri);
//                Log.d("size_img_3", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_14:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture14);
                imgDeletePicture14.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(13, fileUri);
//                Log.d("size_img_4", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_15:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture15);
                imgDeletePicture15.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(14, fileUri);
//                Log.d("size_img_5", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_16:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture16);
                imgDeletePicture16.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(15, fileUri);
//                Log.d("size_img_6", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_17:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture17);
                imgDeletePicture17.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(16, fileUri);
//                Log.d("TAKE_CAMERA_PENGAJUAN_7", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_18:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture18);
                imgDeletePicture18.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(17, fileUri);
//                Log.d("size_img_8", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_19:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture19);
                imgDeletePicture19.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(18, fileUri);
//                Log.d("size_img_9", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_20:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture20);
                imgDeletePicture20.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(19, fileUri);
//                Log.d("size_img_10", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_21:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture21);
                imgDeletePicture21.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(20, fileUri);
//                Log.d("size_img_1", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_22:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture22);
                imgDeletePicture22.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(21, fileUri);
//                Log.d("size_img_2", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_23:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture23);
                imgDeletePicture23.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(22, fileUri);
//                Log.d("size_img_3", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_24:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture24);
                imgDeletePicture24.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(23, fileUri);
//                Log.d("size_img_4", String.valueOf(fileLength));
                break;
            case TAKE_CAMERA_PENGAJUAN_25:
                Glide.with(getContext()).load(fileUri).centerCrop().into(imgTakePicture25);
                imgDeletePicture25.setVisibility(View.VISIBLE);
                mHashMapAttachmentFiles.put(24, fileUri);
//                Log.d("size_img_5", String.valueOf(fileLength));

        }
//        hideFormValidasiAwal();
    }

    @Subscribe
    public void onEventSwitchCamera(SwitchCamera e) {
        if (e.isFront()) {
            Intent intent = new Intent(RetakePhotoActivity.this, CameraActivity.class);
            if (e.isOther) intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_OTHER);
            else intent.putExtra(CameraActivity.FRAME, CameraActivity.FRAME_KTP_FACE);
            intent.putExtra(CameraActivity.IS_OCR, false);
            intent.putExtra(CameraActivity.ISFRONT, true);
            startActivityForResult(intent, 102);
        } else {
            Intent intent = new Intent(RetakePhotoActivity.this, CameraActivity.class);
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
            Intent intent = new Intent(RetakePhotoActivity.this, CameraOtherActivity.class);
            intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
            intent.putExtra(CameraOtherActivity.IS_OCR, false);
            intent.putExtra(CameraOtherActivity.ISFRONT, true);
            startActivityForResult(intent, 103);
        } else {
            Intent intent = new Intent(RetakePhotoActivity.this, CameraOtherActivity.class);
            intent.putExtra(CameraOtherActivity.FRAME, CameraOtherActivity.FRAME_OTHER);
            intent.putExtra(CameraOtherActivity.IS_OCR, false);
            intent.putExtra(CameraOtherActivity.ISFRONT, false);
            startActivityForResult(intent, 103);
        }
    }

    @Override
    public void onValidationSucceeded() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Apakah Anda Yakin ?");
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
                listRetakeAttachment.addAll(mHashMapAttachmentFiles.values());
                if (mHashMapAttachmentFiles.isEmpty()) {
                    txtAttachmentError.setVisibility(View.VISIBLE);
                } else {
                    txtAttachmentError.setVisibility(View.GONE);
                    attachmentPresenter.retakePhoto(token, strAppId, mHashMapAttachmentFiles, offeringType, isUncompleted);
                }
            }
        });
        alert.show();
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
    }
}

