package example.com.demoapp.activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.SubCategoriesAdapter;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.model.DAO.SubCategoriesDAO;
import example.com.demoapp.model.SubCategoriesItem;
import example.com.demoapp.utility.StringUtils;

public class DisplaySubActivity extends ActionBarActivity {
    ListView listView;
    ArrayList<SubCategoriesItem> listSubcategories;
    SubCategoriesAdapter mSubCategoriesAdapter;
    private int category_id;
    private String category_name;
    private String navigation_text = "-";
    private String navigation_back_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        category_id = getIntent().getIntExtra(Consts.CATEGORY_ID, Consts.NOT_FOUND);
        category_name = getIntent().getStringExtra(Consts.CATEGORY_NAME);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //hien thi icon back

        initView();
    }
    public void initView (){
        listView = (ListView) findViewById(R.id.lvSubCategories);

        SubCategoriesDAO sub = new SubCategoriesDAO();
        listSubcategories = sub.getAllSubCategories(category_id);
        mSubCategoriesAdapter = new SubCategoriesAdapter(this, R.layout.activity_shopping_sub_item, listSubcategories);
        listView.setAdapter(mSubCategoriesAdapter);
        mSubCategoriesAdapter.notifyDataSetChanged();

        //set navigation text
        TextView textView = (TextView)findViewById(R.id.navigation_text);
        navigation_text = Consts.CONVERSATION + "-" + category_name;
        navigation_text = StringUtils.addSpaceBetweenChar(navigation_text);
        textView.setText(navigation_text);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), SubPagerActivity.class);
                SubCategoriesItem item = listSubcategories.get(position);
                //send subcategory id
                intent.putExtra(Consts.SUBCATEGORY_ID, item.getId());
                //send navigation text
                String text = category_name + "-" + item.getName();
                text = StringUtils.addSpaceBetweenChar(text);
                intent.putExtra(Consts.NAVIGATION_TEXT,text);
                //send navigation image url
                intent.putExtra(Consts.NAVIGATION_IMAGE,item.getImage_url());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.home){
            NavUtils.navigateUpFromSameTask(this); // khi click back icon se go back sourceAcitivy
        }

        return super.onOptionsItemSelected(item);
    }
}
