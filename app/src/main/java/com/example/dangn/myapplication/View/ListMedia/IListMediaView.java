package com.example.dangn.myapplication.View.ListMedia;
import android.graphics.Bitmap;

import com.example.dangn.myapplication.Model.AudioModel;

import java.util.ArrayList;

public interface IListMediaView {

    public void setListView(ArrayList<AudioModel> list);
    public void setBackground(Bitmap background);
}
