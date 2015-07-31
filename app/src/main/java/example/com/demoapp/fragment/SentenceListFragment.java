package example.com.demoapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;

import example.com.demoapp.R;
import example.com.demoapp.adapter.sentence_adapter.BaseSentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.HistorySentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.MySentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.SentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.FavoriteSentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.TagSentenceAdapter;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.MySingleton;

/**
 * Created by Tony on 23/7/2015.
 */
public class SentenceListFragment extends BaseListFragment {
    private ListView listView;
    public static BaseSentencesAdapter sentencesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_sentences, container, false);

        listView = (ListView) view.findViewById(R.id.lvSentences);
        this.setListView();

        return view;
    }

    private void setListView(){
        this.setNoData_view();
        listView.setEmptyView(noData_view);
        listView.setAdapter(sentencesAdapter);  //apply adapter into listView
        sentencesAdapter.setMode(Attributes.Mode.Single);
    }

    @Override
    protected void initRecommendationView() {
        return;
    }

    @Override
    protected void initListBySubView(){
        sentencesAdapter = new SentencesAdapter(getActivity(), R.layout.custom_row_sen_f_t, listSentences);
    }

    @Override
    protected void initHistoryView(){
        sentencesAdapter = new HistorySentencesAdapter(getActivity(), R.layout.custom_row_sen_d, listSentences);
    }

    @Override
    protected void initFavoriteView(){
        sentencesAdapter = new FavoriteSentencesAdapter(getActivity(), R.layout.custom_row_sen_d, listSentences);
    }

    @Override
    protected void initListByTagView(){
        String tag_id = bundle.getString(Consts.TAG_ID);
        sentencesAdapter = new TagSentenceAdapter(getActivity(),R.layout.custom_row_sen_f_d,
                listSentences, tag_id);
    }

    @Override
    protected void initMySentenceView(){
        sentencesAdapter = new MySentencesAdapter(getActivity(), R.layout.custom_row_sen_f_d_e, listSentences);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pager_parent == Consts.MY_SENTENCE_LIST){
            this.initMySentenceList();
            this.initMySentenceView();
            this.setListView();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible() && isVisibleToUser) {
                if (listSentences!=null) {
                    listSentences = MySingleton.getInstance().getSentenceList();
                    sentencesAdapter.setListSentences(listSentences);
                    sentencesAdapter.notifyDataSetChanged();
                }
        }
    }
}
