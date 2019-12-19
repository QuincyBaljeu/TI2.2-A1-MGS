package com.example.ti22_a1_mgs;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ti22_a1_mgs.Controllers.RouteAdapter;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RouteAdapterTest {

    private static final int AMOUNT_OF_POI = 10;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //Setup
        List<PointOfInterest> pointOfInterestList = generatePoiList();
        RecyclerView recyclerView = new RecyclerView(appContext);

        //1. create adapter
        RouteAdapter routeAdapter = null;
        try {
            routeAdapter = new RouteAdapter(pointOfInterestList, appContext);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2. attach to recyclerView
        recyclerView.setAdapter(routeAdapter);

        //3. get dataSet
        assert routeAdapter != null; //check if route adapter isn't null
        List<PointOfInterest> itemSet = routeAdapter.getDataSet();

        assert itemSet.size() == pointOfInterestList.size(); //check if it's still the same list
        assert recyclerView.getAdapter() != null; //checks if adapter is attached
    }

    private List<PointOfInterest> generatePoiList() {
        List<PointOfInterest> generatedPointOfInterestList = new ArrayList<>();

        for (int i = 0; i < AMOUNT_OF_POI; i++) {
            generatedPointOfInterestList.add(
                    new PointOfInterest(
                            0,
                            "address", "nlDesc", "enDesc", null
                    )
            );
        }
        return generatedPointOfInterestList;
    }
}
