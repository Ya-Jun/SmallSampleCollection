/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.emojiiconlayout.R;
import com.example.util.FileUtil;
import com.example.util.PreferenceUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class EmojiManager {

    private static final String TAG = "EmojiManager";
    private static final int UNZIP_SUCCESS = 11111;
    private final Map<Pattern, Emoji> emoticons = new HashMap<>();
    private HashMap<Object, Drawable> drawableCacheMap = new HashMap<>();

    private volatile static EmojiManager instance;
    private static List<File> emojiGifs = new ArrayList<>();
    private Emoji[] defaultGifEmojis;
    private boolean defaultEmojiInited = false;
    private Handler handler;
    private List<OnUnzipSuccessListener> onUnzipSuccessListeners = new ArrayList<>();

    private static String[] emojiList = new String[]{
            "[微笑]", "[撇嘴]", "[色]", "[发呆]", "[得意]", "[流泪]", "[害羞]",
            "[闭嘴]", "[睡]", "[大哭]", "[尴尬]", "[发怒]", "[调皮]", "[龇牙]",
            "[惊讶]", "[难过]", "[酷]", "[冷汗]", "[抓狂]", "[吐]",

            "[偷笑]", "[可爱]", "[白眼]", "[傲慢]", "[饥饿]", "[困]", "[惊恐]",
            "[流汗]", "[憨笑]", "[大兵]", "[奋斗]", "[咒骂]", "[疑问]", "[嘘…]",
            "[晕]", "[折磨]", "[衰]", "[骷髅]", "[敲打]", "[再见]",

            "[擦汗]", "[抠鼻]", "[鼓掌]", "[糗大了]", "[坏笑]", "[左哼哼]", "[右哼哼]",
            "[哈欠]", "[鄙视]", "[委屈]", "[快哭了]", "[阴险]", "[亲亲]", "[吓]",
            "[可怜]", "[菜刀]", "[西瓜]", "[啤酒]", "[篮球]", "[乒乓球]",

            "[咖啡]", "[饭]", "[猪头]", "[玫瑰]", "[凋谢]", "[示爱]", "[爱心]",
            "[心碎]", "[蛋糕]", "[闪电]", "[炸弹]", "[刀]", "[足球]", "[瓢虫]",
            "[便便]", "[月亮]", "[太阳]", "[礼物]", "[拥抱]", "[强]",

            "[弱]", "[握手]", "[胜利]", "[抱拳]", "[勾引]", "[拳头]", "[差劲]",
            "[爱你]", "[NO]", "[OK]", "[爱情]", "[飞吻]", "[跳跳]", "[发抖]",
            "[怄火]", "[转圈]", "[磕头]", "[回头]", "[跳绳]", "[挥手]"
    };

    private static int[] mEmoticonIds = null;
    private static String[] mEmoticonTexts = {
            "[/0亲亲]", "[/0伤不起]", "[/0伤透了]", "[/0好的]", "[/0委屈]", "[/0媚眼]",
            "[/0红脸]", "[/0尴尬]", "[/0帅哥]", "[/0很好]", "[/0泪]", "[/0美女]",
            "[/0调皮]", "[/0迷茫]", "[/0飞吻]", "[/0鼓掌]", "[/0勾引]", "[/0发怒]",
            "[/0可怜]", "[/0囧]", "[/0坏笑]", "[/0抓狂]", "[/0抱抱]", "[/0抽]",
            "[/0拜托]", "[/0挑逗]"};
    static {
        mEmoticonIds = new int[69];
        mEmoticonIds[0] = R.drawable.m1;
        mEmoticonIds[1] = R.drawable.m2;
        mEmoticonIds[2] = R.drawable.m3;
        mEmoticonIds[3] = R.drawable.m4;
        mEmoticonIds[4] = R.drawable.m5;
        mEmoticonIds[5] = R.drawable.m6;
        mEmoticonIds[6] = R.drawable.m7;
        mEmoticonIds[7] = R.drawable.m8;
        mEmoticonIds[8] = R.drawable.m9;
        mEmoticonIds[9] = R.drawable.m10;
        mEmoticonIds[10] = R.drawable.m11;
        mEmoticonIds[11] = R.drawable.m12;
        mEmoticonIds[12] = R.drawable.m13;
        mEmoticonIds[13] = R.drawable.m14;
        mEmoticonIds[14] = R.drawable.m15;
        mEmoticonIds[15] = R.drawable.m16;
        mEmoticonIds[16] = R.drawable.m17;
        mEmoticonIds[17] = R.drawable.m18;
        mEmoticonIds[18] = R.drawable.m19;
        mEmoticonIds[19] = R.drawable.m20;

        mEmoticonIds[20] = R.drawable.m21;
        mEmoticonIds[21] = R.drawable.m22;
        mEmoticonIds[22] = R.drawable.m23;
        mEmoticonIds[23] = R.drawable.m24;
        mEmoticonIds[24] = R.drawable.m25;
        mEmoticonIds[25] = R.drawable.m26;
    }

    public Emoji[] getCommonEmojiManager(){
        CommonEmoji[] commonEmojis = new CommonEmoji[mEmoticonTexts.length];
        for (int i = 0; i < commonEmojis.length; i++) {
            commonEmojis[i] = new CommonEmoji(mEmoticonIds[i], mEmoticonTexts[i]);
        }
        return commonEmojis;
    }


    private String zipPtath = "/storage/emulated/0/emojizip/emoji.zip";//压缩包路径
    private String unzipPath = "/storage/emulated/0/emojizip/emoji/";//解压路径

    private EmojiManager() {
        handler = new Handler(new Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == UNZIP_SUCCESS) {
                    onUnzipSuccess();
                    return true;
                }
                return false;
            }
        });
    }


    public static EmojiManager getInstance() {
        if (instance == null) {
            synchronized (EmojiManager.class) {
                if (instance == null) {
                    instance = new EmojiManager();
                }
            }
        }
        return instance;
    }

    public void getDefaultEmojiData(OnUnzipSuccessListener unzipSuccessListener) {
        Log.d(TAG, "IS defaultEmojiInited:" + defaultEmojiInited);
        if (defaultEmojiInited) {
            unzipSuccessListener.onUnzipSuccess(defaultGifEmojis);
        } else {
            onUnzipSuccessListeners.add(unzipSuccessListener);
        }
    }

    public void init(final Context context) {
        boolean unziped = PreferenceUtil.getEmojiInitResult(context);
        Log.d(TAG, "IS unziped:" + unziped);
        if (unziped) {
            initDefault(context);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean unzipResult = FileUtil
                            .unzipFile(zipPtath, unzipPath);
                    PreferenceUtil.setEmojiInitResult(context, unzipResult);
                    if (unzipResult) {
                        initDefault(context);
                    }
                }
            }).start();
        }
    }

    private void initDefault(Context context) {
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 7; k++) {
                    if (j == 2 && k == 6) {
                        continue;
                    }
                    File gifFile;
                    gifFile = new File(unzipPath,
                            "e" + i + "/" + k + "_" + j + ".gif");
                    emojiGifs.add(gifFile);
                }
            }
        }
        for (int i = 0; i < emojiList.length; i++) {
            emoticons.put(Pattern.compile(Pattern.quote(emojiList[i])),
                    new DefaultGifEmoji(emojiGifs.get(i), emojiList[i]));
        }
        defaultGifEmojis = new DefaultGifEmoji[emojiList.length];
        for (int i = 0; i < emojiList.length; i++) {
            defaultGifEmojis[i] = new DefaultGifEmoji(emojiGifs.get(i), emojiList[i]);
        }
        defaultEmojiInited = true;
        handler.sendEmptyMessage(UNZIP_SUCCESS);
    }

    public Drawable getDrawableByEmoji(Context context, Emoji emoji) {
        Drawable drawable;
        if (drawableCacheMap.containsKey(emoji.getCacheKey())) {
            drawable = drawableCacheMap
                    .get(emoji.getCacheKey());
        } else {
            drawable = emoji.getDrawable(context);
            drawableCacheMap
                    .put(emoji.getCacheKey(), drawable);
        }
        return drawable;
    }


    private void onUnzipSuccess() {
        Log.i(TAG, "onUnzipSuccess: " + Thread.currentThread().getName());
        for (OnUnzipSuccessListener onUnzipSuccessListener : onUnzipSuccessListeners) {
            onUnzipSuccessListener.onUnzipSuccess(defaultGifEmojis);
        }
        onUnzipSuccessListeners.clear();
    }

    public void displayImage(ImageView imageView, Emoji emoji) {
        if (emoji.getResId() == 0) {
            Glide.with(imageView)
                    .load(emoji.getRes())
                    .apply(new RequestOptions().placeholder(emoji.getDefaultResId()))
                    .into(imageView);
        } else {
            Glide.with(imageView)
                    .load(emoji.getResId())
                    .apply(new RequestOptions().placeholder(emoji.getDefaultResId()))
                    .into(imageView);
        }
    }

    public interface OnUnzipSuccessListener {
        void onUnzipSuccess(Emoji[] defaultGifEmojis);
    }
}
