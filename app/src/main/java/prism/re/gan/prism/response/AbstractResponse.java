package prism.re.gan.prism.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rmbitiru on 3/28/15.
 */
public abstract class AbstractResponse implements Response {

    protected JSONObject responseJSONObject ;
    protected String jsonResponseString ;

    private final String SERVER_ID_KEY = "serverid" ;
    private final String SERVER_NAME_KEY = "serverid" ;
    private final String STATUS_CODE_KEY = "code" ;
    private final String STATUS_MESSAGE_KEY = "status" ;

    public AbstractResponse (String jsonResponseString) throws JSONException{
        this.jsonResponseString = jsonResponseString ;
        responseJSONObject = new JSONObject(jsonResponseString) ;
    }

    public int getCode()  throws JSONException {
        return responseJSONObject.getInt(STATUS_CODE_KEY) ;
    }

    public String getStatusMessage()  throws JSONException {
        return responseJSONObject.getString(STATUS_MESSAGE_KEY) ;
    }

    public String getServerId() throws JSONException {
       return responseJSONObject.getString(SERVER_ID_KEY) ;
    }

    public String getServerName() throws JSONException {
        return responseJSONObject.getString(SERVER_NAME_KEY) ;
    }

    public String getJsonResponse()  throws JSONException {
        return jsonResponseString ;
    }

    protected String get (String key) throws JSONException {
        return (String) responseJSONObject.get(key) ;
    }
}
