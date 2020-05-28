package com.kreditplus.eform.model.bus;

import android.net.Uri;

import java.net.URI;

/**
 * Created by apc-lap012 on 05/07/17.
 */

public class CameraUri {

    private Uri uri;
    private int requestcode;

    public CameraUri(Uri uri, int requestcode) {
        this.uri = uri;
        this.requestcode = requestcode;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getRequestcode() {
        return requestcode;
    }

    public void setRequestcode(int requestcode) {
        this.requestcode = requestcode;
    }
}
