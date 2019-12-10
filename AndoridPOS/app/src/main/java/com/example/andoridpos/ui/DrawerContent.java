package com.example.andoridpos.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.andoridpos.R;

public class DrawerContent extends MotionLayout implements DrawerLayout.DrawerListener {

    //DrawerLayout drawerLayout = findViewById(R.id.motionlayouttest);

    public DrawerContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        context = null;
        attrs = null;
        defStyleAttr = 0;
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
       // drawerLayout.addDrawerListener(this);
    }
}