package prism.re.gan.prism.ui.directions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import prism.re.gan.prism.R;

/**
 * Created by rmbitiru on 2/23/15.
 */
public class MapItem extends RelativeLayout {

    public MapItem (Context context, AttributeSet attributeSet) {
        super(context, attributeSet) ;
        inflateUI() ;
    }

    private void inflateUI () {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        inflater.inflate(R.layout.map_item, this)  ;
    }
}
