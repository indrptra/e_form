package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Iwan Nurdesa on 03/07/16.
 */
@DatabaseTable(tableName = "pengajuan_baru")
public class PengajuanBaru {
    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    //uuid
    @DatabaseField(columnName = "uuid")
    private String uuid;
    // customerid
    @DatabaseField(columnName = "customerId")
    private String customerId;
    //app_id_backend
    @DatabaseField(columnName = "app_id_backend")
    private String appIdBackend;
    //count
    @DatabaseField(columnName = "countSignature")
    private int countSignature;
    //Pilihan Cabang
    @DatabaseField(columnName = "Branch")
    private String branch;
    @DatabaseField(columnName = "MasterBranch")
    private String masterBranch;
    // KOP
    @DatabaseField(columnName = "metode_penjualan_00")
    private String metodePenjualan00;
    @DatabaseField(columnName = "status_pemohon_00")
    private String statusPemohon00;
    @DatabaseField(columnName = "metode_penjualan_position_00")
    private int metodePenjualanPosition00;
    // Data Pribadi
    @DatabaseField(columnName = "nama_lengkap_0")
    private String namaLengkap0;
    @DatabaseField(columnName = "nama_ktp_0")
    private String namaKtp0;
    @DatabaseField(columnName = "no_ktp_0")
    private String noKtp0;
    @DatabaseField(columnName = "no_npwp_0")
    private String noNpwp0;
    @DatabaseField(columnName = "tanggal_ktp_0")
    private String tanggalKtp0;
    @DatabaseField(columnName = "jenis_kelamin_0")
    private String jenisKelamin0;
    @DatabaseField(columnName = "jenis_kelamin_position_0")
    private int jenisKelaminPosition0;
    @DatabaseField(columnName = "tempat_lahir_0")
    private String tempatLahir0;
    @DatabaseField(columnName = "tanggal_lahir_0")
    private String tanggalLahir0;
    @DatabaseField(columnName = "ibu_kandung_0")
    private String ibuKandung0;
    @DatabaseField(columnName = "pendidikan_0")
    private String pendidikan0;
    @DatabaseField(columnName = "pendidikan_position_0")
    private int pendidikanPosition0;
    @DatabaseField(columnName = "warga_negara_0")
    private String wargaNegara0;
    @DatabaseField(columnName = "warga_negara_position_0")
    private int wargaNegaraPosition0;
    @DatabaseField(columnName = "status_rumah_0")
    private String statusRumah0;
    @DatabaseField(columnName = "status_rumah_position_0")
    private int statusRumahPosition0;
    @DatabaseField(columnName = "tinggal_sejak_tahun_0")
    private String tinggalSejakTahun0;
    @DatabaseField(columnName = "tinggal_sejak_bulan_0")
    private String tinggalSejakBulan0;
    @DatabaseField(columnName = "agama_0")
    private String agama0;
    @DatabaseField(columnName = "agama_position_0")
    private int agamaPosition0;
    @DatabaseField(columnName = "status_nikah_0")
    private String statusNikah0;
    @DatabaseField(columnName = "status_nikah_position_0")
    private int statusNikahPosition0;
    @DatabaseField(columnName = "jumlah_tanggungan_0")
    private String jumlahTanggungan0;
    @DatabaseField(columnName = "mobile_phone_0")
    private String mobilePhone0;
    @DatabaseField(columnName = "email_0")
    private String email0;
    // Alamat Tinggal Pemohon
    @DatabaseField(columnName = "alamat_tinggal_1")
    private String alamatTinggal1;
    @DatabaseField(columnName = "auto_alamat_tinggal_1")
    private String autoAlamatTinggal1;
    @DatabaseField(columnName = "rt_tinggal_1")
    private String rtTinggal1;
    @DatabaseField(columnName = "rw_tinggal_1")
    private String rwTinggal1;
    @DatabaseField(columnName = "kelurahan_kode_tinggal_1")
    private String kelurahanKodeTinggal1;
    @DatabaseField(columnName = "kelurahan_tinggal_1")
    private String kelurahanTinggal1;
    @DatabaseField(columnName = "kecamatan_kode_tinggal_1")
    private String kecamatanKodeTinggal1;
    @DatabaseField(columnName = "kecamatan_tinggal_1")
    private String kecamatanTinggal1;
    @DatabaseField(columnName = "kota_kode_tinggal_1")
    private String kotaKodeTinggal1;
    @DatabaseField(columnName = "kota_tinggal_1")
    private String kotaTinggal1;
    @DatabaseField(columnName = "propinsi_kode_tinggal_tinggal_1")
    private String propinsiKodeTinggal1;
    @DatabaseField(columnName = "propinsi_tinggal_1")
    private String propinsiTinggal1;
    @DatabaseField(columnName = "kode_pos_tinggal_1")
    private String kodePosTinggal1;
    @DatabaseField(columnName = "area_phone_tinggal_1")
    private String areaPhoneTinggal1;
    @DatabaseField(columnName = "phone_tinggal_1")
    private String phoneTinggal1;

    // Alamat KTP Pemohon
    @DatabaseField(columnName = "alamat_ktp_1")
    private String alamatKtp1;
    @DatabaseField(columnName = "auto_alamat_ktp_1")
    private String autoAlamatKtp1;
    @DatabaseField(columnName = "rt_ktp_1")
    private String rtKtp1;
    @DatabaseField(columnName = "rw_ktp_1")
    private String rwKtp1;
    @DatabaseField(columnName = "kelurahan_kode_ktp_1")
    private String kelurahanKodeKtp1;
    @DatabaseField(columnName = "kelurahan_ktp_1")
    private String kelurahanKtp1;
    @DatabaseField(columnName = "kecamatan_kode_ktp_1")
    private String kecamatanKodeKtp1;
    @DatabaseField(columnName = "kecamatan_ktp_1")
    private String kecamatanKtp1;
    @DatabaseField(columnName = "kota_kode_ktp_1")
    private String kotaKodeKtp1;
    @DatabaseField(columnName = "kota_ktp_1")
    private String kotaKtp1;
    @DatabaseField(columnName = "propinsi_kode_ktp_1")
    private String propinsiKodeKtp1;
    @DatabaseField(columnName = "propinsi_ktp_1")
    private String propinsiKtp1;
    @DatabaseField(columnName = "kode_pos_ktp_1")
    private String kodePosKtp1;
    @DatabaseField(columnName = "area_phone_ktp_1")
    private String areaPhoneKtp1;
    @DatabaseField(columnName = "phone_ktp_1")
    private String phoneKtp1;
    @DatabaseField(columnName = "checkbox_alamat")
    private Boolean checkboxAlamat;

    // Informasi Kerabat
    @DatabaseField(columnName = "nama_lengkap_2")
    private String namaLengkap2;
    @DatabaseField(columnName = "hubungan_kerabat_2")
    private String hubunganKerabat2;
    @DatabaseField(columnName = "hubungan_kerabat_posisi_2")
    private int HubunganKerabatPosition2;
    @DatabaseField(columnName = "alamat_2")
    private String alamat2;
    @DatabaseField(columnName = "auto_alamat_2")
    private String auto_alamat2;
    @DatabaseField(columnName = "rt_2")
    private String rt2;
    @DatabaseField(columnName = "rw_2")
    private String rw2;
    @DatabaseField(columnName = "kelurahan_kode_2")
    private String kelurahanKode2;
    @DatabaseField(columnName = "kelurahan_2")
    private String kelurahan2;
    @DatabaseField(columnName = "kecamatan_kode_2")
    private String kecamatanKode2;
    @DatabaseField(columnName = "kecamatan_2")
    private String kecamatan2;
    @DatabaseField(columnName = "kota_kode_2")
    private String kotaKode2;
    @DatabaseField(columnName = "kota_2")
    private String kota2;
    @DatabaseField(columnName = "propinsi_kode_2")
    private String propinsiKode2;
    @DatabaseField(columnName = "propinsi_2")
    private String propinsi2;
    @DatabaseField(columnName = "kode_pos_2")
    private String kodePos2;
    @DatabaseField(columnName = "area_phone_rumah_2")
    private String areaPhoneRumah2;
    @DatabaseField(columnName = "phone_rumah_2")
    private String phoneRumah2;
    @DatabaseField(columnName = "area_phone_kantor_2")
    private String areaPhoneKantor2;
    @DatabaseField(columnName = "phone_kantor_2")
    private String phoneKantor2;
    @DatabaseField(columnName = "mobile_phone_2")
    private String mobilePhone2;
    // Data Pekerjaan
    @DatabaseField(columnName = "nama_perusahaan_3")
    private String namaPerusahaan3;
    @DatabaseField(columnName = "alamat_3")
    private String alamat3;
    @DatabaseField(columnName = "auto_alamat_3")
    private String auto_alamat3;
    @DatabaseField(columnName = "rt_3")
    private String rt3;
    @DatabaseField(columnName = "rw_3")
    private String rw3;
    @DatabaseField(columnName = "kelurahan_kode_3")
    private String kelurahanKode3;
    @DatabaseField(columnName = "kelurahan_3")
    private String kelurahan3;
    @DatabaseField(columnName = "kecamatan_kode_3")
    private String kecamatanKode3;
    @DatabaseField(columnName = "kecamatan_3")
    private String kecamatan3;
    @DatabaseField(columnName = "kota_kode_3")
    private String kotaKode3;
    @DatabaseField(columnName = "kota_3")
    private String kota3;
    @DatabaseField(columnName = "propinsi_kode_3")
    private String propinsiKode3;
    @DatabaseField(columnName = "propinsi_3")
    private String propinsi3;
    @DatabaseField(columnName = "kode_pos_3")
    private String kodePos3;
    @DatabaseField(columnName = "area_phone_3")
    private String areaPhone3;
    @DatabaseField(columnName = "phone_3")
    private String phone3;
    @DatabaseField(columnName = "tahun_kerja_3")
    private String tahunKerja3;
    @DatabaseField(columnName = "customer_type_3")
    private String customerType3;
    @DatabaseField(columnName = "customer_type_position_3")
    private int customerTypePostion3;
    @DatabaseField(columnName = "profesi_kode_3")
    private String profesiKode3;
    @DatabaseField(columnName = "profesi_3")
    private String profesi3;
    @DatabaseField(columnName = "job_type_3")
    private String jobType3;
    @DatabaseField(columnName = "job_type_kode_3")
    private String jobTypeKode3;
    @DatabaseField(columnName = "job_position_3")
    private String jobPosition3;
    @DatabaseField(columnName = "job_position_kode_3")
    private String jobPositionKode3;
    @DatabaseField(columnName = "industri_3")
    private String industri3;
    @DatabaseField(columnName = "industri_kode_3")
    private String industriKode3;
    @DatabaseField(columnName = "penghasilan_tetap_3")
    private String penghasilanTetap3;
    @DatabaseField(columnName = "penghasilan_lain_3")
    private String penghasilanLain3;
    @DatabaseField(columnName = "penghasilan_pasangan_3")
    private String penghasilanPasangan3;
    @DatabaseField(columnName = "biaya_hidup_3")
    private String biayaHidup3;
    @DatabaseField(columnName = "counterpart_kode_3")
    private String counterPartKode3;
    @DatabaseField(columnName = "counterpart_3")
    private String counterPart3;
    @DatabaseField(columnName = "debt_business_scale_3")
    private String debtBusinessScale3;
    @DatabaseField(columnName = "debt_group_3")
    private String debtGroup3;
    @DatabaseField(columnName = "is_affiliate_with_pp_3")
    private String isAffiliateWithPp3;
    // Detail Product
    @DatabaseField(columnName = "product_offering_4")
    private String productOffering4;
    @DatabaseField(columnName = "product_offering_kode_4")
    private String productOfferingKode4;
    @DatabaseField(columnName = "product_offering_kode2_4")
    private String productOfferingKode24;
    @DatabaseField(columnName = "jumlah_asset_4")
    private String jumlahAsset4;
    @DatabaseField(columnName = "jumlah_asset_position_4")
    private int jumlahAssetPosition4;
    @DatabaseField(columnName = "pos_kode_4")
    private String posKode4;
    @DatabaseField(columnName = "pos_4")
    private String pos4;
    // Data Asuransi
    @DatabaseField(columnName = "coverage_asuransi_5")
    private String coverageAsurani5;
    @DatabaseField(columnName = "coverage_asuransi_position_5")
    private int coverageAsuraniPosition5;
    @DatabaseField(columnName = "isPersonalAccident_5")
    private String isPersonalAccident5;
    @DatabaseField(columnName = "premiManual")
    private boolean premiManual;
    // Data Perhitungan elektronik
    @DatabaseField(columnName = "premi_asuransi_6")
    private String premiAsuransi6;
    @DatabaseField(columnName = "tenor_6")
    private String tenor6;
    @DatabaseField(columnName = "tenor_position_6")
    private int tenorPosition6;
    @DatabaseField(columnName = "purchase_price_6")
    private String purchasePrice6;
    @DatabaseField(columnName = "discount_6")
    private String discount6;
    @DatabaseField(columnName = "down_payment_6")
    private String downPayment6;
    @DatabaseField(columnName = "ntf_6")
    private String ntf6;
    @DatabaseField(columnName = "biaya_lainnya_6")
    private String biayaLainnya6;
    @DatabaseField(columnName = "jumlah_pembiayaan_6")
    private String jumlahPembiayaan6;
    @DatabaseField(columnName = "bunga_pembiayaan_6")
    private String bungaTotalPembiayaan6;
    @DatabaseField(columnName = "bunga_pembiayaan_perbulan_6")
    private String bungaPembiayaanPerBulan6;
    @DatabaseField(columnName = "total_pinjaman_6")
    private String totalPinjaman6;
    @DatabaseField(columnName = "angsuran_pertama_6")
    private String angsuranPertama6;
    @DatabaseField(columnName = "angsuran_pertama_position_6")
    private int angsuranPertamaPosition6;
    @DatabaseField(columnName = "biaya_admin_6")
    private String biayaAdmin6;
    @DatabaseField(columnName = "flat_rate_6")
    private String flatRate6;
    @DatabaseField(columnName = "effective_6")
    private String effectiveRate6;
    @DatabaseField(columnName = "setor_pertama_6")
    private String setorPertama6;
    @DatabaseField(columnName = "setor_perbulan_6")
    private String setorPerbulan6;
    @DatabaseField(columnName = "pembayaran_awal_6")
    private String pembayaranAwal6;
    @DatabaseField(columnName = "refund_subsidi_6")
    private String refundSubsidi6;
    @DatabaseField(columnName = "bebas_bunga_perhitungan_6")
    private String bebasBungaPerhitungan6;
    @DatabaseField(columnName = "bunga_pembiayaan_bulan_6")
    private String bungaPembiayaanBulan6;
//    @DatabaseField(columnName = "angsuran_perbulan_bebas_bunga_6")
//    private String angsuranPerbulanBebasBunga6;
    @DatabaseField(columnName = "pembayaran_dealer_6")
    private String pembayaranDealer6;
//    @DatabaseField(columnName = "tipe_pembayaran")
//    private String tipePembayaran;
    // Data Perhitungan kendaraan
    @DatabaseField(columnName = "angsuran_perbulan_12")
    private String angsuranPerbulan12;
    @DatabaseField(columnName = "tenor_12")
    private String tenor12;
    @DatabaseField(columnName = "tenor_position_12")
    private int tenorPosition12;
    @DatabaseField(columnName = "total_pinjaman_12")
    private String totalPinjaman12;
    @DatabaseField(columnName = "nilai_agunan_12")
    private String nilaiAgunan12;
    @DatabaseField(columnName = "biaya_administrasi_12")
    private String biaya_administrasi12;
    @DatabaseField(columnName = "nilai_pembiayaan_12")
    private String nilaiPembiayaan12;
    @DatabaseField(columnName = "premi_asuransi_agunan_12")
    private String premiASuransiAgunan12;
    @DatabaseField(columnName = "premi_asuransi_jiwa_12")
    private String premiASuransiJiwa12;
    @DatabaseField(columnName = "biaya_lainnya_12")
    private String biayaLainnya12;
    @DatabaseField(columnName = "jumlah_pembiayaan_12")
    private String jumlahPembiayaan12;
    @DatabaseField(columnName = "bunga_pembiayaan_12")
    private String bungaPembiayaan12;
    @DatabaseField(columnName = "bunga_flat_tahun_12")
    private String bungaFlatTahun12;
    @DatabaseField(columnName = "bunga_flat_bulan_12")
    private String bungaFlatBulan12;
    @DatabaseField(columnName = "harga_kendaraan")
    private String hargaKendaraan;
    @DatabaseField(columnName = "dp_cash_12")
    private String dpCash12;
    @DatabaseField(columnName = "fidusia_12")
    private String fidusia12;
    @DatabaseField(columnName = "stnk_12")
    private String stnk12;
    @DatabaseField(columnName = "biaya_lainnya2_12")
    private String biayaLainnya212;
    @DatabaseField(columnName = "total_setor_pertama_12")
    private String totalSetorPertama12;
    @DatabaseField(columnName = "refund_subsidi_12")
    private String refundSubsidi12;
    @DatabaseField(columnName = "angsuran_pertama_12")
    private String angsuranPertama12;
    @DatabaseField(columnName = "angsuran_pertama_position_12")
    private int angsuranPertamaPosition12;
    @DatabaseField(columnName = "admin_fee_lainnya")
    private int adminFeeLain;
    @DatabaseField(columnName = "firstInstalment")
    private String firstInstalment;

    // Persetujuan
    @DatabaseField(columnName = "ttd_pemohon_7")
    private String ttdPemohon7;
    @DatabaseField(columnName = "ttd_pasangan_7")
    private String ttdPasangan7;
    // Data Pasangan Suami / Istri
    @DatabaseField(columnName = "id_pasangan")
    private String idPasangan8;
    @DatabaseField(columnName = "nama_pasangan_8")
    private String namaPasangan8;
    @DatabaseField(columnName = "no_ktp_pasangan_8")
    private String noKtpPasangan8;
    @DatabaseField(columnName = "tempat_lahir_pasangan_8")
    private String tempatLahirPasangan8;
    @DatabaseField(columnName = "tanggal_lahir_pasangan_8")
    private String tanggalLahirPasangan8;
    @DatabaseField(columnName = "handphone_pasangan_8")
    private String handphonePasangan8;
    // Data kartu kredit
    @DatabaseField(columnName = "nama_bank_9")
    private String namaBank9;
    @DatabaseField(columnName = "no_kartu_kredit_9")
    private String noKartuKredit9;
    @DatabaseField(columnName = "jenis_kartu_kredit_9")
    private String jenisKartuKredit9;
    @DatabaseField(columnName = "limit_kartu_kredit_9")
    private String limitKartuKredit9;
    @DatabaseField(columnName = "month_expired_card_9")
    private String monthExpiredCard9;
    @DatabaseField(columnName = "year_expired_card_9")
    private String yearExpiredCard9;
    // Data kartu membership
    @DatabaseField(columnName = "no_membership_10")
    private String noMembership10;
    @DatabaseField(columnName = "tanggal_efektif_10")
    private String tanggalEfektif10;
    @DatabaseField(columnName = "tanggal_expired_10")
    private String tanggalExpired10;
    // Detail AssetElektronik
    @DatabaseField(columnName = "supplier")
    private String supplier;
    @DatabaseField(columnName = "supplier_kode")
    private String supplierKode;
    @DatabaseField(columnName = "bank_account_kode")
    private String bankAccountKode;
    @DatabaseField(columnName = "bank_account")
    private String bankAccount;
    @DatabaseField(columnName = "marketing_supplier_kode")
    private String marketingSupplierKode;
    @DatabaseField(columnName = "marketing_supplier")
    private String marketingSupplier;

    // Agunan
    @DatabaseField(columnName = "jenis_kb_11")
    private String jenisKb11;
    @DatabaseField(columnName = "jenis_kb_position_11")
    private int jenisKbPosition11;
    @DatabaseField(columnName = "merk_kb_11")
    private String merkKb11;
    @DatabaseField(columnName = "type_kb_11")
    private String typeKb11;
    @DatabaseField(columnName = "tahun_kb_11")
    private String tahunKb11;
    @DatabaseField(columnName = "warna_kb_11")
    private String warnaKb11;
    @DatabaseField(columnName = "isi_silinder_kb_11")
    private String isiSilinderKb11;
    @DatabaseField(columnName = "no_polisi_kb_11")
    private String noPolisiKb11;
    @DatabaseField(columnName = "no_rangka_kb_11")
    private String noRangkaKb11;
    @DatabaseField(columnName = "no_mesin_kb_11")
    private String noMesinKb11;
    @DatabaseField(columnName = "bpkb_kb_11")
    private String bpkbKb11;
    @DatabaseField(columnName = "nilai_agunan_kb_11")
    private String nilaiAgunanKb11;
    //Keterangan
    @DatabaseField(columnName = "keterangan")
    private String keterangan;
    // etc
    @DatabaseField(columnName = "form_type")
    private String formType;
    @DatabaseField(columnName = "isAssignedit")
    private String isAssignedit;
    @DatabaseField(columnName = "application_id")
    private String applicationId;
    @DatabaseField(columnName = "status_sync")
    private int statusSync;
    @DatabaseField(columnName = "created_at")
    private String createdAt;
    @DatabaseField(columnName = "pengajuan_type")
    private String pengajuanType;
    @DatabaseField(columnName = "ef_number")
    private String efNumber;
    @DatabaseField(columnName = "submission_key")
    private String mobileSubmissionKey;
    @DatabaseField(columnName = "total_insurance_persen")
    private String totalInsurancePersen;
    @DatabaseField(columnName = "type_data_offering")
    private String typeDataOffering;
    @DatabaseField(columnName = "blacklist_date")
    private String blacklist_date;
    //id KPM
    @DatabaseField(columnName = "id_kpm")
    private String idKpm;
    @DatabaseField(columnName = "have_masking")
    private int haveMasking;
    @DatabaseField(columnName = "isFromSignin")
    private boolean isFromSignin;
    //    REKOMENDATIONS
    @DatabaseField(columnName = "isJabar_13")
    private int isJabar13;
    @DatabaseField(columnName = "recomendation_13")
    private String recomendation13;
    @DatabaseField(columnName = "recomendation_note_13")
    private String recomendationNote13;
    // LOKASI
    @DatabaseField(columnName = "validate_action_14")
    private String validateAction14;
    @DatabaseField(columnName = "validate_longitude_14")
    private String validateLongitude14;
    @DatabaseField(columnName = "validate_latitude_14")
    private String validateLatitude14;
    @DatabaseField(columnName = "take_ktp_action_14")
    private String takeKtpAction14;
    @DatabaseField(columnName = "take_ktp_longitude_14")
    private String takeKtpLongitude14;
    @DatabaseField(columnName = "take_ktp_latitude_14")
    private String takeKtpLatitude14;
    @DatabaseField(columnName = "take_customer_action_14")
    private String takeCustomerAction14;
    @DatabaseField(columnName = "take_customer_longitude_14")
    private String takeCustomerLongitude14;
    @DatabaseField(columnName = "take_customer_latitude_14")
    private String takeCustomerLatitude14;
    @DatabaseField(columnName = "take_paycheck_action_14")
    private String takePaycheckAction14;
    @DatabaseField(columnName = "take_paycheck_longitude_14")
    private String takePaychecklongitude14;
    @DatabaseField(columnName = "take_paycheck_latitude_14")
    private String takePaychecklatitude14;
    @DatabaseField(columnName = "take_additional_documents_action_14")
    private String takeAdditionalDocumentsAction14;
    @DatabaseField(columnName = "take_additional_documents_longitude_14")
    private String takeAdditionalDocumentsLongitude14;
    @DatabaseField(columnName = "take_additional_documents_latitude_14")
    private String takeAdditionalDocumentsLatitude14;
    @DatabaseField(columnName = "take_signature_action_14")
    private String takeSignatureAction14;
    @DatabaseField(columnName = "take_signature_longitude_14")
    private String takeSignatureLongitude14;
    @DatabaseField(columnName = "take_signature_latitude_14")
    private String takeSignatureLatitude14;
    @DatabaseField(columnName = "submit_action_14")
    private String submitAction14;
    @DatabaseField(columnName = "submit_longitude_14")
    private String submitLongitude14;
    @DatabaseField(columnName = "submit_latitude_14")
    private String submitLatitude14;
    @DatabaseField(columnName = "sync_action_14")
    private String syncAction14;
    @DatabaseField(columnName = "sync_longitude_14")
    private String syncLongitude14;
    @DatabaseField(columnName = "sync_latitude_14")
    private String syncLatitude14;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCountSignature() {
        return countSignature;
    }

    public void setCountSignature(int countSignature) {
        this.countSignature = countSignature;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaLengkap0() {
        return namaLengkap0;
    }

    public void setNamaLengkap0(String namaLengkap0) {
        this.namaLengkap0 = namaLengkap0;
    }

    public String getNamaKtp0() {
        return namaKtp0;
    }

    public void setNamaKtp0(String namaKtp0) {
        this.namaKtp0 = namaKtp0;
    }

    public String getNoKtp0() {
        return noKtp0;
    }

    public void setNoKtp0(String noKtp0) {
        this.noKtp0 = noKtp0;
    }

    public String getNoNpwp0() {
        return noNpwp0;
    }

    public void setNoNpwp0(String noNpwp0) {
        this.noNpwp0 = noNpwp0;
    }

    public String getTanggalKtp0() {
        return tanggalKtp0;
    }

    public void setTanggalKtp0(String tanggalKtp0) {
        this.tanggalKtp0 = tanggalKtp0;
    }

    public String getJenisKelamin0() {
        return jenisKelamin0;
    }

    public void setJenisKelamin0(String jenisKelamin0) {
        this.jenisKelamin0 = jenisKelamin0;
    }

    public String getTempatLahir0() {
        return tempatLahir0;
    }

    public void setTempatLahir0(String tempatLahir0) {
        this.tempatLahir0 = tempatLahir0;
    }

    public String getTanggalLahir0() {
        return tanggalLahir0;
    }

    public void setTanggalLahir0(String tanggalLahir0) {
        this.tanggalLahir0 = tanggalLahir0;
    }

    public String getIbuKandung0() {
        return ibuKandung0;
    }

    public void setIbuKandung0(String ibuKandung0) {
        this.ibuKandung0 = ibuKandung0;
    }

    public String getPendidikan0() {
        return pendidikan0;
    }

    public void setPendidikan0(String pendidikan0) {
        this.pendidikan0 = pendidikan0;
    }

    public String getWargaNegara0() {
        return wargaNegara0;
    }

    public void setWargaNegara0(String wargaNegara0) {
        this.wargaNegara0 = wargaNegara0;
    }

    public String getStatusRumah0() {
        return statusRumah0;
    }

    public void setStatusRumah0(String statusRumah0) {
        this.statusRumah0 = statusRumah0;
    }

    public String getTinggalSejakTahun0() {
        return tinggalSejakTahun0;
    }

    public void setTinggalSejakTahun0(String tinggalSejakTahun0) {
        this.tinggalSejakTahun0 = tinggalSejakTahun0;
    }

    public String getTinggalSejakBulan0() {
        return tinggalSejakBulan0;
    }

    public void setTinggalSejakBulan0(String tinggalSejakBulan0) {
        this.tinggalSejakBulan0 = tinggalSejakBulan0;
    }

    public String getProductOfferingKode24() {
        return productOfferingKode24;
    }

    public void setProductOfferingKode24(String productOfferingKode24) {
        this.productOfferingKode24 = productOfferingKode24;
    }

    public String getAgama0() {
        return agama0;
    }

    public void setAgama0(String agama0) {
        this.agama0 = agama0;
    }

    public String getStatusNikah0() {
        return statusNikah0;
    }

    public void setStatusNikah0(String statusNikah0) {
        this.statusNikah0 = statusNikah0;
    }

    public String getJumlahTanggungan0() {
        return jumlahTanggungan0;
    }

    public void setJumlahTanggungan0(String jumlahTanggungan0) {
        this.jumlahTanggungan0 = jumlahTanggungan0;
    }

    public String getMobilePhone0() {
        return mobilePhone0;
    }

    public void setMobilePhone0(String mobilePhone0) {
        this.mobilePhone0 = mobilePhone0;
    }

    public String getEmail0() {
        return email0;
    }

    public void setEmail0(String email0) {
        this.email0 = email0;
    }

    public String getAlamatTinggal1() {
        return alamatTinggal1;
    }

    public void setAlamatTinggal1(String alamatTinggal1) {
        this.alamatTinggal1 = alamatTinggal1;
    }

    public String getRtTinggal1() {
        return rtTinggal1;
    }

    public void setRtTinggal1(String rtTinggal1) {
        this.rtTinggal1 = rtTinggal1;
    }

    public String getRwTinggal1() {
        return rwTinggal1;
    }

    public void setRwTinggal1(String rwTinggal1) {
        this.rwTinggal1 = rwTinggal1;
    }

    public String getKelurahanKodeTinggal1() {
        return kelurahanKodeTinggal1;
    }

    public void setKelurahanKodeTinggal1(String kelurahanKodeTinggal1) {
        this.kelurahanKodeTinggal1 = kelurahanKodeTinggal1;
    }

    public String getKelurahanTinggal1() {
        return kelurahanTinggal1;
    }

    public void setKelurahanTinggal1(String kelurahanTinggal1) {
        this.kelurahanTinggal1 = kelurahanTinggal1;
    }

    public String getKecamatanTinggal1() {
        return kecamatanTinggal1;
    }

    public void setKecamatanTinggal1(String kecamatanTinggal1) {
        this.kecamatanTinggal1 = kecamatanTinggal1;
    }

    public String getKotaTinggal1() {
        return kotaTinggal1;
    }

    public void setKotaTinggal1(String kotaTinggal1) {
        this.kotaTinggal1 = kotaTinggal1;
    }

    public String getPropinsiTinggal1() {
        return propinsiTinggal1;
    }

    public void setPropinsiTinggal1(String propinsiTinggal1) {
        this.propinsiTinggal1 = propinsiTinggal1;
    }

    public String getKodePosTinggal1() {
        return kodePosTinggal1;
    }

    public void setKodePosTinggal1(String kodePosTinggal1) {
        this.kodePosTinggal1 = kodePosTinggal1;
    }

    public String getAreaPhoneTinggal1() {
        return areaPhoneTinggal1;
    }

    public void setAreaPhoneTinggal1(String areaPhoneTinggal1) {
        this.areaPhoneTinggal1 = areaPhoneTinggal1;
    }

    public String getPhoneTinggal1() {
        return phoneTinggal1;
    }

    public void setPhoneTinggal1(String phoneTinggal1) {
        this.phoneTinggal1 = phoneTinggal1;
    }

    public String getAlamatKtp1() {
        return alamatKtp1;
    }

    public void setAlamatKtp1(String alamatKtp1) {
        this.alamatKtp1 = alamatKtp1;
    }

    public String getRtKtp1() {
        return rtKtp1;
    }

    public void setRtKtp1(String rtKtp1) {
        this.rtKtp1 = rtKtp1;
    }

    public String getRwKtp1() {
        return rwKtp1;
    }

    public void setRwKtp1(String rwKtp1) {
        this.rwKtp1 = rwKtp1;
    }

    public String getKelurahanKodeKtp1() {
        return kelurahanKodeKtp1;
    }

    public void setKelurahanKodeKtp1(String kelurahanKodeKtp1) {
        this.kelurahanKodeKtp1 = kelurahanKodeKtp1;
    }

    public String getKelurahanKtp1() {
        return kelurahanKtp1;
    }

    public void setKelurahanKtp1(String kelurahanKtp1) {
        this.kelurahanKtp1 = kelurahanKtp1;
    }

    public String getKecamatanKtp1() {
        return kecamatanKtp1;
    }

    public void setKecamatanKtp1(String kecamatanKtp1) {
        this.kecamatanKtp1 = kecamatanKtp1;
    }

    public String getKotaKtp1() {
        return kotaKtp1;
    }

    public void setKotaKtp1(String kotaKtp1) {
        this.kotaKtp1 = kotaKtp1;
    }

    public String getPropinsiKtp1() {
        return propinsiKtp1;
    }

    public void setPropinsiKtp1(String propinsiKtp1) {
        this.propinsiKtp1 = propinsiKtp1;
    }

    public String getKodePosKtp1() {
        return kodePosKtp1;
    }

    public void setKodePosKtp1(String kodePosKtp1) {
        this.kodePosKtp1 = kodePosKtp1;
    }

    public String getAreaPhoneKtp1() {
        return areaPhoneKtp1;
    }

    public void setAreaPhoneKtp1(String areaPhoneKtp1) {
        this.areaPhoneKtp1 = areaPhoneKtp1;
    }

    public String getPhoneKtp1() {
        return phoneKtp1;
    }

    public void setPhoneKtp1(String phoneKtp1) {
        this.phoneKtp1 = phoneKtp1;
    }

    public Boolean getCheckboxAlamat() {
        return checkboxAlamat;
    }

    public void setCheckboxAlamat(Boolean checkboxAlamat) {
        this.checkboxAlamat = checkboxAlamat;
    }

    public String getNamaLengkap2() {
        return namaLengkap2;
    }

    public void setNamaLengkap2(String namaLengkap2) {
        this.namaLengkap2 = namaLengkap2;
    }

    public String getHubunganKerabat2() {
        return hubunganKerabat2;
    }

    public void setHubunganKerabat2(String hubunganKerabat2) {
        this.hubunganKerabat2 = hubunganKerabat2;
    }

    public int getHubunganKerabatPosition2() {
        return HubunganKerabatPosition2;
    }

    public void setHubunganKerabatPosition2(int hubunganKerabatPosition2) {
        HubunganKerabatPosition2 = hubunganKerabatPosition2;
    }

    public String getAlamat2() {
        return alamat2;
    }

    public void setAlamat2(String alamat2) {
        this.alamat2 = alamat2;
    }

    public String getRt2() {
        return rt2;
    }

    public void setRt2(String rt2) {
        this.rt2 = rt2;
    }

    public String getRw2() {
        return rw2;
    }

    public void setRw2(String rw2) {
        this.rw2 = rw2;
    }

    public String getKelurahanKode2() {
        return kelurahanKode2;
    }

    public void setKelurahanKode2(String kelurahanKode2) {
        this.kelurahanKode2 = kelurahanKode2;
    }

    public String getKelurahan2() {
        return kelurahan2;
    }

    public void setKelurahan2(String kelurahan2) {
        this.kelurahan2 = kelurahan2;
    }

    public String getKecamatan2() {
        return kecamatan2;
    }

    public void setKecamatan2(String kecamatan2) {
        this.kecamatan2 = kecamatan2;
    }

    public String getKota2() {
        return kota2;
    }

    public void setKota2(String kota2) {
        this.kota2 = kota2;
    }

    public String getPropinsi2() {
        return propinsi2;
    }

    public void setPropinsi2(String propinsi2) {
        this.propinsi2 = propinsi2;
    }

    public String getKodePos2() {
        return kodePos2;
    }

    public void setKodePos2(String kodePos2) {
        this.kodePos2 = kodePos2;
    }

    public String getAreaPhoneRumah2() {
        return areaPhoneRumah2;
    }

    public void setAreaPhoneRumah2(String areaPhoneRumah2) {
        this.areaPhoneRumah2 = areaPhoneRumah2;
    }

    public String getPhoneRumah2() {
        return phoneRumah2;
    }

    public void setPhoneRumah2(String phoneRumah2) {
        this.phoneRumah2 = phoneRumah2;
    }

    public String getAreaPhoneKantor2() {
        return areaPhoneKantor2;
    }

    public void setAreaPhoneKantor2(String areaPhoneKantor2) {
        this.areaPhoneKantor2 = areaPhoneKantor2;
    }

    public String getPhoneKantor2() {
        return phoneKantor2;
    }

    public void setPhoneKantor2(String phoneKantor2) {
        this.phoneKantor2 = phoneKantor2;
    }

    public String getMobilePhone2() {
        return mobilePhone2;
    }

    public void setMobilePhone2(String mobilePhone2) {
        this.mobilePhone2 = mobilePhone2;
    }

    public String getNamaPerusahaan3() {
        return namaPerusahaan3;
    }

    public void setNamaPerusahaan3(String namaPerusahaan3) {
        this.namaPerusahaan3 = namaPerusahaan3;
    }

    public String getAlamat3() {
        return alamat3;
    }

    public void setAlamat3(String alamat3) {
        this.alamat3 = alamat3;
    }

    public String getRt3() {
        return rt3;
    }

    public void setRt3(String rt3) {
        this.rt3 = rt3;
    }

    public String getRw3() {
        return rw3;
    }

    public void setRw3(String rw3) {
        this.rw3 = rw3;
    }

    public String getKelurahanKode3() {
        return kelurahanKode3;
    }

    public void setKelurahanKode3(String kelurahanKode3) {
        this.kelurahanKode3 = kelurahanKode3;
    }

    public String getKelurahan3() {
        return kelurahan3;
    }

    public void setKelurahan3(String kelurahan3) {
        this.kelurahan3 = kelurahan3;
    }

    public String getKecamatan3() {
        return kecamatan3;
    }

    public void setKecamatan3(String kecamatan3) {
        this.kecamatan3 = kecamatan3;
    }

    public String getKota3() {
        return kota3;
    }

    public void setKota3(String kota3) {
        this.kota3 = kota3;
    }

    public String getPropinsi3() {
        return propinsi3;
    }

    public void setPropinsi3(String propinsi3) {
        this.propinsi3 = propinsi3;
    }

    public String getKodePos3() {
        return kodePos3;
    }

    public void setKodePos3(String kodePos3) {
        this.kodePos3 = kodePos3;
    }

    public String getAreaPhone3() {
        return areaPhone3;
    }

    public void setAreaPhone3(String areaPhone3) {
        this.areaPhone3 = areaPhone3;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String getTahunKerja3() {
        return tahunKerja3;
    }

    public void setTahunKerja3(String tahunKerja3) {
        this.tahunKerja3 = tahunKerja3;
    }

    public String getCustomerType3() {
        return customerType3;
    }

    public void setCustomerType3(String customerType3) {
        this.customerType3 = customerType3;
    }

    public String getProfesiKode3() {
        return profesiKode3;
    }

    public void setProfesiKode3(String profesiKode3) {
        this.profesiKode3 = profesiKode3;
    }

    public String getProfesi3() {
        return profesi3;
    }

    public void setProfesi3(String profesi3) {
        this.profesi3 = profesi3;
    }

    public String getJobType3() {
        return jobType3;
    }

    public void setJobType3(String jobType3) {
        this.jobType3 = jobType3;
    }

    public String getJobTypeKode3() {
        return jobTypeKode3;
    }

    public void setJobTypeKode3(String jobTypeKode3) {
        this.jobTypeKode3 = jobTypeKode3;
    }

    public String getJobPosition3() {
        return jobPosition3;
    }

    public void setJobPosition3(String jobPosition3) {
        this.jobPosition3 = jobPosition3;
    }

    public String getJobPositionKode3() {
        return jobPositionKode3;
    }

    public void setJobPositionKode3(String jobPositionKode3) {
        this.jobPositionKode3 = jobPositionKode3;
    }

    public String getIndustri3() {
        return industri3;
    }

    public void setIndustri3(String industri3) {
        this.industri3 = industri3;
    }

    public String getIndustriKode3() {
        return industriKode3;
    }

    public void setIndustriKode3(String industriKode3) {
        this.industriKode3 = industriKode3;
    }

    public String getPenghasilanTetap3() {
        return penghasilanTetap3;
    }

    public void setPenghasilanTetap3(String penghasilanTetap3) {
        this.penghasilanTetap3 = penghasilanTetap3;
    }

    public String getPenghasilanLain3() {
        return penghasilanLain3;
    }

    public void setPenghasilanLain3(String penghasilanLain3) {
        this.penghasilanLain3 = penghasilanLain3;
    }

    public String getPenghasilanPasangan3() {
        return penghasilanPasangan3;
    }

    public void setPenghasilanPasangan3(String penghasilanPasangan3) {
        this.penghasilanPasangan3 = penghasilanPasangan3;
    }

    public String getBiayaHidup3() {
        return biayaHidup3;
    }

    public void setBiayaHidup3(String biayaHidup3) {
        this.biayaHidup3 = biayaHidup3;
    }

    public String getCounterPartKode3() {
        return counterPartKode3;
    }

    public void setCounterPartKode3(String counterPartKode3) {
        this.counterPartKode3 = counterPartKode3;
    }

    public String getCounterPart3() {
        return counterPart3;
    }

    public void setCounterPart3(String counterPart3) {
        this.counterPart3 = counterPart3;
    }

    public String getDebtBusinessScale3() {
        return debtBusinessScale3;
    }

    public void setDebtBusinessScale3(String debtBusinessScale3) {
        this.debtBusinessScale3 = debtBusinessScale3;
    }

    public String getDebtGroup3() {
        return debtGroup3;
    }

    public void setDebtGroup3(String debtGroup3) {
        this.debtGroup3 = debtGroup3;
    }

    public String getIsAffiliateWithPp3() {
        return isAffiliateWithPp3;
    }

    public void setIsAffiliateWithPp3(String isAffiliateWithPp3) {
        this.isAffiliateWithPp3 = isAffiliateWithPp3;
    }

    public String getProductOffering4() {
        return productOffering4;
    }

    public void setProductOffering4(String productOffering4) {
        this.productOffering4 = productOffering4;
    }

    public String getProductOfferingKode4() {
        return productOfferingKode4;
    }

    public void setProductOfferingKode4(String productOfferingKode4) {
        this.productOfferingKode4 = productOfferingKode4;
    }

    public String getJumlahAsset4() {
        return jumlahAsset4;
    }

    public void setJumlahAsset4(String jumlahAsset4) {
        this.jumlahAsset4 = jumlahAsset4;
    }

    public String getPosKode4() {
        return posKode4;
    }

    public void setPosKode4(String posKode4) {
        this.posKode4 = posKode4;
    }

    public String getPos4() {
        return pos4;
    }

    public void setPos4(String pos4) {
        this.pos4 = pos4;
    }

    public String getCoverageAsurani5() {
        return coverageAsurani5;
    }

    public void setCoverageAsurani5(String coverageAsurani5) {
        this.coverageAsurani5 = coverageAsurani5;
    }

    public String getIsPersonalAccident5() {
        return isPersonalAccident5;
    }

    public void setIsPersonalAccident5(String isPersonalAccident5) {
        this.isPersonalAccident5 = isPersonalAccident5;
    }

    public boolean isPremiManual() {
        return premiManual;
    }

    public void setPremiManual(boolean premiManual) {
        this.premiManual = premiManual;
    }

    public String getPremiAsuransi6() {
        return premiAsuransi6;
    }

    public void setPremiAsuransi6(String premiAsuransi6) {
        this.premiAsuransi6 = premiAsuransi6;
    }

    public String getTenor6() {
        return tenor6;
    }

    public void setTenor6(String tenor6) {
        this.tenor6 = tenor6;
    }

    public String getPurchasePrice6() {
        return purchasePrice6;
    }

    public void setPurchasePrice6(String purchasePrice6) {
        this.purchasePrice6 = purchasePrice6;
    }

    public String getDiscount6() {
        return discount6;
    }

    public void setDiscount6(String discount6) {
        this.discount6 = discount6;
    }

    public String getDownPayment6() {
        return downPayment6;
    }

    public void setDownPayment6(String downPayment6) {
        this.downPayment6 = downPayment6;
    }

    public String getNtf6() {
        return ntf6;
    }

    public void setNtf6(String ntf6) {
        this.ntf6 = ntf6;
    }

    public String getBiayaLainnya6() {
        return biayaLainnya6;
    }

    public void setBiayaLainnya6(String biayaLainnya6) {
        this.biayaLainnya6 = biayaLainnya6;
    }

    public String getJumlahPembiayaan6() {
        return jumlahPembiayaan6;
    }

    public void setJumlahPembiayaan6(String jumlahPembiayaan6) {
        this.jumlahPembiayaan6 = jumlahPembiayaan6;
    }

    public String getTotalPinjaman6() {
        return totalPinjaman6;
    }

    public void setTotalPinjaman6(String totalPinjaman6) {
        this.totalPinjaman6 = totalPinjaman6;
    }

    public String getAngsuranPertama6() {
        return angsuranPertama6;
    }

    public void setAngsuranPertama6(String angsuranPertama6) {
        this.angsuranPertama6 = angsuranPertama6;
    }

    public String getBiayaAdmin6() {
        return biayaAdmin6;
    }

    public void setBiayaAdmin6(String biayaAdmin6) {
        this.biayaAdmin6 = biayaAdmin6;
    }

    public String getFlatRate6() {
        return flatRate6;
    }

    public void setFlatRate6(String flatRate6) {
        this.flatRate6 = flatRate6;
    }

    public String getEffectiveRate6() {
        return effectiveRate6;
    }

    public void setEffectiveRate6(String effectiveRate6) {
        this.effectiveRate6 = effectiveRate6;
    }

    public String getSetorPertama6() {
        return setorPertama6;
    }

    public void setSetorPertama6(String setorPertama6) {
        this.setorPertama6 = setorPertama6;
    }

    public String getSetorPerbulan6() {
        return setorPerbulan6;
    }

    public void setSetorPerbulan6(String setorPerbulan6) {
        this.setorPerbulan6 = setorPerbulan6;
    }

    public String getRefundSubsidi6() {
        return refundSubsidi6;
    }

    public void setRefundSubsidi6(String refundSubsidi6) {
        this.refundSubsidi6 = refundSubsidi6;
    }

    public String getAngsuranPerbulan12() {
        return angsuranPerbulan12;
    }

    public void setAngsuranPerbulan12(String angsuranPerbulan12) {
        this.angsuranPerbulan12 = angsuranPerbulan12;
    }

    public String getTenor12() {
        return tenor12;
    }

    public void setTenor12(String tenor12) {
        this.tenor12 = tenor12;
    }

    public String getTotalPinjaman12() {
        return totalPinjaman12;
    }

    public void setTotalPinjaman12(String totalPinjaman12) {
        this.totalPinjaman12 = totalPinjaman12;
    }

    public String getNilaiAgunan12() {
        return nilaiAgunan12;
    }

    public void setNilaiAgunan12(String nilaiAgunan12) {
        this.nilaiAgunan12 = nilaiAgunan12;
    }

    public String getBiaya_administrasi12() {
        return biaya_administrasi12;
    }

    public void setBiaya_administrasi12(String biaya_administrasi12) {
        this.biaya_administrasi12 = biaya_administrasi12;
    }

    public String getNilaiPembiayaan12() {
        return nilaiPembiayaan12;
    }

    public void setNilaiPembiayaan12(String nilaiPembiayaan12) {
        this.nilaiPembiayaan12 = nilaiPembiayaan12;
    }

    public String getPremiASuransiAgunan12() {
        return premiASuransiAgunan12;
    }

    public void setPremiASuransiAgunan12(String premiASuransiAgunan12) {
        this.premiASuransiAgunan12 = premiASuransiAgunan12;
    }

    public String getPremiASuransiJiwa12() {
        return premiASuransiJiwa12;
    }

    public void setPremiASuransiJiwa12(String premiASuransiJiwa12) {
        this.premiASuransiJiwa12 = premiASuransiJiwa12;
    }

    public String getBiayaLainnya12() {
        return biayaLainnya12;
    }

    public void setBiayaLainnya12(String biayaLainnya12) {
        this.biayaLainnya12 = biayaLainnya12;
    }

    public String getJumlahPembiayaan12() {
        return jumlahPembiayaan12;
    }

    public void setJumlahPembiayaan12(String jumlahPembiayaan12) {
        this.jumlahPembiayaan12 = jumlahPembiayaan12;
    }

    public String getBungaPembiayaan12() {
        return bungaPembiayaan12;
    }

    public void setBungaPembiayaan12(String bungaPembiayaan12) {
        this.bungaPembiayaan12 = bungaPembiayaan12;
    }

    public String getBungaFlatTahun12() {
        return bungaFlatTahun12;
    }

    public void setBungaFlatTahun12(String bungaFlatTahun12) {
        this.bungaFlatTahun12 = bungaFlatTahun12;
    }

    public String getDpCash12() {
        return dpCash12;
    }

    public void setDpCash12(String dpCash12) {
        this.dpCash12 = dpCash12;
    }

    public String getFidusia12() {
        return fidusia12;
    }

    public void setFidusia12(String fidusia12) {
        this.fidusia12 = fidusia12;
    }

    public String getStnk12() {
        return stnk12;
    }

    public void setStnk12(String stnk12) {
        this.stnk12 = stnk12;
    }

    public String getBiayaLainnya212() {
        return biayaLainnya212;
    }

    public void setBiayaLainnya212(String biayaLainnya212) {
        this.biayaLainnya212 = biayaLainnya212;
    }

    public String getTotalSetorPertama12() {
        return totalSetorPertama12;
    }

    public void setTotalSetorPertama12(String totalSetorPertama12) {
        this.totalSetorPertama12 = totalSetorPertama12;
    }

    public String getBungaFlatBulan12() {
        return bungaFlatBulan12;
    }

    public void setBungaFlatBulan12(String bungaFlatBulan12) {
        this.bungaFlatBulan12 = bungaFlatBulan12;
    }

    public String getHargaKendaraan() {
        return hargaKendaraan;
    }

    public void setHargaKendaraan(String hargaKendaraan) {
        this.hargaKendaraan = hargaKendaraan;
    }

    public String getTtdPemohon7() {
        return ttdPemohon7;
    }

    public void setTtdPemohon7(String ttdPemohon7) {
        this.ttdPemohon7 = ttdPemohon7;
    }

    public String getTtdPasangan7() {
        return ttdPasangan7;
    }

    public void setTtdPasangan7(String ttdPasangan7) {
        this.ttdPasangan7 = ttdPasangan7;
    }

    public String getIdPasangan8() {
        return idPasangan8;
    }

    public void setIdPasangan8(String idPasangan8) {
        this.idPasangan8 = idPasangan8;
    }

    public String getNamaPasangan8() {
        return namaPasangan8;
    }

    public void setNamaPasangan8(String namaPasangan8) {
        this.namaPasangan8 = namaPasangan8;
    }

    public String getNoKtpPasangan8() {
        return noKtpPasangan8;
    }

    public void setNoKtpPasangan8(String noKtpPasangan8) {
        this.noKtpPasangan8 = noKtpPasangan8;
    }

    public String getTempatLahirPasangan8() {
        return tempatLahirPasangan8;
    }

    public void setTempatLahirPasangan8(String tempatLahirPasangan8) {
        this.tempatLahirPasangan8 = tempatLahirPasangan8;
    }

    public String getTanggalLahirPasangan8() {
        return tanggalLahirPasangan8;
    }

    public void setTanggalLahirPasangan8(String tanggalLahirPasangan8) {
        this.tanggalLahirPasangan8 = tanggalLahirPasangan8;
    }

    public String getHandphonePasangan8() {
        return handphonePasangan8;
    }

    public void setHandphonePasangan8(String handphonePasangan8) {
        this.handphonePasangan8 = handphonePasangan8;
    }

    public String getNamaBank9() {
        return namaBank9;
    }

    public void setNamaBank9(String namaBank9) {
        this.namaBank9 = namaBank9;
    }

    public String getNoKartuKredit9() {
        return noKartuKredit9;
    }

    public void setNoKartuKredit9(String noKartuKredit9) {
        this.noKartuKredit9 = noKartuKredit9;
    }

    public String getJenisKartuKredit9() {
        return jenisKartuKredit9;
    }

    public void setJenisKartuKredit9(String jenisKartuKredit9) {
        this.jenisKartuKredit9 = jenisKartuKredit9;
    }

    public String getLimitKartuKredit9() {
        return limitKartuKredit9;
    }

    public void setLimitKartuKredit9(String limitKartuKredit9) {
        this.limitKartuKredit9 = limitKartuKredit9;
    }

    public String getMonthExpiredCard9() {
        return monthExpiredCard9;
    }

    public void setMonthExpiredCard9(String monthExpiredCard9) {
        this.monthExpiredCard9 = monthExpiredCard9;
    }

    public String getYearExpiredCard9() {
        return yearExpiredCard9;
    }

    public void setYearExpiredCard9(String yearExpiredCard9) {
        this.yearExpiredCard9 = yearExpiredCard9;
    }

    public String getNoMembership10() {
        return noMembership10;
    }

    public void setNoMembership10(String noMembership10) {
        this.noMembership10 = noMembership10;
    }

    public String getTanggalEfektif10() {
        return tanggalEfektif10;
    }

    public void setTanggalEfektif10(String tanggalEfektif10) {
        this.tanggalEfektif10 = tanggalEfektif10;
    }

    public String getTanggalExpired10() {
        return tanggalExpired10;
    }

    public void setTanggalExpired10(String tanggalExpired10) {
        this.tanggalExpired10 = tanggalExpired10;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierKode() {
        return supplierKode;
    }

    public void setSupplierKode(String supplierKode) {
        this.supplierKode = supplierKode;
    }

    public String getBankAccountKode() {
        return bankAccountKode;
    }

    public void setBankAccountKode(String bankAccountKode) {
        this.bankAccountKode = bankAccountKode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getMarketingSupplierKode() {
        return marketingSupplierKode;
    }

    public void setMarketingSupplierKode(String marketingSupplierKode) {
        this.marketingSupplierKode = marketingSupplierKode;
    }

    public String getMarketingSupplier() {
        return marketingSupplier;
    }

    public void setMarketingSupplier(String marketingSupplier) {
        this.marketingSupplier = marketingSupplier;
    }

    public String getJenisKb11() {
        return jenisKb11;
    }

    public void setJenisKb11(String jenisKb11) {
        this.jenisKb11 = jenisKb11;
    }

    public String getMerkKb11() {
        return merkKb11;
    }

    public void setMerkKb11(String merkKb11) {
        this.merkKb11 = merkKb11;
    }

    public String getTypeKb11() {
        return typeKb11;
    }

    public void setTypeKb11(String typeKb11) {
        this.typeKb11 = typeKb11;
    }

    public String getTahunKb11() {
        return tahunKb11;
    }

    public void setTahunKb11(String tahunKb11) {
        this.tahunKb11 = tahunKb11;
    }

    public String getWarnaKb11() {
        return warnaKb11;
    }

    public void setWarnaKb11(String warnaKb11) {
        this.warnaKb11 = warnaKb11;
    }

    public String getIsiSilinderKb11() {
        return isiSilinderKb11;
    }

    public void setIsiSilinderKb11(String isiSilinderKb11) {
        this.isiSilinderKb11 = isiSilinderKb11;
    }

    public String getNoPolisiKb11() {
        return noPolisiKb11;
    }

    public void setNoPolisiKb11(String noPolisiKb11) {
        this.noPolisiKb11 = noPolisiKb11;
    }

    public String getNoRangkaKb11() {
        return noRangkaKb11;
    }

    public void setNoRangkaKb11(String noRangkaKb11) {
        this.noRangkaKb11 = noRangkaKb11;
    }

    public String getNoMesinKb11() {
        return noMesinKb11;
    }

    public void setNoMesinKb11(String noMesinKb11) {
        this.noMesinKb11 = noMesinKb11;
    }

    public String getBpkbKb11() {
        return bpkbKb11;
    }

    public void setBpkbKb11(String bpkbKb11) {
        this.bpkbKb11 = bpkbKb11;
    }

    public String getNilaiAgunanKb11() {
        return nilaiAgunanKb11;
    }

    public void setNilaiAgunanKb11(String nilaiAgunanKb11) {
        this.nilaiAgunanKb11 = nilaiAgunanKb11;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getIsAssignedit() {
        return isAssignedit;
    }

    public void setIsAssignedit(String isAssignedit) {
        this.isAssignedit = isAssignedit;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public int getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(int statusSync) {
        this.statusSync = statusSync;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPengajuanType() {
        return pengajuanType;
    }

    public void setPengajuanType(String pengajuanType) {
        this.pengajuanType = pengajuanType;
    }

    public String getMobileSubmissionKey() {
        return mobileSubmissionKey;
    }

    public void setMobileSubmissionKey(String mobileSubmissionKey) {
        this.mobileSubmissionKey = mobileSubmissionKey;
    }

    public String getRefundSubsidi12() {
        return refundSubsidi12;
    }

    public void setRefundSubsidi12(String refundSubsidi12) {
        this.refundSubsidi12 = refundSubsidi12;
    }

    public String getAngsuranPertama12() {
        return angsuranPertama12;
    }

    public void setAngsuranPertama12(String angsuranPertama12) {
        this.angsuranPertama12 = angsuranPertama12;
    }

    public String getTotalInsurancePersen() {
        return totalInsurancePersen;
    }

    public void setTotalInsurancePersen(String totalInsurancePersen) {
        this.totalInsurancePersen = totalInsurancePersen;
    }

    public int getJenisKelaminPosition0() {
        return jenisKelaminPosition0;
    }

    public void setJenisKelaminPosition0(int jenisKelaminPosition0) {
        this.jenisKelaminPosition0 = jenisKelaminPosition0;
    }

    public int getPendidikanPosition0() {
        return pendidikanPosition0;
    }

    public void setPendidikanPosition0(int pendidikanPosition0) {
        this.pendidikanPosition0 = pendidikanPosition0;
    }

    public int getWargaNegaraPosition0() {
        return wargaNegaraPosition0;
    }

    public void setWargaNegaraPosition0(int wargaNegaraPosition0) {
        this.wargaNegaraPosition0 = wargaNegaraPosition0;
    }

    public int getStatusRumahPosition0() {
        return statusRumahPosition0;
    }

    public void setStatusRumahPosition0(int statusRumahPosition0) {
        this.statusRumahPosition0 = statusRumahPosition0;
    }

    public int getAgamaPosition0() {
        return agamaPosition0;
    }

    public void setAgamaPosition0(int agamaPosition0) {
        this.agamaPosition0 = agamaPosition0;
    }

    public int getStatusNikahPosition0() {
        return statusNikahPosition0;
    }

    public void setStatusNikahPosition0(int statusNikahPosition0) {
        this.statusNikahPosition0 = statusNikahPosition0;
    }

    public int getCustomerTypePostion3() {
        return customerTypePostion3;
    }

    public void setCustomerTypePostion3(int customerTypePostion3) {
        this.customerTypePostion3 = customerTypePostion3;
    }

    public int getJumlahAssetPosition4() {
        return jumlahAssetPosition4;
    }

    public void setJumlahAssetPosition4(int jumlahAssetPosition4) {
        this.jumlahAssetPosition4 = jumlahAssetPosition4;
    }

    public int getJenisKbPosition11() {
        return jenisKbPosition11;
    }

    public void setJenisKbPosition11(int jenisKbPosition11) {
        this.jenisKbPosition11 = jenisKbPosition11;
    }

    public int getCoverageAsuraniPosition5() {
        return coverageAsuraniPosition5;
    }

    public void setCoverageAsuraniPosition5(int coverageAsuraniPosition5) {
        this.coverageAsuraniPosition5 = coverageAsuraniPosition5;
    }


    public int getTenorPosition6() {
        return tenorPosition6;
    }

    public void setTenorPosition6(int tenorPosition6) {
        this.tenorPosition6 = tenorPosition6;
    }

    public int getAngsuranPertamaPosition6() {
        return angsuranPertamaPosition6;
    }

    public void setAngsuranPertamaPosition6(int angsuranPertamaPosition6) {
        this.angsuranPertamaPosition6 = angsuranPertamaPosition6;
    }

    public int getTenorPosition12() {
        return tenorPosition12;
    }

    public void setTenorPosition12(int tenorPosition12) {
        this.tenorPosition12 = tenorPosition12;
    }

    public int getAngsuranPertamaPosition12() {
        return angsuranPertamaPosition12;
    }

    public void setAngsuranPertamaPosition12(int angsuranPertamaPosition12) {
        this.angsuranPertamaPosition12 = angsuranPertamaPosition12;
    }

    public String getMetodePenjualan00() {
        return metodePenjualan00;
    }

    public void setMetodePenjualan00(String metodePenjualan00) {
        this.metodePenjualan00 = metodePenjualan00;
    }

    public String getStatusPemohon00() {
        return statusPemohon00;
    }

    public void setStatusPemohon00(String statusPemohon00) {
        this.statusPemohon00 = statusPemohon00;
    }

    public String getTypeDataOffering() {
        return typeDataOffering;
    }

    public void setTypeDataOffering(String typeDataOffering) {
        this.typeDataOffering = typeDataOffering;
    }

    public String getKecamatanKodeTinggal1() {
        return kecamatanKodeTinggal1;
    }

    public void setKecamatanKodeTinggal1(String kecamatanKodeTinggal1) {
        this.kecamatanKodeTinggal1 = kecamatanKodeTinggal1;
    }

    public String getKotaKodeTinggal1() {
        return kotaKodeTinggal1;
    }

    public void setKotaKodeTinggal1(String kotaKodeTinggal1) {
        this.kotaKodeTinggal1 = kotaKodeTinggal1;
    }

    public String getPropinsiKodeTinggal1() {
        return propinsiKodeTinggal1;
    }

    public void setPropinsiKodeTinggal1(String propinsiKodeTinggal1) {
        this.propinsiKodeTinggal1 = propinsiKodeTinggal1;
    }

    public String getKecamatanKodeKtp1() {
        return kecamatanKodeKtp1;
    }

    public void setKecamatanKodeKtp1(String kecamatanKodeKtp1) {
        this.kecamatanKodeKtp1 = kecamatanKodeKtp1;
    }

    public String getKotaKodeKtp1() {
        return kotaKodeKtp1;
    }

    public void setKotaKodeKtp1(String kotaKodeKtp1) {
        this.kotaKodeKtp1 = kotaKodeKtp1;
    }

    public String getPropinsiKodeKtp1() {
        return propinsiKodeKtp1;
    }

    public void setPropinsiKodeKtp1(String propinsiKodeKtp1) {
        this.propinsiKodeKtp1 = propinsiKodeKtp1;
    }

    public String getKecamatanKode2() {
        return kecamatanKode2;
    }

    public void setKecamatanKode2(String kecamatanKode2) {
        this.kecamatanKode2 = kecamatanKode2;
    }

    public String getKotaKode2() {
        return kotaKode2;
    }

    public void setKotaKode2(String kotaKode2) {
        this.kotaKode2 = kotaKode2;
    }

    public String getPropinsiKode2() {
        return propinsiKode2;
    }

    public void setPropinsiKode2(String propinsiKode2) {
        this.propinsiKode2 = propinsiKode2;
    }

    public String getKecamatanKode3() {
        return kecamatanKode3;
    }

    public void setKecamatanKode3(String kecamatanKode3) {
        this.kecamatanKode3 = kecamatanKode3;
    }

    public String getKotaKode3() {
        return kotaKode3;
    }

    public void setKotaKode3(String kotaKode3) {
        this.kotaKode3 = kotaKode3;
    }

    public String getPropinsiKode3() {
        return propinsiKode3;
    }

    public void setPropinsiKode3(String propinsiKode3) {
        this.propinsiKode3 = propinsiKode3;
    }

    public int getAdminFeeLain() {
        return adminFeeLain;
    }

    public void setAdminFeeLain(int adminFeeLain) {
        this.adminFeeLain = adminFeeLain;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFirstInstalment() {
        return firstInstalment;
    }

    public void setFirstInstalment(String firstInstalment) {
        this.firstInstalment = firstInstalment;
    }

//    public String getTipePembayaran() {
//        return tipePembayaran;
//    }
//
//    public void setTipePembayaran(String tipePembayaran) {
//        this.tipePembayaran = tipePembayaran;
//    }

    public int getMetodePenjualanPosition00() {
        return metodePenjualanPosition00;
    }

    public void setMetodePenjualanPosition00(int metodePenjualanPosition00) {
        this.metodePenjualanPosition00 = metodePenjualanPosition00;
    }

    public String getBlacklist_date() {
        return blacklist_date;
    }

    public void setBlacklist_date(String blacklist_date) {
        this.blacklist_date = blacklist_date;
    }

    public String getAutoAlamatTinggal1() {
        return autoAlamatTinggal1;
    }

    public void setAutoAlamatTinggal1(String autoAlamatTinggal1) {
        this.autoAlamatTinggal1 = autoAlamatTinggal1;
    }

    public String getAutoAlamatKtp1() {
        return autoAlamatKtp1;
    }

    public void setAutoAlamatKtp1(String autoAlamatKtp1) {
        this.autoAlamatKtp1 = autoAlamatKtp1;
    }

    public String getAuto_alamat2() {
        return auto_alamat2;
    }

    public void setAuto_alamat2(String auto_alamat2) {
        this.auto_alamat2 = auto_alamat2;
    }

    public String getAuto_alamat3() {
        return auto_alamat3;
    }

    public void setAuto_alamat3(String auto_alamat3) {
        this.auto_alamat3 = auto_alamat3;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        this.masterBranch = masterBranch;
    }

    public String getIdKpm() {
        return idKpm;
    }

    public void setIdKpm(String idKpm) {
        this.idKpm = idKpm;
    }

    public String getAppIdBackend() {
        return appIdBackend;
    }

    public void setAppIdBackend(String appIdBackend) {
        this.appIdBackend = appIdBackend;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getHaveMasking() {
        return haveMasking;
    }

    public void setHaveMasking(int haveMasking) {
        this.haveMasking = haveMasking;
    }

    public boolean isFromSignin() {
        return isFromSignin;
    }

    public void setFromSignin(boolean fromSignin) {
        isFromSignin = fromSignin;
    }

    public int getIsJabar13() {
        return isJabar13;
    }

    public void setIsJabar13(int isJabar13) {
        this.isJabar13 = isJabar13;
    }

    public String getRecomendation13() {
        return recomendation13;
    }

    public void setRecomendation13(String recomendation13) {
        this.recomendation13 = recomendation13;
    }

    public String getRecomendationNote13() {
        return recomendationNote13;
    }

    public void setRecomendationNote13(String recomendationNote13) {
        this.recomendationNote13 = recomendationNote13;
    }

    public String getValidateAction14() {
        return validateAction14;
    }

    public void setValidateAction14(String validateAction14) {
        this.validateAction14 = validateAction14;
    }

    public String getValidateLongitude14() {
        return validateLongitude14;
    }

    public void setValidateLongitude14(String validateLongitude14) {
        this.validateLongitude14 = validateLongitude14;
    }

    public String getValidateLatitude14() {
        return validateLatitude14;
    }

    public void setValidateLatitude14(String validateLatitude14) {
        this.validateLatitude14 = validateLatitude14;
    }

    public String getTakeKtpAction14() {
        return takeKtpAction14;
    }

    public void setTakeKtpAction14(String takeKtpAction14) {
        this.takeKtpAction14 = takeKtpAction14;
    }

    public String getTakeKtpLongitude14() {
        return takeKtpLongitude14;
    }

    public void setTakeKtpLongitude14(String takeKtpLongitude14) {
        this.takeKtpLongitude14 = takeKtpLongitude14;
    }

    public String getTakeKtpLatitude14() {
        return takeKtpLatitude14;
    }

    public void setTakeKtpLatitude14(String takeKtpLatitude14) {
        this.takeKtpLatitude14 = takeKtpLatitude14;
    }

    public String getTakeCustomerAction14() {
        return takeCustomerAction14;
    }

    public void setTakeCustomerAction14(String takeCustomerAction14) {
        this.takeCustomerAction14 = takeCustomerAction14;
    }

    public String getTakeCustomerLongitude14() {
        return takeCustomerLongitude14;
    }

    public void setTakeCustomerLongitude14(String takeCustomerLongitude14) {
        this.takeCustomerLongitude14 = takeCustomerLongitude14;
    }

    public String getTakeCustomerLatitude14() {
        return takeCustomerLatitude14;
    }

    public void setTakeCustomerLatitude14(String takeCustomerLatitude14) {
        this.takeCustomerLatitude14 = takeCustomerLatitude14;
    }

    public String getTakeSignatureAction14() {
        return takeSignatureAction14;
    }

    public void setTakeSignatureAction14(String takeSignatureAction14) {
        this.takeSignatureAction14 = takeSignatureAction14;
    }

    public String getTakeSignatureLongitude14() {
        return takeSignatureLongitude14;
    }

    public void setTakeSignatureLongitude14(String takeSignatureLongitude14) {
        this.takeSignatureLongitude14 = takeSignatureLongitude14;
    }

    public String getTakeSignatureLatitude14() {
        return takeSignatureLatitude14;
    }

    public void setTakeSignatureLatitude14(String takeSignatureLatitude14) {
        this.takeSignatureLatitude14 = takeSignatureLatitude14;
    }

    public String getSubmitAction14() {
        return submitAction14;
    }

    public void setSubmitAction14(String submitAction14) {
        this.submitAction14 = submitAction14;
    }

    public String getSubmitLongitude14() {
        return submitLongitude14;
    }

    public void setSubmitLongitude14(String submitLongitude14) {
        this.submitLongitude14 = submitLongitude14;
    }

    public String getSubmitLatitude14() {
        return submitLatitude14;
    }

    public void setSubmitLatitude14(String submitLatitude14) {
        this.submitLatitude14 = submitLatitude14;
    }

    public String getSyncAction14() {
        return syncAction14;
    }

    public void setSyncAction14(String syncAction14) {
        this.syncAction14 = syncAction14;
    }

    public String getSyncLongitude14() {
        return syncLongitude14;
    }

    public void setSyncLongitude14(String syncLongitude14) {
        this.syncLongitude14 = syncLongitude14;
    }

    public String getSyncLatitude14() {
        return syncLatitude14;
    }

    public void setSyncLatitude14(String syncLatitude14) {
        this.syncLatitude14 = syncLatitude14;
    }

    public String getTakePaycheckAction14() {
        return takePaycheckAction14;
    }

    public void setTakePaycheckAction14(String takePaycheckAction14) {
        this.takePaycheckAction14 = takePaycheckAction14;
    }

    public String getTakePaychecklongitude14() {
        return takePaychecklongitude14;
    }

    public void setTakePaychecklongitude14(String takePaychecklongitude14) {
        this.takePaychecklongitude14 = takePaychecklongitude14;
    }

    public String getTakePaychecklatitude14() {
        return takePaychecklatitude14;
    }

    public void setTakePaychecklatitude14(String takePaychecklatitude14) {
        this.takePaychecklatitude14 = takePaychecklatitude14;
    }

    public String getTakeAdditionalDocumentsAction14() {
        return takeAdditionalDocumentsAction14;
    }

    public void setTakeAdditionalDocumentsAction14(String takeAdditionalDocumentsAction14) {
        this.takeAdditionalDocumentsAction14 = takeAdditionalDocumentsAction14;
    }

    public String getTakeAdditionalDocumentsLongitude14() {
        return takeAdditionalDocumentsLongitude14;
    }

    public void setTakeAdditionalDocumentsLongitude14(String takeAdditionalDocumentsLongitude14) {
        this.takeAdditionalDocumentsLongitude14 = takeAdditionalDocumentsLongitude14;
    }

    public String getTakeAdditionalDocumentsLatitude14() {
        return takeAdditionalDocumentsLatitude14;
    }

    public void setTakeAdditionalDocumentsLatitude14(String takeAdditionalDocumentsLatitude14) {
        this.takeAdditionalDocumentsLatitude14 = takeAdditionalDocumentsLatitude14;
    }

    public String getEfNumber() {
        return efNumber;
    }

    public void setEfNumber(String efNumber) {
        this.efNumber = efNumber;
    }

    public String getBebasBungaPerhitungan6() {
        return bebasBungaPerhitungan6;
    }

    public void setBebasBungaPerhitungan6(String bebasBungaPerhitungan6) {
        this.bebasBungaPerhitungan6 = bebasBungaPerhitungan6;
    }

    public String getBungaPembiayaanBulan6() {
        return bungaPembiayaanBulan6;
    }

    public void setBungaPembiayaanBulan6(String bungaPembiayaanBulan6) {
        this.bungaPembiayaanBulan6 = bungaPembiayaanBulan6;
    }

    public String getPembayaranDealer6() {
        return pembayaranDealer6;
    }

    public void setPembayaranDealer6(String pembayaranDealer6) {
        this.pembayaranDealer6 = pembayaranDealer6;
    }

    public String getPembayaranAwal6() {
        return pembayaranAwal6;
    }

    public void setPembayaranAwal6(String pembayaranAwal6) {
        this.pembayaranAwal6 = pembayaranAwal6;
    }

    public String getBungaTotalPembiayaan6() {
        return bungaTotalPembiayaan6;
    }

    public void setBungaTotalPembiayaan6(String bungaTotalPembiayaan6) {
        this.bungaTotalPembiayaan6 = bungaTotalPembiayaan6;
    }

    public String getBungaPembiayaanPerBulan6() {
        return bungaPembiayaanPerBulan6;
    }

    public void setBungaPembiayaanPerBulan6(String bungaPembiayaanPerBulan6) {
        this.bungaPembiayaanPerBulan6 = bungaPembiayaanPerBulan6;
    }
}
