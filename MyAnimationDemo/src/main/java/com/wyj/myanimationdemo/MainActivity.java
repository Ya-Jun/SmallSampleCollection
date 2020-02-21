package com.wyj.myanimationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonView;
    private Button buttonDrawable;
    private Button buttonProperty;
    private Button buttonInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        buttonView = (Button) findViewById(R.id.button_view);
        buttonDrawable = (Button) findViewById(R.id.button_drawable);
        buttonProperty = (Button) findViewById(R.id.button_property);
        buttonInterpolator = (Button) findViewById(R.id.button_interpolator);

        buttonView.setOnClickListener(this);
        buttonDrawable.setOnClickListener(this);
        buttonProperty.setOnClickListener(this);
        buttonInterpolator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonView) {
            Intent intent = new Intent(MainActivity.this, ViewAnimation.class);
            MainActivity.this.startActivity(intent);
        } else if (v == buttonDrawable) {
            Intent intent = new Intent(MainActivity.this, DrawableAnimation.class);
            MainActivity.this.startActivity(intent);
        } else if (v == buttonProperty) {

        } else if (v == buttonInterpolator) {
            Intent intent = new Intent(MainActivity.this, InterpolatorCurve.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
