package example.com.demoapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import example.com.demoapp.R;
import example.com.demoapp.adapter.CategoriesAdapter;
import example.com.demoapp.subCategory.DisplaySubActivity;
import example.com.demoapp.utility.Consts;

public class CategoryFragment extends Fragment {

    FloatingActionButton fab_addnew, fab_record, fab_camera;
    public Uri uri_fabbutton;
    String image_namefile = System.currentTimeMillis()+".jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        // Inflate the layout for this fragment
        GridView gridview = (GridView) view.findViewById(R.id.gridViewCategory);
        gridview.setAdapter(new CategoriesAdapter(getActivity()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), DisplaySubActivity.class);
                i.putExtra(Consts.CATEGORY_ID, position + 1);
                startActivity(i);
            }
        });
        view.findViewById(R.id.fab_addnew).setOnClickListener(listener);
        view.findViewById(R.id.fab_record).setOnClickListener(listener);
        view.findViewById(R.id.fab_camera).setOnClickListener(listener);


        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fab_addnew:
                    Intent intent = new Intent(getActivity().getApplicationContext(), AddEditMySentencesActivity.class);
                    intent.putExtra(Consts.ACTION_TYPE,Consts.ADD_MY_SEN);
                    startActivity(intent);
                    break;
                case R.id.fab_record:
                    Intent intent1 = new Intent(getActivity().getApplicationContext(), RecordActivity.class);
                    startActivityForResult(intent1, 1);
                    break;
                case R.id.fab_camera:
                    dispatchTakePictureIntent();
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
