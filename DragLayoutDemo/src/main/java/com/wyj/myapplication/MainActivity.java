package com.wyj.myapplication;

import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private GestureSwitchLayout gestureSwitchLayout;
    private ImageView iv_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureSwitchLayout = (GestureSwitchLayout) findViewById(R.id.gsl);
        ImageView view1 = (ImageView) findViewById(R.id.imageView2);
        ImageView view2 = (ImageView) findViewById(R.id.imageView3);
        iv_loading = (ImageView) findViewById(R.id.iv);

        view1.setBackgroundResource(R.drawable.ic_launcher_background);
        view2.setBackgroundResource(R.drawable.ic_launcher_background);
        iv_loading.setBackgroundResource(R.drawable.ic_launcher_background);

        gestureSwitchLayout.setOnViewDragStateChangedListener(new GestureSwitchLayout.OnViewDragStateChangedListener() {
            @Override
            public void OnViewDragStateChanged(int state) {
                if (state == ViewDragHelper.STATE_IDLE) {
//                    iv_loading.setVisibility(View.VISIBLE);
//                    iv_loading.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            iv_loading.setVisibility(View.GONE);
//                        }
//                    }, 3000);
                }
            }
        });

    }
}
