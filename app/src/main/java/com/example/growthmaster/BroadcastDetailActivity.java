package com.example.growthmaster;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.growthmaster.service.MediaService;

import java.io.File;

public class BroadcastDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView playPause;

    private static final String TAG = "BroadcastDetailActivity";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String mediaUrl;
    private MediaService.ManagerBinder managerBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            managerBinder = (MediaService.ManagerBinder) service;
            if(mediaUrl!=null){
            managerBinder.setUrl(mediaUrl);
            managerBinder.initMediaPlayer();
            } else{
                Log.d(TAG, "onServiceConnected: media is null");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_detail);
        Intent intent = getIntent();
        mediaUrl = intent.getStringExtra("media_url").trim();
        Log.d(TAG, "onCreate: url: " + mediaUrl);

        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        initView();

        Intent serviceIntent = new Intent(this,MediaService.class);
        startService(serviceIntent);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);

//        if(ContextCompat.checkSelfPermission(BroadcastDetailActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(BroadcastDetailActivity.this, new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//        }else {
//            initMediaPlayer();  //初始化MediaPlayer
//        }
    }

    private void initView(){
        playPause = (ImageView) findViewById(R.id.iv_play);
        playPause.setOnClickListener(this);
    }

    private void initMediaPlayer(){
        try{
//            File file = new File(Environment.getExternalStorageDirectory(),
//                    "music.mp3");
            mediaPlayer.setDataSource(mediaUrl);
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else{
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
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
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();  //开始播放
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();  //暂停播放
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();  //停止播放
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
//        if(mediaPlayer != null){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
    }

//    private void getMusic(final int offset) {
//        HttpClient.getSongListInfo(mListInfo.getType(), MUSIC_LIST_SIZE, offset, new HttpCallback<OnlineMusicList>() {
//            @Override
//            public void onSuccess(OnlineMusicList response) {
//                lvOnlineMusic.onLoadComplete();
//                mOnlineMusicList = response;
//                if (offset == 0 && response == null) {
//                    ViewUtils.changeViewState(lvOnlineMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
//                    return;
//                } else if (offset == 0) {
//                    initHeader();
//                    ViewUtils.changeViewState(lvOnlineMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_SUCCESS);
//                }
//                if (response == null || response.getSong_list() == null || response.getSong_list().size() == 0) {
//                    lvOnlineMusic.setEnable(false);
//                    return;
//                }
//                mOffset += MUSIC_LIST_SIZE;
//                mMusicList.addAll(response.getSong_list());
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFail(Exception e) {
//                lvOnlineMusic.onLoadComplete();
//                if (e instanceof RuntimeException) {
//                    // 歌曲全部加载完成
//                    lvOnlineMusic.setEnable(false);
//                    return;
//                }
//                if (offset == 0) {
//                    ViewUtils.changeViewState(lvOnlineMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
//                } else {
//                    ToastUtils.show(R.string.load_fail);
//                }
//            }
//        });
//    }
}
