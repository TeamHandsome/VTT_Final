package example.com.demoapp.activity;

import android.app.Activity;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import example.com.demoapp.R;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

public class PopupActivity extends Activity {
    String soundPath;
    String vn_name;
    String img;
    MediaPlayer mPlayer;
    private int action_type = -1;

    TextView tv_vn;
    ImageView img_body;
    ImageButton bt_cancel,bt_replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        PopupActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.back_pop_up);

        this.action_type = getIntent().getIntExtra(Consts.ACTION_TYPE, Consts.NOT_FOUND);
        mPlayer = new MediaPlayer();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            soundPath = extras.getString(Consts.SOUND_PATH);
            vn_name = extras.getString(Consts.NAME_VN);
            img = extras.getString(Consts.URI);

            this.setTextView();
            if (action_type == Consts.POP_UP_MYSEN) {
                ///////
                if (soundPath != null) {
                    try {
                        mPlayer.setDataSource(soundPath);
                        mPlayer.prepare();
                        if (mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        mPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //////
                this.setImageViewURI(img);
            } else {
                ///////
                if (!soundPath.isEmpty() && soundPath !=null) {
                    Uri uri = StringUtils.buildRawUri(this.getPackageName(), soundPath);
                    mPlayer = MediaPlayer.create(PopupActivity.this, uri);
                    if (mPlayer.isPlaying()) {
                        mPlayer.stop();
                    }
                    mPlayer.start();
                }

                Uri uri = StringUtils.buildDrawableUri(this.getPackageName(), img);
                this.setImageViewURI(uri);
            }
        }

        //set button cancel
        bt_cancel = (ImageButton) findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPath != null) {
                    mPlayer.stop();
                }
                finish();
            }
        });
        //set button replay
        bt_replay = (ImageButton) findViewById(R.id.bt_replay);
        bt_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.start();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(event)) {
            if (soundPath != null) {
                mPlayer.stop();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setTextView(){
        tv_vn = (TextView) findViewById(R.id.tv_vn);
        tv_vn.setText(vn_name.toUpperCase());
    }

    private void setImageViewURI(Uri uri){
        this.intImageView();
        Picasso.with(this)
                .load(uri)
                .resize(360, 360)
                .centerCrop()
                .into(this.img_body);
    }

    private void setImageViewURI(String uri){
        this.intImageView();

        if (!uri.isEmpty()) {
            Picasso.with(this)
                    .load(uri)
                    .resize(360, 360)
                    .centerCrop()
                    .into(this.img_body);
        }
    }

    private void intImageView(){
        Point size = Common.getScreenSizeInPixels(PopupActivity.this);
        int width = size.x;
        int y = width / 2;

        img_body = (ImageView) findViewById(R.id.img_body);
        img_body.setMaxHeight(y);
        img_body.setMaxWidth(y);
        img_body.setMinimumWidth(y);
        img_body.setMinimumHeight(y);
        img_body.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
