package com.example.andoridpos.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.drawerlayout.widget.DrawerLayout;

public class DrawerContent extends MotionLayout implements DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;

    public DrawerContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        Float motionLayout = getProgress();
        motionLayout = slideOffset;
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() != DrawerContent.this) {
            drawerLayout.addDrawerListener(this);
        }
    }
}
