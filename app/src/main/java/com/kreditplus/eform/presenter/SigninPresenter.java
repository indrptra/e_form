package com.kreditplus.eform.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.ImgPhotoProfile;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
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
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.LoginResponse;
import com.kreditplus.eform.model.response.objecthelper.ApplicationSaveDraft;
import com.kreditplus.eform.model.response.objecthelper.Asset;
import com.kreditplus.eform.presenter.mvpview.SigninMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import org.joda.time.DateTime;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class SigninPresenter implements BasePresenter<SigninMvpView> {

    @Inject
    SharedPreferences.Editor editor;

    @Inject
    ApiService mApiService;

    @Inject
    ErrorRetrofitUtil mErrorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SigninMvpView mSigninMvpView;
    private Call<BaseResponse<LoginResponse>> call;
    private HashMap<Integer, File> mHashMapAttachmentFiles = new java.util.HashMap<>();
    private List<File> attachmentList = new ArrayList<>();
    private File objAttachment;
    private HashMap<Integer, File> mHashMapPhoto;
    private List<File> avatarFileList;
    private Context mContext;
    private List<PengajuanBaru> cekPengajuanBaruList;
    private int count;
    private boolean isPremiManual;
    private int countKey;


    public SigninPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SigninMvpView view) {
        mSigninMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mSigninMvpView = null;
    }

    public void login(String username, String password, String longitude, String latitude, String versionCode, String deviceId, String imei, String tipeHp, String os) {
        mSigninMvpView.onPreSignin();
        count = 0;
        call = mApiService.login(username, password, longitude, latitude, versionCode, deviceId, imei, tipeHp, os);
        call.enqueue(new Callback<BaseResponse<LoginResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginResponse>> call, Response<BaseResponse<LoginResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    processData(response);
                    mSigninMvpView.onSuccessSignin(response.body().getData());
                } else {
                    mSigninMvpView.onFailedSignin(mErrorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoginResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (count < 3) {
                        call.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mSigninMvpView != null) {
                            mSigninMvpView.onFailedSignin(mErrorRetrofitUtil.responseFailedError(t));
                            count = 0;
                        }
                    }
                }
            }
        });
    }

    private void processData(Response<BaseResponse<LoginResponse>> response) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String firstLogin = df.format(Calendar.getInstance().getTime());

        Boolean isNewCro = false;
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_token), response.body().getData().getToken());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_cro), response.body().getData().getRole());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_token_firebase), response.body().getData().getFcmToken());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_first_login), firstLogin);
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_aoid), response.body().getData().getAoid());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_status_login), "0");
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_start_time), response.body().getData().getStartTime());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_end_time), response.body().getData().getEndTime());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_message_login), response.body().getData().getMessage());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_url_photo), response.body().getData().getUrlPhoto());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_firstname), response.body().getData().getFirstname());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_lastname), response.body().getData().getLastname());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_email), response.body().getData().getEmail());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_codeMaster), "0");
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_alamat), response.body().getData().getAddress());
        if (response.body().getData().getIsFirstLogin() == 1)
            isNewCro = true;
        editor.putBoolean(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_new_cro), isNewCro);
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_user_name), response.body().getData().getUsername());
        editor.putString(mSigninMvpView.getContext().getResources().getString(R.string.sharedpref_brachCode), response.body().getData().getMasterBranchCode());
        editor.apply();

        // Save Avatar
        final int number = 0;
        mHashMapPhoto = new java.util.HashMap<>();
        avatarFileList = new ArrayList<>();
        final ImgPhotoProfile imgPhotoProfile = new ImgPhotoProfile();
        String url = response.body().getData().getUrlPhoto();
        //ToDO : benerin mik soto profil samain kaya kode
//        Glide.with(mSigninMvpView.getContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                mHashMapPhoto.put(number, new File(Util.bitmapToFile2(resource)));
//                avatarFileList.addAll(mHashMapPhoto.values());
//
//                for (int b = 0; b < avatarFileList.size(); b++) {
//                    imgPhotoProfile.setNamaPhoto("eform" + System.currentTimeMillis());
//                    imgPhotoProfile.setPath1(avatarFileList.get(b).getPath());
//                    imgPhotoProfile.setPath2(avatarFileList.get(b).getAbsolutePath());
//                }
//
//                try {
//                    databaseService.getImgPhotoProfileDao().create(imgPhotoProfile);
//                } catch (SQLException e) {
//                    if (BuildConfig.DEBUG) {
//                        Log.e("Photo Profile", String.valueOf(e));
//                    }
//                }
//
//            }
//        });
//============================================================================================================
// process save to db local
        List<ApplicationSaveDraft> applicationList = response.body().getData().getApplicationList();
        if (applicationList != null && !applicationList.isEmpty()) {
            for (final ApplicationSaveDraft application : applicationList) {
                final MasterFormPengajuan masterFormPengajuan = new MasterFormPengajuan();
                masterFormPengajuan.setTipeSaveData("draft_baru");
                masterFormPengajuan.setBlacklistDate(application.getBlacklistdate());
                masterFormPengajuan.setCustomerIdConfins(application.getCustomerId());
                masterFormPengajuan.setIdKpm(application.getApplicationIdKpm());
                masterFormPengajuan.setTipeDataOffering(application.getOfferingType());
                masterFormPengajuan.setBranch(application.getBranchCode().getAoBranch());
                masterFormPengajuan.setMasterBranch(response.body().getData().getMasterBranchCode());
                masterFormPengajuan.setTipePengajuan(application.getDataType());
                masterFormPengajuan.setMobileSubmissionKey(application.getMobileSubmissionKey());
                masterFormPengajuan.setEfNumber(application.getEFNumber());
                masterFormPengajuan.setUuid(application.getPid());
                masterFormPengajuan.setCreatedAt(Util.listPengajuanTimeFormat(new DateTime()));
                masterFormPengajuan.setAppIdBackend("");

                try {
                    databaseService.getMasterFormPengajuanDao().create(masterFormPengajuan);
                } catch (SQLException mfp) {
                    if (BuildConfig.DEBUG) Log.d("MasterFormPengajuan", String.valueOf(mfp));
                    Crashlytics.logException(mfp);
                }

//        KOP
                TblKop tblKop = new TblKop();
                tblKop.setMasterFormPengajuan(masterFormPengajuan);
                String strMetodePenjualan = application.getSaledMethod();
                tblKop.setMetodePenjualan(application.getSaledMethod());
                if (strMetodePenjualan.equals("N")) tblKop.setMetodePenjualanPosition(0);
                if (strMetodePenjualan.equals("C")) tblKop.setMetodePenjualanPosition(1);
                if (strMetodePenjualan.equals("D")) tblKop.setMetodePenjualanPosition(2);
                if (strMetodePenjualan.equals("M")) tblKop.setMetodePenjualanPosition(3);
                if (strMetodePenjualan.equals("T")) tblKop.setMetodePenjualanPosition(4);
                if (strMetodePenjualan.equals("W")) tblKop.setMetodePenjualanPosition(5);
                if (strMetodePenjualan.equals("X")) tblKop.setMetodePenjualanPosition(6);
                if (strMetodePenjualan.equals("NK")) tblKop.setMetodePenjualanPosition(7);
                tblKop.setStatusCustomer(application.getFinancingPurpose());
                try {
                    databaseService.getTblKopDao().create(tblKop);
                } catch (SQLException msgTblKop) {
                    if (BuildConfig.DEBUG) Log.e("TblKop", String.valueOf(msgTblKop));
                    Crashlytics.log(String.valueOf(msgTblKop));
                }

//        DATA PRIBADI
                TblDataPribadi tblDataPribadi = new TblDataPribadi();
                tblDataPribadi.setMasterFormPengajuan(masterFormPengajuan);
                tblDataPribadi.setNoKtp(application.getPersonalData().getiDNumber());
                tblDataPribadi.setNoNpwp(application.getPersonalData().getPersonalNPWP());
                String strMaritalStatus = application.getPersonalData().getMaritalStatus();
                tblDataPribadi.setStatusPernikahan(strMaritalStatus);
                if (strMaritalStatus.equalsIgnoreCase("M"))
                    tblDataPribadi.setStatusPernikahanPosition(0);
                if (strMaritalStatus.equalsIgnoreCase("S"))
                    tblDataPribadi.setStatusPernikahanPosition(1);
                if (strMaritalStatus.equalsIgnoreCase("D"))
                    tblDataPribadi.setStatusPernikahanPosition(2);
                if (strMaritalStatus.equalsIgnoreCase("W"))
                    tblDataPribadi.setStatusPernikahanPosition(3);
                tblDataPribadi.setTanggalLahir(application.getPersonalData().getBirthDate());
                tblDataPribadi.setNomorHandphone(application.getPersonalData().getMobilePhone());
                tblDataPribadi.setNamaLengkap(application.getPersonalData().getFullName());
                tblDataPribadi.setNamaKtp(application.getPersonalData().getLegalName());
                tblDataPribadi.setTanngalTerbitKtp(application.getPersonalData().getiDTypeIssuedDate());
                String strGender = application.getPersonalData().getGender();
                tblDataPribadi.setJenisKelamin(strGender);
                if (strGender.equalsIgnoreCase("M")) tblDataPribadi.setJenisKelaminPosition(0);
                if (strGender.equalsIgnoreCase("F")) tblDataPribadi.setJenisKelaminPosition(1);
                tblDataPribadi.setTempatLahir(application.getPersonalData().getBirthPlace());
                tblDataPribadi.setNamaIbuKandung(application.getPersonalData().getSurgateMotherName());

                String strEducation = application.getPersonalData().getEducation();
                tblDataPribadi.setStatusPendidikan(strEducation);
                if (strEducation.equals("D1")) tblDataPribadi.setStatusPendidikanPosition(0);
                if (strEducation.equals("D2")) tblDataPribadi.setStatusPendidikanPosition(1);
                if (strEducation.equals("D3")) tblDataPribadi.setStatusPendidikanPosition(2);
                if (strEducation.equals("S1")) tblDataPribadi.setStatusPendidikanPosition(3);
                if (strEducation.equals("S2")) tblDataPribadi.setStatusPendidikanPosition(4);
                if (strEducation.equals("S3")) tblDataPribadi.setStatusPendidikanPosition(5);
                if (strEducation.equals("SLTA")) tblDataPribadi.setStatusPendidikanPosition(6);
                if (strEducation.equals("SLTP")) tblDataPribadi.setStatusPendidikanPosition(7);
                if (strEducation.equals("SD")) tblDataPribadi.setStatusPendidikanPosition(8);
                if (strEducation.equals("TS")) tblDataPribadi.setStatusPendidikanPosition(9);

                String strHomeStatus = application.getPersonalData().getHomeStatus();
                tblDataPribadi.setStatusRumah(strHomeStatus);
                if (strHomeStatus.equalsIgnoreCase("KL")) tblDataPribadi.setStatusRumahPosition(0);
                if (strHomeStatus.equalsIgnoreCase("KP")) tblDataPribadi.setStatusRumahPosition(1);
                if (strHomeStatus.equalsIgnoreCase("KR")) tblDataPribadi.setStatusRumahPosition(2);
                if (strHomeStatus.equalsIgnoreCase("KS")) tblDataPribadi.setStatusRumahPosition(3);
                if (strHomeStatus.equalsIgnoreCase("PE")) tblDataPribadi.setStatusRumahPosition(4);
                if (strHomeStatus.equalsIgnoreCase("SD")) tblDataPribadi.setStatusRumahPosition(5);

                tblDataPribadi.setTinggalSejakTahun(String.valueOf(application.getPersonalData().getStaySinceYear()));
                tblDataPribadi.setTinggalSejakBulan(String.valueOf(application.getPersonalData().getStaySinceMonth()));

                String strReligion = application.getPersonalData().getReligion();
                tblDataPribadi.setAgama(strReligion);
                if (strReligion.equals("1")) tblDataPribadi.setAgamaPosition(0);
                if (strReligion.equals("2")) tblDataPribadi.setAgamaPosition(1);
                if (strReligion.equals("3")) tblDataPribadi.setAgamaPosition(2);
                if (strReligion.equals("4")) tblDataPribadi.setAgamaPosition(3);
                if (strReligion.equals("5")) tblDataPribadi.setAgamaPosition(4);
                if (strReligion.equals("6")) tblDataPribadi.setAgamaPosition(5);
                tblDataPribadi.setJumlahTanggungan(String.valueOf(application.getPersonalData().getNumOfDependence()));
                tblDataPribadi.setEmail(application.getPersonalData().getEmail());
                tblDataPribadi.setWargaNegara(application.getPersonalData().getNationality());
                tblDataPribadi.setWargaNegaraPosition(1);
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
                tblDataPasangan.setNamaLengkap(application.getFamilyDatas().get(0).getName());
                tblDataPasangan.setNomorKtp(application.getFamilyDatas().get(0).getiDNumber());
                tblDataPasangan.setTempatLahir(application.getFamilyDatas().get(0).getBirthPlace());
                tblDataPasangan.setTanggalLahir(application.getFamilyDatas().get(0).getBirthDate());
                tblDataPasangan.setNomorHandphone(application.getFamilyDatas().get(0).getHandphone());
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
                tblAlamat.setAlamatTinggal(application.getResidance().getAddress());
                tblAlamat.setRtTinggal(application.getResidance().getrT());
                tblAlamat.setRwTinggal(application.getResidance().getrW());
                tblAlamat.setKotaTinggal(application.getResidance().getCityName());
                tblAlamat.setKecamatanTinggal(application.getResidance().getDistrictName());
                tblAlamat.setKelurahanTinggal(application.getResidance().getVillageName());
                tblAlamat.setZipcodeTinggal(application.getResidance().getZipCode());
                tblAlamat.setKodeAreaTeleponTinggal(application.getResidance().getAreaPhone());
                tblAlamat.setNomorTeleponTinggal(application.getResidance().getPhone());

                tblAlamat.setAlamatKtp(application.getLegal().getAddress());
                tblAlamat.setRtKtp(application.getLegal().getrT());
                tblAlamat.setRwKtp(application.getLegal().getrW());
                tblAlamat.setKotaKtp(application.getLegal().getCityName());
                tblAlamat.setKecamatanKtp(application.getLegal().getDistrictName());
                tblAlamat.setKelurahanKtp(application.getLegal().getVillageName());
                tblAlamat.setZipcodeKtp(application.getLegal().getZipCode());
                tblAlamat.setKodeAreaTeleponKtp(application.getLegal().getAreaPhone());
                tblAlamat.setNomorTeleponKtp(application.getLegal().getPhone());
                try {
                    databaseService.getTblAlamatDao().create(tblAlamat);
                } catch (SQLException msgTblAlamat) {
                    if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(msgTblAlamat));
                    Crashlytics.log(String.valueOf(msgTblAlamat));
                }

//        KONTAK DARURAT
                TblKontakDarurat tblKontakDarurat = new TblKontakDarurat();
                tblKontakDarurat.setMasterFormPengajuan(masterFormPengajuan);
                tblKontakDarurat.setNamaLengkap(application.getEmergencyContact().getName());

                String strRelationship = application.getEmergencyContact().getRelationship();
                tblKontakDarurat.setHubunganKerabat(strRelationship);
                if (strRelationship.equals("Orang Tua"))
                    tblKontakDarurat.setHubunganKerabatPosition(1);
                if (strRelationship.equals("Mertua"))
                    tblKontakDarurat.setHubunganKerabatPosition(2);
                if (strRelationship.equals("Anak")) tblKontakDarurat.setHubunganKerabatPosition(3);
                if (strRelationship.equals("Adik")) tblKontakDarurat.setHubunganKerabatPosition(4);
                if (strRelationship.equals("Kakak")) tblKontakDarurat.setHubunganKerabatPosition(5);
                if (strRelationship.equals("Saudara Sepupu"))
                    tblKontakDarurat.setHubunganKerabatPosition(6);
                if (strRelationship.equals("Saudara Kandung Ayah/Ibu (Paman/Tante)"))
                    tblKontakDarurat.setHubunganKerabatPosition(7);

                tblKontakDarurat.setAlamat(application.getEmergencyContact().getAddress());
                tblKontakDarurat.setRt(application.getEmergencyContact().getrT());
                tblKontakDarurat.setRw(application.getEmergencyContact().getrW());
                tblKontakDarurat.setKota(application.getEmergencyContact().getCityName());
                tblKontakDarurat.setKecamatan(application.getEmergencyContact().getDistrictName());
                tblKontakDarurat.setKelurahan(application.getEmergencyContact().getVillageName());
                tblKontakDarurat.setZipcode(application.getEmergencyContact().getZipCode());
                tblKontakDarurat.setKodeAreaTeleponRumah(application.getEmergencyContact().getHomePhoneArea());
                tblKontakDarurat.setNomorTeleponRumah(application.getEmergencyContact().getHomePhone());
                tblKontakDarurat.setKodeAreaTeleponKantor(application.getEmergencyContact().getOfficePhoneArea());
                tblKontakDarurat.setNomorTeleponKantor(application.getEmergencyContact().getOfficePhone());
                tblKontakDarurat.setNomorHandphone(application.getEmergencyContact().getMobilePhone());
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
                tblDataPekerjaan.setNamaPerubahaan(application.getCompany().getName());
                tblDataPekerjaan.setAlamat(application.getCompany().getAddress());
                tblDataPekerjaan.setRt(application.getCompany().getrT());
                tblDataPekerjaan.setRw(application.getCompany().getrW());
                tblDataPekerjaan.setKota(application.getCompany().getCityName());
                tblDataPekerjaan.setKecamatan(application.getCompany().getDistrictName());
                tblDataPekerjaan.setKelurahan(application.getCompany().getVillageName());
                tblDataPekerjaan.setZipcode(application.getCompany().getZipCode());
                tblDataPekerjaan.setKodeAreaTelepon(application.getCompany().getAreaPhone());
                tblDataPekerjaan.setNomorTelepon(application.getCompany().getPhone());
                tblDataPekerjaan.setBekerjaSejak(String.valueOf(application.getCompany().getEmploymentSinceYear()));
                tblDataPekerjaan.setKodeProfesi(application.getCompany().getProfessionID());
                tblDataPekerjaan.setProfesi(application.getCompany().getProfessionName());
                tblDataPekerjaan.setKodeTipePekerjaan(application.getCompany().getJobTypeID());
                tblDataPekerjaan.setTipePekerjaan(application.getCompany().getJobTypeName());
                tblDataPekerjaan.setKodePosisiPekerjaan(application.getCompany().getJobPositionID());
                tblDataPekerjaan.setPosisiPekerjaan(application.getCompany().getJobPositionName());
                tblDataPekerjaan.setKodeIndustri(String.valueOf(application.getCompany().getIndustryTypeID()));
                tblDataPekerjaan.setIndustri(application.getCompany().getIndustryTypeName());
                tblDataPekerjaan.setPenghasilanTetap(String.valueOf(application.getCompany().getMonthlyFixedIncome()));
                tblDataPekerjaan.setPenghasilanLain(String.valueOf(application.getCompany().getMonthlyVariableIncome()));
                tblDataPekerjaan.setPenghasilanPasangan(String.valueOf(application.getCompany().getSpouseIncome()));
                tblDataPekerjaan.setBiayaHidup(String.valueOf(application.getCompany().getLivingCostAmount()));
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
                tblDataKartuKredit.setNamaBank(application.getDataCreditCard().getBankName());
                tblDataKartuKredit.setNoKartuKredit(application.getDataCreditCard().getiDCard());
                tblDataKartuKredit.setJenisKartuKredit(application.getDataCreditCard().getCardType());
                tblDataKartuKredit.setLimitKartuKredit(application.getDataCreditCard().getCardLimit());
                tblDataKartuKredit.setTahunKadaluarsaKartuKredit(application.getDataCreditCard().getMembershipOldMonth());
                tblDataKartuKredit.setBulanKadaluarsaKartuKredit(application.getDataCreditCard().getMembershipOldYear());
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
                tblKartuMembership.setNoMembership(application.getMembershipCard().getiDMember());
                tblKartuMembership.setTanggalEfektif(application.getMembershipCard().getEffectiveDate());
                tblKartuMembership.setTanggalExipred(application.getMembershipCard().getExpiredDate());
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
/*                tblDetailProduct.setKodeSupplier(application.getAssetMaster().getSupplierId());
                tblDetailProduct.setSupplier(application.getAssetMaster().getSupplierName());
                tblDetailProduct.setKodeMarketingSupplier(application.getAssetMaster().getSalesmanId());
                tblDetailProduct.setMarketingSupplier(application.getAssetMaster().getSalesmanName());
                tblDetailProduct.setKodeProductId((application.getDetailProduct().getProductId().equals("1") ? "" : application.getDetailProduct().getProductId()));
                tblDetailProduct.setKodeProductOfferingId(application.getDetailProduct().getProductOfferingID());
                tblDetailProduct.setProductOffering((application.getDetailProduct().getProductOfferingID().isEmpty()) ? "" : application.getDetailProduct().getProductOfferingID() + " , " + application.getDetailProduct().getProductOfferingName());
                tblDetailProduct.setPosId(application.getDetailProduct().getPostId());
                tblDetailProduct.setPos(application.getDetailProduct().getPostName());
                tblDetailProduct.setJumlahAsset(application.getDetailProduct().getNumOfAssetUnit());*/
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
                tblAsuransi.setManualAgunan(application.getInsurance().getCoverageType());
                if (application.getInsurance().getCoverageType().equals("All Risk"))
                    tblAsuransi.setManualPremi(String.valueOf("false"));
                else tblAsuransi.setManualPremi(String.valueOf("true"));
                try {
                    databaseService.getTblAsuransiDao().create(tblAsuransi);
                } catch (SQLException msgTblAsuransi) {
                    if (BuildConfig.DEBUG) Log.e("TblAsuransi", String.valueOf(msgTblAsuransi));
                    Crashlytics.log(String.valueOf(msgTblAsuransi));
                }

//        DATA PERHITUNGAN ELEKTRONIK
                TblDataPerhitungan tblDataPerhitungan = new TblDataPerhitungan();
                tblDataPerhitungan.setMasterFormPengajuan(masterFormPengajuan);
/*                tblDataPerhitungan.setTenor(application.getDetailProduct().getTenor());
                tblDataPerhitungan.setFlateRate(application.getDetailFinancing().getFlatRate());
                tblDataPerhitungan.setBiayaAdministrasi(application.getDetailFinancing().getAdminFee());
                tblDataPerhitungan.setBiayaLainnya(application.getDetailFinancing().getOtherFee());
                tblDataPerhitungan.setRefundSubsidi(application.getDetailFinancing().getSubsidyRefund());
                tblDataPerhitungan.setBebasBunga(application.getDetailFinancing().getDiscountRateTimes());
                tblDataPerhitungan.setTotalPrice(application.getDetailFinancing().getPurchasePrice());
                tblDataPerhitungan.setTotalDiscount(application.getDetailFinancing().getDiscount());
                tblDataPerhitungan.setTotalDp(application.getDetailFinancing().getDownPayment());
                tblDataPerhitungan.setPremi(application.getInsurance().getPremiumAmountToCustomer());
                tblDataPerhitungan.setNtf(application.getDetailFinancing().getNTF());
                tblDataPerhitungan.setJumlahPembiayaan(application.getDetailFinancing().getTotalFinancing());
                tblDataPerhitungan.setTotalBungaPembiayaan(application.getDetailFinancing().getInterestFinancing());
                tblDataPerhitungan.setBulanBungaPembiayaan(application.getDetailFinancing().getMonthFinancingInterest());
                tblDataPerhitungan.setTotalPinjaman(application.getDetailFinancing().getTotalLoan());
                tblDataPerhitungan.setAngsuranPrebulanBebasBunga(application.getDetailFinancing().getFirstInstallmentAmmount());
                tblDataPerhitungan.setAngsuranPerbulan(application.getDetailFinancing().getInstallmentAmount());
                tblDataPerhitungan.setPembayaranAwal(application.getDetailFinancing().getFirstPayment());
                tblDataPerhitungan.setPembayaranDelaer(application.getDetailFinancing().getInterestFreeDealerPayment());
                tblDataPerhitungan.setEffectiveRate(application.getDetailFinancing().getEffectiveRate());
                tblDataPerhitungan.setAdminFeeLainnya(String.valueOf(application.getDetailFinancing().getAdminFeeLain()));
                tblDataPerhitungan.setTipePembayaran(application.getDetailFinancing().getFirstInstallment());*/
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
                tblKeterangan.setKeterangan(application.getKeterangan());
                try {
                    databaseService.getTblKeteranganDao().create(tblKeterangan);
                } catch (SQLException msgTblKeterangan) {
                    if (BuildConfig.DEBUG) Log.e("TblKeterangan", String.valueOf(msgTblKeterangan));
                    Crashlytics.log(String.valueOf(msgTblKeterangan));
                }

//        DATA ASSET
/*                List<AssetElektronik> dbAssetElektronik = new ArrayList<>();
                List<Asset> responseAsset = application.getAssets();
                if (!application.getAssets().isEmpty()) {
                    for (int i = 0; i < responseAsset.size() - 1; i++) {

                        AssetElektronik assetElektronik = new AssetElektronik();
                        assetElektronik.setMasterFormPengajuan(masterFormPengajuan);
                        assetElektronik.setKodeBarang(responseAsset.get(i).getAssetCode());
                        assetElektronik.setNamaBarang(responseAsset.get(i).getAssetName());
                        assetElektronik.setType(responseAsset.get(i).getType());
                        assetElektronik.setPrice(responseAsset.get(i).getOtrPrice());
                        assetElektronik.setDp(responseAsset.get(i).getDpAmount());
                        assetElektronik.setDiscount(responseAsset.get(i).getDiscount());
                        dbAssetElektronik.add(assetElektronik);

                        try {
                            databaseService.getAssetDao().create(assetElektronik);
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG) Log.e("Create asset", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                    }
                }*/

//        FOTO PENGAJUAN
                /*if (!application.getAttachmentList().isEmpty()) {
                    for (int a = 0; a < application.getAttachmentList().size(); a++) {

                        final int finalA = a;
                        Glide.with(mSigninMvpView.getContext()).load(application.getAttachmentList().get(a)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                mHashMapAttachmentFiles.put(finalA, new File(Util.bitmapToFile(resource)));
                                objAttachment = new File(Util.bitmapToFile(resource));

                                Attachment attachment = new Attachment();
                                attachment.setMasterFormPengajuan(masterFormPengajuan);
                                attachment.setNamaAttachment("eform" + System.currentTimeMillis());
                                attachment.setPath(objAttachment.getAbsolutePath());
                                attachment.setPath2(objAttachment.getPath());
                                attachment.setKey(finalA);

                                try {
                                    databaseService.getAttachmentDao().create(attachment);
                                } catch (SQLException e) {
                                    if (BuildConfig.DEBUG)
                                        Log.e("Create attachment", String.valueOf(e));
                                    Crashlytics.logException(e);
                                }
                            }
                        });
                    }
                }*/

////        TANDA TANGAN
                if (!application.getSignature().getApplicant().isEmpty()) {
                    TblTandaTangan tblTandaTangan = new TblTandaTangan();
                    tblTandaTangan.setMasterFormPengajuan(masterFormPengajuan);
                    /*tblTandaTangan.setTtdPemohon(application.getSignature().getApplicant());
                    tblTandaTangan.setTtdPasangan(application.getSignature().getApplicantHusbandWife());
                    tblTandaTangan.setJumlahTtd(0);*/
                    try {
                        databaseService.getTblTandaTanganDao().create(tblTandaTangan);
                    } catch (SQLException msgTblTandaTangan) {
                        if (BuildConfig.DEBUG)
                            Log.e("TblTandaTangan", String.valueOf(msgTblTandaTangan));
                        Crashlytics.logException(msgTblTandaTangan);
                    }
                }

//        REKOMENDASI
                TblRekomendasi tblRekomendasi = new TblRekomendasi();
                tblRekomendasi.setMasterFormPengajuan(masterFormPengajuan);
                tblRekomendasi.setJabar(0);
                tblRekomendasi.setIdRekomendasi(application.getReasonRecomendationId());
                tblRekomendasi.setCatatan(application.getReasonRecomendationNotes());
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
                tblLokasi.setValidationAction("");
                tblLokasi.setValidationLongitude(application.getLocation().getValidateLongitude());
                tblLokasi.setValidationLatitude(application.getLocation().getValidateLatitude());
                tblLokasi.setKtpAction("");
                tblLokasi.setKtpLongitude(application.getLocation().getTakeKtpLongitude());
                tblLokasi.setKtpLatitude(application.getLocation().getTakeKtpLatitude());
                tblLokasi.setCustomerAction("");
                tblLokasi.setCustomerLongitude(application.getLocation().getTakePaycheckLongitude());
                tblLokasi.setCustomerLatitude(application.getLocation().getTakePaycheckLatitude());
                tblLokasi.setPaycheckAction("");
                tblLokasi.setPaycheckLongitude(application.getLocation().getTakePaycheckLongitude());
                tblLokasi.setPaycheckLatitude(application.getLocation().getTakePaycheckLongitude());
                tblLokasi.setAddtionalDocumentsAction("");
                tblLokasi.setAddtionalDocumentsLongitude(application.getLocation().getTakeAdditionalDocumentsLongitude());
                tblLokasi.setAddtionalDocumentsLatitude(application.getLocation().getTakeAdditionalDocumentsLatitude());
                tblLokasi.setSignatureAction("");
                tblLokasi.setSignatureLongitude(application.getLocation().getTakeSignatureLongitude());
                tblLokasi.setSignatureLatitude(application.getLocation().getTakeSignatureLatitude());
                tblLokasi.setSubmitAction("");
                tblLokasi.setSubmitLongitude(application.getLocation().getSubmitLongitude());
                tblLokasi.setSubmitLatitude(application.getLocation().getSubmitLatitude());
                tblLokasi.setSyncAction("");
                tblLokasi.setSyncLongitude(application.getLocation().getSyncLongitude());
                tblLokasi.setSyncLatitude(application.getLocation().getSyncLatitude());
                try {
                    databaseService.getTblLokasiDao().create(tblLokasi);
                } catch (SQLException msgTblLokasi) {
                    if (BuildConfig.DEBUG) Log.e("TblLokasi", String.valueOf(msgTblLokasi));
                    Crashlytics.log(String.valueOf(msgTblLokasi));
                }
            }
        }
    }
}
