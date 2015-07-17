package example.com.demoapp.activity;

import android.content.Intent;
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

public class AddTagToMySentences extends ActionBarActivity {

    private AutoCompleteTextView autoComplete2;
    private ArrayList<TagItem> arrayTag;   //to handles AutoCompleteTextView
    private TagAdapter mTagAdapter;

    final TagDAO tag = new TagDAO(this);

    ArrayList<String> arrayTagReceiver = new ArrayList();
    TagView tagView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag_to_my_sentences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //hien thi icon back

        tagView2 = (TagView) findViewById(R.id.tagview2);
        //Nhan data cua cac Tag, sau do hien thi len TagView
        arrayTagReceiver = getIntent().getStringArrayListExtra("availableTag");
        for (String i : arrayTagReceiver) {
            this.addNewTagToTagView(tagView2, i);
        }

        initView();

        //Event for Button Add Tag
        Button bt_addTag = (Button) findViewById(R.id.bt_addtag2);
        bt_addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTag = autoComplete2.getText().toString();
                if (!textTag.isEmpty()) {
                    if (isNotTagged(textTag)) {
                        addNewTagToTagView(tagView2, textTag);
                        arrayTagReceiver.add(textTag);
                        reloadArrayTagForAutocompleteBox();
                    } else {
                        Log.i("aaaaa", "duplicate");
                    }
                    autoComplete2.setText("");
                }
                Log.d("Array Tagging:", arrayTagReceiver + "");
            }
        });
        tagView2.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int position) {
                Toast.makeText(getApplicationContext(), "delete tag id=" + tag.id + " position=" + position, Toast.LENGTH_SHORT).show();
                arrayTagReceiver.remove(position);
            }
        });
        //// Event for Button Accept + Cancel
        findViewById(R.id.bt_accept2).setOnClickListener(listener);
        findViewById(R.id.bt_cancel2).setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_accept2:
                    sendToAddNewMySentences(AddNewMySentencesActivity.RESULT_CODE_ADD_TAG);
                    break;
                case R.id.bt_cancel2:
                    finish();
                    break;
            }
        }
    };

    public void sendToAddNewMySentences(int resultcode) {
        Intent intent = getIntent();
        intent.putStringArrayListExtra("data", arrayTagReceiver);
        setResult(resultcode, intent);
        finish();
    }


    public void initView() {
        autoComplete2 = (AutoCompleteTextView) findViewById(R.id.autoComplete2);
        this.reloadArrayTagForAutocompleteBox();
        autoComplete2.setThreshold(1);

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
        switch (item.getItemId()) {
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
        tag.tagTextSize = 23f;
        tag.radius = 0f;
        tag.isDeletable = true;

        tagView.addTag(tag);
    }


    private void reloadArrayTagForAutocompleteBox() {
        arrayTag = tag.getAllTagFromTagsIgnoreItems(arrayTagReceiver);
        mTagAdapter = new TagAdapter(this, R.layout.activity_add_edit_tag_item, arrayTag);
        autoComplete2.setAdapter(mTagAdapter);
    }

    private boolean isNotTagged(String tagName) {   //handle exception if duplicate available Tag
        for (String i : arrayTagReceiver) {
            if (i.equalsIgnoreCase(tagName))
                return false;
        }
        return true;
    }


}
