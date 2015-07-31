package example.com.demoapp.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import example.com.demoapp.fragment.BaseListFragment;
import example.com.demoapp.fragment.CategoryFragment;
import example.com.demoapp.fragment.SuggestFragment;
import example.com.demoapp.fragment.ImageListFragment;
import example.com.demoapp.fragment.SentenceListFragment;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.MySingleton;

/**
 * Created by Tony on 23/7/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    private int pager_parent = -1;
    Bundle bundle = new Bundle();;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence[] mTitles, int mNumbOfTabsumb,
                            int pager_parent) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.pager_parent = pager_parent;
        this.setData();
    }

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence[] mTitles, int mNumbOfTabsumb,
                            int pager_parent, Bundle bundle) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.pager_parent = pager_parent;
        this.bundle = bundle;
        this.setData();
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        Fragment tab = new Fragment();
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            switch (pager_parent){
                case Consts.HOME:
                    tab = new SuggestFragment();
                    break;
                case Consts.SENTENCE_LIST_BY_SUB:
                    tab = new SentenceListFragment();
                    break;
                case Consts.FAVORITE:
                    tab = new SentenceListFragment();
                    break;
                case Consts.HISTORY:
                    tab = new SentenceListFragment();
                    break;
                case Consts.SENTENCE_LIST_BY_TAG:
                    tab = new SentenceListFragment();
                    break;
                case Consts.MY_SENTENCE_LIST:
                    tab = new SentenceListFragment();
                    break;
                default:
                    Log.e("Parent haven't set yet","please set pager parent now");
                    break;
            }

        }
        else // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            switch (pager_parent){
                case Consts.HOME:
                    tab = new CategoryFragment();
                    break;
                case Consts.SENTENCE_LIST_BY_SUB:
                    tab = new ImageListFragment();
                    break;
                case Consts.FAVORITE:
                    tab = new ImageListFragment();
                    break;
                case Consts.HISTORY:
                    tab = new ImageListFragment();
                    break;
                case Consts.SENTENCE_LIST_BY_TAG:
                    tab = new ImageListFragment();
                    break;
                case Consts.MY_SENTENCE_LIST:
                    tab = new ImageListFragment();
                    break;
                default:
                    Log.e("Parent haven't set yet","please set pager parent now");
                    break;
            }
        }
        //Put bundle and send to fragment tab
        bundle.putInt(Consts.PAGER_PARENT, pager_parent);
        tab.setArguments(bundle);
        return tab;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    private void setData(){
        BaseListFragment fragment = new SentenceListFragment();
        ArrayList<SentenceItem> sentenceList = null;
        switch (pager_parent){
            case Consts.HOME:
                sentenceList = fragment.initRecommendationList();
                break;
            case Consts.SENTENCE_LIST_BY_SUB:
                int sub_id = bundle.getInt(Consts.SUBCATEGORY_ID,Consts.NOT_FOUND);
                sentenceList = fragment.initListBySubList(sub_id);
                break;
            case Consts.FAVORITE:
                sentenceList  = fragment.initFavoriteList();
                break;
            case Consts.HISTORY:
                sentenceList = fragment.initHistoryList();
                break;
            case Consts.SENTENCE_LIST_BY_TAG:
                String tag_id = bundle.getString(Consts.TAG_ID);
                sentenceList = fragment.initListByTagList(tag_id);
                break;
            case Consts.MY_SENTENCE_LIST:
                sentenceList = fragment.initMySentenceList();
                break;
            default:
                Log.e("Parent haven't set yet","please set pager parent now");
                break;
        }
        //set to singleton for global use
        MySingleton.getInstance().setSentenceList(sentenceList);
    }

}
