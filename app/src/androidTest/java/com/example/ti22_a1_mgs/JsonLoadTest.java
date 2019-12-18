package com.example.ti22_a1_mgs;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JsonLoadTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //Setup

        String json = JsonUtil.loadJSONFromAsset(appContext.getApplicationContext());
        BlindWallsBreda blindWallsBreda = BlindWallsBreda.createFromJson(json);

        //Assert
        assert blindWallsBreda != null  &&
                blindWallsBreda.getAllWalls().get(0).getAddress().equals("Stallingstraat");

        //After


    }
}
