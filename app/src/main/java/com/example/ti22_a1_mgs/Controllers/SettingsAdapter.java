package com.example.ti22_a1_mgs.Controllers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.ti22_a1_mgs.Entities.Settings;
import com.example.ti22_a1_mgs.utils.SharedPreferenceManager;

import java.util.Locale;

public class SettingsAdapter {
    private Settings settings;
    private SharedPreferenceManager sharedPreferenceManager;
    private Context context;

    public SettingsAdapter(Context context){
        this.context = context;
        settings = new Settings();
        sharedPreferenceManager = new SharedPreferenceManager(context);
    }

//    public void setLanguage(String language){
//        this.settings.setLanguage(language);
//        sharedPreferenceManager.savePreference("LANGUAGE", language, SharedPreferenceManager.Type.STRING);
//    }

    public void setSatellite(boolean toggle){
        this.settings.setSatelliteMode(toggle);
        sharedPreferenceManager.savePreference("SATELLITEMODE", toggle, SharedPreferenceManager.Type.BOOL);
    }

    public void setColorblind(boolean toggle){
        this.settings.setColorblindMode(toggle);
        sharedPreferenceManager.savePreference("COLORBLINDMODE", toggle, SharedPreferenceManager.Type.BOOL);
    }

    public void setAllWaypointsVisible(boolean toggle){
        this.settings.setMarkerMode(toggle);
        sharedPreferenceManager.savePreference("WAYPOINTMODE", toggle, SharedPreferenceManager.Type.BOOL);
    }

    public Settings getSettings(){
        return this.settings;
    }

}
