package com.kreditplus.eform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kreditplus.eform.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class faceRecogornation extends AppCompatActivity implements SurfaceHolder.Callback, Camera.FaceDetectionListener {
    Camera camera;
    SurfaceView mPreview;
    ImageView borderView;
    SurfaceHolder holderRectangle, holderCamera;
    Button captureImage;
    Bitmap bitmap = null;
    private Camera.Face[] mFaces;
    TextView statusFace;
    LayoutInflater controlInfalter = null;
    Camera.FaceDetectionListener myFaceListener;
    // untuk di callback jangan salah lol use ur fucking eye
    Bitmap correctBmp;
    File uploadFile2;
    private boolean safeToTakePicture = false;
    private float RectLeft, RectTop,RectRight,RectBottom ;
    int  deviceHeight,deviceWidth;
    @Override
    protected void onStart() {
        super.onStart();
    }

    int[] colors = {Color.parseColor("#990000"), Color.parseColor("#FE0000"), Color.parseColor("#990000")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recogornation);
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BR_TL, colors);

        mPreview =  findViewById(R.id.myRealCamera);
        holderCamera = mPreview.getHolder();
        captureImage = findViewById(R.id.captureImage);
        borderView = findViewById(R.id.borderView);
        borderView.setAlpha(0.5f);


        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        /*   mPreview.getHolder().*/
        if (bitmap == null) {
            captureImage.setText("Register Image");
        }
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    camera.takePicture(null, null, myPictureCallback_JPG);
                } else {
                    Toast.makeText(faceRecogornation.this, "Cant Get Camera", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open(0);
            camera.setPreviewDisplay(mPreview.getHolder());
            camera.setDisplayOrientation(90);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setCamFocusMode();
        /*camera.setParameters(params);*/
        camera.startPreview();
        safeToTakePicture = true;



    }

    private void setCamFocusMode() {
        if (null == camera) {
            return;
        }

        /* Set Auto focus */
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size previewSize = getOptimalSize();
        /*Camera.Size bestPreview = getBestPreviewSize(mPreview.getWidth(), mPreview.getHeight(), parameters);*/
        List<Camera.Size> allSizes = parameters.getSupportedPictureSizes();
        Camera.Size size = allSizes.get(0); // get top size
        for (int i = 0; i < allSizes.size(); i++) {
            if (allSizes.get(i).width > size.width)
                size = allSizes.get(i);
        }
        //set max Picture Size
        parameters.setPictureSize(size.width, size.height);
        /*parameters.setPreviewSize(bestPreview.width, bestPreview.height);*/

        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        camera.setParameters(parameters);
    }

    private Camera.Size getOptimalSize() {
        Camera.Size result = null;
        final Camera.Parameters parameters = camera.getParameters();
        for (final Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= mPreview.getWidth() && size.height <= mPreview.getHeight()) {
                if (result == null) {
                    result = size;
                } else {
                    final int resultArea = result.width * result.height;
                    final int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        if (result == null) {
            result = parameters.getSupportedPreviewSizes().get(0);
        }
        return result;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        Log.i("PREVIEW", "surfaceDestroyed");
    }

    private final Camera.ShutterCallback myShutterCallBack = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
        }
    };
    Camera.PictureCallback myPictureCallback_RAW = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
        }
    };
    // TODO: Ganti fungsi untuk mengambil camera
    final transient Camera.PictureCallback myPictureCallback_JPG = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();

            if (pictureFile == null) {
                Toast.makeText(getApplicationContext(),"File Not Found",Toast.LENGTH_LONG).show();
                return;
            }else {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    Bitmap imges = ByteArrayToBitmap(data);
                    imges = rotateImage(imges, 270);
                    imges.compress(Bitmap.CompressFormat.JPEG, 50, bos);

                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(bos.toByteArray());
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.e("Error Make File", e.getMessage());
                } catch (IOException e) {
                    Log.e("Error I/O", e.getMessage());
                }
                safeToTakePicture = false;
            }

        }
    };

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    @Override
    public void onFaceDetection(final Camera.Face[] faces, final Camera camera) {
        if (faces.length == 0) {
            statusFace.setVisibility(View.VISIBLE);
            /*Toast.makeText(getApplicationContext(), "No Face Founded", Toast.LENGTH_LONG).show();*/
        } else {
            statusFace.setVisibility(View.GONE);
            if(safeToTakePicture) {
                //camera.takePicture(myShutterCallBack, myPictureCallback_RAW, myPictureCallback_JPG);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open(1);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setJpegQuality(100);
        camera.setParameters(parameters);
        mPreview.getHolder().addCallback(this);
        mPreview.setZOrderOnTop(true);
        mPreview.setZOrderMediaOverlay(true);
    }



    public Bitmap ByteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_1" + ".jpg");

        return mediaFile;
    }

    private static File getOutputMediaFileTemp() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_2" + ".jpg");

        return mediaFile;
    }

    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
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


}
