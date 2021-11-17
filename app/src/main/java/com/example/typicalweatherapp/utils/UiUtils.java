package com.example.typicalweatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.weather.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UiUtils {

    @NonNull
    public static String formatDate(int dt) {
        // TODO HARDCODED
        return new SimpleDateFormat("d MMMM", new Locale("en")).format(
            new Date(dt * 1000L)
        );
    }

    public static Drawable getWeatherDrawable(Context context, @NonNull WeatherInfo weatherInfo) {
        int drawableId = R.drawable.ic_baseline_help_outline_24;

        if (weatherInfo.getDescription().contains("few clouds")) {
            drawableId = R.drawable.w_partly_cloudy;
        } else {
            switch (weatherInfo.getMain()) {
                case "Clear":
                    drawableId = R.drawable.w_sunny;
                    break;
                case "Drizzle":
                case "Clouds":
                case "Atmosphere":
                    drawableId = R.drawable.w_cloudy;
                    break;
                case "Rain":
                    drawableId = R.drawable.w_rainy;
                    break;
                case "Thunderstorm":
                    drawableId = R.drawable.w_thunderstorm;
                    break;
                case "Snow":
                    drawableId = R.drawable.w_snowy;
                    break;
            }
        }

        return AppCompatResources.getDrawable(context, drawableId);
    }

    public static void updateTheme() {
        String theme = App.getPreferences().getString("theme", "sys_theme");
        int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

        if (theme.equals("dark")) {
            mode = AppCompatDelegate.MODE_NIGHT_YES;
        }
        if (theme.equals("light")) {
            mode = AppCompatDelegate.MODE_NIGHT_NO;
        }

        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
