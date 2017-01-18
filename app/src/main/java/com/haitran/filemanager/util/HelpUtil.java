package com.haitran.filemanager.util;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Hai Tran on 1/10/2017.
 */

public class HelpUtil {

    public static int getImageId(String nameImage, Context context) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(nameImage, "drawable", pkgName);
        return resID;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

}
