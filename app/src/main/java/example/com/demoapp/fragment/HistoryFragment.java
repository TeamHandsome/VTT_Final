package example.com.demoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.demoapp.utility.Consts;


public class HistoryFragment extends BaseListContainerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);

        return view;
    }

    @Override
    void setInitFirstValue() {
        parent_name = Consts.HISTORY;
        navigation_text = Consts.HISTORY_LIST;
        navigation_image = Consts.NAVI_BACK_HISTORY;
    }
}

