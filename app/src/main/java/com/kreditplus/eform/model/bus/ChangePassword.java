package com.kreditplus.eform.model.bus;

/**
 * Created by apc-lap012 on 08/09/17.
 */

public class ChangePassword {

    private String newPassword;

    public ChangePassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
