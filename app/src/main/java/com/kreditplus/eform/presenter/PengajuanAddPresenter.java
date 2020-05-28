package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetKendaraan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.response.AddApplicationResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.PengajuanAddMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class PengajuanAddPresenter implements BasePresenter<PengajuanAddMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private PengajuanAddMvpView mPengajuanAddMvpView;
    private Call<BaseResponse<AddApplicationResponse>> callNew;
    private Call<BaseResponse<AddApplicationResponse>> callEdit;
    private Call<BaseResponse<AddApplicationResponse>> callDraft;
    private PengajuanBaru pengajuanBaru;
    private String premiManualString;

    public PengajuanAddPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(PengajuanAddMvpView view) {
        mPengajuanAddMvpView = view;
    }

    @Override
    public void detachView() {
        if (callNew != null) callNew.cancel();
        if (callEdit != null) callEdit.cancel();
        if (callDraft != null) callDraft.cancel();
        mPengajuanAddMvpView = null;
    }

    public void submitPengajuan(String token, final PengajuanBaru pengajuanBaru) {
        this.pengajuanBaru = pengajuanBaru;
        mPengajuanAddMvpView.onPreSubmitPengajuan();
        Map<String, RequestBody> map = new HashMap<>();


        // Application
        map.put("Application[EFNumber]", Util.toTextRequestBody(pengajuanBaru.getEfNumber()));
        map.put("Application[DataType]", Util.toTextRequestBody(pengajuanBaru.getPengajuanType()));
        map.put("Application[OfferingType]", Util.toTextRequestBody(pengajuanBaru.getTypeDataOffering()));
        map.put("Application[IsJabarRecomendation]", Util.toTextRequestBody(String.valueOf(pengajuanBaru.getIsJabar13())));
        map.put("Application[ReasonRecomendationId]", Util.toTextRequestBody(pengajuanBaru.getRecomendation13()));
        map.put("Application[ReasonRecomendationNotes]", Util.toTextRequestBody(pengajuanBaru.getRecomendationNote13()));

        // Coordinate Locations
        map.put("Location[ValidateAction]", Util.toTextRequestBody(pengajuanBaru.getValidateAction14()));
        map.put("Location[ValidateLongitude]", Util.toTextRequestBody(pengajuanBaru.getValidateLongitude14()));
        map.put("Location[ValidateLatitude]", Util.toTextRequestBody(pengajuanBaru.getValidateLatitude14()));
        map.put("Location[TakeKtpAction]", Util.toTextRequestBody(pengajuanBaru.getTakeKtpAction14()));
        map.put("Location[TakeKtpLongitude]", Util.toTextRequestBody(pengajuanBaru.getTakeKtpLongitude14()));
        map.put("Location[TakeKtpLatitude]", Util.toTextRequestBody(pengajuanBaru.getTakeKtpLatitude14()));
        map.put("Location[TakeCustomerAction]", Util.toTextRequestBody(pengajuanBaru.getTakeCustomerAction14()));
        map.put("Location[TakeCustomerLongitude]", Util.toTextRequestBody(pengajuanBaru.getTakeCustomerLongitude14()));
        map.put("Location[TakeCustomerLatitude]", Util.toTextRequestBody(pengajuanBaru.getTakeCustomerLatitude14()));
        map.put("Location[TakePaycheckAction]", Util.toTextRequestBody(pengajuanBaru.getTakePaycheckAction14()));
        map.put("Location[TakePaycheckLongitude]", Util.toTextRequestBody(pengajuanBaru.getTakePaychecklongitude14()));
        map.put("Location[TakePaycheckLatitude]", Util.toTextRequestBody(pengajuanBaru.getTakePaychecklatitude14()));
        map.put("Location[TakeAdditionalDocumentsAction]", Util.toTextRequestBody(pengajuanBaru.getTakeAdditionalDocumentsAction14()));
        map.put("Location[TakeAdditionalDocumentsLongitude]", Util.toTextRequestBody(pengajuanBaru.getTakeAdditionalDocumentsLongitude14()));
        map.put("Location[TakeAdditionalDocumentsLatitude]", Util.toTextRequestBody(pengajuanBaru.getTakeAdditionalDocumentsLatitude14()));
        map.put("Location[TakeSignatureAction]", Util.toTextRequestBody(pengajuanBaru.getTakeSignatureAction14()));
        map.put("Location[TakeSignatureLongitude]", Util.toTextRequestBody(pengajuanBaru.getTakeSignatureLongitude14()));
        map.put("Location[TakeSignatureLatitude]", Util.toTextRequestBody(pengajuanBaru.getTakeSignatureLatitude14()));
        map.put("Location[SubmitAction]", Util.toTextRequestBody(pengajuanBaru.getSubmitAction14()));
        map.put("Location[SubmitLongitude]", Util.toTextRequestBody(pengajuanBaru.getSubmitLongitude14()));
        map.put("Location[SubmitLatitude]", Util.toTextRequestBody(pengajuanBaru.getSubmitLatitude14()));
        map.put("Location[SyncAction]", Util.toTextRequestBody(pengajuanBaru.getSyncAction14()));
        map.put("Location[SyncLongitude]", Util.toTextRequestBody(pengajuanBaru.getSyncLongitude14()));
        map.put("Location[SyncLatitude]", Util.toTextRequestBody(pengajuanBaru.getSyncLatitude14()));

        //KOP
        map.put("KorpFormulir[SalesMethod]", Util.toTextRequestBody(pengajuanBaru.getMetodePenjualan00()));
        map.put("KorpFormulir[FinancingPurpose]", Util.toTextRequestBody(pengajuanBaru.getStatusPemohon00()));
        map.put("KorpFormulir[DateStart]", Util.toTextRequestBody(pengajuanBaru.getBlacklist_date()));
        map.put("KorpFormulir[BranchIdPrimary]", Util.toTextRequestBody(pengajuanBaru.getMasterBranch()));
        map.put("KorpFormulir[BranchIdLintasCabang]", Util.toTextRequestBody(pengajuanBaru.getBranch()));
        map.put("KorpFormulir[ApplicationIdKPM]", Util.toTextRequestBody(pengajuanBaru.getIdKpm()));
        map.put("KorpFormulir[PID]", Util.toTextRequestBody(pengajuanBaru.getUuid()));
        map.put("KorpFormulir[CustomerIdConfins]", Util.toTextRequestBody(pengajuanBaru.getCustomerId()));
        //Keterangan
        map.put("KorpFormulir[Keterangan]", Util.toTextRequestBody(pengajuanBaru.getKeterangan()));
        // Data Pribadi
        map.put("PersonalData[FullName]", Util.toTextRequestBody(pengajuanBaru.getNamaLengkap0()));
        map.put("PersonalData[LegalName]", Util.toTextRequestBody(pengajuanBaru.getNamaKtp0()));
        map.put("PersonalData[IDNumber]", Util.toTextRequestBody(pengajuanBaru.getNoKtp0()));
        map.put("PersonalData[PersonalNPWP]", Util.toTextRequestBody(pengajuanBaru.getNoNpwp0()));
        map.put("PersonalData[IDTypeIssuedDate]", Util.toTextRequestBody(pengajuanBaru.getTanggalKtp0()));
        map.put("PersonalData[BirthPlace]", Util.toTextRequestBody(pengajuanBaru.getTempatLahir0()));
        map.put("PersonalData[BirthDate]", Util.toTextRequestBody(pengajuanBaru.getTanggalLahir0()));
        map.put("PersonalData[SurgateMotherName]", Util.toTextRequestBody(pengajuanBaru.getIbuKandung0()));
        map.put("PersonalData[Gender]", Util.toTextRequestBody(pengajuanBaru.getJenisKelamin0()));
        map.put("PersonalData[MaritalStatus]", Util.toTextRequestBody(pengajuanBaru.getStatusNikah0()));
        map.put("PersonalData[NumOfDependence]", Util.toTextRequestBody(pengajuanBaru.getJumlahTanggungan0()));
        map.put("PersonalData[HomeStatus]", Util.toTextRequestBody(pengajuanBaru.getStatusRumah0()));
        map.put("PersonalData[StaySinceYear]", Util.toTextRequestBody(pengajuanBaru.getTinggalSejakTahun0()));
        map.put("PersonalData[StaySinceMonth]", Util.toTextRequestBody(pengajuanBaru.getTinggalSejakBulan0()));
        map.put("PersonalData[Education]", Util.toTextRequestBody(pengajuanBaru.getPendidikan0()));
        map.put("PersonalData[Nationality]", Util.toTextRequestBody(pengajuanBaru.getWargaNegara0()));
        map.put("PersonalData[Religion]", Util.toTextRequestBody(pengajuanBaru.getAgama0()));
        map.put("PersonalData[MobilePhone]", Util.toTextRequestBody(pengajuanBaru.getMobilePhone0()));
        map.put("PersonalData[Email]", Util.toTextRequestBody(pengajuanBaru.getEmail0()));
        // Data Pasangan Suami/Istri
        map.put("FamilyData[0][SeqNo]", Util.toTextRequestBody("1"));
        map.put("FamilyData[0][Id]", Util.toTextRequestBody(pengajuanBaru.getIdPasangan8()));
        map.put("FamilyData[0][Name]", Util.toTextRequestBody(pengajuanBaru.getNamaPasangan8()));
        map.put("FamilyData[0][IDNumber]", Util.toTextRequestBody(pengajuanBaru.getNoKtpPasangan8()));
        map.put("FamilyData[0][BirthPlace]", Util.toTextRequestBody(pengajuanBaru.getTempatLahirPasangan8()));
        map.put("FamilyData[0][BirthDate]", Util.toTextRequestBody(pengajuanBaru.getTanggalLahirPasangan8()));
        map.put("FamilyData[0][Handphone]", Util.toTextRequestBody(pengajuanBaru.getHandphonePasangan8()));
        // Alamat Tinggal
        map.put("Residance[Address]", Util.toTextRequestBody(pengajuanBaru.getAlamatTinggal1() + "|" + pengajuanBaru.getAutoAlamatTinggal1()));
        map.put("Residance[RT]", Util.toTextRequestBody(pengajuanBaru.getRtTinggal1()));
        map.put("Residance[RW]", Util.toTextRequestBody(pengajuanBaru.getRwTinggal1()));
        map.put("Residance[VillageCode]", Util.toTextRequestBody(pengajuanBaru.getKelurahanKodeTinggal1()));
        map.put("Residance[AreaPhone]", Util.toTextRequestBody(pengajuanBaru.getAreaPhoneTinggal1()));
        map.put("Residance[Phone]", Util.toTextRequestBody(pengajuanBaru.getPhoneTinggal1()));
        // Alamat KTP
        map.put("Legal[Address]", Util.toTextRequestBody(pengajuanBaru.getAlamatKtp1() + "|" + pengajuanBaru.getAutoAlamatKtp1()));
        map.put("Legal[RT]", Util.toTextRequestBody(pengajuanBaru.getRtKtp1()));
        map.put("Legal[RW]", Util.toTextRequestBody(pengajuanBaru.getRwKtp1()));
        map.put("Legal[VillageCode]", Util.toTextRequestBody(pengajuanBaru.getKelurahanKodeKtp1()));
        map.put("Legal[AreaPhone]", Util.toTextRequestBody(pengajuanBaru.getAreaPhoneKtp1()));
        map.put("Legal[Phone]", Util.toTextRequestBody(pengajuanBaru.getPhoneKtp1()));
        // Informasi Kerabat

        map.put("EmergencyContact[Name]", Util.toTextRequestBody(pengajuanBaru.getNamaLengkap2()));
        map.put("EmergencyContact[Relationship]", Util.toTextRequestBody(pengajuanBaru.getHubunganKerabat2()));
        map.put("EmergencyContact[Address]", Util.toTextRequestBody(pengajuanBaru.getAlamat2() + "|" + pengajuanBaru.getAuto_alamat2()));
        map.put("EmergencyContact[RT]", Util.toTextRequestBody(pengajuanBaru.getRt2()));
        map.put("EmergencyContact[RW]", Util.toTextRequestBody(pengajuanBaru.getRw2()));
        map.put("EmergencyContact[VillageCode]", Util.toTextRequestBody(pengajuanBaru.getKelurahanKode2()));
        map.put("EmergencyContact[HomePhoneArea]", Util.toTextRequestBody(pengajuanBaru.getAreaPhoneRumah2()));
        map.put("EmergencyContact[HomePhone]", Util.toTextRequestBody(pengajuanBaru.getPhoneRumah2()));
        map.put("EmergencyContact[OfficePhoneArea]", Util.toTextRequestBody(pengajuanBaru.getAreaPhoneKantor2()));
        map.put("EmergencyContact[OfficePhone]", Util.toTextRequestBody(pengajuanBaru.getPhoneKantor2()));
        map.put("EmergencyContact[MobilePhone]", Util.toTextRequestBody(pengajuanBaru.getMobilePhone2()));
        // Data Perusahaan
        map.put("Company[ProfessionID]", Util.toTextRequestBody(pengajuanBaru.getProfesiKode3()));
//        map.put("Company[PersonalCustomerType]", Util.toTextRequestBody(pengajuanBaru.getCustomerType3()));
        map.put("Company[Name]", Util.toTextRequestBody(pengajuanBaru.getNamaPerusahaan3()));
        map.put("Company[Address]", Util.toTextRequestBody(pengajuanBaru.getAlamat3() + "|" + pengajuanBaru.getAuto_alamat3()));
        map.put("Company[RT]", Util.toTextRequestBody(pengajuanBaru.getRt3()));
        map.put("Company[RW]", Util.toTextRequestBody(pengajuanBaru.getRw3()));
        map.put("Company[VillageCode]", Util.toTextRequestBody(pengajuanBaru.getKelurahanKode3()));
        map.put("Company[AreaPhone]", Util.toTextRequestBody(pengajuanBaru.getAreaPhone3()));
        map.put("Company[Phone]", Util.toTextRequestBody(pengajuanBaru.getPhone3()));
        map.put("Company[MonthlyFixedIncome]", Util.toTextRequestBody(pengajuanBaru.getPenghasilanTetap3()));
        map.put("Company[MonthlyVariableIncome]", Util.toTextRequestBody(pengajuanBaru.getPenghasilanLain3()));
        map.put("Company[LivingCostAmount]", Util.toTextRequestBody(pengajuanBaru.getBiayaHidup3()));
        map.put("Company[JobPosition]", Util.toTextRequestBody(pengajuanBaru.getJobPositionKode3()));
        map.put("Company[JobType]", Util.toTextRequestBody(pengajuanBaru.getJobTypeKode3()));
        map.put("Company[IndustryTypeID]", Util.toTextRequestBody(pengajuanBaru.getIndustriKode3()));
        map.put("Company[SpouseIncome]", Util.toTextRequestBody(pengajuanBaru.getPenghasilanPasangan3()));
        map.put("Company[EmploymentSinceYear]", Util.toTextRequestBody(pengajuanBaru.getTahunKerja3()));
        // Sementara di-hide dulu
//        map.put("Company[Counterpart]", Util.toTextRequestBody(pengajuanBaru.getCounterPart3()));
//        map.put("Company[DebtBusinessScale]", Util.toTextRequestBody(pengajuanBaru.getDebtBusinessScale3()));
//        map.put("Company[DebtGroup]", Util.toTextRequestBody(pengajuanBaru.getDebtGroup3()));
        map.put("Company[IsAffiliateWithPP]", Util.toTextRequestBody("yes".equalsIgnoreCase(pengajuanBaru.getIsAffiliateWithPp3()
        ) ? "1" : "0"));
        // Data kartu kredit
        map.put("DataCreditCard[BankName]", Util.toTextRequestBody(pengajuanBaru.getNamaBank9()));
        map.put("DataCreditCard[IDCard]", Util.toTextRequestBody(pengajuanBaru.getNoKartuKredit9()));
        map.put("DataCreditCard[CardType]", Util.toTextRequestBody(pengajuanBaru.getJenisKartuKredit9()));
        map.put("DataCreditCard[CardLimit]", Util.toTextRequestBody(pengajuanBaru.getLimitKartuKredit9()));
        map.put("DataCreditCard[MembershipOldMonth]", Util.toTextRequestBody(pengajuanBaru.getMonthExpiredCard9()));
        map.put("DataCreditCard[MembershipOldYear]", Util.toTextRequestBody(pengajuanBaru.getYearExpiredCard9()));
        // Data kartu membership
        map.put("MembershipCard[IDMember]", Util.toTextRequestBody(pengajuanBaru.getNoMembership10()));
        map.put("MembershipCard[EffectiveDate]", Util.toTextRequestBody(pengajuanBaru.getTanggalEfektif10()));
        map.put("MembershipCard[ExpiredDate]", Util.toTextRequestBody(pengajuanBaru.getTanggalExpired10()));

        // Data Asuransi
        map.put("Insurance[CoverageType]", Util.toTextRequestBody(pengajuanBaru.getCoverageAsurani5()));
        if ("E".equalsIgnoreCase(pengajuanBaru.getPengajuanType())) {
            if (pengajuanBaru.isPremiManual()) {
                premiManualString = "1";
            } else {
                premiManualString = "0";
            }
            map.put("Insurance[IsPremiManual]", Util.toTextRequestBody(premiManualString));
        } else {
            map.put("Insurance[PremiAsuransiAgunan]", Util.toTextRequestBody(pengajuanBaru.getPremiASuransiAgunan12()));
            map.put("Insurance[PremiAsuransiJiwa]", Util.toTextRequestBody(pengajuanBaru.getPremiASuransiJiwa12()));
        }
        map.put("Insurance[IsPersonalAccident]", Util.toTextRequestBody("yes".equalsIgnoreCase(pengajuanBaru.getIsPersonalAccident5()) ? "1" : "0"));

        // Detail Product
        map.put("DetailProduct[ProductID]", Util.toTextRequestBody(pengajuanBaru.getProductOfferingKode4() == null ? "" : pengajuanBaru.getProductOfferingKode4()));
        map.put("DetailProduct[ProductOfferingID]", Util.toTextRequestBody(pengajuanBaru.getProductOfferingKode24()));
        map.put("DetailProduct[NumOfAssetUnit]", Util.toTextRequestBody(pengajuanBaru.getJumlahAsset4()));
        map.put("DetailProduct[POS]", Util.toTextRequestBody(pengajuanBaru.getPosKode4()));

        // Data Perhitungan
        if ("E".equalsIgnoreCase(pengajuanBaru.getPengajuanType())) {
            map.put("DetailProduct[Tenor]", Util.toTextRequestBody(pengajuanBaru.getTenor6()));
            map.put("DetailFinancing[FlatRate]", Util.toTextRequestBody(pengajuanBaru.getFlatRate6()));
            map.put("DetailFinancing[AdminFee]", Util.toTextRequestBody(pengajuanBaru.getBiayaAdmin6()));
//            map.put("DetailFinancing[TypePayment]", Util.toTextRequestBody(pengajuanBaru.getTipePembayaran()));
            map.put("DetailFinancing[OtherFee]", Util.toTextRequestBody(pengajuanBaru.getBiayaLainnya6()));
            map.put("DetailFinancing[SubsidyRefund]", Util.toTextRequestBody(pengajuanBaru.getRefundSubsidi6()));
            map.put("[DetailFinancing][DiscountRateTimes]", Util.toTextRequestBody(pengajuanBaru.getBebasBungaPerhitungan6()));
            map.put("DetailFinancing[PurchasePrice]", Util.toTextRequestBody(pengajuanBaru.getPurchasePrice6()));
            map.put("DetailFinancing[Discount]", Util.toTextRequestBody(pengajuanBaru.getDiscount6()));
            map.put("DetailFinancing[DownPayment]", Util.toTextRequestBody(pengajuanBaru.getDownPayment6()));
            map.put("Insurance[PremiumAmountToCustomer]", Util.toTextRequestBody(pengajuanBaru.getPremiAsuransi6()));
            map.put("DetailFinancing[NTF]", Util.toTextRequestBody(pengajuanBaru.getNtf6()));
            map.put("DetailFinancing[TotalFinancing]", Util.toTextRequestBody(pengajuanBaru.getJumlahPembiayaan6()));
            map.put("DetailFinancing[InterestFinancing]", Util.toTextRequestBody(pengajuanBaru.getBungaTotalPembiayaan6()));
            map.put("[DetailFinancing][MonthFinancingInterest]", Util.toTextRequestBody(pengajuanBaru.getBungaPembiayaanPerBulan6()));
            map.put("DetailFinancing[TotalLoan]", Util.toTextRequestBody(pengajuanBaru.getTotalPinjaman6()));
            map.put("[DetailFinancing][FirstInstallmentAmmount]", Util.toTextRequestBody(pengajuanBaru.getSetorPertama6()));
            map.put("DetailFinancing[InstallmentAmount]", Util.toTextRequestBody(pengajuanBaru.getSetorPerbulan6()));
            map.put("DetailFinancing[FirstPayment]", Util.toTextRequestBody(pengajuanBaru.getPembayaranAwal6()));
            map.put("[DetailFinancing][InterestFreeDealerPayment]", Util.toTextRequestBody(pengajuanBaru.getPembayaranDealer6()));
            map.put("DetailFinancing[FirstInstallment]", Util.toTextRequestBody(pengajuanBaru.getFirstInstalment()));
            map.put("DetailFinancing[EffectiveRate]", Util.toTextRequestBody(pengajuanBaru.getEffectiveRate6()));
        } else {
            map.put("DetailProduct[Tenor]", Util.toTextRequestBody(pengajuanBaru.getTenor12()));
            map.put("DetailFinancing[PurchasePrice]", Util.toTextRequestBody(pengajuanBaru.getPurchasePrice6()));
            map.put("DetailFinancing[Discount]", Util.toTextRequestBody(pengajuanBaru.getDiscount6()));
            map.put("DetailFinancing[DownPayment]", Util.toTextRequestBody(pengajuanBaru.getDownPayment6()));
            map.put("DetailFinancing[NTF]", Util.toTextRequestBody(pengajuanBaru.getNilaiPembiayaan12()));
            map.put("DetailFinancing[FirstInstallment]", Util.toTextRequestBody(pengajuanBaru.getFirstInstalment()));
            map.put("DetailFinancing[AdminFee]", Util.toTextRequestBody(pengajuanBaru.getBiaya_administrasi12()));
            map.put("DetailFinancing[InstallmentAmount]", Util.toTextRequestBody(pengajuanBaru.getAngsuranPerbulan12()));
            map.put("DetailFinancing[SubsidyRefund]", Util.toTextRequestBody(pengajuanBaru.getRefundSubsidi12()));
            map.put("DetailFinancing[OtherFee]", Util.toTextRequestBody(pengajuanBaru.getBiayaLainnya12()));
            map.put("DetailFinancing[OtherFee2]", Util.toTextRequestBody(pengajuanBaru.getBiayaLainnya212()));
            map.put("DetailFinancing[TotalFinancing]", Util.toTextRequestBody(pengajuanBaru.getJumlahPembiayaan12()));
            map.put("DetailFinancing[InterestFinancing]", Util.toTextRequestBody(pengajuanBaru.getBungaPembiayaan12()));
            map.put("DetailFinancing[TotalLoan]", Util.toTextRequestBody(pengajuanBaru.getTotalPinjaman12()));
            map.put("DetailFinancing[FirstInstallmentAmmount]", Util.toTextRequestBody(pengajuanBaru.getTotalSetorPertama12()));
            map.put("DetailFinancing[EffectiveRate]", Util.toTextRequestBody(pengajuanBaru.getBungaFlatTahun12()));
            map.put("DetailFinancing[MonthlyEffectiveRate]", Util.toTextRequestBody(pengajuanBaru.getBungaFlatBulan12()));
            map.put("DetailFinancing[OTR]", Util.toTextRequestBody(pengajuanBaru.getHargaKendaraan()));
            map.put("DetailFinancing[DownPayment]", Util.toTextRequestBody(pengajuanBaru.getDpCash12()));
            map.put("DetailFinancing[CostOfObtainingAFiduciary]", Util.toTextRequestBody(pengajuanBaru.getFidusia12()));
            map.put("DetailFinancing[CostOfObtainingSTNK]", Util.toTextRequestBody(pengajuanBaru.getStnk12()));
        }

        // DetailAsset
        map.put("AssetMaster[SupplierID]", Util.toTextRequestBody(pengajuanBaru.getSupplierKode()));
        map.put("AssetMaster[SupplierBankAccountID]", Util.toTextRequestBody(pengajuanBaru.getBankAccountKode()));
        map.put("AssetMaster[SalesmanID]", Util.toTextRequestBody(pengajuanBaru.getMarketingSupplierKode()));
        // Data Agunan
        if (!"E".equalsIgnoreCase(pengajuanBaru.getPengajuanType())) {
            if ("MBL".equalsIgnoreCase(pengajuanBaru.getTypeDataOffering()) ||
                    "MBLBKS".equalsIgnoreCase(pengajuanBaru.getTypeDataOffering())) {
                map.put("DataAgunan[MotorVehicle]", Util.toTextRequestBody("RE"));
            } else if ("MTR".equalsIgnoreCase(pengajuanBaru.getTypeDataOffering())) {
                map.put("DataAgunan[MotorVehicle]", Util.toTextRequestBody("RD"));
            } else {
                map.put("DataAgunan[MotorVehicle]", Util.toTextRequestBody(""));
            }
            map.put("DataAgunan[Merk]", Util.toTextRequestBody(pengajuanBaru.getMerkKb11()));
            map.put("DataAgunan[Type]", Util.toTextRequestBody(pengajuanBaru.getTypeKb11()));
            map.put("DataAgunan[Year]", Util.toTextRequestBody(pengajuanBaru.getTahunKb11()));
            map.put("DataAgunan[Colour]", Util.toTextRequestBody(pengajuanBaru.getWarnaKb11()));
            map.put("DataAgunan[CC]", Util.toTextRequestBody(pengajuanBaru.getIsiSilinderKb11()));
            map.put("DataAgunan[NoPolisi]", Util.toTextRequestBody(pengajuanBaru.getNoPolisiKb11()));
            map.put("DataAgunan[NoRangka]", Util.toTextRequestBody(pengajuanBaru.getNoRangkaKb11()));
            map.put("DataAgunan[NoMesin]", Util.toTextRequestBody(pengajuanBaru.getNoMesinKb11()));
            map.put("DataAgunan[BPKBName]", Util.toTextRequestBody(pengajuanBaru.getBpkbKb11()));
            map.put("DataAgunan[CollateralValue]", Util.toTextRequestBody(pengajuanBaru.getNilaiAgunan12()));
        }
        map.put("mobile_submission_key", Util.toTextRequestBody(pengajuanBaru.getMobileSubmissionKey()));
        // Signature
        map.put("Signature[Applicant]", Util.toTextRequestBody(pengajuanBaru.getTtdPemohon7()));
        map.put("Signature[ApplicantHusbandWife]", Util.toTextRequestBody(pengajuanBaru.getTtdPasangan7()));
        if ("E".equalsIgnoreCase(pengajuanBaru.getPengajuanType())) {
            // AssetElektronikountSignature
            List<AssetElektronik> assetElektronikList = new ArrayList<>();
            try {
                assetElektronikList = databaseService.getAssetDao().queryBuilder().where().eq("pengajuan_id", pengajuanBaru).query();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG) Log.e("Query asset", String.valueOf(e));
                Crashlytics.logException(e);
            }

            for (int i = 0; i < assetElektronikList.size(); i++) {
                map.put("Asset[" + i + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(i + 1)));
                map.put("Asset[" + i + "][AssetCode]", Util.toTextRequestBody(assetElektronikList.get(i).getKodeBarang()));
                map.put("Asset[" + i + "][Type]", Util.toTextRequestBody(assetElektronikList.get(i).getType()));
                map.put("Asset[" + i + "][OTRPrice]", Util.toTextRequestBody(assetElektronikList.get(i).getPrice()));
                map.put("Asset[" + i + "][DPAmount]", Util.toTextRequestBody(assetElektronikList.get(i).getDp().isEmpty() ? "0" : assetElektronikList.get(i).getDp()));
                map.put("Asset[" + i + "][Discount]", Util.toTextRequestBody(assetElektronikList.get(i).getDiscount().isEmpty() ? "0" : assetElektronikList.get(i).getDiscount()));
            }
        } else {
            // AssetKendaraan
            List<AssetKendaraan> assetKendaraanList = new ArrayList<>();
            try {
                assetKendaraanList = databaseService.getAssetKendaraanDao().queryBuilder().where()
                        .eq("pengajuan_id", pengajuanBaru).query();
            } catch (SQLException e) {
                if (BuildConfig.DEBUG)
                    Log.e("Query pengajuan", String.valueOf(e));
                Crashlytics.logException(e);
            }

            for (int i = 0; i < assetKendaraanList.size(); i++) {
                map.put("Asset[" + i + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(i + 1)));
                map.put("Asset[" + i + "][AssetCode]", Util.toTextRequestBody(assetKendaraanList.get(i).getKodeKendaraan()));
                map.put("Asset[" + i + "][Merk]", Util.toTextRequestBody(assetKendaraanList.get(i).getMerkKendaraan()));
                map.put("Asset[" + i + "][TypeAsset]", Util.toTextRequestBody(assetKendaraanList.get(i).getType()));
                map.put("Asset[" + i + "][Type]", Util.toTextRequestBody(assetKendaraanList.get(i).getType()));
                map.put("Asset[" + i + "][ManufacturingYear]", Util.toTextRequestBody(assetKendaraanList.get(i).getTahun()));
                map.put("Asset[" + i + "][Color]", Util.toTextRequestBody(assetKendaraanList.get(i).getWarna()));
                map.put("Asset[" + i + "][UsedNew]", Util.toTextRequestBody(assetKendaraanList.get(i).getKondisi()));
                map.put("Asset[" + i + "][Cylinder]", Util.toTextRequestBody(assetKendaraanList.get(i).getIsiSilinder()));
                map.put("Asset[" + i + "][PoliceNo]", Util.toTextRequestBody(assetKendaraanList.get(i).getNoPolisi()));
                map.put("Asset[" + i + "][FrameNo]", Util.toTextRequestBody(assetKendaraanList.get(i).getNoRangka()));
                map.put("Asset[" + i + "][MachineNo]", Util.toTextRequestBody(assetKendaraanList.get(i).getNoMesin()));
            }
        }

        if ("new".equalsIgnoreCase(pengajuanBaru.getFormType())) {
            callNew = apiService.addApplication(token, map);
            callNew.enqueue(new Callback<BaseResponse<AddApplicationResponse>>() {
                @Override
                public void onResponse(Call<BaseResponse<AddApplicationResponse>> call, Response<BaseResponse<AddApplicationResponse>> response) {
                    if (response.isSuccessful()) {
                        try {
                            UpdateBuilder<PengajuanBaru, Integer> updateBuilder = databaseService.getPengajuanBaruDao().updateBuilder();
                            updateBuilder.where().eq("id", pengajuanBaru.getId());
                            updateBuilder.updateColumnValue("status_sync", 1);
                            updateBuilder.update();
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG) Log.e("update status sync", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        mPengajuanAddMvpView.onSuccessSubmitPengajuan(response.body().getData().getApplicationId());
                    } else if (response.code() == 403) {
                        mPengajuanAddMvpView.onTokenPengajuanExpired();
                    } else if (response.code() == 422) {
                        try {
                            UpdateBuilder<PengajuanBaru, Integer> updateBuilder = databaseService.getPengajuanBaruDao().updateBuilder();
                            updateBuilder.where().eq("id", pengajuanBaru.getId());
                            updateBuilder.updateColumnValue("form_type", "draft_baru");
                            updateBuilder.update();
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG) Log.e("Udpate form type", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.parseError(response));
                        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
                    } else {
                        mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.parseError(response));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<AddApplicationResponse>> call, Throwable t) {
                    if (!callNew.isCanceled()) {
                        if (mPengajuanAddMvpView != null)
                            mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.responseFailedError(t));
                    }
                }
            });
        }  else if ("isAssignEdit".equalsIgnoreCase(pengajuanBaru.getFormType())) {
            String id = pengajuanBaru.getApplicationId();
            callDraft = apiService.addApplicationEdit(token, map, id);
            callDraft.enqueue(new Callback<BaseResponse<AddApplicationResponse>>() {

                @Override
                public void onResponse(Call<BaseResponse<AddApplicationResponse>> call, Response<BaseResponse<AddApplicationResponse>> response) {
                    if (response.isSuccessful()) {
                        try {
                            UpdateBuilder<PengajuanBaru, Integer> updateBuilder = databaseService.getPengajuanBaruDao().updateBuilder();
                            updateBuilder.where().eq("id", pengajuanBaru.getId());
                            updateBuilder.updateColumnValue("status_sync", 1);
                            updateBuilder.update();
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG)
                                Log.e("Update status sync", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        mPengajuanAddMvpView.onSuccessSubmitPengajuan(response.body().getData().getApplicationId());
                    } else if (response.code() == 403) {
                        mPengajuanAddMvpView.onTokenPengajuanExpired();
                    } else if (response.code() == 422) {
                        try {
                            UpdateBuilder<PengajuanBaru, Integer> updateBuilder = databaseService.getPengajuanBaruDao().updateBuilder();
                            updateBuilder.where().eq("id", pengajuanBaru.getId());
                            updateBuilder.updateColumnValue("form_type", "draft_baru");
                            updateBuilder.update();
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG)Log.e("Update form type", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.parseError(response));
                        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
                    } else {
                        mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.
                                parseError(response));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<AddApplicationResponse>> call, Throwable t) {
                    if (!callDraft.isCanceled()) {
                        if (mPengajuanAddMvpView != null)
                            mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.responseFailedError(t));
                        Crashlytics.logException(t);
                    }
                }
            });
        }else if ("edit".equalsIgnoreCase(pengajuanBaru.getFormType())) {
            callEdit = apiService.addApplication(token, map);
            callEdit.enqueue(new Callback<BaseResponse<AddApplicationResponse>>() {
                @Override
                public void onResponse(Call<BaseResponse<AddApplicationResponse>> call, Response<BaseResponse<AddApplicationResponse>> response) {
                    if (response.isSuccessful()) {
                        try {
                            UpdateBuilder<PengajuanBaru, Integer> updateBuilder = databaseService.getPengajuanBaruDao().updateBuilder();
                            updateBuilder.where().eq("id", pengajuanBaru.getId());
                            updateBuilder.updateColumnValue("status_sync", 1);
                            updateBuilder.update();
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG)
                                Log.e("Update status sync", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        mPengajuanAddMvpView.onSuccessSubmitPengajuan(response.body().getData().getApplicationId());
                    } else if (response.code() == 403) {
                        mPengajuanAddMvpView.onTokenPengajuanExpired();
                    } else if (response.code() == 422) {
                        try {
                            UpdateBuilder<PengajuanBaru, Integer> updateBuilder = databaseService.getPengajuanBaruDao().updateBuilder();
                            updateBuilder.where().eq("id", pengajuanBaru.getId());
                            updateBuilder.updateColumnValue("form_type", "draft_baru");
                            updateBuilder.update();
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG)
                                Log.e("Update form type", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.parseError(response));
                        EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
                    } else {
                        mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.parseError(response));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<AddApplicationResponse>> call, Throwable t) {
                    if (!callEdit.isCanceled()) {
                        if (mPengajuanAddMvpView != null)
                            mPengajuanAddMvpView.onFailedSubmitPengajuan(errorRetrofitUtil.responseFailedError(t));
                    }
                }
            });
        }
    }
}
