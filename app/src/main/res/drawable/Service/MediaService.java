package com.example.dangn.music.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;

import com.example.dangn.music.Model.AudioModel;
import com.example.dangn.music.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class MediaService extends Service {
    public static String MAIN_ACTION = "com.example.dangn.music.Service.MediaService.action.main";
    public static String PREV_ACTION = "com.example.dangn.music.Service.MediaService.action.prev";
    public static String PLAY_ACTION = "com.example.dangn.music.Service.MediaService.action.play";
    public static String NEXT_ACTION = "com.example.dangn.music.Service.MediaService.action.next";
    public static String SEEKTO_ACTION = "com.example.dangn.music.Service.MediaService.action.seekto";
    public static String UPDATE_ACTION = "com.example.dangn.music.Service.MediaService.action.update";
    public static String PATH_ACTION = "com.example.dangn.music.Service.MediaService.action.path";
    public static String DURATION_ACTION = "com.example.dangn.music.Service.MediaService.action.duration";
    public static String STARTMEDIASERVER_ACTION = "com.example.dangn.music.Service.MediaService.action.startforeground";
    public static String STOPMEDIASERVER_ACTION = "com.example.dangn.music.Service.MediaService.action.stopforeground";
    public static String MEDIAINIT_ACTION = "com.example.dangn.music.Service.MediaService.action.mediainit";
    public static String POISITIONSONGTO_ACTION = "com.example.dangn.music.Service.MediaService.action.positionsongto";
    public static String UPDATEPAUSE_ACTION = "com.example.dangn.music.Service.MediaService.action.updatepause";
    public static String UPDATESTART_ACTION = "com.example.dangn.music.Service.MediaService.action.updatestart";
    public static final int NOTIFICATION_ID_FOREGROUND_SERVICE = 8466503;
    private MediaPlayer mediaPlayer;
    private ArrayList<AudioModel> list ;
    private int positionSong = 0;
    private RemoteViews remoteViews;
    private NotificationManager notificationManager;
    private Boolean isRuning = true;
    private LocalBroadcastManager localBroadcastManager;
    private int seekto = 0;
    private AtomicBoolean isPlay ;
    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        list  = new ArrayList<>();
        isPlay = new AtomicBoolean();
        isPlay.set(true);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals(STARTMEDIASERVER_ACTION)) {
            if (isRuning)
            {
                LoadData();
                isRuning = false;
                startForeground(1, prepareNotification(intent));
                mediaInit();
                updateTime();
            }else {
                mediaInit();
            }
        }
        else if(intent.getAction().equals(NEXT_ACTION)){
            next();
            notificationManager.notify(1,prepareNotification(intent));
        }
        else if(intent.getAction().equals(PLAY_ACTION)){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                isPlay.set(true);
            }else {
                mediaPlayer.start();
                isPlay.set(false);
                synchronized (isPlay){
                    isPlay.notifyAll();
                }
            }
            notificationManager.notify(1,prepareNotification(intent));
        }
        else if(intent.getAction().equals(PREV_ACTION)){
            prev();
            notificationManager.notify(1,prepareNotification(intent));
        }
        else if(intent.getAction().equals(STOPMEDIASERVER_ACTION)){
            stopForeground(true);
            stopSelf();
            mediaPlayer.stop();
            updatePause();
        }
        else if(intent.getAction().equals(SEEKTO_ACTION)){
            seekto = intent.getIntExtra(SEEKTO_ACTION,0);
            mediaPlayer.seekTo(seekto);
        }
        else if(intent.getAction().equals(POISITIONSONGTO_ACTION)){
            int poisition = intent.getIntExtra(POISITIONSONGTO_ACTION,0);
            positionSong = (poisition >= list.size()) ? 0:poisition;
            setMediaData(positionSong);
            updateStart();
            mediaPlayer.start();
            notificationManager.notify(1,prepareNotification(intent));
        }
        else if(intent.getAction().equals(UPDATESTART_ACTION)){
            if(mediaPlayer.isPlaying())
            {
                updateStart();
            }
        }
        else if(intent.getAction().equals(UPDATEPAUSE_ACTION)){
            updatePause();
        }
        return START_NOT_STICKY;
    }
    private void updateStart(){
        isPlay.set(false);
        synchronized (isPlay){
            isPlay.notifyAll();
        }
    }
    private void updatePause(){
        isPlay.set(true);
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private Notification prepareNotification(Intent intent){
        Intent notificationIntent = new Intent(this, com.example.dangn.music.View.Main.MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification);

        Intent previousIntent = new Intent(this, com.example.dangn.music.Service.MediaService.class);
        previousIntent.setAction(PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_previous,ppreviousIntent);

        Intent playIntent = new Intent(this, com.example.dangn.music.Service.MediaService.class);
        playIntent.setAction(PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_play_pause,pplayIntent);

        Intent nextIntent = new Intent(this, com.example.dangn.music.Service.MediaService.class);
        nextIntent.setAction(NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_next,pnextIntent);

        Intent stopIntent = new Intent(this, com.example.dangn.music.Service.MediaService.class);
        stopIntent.setAction(STOPMEDIASERVER_ACTION);
        PendingIntent pstopIntent = PendingIntent.getService(this, 0,
                stopIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_close,pstopIntent);

//        if(intent.getAction().equals(PLAY_ACTION)){
            if(mediaPlayer.isPlaying()){
                remoteViews.setImageViewResource(R.id.notification_play_pause,R.drawable.ic_pause);
            }
            else {
                remoteViews.setImageViewResource(R.id.notification_play_pause,R.drawable.ic_play);
            }
        remoteViews.setTextViewText(R.id.notification_title,list.get(positionSong).getTrack_name());
        remoteViews.setTextViewText(R.id.notification_text,list.get(positionSong).getArtist());
        remoteViews.setImageViewResource(R.id.notification_previous,R.drawable.ic_skip_previous);
        remoteViews.setImageViewResource(R.id.notification_next,R.drawable.ic_skip_next);
        remoteViews.setImageViewResource(R.id.notification_close,R.drawable.ic_close);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Notification noti = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_pause)
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(remoteViews)
                    .setStyle(new Notification.DecoratedMediaCustomViewStyle())
                    .build();
            noti.flags |= Notification.FLAG_FOREGROUND_SERVICE;
            return noti;
        }

        Notification notification;
        notification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContent(remoteViews)
                .setContentTitle("Đặng phương Nam")
                .setContentText("Look, white in Lollipop, else color!")
                .build();
        return notification;
    }
    public void LoadData(){
        com.example.dangn.music.Model.ListAudio listAudio = new com.example.dangn.music.Model.ListAudio(getApplicationContext());
        list = listAudio.getListAudio();
        if(!list.isEmpty()){
            setMediaData(positionSong);
        }
    }
    private void setMediaData(int progress){
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(list.get(progress).getPath()));
        mediaComple();
    }
    private void next(){
        positionSong = (positionSong == list.size()-1)? 0:positionSong+1;
        setMediaData(positionSong);
        mediaPlayer.start();
        mediaInit();
    }
    private  void prev(){
        positionSong = (positionSong == 0)? (list.size()-1):positionSong-1;
        setMediaData(positionSong);
        mediaPlayer.start();
        mediaInit();
    }

    private void mediaInit(){
        if (!list.isEmpty()){
            Intent intent = new Intent(MEDIAINIT_ACTION);
            intent.putExtra(DURATION_ACTION,mediaPlayer.getDuration());
            intent.putExtra(PATH_ACTION,list.get(positionSong).getPath());
            localBroadcastManager.sendBroadcast(intent);
        }

    }
    public void mediaComple(){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(positionSong<list.size()-1) {
                    next();
                }
            }
        });
    }
    private void updateTime(){
        final Intent intent = new Intent(UPDATE_ACTION);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                        while (true){

                            synchronized (isPlay){
                                if(isPlay.get()){

                                    isPlay.wait();
                                }
                            }
                            intent.putExtra(UPDATE_ACTION,mediaPlayer.getCurrentPosition());
                            intent.putExtra("nam",mediaPlayer.getCurrentPosition()+" + "+mediaPlayer.getDuration());
                            localBroadcastManager.sendBroadcast(intent);
                            Thread.sleep(500);

                        }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}