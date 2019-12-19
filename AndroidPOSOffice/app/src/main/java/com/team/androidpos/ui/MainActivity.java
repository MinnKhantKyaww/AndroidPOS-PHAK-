package com.team.androidpos.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.team.androidpos.R;
import com.team.androidpos.ui.sale.SaleProductFragment;

import static com.team.androidpos.R.drawable.corner_round;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private MaterialCardView listContent;

    private SharePref sharePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharePrefs = new SharePref(this);

        if(sharePrefs.loadNightMode() == true) {
            setTheme(R.style.darktheme);
        }

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        listContent = findViewById(R.id.list_content);
        NavigationView navigationView = findViewById(R.id.navigationView);

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_switch);

        SwitchCompat drawerSwitch = menuItem.getActionView().findViewById(R.id.drawer_switch);
        getSlideLeftNav();
        //AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if(sharePrefs.loadNightMode() == true) {
            drawerSwitch.setChecked(true);
        }
        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharePrefs.setNightMode(true);
                    restartApp();
                } else {
                    //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharePrefs.setNightMode(false);
                    restartApp();
                }
            }
        });


//       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                    drawerLayout.closeDrawer(Gravity.RIGHT);
//                } else {
//                    drawerLayout.openDrawer(Gravity.RIGHT);
//                }
//            }
//        });



        NavigationUI.setupWithNavController(navigationView, Navigation.findNavController(this, R.id.my_nav_host_fragment));

    }

    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSlideLeftNav();
        }
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

    private void getSlideLeftNav() {

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed) {

            private float scale = 6f;
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                listContent.setTranslationX(slideX);
                listContent.setTranslationZ(slideX / 2);
                listContent.setScaleX(1 - (slideOffset / scale));
                listContent.setScaleY(1 - (slideOffset / scale));
                listContent.setTranslationZ(3f);
                listContent.setElevation(6f);
                listContent.setRadius(20f);
                if(slideOffset == 1f || slideOffset == 0.5f) {
                    listContent.animate().translationZ(listContent.getZ() * 2)
                            .setDuration(1000)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .start();
                    //listContent.setElevation(6f);
                    //listContent.setRadius(20f);
                    /*getWindow().setStatusBarColor(Color.parseColor("#242424"));*/
                }else if(slideOffset == 0f || slideOffset == 0.5f) {
                    listContent.setElevation(0f);
                    listContent.setRadius(0f);
                    /*getWindow().setStatusBarColor(Color.parseColor("#242424")); //#1E8B27*/
                }
            }
        };
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setDrawerElevation(0f);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(v -> {
            Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp();
        });
    }

}
