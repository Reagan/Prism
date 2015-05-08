package prism.re.gan.prism.ui.offers;

import android.os.Parcel;
import android.os.Parcelable;

import com.estimote.sdk.Beacon;

/**
 * Created by rmbitiru on 2/23/15.
 */
public class OfferItem implements Parcelable {

    private int productId ;
    private int categoryId;
    private int subCategoryId ;
    private String itemName ;
    private String itemImageUrl;
    private double price ;
    private boolean inStock ;
    private double discount;
    private String category ;

    public OfferItem() {}

    public OfferItem (int productId, int categoryId, int subCategoryId, String itemName,
                      String itemImageUrl, double price, boolean inStock, double discount, String category) {
        setProductId(productId);
        setCategoryId(categoryId);
        setSubCategoryId(subCategoryId);
        setItemName(itemName);
        setItemImageUrl(itemImageUrl);
        setPrice(price);
        setInStock(inStock);
        setDiscount(discount);
        setCategory(category);
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(productId);
        parcel.writeInt(categoryId);
        parcel.writeInt(subCategoryId);
        parcel.writeString(itemName);
        parcel.writeString(itemImageUrl);
        parcel.writeDouble(price);
        parcel.writeByte((byte) (inStock ? 1 : 0));
        parcel.writeDouble(discount);
        parcel.writeString(category);
    }

    public static final Parcelable.Creator<OfferItem> CREATOR = new Creator<OfferItem>() {
        public OfferItem createFromParcel(Parcel source) {

            OfferItem offerItem = new OfferItem() ;
            offerItem.productId = source.readInt() ;
            offerItem.categoryId = source.readInt() ;
            offerItem.subCategoryId = source.readInt() ;
            offerItem.itemName = source.readString() ;
            offerItem.itemImageUrl = source.readString() ;
            offerItem.price = source.readDouble() ;
            offerItem.inStock = source.readByte() != 0 ;
            offerItem.discount = source.readDouble() ;
            offerItem.category = source.readString() ;
            return offerItem ;
        }

        public OfferItem[] newArray(int size) {
            return new OfferItem[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
