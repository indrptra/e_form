package com.kreditplus.eform.service;

import com.kreditplus.eform.model.response.FinancingObjectResponse;
import com.kreditplus.eform.model.KelurahanResponse;
import com.kreditplus.eform.model.TahunProduksiResponse;
import com.kreditplus.eform.model.MerkKendaraanResponse;
import com.kreditplus.eform.model.response.AddApplicationResponse;
import com.kreditplus.eform.model.response.ApplicationDetailResponse;
import com.kreditplus.eform.model.response.AssetMasterResponse;
import com.kreditplus.eform.model.response.BaseResponse;
import com.kreditplus.eform.model.response.BidResponse;
import com.kreditplus.eform.model.response.BlackListResponse;
import com.kreditplus.eform.model.response.CheckEfNumberResponse;
import com.kreditplus.eform.model.response.CheckVersionResponse;
import com.kreditplus.eform.model.response.CodePersetujuanResponse;
import com.kreditplus.eform.model.response.CoordinateResponse;
import com.kreditplus.eform.model.response.CounterpartResponse;
import com.kreditplus.eform.model.response.DetailPerhitunganWhiteGoodsResponse;
import com.kreditplus.eform.model.response.DetailPerhitunganKendaraanResponse;
import com.kreditplus.eform.model.response.DetailSaveDraftResponse;
import com.kreditplus.eform.model.response.FormResponse;
import com.kreditplus.eform.model.response.HargaAgunanResponse;
import com.kreditplus.eform.model.response.JenisKendaraanResponse;
import com.kreditplus.eform.model.response.JenisPembiayaanResponse;
import com.kreditplus.eform.model.response.KreditmuListResponse;
import com.kreditplus.eform.model.response.KreditmuResponse;
import com.kreditplus.eform.model.response.LoginResponse;
import com.kreditplus.eform.model.response.LogoutResponse;
import com.kreditplus.eform.model.response.MarketingSupplierResponse;
import com.kreditplus.eform.model.response.MaskingResponse;
import com.kreditplus.eform.model.response.MasterDataResponse;
import com.kreditplus.eform.model.response.MasterSyncResponse;
import com.kreditplus.eform.model.response.NotifikasiResponse;
import com.kreditplus.eform.model.response.OcrResponse;
import com.kreditplus.eform.model.response.PdfPengajuanResponse;
import com.kreditplus.eform.model.response.PengajuanResponse;
import com.kreditplus.eform.model.response.PosListDownResponse;
import com.kreditplus.eform.model.response.PosResponse;
import com.kreditplus.eform.model.response.ProductOfSupplierMapping;
import com.kreditplus.eform.model.response.ProductOffTenorResponse;
import com.kreditplus.eform.model.response.ProductOfferingResponse;
import com.kreditplus.eform.model.response.ProfilResponse;
import com.kreditplus.eform.model.response.PropinsiResponse;
import com.kreditplus.eform.model.response.QrResponse;
import com.kreditplus.eform.model.response.RateResponse;
import com.kreditplus.eform.model.response.RecomendationResponse;
import com.kreditplus.eform.model.response.ReferalCodeResponse;
import com.kreditplus.eform.model.response.RefreshTokenResponse;
import com.kreditplus.eform.model.response.SaldoKreditmuResponse;
import com.kreditplus.eform.model.response.SalesMethodResponse;
import com.kreditplus.eform.model.response.SendCodeResponse;
import com.kreditplus.eform.model.response.SupplierResponse;
import com.kreditplus.eform.model.response.SyaratDanKetentuanResponse;
import com.kreditplus.eform.model.response.TenorResponse;
import com.kreditplus.eform.model.response.VersionCodeResponse;
import com.kreditplus.eform.model.response.WilayahCabangResponse;
import com.kreditplus.eform.model.response.objecthelper.BodySyncApplication;
import com.kreditplus.eform.model.response.objecthelper.BodyToken;
import com.kreditplus.eform.model.response.objecthelper.CekKodeProgramObjct;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Iwan Nurdesa on 24/05/16.
 */
public interface ApiService {

    // Belum terpakai
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/product-offering")
    Call<BaseResponse<ProductOfferingResponse>> productOffering(@Header("Authorization") String authorization,
                                                                @Query("data_type") String dataType);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @DELETE("/api/v1/user/destroy")
    Call<BaseResponse<Object>> logout(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/region")
    Call<BaseResponse<PropinsiResponse>> propinsi(@Header("Authorization") String authorization,
                                                  @Query("type") String type);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/supplier")
    Call<BaseResponse<SupplierResponse>> supplier(@Header("Authorization") String authorization,
                                                  @Query("product_offering_id") String productOffering,
                                                  @Query("product_id") String productDua);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/application/detail-draft")
    Call<BaseResponse<DetailSaveDraftResponse>> detailSaveDraft(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/source/application")
    Call<BaseResponse<SalesMethodResponse>> salesMethod(@Header("Authorization") String authorization);

    //    tidak terpakai
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/branch/pos")
    Call<BaseResponse<PosResponse>> branchPos(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/tenor")
    Call<BaseResponse<TenorResponse>> tenor(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/user/check-expired-login")
    Call<BaseResponse<LogoutResponse>> ExpiredLogin(@Header("Authorization") String authorization,
                                                    @Query("test") String test);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/user/check-is-fpd")
    Call<BaseResponse> CheckFpd(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/counterpart")
    Call<BaseResponse<CounterpartResponse>> counterpart(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v1/application/{id}/bid")
    Call<BaseResponse<BidResponse>> bid(@Header("Authorization") String authorization,
                                        @Path("id") String applicationId, @Field("is_accept") int isAccept);

//    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v1/user/coordinate")
    Call<BaseResponse<Object>> coordinate(@Header("Authorization") String authorization,
                                          @Field("latitude") double latitude,
                                          @Field("longitude") double longitude);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @PUT("/api/v1/user/change/password")
    Call<BaseResponse<Object>> changePassword(@Header("Authorization") String authorization,
                                              @Query("old_password") String old_password,
                                              @Query("new_password") String new_password);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/master-data")
    Call<BaseResponse<MasterDataResponse>> masterData(@Header("Authorization") String authorization,
                                                      @Query("develop") String develop);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/application/check/prospect-id")
    Call<BaseResponse<CheckEfNumberResponse>> checkEfNumber(@Header("Authorization") String authorization,
                                                            @Query("EFNumber") String EFNumber);

    //    old
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/master-sync")
    Call<BaseResponse<MasterSyncResponse>> masterSync(@Header("Authorization") String authorization,
                                                      @Query("lastDateDump") String dumpDate,
                                                      @Query("lastDateSync") String syncDate,
                                                      @Query("version") String version,
                                                      @Query("type") String type,
                                                      @Query("branchId") String branchId);

    //    new
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/master-sync")
    Call<BaseResponse<MasterSyncResponse>> masterSyncNew(@Header("Authorization") String authorization,
                                                         @Query("lastDateDump") String dumpDate,
                                                         @Query("lastDateSync") String syncDate,
                                                         @Query("version") String version,
                                                         @Query("branchId") String branchId,
                                                         @Query("today") String today,
                                                         @Query("assetTypeID") String assetTypeID);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/ManufacturingYear")
    Call<BaseResponse<TahunProduksiResponse>> tahunProduksiKendaraan(@Header("Authorization") String authorization,
                                                                     @Query("assetCode") String assetCode,
                                                                     @Query("branchID") String branchID);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/MarketPrice")
    Call<BaseResponse<HargaAgunanResponse>> hargaAgunanKendaraan(@Header("Authorization") String authorization,
                                                                 @Query("branchID") String branchID,
                                                                 @Query("assetCode") String assetCode,
                                                                 @Query("manufacturingYear") String manufacturingYear);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/finance/calculate_new")
    Call<BaseResponse<DetailPerhitunganKendaraanResponse>> hitDetailPerhitunganKendaraan(@Header("Authorization") String authorization,
                                                                                         @Query("type") String type,
                                                                                         @Query("branch_code") String branch_code,
                                                                                         @Query("market_price") String market_price,
                                                                                         @Query("down_payment") String down_payment,
                                                                                         @Query("status") String status,
                                                                                         @Query("same_name") String same_name,
                                                                                         @Query("tenor") String tenor,
                                                                                         @Query("provisi_fee") String provisi_fee,
                                                                                         @Query("admin_fee") String admin_fee,
                                                                                         @Query("stnk_fee") String stnk_fee,
                                                                                         @Query("fiduciary_fee") String fiduciary_fee,
                                                                                         @Query("survey_cost") String survey_cost,
                                                                                         @Query("notary_fee") String notary_fee,
                                                                                         @Query("consumer_loan") String consumer_loan,
                                                                                         @Query("effective_rate") String effective_rate,
                                                                                         @Query("monthly_payment") String monthly_payment,
                                                                                         @Query("pinjaman") String pinjaman);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v2/check-version")
    Call<BaseResponse<CheckVersionResponse>> versionCheck(@Header("Authorization") String authorization,
                                                          @Query("mobileVersion") String mobileVersion);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/application/generatePDF/{id}/{pdf}")
    Call<BaseResponse<PdfPengajuanResponse>> downloadPdfPengajuan(@Header("Authorization") String authorization,
                                                                  @Path("id") String applicationId,
                                                                  @Path("pdf") String pdfName);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v2/application/pdf-perjanjian-kredit/{id}")
    Call<BaseResponse<PdfPengajuanResponse>> downloadPdfPerjanjian(@Header("Authorization") String authorization,
                                                                   @Path("id") String applicationId);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v2/user/check-percentage")
    Call<BaseResponse<RateResponse>> retrunRateData(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/application")
    Call<BaseResponse<PengajuanResponse>> pengajuan(@Header("Authorization") String authorization,
                                                    @Query("offset") int offset,
                                                    @Query("limit") int limit,
                                                    @Query("filter") String filter,
                                                    @Query("filter_value") int filterValue,
                                                    @Query("s") String search,
                                                    @Query("dateSubmit") String date,
                                                    @Query("type") String type,
                                                    @QueryMap Map<String, String> appIdList,
                                                    @Query("isDraft") String isDraft);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/application/set-form")
    Call<BaseResponse<FormResponse>> formShowHide(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v1/notification")
    Call<BaseResponse<NotifikasiResponse>> notifikasiList(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v1/user/login")
    Call<BaseResponse<LoginResponse>> login(@Field("username") String username,
                                            @Field("password") String password,
                                            @Field("longitude") String longitude,
                                            @Field("latitude") String latitude,
                                            @Field("version_code") String versionCode,
                                            @Field("device_id") String deviceId,
                                            @Field("imei") String imei,
                                            @Field("tipeHp") String tipeHp,
                                            @Field("os") String os);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v2/check_version")
    Call<BaseResponse<VersionCodeResponse>> checkVersionCode(@Field("version_code") String version_code);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/application/{id}/detail")
    Call<BaseResponse<ApplicationDetailResponse>> applicationDetail(@Header("Authorization") String authorization, @Path("id") String id);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v2/application/syarat-dan-ketentuan")
    Call<BaseResponse<SyaratDanKetentuanResponse>> syaratLoad(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v2/application/recomendation-message")
    Call<BaseResponse<RecomendationResponse>> recomendationMessage(@Header("Authorization") String authorization);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/productOffSupplierMapping")
    Call<BaseResponse<ProductOfSupplierMapping>> productOffSupplierMapping(@Header("Authorization") String authorization,
                                                                           @Query("branchId") String branchId,
                                                                           @Query("supplierId") String supplierId);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application")
    Call<BaseResponse<AddApplicationResponse>> addApplication(@Header("Authorization") String authorization,
                                                              @PartMap Map<String, RequestBody> partMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v2/customer/blacklist")
    Call<BaseResponse<BlackListResponse>> blackList(@Header("Authorization") String authorization,
                                                    @Field("date_birth") String dateBirth,
                                                    @Field("ktp_no") String noKtp,
                                                    @Field("handphone") String handphone,
                                                    @Field("offeringType") String offeringType,
                                                    @Field("branch_code") String branchUser);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v2/customer/blacklist")
    Call<BaseResponse<BlackListResponse>> blackListNew(@Header("Authorization") String authorization,
                                                    @Field("date_birth") String dateBirth,
                                                    @Field("ktp_no") String noKtp,
                                                    @Field("handphone") String handphone,
                                                    @Field("offeringType") String offeringType,
                                                    @Field("branch_code") String branchUser,
                                                    @Field("legal_name") String namaLegal,
                                                    @Field("surgate_mother_name") String ibuKandung,
                                                    @Field("is_edit") String isEdit);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @POST("/api/v2/application/token/signature")
    Call<BaseResponse<SendCodeResponse>> sendCodeSignature(@Header("Authorization") String authorization,
                                                           @Body BodyToken bodyToken);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application/save-draft")
    Call<BaseResponse<Object>> addSaveDraft(@Header("Authorization") String authorization,
                                            @PartMap Map<String, RequestBody> fieldMap,
                                            @Part List<MultipartBody.Part> attachmentadd);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("api/v2/application/bid")
    Call<BaseResponse<Object>> processIgnore(@Header("Authorization") String authorization,
                                             @PartMap Map<String, RequestBody> answer);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("api/v2/send-email")
    Call<BaseResponse<Object>> sendEmail(@Header("Authorization") String authorization,
                                         @PartMap Map<String, RequestBody> email);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v1/application/{id}")
    Call<BaseResponse<AddApplicationResponse>> addApplicationEdit(@Header("Authorization") String authorization,
                                                                  @PartMap Map<String, RequestBody> partMap,
                                                                  @Path("id") String applicationId);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("api/v2/kreditmu")
    Call<BaseResponse<KreditmuResponse>> dataKreditmu(@Header("Authorization") String authorization,
                                                      @PartMap Map<String, RequestBody> partMap,
                                                      @Part List<MultipartBody.Part> attachment);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("api/v2/kreditmu")
    Call<BaseResponse<SaldoKreditmuResponse>> saldoKreditmu(@Header("Authorization") String authorization,
                                                            @PartMap Map<String, RequestBody> partMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("api/v2/application/{id}/check-supplier")
    Call<BaseResponse<QrResponse>> checkingQrResult(@Header("Authorization") String authorization,
                                                    @Path("id") String appId,
                                                    @PartMap Map<String, RequestBody> partMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/image/check-ktp")
    Call<BaseResponse<OcrResponse>> SendImageKrp(@Header("Authorization") String authorization,
                                                 @Part List<MultipartBody.Part> partMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application/check-masking")
    Call<BaseResponse<MaskingResponse>> getMaskingValue(@Header("Authorization") String authorization,
                                                        @PartMap Map<String, RequestBody> partMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v2/application/token/signature/confirm")
    Call<BaseResponse<CodePersetujuanResponse>> confirmCodeSignature(@Header("Authorization") String authorization,
                                                                     @Field("type") String type,
                                                                     @Field("token") String token,
                                                                     @Field("applicationPID") String pid,
                                                                     @Field("mobile_submission_key") String mobileSubmissionKey);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @POST("/api/v1/user/refresh")
    Call<BaseResponse<RefreshTokenResponse>> refreshToken(@Header("Authorization") String authorization);

    @Headers("client_ksyaratLoadey: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/user/update")
    Call<BaseResponse<ProfilResponse>> updatePassword(@Header("Authorization") String authorization,
                                                      @PartMap Map<String, RequestBody> fieldMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("/api/v1/user/set-gcmid")
    Call<BaseResponse<Object>> updateFCM(@Header("Authorization") String authorization,
                                         @Field("gcm_id") String fcmId);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/user/post-position")
    Call<BaseResponse<CoordinateResponse>> coordinate(@Header("Authorization") String authorization,
                                                      @PartMap Map<String, RequestBody> fieldMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("api/v2/user/get-list-kreditmu")
    Call<BaseResponse<KreditmuListResponse>> dataKreditmu(@Header("Authorization") String authorization,
                                                          @PartMap Map<String, RequestBody> partMap);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/user/update")
    Call<BaseResponse<ProfilResponse>> updateProfile(@Header("Authorization") String authorization,
                                                     @PartMap Map<String, RequestBody> fieldMap,
                                                     @Part MultipartBody.Part photoProfile);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/asset-master")
    Call<BaseResponse<AssetMasterResponse>> assetMaster(@Header("Authorization") String authorization,
                                                        @Query("search") String search);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/master-sync")
    Call<BaseResponse<AssetMasterResponse>> searchDataMaster(@Header("Authorization") String authorization,
                                                             @Query("option") String option,
                                                             @Query("value") String value,
                                                             @Query("assetType") String assetType,
                                                             @Query("branchId") String branchId,
                                                             @Query("productOffID") String productOffId);

    //    get cabang dan wilayah cabang
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/Regional")
    Call<BaseResponse<WilayahCabangResponse>> getWilayahCabang(@Header("Authorization") String authorization,
                                                               @Query("branchID") String branchID);

    //    search supplier
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/master-sync")
    Call<BaseResponse<SupplierResponse>> searchSuplierMaster(@Header("Authorization") String authorization,
                                                             @Query("option") String option,
                                                             @Query("value") String value,
                                                             @Query("assetType") String assetType,
                                                             @Query("branchId") String branchId,
                                                             @Query("appType") String appType);

    //    list Marketing Supplier Master
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/master-sync")
    Call<BaseResponse<MarketingSupplierResponse>> searchMarketingSupplierMaster(@Header("Authorization") String authorization,
                                                                                @Query("option") String option,
                                                                                @Query("branchId") String branchId,
                                                                                @Query("supplierID") String supplierId,
                                                                                @Query("assetType") String assetType);

    //    search Product Offering Master
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/master-sync")
    Call<BaseResponse<ProductOfferingResponse>> searchProductOfferingMaster(@Header("Authorization") String authorization,
                                                                            @Query("option") String option,
                                                                            @Query("value") String value,
                                                                            @Query("assetType") String assetType,
                                                                            @Query("supplierID") String supplierId,
                                                                            @Query("branchId") String branchId,
                                                                            @Query("custType") String custType,
                                                                            @Query("appType") String appType,
                                                                            @Query("salesMethod") String AOSalesStatus,
                                                                            @Query("version") String Version);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/check_product_offering")
    Call<BaseResponse<CekKodeProgramObjct>> cekKodeProgram(@Header("Authorization") String authorization,
                                                           @Query("program_code") String programCode);

    //    listdown pos
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/master-sync")
    Call<BaseResponse<PosListDownResponse>> getListdownPos(@Header("Authorization") String authorization,
                                                           @Query("option") String option,
                                                           @Query("branchId") String branchId);

    //    search Product Offering Tenor
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/master-sync")
    Call<BaseResponse<ProductOffTenorResponse>> getProductOfferingTenor(@Header("Authorization") String authorization,
                                                                        @Query("productOffID") String productOffId,
                                                                        @Query("option") String option,
                                                                        @Query("branchId") String branchId);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v3/finance/calculate_wg")
    Call<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> getPerhitunganWGPremiOtomatis(@Header("Authorization") String authorization,
                                                                             @Query("otr_price") long otr_price,
                                                                             @Query("tenor") int tenor,
                                                                             @Query("down_payment") long down_payment,
                                                                             @Query("discount") long discount,
                                                                             @Query("admin_fee") long admin_fee,
                                                                             @Query("interest") float interest,
                                                                             @Query("type") String type,
                                                                                          @Query("discount_rate_times") String bebasBunga,
                                                                                          @Query("dp_percentage") int dpPercentage);
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v3/finance/calculate_wg")
    Call<BaseResponse<DetailPerhitunganWhiteGoodsResponse>> getPerhitunganWGPremiManual(@Header("Authorization") String authorization,
                                                                             @Query("otr_price") long otr_price,
                                                                             @Query("tenor") int tenor,
                                                                             @Query("down_payment") long down_payment,
                                                                             @Query("discount") long discount,
                                                                             @Query("admin_fee") long admin_fee,
                                                                             @Query("interest") float interest,
                                                                             @Query("type") String type,
                                                                             @Query("insurance") int asuransi,
                                                                                        @Query("discount_rate_times") String bebasBunga,
                                                                                        @Query("dp_percentage") int dpPercentage);

    //    listdown jenis kendaraan
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/assetCategory")
    Call<BaseResponse<JenisKendaraanResponse>> getJenisKendaraan(@Header("Authorization") String authorization,
                                                                 @Query("assetTypeID") String assetTypeID);

    //    list down merk kendaraan
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/assetMasterFilter")
    Call<BaseResponse<MerkKendaraanResponse>> pilihKendaraan(@Header("Authorization") String authorization,
                                                             @Query("assetTypeID") String assetTypeID,
                                                             @Query("assetLevel") String assetLevel,
                                                             @Query("category") String categoryId,
                                                             @Query("merk") String merk,
                                                             @Query("branchID") String branchID);

    //    search kelurahan
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/KelurahanFilter")
    Call<BaseResponse<KelurahanResponse>> getSearchKelurahan(@Header("Authorization") String authorization,
                                                             @Query("value") String value);

    //    listdown Jenis Pembiayaan
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/jenisPembiayaan")
    Call<BaseResponse<JenisPembiayaanResponse>> getJenisPembiayaan(@Header("Authorization") String authorization,
                                                                   @Query("tipePengajuan") String tipePengajuan);

    //    SINKRONISASI DATA MOTOR
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application")
    Call<BaseResponse<AddApplicationResponse>> syncKendaraan(@Header("Authorization") String authorization,
                                                             @PartMap Map<String, RequestBody> partMap);

    //    save draft motor
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application/save/draft")
    Call<BaseResponse<AddApplicationResponse>> hitSaveDraft(@Header("Authorization") String authorization,
                                                            @PartMap Map<String, RequestBody> partMap);

    //    SINKRONISASI DATA MOBIL
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application/{id}/attachment")
    Call<BaseResponse<Object>> syncAttachmentKendaraan(@Header("Authorization") String authorization,
                                                       @Path("id") String id,
                                                       @Query("type") String tpyeAttachment,
                                                       @Part List<MultipartBody.Part> attachments,
                                                       @Query("offeringType") String offeringType);

    //    SINKRONISASI DATA MOBIL
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application/{id}/attachment")
    Call<BaseResponse<Object>> syncAttachmentElektronik(@Header("Authorization") String authorization,
                                                       @Path("id") String id,
                                                       @Query("type") String tpyeAttachment,
                                                       @Part List<MultipartBody.Part> attachments);

//    retake foto
    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @Multipart
    @POST("/api/v2/application/{id}/attachment")
    Call<BaseResponse<Object>> addAttachment(@Header("Authorization") String authorization,
                                             @Path("id") String id,
                                             @Query("type") String tpyeAttachment,
                                             @Part List<MultipartBody.Part> attachments,
                                             @Query("offeringType") String offeringType);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v3/master-sync")
    Call<BaseResponse<FinancingObjectResponse>> getTujuanPembiayaan(@Header("Authorization") String authorization,
                                                                    @Query("option") String productOffId);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("/api/v2/application/draft/{id}/delete")
    Call<BaseResponse<Object>> getDeleteDraft(@Header("Authorization") String authorization,
                                              @Path("id") String id);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @GET("api/v3/get-code-refferal")
    Call<BaseResponse<ReferalCodeResponse>> getReferalCode(@Header("Authorization") String authorization,
                                                           @Query("code") String code,
                                                           @Query("supplier_id") String supplierId);

    @Headers("client_key: $2y$10$OoDAS6saH1b3D/nZJ4DXKuOTqVumFTACUZDFkZfepS1h15jDNxdzK")
    @FormUrlEncoded
    @POST("api/v2/user/check-location")
    Call<BaseResponse> userCheckLocation(@Header("Authorization")String authorization,
                                         @Field("longitude") double longitude,
                                         @Field("latitude") double latitude);






}


