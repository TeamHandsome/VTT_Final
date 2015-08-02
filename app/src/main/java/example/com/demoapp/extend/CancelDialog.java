package example.com.demoapp.extend;

import android.app.Activity;
import android.widget.TextView;

import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;

/**
 * Created by Tony on 2/8/2015.
 */
public class CancelDialog extends  BaseDialog {
    private TextView tv_Message, tv_Title;

    public CancelDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void initView() {
        super.initView();
        tv_Message = (TextView) findViewById(R.id.tv_Message);
        tv_Title = (TextView) findViewById(R.id.tv_Title);
        tv_Message.setText(Message.CONFIRM_CANCEL);
        tv_Title.setText(Consts.CONFIRM_CANCEL);
    }

    public void setTitle(String title) {
        tv_Title.setText(title);
    }

    public void setMessage(String message) {
        tv_Message.setText(message);
    }

    @Override
    public void onClickAccept() {

    }

    @Override
    protected int setContentViewLayout() {
        return R.layout.custom_dialog_confirm;
    }
}
