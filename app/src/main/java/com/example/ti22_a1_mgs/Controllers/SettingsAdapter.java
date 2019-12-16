package com.example.ti22_a1_mgs.Controllers;

import com.example.ti22_a1_mgs.Entities.Settings;
import com.example.ti22_a1_mgs.util.Language;

public class SettingsAdapter {
    private Settings settings;

    public SettingsAdapter(){
        settings = new Settings();
    }

    public void setLanguage(Language language){
        this.settings.setLanguage(language);
    }

    public void setSatellite(boolean toggle){
        this.settings.setSatelliteMode(toggle);
    }

    public void setColorblind(boolean toggle){
        this.settings.setColorblindMode(toggle);
    }

    public Settings getSettings(){
        return this.settings;
    }
}
