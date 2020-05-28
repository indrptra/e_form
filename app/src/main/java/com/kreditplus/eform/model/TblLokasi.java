package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblLokasi")
public class TblLokasi {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pengajuan_id", foreign = true, foreignAutoRefresh = true)
    private MasterFormPengajuan masterFormPengajuan;

    @DatabaseField(columnName = "validation_action")
    private String ValidationAction;
    @DatabaseField(columnName = "validation_longitude")
    private String ValidationLongitude;
    @DatabaseField(columnName = "validation_latitude")
    private String ValidationLatitude;
    @DatabaseField(columnName = "ktp_action")
    private String KtpAction;
    @DatabaseField(columnName = "ktp_longitude")
    private String KtpLongitude;
    @DatabaseField(columnName = "ktp_latitude")
    private String KtpLatitude;
    @DatabaseField(columnName = "customer_action")
    private String CustomerAction;
    @DatabaseField(columnName = "customer_longitude")
    private String CustomerLongitude;
    @DatabaseField(columnName = "customer_latitude")
    private String CustomerLatitude;
    @DatabaseField(columnName = "paycheck_action")
    private String PaycheckAction;
    @DatabaseField(columnName = "paycheck_longitude")
    private String PaycheckLongitude;
    @DatabaseField(columnName = "paycheck_latitude")
    private String PaycheckLatitude;
    @DatabaseField(columnName = "addtional_documents_action")
    private String AddtionalDocumentsAction;
    @DatabaseField(columnName = "addtional_documents_longitude")
    private String AddtionalDocumentsLongitude;
    @DatabaseField(columnName = "addtional_documents_latitude")
    private String AddtionalDocumentsLatitude;
    @DatabaseField(columnName = "signature_action")
    private String SignatureAction;
    @DatabaseField(columnName = "signature_longitude")
    private String SignatureLongitude;
    @DatabaseField(columnName = "signature_latitude")
    private String SignatureLatitude;
    @DatabaseField(columnName = "submit_action")
    private String SubmitAction;
    @DatabaseField(columnName = "submit_longitude")
    private String SubmitLongitude;
    @DatabaseField(columnName = "submit_latitude")
    private String SubmitLatitude;
    @DatabaseField(columnName = "sync_action")
    private String SyncAction;
    @DatabaseField(columnName = "sync_longitude")
    private String SyncLongitude;
    @DatabaseField(columnName = "sync_latitude")
    private String SyncLatitude;

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

    public String getValidationAction() {
        return ValidationAction;
    }

    public void setValidationAction(String validationAction) {
        ValidationAction = validationAction;
    }

    public String getValidationLongitude() {
        return ValidationLongitude;
    }

    public void setValidationLongitude(String validationLongitude) {
        ValidationLongitude = validationLongitude;
    }

    public String getValidationLatitude() {
        return ValidationLatitude;
    }

    public void setValidationLatitude(String validationLatitude) {
        ValidationLatitude = validationLatitude;
    }

    public String getKtpAction() {
        return KtpAction;
    }

    public void setKtpAction(String ktpAction) {
        KtpAction = ktpAction;
    }

    public String getKtpLongitude() {
        return KtpLongitude;
    }

    public void setKtpLongitude(String ktpLongitude) {
        KtpLongitude = ktpLongitude;
    }

    public String getKtpLatitude() {
        return KtpLatitude;
    }

    public void setKtpLatitude(String ktpLatitude) {
        KtpLatitude = ktpLatitude;
    }

    public String getCustomerAction() {
        return CustomerAction;
    }

    public void setCustomerAction(String customerAction) {
        CustomerAction = customerAction;
    }

    public String getCustomerLongitude() {
        return CustomerLongitude;
    }

    public void setCustomerLongitude(String customerLongitude) {
        CustomerLongitude = customerLongitude;
    }

    public String getCustomerLatitude() {
        return CustomerLatitude;
    }

    public void setCustomerLatitude(String customerLatitude) {
        CustomerLatitude = customerLatitude;
    }

    public String getPaycheckAction() {
        return PaycheckAction;
    }

    public void setPaycheckAction(String paycheckAction) {
        PaycheckAction = paycheckAction;
    }

    public String getPaycheckLongitude() {
        return PaycheckLongitude;
    }

    public void setPaycheckLongitude(String paycheckLongitude) {
        PaycheckLongitude = paycheckLongitude;
    }

    public String getPaycheckLatitude() {
        return PaycheckLatitude;
    }

    public void setPaycheckLatitude(String paycheckLatitude) {
        PaycheckLatitude = paycheckLatitude;
    }

    public String getAddtionalDocumentsAction() {
        return AddtionalDocumentsAction;
    }

    public void setAddtionalDocumentsAction(String addtionalDocumentsAction) {
        AddtionalDocumentsAction = addtionalDocumentsAction;
    }

    public String getAddtionalDocumentsLongitude() {
        return AddtionalDocumentsLongitude;
    }

    public void setAddtionalDocumentsLongitude(String addtionalDocumentsLongitude) {
        AddtionalDocumentsLongitude = addtionalDocumentsLongitude;
    }

    public String getAddtionalDocumentsLatitude() {
        return AddtionalDocumentsLatitude;
    }

    public void setAddtionalDocumentsLatitude(String addtionalDocumentsLatitude) {
        AddtionalDocumentsLatitude = addtionalDocumentsLatitude;
    }

    public String getSignatureAction() {
        return SignatureAction;
    }

    public void setSignatureAction(String signatureAction) {
        SignatureAction = signatureAction;
    }

    public String getSignatureLongitude() {
        return SignatureLongitude;
    }

    public void setSignatureLongitude(String signatureLongitude) {
        SignatureLongitude = signatureLongitude;
    }

    public String getSignatureLatitude() {
        return SignatureLatitude;
    }

    public void setSignatureLatitude(String signatureLatitude) {
        SignatureLatitude = signatureLatitude;
    }

    public String getSubmitAction() {
        return SubmitAction;
    }

    public void setSubmitAction(String submitAction) {
        SubmitAction = submitAction;
    }

    public String getSubmitLongitude() {
        return SubmitLongitude;
    }

    public void setSubmitLongitude(String submitLongitude) {
        SubmitLongitude = submitLongitude;
    }

    public String getSubmitLatitude() {
        return SubmitLatitude;
    }

    public void setSubmitLatitude(String submitLatitude) {
        SubmitLatitude = submitLatitude;
    }

    public String getSyncAction() {
        return SyncAction;
    }

    public void setSyncAction(String syncAction) {
        SyncAction = syncAction;
    }

    public String getSyncLongitude() {
        return SyncLongitude;
    }

    public void setSyncLongitude(String syncLongitude) {
        SyncLongitude = syncLongitude;
    }

    public String getSyncLatitude() {
        return SyncLatitude;
    }

    public void setSyncLatitude(String syncLatitude) {
        SyncLatitude = syncLatitude;
    }
}
