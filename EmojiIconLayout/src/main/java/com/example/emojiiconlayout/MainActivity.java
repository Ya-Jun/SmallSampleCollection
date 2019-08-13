package com.example.emojiiconlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.emoji.DefaultGifEmoji;
import com.example.emoji.DeleteEmoji;
import com.example.emoji.Emoji;
import com.example.emoji.EmojiManager;
import com.example.emoji.EmojiconGroupEntity;
import com.example.emoji.EmojiconMenu;
import com.example.emoji.EmojiconMenuBase;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EmojiconMenu emojiInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emojiInputView = findViewById(R.id.emojiInputView);
        emojiInputView.setEmojiconMenuListener(new EmojiconMenuBase.EmojiconMenuListener() {
            @Override
            public void onExpressionClicked(Emoji emoji) {
                if (emoji instanceof DeleteEmoji) {
                    Toast.makeText(MainActivity.this, "删除", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, emoji.getEmojiText(), Toast.LENGTH_LONG).show();
                }
            }
        });
        emojiInputView.setSendBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "发送", Toast.LENGTH_LONG).show();
            }
        });
        emojiInputView.setTabBarVisibility(View.VISIBLE);//显示表情TAB

        //添加静态表情
        emojiInputView.addEmojiconGroup(new EmojiconGroupEntity(R.drawable.m3,
                Arrays.asList(EmojiManager.getInstance().getCommonEmojiManager())));

        //添加动态表情
        EmojiManager.getInstance().getDefaultEmojiData(new EmojiManager.OnUnzipSuccessListener() {
            @Override
            public void onUnzipSuccess(Emoji[] defaultGifEmojis) {
                emojiInputView.addEmojiconGroup(new EmojiconGroupEntity(R.drawable.common_emoj_smile,
                        Arrays.asList(defaultGifEmojis)));
            }
        });
    }

    public void deleteGroup(View view) {
        emojiInputView.removeEmojiconGroup(3);
    }

    public void addGroup(View view) {
        // 动态添加表情组
        emojiInputView.addEmojiconGroup(new EmojiconGroupEntity(R.drawable.m8,
                Arrays.asList(EmojiManager.getInstance().getCommonEmojiManager())));
    }
}
