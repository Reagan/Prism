package prism.re.gan.prism.ui.scan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import prism.re.gan.prism.R;

/**
 * Created by rmbitiru on 2/21/15.
 */
public class QRCodeItemScan extends LinearLayout {

    private int qrCodeScanImage;
    private String scanTime;
    private String scanLocation ;

    public QRCodeItemScan (Context context, AttributeSet attributeSet) {
        super (context, attributeSet) ;
        setConfigParams(context, attributeSet);
        inflateUI(context) ;
        setConfigValues() ;
    }

    private void setConfigParams (Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,
                R.styleable.QRCodeItemScan,
                0, 0) ;
        qrCodeScanImage = typedArray.getResourceId(R.styleable.QRCodeItemScan_QRCodeImage,
                0) ;
        scanTime = typedArray.getString(R.styleable.QRCodeItemScan_QRCodeScanTime) ;
        scanLocation = typedArray.getString(R.styleable.QRCodeItemScan_QRCodeScanLocation) ;
        typedArray.recycle();
    }

    private void inflateUI (Context context) {
        LayoutInflater inflater = (LayoutInflater) context.
                                        getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        inflater.inflate(R.layout.qr_code_item_scan, this, true) ;
    }

    private void setConfigValues () {
        LinearLayout layout = (LinearLayout) getChildAt(0) ;
        ImageView qrCodeImage = (ImageView) layout.getChildAt(0) ;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), qrCodeScanImage) ;
        qrCodeImage.setImageBitmap(bitmap);

        TextView scanTimeView = (TextView) layout.getChildAt(1) ;
        scanTimeView.setText(scanTime);

        TextView scanLocationView = (TextView) layout.getChildAt(2) ;
        scanLocationView.setText(scanLocation);
    }
}
