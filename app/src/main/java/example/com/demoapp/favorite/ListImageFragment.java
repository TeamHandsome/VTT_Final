package example.com.demoapp.favorite;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.FavoriteFragment;
import example.com.demoapp.adapter.ImageAdapter;
import example.com.demoapp.model.DAO.FavoriteDAO;

import example.com.demoapp.utility.DbHelper;

public class ListImageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_image_favorite, container, false);

        return view;
    }

}
