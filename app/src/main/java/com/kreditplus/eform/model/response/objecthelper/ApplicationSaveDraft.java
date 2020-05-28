package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apc-lap012 on 09/02/17.
 */

public class ApplicationSaveDraft {

    @SerializedName("CustomerIdConfins")
    private String customerId;
    @SerializedName("branch")
    private BranchObjt branchCode;
    @SerializedName("DataType")
    private String dataType;
    @SerializedName("EFNumber")
    private String EFNumber;
    @SerializedName("mobile_submission_key")
    private String mobileSubmissionKey;
    @SerializedName("product_insurance_persen")
    private Double productInsurancePersen;
    @SerializedName("product_insurance_nominal")
    private Integer productInsuranceNominal;
    @SerializedName("people_insurance_persen")
    private Double peopleInsurancePersen;
    @SerializedName("people_insurance_nominal")
    private Integer peopleInsuranceNominal;
    @SerializedName("total_insurance_persen")
    private Double totalInsurancePersen;
    @SerializedName("total_insurance_nominal")
    private Integer totalInsuranceNominal;
    @SerializedName("PersonalData")
    private PersonalData personalData;
    @SerializedName("FamilyData")
    private List<FamilyData> familyDatas;
    @SerializedName("Residance")
    private Residance residance;
    @SerializedName("Legal")
    private Legal legal;
    @SerializedName("EmergencyContact")
    private EmergencyContact emergencyContact;
    @SerializedName("Company")
    private Company company;
    @SerializedName("DataCreditCard")
    private DataCreditCard dataCreditCard;
    @SerializedName("MembershipCard")
    private MembershipCard membershipCard;
    @SerializedName("DataAgunan")
    private List<DataAgunan> dataAgunanList;
    @SerializedName("DetailProduct")
    private DetailProduct detailProduct;
    @SerializedName("Insurance")
    private Insurance insurance;
    @SerializedName("DetailFinancing")
    private DetailFinancing detailFinancing;
    @SerializedName("AssetMaster")
    private AssetMaster assetMaster;
    @SerializedName("Asset")
    private List<Asset> assets;
    @SerializedName("Signature")
    private Signature signature;
    @SerializedName("Attachment")
    private List<String> attachmentList;
    @SerializedName("Location")
    private Location location;
    @SerializedName("PO")
    private List<String> poList;
    @SerializedName("PdfUrl")
    private String pdfUrl;
    @SerializedName("PdfName")
    private String pdfName;
    @SerializedName("OfferingType")
    private String offeringType;
    @SerializedName("AdminFeeLainya")
    private Integer adminfee2;
    @SerializedName("Keterangan")
    private String keterangan;
    @SerializedName("ApplicationIdKPM")
    private String applicationIdKpm;
    @SerializedName("created_at")
    private String dateCreated;
    @SerializedName("SalesMethod")
    private String saledMethod;
    @SerializedName("PID")
    private String pid;
    @SerializedName("DateStart")
    private String blacklistdate;
    @SerializedName("FinancingPurpose")
    private String financingPurpose;
    @SerializedName("isEdit")
    private String isEdit;
    @SerializedName("ReasonRecomendationId")
    private String ReasonRecomendationId;
    @SerializedName("ReasonRecomendationNotes")
    private String ReasonRecomendationNotes;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getReasonRecomendationId() {
        return ReasonRecomendationId;
    }

    public void setReasonRecomendationId(String reasonRecomendationId) {
        ReasonRecomendationId = reasonRecomendationId;
    }

    public String getReasonRecomendationNotes() {
        return ReasonRecomendationNotes;
    }

    public void setReasonRecomendationNotes(String reasonRecomendationNotes) {
        ReasonRecomendationNotes = reasonRecomendationNotes;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMobileSubmissionKey() {
        return mobileSubmissionKey;
    }

    public void setMobileSubmissionKey(String mobileSubmissionKey) {
        this.mobileSubmissionKey = mobileSubmissionKey;
    }

    public Double getProductInsurancePersen() {
        return productInsurancePersen;
    }

    public void setProductInsurancePersen(Double productInsurancePersen) {
        this.productInsurancePersen = productInsurancePersen;
    }

    public Integer getProductInsuranceNominal() {
        return productInsuranceNominal;
    }

    public void setProductInsuranceNominal(Integer productInsuranceNominal) {
        this.productInsuranceNominal = productInsuranceNominal;
    }

    public Double getPeopleInsurancePersen() {
        return peopleInsurancePersen;
    }

    public void setPeopleInsurancePersen(Double peopleInsurancePersen) {
        this.peopleInsurancePersen = peopleInsurancePersen;
    }

    public Integer getPeopleInsuranceNominal() {
        return peopleInsuranceNominal;
    }

    public void setPeopleInsuranceNominal(Integer peopleInsuranceNominal) {
        this.peopleInsuranceNominal = peopleInsuranceNominal;
    }

    public Double getTotalInsurancePersen() {
        return totalInsurancePersen;
    }

    public void setTotalInsurancePersen(Double totalInsurancePersen) {
        this.totalInsurancePersen = totalInsurancePersen;
    }

    public Integer getTotalInsuranceNominal() {
        return totalInsuranceNominal;
    }

    public void setTotalInsuranceNominal(Integer totalInsuranceNominal) {
        this.totalInsuranceNominal = totalInsuranceNominal;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public List<FamilyData> getFamilyDatas() {
        return familyDatas;
    }

    public void setFamilyDatas(List<FamilyData> familyDatas) {
        this.familyDatas = familyDatas;
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

    public List<DataAgunan> getDataAgunanList() {
        return dataAgunanList;
    }

    public void setDataAgunanList(List<DataAgunan> dataAgunanList) {
        this.dataAgunanList = dataAgunanList;
    }

    public DetailProduct getDetailProduct() {
        return detailProduct;
    }

    public void setDetailProduct(DetailProduct detailProduct) {
        this.detailProduct = detailProduct;
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

    public AssetMaster getAssetMaster() {
        return assetMaster;
    }

    public void setAssetMaster(AssetMaster assetMaster) {
        this.assetMaster = assetMaster;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public List<String> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<String> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public List<String> getPoList() {
        return poList;
    }

    public void setPoList(List<String> poList) {
        this.poList = poList;
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

    public String getApplicationIdKpm() {
        return applicationIdKpm;
    }

    public void setApplicationIdKpm(String applicationIdKpm) {
        this.applicationIdKpm = applicationIdKpm;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(String offeringType) {
        this.offeringType = offeringType;
    }

    public Integer getAdminfee2() {
        return adminfee2;
    }

    public void setAdminfee2(Integer adminfee2) {
        this.adminfee2 = adminfee2;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public BranchObjt getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(BranchObjt branchCode) {
        this.branchCode = branchCode;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSaledMethod() {
        return saledMethod;
    }

    public void setSaledMethod(String saledMethod) {
        this.saledMethod = saledMethod;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBlacklistdate() {
        return blacklistdate;
    }

    public void setBlacklistdate(String blacklistdate) {
        this.blacklistdate = blacklistdate;
    }

    public String getFinancingPurpose() {
        return financingPurpose;
    }

    public void setFinancingPurpose(String financingPurpose) {
        this.financingPurpose = financingPurpose;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEFNumber() {
        return EFNumber;
    }

    public void setEFNumber(String eFNumber) {
        this.EFNumber = EFNumber;
    }
}
