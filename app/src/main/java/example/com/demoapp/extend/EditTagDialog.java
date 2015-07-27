package example.com.demoapp.extend;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import example.com.demoapp.R;

/**
 * Created by Tony on 27/7/2015.
 */
public class EditTagDialog extends BaseDialog{
    EditText et_TagName;
    TextView tv_Error;
    String tag_name;

    public EditTagDialog(Activity activity,String tag_name) {
        super(activity);
        this.tag_name = tag_name;
    }

    public String getTag_name() {
        tag_name = et_TagName.getText().toString();
        return tag_name;
    }

    public void setEt_Error(String messeage) {
        tv_Error.setText(messeage);
    }

    public void clearEt_Error(){
        tv_Error.setText(" ");
    }
    @Override
    protected void initView() {
        super.initView();
        tv_Error = (TextView) findViewById(R.id.tv_Error);
        et_TagName = (EditText) findViewById(R.id.et_TagName);
        et_TagName.setText(tag_name);
        et_TagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEt_Error();
            }
        });
    }

    @Override
    public void onClickAccept() {
    }

    @Override
    protected int setContentViewLayout(){
        return R.layout.custom_dialog_edit_tag;
    }
}
