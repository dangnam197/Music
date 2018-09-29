package com.example.dangn.music.Model;

public class TimeModel {
    private int hours;
    private int minute;
    private int seconds;

    public TimeModel(int milliseconds) {
        int time=milliseconds/1000;
        this.hours = time/3600;
        this.minute =(time%3600)/60;
        this.seconds =(time%3600)%60;
    }
    public TimeModel(){
        this.hours=0;
        this.minute=0;
        this.seconds=0;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    public String timeToString(int milliseconds){
        String s="";
        int time=milliseconds/1000;
        this.hours = time/3600;
        this.minute =(time%3600)/60;
        this.seconds =(time%3600)%60;
        if(this.hours==0){
            if(this.seconds<10){
                s=s+this.minute +":0"+this.seconds;
            }
            else {
                s=s+this.minute +":"+this.seconds;
            }
        }
        else {
            s=s+hours+":";
            if (this.minute<10){
                if(this.seconds<10){
                    s=s+"0"+this.minute +":0"+this.seconds;
                }
                else {
                    s=s+this.minute +":"+this.seconds;
                }
            }
            else {
                if(this.seconds<10){
                    s=s+this.minute +":0"+this.seconds;
                }
                else {
                    s=s+this.minute +":"+this.seconds;
                }
            }
        }
        return s;
    }
    public String timeToString(){
        String s="";
        if(this.hours==0){
            if(this.seconds<10){
                s=s+this.minute +":0"+this.seconds;
            }
            else {
                s=s+this.minute +":"+this.seconds;
            }
        }
        else {
            s=s+hours+":";
            if (this.minute<10){
                if(this.seconds<10){
                    s=s+"0"+this.minute +":0"+this.seconds;
                }
                else {
                    s=s+this.minute +":"+this.seconds;
                }
            }
            else {
                if(this.seconds<10){
                    s=s+this.minute +":0"+this.seconds;
                }
                else {
                    s=s+this.minute +":"+this.seconds;
                }
            }
        }
        return s;
    }
}
