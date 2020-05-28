package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Iwan Nurdesa on 03/07/16.
 */
@DatabaseTable(tableName = "detail_asset_kendaraan")
public class AssetKendaraan {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "kode_kendaraan")
    private String kodeKendaraan;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "pengajuan_id")
    private PengajuanBaru pengajuanBaru;
    @DatabaseField(columnName = "merk_kendaraan")
    private String merkKendaraan;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "tahun")
    private String tahun;
    @DatabaseField(columnName = "warna")
    private String warna;
    @DatabaseField(columnName = "kondisi")
    private String kondisi;
    @DatabaseField(columnName = "isi_silinder")
    private String isiSilinder;
    @DatabaseField(columnName = "no_polisi")
    private String noPolisi;
    @DatabaseField(columnName = "no_rangka")
    private String noRangka;
    @DatabaseField(columnName = "no_mesin")
    private String noMesin;
    @DatabaseField(columnName = "bpkb")
    private String bpkb;

    public String getKodeKendaraan() {
        return kodeKendaraan;
    }

    public void setKodeKendaraan(String kodeKendaraan) {
        this.kodeKendaraan = kodeKendaraan;
    }

    public PengajuanBaru getPengajuanBaru() {
        return pengajuanBaru;
    }

    public void setPengajuanBaru(PengajuanBaru pengajuanBaru) {
        this.pengajuanBaru = pengajuanBaru;
    }

    public String getMerkKendaraan() {
        return merkKendaraan;
    }

    public void setMerkKendaraan(String merkKendaraan) {
        this.merkKendaraan = merkKendaraan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getIsiSilinder() {
        return isiSilinder;
    }

    public void setIsiSilinder(String isiSilinder) {
        this.isiSilinder = isiSilinder;
    }

    public String getNoPolisi() {
        return noPolisi;
    }

    public void setNoPolisi(String noPolisi) {
        this.noPolisi = noPolisi;
    }

    public String getNoRangka() {
        return noRangka;
    }

    public void setNoRangka(String noRangka) {
        this.noRangka = noRangka;
    }

    public String getNoMesin() {
        return noMesin;
    }

    public void setNoMesin(String noMesin) {
        this.noMesin = noMesin;
    }

    public String getBpkb() {
        return bpkb;
    }

    public void setBpkb(String bpkb) {
        this.bpkb = bpkb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
