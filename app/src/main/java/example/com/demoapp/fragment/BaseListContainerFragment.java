package example.com.demoapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.demoapp.R;
import example.com.demoapp.adapter.ViewPagerAdapter;
import example.com.demoapp.tabs.SlidingTabLayout;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

/**
 * Created by Tony on 2/8/2015.
 */
public abstract class BaseListContainerFragment extends Fragment {
    protected ViewPager pager;
    protected ViewPagerAdapter adapter;
    protected SlidingTabLayout tabs;
    protected CharSequence Titles[] = {StringUtils.addSpaceBetweenChar(Consts.SENTENCE_LIST),
            StringUtils.addSpaceBetweenChar(Consts.IMAGE_LIST)};
    protected int Numboftabs = 2;

    protected int parent_name;
    protected String navigation_text;
    protected String navigation_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_container, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        this.setInitFirstValue();
        Common.initNavigationHeaderView(view, container.getContext(), navigation_text, navigation_image);
        this.slidingTab(view);
        return view;

    }

    abstract void setInitFirstValue();

    private void slidingTab(View view) {
        // // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter((getActivity()).getSupportFragmentManager(), Titles,
                Numboftabs,parent_name);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
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
    }
}
