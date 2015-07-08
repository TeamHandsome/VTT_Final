package example.com.demoapp.subCategory;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;

public class PopupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        MediaPlayer mPlayer = new MediaPlayer();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String soundPath = extras.getString(Consts.POSITION);
            Log.i("Sound path: ", soundPath+"");
            Uri uri=Uri.parse("android.resource://" + getPackageName() + "/" +
                    "raw" + "/" + soundPath);
            mPlayer = MediaPlayer.create(PopupActivity.this, uri);
            mPlayer.start();

        }

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
