package com.kreditplus.eform.helper;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.presenter.CoordinatePresenter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Iwan Nurdesa on 22/06/16.
 */
public class Util {
    private View mView;

    public static Intent getPickImageChooserIntent(Context context) {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri(context);

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture by camera.
     */
    public static Uri getCaptureImageOutputUri(Context context) {
        Uri outputFileUri = null;
        File getImage = context.getExternalCacheDir();
        if (getImage != null) {
//            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
            outputFileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".com.kreditplus.eform.helper", new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    /**
     * Get the URI of the selected image from {@link ##getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public static Uri getPickImageResultUri(Intent data, Context context) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri(context) : data.getData();
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, null, null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static RequestBody toTextRequestBody(String value) {
        if (value != null) {
            if (!"".equalsIgnoreCase(value)) {
                RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
                return body;
            }
        }
        return RequestBody.create(MediaType.parse("text/plain"), "");
    }

    public static RequestBody toTextRequestBodyNull() {
        String value = "";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static RequestBody toImageRequestBody(File file) {
        if (file != null) {
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            return body;
        }
        return null;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return "";
    }

    public static String bitmapToFile(Bitmap bitmap) {
        FileOutputStream outStream = null;
        String fileName = getFilename();
        try {
            outStream = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("Bitma to file", String.valueOf(e));
            Crashlytics.logException(e);
        }
        return fileName;
    }

    public static String bitmapToFile2(Bitmap bitmap) {
        FileOutputStream outStream = null;
        String fileName = getFilename2();
        try {
            outStream = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("Bitmap to file 2", String.valueOf(e));
            Crashlytics.logException(e);
        }
        return fileName;
    }

    private static String getFilename2() {
        File file = null;
        file = new File(Environment.getExternalStorageDirectory().getPath() +
                "/Android/data/" + "com.kreditplus.eform" + "/files/" + "Pictures" + "/EasyImage");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + "eform" + System.currentTimeMillis() + ".jpg";
    }

    public static String getFilename() {
        File file = null;
        file = new File(Environment.getExternalStorageDirectory().getPath() +
                "/Android/data/" + "eform" + "/Images/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + "eform" + System.currentTimeMillis() + ".jpg";
    }

    public static boolean checkActiveLocation(Context context) {
        LocationManager locationManager;
        boolean isGPSEnabled = false;

        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled /*&& !isNetworkEnabled*/) {
            showSettingsAlert(context);
            return false;
        }
        return true;
    }

    public static String getEasyImageDir(Context context) {
        return "/storage/emulated/0/Android/data/com.kreditplus.eform/files/Pictures/EasyImage";
    }

    /**
     * this method use for saving taken image to gallery
     */
    public static void saveGallery(Context context, File file) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/EFORM_IMAGES");
        myDir.mkdirs();

        String fileName = System.currentTimeMillis() + ".jpg";
        File tmpFile = new File(myDir, fileName);
        if (tmpFile.exists()) tmpFile.delete();

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        try {
            FileOutputStream outStream = new FileOutputStream(tmpFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e("Save gallery", String.valueOf(e));
            Crashlytics.logException(e);
        }

        MediaScannerConnection.scanFile(context, new String[]{tmpFile.getPath()}, new String[]{"image/jpeg"}, null);

    }

    /**
     * this method use for checked service already running or not
     */
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    // Cek

    public static String kreditmuListTimeFormat() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        Date tommorow = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return dateFormat.format(tommorow);
    }

    public static String persetujuanTimeFormat(DateTime dateTime) {
        DateTimeFormatter dtfStartOut = DateTimeFormat.forPattern("dd/MM/yy");
        return dtfStartOut.print(dateTime);
    }

    public static String listPengajuanTimeFormat(DateTime dateTime) {
        DateTimeFormatter dtfStartOut = DateTimeFormat.forPattern("dd MMMM yyyy");
        return dtfStartOut.print(dateTime);
    }

    public static String TanggalHariIni(DateTime dateTime) {
        DateTimeFormatter dtfStartOut = DateTimeFormat.forPattern("yyyy-MM-dd");
        return dtfStartOut.print(dateTime);
    }

    public static String TanggalBalcklist(DateTime dateTime) {
        DateTimeFormatter dtfStartOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dtfStartOut.print(dateTime);
    }

    public static String RandomDateNumber() {
        long offset = Timestamp.valueOf(TanggalBalcklist(new DateTime())).getTime();
        long rand = (long) (offset / Math.random());
        String random = String.valueOf(rand);
        return random;
    }

    public static String ConvertDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        String newDate = "";
        try {
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            newDate = newformat.format(date);

        } catch (ParseException e) {
            if (BuildConfig.DEBUG)
                Log.e("ConvertDate", String.valueOf(e));
        }
        return newDate;
    }

    public static String ConvertDateSignin(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String newDate = "";
        try {
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat newformat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            newDate = newformat.format(date);

        } catch (ParseException e) {
            if (BuildConfig.DEBUG)
                Log.e("Conver date signin", String.valueOf(e));
        }
        return newDate;
    }

    /**
     * this method use for get version name phone
     */
    public static String getVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            if (BuildConfig.DEBUG)
                Log.e("Get version name", String.valueOf(e));
            Crashlytics.logException(e);
        }
        String version = pInfo.versionName;

        return version;
    }

    /**
     * this method use for get version code phone
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            if (BuildConfig.DEBUG)
                Log.e("Version Name", String.valueOf(ex));
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("Version Name", String.valueOf(e));
        }
        return 0;
    }

    /**
     * This method is used to get formatted nominal by int input type, (i.e. 5000 to Rp. 5.000).
     *
     * @param nominal Nominal in integer
     * @return Return formatted nominal.
     */
    public static String formatNominal(int nominal) {
        try {
            String result = "";
            String s = Integer.toString(nominal) + "";

            String a = s.replace(" ", "");

            String j = "";
            String k = a;
            if (a.endsWith(".00")) {
                j = a.substring(0, a.length() - 3);
                k = j;
            }

            String z = k.replace("-", "");
            String b = z.replace("Rp.", "");
            String c = b.replace("Rp", "");
            String d = c.replace(",00", "");

            s = d.replace(".", "");

            int count = 1;
            for (int i = s.length() - 1; i >= 0; i--) {
                result = s.charAt(i) + result;
                if (count % 3 == 0 && i != 0)
                    result = "." + result;
                count++;
            }
            return "Rp. " + result;
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("formatNominal", String.valueOf(e));
            return Integer.toString(nominal) + "";
        }

    }

    /**
     * This method is used to get formatted nominal, (i.e. 5000 to Rp. 5.000).
     *
     * @param nominalParam String nominal
     * @return
     */
    public static String formatNominal(String nominalParam) {
        try {
            String result = "";
            String nominal = nominalParam;
            String a = nominal.replace(" ", "");

            String j = "";
            String k = a;
            if (a.endsWith(".00")) {
                j = a.substring(0, a.length() - 3);
                k = j;
            }
            if (a.endsWith(".0")) {
                j = a.substring(0, a.length() - 2);
                k = j;
            }

            String z = k.replace("-", "");
            String b = z.replace("Rp.", "");
            String c = b.replace("Rp", "");
            String d = c.replace(",00", "");

            nominal = d.replace(".", "");

            int count = 1;
            for (int i = nominal.length() - 1; i >= 0; i--) {
                result = nominal.charAt(i) + result;
                if (count % 3 == 0 && i != 0)
                    result = "." + result;
                count++;
            }
            return "Rp. " + result;
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("format number", String.valueOf(e));
            return nominalParam + "";
        }
    }

    /**
     * This method is used to get formatted nominal, (i.e. 5000 to Rp. 5.000).
     *
     * @param nominalParam String nominal
     * @return
     */
    public static String formatNominalWithoutRp(String nominalParam) {
        String result = "";
        String nominal = nominalParam;
        String a = nominal.replace(" ", "");

        String j = "";
        String k = a;
        if (a.endsWith(".00")) {
            j = a.substring(0, a.length() - 3);
            k = j;
        }
        if (a.endsWith(".0")) {
            j = a.substring(0, a.length() - 2);
            k = j;
        }

        String z = k.replace("-", "");
        String b = z.replace("Rp.", "");
        String c = b.replace("Rp", "");
        String d = c.replace(",00", "");

        nominal = d.replace(".", "");

        int count = 1;
        for (int i = nominal.length() - 1; i >= 0; i--) {
            result = nominal.charAt(i) + result;
            if (count % 3 == 0 && i != 0)
                result = "." + result;
            count++;
        }
        return result;
    }

    public static void hideKeyboard(Context context, View v) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showSettingsAlert(final Context context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static void saveCoordinate(CoordinatePresenter mCoordinatePresenter, String token, double latitude, double longitude, String action) {
        mCoordinatePresenter.coordinate(token, latitude, longitude, action);
    }

    public static void checkSizeImage(int typePhoto, Uri imageUri) {
        File fileImg = new File(imageUri.getPath());
        long fileLength = fileImg.length() / 1024;
        Log.i("size_file" + typePhoto, String.valueOf(fileLength));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(imageUri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        Log.i("size_imageHeight" + typePhoto, String.valueOf(imageHeight));
        Log.i("size_imageWidth" + typePhoto, String.valueOf(imageWidth));
    }


    public static int settingCamera(List<Camera.Size> sizes) {

        int indexSelected = -1;
        //Descending
        boolean isDesc = sizes.get(0).width > sizes.get(sizes.size() - 1).width;

        if (isDesc) {
            for (int i = 0; i < sizes.size(); i++) {
                if (sizes.get(i).width <= 1080 || sizes.get(i).height <= 1080) {
                    indexSelected = i;
                    break;
                }
            }
        } else {
            for (int i = sizes.size() - 1; i >= 0; i--) {
                if (sizes.get(i).width <= 1080 || sizes.get(i).height <= 1080) {
                    indexSelected = i;
                    break;
                }
            }
        }

        if (indexSelected == -1) {
            if (isDesc) {
                indexSelected = 0;
            } else {
                indexSelected = sizes.size() - 1;
            }
        }

        return indexSelected;
    }

}
