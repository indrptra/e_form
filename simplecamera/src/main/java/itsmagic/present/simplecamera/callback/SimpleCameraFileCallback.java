package itsmagic.present.simplecamera.callback;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import itsmagic.present.simplecamera.SimpleCameraUtil;

/**
 * Created by Alvin Rusli on 9/23/2016.
 */
public class SimpleCameraFileCallback extends SimpleCameraCallback {

    private OnCameraCaptureListener mCaptureListener;
    private File mStorageDir;
    private String mFilename;
    private boolean isWithTimestamp = false;

    /** The constructor of this class */
    public SimpleCameraFileCallback(Context context, OnCameraCaptureListener listener) {
        super(context);
        mCaptureListener = listener;
    }

    /** Sets the storage directory to save the captured image */
    public void setStorageDir(File storageDir) {
        mStorageDir = storageDir;
    }

    /** Sets the filename for the captured image */
    public void setFilename(String filename) {
        mFilename = filename;
    }

    /** Determines if the captured image file should be saved with a timestamp */
    public void setWithTimestamp(boolean withTimestamp) {
        isWithTimestamp = withTimestamp;
    }

    @Override
    public void onPictureTakenSuccessfully(byte[] capturedImage) {
        final File storageDir;
        final String filename;
        final String appName = getContext().getResources().getString(getContext().getApplicationInfo().labelRes);
        byte[] newBytes = capturedImage;

        // If storage file is null, generate one
        if (mStorageDir == null) {
            storageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), appName);
        } else {
            storageDir = mStorageDir;
        }

        // If filename is null or empty, generate one
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if (mFilename == null || mFilename.isEmpty()) {
            // Always add the timestamp if filename is not specified
            filename = appName + "_" + timestamp + ".jpg";
        } else if (isWithTimestamp) {
            filename = mFilename + "_" + timestamp + ".jpg";
        } else {
            if (!mFilename.endsWith(".jpg")) {
                filename = mFilename + ".jpg";
            } else {
                filename = mFilename;
            }
        }

        File pictureFile = SimpleCameraUtil.createImageFile(storageDir, filename);
        if (pictureFile == null) {
            NullPointerException exception = new NullPointerException("Error creating media file, check storage permissions");
            log(Log.ERROR, getClass().getSimpleName(), exception.getMessage());

            if (mCaptureListener != null) mCaptureListener.onCameraFileCaptureFailed(exception);
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pictureFile);
            fos.write(newBytes);
            if (mCaptureListener != null) mCaptureListener.onCameraFileCaptureSuccess(Uri.fromFile(pictureFile));
        } catch (FileNotFoundException e) {
            log(Log.ERROR, getClass().getSimpleName(), "File not found: " + e.getMessage());
            if (mCaptureListener != null) mCaptureListener.onCameraFileCaptureFailed(e);
        } catch (IOException e) {
            log(Log.ERROR, getClass().getSimpleName(), "Error accessing file: " + e.getMessage());
            if (mCaptureListener != null) mCaptureListener.onCameraFileCaptureFailed(e);
        }

        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                log(Log.ERROR, getClass().getSimpleName(), "Error when closing stream: " + e.getMessage());
            }
        }
    }

    /** The listener for camera capture */
    public interface OnCameraCaptureListener {

        /** Called when a picture is successfully obtained */
        void onCameraFileCaptureSuccess(Uri capturedFile);

        /** Called when camera failed to capture a picture */
        void onCameraFileCaptureFailed(Exception e);
    }
}
