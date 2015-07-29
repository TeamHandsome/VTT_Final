package example.com.demoapp.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.example.tony.taglibrary.TagView;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.CompleteTagAdapter;
import example.com.demoapp.extend.CustomToast;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.TagItem;
import example.com.demoapp.utility.Message;

import com.example.tony.taglibrary.OnTagDeleteListener;
import com.example.tony.taglibrary.Tag;

public class AddEditTagActivity extends ActionBarActivity {

    private AutoCompleteTextView autoComplete;
    private ArrayList<String> tag_list;    //to handle tag available
    private CompleteTagAdapter mCompleteTagAdapter;
    String sentences_id;
    final TagDAO tagDAO = new TagDAO(this);
    private int actionType= -1;

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

        this.actionType = getIntent().getIntExtra(Consts.ACTION_TYPE, Consts.NOT_FOUND);
        sentences_id = getIntent().getStringExtra(Consts.SENTENCE_ID);
        //load available tag and show
        this.loadTagsAtFirst();

        initView();

        //Event for Button Add Tag
        ImageButton bt_addTag = (ImageButton) findViewById(R.id.bt_addtag1);
        bt_addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTag = autoComplete.getText().toString();

                if (validate(textTag)) {
                    Common.addNewTagToTagView(AddEditTagActivity.this, tagView, textTag);
                    tag_list.add(textTag);
                    reloadArrayTagForAutocompleteBox();
                }

                autoComplete.setText("");
            }
        });
        tagView.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int position) {
                String tag_name = tag_list.get(position);
                String mess = Message.ITEM_IS_DELETED(tag_name+Consts.TAG);
                Common.showToast(AddEditTagActivity.this, mess, CustomToast.INFO);
                tag_list.remove(position);
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
                    if(actionType==Consts.EDIT_TAG_NORMAL) {
                        Log.d("Count all tags: ", tagDAO.countTags() + "");
                        tagDAO.addTagToTags(sentences_id, tag_list);
                        finish();
                    }else{
                        sendToAddNewMySentences(AddEditMySentencesActivity.RESULT_CODE_ADD_TAG);
                    }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void reloadArrayTagForAutocompleteBox() {
        ArrayList<TagItem> arrayTag = tagDAO.getAllTagFromTagsIgnoreItems(tag_list);
        mCompleteTagAdapter = new CompleteTagAdapter(this, R.layout.activity_add_edit_tag_item_complete, arrayTag);
        autoComplete.setAdapter(mCompleteTagAdapter);
    }

    private boolean isDuplicateTag(String tagName) {   //handle exception if duplicate available Tag
        for (String i : tag_list) {
            if (i.equalsIgnoreCase(tagName))
                return false;
        }
        return true;
    }

    private boolean validate(String tag_name){
        String message = "";
        Activity con = AddEditTagActivity.this;
        if (tag_name == null || tag_name.trim().isEmpty()){
            message = Message.MUST_NOT_EMPTY(Consts.TAG_NAME);
            Common.showToast(con, message,CustomToast.ERROR);
            return false;
        }
        if(!isDuplicateTag(tag_name)){
            message = Message.ITEM_IS_DUPLICATED(tag_name);
            Common.showToast(con, message,CustomToast.ERROR);
            return false;
        };
        if(tag_name.length() > Consts.MAX_TAGNAME_LENGTH){
            message = Message.MAX_CHARACTER_LENGTH(Consts.TAG_NAME, Consts.MAX_TAGNAME_LENGTH);
            Common.showToast(con, message,CustomToast.ERROR);
            return false;
        }
        return true;
    }

    private void loadTagsAtFirst(){
        if(actionType==Consts.EDIT_TAG_NORMAL){
            //list tag available from DB
            tag_list = tagDAO.getTagsFromTagging(sentences_id);
        }else if (actionType==Consts.EDIT_TAG_ADD_SEN){
            tag_list = getIntent().getStringArrayListExtra(Consts.AVAILABLE_TAG);
        }else if (actionType==Consts.EDIT_TAG_MOD_SEN){
            tag_list = getIntent().getStringArrayListExtra(Consts.AVAILABLE_TAG);
        }else{
            Log.e("action type","can not find action type");
        }
        //show tag
        for (String tag : tag_list) {
            Common.addNewTagToTagView(AddEditTagActivity.this, tagView, tag);
        }
    }

    private void sendToAddNewMySentences(int result_code) {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(Consts.DATA, tag_list);
        setResult(result_code, intent);
        finish();
    }
}
