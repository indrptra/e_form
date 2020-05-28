package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblDetailProduct")
public class TblDetailProduct {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "status_konsumen")
    private String StatusKonsumen;
    @DatabaseField(columnName = "kode_supplier")
    private String KodeSupplier;
    @DatabaseField(columnName = "supplier")
    private String Supplier;
    @DatabaseField(columnName = "kode_marketing_supplier")
    private String KodeMarketingSupplier;
    @DatabaseField(columnName = "marketing_supplier")
    private String MarketingSupplier;
    @DatabaseField(columnName = "kode_product_id")
    private String KodeProductId;
    @DatabaseField(columnName = "kode_product_offering_id")
    private String KodeProductOfferingId;
    @DatabaseField(columnName = "product_offering")
    private String ProductOffering;
    @DatabaseField(columnName = "pos_id")
    private String PosId;
    @DatabaseField(columnName = "pos")
    private String Pos;
    @DatabaseField(columnName = "jumlah_asset")
    private String JumlahAsset;
    @DatabaseField(columnName = "id_bank")
    private String IdBank;

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

    public String getKodeSupplier() {
        return KodeSupplier;
    }

    public void setKodeSupplier(String kodeSupplier) {
        KodeSupplier = kodeSupplier;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getKodeMarketingSupplier() {
        return KodeMarketingSupplier;
    }

    public void setKodeMarketingSupplier(String kodeMarketingSupplier) {
        KodeMarketingSupplier = kodeMarketingSupplier;
    }

    public String getMarketingSupplier() {
        return MarketingSupplier;
    }

    public void setMarketingSupplier(String marketingSupplier) {
        MarketingSupplier = marketingSupplier;
    }

    public String getKodeProductId() {
        return KodeProductId;
    }

    public void setKodeProductId(String kodeProductId) {
        KodeProductId = kodeProductId;
    }

    public String getKodeProductOfferingId() {
        return KodeProductOfferingId;
    }

    public void setKodeProductOfferingId(String kodeProductOfferingId) {
        KodeProductOfferingId = kodeProductOfferingId;
    }

    public String getProductOffering() {
        return ProductOffering;
    }

    public void setProductOffering(String productOffering) {
        ProductOffering = productOffering;
    }

    public String getPosId() {
        return PosId;
    }

    public void setPosId(String posId) {
        PosId = posId;
    }

    public String getPos() {
        return Pos;
    }

    public void setPos(String pos) {
        Pos = pos;
    }

    public String getJumlahAsset() {
        return JumlahAsset;
    }

    public void setJumlahAsset(String jumlahAsset) {
        JumlahAsset = jumlahAsset;
    }

    public String getIdBank() {
        return IdBank;
    }

    public void setIdBank(String idBank) {
        IdBank = idBank;
    }

    public String getStatusKonsumen() {
        return StatusKonsumen;
    }

    public void setStatusKonsumen(String statusKonsumen) {
        StatusKonsumen = statusKonsumen;
    }
}
