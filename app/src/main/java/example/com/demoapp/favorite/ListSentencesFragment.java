package example.com.demoapp.favorite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.DbHelper;


public class ListSentencesFragment extends android.support.v4.app.Fragment{

    ListView listView;
    ArrayList<SentenceItem> listSentences;
    ListSenAdapter mSentencesAdapter;
    Context context;

    private DbHelper db ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_sentences_favorite, container, false);
        listView = (ListView) view.findViewById(R.id.lv_SentencesFavorite);
        context = getActivity();
        FavoriteDAO senDao = new FavoriteDAO(context);
        listSentences = senDao.getAllFavorite();   //g�n d? li?u t? database v�o m?ng ArrayList
        mSentencesAdapter = new ListSenAdapter(getActivity(), R.layout.favorite_row, listSentences); //g�n qua Adapter
        listView.setAdapter(mSentencesAdapter);  //t? Adapter l�n listview
        mSentencesAdapter.setMode(Attributes.Mode.Single);


        return view;
    }

}
