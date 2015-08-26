package example.com.demoapp.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 26/7/2015.
 */
public class TagPagerActivity extends BasePagerActivity {

    @Override
    protected void initFirstValue() {
        navigation_text = getIntent().getStringExtra(Consts.NAVIGATION_TEXT);
        navigation_image = Consts.NAVI_BACK_TAG;
        pager_parent = Consts.SENTENCE_LIST_BY_TAG;
        String tag_id = getIntent().getStringExtra(Consts.TAG_ID);
        bundle.putString(Consts.TAG_ID,tag_id);
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
            case R.id.search_sen:
                startActivity(new Intent(TagPagerActivity.this, SearchActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
