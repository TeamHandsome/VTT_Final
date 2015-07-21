package example.com.demoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;

public class MysentencesFragment extends Fragment {
    Context context;
    public MysentencesFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mysentences, container, false);



        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        (getActivity()).getMenuInflater().inflate(R.menu.menu_mysentences, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.action_addNew:
                Intent intent = new Intent(getActivity(), AddEditMySentencesActivity.class);
                intent.putExtra(Consts.ACTION_TYPE,Consts.ADD_MY_SEN);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
