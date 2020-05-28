package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class Pengajuan {

    @SerializedName("id")
    private String id;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("Address")
    private String address;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("FinancingPurpose")
    private String financingPurpose;
    @SerializedName("SubmitedDate")
    private String submitedDate;
    @SerializedName("TotalPinjaman")
    private String totalPinjaman;
    @SerializedName("Status")
    private String status;
    @SerializedName("PdfUrl")
    private String pdfUrl;
    @SerializedName("PdfName")
    private String pdfName;
    @SerializedName("pdfLampiran")
    private String pdfLmapiranUrl;
    @SerializedName("pdfLampiranName")
    private String pdfLampiranName;
    @SerializedName("isDraft")
    private String isDraft;
    @SerializedName("isEdit")
    private String isEdit;
    @SerializedName("Completed")
    private boolean completed;
    @SerializedName("ApplicationIdKPM")
    private String applicationIdKpm;
    @SerializedName("ReminderDate")
    private String reminderDate;
    @SerializedName("PlaceOfReminder")
    private String placeOfReminder;
    @SerializedName("OfferingType")
    private String offeringType;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(String offeringType) {
        this.offeringType = offeringType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFinancingPurpose() {
        return financingPurpose;
    }

    public void setFinancingPurpose(String financingPurpose) {
        this.financingPurpose = financingPurpose;
    }

    public String getSubmitedDate() {
        return submitedDate;
    }

    public void setSubmitedDate(String submitedDate) {
        this.submitedDate = submitedDate;
    }

    public String getTotalPinjaman() {
        return totalPinjaman;
    }

    public void setTotalPinjaman(String totalPinjaman) {
        this.totalPinjaman = totalPinjaman;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfLmapiranUrl() {
        return pdfLmapiranUrl;
    }

    public void setPdfLmapiranUrl(String pdfLmapiranUrl) {
        this.pdfLmapiranUrl = pdfLmapiranUrl;
    }

    public String getPdfLampiranName() {
        return pdfLampiranName;
    }

    public void setPdfLampiranName(String pdfLampiranName) {
        this.pdfLampiranName = pdfLampiranName;
    }

    public String getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(String isDraft) {
        this.isDraft = isDraft;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getApplicationIdKpm() {
        return applicationIdKpm;
    }

    public void setApplicationIdKpm(String applicationIdKpm) {
        this.applicationIdKpm = applicationIdKpm;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getPlaceOfReminder() {
        return placeOfReminder;
    }

    public void setPlaceOfReminder(String placeOfReminder) {
        this.placeOfReminder = placeOfReminder;
    }
}
