package com.pantrypro.persistence.utils;
import android.content.Context;
import android.content.res.AssetManager;

import com.pantrypro.application.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DBHelper class for managing database operations.
 */
public class DBHelper {

    /**
     * Copies the database file from assets to the device's data directory.
     * @param context The context of the application.
     */
    public static void copyDatabaseToDevice(Context context) {
        final String DB_PATH = "db";

        String[] assetNames;
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = context.getAssets();

        try {
            // List all assets in the database path
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }
            // Copy assets to the data directory
            copyAssetsToDirectory(context, assetNames, dataDirectory);

            // Set the database path in the Main class
            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            System.out.println("Unable to access application data: " + ioe.getMessage());
        }

    }

    /**
     * Copies assets to the specified directory.
     * @param context The context of the application.
     * @param assets The list of assets to be copied.
     * @param directory The destination directory.
     * @throws IOException If an I/O error occurs.
     */
    private static void copyAssetsToDirectory(Context context, String[] assets, File directory) throws IOException {
        AssetManager assetManager = context.getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}