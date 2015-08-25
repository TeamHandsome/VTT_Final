package example.com.demoapp.adapter.image_adapter;

import android.app.Activity;
import android.graphics.Point;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.BaseListAdapter;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.StringUtils;

/**
 * Created by Tony on 23/7/2015.
 */
public abstract class BaseImageAdapter extends BaseListAdapter {

    public BaseImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        this.setUpGridView(holder, convertView, position);

        return convertView;
    }

    @Override
    protected ViewHolder buildHolder(ViewHolder holder, View convertView) {
        Point size = Common.getScreenSizeInPixels(context);
        int width = size.x;
        int y = width / 2;

        holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
        holder.imageView.setMaxHeight(y);
        holder.imageView.setMaxWidth(y);
        holder.imageView.setMinimumWidth(y);
        holder.imageView.setMinimumHeight(y);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView.setPadding(2, 2, 2, 2);

        return holder;
    }

    //Setting up data to show on Grid view
    private void setUpGridView(final ViewHolder holder, View convertView, final int position) {
        String pathImage = listSentences.get(position).getImage();

            if (listSentences.get(position).getId().contains("s")) {
                Picasso.with(context)
                        .load(pathImage)
                        .resize(360, 360)
                        .centerCrop()
                        .into(holder.imageView);

            } else {
                Uri uri = StringUtils.buildDrawableUri(context.getPackageName(), pathImage);
                Picasso.with(context)
                        .load(uri)
                        .resize(360,360)
                        .centerCrop()
                        .into(holder.imageView);
            }



//        holder.imageView.setImageResource(context.getResources().getIdentifier(pathImage, "drawable", context.getPackageName()));
    }
}
