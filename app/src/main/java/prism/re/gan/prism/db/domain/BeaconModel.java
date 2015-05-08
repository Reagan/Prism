package prism.re.gan.prism.db.domain;

/**
 * Created by rmbitiru on 4/12/15.
 */
public class BeaconModel {

    private int id ;
    private String beaconName;
    private String beaconUUID;
    private int beaconMajor;
    private int beaconMinor ;
    private String storeId ;
    private com.estimote.sdk.Beacon beacon ;


    public BeaconModel() {}

    public BeaconModel(int id, String beaconUUID, int beaconMajor,
                       int beaconMinor, String storeId) {
        setId(id);
        setBeaconName(getBeaconName());
        setBeaconUUID(beaconUUID);
        setBeaconMajor(beaconMajor);
        setBeaconMinor(beaconMinor);
        setStoreId(storeId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeaconUUID() {
        return beaconUUID;
    }

    public void setBeaconUUID(String beaconUUID) {
        this.beaconUUID = beaconUUID;
    }

    public int getBeaconMajor() {
        return beaconMajor;
    }

    public void setBeaconMajor(int beaconMajor) {
        this.beaconMajor = beaconMajor;
    }

    public int getBeaconMinor() {
        return beaconMinor;
    }

    public void setBeaconMinor(int beaconMinor) {
        this.beaconMinor = beaconMinor;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setBeacon (com.estimote.sdk.Beacon beacon) {
        this.beacon = beacon ;
    }

    public com.estimote.sdk.Beacon getBeacon () {
        return beacon ;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }
}
