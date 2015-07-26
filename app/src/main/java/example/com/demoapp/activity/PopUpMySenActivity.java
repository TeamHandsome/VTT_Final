package example.com.demoapp.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
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
import example.com.demoapp.utility.Consts;

public class PopUpMySenActivity extends Activity {
    String soundPath;
    String vn_name;
    String img;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_my_sen);
        mPlayer = new MediaPlayer();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            soundPath = extras.getString(Consts.POSITION);
            vn_name = extras.getString("vn_name");
            img = extras.getString("img");
            ///////
            if (soundPath != null) {
                try {
                    mPlayer.setDataSource(soundPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.start();
            }
            //////
            ImageView img_body = (ImageView) findViewById(R.id.img_body);
            TextView tv_vn = (TextView) findViewById(R.id.tv_vn);
            tv_vn.setText(vn_name);
            //////
            if (!img.isEmpty()){
                Picasso.with(this)
                        .load(img)
                        .resize(360, 360)
                        .centerCrop()
                        .into(img_body);
            }
        }
        ImageButton bt_cancel = (ImageButton) findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPath != null){
                    mPlayer.stop();}
                finish();
            }
        });
        ImageButton bt_replay = (ImageButton) findViewById(R.id.bt_replay);
        bt_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.start();
            }
        });
    }
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(event)) {
            if (soundPath != null){
                mPlayer.stop();}
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
        getMenuInflater().inflate(R.menu.menu_pop_up_my_sen, menu);
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
}
