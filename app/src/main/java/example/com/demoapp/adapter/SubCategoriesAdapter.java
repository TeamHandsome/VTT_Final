package example.com.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.model.SubCategoriesItem;

/**
 * Created by Long on 6/21/2015.
 */
public class SubCategoriesAdapter extends ArrayAdapter<SubCategoriesItem>{

    Context context;
    int idLayoutResource;
    ArrayList<SubCategoriesItem> listSubcategories;

    public SubCategoriesAdapter(Context context, int idLayoutResource,
                            ArrayList<SubCategoriesItem> listSubcategories) {
        super(context, idLayoutResource, listSubcategories);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSubcategories = listSubcategories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(idLayoutResource, null);
            holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();
        holder.tvId.setText(""+(position+1));
        holder.tvName.setText(listSubcategories.get(position).getName());

        return convertView;
    }

    private class ViewHolder{
        TextView tvId, tvName;
    }
}
