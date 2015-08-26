package example.com.demoapp.activity;

import android.app.Activity;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import example.com.demoapp.R;
import example.com.demoapp.extend.AndroidMediaPlayer;
import example.com.demoapp.extend.Speaker;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

public class PopupActivity extends Activity {
    private Speaker speaker;

    private TextView tv_vn;
    private ImageView img_body;
    private ImageButton bt_cancel,bt_replay;

    private String id;
    private String sound;
    private String img;
    private String vn_name;
    private String jp_name;

    private boolean soundAvailable = true;
    private boolean imgAvailable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        PopupActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.back_pop_up);
        this.setLayoutWidth();
        //set button cancel
        bt_cancel = (ImageButton) findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(listener);
        //set button replay
        bt_replay = (ImageButton) findViewById(R.id.bt_replay);
        bt_replay.setOnClickListener(listener);

        speaker = new AndroidMediaPlayer(PopupActivity.this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            SentenceItem item = extras.getParcelable(Consts.DATA);
            id = item.getId();
            sound = item.getSound();
            img = item.getImage();
            vn_name = item.getNameVn();
            jp_name = item.getNameJp();

            this.setTextView();
            this.setImageView();
            soundAvailable = speaker.speak(item);
            if (!soundAvailable){
                bt_replay.setEnabled(false);
            }
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_cancel:
                    if (soundAvailable) {
                        speaker.stopSpeak();
                    }
                    finish();
                    break;
                case R.id.bt_replay:
                    if (soundAvailable) {
                        speaker.resumeSpeak();
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(event)) {
            if (soundAvailable) {
                speaker.stopSpeak();
            }
            finish();
            return true;
        }
        return false;
    }

    private boolean isOutOfBounds(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(this).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop)) || (y > (decorView.getHeight() + slop));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void setLayoutWidth(){
        Point size = Common.getScreenSizeInPixels(PopupActivity.this);
        int width = size.x;
        int y = width * 191 / 216;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.popup_sen);
        relativeLayout.getLayoutParams().width = y;
    }

    private void setTextView(){
        tv_vn = (TextView) findViewById(R.id.tv_vn);
        tv_vn.setText(vn_name.toUpperCase());
    }

    private void setImageView(){
        if (id.contains("s")) {
            this.setImageViewURI(img);
        } else {

            Uri uri = StringUtils.buildDrawableUri(this.getPackageName(), img);
            this.setImageViewURI(uri);
        }
    }

    private void setImageViewURI(Uri uri){
        this.initImageView();
        Picasso.with(this)
                .load(uri)
                .resize(360, 360)
                .centerCrop()
                .into(this.img_body);
    }

    private void setImageViewURI(String uri){
        this.initImageView();
        if (!uri.isEmpty()) {
            Picasso.with(this)
                    .load(uri)
                    .resize(360, 360)
                    .centerCrop()
                    .into(this.img_body);
        }
    }

    private void initImageView(){
        Point size = Common.getScreenSizeInPixels(PopupActivity.this);
        int width = size.x;
        int y = width / 2;
        if (img.isEmpty()) y = y/3;
        img_body = (ImageView) findViewById(R.id.img_body);
        img_body.setMaxHeight(y);
        img_body.setMaxWidth(y);
        img_body.setMinimumWidth(y);
        img_body.setMinimumHeight(y);
        img_body.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
