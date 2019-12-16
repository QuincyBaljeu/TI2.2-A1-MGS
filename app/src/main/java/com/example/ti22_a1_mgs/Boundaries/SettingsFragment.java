package com.example.ti22_a1_mgs.Boundaries;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ti22_a1_mgs.Controllers.SettingsAdapter;
import com.example.ti22_a1_mgs.R;

import java.io.Console;

public class SettingsFragment extends PreferenceFragment {

    private SettingsAdapter settingsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        settingsAdapter = new SettingsAdapter();

        Preference languagePreference = findPreference("Language");
        languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("@/d", "Preference changed");
                return true;
            }
        });

        Preference satellitePreference = findPreference("Satellite");
        satellitePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean toggle = Boolean.valueOf(newValue.toString());
                settingsAdapter.setSatellite(toggle);
                Log.d("@/d", String.valueOf(settingsAdapter.getSettings().isSatelliteMode()));
                return true;
            }
        });

        Preference colorblindPreference = findPreference("Colorblind");
        colorblindPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean toggle = Boolean.valueOf(newValue.toString());
                settingsAdapter.setColorblind(toggle);
                Log.d("@/d", String.valueOf(settingsAdapter.getSettings().isColorblindMode()));
                return true;
            }
        });
    }

}
