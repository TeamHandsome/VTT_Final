package example.com.demoapp.subCategory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import com.daimajia.swipe.util.Attributes;

import example.com.demoapp.R;
import example.com.demoapp.adapter.SentencesAdapter;
import example.com.demoapp.utility.DbHelper;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.DisplaySentencesItem;


public class ListSentencesFragment extends Fragment {
    ListView listView;
    ArrayList<DisplaySentencesItem> listSentences;
    SentencesAdapter mSentencesAdapter;

    private DbHelper db ;

    public static ListSentencesFragment newInstance() {
        ListSentencesFragment instance = new ListSentencesFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_sentences, container, false);
        listView = (ListView) view.findViewById(R.id.lvSentences);
        initView(view);

        return view;
    }

    private void initView(View v) {

        try {
            db = new DbHelper(getActivity());
            db.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SentencesDAO senDao = new SentencesDAO();
        listSentences = senDao.getAllSentenceBySub(DisplaySentencesActivity.subCategory_id);   //gán dữ liệu từ database vào mảng ArrayList
        mSentencesAdapter = new SentencesAdapter(getActivity(), R.layout.custom_row, listSentences); //gán qua Adapter
        listView.setAdapter(mSentencesAdapter);  //từ Adapter lên listview
        mSentencesAdapter.setMode(Attributes.Mode.Single);

    }
}
