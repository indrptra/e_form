package com.kreditplus.eform.model.bus;

/**
 * Created by apc-lap012 on 23/05/17.
 */

public class SendEmailPo {

    private String email;

    public SendEmailPo(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
