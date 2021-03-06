package example.com.demoapp.adapter.sentence_adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.SentenceItem;

/**
 * Created by dmonkey on 7/24/2015.
 */
public class MySentencesAdapter extends BaseSentencesAdapter {

    public MySentencesAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        this.setUpSound(holder, position);
        this.setUpBtnFavorite(holder, convertView, position);
        this.setUpBtnDelete(holder, convertView, position);
        this.setUpBtnEdit(holder,convertView,position);

        return convertView;
    }

    @Override
    protected void onclickDelete(int position) {
        SentencesDAO dao = new SentencesDAO();
        SentenceItem item = listSentences.get(position);
        dao.removeSentence(item.getId());
    }
}

