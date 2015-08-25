package example.com.demoapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tony.taglibrary.OnTagDeleteListener;
import com.example.tony.taglibrary.Tag;
import com.example.tony.taglibrary.TagView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.adapter.sentence_adapter.BaseSentencesAdapter;
import example.com.demoapp.extend.CancelDialog;
import example.com.demoapp.extend.ConfirmDeleteDialog;
import example.com.demoapp.extend.CustomToast;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;

public class AddEditMySentencesActivity extends ActionBarActivity {
    BaseSentencesAdapter baseSentencesAdapter;
    Context context;
    public static final int REQUEST_CODE_ADD_TAG = 10;
    public static final int RESULT_CODE_ADD_TAG = 20;
    public static final int REQUEST_IMAGE_SELECTOR = 30;
    public static final int REQUEST_IMAGE_CAPTURE = 40;
    public static final int RESULT_IMAGE_CAPTURE = 41;
    public static final int REQUEST_CODE_RECORD = 50;
    public static final int RESULT_CODE_RECORD = 51;
    SentencesDAO addNewSenDAO;
    TagDAO addtagMySenDAO;

    public static String folder_main = "tachikon/";
    String image_namefile = System.currentTimeMillis() + ".jpg";     //save random name Image
    private String db_path = "/sdcard/Android/data/com.demoapp.app/";

    public static Uri uri_fabbutton;
    public static Uri takingPhoto;   //save Uri path image load from Camera
    public static Uri selectedImage;  //save Uri path image load from gallery
    List<Uri> saveUri = new ArrayList<>();
    public String uri_record;         //save Uri path record
    ArrayList<String> resultTag = new ArrayList<>();      // result Tag tra ve tu AddEditTagActivity
    public static ArrayList<SentenceItem> listSentences;

    private int action_type = -1;
    File file;
    String fab_record;
    TagView tagView;
    ImageButton bt_record, bt_recordPlay, bt_recordStop, bt_recordDelete, bt_recordPath,
            bt_takephoto, bt_gallery, bt_photodelete, bt_cancel2, bt_accept2;
    ImageView img_photo;
    EditText ed_japanese, ed_vietnamese;
    String vn, jp_hiragana, audio, image_d, id, id_edit;
    SentenceItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_my_sentences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //h

        Common.setupUIForHideSoftKeyBroad(findViewById(R.id.activity_add_edit_my_sentence),
                AddEditMySentencesActivity.this);

        this.action_type = getIntent().getIntExtra(Consts.ACTION_TYPE, Consts.NOT_FOUND);

        findViewById(R.id.bt_addTagMySentences).setOnClickListener(listener);
        tagView = (TagView) findViewById(R.id.tagView1);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        ed_vietnamese = (EditText) findViewById(R.id.ed_vietnamese);
        ed_japanese = (EditText) findViewById(R.id.ed_japanese);
        //Xy ly phan Record
        bt_record = (ImageButton) findViewById(R.id.bt_record);
        bt_recordPlay = (ImageButton) findViewById(R.id.bt_recordPlay);
        bt_recordDelete = (ImageButton) findViewById(R.id.bt_recordDelete);
        bt_recordPath = (ImageButton) findViewById(R.id.bt_recordPath);
        bt_takephoto = (ImageButton) findViewById(R.id.bt_takephoto);
        bt_gallery = (ImageButton) findViewById(R.id.bt_gallery);
        bt_photodelete = (ImageButton) findViewById(R.id.bt_photodelete);
        bt_accept2 = (ImageButton) findViewById(R.id.bt_accept2);
        bt_cancel2 = (ImageButton) findViewById(R.id.bt_cancel2);
        bt_record.setOnClickListener(listener);
        bt_recordPlay.setOnClickListener(listener);
        bt_recordDelete.setOnClickListener(listener);
        bt_recordPath.setOnClickListener(listener);
        bt_takephoto.setOnClickListener(listener);
        bt_gallery.setOnClickListener(listener);
        bt_photodelete.setOnClickListener(listener);
        bt_accept2.setOnClickListener(listener);
        bt_cancel2.setOnClickListener(listener);

        bt_recordPlay.setEnabled(false);
        bt_recordDelete.setEnabled(false);
        bt_photodelete.setEnabled(false);

        //hanlde for Float Button Record from main
        Intent intent1 = getIntent();
        fab_record = intent1.getStringExtra("data1");
        if (fab_record != null) {
            uri_record = fab_record;
//            Toast.makeText(getApplicationContext(), "Uri record: "+ uri_record, Toast.LENGTH_SHORT).show();
            bt_recordPlay.setEnabled(true);
            bt_recordDelete.setEnabled(true);
            bt_recordPath.setEnabled(false);
        }
        ;
        //handle  for Float Button Camera from main
        uri_fabbutton = getIntent().getData();
        Log.d("aaaaaaaaaaaaa", uri_fabbutton + "");
        if (uri_fabbutton != null) {
            bt_photodelete.setEnabled(true);
            takingPhoto = uri_fabbutton;
            Picasso.with(this).load(takingPhoto).config(Bitmap.Config.RGB_565).resize(600, 600).centerInside().into(img_photo);
        }

        /////// modify
        if (action_type == Consts.EDIT_MY_SEN) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                SentenceItem item = extras.getParcelable(Consts.DATA);
                id_edit = item.getId();
                uri_record = item.getSound();
                if (uri_record != null && !uri_record.isEmpty()){
                    bt_recordPlay.setEnabled(true);
                    bt_recordDelete.setEnabled(true);
                    bt_record.setEnabled(false);
                    bt_recordPath.setEnabled(false);
                }
                image_d = item.getImage();
                if (image_d !=null && !image_d.isEmpty()){
                    bt_photodelete.setEnabled(true);
                    bt_takephoto.setEnabled(false);
                    bt_gallery.setEnabled(false);
                }
                vn = item.getNameVn();
                jp_hiragana = item.getNameJp();

                ed_japanese.setText(jp_hiragana);
                ed_vietnamese.setText(vn);
                loadImg_edit(image_d);
                load_tag(id_edit);
            }
        }

    }
    public void loadImg_edit(String img){
        if(img !=null && !img.isEmpty()){
            Picasso.with(this)
                    .load(img)
                    .resize(360, 360)
                    .centerCrop()
                    .into(this.img_photo);
        }else {
            img_photo.setImageResource(R.drawable.default_image);
        }
    }
    public void load_tag(String sentences_id){
        ArrayList<String> arrayTag = new ArrayList<String>();
        TagDAO tagDAO = new TagDAO();
        resultTag = tagDAO.getTagsFromTagging(sentences_id);
        for (String tag : resultTag) {
            Common.addNewTagToTagView(AddEditMySentencesActivity.this, tagView, tag);
        }
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_addTagMySentences:
                    Intent i = new Intent(getApplicationContext(), AddEditTagActivity.class);
                    i.putExtra(Consts.ACTION_TYPE, Consts.EDIT_TAG_ADD_SEN);
                    i.putStringArrayListExtra(Consts.AVAILABLE_TAG, resultTag);
                    startActivityForResult(i, REQUEST_CODE_ADD_TAG);
                    break;
                case R.id.bt_record:

                    Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_RECORD);
                    break;
                case R.id.bt_recordPlay:
                    MediaPlayer m = new MediaPlayer();
                    try {
                        m.setDataSource(uri_record);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        m.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    m.start();
                    Toast.makeText(getApplicationContext(), "オーディオをしています", Toast.LENGTH_LONG).show();
                    break;
                case R.id.bt_recordPath:
                    openFolderRecord();
                    break;
                case R.id.bt_recordDelete:
                    ConfirmDeleteDialog confirm = new ConfirmDeleteDialog(AddEditMySentencesActivity.this,
                            Consts.DELETE_RECORD,Message.CONFIRM_DELETE){
                        @Override
                        public void onClickAccept() {
                            file = new File(uri_record);
                            file.delete();
                            bt_recordDelete.setEnabled(false);
                            bt_recordPlay.setEnabled(false);
                            bt_record.setEnabled(true);
                            bt_recordPath.setEnabled(true);
                            uri_record = "";
                            Common.showToast(AddEditMySentencesActivity.this, Message.ITEM_IS_DELETED(Consts.AUDIO));
                        }
                    };
                    confirm.show();
                    break;
                case R.id.bt_takephoto:
                    dispatchTakePictureIntent();
                    break;
                case R.id.bt_gallery:
                    dispatchPhotoSelectionIntent();
                    break;
                case R.id.bt_photodelete:
                    if (takingPhoto != null || selectedImage != null || !image_d.isEmpty()) {
                        ConfirmDeleteDialog confirm1 = new ConfirmDeleteDialog(AddEditMySentencesActivity.this,
                                Consts.DELETE_PHOTO,Message.CONFIRM_DELETE){
                            @Override
                            public void onClickAccept() {
                                bt_photodelete.setEnabled(false);
                                bt_takephoto.setEnabled(true);
                                bt_gallery.setEnabled(true);
                                if (action_type == Consts.EDIT_MY_SEN){
                                    file = new File(image_d);
                                    file.delete();
                                    image_d ="";
                                    img_photo.setImageResource(R.drawable.default_image);
                                }else {
                                    file = new File(Environment.getExternalStorageDirectory() + File.separator + folder_main + image_namefile);
                                    file.delete();
                                    takingPhoto = null;
                                    selectedImage = null;
                                    img_photo.setImageResource(R.drawable.default_image);
                                }
                                Common.showToast(AddEditMySentencesActivity.this, Message.ITEM_IS_DELETED(Consts.IMAGE));
                            }
                        };
                        confirm1.show();
                    }
                    break;
                case R.id.bt_cancel2:
                    if (action_type == Consts.EDIT_MY_SEN){
                        CancelDialog dialog = new CancelDialog(AddEditMySentencesActivity.this) {
                            @Override
                            public void onClickAccept() {
                                finish();
                            }
                        };
                        dialog.show();
                    }else {
                        if ( (ed_vietnamese.getText().toString()).isEmpty() && (ed_japanese.getText().toString()).isEmpty() &&
                                takingPhoto == null && selectedImage == null){
                            finish();
                        }else {
                            CancelDialog dialog = new CancelDialog(AddEditMySentencesActivity.this) {
                                @Override
                                public void onClickAccept() {
                                    if (uri_record != null) {
                                        file = new File(uri_record);
                                        file.delete();
                                    }
                                    if (takingPhoto != null) {
                                        file = new File(Environment.getExternalStorageDirectory() + File.separator + folder_main + image_namefile);
                                        file.delete();
                                    }
                                    finish();
                                }
                            };
                            dialog.show();
                        }
                    }

                    break;
                case R.id.bt_accept2:
                    saveData();
                    if (validateSentence(item)) {
                        if (action_type == Consts.ADD_MY_SEN) {
                            addNewSenDAO.addSentences(item);
                            addtagMySenDAO.addTagToTags(id, resultTag);
                        } else if (action_type == Consts.EDIT_MY_SEN) {
                            addNewSenDAO.updateSentences(id_edit, item);
                            addtagMySenDAO.addTagToTags(id_edit, resultTag);
                        } else {
                            addNewSenDAO.addSentences(item);
                            addtagMySenDAO.addTagToTags(id, resultTag);
                        }
                        finish();
                        String mess = Consts.ADD_SUCCESSFUL;
                        Common.showToast(AddEditMySentencesActivity.this, mess, CustomToast.SUCCESS);
                        takingPhoto = null;
                        selectedImage = null;
                    }


                    break;
            }
        }
    };

    public void saveData() {
        vn = ed_vietnamese.getText().toString();
        jp_hiragana = ed_japanese.getText().toString();
        audio = uri_record;
        if (takingPhoto != null)
            image_d = takingPhoto +"";
        if (selectedImage !=null)
            image_d = selectedImage + "";
        addNewSenDAO = new SentencesDAO();
        addtagMySenDAO = new TagDAO();

        int countId = addNewSenDAO.findLastIDMySenNumber() + 1;
        id= "s" + countId;
        item = new SentenceItem(id,jp_hiragana,vn,audio,image_d);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TAG && resultCode == RESULT_CODE_ADD_TAG) {
            resultTag = data.getStringArrayListExtra(Consts.DATA);
//                    resultTag.removeAll(availableTag);    //delete duplicate
            tagView.removeAllTags();
            for (String tag : resultTag) {
                Common.addNewTagToTagView(AddEditMySentencesActivity.this, tagView, tag);
            }
            tagView.setOnTagDeleteListener(new OnTagDeleteListener() {
                @Override
                public void onTagDeleted(Tag tag, int position) {
                    Toast.makeText(getApplicationContext(), "delete tag id=" + tag.id + " position=" + position, Toast.LENGTH_SHORT).show();
                    resultTag.remove(position);
                }
            });
        }

        if (requestCode == REQUEST_IMAGE_SELECTOR && resultCode == RESULT_OK && null != data) {
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                bt_photodelete.setEnabled(true);
                bt_takephoto.setEnabled(false);
                Bitmap photo = extras2.getParcelable("data");
                selectedImage = getImageUri(this, photo);      //save file image path
                //  saveUri.add(selectedImage);

                Picasso.with(this).load(selectedImage).config(Bitmap.Config.RGB_565).fit().centerInside().into(img_photo);
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            File photo = new File(Environment.getExternalStorageDirectory() + File.separator + folder_main + image_namefile);

            try {
                cropCapturedImage(Uri.fromFile(photo));
            } catch (ActivityNotFoundException aNFE) {
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if (requestCode == RESULT_IMAGE_CAPTURE) {
            //get the cropped bitmap from extras
            Bundle extras = data.getExtras();
            if (extras != null) {
                bt_photodelete.setEnabled(true);
                bt_gallery.setEnabled(false);
                bt_takephoto.setEnabled(false);
                Bitmap bitmap = extras.getParcelable("data");
                saveBitmap(bitmap);
                takingPhoto = getImageUri(this, bitmap);
                //    saveUri.add(takingPhoto);

                Picasso.with(this).load(takingPhoto).config(Bitmap.Config.RGB_565).fit().centerInside().into(img_photo);
            }

        }
        if (requestCode == REQUEST_CODE_RECORD && resultCode == RESULT_CODE_RECORD) {
            uri_record = data.getStringExtra("data");
            if (uri_record != null) {
                bt_record.setEnabled(false);
                bt_recordPath.setEnabled(false);
                bt_recordPlay.setEnabled(true);
                bt_recordDelete.setEnabled(true);
            }
        }

    }

    public void openFolderRecord() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(db_path);
        intent.setDataAndType(uri, "text/csv");
        startActivity(Intent.createChooser(intent, "Open"));
    }

    private void dispatchPhotoSelectionIntent() {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);

        try {
            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent,
                    "Complete action using"), REQUEST_IMAGE_SELECTOR);

        } catch (ActivityNotFoundException e) {

        }
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folder_main + image_namefile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void saveBitmap(Bitmap bmp) {
        String file_path = Environment.getExternalStorageDirectory() + File.separator + folder_main;
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, image_namefile);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void cropCapturedImage(Uri picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, RESULT_IMAGE_CAPTURE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_my_sentences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                CancelDialog dialog = new CancelDialog(AddEditMySentencesActivity.this){
                    @Override
                    public void onClickAccept() {
                        finish();
                    }
                };
                dialog.show();
                return true;
            case R.id.action_addNew:
                Intent intent = new Intent(getApplicationContext(), AddEditMySentencesActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateSentence(SentenceItem sen) {
        String message = "";
        Activity con = AddEditMySentencesActivity.this;
        if (sen.getNameJp() == null || sen.getNameJp().trim().isEmpty()) {
            message = Message.MUST_NOT_EMPTY(Consts.JAPANESE);
            Common.showToast(con, message,CustomToast.ERROR);
            return false;
        }
        if (sen.getNameVn().length() > Consts.MAX_VIE_CHAR_LENGTH) {
            message = Message.MAX_CHARACTER_LENGTH(sen.getNameVn(), Consts.MAX_VIE_CHAR_LENGTH);
            Common.showToast(con, message,CustomToast.ERROR);
            return false;
        }
        if (sen.getNameJp().length() > Consts.MAX_JAP_CHAR_LENGTH) {
            message = Message.MAX_CHARACTER_LENGTH(sen.getNameJp(), Consts.MAX_JAP_CHAR_LENGTH);
            Common.showToast(con, message,CustomToast.ERROR);
            return false;
        }
        return true;
    }
}
