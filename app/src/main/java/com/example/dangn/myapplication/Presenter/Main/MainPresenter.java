package com.example.dangn.myapplication.Presenter.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v4.content.LocalBroadcastManager;

import com.example.dangn.myapplication.R;
import com.example.dangn.myapplication.Service.MediaService;
import com.example.dangn.myapplication.View.Main.IMainView;

public class MainPresenter implements IMainPresenter {
    private IMainView mainView;
    private Context context;
    private MediaMetadataRetriever retriever;
    private Bitmap bitmap,bmPlay;
    private String path="",title,artist;
    private LocalBroadcastManager localBroadcastManager;
    private int time = 0;
    public MainPresenter(IMainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
        startService(MediaService.STARTMEDIASERVER_ACTION);
        localBroadcastManager =LocalBroadcastManager.getInstance(context);
        IntentFilter filter = new IntentFilter(MediaService.MEDIAINIT_ACTION);
        localBroadcastManager.registerReceiver(broadcastComple,filter);
        IntentFilter filterUpdateTime = new IntentFilter(MediaService.UPDATE_ACTION);
        localBroadcastManager.registerReceiver(updateTime,filterUpdateTime);
        IntentFilter filterUpdateButton = new IntentFilter(MediaService.UPDATEBUTTON_ACTION);
        localBroadcastManager.registerReceiver(updateButon,filterUpdateButton);
        //run();


    }
    public void startService(String action){
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(action);
        context.startService(intent);
    }
    private BroadcastReceiver broadcastComple = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            path = intent.getStringExtra(MediaService.PATH_ACTION);
            int duration = intent.getIntExtra(MediaService.DURATION_ACTION, 0);
            title = intent.getStringExtra(MediaService.TITLE_ACTION);
            artist = intent.getStringExtra(MediaService.ARTIST_ACTION);
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            byte[] img = retriever.getEmbeddedPicture();
            if (img != null) {
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            }
            else {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
            }
            mainView.Init(duration, bitmap,title,artist);

        }
    };
    private BroadcastReceiver updateTime = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            time = intent.getIntExtra(MediaService.UPDATE_ACTION,0);
            mainView.updateView(time);
            mainView.text(intent.getStringExtra("nam"));
        }
    };
    private BroadcastReceiver updateButon =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean play;
            play = intent.getBooleanExtra(MediaService.UPDATEBUTTON_ACTION,true);
            int resPlay;
            if(play == MediaService.isPlay){
               resPlay = R.drawable.ic_pause_background;
            }else {
              resPlay = R.drawable.ic_play_background;
            }
            mainView.updateButton(resPlay,0);
        }
    };
    @Override
    public void play() {
        startService(MediaService.PLAY_ACTION);
    }

    @Override
    public void next() {
        startService(MediaService.NEXT_ACTION);

    }

    @Override
    public void prev() {
        startService(MediaService.PREV_ACTION);
    }

    @Override
    public void setProgress(int progress) {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(MediaService.SEEKTO_ACTION);
        intent.putExtra(MediaService.SEEKTO_ACTION,progress);
        context.startService(intent);
    }

    @Override
    public void show() {

    }

    @Override
    public void updateStart() {
        Intent intent = new Intent(context,MediaService.class);
        intent.setAction(MediaService.UPDATESTART_ACTION);
        context.startService(intent);
    }

    @Override
    public void updatePause() {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(MediaService.UPDATEPAUSE_ACTION);
        context.startService(intent);
    }

    @Override
    public void updateButton() {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(MediaService.UPDATEBUTTON_ACTION);
        context.startService(intent);
    }

}
