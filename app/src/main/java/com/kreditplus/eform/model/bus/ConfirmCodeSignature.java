package com.kreditplus.eform.model.bus;

/**
 * Created by Iwan Nurdesa on 05/10/16.
 */

public class ConfirmCodeSignature {

    private String code;

    public ConfirmCodeSignature(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
