package example.com.demoapp.adapter.image_adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 26/7/2015.
 */
public class TagImageAdapter extends BaseImageAdapter {
    private String tag_id;

    public TagImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences
            ,String tag_id) {
        super(context, idLayoutResource, listSentences);
        this.tag_id = tag_id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        this.setUpSound(holder, position);
        this.setUpBtnFavorite(holder, convertView, position);
        this.setUpBtnDelete(holder, convertView, position);
        return convertView;
    }

    @Override
    protected void onclickDelete(int position) {
        TagDAO dao = new TagDAO();
        SentenceItem item = listSentences.get(position);
        dao.removeSentenceFromTag(tag_id, item.getId());
    }

    @Override
    protected String setTitleConfirmDeleteDialog(){
        return Consts.DELETE_SEN_FROM_TAG;
    }
}
