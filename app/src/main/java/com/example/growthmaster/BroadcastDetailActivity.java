package com.example.growthmaster;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.growthmaster.api.BroadcastDetailApi;
import com.example.growthmaster.bean.FM;
import com.example.growthmaster.service.MediaService;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BroadcastDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int UPDATE_TEXT = 1;

    private ImageView ivCover;
    private ImageView playPause;
    private TextView currentTime, totalTime;
    private SeekBar progressBar;

    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");

    private List<FM> fmList;
    private FM data;

    private static final String TAG = "BroadcastDetailActivity";

    private String mediaId;
    private String mediaUrl;
    private int mediaCurrentTime;
    private int mediaTotalTime;
    private BroadcastDetailApi broadcastDetailApi;
    private BroadcastDetailTask broadcastDetailTask;

    private MediaPlayer mediaPlayer;
    private MediaService.ManagerBinder managerBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            managerBinder = (MediaService.ManagerBinder) service;
            managerBinder.play(mediaUrl);
            playPause.setSelected(true);
            mediaPlayer = managerBinder.getMediaPlayer();
            Log.d(TAG, "onServiceConnected: "+mediaPlayer.isPlaying());

            //使用定时器,每隔500毫秒让handler发送一个空信息
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.d(TAG, "run: XXXXXXXXXXXX");
                    if( mediaPlayer.isPlaying()){
                        myHandler.sendEmptyMessage(UPDATE_TEXT);
                        Log.d(TAG, "run: playing");
                    }else{
                        Log.d(TAG, "run: stop ");
                    }

                    Log.d(TAG, "run: service connection");
                }
            }, 0,500);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    private static class MyHandler extends Handler {
        private final WeakReference<BroadcastDetailActivity> mActivity;

        public MyHandler(BroadcastDetailActivity broadcastDetailActivity){
            mActivity = new WeakReference<>(broadcastDetailActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BroadcastDetailActivity broadcastDetailActivity = mActivity.get();
            if (broadcastDetailActivity != null){
                switch (msg.what){
                    case UPDATE_TEXT:
                        Log.d(TAG, "handleMessage: "+broadcastDetailActivity.mediaPlayer.getCurrentPosition());

                        broadcastDetailActivity.currentTime.setText(broadcastDetailActivity.time.format(broadcastDetailActivity.mediaPlayer.getCurrentPosition()));
                        Log.d(TAG, "handleMessage: "+broadcastDetailActivity.mediaPlayer.getCurrentPosition());
                        broadcastDetailActivity.progressBar.setProgress(broadcastDetailActivity.mediaPlayer.getCurrentPosition());
                        break;
                    default:
                        break;
                }
            }

        }


    }

    private final MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_detail);

        //获取从上一个Activity传入的选择的FM的id
        Intent intent = getIntent();
        mediaId = intent.getStringExtra("object_id");
        mediaUrl = intent.getStringExtra("media_url").trim();
        
        Log.d(TAG, "onCreate: url: " + mediaUrl);

        initView();
        initData();

        Intent serviceIntent = new Intent(this,MediaService.class);
        startService(serviceIntent);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);



    }

    private void initView() {
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        playPause = (ImageView) findViewById(R.id.iv_play);
        playPause.setOnClickListener(this);
        currentTime = (TextView) findViewById(R.id.tv_current_time);
        totalTime = (TextView) findViewById(R.id.tv_total_time);
        progressBar = (SeekBar) findViewById(R.id.sb_progress);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser == true){
                    mediaCurrentTime = progress;
                    currentTime.setText(time.format(mediaCurrentTime));
                    managerBinder.getMediaPlayer().seekTo(mediaCurrentTime);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initData(){
        data = new FM();
        broadcastDetailApi = new BroadcastDetailApi();
        broadcastDetailTask = new BroadcastDetailTask();
        broadcastDetailTask.execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_play:
                managerBinder.playPause();
                if(playPause.isSelected()){
                    playPause.setSelected(false);
                }else {
                    playPause.setSelected(true);
                }
                Log.d(TAG, "onClick: play or pause");
                break;
            default:
                break;
        }
    }

    private class BroadcastDetailTask extends AsyncTask<Void,Void, FM> {

        public BroadcastDetailTask(){ }

        @Override
        protected FM doInBackground(Void... params) {
            return broadcastDetailApi.fetchFM(mediaId);
        }

        @Override
        protected void onPostExecute(FM fm) {
            super.onPostExecute(fm);
            Glide.with(BroadcastDetailActivity.this)
                    .load(fm.getCover())
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivCover);
            //时间以毫秒为单位
            mediaTotalTime = fm.getDuration() * 1000;
            totalTime.setText(time.format(mediaTotalTime));
            progressBar.setMax(mediaTotalTime);
            data = fm;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            broadcastDetailTask = null;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        broadcastDetailTask.cancel(true);
    }


}
