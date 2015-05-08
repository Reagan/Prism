package prism.re.gan.prism;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;

import prism.re.gan.prism.internet.InternetConnection;

/**
 * Created by rmbitiru on 3/28/15.
 */
public class Utils {

    private static final String TAG = Utils.class.getName() ;

    public static void displayShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void displayLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isConnectedToInternet(Context context) {
        boolean connected = false;
        try {
            // String host = "http://www.google.com";
            String host = "http://172.29.52.50";
            InternetConnection connection = new InternetConnection(context, host);
            connection.connect() ;
            connected = connection.isConnected();
            connection.close();
            return connected;
        } catch (MalformedURLException ex) {
            Log.e(TAG, ex.toString()) ;
        } catch (IOException ex) {
            Log.e(TAG, ex.toString()) ;
        }
        return connected ;
    }

    public static int getApiVersion () {
        return Build.VERSION.SDK_INT ;
    }

    public static String getCodeName () {
        return Build.VERSION.CODENAME ;
    }
}
