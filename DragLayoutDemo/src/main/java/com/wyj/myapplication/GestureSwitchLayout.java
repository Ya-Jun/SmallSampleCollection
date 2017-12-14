package com.wyj.myapplication;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class GestureSwitchLayout extends FrameLayout {
    private static final String TAG = "DragLayout";
    private ViewDragHelper dragHelper;
    private View mDragView, contentView;
    private int dragRange;

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
        contentView = findViewById(R.id.imageView2);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (dy > 0) {
                contentView.layout(0, -(contentView.getHeight() - top), getWidth(), top);
            } else if (dy < 0) {
                contentView.layout(0, top + mDragView.getHeight(), getWidth(), top + mDragView.getHeight() + dragRange);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return dragRange;
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
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragRange = contentView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mDragView.layout(0, getHeight() - mDragView.getHeight(), getWidth(), getHeight());
        contentView.layout(0, getHeight(), getWidth(), getHeight() + dragRange);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            dragHelper.cancel();
            return false;
        }
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
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
}

