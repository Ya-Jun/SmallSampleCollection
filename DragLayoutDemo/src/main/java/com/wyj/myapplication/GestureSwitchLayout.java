package com.wyj.myapplication;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class GestureSwitchLayout extends FrameLayout {
    private static final String TAG = "DragLayout";
    private ViewDragHelper dragHelper;
    private View contentView_bottom, contentView_top, contentView_loading;
    private ViewGroup mDragView;
    private OnViewDragStateChangedListener onViewDragStateChangedListener;
    public static final int STATE_IDLE_TOP = 1;
    public static final int STATE_IDLE_BOTTOM = 2;

    private boolean isScreenSizeChange = false;
    private boolean isChanged = true;//此变量重要不可随意改动'
    private boolean isStateSettling = false;
    private int initHeight;

    public GestureSwitchLayout(Context context) {
        super(context);
        init();
    }

    public GestureSwitchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureSwitchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = (ViewGroup) findViewById(R.id.rl);
        contentView_top = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        contentView_loading = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        contentView_bottom = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        contentView_top.setId(R.id.imageView1);
        contentView_loading.setId(R.id.imageView2);
        contentView_bottom.setId(R.id.imageView3);
        addView(contentView_bottom);
        addView(contentView_top);
        mDragView.addView(contentView_loading);
        contentView_loading.setVisibility(GONE);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//            contentView_top.layout(0, -(contentView_top.getHeight() - top), getWidth(), top);
//            contentView_bottom.layout(0, top + mDragView.getHeight(), getWidth(), top + mDragView.getHeight() + contentView_bottom.getHeight());
            contentView_top.offsetTopAndBottom(dy);
            contentView_bottom.offsetTopAndBottom(dy);
            requestLayout();//低版本系统、不能及时刷新

            if (top == changedView.getHeight()) {//向下滑动停止
                if (onViewDragStateChangedListener != null) {
                    onViewDragStateChangedListener.OnViewDragStateChanged(STATE_IDLE_BOTTOM);
                }
            } else if (top == -changedView.getHeight()) {//向上滑动停止
                if (onViewDragStateChangedListener != null) {
                    onViewDragStateChangedListener.OnViewDragStateChanged(STATE_IDLE_TOP);
                }
            }
        }

        // 如果通过滑动速度决定是否切换使用该方法，如果通过滑动位置决定是否切换使用onViewDragStateChanged
//        @Override
//        public void onViewReleased(View releasedChild, float xvel, float yvel) {
//            super.onViewReleased(releasedChild, xvel, yvel);
//            if (yvel > 800) {
//                smoothToBottom();
//            } else if (yvel < -800) {
//                smoothToTop();
//            } else {
//                smoothToRestore();
//            }
//        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_IDLE) {
                isStateSettling = false;
                if (mDragView.getTop() > 350 && mDragView.getTop() < getHeight()) {
                    smoothToBottom();
                } else if (mDragView.getTop() < -250 && mDragView.getTop() > -getHeight()) {
                    smoothToTop();
                } else if (mDragView.getTop() < 350 && mDragView.getTop() > -250) {
                    if (mDragView.getTop() != 0) {
                        smoothToRestore();
                    }
                } else {
//                    toRestore();
                }
            } else if (state == ViewDragHelper.STATE_SETTLING) {
                isStateSettling = true;
            } else {
                isStateSettling = false;
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight();
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isChanged) {
            initLayout();
            isChanged = false;
        } else {
            if (changed) {
                initLayout();
            } else {
                contentView_top.layout(0, contentView_top.getTop(), getWidth(), contentView_top.getTop() + getHeight());
                mDragView.layout(0, mDragView.getTop(), getWidth(), mDragView.getTop() + getHeight());
                contentView_bottom.layout(0, contentView_bottom.getTop(), getWidth(), contentView_bottom.getTop() + getHeight());
            }
        }

        int currentHeight = getHeight();
        if (initHeight < currentHeight) {
            initHeight = currentHeight;
        }
        if (currentHeight < initHeight - 200) {//由于带虚拟返回键的手机，可以动态隐藏显示控制栏，导致getHeight高度有小于200的变化
            isScreenSizeChange = true;
        } else {
            isScreenSizeChange = false;
        }
    }

    private void initLayout() {
        mDragView.layout(0, 0, getWidth(), getHeight());
        contentView_bottom.layout(0, getHeight(), getWidth(), getHeight() * 2);
        contentView_top.layout(0, -getHeight(), getWidth(), 0);
    }

    public void toRestore() {
        isChanged = true;
        requestLayout();
        contentView_loading.setVisibility(View.GONE);
    }

    private float downX;
    private float downY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isScreenSizeChange || isStateSettling) {
            dragHelper.cancel();
            return super.onInterceptTouchEvent(event);
        }
        // 只在垂直方向上处理
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
            downY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float moveX = event.getX();
            float moveY = event.getY();
            float adx = Math.abs(moveX - downX);
            float ady = Math.abs(moveY - downY);
            int slop = dragHelper.getTouchSlop();
            if (ady > slop && adx > ady) {
                dragHelper.cancel();
                return super.onInterceptTouchEvent(event);
            }
        }
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isScreenSizeChange && !isStateSettling) {
            dragHelper.processTouchEvent(event);
        }
        return true;
    }

    private void smoothToRestore() {
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void smoothToTop() {
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), -getHeight())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void smoothToBottom() {
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), getHeight())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setOnViewDragStateChangedListener(OnViewDragStateChangedListener onViewDragStateChangedListener) {
        this.onViewDragStateChangedListener = onViewDragStateChangedListener;
    }

    public interface OnViewDragStateChangedListener {
        void OnViewDragStateChanged(int state);
    }

}

