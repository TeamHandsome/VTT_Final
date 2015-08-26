package example.com.demoapp.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.SearchAdapter;
import example.com.demoapp.adapter.sentence_adapter.BaseSentencesAdapter;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.SentenceItem;

public class SearchActivity extends AppCompatActivity {
    Context context;
    SearchView searchView;
    ListView listView;
    ArrayList<SentenceItem> listSearch;
    BaseSentencesAdapter sentencesAdapter;
    SearchAdapter searchAdapter;
    SentencesDAO sentencesDAO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  //hien thi toolbar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.lv_search);
        //  searchView = (SearchView) findViewById(R.id.searchView1);
        sentencesDAO = new SentencesDAO();
        listSearch = sentencesDAO.getAllSentences();
        searchAdapter = new SearchAdapter(this, R.layout.custom_row_sen_f_t, listSearch);
        listView.setAdapter(searchAdapter);
        listView.setTextFilterEnabled(true);
        searchAdapter.setMode(Attributes.Mode.Single);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        //change default button close
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView imageView = (ImageView) searchView.findViewById(id);
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_navigation_close));
        //change default icon search
        int id1 = searchView.getContext().getResources().getIdentifier("android:id/search_button", null, null);
        ImageView imageView1 = (ImageView) searchView.findViewById(id1);
        if (imageView1!=null){
            imageView1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_search));
        }

        //Set the plate color to White
        int linlayId = getResources().getIdentifier("android:id/search_plate", null, null);
        View view = searchView.findViewById(linlayId);
        Drawable drawColor = getResources().getDrawable(R.drawable.searchcolor);
        view.setBackground(drawColor);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    listView.clearTextFilter();
                } else {
                    listView.setFilterText(newText);
                }
                return true;
            }
        });
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


}
