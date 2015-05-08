package prism.re.gan.prism.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Contacts;
import android.util.Log;

import prism.re.gan.prism.PrismApplication;
import prism.re.gan.prism.R;
import prism.re.gan.prism.service.BeaconsService;

/**
 * Created by rmbitiru on 2/24/15.
 */
public class BeaconsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);

            switch (state) {
                case BluetoothAdapter.STATE_TURNING_OFF:
                    PrismApplication app = (PrismApplication) context.getApplicationContext() ;
                    app.stopScanning();
                    break;

                case BluetoothAdapter.STATE_ON:
                    break;

                default:
            }
        }
    }
}
