package com.rakuishi.postalcode.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import timber.log.Timber;

public class UnzipDbUtil {

    public static void unzipIfNecessary(Context context, String zipName, String toName) throws IOException, SecurityException {
        if (context.getDatabasePath(toName).exists()) {
            Timber.d("Database is already exist.");
        } else {
            copyAndUnzipDbFromAsset(context, zipName, toName);
        }
    }

    public static void deleteDbFromDbPath(Context context, String name) throws SecurityException {
        File file = context.getDatabasePath(name);
        if (file.exists()) {
            file.delete();
            Timber.d("Database is deleted.");
        } else {
            Timber.d("Database is not exist.");
        }
    }

    private static void copyAndUnzipDbFromAsset(Context context, String zipName, String toName) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(zipName, AssetManager.ACCESS_STREAMING);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        File toPath = context.getDatabasePath(toName);

        if (zipEntry != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(toPath, false);
            byte[] buffer = new byte[1024];
            int size;

            while ((size = zipInputStream.read(buffer, 0, buffer.length)) > -1) {
                fileOutputStream.write(buffer, 0, size);
            }
            fileOutputStream.close();
            zipInputStream.closeEntry();;
        }
        zipInputStream.close();

        Timber.d("Database is unzipped from assets.");
    }
}
