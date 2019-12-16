package com.example.ti22_a1_mgs.Controllers;

import android.content.Context;

import com.example.ti22_a1_mgs.Entities.HelpData;
import com.example.ti22_a1_mgs.util.SharedPreferenceManager;

public class HelpAdapter {

    private SharedPreferenceManager sharedPreferenceManager;

    public HelpAdapter(Context context) {
       sharedPreferenceManager = new SharedPreferenceManager(context);
    }

    public String requestHelpText(){
        String selectedLanguage = sharedPreferenceManager.loadStringPreference("LANGUAGE", "default");

        switch (selectedLanguage) {
            case "NEDERLANDS":
                return HelpData.getHelpTextNederlands();
            case "ENGLISH":
                return HelpData.getHelpTextEnglish();
            case "DEUTSCH":
                return HelpData.getHelpTextDeutsch();
                default:
                    return HelpData.getHelpTextEnglish();
        }

    }


}
