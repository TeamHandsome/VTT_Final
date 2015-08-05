package example.com.demoapp.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditMySentencesActivity;
import example.com.demoapp.activity.RecordActivity;
import example.com.demoapp.utility.Consts;

public class BackgroundFragment extends Fragment {
    View mDimmerView;
    private List<FloatingActionButton> menus = new ArrayList<>();
    private List<TextView> tv_menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();
    FloatingActionButton fab_addnew, fab_record, fab_camera;
    TextView tv_camera, tv_record, tv_addnew;
    //
    public Uri uri_fabbutton;
    String image_namefile = System.currentTimeMillis()+".jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);
//        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.fab_label_background);
        mDimmerView = (View) view.findViewById(R.id.dimmer_view);
        fab_addnew = (FloatingActionButton) view.findViewById(R.id.fab_addnew);
        fab_record = (FloatingActionButton) view.findViewById(R.id.fab_record);
        fab_camera = (FloatingActionButton) view.findViewById(R.id.fab_camera);
        tv_camera = (TextView) view.findViewById(R.id.tv_camera);
        tv_record = (TextView) view.findViewById(R.id.tv_record);
        tv_addnew = (TextView) view.findViewById(R.id.tv_addnew);

        menus.add(fab_addnew);
        menus.add(fab_record);
        menus.add(fab_camera);
        fab_addnew.hide(false);
        fab_record.hide(false);
        fab_camera.hide(false);
        int delay = 100;
        for (final FloatingActionButton menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.show(true);
                }
            }, delay);
            delay += 100;
        }

        mDimmerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                closeFragment();
            }
        });

        tv_menus.add(tv_addnew);
        tv_menus.add(tv_record);
        tv_menus.add(tv_camera);
        tv_camera.setVisibility(View.INVISIBLE);
        tv_record.setVisibility(View.INVISIBLE);
        tv_addnew.setVisibility(View.INVISIBLE);
        int delay1 = 100;
        for (final TextView menu : tv_menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.setVisibility(View.VISIBLE);
                }
            }, delay1);
            delay1 += 100;
        }

        view.findViewById(R.id.fab_addnew).setOnClickListener(listener);
        view.findViewById(R.id.fab_record).setOnClickListener(listener);
        view.findViewById(R.id.fab_camera).setOnClickListener(listener);
//
        return view;
    }

    public void hide(){
        fab_addnew.hide(true);
        fab_record.hide(true);
        fab_camera.hide(true);
        mDimmerView.setVisibility(View.GONE);
    }
    public void closeFragment(){
        CategoryFragment.fab_menu.setVisibility(View.VISIBLE);
        getActivity().getSupportFragmentManager().beginTransaction().remove(BackgroundFragment.this).commit();
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fab_addnew:
                    hide();
                    Intent intent = new Intent(getActivity().getApplicationContext(), AddEditMySentencesActivity.class);
                    intent.putExtra(Consts.ACTION_TYPE, Consts.ADD_MY_SEN);
                    startActivity(intent);
                    closeFragment();
                    break;
                case R.id.fab_record:
                    hide();
                    Intent intent1 = new Intent(getActivity().getApplicationContext(), RecordActivity.class);
                    startActivityForResult(intent1, 1);
                    closeFragment();
                    break;
                case R.id.fab_camera:
                    hide();
                    dispatchTakePictureIntent();
                    closeFragment();
                    break;

            }
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode== AddEditMySentencesActivity.RESULT_CODE_RECORD && data!=null){
            String uri_record = data.getStringExtra("data");

            Intent intent = new Intent(getActivity().getApplicationContext(),AddEditMySentencesActivity.class);
            intent.putExtra("data1",uri_record);
            startActivity(intent);
            closeFragment();
        }
        if(requestCode== AddEditMySentencesActivity.REQUEST_IMAGE_CAPTURE){
            File photo = new File(Environment.getExternalStorageDirectory() + File.separator
                    + AddEditMySentencesActivity.folder_main
                    + image_namefile);
            try {
                cropCapturedImage(Uri.fromFile(photo));
            }
            catch(ActivityNotFoundException aNFE){
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if(requestCode== AddEditMySentencesActivity.RESULT_IMAGE_CAPTURE){
            //get the cropped bitmap from extras
            Bundle extras = data.getExtras();
            if (extras!=null){
                Bitmap bitmap= extras.getParcelable("data");
                saveBitmap(bitmap);
                uri_fabbutton = AddEditMySentencesActivity.getImageUri(getActivity().getApplicationContext(), bitmap);

                Intent intent = new Intent(getActivity().getApplicationContext(),AddEditMySentencesActivity.class);
                intent.setData(uri_fabbutton);
                startActivity(intent);
                closeFragment();
            }

        }
    }
    private void dispatchTakePictureIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator
                + AddEditMySentencesActivity.folder_main
                + image_namefile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, AddEditMySentencesActivity.REQUEST_IMAGE_CAPTURE);
    }
    private void cropCapturedImage(Uri picUri){
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
        startActivityForResult(cropIntent, AddEditMySentencesActivity.RESULT_IMAGE_CAPTURE);
    }
    public void saveBitmap(Bitmap bmp)
    {
        String file_path = Environment.getExternalStorageDirectory() + File.separator + AddEditMySentencesActivity.folder_main;
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, image_namefile);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
