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
import java.util.List;


/**
 * 表情图片控件
 */
public class EmojiconMenu extends EmojiconMenuBase {

    private int emojiconColumns;
    private final static int DEFAULT_COLUMNS = 7;
    private int bigEmojiconColumns;
    private final static int DEFAULT_BIG_COLUMNS = 4;
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
        bigEmojiconColumns = ta.getInt(R.styleable.EmojiconMenu_bigEmojiconColumns, DEFAULT_BIG_COLUMNS);
        ta.recycle();

        pagerView = findViewById(R.id.pager_view);
        indicatorView = findViewById(R.id.indicator_view);
        tabBar = findViewById(R.id.tab_bar);

        emojiconGroupList = new ArrayList<>();
        pagerView.setPagerViewListener(new EmojiconPagerViewListener());
        pagerView.init(emojiconGroupList, emojiconColumns, bigEmojiconColumns);

        tabBar.setTabBarItemClickListener(new EmojiconScrollTabBar.EaseScrollTabBarItemClickListener() {
            @Override
            public void onItemClick(int position) {
                pagerView.setGroupPostion(position);
            }
        });

    }


    public void setSendBtnListener(View.OnClickListener sendBtnListener) {
        tabBar.setSendBtnListener(sendBtnListener);
    }

    /**
     * 添加表情组
     */
    public void addEmojiconGroup(EmojiconGroupEntity groupEntity) {
        emojiconGroupList.add(groupEntity);
        tabBar.addTab(groupEntity.getIcon());
        pagerView.addEmojiconGroup(groupEntity, true);
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
        public void addEmojiconGroup(int pagerSizeOfGroug) {
            // 如果是第一次添加执行下边操作
            if (emojiconGroupList.size() == 1) {
                tabBar.selectedTo(0);
                indicatorView.updateIndicator(pagerSizeOfGroug);
                indicatorView.selectTo(0);
            }
        }

        @Override
        public void onExpressionClicked(Emoji emoji) {
            if (listener != null) {
                listener.onExpressionClicked(emoji);
            }
        }

    }

}
