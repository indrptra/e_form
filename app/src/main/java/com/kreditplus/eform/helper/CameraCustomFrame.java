package com.kreditplus.eform.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class CameraCustomFrame extends BaseActivity implements View.OnClickListener {

    public final static String FRAME = "frame";
    public final static String NO_FRAME = "no_frame";
    public final static String FRAME_KTP = "frame_ktp";
    public final static String FRAME_KTP_SELFIE = "frame_ktp_selfie";

    static Bitmap mutableBitmap;

    @BindView(R.id.ivFrameKTP)
    ImageView ivFrameKTP;
    @BindView(R.id.ivFrameKTPSelfie)
    ImageView ivFrameKTPSelfie;
    @BindView(R.id.image)
    ImageView image;
//    @BindView(R.id.rlDirectorSquare)
//    RelativeLayout rlDirectorSquare;
    Bitmap bmp, itembmp;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    File imageFileName = null;
    File imageFileFolder = null;
    int camCurrentId = 0 ;
    int camFrontId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    Display d;
    int screenhgt, screenwdh;
    ProgressDialog dialog;
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private Camera camera = null;
    private boolean inPreview = false;
    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
            } catch (Throwable t) {
                if (BuildConfig.DEBUG) {
                    Log.e("surfaceCallback", "Exception in setPreviewDisplay()", t);
                }
                Toast.makeText(CameraCustomFrame.this, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        }


        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {

            Camera.Parameters parameters = camera.getParameters();
            //SET THE PARAMS
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//            parameters.setPictureFormat(PixelFormat.JPEG);
//            parameters.setJpegQuality(100);

            Camera.Size previewSize = getBestPreviewSize(width, height,
                    parameters);

            Camera.Size picSize = getBestPictureSize(width, height,
                    parameters);


            if (previewSize != null && picSize != null) {
                try {
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                    parameters.setPictureSize(picSize.width / 3, picSize.height / 3);
                    camera.setDisplayOrientation(90);
                    camera.setParameters(parameters);

                    camera.startPreview();
                    inPreview = true;

                    camCurrentId = Camera.CameraInfo.CAMERA_FACING_FRONT;
//                    camera = Camera.open(camCurrentId);

                    setCameraDisplayOrientation(CameraCustomFrame.this, camCurrentId, camera);
                    try {

                        camera.setPreviewDisplay(previewHolder);
                    } catch (IOException ey) {
                        ey.printStackTrace();
                    }
                    camera.startPreview();
                } catch (Exception e) {
                    Camera.Parameters p = camera.getParameters();
                    //SET THE PARAMS
//                    p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                    Camera.Size s = p.getSupportedPreviewSizes().get(0);
                    p.setPreviewSize(s.width, s.height);
                    camera.setDisplayOrientation(90);
                    camera.setParameters(p);
//                    camera.startPreview();
                    inPreview = true;


                   /* if (inPreview) {
                        camera.stopPreview();
                    }
//NB: if you don't release the current camera before switching, you app will crash
                    camera.release();

//swap the id of the camera to be used
                    if(camCurrentId == Camera.CameraInfo.CAMERA_FACING_BACK){
                        camCurrentId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    }
                    else {
                        camCurrentId = Camera.CameraInfo.CAMERA_FACING_BACK;
                    }*/
                    camCurrentId = Camera.CameraInfo.CAMERA_FACING_FRONT;
//                    camera = Camera.open(camCurrentId);

                    setCameraDisplayOrientation(CameraCustomFrame.this, camCurrentId, camera);
                    try {

                        camera.setPreviewDisplay(previewHolder);
                    } catch (IOException ey) {
                        ey.printStackTrace();
                    }
                    camera.startPreview();
                }
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }

    };
    private MediaScannerConnection msConn;
    Camera.PictureCallback photoCallback = new Camera.PictureCallback() {
        public void onPictureTaken(final byte[] data, final Camera camera) {
            dialog = ProgressDialog.show(CameraCustomFrame.this, "", "Saving Photo");
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                    }
                    onPictureTake(data, camera);
                }
            }.start();
        }
    };

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.camera_custom_frame);
        ButterKnife.bind(this);

        initComponents();

        preview = (SurfaceView) findViewById(R.id.surface);

        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        previewHolder.setFixedSize(getWindow().getWindowManager()
                .getDefaultDisplay().getWidth(), getWindow().getWindowManager()
                .getDefaultDisplay().getHeight());
    }

    @Override
    protected int getContentView() {
        return R.layout.camera_custom_frame;
    }

    private void initComponents() {

//        requestFullScreenLayout();
//        requestTransparentStatusBar();

        //REQUEST SCREEN ALWAYS ON
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        //GET DISPLAY SIZE
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int displayWidth = size.x;
//        int displayHeight = size.y;
//
//        //SET SIZE BASED ON KTP SIZE COMPARISON 1:1.5 -- 1.586:1
//        int height = displayHeight - 100;
////        int width = height + (height / 2);
//        int width = (int) (height * 1.586);
//
//        rlDirectorSquare.getLayoutParams().height = height;
//        rlDirectorSquare.getLayoutParams().width = width;

        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString(FRAME, "").equals(NO_FRAME)) {
                ivFrameKTP.setVisibility(View.GONE);
                ivFrameKTPSelfie.setVisibility(View.GONE);
//                image.setRotation(0);
            } else if (bundle.getString(FRAME, "").equals(FRAME_KTP_SELFIE)) {
                ivFrameKTP.setVisibility(View.GONE);
                ivFrameKTPSelfie.setVisibility(View.VISIBLE);
//                image.setRotation(270);
            } else {
                ivFrameKTP.setVisibility(View.VISIBLE);
                ivFrameKTPSelfie.setVisibility(View.GONE);
//                image.setRotation(0);
            }
        }*/
        ivFrameKTP.setVisibility(View.GONE);
        ivFrameKTPSelfie.setVisibility(View.VISIBLE);
//        preview.setRotation(270);
//        image.setRotation(270);

    }

    @OnClick(R.id.rlTakePicture)
    public void onClick() {
        try {
            camera.takePicture(null, null, photoCallback);
            inPreview = false;
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            camera = Camera.open();
//            camera.setDisplayOrientation(90);
        } catch (Exception e) {
        }

    }

    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera = null;
        inPreview = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onDestroy();
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return (result);
    }

    private Camera.Size getBestPictureSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return (result);
    }

    public void onPictureTake(byte[] data, Camera camera) {

        bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        mutableBitmap = bmp.copy(Bitmap.Config.RGB_565, true);

        //SAVE PHOTO TO MEMORY
//        savePhoto(mutableBitmap);
        imageFileFolder = new File(getCacheDir(), "Kreditplus Mobile");
        imageFileFolder.mkdir();
        Calendar c = Calendar.getInstance();
        String date = fromInt(c.get(Calendar.MONTH)) + fromInt(c.get(Calendar.DAY_OF_MONTH)) + fromInt(c.get(Calendar.YEAR)) + fromInt(c.get(Calendar.HOUR_OF_DAY)) + fromInt(c.get(Calendar.MINUTE)) + fromInt(c.get(Calendar.SECOND));
        imageFileName = new File(imageFileFolder, date.toString() + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(imageFileName);
            fos.write(data);
            fos.close();
            scanPhoto(imageFileName.toString());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        dialog.dismiss();

        //SEND RESULT
        Intent intent = new Intent();
        intent.putExtra("data", mutableBitmap);
        intent.putExtra("file", imageFileName);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void savePhoto(Bitmap bmp) {
        imageFileFolder = new File(getCacheDir(), "Kreditplus Mobile");
        imageFileFolder.mkdir();
        FileOutputStream out = null;
        Calendar c = Calendar.getInstance();
        String date = fromInt(c.get(Calendar.MONTH)) + fromInt(c.get(Calendar.DAY_OF_MONTH)) + fromInt(c.get(Calendar.YEAR)) + fromInt(c.get(Calendar.HOUR_OF_DAY)) + fromInt(c.get(Calendar.MINUTE)) + fromInt(c.get(Calendar.SECOND));
        imageFileName = new File(imageFileFolder, date.toString() + ".jpg");
        try {
            out = new FileOutputStream(imageFileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            scanPhoto(imageFileName.toString());
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String fromInt(int val) {
        return String.valueOf(val);
    }

    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(CameraCustomFrame.this, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);
                if (BuildConfig.DEBUG) {
                    Log.i("objinPhotoUtility", "connection established");
                }
            }

            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();
                if (BuildConfig.DEBUG) {
                    Log.i("objinPhotoUtility", "scan completed");
                }
            }
        });
        msConn.connect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBack() {
//        Log.e("onBack :", "yes");
//        camera.takePicture(null, null, photoCallback);
//        inPreview = false;
    }

    @Override
    public void onClick(View v) {
        camera.takePicture(null, null, photoCallback);
        inPreview = false;
    }

}