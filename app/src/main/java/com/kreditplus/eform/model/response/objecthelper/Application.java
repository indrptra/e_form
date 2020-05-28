package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class Application {

    @SerializedName("EFNumber")
    @Expose
    public String eFNumber;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("mobile_submission_key")
    @Expose
    public String mobileSubmissionKey;
    @SerializedName("branch")
    @Expose
    private BranchObjt branchObjt;
    @SerializedName("KorpFormulir")
    @Expose
    public KorpFormulir korpFormulir;
    @SerializedName("product_insurance_persen")
    @Expose
    public String productInsurancePersen;
    @SerializedName("product_insurance_nominal")
    @Expose
    public String productInsuranceNominal;
    @SerializedName("poi_of_reminder")
    @Expose
    public String poiOfReminder;
    @SerializedName("reminder_date")
    @Expose
    public String reminderDate;
    @SerializedName("people_insurance_persen")
    @Expose
    public String peopleInsurancePersen;
    @SerializedName("people_insurance_nominal")
    @Expose
    public int peopleInsuranceNominal;
    @SerializedName("total_insurance_nominal")
    @Expose
    public int totalInsuranceNominal;
    @SerializedName("total_insurance_persen")
    @Expose
    public Double totalInsurancePersen;
    @SerializedName("PersonalData")
    @Expose
    public PersonalData personalData;
    @SerializedName("Residance")
    @Expose
    public Residance residance;
    @SerializedName("Legal")
    @Expose
    public Legal legal;
    @SerializedName("EmergencyContact")
    @Expose
    public EmergencyContact emergencyContact;
    @SerializedName("Company")
    @Expose
    public Company company;
    @SerializedName("Insurance")
    @Expose
    public Insurance insurance;
    @SerializedName("DetailFinancing")
    @Expose
    public DetailFinancing detailFinancing;
    @SerializedName("DetailProduct")
    @Expose
    public DetailProduct detailProduct;
    @SerializedName("AssetMaster")
    @Expose
    public AssetMaster assetMaster;
    @SerializedName("Asset")
    @Expose
    public List<Asset> asset;
    @SerializedName("Signature")
    @Expose
    public Signature signature;
    @SerializedName("FamilyData")
    @Expose
    public List<FamilyData> familyData;
    @SerializedName("DataCreditCard")
    @Expose
    public DataCreditCard dataCreditCard;
    @SerializedName("MembershipCard")
    @Expose
    public MembershipCard membershipCard;
    @SerializedName("PO")
    @Expose
    private List<String> poList;
    @SerializedName("Attachment_MTR")
    @Expose
    public List<String> AttachmentMTR;
    @SerializedName("Attachment_ELC")
    @Expose
    public List<String> attachmentElc;
//    @SerializedName("DataAgunan")
//    @Expose
//    public DataAgunan dataAgunan;
    @SerializedName("AdminFeeLainya")
    @Expose
    public String adminFeeLainya;
    @SerializedName("DescriptionEdit")
    @Expose
    public String descriptionEdit;
    @SerializedName("DescriptionRetake")
    @Expose
    public String descriptionRetake;
    @SerializedName("OfferingType")
    @Expose
    public String offeringType;
    @SerializedName("DataType")
    @Expose
    public String dataType;
    @SerializedName("OVD")
    @Expose
    public String oVD;
    @SerializedName("DateStart")
    @Expose
    public String dateStart;
    @SerializedName("nomorPO")
    @Expose
    public String nomorPO;
    @SerializedName("dateSubmitPO")
    @Expose
    public String dateSubmitPO;
    @SerializedName("Keterangan")
    @Expose
    public String keterangan;
    @SerializedName("isDraft")
    @Expose
    public Boolean isDraft;
    @SerializedName("isEdit")
    @Expose
    public Boolean isEdit;
    @SerializedName("countEdit")
    @Expose
    public String countEdit;
    @SerializedName("PdfName")
    @Expose
    public String pdfName;
    @SerializedName("PID")
    @Expose
    public String pID;
    @SerializedName("PdfUrl")
    @Expose
    public String pdfUrl;
    @SerializedName("ApplicationIdKPM")
    @Expose
    public String applicationIdKPM;
    @SerializedName("pdfLampiranName")
    @Expose
    public String pdfLampiranName;
    @SerializedName("ReasonRecomendation")
    @Expose
    public String reasonRecomendation;
    @SerializedName("ReasonRecomendationId")
    @Expose
    public String reasonRecomendationId;
    @SerializedName("ReasonRecomendationNotes")
    @Expose
    public String reasonRecomendationNotes;
    @SerializedName("AppId")
    @Expose
    public String appId;
    @SerializedName("CustomerId")
    @Expose
    public String customerId;
    @SerializedName("CustomerIdConfins")
    @Expose
    public String customerIdConfins;
    @SerializedName("ApplicationPriority")
    @Expose
    public String applicationPriority;
    @SerializedName("pdfLampiran")
    @Expose
    public String pdfLampiran;
    @SerializedName("Location")
    @Expose
    public Location location;
    @SerializedName("AgreetoAcceptOtherOffering")
    @Expose
    public String agreetoAcceptOtherOffering;
    @SerializedName("AdvanceCustomerAgreement")
    @Expose
    public String advanceCustomerAgreement;

    public String getAdvanceCustomerAgreement() {
        return advanceCustomerAgreement;
    }

    public void setAdvanceCustomerAgreement(String advanceCustomerAgreement) {
        this.advanceCustomerAgreement = advanceCustomerAgreement;
    }

    public String getAgreetoAcceptOtherOffering() {
        return agreetoAcceptOtherOffering;
    }

    public void setAgreetoAcceptOtherOffering(String agreetoAcceptOtherOffering) {
        this.agreetoAcceptOtherOffering = agreetoAcceptOtherOffering;
    }

    public List<String> getAttachmentElc() {
        return attachmentElc;
    }

    public void setAttachmentElc(List<String> attachmentElc) {
        this.attachmentElc = attachmentElc;
    }

    public List<String> getAttachmentMTR() {
        return AttachmentMTR;
    }

    public void setAttachmentMTR(List<String> attachmentMTR) {
        AttachmentMTR = attachmentMTR;
    }

    public String geteFNumber() {
        return eFNumber;
    }

    public void seteFNumber(String eFNumber) {
        this.eFNumber = eFNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMobileSubmissionKey() {
        return mobileSubmissionKey;
    }

    public void setMobileSubmissionKey(String mobileSubmissionKey) {
        this.mobileSubmissionKey = mobileSubmissionKey;
    }

    public BranchObjt getBranchObjt() {
        return branchObjt;
    }

    public void setBranchObjt(BranchObjt branchObjt) {
        this.branchObjt = branchObjt;
    }

    public KorpFormulir getKorpFormulir() {
        return korpFormulir;
    }

    public void setKorpFormulir(KorpFormulir korpFormulir) {
        this.korpFormulir = korpFormulir;
    }

    public String getProductInsurancePersen() {
        return productInsurancePersen;
    }

    public void setProductInsurancePersen(String productInsurancePersen) {
        this.productInsurancePersen = productInsurancePersen;
    }

    public String getProductInsuranceNominal() {
        return productInsuranceNominal;
    }

    public void setProductInsuranceNominal(String productInsuranceNominal) {
        this.productInsuranceNominal = productInsuranceNominal;
    }

    public int getPeopleInsuranceNominal() {
        return peopleInsuranceNominal;
    }

    public void setPeopleInsuranceNominal(int peopleInsuranceNominal) {
        this.peopleInsuranceNominal = peopleInsuranceNominal;
    }

    public int getTotalInsuranceNominal() {
        return totalInsuranceNominal;
    }

    public void setTotalInsuranceNominal(int totalInsuranceNominal) {
        this.totalInsuranceNominal = totalInsuranceNominal;
    }

    public String getPoiOfReminder() {
        return poiOfReminder;
    }

    public void setPoiOfReminder(String poiOfReminder) {
        this.poiOfReminder = poiOfReminder;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getPeopleInsurancePersen() {
        return peopleInsurancePersen;
    }

    public void setPeopleInsurancePersen(String peopleInsurancePersen) {
        this.peopleInsurancePersen = peopleInsurancePersen;
    }


    public Double getTotalInsurancePersen() {
        return totalInsurancePersen;
    }

    public void setTotalInsurancePersen(Double totalInsurancePersen) {
        this.totalInsurancePersen = totalInsurancePersen;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Residance getResidance() {
        return residance;
    }

    public void setResidance(Residance residance) {
        this.residance = residance;
    }

    public Legal getLegal() {
        return legal;
    }

    public void setLegal(Legal legal) {
        this.legal = legal;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public DetailFinancing getDetailFinancing() {
        return detailFinancing;
    }

    public void setDetailFinancing(DetailFinancing detailFinancing) {
        this.detailFinancing = detailFinancing;
    }

    public DetailProduct getDetailProduct() {
        return detailProduct;
    }

    public void setDetailProduct(DetailProduct detailProduct) {
        this.detailProduct = detailProduct;
    }

    public AssetMaster getAssetMaster() {
        return assetMaster;
    }

    public void setAssetMaster(AssetMaster assetMaster) {
        this.assetMaster = assetMaster;
    }

    public List<Asset> getAsset() {
        return asset;
    }

    public void setAsset(List<Asset> asset) {
        this.asset = asset;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public List<FamilyData> getFamilyData() {
        return familyData;
    }

    public void setFamilyData(List<FamilyData> familyData) {
        this.familyData = familyData;
    }

    public DataCreditCard getDataCreditCard() {
        return dataCreditCard;
    }

    public void setDataCreditCard(DataCreditCard dataCreditCard) {
        this.dataCreditCard = dataCreditCard;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    public List<String> getPoList() {
        return poList;
    }

    public void setPoList(List<String> poList) {
        this.poList = poList;
    }

//    public DataAgunan getDataAgunan() {
//        return dataAgunan;
//    }
//
//    public void setDataAgunan(DataAgunan dataAgunan) {
//        this.dataAgunan = dataAgunan;
//    }

    public String getAdminFeeLainya() {
        return adminFeeLainya;
    }

    public void setAdminFeeLainya(String adminFeeLainya) {
        this.adminFeeLainya = adminFeeLainya;
    }

    public String getDescriptionEdit() {
        return descriptionEdit;
    }

    public void setDescriptionEdit(String descriptionEdit) {
        this.descriptionEdit = descriptionEdit;
    }

    public String getDescriptionRetake() {
        return descriptionRetake;
    }

    public void setDescriptionRetake(String descriptionRetake) {
        this.descriptionRetake = descriptionRetake;
    }

    public String getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(String offeringType) {
        this.offeringType = offeringType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getoVD() {
        return oVD;
    }

    public void setoVD(String oVD) {
        this.oVD = oVD;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getNomorPO() {
        return nomorPO;
    }

    public void setNomorPO(String nomorPO) {
        this.nomorPO = nomorPO;
    }

    public String getDateSubmitPO() {
        return dateSubmitPO;
    }

    public void setDateSubmitPO(String dateSubmitPO) {
        this.dateSubmitPO = dateSubmitPO;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }

    public String getCountEdit() {
        return countEdit;
    }

    public void setCountEdit(String countEdit) {
        this.countEdit = countEdit;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getApplicationIdKPM() {
        return applicationIdKPM;
    }

    public void setApplicationIdKPM(String applicationIdKPM) {
        this.applicationIdKPM = applicationIdKPM;
    }

    public String getPdfLampiranName() {
        return pdfLampiranName;
    }

    public void setPdfLampiranName(String pdfLampiranName) {
        this.pdfLampiranName = pdfLampiranName;
    }

    public String getReasonRecomendation() {
        return reasonRecomendation;
    }

    public void setReasonRecomendation(String reasonRecomendation) {
        this.reasonRecomendation = reasonRecomendation;
    }

    public String getReasonRecomendationId() {
        return reasonRecomendationId;
    }

    public void setReasonRecomendationId(String reasonRecomendationId) {
        this.reasonRecomendationId = reasonRecomendationId;
    }

    public String getReasonRecomendationNotes() {
        return reasonRecomendationNotes;
    }

    public void setReasonRecomendationNotes(String reasonRecomendationNotes) {
        this.reasonRecomendationNotes = reasonRecomendationNotes;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerIdConfins() {
        return customerIdConfins;
    }

    public void setCustomerIdConfins(String customerIdConfins) {
        this.customerIdConfins = customerIdConfins;
    }

    public String getApplicationPriority() {
        return applicationPriority;
    }

    public void setApplicationPriority(String applicationPriority) {
        this.applicationPriority = applicationPriority;
    }

    public String getPdfLampiran() {
        return pdfLampiran;
    }

    public void setPdfLampiran(String pdfLampiran) {
        this.pdfLampiran = pdfLampiran;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
