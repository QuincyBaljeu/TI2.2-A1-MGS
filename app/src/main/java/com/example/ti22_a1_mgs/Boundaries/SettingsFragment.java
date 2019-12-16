package com.example.ti22_a1_mgs.Boundaries;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ti22_a1_mgs.Controllers.SettingsAdapter;
import com.example.ti22_a1_mgs.R;
import com.example.ti22_a1_mgs.util.SharedPreferenceManager;

import java.io.Console;

public class SettingsFragment extends PreferenceFragment {

    private SettingsAdapter settingsAdapter;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        settingsAdapter = new SettingsAdapter(this.getActivity().getBaseContext());
        sharedPreferenceManager = new SharedPreferenceManager(this.getActivity().getBaseContext());

        final ListPreference languagePreference = (ListPreference) findPreference("Language");
        languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String selectedLanguage = (String) newValue;
                settingsAdapter.setLanguage(selectedLanguage);
                Log.d("@/class", settingsAdapter.getSettings().getLanguage());
                Log.d("@/SharedPreferece", sharedPreferenceManager.loadStringPreference("LANGUAGE", "default"));
                return true;
            }
        });

        Preference satellitePreference = findPreference("Satellite");
        satellitePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean toggle = Boolean.valueOf(newValue.toString());
                settingsAdapter.setSatellite(toggle);
                Log.d("@/Class", String.valueOf(settingsAdapter.getSettings().isSatelliteMode()));
                Log.d("@/SharedPreference", String.valueOf(sharedPreferenceManager.loadBoolPreference("SATELLITEMODE", true)));

                return true;
            }
        });

        Preference colorblindPreference = findPreference("Colorblind");
        colorblindPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean toggle = Boolean.valueOf(newValue.toString());
                settingsAdapter.setColorblind(toggle);
                Log.d("@/Class", String.valueOf(settingsAdapter.getSettings().isColorblindMode()));
                Log.d("@/SharedPreference", String.valueOf(sharedPreferenceManager.loadBoolPreference("COLORBLINDMODE", true)));

                return true;
            }
        });
    }

}
