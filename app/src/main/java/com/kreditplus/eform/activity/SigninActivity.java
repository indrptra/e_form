package com.kreditplus.eform.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.Constant;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.SyncDateDump;
import com.kreditplus.eform.model.response.LoginResponse;
import com.kreditplus.eform.model.response.VersionCodeResponse;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.ForceUpdateApkPresenter;
import com.kreditplus.eform.presenter.MasterDataPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.SendFcmIdPresenter;
import com.kreditplus.eform.presenter.SigninPresenter;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.MasterDataMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.presenter.mvpview.SendFcmIdMvpView;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.presenter.mvpview.VersionCodeMvpView;
import com.kreditplus.eform.service.DatabaseService;
import com.kreditplus.eform.service.GPSTrackerService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.services.common.Crash;
import itsmagic.present.permissionhelper.util.PermissionHelper;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class SigninActivity extends BaseActivity implements
        RefreshTokenMvpView, SigninMvpView, Validator.ValidationListener,
        CoordinateMvpView, MasterDataMvpView, VersionCodeMvpView {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 6000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
    private Location mCurrentLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;

    private Boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor editor;

    @Inject
    RefreshTokenPresenter mRefreshTokenPresenter;

    @NotEmpty
    @BindView(R.id.edt_user_id)
    EditText edtUserId;
    @Password
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.btn_refresh)
    ImageView btnRefresh;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
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
    @BindView(R.id.testingCameraView)
    ImageView cameraTesting;

    private Validator validator;
    private String token;
    private MasterDataPresenter mMasterDataPresenter;
    private SigninPresenter mSigninPresenter;
    private ForceUpdateApkPresenter mForceUpdateApkPresenter;
    private DatabaseService databaseService;

    // Location
    private CoordinatePresenter mCoordinatePresenter;
    private String deviceId;

    private double sharedLatitude;
    private double sharedLongitude;

    private Handler handler = new Handler();
    private PermissionHelper mPermissionHelper;
    private String strImei;
    private String strTipeHp;
    private String strOsVersion;
    private String globalUsername;
//    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.appComponent().inject(this);
        unbinder = ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "sign_in");
        mFirebaseAnalytics.logEvent("sign_in_open", bundle);

        ivRefresh.setImageResource(0);
        deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        cameraTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigninActivity.this, CameraKTP.class);
                startActivity(i);
            }
        });
//        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        strImei = telephonyManager.getDeviceId();
//        strTipeHp = android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
//        strOsVersion = System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")" + ", " + android.os.Build.VERSION.SDK_INT;

        mRequestingLocationUpdates = true;
        mLastUpdateTime = "";
        updateValuesFromBundle(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);


        validator = new Validator(this);
        validator.setValidationListener(this);

        mRefreshTokenPresenter = new RefreshTokenPresenter();
        mMasterDataPresenter = new MasterDataPresenter();
        mSigninPresenter = new SigninPresenter();
        mForceUpdateApkPresenter = new ForceUpdateApkPresenter();
        mCoordinatePresenter = new CoordinatePresenter();
        databaseService = new DatabaseService(this);

        mRefreshTokenPresenter.attachView(this);
        mMasterDataPresenter.attachView(this);
        mSigninPresenter.attachView(this);
        mForceUpdateApkPresenter.attachView(this);
        mCoordinatePresenter.attachView(this);

        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");

        //To make app fullscreen but still show the status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        sharedLatitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LAT", "0"));
        sharedLongitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LNG", "0"));

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean blnGPS = false;
        if (service != null) blnGPS = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (blnGPS) {
            mForceUpdateApkPresenter.CheckVersionAPk(BuildConfig.VERSION_NAME);
            if (ivRefresh != null) {
                if (sharedLatitude != 0.0 && sharedLongitude != 0.0) {
                    ivRefresh.setImageResource(R.color.green);
                    Toast.makeText(this, "Lokasi sudah terdeteksi, silahkan login", Toast.LENGTH_SHORT).show();
                } else {
                    callUpdateLocation();
                    if (mCurrentLocation != null && mCurrentLocation.getLongitude() != 0.0 && mCurrentLocation.getLatitude() != 0.0) {
                        ivRefresh.setImageResource(R.color.green);
                        Toast.makeText(this, "Lokasi sudah terdeteksi, silahkan login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        edtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    signin();
                    return true;
                }
                return false;
            }
        });
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLongLat();
            }
        };
    }

    private void callUpdateLocation() {
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest).addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                if (ActivityCompat.checkSelfPermission(SigninActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SigninActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mRequestingLocationUpdates = true;
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                updateLongLat();
            }
        });
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES))
                mRequestingLocationUpdates = savedInstanceState.getBoolean(KEY_REQUESTING_LOCATION_UPDATES);
            if (savedInstanceState.keySet().contains(KEY_LOCATION))
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING))
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            updateLongLat();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_signin;
    }

    @OnClick(R.id.btn_refresh)
    public void onClickRefresh() {
        startActivity(new Intent(this, SigninActivity.class));
        finish();


    }

    @OnClick(R.id.btn_signin)
    public void signin() {
        if (Util.checkActiveLocation(this)) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "sign_in_submit");
            mFirebaseAnalytics.logEvent("sign_in_submit", bundle);

            validator.validate();
            callUpdateLocation();
        }
    }

    private void updateLongLat() {
        if (ivRefresh != null) {
            if (sharedLatitude != 0.0 && sharedLongitude != 0.0 || mCurrentLocation != null) {
                ivRefresh.setImageResource(R.color.green);
            } else ivRefresh.setImageResource(R.color.red);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }

    @Override
    public void onPreCoordinate() {

    }

    @Override
    public void onFailedCoordinate(String massage) {

    }

    @Override
    public void onTokenCoordinateExpired() {
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(token);
    }

    @Override
    public void onPreMasterData() {
        llLoading.setVisibility(View.VISIBLE);
        pbHorizontal.setProgress(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessMasterData() {
        String Date = Util.TanggalHariIni(new DateTime());
        SyncDateDump syncDateDump = new SyncDateDump();
        syncDateDump.setDateSyncDump(Date);
        try {
            databaseService.getSyncDateDumpDao().create(syncDateDump);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e("SyncDate", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }
        Crashlytics.setUserName(globalUsername);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailedMasterData(String message) {
        hideAllLoading();
        tvMessage.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        tvMessage.setText(message);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.Flag.IS_QA_WGS) mMasterDataPresenter.MasterData(token, "true");
                else mMasterDataPresenter.MasterData(token, "false");
            }
        });
    }

    @Override
    public void onTokenMasterDataExpired() {
        hideAllLoading();
        token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(token);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onUpdateCountMasterData(final int countMasterData) {
        final String number = String.valueOf(countMasterData);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                tvPbh.setText(number + " / 15");
                pbHorizontal.setProgress(countMasterData);
            }
        });
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        this.token = token;
        editor.putString(getResources().getString(R.string.sharedpref_token), token);
        editor.commit();
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreSignin() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessSignin(LoginResponse loginResponse) {
        if (loginResponse.getStatus().equals("0")) {
            hideAllLoading();
            pbMain.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Pemberitahuan");
            alert.setCancelable(false);
            alert.setMessage(loginResponse.getMessage());
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.go_to_playstore_app)))); /*intent playstore*/
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.go_to_playstore_web)))); /*intent  web site*/
                    }
                }
            });
            alert.show();
        } else {
            String action = getString(R.string.action_successful_login);
            double sharedLatitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LAT", "0"));
            double sharedLongitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LNG", "0"));
//            String sharedAOSalesStatus = sharedPreferences.getString("AOSALES_STATUS","");
            editor.putBoolean("BranchLocking",loginResponse.isBranchLocking());
            editor.putString("AOsales_status", loginResponse.getAOSalesStatus());
            editor.commit();
            token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
            Log.i("logToken", token);


            if (mCurrentLocation != null) {
                double dblLongitude = mCurrentLocation.getLongitude();
                double dblLatitude = mCurrentLocation.getLatitude();
                if (dblLongitude != 0.0 && dblLatitude != 0.0)
                    Util.saveCoordinate(mCoordinatePresenter, token, dblLatitude, dblLongitude, action);
                else
                    Util.saveCoordinate(mCoordinatePresenter, token, sharedLatitude, sharedLongitude, action);
            } else
                Util.saveCoordinate(mCoordinatePresenter, token, sharedLatitude, sharedLongitude, action);

            try {
                databaseService.createDatabase();
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
            hideAllLoading();
            pbMain.setVisibility(View.GONE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (Constant.Flag.IS_QA_WGS) mMasterDataPresenter.MasterData(token, "true");
            else mMasterDataPresenter.MasterData(token, "false");
        }
    }

    private void hideAllLoading() {
        llLoading.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mRequestingLocationUpdates = false;
            }
        });
    }


    @Override
    public void onFailedSignin(String message) {
        pbMain.setVisibility(View.GONE);
        hideAllLoading();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationSucceeded() {
        double spLatitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LAT", "0"));
        double spLongitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LNG", "0"));

        String versionCode = Constant.Flag.IS_VERSION_CODE;
        if (Util.checkActiveLocation(this)) {
            String username = edtUserId.getText().toString();
            String password = edtPass.getText().toString();
            globalUsername = username;
            if(password.isEmpty()){
                edtPass.setError("This field is required");
            }else {
                if (Constant.Flag.IS_DEVELOPER) {
                    mSigninPresenter.login(username, password, String.valueOf(spLongitude), String.valueOf(spLatitude), versionCode, "developer", "123", "123", "123");
                } else {
                    if (mCurrentLocation != null) {
                        double dblLongitude = mCurrentLocation.getLongitude();
                        double dblLatitude = mCurrentLocation.getLatitude();
                        if (dblLongitude != 0.0 && dblLatitude != 0.0) {
                            if (username.equals("trial9") || username.equals("trial11") || username.equals("maryati"))
                                mSigninPresenter.login(username, password, String.valueOf(dblLongitude), String.valueOf(dblLatitude), versionCode, "developer", "123", "123", "123");
                            else
                                mSigninPresenter.login(username, password, String.valueOf(dblLongitude), String.valueOf(dblLatitude), versionCode, deviceId, "123", "123", "123");
                        }
                    } else if (spLatitude != 0.0 && spLongitude != 0.0) {
                        if (username.equals("trial9") || username.equals("trial11") || username.equals("maryati"))
                            mSigninPresenter.login(username, password, String.valueOf(spLongitude), String.valueOf(spLatitude), versionCode, "developer", "123", "123", "123");
                        else
                            mSigninPresenter.login(username, password, String.valueOf(spLongitude), String.valueOf(spLatitude), versionCode, "developer", "123", "123", "123");
                    } else {
                        Toast.makeText(this, "Lokasi belum terdeteksi, silahkan tekan tombol refresh", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            String username = edtUserId.getText().toString();
            String password = edtPass.getText().toString();

            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if(password.isEmpty()) message = "This field is required";
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ivRefresh.setImageResource(0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        double onrsrtLatitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LAT", "0"));
        double onrsrtLongitude = Double.parseDouble(sharedPreferences.getString("LOCATION_LNG", "0"));

        boolean isGPSEnabled = true;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null)
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isGPSEnabled) {
            mForceUpdateApkPresenter.CheckVersionAPk(Constant.Flag.IS_VERSION_CODE);
            if (ivRefresh != null) {
                updateLongLat();
                callUpdateLocation();
                if (onrsrtLatitude != 0.0 && onrsrtLongitude != 0.0) {
                    Toast.makeText(this, "Lokasi sudah terdeteksi, silahkan login", Toast.LENGTH_SHORT).show();
                } else {
                    callUpdateLocation();
                    if (mCurrentLocation != null && mCurrentLocation.getLongitude() != 0.0 && mCurrentLocation.getLatitude() != 0.0) {
                        Toast.makeText(this, "Lokasi sudah terdeteksi, silahkan login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefreshTokenPresenter.detachView();
        mSigninPresenter.detachView();
        mForceUpdateApkPresenter.detachView();
        mMasterDataPresenter.detachView();
        mCoordinatePresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.iv_logo)
    public void onViewClicked() {
        if (Constant.Flag.IS_DEVELOPER) {
            edtUserId.setText("trial11");
            edtPass.setText("finansia");
        }
    }

    @Override
    public void onPreCheckVersionCode() {
        hideAllLoading();
        pbMain.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onSuccessCheckVersionCode(VersionCodeResponse versionCodeResponse) {
        hideAllLoading();
        pbMain.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (versionCodeResponse.getStatus().equals("0")) {
            final String appPackageName = getPackageName();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Pemberitahuan");
            alert.setCancelable(false);
            alert.setMessage(versionCodeResponse.getMessage());
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.go_to_playstore_app)))); /*intent playstore*/
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.go_to_playstore_web)))); /*intent  web site*/
                    }
                }
            });
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onFailedCheckVersionCode(String message) {
        pbMain.setVisibility(View.GONE);
        hideAllLoading();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
