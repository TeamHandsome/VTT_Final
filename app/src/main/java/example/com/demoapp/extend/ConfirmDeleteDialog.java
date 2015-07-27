package example.com.demoapp.extend;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import example.com.demoapp.R;
import example.com.demoapp.utility.Message;

/**
 * Created by Tony on 27/7/2015.
 */
public abstract class ConfirmDeleteDialog extends Dialog {
    private TextView tv_Message, tv_Title;
    private ImageButton accept, cancel;
    private String title,message;

    public ConfirmDeleteDialog(Activity activity,String title, String message) {
        super(activity);
        this.message = message;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        tv_Message.setText(message);
        tv_Title.setText(title);
        accept = (ImageButton) findViewById(R.id.bt_accept);
        cancel = (ImageButton) findViewById(R.id.bt_cancel);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAccept();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initView(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.back_pop_up);
        setContentView(R.layout.custom_dialog_confirm_delete);
        tv_Message = (TextView) findViewById(R.id.tv_Message);
        tv_Title = (TextView) findViewById(R.id.tv_Title);
    }

    public abstract void  onClickAccept();
}
