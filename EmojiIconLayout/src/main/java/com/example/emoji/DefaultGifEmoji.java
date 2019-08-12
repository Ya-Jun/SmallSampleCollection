package com.example.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.emojiiconlayout.R;

import java.io.File;


public class DefaultGifEmoji implements Emoji {

    private String emojiText;
    private File emojiconFile;

    public DefaultGifEmoji(File emojiconFile, String emojiText) {
        this.emojiconFile = emojiconFile;
        this.emojiText = emojiText;
    }

    @Override
    public Drawable getDrawable(Context context) {
        return ContextCompat.getDrawable(context, getDefaultResId());
    }

    public CharSequence getEmojiText() {
        return emojiText;
    }

    @Override
    public int getResId() {
        return 0;
    }

    @Override
    public String getRes() {
        return emojiconFile.getAbsolutePath();
    }

    @Override
    public int getResType() {
        return 0;
    }

    @Override
    public boolean isDeleteIcon() {
        return false;
    }

    @Override
    public int getDefaultResId() {
        return R.drawable.common_emoj_smile_default;
    }

    @Override
    public Object getCacheKey() {
        if (emojiconFile.exists()) {
            return emojiconFile.getAbsolutePath();
        } else {
            return R.drawable.common_emoj_smile_default;
        }
    }


}
