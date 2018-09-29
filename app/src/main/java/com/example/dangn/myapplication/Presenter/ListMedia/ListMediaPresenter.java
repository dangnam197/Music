package com.example.dangn.myapplication.Presenter.ListMedia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v4.content.LocalBroadcastManager;


import com.example.dangn.myapplication.Model.AudioModel;
import com.example.dangn.myapplication.Model.ListAudio;
import com.example.dangn.myapplication.R;
import com.example.dangn.myapplication.Service.MediaService;
import com.example.dangn.myapplication.View.ListMedia.IListMediaView;

import java.util.ArrayList;

public class ListMediaPresenter implements IListMediaPresenter {
    private IListMediaView listMediaView;
    private Context context;
    private ArrayList<AudioModel> list;
    private MediaMetadataRetriever retriever;
    private String path;
    private Bitmap bitmap;
    private LocalBroadcastManager localBroadcastManager;
    private int poisition;
    public ListMediaPresenter(IListMediaView listMediaView, Context context) {
        this.listMediaView = listMediaView;
        this.context = context;
        list = new ArrayList<>();
        LoadData();
        getPoisitionSong();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter filter = new IntentFilter(MediaService.POISITIONSONG_ACTION);
        localBroadcastManager.registerReceiver(broadcastComple,filter);
        listMediaView.setListView(list);
    }
    public void LoadData(){
        ListAudio listAudio = new ListAudio(context);
        list = listAudio.getListAudio();

    }
    public void getPoisitionSong(){
        Intent intent = new Intent(context,MediaService.class);
        intent.setAction(MediaService.POISITIONSONG_ACTION);
        context.startService(intent);
    }
    private BroadcastReceiver broadcastComple = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            poisition = intent.getIntExtra(MediaService.POISITIONSONG_ACTION, 0);
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(list.get(poisition).getPath());
            byte[] img = retriever.getEmbeddedPicture();
            if (img != null) {
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            }
            else {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
            }
            listMediaView.setBackground(bitmap);


        }
    };
    @Override
    public void clickItem(int position) {
        Intent start = new Intent(context, MediaService.class);
        start.setAction(MediaService.POISITIONSONGTO_ACTION);
        start.putExtra(MediaService.POISITIONSONGTO_ACTION,position);
        context.startService(start);
    }
}
