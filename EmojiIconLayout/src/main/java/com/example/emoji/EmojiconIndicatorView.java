package com.example.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.emojiiconlayout.R;
import com.example.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class EmojiconIndicatorView extends LinearLayout {

    private Context context;
    private Bitmap selectedBitmap;
    private Bitmap unselectedBitmap;

    private List<ImageView> dotViews;

    private int dotHeight = 12;

    public EmojiconIndicatorView(Context context, AttributeSet attrs, int defStyle) {
        this(context, null);
    }

    public EmojiconIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmojiconIndicatorView(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        dotHeight = Utils.dp2px(context, dotHeight);
        selectedBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.common_emoj_dot_selected);
        unselectedBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.common_emoj_dot_unselected);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void updateIndicator(int count) {
        if (dotViews == null) {
            dotViews = new ArrayList<ImageView>();
        }
        for (int i = 0; i < dotViews.size(); i++) {
            if (i >= count) {
                dotViews.get(i).setVisibility(GONE);
                ((View) dotViews.get(i).getParent()).setVisibility(GONE);
            } else {
                dotViews.get(i).setVisibility(VISIBLE);
                ((View) dotViews.get(i).getParent()).setVisibility(VISIBLE);
            }
        }
        if (count > dotViews.size()) {
            int diff = count - dotViews.size();
            for (int i = 0; i < diff; i++) {
                RelativeLayout rl = new RelativeLayout(context);
                LayoutParams params = new LayoutParams(dotHeight, dotHeight);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                ImageView imageView = new ImageView(context);
                imageView.setImageBitmap(unselectedBitmap);
                rl.addView(imageView, layoutParams);
                rl.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                this.addView(rl, params);
                dotViews.add(imageView);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (selectedBitmap != null) {
            selectedBitmap.recycle();
        }
        if (unselectedBitmap != null) {
            unselectedBitmap.recycle();
        }
    }

    public void selectTo(int position) {
        for (ImageView iv : dotViews) {
            iv.setImageBitmap(unselectedBitmap);
        }
        dotViews.get(position).setImageBitmap(selectedBitmap);
    }


    public void selectTo(int startPosition, int targetPostion) {
        ImageView startView = dotViews.get(startPosition);
        ImageView targetView = dotViews.get(targetPostion);
        startView.setImageBitmap(unselectedBitmap);
        targetView.setImageBitmap(selectedBitmap);
    }

}   
