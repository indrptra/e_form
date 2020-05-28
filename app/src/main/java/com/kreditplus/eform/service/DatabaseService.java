package com.kreditplus.eform.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.backup.IndustryType;
import com.kreditplus.eform.model.Aobranch;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetKendaraan;
import com.kreditplus.eform.model.AssetMaster;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.BranchMaster;
import com.kreditplus.eform.model.DataFinansial;
import com.kreditplus.eform.model.ImgPhotoProfile;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.PosMaster;
import com.kreditplus.eform.model.ProductOfTenor;
import com.kreditplus.eform.model.ProductOfferingMaster;
import com.kreditplus.eform.model.ProductOfferingSupplier;
import com.kreditplus.eform.model.Recomendation;
import com.kreditplus.eform.model.ResultAobranch;
import com.kreditplus.eform.model.SupplierEmp;
import com.kreditplus.eform.model.SupplierMaster;
import com.kreditplus.eform.model.SyncDateDump;
import com.kreditplus.eform.model.TblAgunan;
import com.kreditplus.eform.model.TblAlamat;
import com.kreditplus.eform.model.TblAssetMasterMerkKendaraan;
import com.kreditplus.eform.model.TblAssetMasterTypeKendaraan;
import com.kreditplus.eform.model.TblAsuransi;
import com.kreditplus.eform.model.TblCaraPembayaran;
import com.kreditplus.eform.model.TblDataKartuKredit;
import com.kreditplus.eform.model.TblDataPasangan;
import com.kreditplus.eform.model.TblDataPekerjaan;
import com.kreditplus.eform.model.TblDataPerhitungan;
import com.kreditplus.eform.model.TblDataPerhitunganKendaraan;
import com.kreditplus.eform.model.TblDataPribadi;
import com.kreditplus.eform.model.TblDetailProduct;
import com.kreditplus.eform.model.TblFirebase;
import com.kreditplus.eform.model.TblKartuMembership;
import com.kreditplus.eform.model.TblKeterangan;
import com.kreditplus.eform.model.TblKontakDarurat;
import com.kreditplus.eform.model.TblKop;
import com.kreditplus.eform.model.TblLokasi;
import com.kreditplus.eform.model.TblNewIndustryTypeMaster;
import com.kreditplus.eform.model.TblNewKelurahan;
import com.kreditplus.eform.model.TblNewProfJobPosition;
import com.kreditplus.eform.model.TblNewProfJobType;
import com.kreditplus.eform.model.TblNewProfession;
import com.kreditplus.eform.model.TblRekomendasi;
import com.kreditplus.eform.model.TblTandaTangan;
import com.kreditplus.eform.model.response.objecthelper.AoBranchObjt;
import com.kreditplus.eform.model.response.objecthelper.AssetMasterMerk;
import com.kreditplus.eform.model.response.objecthelper.AssetMasterObjt;
import com.kreditplus.eform.model.response.objecthelper.AssetMasterType;
import com.kreditplus.eform.model.response.objecthelper.Industri;
import com.kreditplus.eform.model.response.objecthelper.JobPosition;
import com.kreditplus.eform.model.response.objecthelper.JobType;
import com.kreditplus.eform.model.response.objecthelper.KelurahanObjt;
import com.kreditplus.eform.model.response.objecthelper.MarketingSupplierObjt;
import com.kreditplus.eform.model.response.objecthelper.POS;
import com.kreditplus.eform.model.response.objecthelper.ProductOfTenorObjt;
import com.kreditplus.eform.model.response.objecthelper.ProductOfferingObjt;
import com.kreditplus.eform.model.response.objecthelper.Profession;
import com.kreditplus.eform.model.response.objecthelper.RecomendationObjt;
import com.kreditplus.eform.model.response.objecthelper.SupplierMasterObjt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.kreditplus.eform.model.ProductOfTenor.AdminFee;
import static com.kreditplus.eform.model.ProductOfTenor.DiscountRateTimes;
import static com.kreditplus.eform.model.ProductOfTenor.EffectiveRate;
import static com.kreditplus.eform.model.ProductOfTenor.FirstInstallment;
import static com.kreditplus.eform.model.ProductOfTenor.FlatRate;
import static com.kreditplus.eform.model.ProductOfTenor.IsActive;
import static com.kreditplus.eform.model.ProductOfTenor.NTF;
import static com.kreditplus.eform.model.ProductOfTenor.ProductID;
import static com.kreditplus.eform.model.ProductOfTenor.ProductOfferingId2;
import static com.kreditplus.eform.model.ProductOfTenor.Tenor;
import static com.kreditplus.eform.model.ProductOfferingMaster.AssetTypeID;
import static com.kreditplus.eform.model.ProductOfferingMaster.Description;
import static com.kreditplus.eform.model.ProductOfferingMaster.ProductID2;
import static com.kreditplus.eform.model.ProductOfferingMaster.ProductOfferingID;
import static com.kreditplus.eform.model.SupplierEmp.SupplierID3;

/**
 * Created by Iwan Nurdesa on 23/11/15.
 */
public class DatabaseService extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "eform_db";
    private String DATABASE_PATH;
    private static final File DATABASE_EXPORTPATH = new File(Environment.getExternalStorageDirectory(), "MyDirectory");
    private static final File DATABASE_PATH_FILE = new File(Environment.getDataDirectory() + "/data/com.kreditplus.eform/databases/" + DATABASE_NAME);
    private static final int DATABASE_VERSION = 13;
    private boolean createDb = false, upgradeDb = false;
    private SQLiteDatabase myDataBase;
    private Context context;

    private Dao<PengajuanBaru, Integer> pengajuanBaruDao = null;
    private Dao<AssetElektronik, String> assetDao = null;
    private Dao<AssetKendaraan, String> assetKendaraanDao = null;
    private Dao<Attachment, Integer> attachmentDao = null;
    private Dao<AssetMaster, String> assetMasterDao = null;
    private Dao<IndustryType, String> industryTypesDao = null;
    private Dao<PosMaster, String> posMasterDao = null;
    private Dao<ProductOfferingMaster, String> productOfferingMasterDao = null;
    private Dao<ProductOfTenor, String> productOfTenorDao = null;
    private Dao<ProductOfferingSupplier, String> productOfferingSuppliersDao = null;
    private Dao<SupplierEmp, String> supplierEmpDao = null;
    private Dao<SupplierMaster, String> supplierMasterDao = null;
    private Dao<DataFinansial, String> dataFinansialDao = null;
    private Dao<Aobranch, String> aobranchDao = null;
    private Dao<BranchMaster, String> branchMasterDao = null;
    private Dao<ImgPhotoProfile, String> imgPhotoProfileDao = null;
    private Dao<ResultAobranch, String> resultAobranchDao = null;
    private Dao<SyncDateDump, String> syncDateDumpDao = null;
    private Dao<MaskingTenor, String> maskingTenorDao = null;
    private Dao<MaskingRate, String> maskingRateDao = null;
    private Dao<Recomendation, String> recomendationDao = null;
    private Dao<TblNewIndustryTypeMaster, String> newIndustryTypeMasterDao = null;
    private Dao<TblNewKelurahan, String> newKelurahanDao = null;
    private Dao<TblNewProfession, String> newProfessionDao = null;
    private Dao<TblNewProfJobPosition, String> newProfJobPositionDao = null;
    private Dao<TblNewProfJobType, String> newProfJobTypeDao = null;
    //    NEW
    private Dao<MasterFormPengajuan, Integer> newMasterFormPengajuanDao = null;
    private Dao<TblKop, String> newTblKop = null;
    private Dao<TblDataPribadi, String> newTblDataPribadi = null;
    private Dao<TblDataPasangan, String> newTblDataPasangan = null;
    private Dao<TblAlamat, String> newTblAlamat = null;
    private Dao<TblKontakDarurat, String> newTblKontakDarurat = null;
    private Dao<TblDataPekerjaan, String> newTblDataPekerjaan = null;
    private Dao<TblDataKartuKredit, String> newTblDataKartuKredit = null;
    private Dao<TblKartuMembership, String> newTblKartuMembership = null;
    private Dao<TblDetailProduct, String> newTblDetailProduct = null;
    private Dao<TblAsuransi, String> newTblAsuransi = null;
    private Dao<TblDataPerhitungan, String> newTblDataPerhitungan = null;
    private Dao<TblKeterangan, String> newTblKeterangan = null;
    private Dao<TblCaraPembayaran, String> newTblCaraPembayaran = null;
    private Dao<TblRekomendasi, String> newTblRekomendasi = null;
    private Dao<TblLokasi, String> newTblLokasi = null;
    private Dao<TblTandaTangan, String> newTblTandaTangan = null;
    private Dao<TblAgunan, String> newTblAgunan = null;
    private Dao<TblDataPerhitunganKendaraan, String> newTblDataPerhitunganKendaraan = null;
    private Dao<TblAssetMasterMerkKendaraan, String> newTblAssetMasterMerkKendaraan = null;
    private Dao<TblAssetMasterTypeKendaraan, String> newTblAssetMasterTypeKendaraan = null;
    private Dao<TblFirebase, Integer> newTblFirebase = null;

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).toString();
        if (BuildConfig.DEBUG) Log.e("DB Path", DATABASE_PATH);
    }

    public void CreateDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (dbExist) {
            this.getWritableDatabase();
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.e("copy Database", String.valueOf(e));
            }
        }
    }

    private boolean checkDatabase() {

        SQLiteDatabase checkDb = null;

        try {
            checkDb = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG) Log.e("Cant Open Database", String.valueOf(e));
        }

        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null ? true : false;

    }

    private void copyDatabase() throws IOException {

        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        String outname = DATABASE_PATH;
        OutputStream outputStream = null;
        byte[] buffer = new byte[1024];
        int length;
        try {
            outputStream = new FileOutputStream(outname);
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e("Cant Read Database", String.valueOf(e));
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    inputStream.close();
                    setDatabaseVersion();
                }
            } catch (IOException e) {
                if (BuildConfig.DEBUG) Log.e("Close outstream ", String.valueOf(e));
            }
        }

    }

    public void OpenDatabase() throws SQLException {
        myDataBase = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    private void setDatabaseVersion() {
        SQLiteDatabase db = null;
        String myPath = DATABASE_PATH;
        try {
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + DATABASE_VERSION);
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG) Log.e("Database Version", String.valueOf(e));
        } finally {
            if (db != null && db.isOpen()) db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) {
            Log.e("oldversion", String.valueOf(oldVersion));
            Log.e("newVersion", String.valueOf(newVersion));
        }

        if (oldVersion < 2) {
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN premiManual BOOLEAN");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN customerId TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN app_id_backend TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN id_pasangan TEXT");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 2", "in");
        }
        if (oldVersion < 3) {
            database.execSQL("CREATE TABLE MaskingTenor ( id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER, tenor INTEGER)");
            database.execSQL("CREATE TABLE MaskingRate ( id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER, rate TEXT)");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN have_masking INTEGER");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN isFromSignin BOOLEAN");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 3", "in");
        }
        if (oldVersion < 4) {
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN no_npwp_0 TEXT");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 4", "in");
        }
        if (oldVersion < 5) {
            database.execSQL("CREATE TABLE tblRecomendation (romendationId TEXT PRIMARY KEY,recomendations TEXT,pengajuan_id INTEGER)");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN recomendation_13 TEXT");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 5", "in");
        }
        if (oldVersion < 6) {
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN recomendation_note_13 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN isJabar_13 INTEGER");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 6", "in");
        }
        if (oldVersion < 7) {
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN validate_action_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN validate_longitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN validate_latitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_ktp_action_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_ktp_longitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_ktp_latitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_customer_action_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_customer_longitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_customer_latitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_signature_action_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_signature_longitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN take_signature_latitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN submit_action_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN submit_longitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN submit_latitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN sync_action_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN sync_longitude_14 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN sync_latitude_14 TEXT");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 7", "in");
        }
        if (oldVersion < 8) {
            database.execSQL("CREATE TABLE ProductOfferingSupplier (BranchID TEXT,ProductID TEXT,ProductOfferingID TEXT,SupplierID TEXT)");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 8", "in");
        }
        if (oldVersion < 9) {
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN ef_number TEXT");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 9", "in");
        }
        if (oldVersion < 10) {
            database.execSQL("ALTER TABLE `productofferingmaster` ADD COLUMN SupplierID TEXT");
            database.execSQL("ALTER TABLE `productofferingmaster` ADD COLUMN SupplierName TEXT");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 10", "in");
        }
        if (oldVersion < 11) {
            database.execSQL("CREATE TABLE tblNewIndustryTypeMaster (IndustryTypeID TEXT,Description TEXT,IsActive TEXT)");
            database.execSQL("CREATE TABLE tblNewKelurahan (City TEXT,Kelurahan TEXT,Kecamatan TEXT,ZipCode TEXT,IsActive TEXT)");
            database.execSQL("CREATE TABLE tblNewProfession (ProfessionID TEXT,Description TEXT,IsActive TEXT)");
            database.execSQL("CREATE TABLE tblNewProfJobPosition (JobTypeID TEXT,JobPositionID TEXT,Description TEXT,IsActive TEXT)");
            database.execSQL("CREATE TABLE tblNewProfJobType (ProfessionID TEXT,JobTypeID TEXT,Description TEXT,IsActive TEXT)");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 11", "in");
        }
        if (oldVersion < 12) {
            database.execSQL("CREATE TABLE MasterFormPengajuan (id INTEGER PRIMARY KEY ASC, have_masking INTEGER, application_id TEXT, app_id_backend TEXT, created_at TEXT, tipe_save_data TEXT, id_kpm TEXT, blacklist_date TEXT, uuid TEXT, customer_id_confins TEXT, id_kpm TEXT, branch TEXT, master_branch TEXT, tipe_data_offering TEXT, tipe_pengajuan TEXT, mobile_submission_key TEXT, ef_number TEXT, status_sync INTEGER)");
            database.execSQL("CREATE TABLE TblKop (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,metode_penjualan TEXT,metode_penjualan_position TEXT,status_customer TEXT)");
            database.execSQL("CREATE TABLE TblDataPribadi (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER, no_ktp TEXT,no_npwp TEXT,status_pernikahan TEXT,tanggal_lahir TEXT,nomor_handphone TEXT,nama_lengkap TEXT,nama_ktp TEXT,tanngal_terbit_ktp TEXT,jenis_kelamin TEXT,tempat_lahir TEXT,nama_ibu_kandung TEXT,status_pendidikan TEXT,status_rumah TEXT,tinggal_sejak_tahun TEXT,tinggal_sejak_bulan TEXT,agama TEXT,jumlah_tanggungan TEXT,email TEXT, status_pernikahan_position INTEGER, jenis_kelamin_position INTEGER,status_pendidikan_position INTEGER,status_rumah_position INTEGER,agama_position INTEGER, warga_negara TEXT, warga_negara_position INTEGER)");
            database.execSQL("CREATE TABLE TblDataPasangan (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,id_pasangan TEXT, nama_lengkap TEXT, nomor_ktp TEXT, tempat_lahir TEXT, tanggal_lahir TEXT, nomor_handphone TEXT)");
            database.execSQL("CREATE TABLE TblAlamat (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,alamat_tinggal TEXT, rt_tinggal TEXT, rw_tinggal TEXT, kota_tinggal TEXT, kode_area_telepon_tinggal TEXT, nomor_telepon_tinggal TEXT, alamat_sama_ktp TEXT, alamat_ktp TEXT, rt_ktp TEXT, rw_ktp TEXT, kota_ktp TEXT, kode_area_telepon_ktp TEXT, nomor_telepon_ktp TEXT,kecamatan_tinggal TEXT, kelurahan_tinggal TEXT, zipcode_tinggal TEXT, kecamatan_ktp TEXT, kelurahan_ktp TEXT, zipcode_ktp TEXT)");
            database.execSQL("CREATE TABLE TblKontakDarurat (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,nama_lengkap TEXT, hubungan_kerabat TEXT, alamat TEXT, rt TEXT, rw TEXT, kota TEXT, kode_area_telepon_rumah TEXT, nomor_telepon_rumah TEXT, kode_area_telepon_kantor TEXT, nomor_telepon_kantor TEXT, nomor_handphone TEXT, hubungan_kerabat_position INTEGER,kecamatan TEXT, kelurahan TEXT, zipcode TEXT)");
            database.execSQL("CREATE TABLE TblDataPekerjaan (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,nama_perubahaan TEXT, alamat TEXT, rt TEXT, rw TEXT, kota TEXT, kode_area_telepon TEXT, nomor_telepon TEXT, bekerja_sejak TEXT, profesi TEXT, tipe_pekerjaan TEXT, posisi_pekerjaan TEXT, industri TEXT, penghasilan_tetap TEXT, penghasilan_lain TEXT, penghasilan_pasangan TEXT, biaya_hidup TEXT,kode_profesi TEXT,kode_tipe_pekerjaan TEXT,kode_posisi_pekerjaan TEXT,kode_industri TEXT, kecamatan TEXT, kelurahan TEXT, zipcode TEXT)");
            database.execSQL("CREATE TABLE TblDataKartuKredit (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,nama_bank TEXT, no_kartu_kredit TEXT, jenis_kartu_kredit TEXT, limit_kartu_kredit TEXT, tahun_kadaluarsa_kartu_kredit TEXT, bulan_kadaluarsa_kartu_kredit TEXT)");
            database.execSQL("CREATE TABLE TblKartuMembership (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,no_membership TEXT, tanggal_efektif TEXT, tanggal_exipred TEXT)");
            database.execSQL("CREATE TABLE TblDetailProduct (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,id_bank TEXT, kode_supplier TEXT, supplier TEXT, kode_marketing_supplier TEXT, marketing_supplier TEXT, kode_product_id TEXT, kode_product_offering_id TEXT, product_offering TEXT, pos_id TEXT, pos TEXT, jumlah_asset TEXT)");
            database.execSQL("CREATE TABLE TblAsuransi (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,manual_agunan TEXT,manual_premi TEXT)");
            database.execSQL("CREATE TABLE TblDataPerhitungan (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,tenor TEXT, flate_rate TEXT, biaya_administrasi TEXT, tipe_pembayaran TEXT, biaya_lainnya TEXT, refund_subsidi TEXT, bebas_bunga TEXT, total_price TEXT, total_discount TEXT, total_dp TEXT, premi TEXT, ntf TEXT, jumlah_pembiayaan TEXT, total_bunga_pembiayaan TEXT, bulan_bunga_pembiayaan TEXT, total_pinjaman TEXT, angsuran_prebulan_bebas_bunga TEXT, angsuran_perbulan TEXT, pembayaran_awal TEXT, pembayaran_delaer TEXT,posisi_tenor INTEGER,effective_rate TEXT, admin_fee_lainnya TEXT)");
            database.execSQL("CREATE TABLE TblKeterangan (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,keterangan TEXT)");
            database.execSQL("CREATE TABLE TblRekomendasi (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,id_rekomendasi TEXT,rekomendasi TEXT,catatan TEXT,jabar INTEGER)");
            database.execSQL("CREATE TABLE TblLokasi (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,validation_action TEXT , validation_longitude TEXT , validation_latitude TEXT , ktp_action TEXT , ktp_longitude TEXT , ktp_latitude TEXT , customer_action TEXT , customer_longitude TEXT , customer_latitude TEXT , paycheck_action TEXT , paycheck_longitude TEXT , paycheck_latitude TEXT , addtional_documents_action TEXT , addtional_documents_longitude TEXT , addtional_documents_latitude TEXT , signature_action TEXT , signature_longitude TEXT , signature_latitude TEXT , submit_action TEXT , submit_longitude TEXT , submit_latitude TEXT , sync_action TEXT , sync_longitude TEXT , sync_latitude TEXT)");
            database.execSQL("CREATE TABLE TblTandaTangan (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER,ttd_pemohon TEXT, ttd_pasangan TEXT, jumlah_ttd INTEGER)");
            database.execSQL("ALTER TABLE `productoftenor` ADD COLUMN DiscountRateTimes INTEGER");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN bebas_bunga_perhitungan_6 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN bunga_pembiayaan_bulan_6 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN pembayaran_dealer_6 TEXT");
            database.execSQL("ALTER TABLE `pengajuan_baru` ADD COLUMN pembayaran_awal_6 TEXT");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 12", "in");
        }
        if (oldVersion < 13) {
            database.execSQL("ALTER TABLE `detail_asset` ADD COLUMN category TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kode_area_telepon_rumah TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN no_telepon_rumah TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kode_area_telepon_perusahaan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN no_telepon_perusahaan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN alamat_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kota_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN profesi_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN job_type_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN job_position_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN industri_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN status_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN nama_perusahaan_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kecamatan_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kelurahan_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN zipcode_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kode_profesi_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kode_job_type_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kode_job_position_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDataPasangan` ADD COLUMN kode_industri_pasangan TEXT");
            database.execSQL("ALTER TABLE `TblDetailProduct` ADD COLUMN status_konsumen TEXT");
            database.execSQL("CREATE TABLE TblAgunan (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER, wilayah_kendaraan TEXT,cabang_kendaraan TEXT,jenis_kendaraan TEXT,merk_kendaraan TEXT,type_kendaraan TEXT,tahun_kendaraan TEXT,warna_kendaraan TEXT,status_kendaraan TEXT,isi_silinder TEXT,no_polisi TEXT,no_rangka TEXT,no_mesin TEXT,bpkb_atas_nama TEXT,nama_bpkb TEXT,masa_berlaku_stnk TEXT,masa_berlaku_pajak_stnk TEXT,pemakaian_kendaraan TEXT,negara_produksi TEXT,kode_jenis_kendaraan TEXT,kode_merk_kendaraan TEXT,kode_type_kendaraan TEXT)");
            database.execSQL("CREATE TABLE TblDataPerhitunganKendaraan (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER, harga_agunan TEXT, pokok_pembiayaan TEXT, biaya_admin_perhitungan_kendaraan TEXT, premi_asuransi_perhitungan_agunan_kendaraan TEXT, premi_asuransi_perhitungan_jiwa_kendaraan TEXT, jumlah_pembiayaan_perhitungan_kendaraan TEXT, bunga_pembiayaan_kendaraan TEXT, total_pinjaman_perhitungan_kendaraan TEXT, nilai_agunan_perhitungan_kendaraan TEXT, angsuran_perbulan_perhitungan_kendaraan TEXT, flat_pertahun_perhitungan_kendaraan TEXT, flat_perbulan_perhitungan_kendaraan TEXT, asuransi_agunan TEXT, tenor_perhitungan_kendaraan TEXT, fidusia_perhitungan_kendaraan TEXT, stnk_perhitungan_kendaraan TEXT, biaya_lainnya_perhitungan_kendaraan TEXT, angsuran_pertama_perhitungan_kendaraan TEXT)");
            database.execSQL("CREATE TABLE TblAssetMasterMerkKendaraan (AssetCode TEXT, Description TEXT, AssetTypeId TEXT)");
            database.execSQL("CREATE TABLE TblAssetMasterTypeKendaraan (AssetCode TEXT, Description TEXT, AssetTypeId TEXT)");
            database.execSQL("CREATE TABLE TblFirebase (id INTEGER PRIMARY KEY ASC, TokenFCM TEXT)");
            database.execSQL("ALTER TABLE `TblKop` ADD COLUMN StatusKreditmu TEXT");
            database.execSQL("ALTER TABLE `TblKop` ADD COLUMN JenisPembiayaan TEXT");
            database.execSQL("ALTER TABLE `TblKop` ADD COLUMN JenisPembiayaanPosition TEXT");
            database.execSQL("ALTER TABLE `TblKop` ADD COLUMN TujuanDana TEXT");
            database.execSQL("ALTER TABLE `TblKop` ADD COLUMN TujuanDanaPosition TEXT");
            database.execSQL("ALTER TABLE `TblKop` ADD COLUMN TujuanDanaLain TEXT");
            database.execSQL("ALTER TABLE `TblKop` ADD COLUMN MetodePenjualanLain TEXT");
            database.execSQL("ALTER TABLE `TblDataPribadi` ADD COLUMN PerjanjianPisahHarta TEXT");
            database.execSQL("CREATE TABLE TblCaraPembayaran (id INTEGER PRIMARY KEY ASC, pengajuan_id INTEGER, CaraPembayaran TEXT, CaraPembayaranPosition TEXT)");
            if (BuildConfig.DEBUG) Log.e("onUpgrade 13", "in");
        }
    }

    @Override
    public void close() {
        super.close();
        pengajuanBaruDao = null;
        assetDao = null;
        assetKendaraanDao = null;
        attachmentDao = null;
        assetMasterDao = null;
        industryTypesDao = null;
        posMasterDao = null;
        productOfferingMasterDao = null;
        productOfferingSuppliersDao = null;
        productOfTenorDao = null;
        supplierEmpDao = null;
        supplierMasterDao = null;
        dataFinansialDao = null;
        aobranchDao = null;
        branchMasterDao = null;
        imgPhotoProfileDao = null;
        resultAobranchDao = null;
        syncDateDumpDao = null;
        maskingRateDao = null;
        maskingTenorDao = null;
        recomendationDao = null;
        newIndustryTypeMasterDao = null;
        newKelurahanDao = null;
        newProfessionDao = null;
        newProfJobPositionDao = null;
        newProfJobTypeDao = null;
//        new
        newMasterFormPengajuanDao = null;
        newTblKop = null;
        newTblDataPribadi = null;
        newTblDataPasangan = null;
        newTblAlamat = null;
        newTblKontakDarurat = null;
        newTblDataPekerjaan = null;
        newTblDataKartuKredit = null;
        newTblKartuMembership = null;
        newTblDetailProduct = null;
        newTblAsuransi = null;
        newTblDataPerhitungan = null;
        newTblKeterangan = null;
        newTblCaraPembayaran = null;
        newTblRekomendasi = null;
        newTblLokasi = null;
        newTblTandaTangan = null;
        newTblAgunan = null;
        newTblDataPerhitunganKendaraan = null;
        newTblAssetMasterMerkKendaraan = null;
        newTblAssetMasterTypeKendaraan = null;
        newTblFirebase = null;
    }

    private void createNewTable(ConnectionSource connectionSource) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Recomendation.class);
        TableUtils.createTableIfNotExists(connectionSource, ProductOfferingSupplier.class);
        TableUtils.createTableIfNotExists(connectionSource, TblNewIndustryTypeMaster.class);
        TableUtils.createTableIfNotExists(connectionSource, TblNewKelurahan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblNewProfession.class);
        TableUtils.createTableIfNotExists(connectionSource, TblNewProfJobPosition.class);
        TableUtils.createTableIfNotExists(connectionSource, TblNewProfJobType.class);
        TableUtils.createTableIfNotExists(connectionSource, MasterFormPengajuan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblKop.class);
        TableUtils.createTableIfNotExists(connectionSource, TblDataPribadi.class);
        TableUtils.createTableIfNotExists(connectionSource, TblDataPasangan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblAlamat.class);
        TableUtils.createTableIfNotExists(connectionSource, TblKontakDarurat.class);
        TableUtils.createTableIfNotExists(connectionSource, TblDataPekerjaan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblDataKartuKredit.class);
        TableUtils.createTableIfNotExists(connectionSource, TblKartuMembership.class);
        TableUtils.createTableIfNotExists(connectionSource, TblDetailProduct.class);
        TableUtils.createTableIfNotExists(connectionSource, TblAsuransi.class);
        TableUtils.createTableIfNotExists(connectionSource, TblDataPerhitungan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblKeterangan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblCaraPembayaran.class);
        TableUtils.createTableIfNotExists(connectionSource, TblRekomendasi.class);
        TableUtils.createTableIfNotExists(connectionSource, TblLokasi.class);
        TableUtils.createTableIfNotExists(connectionSource, TblTandaTangan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblAgunan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblDataPerhitunganKendaraan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblAssetMasterMerkKendaraan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblAssetMasterTypeKendaraan.class);
        TableUtils.createTableIfNotExists(connectionSource, TblFirebase.class);

        TableUtils.createTableIfNotExists(connectionSource, AssetElektronik.class);
        TableUtils.createTableIfNotExists(connectionSource, AssetKendaraan.class);
        TableUtils.createTableIfNotExists(connectionSource, Attachment.class);
        TableUtils.createTableIfNotExists(connectionSource, AssetMaster.class);
        TableUtils.createTableIfNotExists(connectionSource, IndustryType.class);
        TableUtils.createTableIfNotExists(connectionSource, PosMaster.class);
        TableUtils.createTableIfNotExists(connectionSource, ProductOfferingMaster.class);
        TableUtils.createTableIfNotExists(connectionSource, ProductOfTenor.class);
        TableUtils.createTableIfNotExists(connectionSource, SupplierEmp.class);
        TableUtils.createTableIfNotExists(connectionSource, SupplierMaster.class);
        TableUtils.createTableIfNotExists(connectionSource, DataFinansial.class);
        TableUtils.createTableIfNotExists(connectionSource, Aobranch.class);
        TableUtils.createTableIfNotExists(connectionSource, BranchMaster.class);
        TableUtils.createTableIfNotExists(connectionSource, ImgPhotoProfile.class);
        TableUtils.createTableIfNotExists(connectionSource, ResultAobranch.class);
        TableUtils.createTableIfNotExists(connectionSource, SyncDateDump.class);
        TableUtils.createTableIfNotExists(connectionSource, MaskingTenor.class);
        TableUtils.createTableIfNotExists(connectionSource, MaskingRate.class);
    }

    private void dropAllTable(ConnectionSource connectionSource) throws SQLException {

        // ------------------------ tabel data dump tidak dihapus --------------------------------
//        TableUtils.dropTable(connectionSource, Kelurahan.class, true);
//        TableUtils.dropTable(connectionSource, AssetMaster.class, true);
//        TableUtils.dropTable(connectionSource, IndustryType.class, true);
//        TableUtils.dropTable(connectionSource, TypeJobPosition.class, true);
//        TableUtils.dropTable(connectionSource, Profession.class, true);
//        TableUtils.dropTable(connectionSource, ProfessionJobType.class, true);
//        TableUtils.dropTable(connectionSource, StatusCreate.class, true);
//        TableUtils.dropTable(connectionSource, ResultKelurahan.class, true);
        // ----------------------- tabel data yang di hapus --------------------------------------
        TableUtils.dropTable(connectionSource, PengajuanBaru.class, true);
        TableUtils.dropTable(connectionSource, AssetElektronik.class, true);
        TableUtils.dropTable(connectionSource, AssetKendaraan.class, true);
        TableUtils.dropTable(connectionSource, Attachment.class, true);
        TableUtils.dropTable(connectionSource, PosMaster.class, true);
        TableUtils.dropTable(connectionSource, ProductOfferingMaster.class, true);
        TableUtils.dropTable(connectionSource, ProductOfTenor.class, true);
        TableUtils.dropTable(connectionSource, SupplierEmp.class, true);
        TableUtils.dropTable(connectionSource, SupplierMaster.class, true);
        TableUtils.dropTable(connectionSource, DataFinansial.class, true);
        TableUtils.dropTable(connectionSource, Aobranch.class, true);
        TableUtils.dropTable(connectionSource, BranchMaster.class, true);
        TableUtils.dropTable(connectionSource, ImgPhotoProfile.class, true);
        TableUtils.dropTable(connectionSource, ResultAobranch.class, true);
        TableUtils.dropTable(connectionSource, SyncDateDump.class, true);
        TableUtils.dropTable(connectionSource, MaskingTenor.class, true);
        TableUtils.dropTable(connectionSource, MaskingRate.class, true);
        TableUtils.dropTable(connectionSource, Recomendation.class, true);
        TableUtils.dropTable(connectionSource, ProductOfferingSupplier.class, true);
    }

    public void createDatabase() throws SQLException {
        createNewTable(getConnectionSource());
    }

    public void resetDatabase() throws SQLException {
        dropAllTable(getConnectionSource());
    }

    public Dao<PengajuanBaru, Integer> getPengajuanBaruDao() throws SQLException {
        if (pengajuanBaruDao == null) pengajuanBaruDao = getDao(PengajuanBaru.class);
        return pengajuanBaruDao;
    }

    public Dao<AssetElektronik, String> getAssetDao() throws SQLException {
        if (assetDao == null) assetDao = getDao(AssetElektronik.class);
        return assetDao;
    }

    public Dao<Attachment, Integer> getAttachmentDao() throws SQLException {
        if (attachmentDao == null) attachmentDao = getDao(Attachment.class);
        return attachmentDao;
    }

    public Dao<AssetKendaraan, String> getAssetKendaraanDao() throws SQLException {
        if (assetKendaraanDao == null) assetKendaraanDao = getDao(AssetKendaraan.class);
        return assetKendaraanDao;
    }

    public Dao<AssetMaster, String> getAssetMastersDao() throws SQLException {
        if (assetMasterDao == null) assetMasterDao = getDao(AssetMaster.class);
        return assetMasterDao;
    }

    public Dao<PosMaster, String> getPosMasterDao() throws SQLException {
        if (posMasterDao == null) posMasterDao = getDao(PosMaster.class);
        return posMasterDao;
    }

    public Dao<ProductOfferingMaster, String> getProductOfferingMasterDao() throws SQLException {
        if (productOfferingMasterDao == null)
            productOfferingMasterDao = getDao(ProductOfferingMaster.class);
        return productOfferingMasterDao;
    }

    public Dao<ProductOfferingSupplier, String> productOfferingSuppliersDao() throws SQLException {
        if (productOfferingSuppliersDao == null)
            productOfferingSuppliersDao = getDao(ProductOfferingSupplier.class);
        return productOfferingSuppliersDao;
    }

    public Dao<ProductOfTenor, String> getProductOfTenorDao() throws SQLException {
        if (productOfTenorDao == null) productOfTenorDao = getDao(ProductOfTenor.class);
        return productOfTenorDao;
    }

    public Dao<SupplierEmp, String> getSupplierEmpDao() throws SQLException {
        if (supplierEmpDao == null) supplierEmpDao = getDao(SupplierEmp.class);
        return supplierEmpDao;
    }

    public Dao<SupplierMaster, String> getSupplierMasterDao() throws SQLException {
        if (supplierMasterDao == null) supplierMasterDao = getDao(SupplierMaster.class);
        return supplierMasterDao;
    }

    public Dao<DataFinansial, String> getDataFinansialDao() throws SQLException {
        if (dataFinansialDao == null) dataFinansialDao = getDao(DataFinansial.class);
        return dataFinansialDao;
    }

    public Dao<Aobranch, String> getAobranchDao() throws SQLException {
        if (aobranchDao == null) aobranchDao = getDao(Aobranch.class);
        return aobranchDao;
    }

    public Dao<BranchMaster, String> getBranchMasterDao() throws SQLException {
        if (branchMasterDao == null) branchMasterDao = getDao(BranchMaster.class);
        return branchMasterDao;
    }

    public Dao<ImgPhotoProfile, String> getImgPhotoProfileDao() throws SQLException {
        if (imgPhotoProfileDao == null) imgPhotoProfileDao = getDao(ImgPhotoProfile.class);
        return imgPhotoProfileDao;
    }

    public Dao<ResultAobranch, String> getResultAobranchDao() throws SQLException {
        if (resultAobranchDao == null) resultAobranchDao = getDao(ResultAobranch.class);
        return resultAobranchDao;
    }

    public Dao<SyncDateDump, String> getSyncDateDumpDao() throws SQLException {
        if (syncDateDumpDao == null) syncDateDumpDao = getDao(SyncDateDump.class);
        return syncDateDumpDao;
    }

    public Dao<MaskingTenor, String> getMaskingTenorDao() throws SQLException {
        if (maskingTenorDao == null) maskingTenorDao = getDao(MaskingTenor.class);
        return maskingTenorDao;
    }

    public Dao<MaskingRate, String> getMaskingRateDao() throws SQLException {
        if (maskingRateDao == null) maskingRateDao = getDao(MaskingRate.class);
        return maskingRateDao;
    }

    public Dao<Recomendation, String> getRecomendationDao() throws SQLException {
        if (recomendationDao == null) recomendationDao = getDao(Recomendation.class);
        return recomendationDao;
    }

    public Dao<TblNewIndustryTypeMaster, String> getIndustryTypeMasterDao() throws SQLException {
        if (newIndustryTypeMasterDao == null)
            newIndustryTypeMasterDao = getDao(TblNewIndustryTypeMaster.class);
        return newIndustryTypeMasterDao;
    }

    public Dao<TblNewKelurahan, String> getKelurahanDao() throws SQLException {
        if (newKelurahanDao == null) newKelurahanDao = getDao(TblNewKelurahan.class);
        return newKelurahanDao;
    }

    public Dao<TblNewProfession, String> getProfessionDao() throws SQLException {
        if (newProfessionDao == null) newProfessionDao = getDao(TblNewProfession.class);
        return newProfessionDao;
    }

    public Dao<TblNewProfJobPosition, String> getProfJobPositionDao() throws SQLException {
        if (newProfJobPositionDao == null)
            newProfJobPositionDao = getDao(TblNewProfJobPosition.class);
        return newProfJobPositionDao;
    }

    public Dao<TblNewProfJobType, String> getProfJobTypeDao() throws SQLException {
        if (newProfJobTypeDao == null) newProfJobTypeDao = getDao(TblNewProfJobType.class);
        return newProfJobTypeDao;
    }

    public Dao<TblKop, String> getTblKopDao() throws SQLException {
        if (newTblKop == null) newTblKop = getDao(TblKop.class);
        return newTblKop;
    }

    public Dao<TblDataPribadi, String> getTblDataPribadiDao() throws SQLException {
        if (newTblDataPribadi == null) newTblDataPribadi = getDao(TblDataPribadi.class);
        return newTblDataPribadi;
    }

    public Dao<TblDataPasangan, String> getTblDataPasanganDao() throws SQLException {
        if (newTblDataPasangan == null) newTblDataPasangan = getDao(TblDataPasangan.class);
        return newTblDataPasangan;
    }

    public Dao<TblAlamat, String> getTblAlamatDao() throws SQLException {
        if (newTblAlamat == null) newTblAlamat = getDao(TblAlamat.class);
        return newTblAlamat;
    }

    public Dao<TblKontakDarurat, String> getTblKontakDaruratDao() throws SQLException {
        if (newTblKontakDarurat == null) newTblKontakDarurat = getDao(TblKontakDarurat.class);
        return newTblKontakDarurat;
    }

    public Dao<TblDataPekerjaan, String> getTblDataPekerjaanDao() throws SQLException {
        if (newTblDataPekerjaan == null) newTblDataPekerjaan = getDao(TblDataPekerjaan.class);
        return newTblDataPekerjaan;
    }

    public Dao<TblDataKartuKredit, String> getTblDataKartuKreditDao() throws SQLException {
        if (newTblDataKartuKredit == null) newTblDataKartuKredit = getDao(TblDataKartuKredit.class);
        return newTblDataKartuKredit;
    }

    public Dao<TblKartuMembership, String> getTblKartuMembershipDao() throws SQLException {
        if (newTblKartuMembership == null) newTblKartuMembership = getDao(TblKartuMembership.class);
        return newTblKartuMembership;
    }

    public Dao<TblDetailProduct, String> getTblDetailProductDao() throws SQLException {
        if (newTblDetailProduct == null) newTblDetailProduct = getDao(TblDetailProduct.class);
        return newTblDetailProduct;
    }

    public Dao<TblAsuransi, String> getTblAsuransiDao() throws SQLException {
        if (newTblAsuransi == null) newTblAsuransi = getDao(TblAsuransi.class);
        return newTblAsuransi;
    }

    public Dao<TblDataPerhitungan, String> getTblDataPerhitunganDao() throws SQLException {
        if (newTblDataPerhitungan == null) newTblDataPerhitungan = getDao(TblDataPerhitungan.class);
        return newTblDataPerhitungan;
    }

    public Dao<TblKeterangan, String> getTblKeteranganDao() throws SQLException {
        if (newTblKeterangan == null) newTblKeterangan = getDao(TblKeterangan.class);
        return newTblKeterangan;
    }

    public Dao<TblCaraPembayaran, String> getTblCaraPembayaranDao() throws SQLException {
        if (newTblCaraPembayaran == null) newTblCaraPembayaran = getDao(TblCaraPembayaran.class);
        return newTblCaraPembayaran;
    }

    public Dao<TblRekomendasi, String> getTblRekomendasiDao() throws SQLException {
        if (newTblRekomendasi == null) newTblRekomendasi = getDao(TblRekomendasi.class);
        return newTblRekomendasi;
    }

    public Dao<TblLokasi, String> getTblLokasiDao() throws SQLException {
        if (newTblLokasi == null) newTblLokasi = getDao(TblLokasi.class);
        return newTblLokasi;
    }

    public Dao<TblTandaTangan, String> getTblTandaTanganDao() throws SQLException {
        if (newTblTandaTangan == null) newTblTandaTangan = getDao(TblTandaTangan.class);
        return newTblTandaTangan;
    }

    public Dao<TblAgunan, String> getTblAgunanDao() throws SQLException {
        if (newTblAgunan == null) newTblAgunan = getDao(TblAgunan.class);
        return newTblAgunan;
    }

    public Dao<TblDataPerhitunganKendaraan, String> getTblDataPerhitunganKendaraanDao() throws SQLException {
        if (newTblDataPerhitunganKendaraan == null)
            newTblDataPerhitunganKendaraan = getDao(TblDataPerhitunganKendaraan.class);
        return newTblDataPerhitunganKendaraan;
    }

    public Dao<TblAssetMasterMerkKendaraan, String> getTblAssetMasterMerkKendaraanDao() throws SQLException {
        if (newTblAssetMasterMerkKendaraan == null)
            newTblAssetMasterMerkKendaraan = getDao(TblAssetMasterMerkKendaraan.class);
        return newTblAssetMasterMerkKendaraan;
    }

    public Dao<TblAssetMasterTypeKendaraan, String> getTblAssetMasterTypeKendaraanDao() throws SQLException {
        if (newTblAssetMasterTypeKendaraan == null)
            newTblAssetMasterTypeKendaraan = getDao(TblAssetMasterTypeKendaraan.class);
        return newTblAssetMasterTypeKendaraan;
    }

    public Dao<TblFirebase, Integer> getTblFirebaseDao() throws SQLException {
        if (newTblFirebase == null) newTblFirebase = getDao(TblFirebase.class);
        return newTblFirebase;
    }

    public Dao<MasterFormPengajuan, Integer> getMasterFormPengajuanDao() throws SQLException {
        if (newMasterFormPengajuanDao == null)
            newMasterFormPengajuanDao = getDao(MasterFormPengajuan.class);
        return newMasterFormPengajuanDao;
    }

    public List<AoBranchObjt> getAllBranch() {
        List<AoBranchObjt> aoBranchObjtList = new ArrayList<AoBranchObjt>();

        String selectQuery = "select * From resultAobranch Where IsActive = 'True' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                aoBranchObjtList.add(new AoBranchObjt(cursor.getString(cursor.getColumnIndex("Branchid")), cursor.getString(cursor.getColumnIndex("isActive")), cursor.getString(cursor.getColumnIndex("ResultBranch"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return aoBranchObjtList;
    }

    public List<ProductOfferingObjt> getAllProductOfering(String assetType, String branch, String supplierId) {
        List<ProductOfferingObjt> labelProOff = new ArrayList<ProductOfferingObjt>();

        String selectQuery = "select " + Description + " , " + ProductID2 + " , " + ProductOfferingID + " FROM productofferingmaster Where BranchID = " + branch + " AND " + AssetTypeID + "=?";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{assetType});

        if (cursor.moveToFirst()) {
            do {
                labelProOff.add(new ProductOfferingObjt(cursor.getString((cursor.getColumnIndex("Description"))), cursor.getString(cursor.getColumnIndex("ProductOfferingID")), cursor.getString(cursor.getColumnIndex("ProductID"))));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelProOff;
    }

    public List<POS> getAllPos(String branch) {
        List<POS> labelPos = new ArrayList<POS>();

        String selectQuery = "select * FROM posmaster Where BranchID =? ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{branch});

        if (cursor.moveToFirst()) {
            do {
                labelPos.add(new POS(cursor.getString(cursor.getColumnIndex("POSID")), cursor.getString(cursor.getColumnIndex("POSName"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelPos;
    }

    public List<ProductOfTenorObjt> getAllProdOfTenor(String pof2, String pof, String branch) {
        List<ProductOfTenorObjt> labelPos = new ArrayList<ProductOfTenorObjt>();
        String selectQuery = "select " + Tenor + " , " + NTF + " , " + FlatRate + " , " + EffectiveRate + " , " + AdminFee + " , " + FirstInstallment + " , " + DiscountRateTimes + " FROM productoftenor Where BranchID = " + branch + " AND " + ProductID + " = '" + pof2 + "' AND " + ProductOfferingId2 + " = '" + pof + "' AND " + IsActive + "= '1' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labelPos.add(new ProductOfTenorObjt(cursor.getString(cursor.getColumnIndex("Tenor")), cursor.getString(cursor.getColumnIndex("NTF")), cursor.getString(cursor.getColumnIndex("FlatRate")), cursor.getString(cursor.getColumnIndex("EffectiveRate")), cursor.getString(cursor.getColumnIndex("AdminFee")), cursor.getString(cursor.getColumnIndex("FirstInstallment")), cursor.getString(cursor.getColumnIndex("DiscountRateTimes"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelPos;
    }

    public List<SupplierMasterObjt> getAllSupplierMaster(String branch) {
        List<SupplierMasterObjt> labelPos = new ArrayList<SupplierMasterObjt>();

        String selectQuery = "select * FROM suppliermaster Where BranchID = " + branch + " AND IsActive = '1'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labelPos.add(new SupplierMasterObjt(cursor.getString(cursor.getColumnIndex("SupplierID")), cursor.getString(cursor.getColumnIndex("SupplierName"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelPos;
    }

    public List<MarketingSupplierObjt> getAllSupplierMarketing(String supplierId, String branch) {
        List<MarketingSupplierObjt> labelsupp = new ArrayList<MarketingSupplierObjt>();

        String selectQuery = "select * FROM supplieremp Where BranchID = " + branch + " AND IsActive = '1' AND " + SupplierID3 + " =?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{supplierId});

        if (cursor.moveToFirst()) {
            do {
                labelsupp.add(new MarketingSupplierObjt(cursor.getString(cursor.getColumnIndex("SupplierID")), cursor.getString(cursor.getColumnIndex("SupplierEmployeeID")), cursor.getString(cursor.getColumnIndex("SupplierEmployeeName"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelsupp;
    }

    public List<AssetMasterObjt> getAllAssetMaster() {
        List<AssetMasterObjt> labelAsset = new ArrayList<AssetMasterObjt>();

        String selectQuery = "select * FROM AssetMaster Where IsActive = 'True'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labelAsset.add(new AssetMasterObjt(cursor.getString(cursor.getColumnIndex("Description")), cursor.getString(cursor.getColumnIndex("AssetCode")), cursor.getString(cursor.getColumnIndex("CategoryID"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelAsset;
    }

    public List<RecomendationObjt> getAllRecomendations() {
        List<RecomendationObjt> recomendationObjtList = new ArrayList<RecomendationObjt>();

        String selectQuery = "select * From tblRecomendation";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                recomendationObjtList.add(new RecomendationObjt(cursor.getString(cursor.getColumnIndex("romendationId")), cursor.getString(cursor.getColumnIndex("recomendations"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recomendationObjtList;
    }

    public List<KelurahanObjt> getAllKelurahan() {
        List<KelurahanObjt> kelurahanObjtList = new ArrayList<KelurahanObjt>();

        String selectQuery = "select * From tblNewKelurahan";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                kelurahanObjtList.add(new KelurahanObjt(cursor.getString(cursor.getColumnIndex("City")), cursor.getString(cursor.getColumnIndex("Kecamatan")), cursor.getString(cursor.getColumnIndex("Kelurahan")), cursor.getString(cursor.getColumnIndex("ZipCode"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return kelurahanObjtList;
    }

    public List<Industri> getAllIndustry() {
        List<Industri> labelindustri = new ArrayList<Industri>();

        String selectQuery = "select IndustryTypeID, Description FROM tblNewIndustryTypeMaster Where IsActive ='True' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labelindustri.add(new Industri(cursor.getString((cursor.getColumnIndex("IndustryTypeID"))), cursor.getString((cursor.getColumnIndex("Description")))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelindustri;
    }

    public List<AssetMasterMerk> getAssetMasterMerk() {
        List<AssetMasterMerk> labelAssetMasterMerk = new ArrayList<AssetMasterMerk>();

        String selectQuery = "select AssetCode, Description FROM TblAssetMasterMerkKendaraan";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labelAssetMasterMerk.add(new AssetMasterMerk(cursor.getString((cursor.getColumnIndex("AssetCode"))), cursor.getString((cursor.getColumnIndex("Description")))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelAssetMasterMerk;
    }

    public List<AssetMasterType> getAssetMasterType() {
        List<AssetMasterType> labelAssetMasterType = new ArrayList<AssetMasterType>();

        String selectQuery = "select AssetCode, Description FROM TblAssetMasterTypeKendaraan";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labelAssetMasterType.add(new AssetMasterType(cursor.getString((cursor.getColumnIndex("AssetCode"))), cursor.getString((cursor.getColumnIndex("Description")))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelAssetMasterType;
    }

    public List<Profession> getAllProfession() {
        List<Profession> labels = new ArrayList<Profession>();

        String selectQuery = "select * From tblNewProfession Where IsActive = 'True' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new Profession(cursor.getString((cursor.getColumnIndex("ProfessionID"))), cursor.getString((cursor.getColumnIndex("Description"))), cursor.getString((cursor.getColumnIndex("IsActive")))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }

    public List<JobType> getAllJobType(String idpro) {
        List<JobType> labeljob = new ArrayList<JobType>();

        String selectQuery = "select * FROM tblNewProfJobType Where ProfessionID = ? AND IsActive='True'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{idpro});

        if (cursor.moveToFirst()) {
            do {
                labeljob.add(new JobType(cursor.getString(cursor.getColumnIndex("ProfessionID")), cursor.getString(cursor.getColumnIndex("JobTypeID")), cursor.getString(cursor.getColumnIndex("Description"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labeljob;
    }

    public List<JobPosition> getAllJObPosition(String idTypeJOb) {
        List<JobPosition> labelPosition = new ArrayList<JobPosition>();

        String selectQuery = "select * FROM tblNewProfJobPosition Where JobTypeID = ? AND IsActive = 'True'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{idTypeJOb});

        if (cursor.moveToFirst()) {
            do {
                labelPosition.add(new JobPosition(cursor.getString(cursor.getColumnIndex("JobTypeID")), cursor.getString(cursor.getColumnIndex("JobPositionID")), cursor.getString(cursor.getColumnIndex("Description"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labelPosition;

    }
}
