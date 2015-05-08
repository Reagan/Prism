package prism.re.gan.prism.request;

import android.content.Context;

import org.json.JSONException;

/**
 * Created by rmbitiru on 3/28/15.
 */
public class LoginRequest extends AbstractRequest {

    private String username;
    private String password ;

    public LoginRequest (Context context, String username,
                         String encryptedPassword) throws  JSONException {
        super(context) ;
        setUsername (username) ;
        setPassword (getPassword()) ;
    }

    public String toJsonString() throws JSONException {
        append("username", username);
        append("password", password);
        return getJsonRequestObject().toString() ;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
