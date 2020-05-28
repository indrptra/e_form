package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

public class CekKodeProgramObjct {

    @SerializedName("isSpecialProdOff")
    private boolean isSpecialProdOff;
    @SerializedName("program_code")
    private String programCode;

    public boolean isSpecialProdOff() {
        return isSpecialProdOff;
    }

    public void setSpecialProdOff(boolean specialProdOff) {
        isSpecialProdOff = specialProdOff;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
}