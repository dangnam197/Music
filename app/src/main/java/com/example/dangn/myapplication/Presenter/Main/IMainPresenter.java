package com.example.dangn.myapplication.Presenter.Main;

public interface IMainPresenter {
    public void play();
    public void next();
    public void prev();
    public void setProgress(int progress);
    public void show();
    public void updateStart();
    public void updatePause();
    public void updateButton();
}
