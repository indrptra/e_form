package itsmagic.present.simplecamera.callback;

import android.content.Context;

/**
 * Created by Alvin Rusli on 9/23/2016.
 */
public class SimpleCameraByteCallback extends SimpleCameraCallback {

    private OnCameraCaptureListener mCaptureListener;

    /** The constructor of this class */
    public SimpleCameraByteCallback(Context context, OnCameraCaptureListener listener) {
        super(context);
        mCaptureListener = listener;
    }

    @Override
    public void onPictureTakenSuccessfully(byte[] capturedImage) {
        if (mCaptureListener != null) mCaptureListener.onCameraByteCaptureSuccess(capturedImage);
    }

    /** The listener for camera capture */
    public interface OnCameraCaptureListener {

        /** Called when a picture is successfully obtained */
        void onCameraByteCaptureSuccess(byte[] capturedBytes);
    }
}
