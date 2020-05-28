package itsmagic.present.simplecamera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import itsmagic.present.simplecamera.R;
import itsmagic.present.simplecamera.callback.SimpleCameraByteCallback;
import itsmagic.present.simplecamera.callback.SimpleCameraCallback;
import itsmagic.present.simplecamera.SimpleCamera;
import itsmagic.present.simplecamera.SimpleCameraUtil;

/**
 * Created by Alvin Rusli on 9/28/2016.
 * <p/>
 * A custom {@link SurfaceView} class for camera previews.
 */
public class SimpleCameraPreview extends FrameLayout implements SimpleCamera.OnSurfaceChangedListener, SimpleCamera.OnMeasureListener {

    /** The {@link SimpleCamera} object */
    private SimpleCamera mSimpleCamera = null;

    /** The {@link ImageView} object */
    private ImageView mCameraImageView = null;

    /** The {@link TextView} for info texts */
    private TextView mNoCameraText = null;

    /** The preview size listener */
    private OnPreviewSizeChangedListener mPreviewSizeChangedListener = null;

    /** The preview width */
    private int mPreviewWidth = -1;

    /** The preview height */
    private int mPreviewHeight = -1;

    /** The preview gravity */
    private int mPreviewGravity = Gravity.CENTER;

    /** Determines if the camera preview should run */
    private boolean isPreviewShown = false;

    /** Determines if this class should print log messages */
    private boolean isLogPrinted = false;

    public SimpleCameraPreview(Context context) {
        super(context);
        initCameraPreview();
    }

    public SimpleCameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCameraPreview();
    }

    public SimpleCameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCameraPreview();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleCameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initCameraPreview();
    }

    /** Initializes this widget */
    private void initCameraPreview() {
        // Set the background color of this widget to black
        setBackgroundColor(Color.BLACK);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

    /** Sets the {@link SimpleCamera} object */
    public final void setCamera(SimpleCamera camera) {
        setCamera(camera, mPreviewGravity);
    }

    /** Sets the {@link SimpleCamera} object */
    public final void setCamera(SimpleCamera camera, int previewGravity) {
        if (mSimpleCamera == null && camera != null) {
            mSimpleCamera = camera;
            mSimpleCamera.setOnSurfaceChangedListener(this);
            mSimpleCamera.setOnMeasureListener(this);
            mSimpleCamera.getCameraWrapper().openCamera();
            mSimpleCamera.enableTapToFocus();

            if (mSimpleCamera.getCamera() != null) {
                // Set the SurfaceView on the layout with the specified gravity
                final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                params.gravity = previewGravity;
                mPreviewGravity = previewGravity;
                mSimpleCamera.setLayoutParams(params);

                // Add a image view
                mCameraImageView = new ImageView(getContext());
                mCameraImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mCameraImageView.setLayoutParams(params);

                // Add the SurfaceView and an ImageView at the BOTTOM of the Z axis
                post(new Runnable() {
                    @Override
                    public void run() {
                        addView(mSimpleCamera);
                        addView(mCameraImageView);
                        SimpleCameraUtil.sendViewToBack(mSimpleCamera);
                        SimpleCameraUtil.sendViewToBack(mCameraImageView);
                    }
                });
            } else {
                // Set the error TextView at the center of the layout
                addErrorLayout();
            }
        } else {
            if (camera != null) {
                // Remove the previous camera, and set the new camera afterwards
                removeCamera();
                setCamera(camera, previewGravity);
            } else {
                // Set the error TextView at the center of the layout
                NullPointerException npe = new NullPointerException("Trying to set a null SimpleCamera object");
                log(Log.WARN, getClass().getSimpleName() + "#setCamera()", npe.getMessage());
                addErrorLayout(npe.getMessage());
            }
        }
    }

    /** Remove the {@link SurfaceView} and {@link TextView} object from this layout, and set them to null */
    public final void removeCamera() {
        removeLayoutViews();
        mSimpleCamera = null;
        mNoCameraText = null;
    }

    /** Show a simple {@link TextView} showing an error message */
    private void addErrorLayout() {
        addErrorLayout(getResources().getString(R.string.simplecamera_no_camera_error));
    }

    /** Show a simple {@link TextView} showing an error message */
    private void addErrorLayout(String errorMessage) {
        mNoCameraText = new TextView(getContext());
        mNoCameraText.setText(errorMessage);
        mNoCameraText.setTextColor(Color.WHITE);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = mPreviewGravity;
        mNoCameraText.setLayoutParams(params);

        // Add the TextView
        addView(mNoCameraText);
        SimpleCameraUtil.sendViewToBack(mNoCameraText);
    }

    /** Remove the {@link SurfaceView} and {@link TextView} object from this layout */
    private void removeLayoutViews() {
        if (mSimpleCamera != null) {
            removeView(mSimpleCamera);
        }

        if (mNoCameraText != null) {
            removeView(mNoCameraText);
        }
    }

    /** Get a list of supported picture sizes */
    public final List<Camera.Size> getSupportedPictureSizes() {
        if (mSimpleCamera != null) {
            return mSimpleCamera.getSupportedPictureSizes();
        } else {
            return null;
        }
    }

    /** Set the picture size for the camera */
    public final void setPictureSize(Camera.Size pictureSize) {
        if (mSimpleCamera != null) mSimpleCamera.setPictureSize(pictureSize);
    }

    /** Obtain the currently selected preview size */
    public final Camera.Size getPreviewSize() {
        if (mSimpleCamera != null) {
            return mSimpleCamera.getPreviewSize();
        } else {
            return null;
        }
    }

    /** Obtain the currently selected picture size */
    public final Camera.Size getPictureSize() {
        if (mSimpleCamera != null) {
            return mSimpleCamera.getPictureSize();
        } else {
            return null;
        }
    }

    /**
     * Determines if the device has multiple camera (front and back).
     * This method currently does <strong>NOT</strong> handle
     * devices that has 2 front-facing or 2 back-facing cameras.
     * @return true if both front and back camera is available.
     */
    public final boolean isMultipleCameraAvailable() {
        return mSimpleCamera != null && mSimpleCamera.isMultipleCameraAvailable();
    }

    /**
     * Determines if the app currently selects the back-facing camera.
     * @return true if back-facing camera is currently selected.
     */
    public final boolean isBackFacingCameraSelected() {
        return mSimpleCamera != null && mSimpleCamera.isBackFacingCameraSelected();
    }

    /**
     * Determines if the app currently selects the front-facing camera.
     * @return true if front-facing camera is currently selected.
     */
    public final boolean isFrontFacingCameraSelected() {
        return mSimpleCamera != null && mSimpleCamera.isFrontFacingCameraSelected();
    }

    /** Use the front facing camera (if available) **/
    public final void useFrontFacingCamera() {
        if (mSimpleCamera != null && !mSimpleCamera.isFrontFacingCameraSelected()) switchCamera();
    }

    /** Use the back facing camera (if available) **/
    public final void useBackFacingCamera() {
        if (mSimpleCamera != null && !mSimpleCamera.isBackFacingCameraSelected()) switchCamera();
    }

    /** Switches the between the available cameras */
    public final void switchCamera() {
        if (mSimpleCamera != null) {
            if (mSimpleCamera.isMultipleCameraAvailable()) {
                mSimpleCamera.switchCamera();

                // Get the preview gravity
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mSimpleCamera.getLayoutParams();

                // Recreate the SurfaceView with the new camera
                setCamera(mSimpleCamera, params.gravity);
                mSimpleCamera.recalculateSizes();
            }
        }
    }

    /** Start a preview of the camera */
    public final void startPreview() {
        if (mSimpleCamera != null && mSimpleCamera.getCameraWrapper() != null) {
            isPreviewShown = true;
            mSimpleCamera.getCameraWrapper().setPreviewShown(isPreviewShown);
            mSimpleCamera.getCameraWrapper().openCamera();
            mSimpleCamera.getCameraWrapper().startPreview();
        } else {
            log(Log.WARN, getClass().getSimpleName(), "Trying to start a preview with null camera, call #setCamera() beforehand");
            setCamera(null);
        }
    }

    /** Stop the camera preview */
    public final void stopPreview() {
        if (mSimpleCamera != null && mSimpleCamera.getCameraWrapper() != null) {
            isPreviewShown = false;
            mSimpleCamera.getCameraWrapper().setPreviewShown(isPreviewShown);
            mSimpleCamera.getCameraWrapper().stopPreview();
        } else {
            log(Log.WARN, getClass().getSimpleName(), "Trying to stop a preview with null camera, call #setCamera() beforehand");
        }
    }

    /** Take a picture using the camera */
    public final void takePicture(final SimpleCameraCallback callback) {
        if (mSimpleCamera != null && mSimpleCamera.getCameraWrapper() != null) {
            if (mSimpleCamera.isCaptureOriginalImage()) {
                mSimpleCamera.getCameraWrapper().takePicture(callback);
            } else {
                // Obtain the bitmap from the camera capture
                mSimpleCamera.getCameraWrapper().takePicture(new SimpleCameraByteCallback(getContext(), new SimpleCameraByteCallback.OnCameraCaptureListener() {
                    @Override
                    public void onCameraByteCaptureSuccess(byte[] capturedBytes) {
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(capturedBytes, 0, capturedBytes.length);
                        mCameraImageView.setImageBitmap(bitmap);

                        // Obtain the bitmap from the ImageView
                        mCameraImageView.setDrawingCacheEnabled(true);
                        Bitmap newBitmap = mCameraImageView.getDrawingCache(true).copy(Bitmap.Config.ARGB_8888, false);
                        mCameraImageView.destroyDrawingCache();
                        mCameraImageView.setImageBitmap(null);

                        // Convert the new bitmap into byte array
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        // Call the actual callback
                        callback.onPictureTaken(byteArray, null);
                    }
                }));
            }
        } else {
            log(Log.WARN, getClass().getSimpleName(), "Trying to take a picture with null camera, call #setCamera() beforehand");
        }
    }

    /** Sets if the camera preview should be drawn in full size (cropped) or not */
    public final void setCameraCropped(boolean isCameraCropped) {
        if (mSimpleCamera != null) mSimpleCamera.setCameraCropped(isCameraCropped);
    }

    /** Sets if the captured image should be the original camera image */
    public final void setCaptureOriginalImage(boolean isCaptureOriginalImage) {
        if (mSimpleCamera != null) mSimpleCamera.setCaptureOriginalImage(isCaptureOriginalImage);
    }

    /** Enable log messages */
    public final void enableLogging() {
        isLogPrinted = true;
    }

    /** Disable log messages */
    public final void disableLogging() {
        isLogPrinted = false;
    }

    /** Obtain the preview layout's width */
    public final int getPreviewWidth() {
        return mPreviewWidth;
    }

    /** Obtain the preview layout's height */
    public final int getPreviewHeight() {
        return mPreviewHeight;
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

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // Do nothing
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isPreviewShown = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
        // Do nothing
    }

    @Override
    public void onPreviewMeasured(int widthMeasureSpec, int heightMeasureSpec) {
        View childView;
        LayoutParams layoutParams;
        for (int i = 0; i < getChildCount(); i++) {
            childView = getChildAt(i);
            layoutParams = (LayoutParams) childView.getLayoutParams();
            if (layoutParams.isScaleWithPreview()) {
                if (mSimpleCamera == null) return;
                layoutParams.width = widthMeasureSpec;
                layoutParams.height = heightMeasureSpec;
                layoutParams.gravity = ((FrameLayout.LayoutParams) mSimpleCamera.getLayoutParams()).gravity;
            }
        }

        if (mPreviewWidth != widthMeasureSpec || mPreviewHeight != heightMeasureSpec) {
            mPreviewWidth = widthMeasureSpec;
            mPreviewHeight = heightMeasureSpec;
            if (mPreviewSizeChangedListener != null)
                mPreviewSizeChangedListener.onPreviewSizeChanged(mPreviewWidth, mPreviewHeight);
        }
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {

        /**
         * Determines if the child view should match the preview dimensions.
         */
        private boolean isScaleWithPreview = false;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleCameraPreview);
            isScaleWithPreview = typedArray.getBoolean(R.styleable.SimpleCameraPreview_scaleWithPreview, false);
            typedArray.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public LayoutParams(FrameLayout.LayoutParams source) {
            super(source);
        }

        public boolean isScaleWithPreview() {
            return isScaleWithPreview;
        }

        public void setScaleWithPreview(boolean scaleWithPreview) {
            isScaleWithPreview = scaleWithPreview;
        }
    }

    /** Obtain the preview size listener */
    public OnPreviewSizeChangedListener getOnPreviewSizeChangedListener() {
        return mPreviewSizeChangedListener;
    }

    /** Set the preview size listener */
    public void setOnPreviewSizeChangedListener(OnPreviewSizeChangedListener listener) {
        mPreviewSizeChangedListener = listener;
    }

    /** The listener for preview size changes */
    public interface OnPreviewSizeChangedListener {

        /** Called when preview size changes */
        void onPreviewSizeChanged(int width, int height);
    }
}