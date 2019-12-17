package com.example.ti22_a1_mgs.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    public enum Type {INT, STRING, BOOL}

    private static final String PREF_PATH = "MGSPrefs";
    private Context context;
    private SharedPreferences pm;

    public SharedPreferenceManager(Context context) {
        this.context = context;
        this.pm = context.getSharedPreferences(PREF_PATH, Context.MODE_PRIVATE);
    }

    public void savePreference(String key, Object val, Type typeEnum) {

        switch (typeEnum) {
            case INT:
                pm.edit().putInt(key, (int) val).apply();
                break;
            case STRING:
                pm.edit().putString(key, (String) val).apply();
                break;
            case BOOL:
                pm.edit().putBoolean(key, (boolean) val).apply();
                break;
        }
    }

    public int loadIntPreference(String key, int defaultValue){
        return pm.getInt(key,defaultValue);
    }

    public String loadStringPreference(String key, String defaultValue){
        return pm.getString(key,defaultValue);
    }

    public boolean loadBoolPreference(String key, boolean defaultValue){
        return pm.getBoolean(key,defaultValue);
    }

    public void removePreference(String key){
        pm.edit().remove(key).apply();
    }

    public void clearSharedPreferences(){
        pm.edit().clear().apply();
    }
}
