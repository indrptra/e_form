package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apc-lap012 on 06/02/17.
 */
public class ApplicationBlacklist {
    @SerializedName("DataType")
    private String dataType;
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
    private DataAgunan dataAgunan;
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
    @SerializedName("IsCensor")
    private int IsCensor;
    @SerializedName("CustomerIdConfins")
    private String customerIdConfins;

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

    public DataAgunan getDataAgunan() {
        return dataAgunan;
    }

    public void setDataAgunan(DataAgunan dataAgunan) {
        this.dataAgunan = dataAgunan;
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

    public int getIsCensor() {
        return IsCensor;
    }

    public void setIsCensor(int isCensor) {
        IsCensor = isCensor;
    }

    public String getCustomerIdConfins() {
        return customerIdConfins;
    }

    public void setCustomerIdConfins(String customerIdConfins) {
        this.customerIdConfins = customerIdConfins;
    }
}
