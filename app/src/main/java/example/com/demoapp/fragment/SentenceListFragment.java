package example.com.demoapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.BaseSentencesAdapter;
import example.com.demoapp.adapter.MySentencesAdapter;
import example.com.demoapp.adapter.SentencesAdapter;
import example.com.demoapp.adapter.FavoriteSentencesAdapter;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.DAO.MySentencesDAO;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.activity.DisplaySentencesActivity;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 23/7/2015.
 */
public class SentenceListFragment extends Fragment {
    ListView listView;
    ArrayList<SentenceItem> listSentences;
    BaseSentencesAdapter sentencesAdapter;
    Context context;
    int pager_parent = Consts.NOT_FOUND;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pager_parent = (int)container.getTag();
        context = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_sentences, container, false);

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
        listView = (ListView) view.findViewById(R.id.lvSentences);

        listView.setAdapter(sentencesAdapter);  //apply adapter into listView
        sentencesAdapter.setMode(Attributes.Mode.Single);

        return view;
    }

    private void initListBySubView(){
        SentencesDAO dao = new SentencesDAO(context);
        listSentences = dao.getAllSentenceBySub(DisplaySentencesActivity.subCategory_id);
        sentencesAdapter = new SentencesAdapter(getActivity(), R.layout.custom_row_sen_f_t, listSentences);
    }

    private void initHistoryView(){

    }

    private void initFavoriteView(){
        FavoriteDAO dao = new FavoriteDAO(context);
        listSentences = dao.getAllFavorite();   //get sentence list from DB
        sentencesAdapter = new FavoriteSentencesAdapter(getActivity(), R.layout.custom_row_sen_d, listSentences); //add to adapter
    }

    private void initListByTagView(){
        SentencesDAO dao = new SentencesDAO(context);

    }

    private void initMySentenceView(){
        MySentencesDAO dao = new MySentencesDAO(context);
        listSentences = dao.getAllMS();   //get sentence list from DB
        sentencesAdapter = new MySentencesAdapter(getActivity(), R.layout.custom_row_sen_ms, listSentences); //add to adapter
    }
}
