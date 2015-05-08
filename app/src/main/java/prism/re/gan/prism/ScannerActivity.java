package prism.re.gan.prism;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import prism.re.gan.prism.qrcodescanner.CameraManager;
import prism.re.gan.prism.qrcodescanner.CaptureHandler;
import prism.re.gan.prism.qrcodescanner.PreviewCallback;
import prism.re.gan.prism.qrcodescanner.view.BoundingView;
import prism.re.gan.prism.qrcodescanner.view.CameraPreviewView;

/**
 * Created by rmbitiru on 2/21/15.
 */
public class ScannerActivity extends Activity {

    public static String PUBLIC_STATIC_STRING_IDENTIFIER;

    /**
     * Camera preview view
     */
    private CameraPreviewView cameraPreview;
    /**
     * Camera manager
     */
    private CameraManager cameraManager;

    /**
     * Capture handler
     */
    private Handler captureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.scanner_activity);
        setContentView(getContentView());
        getActionBar().setDisplayHomeAsUpEnabled(true);

        initializeCamera();
        initializeCameraPreview();
        initializeBoundingView();
    }

    private View getContentView() {
        RelativeLayout contentView = new RelativeLayout(this);
        contentView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        contentView.addView(getBoundingView());
        contentView.addView(getCameraPreviewView());

        return contentView;
    }

    private BoundingView getBoundingView() {
        //Displays the bounding content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        BoundingView boundingView = new BoundingView(this);
        boundingView.setId(R.id.bounding_view);
        boundingView.setLayoutParams(params);

        return boundingView;
    }

    private CameraPreviewView getCameraPreviewView() {
        //This is the camera SurfaceView
        CameraPreviewView camPreview = new CameraPreviewView(this);
        camPreview.setId(R.id.camera_preview);
        camPreview.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        return camPreview;
    }

    private void initializeCamera() {
        // Create an instance of Camera
        cameraManager = new CameraManager();
        captureHandler = new CaptureHandler(cameraManager, this, new OnDecoded());

        //requesting next frame for decoding
        cameraManager.requestNextFrame(new PreviewCallback(captureHandler, cameraManager));
    }

    private void initializeCameraPreview() {
        // Create our Preview view and set it as the content of our activity.
        cameraPreview = (CameraPreviewView) findViewById(R.id.camera_preview);
        cameraPreview.setCameraManager(cameraManager);
    }

    private void initializeBoundingView() {
        //Set the cameraManager to the bounding view
        ((BoundingView) findViewById(R.id.bounding_view)).setCameraManager(cameraManager);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //We don't want the cameraManager to take up unneeded
        //resources so we release it from memory onPause
        cameraManager.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_activity, menu);
        return true;
    }

    /**
     * This handles the decoded content from the Zxing framework. As
     * soon as the framework has completed the decoding process the CaptureHandler
     * will pass it back via this listener for you to handle it further.
     *
     */
    private class OnDecoded implements CaptureHandler.OnDecodedCallback {
        @Override
        public void onDecoded(String decodedData) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, decodedData);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}
