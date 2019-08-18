package com.example.andoridpos.util;

import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

public class BindingUtil {

    @BindingAdapter("android:text")
    public static void setNumber(TextView textView, long value) {
        textView.setText(String.valueOf(value));
    }

    @BindingAdapter("android:text")
    public static void setNumber(TextView textView, double value) {
        textView.setText(String.valueOf(value));
    }

    @BindingAdapter("android:text")
    public static void setNumber(EditText editText, double value) {
        editText.setText(String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getNumber(EditText editText) {
        String value = editText.getText().toString();
        return value.isEmpty() ? 0.0 : Double.parseDouble(value);
    }
}
