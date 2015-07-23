package example.com.demoapp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import example.com.demoapp.model.SentenceItem;

/**
 * Created by Long on 6/21/2015.
 */
public class SentencesAdapter extends BaseSentencesAdapter {

    public SentencesAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
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
