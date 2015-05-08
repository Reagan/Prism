package prism.re.gan.prism;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by rmbitiru on 3/29/15.
 */
public class PrefsActivity extends PreferenceActivity {

    protected void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.prefs);
    }
}
