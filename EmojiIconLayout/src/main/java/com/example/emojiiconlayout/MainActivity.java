package com.example.emojiiconlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        EmojiManager.getInstance().init(MainActivity.this);

        emojiInputView = findViewById(R.id.emojiInputView);
        emojiInputView.setEmojiconMenuListener(new EmojiconMenuBase.EmojiconMenuListener() {
            @Override
            public void onExpressionClicked(Emoji emoji) {
                if (emoji instanceof DeleteEmoji) {
                    Toast.makeText(MainActivity.this, "删除", Toast.LENGTH_LONG).show();
                } else if (emoji instanceof DefaultGifEmoji) {
                    Toast.makeText(MainActivity.this, emoji.getEmojiText(), Toast.LENGTH_LONG).show();
                }
            }
        });
        emojiInputView.setTabBarVisibility(View.VISIBLE);//显示表情TAB

//        EmojiManager.getInstance().getDefaultEmojiData(new EmojiManager.OnUnzipSuccessListener() {
//            @Override
//            public void onUnzipSuccess(DefaultGifEmoji[] defaultGifEmojis) {
//                emojiInputView.addEmojiconGroup(new EmojiconGroupEntity(R.drawable.common_emoj_smile,
//                        Arrays.asList(defaultGifEmojis)));
////                emojiInputView.addEmojiconGroup(new EmojiconGroupEntity(R.drawable.common_emoj_smile,
////                        Arrays.asList(defaultGifEmojis)));
//            }
//        });
    }
}
