package prism.re.gan.prism.db.domain;

/**
 * Created by rmbitiru on 4/12/15.
 */
public class LocatedObjectModel {

    private int productId ;
    private int storeId ;
    private String beaconUUID;
    private String beaconMajor ;
    private String beaconMinor ;

    public LocatedObjectModel() {}

    public LocatedObjectModel(int productId, int storeId,
                              String beaconUUID, String beaconMajor,
                              String beaconMinor) {
        setProductId(productId);
        setStoreId(storeId);
        setBeaconUUID(beaconUUID);
        setBeaconMajor(beaconMajor);
        setBeaconMinor(beaconMinor);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getBeaconUUID() {
        return beaconUUID;
    }

    public void setBeaconUUID(String beaconUUID) {
        this.beaconUUID = beaconUUID;
    }

    public String getBeaconMajor() {
        return beaconMajor;
    }

    public void setBeaconMajor(String beaconMajor) {
        this.beaconMajor = beaconMajor;
    }

    public String getBeaconMinor() {
        return beaconMinor;
    }

    public void setBeaconMinor(String beaconMinor) {
        this.beaconMinor = beaconMinor;
    }
}
