package com.wyj.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private GestureSwitchLayout gestureSwitchLayout;
    private ImageView iv_loading;
    private Runnable mRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureSwitchLayout = (GestureSwitchLayout) findViewById(R.id.gsl);
        ImageView view1 = (ImageView) findViewById(R.id.imageView1);
        ImageView view2 = (ImageView) findViewById(R.id.imageView3);
        iv_loading = (ImageView) findViewById(R.id.imageView2);

        view1.setBackgroundResource(R.drawable.bg_one);
        view2.setBackgroundResource(R.drawable.bg_two);


        gestureSwitchLayout.setOnViewDragStateChangedListener(new GestureSwitchLayout.OnViewDragStateChangedListener() {
            @Override
            public void OnViewDragStateChanged(int state) {
                iv_loading.setVisibility(View.VISIBLE);
                if (state == GestureSwitchLayout.STATE_IDLE_TOP) {
                    iv_loading.setBackgroundResource(R.drawable.bg_two);
                } else if (state == GestureSwitchLayout.STATE_IDLE_BOTTOM) {
                    iv_loading.setBackgroundResource(R.drawable.bg_one);
                }

                // 模拟数据加载完成
                iv_loading.removeCallbacks(mRun);
                iv_loading.postDelayed(mRun, 1500);
            }
        });


        mRun = new Runnable() {
            @Override
            public void run() {
                gestureSwitchLayout.toRestore();
            }
        };

    }
}
