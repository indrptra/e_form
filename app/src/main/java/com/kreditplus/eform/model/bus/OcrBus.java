package com.kreditplus.eform.model.bus;

/**
 * Created by pangestu on 11/17/2017.
 */

public class OcrBus {
    private String string;

    public OcrBus(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
