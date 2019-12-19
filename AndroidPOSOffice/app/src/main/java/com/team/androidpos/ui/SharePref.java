package com.team.androidpos.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.team.androidpos.ui.sale.SaleProductFragment;

public class SharePref {

    private SharedPreferences sharedPreferences;

    public SharePref(Context context) {
        this.sharedPreferences = context.getSharedPreferences("sharePref", Context.MODE_PRIVATE);
    }

    public SharePref(SaleProductFragment saleProductFragment) {
        this.sharedPreferences = saleProductFragment.getContext().getSharedPreferences("salePref", Context.MODE_PRIVATE);
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

    public void setSaleNoti (int count) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("SaleCountNoti", count);
        editor.commit();
    }

    public int loadSaleCountNoti() {
        int mode = sharedPreferences.getInt("SaleCountNoti", 0);
        return mode;
    }
}
