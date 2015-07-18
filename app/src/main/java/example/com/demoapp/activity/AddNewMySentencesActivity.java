package example.com.demoapp.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tony.taglibrary.OnTagDeleteListener;
import com.example.tony.taglibrary.Tag;
import com.example.tony.taglibrary.TagView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import example.com.demoapp.R;
import example.com.demoapp.adapter.ImageAdapter;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.DAO.TagDAO;

public class AddNewMySentencesActivity extends ActionBarActivity {
    Context context;
    public static final int REQUEST_CODE_ADD_TAG = 10;
    public static final int RESULT_CODE_ADD_TAG = 20;
    public static final int REQUEST_IMAGE_SELECTOR = 30;
    public static final int REQUEST_IMAGE_CAPTURE = 40;
    public static final int RESULT_IMAGE_CAPTURE = 41;
    public static final int REQUEST_CODE_RECORD = 50;
    public static final int RESULT_CODE_RECORD = 51;
    SentencesDAO addNewSenDAO = new SentencesDAO();
    TagDAO addtagMySenDAO = new TagDAO(context);

    private String image_namefile;     //save random name Image
    private String db_path = "/sdcard/Android/data/com.demoapp.app/";

    public static Uri uri;
    public static Uri takingPhoto;   //save Uri path image load from Camera
    public static Uri selectedImage;  //save Uri path image load from gallery
    List<Uri> saveUri = new ArrayList<>();
    public String uri_record;         //save Uri path record
    ArrayList<String> resultTag = new ArrayList<>();      // result Tag tra ve tu AddTagToMySentences



    File file;
    String folder_main = "NewFolder/";
    TagView tagView;
    ImageButton bt_record, bt_recordPlay, bt_recordStop, bt_recordDelete, bt_recordPath,
            bt_takephoto, bt_gallery, bt_photodelete, bt_cancel2 , bt_accept2;
    ImageView img_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_my_sentences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //h

        findViewById(R.id.bt_addTagMySentences).setOnClickListener(listener);
        tagView = (TagView) findViewById(R.id.tagView1);
        img_photo = (ImageView) findViewById(R.id.img_photo);
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

        bt_recordPlay.setClickable(false);
        bt_recordDelete.setClickable(false);

        //  bt_record.setVisibility(View.GONE);
        randomStringImage();


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_addTagMySentences:
                    Intent i = new Intent(getApplicationContext(), AddTagToMySentences.class);
                    i.putStringArrayListExtra("availableTag", resultTag);
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
                    Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
                    break;
                case R.id.bt_recordPath:
                    openFolderRecord();
                    break;
                case R.id.bt_recordDelete:
                    file = new File(uri_record);
                    file.delete();
                    bt_recordDelete.setClickable(false);
                    bt_recordPlay.setClickable(false);
                    bt_record.setClickable(true);
                    bt_recordPath.setClickable(true);
                    Toast.makeText(getApplicationContext(), "Audio deleted in storage!", Toast.LENGTH_LONG).show();
                    break;
                case R.id.bt_takephoto:
                    dispatchTakePictureIntent();
                    break;
                case R.id.bt_gallery:
                    dispatchPhotoSelectionIntent();
                    break;
                case R.id.bt_photodelete:
                    Toast.makeText(getApplicationContext(), "Picture deleted!", Toast.LENGTH_LONG).show();
                    takingPhoto = null;
                    selectedImage = null;
                    img_photo.setImageResource(android.R.color.transparent);
                    break;
                case R.id.bt_cancel2:
                    if (uri_record!=null){
                        file = new File(uri_record);
                        file.delete();
                    }
                    if (takingPhoto!=null){
                        file = new File(Environment.getExternalStorageDirectory() + File.separator + folder_main+ image_namefile);
                        file.delete();
                    }
                    finish();
                    break;
//                case R.id.bt_accept2:
//                    addNewSenDAO.addUri(saveUri);
//                    addtagMySenDAO.addTagToTags(1,resultTag);
//
//                    break;

            }
        }
    };

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
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folder_main+ image_namefile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TAG && resultCode == RESULT_CODE_ADD_TAG) {
            resultTag = data.getStringArrayListExtra("data");
//                    resultTag.removeAll(availableTag);    //delete duplicate
            tagView.removeAllTags();
            for (String tag : resultTag) {
                addNewTagToTagView(tagView, tag);
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
                Bitmap photo = extras2.getParcelable("data");
                selectedImage = getImageUri(this,photo);      //save file image path
                saveUri.add(selectedImage);

                Picasso.with(this).load(selectedImage).config(Bitmap.Config.RGB_565).fit().centerInside().into(img_photo);
            }
        }

        if(requestCode==REQUEST_IMAGE_CAPTURE){

            File photo = new File(Environment.getExternalStorageDirectory() + File.separator + folder_main+ image_namefile);

            try {
                cropCapturedImage(Uri.fromFile(photo));
            }
            catch(ActivityNotFoundException aNFE){
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if(requestCode==RESULT_IMAGE_CAPTURE){
            //get the cropped bitmap from extras
            Bundle extras = data.getExtras();
            if (extras!=null){
                Bitmap bitmap= extras.getParcelable("data");
                takingPhoto = getImageUri(this,bitmap);
                saveUri.add(takingPhoto);

                Picasso.with(this).load(takingPhoto).config(Bitmap.Config.RGB_565).fit().centerInside().into(img_photo);
            }

        }
        if (requestCode==REQUEST_CODE_RECORD && resultCode==RESULT_CODE_RECORD){
            uri_record = data.getStringExtra("data");
            if (uri_record!=null){
                bt_record.setClickable(false);
                bt_recordPath.setClickable(false);
                bt_recordPlay.setClickable(true);
                bt_recordDelete.setClickable(true);
            }
        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void cropCapturedImage(Uri picUri){
        //call the standard crop action intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, RESULT_IMAGE_CAPTURE);
    }

    public void addNewTagToTagView(TagView tagView, String tag) {
        Tag t = new Tag(tag);
        t.layoutBorderSize = 1f;
        t.layoutBorderColor = getResources().getColor(R.color.colorAccent);
        t.tagTextSize = 25f;
        t.radius = 0f;
        t.isDeletable = true;
        tagView.addTag(t);
    }


    public void randomStringImage(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        image_namefile = sb.toString() + ".jpg";
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
