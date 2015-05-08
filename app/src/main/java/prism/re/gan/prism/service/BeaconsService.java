package prism.re.gan.prism.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import prism.re.gan.prism.beacon.BeaconsManager;

/**
 * Created by rmbitiru on 2/24/15.
 */
public class BeaconsService extends Service {

    public IBinder onBind (Intent arg) {
        return null ;
    }

    public int onStartCommand (Intent intent, int flags, int startId) {

        try {
            BeaconsManager
                    .create((NotificationManager)
                            this.getSystemService(Context.NOTIFICATION_SERVICE),
                                                    this, intent) ;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return START_STICKY ;
    }

    public void onDestroy () {
        super.onDestroy();
        BeaconsManager.stop();
    }
}
