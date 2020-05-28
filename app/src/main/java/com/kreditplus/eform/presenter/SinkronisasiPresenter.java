package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.MasterFormPengajuan;
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
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.model.response.AddApplicationResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.SynchronizationMvpView;
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

public class SinkronisasiPresenter implements BasePresenter<SynchronizationMvpView> {
    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private SynchronizationMvpView mSynchronizationMvpView;
    private Call<BaseResponse<AddApplicationResponse>> call;
    private MasterFormPengajuan masterFormPengajuan;

    public SinkronisasiPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(SynchronizationMvpView view) {mSynchronizationMvpView = view;}

    @Override
    public void detachView() {
        if (call != null) call.cancel();
        mSynchronizationMvpView = null;
    }

    public void sinkronisasiPengajuan(String token, final MasterFormPengajuan masterFormPengajuan) {
        this.masterFormPengajuan = masterFormPengajuan;
        mSynchronizationMvpView.onPreSynchronization();
        Map<String, RequestBody> map = new HashMap<>();
        map.put("KorpFormulir[DateStart]", Util.toTextRequestBody(masterFormPengajuan.getBlacklistDate()));
        map.put("KorpFormulir[BranchIdPrimary]", Util.toTextRequestBody(masterFormPengajuan.getMasterBranch()));
        map.put("KorpFormulir[BranchIdLintasCabang]", Util.toTextRequestBody(masterFormPengajuan.getBranch()));
        map.put("KorpFormulir[ApplicationIdKPM]", Util.toTextRequestBody(masterFormPengajuan.getIdKpm()));
        map.put("KorpFormulir[PID]", Util.toTextRequestBody(masterFormPengajuan.getUuid()));
        map.put("KorpFormulir[CustomerIdConfins]", Util.toTextRequestBody(masterFormPengajuan.getCustomerIdConfins()));

        map.put("Application[DataType]", Util.toTextRequestBody(masterFormPengajuan.getTipePengajuan()));
        map.put("Application[OfferingType]", Util.toTextRequestBody(masterFormPengajuan.getTipeDataOffering()));
//        map.put("KorpFormulir[DataType]", Util.toTextRequestBody(masterFormPengajuan.getTipePengajuan())); ini kayanya nggak ada
        map.put("mobile_submission_key", Util.toTextRequestBody(masterFormPengajuan.getMobileSubmissionKey()));
        map.put("Application[EFNumber]", Util.toTextRequestBody(masterFormPengajuan.getEfNumber()));


//        KOP
        List<TblKop> tblKops = new ArrayList<>();
        try {
            tblKops = databaseService.getTblKopDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblKop) {
            if (BuildConfig.DEBUG) Log.e("TblKop", String.valueOf(eTblKop));
            Crashlytics.logException(eTblKop);
        }
        for (int i = 0; i < tblKops.size(); i++) {
            map.put("KorpFormulir[SalesMethod]", Util.toTextRequestBody(tblKops.get(i).getMetodePenjualan()));
            map.put("KorpFormulir[FinancingPurpose]", Util.toTextRequestBody(tblKops.get(i).getStatusCustomer()));
            map.put("KorpFormulir[TypeOfFinancing]", Util.toTextRequestBody(tblKops.get(i).getJenisPembiayaan()));
        }

//        DATA PRIBADI
        List<TblDataPribadi> tblDataPribadis = new ArrayList<>();
        try {
            tblDataPribadis = databaseService.getTblDataPribadiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblDataPribadi) {
            if (BuildConfig.DEBUG) Log.e("TblDataPribadi", String.valueOf(eTblDataPribadi));
            Crashlytics.logException(eTblDataPribadi);
        }
        for (int i = 0; i < tblDataPribadis.size(); i++) {
            map.put("PersonalData[IDNumber]", Util.toTextRequestBody(tblDataPribadis.get(i).getNoKtp()));
            map.put("PersonalData[PersonalNPWP]", Util.toTextRequestBody(tblDataPribadis.get(i).getNoNpwp()));
            map.put("PersonalData[MaritalStatus]", Util.toTextRequestBody(tblDataPribadis.get(i).getStatusPernikahan()));
            map.put("PersonalData[BirthDate]", Util.toTextRequestBody(tblDataPribadis.get(i).getTanggalLahir()));
            map.put("PersonalData[MobilePhone]", Util.toTextRequestBody(tblDataPribadis.get(i).getNomorHandphone()));
            map.put("PersonalData[FullName]", Util.toTextRequestBody(tblDataPribadis.get(i).getNamaLengkap()));
            map.put("PersonalData[LegalName]", Util.toTextRequestBody(tblDataPribadis.get(i).getNamaKtp()));
            map.put("PersonalData[IDTypeIssuedDate]", Util.toTextRequestBody(tblDataPribadis.get(i).getTanngalTerbitKtp()));
            map.put("PersonalData[Gender]", Util.toTextRequestBody(tblDataPribadis.get(i).getJenisKelamin()));
            map.put("PersonalData[BirthPlace]", Util.toTextRequestBody(tblDataPribadis.get(i).getTempatLahir()));
            map.put("PersonalData[SurgateMotherName]", Util.toTextRequestBody(tblDataPribadis.get(i).getNamaIbuKandung()));
            map.put("PersonalData[Education]", Util.toTextRequestBody(tblDataPribadis.get(i).getStatusPendidikan()));
            map.put("PersonalData[HomeStatus]", Util.toTextRequestBody(tblDataPribadis.get(i).getStatusRumah()));
            map.put("PersonalData[StaySinceYear]", Util.toTextRequestBody(tblDataPribadis.get(i).getTinggalSejakTahun()));
            map.put("PersonalData[StaySinceMonth]", Util.toTextRequestBody(tblDataPribadis.get(i).getTinggalSejakBulan()));
            map.put("PersonalData[Religion]", Util.toTextRequestBody(tblDataPribadis.get(i).getAgama()));
            map.put("PersonalData[NumOfDependence]", Util.toTextRequestBody(tblDataPribadis.get(i).getJumlahTanggungan()));
            map.put("PersonalData[Email]", Util.toTextRequestBody(tblDataPribadis.get(i).getEmail()));
            map.put("PersonalData[Nationality]", Util.toTextRequestBody(tblDataPribadis.get(i).getWargaNegara()));
        }

//        DATA PASANGAN
        List<TblDataPasangan> tblDataPasangans = new ArrayList<>();
        try {
            tblDataPasangans = databaseService.getTblDataPasanganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblDataPasangan) {
            if (BuildConfig.DEBUG) Log.e("TblDataPasangan", String.valueOf(eTblDataPasangan));
            Crashlytics.logException(eTblDataPasangan);
        }
        for (int i = 0; i < tblDataPasangans.size(); i++) {
            map.put("FamilyData[0][SeqNo]", Util.toTextRequestBody("1"));
            map.put("FamilyData[0][Id]", Util.toTextRequestBody(tblDataPasangans.get(i).getIdPasangan()));
            map.put("FamilyData[0][Name]", Util.toTextRequestBody(tblDataPasangans.get(i).getNamaLengkap()));
            map.put("FamilyData[0][IDNumber]", Util.toTextRequestBody(tblDataPasangans.get(i).getNomorKtp()));
            map.put("FamilyData[0][BirthPlace]", Util.toTextRequestBody(tblDataPasangans.get(i).getTempatLahir()));
            map.put("FamilyData[0][BirthDate]", Util.toTextRequestBody(tblDataPasangans.get(i).getTanggalLahir()));
            map.put("FamilyData[0][Handphone]", Util.toTextRequestBody(tblDataPasangans.get(i).getNomorHandphone()));
        }

//        ALAMAT TINGGAL DAN KTP
        List<TblAlamat> tblAlamats = new ArrayList<>();
        try {
            tblAlamats = databaseService.getTblAlamatDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblAlamat) {
            if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(eTblAlamat));
            Crashlytics.logException(eTblAlamat);
        }
        for (int i = 0; i < tblAlamats.size(); i++) {
            map.put("Residance[Address]", Util.toTextRequestBody(tblAlamats.get(i).getAlamatTinggal() +"|" + tblAlamats.get(i).getKotaTinggal()));
            map.put("Residance[RT]", Util.toTextRequestBody(tblAlamats.get(i).getRtTinggal()));
            map.put("Residance[RW]", Util.toTextRequestBody(tblAlamats.get(i).getRwTinggal()));
            map.put("Residance[VillageCode]", Util.toTextRequestBody("1221"));
            map.put("Residance[AreaPhone]", Util.toTextRequestBody(tblAlamats.get(i).getKodeAreaTeleponTinggal()));
            map.put("Residance[Phone]", Util.toTextRequestBody(tblAlamats.get(i).getNomorTeleponTinggal()));

            map.put("Legal[Address]", Util.toTextRequestBody(tblAlamats.get(i).getAlamatKtp() +"|"+ tblAlamats.get(i).getKotaKtp()));
            map.put("Legal[RT]", Util.toTextRequestBody(tblAlamats.get(i).getRtKtp()));
            map.put("Legal[RW]", Util.toTextRequestBody(tblAlamats.get(i).getRwKtp()));
            map.put("Legal[VillageCode]", Util.toTextRequestBody("1221"));
            map.put("Legal[AreaPhone]", Util.toTextRequestBody(tblAlamats.get(i).getKodeAreaTeleponKtp()));
            map.put("Legal[Phone]", Util.toTextRequestBody(tblAlamats.get(i).getNomorTeleponKtp()));
        }

//        KONTAK DARURAT
        List<TblKontakDarurat> tblKontakDarurats = new ArrayList<>();
        try {
            tblKontakDarurats = databaseService.getTblKontakDaruratDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblAlamat) {
            if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(eTblAlamat));
            Crashlytics.logException(eTblAlamat);
        }
        for (int i = 0; i < tblKontakDarurats.size(); i++) {
            map.put("EmergencyContact[Name]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNamaLengkap()));
            map.put("EmergencyContact[Relationship]", Util.toTextRequestBody(tblKontakDarurats.get(i).getHubunganKerabat()));
            map.put("EmergencyContact[Address]", Util.toTextRequestBody(tblKontakDarurats.get(i).getAlamat() + "|" + tblKontakDarurats.get(i).getKota()));
            map.put("EmergencyContact[RT]", Util.toTextRequestBody(tblKontakDarurats.get(i).getRt()));
            map.put("EmergencyContact[RW]", Util.toTextRequestBody(tblKontakDarurats.get(i).getRw()));
            map.put("EmergencyContact[VillageCode]", Util.toTextRequestBody("1221"));
            map.put("EmergencyContact[HomePhoneArea]", Util.toTextRequestBody(tblKontakDarurats.get(i).getKodeAreaTeleponRumah()));
            map.put("EmergencyContact[HomePhone]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNomorTeleponRumah()));
            map.put("EmergencyContact[OfficePhoneArea]", Util.toTextRequestBody(tblKontakDarurats.get(i).getKodeAreaTeleponKantor()));
            map.put("EmergencyContact[OfficePhone]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNomorTeleponKantor()));
            map.put("EmergencyContact[MobilePhone]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNomorHandphone()));
        }

//        DATA PEKERJAAN
        List<TblDataPekerjaan> tblDataPekerjaans = new ArrayList<>();
        try {
            tblDataPekerjaans = databaseService.getTblDataPekerjaanDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblAlamat) {
            if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(eTblAlamat));
            Crashlytics.logException(eTblAlamat);
        }
        for (int i = 0; i < tblDataPekerjaans.size(); i++) {
            map.put("Company[Name]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getNamaPerubahaan()));
            map.put("Company[Address]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getAlamat() +"|"+ tblDataPekerjaans.get(i).getKota()));
            map.put("Company[RT]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getRt()));
            map.put("Company[RW]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getRw()));
            map.put("Company[AreaPhone]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeAreaTelepon()));
            map.put("Company[Phone]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getNomorTelepon()));
            map.put("Company[EmploymentSinceYear]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getBekerjaSejak()));
            map.put("Company[ProfessionID]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeProfesi()));
//            map.put("Company[Profession]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getProfesi()));
            map.put("Company[JobType]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeTipePekerjaan()));
//            map.put("Company[JobType]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getTipePekerjaan()));
            map.put("Company[JobPosition]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodePosisiPekerjaan()));
//            map.put("Company[JobPosition]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPosisiPekerjaan()));
            map.put("Company[IndustryTypeID]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeIndustri()));
//            map.put("Company[IndustryTypeID]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getIndustri()));
            map.put("Company[MonthlyFixedIncome]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPenghasilanTetap()));
            map.put("Company[MonthlyVariableIncome]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPenghasilanLain()));
            map.put("Company[SpouseIncome]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPenghasilanPasangan()));
            map.put("Company[LivingCostAmount]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getBiayaHidup()));
            map.put("Company[VillageCode]", Util.toTextRequestBody("1221"));
            map.put("Company[IsAffiliateWithPP]", Util.toTextRequestBody("0"));
        }

//        DATA KARTU KREDIT
        List<TblDataKartuKredit> tblDataKartuKredits = new ArrayList<>();
        try {
            tblDataKartuKredits = databaseService.getTblDataKartuKreditDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblDataKartuKredit) {
            if (BuildConfig.DEBUG) Log.e("eTblDataKartuKredit", String.valueOf(eTblDataKartuKredit));
            Crashlytics.logException(eTblDataKartuKredit);
        }
        for (int i = 0; i < tblDataKartuKredits.size(); i++) {
            map.put("DataCreditCard[BankName]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getNamaBank()));
            map.put("DataCreditCard[IDCard]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getNoKartuKredit()));
            map.put("DataCreditCard[CardType]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getJenisKartuKredit()));
            map.put("DataCreditCard[CardLimit]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getLimitKartuKredit()));
            map.put("DataCreditCard[MembershipOldYear]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getTahunKadaluarsaKartuKredit()));
            map.put("DataCreditCard[MembershipOldMonth]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getBulanKadaluarsaKartuKredit()));
        }

//        DATA KARTU MEMBERSHIP
        List<TblKartuMembership> tblKartuMemberships = new ArrayList<>();
        try {
            tblKartuMemberships = databaseService.getTblKartuMembershipDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblDataKartuKredit) {
            if (BuildConfig.DEBUG) Log.e("eTblDataKartuKredit", String.valueOf(eTblDataKartuKredit));
            Crashlytics.logException(eTblDataKartuKredit);
        }
        for (int i = 0; i < tblKartuMemberships.size(); i++) {
            map.put("MembershipCard[IDMember]", Util.toTextRequestBody(tblKartuMemberships.get(i).getNoMembership()));
            map.put("MembershipCard[EffectiveDate]", Util.toTextRequestBody(tblKartuMemberships.get(i).getTanggalEfektif()));
            map.put("MembershipCard[ExpiredDate]", Util.toTextRequestBody(tblKartuMemberships.get(i).getTanggalExipred()));
        }

//        DETAIL PRODUCT
        List<TblDetailProduct> tblDetailProducts = new ArrayList<>();
        try {
            tblDetailProducts = databaseService.getTblDetailProductDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblDetailProduct) {
            if (BuildConfig.DEBUG) Log.e("eTblDetailProduct", String.valueOf(eTblDetailProduct));
            Crashlytics.logException(eTblDetailProduct);
        }
        for (int i = 0; i < tblDetailProducts.size(); i++) {
            map.put("AssetMaster[SupplierID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeSupplier()));
            map.put("AssetMaster[SupplierName]", Util.toTextRequestBody(tblDetailProducts.get(i).getSupplier()));
            map.put("AssetMaster[SalesmanID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeMarketingSupplier()));
            map.put("AssetMaster[SalesmanName]", Util.toTextRequestBody(tblDetailProducts.get(i).getMarketingSupplier()));
            map.put("DetailProduct[ProductID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeProductId()));
            map.put("DetailProduct[ProductOfferingID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeProductOfferingId()));
            map.put("DetailProduct[ProductOfferingName]", Util.toTextRequestBody(tblDetailProducts.get(i).getProductOffering()));
            map.put("DetailProduct[POS]", Util.toTextRequestBody(tblDetailProducts.get(i).getPosId()));
//            map.put("DetailProduct[POS]", Util.toTextRequestBody(tblDetailProducts.get(i).getPos()));
            map.put("DetailProduct[NumOfAssetUnit]", Util.toTextRequestBody(tblDetailProducts.get(i).getJumlahAsset()));
            map.put("AssetMaster[SupplierBankAccountID]", Util.toTextRequestBody(""));
        }

//        DATA ASURANSI
        List<TblAsuransi> tblAsuransis = new ArrayList<>();
        try {
            tblAsuransis = databaseService.getTblAsuransiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblAsuransi) {
            if (BuildConfig.DEBUG) Log.e("eTblAsuransi", String.valueOf(eTblAsuransi));
            Crashlytics.logException(eTblAsuransi);
        }
        for (int i = 0; i < tblAsuransis.size(); i++) {
            map.put("Insurance[CoverageType]", Util.toTextRequestBody(tblAsuransis.get(i).getManualAgunan()));
            String premiManualString;
            if (tblAsuransis.get(i).getManualAgunan().equalsIgnoreCase("TRUE")) premiManualString = "1";
            else premiManualString = "0";
            map.put("Insurance[IsPremiManual]", Util.toTextRequestBody(premiManualString));
            map.put("Insurance[IsPersonalAccident]", Util.toTextRequestBody("0"));
        }

//        DATA PERHITUNGAN
        List<TblDataPerhitungan> tblDataPerhitungans = new ArrayList<>();
        try {
            tblDataPerhitungans = databaseService.getTblDataPerhitunganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblKeterangan) {
            if (BuildConfig.DEBUG) Log.e("eTblKeterangan", String.valueOf(eTblKeterangan));
            Crashlytics.logException(eTblKeterangan);
        }
        for (int i = 0; i < tblDataPerhitungans.size(); i++) {
            map.put("DetailProduct[Tenor]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTenor()));
            map.put("DetailFinancing[FlatRate]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getFlateRate()));
            map.put("DetailFinancing[AdminFee]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBiayaAdministrasi()));
            map.put("DetailFinancing[OtherFee]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBiayaLainnya()));
//            map.put("DetailFinancing[TypePayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTipePembayaran()));
            map.put("DetailFinancing[SubsidyRefund]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getRefundSubsidi()));
            map.put("DetailFinancing[DiscountRateTimes]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBebasBunga()));
            map.put("DetailFinancing[PurchasePrice]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalPrice()));
            map.put("DetailFinancing[Discount]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalDiscount()));
            map.put("DetailFinancing[DownPayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalDp()));
            map.put("Insurance[PremiumAmountToCustomer]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getPremi()));
            map.put("DetailFinancing[NTF]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getNtf()));
            map.put("DetailFinancing[TotalFinancing]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getJumlahPembiayaan()));
            map.put("DetailFinancing[InterestFinancing]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalBungaPembiayaan()));
            map.put("DetailFinancing[MonthFinancingInterest]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBulanBungaPembiayaan()));
            map.put("DetailFinancing[TotalLoan]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalPinjaman()));
            map.put("DetailFinancing[FirstInstallmentAmmount]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getAngsuranPrebulanBebasBunga()));
            map.put("DetailFinancing[InstallmentAmount]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getAngsuranPerbulan()));
            map.put("DetailFinancing[FirstPayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getPembayaranAwal()));
            map.put("DetailFinancing[InterestFreeDealerPayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getPembayaranDelaer()));
            map.put("DetailFinancing[FirstInstallment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTipePembayaran()));
            map.put("DetailFinancing[EffectiveRate]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getEffectiveRate()));
        }

//        KETERANGAN
        List<TblKeterangan> tblKeterangans = new ArrayList<>();
        try {
            tblKeterangans = databaseService.getTblKeteranganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblKeterangan) {
            if (BuildConfig.DEBUG) Log.e("eTblKeterangan", String.valueOf(eTblKeterangan));
            Crashlytics.logException(eTblKeterangan);
        }
        for (int i = 0; i < tblKeterangans.size(); i++) map.put("KorpFormulir[Keterangan]", Util.toTextRequestBody(tblKeterangans.get(i).getKeterangan()));

//        REKOEMENDASI
        List<TblRekomendasi> tblRekomendasis = new ArrayList<>();
        try {
            tblRekomendasis = databaseService.getTblRekomendasiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblRekomendasi) {
            if (BuildConfig.DEBUG) Log.e("eTblRekomendasi", String.valueOf(eTblRekomendasi));
            Crashlytics.logException(eTblRekomendasi);
        }
        for (int i = 0; i < tblRekomendasis.size(); i++) {
            map.put("Application[IsJabarRecomendation]", Util.toTextRequestBody("0"));
            map.put("Application[ReasonRecomendationId]", Util.toTextRequestBody(tblRekomendasis.get(i).getIdRekomendasi()));
            map.put("Application[ReasonRecomendationNotes]", Util.toTextRequestBody(tblRekomendasis.get(i).getCatatan()));
        }

//        LOKASI
        List<TblLokasi> tblLokasis = new ArrayList<>();
        try {
            tblLokasis = databaseService.getTblLokasiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblLokasi) {
            if (BuildConfig.DEBUG) Log.e("eTblLokasi", String.valueOf(eTblLokasi));
            Crashlytics.logException(eTblLokasi);
        }
        for (int i = 0; i < tblLokasis.size(); i++) {
            map.put("Location[ValidateAction]", Util.toTextRequestBody(tblLokasis.get(i).getValidationAction()));
            map.put("Location[ValidateLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getValidationLongitude()));
            map.put("Location[ValidateLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getValidationLatitude()));
            map.put("Location[TakeKtpAction]", Util.toTextRequestBody(tblLokasis.get(i).getKtpAction()));
            map.put("Location[TakeKtpLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getKtpLongitude()));
            map.put("Location[TakeKtpLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getKtpLatitude()));
            map.put("Location[TakeCustomerAction]", Util.toTextRequestBody(tblLokasis.get(i).getCustomerAction()));
            map.put("Location[TakeCustomerLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getCustomerLongitude()));
            map.put("Location[TakeCustomerLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getCustomerLatitude()));
            map.put("Location[TakePaycheckAction]", Util.toTextRequestBody(tblLokasis.get(i).getPaycheckAction()));
            map.put("Location[TakePaycheckLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getPaycheckLongitude()));
            map.put("Location[TakePaycheckLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getPaycheckLatitude()));
            map.put("Location[TakeAdditionalDocumentsAction]", Util.toTextRequestBody(tblLokasis.get(i).getAddtionalDocumentsAction()));
            map.put("Location[TakeAdditionalDocumentsLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getAddtionalDocumentsLongitude()));
            map.put("Location[TakeAdditionalDocumentsLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getAddtionalDocumentsLatitude()));
            map.put("Location[TakeSignatureAction]", Util.toTextRequestBody(tblLokasis.get(i).getSignatureAction()));
            map.put("Location[TakeSignatureLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getSignatureLongitude()));
            map.put("Location[TakeSignatureLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getSignatureLatitude()));
            map.put("Location[SubmitAction]", Util.toTextRequestBody(tblLokasis.get(i).getSubmitAction()));
            map.put("Location[SubmitLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getSubmitLongitude()));
            map.put("Location[SubmitLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getSubmitLatitude()));
            map.put("Location[SyncAction]", Util.toTextRequestBody(tblLokasis.get(i).getSyncAction()));
            map.put("Location[SyncLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getSyncLongitude()));
            map.put("Location[SyncLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getSyncLatitude()));
        }

//        TANDA TANGAN
        List<TblTandaTangan> tblTandaTangans = new ArrayList<>();
        try {
            tblTandaTangans = databaseService.getTblTandaTanganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eTblTandaTangan) {
            if (BuildConfig.DEBUG) Log.e("eTblTandaTangan", String.valueOf(eTblTandaTangan));
            Crashlytics.logException(eTblTandaTangan);
        }
        for (int i = 0; i < tblTandaTangans.size(); i++) {
            map.put("Signature[Applicant]", Util.toTextRequestBody(tblTandaTangans.get(i).getTtdPemohon()));
            map.put("Signature[ApplicantHusbandWife]", Util.toTextRequestBody(tblTandaTangans.get(i).getTtdPasangan()));
        }

        //        DETAIL ASSET
        List<AssetElektronik> assetElektronikList = new ArrayList<>();
        try {
            assetElektronikList = databaseService.getAssetDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuan).query();
        } catch (SQLException eAssetElektronik) {
            if (BuildConfig.DEBUG) Log.e("eAssetElektronik", String.valueOf(eAssetElektronik));
            Crashlytics.logException(eAssetElektronik);
        }

        for (int i = 0; i < assetElektronikList.size(); i++) {
            map.put("Asset[" + i + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(i + 1)));
            map.put("Asset[" + i + "][Category]", Util.toTextRequestBody(assetElektronikList.get(i).getCategory()));
            map.put("Asset[" + i + "][AssetCode]", Util.toTextRequestBody(assetElektronikList.get(i).getKodeBarang()));
            map.put("Asset[" + i + "][Type]", Util.toTextRequestBody(assetElektronikList.get(i).getType()));
            map.put("Asset[" + i + "][OTRPrice]", Util.toTextRequestBody(assetElektronikList.get(i).getPrice()));
            map.put("Asset[" + i + "][DPAmount]", Util.toTextRequestBody(assetElektronikList.get(i).getDp().isEmpty() ? "0" : assetElektronikList.get(i).getDp()));
            map.put("Asset[" + i + "][Discount]", Util.toTextRequestBody(assetElektronikList.get(i).getDiscount().isEmpty() ? "0" : assetElektronikList.get(i).getDiscount()));
            map.put("Asset[" + i + "][Description]",Util.toTextRequestBody(assetElektronikList.get(i).getNamaBarang()));
        }

        Log.d("tblMapAPI", String.valueOf(map));
        Log.d("tblMstrPengajuan", String.valueOf(masterFormPengajuan));
        Log.d("tblMstrMap", String.valueOf(masterFormPengajuan));

        if ("new".equalsIgnoreCase(masterFormPengajuan.getTipeSaveData())) {
            call = apiService.addApplication(token, map);
        } else if ("isAssignEdit".equalsIgnoreCase(masterFormPengajuan.getTipeSaveData())) {
            String id = masterFormPengajuan.getApplicationId();
            call = apiService.addApplicationEdit(token, map, id);
        } else if ("edit".equalsIgnoreCase(masterFormPengajuan.getTipeSaveData())) {
            call = apiService.addApplication(token, map);
        }
        call.enqueue(new Callback<BaseResponse<AddApplicationResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AddApplicationResponse>> call, Response<BaseResponse<AddApplicationResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        UpdateBuilder<MasterFormPengajuan, Integer> updateBuilder = databaseService.getMasterFormPengajuanDao().updateBuilder();
                        updateBuilder.where().eq("id", masterFormPengajuan.getId());
                        updateBuilder.updateColumnValue("status_sync", 1);
                        updateBuilder.update();
                    } catch (SQLException e) {
                        if (BuildConfig.DEBUG) Log.e("update status sync", String.valueOf(e));
                        Crashlytics.logException(e);
                    }
                    mSynchronizationMvpView.onSuccessSynchronization(response.body().getData().getApplicationId());
                } else if (response.code() == 403) {
                    mSynchronizationMvpView.onTokenSynchronization();
                } else if (response.code() == 422) {
                    try {
                        UpdateBuilder<MasterFormPengajuan, Integer> updateBuilder = databaseService.getMasterFormPengajuanDao().updateBuilder();
                        updateBuilder.where().eq("id", masterFormPengajuan.getId());
                        updateBuilder.updateColumnValue("tipe_save_data", "draft_baru");
                        updateBuilder.update();
                    } catch (SQLException e) {
                        if (BuildConfig.DEBUG) Log.e("Udpate form type", String.valueOf(e));
                        Crashlytics.logException(e);
                    }
                    mSynchronizationMvpView.onFailedSynchronization(errorRetrofitUtil.parseError(response));
                    EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());
                } else {
                    mSynchronizationMvpView.onFailedSynchronization(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AddApplicationResponse>> call, Throwable t) {
                if (!call.isCanceled()) {
                    if (mSynchronizationMvpView != null) mSynchronizationMvpView.onFailedSynchronization(errorRetrofitUtil.responseFailedError(t));
                }
            }
        });
    }
}
