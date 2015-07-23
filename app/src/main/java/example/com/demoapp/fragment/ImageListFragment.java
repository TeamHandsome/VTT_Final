package example.com.demoapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.image_adapter.BaseImageAdapter;
import example.com.demoapp.adapter.image_adapter.FavoriteImageAdapter;
import example.com.demoapp.adapter.image_adapter.HistoryImageAdapter;
import example.com.demoapp.adapter.image_adapter.ImageAdapter;
import example.com.demoapp.adapter.image_adapter.MyImageAdapter;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.DAO.HistoryDAO;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.activity.DisplaySentencesActivity;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 23/7/2015.
 */
public class ImageListFragment extends Fragment {
    GridView gridView;
    ArrayList<SentenceItem> listSentences;
    BaseImageAdapter imageAdapter;
    Context context;
    int pager_parent = Consts.NOT_FOUND;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pager_parent = (int)container.getTag();
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_list_images, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);

        switch (pager_parent){
            case Consts.SENTENCE_LIST_BY_SUB:
                this.initListBySubView();
                break;
            case Consts.FAVORITE:
                this.initFavoriteView();
                break;
            case Consts.HISTORY:
                this.initHistoryView();
                break;
            case Consts.SENTENCE_LIST_BY_TAG:
                this.initListByTagView();
                break;
            case Consts.MY_SENTENCE_LIST:
                this.initMySentenceView();
                break;
        }

        gridView.setAdapter(imageAdapter);  //apply adapter to gridview
        imageAdapter.setMode(Attributes.Mode.Multiple);

        return view;
    }

    private void initListBySubView(){
        SentencesDAO dao = new SentencesDAO();
        listSentences = dao.getAllSentenceBySub(DisplaySentencesActivity.subCategory_id);
        imageAdapter = new ImageAdapter(getActivity(), R.layout.custom_row_img_f_t, listSentences);
    }

    private void initHistoryView(){
        HistoryDAO dao = new HistoryDAO(context);
        listSentences = dao.getAllHistory();
        imageAdapter = new HistoryImageAdapter(getActivity(), R.layout.custom_row_img_h, listSentences);

    }

    private void initFavoriteView(){
        FavoriteDAO dao = new FavoriteDAO(context);
        listSentences = dao.getAllFavorite();
        imageAdapter = new FavoriteImageAdapter(getActivity(), R.layout.custom_row_img_d, listSentences);
    }

    private void initListByTagView(){
        SentencesDAO dao = new SentencesDAO(context);

    }

    private void initMySentenceView(){
        SentencesDAO dao = new SentencesDAO(context);
        listSentences = dao.getAllMySentence();   //get sentence list from DB
        imageAdapter = new MyImageAdapter(getActivity(), R.layout.custom_row_img_ms, listSentences);
    }
}
