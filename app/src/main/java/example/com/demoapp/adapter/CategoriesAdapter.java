package example.com.demoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.demoapp.activity.DisplaySubActivity;
import example.com.demoapp.model.CategoryItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;

/**
 * Created by Long on 6/14/2015.
 */
public class CategoriesAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<CategoryItem> categoryList;

    private ImageView imageView;

    public CategoriesAdapter(Context c,ArrayList<CategoryItem> categoryList){
        context =c;
        this.categoryList = categoryList;
    }
    public int getCount() {
        return categoryList !=null ? categoryList.size():0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Point size = Common.getScreenSizeInPixels(parent.getContext());
        int width = size.x;
        int y = width/2;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(y, y));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        String imageName = categoryList.get(position).getImage();
        int res = context.getResources().getIdentifier(imageName,"drawable",context.getPackageName());
        Picasso.with(context)
                .load(res)
                .resize(y,y)
                .centerInside()
                .into(imageView);

        return imageView;
    }
}
