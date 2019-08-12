package com.wyj.myapplication;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在加载图片之前，你必须初始化Fresco类
        Fresco.initialize(this);

        setContentView(R.layout.activity_main);


        Uri uri = Uri.parse("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3103457797,1011040581&fm=26&gp=0.jpg");
        SimpleDraweeView draweeView1 = (SimpleDraweeView) findViewById(R.id.imageview1);
        SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.imageview2);
        SimpleDraweeView draweeView3 = (SimpleDraweeView) findViewById(R.id.imageview3);
        SimpleDraweeView draweeView4 = (SimpleDraweeView) findViewById(R.id.imageview4);
        SimpleDraweeView draweeView5 = (SimpleDraweeView) findViewById(R.id.imageview5);
        SimpleDraweeView draweeView6 = (SimpleDraweeView) findViewById(R.id.imageview6);
        draweeView1.setImageURI(uri);
        draweeView2.setImageURI(uri);
        draweeView3.setImageURI(uri);
        draweeView4.setImageURI(uri);
        draweeView5.setImageURI(uri);
        draweeView6.setImageURI("");

        SimpleDraweeView draweeView7 = (SimpleDraweeView) findViewById(R.id.imageview7);
        Uri uri2 = Uri.parse("");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .setAutoPlayAnimations(true)
                .build();
        draweeView7.setController(controller);
    }
}
