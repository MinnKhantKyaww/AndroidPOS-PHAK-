package com.team.androidpos.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.team.androidpos.R;

public class DrawerContent extends MotionLayout implements DrawerLayout.DrawerListener {

    public DrawerContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawerContent(Context context) {
        super(context);
    }

    public DrawerContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        setProgress(slideOffset);
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
        DrawerLayout drawerLayout = (DrawerLayout)getParent();
        drawerLayout.addDrawerListener(this);
    }


}
