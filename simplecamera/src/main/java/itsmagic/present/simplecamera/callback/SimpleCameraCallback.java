package itsmagic.present.simplecamera.callback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvin Rusli on 9/23/2016.
 */
public abstract class SimpleCameraCallback implements Camera.PictureCallback {

    /** The context */
    private Context mContext;

    /** Determines if this class should print log messages */
    private boolean isLogPrinted = false;

    /** Determines if the image should be mirrored */
    private boolean isImageMirrored = false;

    /** The constructor of this class */
    public SimpleCameraCallback(Context context) {
        mContext = context;
    }

    /** Obtain the context */
    public final Context getContext() {
        return mContext;
    }

    /** Enable log messages */
    public void enableLogging() {
        isLogPrinted = true;
    }

    /** Disable log messages */
    public void disableLogging() {
        isLogPrinted = false;
    }

    /** Enables image mirroring when saving */
    public void enableMirroring() {
        isImageMirrored = true;
    }

    /** Disables image mirroring when saving */
    public void disableMirroring() {
        isImageMirrored = false;
    }

    /**
     * Prints a {@link Log} message.
     * Log messages printed via this method will only show up if specified.
     * @param logType The specified log type, may be {@link Log#DEBUG}, {@link Log#INFO}, and other log types
     * @param tag The log tag to print
     * @param message The log message to print
     */
    protected void log(int logType, String tag, String message) {
        if (isLogPrinted) Log.println(logType, tag, message);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        boolean isEdited = false;
        byte[] newBytes = data;
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        // Rotate the image if required
        int orientation = getOrientation(data);
        if (orientation > 0) {
            imageBitmap = rotateBitmap(imageBitmap, orientation);

            log(Log.INFO, getClass().getSimpleName(), "Image rotated (" + orientation + ")");
            isEdited = true;
        }

        // Mirror the image if required
        if (isImageMirrored) {
            // Use matrix to reverse image data
            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);

            // Generate the mirrored bitmap
            imageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);

            log(Log.INFO, getClass().getSimpleName(), "Image mirrored");
            isEdited = true;
        }

        if (isEdited && imageBitmap != null) {
            // Set the bytes with the new bitmap data if the image is edited
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            newBytes = stream.toByteArray();
        }

        onPictureTakenSuccessfully(newBytes);
    }

    // Returns the degrees in clockwise. Values are 0, 90, 180, or 270.
    // Obtained from http://stackoverflow.com/questions/5468098/reading-exif-data-from-byte-array-in-android
    private int getOrientation(byte[] jpeg) {
        if (jpeg == null) {
            return 0;
        }

        int offset = 0;
        int length = 0;

        // ISO/IEC 10918-1:1993(E)
        while (offset + 3 < jpeg.length && (jpeg[offset++] & 0xFF) == 0xFF) {
            int marker = jpeg[offset] & 0xFF;

            // Check if the marker is a padding.
            if (marker == 0xFF) {
                continue;
            }
            offset++;

            // Check if the marker is SOI or TEM.
            if (marker == 0xD8 || marker == 0x01) {
                continue;
            }
            // Check if the marker is EOI or SOS.
            if (marker == 0xD9 || marker == 0xDA) {
                break;
            }

            // Get the length and check if it is reasonable.
            length = pack(jpeg, offset, 2, false);
            if (length < 2 || offset + length > jpeg.length) {
                log(Log.ERROR, getClass().getSimpleName(), "Invalid length");
                return 0;
            }

            // Break if the marker is EXIF in APP1.
            if (marker == 0xE1 && length >= 8 &&
                    pack(jpeg, offset + 2, 4, false) == 0x45786966 &&
                    pack(jpeg, offset + 6, 2, false) == 0) {
                offset += 8;
                length -= 8;
                break;
            }

            // Skip other markers.
            offset += length;
            length = 0;
        }

        // JEITA CP-3451 Exif Version 2.2
        if (length > 8) {
            // Identify the byte order.
            int tag = pack(jpeg, offset, 4, false);
            if (tag != 0x49492A00 && tag != 0x4D4D002A) {
                log(Log.ERROR, getClass().getSimpleName(), "Invalid byte order");
                return 0;
            }
            boolean littleEndian = (tag == 0x49492A00);

            // Get the offset and check if it is reasonable.
            int count = pack(jpeg, offset + 4, 4, littleEndian) + 2;
            if (count < 10 || count > length) {
                log(Log.ERROR, getClass().getSimpleName(), "Invalid offset");
                return 0;
            }
            offset += count;
            length -= count;

            // Get the count and go through all the elements.
            count = pack(jpeg, offset - 2, 2, littleEndian);
            while (count-- > 0 && length >= 12) {
                // Get the tag and check if it is orientation.
                tag = pack(jpeg, offset, 2, littleEndian);
                if (tag == 0x0112) {
                    // We do not really care about type and count, do we?
                    int orientation = pack(jpeg, offset + 8, 2, littleEndian);
                    switch (orientation) {
                        case 1:
                            return 0;
                        case 3:
                            return 180;
                        case 6:
                            return 90;
                        case 8:
                            return 270;
                    }
                    log(Log.INFO, getClass().getSimpleName(), "Unsupported orientation");
                    return 0;
                }
                offset += 12;
                length -= 12;
            }
        }

        log(Log.INFO, getClass().getSimpleName(), "Orientation not found");
        return 0;
    }

    private int pack(byte[] bytes, int offset, int length,
                            boolean littleEndian) {
        int step = 1;
        if (littleEndian) {
            offset += length - 1;
            step = -1;
        }

        int value = 0;
        while (length-- > 0) {
            value = (value << 8) | (bytes[offset] & 0xFF);
            offset += step;
        }
        return value;
    }

    /**
     * Rotates the image to be edited by the specified degree.
     * Only supported angles are:
     * * 90  / -90
     * * 180 / -180
     * * 270 / -270
     * @param rotationAngle the rotation angle
     */
    public final Bitmap rotateBitmap(final Bitmap bitmap, final int rotationAngle) {
        // Generate a new rotated bitmap, and set it as the ImageView's bitmap
        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAngle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /** Called after image has been obtained */
    protected abstract void onPictureTakenSuccessfully(byte[] capturedImage);
}
