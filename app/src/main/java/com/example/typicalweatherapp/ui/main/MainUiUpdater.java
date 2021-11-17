package com.example.typicalweatherapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.weather.Weather;
import com.example.typicalweatherapp.data.model.weather.WeatherInfo;
import com.example.typicalweatherapp.data.model.weather.daily.Daily;
import com.example.typicalweatherapp.data.model.weather.hourly.Hourly;
import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.example.typicalweatherapp.databinding.BottomSheetMainBinding;
import com.example.typicalweatherapp.databinding.CardWeatherBinding;
import com.example.typicalweatherapp.utils.UiUtils;
import com.example.typicalweatherapp.utils.UnitsFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class MainUiUpdater {
    private final Context context;
    private final ActivityMainBinding binding;
    private final LayoutInflater layoutInflater;
    private final Weather weather;

    private final BottomSheetMainBinding bottomSheet;
    private final UnitsFormatter formatter;
    private final Daily today;

    public MainUiUpdater(
        Context context,
        ActivityMainBinding binding,
        LayoutInflater layoutInflater,
        Weather weather
    ) {
        this.context = context;
        this.binding = binding;
        this.layoutInflater = layoutInflater;
        this.weather = weather;

        this.bottomSheet = this.binding.content.bottomSheet;
        this.formatter = new UnitsFormatter();
        this.today = this.weather.getDaily().get(0);
    }

    public void updateAll() {
        updateMainInfo();

        updateWeatherCards();

        updateDayTempCard();
        updateDayHumidityCard();
        updateDayWindSpeed();
        updateDayPressure();
    }

    public void updateMainInfo() {
        formatter.updateTempUnits();
        binding.content.textViewTemperature.setText(
            context.getString(
                formatter.getTempPlaceholderId(),
                formatter.formatTemp(weather.getCurrent().getTemp())
            )
        );
        binding.content.textViewCurrentDate.setText(
            UiUtils.formatDate(today.getDt())
        );
    }

    public void updateWeatherCards() {
        formatter.updateTempUnits();

        LinearLayoutCompat weatherCards = bottomSheet.layoutWeatherCards;
        weatherCards.removeAllViews();

        for (int i = 0; i < 4; i++) {
            View card = layoutInflater.inflate(
                R.layout.card_weather,
                weatherCards,
                false
            );

            CardWeatherBinding cardBinding = CardWeatherBinding.bind(card);
            Hourly hourly = weather.getHourly().get(i * 6);

            cardBinding.textViewTime.setText(
                new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(
                    new Date(hourly.getDt() * 1000L)
                )
            );

            WeatherInfo hourlyWeather = hourly.getWeather().get(0);
            cardBinding.imageViewWeather.setImageDrawable(
                UiUtils.getWeatherDrawable(context, hourlyWeather)
            );

            cardBinding.textViewDegrees.setText(
                context.getString(
                    formatter.getTempPlaceholderId(),
                    formatter.formatTemp(hourly.getTemp())
                )
            );

            weatherCards.addView(card);
        }
    }

    public void updateDayTempCard() {
        formatter.updateTempUnits();

        TextView textView = bottomSheet.cardDayTemp.textViewCardInfo;
        textView.setText(
            context.getString(
                formatter.getTempPlaceholderId(),
                formatter.formatTemp(today.getTemp().getDay())
            )
        );
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_thermometer, 0, 0, 0
        );
    }

    public void updateDayHumidityCard() {
        TextView textView = bottomSheet.cardDayHumidity.textViewCardInfo;
        textView.setText(
            context.getString(R.string.humidity_p, today.getHumidity())
        );
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_humidity, 0, 0, 0
        );
    }

    public void updateDayWindSpeed() {
        formatter.updateSpeedUnits();

        TextView textView = bottomSheet.cardDayWindSpeed.textViewCardInfo;
        textView.setText(
            context.getString(
                formatter.getSpeedPlaceholderId(),
                formatter.formatSpeed(today.getWindSpeed())
            )
        );
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_breeze, 0, 0, 0
        );
    }

    public void updateDayPressure() {
        formatter.updatePressureUnits();

        TextView textView = bottomSheet.cardDayPressure.textViewCardInfo;
        textView.setText(
            context.getString(
                formatter.getPressurePlaceholderId(),
                formatter.formatPressure(today.getPressure())
            )
        );
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_barometer, 0, 0, 0
        );
    }
}
