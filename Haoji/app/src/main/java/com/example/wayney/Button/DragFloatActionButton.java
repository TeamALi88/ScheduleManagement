package com.example.wayney.Button;

/**
 * Created by WayneY on 2017/11/9.
 */

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import com.example.wayney.Button.ScreenUtils;

    public class DragFloatActionButton extends FloatingActionButton {
        private int screenWidth;
        private int screenHeight;
        private int screenWidthHalf;
        private int statusHeight;
        private int virtualHeight;
        public DragFloatActionButton(Context context) {
            super(context);
            init();
        }
        public DragFloatActionButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }
        public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }
        private void init() {
            screenWidth = ScreenUtils.getScreenWidth(getContext());
            screenWidthHalf = screenWidth / 2;
            screenHeight = ScreenUtils.getScreenHeight(getContext());
            statusHeight = ScreenUtils.getStatusHeight(getContext());
            virtualHeight=ScreenUtils.getVirtualBarHeigh(getContext());
        }
        private int lastX;
        private int lastY;
        private boolean isDrag;
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int rawX = (int) event.getRawX();
            int rawY = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    isDrag = false;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    lastX = rawX;
                    lastY = rawY;
                    Log.e("down---->", "getX=" + getX() + "；screenWidthHalf=" + screenWidthHalf);
                    break;
                case MotionEvent.ACTION_MOVE:
                    isDrag = true;
                    //计算手指移动了多少
                    int dx = rawX - lastX;
                    int dy = rawY - lastY;
                    //这里修复一些手机无法触发点击事件的问题
                    int distance= (int) Math.sqrt(dx*dx+dy*dy);
                    Log.e("distance---->",distance+"");
                    if(distance<3){//给个容错范围，不然有部分手机还是无法点击
                        isDrag=false;
                        break;
                    }
                    float x = getX() + dx;
                    float y = getY() + dy;
                    //检测是否到达边缘 左上右下
                    x = x < 0 ? 0 : x > screenWidth - getWidth() ? screenWidth - getWidth() : x;
                    // y = y < statusHeight ? statusHeight : (y + getHeight() >= screenHeight ? screenHeight - getHeight() : y);
                    if (y<0){
                        y=0;
                    }
                    if (y>screenHeight-statusHeight-getHeight()){
                        y=screenHeight-statusHeight-getHeight();
                    }
                    setX(x);
                    setY(y);
                    lastX = rawX;
                    lastY = rawY;
                    Log.e("move---->", "getX=" + getX() + "；screenWidthHalf=" + screenWidthHalf + " " + isDrag+" statusHeight="+statusHeight+ " virtualHeight"+virtualHeight+ " screenHeight"+ screenHeight+" getHeight="+getHeight()+" y"+y);
                    break;
                case MotionEvent.ACTION_UP:
                    if (isDrag) {
                        //恢复按压效果
                        setPressed(false);
                        Log.e("ACTION_UP---->", "getX=" + getX() + "；screenWidthHalf=" + screenWidthHalf);
                    }
                    Log.e("up---->",isDrag+"");
                    break;
            }
            //如果是拖拽则消耗事件，否则正常传递即可。
            return isDrag || super.onTouchEvent(event);
        }
    }
