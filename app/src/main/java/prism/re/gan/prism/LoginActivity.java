package prism.re.gan.prism;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import prism.re.gan.prism.authentication.Authentication;

/**
 * Created by rmbitiru on 2/23/15.
 */
public class LoginActivity extends Activity {

    private ImageView loginImageButton ;
    private EditText username ;
    private EditText password ;
    private final String TAG = LoginActivity.class.getName() ;
    private String SUCCESSFUL_LOGIN_MESSAGE ;
    private final String ERROR_LOGIN_MESSAGE = getResources().getString(R.string.error_login_message);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SUCCESSFUL_LOGIN_MESSAGE = getResources().getString(R.string.success_login_message);

        username =  (EditText) findViewById(R.id.username) ;
        password = (EditText) findViewById(R.id.password) ;

        loginImageButton = (ImageView) findViewById(R.id.login_button) ;
        loginImageButton.setOnClickListener(new LoginButtonListener(username, password));
    }

    class LoginButtonListener implements View.OnClickListener {

        String username ;
        String password ;
        Authentication authentication ;

        LoginButtonListener(EditText usernameEdittext, EditText passwordEditext) {
            this.username = username ;
            this.password = password ;
            authentication = new Authentication() ;
        }

        public void onClick (View view) {
            Boolean authenticated = authentication.authenticate(view.getContext(), username, password) ;
            if (authenticated) {
                displaySuccessfulLoginMessage() ;
                redirectToLoginActivity();
            } else  {
                Authentication.AuthenticationError authenticationError = authentication.getError() ;
                displayErrorMessage (authenticationError.getMessage()) ;
                Log.e(TAG, authenticationError.toString()) ;
            }
        }

        private void displaySuccessfulLoginMessage () {
            Utils.displayShortToast(getApplicationContext(), SUCCESSFUL_LOGIN_MESSAGE) ;
        }

        private void displayErrorMessage (String errorMessage) {
            Utils.displayShortToast(getApplicationContext(), errorMessage)  ;
        }

        private void redirectToLoginActivity () {
            Intent LoginActivity = new Intent(getApplicationContext(), LaunchActivity.class) ;
            startActivity(LoginActivity);
        }
    }
}
