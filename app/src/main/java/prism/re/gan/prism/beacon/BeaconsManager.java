package prism.re.gan.prism.beacon;

/**
 * Created by rmbitiru on 2/24/15.
 */

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import prism.re.gan.prism.OffersActivity;
import prism.re.gan.prism.R;

public class BeaconsManager {

    private static final int NOTIFICATION_ID = 123;
    private static BeaconManager beaconManager;
    private static NotificationManager notificationManager;
    public static final String EXTRAS_BEACON = "extrasBeacon";
    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final Region ESTIMOTE_REGION = new Region("regionId",
            ESTIMOTE_PROXIMITY_UUID, null, null);
    private static final String TAG = BeaconsManager.class.getSimpleName();

    private static final String FAR = "FAR";
    private static final String NEAR = "NEAR";
    private static final String IMMEDIATE = "IMMEDIATE";
    private static final String UNKNOWN = "UNKNOWN";

    private static Context context;
    private static Beacon currentBeacon = null ;

    public static void create(NotificationManager notificationMngr,
                              Context context,
                              final Intent i) {
        try {
            notificationManager = notificationMngr;
            BeaconsManager.context = context;

            beaconManager = new BeaconManager(context);
            beaconManager.setRangingListener(new BeaconManager.RangingListener() {
                @Override
                public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                    Log.i(TAG, "Found " +  beacons.size() + " beacons") ;
                    for (Beacon beacon : beacons) {
                        if (computeProximity(beacon) == IMMEDIATE || computeProximity(beacon) == NEAR) {
                            if (beacon != currentBeacon) {
                                currentBeacon = beacon ;
                                postNotificationIntent("German Butcher",
                                        "Offer found!", beacon);
                            }
                        }
                    }
                }
            });

            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    try {
                        beaconManager.startRanging(ESTIMOTE_REGION);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString()) ;
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public static void postNotificationIntent(String title, String msg, Beacon beacon) {

        Intent offersIntent = null ;

        if (null != beacon) {
            offersIntent = new Intent(context, OffersActivity.class);
            offersIntent.putExtra("beacons", beacon) ;
            offersIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP) ;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                offersIntent,
                PendingIntent.FLAG_UPDATE_CURRENT) ;

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setLights(Color.YELLOW, 1000, 1000)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public static void stop() {
        try {
            beaconManager.stopRanging(ESTIMOTE_REGION);
            beaconManager.disconnect();
        } catch (Exception e) {
        }
    }

    public static Beacon getCurrentBeacon() {
        return currentBeacon ;
    }

    private static String computeProximity(Beacon beacon) {
        Utils.Proximity proximity = Utils.computeProximity(beacon);
        if (proximity == Utils.Proximity.FAR)
            return FAR;
        else if (proximity == Utils.Proximity.NEAR)
            return NEAR;
        else if (proximity == Utils.Proximity.IMMEDIATE)
            return IMMEDIATE;
        else
            return UNKNOWN;
    }

}