package example.com.demoapp.extend;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import example.com.demoapp.R;
import example.com.demoapp.utility.StringUtils;

/**
 * Created by Tony on 29/7/2015.
 */
public class CustomToast {
    private Activity activity;
    private String message;
    private int viewID;
    private int layoutID;

    private Toast toast;
    private int toast_type;
    private int duration_type;

    private TextView text;

    public static final int LENGTH_LONG = Toast.LENGTH_LONG;
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;

    public static final int INFO = 1;
    public static final int ERROR = 2;
    public static final int SUCCESS = 3;

    public CustomToast(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
    }

    public CustomToast(Activity activity, String message, int type) {
        this.activity = activity;
        this.message = message;
        this.toast_type = type;
    }

    public CustomToast(Activity activity, String message, int toast_type, int duration_type) {
        this.activity = activity;
        this.message = message;
        this.toast_type = toast_type;
        this.duration_type = duration_type;
    }

    public int getViewID() {
        return viewID;
    }

    public void setViewID(int viewID) {
        this.viewID = viewID;
    }

    private void initView(){

        // get your toast.xml layout
        LayoutInflater inflater = activity.getLayoutInflater();
        this.setToastType(toast_type);
        View layout = inflater.inflate(layoutID,(ViewGroup) activity.findViewById(viewID));

        // set a message
        text = (TextView) layout.findViewById(R.id.toast_text);
        text.setText(message);

        // Toast configuration
        toast = new Toast(activity.getApplicationContext());
        this.setDuration(duration_type);
        toast.setView(layout);
    };

    public void show(){
        this.initView();
        toast.show();
    }

    private void setToastType(int toast_type){
        switch (toast_type){
            case INFO:
                layoutID = R.layout.toast_info;
                viewID = R.id.toast_info;
                break;
            case ERROR:
                layoutID = R.layout.toast_error;
                viewID = R.id.toast_error;
                break;
            case SUCCESS:
                layoutID = R.layout.toast_success;
                viewID = R.id.toast_success;
                break;
            default:
                layoutID = R.layout.toast_success;
                viewID = R.id.toast_success;
                break;
        }
    }

    private void setDuration(int duration_type){
        switch (duration_type){
            case LENGTH_LONG:
                toast.setDuration(Toast.LENGTH_LONG);
                break;
            case LENGTH_SHORT:
                toast.setDuration(Toast.LENGTH_SHORT);
                break;
            default:
                toast.setDuration(Toast.LENGTH_LONG);
                break;
        }
    }
}
