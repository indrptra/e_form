package com.kreditplus.eform.presenter;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.helper.ErrorRetrofitUtil;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.KreditmuResponse;
import com.kreditplus.eform.model.response.objecthelper.AttachmentObjt;
import com.kreditplus.eform.presenter.mvpview.KreditmuMvpView;
import com.kreditplus.eform.service.ApiService;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuPresenter implements BasePresenter<KreditmuMvpView>{

    @Inject
    ApiService apiService;

    @Inject
    ErrorRetrofitUtil errorRetrofitUtil;

    private KreditmuMvpView mKreditmuMvpView;
    private Call<BaseResponse<KreditmuResponse>> call;
    private List<MultipartBody.Part> multipartEmpty = null;
    private int count;
    private File compres;

    public KreditmuPresenter() {
        App.appComponent().inject(this);
    }

    @Override
    public void attachView(KreditmuMvpView view) {
        mKreditmuMvpView = view;
    }

    @Override
    public void detachView() {
        if (call != null) {
            call.cancel();
        }
        mKreditmuMvpView = null;
    }

    // untuk spinner
    public void spinnerKreditmu(String token, String option){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option", Util.toTextRequestBody(option));
        kreditmu(token,map,multipartEmpty);
    }

    public void cityKreditmu(String token){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option",Util.toTextRequestBody("city"));
        kreditmu(token,map,multipartEmpty);
    }

    public void kecamatanKreditmu(String token, String city){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option",Util.toTextRequestBody("kecamatan"));
        map.put("city",Util.toTextRequestBody(city));
        kreditmu(token,map,multipartEmpty);
    }

    public void kelurahanKreditmu(String token, String city, String kecamatan){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option",Util.toTextRequestBody("kelurahan"));
        map.put("city",Util.toTextRequestBody(city));
        map.put("kecamatan",Util.toTextRequestBody(kecamatan));
        kreditmu(token,map,multipartEmpty);
    }

    public void zipCodeKreditmu(String token, String city, String kecamatan, String kelurahan){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option",Util.toTextRequestBody("zipCode"));
        map.put("city",Util.toTextRequestBody(city));
        map.put("kecamatan",Util.toTextRequestBody(kecamatan));
        map.put("kelurahan",Util.toTextRequestBody(kelurahan));
        kreditmu(token, map,multipartEmpty);
    }

    public void professionKreditmu(String token){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option",Util.toTextRequestBody("professionId"));
        kreditmu(token, map,multipartEmpty);
    }

    public void jobTypeKreditmu(String token, String professionId){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option",Util.toTextRequestBody("jobType"));
        map.put("professionId",Util.toTextRequestBody(professionId));
        kreditmu(token, map,multipartEmpty);
    }

    public void jobPosition(String token, String professionId, String jobType){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("option",Util.toTextRequestBody("jobPosition"));
        map.put("professionId",Util.toTextRequestBody(professionId));
        map.put("jobTypeID",Util.toTextRequestBody(jobType));
        kreditmu(token, map,multipartEmpty);
    }

    public void submit(String token, Map<String, String> map, Map<String, String> mapJob, Map<String, String> mapSpinner,
                       List<AttachmentObjt> attachmentList, List<AttachmentObjt> signatureList){
        Map<String, RequestBody> mapSubmit = new HashMap<>();
        List<MultipartBody.Part> multiparts = null;

        mapSubmit.put("option",Util.toTextRequestBody("kreditmuPost"));
        mapSubmit.put("kreditmuPost[IDNumber]",Util.toTextRequestBody(map.get("Nomor KTP")));
        mapSubmit.put("kreditmuPost[CustomerName]",Util.toTextRequestBody(map.get("Nama lengkap pemohon")));
        mapSubmit.put("kreditmuPost[BirthPlace]",Util.toTextRequestBody(map.get("Tempat lahir")));
        mapSubmit.put("kreditmuPost[BirthDate]",Util.toTextRequestBody(map.get("Tanggal lahir")));
        mapSubmit.put("kreditmuPost[MobilePhone]",Util.toTextRequestBody(map.get("Nomor handphone")));
        mapSubmit.put("kreditmuPost[Email]",Util.toTextRequestBody(map.get("Alamat email")));
        mapSubmit.put("kreditmuPost[Education]",Util.toTextRequestBody(mapSpinner.get("Pendidikan")));
        mapSubmit.put("kreditmuPost[MaritalStatus]",Util.toTextRequestBody(mapSpinner.get("Status pernikahan")));
        mapSubmit.put("kreditmuPost[NumOfDependence]",Util.toTextRequestBody(map.get("Jumlah tanggungan")));
        mapSubmit.put("kreditmuPost[HomeStatus]",Util.toTextRequestBody(mapSpinner.get("Status kepemilikan rumah")));
        mapSubmit.put("kreditmuPost[StaySinceYear]",Util.toTextRequestBody(map.get("Mulai tinggal tahun")));
        mapSubmit.put("kreditmuPost[StaySinceMonth]",Util.toTextRequestBody(map.get("Mulai tinggal bulan")));
        mapSubmit.put("kreditmuPost[ProfessionID]",Util.toTextRequestBody(mapJob.get("Profesi")));
        mapSubmit.put("kreditmuPost[JobTypeID]",Util.toTextRequestBody(mapJob.get("Type pekerjaan")));
        mapSubmit.put("kreditmuPost[JobPositionID]",Util.toTextRequestBody(mapJob.get("Posisi pekerjaan")));
        mapSubmit.put("kreditmuPost[WorkSinceYear]",Util.toTextRequestBody(map.get("Mulai bekerja tahun")));
        mapSubmit.put("kreditmuPost[WorkSinceMonth]",Util.toTextRequestBody(map.get("Mulai bekerja bulan")));
        mapSubmit.put("kreditmuPost[MotherMaidenName]",Util.toTextRequestBody(map.get("Nama gadis ibu kandung")));
        mapSubmit.put("kreditmuPost[MonthlyFixedIncome]",Util.toTextRequestBody(map.get("Penghasilan bersih").replaceAll(",","")));
        mapSubmit.put("kreditmuPost[ResidencePhone]",Util.toTextRequestBody(map.get("area")+map.get("phone")));
        mapSubmit.put("kreditmuPost[ResidenceAddress]",Util.toTextRequestBody(map.get("Alamat tinggal domisili")));
        mapSubmit.put("kreditmuPost[ResidenceKelurahan]",Util.toTextRequestBody(map.get("Kelurahan domisili")));
        mapSubmit.put("kreditmuPost[ResidenceKecamatan]",Util.toTextRequestBody(map.get("Kecamatan domisili")));
        mapSubmit.put("kreditmuPost[ResidenceCity]",Util.toTextRequestBody(map.get("Kota domisili")));
        mapSubmit.put("kreditmuPost[ResidenceZipCode]",Util.toTextRequestBody(map.get("Kode pos domisili")));
        mapSubmit.put("kreditmuPost[LegalAddress]",Util.toTextRequestBody(map.get("Alamat sesuai ktp")));
        mapSubmit.put("kreditmuPost[LegalKelurahan]",Util.toTextRequestBody(map.get("Kelurahan KTP")));
        mapSubmit.put("kreditmuPost[LegalKecamatan]",Util.toTextRequestBody(map.get("Kecamatan KTP")));
        mapSubmit.put("kreditmuPost[LegalCity]",Util.toTextRequestBody(map.get("Kota KTP")));
        mapSubmit.put("kreditmuPost[LegalZipCode]",Util.toTextRequestBody(map.get("Kode pos kTP")));
        mapSubmit.put("kreditmuPost[CompanyAddress]",Util.toTextRequestBody(map.get("Alamat kantor")));
        mapSubmit.put("kreditmuPost[CompanyKelurahan]",Util.toTextRequestBody(map.get("Kelurahan pekerjaan")));
        mapSubmit.put("kreditmuPost[CompanyKecamatan]",Util.toTextRequestBody(map.get("Kecamatan pekerjaan")));
        mapSubmit.put("kreditmuPost[CompanyCity]",Util.toTextRequestBody(map.get("Kota pekerjaan")));
        mapSubmit.put("kreditmuPost[CompanyZipCode]",Util.toTextRequestBody(map.get("Kode pos pekerjaan")));
        if (BuildConfig.DEBUG){
            Log.e("send attachment", String.valueOf(attachmentList.size()));
            Log.e("send signture", String.valueOf(signatureList.size()));
        }

        multiparts = new ArrayList<>();
        for (int i = 0; i < attachmentList.size();i++){
            File fileTmp1 = FileUtils.getFile(attachmentList.get(i).getPath1());
            File fileTmp2 = FileUtils.getFile(attachmentList.get(i).getPath2());

            try {
                compres = new Compressor(mKreditmuMvpView.getContext())
                        .setMaxHeight(720)
                        .setMaxWidth(1080)
                        .setQuality(25)
                        .compressToFile(fileTmp1 == null?fileTmp2 : fileTmp1);
            } catch (IOException e) {
                Crashlytics.logException(e);
                if (BuildConfig.DEBUG)
                    Log.e("Kreditmu gagal compress",e.getMessage());
                mKreditmuMvpView.onFailedLoadKreditmu(mKreditmuMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
                return;
            }
            multiparts.add(prepareFilePart("kreditmuPost[Attachment]["+i+"]",compres));
        }

        for (int i = 0; i < signatureList.size();i++){
            File fileTmp1 = FileUtils.getFile(signatureList.get(i).getPath1());
            File fileTmp2 = FileUtils.getFile(signatureList.get(i).getPath2());

            try {
                compres = new Compressor(mKreditmuMvpView.getContext())
                        .setMaxHeight(720)
                        .setMaxWidth(1080)
                        .setQuality(25)
                        .compressToFile(fileTmp1 == null?fileTmp2 : fileTmp1);
            } catch (IOException e) {
                Crashlytics.logException(e);
                if (BuildConfig.DEBUG)
                    Log.e("Kreditmu gagal compress",e.getMessage());
                mKreditmuMvpView.onFailedLoadKreditmu(mKreditmuMvpView.getContext().getResources().getString(R.string.warning_failed_compress));
                return;
            }
            multiparts.add(prepareFilePart("kreditmuPost[Signature]["+i+"]",compres));
        }

        kreditmu(token, mapSubmit,multiparts);

    }

    private void kreditmu(String token, Map<String, RequestBody> map, List<MultipartBody.Part> multiparts){
        mKreditmuMvpView.onPreLoadKreditmu();
        count = 0;
        call = apiService.dataKreditmu(token,map,multiparts);
        call.enqueue(new Callback<BaseResponse<KreditmuResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<KreditmuResponse>> call, Response<BaseResponse<KreditmuResponse>> response) {
                count = 0;
                if (response.isSuccessful()){
                    mKreditmuMvpView.onSuccessLoadKreditmu(response.body().getData());
                }else if (response.code() == 403){
                    mKreditmuMvpView.onTokenKreditmuExpired();
                }else {
                    mKreditmuMvpView.onFailedLoadKreditmu(errorRetrofitUtil.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<KreditmuResponse>> call, Throwable t) {
                if (!call.isCanceled()){
                    if (count < 3){
                        call.clone().enqueue(this);
                        count += 1;
                    }else{
                        if (mKreditmuMvpView != null){
                            mKreditmuMvpView.onFailedLoadKreditmu(errorRetrofitUtil.responseFailedError(t));
                            count = 0;
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
