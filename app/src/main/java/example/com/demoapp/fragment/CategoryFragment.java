package example.com.demoapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.DisplaySubActivity;
import example.com.demoapp.adapter.CategoriesAdapter;
import example.com.demoapp.model.CategoryItem;
import example.com.demoapp.model.DAO.CategoryDAO;
import example.com.demoapp.utility.Consts;

public class CategoryFragment extends Fragment {

    public static FloatingActionButton fab_menu;
    ArrayList<CategoryItem> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        CategoryDAO dao = new CategoryDAO();
        categoryList = dao.getAllCategories();
        // Inflate the layout for this fragment
        GridView gridview = (GridView) view.findViewById(R.id.gridViewCategory);
        gridview.setAdapter(new CategoriesAdapter(getActivity(),categoryList));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), DisplaySubActivity.class);
                CategoryItem item = categoryList.get(position);
                i.putExtra(Consts.CATEGORY_ID, item.getId());
                i.putExtra(Consts.CATEGORY_NAME, item.getName());
                i.putExtra(Consts.NAVIGATION_IMAGE, item.getChildImage());
                startActivity(i);
            }
        });
        fab_menu = (FloatingActionButton) view.findViewById(R.id.menu_fab);
        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_menu.setVisibility(View.GONE);
                BackgroundFragment fragment = new BackgroundFragment();
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_container, fragment, null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
