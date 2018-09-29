package com.example.dangn.myapplication.View.ListMedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dangn.myapplication.Model.AudioModel;
import com.example.dangn.myapplication.Presenter.ListMedia.IListMediaPresenter;
import com.example.dangn.myapplication.Presenter.ListMedia.ListMediaPresenter;
import com.example.dangn.myapplication.R;
import com.example.dangn.myapplication.View.Adapter.MediaAdapter;
import com.example.dangn.myapplication.View.Blur.Blur;
import com.example.dangn.myapplication.View.Main.MainActivity;

import java.util.ArrayList;

public class ListMediaActivity extends AppCompatActivity implements IListMediaView {
    private ListView listViewMedia;
    private IListMediaPresenter listMediaPresenter;
    private ImageView imgBackground;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmedia);
        Init();
        listViewClickItem();

    }
    public void Init(){
        listViewMedia = (ListView) findViewById(R.id.lv_media);
        listMediaPresenter = new ListMediaPresenter(this,getApplicationContext());
        imgBackground = (ImageView) findViewById(R.id.list_media_background);
    }
    public void listViewClickItem(){
        listViewMedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listMediaPresenter.clickItem(i);
                Intent intent = new Intent(ListMediaActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void setListView(ArrayList<AudioModel> list) {
        MediaAdapter mediaAdapter = new MediaAdapter(getApplicationContext(),R.layout.list_media,list);
        //ArrayAdapter mediaAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list.);
        listViewMedia.setAdapter(mediaAdapter);

    }

    @Override
    public void setBackground(Bitmap background) {
        Bitmap bitmapBlur = Blur.blur(getApplicationContext(),background);
        imgBackground.setImageBitmap(bitmapBlur);
    }

}
