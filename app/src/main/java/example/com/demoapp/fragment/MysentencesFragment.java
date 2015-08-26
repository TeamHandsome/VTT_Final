package example.com.demoapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditMySentencesActivity;
import example.com.demoapp.utility.Consts;

public class MysentencesFragment extends BaseListContainerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);

        setHasOptionsMenu(true);
        return view;

    }

    @Override
    void setInitFirstValue() {
        parent_name = Consts.MY_SENTENCE_LIST;
        navigation_text = Consts.MY_SEN_LIST;
        navigation_image = Consts.NAVI_BACK_MYSEN;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_mysentences, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addNew){
           Intent intent = new Intent(getActivity().getApplicationContext(), AddEditMySentencesActivity.class);
            intent.putExtra(Consts.ACTION_TYPE, Consts.ADD_MY_SEN);
            getActivity().startActivityForResult(intent, 14);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
