package com.example.ti22_a1_mgs;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ti22_a1_mgs.Controllers.SettingsAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SettingsTest {
    @Test
    public void setColorBlindTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //create adapter
        SettingsAdapter settingsAdapter = new SettingsAdapter(appContext);

        //setColorBlind
        settingsAdapter.setColorblind(true);

        //test
        assertEquals(true, settingsAdapter.getSettings().isColorblindMode());
    }

    @Test
    public void setSatelliteTest(){
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //create adapter
        SettingsAdapter settingsAdapter = new SettingsAdapter(appContext);

        //setColorBlind
        settingsAdapter.setSatellite(true);

        //test
        assertEquals(true, settingsAdapter.getSettings().isSatelliteMode());
    }

    @Test
    public void sharedPreferenceColorBlindTest(){
        /** IMPORTANT. RUN THIS TEST AFTER UNIT TEST 1 */

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //create adapter
        SettingsAdapter settingsAdapter = new SettingsAdapter(appContext);

        //test
        assertEquals(true, settingsAdapter.getSettings().isSatelliteMode());


    }


}
