package example.com.demoapp.adapter;

/**
 * Created by Long on 6/3/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.model.DrawerMenuItem;

public class DrawerMenuItemAdapter extends BaseAdapter{

    List<DrawerMenuItem> mItems;
    Context mContext;
    private int mSelectedItem;

    public void selectedItem(int selectedItem){
        this.mSelectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public DrawerMenuItemAdapter(Context context,List<DrawerMenuItem> mItems) {
        this.mItems = mItems;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_drawer_menu_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.img_drawer_menu_item_icon);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_drawer_menu_item_text);

        DrawerMenuItem item = mItems.get(position);

        imgIcon.setImageResource(item.getIcon());
        tvTitle.setText(item.getText());

        if (position == mSelectedItem){
            int newColor = mContext.getResources().getColor(R.color.colorPrimary);
            imgIcon.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
            tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorSecondText));
            int oldColor = mContext.getResources().getColor(R.color.colorSecondText);
            imgIcon.setColorFilter(oldColor, PorterDuff.Mode.SRC_ATOP);
        }

        return convertView;
    }


}

