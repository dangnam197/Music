package com.example.dangn.music.Model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.dangn.myapplication.Model.AudioModel;

import java.util.ArrayList;

public class ListAudio {
    Context context;
    final String track_id = MediaStore.Audio.Media._ID;
    final String track_no = MediaStore.Audio.Media.TRACK;
    final String track_name = MediaStore.Audio.Media.TITLE;
    final String artist = MediaStore.Audio.Media.ARTIST;
    final String duration = MediaStore.Audio.Media.DURATION;
    final String album = MediaStore.Audio.Media.ALBUM;
    final String composer = MediaStore.Audio.Media.COMPOSER;
    final String year = MediaStore.Audio.Media.YEAR;
    final String path = MediaStore.Audio.Media.DATA;
    final String date_added = MediaStore.Audio.Media.DATE_ADDED;
    public ListAudio(Context context) {
        this.context = context;
    }
    public ArrayList<AudioModel> getListAudio(){
        ArrayList <AudioModel> list = new ArrayList<>();
        ContentResolver contentResolver= context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        int []index = {
                cursor.getColumnIndex(track_id),
                cursor.getColumnIndex(track_no),
                cursor.getColumnIndex(track_name),
                cursor.getColumnIndex(artist),
                cursor.getColumnIndex(duration),
                cursor.getColumnIndex(album),
                cursor.getColumnIndex(composer),
                cursor.getColumnIndex(year),
                cursor.getColumnIndex(path),
                cursor.getColumnIndex(date_added)
        };

        while (cursor.moveToNext()){
            AudioModel audioModel = new AudioModel();
            audioModel.setTrack_id(cursor.getString(index[0]));
            audioModel.setTrack_no(cursor.getString(index[1]));
            audioModel.setTrack_name(cursor.getString(index[2]));
            audioModel.setArtist(cursor.getString(index[3]));
            audioModel.setDuration(cursor.getString(index[4]));
            audioModel.setAlbum(cursor.getString(index[5]));
            audioModel.setComposer(cursor.getString(index[6]));
            audioModel.setYear(cursor.getString(index[7]));
            audioModel.setPath(cursor.getString(index[8]));
            audioModel.setDate_added(cursor.getString(index[9]));
            list.add(audioModel);
        }

        return list;
    }
}
