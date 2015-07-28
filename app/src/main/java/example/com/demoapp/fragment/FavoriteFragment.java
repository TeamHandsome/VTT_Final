package example.com.demoapp.fragment;


import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import example.com.demoapp.R;
import example.com.demoapp.adapter.ViewPagerAdapter;
import example.com.demoapp.tabs.SlidingTabLayout;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

public class FavoriteFragment extends Fragment {
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    CharSequence Titles[] = {StringUtils.addSpaceBetweenChar(Consts.SENTENCE_LIST),
            StringUtils.addSpaceBetweenChar(Consts.IMAGE_LIST)};
    int Numboftabs = 2;
    private String navigation_text = Consts.FAVORITE_LIST;
    private String navigation_image = "navi_back_favorite";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        Common.initNavigationHeaderView(view,container.getContext(),navigation_text, navigation_image);

        adapter = new ViewPagerAdapter((getActivity()).getSupportFragmentManager(), Titles,
                Numboftabs,Consts.FAVORITE);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) view.findViewById(R.id.pager1);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs1);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);


        return view;

    }

    public void slidingTab() {
        // // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.

    }
}

