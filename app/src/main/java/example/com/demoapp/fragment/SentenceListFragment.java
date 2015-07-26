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

/**
 * Created by Tony on 23/7/2015.
 */
public class SentenceListFragment extends BaseListFragment {
    ListView listView;
    BaseSentencesAdapter sentencesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_sentences, container, false);

        listView = (ListView) view.findViewById(R.id.lvSentences);
        ImageView no_data = (ImageView) view.findViewById(R.id.no_data);
        listView.setEmptyView(no_data);
        listView.setAdapter(sentencesAdapter);  //apply adapter into listView
        sentencesAdapter.setMode(Attributes.Mode.Single);

        return view;
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
        sentencesAdapter = new TagSentenceAdapter(getActivity(),R.layout.custom_row_sen_f_d,
                listSentences, TagPagerActivity.tag_id);
    }

    @Override
    protected void initMySentenceView(){
        sentencesAdapter = new MySentencesAdapter(getActivity(), R.layout.custom_row_sen_f_d_e, listSentences);
    }
}
