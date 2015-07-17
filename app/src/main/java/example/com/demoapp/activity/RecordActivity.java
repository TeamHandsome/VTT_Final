package example.com.demoapp.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import at.markushi.ui.CircleButton;
import example.com.demoapp.R;

public class RecordActivity extends ActionBarActivity {
    CircleButton bt_record, bt_play, bt_record_stop;
    EditText ed_nameRecord;
    TextView tv_showtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //hien thi icon back
        tv_showtime = (TextView) findViewById(R.id.tv_showtime);
        ed_nameRecord = (EditText) findViewById(R.id.ed_nameRecord);
        bt_play = (CircleButton) findViewById(R.id.bt_play);
        bt_record = (CircleButton) findViewById(R.id.bt_circle);
        bt_record_stop = (CircleButton) findViewById(R.id.bt_circle_stop);


        final CounterClass timer = new CounterClass(5000, 1000);
        ;
        bt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_record.setVisibility(View.INVISIBLE);
                bt_record_stop.setVisibility(View.VISIBLE);
                timer.start();
            }
        });
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "aaaaaaa", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv_showtime.setText("Completed.");
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            tv_showtime.setText(hms);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
