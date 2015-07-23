package example.com.demoapp.subCategory;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import example.com.demoapp.R;
import example.com.demoapp.adapter.ViewPagerSentenceAdapter;
import example.com.demoapp.tabs.SlidingTabLayout;
import example.com.demoapp.utility.Consts;

public class DisplaySentencesActivity extends ActionBarActivity {
    private ViewPager pager;
    private ViewPagerSentenceAdapter adapter;
    private SlidingTabLayout tabs;
    CharSequence Titles[]={"文章","画像"};
    int Numboftabs =2;

    public static int subCategory_id = Consts.NOT_FOUND;
    public static String navigation_text = "";
    public static String navigation_image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.slidingTab();
        pager.setCurrentItem(0);

        navigation_text = getIntent().getStringExtra(Consts.NAVIGATION_TEXT);
        navigation_image = getIntent().getStringExtra(Consts.NAVIGATION_IMAGE);
        initView();

        subCategory_id = getIntent().getIntExtra(Consts.SUBCATEGORY_ID, Consts.NOT_FOUND);
        if (subCategory_id != Consts.NOT_FOUND) {
            ListSentencesFragment objfrag = ListSentencesFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.pager, objfrag);
            transaction.commit();
        }


    }

    private void initView() {
        TextView textView = (TextView)findViewById(R.id.navigation_text);
        textView.setText(navigation_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void slidingTab(){
        // // Creating The ViewPagerSentenceAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerSentenceAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

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
