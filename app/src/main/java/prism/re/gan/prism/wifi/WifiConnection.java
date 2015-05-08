package prism.re.gan.prism.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import prism.re.gan.prism.PrismApplication;

/**
 * Created by rmbitiru on 4/9/15.
 */
public class WifiConnection {

    private Context context;
    private String host ;
    private final String networkSSID = "CMUR-TELCOM";
    private final String networkPass = "rbabc@1234";
    private boolean isConnected = false;
    private HttpURLConnection connection;
    private String error = "" ;
    private final String ERROR_UNABLE__TO_CONNECT = "Error. Unable to connect" ;
    private Object response ;

    public WifiConnection(Context context, String host) {
        this.context = context;
        this.host = host ;
    }

    public boolean connect() throws IOException{
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null && networkInfo.isConnected()) {
                connection = (HttpURLConnection) (new URL(host).openConnection());
                connection.setRequestProperty("Connection", "Close");
                connection.setRequestProperty("User-Agent", "Android");
                connection.setConnectTimeout(1500);

                connection.connect();

                isConnected = (connection.getResponseCode() == 200);

                if (connection.getResponseCode() != 200) {

                    String remoteOffersURL = ((PrismApplication) context.getApplicationContext()).getRemoteOffersUrl() ;
                    connection = (HttpURLConnection) (new URL(remoteOffersURL).openConnection());
                    connection.setRequestProperty("Connection", "Close");
                    connection.setRequestProperty("User-Agent", "Android");
                    connection.setConnectTimeout(1500) ;

                    connection.connect();

                    if (connection.getResponseCode() != 200) {
                        error = ERROR_UNABLE__TO_CONNECT ;
                        return false ;
                    }

                    if (connection.getContentType().equals("application/json")) {
                        response = connection.getResponseMessage() ;
                    }
                }
            }
        }
        return false;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void enableWifi() {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + networkSSID + "\"";
        wifiConfiguration.preSharedKey = "\"" + networkPass + "\"";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(wifiConfiguration);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }

    public Object getResponse () {
        return response ;
    }
}
