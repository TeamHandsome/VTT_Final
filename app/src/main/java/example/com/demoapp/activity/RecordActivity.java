package example.com.demoapp.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import at.markushi.ui.CircleButton;
import example.com.demoapp.R;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.StringUtils;

public class RecordActivity extends ActionBarActivity {
    CircleButton bt_record_record, bt_record_play, bt_record_stop;
    ImageButton bt_cancel_record, bt_accept_record;
    EditText ed_nameRecord;
    TextView tv_showtime;
    CounterClass timer;

    final String MAX_RECORD_TIME = StringUtils.buildStringTime(Consts.MAX_RECORD_TIME_MILLISECOND);

    private MediaRecorder myAudioRecorder;
    private String outputFile;
    private String db_path = "/sdcard/Android/data/com.demoapp.app/";
    private String recording_namefile = System.currentTimeMillis()+"";      //save random name Record

    long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //hien thi icon back
        //create folder  to save name record
        File mydir = new File(db_path);
        mydir.mkdir();
//        randomStringRecord();

        tv_showtime = (TextView) findViewById(R.id.tv_showtime);
        ed_nameRecord = (EditText) findViewById(R.id.ed_nameRecord);
        bt_record_play = (CircleButton) findViewById(R.id.bt_play);
        bt_record_record = (CircleButton) findViewById(R.id.bt_circle_record);
        bt_record_stop = (CircleButton) findViewById(R.id.bt_circle_stop);
        bt_cancel_record = (ImageButton) findViewById(R.id.bt_cancel_record);
        bt_accept_record = (ImageButton) findViewById(R.id.bt_accept_record);
        bt_record_record.setOnClickListener(listener);
        bt_record_play.setOnClickListener(listener);
        bt_cancel_record.setOnClickListener(listener);
        bt_accept_record.setOnClickListener(listener);


    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_circle_record:
                    bt_record_record.setVisibility(View.GONE);
                    bt_record_stop.setVisibility(View.VISIBLE);
                    timer = new CounterClass(Consts.MAX_RECORD_TIME_MILLISECOND, 1000);
                    timer.start();

                    PackageManager pmanager = getApplicationContext().getPackageManager();
                    if (pmanager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
                        try {
                            outputFile = db_path  + recording_namefile+".mp3";
                            myAudioRecorder = new MediaRecorder();
                            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                            myAudioRecorder.setOutputFile(outputFile);
                            myAudioRecorder.prepare();
                            myAudioRecorder.start();
//                            new Auto_Stop_Task().execute();  // call this function to automatically after 5s
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "This device doesn't have a mic!", Toast.LENGTH_LONG).show();
                    }

                    break;
                case R.id.bt_circle_stop:
                    stopRecording();
                    timer.cancel();
                    break;
                case R.id.bt_play:
                    // Handle prevent click many times
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    MediaPlayer m = new MediaPlayer();
                    try {
                        m.setDataSource(outputFile);
                        m.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    m.start();
                    Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
                    break;
                case R.id.bt_cancel_record:
                    outputFile = "";
                    File file = new File(outputFile);
                    file.delete();
                    finish();
                    break;
                case R.id.bt_accept_record:
                    Intent intent = new Intent();
                    intent.putExtra(Consts.DATA, outputFile);
                    setResult(AddEditMySentencesActivity.RESULT_CODE_RECORD, intent);
                    finish();
                    break;

            }
        }
    };

    public class Auto_Stop_Task extends AsyncTask<Void,Void, Integer> {
        //int flag=0;
        @Override
        protected Integer doInBackground(Void... arg0) {

            try {
                Thread.sleep(Consts.MAX_RECORD_TIME_MILLISECOND);
                myAudioRecorder.stop();
                myAudioRecorder.reset();
                //flag=1;
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            return null;
        }

    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv_showtime.setText("00:00");
            stopRecording();
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
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

    public void stopRecording(){
        bt_record_stop.setVisibility(View.GONE);
        bt_record_record.setVisibility(View.VISIBLE);
        myAudioRecorder.stop();
        myAudioRecorder.reset();
        tv_showtime.setText(MAX_RECORD_TIME);
    }
}
