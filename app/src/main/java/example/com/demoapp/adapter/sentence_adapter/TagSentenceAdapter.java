package example.com.demoapp.adapter.sentence_adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Message;

/**
 * Created by Tony on 26/7/2015.
 */
public class TagSentenceAdapter extends BaseSentencesAdapter {
    private String tag_id;

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public TagSentenceAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        this.setUpSound(holder, position);
        this.setUpBtnFavorite(holder, convertView, position);
        this.setUpBtnDelete(holder, convertView, position);
        return convertView;
    }

    @Override
    protected void onclickDelete(int position) {
        TagDAO dao = new TagDAO(context);
        SentenceItem item = listSentences.get(position);
        dao.removeSentenceFromTag(tag_id,item.getId());
        Common.showToastMessage(getContext(), Message.ITEM_IS_DELETED(item.getNameJp()));
    }
}
