package prism.re.gan.prism.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import prism.re.gan.prism.db.domain.OfferModel;

/**
 * Created by rmbitiru on 4/11/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper" ;
    private static final int databaseVersion = 1 ;
    private static final String DATABASE_NAME = "prism" ;

    private static final String TABLE_OFFERS = "Offers" ;
    private static final String TABLE_COMMENTS = "comments" ;
    private static final String TABLE_LOCATED_OBJECTS = "located_objects"  ;
    private static final String TABLE_BEACONS = "beacons" ;
    private static final String TABLE_SCANS = "Scans" ;
    private static final String TABLE_PRODUCTS = "products" ;

    // Columns for products table
    private static final String KEY_OFFERS_ID = "id" ;
    private static final String KEY_OFFERS_PRODUCT_ID = "product_id" ;
    private static final String KEY_OFFERS_NAME = "name" ;
    private static final String KEY_OFFERS_PRICE = "price" ;
    private static final String KEY_OFFERS_IMAGE_URL = "image_url" ;
    private static final String KEY_OFFERS_IN_STOCK_STATUS = "in_stock_status" ;
    private static final String KEY_OFFERS_DISCOUNT = "discount" ;
    private static final String KEY_OFFERS_BEACON_ID = "beacon_id" ;

    // Columns for comments table
    private static final String KEY_COMMENTS_ID = "id" ;
    private static final String KEY_COMMENTS_PRODUCT_ID = "product_id" ;
    private static final String KEY_COMMENTS_COMMENT_ID = "comment_id" ;
    private static final String KEY_COMMENTS_AUTHOR_ID = "author_id" ;
    private static final String KEY_COMMENTS_PUBLISH_TIMESTAMP = "publish_timestamp" ;
    private static final String KEY_COMMENTS_PARENT_COMMENT_ID = "comment_id" ;
    private static final String KEY_COMMENTS_COMMENT = "comment" ;

    // Columns for LocatedObjects Table
    private static final String KEY_LOCATEDOBJECTS_ID = "id" ;
    private static final String KEY_LOCATEDOBJECTS_PRODUCT_ID = "product_id" ;
    private static final String KEY_LOCATEDOBJECTS_STORE_ID = "store_id" ;
    private static final String KEY_LOCATEDOBJECTS_BEACON_UUID = "beacon_uuid" ;
    private static final String KEY_LOCATEDOBJECTS_BEACON_MAJOR = "beacon_major" ;
    private static final String KEY_LOCATEDOBJECTS_BEACON_MINOR = "beacon_minor" ;

    // Columns for Beacons Table
    private static final String KEY_BEACONS_ID = "id" ;
    private static final String KEY_BEACONS_BEACON_UUID = "beacon_uuid" ;
    private static final String KEY_BEACONS_BEACON_MAJOR = "beacon_major" ;
    private static final String KEY_BEACONS_BEACON_MINOR = "beacon_minor" ;
    private static final String KEY_BEACONS_STORE_ID = "beacon_store_id" ;
    private static final String KEY_BEACONS_NAME = "beacon_name" ;

    // Columns for scans
    private static final String KEY_SCANS_ID = "id" ;
    private static final String KEY_SCANS_DATE = "scans_date" ;
    private static final String KEY_SCANS_NO = "scans_no" ;
    private static final String KEY_SCANS_BEACON_ID = "beacon_id" ;

    // Column for products
    private static final String KEY_PRODUCTS_ID = "id" ;
    private static final String KEY_PRODUCTS_CATEGORY_ID = "category_id" ;
    private static final String KEY_PRODUCTS_SUB_CATEGORY_ID = "sub_category_id" ;
    private static final String KEY_PRODUCTS_PRODUCT_ID = "product_id" ;
    private static final String KEY_PRODUCTS_NAME = "name" ;
    private static final String KEY_PRODUCTS_PRICE = "price" ;
    private static final String KEY_PRODUCTS_IMAGE_URL = "image_url" ;
    private static final String KEY_PRODUCTS_IN_STOCK_STATUS = "in_stock_status" ;
    private static final String KEY_PRODUCTS_DISCOUNT = "discount" ;

    // Create statement for offers table
    private final String CREATE_OFFERS_TABLE_QUERY = "CREATE TABLE " + TABLE_OFFERS
            + "(" + KEY_OFFERS_ID + " INTEGER PRIMARY KEY, "
            + KEY_OFFERS_PRODUCT_ID + " INTEGER, "
            + KEY_OFFERS_NAME + " TEXT, "
            + KEY_OFFERS_PRICE + " DOUBLE, "
            + KEY_OFFERS_IMAGE_URL +" TEXT, "
            + KEY_OFFERS_IN_STOCK_STATUS + " INTEGER, " // 0 - No, Yes - any other value
            + KEY_OFFERS_DISCOUNT + " DOUBLE, "
            + KEY_OFFERS_BEACON_ID + " TEXT" + ")";

    // Create statement for comments table
    private final String CREATE_COMMENTS_TABLE_QUERY = "CREATE TABLE " + TABLE_COMMENTS
            + "(" + KEY_COMMENTS_ID + " INTEGER PRIMARY KEY, "
            + KEY_COMMENTS_PRODUCT_ID + " INTEGER, "
            + KEY_COMMENTS_COMMENT_ID + " INTEGER, "
            + KEY_COMMENTS_AUTHOR_ID + " INTEGER, "
            + KEY_COMMENTS_PUBLISH_TIMESTAMP + " DATETIME, "
            + KEY_COMMENTS_PARENT_COMMENT_ID + " INTEGER, "
            + KEY_COMMENTS_COMMENT + " BLOB" + ")" ;

    // Create statement for Located objects table
    private final String CREATE_LOCATED_OBJECTS_TABLE_QUERY = "CREATE TABLE " + TABLE_LOCATED_OBJECTS
            + "(" + KEY_LOCATEDOBJECTS_ID + " INTEGER PRIMARY KEY, "
            + KEY_LOCATEDOBJECTS_PRODUCT_ID + " INTEGER, "
            + KEY_LOCATEDOBJECTS_STORE_ID + " INTEGER, "
            + KEY_LOCATEDOBJECTS_BEACON_UUID + " TEXT, "
            + KEY_LOCATEDOBJECTS_BEACON_MAJOR + " TEXT, "
            + KEY_LOCATEDOBJECTS_BEACON_MINOR + " TEXT" + ")" ;

    // Create statement for Beacons table
    private final String CREATE_BEACONS_TABLE_QUERY = "CREATE TABLE " + TABLE_BEACONS
            + "(" + KEY_BEACONS_ID + " INTEGER PRIMARY KEY, "
            + KEY_BEACONS_BEACON_UUID + " TEXT, "
            + KEY_BEACONS_BEACON_MAJOR + " TEXT, "
            + KEY_BEACONS_BEACON_MINOR + " TEXT, "
            + KEY_BEACONS_STORE_ID + " INTEGER, "
            + KEY_BEACONS_NAME + " TEXT" + ")" ;

    // Create statement for scans table
    private final String CREATE_SCANS_TABLE_QUERY = "CREATE TABLE " + TABLE_SCANS
            + "(" + KEY_SCANS_ID + " INTEGER PRIMARY KEY, "
            + KEY_SCANS_DATE + " DATETIME, "
            + KEY_SCANS_NO + " INTEGER, "
            + KEY_BEACONS_ID + " INTEGER" + ")" ;

    // Create statement for products table
    private final String CREATE_PRODUCTS_TABLE_QUERY = "CREATE TABLE " + TABLE_PRODUCTS
            + "(" + KEY_PRODUCTS_ID + " INTEGER PRIMARY KEY, "
            + KEY_PRODUCTS_CATEGORY_ID + " INTEGER, "
            + KEY_PRODUCTS_SUB_CATEGORY_ID + " INTEGER, "
            + KEY_PRODUCTS_PRODUCT_ID + " INTEGER, "
            + KEY_PRODUCTS_NAME + "TEXT, "
            + KEY_PRODUCTS_PRICE + " DOUBLE, "
            + KEY_PRODUCTS_IMAGE_URL + " TEXT, "
            + KEY_PRODUCTS_IN_STOCK_STATUS + " INTEGER, " // 0- No, anything else, has
            + KEY_PRODUCTS_DISCOUNT + " DOUBLE" + ")" ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, databaseVersion) ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_OFFERS_TABLE_QUERY);
        db.execSQL(CREATE_COMMENTS_TABLE_QUERY);
        db.execSQL(CREATE_LOCATED_OBJECTS_TABLE_QUERY);
        db.execSQL(CREATE_BEACONS_TABLE_QUERY);
        db.execSQL(CREATE_SCANS_TABLE_QUERY);
        db.execSQL(CREATE_PRODUCTS_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATED_OBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEACONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCANS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

        onCreate(db);
    }

    //==================================================================
    //                          OFFERS CRUD
    //==================================================================

    /**
     *  Creating an offer
     */
    public long createOffer (OfferModel offerModel) {
        SQLiteDatabase db = getWritableDatabase() ;

        ContentValues values = new ContentValues() ;
        values.put(KEY_OFFERS_PRODUCT_ID, offerModel.getProductId());
        values.put(KEY_OFFERS_NAME, offerModel.getName());
        values.put(KEY_OFFERS_PRICE, offerModel.getPrice());
        values.put(KEY_OFFERS_IMAGE_URL, offerModel.getImageUrl()) ;
        values.put(KEY_OFFERS_IN_STOCK_STATUS, offerModel.getImageUrl());
        values.put(KEY_OFFERS_DISCOUNT, offerModel.getDiscount());
        values.put(KEY_OFFERS_BEACON_ID, offerModel.getBeaconId());

        long offer_id = db.insert(TABLE_OFFERS, null, values) ;
        return offer_id ;
    }

    /**
     *  Retrieving an offer
     */
    public OfferModel getOffer (long offerId) {
        SQLiteDatabase db = getWritableDatabase() ;

        String selectQuery = "SELECT * FROM " + TABLE_OFFERS + " WHERE "
                + KEY_OFFERS_ID + " = " + offerId ;

        Log.e(LOG, selectQuery) ;

        Cursor c = db.rawQuery(selectQuery, null) ;
        if (c != null)
            c.moveToFirst() ;

        OfferModel offerModel = new OfferModel() ;
        offerModel.setId(c.getInt(c.getColumnIndex(KEY_OFFERS_ID)));
        offerModel.setProductId(c.getInt(c.getColumnIndex(KEY_OFFERS_PRODUCT_ID)));
        offerModel.setName(c.getString(c.getColumnIndex(KEY_OFFERS_NAME)));
        offerModel.setPrice(c.getInt(c.getColumnIndex(KEY_OFFERS_PRICE)));
        offerModel.setImageUrl(c.getString(c.getColumnIndex(KEY_OFFERS_IMAGE_URL)));
        boolean inStock = (c.getInt(c.getColumnIndex(KEY_OFFERS_IN_STOCK_STATUS)) == 0 )? false : true ;
        offerModel.setInStock(inStock);
        offerModel.setDiscount(c.getDouble(c.getColumnIndex(KEY_OFFERS_DISCOUNT)));
        offerModel.setBeaconId(c.getInt(c.getColumnIndex(KEY_OFFERS_BEACON_ID)));

        return offerModel;
    }


    /**
     *  Fetching all offers
     */
    public List<OfferModel> getOffers () {
        SQLiteDatabase db = getWritableDatabase() ;
        List<OfferModel> offerModels = new ArrayList<OfferModel>() ;
        String selectQuery = "SELECT * FROM " + TABLE_OFFERS ;

        Log.e(LOG, selectQuery) ;

        Cursor c = db.rawQuery(selectQuery, null) ;
        if (c.moveToFirst()) {
            do {
                OfferModel offerModel = new OfferModel() ;
                offerModel.setId(c.getInt(c.getColumnIndex(KEY_OFFERS_ID)));
                offerModel.setProductId(c.getInt(c.getColumnIndex(KEY_OFFERS_PRODUCT_ID)));
                offerModel.setName(c.getString(c.getColumnIndex(KEY_OFFERS_NAME)));
                offerModel.setPrice(c.getInt(c.getColumnIndex(KEY_OFFERS_PRICE)));
                offerModel.setImageUrl(c.getString(c.getColumnIndex(KEY_OFFERS_IMAGE_URL)));
                boolean inStock = (c.getInt(c.getColumnIndex(KEY_OFFERS_IN_STOCK_STATUS)) == 0 )? false : true ;
                offerModel.setInStock(inStock);
                offerModel.setDiscount(c.getDouble(c.getColumnIndex(KEY_OFFERS_DISCOUNT)));
                offerModel.setBeaconId(c.getInt(c.getColumnIndex(KEY_OFFERS_BEACON_ID)));

                offerModels.add(offerModel) ;
            } while (c.moveToNext())  ;
        }

        return offerModels;
    }

    /**
     * Updating an offer
     */
    public int updateOffer (OfferModel offerModel) {
        SQLiteDatabase db = getWritableDatabase() ;

        ContentValues values = new ContentValues() ;
        values.put(KEY_OFFERS_PRODUCT_ID, offerModel.getProductId());
        values.put(KEY_OFFERS_NAME, offerModel.getName());
        values.put(KEY_OFFERS_PRICE, offerModel.getPrice());
        values.put(KEY_OFFERS_IMAGE_URL, offerModel.getImageUrl()) ;
        values.put(KEY_OFFERS_IN_STOCK_STATUS, offerModel.getImageUrl());
        values.put(KEY_OFFERS_DISCOUNT, offerModel.getDiscount());
        values.put(KEY_OFFERS_BEACON_ID, offerModel.getBeaconId());

        return db.update(TABLE_OFFERS, values, KEY_OFFERS_ID + " = ?",
                new String[] {String.valueOf(offerModel.getId()) }) ;

    }

    /**
     * Deleting an offer
     */
    public void deleteOffer (long offer_id) {
        SQLiteDatabase db = getWritableDatabase() ;
        db.delete(TABLE_OFFERS, KEY_OFFERS_ID + " = ?" ,
            new String[] { String.valueOf(offer_id)}) ;
    }
}
