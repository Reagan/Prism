package prism.re.gan.prism.db.domain;

/**
 * Created by rmbitiru on 4/11/15.
 */
public class OfferModel {

    private int id ;
    private int productId;
    private String name ;
    private double price ;
    private String imageUrl ;
    private boolean inStock ;
    private double discount ;
    private int beaconId;

    public OfferModel() {}

    public OfferModel(int productId, String name, double price,
                      String imageUrl, boolean inStock, double discount,
                      int beaconId) {
        setProductId(productId);
        setName(name);
        setPrice(price);
        setImageUrl(imageUrl);
        setInStock(inStock);
        setDiscount(discount);
        setBeaconId(beaconId);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
