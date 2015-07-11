package example.com.demoapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.model.TagItem;

/**
 * Created by Long on 7/8/2015.
 */
public class TagAdapter extends ArrayAdapter<TagItem>{
    Context mContext;
    int idLayoutResource;
    private ArrayList<TagItem> items;
    private ArrayList<TagItem> itemsAll;
    private ArrayList<TagItem> suggestions;
    private LayoutInflater layoutInflater;

    public TagAdapter(Context mContext, int idLayoutResource,
                      ArrayList<TagItem> items) {
        super(mContext, idLayoutResource, items);
        this.items = items;
        this.itemsAll = (ArrayList<TagItem>) items.clone();
        this.suggestions = new ArrayList<TagItem>();
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private class ViewHolder {
        TextView tvTag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.activity_add_edit_tag_item, null);
            holder.tvTag = (TextView) convertView.findViewById(R.id.tvTag);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.tvTag.setText(items.get(position).getNameTag());
        Log.i("Data Tag: ", items.get(position).getNameTag() + "");

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((TagItem)(resultValue)).getNameTag();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (TagItem tagItem : itemsAll) {
                    if(tagItem.getNameTag().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(tagItem);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<TagItem> filteredList = (ArrayList<TagItem>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (TagItem c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}
