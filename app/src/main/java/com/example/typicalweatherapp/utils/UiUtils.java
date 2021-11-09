package com.example.typicalweatherapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;

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

    static public Drawable getWeatherResource(
        Context context,
        String weatherClass,
        String weatherDescription
    ) {
        int drawableId = R.drawable.ic_baseline_help_outline_24;

        if (weatherDescription.equals("few clouds")) {
            drawableId = R.drawable.w_partly_cloudy;
        }

        switch (weatherClass) {
            case ("Clear"):
                drawableId = R.drawable.w_sunny;
                break;
            case ("Drizzle"):
            case ("Clouds"):
            case ("Atmosphere"):
                drawableId = R.drawable.w_cloudy;
                break;
            case ("Rain"):
                drawableId = R.drawable.w_rainy;
                break;
            case ("Thunderstorm"):
                drawableId = R.drawable.w_thunderstorm;
                break;
            case ("Snow"):
                drawableId = R.drawable.w_snowy;
                break;
        }

        return AppCompatResources.getDrawable(context, drawableId);
    }
}
