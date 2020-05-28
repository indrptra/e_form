package com.kreditplus.eform.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.Aobranch;
import com.kreditplus.eform.model.BranchMaster;
import com.kreditplus.eform.model.DataFinansial;
import com.kreditplus.eform.model.ResultAobranch;
import com.kreditplus.eform.model.TblNewIndustryTypeMaster;
import com.kreditplus.eform.model.TblNewKelurahan;
import com.kreditplus.eform.model.TblNewProfJobPosition;
import com.kreditplus.eform.model.TblNewProfJobType;
import com.kreditplus.eform.model.TblNewProfession;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.MasterDataResponse;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AoBranchMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.DataFinansialSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.IndustryTypeSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.KelurahanSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProffesionJobTypeSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProffesionSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.TypeJobPositionSync;
import com.kreditplus.eform.presenter.mvpview.MasterDataMvpView;
import com.kreditplus.eform.service.ApiService;
import com.kreditplus.eform.service.DatabaseService;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterDataPresenter implements BasePresenter<MasterDataMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    @Inject
    DatabaseService databaseService;

    private MasterDataMvpView masterDataMvpView;
    private Call<BaseResponse<MasterDataResponse>> callBack;
    private int count;
    private int countMasterData;

    public MasterDataPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(MasterDataMvpView view) {
        masterDataMvpView = view;
    }

    @Override
    public void detachView() {
        if (callBack != null) callBack.cancel();
        masterDataMvpView = null;
    }

    public void MasterData(String token, String develop) {
        count = 0;
        countMasterData = 0;
        masterDataMvpView.onPreMasterData();
        callBack = apiService.masterData(token, develop);
        callBack.enqueue(new Callback<BaseResponse<MasterDataResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<MasterDataResponse>> call, Response<BaseResponse<MasterDataResponse>> response) {
                count = 0;
                if (response.isSuccessful()) {
                    new SyncQuery(response).execute();
                } else if (response.code() == 403) {
                    masterDataMvpView.onTokenMasterDataExpired();
                } else {
                    masterDataMvpView.onFailedMasterData(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MasterDataResponse>> call, Throwable t) {
                if (!callBack.isCanceled()) {
                    if (count < 3) {
                        count += 1;
                        call.clone().enqueue(this);
                    } else {
                        if (masterDataMvpView != null) {
                            masterDataMvpView.onFailedMasterData(errorRetrofitUtil.responseFailedError(t));
                        }
                    }

                }
            }
        });
    }

    private class SyncQuery extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private String branchPrimary;
        private List<IndustryTypeSync> industryTypeSyncList = new ArrayList<>();
        private List<KelurahanSync> kelurahanSyncList = new ArrayList<>();
        private List<ProffesionSync> proffesionSyncList = new ArrayList<>();
        private List<TypeJobPositionSync> typeJobPositionSyncList = new ArrayList<>();
        private List<ProffesionJobTypeSync> proffesionJobTypeSyncList = new ArrayList<>();
        private DataFinansialSync dataFinansialSync = new DataFinansialSync();
        private List<AoBranchMasterSync> aobranchSyncList = new ArrayList<>();

        public SyncQuery(Response<BaseResponse<MasterDataResponse>> response) {
            this.industryTypeSyncList = response.body().getData().getIndustryTypeSyncList();
            this.kelurahanSyncList = response.body().getData().getKelurahanSyncList();
            this.proffesionSyncList = response.body().getData().getProffesionSyncList();
            this.proffesionJobTypeSyncList = response.body().getData().getProffesionJobTypeSyncList();
            this.typeJobPositionSyncList = response.body().getData().getTypeJobPositionSyncList();
            this.dataFinansialSync = response.body().getData().getDatafinancial();
            this.aobranchSyncList = response.body().getData().getAoBranch();
            this.branchPrimary = response.body().getData().getBranchPrimary();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                databaseService.getIndustryTypeMasterDao().callBatchTasks(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        for (int d = 0; d < industryTypeSyncList.size(); d++) {
                            final TblNewIndustryTypeMaster industryTypeMaster = new TblNewIndustryTypeMaster();
                            List<TblNewIndustryTypeMaster> listIndustryTypeMaster = new ArrayList<>();

                            industryTypeMaster.setIndustryTypeID(industryTypeSyncList.get(d).getIdIndustryType());
                            industryTypeMaster.setDescription(industryTypeSyncList.get(d).getDescrption().trim());
                            industryTypeMaster.setIsActive(industryTypeSyncList.get(d).getIsActive());

                            listIndustryTypeMaster = databaseService.getIndustryTypeMasterDao().queryBuilder().where().eq("IndustryTypeID", industryTypeSyncList.get(d).getIdIndustryType()).query();
                            if (!listIndustryTypeMaster.isEmpty()) {
                                UpdateBuilder<TblNewIndustryTypeMaster, String> updtTblNewIndustryTypeMaster = databaseService.getIndustryTypeMasterDao().updateBuilder();
                                updtTblNewIndustryTypeMaster.where().eq("IndustryTypeID", industryTypeSyncList.get(d).getIdIndustryType());
                                updtTblNewIndustryTypeMaster.updateColumnValue("IndustryTypeID", industryTypeSyncList.get(d).getIdIndustryType());
                                updtTblNewIndustryTypeMaster.updateColumnValue("Description", industryTypeSyncList.get(d).getDescrption().trim());
                                updtTblNewIndustryTypeMaster.updateColumnValue("IsActive", industryTypeSyncList.get(d).getIsActive());
                            } else {
                                databaseService.getIndustryTypeMasterDao().create(industryTypeMaster);
                            }
                        }
                        countMasterData++;
                        masterDataMvpView.onUpdateCountMasterData(countMasterData);
                        return null;
                    }
                });
            } catch (Exception e) {
                if (BuildConfig.DEBUG) Log.e("MD_Industri", String.valueOf(e));
                Crashlytics.logException(e);
            }
//            +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            try {
                databaseService.getKelurahanDao().callBatchTasks(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        for (int e = 0; e < kelurahanSyncList.size(); e++) {
                            final TblNewKelurahan kelurahan = new TblNewKelurahan();
                            List<TblNewKelurahan> listkelurahan = new ArrayList<>();

                            kelurahan.setCity(kelurahanSyncList.get(e).getCity());
                            kelurahan.setKecamatan(kelurahanSyncList.get(e).getKecamatan());
                            kelurahan.setKelurahan(kelurahanSyncList.get(e).getKelurahan());
                            kelurahan.setZipCode(kelurahanSyncList.get(e).getZipCode());
                            kelurahan.setIsActive(kelurahanSyncList.get(e).getIsactive());

                            listkelurahan = databaseService.getKelurahanDao().queryBuilder().where().eq("City", kelurahanSyncList.get(e).getKelurahan())
                                    .and().eq("Kelurahan", kelurahanSyncList.get(e).getKelurahan())
                                    .and().eq("Kecamatan", kelurahanSyncList.get(e).getKecamatan())
                                    .and().eq("ZipCode", kelurahanSyncList.get(e).getZipCode()).query();
                            if (!listkelurahan.isEmpty()) {
                                UpdateBuilder<TblNewKelurahan, String> updtTblNewKelurahan = databaseService.getKelurahanDao().updateBuilder();
                                updtTblNewKelurahan.where().eq("City", kelurahanSyncList.get(e).getCity())
                                        .and().eq("Kelurahan", kelurahanSyncList.get(e).getKelurahan())
                                        .and().eq("Kecamatan", kelurahanSyncList.get(e).getKecamatan())
                                        .and().eq("ZipCode", kelurahanSyncList.get(e).getZipCode());
                                updtTblNewKelurahan.updateColumnValue("City", kelurahanSyncList.get(e).getCity());
                                updtTblNewKelurahan.updateColumnValue("Kecamatan", kelurahanSyncList.get(e).getKecamatan());
                                updtTblNewKelurahan.updateColumnValue("Kelurahan", kelurahanSyncList.get(e).getKelurahan());
                                updtTblNewKelurahan.updateColumnValue("ZipCode", kelurahanSyncList.get(e).getZipCode());
                                updtTblNewKelurahan.updateColumnValue("IsActive", kelurahanSyncList.get(e).getIsactive());
                            } else {
                                databaseService.getKelurahanDao().create(kelurahan);
                            }
                        }
                        countMasterData++;
                        masterDataMvpView.onUpdateCountMasterData(countMasterData);
                        return null;
                    }
                });
            } catch (Exception e) {
                if (BuildConfig.DEBUG) Log.e("MD_Kelurahan", String.valueOf(e));
                Crashlytics.logException(e);
            }
//            +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            try {
                databaseService.getProfessionDao().callBatchTasks(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        for (int f = 0; f < proffesionSyncList.size(); f++) {
                            final TblNewProfession profession = new TblNewProfession();
                            List<TblNewProfession> listprofession = new ArrayList<>();

                            profession.setProfessionID(proffesionSyncList.get(f).getProfessionId());
                            profession.setDescription(proffesionSyncList.get(f).getDescription());
                            profession.setIsActive(proffesionSyncList.get(f).getIsActive());

                            listprofession = databaseService.getProfessionDao().queryBuilder().where().eq("ProfessionID", proffesionSyncList.get(f).getProfessionId()).query();
                            if (!listprofession.isEmpty()) {
                                UpdateBuilder<TblNewProfession, String> updtTblNewProfession = databaseService.getProfessionDao().updateBuilder();
                                updtTblNewProfession.where().eq("ProfessionID", proffesionSyncList.get(f).getProfessionId());
                                updtTblNewProfession.updateColumnValue("ProfessionID", proffesionSyncList.get(f).getProfessionId());
                                updtTblNewProfession.updateColumnValue("Description", proffesionSyncList.get(f).getDescription());
                                updtTblNewProfession.updateColumnValue("IsActive", proffesionSyncList.get(f).getIsActive());
                            } else {
                                databaseService.getProfessionDao().create(profession);
                            }
                        }
                        countMasterData++;
                        masterDataMvpView.onUpdateCountMasterData(countMasterData);
                        return null;
                    }
                });
            } catch (Exception e) {
                if (BuildConfig.DEBUG) Log.e("MD_Profession", String.valueOf(e));
                Crashlytics.logException(e);
            }
//            +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            try {
                databaseService.getProfJobPositionDao().callBatchTasks(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        for (int g = 0; g < typeJobPositionSyncList.size(); g++) {
                            final TblNewProfJobPosition jobPosition = new TblNewProfJobPosition();
                            List<TblNewProfJobPosition> listjobPosition = new ArrayList<>();

                            jobPosition.setJobTypeID(typeJobPositionSyncList.get(g).getJobTypeId());
                            jobPosition.setJobPositionID(typeJobPositionSyncList.get(g).getJobPositionId());
                            jobPosition.setDescription(typeJobPositionSyncList.get(g).getDescription());
                            jobPosition.setIsActive(typeJobPositionSyncList.get(g).getIsActive());

                            listjobPosition = databaseService.getProfJobPositionDao().queryBuilder().where().eq("JobTypeID", typeJobPositionSyncList.get(g).getJobTypeId())
                                    .and().eq("JobPositionID", typeJobPositionSyncList.get(g).getJobPositionId())
                                    .and().eq("Description", typeJobPositionSyncList.get(g).getDescription()).query();

                            if (!listjobPosition.isEmpty()) {
                                UpdateBuilder<TblNewProfJobPosition, String> updtTblNewProfJobPosition = databaseService.getProfJobPositionDao().updateBuilder();
                                updtTblNewProfJobPosition.where().eq("JobTypeID", typeJobPositionSyncList.get(g).getJobTypeId())
                                        .and().eq("JobPositionID", typeJobPositionSyncList.get(g).getJobPositionId())
                                        .and().eq("Description", typeJobPositionSyncList.get(g).getDescription());
                                updtTblNewProfJobPosition.updateColumnValue("JobTypeID", typeJobPositionSyncList.get(g).getJobTypeId());
                                updtTblNewProfJobPosition.updateColumnValue("JobPositionID", typeJobPositionSyncList.get(g).getJobPositionId());
                                updtTblNewProfJobPosition.updateColumnValue("Description", typeJobPositionSyncList.get(g).getDescription());
                                updtTblNewProfJobPosition.updateColumnValue("IsActive", typeJobPositionSyncList.get(g).getIsActive());
                            } else {
                                databaseService.getProfJobPositionDao().create(jobPosition);
                            }
                        }
                        countMasterData++;
                        masterDataMvpView.onUpdateCountMasterData(countMasterData);
                        return null;
                    }
                });
            } catch (Exception e) {
                if (BuildConfig.DEBUG) Log.e("MD_ProfJobPosition", String.valueOf(e));
                Crashlytics.logException(e);
            }
//            +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            try {
                databaseService.getProfJobTypeDao().callBatchTasks(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        for (int h = 0; h < proffesionJobTypeSyncList.size(); h++) {
                            final TblNewProfJobType jobType = new TblNewProfJobType();
                            List<TblNewProfJobType> listjobType = new ArrayList<>();

                            jobType.setProfessionID(proffesionJobTypeSyncList.get(h).getProfessionId());
                            jobType.setJobTypeID(proffesionJobTypeSyncList.get(h).getJobTypeId());
                            jobType.setDescription(proffesionJobTypeSyncList.get(h).getDescription());
                            jobType.setIsActive(proffesionJobTypeSyncList.get(h).getIsActive());

                            listjobType = databaseService.getProfJobTypeDao().queryBuilder().where().eq("JobTypeID", proffesionJobTypeSyncList.get(h).getJobTypeId())
                                    .and().eq("ProfessionID", proffesionJobTypeSyncList.get(h).getProfessionId())
                                    .and().eq("Description", proffesionJobTypeSyncList.get(h).getDescription())
                                    .and().eq("IsActive", proffesionJobTypeSyncList.get(h).getIsActive()).query();
                            if (!listjobType.isEmpty()) {
                                UpdateBuilder<TblNewProfJobType, String> updtTblNewProfJobType = databaseService.getProfJobTypeDao().updateBuilder();
                                updtTblNewProfJobType.where().eq("JobTypeID", proffesionJobTypeSyncList.get(h).getJobTypeId())
                                        .and().eq("ProfessionID", proffesionJobTypeSyncList.get(h).getProfessionId())
                                        .and().eq("Description", proffesionJobTypeSyncList.get(h).getDescription())
                                        .and().eq("IsActive", proffesionJobTypeSyncList.get(h).getIsActive());

                                updtTblNewProfJobType.updateColumnValue("ProfessionID", proffesionJobTypeSyncList.get(h).getProfessionId());
                                updtTblNewProfJobType.updateColumnValue("JobTypeID", proffesionJobTypeSyncList.get(h).getJobTypeId());
                                updtTblNewProfJobType.updateColumnValue("Description", proffesionJobTypeSyncList.get(h).getDescription());
                                updtTblNewProfJobType.updateColumnValue("IsActive", proffesionJobTypeSyncList.get(h).getIsActive());
                            } else {
                                databaseService.getProfJobTypeDao().create(jobType);
                            }
                        }
                        countMasterData++;
                        masterDataMvpView.onUpdateCountMasterData(countMasterData);
                        return null;
                    }
                });
            } catch (Exception e) {
                if (BuildConfig.DEBUG) Log.e("Master Data ProfjobType", String.valueOf(e));
                Crashlytics.logException(e);
            }
//            AOBRANCH ====================================================================================
            for (int a = 0; a < aobranchSyncList.size(); a++) {
                final Aobranch aobranch = new Aobranch();
                final ResultAobranch resultAobranch = new ResultAobranch();
                List<Aobranch> aobranchCekList;
                String Date = Util.TanggalHariIni(new DateTime());

                aobranch.setBranchIdAo(aobranchSyncList.get(a).getBranchId());
                aobranch.setIsActive(aobranchSyncList.get(a).getIsActive());
                aobranch.setBranchName(aobranchSyncList.get(a).getBranchFullName());
                if (aobranchSyncList.get(a).getBranchId().equalsIgnoreCase(branchPrimary)) {
                    aobranch.setFirstSyncDate(Date); // aobranch yang sama dengan master branch field first data diberi nilai date dahulu agar tidak  sync ulang pada saat pengajuan baru
                } else {
                    aobranch.setFirstSyncDate("-");
                }

                //result branch
                resultAobranch.setBranchId(aobranchSyncList.get(a).getBranchId());
                resultAobranch.setResultBranch(aobranchSyncList.get(a).getBranchId() + " - " + aobranchSyncList.get(a).getBranchFullName());
                resultAobranch.setIsActive(aobranchSyncList.get(a).getIsActive());
                try {
                    aobranchCekList = databaseService.getAobranchDao().queryBuilder().where().eq("BranchIdAo", aobranchSyncList.get(a).getBranchId()).query();
                    if (!aobranchCekList.isEmpty()) {
                        UpdateBuilder<Aobranch, String> updateAobranch = databaseService.getAobranchDao().updateBuilder();
                        updateAobranch.where().eq("BranchIdAo", aobranchSyncList.get(a).getBranchId());
                        updateAobranch.updateColumnValue("BranchIdAo", aobranchSyncList.get(a).getBranchId());
                        updateAobranch.updateColumnValue("IsActive", aobranchSyncList.get(a).getIsActive());
                        updateAobranch.update();
                        // result branch
                        UpdateBuilder<ResultAobranch, String> updateResult = databaseService.getResultAobranchDao().updateBuilder();
                        updateResult.where().eq("Branchid", aobranchSyncList.get(a).getBranchId());
                        updateResult.updateColumnValue("ResultBranch", aobranchSyncList.get(a).getBranchId() + " - " + aobranchSyncList.get(a).getBranchFullName());
                        updateResult.updateColumnValue("isActive", aobranchSyncList.get(a).getIsActive());
                        updateResult.update();
                    } else {
                        databaseService.getAobranchDao().callBatchTasks(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                databaseService.getAobranchDao().create(aobranch);
                                return null;
                            }
                        });
                        databaseService.getResultAobranchDao().callBatchTasks(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                databaseService.getResultAobranchDao().create(resultAobranch);
                                return null;
                            }
                        });
                    }
                } catch (Exception e) {
                    if (BuildConfig.DEBUG)
                        Log.e("Aobranch", String.valueOf(e));
                    Crashlytics.logException(e);
                }

            }
//            BRANCH MASTER ====================================================================================
            try {
                databaseService.getBranchMasterDao().callBatchTasks(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        final BranchMaster branchMaster = new BranchMaster();
                        List<BranchMaster> branchMasterCekList = new ArrayList<>();
                        branchMaster.setBranchPrimary(branchPrimary);

                        try {
                            branchMasterCekList = databaseService.getBranchMasterDao().queryBuilder().where().eq("BranchPrimary", branchPrimary).query();
                            if (!branchMasterCekList.isEmpty()) {
                                UpdateBuilder<BranchMaster, String> updateBranchMaster = databaseService.getBranchMasterDao().updateBuilder();
                                updateBranchMaster.where().eq("BranchPrimary", branchPrimary);
                                updateBranchMaster.updateColumnValue("BranchPrimary", branchPrimary);
                                updateBranchMaster.update();
                            } else {
                                databaseService.getBranchMasterDao().create(branchMaster);
                            }
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG)
                                Log.e("Branch Master", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                if (BuildConfig.DEBUG)
                    Log.e("callback Branch", String.valueOf(e));
                Crashlytics.logException(e);
            }
//            DATA FINANSIAL ====================================================================================
            try {
                databaseService.getDataFinansialDao().callBatchTasks(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        DataFinansial dataFinansial = new DataFinansial();
                        List<DataFinansial> dataFinansials = new ArrayList<>();

                        dataFinansial.setPeopleInsuranceNominal(dataFinansialSync.getPeopleInsuranceNominal());
                        dataFinansial.setPeopleInsurancePersen(dataFinansialSync.getPeopleInsurancePersen());
                        dataFinansial.setProductInsuranceNominal(dataFinansialSync.getProductInsuranceNominal());
                        dataFinansial.setProductInsurancePersen(dataFinansialSync.getProductInsurancePersen());
                        dataFinansial.setTotalInsuranceNominal(dataFinansialSync.getTotalInsuranceNominal());
                        dataFinansial.setTotalInsurancePersen(dataFinansialSync.getTotalInsurancePersen());
                        dataFinansial.setAdminFeeLainnya(dataFinansialSync.getAdminFeeLainnya());

                        try {
                            dataFinansials = databaseService.getDataFinansialDao().queryBuilder().where()
                                    .eq("AdminFeeLainnya", dataFinansialSync.getAdminFeeLainnya()).or().eq("ProductInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
                                    .or().eq("ProductInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("PeopleInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
                                    .or().eq("PeopleInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("TotalInsurancePersen", dataFinansialSync.getTotalInsurancePersen())
                                    .or().eq("TotalInsuranceNominal", dataFinansialSync.getTotalInsuranceNominal()).query();
                            if (!dataFinansials.isEmpty()) {
                                UpdateBuilder<DataFinansial, String> updateFinansial = databaseService.getDataFinansialDao().updateBuilder();
                                updateFinansial.where().eq("AdminFeeLainnya", dataFinansialSync.getAdminFeeLainnya()).or().eq("ProductInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
                                        .or().eq("ProductInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("PeopleInsurancePersen", dataFinansialSync.getPeopleInsurancePersen())
                                        .or().eq("PeopleInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal()).or().eq("TotalInsurancePersen", dataFinansialSync.getTotalInsurancePersen())
                                        .or().eq("TotalInsuranceNominal", dataFinansialSync.getTotalInsuranceNominal());
                                updateFinansial.updateColumnValue("AdminFeeLainnya", dataFinansialSync.getAdminFeeLainnya());
                                updateFinansial.updateColumnValue("ProductInsurancePersen", dataFinansialSync.getPeopleInsurancePersen());
                                updateFinansial.updateColumnValue("ProductInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal());
                                updateFinansial.updateColumnValue("PeopleInsurancePersen", dataFinansialSync.getPeopleInsurancePersen());
                                updateFinansial.updateColumnValue("PeopleInsuranceNominal", dataFinansialSync.getPeopleInsuranceNominal());
                                updateFinansial.updateColumnValue("TotalInsurancePersen", dataFinansialSync.getTotalInsurancePersen());
                                updateFinansial.updateColumnValue("TotalInsuranceNominal", dataFinansialSync.getTotalInsuranceNominal());
                                updateFinansial.update();
                            } else {
                                databaseService.getDataFinansialDao().create(dataFinansial);
                            }
                        } catch (SQLException e) {
                            if (BuildConfig.DEBUG)
                                Log.e("Data Finansial", String.valueOf(e));
                            Crashlytics.logException(e);
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                if (BuildConfig.DEBUG)
                    Log.e("callback JobType", String.valueOf(e));
                Crashlytics.logException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (masterDataMvpView != null) masterDataMvpView.onSuccessMasterData();
        }
    }
}
