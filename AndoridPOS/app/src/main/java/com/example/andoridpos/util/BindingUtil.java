package com.example.andoridpos.util;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class BindingUtil {

    @BindingAdapter("android:text")
    public static void setLong(TextView textView, long value) {
        textView.setText(String.valueOf(value));
    }
}
