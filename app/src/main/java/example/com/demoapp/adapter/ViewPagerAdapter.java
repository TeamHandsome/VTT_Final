package example.com.demoapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Objects;

import example.com.demoapp.activity.CategoryFragment;
import example.com.demoapp.activity.SuggestFragment;
import example.com.demoapp.fragment.ImageListFragment;
import example.com.demoapp.fragment.SentenceListFragment;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 23/7/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    public static int pager_parent = -1;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence[] mTitles, int mNumbOfTabsumb,
                            int pager_parent) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.pager_parent = pager_parent;
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
            }
        }
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
}
