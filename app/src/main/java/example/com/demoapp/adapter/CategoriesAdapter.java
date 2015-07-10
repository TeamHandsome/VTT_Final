package example.com.demoapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import example.com.demoapp.R;

/**
 * Created by Long on 6/14/2015.
 */
public class CategoriesAdapter extends BaseAdapter{
    private Context mContext;

    public CategoriesAdapter(Context c) {
        mContext = c;
    }
    public CategoriesAdapter(Context c,Integer []arrIds){
        mContext=c;
        mThumbIds=arrIds;
    }
    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(360, 360));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.conversation, R.drawable.hotel,
            R.drawable.traffic, R.drawable.hospital,
            R.drawable.restaurant, R.drawable.airport,
            R.drawable.shopping, R.drawable.tourism
    };
}
