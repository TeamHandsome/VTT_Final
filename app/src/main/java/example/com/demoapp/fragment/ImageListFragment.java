package example.com.demoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.daimajia.swipe.util.Attributes;

import example.com.demoapp.R;
import example.com.demoapp.activity.TagPagerActivity;
import example.com.demoapp.adapter.image_adapter.BaseImageAdapter;
import example.com.demoapp.adapter.image_adapter.FavoriteImageAdapter;
import example.com.demoapp.adapter.image_adapter.HistoryImageAdapter;
import example.com.demoapp.adapter.image_adapter.ImageAdapter;
import example.com.demoapp.adapter.image_adapter.MyImageAdapter;
import example.com.demoapp.adapter.image_adapter.TagImageAdapter;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 23/7/2015.
 */
public class ImageListFragment extends BaseListFragment {
    private GridView gridView;
    private BaseImageAdapter imageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_images, container, false);

        gridView = (GridView) view.findViewById(R.id.gridview);
        this.setGridView();

        return view;
    }

    private void setGridView(){
        ImageView no_data = (ImageView) view.findViewById(R.id.no_data);
        gridView.setEmptyView(no_data);
        gridView.setAdapter(imageAdapter);  //apply adapter to gridview
        imageAdapter.setMode(Attributes.Mode.Multiple);
    }

    @Override
    protected void initRecommendationView() {
        return;
    }

    @Override
    protected void initListBySubView(){
        imageAdapter = new ImageAdapter(getActivity(), R.layout.custom_row_img_f_t, listSentences);
    }

    @Override
    protected void initHistoryView(){
        imageAdapter = new HistoryImageAdapter(getActivity(), R.layout.custom_row_img_d, listSentences);
    }

    @Override
    protected void initFavoriteView(){
        imageAdapter = new FavoriteImageAdapter(getActivity(), R.layout.custom_row_img_d, listSentences);
    }

    @Override
    protected void initListByTagView(){
        imageAdapter = new TagImageAdapter(getActivity(),R.layout.custom_row_img_f_d,
                listSentences, TagPagerActivity.tag_id);
    }

    @Override
    protected void initMySentenceView(){
        imageAdapter = new MyImageAdapter(getActivity(), R.layout.custom_row_img_f_d_e, listSentences);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pager_parent == Consts.MY_SENTENCE_LIST){
            this.initMySentenceList();
            this.initMySentenceView();
            this.setGridView();
        }
    }
}
