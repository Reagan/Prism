package prism.re.gan.prism.response;

import org.json.JSONException;

/**
 * Created by rmbitiru on 3/28/15.
 */
public interface Response {

    public int getCode()  throws JSONException;
    public String getStatusMessage ()  throws JSONException ;
    public String getServerId()  throws JSONException ;
    public String getServerName()  throws JSONException ;
    public String getJsonResponse()  throws JSONException ;
}
