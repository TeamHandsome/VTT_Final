package example.com.demoapp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.DAO.HistoryDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Message;

/**
 * Created by dmonkey on 7/24/2015.
 */
public class HistorySentencesAdapter extends BaseSentencesAdapter {

    public HistorySentencesAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        this.setUpSound(holder, position);
        this.setUpBtnDelete(holder,convertView,position);

        return convertView;
    }

    @Override
    protected void onclickDelete(int position) {
        HistoryDAO dao = new HistoryDAO(context);
        SentenceItem item = listSentences.get(position);
        dao.removeFromHistory(item.getId());
        Common.showToastMessage(getContext(), Message.DELETED_FROM_HISTORY);
    }
}

