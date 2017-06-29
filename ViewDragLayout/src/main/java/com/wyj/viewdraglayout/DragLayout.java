package com.wyj.viewdraglayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class DragLayout extends LinearLayout {
    private static final String TAG = "DragLayout";
    private ViewDragHelper dragHelper;

    private TextView mDragView1;
    private View mDragView2;
    private View mDragView3;

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, callback);

        // 设置边界触摸回调被激活
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_TOP | ViewDragHelper.EDGE_RIGHT | ViewDragHelper.EDGE_BOTTOM);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         * 指定View是否可拖拽
         * @param child 用户捕获的view
         * @param pointerId
         * @return 捕获的view是否可拖动
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView1 || child == mDragView3;
        }

        /**
         * @param state 拖拽的状态
         *
         * @see # STATE_IDLE 拖拽结束
         * @see # STATE_DRAGGING 正在拖拽
         * @see # STATE_SETTLING 正在被放置，这个状态不会出现
         */
        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_DRAGGING) {
                mDragView1.setText("正在拖拽");
            } else if (state == ViewDragHelper.STATE_IDLE) {
                mDragView1.setText("拖拽结束");
            }
        }

        /**
         * 当View的位置发生改变是回调
         * @param changedView 当前发生位置改变的View
         * @param left view左上角新X坐标
         * @param top view左上角新Y坐标
         * @param dx 俩次回调X轴方向移动的距离
         * @param dy 俩次回调Y轴方向移动的距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.d(TAG, "onViewPositionChanged left" + left);
            Log.d(TAG, "onViewPositionChanged top" + top);
            Log.d(TAG, "onViewPositionChanged dx" + dx);
            Log.d(TAG, "onViewPositionChanged dy" + dy);
            Log.d(TAG, "----------------------");
        }

        /**
         * 当view被捕获时回调
         *
         * @param capturedChild 被捕获的view
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {

        }

        /**
         * 手指释放时候回调该方法
         * @param releasedChild 被释放的子view
         * @param xvel 手指的X轴速度，以像素每秒钟离开屏幕
         * @param yvel 手指的Y轴速度，以像素每秒钟离开屏幕
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 向下，向右为正，否则为负
            if (yvel > 0) {
                smoothToBottom(releasedChild);
                Toast.makeText(getContext(), "释放时，速度向下大于0", Toast.LENGTH_SHORT).show();
            } else if (yvel < 0) {
                smoothToTop(releasedChild);
                Toast.makeText(getContext(), "释放时，速度向上大于0", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 当触摸到父布局边界时回调（必须调用ViewDragHelper.setEdgeTrackingEnabled才会生效）
         *
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            if (edgeFlags == ViewDragHelper.EDGE_TOP) {
                Toast.makeText(getContext(), "到上边界了", Toast.LENGTH_SHORT).show();
            } else if (edgeFlags == ViewDragHelper.EDGE_BOTTOM) {
                Toast.makeText(getContext(), "到下边界了", Toast.LENGTH_SHORT).show();
            } else if (edgeFlags == ViewDragHelper.EDGE_LEFT) {
                Toast.makeText(getContext(), "到左边界了", Toast.LENGTH_SHORT).show();
            } else if (edgeFlags == ViewDragHelper.EDGE_RIGHT) {
                Toast.makeText(getContext(), "到右边界了", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return false;
        }

        /**
         * 当没有捕获到子view时，通过触摸边界拖拽子view（场景：当view在屏幕外，无法捕获view，只能通过触摸屏幕边界先让他滚回来）
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            dragHelper.captureChildView(mDragView2, pointerId);
            dragHelper.captureChildView(mDragView3, pointerId);
        }

        /**
         * 返回捕获的view Z轴的坐标（即当前view在布局的第几层）
         * @param index
         * @return
         */
        @Override
        public int getOrderedChildIndex(int index) {
            Log.d(TAG, "getOrderedChildIndex index" + index);
            return index;
        }

        /**
         * 该view水平方向拖动的范围(子控件消耗点击事件，例如按钮时候才回调)
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        /**
         * 该view垂直方向拖动的范围(子控件消耗点击事件，例如按钮时候才回调)
         * @param child
         * @return
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        /**
         * 垂直方向限制View的拖动范围
         * @param child 被拖动的子View
         * @param top 移动过程中Y轴的坐标
         * @param dy
         * @return view的新位置
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            // 限定view上下边界（以view左上角坐标点为准）
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - child.getHeight();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }

        /**
         * 水平方向限制View的拖动范围
         * @param child 被拖动的子View
         * @param left 移动过程中X轴的坐标
         * @param dx
         * @return view的新位置
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView1 = (TextView) getChildAt(0);
        mDragView2 = getChildAt(1);
        mDragView3 = getChildAt(2);
    }

    public void smoothToTop(View view) {
        // 动画平滑的移动到指定位置
        if (dragHelper.smoothSlideViewTo(view, getPaddingLeft(), getPaddingTop())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void smoothToBottom(View view) {
        // 动画平滑的移动到指定位置
        if (dragHelper.smoothSlideViewTo(view, getPaddingLeft(), getHeight() - view.getHeight())) {
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

