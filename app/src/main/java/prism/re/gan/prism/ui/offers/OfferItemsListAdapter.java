package prism.re.gan.prism.ui.offers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import prism.re.gan.prism.PrismApplication;
import prism.re.gan.prism.R;
import prism.re.gan.prism.internet.InternetConnection;

/**
 * Created by rmbitiru on 2/23/15.
 */
public class OfferItemsListAdapter extends ArrayAdapter<OfferItem> {

    private final String TAG = OfferItemsListAdapter.class.getSimpleName() ;

    public OfferItemsListAdapter (Context context, int textViewResource) {
        super(context, textViewResource);
    }

    public OfferItemsListAdapter(Context context, int resource, List<OfferItem> items) {
        super(context, resource, items);
    }

    public View getView (int position, View view, ViewGroup parent) {
        View v = view ;

        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.offer_item, parent, false)  ;
        }

        OfferItem offerItem = getItem(position) ;
        if (offerItem != null) {
            ImageView itemImageImageView = (ImageView) v.findViewById(R.id.item_image)  ;
            TextView itemNameTextView = (TextView) v.findViewById(R.id.item_name) ;
            TextView percentOffTextView = (TextView) v.findViewById(R.id.percent_off) ;


            if (null != itemImageImageView)  {
                DownloadImage downloadImage = new DownloadImage(offerItem.getItemImageUrl(),
                                                                itemImageImageView) ;
                downloadImage.execute() ;
            }


            if (null != itemNameTextView)
                itemNameTextView.setText(offerItem.getItemName());

            if (null != percentOffTextView)
                percentOffTextView.setText( (int) offerItem.getDiscount() + "%");
        }
        return v ;
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        String imageURL;
        ImageView imageView ;
        InternetConnection internetConnection ;

        public DownloadImage(String imageURL, ImageView imageView) {
            this.imageURL = imageURL;
            this.imageView = imageView ;
        }

        protected Bitmap doInBackground (String... urls) {
            Bitmap image = null ;
            InputStream inputStream ;

            try {
                String remoteOffersURL = ((PrismApplication) (getContext().getApplicationContext())).getBeaconsContentImagesUrl() +
                        File.separator + this.imageURL;
                Log.i(TAG, "Fetching image " + remoteOffersURL) ;
                inputStream = new java.net.URL(remoteOffersURL).openStream();
                image = BitmapFactory.decodeStream(inputStream) ;

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return image ;
        }

        protected void  onPostExecute(Bitmap image) {
            imageView.setImageBitmap(image);
            Log.i(TAG, "Set remote image to imageview") ;
        }
    }

}
