package com.example.abhijit.helloandroid;

import android.app.Application;
import android.content.Context;

import com.estimote.sdk.EstimoteSDK;

/**
 * Created by abhijit on 3/2/2017.
 */

public class MyApplication extends Application {
    private static boolean beaconNotificationsEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        EstimoteSDK.initialize(getApplicationContext(), "app_0s7hzhxc3k", "c1ba9940d6de456d072b823cd5667c3e");
    }

    public static void enableBeaconNotifications(Context context) {
        if (beaconNotificationsEnabled) { return; }
        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(context);
        beaconNotificationsManager.addNotification(new BeaconID("*************",4354,9195),
                "id:1:enter",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(new BeaconID("*************",38813,34937),
                "id:1:enter",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(new BeaconID("*************",62314,63730),
                "id:1:enter",
                "Goodbye, world.");
        beaconNotificationsManager.addNotification(new BeaconID("*************",44979,35401),
                "id:1:enter",
                "Goodbye, world.");
        beaconNotificationsManager.startMonitoring();

        beaconNotificationsEnabled = true;
    }


    public static boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }

}
