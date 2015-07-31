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
import example.com.demoapp.utility.MySingleton;
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
        listSentences = MySingleton.getInstance().getSentenceList();
        context = getActivity();

        switch (pager_parent){
            case Consts.HOME:
                this.initRecommendationView();
                break;
            case Consts.SENTENCE_LIST_BY_SUB:
                this.initListBySubView();
                break;
            case Consts.FAVORITE:
                this.initFavoriteView();
                break;
            case Consts.HISTORY:
                this.initHistoryView();
                break;
            case Consts.SENTENCE_LIST_BY_TAG:
                this.initListByTagView();
                break;
            case Consts.MY_SENTENCE_LIST:
                this.initMySentenceView();
                break;
            default:
                Log.e("Parent haven't set yet","please set pager parent now");
                break;
        }

        return view;
    }

    //get recommendation sentence list from DB
    public ArrayList<SentenceItem> initRecommendationList(){
        return listSentences;
    };

    //set View for recommendation list
    protected abstract void initRecommendationView();

    //get sentence list by sub list from DB
    public ArrayList<SentenceItem> initListBySubList(int subCategory_id){
        SentencesDAO dao = new SentencesDAO();
        return listSentences = dao.getAllSentenceBySub(subCategory_id);
    };

    //set View for sentence list by sub list
    protected abstract void initListBySubView();

    //get history list from DB
    public ArrayList<SentenceItem> initHistoryList(){
        HistoryDAO dao = new HistoryDAO();
        return listSentences = dao.getAllHistory();
    };

    //set View for history list
    protected abstract void initHistoryView();

    //get favorite list from DB
    public ArrayList<SentenceItem> initFavoriteList(){
        FavoriteDAO dao = new FavoriteDAO();
        return listSentences = dao.getAllFavorite();
    };

    //set View for favorite list
    protected abstract void initFavoriteView();

    //get sentence list by tag list from DB
    public ArrayList<SentenceItem> initListByTagList(String tag_id){
        SentencesDAO dao = new SentencesDAO();
        return listSentences = dao.getAllSentenceByTagID(tag_id);
    };

    //set View for sentence list by tag list
    protected abstract void initListByTagView();

    //get my sentence list from DB
    public ArrayList<SentenceItem> initMySentenceList(){
        SentencesDAO dao = new SentencesDAO();
        return listSentences = dao.getAllMySentence();   //get sentence list from DB
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
