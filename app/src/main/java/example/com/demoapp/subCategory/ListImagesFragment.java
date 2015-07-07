package example.com.demoapp.subCategory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.daimajia.swipe.util.Attributes;

import java.io.IOException;
import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.adapter.ImageAdapter;
import example.com.demoapp.common.DbHelper;
import example.com.demoapp.model.DAO.SentencesDAO;
import example.com.demoapp.model.DisplaySentencesItem;


public class ListImagesFragment extends Fragment {
    GridView gridView;
    ArrayList<DisplaySentencesItem> listSentences;
    ImageAdapter mImageAdapter;

    private DbHelper db ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_images, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);

        initView(view);

        return view;
    }
    private void initView(View v) {

        try {
            db = new DbHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            db.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SentencesDAO senDao = new SentencesDAO();
        listSentences = senDao.getAllSentenceBySub(DisplaySentencesActivity.subCategory_id);   //gán dữ liệu từ database vào mảng ArrayList
        mImageAdapter = new ImageAdapter(getActivity(), R.layout.custom_image, listSentences); //gán qua Adapter
        gridView.setAdapter(mImageAdapter);  //từ Adapter lên listview
        mImageAdapter.setMode(Attributes.Mode.Multiple);

    }

}
