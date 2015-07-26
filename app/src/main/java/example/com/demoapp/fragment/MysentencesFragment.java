package example.com.demoapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditMySentencesActivity;
import example.com.demoapp.adapter.ViewPagerAdapter;
import example.com.demoapp.tabs.SlidingTabLayout;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

public class MysentencesFragment extends Fragment {private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    CharSequence Titles[] = {StringUtils.addSpaceBetweenChar(Consts.SENTENCE_LIST),
            StringUtils.addSpaceBetweenChar(Consts.IMAGE_LIST)};
    int Numboftabs = 2;
    private String navigation_text = Consts.MY_SEN_LIST;
    private String navigation_image = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mysentences, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initNavigationHeaderView(view);
        slidingTab(view);


        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_mysentences, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_addNew){
           Intent intent = new Intent(getActivity().getApplicationContext(), AddEditMySentencesActivity.class);
            intent.putExtra(Consts.ACTION_TYPE, Consts.ADD_MY_SEN);
            getActivity().startActivityForResult(intent, 14);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initNavigationHeaderView(final View view) {
        TextView textView = (TextView)view.findViewById(R.id.navigation_text);
        textView.setText(StringUtils.addSpaceBetweenChar(navigation_text));
    }

    public void slidingTab(View view) {
        adapter = new ViewPagerAdapter((getActivity()).getSupportFragmentManager(), Titles,
                Numboftabs,Consts.MY_SENTENCE_LIST);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) view.findViewById(R.id.pagerMS);
        pager.setAdapter(adapter);
        pager.setTag(Consts.MY_SENTENCE_LIST);
        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabMS);
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
