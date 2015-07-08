package example.com.demoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import example.com.demoapp.R;
import example.com.demoapp.adapter.CategoriesAdapter;
import example.com.demoapp.subCategory.DisplaySubActivity;
import example.com.demoapp.utility.Consts;

public class CategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        // Inflate the layout for this fragment
        GridView gridview = (GridView) view.findViewById(R.id.gridViewCategory);
        gridview.setAdapter(new CategoriesAdapter(getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(),DisplaySubActivity.class);
                i.putExtra(Consts.CATEGORY_ID,position+1);
                startActivity(i);
            }
        });
        return view;
    }


}
