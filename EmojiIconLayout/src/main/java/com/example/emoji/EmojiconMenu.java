package com.example.emoji;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.emojiiconlayout.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 表情图片控件
 */
public class EmojiconMenu extends EmojiconMenuBase {

    private int emojiconColumns;
    private final static int DEFAULT_COLUMNS = 7;
    private EmojiconScrollTabBar tabBar;
    private EmojiconIndicatorView indicatorView;
    private EmojiconPagerView pagerView;

    private List<EmojiconGroupEntity> emojiconGroupList = new ArrayList<EmojiconGroupEntity>();


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public EmojiconMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EmojiconMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmojiconMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.common_emoj_widget_emojicon, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EmojiconMenu);
        emojiconColumns = ta.getInt(R.styleable.EmojiconMenu_emojiconColumns, DEFAULT_COLUMNS);
        ta.recycle();

        pagerView = findViewById(R.id.pager_view);
        indicatorView = findViewById(R.id.indicator_view);
        tabBar = findViewById(R.id.tab_bar);

//        emojiconGroupList = new ArrayList<>();
//        pagerView.setPagerViewListener(new EmojiconPagerViewListener());
//        pagerView.init(emojiconGroupList, emojiconColumns);
//
//        tabBar.setTabBarItemClickListener(new EmojiconScrollTabBar.EaseScrollTabBarItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                pagerView.setGroupPostion(position);
//            }
//        });

        // 添加默认表情
        EmojiManager.getInstance().getDefaultEmojiData(new EmojiManager.OnUnzipSuccessListener() {
            @Override
            public void onUnzipSuccess(DefaultGifEmoji[] defaultGifEmojis) {
                initDefault(defaultGifEmojis);
            }
        });

    }


    public void setSendBtnListener(View.OnClickListener sendBtnListener) {
        tabBar.setSendBtnListener(sendBtnListener);
    }

    private void initDefault(DefaultGifEmoji[] defaultGifEmojis) {
        emojiconGroupList = new ArrayList<>();
        emojiconGroupList.add(new EmojiconGroupEntity(R.drawable.common_emoj_smile,
                Arrays.asList(defaultGifEmojis)));
        for (EmojiconGroupEntity groupEntity : emojiconGroupList) {
            tabBar.addTab(groupEntity.getIcon());
        }

        pagerView.setPagerViewListener(new EmojiconPagerViewListener());
        pagerView.init(emojiconGroupList, emojiconColumns);

        tabBar.setTabBarItemClickListener(new EmojiconScrollTabBar.EaseScrollTabBarItemClickListener() {

            @Override
            public void onItemClick(int position) {
                pagerView.setGroupPostion(position);
            }
        });

    }


    /**
     * 添加表情组
     */
    public void addEmojiconGroup(EmojiconGroupEntity groupEntity) {
        emojiconGroupList.add(groupEntity);
        pagerView.addEmojiconGroup(groupEntity, true);
        tabBar.addTab(groupEntity.getIcon());
    }

    /**
     * 添加一系列表情组
     */
    public void addEmojiconGroup(List<EmojiconGroupEntity> groupEntitieList) {
        for (int i = 0; i < groupEntitieList.size(); i++) {
            EmojiconGroupEntity groupEntity = groupEntitieList.get(i);
            emojiconGroupList.add(groupEntity);
            pagerView.addEmojiconGroup(groupEntity, i == groupEntitieList.size() - 1 ? true : false);
            tabBar.addTab(groupEntity.getIcon());
        }

    }

    /**
     * 移除表情组
     */
    public void removeEmojiconGroup(int position) {
        emojiconGroupList.remove(position);
        pagerView.removeEmojiconGroup(position);
        tabBar.removeTab(position);
    }

    public void setTabBarVisibility(int visibility) {
        tabBar.setVisibility(visibility);
    }


    private class EmojiconPagerViewListener implements EmojiconPagerView.EaseEmojiconPagerViewListener {

        @Override
        public void onPagerViewInited(int groupMaxPageSize, int firstGroupPageSize) {
            indicatorView.init(groupMaxPageSize);
            indicatorView.updateIndicator(firstGroupPageSize);
            tabBar.selectedTo(0);
        }

        @Override
        public void onGroupPositionChanged(int groupPosition, int pagerSizeOfGroup) {
            indicatorView.updateIndicator(pagerSizeOfGroup);
            tabBar.selectedTo(groupPosition);
        }

        @Override
        public void onGroupInnerPagePostionChanged(int oldPosition, int newPosition) {
            indicatorView.selectTo(oldPosition, newPosition);
        }

        @Override
        public void onGroupPagePostionChangedTo(int position) {
            indicatorView.selectTo(position);
        }

        @Override
        public void onGroupMaxPageSizeChanged(int maxCount) {
            indicatorView.updateIndicator(maxCount);
        }

        @Override
        public void onExpressionClicked(Emoji emoji) {
            if (listener != null) {
                listener.onExpressionClicked(emoji);
            }
        }

    }

}
