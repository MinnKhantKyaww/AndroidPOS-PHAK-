package com.team.androidpos.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.team.androidpos.R;

import static com.team.androidpos.R.drawable.corner_round;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
       // MaterialCardView listContent = findViewById(R.id.list_content);
        NavigationView navigationView = findViewById(R.id.navigationView);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed) {

            private float scale = 6f;
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                /*float slideX = drawerView.getWidth() * slideOffset;
                    listContent.setTranslationX(slideX);
                    listContent.setTranslationZ(slideX / 2);
                    listContent.setScaleX(1 - (slideOffset / scale));
                    listContent.setScaleY(1 - (slideOffset / scale));
                    if(slideOffset == 1f || slideOffset == 0.5f) {
                        listContent.setElevation(8f);
                        listContent.setRadius(20f);
                        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
                    }else if(slideOffset == 0f || slideOffset == 0.5f) {
                        listContent.setElevation(0f);
                        listContent.setRadius(0f);
                        getWindow().setStatusBarColor(Color.parseColor("#1E8B27"));
                    }*/
            }
        };
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        //drawerLayout.setDrawerElevation(0f);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp();
        NavigationUI.setupWithNavController(navigationView, Navigation.findNavController(this, R.id.my_nav_host_fragment));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void switchToggle(boolean enable) {
        toggle.setDrawerIndicatorEnabled(enable);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
