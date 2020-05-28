package com.kreditplus.eform.presenter;


import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetKendaraan;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.SendSaveDraftMvpview;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by apc-lap012 on 07/02/17.
 */

public class SendSaveDraftPresenter implements BasePresenter<SendSaveDraftMvpview> {

    @Inject
    ApiService apiService;
    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;
    @Inject
    DatabaseService databaseService;

    private SendSaveDraftMvpview msSendSaveDraftMvpView;
    private Call<BaseResponse<Object>> callSave;
    private List<PengajuanBaru> pengajuanBaruList;
    private String convertDate;
    private int count;
    private String premiManualString;
    private File compres;

    public SendSaveDraftPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SendSaveDraftMvpview view) {
        msSendSaveDraftMvpView = view;
    }

    @Override
    public void detachView() {
        if (callSave != null) callSave.cancel();
    }


    public void sendDraft(String token, List<PengajuanBaru> pengajuanBaruList) {
        this.pengajuanBaruList = pengajuanBaruList;
        msSendSaveDraftMvpView.onPreSubmitSaveDraft();
        count = 0;
        Map<String, RequestBody> map = new HashMap<>();
        List<MultipartBody.Part> multiparts = null;


        if (pengajuanBaruList.isEmpty()) {
            map.put("", Util.toTextRequestBodyNull());
        }

        for (int i = 0; i < pengajuanBaruList.size(); i++) {

            // Locations
            map.put(i + "[Location][ValidateAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getValidateAction14()));
            map.put(i + "[Location][ValidateLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getValidateLongitude14()));
            map.put(i + "[Location][ValidateLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getValidateLatitude14()));
            map.put(i + "[Location][TakeKtpAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeKtpAction14()));
            map.put(i + "[Location][TakeKtpLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeKtpLongitude14()));
            map.put(i + "[Location][TakeKtpLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeKtpLatitude14()));
            map.put(i + "[Location][TakeCustomerAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeCustomerAction14()));
            map.put(i + "[Location][TakeCustomerLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeCustomerLongitude14()));
            map.put(i + "[Location][TakeCustomerLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeCustomerLatitude14()));
            map.put(i + "[Location][TakePaycheckAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakePaycheckAction14()));
            map.put(i + "[Location][TakePaycheckLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakePaychecklongitude14()));
            map.put(i + "[Location][TakePaycheckLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakePaychecklatitude14()));
            map.put(i + "[Location][TakeAdditionalDocumentsAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeAdditionalDocumentsAction14()));
            map.put(i + "[Location][TakeAdditionalDocumentsLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeAdditionalDocumentsLongitude14()));
            map.put(i + "[Location][TakeAdditionalDocumentsLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeAdditionalDocumentsLatitude14()));
            map.put(i + "[Location][TakeSignatureAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeSignatureAction14()));
            map.put(i + "[Location][TakeSignatureLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeSignatureLongitude14()));
            map.put(i + "[Location][TakeSignatureLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTakeSignatureLatitude14()));
            map.put(i + "[Location][SubmitAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSubmitAction14()));
            map.put(i + "[Location][SubmitLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSubmitLongitude14()));
            map.put(i + "[Location][SubmitLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSubmitLatitude14()));
            map.put(i + "[Location][SyncAction]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSyncAction14()));
            map.put(i + "[Location][SyncLongitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSyncLongitude14()));
            map.put(i + "[Location][SyncLatitude]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSyncLatitude14()));

            // ApplicationLocation
            map.put(i + "[Application][EFNumber]", Util.toTextRequestBody(pengajuanBaruList.get(i).getEfNumber() == null ? "" : pengajuanBaruList.get(i).getEfNumber()));
            map.put(i + "[Application][DataType]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPengajuanType() == null ? "" : pengajuanBaruList.get(i).getPengajuanType()));
            map.put(i + "[Application][OfferingType]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTypeDataOffering() == null ? "" : pengajuanBaruList.get(i).getTypeDataOffering()));
            map.put(i + "[Application][IsJabarRecomendation]", Util.toTextRequestBody(String.valueOf(pengajuanBaruList.get(i).getIsJabar13())));
            map.put(i + "[Application][ReasonRecomendationId]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRecomendation13() == null ? "" : pengajuanBaruList.get(i).getRecomendation13()));
            map.put(i + "[Application][ReasonRecomendationNotes]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRecomendationNote13() == null ? "" : pengajuanBaruList.get(i).getRecomendationNote13()));
            //KOP
            map.put(i + "[KorpFormulir][SalesMethod]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMetodePenjualan00()));
            map.put(i + "[KorpFormulir][FinancingPurpose]", Util.toTextRequestBody(pengajuanBaruList.get(i).getStatusPemohon00()));
            map.put(i + "[KorpFormulir][BranchIdPrimary]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMasterBranch()));
            map.put(i + "[KorpFormulir][BranchIdLintasCabang]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBranch()));
            map.put(i + "[KorpFormulir][ApplicationIdKPM]", Util.toTextRequestBody(pengajuanBaruList.get(i).getIdKpm()));
            map.put(i + "[KorpFormulir][DateStart]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBlacklist_date()));
            map.put(i + "[KorpFormulir][PID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getUuid() == null ? "" : pengajuanBaruList.get(i).getUuid()));
            map.put(i + "[KorpFormulir][CustomerIdConfins]", Util.toTextRequestBody(pengajuanBaruList.get(i).getUuid() == null ? "" : pengajuanBaruList.get(i).getCustomerId()));
            //Keterangan
            map.put(i + "[KorpFormulir][Keterangan]", Util.toTextRequestBody(pengajuanBaruList.get(i).getKeterangan()));
            //date created
            if (pengajuanBaruList.get(i).getCreatedAt() == null) { // convert date
                convertDate = Util.ConvertDate(Util.listPengajuanTimeFormat(new DateTime()));

            } else {
                convertDate = Util.ConvertDate(pengajuanBaruList.get(i).getCreatedAt());
            }
            map.put(i + "[KorpFormulir][created_at]", Util.toTextRequestBody(convertDate));
            //isEdit
            if ("isAssignEdit".equalsIgnoreCase(pengajuanBaruList.get(i).getIsAssignedit())) {
                map.put(i + "[UserApplication][isEdit]", Util.toTextRequestBody("1"));
            } else {
                map.put(i + "[UserApplication][isEdit]", Util.toTextRequestBody("0"));
            }

            // Data Pribadi
            map.put(i + "[PersonalData][FullName]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNamaLengkap0()));
            map.put(i + "[PersonalData][LegalName]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNamaKtp0()));
            map.put(i + "[PersonalData][IDNumber]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNoKtp0()));
            map.put(i + "[PersonalData][IDTypeIssuedDate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTanggalKtp0()));
            map.put(i + "[PersonalData][BirthPlace]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTempatLahir0()));
            map.put(i + "[PersonalData][BirthDate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTanggalLahir0()));
            map.put(i + "[PersonalData][SurgateMotherName]", Util.toTextRequestBody(pengajuanBaruList.get(i).getIbuKandung0()));
            map.put(i + "[PersonalData][Gender]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJenisKelamin0()));
//            map.put(i+"[PersonalData][GenderPosition]", Util.toTextRequestBody(String.valueOf(pengajuanBaruList.get(i).getJenisKelaminPosition0())));
            map.put(i + "[PersonalData][MaritalStatus]", Util.toTextRequestBody(pengajuanBaruList.get(i).getStatusNikah0()));
            map.put(i + "[PersonalData][NumOfDependence]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJumlahTanggungan0()));
            map.put(i + "[PersonalData][HomeStatus]", Util.toTextRequestBody(pengajuanBaruList.get(i).getStatusRumah0()));
//            map.put(i+"[PersonalData][HomeStatusPosition]", Util.toTextRequestBody(String.valueOf(pengajuanBaruList.get(i).getStatusRumahPosition0())));
            map.put(i + "[PersonalData][StaySinceYear]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTinggalSejakTahun0()));
            map.put(i + "[PersonalData][StaySinceMonth]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTinggalSejakBulan0()));
            map.put(i + "[PersonalData][Education]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPendidikan0()));
            map.put(i + "[PersonalData][Nationality]", Util.toTextRequestBody(pengajuanBaruList.get(i).getWargaNegara0()));
//            map.put(i+"[PersonalData][NationalityPosition]", Util.toTextRequestBody(String.valueOf(pengajuanBaruList.get(i).getWargaNegaraPosition0())));
            map.put(i + "[PersonalData][Religion]", Util.toTextRequestBody(pengajuanBaruList.get(i).getAgama0()));
            map.put(i + "[PersonalData][MobilePhone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMobilePhone0()));
            map.put(i + "[PersonalData][Email]", Util.toTextRequestBody(pengajuanBaruList.get(i).getEmail0()));
            // Data Pasangan Suami/Istri
            map.put(i + "[FamilyData][0][SeqNo]", Util.toTextRequestBody("1"));
            map.put(i + "[FamilyData][0][Id]", Util.toTextRequestBody(pengajuanBaruList.get(i).getIdPasangan8()));
            map.put(i + "[FamilyData][0][Name]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNamaPasangan8()));
            map.put(i + "[FamilyData][0][IDNumber]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNoKtpPasangan8()));
            map.put(i + "[FamilyData][0][BirthPlace]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTempatLahirPasangan8()));
            map.put(i + "[FamilyData][0][BirthDate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTanggalLahirPasangan8()));
            map.put(i + "[FamilyData][0][Handphone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getHandphonePasangan8()));
            // Alamat Tinggal
            String alamatTinggalString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAlamatTinggal1()) ? " " : pengajuanBaruList.get(i).getAlamatTinggal1();
            String autoAlmatTinggalString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAutoAlamatTinggal1()) ? " " : pengajuanBaruList.get(i).getAutoAlamatTinggal1();

            map.put(i + "[Residance][Address]", Util.toTextRequestBody(alamatTinggalString + "|" + autoAlmatTinggalString));
            map.put(i + "[Residance][RT]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRtTinggal1()));
            map.put(i + "[Residance][RW]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRwTinggal1()));
            map.put(i + "[Residance][VillageCode]", Util.toTextRequestBody(pengajuanBaruList.get(i).getKelurahanKodeTinggal1()));
            map.put(i + "[Residance][AreaPhone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getAreaPhoneTinggal1()));
            map.put(i + "[Residance][Phone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPhoneTinggal1()));
            // Alamat KTP
            String alamatKtpString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAlamatKtp1()) ? " " : pengajuanBaruList.get(i).getAlamatKtp1();
            String autoAlmatKtpString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAutoAlamatKtp1()) ? " " : pengajuanBaruList.get(i).getAutoAlamatKtp1();

            map.put(i + "[Legal][Address]", Util.toTextRequestBody(alamatKtpString + "|" + autoAlmatKtpString));
            map.put(i + "[Legal][RT]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRtKtp1()));
            map.put(i + "[Legal][RW]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRwKtp1()));
            map.put(i + "[Legal][VillageCode]", Util.toTextRequestBody(pengajuanBaruList.get(i).getKelurahanKodeKtp1()));
            map.put(i + "[Legal][AreaPhone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getAreaPhoneKtp1()));
            map.put(i + "[Legal][Phone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPhoneKtp1()));
            // Informasi Kerabat
            String alamatEconString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAlamat2()) ? " " : pengajuanBaruList.get(i).getAlamat2();
            String autoAlmatEconString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAuto_alamat2()) ? " " : pengajuanBaruList.get(i).getAuto_alamat2();

            map.put(i + "[EmergencyContact][Name]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNamaLengkap2()));
            map.put(i + "[EmergencyContact][Relationship]", Util.toTextRequestBody(pengajuanBaruList.get(i).getHubunganKerabat2()));
            map.put(i + "[EmergencyContact][Address]", Util.toTextRequestBody(alamatEconString + "|" + autoAlmatEconString));
            map.put(i + "[EmergencyContact][RT]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRt2()));
            map.put(i + "[EmergencyContact][RW]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRw2()));
            map.put(i + "[EmergencyContact][VillageCode]", Util.toTextRequestBody(pengajuanBaruList.get(i).getKelurahanKode2()));
            map.put(i + "[EmergencyContact][HomePhoneArea]", Util.toTextRequestBody(pengajuanBaruList.get(i).getAreaPhoneRumah2()));
            map.put(i + "[EmergencyContact][HomePhone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPhoneRumah2()));
            map.put(i + "[EmergencyContact][OfficePhoneArea]", Util.toTextRequestBody(pengajuanBaruList.get(i).getAreaPhoneKantor2()));
            map.put(i + "[EmergencyContact][OfficePhone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPhoneKantor2()));
            map.put(i + "[EmergencyContact][MobilePhone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMobilePhone2()));
            // Data Perusahaan
            String alamatCompanyString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAlamat3()) ? " " : pengajuanBaruList.get(i).getAlamat3();
            String autoAlmatCompanyString = "".equalsIgnoreCase(pengajuanBaruList.get(i).getAuto_alamat3()) ? " " : pengajuanBaruList.get(i).getAuto_alamat3();

            map.put(i + "[Company][ProfessionID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getProfesiKode3()));
            map.put(i + "[Company][PersonalCustomerType]", Util.toTextRequestBody(pengajuanBaruList.get(i).getCustomerType3()));
            map.put(i + "[Company][Name]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNamaPerusahaan3()));
            map.put(i + "[Company][Address]", Util.toTextRequestBody(alamatCompanyString + "|" + autoAlmatCompanyString));
            map.put(i + "[Company][RT]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRt3()));
            map.put(i + "[Company][RW]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRw3()));
            map.put(i + "[Company][VillageCode]", Util.toTextRequestBody(pengajuanBaruList.get(i).getKelurahanKode3()));
            map.put(i + "[Company][AreaPhone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getAreaPhone3()));
            map.put(i + "[Company][Phone]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPhone3()));
            map.put(i + "[Company][MonthlyFixedIncome]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPenghasilanTetap3()));
            map.put(i + "[Company][MonthlyVariableIncome]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPenghasilanLain3()));
            map.put(i + "[Company][LivingCostAmount]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBiayaHidup3()));
            map.put(i + "[Company][JobPosition]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJobPositionKode3()));
            map.put(i + "[Company][JobType]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJobTypeKode3()));
            map.put(i + "[Company][IndustryTypeID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getIndustriKode3()));
            map.put(i + "[Company][SpouseIncome]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPenghasilanPasangan3()));
            map.put(i + "[Company][EmploymentSinceYear]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTahunKerja3()));
            // Sementara di-hide dulu
//        map.put("Company[Counterpart]", Util.toTextRequestBody(pengajuanBaru.getCounterPart3()));
//        map.put("Company[DebtBusinessScale]", Util.toTextRequestBody(pengajuanBaru.getDebtBusinessScale3()));
//        map.put("Company[DebtGroup]", Util.toTextRequestBody(pengajuanBaru.getDebtGroup3()));
            map.put(i + "[Company][IsAffiliateWithPP]", Util.toTextRequestBody("yes".equalsIgnoreCase(pengajuanBaruList.get(i).getIsAffiliateWithPp3()
            ) ? "1" : "0"));
            // Data kartu kredit
            map.put(i + "[DataCreditCard][BankName]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNamaBank9()));
            map.put(i + "[DataCreditCard][IDCard]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNoKartuKredit9()));
            map.put(i + "[DataCreditCard][CardType]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJenisKartuKredit9()));
            map.put(i + "[DataCreditCard][CardLimit]", Util.toTextRequestBody(pengajuanBaruList.get(i).getLimitKartuKredit9()));
            map.put(i + "[DataCreditCard][MembershipOldMonth]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMonthExpiredCard9()));
            map.put(i + "[DataCreditCard][MembershipOldYear]", Util.toTextRequestBody(pengajuanBaruList.get(i).getYearExpiredCard9()));
            // Data kartu membership
            map.put(i + "[MembershipCard][IDMember]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNoMembership10()));
            map.put(i + "[MembershipCard][EffectiveDate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTanggalEfektif10()));
            map.put(i + "[MembershipCard][ExpiredDate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTanggalExpired10()));
            // Detail Product
            map.put(i + "[DetailProduct][ProductID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getProductOfferingKode4() == null ? "1" : pengajuanBaruList.get(i).getProductOfferingKode4()));
            map.put(i + "[DetailProduct][ProductOfferingID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getProductOfferingKode24() == null ? "0" : pengajuanBaruList.get(i).getProductOfferingKode24()));
            map.put(i + "[DetailProduct][NumOfAssetUnit]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJumlahAsset4()));
            map.put(i + "[DetailProduct][POS]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPosKode4()));

            // Data Asuransi
            map.put(i + "[Insurance][CoverageType]", Util.toTextRequestBody(pengajuanBaruList.get(i).getCoverageAsurani5()));

            if (pengajuanBaruList.get(i).isPremiManual()) {
                premiManualString = "1";
            } else {
                premiManualString = "0";
            }
            map.put(i + "[Insurance][IsPremiManual]", Util.toTextRequestBody(premiManualString));
//            map.put(i + "[Insurance][IsPersonalAccident]", Util.toTextRequestBody
//                    (pengajuanBaruList.get(i).getIsPersonalAccident5().equalsIgnoreCase("yes") ? "1" : "0"));
            // Data Perhitungan
            if ("E".equalsIgnoreCase(pengajuanBaruList.get(i).getPengajuanType())) {
                map.put(i + "[DetailProduct][Tenor]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTenor6()));
                map.put(i + "[DetailFinancing][FlatRate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getFlatRate6()));
                map.put(i + "[DetailFinancing][AdminFee]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBiayaAdmin6()));
//                map.put(i + "[DetailFinancing][TypePayment]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTipePembayaran()));
                map.put(i + "[DetailFinancing][OtherFee]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBiayaLainnya6()));
                map.put(i + "[DetailFinancing][SubsidyRefund]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRefundSubsidi6()));
                map.put(i + "[DetailFinancing][DiscountRateTimes]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBebasBungaPerhitungan6()));
                map.put(i + "[DetailFinancing][PurchasePrice]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPurchasePrice6()));
                map.put(i + "[DetailFinancing][Discount]", Util.toTextRequestBody(pengajuanBaruList.get(i).getDiscount6()));
                map.put(i + "[DetailFinancing][DownPayment]", Util.toTextRequestBody(pengajuanBaruList.get(i).getDownPayment6()));
                map.put(i + "[Insurance][PremiumAmountToCustomer]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPremiAsuransi6()));
                map.put(i + "[DetailFinancing][NTF]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNtf6()));
                map.put(i + "[DetailFinancing][TotalFinancing]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJumlahPembiayaan6()));
                map.put(i + "[DetailFinancing][InterestFinancing]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBungaTotalPembiayaan6()));
                map.put(i + "[DetailFinancing][MonthFinancingInterest]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBungaPembiayaanPerBulan6()));
                map.put(i + "[DetailFinancing][TotalLoan]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTotalPinjaman6()));
                map.put(i + "[DetailFinancing][FirstInstallmentAmmount]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSetorPertama6()));
                map.put(i + "[DetailFinancing][InstallmentAmount]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSetorPerbulan6()));
                map.put(i + "[DetailFinancing][FirstPayment]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPembayaranAwal6()));
                map.put(i + "[DetailFinancing][InterestFreeDealerPayment]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPembayaranDealer6()));
                map.put(i + "[DetailFinancing][FirstInstallment]", Util.toTextRequestBody(pengajuanBaruList.get(i).getFirstInstalment()));
                map.put(i + "[DetailFinancing][EffectiveRate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getEffectiveRate6()));
            } else {
                map.put(i + "[Insurance][PremiAsuransiAgunan]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPremiASuransiAgunan12()));
                map.put(i + "[Insurance][PremiAsuransiJiwa]", Util.toTextRequestBody(pengajuanBaruList.get(i).getPremiASuransiJiwa12()));
                map.put(i + "[DetailProduct][Tenor]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTenor12()));
                map.put(i + "[DetailFinancing][NTF]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNilaiPembiayaan12()));
                map.put(i + "[DetailFinancing][FirstInstallment]", Util.toTextRequestBody(pengajuanBaruList.get(i).getFirstInstalment()));
                map.put(i + "[DetailFinancing][AdminFee]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBiaya_administrasi12()));
                map.put(i + "[DetailFinancing][InstallmentAmount]", Util.toTextRequestBody(pengajuanBaruList.get(i).getAngsuranPerbulan12()));
                map.put(i + "[DetailFinancing][SubsidyRefund]", Util.toTextRequestBody(pengajuanBaruList.get(i).getRefundSubsidi12()));
                map.put(i + "[DetailFinancing][OtherFee]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBiayaLainnya12()));
                map.put(i + "[DetailFinancing][OtherFee2]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBiayaLainnya212()));
                map.put(i + "[DetailFinancing][TotalFinancing]", Util.toTextRequestBody(pengajuanBaruList.get(i).getJumlahPembiayaan12()));
                map.put(i + "[DetailFinancing][InterestFinancing]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBungaPembiayaan12()));
                map.put(i + "[DetailFinancing][TotalLoan]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTotalPinjaman12()));
                map.put(i + "[DetailFinancing][FirstInstallmentAmmount]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTotalSetorPertama12()));
                map.put(i + "[DetailFinancing][EffectiveRate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBungaFlatTahun12()));
                map.put(i + "[DetailFinancing][MonthlyEffectiveRate]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBungaFlatBulan12()));
                map.put(i + "[DetailFinancing][OTR]", Util.toTextRequestBody(pengajuanBaruList.get(i).getHargaKendaraan()));
                map.put(i + "[DetailFinancing][DownPayment]", Util.toTextRequestBody(pengajuanBaruList.get(i).getDpCash12()));
                map.put(i + "[DetailFinancing][CostOfObtainingAFiduciary]", Util.toTextRequestBody(pengajuanBaruList.get(i).getFidusia12()));
                map.put(i + "[DetailFinancing][CostOfObtainingSTNK]", Util.toTextRequestBody(pengajuanBaruList.get(i).getStnk12()));
                map.put(i + "[DetailFinancing][AdminFeeLain]", Util.toTextRequestBody(String.valueOf(pengajuanBaruList.get(i).getAdminFeeLain())));
            }

            // DetailAsset
            map.put(i + "[AssetMaster][SupplierID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getSupplierKode()));
            map.put(i + "[AssetMaster][SupplierBankAccountID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBankAccountKode()));
            map.put(i + "[AssetMaster][SalesmanID]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMarketingSupplierKode()));

            // Data Agunan
            if (!"E".equalsIgnoreCase(pengajuanBaruList.get(i).getPengajuanType())) {
                if ("MBL".equalsIgnoreCase(pengajuanBaruList.get(i).getTypeDataOffering()) ||
                        "MBLBKS".equalsIgnoreCase(pengajuanBaruList.get(i).getTypeDataOffering())) {
                    map.put(i + "[DataAgunan][MotorVehicle]", Util.toTextRequestBody("RE"));
                } else if ("MTR".equalsIgnoreCase(pengajuanBaruList.get(i).getTypeDataOffering())) {
                    map.put(i + "[DataAgunan][MotorVehicle]", Util.toTextRequestBody("RD"));
                } else {
                    map.put(i + "[DataAgunan][MotorVehicle]", Util.toTextRequestBody(""));
                }
                map.put(i + "[DataAgunan][Merk]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMerkKb11()));
                map.put(i + "[DataAgunan][Type]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTypeKb11()));
                map.put(i + "[DataAgunan][Year]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTahunKb11()));
                map.put(i + "[DataAgunan][Colour]", Util.toTextRequestBody(pengajuanBaruList.get(i).getWarnaKb11()));
                map.put(i + "[DataAgunan][CC]", Util.toTextRequestBody(pengajuanBaruList.get(i).getIsiSilinderKb11()));
                map.put(i + "[DataAgunan][NoPolisi]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNoPolisiKb11()));
                map.put(i + "[DataAgunan][NoRangka]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNoRangkaKb11()));
                map.put(i + "[DataAgunan][NoMesin]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNoMesinKb11()));
                map.put(i + "[DataAgunan][BPKBName]", Util.toTextRequestBody(pengajuanBaruList.get(i).getBpkbKb11()));
                map.put(i + "[DataAgunan][CollateralValue]", Util.toTextRequestBody(pengajuanBaruList.get(i).getNilaiAgunan12()));
            }
            map.put(i + "[mobile_submission_key]", Util.toTextRequestBody(pengajuanBaruList.get(i).getMobileSubmissionKey()));

            // Signature
            map.put(i + "[Signature][Applicant]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTtdPemohon7()));
            map.put(i + "[Signature][ApplicantHusbandWife]", Util.toTextRequestBody(pengajuanBaruList.get(i).getTtdPasangan7()));

            //Asset
            if ("E".equalsIgnoreCase(pengajuanBaruList.get(i).getPengajuanType())) {
                // AssetElektronik
                List<AssetElektronik> assetElektronikList = new ArrayList<>();
                try {
                    assetElektronikList = databaseService.getAssetDao().queryBuilder().where().eq("pengajuan_id", pengajuanBaruList.get(i)).query();
                } catch (SQLException e) {
                    if (BuildConfig.DEBUG)Log.e("Query asset", String.valueOf(e));
                    Crashlytics.logException(e);
                }

                for (int j = 0; j < assetElektronikList.size(); j++) {
                    map.put(i + "[Asset][" + j + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(j + 1)));
                    map.put(i + "[Asset][" + j + "][AssetCode]", Util.toTextRequestBody(assetElektronikList.get(j).getKodeBarang()));
                    map.put(i + "[Asset][" + j + "][Type]", Util.toTextRequestBody(assetElektronikList.get(j).getType()));
                    map.put(i + "[Asset][" + j + "][OTRPrice]", Util.toTextRequestBody(assetElektronikList.get(j).getPrice()));
                    map.put(i + "[Asset][" + j + "][DPAmount]", Util.toTextRequestBody(assetElektronikList.get(j).getDp()));
                    map.put(i + "[Asset][" + j + "][Discount]", Util.toTextRequestBody(assetElektronikList.get(j).getDiscount()));
                }
            } else {
                // AssetKendaraan
                List<AssetKendaraan> assetKendaraanList = new ArrayList<>();
                try {
                    assetKendaraanList = databaseService.getAssetKendaraanDao().queryBuilder().where()
                            .eq("pengajuan_id", pengajuanBaruList.get(i)).query();
                } catch (SQLException e) {
                    if (BuildConfig.DEBUG)
                        Log.e("pengajuan", String.valueOf(e));
                    Crashlytics.logException(e);
                }

                for (int j = 0; j < assetKendaraanList.size(); j++) {
                    map.put(i + "[Asset][" + j + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(j + 1)));
                    map.put(i + "[Asset][" + j + "][AssetCode]", Util.toTextRequestBody(assetKendaraanList.get(j).getKodeKendaraan()));
                    map.put(i + "[Asset][" + j + "][Merk]", Util.toTextRequestBody(assetKendaraanList.get(j).getMerkKendaraan()));
                    map.put(i + "[Asset][" + j + "][TypeAsset]", Util.toTextRequestBody(assetKendaraanList.get(j).getType()));
                    map.put(i + "[Asset][" + j + "][Type]", Util.toTextRequestBody(assetKendaraanList.get(j).getType()));
                    map.put(i + "[Asset][" + j + "][ManufacturingYear]", Util.toTextRequestBody(assetKendaraanList.get(j).getTahun()));
                    map.put(i + "[Asset][" + j + "][Color]", Util.toTextRequestBody(assetKendaraanList.get(j).getWarna()));
                    map.put(i + "[Asset][" + j + "][UsedNew]", Util.toTextRequestBody(assetKendaraanList.get(j).getKondisi()));
                    map.put(i + "[Asset][" + j + "][Cylinder]", Util.toTextRequestBody(assetKendaraanList.get(j).getIsiSilinder()));
                    map.put(i + "[Asset][" + j + "][PoliceNo]", Util.toTextRequestBody(assetKendaraanList.get(j).getNoPolisi()));
                    map.put(i + "[Asset][" + j + "][FrameNo]", Util.toTextRequestBody(assetKendaraanList.get(j).getNoRangka()));
                    map.put(i + "[Asset][" + j + "][MachineNo]", Util.toTextRequestBody(assetKendaraanList.get(j).getNoMesin()));
                }
            }

            //Attachment

            List<Attachment> attachmentList = new ArrayList<>();
            try {
                attachmentList = databaseService.getAttachmentDao().queryBuilder().where().eq("pengajuan_id", pengajuanBaruList.get(i)).query();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG)
                    Log.e("Activity not found", String.valueOf(e));
                Crashlytics.logException(e);
            }

            multiparts = new ArrayList<>();
            if (!attachmentList.isEmpty()) {
                for (int k = 0; k < attachmentList.size(); k++) {
                    File fileTmp1 = FileUtils.getFile(attachmentList.get(k).getPath());
                    File fileTmp2 = FileUtils.getFile(attachmentList.get(k).getPath2());
//                map.put(i + "[Attachment][" + k + "][AttacmentName]", Util.toT extRequestBody(attachmentList.get(k).getNamaAttachment()));

                    if (fileTmp1 != null || fileTmp2 != null) {
                        try {
                            compres = new Compressor(msSendSaveDraftMvpView.getContext())
                                    .setMaxHeight(720)
                                    .setMaxWidth(1080)
                                    .setQuality(25)
                                    .compressToFile(fileTmp1 == null ? fileTmp2 : fileTmp1);
                        } catch (IOException e) {
                            if (BuildConfig.DEBUG)
                                Log.e("Gagal Kompress", e.getMessage());
                            Crashlytics.logException(e);
                            msSendSaveDraftMvpView.onFailedSubmitSaveDraft(msSendSaveDraftMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
                            return;
                        }
                    } else {
                        msSendSaveDraftMvpView.onFailedSubmitSaveDraft(msSendSaveDraftMvpView.getContext().getResources().getString(R.string.warning_no_attachment)
                                + " " + pengajuanBaruList.get(i).getNamaLengkap0());
                        return;
                    }
                    multiparts.add(prepareFilePart(i + "[Attachment][" + k + "]", compres));
                }
            }

        }

        callSave = apiService.addSaveDraft(token, map, multiparts);
        callSave.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    msSendSaveDraftMvpView.onSuccessSubmitSaveDraft();
                } else if (response.code() == 204) {
                    msSendSaveDraftMvpView.onSuccessSubmitSaveDraft();
                } else if (response.code() == 403) {
                    msSendSaveDraftMvpView.onTokenPengajuanExpired();
                } else {
                    msSendSaveDraftMvpView.onFailedSubmitSaveDraft(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (!callSave.isCanceled()) {
                    if (count < 3) {
                        callSave.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (msSendSaveDraftMvpView != null) {
                            msSendSaveDraftMvpView.onFailedSubmitSaveDraft(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
                            Crashlytics.logException(t);
                        }
                    }
                }

            }
        });

    }


    @Nonnull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        RequestBody requestFile = Util.toImageRequestBody(file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);

    }


}
