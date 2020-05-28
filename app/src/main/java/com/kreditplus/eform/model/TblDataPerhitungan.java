package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDataPerhitungan")
public class TblDataPerhitungan {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "tenor")
    private String Tenor;
    @DatabaseField(columnName = "posisi_tenor")
    private int PosisiTenor;
    @DatabaseField(columnName = "flate_rate")
    private String FlateRate;
    @DatabaseField(columnName = "biaya_administrasi")
    private String BiayaAdministrasi;
    @DatabaseField(columnName = "biaya_lainnya")
    private String BiayaLainnya;
    @DatabaseField(columnName = "refund_subsidi")
    private String RefundSubsidi;
    @DatabaseField(columnName = "bebas_bunga")
    private String BebasBunga;
    @DatabaseField(columnName = "total_price")
    private String TotalPrice;
    @DatabaseField(columnName = "total_discount")
    private String TotalDiscount;
    @DatabaseField(columnName = "total_dp")
    private String TotalDp;
    @DatabaseField(columnName = "premi")
    private String Premi;
    @DatabaseField(columnName = "ntf")
    private String Ntf;
    @DatabaseField(columnName = "jumlah_pembiayaan")
    private String JumlahPembiayaan;
    @DatabaseField(columnName = "total_bunga_pembiayaan")
    private String TotalBungaPembiayaan;
    @DatabaseField(columnName = "bulan_bunga_pembiayaan")
    private String BulanBungaPembiayaan;
    @DatabaseField(columnName = "total_pinjaman")
    private String TotalPinjaman;
    @DatabaseField(columnName = "angsuran_prebulan_bebas_bunga")
    private String AngsuranPrebulanBebasBunga;
    @DatabaseField(columnName = "angsuran_perbulan")
    private String AngsuranPerbulan;
    @DatabaseField(columnName = "pembayaran_awal")
    private String PembayaranAwal;
    @DatabaseField(columnName = "pembayaran_delaer")
    private String PembayaranDelaer;
    @DatabaseField(columnName = "effective_rate")
    private String EffectiveRate;
    @DatabaseField(columnName = "admin_fee_lainnya")
    private String AdminFeeLainnya;
    @DatabaseField(columnName = "tipe_pembayaran")
    private String TipePembayaran;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MasterFormPengajuan getMasterFormPengajuan() {
        return masterFormPengajuan;
    }

    public void setMasterFormPengajuan(MasterFormPengajuan masterFormPengajuan) {
        this.masterFormPengajuan = masterFormPengajuan;
    }

    public String getTenor() {
        return Tenor;
    }

    public void setTenor(String tenor) {
        Tenor = tenor;
    }

    public int getPosisiTenor() {
        return PosisiTenor;
    }

    public void setPosisiTenor(int posisiTenor) {
        PosisiTenor = posisiTenor;
    }

    public String getFlateRate() {
        return FlateRate;
    }

    public void setFlateRate(String flateRate) {
        FlateRate = flateRate;
    }

    public String getBiayaAdministrasi() {
        return BiayaAdministrasi;
    }

    public void setBiayaAdministrasi(String biayaAdministrasi) {
        BiayaAdministrasi = biayaAdministrasi;
    }

    public String getBiayaLainnya() {
        return BiayaLainnya;
    }

    public void setBiayaLainnya(String biayaLainnya) {
        BiayaLainnya = biayaLainnya;
    }

    public String getRefundSubsidi() {
        return RefundSubsidi;
    }

    public void setRefundSubsidi(String refundSubsidi) {
        RefundSubsidi = refundSubsidi;
    }

    public String getBebasBunga() {
        return BebasBunga;
    }

    public void setBebasBunga(String bebasBunga) {
        BebasBunga = bebasBunga;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        TotalDiscount = totalDiscount;
    }

    public String getTotalDp() {
        return TotalDp;
    }

    public void setTotalDp(String totalDp) {
        TotalDp = totalDp;
    }

    public String getPremi() {
        return Premi;
    }

    public void setPremi(String premi) {
        Premi = premi;
    }

    public String getNtf() {
        return Ntf;
    }

    public void setNtf(String ntf) {
        Ntf = ntf;
    }

    public String getJumlahPembiayaan() {
        return JumlahPembiayaan;
    }

    public void setJumlahPembiayaan(String jumlahPembiayaan) {
        JumlahPembiayaan = jumlahPembiayaan;
    }

    public String getTotalBungaPembiayaan() {
        return TotalBungaPembiayaan;
    }

    public void setTotalBungaPembiayaan(String totalBungaPembiayaan) {
        TotalBungaPembiayaan = totalBungaPembiayaan;
    }

    public String getBulanBungaPembiayaan() {
        return BulanBungaPembiayaan;
    }

    public void setBulanBungaPembiayaan(String bulanBungaPembiayaan) {
        BulanBungaPembiayaan = bulanBungaPembiayaan;
    }

    public String getTotalPinjaman() {
        return TotalPinjaman;
    }

    public void setTotalPinjaman(String totalPinjaman) {
        TotalPinjaman = totalPinjaman;
    }

    public String getAngsuranPrebulanBebasBunga() {
        return AngsuranPrebulanBebasBunga;
    }

    public void setAngsuranPrebulanBebasBunga(String angsuranPrebulanBebasBunga) {
        AngsuranPrebulanBebasBunga = angsuranPrebulanBebasBunga;
    }

    public String getAngsuranPerbulan() {
        return AngsuranPerbulan;
    }

    public void setAngsuranPerbulan(String angsuranPerbulan) {
        AngsuranPerbulan = angsuranPerbulan;
    }

    public String getPembayaranAwal() {
        return PembayaranAwal;
    }

    public void setPembayaranAwal(String pembayaranAwal) {
        PembayaranAwal = pembayaranAwal;
    }

    public String getPembayaranDelaer() {
        return PembayaranDelaer;
    }

    public void setPembayaranDelaer(String pembayaranDelaer) {
        PembayaranDelaer = pembayaranDelaer;
    }

    public String getEffectiveRate() {
        return EffectiveRate;
    }

    public void setEffectiveRate(String effectiveRate) {
        EffectiveRate = effectiveRate;
    }

    public String getAdminFeeLainnya() {
        return AdminFeeLainnya;
    }

    public void setAdminFeeLainnya(String adminFeeLainnya) {
        AdminFeeLainnya = adminFeeLainnya;
    }

    public String getTipePembayaran() {
        return TipePembayaran;
    }

    public void setTipePembayaran(String tipePembayaran) {
        TipePembayaran = tipePembayaran;
    }
}
