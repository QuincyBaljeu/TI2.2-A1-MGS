package com.example.ti22_a1_mgs;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ti22_a1_mgs.Boundaries.MainActivity;
import com.example.ti22_a1_mgs.Database.Reposetory;
import com.example.ti22_a1_mgs.Database.blindwalls.BlindWallsBreda;
import com.example.ti22_a1_mgs.Database.blindwalls.JsonUtil;
import com.example.ti22_a1_mgs.Database.entities.Waypoint;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DatabaseEquivalenceTest   {
    //Todo: Change Database to use Context, not Application in main app
/*


    @Test
    public void useAppContext() throws InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //Setup
        Reposetory reposetory = new Reposetory());
        LiveData<List<Waypoint>> list1 = reposetory.getAllWaypoints();
        Waypoint newWaypoint = new Waypoint(500, 30, 30, null);
        reposetory.insert(newWaypoint);
        wait(1000);
        LiveData<List<Waypoint>> list2 = reposetory.getAllWaypoints();
        reposetory.delete(newWaypoint);
        wait(1000);
        LiveData<List<Waypoint>> list3 = reposetory.getAllWaypoints();

        //Assert
        assert list1.equals(list3) &&
                list2.getValue().contains(newWaypoint);

        //After
*/



    }
}
