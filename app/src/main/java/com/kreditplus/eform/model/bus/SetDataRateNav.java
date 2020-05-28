package com.kreditplus.eform.model.bus;

/**
 * Created by apc-lap012 on 02/08/17.
 */

public class SetDataRateNav {

    private double rate;
    private double penggunaan;

    public SetDataRateNav(double rate, double penggunaan) {
        this.rate = rate;
        this.penggunaan = penggunaan;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getPenggunaan() {
        return penggunaan;
    }

    public void setPenggunaan(int penggunaan) {
        this.penggunaan = penggunaan;
    }
}
