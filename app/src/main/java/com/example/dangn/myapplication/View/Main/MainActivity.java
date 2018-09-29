package com.example.dangn.myapplication.View.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dangn.myapplication.Model.TimeModel;
import com.example.dangn.myapplication.Presenter.Main.IMainPresenter;
import com.example.dangn.myapplication.Presenter.Main.MainPresenter;
import com.example.dangn.myapplication.R;
import com.example.dangn.myapplication.View.Blur.Blur;
import com.example.dangn.myapplication.View.ListMedia.ListMediaActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IMainView{
    SeekBar seekBar;
    ImageButton play,pre,next;
    TextView tvTimeDuration,tvTimeCurent,tvTitle,tvArtist;
    TimeModel timeDuration,timeCurent;

    ImageView imageView,background;
    private IMainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    try {
                        while (true){
                            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                init();
                                setEvent();
                                break;
                            }
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    init();
                    setEvent();
                }
            }
        });
    }
    public void setEvent(){
        pre.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTimeCurent.setText(timeCurent.timeToString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mainPresenter.setProgress(seekBar.getProgress());
            }
        });
    }
    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

        }
    }
    @Override
    protected void onResume() {
        mainPresenter.updateStart();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mainPresenter.updatePause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mainPresenter.updatePause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.updatePause();
        super.onDestroy();
    }

    public void init(){
        imageView = (ImageView) findViewById(R.id.imageView);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvArtist = (TextView) findViewById(R.id.tvArtist) ;
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        pre = (ImageButton) findViewById(R.id.btnPre);
        play = (ImageButton) findViewById(R.id.btnPlay);
        next = (ImageButton) findViewById(R.id.btnNext);
        background = (ImageView) findViewById(R.id.background) ;
        tvTimeCurent = (TextView) findViewById(R.id.tvTimeCurent);
        tvTimeDuration = (TextView) findViewById(R.id.tvTimeDuration);
        //imageView = (ImageView) findViewById(R.id.imageView);
        timeDuration = new TimeModel();
        timeCurent = new TimeModel();
        mainPresenter = new MainPresenter(MainActivity.this,getApplicationContext());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list_media) {
            Intent start= new Intent(MainActivity.this, ListMediaActivity.class);
            startActivity(start);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
            {
                mainPresenter.play();
                break;
            }
            case R.id.btnNext:
            {
                mainPresenter.next();
                break;
            }
            case R.id.btnPre:
            {
                mainPresenter.prev();
                break;
            }

        }
    }

    @Override
    public void Init(int seekbarMax, Bitmap bitmap, String title, String artist) {
        tvTimeDuration.setText(timeDuration.timeToString(seekbarMax));
        tvTimeCurent.setText(timeCurent.timeToString(0));
        seekBar.setMax(seekbarMax);
        imageView.setImageBitmap(bitmap);
        Bitmap bitmapBlur = Blur.blur(getApplicationContext(),bitmap);
        background.setImageBitmap(bitmapBlur);
        tvTitle.setText(title);
        tvArtist.setText(artist);
    }

    @Override
    public void updateView(final int progress) {
        seekBar.setProgress(progress);
       // tvTimeCurent.setText(timeCurent.timeToString(progress));

    }

    @Override
    public void text(String text) {

    }

    @Override
    public void updateButton(int resPlay, int resLoop) {
        play.setImageResource(resPlay);
    }


}
