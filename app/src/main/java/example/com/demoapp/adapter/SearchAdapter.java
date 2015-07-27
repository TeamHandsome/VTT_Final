package example.com.demoapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.sentence_adapter.BaseSentencesAdapter;
import example.com.demoapp.model.SentenceItem;

/**
 * Created by Long on 7/23/2015.
 */
public class SearchAdapter extends BaseSentencesAdapter implements Filterable {
    Context mContext;
    ArrayList<SentenceItem> listAll;
    ArrayList<SentenceItem> listSentences;

    private int count;
    private int stepNumber;
    private int startCount;

    //    public SearchAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
//        super(context, idLayoutResource, listSentences);
//        mContext = context;
//        this.idLayoutResource = idLayoutResource;
//        this.listSentences = listSentences;
//    }
    public SearchAdapter(Activity context, ArrayList<SentenceItem> listSentences, int startCount, int stepNumber) {
        super(context, R.layout.custom_row_sen_f_t, listSentences);
        this.mContext = context;
        this.listSentences = listSentences;
        this.startCount = Math.min(startCount, listSentences.size());
        this.count = this.startCount;
        this.stepNumber = stepNumber;

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
    public int getCount() {
        return count;
    }
    @Override
    public Object getItem(int i) {
        return listSentences.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listSentences.get(i).hashCode();
    }

    public boolean showMore() {
        if (count == listSentences.size()) {
            return true;
        } else {
            count = Math.min(count + stepNumber, listSentences.size()); //don't go past the end
            notifyDataSetChanged(); //the count size has changed, so notify the super of the change
            return endReached();
        }
    }

    public boolean endReached() {
        return count == listSentences.size();
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
                            if (g.getNameJpHira().toLowerCase().contains(constraint.toString()))

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
