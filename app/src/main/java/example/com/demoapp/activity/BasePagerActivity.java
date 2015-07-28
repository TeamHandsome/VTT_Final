package example.com.demoapp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import example.com.demoapp.R;
import example.com.demoapp.adapter.ViewPagerAdapter;
import example.com.demoapp.tabs.SlidingTabLayout;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

/**
 * Created by Tony on 26/7/2015.
 */
public abstract class BasePagerActivity extends ActionBarActivity {
    protected ViewPager pager;
    protected ViewPagerAdapter adapter;
    protected SlidingTabLayout tabs;
    protected CharSequence Titles[]={StringUtils.addSpaceBetweenChar(Consts.SENTENCE_LIST),
            StringUtils.addSpaceBetweenChar(Consts.IMAGE_LIST)};
    protected int Numboftabs =2;
    protected int pager_parent = -1;
    protected int current_item = 0;

    protected String navigation_text = "";
    protected String navigation_image = "";

    protected Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initFirstValue();
        setContentView(R.layout.activity_display_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.slidingTab();
        initNavigationHeaderView();
    }

    /*You need to init the following variable
    navigation_text: text to display text on navigation header
    navigation_image: uri to display image on navigation header
    pager_tag: or we can say pager parent name
     */
    protected abstract void initFirstValue();

    protected void initNavigationHeaderView(){
        TextView textView = (TextView)findViewById(R.id.navigation_text);
        textView.setText(navigation_text);
    };

    protected void slidingTab(){
        // // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs,
                pager_parent);
        adapter.setBundle(bundle);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(current_item);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
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
    }
}
