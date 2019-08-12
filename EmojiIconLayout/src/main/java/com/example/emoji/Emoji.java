package com.example.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface Emoji {

    Drawable getDrawable(Context context);

    CharSequence getEmojiText();

    int getResId();

    String getRes();

    int getResType();

    boolean isDeleteIcon();

    int getDefaultResId();

    Object getCacheKey();

}
