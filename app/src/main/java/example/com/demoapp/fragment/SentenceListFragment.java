package example.com.demoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;

import example.com.demoapp.R;
import example.com.demoapp.activity.TagPagerActivity;
import example.com.demoapp.adapter.sentence_adapter.BaseSentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.HistorySentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.MySentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.SentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.FavoriteSentencesAdapter;
import example.com.demoapp.adapter.sentence_adapter.TagSentenceAdapter;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 23/7/2015.
 */
public class SentenceListFragment extends BaseListFragment {
    private ListView listView;

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
        listView.setAdapter(listAdapter);  //apply adapter into listView
        listAdapter.setMode(Attributes.Mode.Single);
    }

    @Override
    protected void initRecommendationView() {
        return;
    }

    @Override
    protected void initListBySubView(){
        listAdapter = new SentencesAdapter(getActivity(), R.layout.custom_row_sen_f_t, listSentences);
    }

    @Override
    protected void initHistoryView(){
        listAdapter = new HistorySentencesAdapter(getActivity(), R.layout.custom_row_sen_d, listSentences);
    }

    @Override
    protected void initFavoriteView(){
        listAdapter = new FavoriteSentencesAdapter(getActivity(), R.layout.custom_row_sen_d, listSentences);
    }

    @Override
    protected void initListByTagView(){
        String tag_id = bundle.getString(Consts.TAG_ID);
        listAdapter = new TagSentenceAdapter(getActivity(),R.layout.custom_row_sen_f_d,
                listSentences, tag_id);
    }

    @Override
    protected void initMySentenceView(){
        listAdapter = new MySentencesAdapter(getActivity(), R.layout.custom_row_sen_f_d_e, listSentences);
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
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {

            }
        }
    }
}
