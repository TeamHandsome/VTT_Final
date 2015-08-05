package example.com.demoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    private String navigation_image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        category_id = getIntent().getIntExtra(Consts.CATEGORY_ID, Consts.NOT_FOUND);
        category_name = getIntent().getStringExtra(Consts.CATEGORY_NAME);
        navigation_image = getIntent().getStringExtra(Consts.NAVIGATION_IMAGE);
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

        navigation_text = Consts.CONVERSATION + "-" + category_name;
        this.initNavigationHeaderView();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), SubPagerActivity.class);
                SubCategoriesItem item = listSubcategories.get(position);
                //send subcategory id
                intent.putExtra(Consts.SUBCATEGORY_ID, item.getId());
                //send navigation text
                String text = category_name + "-" + item.getName();
                intent.putExtra(Consts.NAVIGATION_TEXT, text);
                //send navigation image url
                intent.putExtra(Consts.NAVIGATION_IMAGE, navigation_image);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_search) {
            startActivity(new Intent(DisplaySubActivity.this, SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    protected void initNavigationHeaderView(){
        TextView textView = (TextView)findViewById(R.id.navigation_text);
        textView.setText(StringUtils.addSpaceBetweenChar(navigation_text));

        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.y;
        int height = size.x*407/1920;
        ImageView imageView = (ImageView)findViewById(R.id.navigation_back);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Uri uri=StringUtils.buildDrawableUri(getPackageName(),navigation_image);
        Picasso.with(this)
                .load(uri)
                .resize(width, height)
                .centerInside()
                .into(imageView);
    };
}
