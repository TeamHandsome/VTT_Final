package example.com.demoapp.favorite;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditTagActivity;
import example.com.demoapp.adapter.BaseSentencesAdapter;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.subCategory.PopupActivity;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;

/**
 * Created by dmonkey on 7/15/2015.
 */
public class FavoriteSentencesAdapter extends BaseSentencesAdapter {

    public FavoriteSentencesAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
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

