package com.example.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.emojiiconlayout.R;

public class DeleteEmoji implements Emoji {


    public DeleteEmoji() {

    }


    @Override
    public Drawable getDrawable(Context context) {
        return ContextCompat.getDrawable(context, getDefaultResId());
    }

    public CharSequence getEmojiText() {
        return "";
    }

    @Override
    public int getResId() {
        return R.drawable.common_emoj_delete_expression;
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
        return true;
    }

    @Override
    public int getDefaultResId() {
        return R.drawable.common_emoj_delete_expression;
    }

    @Override
    public Object getCacheKey() {
        return R.drawable.common_emoj_delete_expression;
    }


}
