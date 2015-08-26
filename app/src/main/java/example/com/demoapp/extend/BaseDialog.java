package example.com.demoapp.extend;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import example.com.demoapp.R;

/**
 * Created by Tony on 27/7/2015.
 */
public abstract class BaseDialog extends Dialog {
    protected ImageButton accept, cancel;
    public boolean close = true;

    public BaseDialog(Activity activity) {
        super(activity);
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        accept = (ImageButton) findViewById(R.id.bt_accept);
        cancel = (ImageButton) findViewById(R.id.bt_cancel);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAccept();
                closeDialog(close);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    protected void initView(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.back_pop_up);
        setContentView(setContentViewLayout());
    }

    public abstract void  onClickAccept();

    protected abstract int setContentViewLayout();

    //true if you want to close dialog and false if you want to do sth else
    protected void closeDialog(boolean close){
        if (close) {
            dismiss();
        }
    };
}
