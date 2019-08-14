package com.example.emoji;

import java.util.List;

public class EmojiconGroupEntity {

    /**
     * 表情数据
     */
    private List<Emoji> defaultGifEmojiList;
    /**
     * 图片
     */
    private int icon;
    /**
     * 组名
     */
    private String name;

    private boolean isBigEmoji = false;

    public EmojiconGroupEntity(int icon, List<Emoji> defaultGifEmojiList) {
        this.icon = icon;
        this.defaultGifEmojiList = defaultGifEmojiList;
    }

    public EmojiconGroupEntity(int icon, List<Emoji> defaultGifEmojiList, boolean isBigEmoji) {
        this.icon = icon;
        this.defaultGifEmojiList = defaultGifEmojiList;
        this.isBigEmoji = isBigEmoji;
    }

    public List<Emoji> getDefaultGifEmojiList() {
        return defaultGifEmojiList;
    }

    public void setDefaultGifEmojiList(List<Emoji> defaultGifEmojiList) {
        this.defaultGifEmojiList = defaultGifEmojiList;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBigEmoji() {
        return isBigEmoji;
    }

    public void setBigEmoji(boolean bigEmoji) {
        isBigEmoji = bigEmoji;
    }
}
