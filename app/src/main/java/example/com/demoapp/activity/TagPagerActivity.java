package example.com.demoapp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;

/**
 * Created by Tony on 26/7/2015.
 */
public class TagPagerActivity extends BasePagerActivity {
    public static String tag_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tag_id = getIntent().getStringExtra(Consts.TAG_ID);
    }

    @Override
    protected void initFirstValue() {
        navigation_text = getIntent().getStringExtra(Consts.NAVIGATION_TEXT);
        navigation_image = "";
        pager_tag = Consts.SENTENCE_LIST_BY_TAG;
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
}
