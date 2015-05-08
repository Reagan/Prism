package prism.re.gan.prism;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.widget.Toast;

import prism.re.gan.prism.service.BeaconsService;

/**
 * Created by rmbitiru on 3/28/15.
 */
public class PrismApplication extends Application {

    public String appInstanceIdentifier ;
    private boolean scanningEnabled = false ;
    private final String SCANNING_ENABLED = "Beacons Scanning enabled" ;
    private final String SCANNING_DISABLED = "Beacons Scanning disabled" ;

    private Intent beaconServiceIntent;
    private BluetoothAdapter bluetoothAdapter ;

    private final String BEACONS_SERVICE_URL = "http://172.29.52.50:8111/vs1/beaconProducts" ;
    private final String BEACONS_CONTENT_IMAGES_URL = "http://172.29.52.50:8111/vs1/images" ;
    private final String REMOTE_BEACONS_SERVICE_URL = "http://172.29.52.50:8111/store-1234/beaconProducts" ;
    private final String SEARCH_URL = "http://172.29.52.50:8111/vs1/s?q=" ;

    public PrismApplication() {

    }

    public void onCreate () {
        super.onCreate();

        // init singleton applications
        // appInstanceIdentifier = generateIdentifier() ;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter() ;
    }

    public String getAppInstanceIdentifier () {
        return appInstanceIdentifier ;
    }

    /**
     * This method initiates beacon ranging
     */
    public void startBeaconRanging() {

    }

    public String getLocalOffersUrl() {
        return BEACONS_SERVICE_URL;
    }

    public String getRemoteOffersUrl() {
        return BEACONS_SERVICE_URL;
    }

    public String getSearchUrl() {
        return SEARCH_URL ;
    }

    public String getBeaconsContentImagesUrl() {
        return BEACONS_CONTENT_IMAGES_URL ;
    }

    public boolean isScanningEnabled() {
        return scanningEnabled ;
    }

    public void setScanningEnabled (boolean scanningEnabled) {
        this.scanningEnabled = scanningEnabled ;
    }

    public void toggleScanning () {
        setScanningEnabled(!scanningEnabled);
        if (isScanningEnabled())
            startScanning();
        else
            stopScanning();
    }

    public void startScanning() {
        if (beaconServiceIntent == null) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE) ;
                enableBlueTooth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                startActivity(enableBlueTooth) ;
            }

            beaconServiceIntent = new Intent(this, BeaconsService.class);
            this.startService(beaconServiceIntent);
        }
        Toast.makeText(this, SCANNING_ENABLED, Toast.LENGTH_SHORT).show();
    }

    public void stopScanning() {
        if (beaconServiceIntent != null) {
            // @TODO: ask user if they want to disable BT
            // bluetoothAdapter.disable() ;
            // @TODO: disable scan toggle button when BT is disabled from settings
            this.stopService(beaconServiceIntent);
            beaconServiceIntent = null;
        }
        Toast.makeText(this, SCANNING_DISABLED, Toast.LENGTH_SHORT).show();
    }

}
