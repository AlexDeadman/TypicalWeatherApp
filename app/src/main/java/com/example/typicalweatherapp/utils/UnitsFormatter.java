package com.example.typicalweatherapp.utils;

import android.content.SharedPreferences;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;

public class UnitsFormatter {
    private final SharedPreferences preferences;

    private int tempPlaceholderId = R.string.temperature_c;
    private boolean isTempUnitsF = false;

    private int speedPlaceholderId = R.string.wind_speed_m_s;
    private boolean isSpeedUnitsKmh = false;

    private int pressurePlaceholderId = R.string.pressure_hpa;
    private boolean isPressureUnitsMmhg = false;

    public UnitsFormatter() {
        this.preferences = App.getPreferences();
    }

    public void updateAllUnits() {
        updateTempUnits();
        updateSpeedUnits();
        updatePressureUnits();
    }

    public void updateTempUnits() {
        isTempUnitsF = preferences.getBoolean(Constants.unitsTempPrefKey, false);
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
        isSpeedUnitsKmh = preferences.getBoolean(Constants.unitsSpeedPrefKey, false);
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
        isPressureUnitsMmhg = preferences.getBoolean(Constants.unitsPressurePrefKey, false);
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
