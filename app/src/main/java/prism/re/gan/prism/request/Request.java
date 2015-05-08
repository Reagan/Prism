package prism.re.gan.prism.request;

import org.json.JSONException;

/**
 * Created by rmbitiru on 3/28/15.
 */
public interface Request {
    /**
     * Will specify the code for the request
     * @return
     */
    public int getCode() ;

    /**
     * Will format the request to a JSON string
     * @return
     */
    public String toJsonString() throws JSONException;
}
