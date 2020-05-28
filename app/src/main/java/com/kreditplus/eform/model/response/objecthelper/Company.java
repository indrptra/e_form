package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class Company {

    @SerializedName("ProfessionID")
    @Expose
    public String professionID;
    @SerializedName("ProfessionName")
    @Expose
    public String professionName;
    @SerializedName("PersonalCustomerType")
    @Expose
    public String personalCustomerType;
    @SerializedName("PersonalCustomerTypeName")
    @Expose
    public String personalCustomerTypeName;
    @SerializedName("JobTypeID")
    @Expose
    public String jobTypeID;
    @SerializedName("JobTypeName")
    @Expose
    public String jobTypeName;
    @SerializedName("JobPositionID")
    @Expose
    public String jobPositionID;
    @SerializedName("JobPositionName")
    @Expose
    public String jobPositionName;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("RT")
    @Expose
    public String rT;
    @SerializedName("RW")
    @Expose
    public String rW;
    @SerializedName("ProvinceCode")
    @Expose
    public String provinceCode;
    @SerializedName("CityCode")
    @Expose
    public String cityCode;
    @SerializedName("DistrictCode")
    @Expose
    public String districtCode;
    @SerializedName("VillageCode")
    @Expose
    public String villageCode;
    @SerializedName("ProvinceName")
    @Expose
    public String provinceName;
    @SerializedName("CityName")
    @Expose
    public String cityName;
    @SerializedName("DistrictName")
    @Expose
    public String districtName;
    @SerializedName("VillageName")
    @Expose
    public String villageName;
    @SerializedName("ZipCode")
    @Expose
    public String zipCode;
    @SerializedName("City")
    @Expose
    public String city;
    @SerializedName("AreaPhone")
    @Expose
    public String areaPhone;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("MonthlyFixedIncome")
    @Expose
    public String monthlyFixedIncome;
    @SerializedName("MonthlyVariableIncome")
    @Expose
    public String monthlyVariableIncome;
    @SerializedName("LivingCostAmount")
    @Expose
    public String livingCostAmount;
    @SerializedName("IndustryTypeID")
    @Expose
    public String industryTypeID;
    @SerializedName("IndustryTypeName")
    @Expose
    public String industryTypeName;
    @SerializedName("SpouseIncome")
    @Expose
    public String spouseIncome;
    @SerializedName("EmploymentSinceYear")
    @Expose
    public String employmentSinceYear;
    @SerializedName("Counterpart")
    @Expose
    public String counterpart;
    @SerializedName("DebtBusinessScale")
    @Expose
    public String debtBusinessScale;
    @SerializedName("isAffiliateWithPP")
    @Expose
    public String isAffiliateWithPP;
    @SerializedName("PhoneNumber")
    @Expose
    public String PhoneNumber;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getProfessionID() {
        return professionID;
    }

    public void setProfessionID(String professionID) {
        this.professionID = professionID;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getPersonalCustomerType() {
        return personalCustomerType;
    }

    public void setPersonalCustomerType(String personalCustomerType) {
        this.personalCustomerType = personalCustomerType;
    }

    public String getPersonalCustomerTypeName() {
        return personalCustomerTypeName;
    }

    public void setPersonalCustomerTypeName(String personalCustomerTypeName) {
        this.personalCustomerTypeName = personalCustomerTypeName;
    }

    public String getJobTypeID() {
        return jobTypeID;
    }

    public void setJobTypeID(String jobTypeID) {
        this.jobTypeID = jobTypeID;
    }

    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName;
    }

    public String getJobPositionID() {
        return jobPositionID;
    }

    public void setJobPositionID(String jobPositionID) {
        this.jobPositionID = jobPositionID;
    }

    public String getJobPositionName() {
        return jobPositionName;
    }

    public void setJobPositionName(String jobPositionName) {
        this.jobPositionName = jobPositionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getrT() {
        return rT;
    }

    public void setrT(String rT) {
        this.rT = rT;
    }

    public String getrW() {
        return rW;
    }

    public void setrW(String rW) {
        this.rW = rW;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaPhone() {
        return areaPhone;
    }

    public void setAreaPhone(String areaPhone) {
        this.areaPhone = areaPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMonthlyFixedIncome() {
        return monthlyFixedIncome;
    }

    public void setMonthlyFixedIncome(String monthlyFixedIncome) {
        this.monthlyFixedIncome = monthlyFixedIncome;
    }

    public String getMonthlyVariableIncome() {
        return monthlyVariableIncome;
    }

    public void setMonthlyVariableIncome(String monthlyVariableIncome) {
        this.monthlyVariableIncome = monthlyVariableIncome;
    }

    public String getLivingCostAmount() {
        return livingCostAmount;
    }

    public void setLivingCostAmount(String livingCostAmount) {
        this.livingCostAmount = livingCostAmount;
    }

    public String getIndustryTypeID() {
        return industryTypeID;
    }

    public void setIndustryTypeID(String industryTypeID) {
        this.industryTypeID = industryTypeID;
    }

    public String getIndustryTypeName() {
        return industryTypeName;
    }

    public void setIndustryTypeName(String industryTypeName) {
        this.industryTypeName = industryTypeName;
    }

    public String getSpouseIncome() {
        return spouseIncome;
    }

    public void setSpouseIncome(String spouseIncome) {
        this.spouseIncome = spouseIncome;
    }

    public String getEmploymentSinceYear() {
        return employmentSinceYear;
    }

    public void setEmploymentSinceYear(String employmentSinceYear) {
        this.employmentSinceYear = employmentSinceYear;
    }

    public String getCounterpart() {
        return counterpart;
    }

    public void setCounterpart(String counterpart) {
        this.counterpart = counterpart;
    }

    public String getDebtBusinessScale() {
        return debtBusinessScale;
    }

    public void setDebtBusinessScale(String debtBusinessScale) {
        this.debtBusinessScale = debtBusinessScale;
    }

    public String getIsAffiliateWithPP() {
        return isAffiliateWithPP;
    }

    public void setIsAffiliateWithPP(String isAffiliateWithPP) {
        this.isAffiliateWithPP = isAffiliateWithPP;
    }
}
