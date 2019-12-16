package com.example.ti22_a1_mgs.Entities;

import com.example.ti22_a1_mgs.util.Language;

public class Settings {

    private Language language;
    private boolean colorblindMode;
    private boolean satelliteMode;

    public Settings(){
        this.language = Language.ENGLISH;
        this.colorblindMode = false;
        this.satelliteMode = false;
    }


    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public boolean isColorblindMode() {
        return colorblindMode;
    }

    public void setColorblindMode(boolean colorblindMode) {
        this.colorblindMode = colorblindMode;
    }

    public boolean isSatelliteMode() {
        return satelliteMode;
    }

    public void setSatelliteMode(boolean satelliteMode) {
        this.satelliteMode = satelliteMode;
    }
}
