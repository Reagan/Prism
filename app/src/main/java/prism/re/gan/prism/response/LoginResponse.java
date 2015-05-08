package prism.re.gan.prism.response;

import org.json.JSONException;

/**
 * Created by rmbitiru on 3/28/15.
 */
public class LoginResponse extends AbstractResponse {

    private final String AUTH_CODE_KEY = "authcode" ;
    public static final String OK = "OK" ;

    public LoginResponse (String jsonString) throws JSONException {
        super(jsonString);
    }

    public String getAuthorizationCode () throws JSONException {
        return get(AUTH_CODE_KEY) ;
    }

}
