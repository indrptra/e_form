package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

public class VersionCodeResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("link")
    private String link;
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
