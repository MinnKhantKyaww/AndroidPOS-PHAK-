package com.team.androidpos.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

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

    private int AnimatedNumber = 1;

    private ConstraintLayout rootbg;
    private ValueAnimator reboundAnimator;
    private ConstraintLayout navHeaderLayout;

    private float mCurProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharePrefs = new SharePref(this);

        if (sharePrefs.loadNightMode() == true) {
            setTheme(R.style.darktheme);
        }

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        listContent = findViewById(R.id.list_content);
        NavigationView navigationView = findViewById(R.id.navigationView);

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_switch);

        MenuItem saleMenuItem = navigationView.getMenu().findItem(R.id.saleProductFragment);
        getSlideLeftNav();
        startVectorAnimation();
        SwitchCompat drawerSwitch = menuItem.getActionView().findViewById(R.id.drawer_switch);
        //AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if (sharePrefs.loadNightMode() == true) {
            drawerSwitch.setChecked(true);
        }
        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharePrefs.setNightMode(true);
                    MainActivity.this.recreate();
                } else {
                    //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharePrefs.setNightMode(false);
                    MainActivity.this.recreate();
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
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        // finish();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSlideLeftNav();
        }
    }

    @Override
    public void recreate() {
        drawerLayout.closeDrawer(GravityCompat.START, true);
        super.recreate();
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

    @Override
    protected void onResume() {
        super.onResume();
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
                listContent.setTranslationZ(5f);
                listContent.setElevation(6f);
                listContent.setRadius(20f);
                //animProgress();
                if (slideOffset == 1f || slideOffset == 0.5f) {
                    listContent.animate().translationZ(listContent.getZ() * 2)
                            .setDuration(1000)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .start();
                    startVectorAnimation();
                    //listContent.setElevation(6f);
                    //listContent.setRadius(20f);
                    /*getWindow().setStatusBarColor(Color.parseColor("#242424"));*/
                } else if (slideOffset == 0f || slideOffset == 0.5f) {
                    listContent.setElevation(0f);
                    listContent.setRadius(0f);
                    startVectorAnimation();
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

    private void startVectorAnimation() {

        AnimatedVectorDrawableCompat drawableCompat = null;
        rootbg = findViewById(R.id.constrainLayout);
        navHeaderLayout = findViewById(R.id.nav_header_layout);

        switch (AnimatedNumber) {
             case 1:
                 drawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animate_wave_1);
                 break;

             case 2:
                 drawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animate_wave_2);
                 break;

             case 3:
                 drawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animate_wave_3);
                 break;

             case 4:
                 drawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animate_wave_4);
                 break;

             case 5:
                 drawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animate_wave_5);
                 AnimatedNumber = 0;
                 break;
            default:
                drawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animate_wave_5);
        }

        AnimatedNumber++;
        rootbg.setBackground(drawableCompat);
        //navigationView.setBackground(drawableCompat);
        //navHeaderLayout.setBackground(drawableCompat);
        assert drawableCompat != null;
        drawableCompat.start();
    }

    private void animProgress() {
        if (mCurProgress != 2f) {
            if (reboundAnimator != null) {
                reboundAnimator.cancel();
            }
            reboundAnimator = ValueAnimator.ofFloat(2f);

            reboundAnimator.setDuration(2000);
            reboundAnimator.setInterpolator(new FastOutSlowInInterpolator());

            reboundAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    reboundAnimator = null;
                }
            });
            reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    startVectorAnimation();
                }
            });
            reboundAnimator.start();
        }
    }

}
