package example.com.demoapp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Message;

/**
 * Created by dmonkey on 7/24/2015.
 */
public class MyImageAdapter extends BaseImageAdapter{

    public MyImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        this.setUpSound(holder, position);
        this.setUpBtnFavorite(holder,convertView,position);
        this.setUpBtnDelete(holder,convertView,position);
        this.setUpBtnEdit(holder,convertView,position);

        return convertView;
    }

    @Override
    protected void onclickDelete(int position) {
        SentencesDAO dao = new SentencesDAO(context);
        SentenceItem item = listSentences.get(position);
        dao.removeSentence(item.getId());
        Common.showToastMessage(getContext(), Message.ITEM_IS_DELETED(item.getNameJp()));
    }
}

