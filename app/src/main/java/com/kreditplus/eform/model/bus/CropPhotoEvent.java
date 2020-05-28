package com.kreditplus.eform.model.bus;

import android.graphics.Bitmap;

public class CropPhotoEvent {
    private int tipeImg;
    private Bitmap bitmap;

    public CropPhotoEvent(int tipeImg, Bitmap bitmap) {
        this.tipeImg = tipeImg;
        this.bitmap = bitmap;
    }

    public int getTipeImg() {
        return tipeImg;
    }

    public void setTipeImg(int tipeImg) {
        this.tipeImg = tipeImg;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
