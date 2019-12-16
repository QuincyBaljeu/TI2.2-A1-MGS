package com.example.ti22_a1_mgs.Database.blindwalls;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Paul de Mast on 06-Nov-17.
 * Edited by Sebastiaan Colijn on 16-Dec-19
 */

public class JsonUtil {
    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("walls.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}