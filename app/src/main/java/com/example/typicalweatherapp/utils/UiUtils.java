package com.example.typicalweatherapp.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.ActionBar;

import com.example.typicalweatherapp.R;

public final class UiUtils {
    static public void initActionBar(ActionBar actionBar, String title) {
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios);
            actionBar.setTitle(title);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            actionBar.setElevation(0);
        }
    }
}
