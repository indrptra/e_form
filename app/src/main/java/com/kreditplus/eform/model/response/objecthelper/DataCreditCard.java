package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 13/10/16.
 */

public class DataCreditCard {

    @SerializedName("BankName")
    @Expose
    public String bankName;
    @SerializedName("IDCard")
    @Expose
    public String iDCard;
    @SerializedName("CardType")
    @Expose
    public String cardType;
    @SerializedName("CardLimit")
    @Expose
    public String cardLimit;
    @SerializedName("MembershipOldMonth")
    @Expose
    public String membershipOldMonth;
    @SerializedName("MembershipOldYear")
    @Expose
    public String membershipOldYear;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getiDCard() {
        return iDCard;
    }

    public void setiDCard(String iDCard) {
        this.iDCard = iDCard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getMembershipOldMonth() {
        return membershipOldMonth;
    }

    public void setMembershipOldMonth(String membershipOldMonth) {
        this.membershipOldMonth = membershipOldMonth;
    }

    public String getMembershipOldYear() {
        return membershipOldYear;
    }

    public void setMembershipOldYear(String membershipOldYear) {
        this.membershipOldYear = membershipOldYear;
    }
}
