package example.com.demoapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import example.com.demoapp.R;
import example.com.demoapp.adapter.sentence_adapter.SentencesAdapter;
import example.com.demoapp.extend.CustomToast;
import example.com.demoapp.model.DAO.LocationDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.MySingleton;
import example.com.demoapp.utility.StringUtils;


public class SuggestFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 10;

    List<Integer> content = null;
    SentencesAdapter sentencesAdapter;
    ArrayList<SentenceItem> arrayList;
    ListView listView;
    ImageView imageView;
    LocationDAO dao;
    int type_id = 0;
    private Handler mUiHandler = new Handler();
    ProgressDialog dialog;
    boolean _areLecturesLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest, container, false);
        listView = (ListView) view.findViewById(R.id.lv_suggest);
        dao = new LocationDAO();

        FloatingActionButton bt_gps = (FloatingActionButton) view.findViewById(R.id.bt_GPS);
        bt_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPlacePicker();
            }
        });

        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !_areLecturesLoaded) {
            if (isNetworkConnected()) {

                dialog = ProgressDialog.show(SuggestFragment.this.getActivity(), "GPS Location", "Loading...", true);
                guessCurrentPlace();
                mUiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        setListView(type_id);
                    }
                }, 4000);
            } else {
                _setNoData();
                String mess = Consts.CONNECT_INTERNET;
                Common.showToast(getActivity(), mess, CustomToast.INFO);
            }
            _areLecturesLoaded = true;
        }
    }

    private void setListView(int id) {
        arrayList = dao.getSentencesByLocation(id);
        sentencesAdapter = new SentencesAdapter(getActivity(), R.layout.custom_row_sen_f_t, arrayList);
        listView.setAdapter(sentencesAdapter);
        _setNoData();
        listView.setEmptyView(imageView);
    }

    private void _setNoData(){
        imageView = (ImageView) getActivity().findViewById(R.id.tv_nodata);
        Picasso.with(getActivity())
                .load(R.drawable.no_data)
                .resize(360, 360)
                .centerCrop()
                .into(imageView);
    }

    private void guessCurrentPlace() {
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {

                PlaceLikelihood placeLikelihood = likelyPlaces.get(0);

                if (placeLikelihood != null && placeLikelihood.getPlace() != null && !TextUtils.isEmpty(placeLikelihood.getPlace().getName()))
                    content = placeLikelihood.getPlace().getPlaceTypes();

                getLocationId(content);

                likelyPlaces.release();
            }
        });
    }

    private void displayPlacePicker() {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected())
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(getActivity().getApplicationContext()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesRepairableException thrown");
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown");
        }
    }

    private int getLocationId(List<Integer> content) {
        List<Integer> list1 = dao.getLocation_id();
        for (Integer id : content) {
            if (list1.contains(id)) {
                type_id = id;
            }
        }

        return type_id;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST && data != null) {
            displayPlace(PlacePicker.getPlace(data, getActivity().getApplicationContext()));
        }
    }

    private void displayPlace(Place place) {
        if (place == null)
            return;
        type_id = 0;
        content = place.getPlaceTypes();
        getLocationId(content);
        Log.d("TYPE_ID", type_id +"");
        if (type_id == 0 ) {
            setListView(type_id);
        } else
            setListView(type_id);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
