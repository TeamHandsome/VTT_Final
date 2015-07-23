package example.com.demoapp.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.model.SentenceItem;

/**
 * Created by Long on 7/7/2015.
 */
public class ImageAdapter extends BaseImageAdapter {

    public ImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        this.setUpSound(holder, position);
        this.setUpBtnFavorite(holder, convertView, position);
        this.setUpBtnTag(holder, convertView, position);

        return convertView;
    }

    @Override
    protected void onclickDelete(int position) {
    }


}

