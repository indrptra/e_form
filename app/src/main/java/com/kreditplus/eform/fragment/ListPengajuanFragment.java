package com.kreditplus.eform.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.FormPengajuanNonElcActivity;
import com.kreditplus.eform.adapter.PengajuanAdapter;
import com.kreditplus.eform.dialog.ChangePasswordDialog;
import com.kreditplus.eform.dialog.UpdateAplikasiDialog;
import com.kreditplus.eform.helper.ShowNotification;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.SyncDateDump;
import com.kreditplus.eform.model.bus.ChangePassword;
import com.kreditplus.eform.model.bus.QrScanner;
import com.kreditplus.eform.model.bus.RefreshPengajuanEvent;
import com.kreditplus.eform.model.bus.SetDataRateNav;
import com.kreditplus.eform.model.response.MasterSyncResponse;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;
import com.kreditplus.eform.presenter.CheckVersionPresenter;
import com.kreditplus.eform.presenter.CoordinatePresenter;
import com.kreditplus.eform.presenter.DownloadPdfPresenter;
import com.kreditplus.eform.presenter.PengajuanListPresenter;
import com.kreditplus.eform.presenter.ProfilPresenter;
import com.kreditplus.eform.presenter.QrPresenter;
import com.kreditplus.eform.presenter.RefreshTokenPresenter;
import com.kreditplus.eform.presenter.ReturnRatePresenter;
import com.kreditplus.eform.presenter.mvpview.CheckVersionMvpView;
import com.kreditplus.eform.presenter.mvpview.CoordinateMvpView;
import com.kreditplus.eform.presenter.mvpview.DownloadPdfMvpView;
import com.kreditplus.eform.presenter.mvpview.MasterSyncMvpView;
import com.kreditplus.eform.presenter.mvpview.PengajuanListMvpView;
import com.kreditplus.eform.presenter.mvpview.ProfilMvpView;
import com.kreditplus.eform.presenter.mvpview.QrMvpView;
import com.kreditplus.eform.presenter.mvpview.RefreshTokenMvpView;
import com.kreditplus.eform.presenter.mvpview.ReturnRateMvpView;
import com.kreditplus.eform.service.DatabaseService;
import com.paginate.Paginate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class ListPengajuanFragment extends BaseFragment implements PengajuanListMvpView, RefreshTokenMvpView, Paginate.Callbacks,
        MasterSyncMvpView, CheckVersionMvpView, ReturnRateMvpView, DownloadPdfMvpView, ProfilMvpView,
        QrMvpView, CoordinateMvpView {

    @BindView(R.id.rv_pengajuan)
    RecyclerView rvPengajuan;
    @BindView(R.id.spn_pengajuan)
    Spinner spnPengajuan;
    @BindArray(R.array.pengajuan)
    String[] pengajuanFilter;
    @BindView(R.id.frm_wrap)
    FrameLayout frmSpinnerWrapper;
    @BindView(R.id.txt_error_api)
    TextView txtErrorApi;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;


    @Inject
    SharedPreferences.Editor editor;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    PengajuanListPresenter mPengajuanListPresenter;

    @Inject
    RefreshTokenPresenter mRefreshTokenPresenter;


    private static final int REFRESH_PENGAJUAN = 1;
    private static final int REFRESH_MASTER = 2;
    private static final int REFRESH_EXPIRED = 3;
    private static final int REFRESH_DRAFT = 4;
    private static final int REFRESH_VERSION = 5;
    private static final int REFRESH_RATE = 6;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.btn_date)
    ImageView btnDate;
    @BindView(R.id.ll_date)
    LinearLayout llDate;

    private int limit = 10;
    private String search;
    private String dateSearch;
    private String mToken;
    private String cekToken;
    private String codeSync;
    private ViewAnimator vaPengajuan;
    private PengajuanAdapter adapter;
    private List<Pengajuan> mPengajuanList = new ArrayList<>();
    private String filter = "all";
    private int filterValue;
    private List<String> appIdList = new ArrayList<>();
    private boolean loading;
    private boolean isFetch = false;
    private boolean mHasLoadedAllItems;
    private Paginate paginate;
    private int firstSelectedListener;
    private ProgressDialog progressDialog;
    private String dateSyncdump;
    private DatabaseService databaseService;
    @BindArray(R.array.type_master_sync)
    String[] typeMasterSyncs;
    @BindArray(R.array.master_sync_strings)
    String[] masterSyncArrayDialog;
    private int countMaster;
    private String typeString;
    private String stringMasterSyncDialogs;
    private CheckVersionPresenter mCheckVersionPresenter;
    private ReturnRatePresenter mReturnRatePresenter;
    private DownloadPdfPresenter mDownloadPdfPresenter;
    private ProfilPresenter mProfilPresenter;
    private QrPresenter mQrScannerPresenter;
    private boolean isNewCro;
    private String username;
    private boolean isDownloadingMaster;
    private List<Integer> typeRefreshTokens;
    private boolean isAlradeyRefresh;
    private List<PengajuanBaru> pengajuanBaruList;
    private HashMap<String, String> mapId;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar calendar;
    private View view;
    private String pdfUrl;
    private String pdfName;
    private AlertDialog dialogLoading;
    private CoordinatePresenter mCoordinatePresenter;
    private String cro;
    private String[] listPengajuan;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        AlertDialog.Builder builderLoading = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builderLoading.setView(R.layout.dialog_loading);
        }
        builderLoading.setCancelable(false);
        dialogLoading = builderLoading.create();

        if (bundle != null) {
            appIdList = bundle.getStringArrayList("app_id_list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_pengajuan, container, false);
        ButterKnife.bind(this, view);
        vaPengajuan = (ViewAnimator) view.findViewById(R.id.va_pengajuan);
        App.appComponent().inject(this);

        databaseService = new DatabaseService(getContext());
        mCheckVersionPresenter = new CheckVersionPresenter();
        mReturnRatePresenter = new ReturnRatePresenter();
        mDownloadPdfPresenter = new DownloadPdfPresenter();
        mProfilPresenter = new ProfilPresenter();
        mQrScannerPresenter = new QrPresenter();
        mCoordinatePresenter = new CoordinatePresenter();

        mCoordinatePresenter.attachView(this);
        mPengajuanListPresenter.attachView(this);
        mRefreshTokenPresenter.attachView(this);
        mCheckVersionPresenter.attachView(this);
        mReturnRatePresenter.attachView(this);
        mDownloadPdfPresenter.attachView(this);
        mProfilPresenter.attachView(this);
        mQrScannerPresenter.attachView(this);
        mToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        cro = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_cro), "");
        codeSync = sharedPreferences.getString("CODEMASTER", "");
        isNewCro = sharedPreferences.getBoolean(getActivity().getResources().getString(R.string.sharedpref_new_cro), false);
        isDownloadingMaster = sharedPreferences.getBoolean(getActivity().getResources().getString(R.string.sharedpref_downloadingMaster), false);
        username = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_user_name), "");
        EventBus.getDefault().register(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.setTitle("");
        progressDialog.setCanceledOnTouchOutside(false);

        typeRefreshTokens = new ArrayList<>();
        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (cro.equalsIgnoreCase("cro")) {
            listPengajuan = new String[]{
                    "All",
                    "New",
                    "On Process",
                    "Purchase Order",
                    "Reject",
                    "Cancel",
                    "Delivered",
                    "Invoice",
                    "Funding Process",
                    "Transferred",
                    "Principal Approval",
                    "Approved",
                    "RCA"};
        } else {
            listPengajuan = new String[]{
                    "All",
                    "New",
                    "On Process",
                    "Rejected",
                    "Canceled",
                    "Principle Approval",
                    "Approved"};
        }
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_dropdown, R.id.id_item, listPengajuan);
        spnPengajuan.setAdapter(spnAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPengajuan.setLayoutManager(linearLayoutManager);

        adapter = new PengajuanAdapter(getActivity(), mPengajuanList, this);
        rvPengajuan.setAdapter(adapter);

        if (BuildConfig.DEBUG) {
            Log.e("isNewCro", String.valueOf(isNewCro));
            Log.e("username", username);
        }

        isAlradeyRefresh = false;
        if (isNewCro) checkVersion();

        if (!appIdList.isEmpty()) {
            frmSpinnerWrapper.setVisibility(View.GONE);
            filterSubmission();
        } else {
            setupPaginaton();
        }


        spnPengajuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firstSelectedListener += 1;
                if (firstSelectedListener > 1) switch (position) {
                    case 0:/*All*/
                        filterSubmission();
                        break;
                    case 1:/*New*/
                        filterSubmission();
                        break;
                    case 2:/*On Process*/
                        filterSubmission();
                        break;
                    case 3:/*Purchase Order*/
                        filterSubmission();
                        break;
                    case 4:/*Reject*/
                        filterSubmission();
                        break;
                    case 5:/*Cancel*/
                        filterSubmission();
                        break;
                    case 6:/*Delivered*/
                        filterSubmission();
                        break;
                    case 7:/*Invoice*/
                        filterSubmission();
                        break;
                    case 8:/*Funding Process*/
                        filterSubmission();
                        break;
                    case 9:/*Transferred*/
                        filterSubmission();
                        break;
                    case 10:/*Principal Approval*/
                        filterSubmission();
                        break;
                    case 11:/*Approved*/
                        filterSubmission();
                        break;
                    case 12:/*RCA*/
                        filterSubmission();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void playStoreDialog() {
        DialogFragment dialogFragment = new UpdateAplikasiDialog();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "UpdateAplikasiDialog");
    }

    private void checkVersion() {
        String versionCode = Integer.toString(Util.getVersionCode(getContext()));
        mCheckVersionPresenter.checkingVersion(mToken, versionCode);
    }

    private void setupPaginaton() {
        if (paginate != null) paginate.unbind();
        loading = false;
        mHasLoadedAllItems = false;

        paginate = Paginate.with(rvPengajuan, this).setLoadingTriggerThreshold(7).addLoadingListItem(true).build();
    }

    private void filterSubmission() {
        search = etSearch.getText().toString().trim();
        dateSearch = etDate.getText().toString().trim();

        if (spnPengajuan.getSelectedItem().toString().equals("all")) filter = "all";
        else filter = "status";

        if (spnPengajuan.getSelectedItem().toString().equals("All")) filterValue = 0;
        else if (spnPengajuan.getSelectedItem().toString().equals("New")) filterValue = 16;
        else if (spnPengajuan.getSelectedItem().toString().equals("On Process")) filterValue = 1;
        else if (spnPengajuan.getSelectedItem().toString().equals("Purchase Order"))
            filterValue = 8;
        else if (spnPengajuan.getSelectedItem().toString().equals("Reject") || spnPengajuan.getSelectedItem().toString().equals("Rejected"))
            filterValue = 14;
        else if (spnPengajuan.getSelectedItem().toString().equals("Cancel") || spnPengajuan.getSelectedItem().toString().equals("Canceled")) filterValue = 19;
        else if (spnPengajuan.getSelectedItem().toString().equals("Delivered")) filterValue = 10;
        else if (spnPengajuan.getSelectedItem().toString().equals("Invoice")) filterValue = 27;
        else if (spnPengajuan.getSelectedItem().toString().equals("Funding Process"))
            filterValue = 28;
        else if (spnPengajuan.getSelectedItem().toString().equals("Transferred")) filterValue = 29;
        else if (spnPengajuan.getSelectedItem().toString().equals("Principal Approval")|| spnPengajuan.getSelectedItem().toString().equals("Principle Approval"))
            filterValue = 32;
        else if (spnPengajuan.getSelectedItem().toString().equals("Approved")) filterValue = 22;
        else if (spnPengajuan.getSelectedItem().toString().equals("RCA")) filterValue = 35;

        mPengajuanList.clear();

        //refresh adapter disini

        adapter.notifyDataSetChanged();
        mapId = new HashMap<>();
        for (int i = 0; i < appIdList.size(); i++) {
            mapId.put("application_id[" + i + "]", appIdList.get(i));
        }
        if(isFetch){

        }else {
            isFetch = true;

            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "search_pengajuan_click");
            mFirebaseAnalytics.logEvent("search_pengajuan_click", bundle);
            mPengajuanListPresenter.pengajuanList(mToken, mPengajuanList.size(), limit, filter, filterValue, dateSearch, search, mapId, "", "false");
        }
    }

    private void callMasterSync() {
        // bila setelah login tidak perlu memanggil mastersync
        if ("0".equalsIgnoreCase(codeSync)) {
            editor.putString(getContext().getResources().getString(R.string.sharedpref_codeMaster), "1");
            editor.commit();
        } else {
            try {
                countMaster = 0;
                typeString = typeMasterSyncs[countMaster];
                stringMasterSyncDialogs = masterSyncArrayDialog[countMaster];
                List<SyncDateDump> dateDumpList = databaseService.getSyncDateDumpDao().queryForAll();
                dateSyncdump = dateDumpList.get(0).getDateSyncDump();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("Sync Home", String.valueOf(e));
                Crashlytics.logException(e);
            }
        }
    }

    private void checkReturnRate(double nowRate, String date, double penggunaan) {

        //cek apakah baru pertama kali punya returnRate
        int firstTimeReturnRate = sharedPreferences.getInt(getActivity().getResources().getString(R.string.sharedpref_return_rate_first_time), 0);
        if (firstTimeReturnRate == 0) {
            editor.putLong(getActivity().getResources().getString(R.string.sharedpref_return_rate_save), Double.doubleToRawLongBits(nowRate));
            editor.putLong(getActivity().getResources().getString(R.string.sharedpref_precentage_usage_save), Double.doubleToRawLongBits(penggunaan));
            editor.putInt(getActivity().getResources().getString(R.string.sharedpref_return_rate_first_time), 1);
            editor.commit();
            if (BuildConfig.DEBUG) Log.e("Retrun Rate", "This is first time");
        } else {
            if (BuildConfig.DEBUG) Log.e("Retrun Rate", "This is not first time");
        }

        double rateSave = Double.longBitsToDouble(sharedPreferences.getLong(getActivity().getResources().getString(R.string.sharedpref_return_rate_save), 0));
        double usageSave = Double.longBitsToDouble(sharedPreferences.getLong(getActivity().getResources().getString(R.string.sharedpref_precentage_usage_save), 0));
        double firstRate = rateSave + 10;
        double firstUsage = usageSave + 10;
        String convertDate = Util.ConvertDateSignin(date);
        String bigText = getActivity().getResources().getString(R.string.text_return_rate) + " " + nowRate + "%" + "\n" + getActivity().getResources().getString(R.string.text_presentasi_penggunaan) + " " + penggunaan + "%" + "\n" + getActivity().getResources().getString(R.string.text_date_rate) + " " + convertDate;
        String smallText = getActivity().getResources().getString(R.string.text_data_return_rate_small);

        editor.putLong(getActivity().getResources().getString(R.string.sharedpref_return_rate), Double.doubleToRawLongBits(nowRate));
        editor.putLong(getActivity().getResources().getString(R.string.sharedpref_precentage_usage), Double.doubleToRawLongBits(penggunaan));
        editor.commit();

        if (BuildConfig.DEBUG) {
            Log.e("nowrate", String.valueOf(nowRate));
            Log.e("penggunaan", String.valueOf(penggunaan));
            Log.e("ratesave", String.valueOf(rateSave));
            Log.e("firstRate", String.valueOf(firstRate));
            Log.e("usageSave", String.valueOf(usageSave));
            Log.e("firstusage", String.valueOf(firstUsage));
            Log.e("date rate", convertDate);
        }

        EventBus.getDefault().post(new SetDataRateNav(nowRate, penggunaan));
        if (nowRate > 50) {
            if (firstRate <= nowRate || firstUsage <= penggunaan) {
                editor.putLong(getActivity().getResources().getString(R.string.sharedpref_return_rate_save), Double.doubleToRawLongBits(nowRate));
                editor.putLong(getActivity().getResources().getString(R.string.sharedpref_precentage_usage_save), Double.doubleToRawLongBits(penggunaan));
                editor.commit();
                ShowNotification.dataNotification(bigText, "Return Rate & Presentase Penggunaan", getContext(), smallText);
                if (BuildConfig.DEBUG) {
                    Log.e("Show Notification", "true");
                }
            } else {
                if (BuildConfig.DEBUG) {
                    Log.e("Show Notification", "false(false in comparation)");
                }
            }
        } else {
            if (BuildConfig.DEBUG) {
                Log.e("Show Notification", "false(less than 50)");
            }
        }

    }

    public void getPdfurl(String applicationId, String pdfName, int type) {

        if (type == 0) {
            mDownloadPdfPresenter.downloadPdfPengajuan(mToken, pdfName, applicationId);
        } else {
            mDownloadPdfPresenter.downloadPdfPerjanjian(mToken, applicationId);
        }
    }

    public void passwordDialog() {
        DialogFragment dialogFragment = new ChangePasswordDialog();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "UpdateAplikasiDialog");
    }

    @OnClick(R.id.btn_retry)
    public void retryCallApi() {
        mHasLoadedAllItems = true;
        isFetch = false;
        filterSubmission();
    }


    @Subscribe
    public void refreshPengajuan(RefreshPengajuanEvent e) {
        mHasLoadedAllItems = true;
        isFetch = false;
        filterSubmission();
    }

    @Subscribe
    public void changePassword(ChangePassword e) {
        mProfilPresenter.updatePassword(mToken, username, e.getNewPassword());
    }

    @Subscribe
    public void checkQrResult(QrScanner e) {
        mQrScannerPresenter.qrResultValidaiton(mToken, e.getAppId(), e.getResult());
    }

    @Override
    public void onPreLoadPengajuan() {
        if (mPengajuanList.isEmpty()) {
            vaPengajuan.setDisplayedChild(2);
        }
    }

    @Override
    public void onSuccessLoadPengajuan(int totalData, List<Pengajuan> pengajuanList) {
        loading = false;
        isFetch = false;
            if (pengajuanList.isEmpty() && mPengajuanList.isEmpty()) {
                vaPengajuan.setDisplayedChild(0);
            } else {
                vaPengajuan.setDisplayedChild(3);
                if (!pengajuanList.isEmpty()) {
                    mHasLoadedAllItems = false;
                    mPengajuanList.addAll(pengajuanList);
                    Log.d("pengajuan list :" , mPengajuanList.toString());
                    adapter.notifyItemRangeInserted(mPengajuanList.size(), pengajuanList.size());
                } else {
                    mHasLoadedAllItems = true;
                    paginate.setHasMoreDataToLoad(false);
                }
            }
    }

    @Override
    public void onFailedLoadPengajuan(String message) {
        loading = false;
        if (mPengajuanList.isEmpty()) {
            vaPengajuan.setDisplayedChild(1);
            txtErrorApi.setText(message);
        }
    }

    @Override
    public void onTokenExpired() {
        cekToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
        typeRefreshTokens.add(REFRESH_PENGAJUAN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPengajuanListPresenter.detachView();
        mCoordinatePresenter.detachView();
        mRefreshTokenPresenter.detachView();
        mCheckVersionPresenter.detachView();
        mReturnRatePresenter.detachView();
        mDownloadPdfPresenter.detachView();
        mProfilPresenter.detachView();
        mQrScannerPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPreRefreshToken() {

    }

    @Override
    public void onSuccessRefreshToken(String token) {
        mToken = token;
        editor.putString(getContext().getResources().getString(R.string.sharedpref_token), token);
        editor.commit();

        if (BuildConfig.DEBUG) Log.e("already refresh", String.valueOf(isAlradeyRefresh));
        if (!isAlradeyRefresh && !typeRefreshTokens.isEmpty()) {
            for (int i = 0; i < typeRefreshTokens.size(); i++) {
                switch (typeRefreshTokens.get(i)) {
                    case REFRESH_DRAFT:
                        break;
                    case REFRESH_EXPIRED:
                        break;
                    case REFRESH_MASTER:
                        callMasterSync();
                        break;
                    case REFRESH_PENGAJUAN:
                        if(isFetch){

                        }else{
                            isFetch = true;
                            mPengajuanListPresenter.pengajuanList(mToken, mPengajuanList.size(), limit, filter, filterValue, dateSearch, search, mapId, "", "false");
                        }
                        break;
                    case REFRESH_RATE:
                        mReturnRatePresenter.returnRateData(mToken);
                        break;
                    case REFRESH_VERSION:
                        checkVersion();
                        break;
                }
            }
            typeRefreshTokens.clear();
        }
    }

    @Override
    public void onFailedRefreshToken(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        search = etSearch.getText().toString().trim();
        dateSearch = etDate.getText().toString().trim();
        loading = true;

        Map<String, String> mapId = new HashMap<>();
        for (int i = 0; i < appIdList.size(); i++) {
            mapId.put("application_id[" + i + "]", appIdList.get(i));
        }
        if(isFetch){

        }else {
            isFetch = true;
            mPengajuanListPresenter.pengajuanList(mToken, mPengajuanList.size(), limit, filter, filterValue, dateSearch, search, mapId, "", "false");
        }
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return mHasLoadedAllItems;
    }

    @Override
    public void onPreLoadmasterSync() {
    }

    @Override
    public void onSuccessLoadmasterSync(MasterSyncResponse masterSyncResponse) {
        countMaster++;
        String Date = Util.TanggalHariIni(new DateTime());
        if (countMaster <= typeMasterSyncs.length - 1) {
            typeString = typeMasterSyncs[countMaster];
            stringMasterSyncDialogs = masterSyncArrayDialog[countMaster];
        } else {
            try {
                //update sync dump
                UpdateBuilder<SyncDateDump, String> updateDump = databaseService.getSyncDateDumpDao().updateBuilder();
                updateDump.where().eq("DateSyncDump", dateSyncdump);
                updateDump.updateColumnValue("DateSyncDump", Date);
                updateDump.update();
                progressDialog.dismiss();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("Update Date Sync", String.valueOf(e));
                Crashlytics.logException(e);
            }
            editor.putBoolean(getActivity().getResources().getString(R.string.sharedpref_downloadingMaster), false);
            editor.commit();
        }
    }

    @Override
    public void onFailedLoadLoadmasterSync(String message) {
        progressDialog.dismiss();
    }

    @Override
    public void onTokenMasterSyncExpired() {
        cekToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
        typeRefreshTokens.add(REFRESH_MASTER);
    }

    @Override
    public void onUpdateCountMasterSync(int countMasterSync) {

    }

    @Override
    public void onPreCheckVersion() {

    }

    @Override
    public void onSuccessCheckVersion(String check) {
        if ("Out Of Date".equalsIgnoreCase(check)) playStoreDialog();
    }

    @Override
    public void onFailedCheckVersion(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenCheckVersionExpired() {
        progressDialog.dismiss();
        cekToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
        typeRefreshTokens.add(REFRESH_VERSION);
    }

    @Override
    public void onPreLoadRaturnRate() {

    }

    @Override
    public void onSuccessRaturnRate(double rate, int penggunaan, String date) {
        checkReturnRate(rate, date, penggunaan);
    }

    @Override
    public void onFailedReturnRate(String message) {
        if (BuildConfig.DEBUG) {
            Log.e("Return Rate Failed", message);
        }
    }

    @Override
    public void onReturnRateTokenExpired() {
        String cekToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
        typeRefreshTokens.add(REFRESH_RATE);
    }

    @Override
    public void onPreDownloadPdf() {
        progressDialog.setMessage("Generate Pdf");
        progressDialog.show();
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

    @Override
    public void onRefreshTokenPdf() {
        String cekToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
    }

    @Override
    public void onPreCheckQrScanner() {
        progressDialog.setMessage(getActivity().getResources().getString(R.string.progress_qr_scanner));
        progressDialog.show();
    }

    @Override
    public void onSuccessCheckQrScanner(int checked) {
        progressDialog.dismiss();
        String message;
        if (checked == 0) {
            message = getResources().getString(R.string.warning_qr_failed);
        } else {
            message = getResources().getString(R.string.warning_qr_success);
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedCheckQrScanner(String message) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenExpiredCheckQrScanner() {
        String cekToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
    }

    @Override
    public void onPreLoadProfil() {
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void onSuccessLoadProfil(String message) {
        isNewCro = false;
        editor.putBoolean(getActivity().getResources().getString(R.string.sharedpref_new_cro), false);
        editor.apply();
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if (BuildConfig.DEBUG) Log.e("isNewCroNow-Success", String.valueOf(isNewCro));
    }

    @Override
    public void onFailedLoadProfil(String message) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        passwordDialog();
        if (BuildConfig.DEBUG) Log.e("isNewCroNow-Failed", String.valueOf(isNewCro));
    }

    @Override
    public void onTokenProfilExpired() {
        String cekToken = sharedPreferences.getString(getActivity().getResources().getString(R.string.sharedpref_token), "");
        mRefreshTokenPresenter.refreshToken(cekToken);
    }

    @OnClick(R.id.btn_search)
    public void onClickSearch() {
        filterSubmission();
    }

    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.ll_date)
    public void onClickDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View viewDate = inflater.inflate(R.layout.dialog_date, null);
        builder.setView(viewDate);

        final DatePicker datePicker = (DatePicker) viewDate.findViewById(R.id.date_picker);
        datePicker.setSpinnersShown(false);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etDate.getText().clear();
                etDate.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateSearch = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
                etDate.setText(dateSearch);
                filterSubmission();
                etDate.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        builder.show();
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
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
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
}
