package prism.re.gan.prism.internet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by rmbitiru on 3/28/15.
 */
public class InternetConnection {

    private String host ;
    private final String MALFORMED_URL = "Mallformed URL host" ;
    private boolean isConnected = false ;
    private Context context ;
    private HttpURLConnection connection ;
    private String responseString = "" ;
    private Bitmap responseBitMapImage;
    private PayLoad payLoad ;

    public InternetConnection (Context context) {
        this.context = context ;
    }

    public InternetConnection (Context context, String host)  throws MalformedURLException  {
        this.context = context ;
        setHost(host);
    }

    public void setHost (String host)  throws MalformedURLException {
    if (isValid(host))
            this.host = host ;
        else
            throw new MalformedURLException(MALFORMED_URL + " " + host) ;
    }

    public void setPayLoad (InternetConnection.PayLoad payLoad) {
        this.payLoad = payLoad ;
    }

    public void connect() throws MalformedURLException, IOException{
        if (!isConnected && isValid(host)) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                connection = (HttpURLConnection) (new URL(host).openConnection());
                connection.setRequestProperty("Connection", "Close");
                connection.setRequestProperty("User-Agent", "Android");
                connection.setConnectTimeout(1500);

                if (payLoad != null) {
                    if (payLoad.getContentType().equals("image/jpeg")) {
                        Bitmap bitmap
                                = BitmapFactory.decodeByteArray((byte[]) payLoad.getPayload(),
                                                                0,
                                                                (((byte[]) payLoad.getPayload()).length));

                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "image/jpeg");
                        connection.setRequestMethod("POST");
                        OutputStream outputStream = connection.getOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                        outputStream.close();
                    }
                } else {

                    connection.connect();

                    isConnected = (connection.getResponseCode() == 200);
                    String contentType = connection.getContentType() ;
                    if (connection.getContentType().equals("application/json; charset=UTF-8"))
                        processResponseString(connection);
                    else if (connection.getContentType().equals("image/jpeg"))
                        processResponseImage(connection);
                }
            }
        }
    }

    public String getResponseString () {
        return responseString ;
    }

    public Bitmap getResponseBitMapImage() {
        return responseBitMapImage;
    }

    public boolean isConnected () {
        return isConnected ;
    }

    public void close () {
        connection.disconnect();
    }

    public boolean isValid(String host) {
        return (host != null
                && !host.equals("")
                //&& host.startsWith("^http[s]*:\\/\\/[a-zA-Z0-9.]{0,}\\/[a-zA-Z0-9]{0,}")
        ) ;
    }

    private void processResponseString (HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
        String inputLine = "";
        while ((inputLine = in.readLine()) != null)
            responseString += inputLine ;
        in.close();
    }

    private void processResponseImage (HttpURLConnection connection) throws IOException {
        InputStream is = connection.getInputStream() ;
        BufferedInputStream bis = new BufferedInputStream(is, 8190) ;
        ByteArrayBuffer baf = new ByteArrayBuffer(50) ;
        int current = 0 ;
        while ((current = bis.read()) != -1) {
            baf.append((byte) current);
        }
        byte [] imageData = baf.toByteArray() ;
        responseBitMapImage = BitmapFactory.decodeByteArray(imageData, 0, imageData.length) ;
    }

   static public class PayLoad {

        String contentType ;
        Object payload ;

        public PayLoad (String contentType, Object payload) {
            this.contentType = contentType ;
            this.payload = payload ;
        }

        public String getContentType () {
            return contentType ;
        }

        public Object getPayload () {
            return payload ;
        }
    }
}
