package com.kreditplus.eform.model.bus;

import android.graphics.Bitmap;

/**
 * Created by Iwan Nurdesa on 09/06/16.
 */
public class SignatureEvent {
    private int tipeTtd;
    private Bitmap bitmap;

    public SignatureEvent(int tipeTtd, Bitmap bitmap) {
        this.tipeTtd = tipeTtd;
        this.bitmap = bitmap;
    }

    public int getTipeTtd() {
        return tipeTtd;
    }

    public void setTipeTtd(int tipeTtd) {
        this.tipeTtd = tipeTtd;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
