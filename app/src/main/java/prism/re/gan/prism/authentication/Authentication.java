package prism.re.gan.prism.authentication;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

import prism.re.gan.prism.FileUtils;
import prism.re.gan.prism.PrismApplication;
import prism.re.gan.prism.Security;
import prism.re.gan.prism.Utils;
import prism.re.gan.prism.internet.InternetConnection;
import prism.re.gan.prism.request.LoginRequest;
import prism.re.gan.prism.response.LoginResponse;

/**
 * Created by rmbitiru on 3/28/15.
 */

public class Authentication {

    private AuthenticationError  authenticationError ;
    private enum AuthenticationErrorCode {
        _404, // Invalid credentials
        _500
        // Server error
    } ;
    private final String AUTHENTICATION_URL = "http://localhost/authenticate" ;
    private final String TAG = Authentication.class.getName() ;
    private String authCodeFileName = "" ; // @TODO: Load file to be written from PrismApplication

    public Authentication () {}

    public boolean authenticate (Context context, String username, String password) {
        String encryptedPassword = Security.encrypt(password) ;
        String appIdentifier = ((PrismApplication) context.getApplicationContext()).getAppInstanceIdentifier();
        if (Utils.isConnectedToInternet(context))
            return authenticateRemote (context, username, encryptedPassword, appIdentifier) ;
        else
            return authenticateLocal (context, username, encryptedPassword, appIdentifier) ;
    }

    public AuthenticationError getError () {
        return authenticationError ;
    }

    // add app identifier to log in process
    public boolean authenticateRemote (Context context, String username,
                                       String encryptedPassword, String appIdenitifier) {
        boolean authenticatedRemotely = false;
        try {
            InternetConnection connection = new InternetConnection(context, AUTHENTICATION_URL);
            LoginRequest request = new LoginRequest(context, username, encryptedPassword) ;
            connection.setPayLoad(new InternetConnection.PayLoad("application/json", request.toString()));
            connection.connect();
            LoginResponse response =  new LoginResponse(connection.getResponseString()) ;
            authenticatedRemotely = (response.getCode() == 200 &&
                    response.getStatusMessage().equals(LoginResponse.OK)) ;
            copyAuthCodeToDisk(context, response.getAuthorizationCode(), authCodeFileName) ;
        } catch (MalformedURLException ex) {
            Log.e(TAG, ex.toString()) ;
        } catch (IOException ex) {
            Log.e(TAG, ex.toString()) ;
        } catch (JSONException ex) {
            Log.e(TAG, ex.toString()) ;
        }

        return authenticatedRemotely ;
    }

    // @TODO: add app identifier to log in process
    public boolean authenticateLocal (Context context, String username,
                                      String encryptedPassword, String appIdentifier) {
        if(fileExists(authCodeFileName)) {
            String authorizationCode = readFile(context, authCodeFileName) ;
            String generatedAuthCode = Security.generateAuthorizationCode (username, encryptedPassword) ;
            return authorizationCode.equals(generatedAuthCode) ;
        }
        return false ;
    }

    public boolean fileExists (String fileName) {
        return FileUtils.fileExists(fileName) ;
    }

    public String readFile (Context context, String fileName) {
        return FileUtils.readFile (context, fileName) ;
    }

    private void copyAuthCodeToDisk(Context context, String content, String fileName) {
        try {
            FileUtils.writeToFile(context, fileName, content);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    public class AuthenticationError {

        AuthenticationErrorCode code ;
        String errorMessage ;

        AuthenticationError (AuthenticationErrorCode code, String errorMessage) {
            this.code = code ;
            this.errorMessage = errorMessage ;
        }

        public AuthenticationErrorCode getErrorCode () {
            return code ;
        }

        public String getMessage () {
            return errorMessage ;
        }
    }
}
