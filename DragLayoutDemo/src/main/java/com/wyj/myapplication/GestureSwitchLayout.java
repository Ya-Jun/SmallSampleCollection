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
    private View mDragView, contentView, contentView2;
    private int dragRange;
    private OnViewDragStateChangedListener onViewDragStateChangedListener;
    private boolean isInit = true;

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
        contentView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        contentView2 = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        contentView.setId(R.id.imageView2);
        contentView2.setId(R.id.imageView3);
        addView(contentView);
        addView(contentView2);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            contentView2.layout(0, -(contentView2.getHeight() - top), getWidth(), top);
            contentView.layout(0, top + mDragView.getHeight(), getWidth(), top + mDragView.getHeight() + dragRange);
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

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_DRAGGING) {

            } else if (state == ViewDragHelper.STATE_IDLE) {
                initLayout();
            }
            if (onViewDragStateChangedListener != null) {
                onViewDragStateChangedListener.OnViewDragStateChanged(state);
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
        if (isInit) {
            initLayout();
//            isInit = false;
        }
    }

    private void initLayout(){
        mDragView.layout(0, 0, getWidth(), getHeight());
        contentView.layout(0, getHeight(), getWidth(), getHeight() * 2);
        contentView2.layout(0, -getHeight(), getWidth(), 0);
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

