package example.com.demoapp.adapter.image_adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.com.demoapp.adapter.image_adapter.BaseImageAdapter;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Message;

/**
 * Created by Tony on 23/7/2015.
 */
public class FavoriteImageAdapter extends BaseImageAdapter {

    public FavoriteImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
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
        FavoriteDAO dao = new FavoriteDAO(context);
        SentenceItem item = listSentences.get(position);
        dao.removeFromFavorite(item.getId());
        Common.showToastMessage(getContext(), Message.FAVORITE_UN_SIGN);
    }
}
