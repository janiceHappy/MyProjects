package com.example.growthmaster.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.growthmaster.BroadcastDetailActivity;
import com.example.growthmaster.bean.FM;

import java.io.IOException;
import java.util.List;

public class MediaService extends Service {

    private static final String TAG = "MediaService";
    private MediaPlayer mediaPlayer;
    private ManagerBinder mBinder = new ManagerBinder();
    private List<FM> fmList;


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class ManagerBinder extends Binder{

        public MediaPlayer getMediaPlayer(){ return mediaPlayer; }

        public void play(String url){
            try{
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.setVolume(1.0f,1.0f);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        Log.d(TAG, "onPrepared: "+mediaPlayer.isPlaying());
                    }
                });
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void playPause(){

            Log.d(TAG, "playPause: ");
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();  //开始播放
                Log.d(TAG, "play"+mediaPlayer.isPlaying());
            }else {
                mediaPlayer.pause();  //暂停播放
                Log.d(TAG, "pause"+mediaPlayer.isPlaying());
            }
        }


    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();

        Log.d(TAG, "onCreate: 初始化MediaPlayer");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
