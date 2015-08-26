package example.com.demoapp.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;

public class SubPagerActivity extends BasePagerActivity {

    @Override
    protected void initFirstValue() {
        navigation_text = getIntent().getStringExtra(Consts.NAVIGATION_TEXT);
        navigation_image = getIntent().getStringExtra(Consts.NAVIGATION_IMAGE);
        pager_parent = Consts.SENTENCE_LIST_BY_SUB;
        int subCategory_id = getIntent().getIntExtra(Consts.SUBCATEGORY_ID, Consts.NOT_FOUND);
        bundle.putInt(Consts.SUBCATEGORY_ID,subCategory_id);
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
                startActivity(new Intent(SubPagerActivity.this, SearchActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
