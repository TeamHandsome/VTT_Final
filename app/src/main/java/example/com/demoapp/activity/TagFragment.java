package example.com.demoapp.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.adapter.SentencesAdapter;
import example.com.demoapp.adapter.TagListAdapter;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.model.TagItem;
import example.com.demoapp.subCategory.DisplaySentencesActivity;

public class TagFragment extends Fragment {
    public TagFragment() {
    }

    ListView lv_tags;
    ArrayList<TagItem> listTags;
    TagListAdapter mTagListAdapter;
    TextView tv_noUseTag,tv_usedTag;
    TagDAO tagDAO =null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        lv_tags = (ListView) view.findViewById(R.id.lv_tags);
        tv_usedTag = (TextView) view.findViewById(R.id.tv_usedTag);
        tv_noUseTag = (TextView) view.findViewById(R.id.tv_noUseTag);
        initView();

        ArrayList<String> IdTags = tagDAO.getIdFromTags();
        ArrayList<String> IdTagging = tagDAO.getIdFromTagging();
        List result = new ArrayList(IdTags);
        result.removeAll(IdTagging);
        Log.d("RESULT : ", result+"");
        int sizeNoUse = result.size();
        tv_noUseTag.setText(sizeNoUse+"");
        tv_usedTag.setText((IdTags.size()-sizeNoUse)+"");

        return view;
    }

    public void initView() {
        tagDAO =  new TagDAO(getActivity().getApplicationContext());
        TagDAO tagDAO = new TagDAO(getActivity().getApplicationContext());
        listTags = tagDAO.getAllTagFromTags();  //gán dữ liệu từ database vào mảng ArrayList
        mTagListAdapter = new TagListAdapter(getActivity(), R.layout.fragment_tag_item, listTags); //gán qua Adapter
        lv_tags.setAdapter(mTagListAdapter);  //từ Adapter lên listview
        mTagListAdapter.setMode(Attributes.Mode.Single);
    }
}
