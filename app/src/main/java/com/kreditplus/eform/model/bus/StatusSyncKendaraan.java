package com.kreditplus.eform.model.bus;

public class StatusSyncKendaraan {
    private String strstatus;
    private String strbtnLeft;
    private String btnRight;

    public String getStrstatus() {
        return strstatus;
    }

    public void setStrstatus(String strstatus) {
        this.strstatus = strstatus;
    }

    public String getStrbtnLeft() {
        return strbtnLeft;
    }

    public void setStrbtnLeft(String strbtnLeft) {
        this.strbtnLeft = strbtnLeft;
    }

    public String getBtnRight() {
        return btnRight;
    }

    public void setBtnRight(String btnRight) {
        this.btnRight = btnRight;
    }

    public StatusSyncKendaraan(String strstatus, String strbtnLeft, String btnRight) {
        this.strstatus = strstatus;
        this.strbtnLeft = strbtnLeft;
        this.btnRight = btnRight;
    }
}
