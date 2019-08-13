package com.example.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.emojiiconlayout.R;

import java.io.File;


public class CommonEmoji implements Emoji {

    private String emojiText;
    private int emojiconId;

    public CommonEmoji(int emojiconId, String emojiText) {
        this.emojiconId = emojiconId;
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
        return emojiconId;
    }

    @Override
    public String getRes() {
        return "";
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
        return R.drawable.common_emoj_smile_default;
    }

}
