package com.example.ti22_a1_mgs.Entities;



public class Settings {

    private String language;
    private boolean colorblindMode;
    private boolean satelliteMode;
    private boolean markerMode;

    public Settings(){
        this.language = "ENGLISH";
        this.colorblindMode = false;
        this.satelliteMode = false;
        this.markerMode = false;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
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

    public boolean isMarkerMode() {
        return markerMode;
    }

    public void setMarkerMode(boolean markerMode) {
        this.markerMode = markerMode;
    }
}
