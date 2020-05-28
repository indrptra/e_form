package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.Attachment;
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
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.presenter.mvpview.LogOutUploadDraftMvpView;
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

public class LogoutUploadDraftPresenter implements BasePresenter<LogOutUploadDraftMvpView> {

    @Inject
    ApiService apiService;
    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;
    @Inject
    DatabaseService databaseService;

    private LogOutUploadDraftMvpView mLogOutUploadDraftMvpView;
    private Call<BaseResponse<Object>> callSave;
    private List<MasterFormPengajuan> masterFormPengajuanList;
    private String convertDate;
    private int count;
    private String premiManualString;
    private File compres;

    public LogoutUploadDraftPresenter() {App.appComponent().inject(this); }

    @Override
    public void attachView(LogOutUploadDraftMvpView view) { mLogOutUploadDraftMvpView = view;}

    @Override
    public void detachView() {if (callSave != null) callSave.cancel();}

    public void uploadDraft(String token, List<MasterFormPengajuan> masterFormPengajuanList) {
        this.masterFormPengajuanList = masterFormPengajuanList;
        mLogOutUploadDraftMvpView.onPreLogOutUploadDraft();
        count = 0;
        Map<String, RequestBody> map = new HashMap<>();
        List<MultipartBody.Part> multiparts = null;

        if (masterFormPengajuanList.isEmpty()) map.put("", Util.toTextRequestBodyNull());

        for (int z = 0; z < masterFormPengajuanList.size(); z++) {
            if (masterFormPengajuanList.get(z).getCreatedAt() == null) convertDate = Util.ConvertDate(Util.listPengajuanTimeFormat(new DateTime()));
            else convertDate = Util.ConvertDate(masterFormPengajuanList.get(z).getCreatedAt());

//            if ("isAssignEdit".equalsIgnoreCase(masterFormPengajuanList.get(z).getis())) {
//                map.put(i + "[UserApplication][isEdit]", Util.toTextRequestBody("1"));
//            } else {
//                map.put(i + "[UserApplication][isEdit]", Util.toTextRequestBody("0"));
//            }

//            map.put(z + "[UserApplication][isEdit]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getTipeSaveData()));
            map.put(z + "[KorpFormulir][DateStart]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getBlacklistDate()));
            map.put(z + "[KorpFormulir][BranchIdPrimary]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getMasterBranch()));
            map.put(z + "[KorpFormulir][BranchIdLintasCabang]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getBranch()));
            map.put(z + "[KorpFormulir][ApplicationIdKPM]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getIdKpm()));
            map.put(z + "[KorpFormulir][PID]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getUuid()));
            map.put(z + "[KorpFormulir][CustomerIdConfins]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getCustomerIdConfins()));
            map.put(z + "[Application][DataType]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getTipePengajuan()));
            map.put(z + "[Application][OfferingType]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getTipeDataOffering()));
            map.put(z + "[KorpFormulir][DataType]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getTipePengajuan()));
            map.put(z + "[mobile_submission_key]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getMobileSubmissionKey()));
            map.put(z + "[Application][EFNumber]", Util.toTextRequestBody(masterFormPengajuanList.get(z).getEfNumber()));
            map.put(z + "[KorpFormulir][created_at]", Util.toTextRequestBody(convertDate));

            /*KOP*/
            List<TblKop> tblKops = new ArrayList<>();
            try {
                tblKops = databaseService.getTblKopDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblKop) {
                if (BuildConfig.DEBUG) Log.e("TblKop", String.valueOf(eTblKop));
                Crashlytics.logException(eTblKop);
            }
            for (int i = 0; i < tblKops.size(); i++) {
                map.put(z + "[KorpFormulir][SalesMethod]", Util.toTextRequestBody(tblKops.get(i).getMetodePenjualan()));
                map.put(z + "[KorpFormulir][FinancingPurpose]", Util.toTextRequestBody(tblKops.get(i).getStatusCustomer()));
            }

            /*DATA PRIBADI*/
            List<TblDataPribadi> tblDataPribadis = new ArrayList<>();
            try {
                tblDataPribadis = databaseService.getTblDataPribadiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblDataPribadi) {
                if (BuildConfig.DEBUG) Log.e("TblDataPribadi", String.valueOf(eTblDataPribadi));
                Crashlytics.logException(eTblDataPribadi);
            }
            for (int i = 0; i < tblDataPribadis.size(); i++) {
                map.put(z + "[PersonalData][IDNumber]", Util.toTextRequestBody(tblDataPribadis.get(i).getNoKtp()));
                map.put(z + "[PersonalData][PersonalNPWP]", Util.toTextRequestBody(tblDataPribadis.get(i).getNoNpwp()));
                map.put(z + "[PersonalData][MaritalStatus]", Util.toTextRequestBody(tblDataPribadis.get(i).getStatusPernikahan()));
                map.put(z + "[PersonalData][BirthDate]", Util.toTextRequestBody(tblDataPribadis.get(i).getTanggalLahir()));
                map.put(z + "[PersonalData][MobilePhone]", Util.toTextRequestBody(tblDataPribadis.get(i).getNomorHandphone()));
                map.put(z + "[PersonalData][FullName]", Util.toTextRequestBody(tblDataPribadis.get(i).getNamaLengkap()));
                map.put(z + "[PersonalData][LegalName]", Util.toTextRequestBody(tblDataPribadis.get(i).getNamaKtp()));
                map.put(z + "[PersonalData][IDTypeIssuedDate]", Util.toTextRequestBody(tblDataPribadis.get(i).getTanngalTerbitKtp()));
                map.put(z + "[PersonalData][Gender]", Util.toTextRequestBody(tblDataPribadis.get(i).getJenisKelamin()));
                map.put(z + "[PersonalData][BirthPlace]", Util.toTextRequestBody(tblDataPribadis.get(i).getTempatLahir()));
                map.put(z + "[PersonalData][SurgateMotherName]", Util.toTextRequestBody(tblDataPribadis.get(i).getNamaIbuKandung()));
                map.put(z + "[PersonalData][Education]", Util.toTextRequestBody(tblDataPribadis.get(i).getStatusPendidikan()));
                map.put(z + "[PersonalData][HomeStatus]", Util.toTextRequestBody(tblDataPribadis.get(i).getStatusRumah()));
                map.put(z + "[PersonalData][StaySinceYear]", Util.toTextRequestBody(tblDataPribadis.get(i).getTinggalSejakTahun()));
                map.put(z + "[PersonalData][StaySinceMonth]", Util.toTextRequestBody(tblDataPribadis.get(i).getTinggalSejakBulan()));
                map.put(z + "[PersonalData][Religion]", Util.toTextRequestBody(tblDataPribadis.get(i).getAgama()));
                map.put(z + "[PersonalData][NumOfDependence]", Util.toTextRequestBody(tblDataPribadis.get(i).getJumlahTanggungan()));
                map.put(z + "[PersonalData][Email]", Util.toTextRequestBody(tblDataPribadis.get(i).getEmail()));
                map.put(z + "[PersonalData][Nationality]", Util.toTextRequestBody(tblDataPribadis.get(i).getWargaNegara()));
            }

//        DATA PASANGAN
            List<TblDataPasangan> tblDataPasangans = new ArrayList<>();
            try {
                tblDataPasangans = databaseService.getTblDataPasanganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblDataPasangan) {
                if (BuildConfig.DEBUG) Log.e("TblDataPasangan", String.valueOf(eTblDataPasangan));
                Crashlytics.logException(eTblDataPasangan);
            }
            for (int i = 0; i < tblDataPasangans.size(); i++) {
                map.put(z + "[FamilyData][0][SeqNo]", Util.toTextRequestBody("1"));
                map.put(z + "[FamilyData][0][Id]", Util.toTextRequestBody(tblDataPasangans.get(i).getIdPasangan()));
                map.put(z + "[FamilyData][0][Name]", Util.toTextRequestBody(tblDataPasangans.get(i).getNamaLengkap()));
                map.put(z + "[FamilyData][0][IDNumber]", Util.toTextRequestBody(tblDataPasangans.get(i).getNomorKtp()));
                map.put(z + "[FamilyData][0][BirthPlace]", Util.toTextRequestBody(tblDataPasangans.get(i).getTempatLahir()));
                map.put(z + "[FamilyData][0][BirthDate]", Util.toTextRequestBody(tblDataPasangans.get(i).getTanggalLahir()));
                map.put(z + "[FamilyData][0][Handphone]", Util.toTextRequestBody(tblDataPasangans.get(i).getNomorHandphone()));
            }

            /*ALAMAT TINGGAL DAN KTP*/
            List<TblAlamat> tblAlamats = new ArrayList<>();
            try {
                tblAlamats = databaseService.getTblAlamatDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblAlamat) {
                if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(eTblAlamat));
                Crashlytics.logException(eTblAlamat);
            }
            for (int i = 0; i < tblAlamats.size(); i++) {
                map.put(z + "[Residance][Address]", Util.toTextRequestBody(tblAlamats.get(i).getAlamatTinggal() + "|" +
                        tblAlamats.get(i).getKotaTinggal() + "|" +
                        tblAlamats.get(i).getKecamatanTinggal() + "|" +
                        tblAlamats.get(i).getKelurahanTinggal() + "|" +
                        tblAlamats.get(i).getZipcodeTinggal()));
                map.put(z + "[Residance][RT]", Util.toTextRequestBody(tblAlamats.get(i).getRtTinggal()));
                map.put(z + "[Residance][RW]", Util.toTextRequestBody(tblAlamats.get(i).getRwTinggal()));
                map.put(z + "[Residance][VillageCode]", Util.toTextRequestBody("1221"));
                map.put(z + "[Residance][AreaPhone]", Util.toTextRequestBody(tblAlamats.get(i).getKodeAreaTeleponTinggal()));
                map.put(z + "[Residance][Phone]", Util.toTextRequestBody(tblAlamats.get(i).getNomorTeleponTinggal()));

                map.put(z + "[Legal][Address]", Util.toTextRequestBody(tblAlamats.get(i).getAlamatKtp() + "|" +
                        tblAlamats.get(i).getKotaKtp()  + "|" +
                        tblAlamats.get(i).getKecamatanKtp()  + "|" +
                        tblAlamats.get(i).getKelurahanKtp()  + "|" +
                        tblAlamats.get(i).getZipcodeKtp()));
                map.put(z + "[Legal][RT]", Util.toTextRequestBody(tblAlamats.get(i).getRtKtp()));
                map.put(z + "[Legal][RW]", Util.toTextRequestBody(tblAlamats.get(i).getRwKtp()));
                map.put(z + "[Legal][VillageCode]", Util.toTextRequestBody("1221"));
                map.put(z + "[Legal][AreaPhone]", Util.toTextRequestBody(tblAlamats.get(i).getKodeAreaTeleponKtp()));
                map.put(z + "[Legal][Phone]", Util.toTextRequestBody(tblAlamats.get(i).getNomorTeleponKtp()));
            }

            /*KONTAK DARURAT*/
            List<TblKontakDarurat> tblKontakDarurats = new ArrayList<>();
            try {
                tblKontakDarurats = databaseService.getTblKontakDaruratDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblAlamat) {
                if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(eTblAlamat));
                Crashlytics.logException(eTblAlamat);
            }
            for (int i = 0; i < tblKontakDarurats.size(); i++) {
                map.put(z + "[EmergencyContact][Name]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNamaLengkap()));
                map.put(z + "[EmergencyContact][Relationship]", Util.toTextRequestBody(tblKontakDarurats.get(i).getHubunganKerabat()));
                map.put(z + "[EmergencyContact][Address]", Util.toTextRequestBody(tblKontakDarurats.get(i).getAlamat() + "|" +
                        tblKontakDarurats.get(i).getKota()  + "|" +
                        tblKontakDarurats.get(i).getKecamatan()  + "|" +
                        tblKontakDarurats.get(i).getKelurahan()  + "|" +
                        tblKontakDarurats.get(i).getZipcode()));
                map.put(z + "[EmergencyContact][RT]", Util.toTextRequestBody(tblKontakDarurats.get(i).getRt()));
                map.put(z + "[EmergencyContact][RW]", Util.toTextRequestBody(tblKontakDarurats.get(i).getRw()));
                map.put(z + "[EmergencyContact][VillageCode]", Util.toTextRequestBody("1221"));
                map.put(z + "[EmergencyContact][HomePhoneArea]", Util.toTextRequestBody(tblKontakDarurats.get(i).getKodeAreaTeleponRumah()));
                map.put(z + "[EmergencyContact][HomePhone]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNomorTeleponRumah()));
                map.put(z + "[EmergencyContact][OfficePhoneArea]", Util.toTextRequestBody(tblKontakDarurats.get(i).getKodeAreaTeleponKantor()));
                map.put(z + "[EmergencyContact][OfficePhone]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNomorTeleponKantor()));
                map.put(z + "[EmergencyContact][MobilePhone]", Util.toTextRequestBody(tblKontakDarurats.get(i).getNomorHandphone()));
            }

            /*DATA PEKERJAAN*/
            List<TblDataPekerjaan> tblDataPekerjaans = new ArrayList<>();
            try {
                tblDataPekerjaans = databaseService.getTblDataPekerjaanDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblAlamat) {
                if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(eTblAlamat));
                Crashlytics.logException(eTblAlamat);
            }
            for (int i = 0; i < tblDataPekerjaans.size(); i++) {
                map.put(z + "[Company][Name]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getNamaPerubahaan()));
                map.put(z + "[Company][Address]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getAlamat() + "|" +
                        tblDataPekerjaans.get(i).getKota()  + "|" +
                        tblDataPekerjaans.get(i).getKecamatan()  + "|" +
                        tblDataPekerjaans.get(i).getKelurahan()  + "|" +
                        tblDataPekerjaans.get(i).getZipcode()));
                map.put(z + "[Company][RT]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getRt()));
                map.put(z + "[Company][RW]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getRw()));
                map.put(z + "[Company][AreaPhone]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeAreaTelepon()));
                map.put(z + "[Company][Phone]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getNomorTelepon()));
                map.put(z + "[Company][EmploymentSinceYear]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getBekerjaSejak()));
                map.put(z + "[Company][ProfessionID]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeProfesi()));
                map.put(z + "[Company][Profession]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getProfesi()));
                map.put(z + "[Company][JobType]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeTipePekerjaan()));
//                map.put(z + "[Company][JobTypeName]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getTipePekerjaan()));
                map.put(z + "[Company][JobPosition]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodePosisiPekerjaan()));
//                map.put(z + "[Company][JobPositionName]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPosisiPekerjaan()));
                map.put(z + "[Company][IndustryTypeID]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getKodeIndustri()));
//                map.put(z + "[Company][IndustryTypeIDName]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getIndustri()));
                map.put(z + "[Company][MonthlyFixedIncome]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPenghasilanTetap()));
                map.put(z + "[Company][MonthlyVariableIncome]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPenghasilanLain()));
                map.put(z + "[Company][SpouseIncome]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getPenghasilanPasangan()));
                map.put(z + "[Company][LivingCostAmount]", Util.toTextRequestBody(tblDataPekerjaans.get(i).getBiayaHidup()));
                map.put(z + "[Company][VillageCode]", Util.toTextRequestBody("1221"));
                map.put(z + "[Company][IsAffiliateWithPP]", Util.toTextRequestBody("0"));
            }

            /*DATA KARTU KREDIT*/
            List<TblDataKartuKredit> tblDataKartuKredits = new ArrayList<>();
            try {
                tblDataKartuKredits = databaseService.getTblDataKartuKreditDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblDataKartuKredit) {
                if (BuildConfig.DEBUG) Log.e("eTblDataKartuKredit", String.valueOf(eTblDataKartuKredit));
                Crashlytics.logException(eTblDataKartuKredit);
            }
            for (int i = 0; i < tblDataKartuKredits.size(); i++) {
                map.put(z + "[DataCreditCard][BankName]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getNamaBank()));
                map.put(z + "[DataCreditCard][IDCard]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getNoKartuKredit()));
                map.put(z + "[DataCreditCard][CardType]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getJenisKartuKredit()));
                map.put(z + "[DataCreditCard][CardLimit]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getLimitKartuKredit()));
                map.put(z + "[DataCreditCard][MembershipOldYear]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getTahunKadaluarsaKartuKredit()));
                map.put(z + "[DataCreditCard][MembershipOldMonth]", Util.toTextRequestBody(tblDataKartuKredits.get(i).getBulanKadaluarsaKartuKredit()));
            }

            /*DATA KARTU MEMBERSHIP*/
            List<TblKartuMembership> tblKartuMemberships = new ArrayList<>();
            try {
                tblKartuMemberships = databaseService.getTblKartuMembershipDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblDataKartuKredit) {
                if (BuildConfig.DEBUG) Log.e("eTblDataKartuKredit", String.valueOf(eTblDataKartuKredit));
                Crashlytics.logException(eTblDataKartuKredit);
            }
            for (int i = 0; i < tblKartuMemberships.size(); i++) {
                map.put(z + "[MembershipCard][IDMember]", Util.toTextRequestBody(tblKartuMemberships.get(i).getNoMembership()));
                map.put(z + "[MembershipCard][EffectiveDate]", Util.toTextRequestBody(tblKartuMemberships.get(i).getTanggalEfektif()));
                map.put(z + "[MembershipCard][ExpiredDate]", Util.toTextRequestBody(tblKartuMemberships.get(i).getTanggalExipred()));
            }

            /*DETAIL PRODUCT*/
            List<TblDetailProduct> tblDetailProducts = new ArrayList<>();
            try {
                tblDetailProducts = databaseService.getTblDetailProductDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblDetailProduct) {
                if (BuildConfig.DEBUG) Log.e("eTblDetailProduct", String.valueOf(eTblDetailProduct));
                Crashlytics.logException(eTblDetailProduct);
            }
            for (int i = 0; i < tblDetailProducts.size(); i++) {
                map.put(z + "[AssetMaster][SupplierID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeSupplier()));
//                map.put(z + "[AssetMaster][SupplierIDName]", Util.toTextRequestBody(tblDetailProducts.get(i).getSupplier()));
                map.put(z + "[AssetMaster][SalesmanID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeMarketingSupplier()));
//                map.put(z + "[AssetMaster][SalesmanIDName]", Util.toTextRequestBody(tblDetailProducts.get(i).getMarketingSupplier()));
                map.put(z + "[DetailProduct][ProductID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeProductId()));
                map.put(z + "[DetailProduct][ProductOfferingID]", Util.toTextRequestBody(tblDetailProducts.get(i).getKodeProductOfferingId()));
//                map.put(z + "[DetailProduct][ProductOfferingIDName]", Util.toTextRequestBody(tblDetailProducts.get(i).getProductOffering()));
                map.put(z + "[DetailProduct][POS]", Util.toTextRequestBody(tblDetailProducts.get(i).getPosId()));
//                map.put(z + "[DetailProduct][POSName]", Util.toTextRequestBody(tblDetailProducts.get(i).getPos()));
                map.put(z + "[DetailProduct][NumOfAssetUnit]", Util.toTextRequestBody(tblDetailProducts.get(i).getJumlahAsset()));
                map.put(z + "[AssetMaster][SupplierBankAccountID]", Util.toTextRequestBody(tblDetailProducts.get(i).getIdBank()));
            }

            /*DATA ASURANSI*/
            List<TblAsuransi> tblAsuransis = new ArrayList<>();
            try {
                tblAsuransis = databaseService.getTblAsuransiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblAsuransi) {
                if (BuildConfig.DEBUG) Log.e("eTblAsuransi", String.valueOf(eTblAsuransi));
                Crashlytics.logException(eTblAsuransi);
            }
            for (int i = 0; i < tblAsuransis.size(); i++) {
                map.put(z + "[Insurance][CoverageType]", Util.toTextRequestBody(tblAsuransis.get(i).getManualAgunan()));
                String premiManualString;
                if (tblAsuransis.get(i).getManualAgunan().equalsIgnoreCase("TRUE")) premiManualString = "1";
                else premiManualString = "0";
                map.put(z + "[Insurance][IsPremiManual]", Util.toTextRequestBody(premiManualString));
                map.put(z + "[Insurance][IsPersonalAccident]", Util.toTextRequestBody("0"));
            }

            /*DATA PERHITUNGAN*/
            List<TblDataPerhitungan> tblDataPerhitungans = new ArrayList<>();
            try {
                tblDataPerhitungans = databaseService.getTblDataPerhitunganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblKeterangan) {
                if (BuildConfig.DEBUG) Log.e("eTblKeterangan", String.valueOf(eTblKeterangan));
                Crashlytics.logException(eTblKeterangan);
            }
            for (int i = 0; i < tblDataPerhitungans.size(); i++) {
                map.put(z + "[DetailProduct][Tenor]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTenor()));
                map.put(z + "[DetailFinancing][FlatRate]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getFlateRate()));
                map.put(z + "[DetailFinancing][AdminFee]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBiayaAdministrasi()));
                map.put(z + "[DetailFinancing][OtherFee]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBiayaLainnya()));
                map.put(z + "[DetailFinancing][TypePayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTipePembayaran()));
                map.put(z + "[DetailFinancing][SubsidyRefund]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getRefundSubsidi()));
                map.put(z + "[DetailFinancing][DiscountRateTimes]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBebasBunga()));
                map.put(z + "[DetailFinancing][PurchasePrice]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalPrice()));
                map.put(z + "[DetailFinancing][Discount]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalDiscount()));
                map.put(z + "[DetailFinancing][DownPayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalDp()));
                map.put(z + "[Insurance][PremiumAmountToCustomer]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getPremi()));
                map.put(z + "[DetailFinancing][NTF]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getNtf()));
                map.put(z + "[DetailFinancing][TotalFinancing]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getJumlahPembiayaan()));
                map.put(z + "[DetailFinancing][InterestFinancing]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalBungaPembiayaan()));
                map.put(z + "[DetailFinancing][MonthFinancingInterest]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getBulanBungaPembiayaan()));
                map.put(z + "[DetailFinancing][TotalLoan]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTotalPinjaman()));
                map.put(z + "[DetailFinancing][FirstInstallmentAmmount]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getAngsuranPrebulanBebasBunga()));
                map.put(z + "[DetailFinancing][InstallmentAmount]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getAngsuranPerbulan()));
                map.put(z + "[DetailFinancing][FirstPayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getPembayaranAwal()));
                map.put(z + "[DetailFinancing][InterestFreeDealerPayment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getPembayaranDelaer()));
                map.put(z + "[DetailFinancing][FirstInstallment]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getTipePembayaran()));
                map.put(z + "[DetailFinancing][EffectiveRate]", Util.toTextRequestBody(tblDataPerhitungans.get(i).getEffectiveRate()));
            }

            /*KETERANGAN*/
            List<TblKeterangan> tblKeterangans = new ArrayList<>();
            try {
                tblKeterangans = databaseService.getTblKeteranganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblKeterangan) {
                if (BuildConfig.DEBUG) Log.e("eTblKeterangan", String.valueOf(eTblKeterangan));
                Crashlytics.logException(eTblKeterangan);
            }
            for (int i = 0; i < tblKeterangans.size(); i++) map.put(z + "[KorpFormulir][Keterangan]", Util.toTextRequestBody(tblKeterangans.get(i).getKeterangan()));

            /*REKOEMENDASI*/
            List<TblRekomendasi> tblRekomendasis = new ArrayList<>();
            try {
                tblRekomendasis = databaseService.getTblRekomendasiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblRekomendasi) {
                if (BuildConfig.DEBUG) Log.e("eTblRekomendasi", String.valueOf(eTblRekomendasi));
                Crashlytics.logException(eTblRekomendasi);
            }
            for (int i = 0; i < tblRekomendasis.size(); i++) {
                map.put(z + "[Application][IsJabarRecomendation]", Util.toTextRequestBody("0"));
                map.put(z + "[Application][ReasonRecomendationId]", Util.toTextRequestBody(tblRekomendasis.get(i).getIdRekomendasi()));
                map.put(z + "[Application][ReasonRecomendationNotes]", Util.toTextRequestBody(tblRekomendasis.get(i).getCatatan()));
            }

            /*LOKASI*/
            List<TblLokasi> tblLokasis = new ArrayList<>();
            try {
                tblLokasis = databaseService.getTblLokasiDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblLokasi) {
                if (BuildConfig.DEBUG) Log.e("eTblLokasi", String.valueOf(eTblLokasi));
                Crashlytics.logException(eTblLokasi);
            }
            for (int i = 0; i < tblLokasis.size(); i++) {
                map.put(z + "[Location][ValidateAction]", Util.toTextRequestBody(tblLokasis.get(i).getValidationAction()));
                map.put(z + "[Location][ValidateLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getValidationLongitude()));
                map.put(z + "[Location][ValidateLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getValidationLatitude()));
                map.put(z + "[Location][TakeKtpAction]", Util.toTextRequestBody(tblLokasis.get(i).getKtpAction()));
                map.put(z + "[Location][TakeKtpLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getKtpLongitude()));
                map.put(z + "[Location][TakeKtpLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getKtpLatitude()));
                map.put(z + "[Location][TakeCustomerAction]", Util.toTextRequestBody(tblLokasis.get(i).getCustomerAction()));
                map.put(z + "[Location][TakeCustomerLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getCustomerLongitude()));
                map.put(z + "[Location][TakeCustomerLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getCustomerLatitude()));
                map.put(z + "[Location][TakePaycheckAction]", Util.toTextRequestBody(tblLokasis.get(i).getPaycheckAction()));
                map.put(z + "[Location][TakePaycheckLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getPaycheckLongitude()));
                map.put(z + "[Location][TakePaycheckLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getPaycheckLatitude()));
                map.put(z + "[Location][TakeAdditionalDocumentsAction]", Util.toTextRequestBody(tblLokasis.get(i).getAddtionalDocumentsAction()));
                map.put(z + "[Location][TakeAdditionalDocumentsLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getAddtionalDocumentsLongitude()));
                map.put(z + "[Location][TakeAdditionalDocumentsLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getAddtionalDocumentsLatitude()));
                map.put(z + "[Location][TakeSignatureAction]", Util.toTextRequestBody(tblLokasis.get(i).getSignatureAction()));
                map.put(z + "[Location][TakeSignatureLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getSignatureLongitude()));
                map.put(z + "[Location][TakeSignatureLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getSignatureLatitude()));
                map.put(z + "[Location][SubmitAction]", Util.toTextRequestBody(tblLokasis.get(i).getSubmitAction()));
                map.put(z + "[Location][SubmitLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getSubmitLongitude()));
                map.put(z + "[Location][SubmitLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getSubmitLatitude()));
                map.put(z + "[Location][SyncAction]", Util.toTextRequestBody(tblLokasis.get(i).getSyncAction()));
                map.put(z + "[Location][SyncLongitude]", Util.toTextRequestBody(tblLokasis.get(i).getSyncLongitude()));
                map.put(z + "[Location][SyncLatitude]", Util.toTextRequestBody(tblLokasis.get(i).getSyncLatitude()));
            }

            /*TANDA TANGAN*/
            List<TblTandaTangan> tblTandaTangans = new ArrayList<>();
            try {
                tblTandaTangans = databaseService.getTblTandaTanganDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eTblTandaTangan) {
                if (BuildConfig.DEBUG) Log.e("eTblTandaTangan", String.valueOf(eTblTandaTangan));
                Crashlytics.logException(eTblTandaTangan);
            }
            for (int i = 0; i < tblTandaTangans.size(); i++) {
                map.put(z + "[Signature][Applicant]", Util.toTextRequestBody(tblTandaTangans.get(i).getTtdPemohon()));
                map.put(z + "[Signature][ApplicantHusbandWife]", Util.toTextRequestBody(tblTandaTangans.get(i).getTtdPasangan()));
            }

            /*DETAIL ASSET*/
            List<AssetElektronik> assetElektronikList = new ArrayList<>();
            try {
                assetElektronikList = databaseService.getAssetDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
            } catch (SQLException eAssetElektronik) {
                if (BuildConfig.DEBUG) Log.e("eAssetElektronik", String.valueOf(eAssetElektronik));
                Crashlytics.logException(eAssetElektronik);
            }

            if (assetElektronikList.isEmpty()) {
                map.put(z + "[Asset]", Util.toTextRequestBody(String.valueOf(0)));
            } else {
                for (int i = 0; i < assetElektronikList.size(); i++) {
                    map.put(z + "[Asset][" + i + "][AssetSeqNo]", Util.toTextRequestBody(String.valueOf(i + 1)));
                    map.put(z + "[Asset][" + i + "][AssetCode]", Util.toTextRequestBody(assetElektronikList.get(i).getKodeBarang()));
                    map.put(z + "[Asset][" + i + "][Type]", Util.toTextRequestBody(assetElektronikList.get(i).getType()));
                    map.put(z + "[Asset][" + i + "][OTRPrice]", Util.toTextRequestBody(assetElektronikList.get(i).getPrice()));
                    map.put(z + "[Asset][" + i + "][DPAmount]", Util.toTextRequestBody(assetElektronikList.get(i).getDp().isEmpty() ? "0" : assetElektronikList.get(i).getDp()));
                    map.put(z + "[Asset][" + i + "][Discount]", Util.toTextRequestBody(assetElektronikList.get(i).getDiscount().isEmpty() ? "0" : assetElektronikList.get(i).getDiscount()));
                }
            }

            /*Attachment*/
            List<Attachment> attachmentList = new ArrayList<>();
            try {
                attachmentList = databaseService.getAttachmentDao().queryBuilder().where().eq("pengajuan_id", masterFormPengajuanList.get(z)).query();
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

                    if (fileTmp1 != null || fileTmp2 != null) {
                        try {
                            compres = new Compressor(mLogOutUploadDraftMvpView.getContext())
                                    .setMaxHeight(720)
                                    .setMaxWidth(1080)
                                    .setQuality(25)
                                    .compressToFile(fileTmp1 == null ? fileTmp2 : fileTmp1);
                        } catch (IOException e) {
                            if (BuildConfig.DEBUG)
                                Log.e("Gagal Kompress", e.getMessage());
                            Crashlytics.logException(e);
                            mLogOutUploadDraftMvpView.onFailedLogOutUploadDraft("Gagal Kompresi Gambar");
                            return;
                        }
                    } else {
                        for (int y = 0; y < tblDataPribadis.size(); y++) {
                            mLogOutUploadDraftMvpView.onFailedLogOutUploadDraft("Tidak menemukan data gambar untuk pengajuan" + " " + tblDataPribadis.get(y).getNamaLengkap());
                        }
                        return;
                    }
                    multiparts.add(prepareFilePart(z + "[Attachment][" + k + "]", compres));
                }
            }
        }

        Log.d("tblMstrPengajuan", String.valueOf(masterFormPengajuanList));
        Log.d("logMap", String.valueOf(map));
        callSave = apiService.addSaveDraft(token, map, multiparts);
        callSave.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    mLogOutUploadDraftMvpView.onSuccessLogOutUploadDraft();
                } else if (response.code() == 204) {
                    mLogOutUploadDraftMvpView.onSuccessLogOutUploadDraft();
                } else if (response.code() == 403) {
                    mLogOutUploadDraftMvpView.onTokenLogOutUploadDraft();
                } else {
                    mLogOutUploadDraftMvpView.onFailedLogOutUploadDraft(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                if (!callSave.isCanceled()) {
                    if (count < 3) {
                        callSave.clone().enqueue(this);
                        count += 1;
                    } else {
                        if (mLogOutUploadDraftMvpView != null) {
                            mLogOutUploadDraftMvpView.onFailedLogOutUploadDraft(errorRetrofitUtil.responseFailedError(t));
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
        RequestBody requestFile = Util.toImageRequestBody(file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);

    }
}
