package prism.re.gan.prism;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;


public class LaunchActivity extends Activity {

    private ToggleButton scanningButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        scanningButton = (ToggleButton) findViewById(R.id.alerts_icon) ;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // @TODO: Need to implement the shared preferences profile
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case (R.id.scan_button) :
                Intent scanIntent = new Intent(getApplicationContext(), ScannerActivity.class) ;
                startActivity(scanIntent);
                break ;

            case (R.id.login_button_launch_activity) :
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class) ;
                startActivity(loginIntent);
                break;

            case (R.id.offers_button) :
                Intent offersIntent = new Intent(getApplicationContext(), OffersActivity.class) ;
                startActivity(offersIntent);
                break;

            case (R.id.locate_button) :
                Intent locateIntent = new Intent(getApplicationContext(), LocateActivity.class) ;
                startActivity(locateIntent);
                break;

            case (R.id.alerts_icon) :
                PrismApplication prismApplication = ((PrismApplication) this.getApplication());
                prismApplication.toggleScanning();
                break;

            default:
        }
    }

    public void disableScanningButton() {
        scanningButton.setChecked(false);
    }

    public void enableScanningButton() {
        scanningButton.setChecked(true);
    }
}
