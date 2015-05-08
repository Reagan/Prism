package prism.re.gan.prism.db;

import android.content.Context;

import com.estimote.sdk.Beacon;

import java.util.ArrayList;
import java.util.List;

import prism.re.gan.prism.db.domain.OfferModel;
import prism.re.gan.prism.ui.offers.OfferItem;

/**
 * Created by rmbitiru on 4/11/15.
 */
public class LocalDbStorage {

    private DatabaseHelper databaseHelper  ;

    public LocalDbStorage (Context context) {
        databaseHelper = new DatabaseHelper(context) ;
    }

    public ArrayList<OfferItem> fetchOfferItems (ArrayList<Beacon> beacons) {
        return null ;
    }

    public void insertOfferItems (List<OfferModel> offerModels) {

    }
}
