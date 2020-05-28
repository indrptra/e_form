package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 28/07/17.
 */

public class PdfPengajuanResponse {

    @SerializedName("PdfUrl")
    private String pdfUrl;
    @SerializedName("PdfName")
    private String pdfName;


    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }
}
