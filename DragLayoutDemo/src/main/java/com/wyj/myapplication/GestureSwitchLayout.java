package com.wyj.myapplication;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class GestureSwitchLayout extends FrameLayout {
    private static final String TAG = "DragLayout";
    private ViewDragHelper dragHelper;
    private View mDragView, contentView_bottom, contentView_top;
    private OnViewDragStateChangedListener onViewDragStateChangedListener;

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
        mDragView = findViewById(R.id.rl);
        contentView_bottom = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        contentView_top = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        contentView_bottom.setId(R.id.imageView2);
        contentView_top.setId(R.id.imageView3);
        addView(contentView_bottom);
        addView(contentView_top);
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

            System.out.println("---------------------------------移动中top" + contentView_top.getTop());
            System.out.println("---------------------------------移动中" + mDragView.getTop());
            System.out.println("---------------------------------移动中bottom" + contentView_bottom.getTop());
            System.out.println("---------------------------------");

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (yvel > 0) {
                smoothToBottom();
            } else if (yvel < 0) {
                smoothToTop();
            } else {
                smoothToRestore();
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_DRAGGING) {

            } else if (state == ViewDragHelper.STATE_IDLE) {
                System.out.println("---------------------------------还原" + contentView_top.getTop());
                System.out.println("---------------------------------还原" + mDragView.getTop());
                System.out.println("---------------------------------还原" + contentView_bottom.getTop());

                initLayout();

//                contentView_top.offsetTopAndBottom(-mDragView.getTop());
//                contentView_bottom.offsetTopAndBottom(-mDragView.getTop());
//                mDragView.offsetTopAndBottom(-mDragView.getTop());

                System.out.println("---------------------------------还原完成上" + contentView_top.getTop());
                System.out.println("---------------------------------还原完成中" + mDragView.getTop());
                System.out.println("---------------------------------还原完成下" + contentView_bottom.getTop());

            }
            if (onViewDragStateChangedListener != null) {
                onViewDragStateChangedListener.OnViewDragStateChanged(state);
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
//        super.onLayout(changed, l, t, r, b);
        if (changed) {
            System.out.println("---------------------------------初始化");
            initLayout();
        } else {
            contentView_top.layout(0, contentView_top.getTop(), getWidth(), contentView_top.getTop() + getHeight());
            mDragView.layout(0, mDragView.getTop(), getWidth(), mDragView.getTop() + getHeight());
            contentView_bottom.layout(0, contentView_bottom.getTop(), getWidth(), contentView_bottom.getTop() + getHeight());
        }
        System.out.println("---------------------------------onLayout上" + contentView_top.getTop());
        System.out.println("---------------------------------onLayout中" + mDragView.getTop());
        System.out.println("---------------------------------onLayout下" + contentView_bottom.getTop());
    }

    private void initLayout() {
        mDragView.layout(0, 0, getWidth(), getHeight());
        contentView_bottom.layout(0, getHeight(), getWidth(), getHeight() * 2);
        contentView_top.layout(0, -getHeight(), getWidth(), 0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    private void smoothToRestore() {
        System.out.println("---------------------------------smoothToRestore");
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void smoothToTop() {
        System.out.println("---------------------------------smoothToTop");
        if (dragHelper.smoothSlideViewTo(mDragView, getPaddingLeft(), -getHeight())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void smoothToBottom() {
        System.out.println("---------------------------------smoothToBottom");
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

