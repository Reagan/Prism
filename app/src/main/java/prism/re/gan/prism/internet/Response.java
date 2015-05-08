package prism.re.gan.prism.internet;

import java.util.ArrayList;

import prism.re.gan.prism.ui.offers.OfferItem;

/**
 * Created by rmbitiru on 4/28/15.
 */
public class Response {

    private String serverName ;
    private String serverId ;
    private String status ;
    private String code ;
    private OfferItem[] payload ;

    public Response () {}

    public Response (String serverName, String serverId,
                     String status, String code,
                     OfferItem[] payload) {
        setServerName(serverName);
        setServerId(serverId);
        setStatus(status);
        setCode(code);
        setPayload(payload);
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OfferItem[] getPayload() {
        return payload;
    }

    public void setPayload(OfferItem [] payload) {
        this.payload = payload;
    }
}
