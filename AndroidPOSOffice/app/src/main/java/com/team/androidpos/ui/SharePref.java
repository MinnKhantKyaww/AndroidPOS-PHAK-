package com.team.androidpos.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    private SharedPreferences sharedPreferences;

    public SharePref(Context context) {
        this.sharedPreferences = context.getSharedPreferences("sharePref", Context.MODE_PRIVATE);
    }

    public void setNightMode (Boolean mode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", mode);
        editor.commit();
    }

    public Boolean loadNightMode() {
        Boolean mode = sharedPreferences.getBoolean("NightMode", false);
        return mode;
    }
}
