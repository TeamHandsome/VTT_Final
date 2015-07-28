package example.com.demoapp.adapter.sentence_adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.BaseListAdapter;
import example.com.demoapp.model.SentenceItem;

/**
 * Created by Tony on 23/7/2015.
 */
public abstract class BaseSentencesAdapter extends BaseListAdapter {

    public BaseSentencesAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position,convertView,parent);

        this.setUpListView(holder,convertView,position);

        return convertView;
    }

    @Override
    protected ViewHolder buildHolder(ViewHolder holder,View convertView) {
        holder.tvDisplayID = (TextView) convertView.findViewById(R.id.tvDisplayId);
        holder.tvDisplayName = (TextView) convertView.findViewById(R.id.tvDisplayName);

        return holder;
    }

    //Setting up data to show on List view
    private void setUpListView(final ViewHolder holder,View convertView,final int position){
        holder.tvDisplayID.setText("" + (position + 1));
        holder.tvDisplayName.setText(listSentences.get(position).getNameJp());
    }

}
