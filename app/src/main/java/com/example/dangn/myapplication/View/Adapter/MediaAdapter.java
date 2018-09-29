package com.example.dangn.myapplication.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dangn.myapplication.Model.AudioModel;
import com.example.dangn.myapplication.R;


import java.util.ArrayList;

public class MediaAdapter extends ArrayAdapter<AudioModel>{
    private Context context;
    private ArrayList<AudioModel> list;
    private int resource;

    public MediaAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AudioModel> list) {
        super(context,resource,list);
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHoder viewHoder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_media,parent,false);
            viewHoder = new ViewHoder();
            viewHoder.name = (TextView) convertView.findViewById(R.id.media_name);
            viewHoder.artist = (TextView) convertView.findViewById(R.id.media_artist) ;
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        String name = list.get(position).getTrack_name();

         String artist =  list.get(position).getArtist();

        viewHoder.name.setText(name);
        viewHoder.artist.setText(artist);
        return convertView;
    }

    public class ViewHoder{
        TextView name,artist;
    }
}
