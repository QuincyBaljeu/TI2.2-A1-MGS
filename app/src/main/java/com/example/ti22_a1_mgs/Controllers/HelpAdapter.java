package com.example.ti22_a1_mgs.Controllers;

import android.content.Context;

import com.example.ti22_a1_mgs.Entities.HelpData;
import com.example.ti22_a1_mgs.utils.SharedPreferenceManager;

import java.util.Locale;

public class HelpAdapter {

    private SharedPreferenceManager sharedPreferenceManager;

    public HelpAdapter(Context context) {
       sharedPreferenceManager = new SharedPreferenceManager(context);
    }

    public String requestHelpText(){
        String selectedLanguage = Locale.getDefault().getLanguage();
        switch (selectedLanguage) {
            case "nl":
                return HelpData.getHelpTextNederlands();
            case "en":
                return HelpData.getHelpTextEnglish();
            case "de":
                return HelpData.getHelpTextDeutsch();
            default:
                return HelpData.getHelpTextEnglish();
        }

    }


}
