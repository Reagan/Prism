package prism.re.gan.prism;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;

import prism.re.gan.prism.ui.directions.CompassView;
import prism.re.gan.prism.ui.offers.OfferItem;

/**
 * Created by rmbitiru on 2/23/15.
 */
public class LocateActivity extends Activity {

    private OfferItem offerItem;

    private final String YOU_ARE_IN = "you are in";
    private final String CURRENT_LOCATION = "Current Location";
    private Direction itemDirection;
    private final String CANNOT_LOCATE_DIRECTION_ERROR = "No root beacon. Cannot locate direction to item   ";


    private enum Direction {
        NORTH, SOUTH, EAST, WEST, NORTH_EAST,
        SOUTH_EAST, SOUTH_WEST, NORTH_WEST,
        UNKNOWN;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Extract offer item from intent
        offerItem = getIntent().getParcelableExtra(OffersActivity.BEACON);

        if (OffersActivity.currentBeacon != null) {
            // update navigation distance, item and directions
            updateNavigationTextViews(offerItem);

            // update navigation arrow
            CompassView compassView = (CompassView) findViewById(R.id.compass_item);
            compassView.setRotationDegree(convertToDegree(itemDirection));
            compassView.setRotation(convertToDegree(itemDirection));

            // @TODO update navigation map

            // add image to lower section

        } else
            Toast.makeText(this, CANNOT_LOCATE_DIRECTION_ERROR, Toast.LENGTH_LONG).show();
    }

    private void updateNavigationTextViews(OfferItem offerItem) {
        TextView distanceRemainingAndItems = (TextView) findViewById(R.id.distance_remaining);
        TextView turnInstructions = (TextView) findViewById(R.id.turn_instructions);

        if (belongsToBeacon(OffersActivity.currentBeacon, offerItem)) {
            distanceRemainingAndItems.setText(YOU_ARE_IN);
            turnInstructions.setText(CURRENT_LOCATION);
        } else {
            itemDirection = getDirection(OffersActivity.currentBeacon, offerItem);
            int distance = 10;

            String distanceAndItems = distance + "m to " + offerItem.getCategory();
            String displayedDirection = removeUnderscore("Turn " + itemDirection.toString());
            distanceRemainingAndItems.setText(distanceAndItems);
            turnInstructions.setText(displayedDirection);
        }
    }

    private boolean belongsToBeacon(Beacon currentBeacon, OfferItem currOfferItem)
            throws NumberFormatException {
        if (currentBeacon.getMajor() == 63482) {
            if (currOfferItem.getProductId() == 5 ||
                    currOfferItem.getProductId() == 6 ||
                    currOfferItem.getProductId() == 7)
                return true;

        } else if (currentBeacon.getMajor() == 35607) {
            if (currOfferItem.getProductId() == 1 ||
                    currOfferItem.getProductId() == 2 ||
                    currOfferItem.getProductId() == 3 ||
                    currOfferItem.getProductId() == 4)
                return true;

        } else if (currentBeacon.getMajor() == 41046) {
            if (currOfferItem.getProductId() == 8 ||
                    currOfferItem.getProductId() == 9 ||
                    currOfferItem.getProductId() == 10)
                return true;
        }
        return false;
    }

    private Direction getDirection(Beacon currentBeacon, OfferItem currOfferItem) {
        if (currentBeacon.getMajor() == 63482) {
            if (currOfferItem.getProductId() == 1 ||
                    currOfferItem.getProductId() == 2 ||
                    currOfferItem.getProductId() == 3 ||
                    currOfferItem.getProductId() == 4)
                return Direction.NORTH_EAST;

            if (currOfferItem.getProductId() == 8 ||
                    currOfferItem.getProductId() == 9 ||
                    currOfferItem.getProductId() == 10)
                return Direction.NORTH_WEST;

        } else if (currentBeacon.getMajor() == 35607) {
            if (currOfferItem.getProductId() == 8 ||
                    currOfferItem.getProductId() == 9 ||
                    currOfferItem.getProductId() == 10)
                return Direction.WEST;

            if (currOfferItem.getProductId() == 5 ||
                    currOfferItem.getProductId() == 6 ||
                    currOfferItem.getProductId() == 7)
                return Direction.SOUTH_WEST;

        } else if (currentBeacon.getMajor() == 41046) {
            if (currOfferItem.getProductId() == 1 ||
                    currOfferItem.getProductId() == 2 ||
                    currOfferItem.getProductId() == 3 ||
                    currOfferItem.getProductId() == 4)
                return Direction.EAST;

            if (currOfferItem.getProductId() == 5 ||
                    currOfferItem.getProductId() == 6 ||
                    currOfferItem.getProductId() == 7)
                return Direction.SOUTH_EAST;
        }
        return Direction.UNKNOWN;
    }

    private int convertToDegree(Direction direction) {
        if (direction == Direction.NORTH)
            return 0;
        else if (direction == Direction.NORTH_WEST)
            return 315;
        else if (direction == Direction.WEST)
            return 270;
        else if (direction == Direction.SOUTH_WEST)
            return 225;
        else if (direction == Direction.SOUTH)
            return 180;
        else if (direction == Direction.SOUTH_EAST)
            return 135;
        else if (direction == Direction.EAST)
            return 90;
        else if (direction == Direction.NORTH_EAST)
            return 45;
        return 0;
    }

    private String removeUnderscore(String str) {
        return str.replace("_", " ");
    }

}
