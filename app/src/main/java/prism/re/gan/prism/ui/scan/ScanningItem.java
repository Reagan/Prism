package prism.re.gan.prism.ui.scan;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import prism.re.gan.prism.R;

/**
 * Created by rmbitiru on 2/21/15.
 */
public class ScanningItem extends RelativeLayout {

    public ScanningItem (Context context, AttributeSet attributeSet) {
        super(context, attributeSet) ;
        inflateUI() ;
    }

    private void inflateUI () {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        inflater.inflate(R.layout.scanning_item, this)  ;
    }
}
