package example.com.demoapp.adapter.image_adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.demoapp.model.DAO.HistoryDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Consts;

/**
 * Created by dmonkey on 7/24/2015.
 */
public class HistoryImageAdapter extends BaseImageAdapter {

    public HistoryImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        this.setUpSound(holder, position);
        this.setUpBtnDelete(holder, convertView, position);

        return convertView;
    }

    @Override
    protected void onclickDelete(int position) {
        HistoryDAO dao = new HistoryDAO();
        SentenceItem item = listSentences.get(position);
        dao.removeFromHistory(item.getId());
    }

    @Override
    protected String setTitleConfirmDeleteDialog(){
        return Consts.DELETE_FROM_HISTORY;
    }
}

