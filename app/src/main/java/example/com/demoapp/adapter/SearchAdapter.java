package example.com.demoapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import example.com.demoapp.adapter.sentence_adapter.BaseSentencesAdapter;
import example.com.demoapp.model.SentenceItem;

/**
 * Created by Long on 7/23/2015.
 */
public class SearchAdapter extends BaseSentencesAdapter implements Filterable {
    Context mContext;
    ArrayList<SentenceItem> listAll;

    public SearchAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        mContext = context;
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

    // Filter Class
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SentenceItem> results = new ArrayList<SentenceItem>();
                if (listAll == null)
                    listAll = listSentences;
                if (constraint != null) {
                    if (listAll != null && listAll.size() > 0) {
                        for (final SentenceItem g : listAll) {
                            if (g.getNameFull()!=null && g.getNameFull().toLowerCase().contains(constraint.toString()))

                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                listSentences = (ArrayList<SentenceItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    protected void onclickDelete(int position) {

    }
}
