package itsmagic.present.simplecamera;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import itsmagic.present.simplecamera.callback.SimpleCameraCallback;

/**
 * Created by Alvin Rusli on 9/28/2016.
 * <p/>
 * A simplified camera wrapper for Android Camera API.
 */
public class SimpleCameraWrapper {

    /** Back-facing camera */
    private Camera mBackCamera = null;
    private CameraInfo mBackCameraInfo = null;
    private int mBackCameraId = -1;

    /** Front-facing camera */
    private Camera mFrontCamera = null;
    private CameraInfo mFrontCameraInfo = null;
    private int mFrontCameraId = -1;

    /** The initial camera facing selection */
    private int mSelectedCameraFacing = -1;

    /** The currently selected camera ID */
    private int mSelectedCameraId = -1;

    /** The camera display orientation */
    private int mDisplayOrientation = 0;

    /** Determines if camera is already open */
    private boolean isCameraOpened = false;

    /** Determines if the camera preview should run */
    private boolean isPreviewShown = false;

    /** Determines if the camera should try to enable auto-focus */
    private boolean isAutoFocusEnabled = true;

    /** Determines if camera is currently focusing */
    private boolean isFocusing = false;

    public SimpleCameraWrapper() {
        this(-1);
    }

    public SimpleCameraWrapper(int cameraFacingId) {
        if (cameraFacingId == CameraInfo.CAMERA_FACING_BACK || cameraFacingId == CameraInfo.CAMERA_FACING_FRONT) {
            mSelectedCameraFacing = cameraFacingId;
        }
        initCameraInstance();
    }

    /** A safe way to get an instance of the Camera object. */
    private void initCameraInstance() {
        int numOfCameras = Camera.getNumberOfCameras();
        if (numOfCameras > 0) {
            CameraInfo info;
            for (int i = 0; i < numOfCameras; i++) {
                info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);

                // Detect back-facing camera
                if (mBackCameraId == -1 && info.facing == CameraInfo.CAMERA_FACING_BACK) {
                    mBackCameraInfo = info;
                    mBackCameraId = i;
                }

                // Detect front-facing camera
                if (mFrontCameraId == -1 && info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                    mFrontCameraInfo = info;
                    mFrontCameraId = i;
                }

                if (mBackCameraId != -1 && mFrontCameraId != -1) break;
            }
        }

        if (mSelectedCameraFacing != -1) {
            if (mSelectedCameraFacing == CameraInfo.CAMERA_FACING_BACK) {
                mSelectedCameraId = mBackCameraId;
            } else if (mSelectedCameraFacing == CameraInfo.CAMERA_FACING_FRONT) {
                mSelectedCameraId = mFrontCameraId;
            } else {
                // Try to use the back camera as the initial selected camera
                mSelectedCameraFacing = (mBackCameraId != -1) ? CameraInfo.CAMERA_FACING_BACK : CameraInfo.CAMERA_FACING_FRONT;
                mSelectedCameraId = (mBackCameraId != -1) ? mBackCameraId : mFrontCameraId;
            }
        } else {
            // Try to use the back camera as the initial selected camera
            mSelectedCameraFacing = (mBackCameraId != -1) ? CameraInfo.CAMERA_FACING_BACK : CameraInfo.CAMERA_FACING_FRONT;
            mSelectedCameraId = (mBackCameraId != -1) ? mBackCameraId : mFrontCameraId;
        }
        openCamera();
    }

    /** Determines if camera is already open */
    public boolean isCameraOpened() {
        return isCameraOpened;
    }

    /** Try to open the selected camera */
    public final void openCamera() {
        if (isCameraOpened) return;

        if (mSelectedCameraId == mBackCameraId) {
            try {
                mBackCamera = Camera.open(mBackCameraId);
                isCameraOpened = true;
            } catch (Exception e) {
                // Back-facing camera is not available
                mBackCamera = null;

                Log.w(getClass().getSimpleName(), e.getMessage());
                e.printStackTrace();
            }
        } else if (mSelectedCameraId == mFrontCameraId) {
            try {
                mFrontCamera = Camera.open(mFrontCameraId);
                isCameraOpened = true;
            } catch (Exception e) {
                // Front-facing camera is not available
                mFrontCamera = null;

                Log.w(getClass().getSimpleName(), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /** Release the camera to allow other apps to use the camera */
    public final void release() {
        if (mBackCamera != null) {
            mBackCamera.release();
        }

        if (mFrontCamera != null) {
            mFrontCamera.release();
        }

        isCameraOpened = false;
    }

    /** Obtain the camera instance */
    public Camera getCamera() {
        if (mSelectedCameraId != -1) {
            if (mSelectedCameraId == mBackCameraId) {
                return mBackCamera;
            } else if (mSelectedCameraId == mFrontCameraId) {
                return mFrontCamera;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /** Obtain the camera info */
    public CameraInfo getCameraInfo() {
        if (mSelectedCameraId != -1) {
            if (mSelectedCameraId == mBackCameraId) {
                return mBackCameraInfo;
            } else if (mSelectedCameraId == mFrontCameraId) {
                return mFrontCameraInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /** Obtain the camera ID */
    public int getCameraId() {
        return mSelectedCameraId;
    }

    /** Determine if camera has auto-focus capabilities */
    public boolean isHasAutoFocus() {
        Parameters parameters = getCameraParameters();
        if (parameters != null) {
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            return (supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO));
        } else {
            return false;
        }
    }

    /** Obtain the camera parameters */
    public Parameters getCameraParameters() {
        Camera camera = getCamera();
        if (camera != null) {
            return camera.getParameters();
        } else {
            return null;
        }
    }

    /** Set the camera parameters */
    public void setCameraParameters(Parameters newParameters) {
        Camera camera = getCamera();
        if (camera != null) {
            camera.setParameters(newParameters);
        }
    }

    /** Set the camera orientation */
    public void setDisplayOrientation(int orientation) {
        Camera camera = getCamera();
        if (camera != null) {
            mDisplayOrientation = orientation;
            camera.setDisplayOrientation(orientation);
        }
    }

    /** Get the camera orientation */
    public int getDisplayOrientation() {
        return mDisplayOrientation;
    }

    /** Set the camera orientation */
    public void setPictureOrientation(int orientation) {
        Camera camera = getCamera();
        Parameters parameters = getCameraParameters();
        if (camera != null && parameters != null) {
            parameters.setRotation(orientation);
            setCameraParameters(parameters);
        }
    }

    /** Set the surface holder for previews */
    public void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
        Camera camera = getCamera();
        if (camera != null) {
            camera.setPreviewDisplay(surfaceHolder);
        }
    }

    /** Set the surface texture for previews */
    public void setPreviewTexture(SurfaceTexture surfaceTexture) throws IOException {
        Camera camera = getCamera();
        if (camera != null) {
            camera.setPreviewTexture(surfaceTexture);
        }
    }

    /** Enables the camera auto-focus */
    public void enableAutoFocus() {
        isAutoFocusEnabled = true;
        isFocusing = false;

        try {
            getCamera().cancelAutoFocus();
            Parameters params = getCameraParameters();
            if (isAutoFocusEnabled && isHasAutoFocus()) params.setFocusMode(Parameters.FOCUS_MODE_AUTO);
            else params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            getCamera().setParameters(params);
            getCamera().autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    try {
                        camera.cancelAutoFocus();
                    } catch (Exception ignored) {

                    }
                    isFocusing = false;
                }
            });
        } catch (Exception e) {
            isFocusing = false;
        }
    }

    /** Disables the camera auto-focus */
    public void disableAutoFocus() {
        isAutoFocusEnabled = false;
        isFocusing = false;

        try {
            getCamera().cancelAutoFocus();
            Parameters params = getCameraParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            setCameraParameters(params);
            getCamera().autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    try {
                        camera.cancelAutoFocus();
                    } catch (Exception ignored) {

                    }
                    isFocusing = false;
                }
            });
        } catch (Exception e) {
            isFocusing = false;
        }
    }

    /** Start the camera focus function */
    public void startFocus(Rect focusArea) {
        if (!isFocusing) {
            isFocusing = true;

            try {
                Parameters params = getCameraParameters();
                params.setFocusMode(Parameters.FOCUS_MODE_MACRO);
                if (params.getMaxNumFocusAreas() > 0) {
                    List<Camera.Area> mylist = new ArrayList<>();
                    mylist.add(new Camera.Area(focusArea, 1000));
                    params.setFocusAreas(mylist);
                }
                setCameraParameters(params);
                getCamera().autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        try {
                            camera.cancelAutoFocus();
                            Parameters params = getCameraParameters();
                            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                            setCameraParameters(params);
                        } catch (Exception ignored) {

                        }
                        isFocusing = false;
                    }
                });
            } catch (Exception e) {
                isFocusing = false;
            }
        }
    }

    /**
     * Determines if the device has multiple camera (front and back).
     * This method currently does <strong>NOT</strong> handle
     * devices that has 2 front-facing or 2 back-facing cameras.
     * @return true if both front and back camera is available.
     */
    public boolean isMultipleCameraAvailable() {
        return mBackCameraId != -1 && mFrontCameraId != -1;
    }

    /**
     * Determines if the app currently selects the back-facing camera.
     * @return true if back-facing camera is currently selected.
     */
    public boolean isBackFacingCameraSelected() {
        return mSelectedCameraId == mBackCameraId;
    }

    /**
     * Determines if the app currently selects the front-facing camera.
     * @return true if front-facing camera is currently selected.
     */
    public boolean isFrontFacingCameraSelected() {
        return mSelectedCameraId == mFrontCameraId;
    }

    /** Switches the between the available cameras */
    public boolean switchCamera() {
        // Only available if both cameras aren't null
        boolean retval;
        if (isMultipleCameraAvailable()) {
            if (mSelectedCameraId == mBackCameraId) {
                mSelectedCameraFacing = CameraInfo.CAMERA_FACING_FRONT;
                mSelectedCameraId = mFrontCameraId;
            } else {
                mSelectedCameraFacing = CameraInfo.CAMERA_FACING_BACK;
                mSelectedCameraId = mBackCameraId;
            }
            retval = true;
        } else {
            retval = false;
        }

        return retval;
    }

    public void setPreviewShown(boolean previewShown) {
        isPreviewShown = previewShown;
    }

    public boolean isPreviewShown() {
        return isPreviewShown;
    }

    /** Start the camera preview */
    public void startPreview() {
        Camera camera = getCamera();
        if (camera != null) {
            camera.startPreview();
        }
    }

    /** Stop the camera preview */
    public void stopPreview() {
        Camera camera = getCamera();
        if (camera != null) {
            camera.stopPreview();
        }
    }

    /** Take a picture using the camera */
    public void takePicture(SimpleCameraCallback callback) {
        Camera camera = getCamera();
        if (camera != null) {
            camera.takePicture(null, null, callback);
        }
    }
}
