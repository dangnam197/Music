package com.example.dangn.myapplication.Model;

public class AudioModel {

    private String track_id ;
    private String track_no ;
    private String track_name ;
    private String artist ;
    private String duration ;
    private String album ;
    private String composer ;
    private String year ;
    private String path ;
    private String date_added;

    public AudioModel() {
    }

    public AudioModel(String track_id, String track_no, String track_name, String artist, String duration, String album, String composer, String year, String path, String date_added) {
        this.track_id = track_id;
        this.track_no = track_no;
        this.track_name = track_name;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
        this.composer = composer;
        this.year = year;
        this.path = path;
        this.date_added = date_added;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getTrack_no() {
        return track_no;
    }

    public void setTrack_no(String track_no) {
        this.track_no = track_no;
    }

    public String getTrack_name() {
        return track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}