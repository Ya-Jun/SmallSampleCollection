package com.wyj.myanimationdemo;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * 帧动画
 */

public class DrawableAnimation extends AppCompatActivity {
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_animation);

        imageView1 = (ImageView) findViewById(R.id.imageView1);

        imageView1.setBackgroundResource(R.drawable.explode_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView1.getBackground();
        animationDrawable.start();//动画开始。

//        animationDrawable.setOneShot(true);//设置动画是否只播放一次。
//        animationDrawable.stop();//动画停止。
//        animationDrawable.isRunning();//返回动画是否正在运行。
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.explode_1), 80);//通过代码添加帧

    }

}
