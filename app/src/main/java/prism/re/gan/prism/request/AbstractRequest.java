package prism.re.gan.prism.request;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import prism.re.gan.prism.PrismApplication;
import prism.re.gan.prism.Utils;

/**
 * Created by rmbitiru on 3/28/15.
 */
public abstract class AbstractRequest implements Request {

    protected int code = -1 ;
    private JSONObject jsonRequestObject ;
    private Context context ;
    private boolean superToJSONStringCalled = false ;

    public AbstractRequest (Context context) throws  JSONException {
        jsonRequestObject = new JSONObject() ;
        this.context = context ;
        prependJSONObject();
    }

    public int getCode () {
        return code ;
    }

    protected JSONObject getJsonRequestObject () {
        return jsonRequestObject ;
    }

    protected void append (String key, Object value) throws JSONException {
        jsonRequestObject.put(key, value) ;
    }

    protected void prependJSONObject() throws JSONException {
        String appIdentifier
                = ((PrismApplication) context.getApplicationContext())
                .getAppInstanceIdentifier() ;
        append("appid", appIdentifier) ;
        append("platform", Utils.getApiVersion()) ;
        append("codename", Utils.getCodeName()) ;
        append("timestamp", Calendar.getInstance()) ;
    }
}
