package com.kreditplus.eform.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.fragment.FaqFragment;
import com.kreditplus.eform.fragment.HomeFragment;
import com.kreditplus.eform.fragment.KreditmuFragment;
import com.kreditplus.eform.fragment.ListJanjiBertemuFragment;
import com.kreditplus.eform.fragment.ListKpmFragment;
import com.kreditplus.eform.fragment.ListKreditmuFragment;
import com.kreditplus.eform.fragment.NotifikasiFragment;
import com.kreditplus.eform.fragment.ProfilFragment;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.Aobranch;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.BranchMaster;
import com.kreditplus.eform.model.DataFinansial;
import com.kreditplus.eform.model.ImgPhotoProfile;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PosMaster;
import com.kreditplus.eform.model.ProductOfTenor;
import com.kreditplus.eform.model.ProductOfferingMaster;
import com.kreditplus.eform.model.Recomendation;
import com.kreditplus.eform.model.ResultAobranch;
import com.kreditplus.eform.model.SupplierEmp;
import com.kreditplus.eform.model.SupplierMaster;
import com.kreditplus.eform.model.SyncDateDump;
import com.kreditplus.eform.model.TblAlamat;
import com.kreditplus.eform.model.TblAssetMasterMerkKendaraan;
import com.kreditplus.eform.model.TblAssetMasterTypeKendaraan;
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
import com.kreditplus.eform.model.TblNewIndustryTypeMaster;
import com.kreditplus.eform.model.TblNewProfJobPosition;
import com.kreditplus.eform.model.TblNewProfJobType;
import com.kreditplus.eform.model.TblNewProfession;
import com.kreditplus.eform.model.TblRekomendasi;
import com.kreditplus.eform.model.TblTandaTangan;
import com.kreditplus.eform.model.bus.ContextKreditmuFragment;
import com.kreditplus.eform.model.bus.SetDataProfileNav;
import com.kreditplus.eform.model.bus.SetDataRateNav;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.CoordinateResponse;
import com.kreditplus.eform.presenter.CheckLocationPresenter;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.SendFcmIdPresenter;
import com.kreditplus.eform.presenter.SignoutPresenter;
import com.kreditplus.eform.presenter.mvpview.CheckLocationMvpView;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.presenter.mvpview.SendFcmIdMvpView;
import com.kreditplus.eform.presenter.mvpview.SignoutMvpView;
import com.kreditplus.eform.service.DatabaseService;
import com.kreditplus.eform.service.GPSTrackerService;
import com.kreditplus.eform.service.MyFirebaseMessagingService;
//import com.kreditplus.eform.service.RegistrationIntentService;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import itsmagic.present.permissionhelper.util.PermissionHelper;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class HomeActivity extends BaseActivity implements
        FragmentManager.OnBackStackChangedListener,
        SignoutMvpView, RefreshTokenMvpView,
        CoordinateMvpView, SendFcmIdMvpView, CheckLocationMvpView {

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    SignoutPresenter mSignoutPresenter;

    @Inject
    SharedPreferences.Editor editor;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    DatabaseService databaseService;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.pb_circle)
    ProgressBar pbCircle;

    private LinearLayout lnPengajuanExpand;
    private LinearLayout lnKpmExpand;
    private LinearLayout lnKreditmuExpand;

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private final Handler mDrawerActionHandler = new Handler();
    private ActionBarDrawerToggle toggle;

    private String token;
    private String cro;
    private String statusLogin;
    private MyFirebaseMessagingService firebaseMessagingService;

    private List<MasterFormPengajuan> masterFormPengajuanList = new ArrayList<>();

    private String path1;

    private PermissionHelper mPermissionHelper;
    private SendFcmIdPresenter mSendFcmIdPresenter;
    private RefreshTokenPresenter mRefreshTokenPresenter;
    private CheckLocationPresenter mCheckLocationPresenter;
    private double returnRate;
    private double penggunaan;
    private boolean isFromKreditmu;
    private KreditmuFragment fragmentKreditmu;
    private boolean isFromPengajuanBaru;

    // Location
    private GPSTrackerService gps;
    private CoordinatePresenter mCoordinatePresenter;
    private CoordinateResponse mCoordinateResponse;
    private double longitude;
    private double latitude;
    private String action;
    private FragmentManager fm;
    private boolean isFormPengajuanFragment;
    private String branchId;
    private AlertDialog dialogLoading;
    private String firstLogin;
    private String checkDateLogin;
    private String username, fcmId;
    private boolean branchLocking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        checkDateLogin = df.format(Calendar.getInstance().getTime());
        firebaseMessagingService = new MyFirebaseMessagingService();
        init(savedInstanceState);
        initPermission();


        branchId = sharedPreferences.getString(getResources().getString(R.string.sharedpref_brachCode), "");
        firstLogin = sharedPreferences.getString(getResources().getString(R.string.sharedpref_first_login), "");

        if (!firstLogin.equals(checkDateLogin)) {
            deleteFormDraft();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getContext(), SigninActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    private void initPermission() {
        mPermissionHelper = new PermissionHelper.Builder(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA).withPermissionInfos(
                getString(R.string.warning_permission_location),
                getString(R.string.warning_permission_location),
                getString(R.string.warning_permission_camera)).withListener(new PermissionHelper.OnPermissionCheckedListener() {
            @Override
            public void onPermissionGranted(boolean isPermissionAlreadyGranted) {
//                        startService();
            }

            @Override
            public void onPermissionDenied() {

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
    protected void onDestroy() {
        super.onDestroy();
        mSignoutPresenter.detachView();
        mRefreshTokenPresenter.detachView();
        mSendFcmIdPresenter.detachView();
        mCoordinatePresenter.detachView();
        EventBus.getDefault().unregister(this);
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
    }

    private void init(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        EventBus.getDefault().register(this);

        mSignoutPresenter = new SignoutPresenter();
        mSendFcmIdPresenter = new SendFcmIdPresenter();
        mRefreshTokenPresenter = new RefreshTokenPresenter();
        mCoordinatePresenter = new CoordinatePresenter();
        mCheckLocationPresenter = new CheckLocationPresenter();

        App.appComponent().inject(this);
        mSignoutPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        mSendFcmIdPresenter.attachView(this);
        mCoordinatePresenter.attachView(this);
        mCheckLocationPresenter.attachView(this);

        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        fcmId = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token_firebase_first), "");
        cro = sharedPreferences.getString(getResources().getString(R.string.sharedpref_cro), "");
        username = sharedPreferences.getString(getResources().getString(R.string.sharedpref_user_name), "");
        statusLogin = sharedPreferences.getString(getResources().getString(R.string.sharedpref_status_login), "");
        branchLocking = sharedPreferences.getBoolean("BranchLocking", false);

        returnRate = Double.longBitsToDouble(sharedPreferences.getLong(getResources().getString(R.string.sharedpref_return_rate), 0));
        penggunaan = Double.longBitsToDouble(sharedPreferences.getLong(getResources().getString(R.string.sharedpref_precentage_usage), 0));

        longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
        latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));

        AlertDialog.Builder builderLoading = new AlertDialog.Builder(this);
        builderLoading.setView(R.layout.dialog_loading);
        builderLoading.setCancelable(false);
        dialogLoading = builderLoading.create();

        setRate(returnRate, penggunaan);
        photoRecord();// ambil foto cro dari database untuk ditampilkan pada menu drawer
        setupContentNavigationView();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        Fragment fragmentHome = new HomeFragment();
        Bundle bundle = getIntent().getExtras();
        fragmentHome.setArguments(bundle);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container_home, fragmentHome, "HomeFragment").commit();
        if (fcmId.isEmpty()) {
          setUpNewToken();
        } else {
            mSendFcmIdPresenter.SendFcmId(token, fcmId);
            Log.i("Token_Old", fcmId);
        }


    }
    private void setUpNewToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                sharedPreferences = getSharedPreferences(getString(R.string.sharedprefname), Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.sharedpref_token_firebase_first), newToken);
                editor.apply();
                mSendFcmIdPresenter.SendFcmId(token, newToken);
                Log.i("TokenFirebaseAct", newToken);
            }
        });
    }

    private void setupContentNavigationView() {
        View contentNavigationView = navView.getChildAt(1);
        setProfile();

        RelativeLayout rlHome = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_home);
        RelativeLayout rlProfil = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_profil);
        RelativeLayout rlPengajuan = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_pengajuan);
        RelativeLayout rlNotifikasi = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_notifikasi);
        RelativeLayout rlPengaturan = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_pengaturan);
        RelativeLayout rlKeluar = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_keluar);
        RelativeLayout rlElektronik = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_elektronik);
        RelativeLayout rlMobil = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_mobil);
//        RelativeLayout rlMobilBekas = (RelativeLayout) contentNavigationView.findViewById(R.id.rl_mobil_bekas);
        RelativeLayout rlMotor = contentNavigationView.findViewById(R.id.rl_motor);
        RelativeLayout rlFaq = contentNavigationView.findViewById(R.id.rl_faq);
        RelativeLayout rlKreditmu = contentNavigationView.findViewById(R.id.rl_kreditmu);
        RelativeLayout rlFormKreditmu = contentNavigationView.findViewById(R.id.rl_form_kreditmu);
        RelativeLayout rlListKreditmu = contentNavigationView.findViewById(R.id.rl_list_kreditmu);
        RelativeLayout rlMenuKpm = contentNavigationView.findViewById(R.id.rl_menu_kpm);
        RelativeLayout rlListKpm = contentNavigationView.findViewById(R.id.rl_list_kpm);
        RelativeLayout rlListJanji = contentNavigationView.findViewById(R.id.rl_list_janji);
        lnPengajuanExpand = contentNavigationView.findViewById(R.id.ln_expand_pengajuan);
        lnKpmExpand = contentNavigationView.findViewById(R.id.ln_expand_kpm);
        lnKreditmuExpand = contentNavigationView.findViewById(R.id.ln_expand_kreditmu);

        //TODO: Validasi untuk Elektronik atau Bukan
        if (cro.equalsIgnoreCase("cro")) {
            rlMobil.setVisibility(View.GONE);
            rlMotor.setVisibility(View.GONE);
        } else if (cro.equalsIgnoreCase("cmo")) {
            rlElektronik.setVisibility(View.GONE);
            rlMobil.setVisibility(View.GONE);
        } else if (cro.equalsIgnoreCase("cmo-mobil")) {
            rlElektronik.setVisibility(View.GONE);
            rlMotor.setVisibility(View.GONE);
        }

        rlHome.setOnClickListener(sidebarItemOnClick());
        rlProfil.setOnClickListener(sidebarItemOnClick());
        rlPengajuan.setOnClickListener(sidebarItemOnClick());
        rlNotifikasi.setOnClickListener(sidebarItemOnClick());
        rlPengaturan.setOnClickListener(sidebarItemOnClick());
        rlKeluar.setOnClickListener(sidebarItemOnClick());
        rlElektronik.setOnClickListener(sidebarItemOnClick());
        rlMobil.setOnClickListener(sidebarItemOnClick());
        rlMotor.setOnClickListener(sidebarItemOnClick());
        rlKreditmu.setOnClickListener(sidebarItemOnClick());
        rlFormKreditmu.setOnClickListener(sidebarItemOnClick());
        rlListKreditmu.setOnClickListener(sidebarItemOnClick());
        rlFaq.setOnClickListener(sidebarItemOnClick());
        rlMenuKpm.setOnClickListener(sidebarItemOnClick());
        rlListKpm.setOnClickListener(sidebarItemOnClick());
        rlListJanji.setOnClickListener(sidebarItemOnClick());
    }

    private void setProfile() {
        photoRecord();
        View contentNavigationView = navView.getChildAt(1);
        CircleImageView imgUserPhoto = (CircleImageView) contentNavigationView.findViewById(R.id.img_user_photo);
        TextView txtName = (TextView) contentNavigationView.findViewById(R.id.txt_name);
        TextView txtEmail = (TextView) contentNavigationView.findViewById(R.id.txt_email);
        TextView txtVersion = (TextView) contentNavigationView.findViewById(R.id.txt_version);
        //
        String urlPhoto = sharedPreferences.getString(getResources().getString(R.string.sharedpref_url_photo), "R.drawable.ic_default_photo");
        String name = sharedPreferences.getString(getResources().getString(R.string.sharedpref_firstname), "-") + " " + sharedPreferences.getString(getResources().getString(R.string.sharedpref_lastname), "");
        String email = sharedPreferences.getString(getResources().getString(R.string.sharedpref_email), "-");
        Glide.with(this).load(urlPhoto).centerCrop().
                placeholder(R.drawable.ic_default_photo).error(R.drawable.ic_default_photo).into(imgUserPhoto);
        txtName.setText(name);
        txtEmail.setText(email);
        txtVersion.setText("Version : " + Util.getVersionName(this));


    }

    private void setRate(double rate, double penggunaan) {
        View contentNavigationView = navView.getChildAt(1);
        TextView rateText = (TextView) contentNavigationView.findViewById(R.id.txt_name_rate);
        TextView penggunaanText = (TextView) contentNavigationView.findViewById(R.id.txt_name_penggunaan);
        TextView tvViewNameNameRate = (TextView) contentNavigationView.findViewById(R.id.tv_view_name_rate);
        TextView tvViewNamePenggunaan = (TextView) contentNavigationView.findViewById(R.id.tv_view_name_penggunaan);

        if (rate >= 50) {
            rateText.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } else {
            rateText.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        }

        if (penggunaan >= 50) {
            penggunaanText.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } else {
            penggunaanText.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        }

        String rateString = " " + String.valueOf(rate) + "%";
        String penggunaanString = " " + String.valueOf(penggunaan) + "%";

        rateText.setText(rateString);
        penggunaanText.setText(penggunaanString);
    }

    private View.OnClickListener sidebarItemOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v.getId() == R.id.rl_pengajuan) {
                    if (lnPengajuanExpand != null) {
                        if (lnPengajuanExpand.getVisibility() == View.GONE) {
                            lnPengajuanExpand.setVisibility(View.VISIBLE);
                        } else {
                            lnPengajuanExpand.setVisibility(View.GONE);
                        }
                    }
                } else if (v.getId() == R.id.rl_menu_kpm) {
                    if (lnKpmExpand.getVisibility() == View.GONE) {
                        lnKpmExpand.setVisibility(View.VISIBLE);
                    } else {
                        lnKpmExpand.setVisibility(View.GONE);
                    }
                } else if (v.getId() == R.id.rl_kreditmu) {
                    if (lnKreditmuExpand.getVisibility() == View.GONE) {
                        lnKreditmuExpand.setVisibility(View.VISIBLE);
                    } else {
                        lnKreditmuExpand.setVisibility(View.GONE);
                    }
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    mDrawerActionHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            navigateSidebar(v);
                        }
                    }, DRAWER_CLOSE_DELAY_MS);
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged() {
        fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() <= 0) {
            getSupportActionBar().setTitle(("EFORM"));
        }
    }

    private void navigateSidebar(View view) {
        if (Util.checkActiveLocation(this)) {
            checkNavigationMenu(view);
        }

    }

    private void checkNavigationMenu(View view) {
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String tag = "";
        switch (view.getId()) {
            case R.id.rl_home:
//                fragment = new HomeFragment();
//                tag = "HomeFragment";

                Bundle bundleHome = new Bundle();
                bundleHome.putString(FirebaseAnalytics.Param.ITEM_NAME, "navbar_home_click");
                mFirebaseAnalytics.logEvent("navbar_home_click", bundleHome);
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.rl_profil:
                Bundle bundleProfile = new Bundle();
                bundleProfile.putString(FirebaseAnalytics.Param.ITEM_NAME, "navbar_profile_click");
                mFirebaseAnalytics.logEvent("navbar_profile_click", bundleProfile);
                fragment = new ProfilFragment();
                tag = "ProfilFragment";
                break;
            case R.id.rl_elektronik:
                if (branchLocking) {
                    mCheckLocationPresenter.checkUserLocation(token, longitude, latitude);
                } else {

                    Bundle bundleElc = new Bundle();
                    bundleElc.putString(FirebaseAnalytics.Param.ITEM_NAME, "navbar_elektronik_click");
                    mFirebaseAnalytics.logEvent("navbar_elektronik_click", bundleElc);
                    Intent intent = new Intent(this, FormPengajuanActivity.class);
                    intent.putExtra("pengajuan_type", "E");
                    intent.putExtra("type_data_offering", "ELC");
                    intent.putExtra("form", "New");
                    startActivity(intent);
                }
                break;
            case R.id.rl_mobil:

                Bundle bundleMobil = new Bundle();
                bundleMobil.putString(FirebaseAnalytics.Param.ITEM_NAME, "navbar_mobil_click");
                mFirebaseAnalytics.logEvent("navbar_mobil_click", bundleMobil);
                Intent intentMobil = new Intent(this, FormPengajuanNonElcActivity.class);
                intentMobil.putExtra("pengajuan_type", "E");
                intentMobil.putExtra("type_data_offering", "MBL");
                intentMobil.putExtra("application_id", "");
                intentMobil.putExtra("form", "New");
                startActivity(intentMobil);
                break;
/*            case R.id.rl_mobil_bekas:
                Intent intentMobilBekas = new Intent(this, FormPengajuanNonElcActivity.class);
                intentMobilBekas.putExtra("pengajuan_type", "G");
                intentMobilBekas.putExtra("type_data_offering", "MBLBKS");
                intentMobilBekas.putExtra("form", "New");
                startActivity(intentMobilBekas);
                break;*/
            case R.id.rl_motor:

                Bundle bundleMotor = new Bundle();
                bundleMotor.putString(FirebaseAnalytics.Param.ITEM_NAME, "navbar_motor_click");
                mFirebaseAnalytics.logEvent("navbar_motor_click", bundleMotor);
                Intent intentMotor = new Intent(this, FormPengajuanNonElcActivity.class);
                intentMotor.putExtra("pengajuan_type", "G");
                intentMotor.putExtra("type_data_offering", "MTR");
                intentMotor.putExtra("application_id", "");
                intentMotor.putExtra("form", "New");
                startActivity(intentMotor);
                break;
            case R.id.rl_notifikasi:

                Bundle bundleNotif = new Bundle();
                bundleNotif.putString(FirebaseAnalytics.Param.ITEM_NAME, "navbar_notif_click");
                mFirebaseAnalytics.logEvent("navbar_home_click", bundleNotif);
                fragment = new NotifikasiFragment();
                tag = "NotifikasiFragment";
                break;
            case R.id.rl_list_kpm:
                fragment = new ListKpmFragment();
                tag = "ListKpmFragment";
                break;
            case R.id.rl_list_janji:
                fragment = new ListJanjiBertemuFragment();
                tag = "ListjanjiBertemuFragment";
                break;
            case R.id.rl_faq:

                Bundle bundleFaq = new Bundle();
                bundleFaq.putString(FirebaseAnalytics.Param.ITEM_NAME, "navbar_faq_click");
                mFirebaseAnalytics.logEvent("navbar_faq_click", bundleFaq);
                fragment = new FaqFragment();
                tag = "FaqFragment";
                break;
            case R.id.rl_form_kreditmu:
                fragment = new KreditmuFragment();
                tag = "KreditmuFragment";
                break;
            case R.id.rl_list_kreditmu:
                fragment = new ListKreditmuFragment();
                tag = "ListKreditmuFragment";
                break;
            case R.id.rl_pengaturan:
                startActivity(new Intent(this, PengaturanActivity.class));
                break;
            case R.id.rl_keluar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Apakah Anda memilih keluar aplikasi?");
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            masterFormPengajuanList = databaseService.getMasterFormPengajuanDao().queryBuilder().where().eq("status_sync", 0).query();
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG) Log.e("Query status", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        action = getString(R.string.action_successful_logout);
                        sendLocations();
                        deleteFormDraft();
                    }
                });
                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }
        if (fragment != null && !"".equalsIgnoreCase(tag)) {
            changeFragment(fragment, tag);
        }
    }

    private void deleteFormDraft() {
        dialogLoading.show();
        try {
            if (firstLogin.equals(checkDateLogin)) {
                masterFormPengajuanList = databaseService.getMasterFormPengajuanDao().queryBuilder().where().eq("status_sync", 0).query();
            } else {
                masterFormPengajuanList = databaseService.getMasterFormPengajuanDao().queryBuilder().query();
            }

            /*API MASTER DATA*/
            DeleteBuilder<TblNewIndustryTypeMaster, String> dltTblNewIndustryTypeMaster = databaseService.getIndustryTypeMasterDao().deleteBuilder();
            dltTblNewIndustryTypeMaster.delete();

            DeleteBuilder<TblNewProfession, String> dltTblNewProfession = databaseService.getProfessionDao().deleteBuilder();
            dltTblNewProfession.delete();

            DeleteBuilder<TblNewProfJobPosition, String> dltTblNewProfJobPosition = databaseService.getProfJobPositionDao().deleteBuilder();
            dltTblNewProfJobPosition.delete();

            DeleteBuilder<TblNewProfJobType, String> dltTblNewProfJobType = databaseService.getProfJobTypeDao().deleteBuilder();
            dltTblNewProfJobType.delete();

            /*API MASTER SYNC*/
            DeleteBuilder<PosMaster, String> dltPosMaster = databaseService.getPosMasterDao().deleteBuilder();
            dltPosMaster.delete();

            DeleteBuilder<ProductOfferingMaster, String> dltProductOfferingMaster = databaseService.getProductOfferingMasterDao().deleteBuilder();
            dltProductOfferingMaster.delete();

            DeleteBuilder<ProductOfTenor, String> dltProductOfTenor = databaseService.getProductOfTenorDao().deleteBuilder();
            dltProductOfTenor.delete();

            DeleteBuilder<SupplierEmp, String> dltSupplierEmp = databaseService.getSupplierEmpDao().deleteBuilder();
            dltSupplierEmp.delete();

            DeleteBuilder<SupplierMaster, String> dltSupplierMaster = databaseService.getSupplierMasterDao().deleteBuilder();
            dltSupplierMaster.delete();

            DeleteBuilder<Aobranch, String> dltAobranch = databaseService.getAobranchDao().deleteBuilder();
            dltAobranch.delete();

            DeleteBuilder<BranchMaster, String> dltBranchMaster = databaseService.getBranchMasterDao().deleteBuilder();
            dltBranchMaster.delete();

            DeleteBuilder<ResultAobranch, String> dltResultAobranch = databaseService.getResultAobranchDao().deleteBuilder();
            dltResultAobranch.delete();

            DeleteBuilder<DataFinansial, String> dltDataFinansial = databaseService.getDataFinansialDao().deleteBuilder();
            dltDataFinansial.delete();

            DeleteBuilder<TblAssetMasterMerkKendaraan, String> dltTblAssetMasterMerkKendaraan = databaseService.getTblAssetMasterMerkKendaraanDao().deleteBuilder();
            dltTblAssetMasterMerkKendaraan.delete();

            DeleteBuilder<TblAssetMasterTypeKendaraan, String> dltTblAssetMasterTypeKendaraan = databaseService.getTblAssetMasterTypeKendaraanDao().deleteBuilder();
            dltTblAssetMasterTypeKendaraan.delete();

            /*DB LOKAL*/
            DeleteBuilder<MasterFormPengajuan, Integer> dltMasterFormPengajuan = databaseService.getMasterFormPengajuanDao().deleteBuilder();
            dltMasterFormPengajuan.delete();

            DeleteBuilder<TblKop, String> dltTblKop = databaseService.getTblKopDao().deleteBuilder();
            dltTblKop.delete();

            DeleteBuilder<TblDataPribadi, String> dltTblDataPribadi = databaseService.getTblDataPribadiDao().deleteBuilder();
            dltTblDataPribadi.delete();

            DeleteBuilder<TblDataPasangan, String> dltTblDataPasangan = databaseService.getTblDataPasanganDao().deleteBuilder();
            dltTblDataPasangan.delete();

            DeleteBuilder<TblAlamat, String> dltTblAlamat = databaseService.getTblAlamatDao().deleteBuilder();
            dltTblAlamat.delete();

            DeleteBuilder<TblKontakDarurat, String> dltTblKontakDarurat = databaseService.getTblKontakDaruratDao().deleteBuilder();
            dltTblKontakDarurat.delete();

            DeleteBuilder<TblDataPekerjaan, String> dltTblDataPekerjaan = databaseService.getTblDataPekerjaanDao().deleteBuilder();
            dltTblDataPekerjaan.delete();

            DeleteBuilder<TblDataKartuKredit, String> dltTblDataKartuKredit = databaseService.getTblDataKartuKreditDao().deleteBuilder();
            dltTblDataKartuKredit.delete();

            DeleteBuilder<TblKartuMembership, String> dltTblKartuMembership = databaseService.getTblKartuMembershipDao().deleteBuilder();
            dltTblKartuMembership.delete();

            DeleteBuilder<TblDetailProduct, String> dltTblDetailProduct = databaseService.getTblDetailProductDao().deleteBuilder();
            dltTblDetailProduct.delete();

            DeleteBuilder<TblAsuransi, String> dltTblAsuransi = databaseService.getTblAsuransiDao().deleteBuilder();
            dltTblAsuransi.delete();

            DeleteBuilder<TblDataPerhitungan, String> dltTblDataPerhitungan = databaseService.getTblDataPerhitunganDao().deleteBuilder();
            dltTblDataPerhitungan.delete();

            DeleteBuilder<TblKeterangan, String> dltTblKeterangan = databaseService.getTblKeteranganDao().deleteBuilder();
            dltTblKeterangan.delete();

            DeleteBuilder<TblRekomendasi, String> dltTblRekomendasi = databaseService.getTblRekomendasiDao().deleteBuilder();
            dltTblRekomendasi.delete();

            DeleteBuilder<TblLokasi, String> dltTblLokasi = databaseService.getTblLokasiDao().deleteBuilder();
            dltTblLokasi.delete();

            DeleteBuilder<TblTandaTangan, String> dltTblTandaTangan = databaseService.getTblTandaTanganDao().deleteBuilder();
            dltTblTandaTangan.delete();

            DeleteBuilder<Attachment, Integer> deleteAttachmentBuilder = databaseService.getAttachmentDao().deleteBuilder();
            deleteAttachmentBuilder.delete();

            DeleteBuilder<AssetElektronik, String> deleteAssetBuilder = databaseService.getAssetDao().deleteBuilder();
            deleteAssetBuilder.delete();

            DeleteBuilder<MaskingRate, String> rateStringDeleteBuilder = databaseService.getMaskingRateDao().deleteBuilder();
            rateStringDeleteBuilder.delete();

            DeleteBuilder<MaskingTenor, String> tenorStringDeleteBuilder = databaseService.getMaskingTenorDao().deleteBuilder();
            tenorStringDeleteBuilder.delete();

            DeleteBuilder<SyncDateDump, String> dltSyncDateDump = databaseService.getSyncDateDumpDao().deleteBuilder();
            dltSyncDateDump.delete();

            DeleteBuilder<Recomendation, String> dltRecomendation = databaseService.getRecomendationDao().deleteBuilder();
            dltRecomendation.delete();

            mSignoutPresenter.signout(token);
        } catch (java.sql.SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Del Draft Logout", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    private void changeFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        if (fragment instanceof HomeFragment) {
            ft.replace(R.id.container_home, fragment, tag);
            ft.commit();
        } else {
            ft.hide(getSupportFragmentManager().findFragmentByTag("HomeFragment"));
            ft.add(R.id.container_home, fragment, tag);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpened()) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            fm = getSupportFragmentManager();
            Fragment formPengajuanFragment = fm.findFragmentByTag("FormPengajuanFragment");
            if (fm.getBackStackEntryCount() >= 1) {
                if (formPengajuanFragment != null) {
                    if (formPengajuanFragment.isVisible()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Kembali ke menu utama?");
                        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HomeActivity.super.onBackPressed();
                            }
                        });
                        builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        return;
                    }
                }
                super.onBackPressed();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Yakin keluar aplikasi?");
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }
    }

    public void onBackPressedWithoutPopup() {
        if (isDrawerOpened()) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() >= 1) {
                super.onBackPressed();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Yakin keluar aplikasi?");
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }
    }

    private boolean isDrawerOpened() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onPreSignout() {
        dialogLoading.show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessSignout() {
        editor.clear();
        editor.commit();

        try {
            FileUtils.deleteDirectory(new File(Util.getEasyImageDir(this)));
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e("Delete dir photo", String.valueOf(e));
            Crashlytics.logException(e);
        }

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        dialogLoading.dismiss();
        Intent intent = new Intent(this, SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onFailedSignout(String message) {
        dialogLoading.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onTokenExpired() {
        dialogLoading.dismiss();
        String checkToken = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(checkToken);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.commit();
        mSignoutPresenter.signout(token);
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void photoRecord() {
        try {
            List<ImgPhotoProfile> imgPhotoProfileList = databaseService.getImgPhotoProfileDao().queryForAll();
            for (int i = 0; i < imgPhotoProfileList.size(); i++) {
                path1 = imgPhotoProfileList.get(i).getPath1();
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Query photo profile", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }


    @Subscribe
    public void SetDataProfile(SetDataProfileNav e) {
        setProfile();
    }

    @Subscribe
    public void SetDataRate(SetDataRateNav e) {
        setRate(e.getRate(), e.getPenggunaan());
    }

    @Subscribe
    public void contextKreditmu(ContextKreditmuFragment e) {
        fragmentKreditmu = e.getKreditmuFragment();
        isFromKreditmu = true;
    }


    @Override
    public void onPreCoordinate() {

    }

    @Override
    public void onFailedCoordinate(String massage) {
        Toast.makeText(getContext(), massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenCoordinateExpired() {
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(token);
    }

    private void sendLocations() {
        try {
            longitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_longitude), "0"));
            latitude = Double.parseDouble(sharedPreferences.getString(getString(R.string.sharedpref_location_latitude), "0"));
            Util.saveCoordinate(mCoordinatePresenter, token, latitude, longitude, action);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e("GPS", String.valueOf(e));
            Crashlytics.logException(e);
        }
    }

    @Override
    public void onPreSendFcmId() {

    }

    @Override
    public void onSuccessSendFcmId() {

    }

    @Override
    public void onFailedSendFcmId(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreCheckLocation() {
        dialogLoading.show();
    }

    @Override
    public void onSuccessCheckLocation(BaseResponse baseResponse) {
        if (baseResponse.getMeta().isSucceded()) {
            dialogLoading.dismiss();
            Intent intent = new Intent(this, FormPengajuanActivity.class);
            intent.putExtra("pengajuan_type", "E");
            intent.putExtra("type_data_offering", "ELC");
            intent.putExtra("form", "New");
            startActivity(intent);
        }
    }

    @Override
    public void onFailedCheckLocation(String message) {
        try {
            masterFormPengajuanList = databaseService.getMasterFormPengajuanDao().queryBuilder().where().eq("status_sync", 0).query();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) Log.e("Query status", String.valueOf(e));
            Crashlytics.logException(e);
        }
        action = getString(R.string.action_successful_logout);
        sendLocations();
        deleteFormDraft();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
