package com.example.typicalweatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UiFormatter {
    private final SharedPreferences preferences;

    private int tempPlaceholderId = R.string.temperature_c;
    private boolean isTempUnitsF = false;

    private int speedPlaceholderId = R.string.wind_speed_m_s;
    private boolean isSpeedUnitsKmh = false;

    private int pressurePlaceholderId = R.string.pressure_hpa;
    private boolean isPressureUnitsMmhg = false;

    public UiFormatter(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void updateAllUnits() {
        updateTempUnits();
        updateSpeedUnits();
        updatePressureUnits();
    }

    public void updateTempUnits() {
        isTempUnitsF = preferences.getBoolean("units_temp", false);
        if (isTempUnitsF) {
            tempPlaceholderId = R.string.temperature_f;
        } else {
            tempPlaceholderId = R.string.temperature_c;
        }
    }

    public int formatTemp(double temp) {
        if (isTempUnitsF) {
            temp = temp * 1.8 + 32.0;
        }
        return (int) Math.round(temp);
    }

    public void updateSpeedUnits() {
        isSpeedUnitsKmh = preferences.getBoolean("units_speed", false);
        if (isSpeedUnitsKmh) {
            speedPlaceholderId = R.string.wind_speed_km_h;
        } else {
            speedPlaceholderId = R.string.wind_speed_m_s;
        }
    }

    public double formatSpeed(double speed) {
        if (isSpeedUnitsKmh) {
            speed *= 3.6;
        }
        return speed;
    }

    public void updatePressureUnits() {
        isPressureUnitsMmhg = preferences.getBoolean("units_pressure", false);
        if (isPressureUnitsMmhg) {
            pressurePlaceholderId = R.string.pressure_mmhg;
        } else {
            pressurePlaceholderId = R.string.pressure_hpa;
        }
    }

    public int formatPressure(int pressure) {
        if (isPressureUnitsMmhg) {
            pressure = (int) (pressure * 0.75);
        }
        return pressure;
    }

    public String formatDate(int dt) {
        // TODO                                                     HARDCODED
        return new SimpleDateFormat("d MMMM", new Locale("en")).format(
            new Date(dt * 1000L)
        );
    }

    public Drawable getWeatherDrawable(Context context, @NonNull WeatherInfo weatherInfo) {
        int drawableId = R.drawable.ic_baseline_help_outline_24;

        if (weatherInfo.getDescription().contains("few clouds")) {
            drawableId = R.drawable.w_partly_cloudy;
        } else {
            switch (weatherInfo.getMain()) {
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
        }

        return AppCompatResources.getDrawable(context, drawableId);
    }

    public int getTempPlaceholderId() {
        return tempPlaceholderId;
    }

    public int getSpeedPlaceholderId() {
        return speedPlaceholderId;
    }

    public int getPressurePlaceholderId() {
        return pressurePlaceholderId;
    }
}
