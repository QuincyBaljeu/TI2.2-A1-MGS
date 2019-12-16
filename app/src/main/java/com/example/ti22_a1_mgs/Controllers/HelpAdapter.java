package com.example.ti22_a1_mgs.Controllers;

import com.example.ti22_a1_mgs.Entities.HelpData;

public class HelpAdapter {

    public String getHelpTextNederlands(){
        return HelpData.getHelpTextNederlands();
    }

    public String getHelpTextEnglish(){
        return HelpData.getHelpTextEnglish();
    }

    public String getHelpTextDeutsch(){
        return HelpData.getHelpTextDeutsch();
    }
}
