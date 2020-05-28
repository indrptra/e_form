package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 20/06/16.
 */
public class BaseResponse<T> {

    @SerializedName("meta")
    private Meta meta;
    @SerializedName("data")
    private T data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
