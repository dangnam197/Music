package com.example.dangn.myapplication.View.Main;

import android.graphics.Bitmap;

public interface IMainView {
    public void Init(int seekbarMax, Bitmap bitmap,String title,String artist);
    public void updateView(int progress);
    public void text(String text);
    public void updateButton(int resPlay,int resLoop);
}
