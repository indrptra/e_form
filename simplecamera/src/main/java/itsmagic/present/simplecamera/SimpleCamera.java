package itsmagic.present.simplecamera;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvin Rusli on 9/28/2016.
 * <p/>
 * A custom {@link SurfaceView} class for camera previews.
 */
public class SimpleCamera extends SurfaceView implements SurfaceHolder.Callback {

    private SimpleCameraWrapper mSimpleCameraWrapper;

    private SurfaceHolder mHolder;
    private List<Size> mSupportedPreviewSizes;
    private List<Size> mSupportedPictureSizes;

    private List<Double> mRatioList = null;
    private List<Size> mOptimalPreviewSizes = null;

    private Size mSelectedPreviewSize = null;
    private Size mSelectedPictureSize = null;
    private double mSelectedRatio = 0;
    private boolean isOptimalSizeCalculated = false;
    private boolean isPreviewSizeDescending = false;
    private boolean isPictureSizeDescending = false;

    private OnSurfaceChangedListener mSurfaceListener = null;
    private OnMeasureListener mMeasureListener = null;

    /** Determines if the preview should be drawn in full size (cropped) */
    private boolean isCameraCropped = false;

    /** Determines if the captured image should be be the original camera image */
    private boolean isCaptureOriginalImage = true;

    private final float FOCUS_AREA_SIZE = 75f;

    /** Determines if this class should print log messages */
    private boolean isLogPrinted = false;

    /** Types of camera face */
    public enum CameraFace {
        FRONT, BACK
    }

    /** The constructor for this class */
    public SimpleCamera(Context context) {
        this(context, CameraFace.BACK);
    }

    /** The constructor for this class */
    public SimpleCamera(Context context, CameraFace cameraFace) {
        super(context);
        if (cameraFace == CameraFace.FRONT) {
            mSimpleCameraWrapper = new SimpleCameraWrapper(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            mSimpleCameraWrapper = new SimpleCameraWrapper(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        mRatioList = new ArrayList<>();
        mOptimalPreviewSizes = new ArrayList<>();

        // Initialize camera focus type
        post(new Runnable() {
            @Override
            public void run() {
                if (mSimpleCameraWrapper.isHasAutoFocus()) {
                    mSimpleCameraWrapper.enableAutoFocus();
                } else {
                    mSimpleCameraWrapper.disableAutoFocus();
                }
            }
        });

        initSimpleCameraSurfaceView();
    }

    /** Release the camera instance */
    protected final void release() {
        mSimpleCameraWrapper.release();
    }

    /** Recalculate the supported sizes for preview and picture sizes */
    protected final void recalculateSizes() {
        isOptimalSizeCalculated = false;
        mRatioList = new ArrayList<>();
        mOptimalPreviewSizes = new ArrayList<>();
        initSimpleCameraSurfaceView();
    }

    /** Initializes this {@link SurfaceView} class */
    private void initSimpleCameraSurfaceView() {
        if (mSimpleCameraWrapper.getCamera() == null) return;

        // Check all supported preview and picture sizes
        mSupportedPreviewSizes = mSimpleCameraWrapper.getCameraParameters().getSupportedPreviewSizes();
        mSupportedPictureSizes = mSimpleCameraWrapper.getCameraParameters().getSupportedPictureSizes();

        // By default, auto select the picture size as the largest available
        mSelectedPictureSize = getLargestPictureSize();
        mSelectedRatio = (double) mSelectedPictureSize.height / mSelectedPictureSize.width;
        // Calculation for the optimal preview size for the selected picture size should be done in #onMeasure()

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        if (mHolder == null) {
            mHolder = getHolder();
            mHolder.addCallback(this);

            // Deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    protected OnSurfaceChangedListener getOnSurfaceChangedListener() {
        return mSurfaceListener;
    }

    protected void setOnSurfaceChangedListener(OnSurfaceChangedListener surfaceListener) {
        mSurfaceListener = surfaceListener;
    }

    protected OnMeasureListener getOnMeasureListener() {
        return mMeasureListener;
    }

    protected void setOnMeasureListener(OnMeasureListener measureListener) {
        mMeasureListener = measureListener;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // Do nothing, surfaceChanged will take care of stuff

        // Call the listener
        if (mSurfaceListener != null) mSurfaceListener.surfaceCreated(surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mSimpleCameraWrapper != null) mSimpleCameraWrapper.release();

        // Call the listener
        if (mSurfaceListener != null) mSurfaceListener.surfaceDestroyed(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
        log(Log.DEBUG, getClass().getSimpleName() + "#surfaceChanged()", "format=" + format + ", w = " + w + ", h = " + h);

        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.getSurface() == null) {
            // Preview surface does not exist
            return;
        }

        // Stop the preview before making any changes
        try {
            mSimpleCameraWrapper.stopPreview();
        } catch (Exception e) {
            // Ignore this, tried to stop a non-existent preview
        }

        // Set preview size and make any resize, rotate or reformatting changes here

        // Start the preview with new settings
        boolean isExceptionCaught = false;
        try {
            Camera.Parameters parameters = mSimpleCameraWrapper.getCameraParameters();
            parameters.setPreviewSize(mSelectedPreviewSize.width, mSelectedPreviewSize.height);

            // Stop the preview before updating the camera parameters
            mSimpleCameraWrapper.stopPreview();
            mSimpleCameraWrapper.setCameraParameters(parameters);

            // Additional settings
            setCameraDisplayOrientation();
            mSimpleCameraWrapper.setPreviewDisplay(mHolder);

            // Start the camera preview
            if (mSimpleCameraWrapper.isPreviewShown()) mSimpleCameraWrapper.startPreview();
        } catch (Exception e) {
            isExceptionCaught = true;
            log(Log.ERROR, getClass().getSimpleName() + "#surfaceChanged()", "Error starting camera preview: " + e.getMessage());
        }

        if (isExceptionCaught) {
            // Try to re-open camera
            mSimpleCameraWrapper.openCamera();

            // Start the preview with new settings
            try {
                Camera.Parameters parameters = mSimpleCameraWrapper.getCameraParameters();
                parameters.setPreviewSize(mSelectedPreviewSize.width, mSelectedPreviewSize.height);

                // Stop the preview before updating the camera parameters
                mSimpleCameraWrapper.stopPreview();
                mSimpleCameraWrapper.setCameraParameters(parameters);

                // Additional settings
                setCameraDisplayOrientation();
                mSimpleCameraWrapper.setPreviewDisplay(mHolder);

                // Start the camera preview
                if (mSimpleCameraWrapper.isPreviewShown()) mSimpleCameraWrapper.startPreview();
            } catch (Exception e) {
                log(Log.ERROR, getClass().getSimpleName() + "#surfaceChanged()", "Error starting camera preview: " + e.getMessage());
            }
        }

        // Call the listener
        if (mSurfaceListener != null) mSurfaceListener.surfaceChanged(surfaceHolder, format, w, h);
    }

    /** Enables tap to focus for the camera */
    protected void enableTapToFocus() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mSimpleCameraWrapper.getCamera() != null) {
                    Rect focusRect = calculateTapArea(event.getX(), event.getY(), 1f);
                    mSimpleCameraWrapper.startFocus(focusRect);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /** Set the correct orientation for the camera */
    private void setCameraDisplayOrientation() {
        int cameraOrientation = mSimpleCameraWrapper.getCameraInfo().orientation;
        int rotation = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation();
        int degrees;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                degrees = 0;
        }

        int displayOrientation, pictureOrientation;
        if (isFrontFacingCameraSelected()) {
            // Preview orientation
            displayOrientation = (cameraOrientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360; // Compensate the mirror

            // Picture orientation
            pictureOrientation = (360 - displayOrientation) % 360; // Compensate the mirror
        } else {
            // Preview orientation
            displayOrientation = (360 + cameraOrientation - degrees) % 360;

            // Picture orientation
            pictureOrientation = displayOrientation;
        }

        mSimpleCameraWrapper.setDisplayOrientation(displayOrientation);
        mSimpleCameraWrapper.setPictureOrientation(pictureOrientation);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        
//        SimpleCameraUtil.log(Log.DEBUG, getClass().getSimpleName() + "#onMeasure()",
//                "Suggested width and height = " + width+ ":" + height);
//        mTargetWidth = width;
//        mTargetHeight = height;

        if (mSupportedPreviewSizes != null) {
            mSelectedPreviewSize = getOptimalPreviewSize();
        }

        // Don't calculate anything if preview size is still null
        if (mSelectedPreviewSize == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        float ratio;
        if (mSelectedPreviewSize.height >= mSelectedPreviewSize.width) {
            ratio = (float) mSelectedPreviewSize.height / (float) mSelectedPreviewSize.width;
        } else {
            ratio = (float) mSelectedPreviewSize.width / (float) mSelectedPreviewSize.height;
        }

        // Set the measured dimension depending on the screen orientation
        int targetWidth, targetHeight;
        double parentWidth, parentHeight;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            parentWidth = ((View) getParent()).getWidth();
            parentHeight = ((View) getParent()).getHeight();
            final int newWidth = (int) (height * ratio);

            if (newWidth > parentWidth) {
                targetWidth = (int) parentWidth;
                targetHeight = (int) (height * (parentWidth / newWidth));
            } else if (height > parentHeight) {
                targetWidth = (int) (newWidth * (parentHeight / height));
                targetHeight = (int) parentHeight;
            } else {
                targetWidth = newWidth;
                targetHeight = height;
            }
        } else {
            parentWidth = ((View) getParent()).getWidth();
            parentHeight = ((View) getParent()).getHeight();
            final int newHeight = (int) (width * ratio);

            if (width > parentWidth) {
                targetWidth = (int) parentWidth;
                targetHeight = (int) (height * (parentWidth / width));
            } else if (newHeight > parentHeight) {
                targetWidth = (int) (width * (parentHeight / newHeight));
                targetHeight = (int) parentHeight;
            } else {
                targetWidth = width;
                targetHeight = newHeight;
            }
        }

        // Make the preview as large as possible (like an ImageView's centerCrop)
        if (isCameraCropped) {
            float sizeDifference;
            if (targetWidth < parentWidth) {
                sizeDifference = (float) parentWidth / (float) targetWidth;
                targetHeight = (int) (targetHeight * sizeDifference);
                targetWidth = (int) parentWidth;
            } else if (targetHeight < parentHeight) {
                sizeDifference = (float) parentHeight / (float) targetHeight;
                targetWidth = (int) (targetWidth * sizeDifference);
                targetHeight = (int) parentHeight;
            }
        }

        setMeasuredDimension(targetWidth, targetHeight);
        if (mMeasureListener != null) mMeasureListener.onPreviewMeasured(targetWidth, targetHeight);
    }

    /** Obtian the most optimal preview size */
    protected Size getOptimalPreviewSize() {
        if (!isOptimalSizeCalculated) {
            calculateOptimalPreviewSizes();
            isOptimalSizeCalculated = true;
        }

        log(Log.DEBUG, getClass().getSimpleName(), "Calculating the optimal size for " + mSelectedRatio + " ratio");
        for (int i = 0; i < mRatioList.size(); i++) {
//            log(Log.DEBUG, getClass().getSimpleName() + "#getOptimalPreviewSize()",
//                    "Selected:List = " + mSelectedRatio + ":" + mRatioList.get(i));
            if (Double.doubleToLongBits(mRatioList.get(i)) == Double.doubleToLongBits(mSelectedRatio)) {
                log(Log.DEBUG, getClass().getSimpleName(),
                        "Using the optimal size of " + mOptimalPreviewSizes.get(i).width + ":" + mOptimalPreviewSizes.get(i).height);
                return mOptimalPreviewSizes.get(i);
            }
        }

        Size largestPreviewSize = getLargestPreviewSize();
        log(Log.DEBUG, getClass().getSimpleName() + "#getOptimalPreviewSize()",
                "Ratio not found, using " + largestPreviewSize.width + ":" + largestPreviewSize.height);
        return largestPreviewSize;


//        if (sizes == null) return null;
//
//        final double ASPECT_TOLERANCE = 0.1;
//
//        Size optimalSize = null;
//        double targetRatio = (double) height / width;
//        double minDiff = Double.MAX_VALUE;
//
//        double ratio;
//        for (Size size : sizes) {
//            ratio = (double) size.height / size.width;
//            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
//                continue;
//
//            if (Math.abs(size.height - height) < minDiff) {
//                optimalSize = size;
//                minDiff = Math.abs(size.height - height);
//
//                // Add the available ratio
//                if (!mRatioList.contains(ratio)) {
//                    mRatioList.add(ratio);
//                    mOptimalPreviewSizes.add(optimalSize);
//                } else {
//                    int ratioIdx = mRatioList.indexOf(ratio);
//                    mOptimalPreviewSizes.set(ratioIdx, optimalSize);
//                }
//            }
//        }
//
//        if (optimalSize == null) {
//            minDiff = Double.MAX_VALUE;
//            for (Size size : sizes) {
//                ratio = (double) size.height / size.width;
//                if (Math.abs(size.height - height) < minDiff) {
//                    optimalSize = size;
//                    minDiff = Math.abs(size.height - height);
//
//                    // Add the available ratio
//                    if (!mRatioList.contains(ratio)) {
//                        mRatioList.add(ratio);
//                        mOptimalPreviewSizes.add(optimalSize);
//                    } else {
//                        int ratioIdx = mRatioList.indexOf(ratio);
//                        mOptimalPreviewSizes.set(ratioIdx, optimalSize);
//                    }
//                }
//            }
//        }
//
//        // FIXME: 9/30/2016 Test logging
//        SimpleCameraUtil.log(Log.DEBUG, getClass().getSimpleName(), "Ratio and optimal preview size list");
//        for (int i = 0; i < mRatioList.size(); i++) {
//            SimpleCameraUtil.log(Log.DEBUG, getClass().getSimpleName(), mRatioList.get(i) + " - " + mOptimalPreviewSizes.get(i).width + ":" + mOptimalPreviewSizes.get(i).height);
//        }
//
//        assert optimalSize != null;
//        SimpleCameraUtil.log(Log.DEBUG, getClass().getSimpleName() + "#getOptimalPreviewSize()",
//                "Using the most optimal preview size of " + optimalSize.width + ":" + optimalSize.height);
//
//        isOptimalSizeCalculated = true;
//        return optimalSize;
    }

    /** Obtain the most optimal preview size depending on the screen dimensions */
    protected void calculateOptimalPreviewSizes() {
        if (mSupportedPreviewSizes == null) return;

        double ratio;
        Size selectedSize;

        // Find the ratios from the available sizes of the camera
        for (int i = 0; i < mSupportedPictureSizes.size(); i++) {
            selectedSize = mSupportedPictureSizes.get(i);
            ratio = (double) selectedSize.height / selectedSize.width;

            // Add to the available ratio list
            if (!mRatioList.contains(ratio)) mRatioList.add(ratio);
        }

        // Set the initial list of optimal preview sizes
        mOptimalPreviewSizes = new ArrayList<>();

        // By default, set the largest preview size as the 'best' preview for each ratio
        {
            Size largestPreviewSize = getLargestPreviewSize();
            for (int i = 0; i < mRatioList.size(); i++) {
                mOptimalPreviewSizes.add(largestPreviewSize);
            }
        }

        // Find the largest preview image from the available ratios
        if (isPreviewSizeDescending) {
            for (int i = (mSupportedPreviewSizes.size() - 1); i >= 0 ; i--) {
                selectedSize = mSupportedPreviewSizes.get(i);
                ratio = (double) selectedSize.height / selectedSize.width;
                setOptimalPreviewSize(selectedSize, ratio);
            }
        } else {
            for (int i = 0; i < mSupportedPreviewSizes.size(); i++) {
                selectedSize = mSupportedPreviewSizes.get(i);
                ratio = (double) selectedSize.height / selectedSize.width;
                setOptimalPreviewSize(selectedSize, ratio);
            }
        }

        // Additional logging
        if (isLogPrinted) {
            log(Log.DEBUG, getClass().getSimpleName(), "Supported picture size list");
            for (int i = 0; i < mSupportedPictureSizes.size(); i++) {
                log(Log.DEBUG, getClass().getSimpleName(),
                        "[" + i + "] = " + mSupportedPictureSizes.get(i).width + ":" + mSupportedPictureSizes.get(i).height);
            }

            log(Log.DEBUG, getClass().getSimpleName(), "Supported preview size list");
            for (int i = 0; i < mSupportedPreviewSizes.size(); i++) {
                log(Log.DEBUG, getClass().getSimpleName(),
                        "[" + i + "] = " + mSupportedPreviewSizes.get(i).width + ":" + mSupportedPreviewSizes.get(i).height);
            }

            log(Log.DEBUG, getClass().getSimpleName(), "Picture ratio list");
            for (int i = 0; i < mRatioList.size(); i++) {
                log(Log.DEBUG, getClass().getSimpleName(),
                        "[" + i + "] = " + mRatioList.get(i));
            }

            log(Log.DEBUG, getClass().getSimpleName(), "Optimal preview size list");
            for (int i = 0; i < mOptimalPreviewSizes.size(); i++) {
                log(Log.DEBUG, getClass().getSimpleName(),
                        "[" + i + "] = " + mOptimalPreviewSizes.get(i).width + ":" + mOptimalPreviewSizes.get(i).height);
            }

            log(Log.DEBUG, getClass().getSimpleName(), "Picture ratio and optimal preview size list");
            for (int i = 0; i < mRatioList.size(); i++) {
                log(Log.DEBUG, getClass().getSimpleName(),
                        "[" + i + "] = " + mRatioList.get(i) + " - " + mOptimalPreviewSizes.get(i).width + ":" + mOptimalPreviewSizes.get(i).height);
            }
        }
    }

    /** Sets the optimal preview size */
    protected void setOptimalPreviewSize(Size previewSize, double ratio) {
        // Set the optimal preview size
        if (mRatioList.contains(ratio)) {
            int index = mRatioList.indexOf(ratio);
            mOptimalPreviewSizes.set(index, previewSize);
        }
    }

    /** Obtain the largest available preview size */
    protected Size getLargestPreviewSize() {
        Size firstPreviewSize = mSupportedPreviewSizes.get(0);
        Size lastPreviewSize = mSupportedPreviewSizes.get(mSupportedPreviewSizes.size() - 1);
        if (firstPreviewSize.width > lastPreviewSize.width || firstPreviewSize.height > lastPreviewSize.height) {
            isPreviewSizeDescending = true;
            return firstPreviewSize;
        } else {
            isPreviewSizeDescending = false;
            return lastPreviewSize;
        }
    }

    /** Obtain the largest available picture size */
    protected Size getLargestPictureSize() {
        Size firstPictureSize = mSupportedPictureSizes.get(0);
        Size lastPictureSize = mSupportedPictureSizes.get(mSupportedPictureSizes.size() - 1);
        if (firstPictureSize.width > lastPictureSize.width || firstPictureSize.height > lastPictureSize.height) {
            isPictureSizeDescending = true;
            return firstPictureSize;
        } else {
            isPictureSizeDescending = false;
            return lastPictureSize;
        }
    }

    protected Camera getCamera() {
        if (mSimpleCameraWrapper != null) {
            return mSimpleCameraWrapper.getCamera();
        }

        return null;
    }

    protected SimpleCameraWrapper getCameraWrapper() {
        return mSimpleCameraWrapper;
    }

    /** Get a list of supported picture sizes */
    protected List<Size> getSupportedPictureSizes() {
        return mSupportedPictureSizes;
    }

    /** Determine if the previes size list is in descending fashion */
    protected boolean isPreviewSizeDescending() {
        return isPreviewSizeDescending;
    }

    /** Determine if the picture size list is in descending fashion */
    protected boolean isPictureSizeDescending() {
        return isPictureSizeDescending;
    }

    /** Set the picture size for the camera */
    protected void setPictureSize(Size pictureSize) {
        double previousRatio = mSelectedRatio;

        mSelectedPictureSize = pictureSize;
        mSelectedRatio = (double) mSelectedPictureSize.height / mSelectedPictureSize.width;
        mSelectedPreviewSize = getOptimalPreviewSize();

        // Update the camera picture and preview size parameters
        Camera.Parameters parameters = mSimpleCameraWrapper.getCameraParameters();
        parameters.setPictureSize(mSelectedPictureSize.width, mSelectedPictureSize.height);
        parameters.setPreviewSize(mSelectedPreviewSize.width, mSelectedPreviewSize.height);

        // Stop the preview before updating the camera parameters
        mSimpleCameraWrapper.stopPreview();
        mSimpleCameraWrapper.setCameraParameters(parameters);

        // Start the preview
        if (mSimpleCameraWrapper.isPreviewShown()) mSimpleCameraWrapper.startPreview();

        // Request a new layout when the preview size is changing
        if (Double.doubleToLongBits(previousRatio) != Double.doubleToLongBits(mSelectedRatio)) requestLayout();

        log(Log.DEBUG, getClass().getSimpleName(), "Now capturing in " + mSelectedPictureSize.width + ":" + mSelectedPictureSize.height + " (" + mSelectedRatio + ")");
    }

    /** Obtain the currently selected preview size */
    protected final Size getPreviewSize() {
        return mSelectedPreviewSize;
    }

    /** Obtain the currently selected picture size */
    protected final Size getPictureSize() {
        return mSelectedPictureSize;
    }

    /**
     * Determines if the device has multiple camera (front and back).
     * This method currently does <strong>NOT</strong> handle
     * devices that has 2 front-facing or 2 back-facing cameras.
     * @return true if both front and back camera is available.
     */
    protected boolean isMultipleCameraAvailable() {
        if (mSimpleCameraWrapper != null) {
            return mSimpleCameraWrapper.isMultipleCameraAvailable();
        } else {
            return false;
        }
    }

    /**
     * Determines if the app currently selects the back-facing camera.
     * @return true if back-facing camera is currently selected.
     */
    protected boolean isBackFacingCameraSelected() {
        if (mSimpleCameraWrapper != null) {
            return mSimpleCameraWrapper.isBackFacingCameraSelected();
        } else {
            return false;
        }
    }

    /**
     * Determines if the app currently selects the front-facing camera.
     * @return true if front-facing camera is currently selected.
     */
    protected boolean isFrontFacingCameraSelected() {
        if (mSimpleCameraWrapper != null) {
            return mSimpleCameraWrapper.isFrontFacingCameraSelected();
        } else {
            return false;
        }
    }

    /**
     * Switches the between the available cameras.
     * @return true if camera is switched
     */
    protected boolean switchCamera() {
        boolean retval;
        if (mSimpleCameraWrapper != null) {
            if (mSimpleCameraWrapper.isMultipleCameraAvailable()) {
                retval = mSimpleCameraWrapper.switchCamera();
            } else {
                retval = false;
            }
        } else {
            retval = false;
        }

        if (retval) {
            mHolder.removeCallback(this);
            mHolder.addCallback(this);
        }

        return retval;
    }

    /** Convert touch position x:y to {@link android.hardware.Camera.Area} position -1000:-1000 to 1000:1000. */
    private Rect calculateTapArea(float x, float y, float coefficient) {
        int areaSize = Float.valueOf(FOCUS_AREA_SIZE * coefficient).intValue();

        int left = clamp((int) x - areaSize / 2, 0, getWidth() - areaSize);
        int top = clamp((int) y - areaSize / 2, 0, getHeight() - areaSize);

        RectF rect = new RectF(left, top, left + areaSize, top + areaSize);
        log(Log.DEBUG, getClass().getSimpleName() + "#CalculateTapArea()", rect.toShortString());
        return round(rect);
    }

    private int clamp(int x, int min, int max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    private Rect round(RectF rect) {
        return new Rect(Math.round(rect.left), Math.round(rect.top), Math.round(rect.right), Math.round(rect.bottom));
    }

    /** Sets if the camera preview should be drawn in full size (cropped) or not */
    protected void setCameraCropped(boolean isCameraCropped) {
        this.isCameraCropped = isCameraCropped;
        invalidate();
    }

    /** Sets if the captured image should be the original camera image */
    protected void setCaptureOriginalImage(boolean isCaptureOriginalImage) {
        this.isCaptureOriginalImage = isCaptureOriginalImage;
    }

    /** Determine if the captured image should be the original camera image */
    protected boolean isCaptureOriginalImage() {
        return isCaptureOriginalImage;
    }

    /** Enable log messages */
    public void enableLogging() {
        isLogPrinted = true;
    }

    /** Disable log messages */
    public void disableLogging() {
        isLogPrinted = false;
    }

    /**
     * Prints a {@link Log} message.
     * Log messages printed via this method will only show up if specified.
     * @param logType The specified log type, may be {@link Log#DEBUG}, {@link Log#INFO}, and other log types
     * @param tag The log tag to print
     * @param message The log message to print
     */
    private void log(int logType, String tag, String message) {
        if (isLogPrinted) Log.println(logType, tag, message);
    }

    /** The interface for surface changes */
    public interface OnSurfaceChangedListener {
        void surfaceCreated(SurfaceHolder surfaceHolder);
        void surfaceDestroyed(SurfaceHolder surfaceHolder);
        void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h);
    }

    /** The interface for view measure changes */
    public interface OnMeasureListener {
        void onPreviewMeasured(int widthMeasureSpec, int heightMeasureSpec);
    }
}