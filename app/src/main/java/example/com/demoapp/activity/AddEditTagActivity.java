package example.com.demoapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.tony.taglibrary.TagView;

import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.adapter.SentencesAdapter;
import example.com.demoapp.adapter.TagAdapter;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.DbHelper;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.TagItem;
import com.example.tony.taglibrary.OnTagDeleteListener;
import com.example.tony.taglibrary.Tag;
import com.example.tony.taglibrary.TagView;

public class AddEditTagActivity extends ActionBarActivity {

    private AutoCompleteTextView autoComplete;
    private ArrayList<TagItem> arrayTag;   //to handles AutoCompleteTextView
    private List<String> arrayTagging;    //to handle tag available
    private TagAdapter mTagAdapter;
    int sentences_id;
    final TagDAO tag = new TagDAO(this);

    List<String> arrayDynamicTag = new ArrayList();  //to handle dynamic user add tag
    TagView tagView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_tag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //hien thi icon back

        tagView = (TagView) findViewById(R.id.tagview);

        sentences_id = getIntent().getIntExtra(Consts.SENTENCE_ID, Consts.NOT_FOUND);
        //list tag available from DB
        arrayTagging = tag.getTagsFromTagging(sentences_id);
        for (String i : arrayTagging) {
            this.addNewTagToTagView(tagView, i);
        }

        initView();

        //Event for Button Add Tag
        Button bt_addTag = (Button) findViewById(R.id.bt_addtag1);
        bt_addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTag = autoComplete.getText().toString();
                if (!textTag.isEmpty()) {
                    //arrayDynamicTag.add(textTag);  //add text Tag tu autoCompleteTextView vao tag Dynamic

                    if (isNotTagged(textTag)) {
                        addNewTagToTagView(tagView, textTag);
                        arrayTagging.add(textTag);
                        reloadArrayTagForAutocompleteBox();
                    } else {
                        //write some code here
                        Log.i("aaaaa", "duplicate");
                    }

                    autoComplete.setText("");
                }

                Log.d("Array Tagging:", arrayTagging + "");
            }
        });
        tagView.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int position) {
                Toast.makeText(AddEditTagActivity.this, "delete tag id=" + tag.id + " position=" + position, Toast.LENGTH_SHORT).show();
                arrayTagging.remove(position);
            }
        });
        //// Event for Button Accept + Cancel
        findViewById(R.id.bt_accept).setOnClickListener(listener);
        findViewById(R.id.bt_cancel1).setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_accept:
                    Log.d("Count all tags: ", tag.countTags() + "");
                    tag.addTagToTags(sentences_id,arrayTagging);

                    // tag.addTagToTagging(arrayDynamicTag);
                    finish();
                    break;
                case R.id.bt_cancel1:
                    finish();
                    break;
            }
        }
    };


    public void initView() {
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        this.reloadArrayTagForAutocompleteBox();
        autoComplete.setThreshold(1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void addNewTagToTagView(TagView tagView, String tagName) {
        Tag tag = new Tag(tagName);
        tag.layoutBorderSize = 1f;
        tag.layoutBorderColor = getResources().getColor(R.color.colorAccent);
        tag.tagTextSize = 20f;
        tag.radius = 20f;
        tag.isDeletable = true;

        tagView.addTag(tag);
    }


    private void reloadArrayTagForAutocompleteBox() {
        arrayTag = tag.getAllTagFromTagsIgnoreItems(arrayTagging);
        mTagAdapter = new TagAdapter(this, R.layout.activity_add_edit_tag_item, arrayTag);
        autoComplete.setAdapter(mTagAdapter);
    }

    private boolean isNotTagged(String tagName) {   //handle exception if duplicate available Tag
        for (String i : arrayTagging) {
            if (i.equalsIgnoreCase(tagName))
                return false;
        }
        return true;
    }

    private boolean isNotUserAdd(String tagName) {  //handle exception if duplicate dynamic Tag
        for (String i : arrayDynamicTag) {
            if (i.equalsIgnoreCase(tagName))
                return false;
        }
        return true;
    }


}
