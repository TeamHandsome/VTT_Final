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
public class ConfirmDeleteDialog extends BaseDialog {
    private TextView tv_Message, tv_Title;
    private String title,message;

    public ConfirmDeleteDialog(Activity activity,String title, String message) {
        super(activity);
        this.message = message;
        this.title = title;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_Message = (TextView) findViewById(R.id.tv_Message);
        tv_Title = (TextView) findViewById(R.id.tv_Title);
        tv_Message.setText(message);
        tv_Title.setText(title);
    }

    @Override
    public void onClickAccept() {
    }

    @Override
    protected int setContentViewLayout(){
        return R.layout.custom_dialog_confirm_delete;
    }
}
