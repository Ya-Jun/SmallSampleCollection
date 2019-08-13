package com.example.emojiiconlayout;

import android.app.Application;

import com.example.emoji.EmojiManager;

public class MainApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    EmojiManager.getInstance().init(this);
  }
}
