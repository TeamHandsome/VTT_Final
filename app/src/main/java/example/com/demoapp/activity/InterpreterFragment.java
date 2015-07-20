package example.com.demoapp.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.demoapp.R;

public class InterpreterFragment extends Fragment {
    public InterpreterFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_interpreter, container, false);




        return view;
    }
}
