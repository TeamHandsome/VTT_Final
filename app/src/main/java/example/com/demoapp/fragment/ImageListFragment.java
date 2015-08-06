package example.com.demoapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.daimajia.swipe.util.Attributes;

import example.com.demoapp.R;
import example.com.demoapp.adapter.image_adapter.BaseImageAdapter;
import example.com.demoapp.adapter.image_adapter.FavoriteImageAdapter;
import example.com.demoapp.adapter.image_adapter.HistoryImageAdapter;
import example.com.demoapp.adapter.image_adapter.ImageAdapter;
import example.com.demoapp.adapter.image_adapter.MyImageAdapter;
import example.com.demoapp.adapter.image_adapter.TagImageAdapter;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.MySingleton;

/**
 * Created by Tony on 23/7/2015.
 */
public class ImageListFragment extends BaseListFragment {
    private GridView gridView;
    public static BaseImageAdapter imageAdapter;

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
        this.setNoData_view();
        gridView.setEmptyView(noData_view);
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
        String tag_id = bundle.getString(Consts.TAG_ID);
        imageAdapter = new TagImageAdapter(getActivity(),R.layout.custom_row_img_f_d,
                listSentences, tag_id);
    }

    @Override
    protected void initMySentenceView(){
        imageAdapter = new MyImageAdapter(getActivity(), R.layout.custom_row_img_f_d_e, listSentences);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pager_parent == Consts.MY_SENTENCE_LIST){
            imageAdapter.setListSentences(listSentences);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(pager_parent == Consts.MY_SENTENCE_LIST && resultCode == Consts.FOUNDED){
            imageAdapter.setListSentences(listSentences);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible() && isVisibleToUser) {
            // If we are becoming invisible, then...
            if (listSentences!=null) {
                listSentences = MySingleton.getInstance().getSentenceList();;
                imageAdapter.setListSentences(listSentences);
                imageAdapter.notifyDataSetChanged();
            }
        }
    }
}
