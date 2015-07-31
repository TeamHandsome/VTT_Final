package example.com.demoapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.SubPagerActivity;
import example.com.demoapp.activity.TagPagerActivity;
import example.com.demoapp.adapter.BaseListAdapter;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.DAO.HistoryDAO;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

/**
 * Created by Tony on 26/7/2015.
 */
public abstract class BaseListFragment extends Fragment{
    protected View view;
    protected ArrayList<SentenceItem> listSentences;
    protected Context context;
    protected int pager_parent;
    protected Bundle bundle;
    protected ImageView noData_view;

    public BaseListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = this.getArguments();
        pager_parent = bundle.getInt(Consts.PAGER_PARENT, Consts.NOT_FOUND);

        context = getActivity();

        switch (pager_parent){
            case Consts.HOME:
                this.initRecommendationList();
                this.initRecommendationView();
                break;
            case Consts.SENTENCE_LIST_BY_SUB:
                this.initListBySubList();
                this.initListBySubView();
                break;
            case Consts.FAVORITE:
                this.initFavoriteList();
                this.initFavoriteView();
                break;
            case Consts.HISTORY:
                this.initHistoryList();
                this.initHistoryView();
                break;
            case Consts.SENTENCE_LIST_BY_TAG:
                this.initListByTagList();
                this.initListByTagView();
                break;
            case Consts.MY_SENTENCE_LIST:
                this.initMySentenceList();
                this.initMySentenceView();
                break;
            default:
                Log.e("Parent haven't set yet","please set pager parent now");
                break;
        }

        return view;
    }

    //get recommendation sentence list from DB
    protected void initRecommendationList(){

    };

    //set View for recommendation list
    protected abstract void initRecommendationView();

    //get sentence list by sub list from DB
    protected void initListBySubList(){
        SentencesDAO dao = new SentencesDAO();
        int subCategory_id = bundle.getInt(Consts.SUBCATEGORY_ID, Consts.NOT_FOUND);
        listSentences = dao.getAllSentenceBySub(subCategory_id);
    };

    //set View for sentence list by sub list
    protected abstract void initListBySubView();

    //get history list from DB
    protected void initHistoryList(){
        HistoryDAO dao = new HistoryDAO();
        listSentences = dao.getAllHistory();
    };

    //set View for history list
    protected abstract void initHistoryView();

    //get favorite list from DB
    protected void initFavoriteList(){
        FavoriteDAO dao = new FavoriteDAO();
        listSentences = dao.getAllFavorite();
    };

    //set View for favorite list
    protected abstract void initFavoriteView();

    //get sentence list by tag list from DB
    protected void initListByTagList(){
        SentencesDAO dao = new SentencesDAO();
        String tag_id = bundle.getString(Consts.TAG_ID);
        listSentences = dao.getAllSentenceByTagID(tag_id);
    };

    //set View for sentence list by tag list
    protected abstract void initListByTagView();

    //get my sentence list from DB
    protected void initMySentenceList(){
        SentencesDAO dao = new SentencesDAO();
        listSentences = dao.getAllMySentence();   //get sentence list from DB
    };

    //set View for my sentence list
    protected abstract void initMySentenceView();

    //set no_data image view
    protected void setNoData_view(){
        noData_view = (ImageView) view.findViewById(R.id.no_data);
        Uri uri1 = StringUtils.buildDrawableUri(context.getPackageName(), "no_data");

        Picasso.with(context)
                .load(uri1)
                .resize(360, 360)
                .centerCrop()
                .into(noData_view);
    }
}
