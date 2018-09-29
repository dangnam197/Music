package com.example.dangn.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dangn.myapplication.Service.MediaService;

public class MainActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RemoteViews remoteViews =new RemoteViews(getPackageName(),R.layout.notification);
//        Notification notification;
//        notification = new Notification.Builder(this)
//                .setAutoCancel(true)
//                .setColor(Color.RED)
//                .setSmallIcon(R.drawable.ic_android_black_24dp)
//                .setContent(remoteViews)
//                .setContentTitle("Đặng phương Nam")
//                .setContentText("Look, white in Lollipop, else color!")
//                .build();
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(1,notification);
        Intent intent = new Intent(getApplicationContext(), MediaService.class);
        intent.setAction(MediaService.STARTMEDIASERVER_ACTION);
        startService(intent);
    }
}
