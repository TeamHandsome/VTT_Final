package example.com.demoapp.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;

public class PopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
        MediaPlayer mPlayer = new MediaPlayer();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String soundPath = extras.getString(Consts.POSITION);
            String vn_name = extras.getString("vn_name");
            String img = extras.getString("img");
            ///////
            if (soundPath!=null){
                Uri uri=Uri.parse("android.resource://" + getPackageName() + "/" +
                        "raw" + "/" + soundPath);
                mPlayer = MediaPlayer.create(PopupActivity.this, uri);
                if(mPlayer.isPlaying()){
                    mPlayer.stop();
                }
                mPlayer.start();
            }
            //////
            TextView tv_vn = (TextView) findViewById(R.id.tv_vn);
            tv_vn.setText(vn_name);
            //////
            ImageView img_body = (ImageView) findViewById(R.id.img_body);
            Uri uri1 =Uri.parse("android.resource://" + this.getPackageName() + "/" +
                    "drawable" + "/" + img);

            Picasso.with(this)
                    .load(uri1)
                    .resize(360, 360)
                    .centerCrop()
                    .into(img_body);

        }
        ImageButton bt_cancel = (ImageButton) findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageButton bt_replay = (ImageButton) findViewById(R.id.bt_replay);
        final MediaPlayer finalMPlayer = mPlayer;
        bt_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalMPlayer.start();
            }
        });
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


}
