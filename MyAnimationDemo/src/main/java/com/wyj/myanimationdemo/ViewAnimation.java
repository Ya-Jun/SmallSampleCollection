package com.wyj.myanimationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class ViewAnimation extends AppCompatActivity {
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;

    private RotateAnimation rotateAnimation;
    private ScaleAnimation scaleAnimation;
    private AlphaAnimation alphaAnimation;
    private TranslateAnimation translateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        createRotateAnimation();
        createScaleAnimation();
        createAlphaAnimation();
        createTranslateAnimation();
        createAnimationSet();
    }

    /**
     * 旋转动画
     */
    private void createRotateAnimation() {
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500); // 动画持续时间
        rotateAnimation.setStartOffset(2000); // 延时开始

        rotateAnimation.setFillAfter(true); // 动画结束后是否保持动画最后的状态
        rotateAnimation.setFillBefore(true);// 动画结束后是否还原到开始动画前的状态
        rotateAnimation.setFillEnabled(true);// 与setFillBefore效果相同

        rotateAnimation.setRepeatCount(-1);// 重复次数（“- 1”为无限重复。“1”为动画在动画初始运行后重复一次，因此动画总共播放两次。默认值为“0”，为没有重复。）
        rotateAnimation.setRepeatMode(Animation.REVERSE);//重复方式（reverse|restart）

        rotateAnimation.setInterpolator(new LinearInterpolator());//设置插值器

//        // XML实现
//        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(ViewAnimation.this, R.anim.view_anim_rotate);

        imageView1.startAnimation(rotateAnimation);
    }

    /**
     * 缩放动画
     */
    private void createScaleAnimation() {
        scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(-1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

//        // XML实现
//        scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(ViewAnimation.this, R.anim.view_anim_scale);

        imageView2.startAnimation(scaleAnimation);
    }

    /**
     * 透明度动画
     */
    private void createAlphaAnimation() {
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(-1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

//        // XML实现
//        alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(ViewAnimation.this, R.anim.view_anim_alpha);

        imageView3.startAnimation(alphaAnimation);
    }


    /**
     * 位移动画
     */
    private void createTranslateAnimation() {
        translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -2f,
                Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setRepeatMode(Animation.REVERSE);

//        // XML实现
//        translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(ViewAnimation.this, R.anim.view_anim_translate);

        imageView4.startAnimation(translateAnimation);
    }

    /**
     * 组合动画
     */
    private void createAnimationSet() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(2000);
        animationSet.setRepeatCount(-1);
        animationSet.setRepeatMode(Animation.REVERSE);

//        // XML实现
//        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(ViewAnimation.this, R.anim.view_anim_set);

        imageView5.startAnimation(animationSet);
    }
}
