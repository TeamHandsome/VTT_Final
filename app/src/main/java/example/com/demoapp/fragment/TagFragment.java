package example.com.demoapp.fragment;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditMySentencesActivity;
import example.com.demoapp.activity.TagPagerActivity;
import example.com.demoapp.adapter.TagListAdapter;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.TagItem;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

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


        setHasOptionsMenu(true);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_tag, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(true);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        //change default button close
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView imageView = (ImageView) searchView.findViewById(id);
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_navigation_close));
        //change default icon search
        int id1 = searchView.getContext().getResources().getIdentifier("android:id/search_button", null, null);
        ImageView imageView1 = (ImageView) searchView.findViewById(id1);
        if (imageView1!=null){
            imageView1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_search));
        }

        //Set the plate color to White
        int linlayId = getResources().getIdentifier("android:id/search_plate", null, null);
        View view = searchView.findViewById(linlayId);
        Drawable drawColor = getResources().getDrawable(R.drawable.searchcolor);
        view.setBackground(drawColor);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    lv_tags.clearTextFilter();
                } else {
                    lv_tags.setFilterText(newText);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void initView() {
        tagDAO =  new TagDAO();
        TagDAO tagDAO = new TagDAO();
        listTags = tagDAO.getAllTagFromTags();  //gán dữ liệu từ database vào mảng ArrayList
        mTagListAdapter = new TagListAdapter(getActivity(), R.layout.fragment_tag_item, listTags); //gán qua Adapter
        lv_tags.setAdapter(mTagListAdapter);  //từ Adapter lên listview
        lv_tags.setTextFilterEnabled(true);
        mTagListAdapter.setMode(Attributes.Mode.Single);
    }

    @Override
    public void onResume() {
        super.onResume();
        listTags = tagDAO.getAllTagFromTags();
        mTagListAdapter.setListTags(listTags);
        mTagListAdapter.notifyDataSetChanged();
        setHeaderView();
    }

    private void setHeaderView(){
        ArrayList<String> IdTags = tagDAO.getIdFromTags();
        ArrayList<String> IdTagging = tagDAO.getIdFromTagging();
        List result = new ArrayList(IdTags);
        result.removeAll(IdTagging);
        Log.d("RESULT : ", result+"");
        int sizeNoUse = result.size();
        if (sizeNoUse < 10 ){
            tv_noUseTag.setText("0"+sizeNoUse);
        }else {
            tv_noUseTag.setText(sizeNoUse+"");
        }
        if (IdTags.size() < 10 ){
            tv_usedTag.setText("0"+IdTags.size());
        }else{
            tv_usedTag.setText(IdTags.size()+"");
        }
    }
}
