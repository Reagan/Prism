package prism.re.gan.prism;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import prism.re.gan.prism.beacon.BeaconsManager;
import prism.re.gan.prism.db.LocalDbStorage;
import prism.re.gan.prism.internet.InternetConnection;
import prism.re.gan.prism.ui.offers.OfferItem;
import prism.re.gan.prism.ui.offers.OfferItemsListAdapter;
import prism.re.gan.prism.wifi.WifiConnection;

/**
 * Created by rmbitiru on 2/23/15.
 */
public class OffersActivity extends Activity {

    private ArrayList<OfferItem> offerItems = new ArrayList<>();
    private OfferItemsListAdapter offerItemsAdapter;
    private final String TAG = OffersActivity.class.getName();
    private WifiConnection wifiConnection;
    private InternetConnection internetConnection;

    private TextView sectionTextView;

    public static final String BEACON = "beacon" ;
    public static Beacon currentBeacon ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain reference to the List view
        ListView offersList = (ListView) findViewById(R.id.offer_items);
        offerItemsAdapter = new OfferItemsListAdapter(this, R.layout.offer_item, offerItems);
        offersList.setAdapter(offerItemsAdapter);

        // add OnClick Listener
        offersList.setOnItemClickListener(createOfferItemsClickListener());

        // Get reference to section title
        sectionTextView = (TextView) findViewById(R.id.sectionTitle);

        // extract any intent coming in from another location
        Intent i = getIntent();
        currentBeacon = i.getParcelableExtra("beacons");
        if (currentBeacon != null) {
            try {
                fetchItems(currentBeacon);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void onStart(Intent intent, int startId) throws IOException {
        /**
         Bundle extras = intent.getExtras() ;
         if (extras != null) {
         ArrayList<Beacon> beacons = (ArrayList<Beacon>) extras.get("beacons") ;
         ArrayList<OfferItem> offerItems = fetchItems(beacons) ;
         addItems(offerItems);
         saveItemsToDisk(offerItems) ;
         }
         **/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        final String INVALID_QUERY_ERROR = "Invalid query entered" ;
        getMenuInflater().inflate(R.menu.menu_offers_activity, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (null != query && !query.trim().equals(""))
                    searchAndLoad(query.trim());
                else
                    Toast.makeText(getApplicationContext(), INVALID_QUERY_ERROR, Toast.LENGTH_SHORT).show();
                return true ;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.action_settings):
                Log.i(TAG, "Settings menu item selected");
                Intent prefsSettings = new Intent(this, PrefsActivity.class);
                startActivity(prefsSettings);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addItems() {
        OfferItem offerItem = new OfferItem(1, 2, 3, "Polo Sweaters", "R.drawable.sweater_1", 200, true, 25, "toys");
        offerItems.add(offerItem);
        offerItemsAdapter.notifyDataSetChanged();
    }

    private void setActivityCategory (String category) {
        final String SECTION =" Section" ;
        sectionTextView.setText(category + SECTION);
    }

    private void addItems(ArrayList<OfferItem> offerItems) {
        if (null != offerItems && offerItems.size() > 0) {
            this.offerItems.clear();
            this.offerItems.addAll(offerItems);
            offerItemsAdapter.notifyDataSetChanged();
        }
    }

    private void fetchItems(Beacon beacon)
            throws IOException {
        Download download = new Download(beacon);
        download.execute();
    }

    private void searchAndLoad(String searchTerm) {
        Search search = new Search() ;
        search.setSearchTerm(searchTerm) ;
        search.execute() ;
    }

    private void saveItemsToDisk(ArrayList<OfferItem> offerItems) {

    }

    private ArrayList<OfferItem> convertToOfferItems(String jsonString) {

        ArrayList<OfferItem> offerItems = new ArrayList<>();
        Log.i(TAG, jsonString);

        try {
            JSONObject offersString = new JSONObject(jsonString);
            Log.d(TAG, offersString.toString());
            JSONArray payloadItems = offersString.getJSONArray("payload");

            for (int payloadCounter = 0; payloadCounter < payloadItems.length(); payloadCounter++) {

                JSONObject currPayload = (JSONObject) payloadItems.get(payloadCounter);

                OfferItem currOfferItem = new OfferItem();
                currOfferItem.setProductId(Integer.parseInt(currPayload.getString("productId")));
                currOfferItem.setCategoryId(Integer.parseInt(currPayload.getString("categoryId")));
                currOfferItem.setSubCategoryId(Integer.parseInt(currPayload.getString("subCategoryId")));
                currOfferItem.setItemName(currPayload.getString("productName"));
                currOfferItem.setItemImageUrl(currPayload.getString("imageUrl"));
                currOfferItem.setPrice(Double.parseDouble(currPayload.getString("productPrice")));
                currOfferItem.setInStock(Boolean.valueOf(currPayload.getString("inStockStatus")));
                currOfferItem.setDiscount(Double.parseDouble(currPayload.getString("discount")));
                currOfferItem.setCategory(currPayload.getString("category"));

                offerItems.add(currOfferItem);
            }

        } catch (Throwable t) {
            Log.e(TAG, t.toString());
        }

        return offerItems;
    }

    private ArrayList<OfferItem> fetchFromSDCard(Beacon beacon) {
        LocalDbStorage localDbStorage = new LocalDbStorage(this);
        // return localDbStorage.fetchOfferItems(beacons);
        return null;
    }

    class Search extends AsyncTask<String, Void, ArrayList<OfferItem>> {

        public String searchTerm ;

        Search () {}

        public void setSearchTerm (String searchTerm) {
            this.searchTerm = searchTerm ;
        }

        public String getSearchTerm () {
            return searchTerm ;
        }

        @Override
        protected ArrayList<OfferItem> doInBackground(String... params) {

            ArrayList<OfferItem> fetchedOfferItems = new ArrayList<>();
            try {

                String searchURL = ((PrismApplication) getApplicationContext()).getSearchUrl() + searchTerm ;
                internetConnection = new InternetConnection(getApplicationContext(),
                        searchURL);
                internetConnection.connect();
                boolean isConnected = internetConnection.isConnected();
                if (isConnected) {

                    fetchedOfferItems = convertToOfferItems(internetConnection.getResponseString());
                    Log.i(TAG, "Obtained results from Internet");

                } else {
                    // fetchedOfferItems = fetchFromSDCard(beacon);
                    Log.i(TAG, "Obtained results from Disk");
                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return fetchedOfferItems;
        }

        protected void onPostExecute(ArrayList<OfferItem> fetchedOfferItems) {
            if (null != fetchedOfferItems && fetchedOfferItems.size() > 0) {
                String category = ((OfferItem) fetchedOfferItems.get(0)).getCategory();
                setActivityCategory(category);
                addItems(fetchedOfferItems);
                saveItemsToDisk(fetchedOfferItems);
                internetConnection.close();
            }
        }
    }

    class Download extends AsyncTask<String, Void, ArrayList<OfferItem>> {

        public Beacon beacon;

        Download(Beacon beacon) {
            this.beacon = beacon;
        }

        @Override
        protected ArrayList<OfferItem> doInBackground(String... params) {

            ArrayList<OfferItem> fetchedOfferItems = new ArrayList<>();
            try {

                String remoteOffersURL = ((PrismApplication) getApplicationContext()).getRemoteOffersUrl() + processBeaconGETVars(beacon);
                internetConnection = new InternetConnection(getApplicationContext(),
                        remoteOffersURL);
                internetConnection.connect();
                boolean isConnected = internetConnection.isConnected();
                if (isConnected) {

                    fetchedOfferItems = convertToOfferItems(internetConnection.getResponseString());
                    Log.i(TAG, "Obtained results from Internet");

                } else {
                    fetchedOfferItems = fetchFromSDCard(beacon);
                    Log.i(TAG, "Obtained results from Disk");
                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return fetchedOfferItems;
        }

        protected void onPostExecute(ArrayList<OfferItem> fetchedOfferItems) {
            if (null != fetchedOfferItems && fetchedOfferItems.size() > 0) {
                String category = ((OfferItem) fetchedOfferItems.get(0)).getCategory();
                setActivityCategory(category);
                addItems(fetchedOfferItems);
                saveItemsToDisk(fetchedOfferItems);
                internetConnection.close();
            }
        }
    }

    private String processBeaconGETVars(Beacon beacon) {
        return "?"
                + "uuid=" + beacon.getProximityUUID()
                + "&major=" + beacon.getMajor()
                + "&minor=" + beacon.getMinor();
    }

    private AdapterView.OnItemClickListener createOfferItemsClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent navigationIntent = new Intent(getApplicationContext(), LocateActivity.class) ;
                if (BeaconsManager.getCurrentBeacon() != null) {
                    navigationIntent.putExtra(BEACON, (OfferItem) offerItemsAdapter.getItem(position)) ;
                }
                startActivity(navigationIntent);
            }
        };
    }
}
