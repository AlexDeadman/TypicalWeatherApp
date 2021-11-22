package com.example.typicalweatherapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
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

class MainWeatherUiUpdater {
    private final Context context;
    private final ActivityMainBinding binding;
    private final LayoutInflater layoutInflater;

    private final BottomSheetMainBinding bottomSheet;
    private final UnitsFormatter formatter;

    private Weather weather;
    private Daily today;
    private FavouriteCity currentCity;

    public MainWeatherUiUpdater(
        Context context,
        ActivityMainBinding binding,
        LayoutInflater layoutInflater
    ) {
        this.context = context;
        this.binding = binding;
        this.layoutInflater = layoutInflater;

        this.bottomSheet = this.binding.content.bottomSheet;
        this.formatter = new UnitsFormatter();
    }

    public void updateAll() {
        updateMainInfo();

        updateWeatherCards();

        updateDayTempCard();
        updateDayHumidityCard();
        updateDayWindSpeed();
        updateDayPressure();
    }

    public void clearAll() {
        binding.content.textViewCity.setText(R.string.city_not_chosen);
        binding.content.textViewCurrentDate.setText("");
        binding.content.textViewTemperature.setText("");
        bottomSheet.layoutHourlyWeatherCards.removeAllViews();
        bottomSheet.buttonWeekForecast.setVisibility(View.INVISIBLE);
        // no need to clean day weather cards because bottom sheet would be locked
    }

    public void updateMainInfo() {
        if (weather != null && currentCity != null) {
            formatter.updateTempUnits();
            binding.content.textViewCity.setText(UiUtils.formatCityText(currentCity));
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
    }

    public void updateWeatherCards() {
        if (weather != null) {
            formatter.updateTempUnits();

            LinearLayoutCompat weatherCards = bottomSheet.layoutHourlyWeatherCards;
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
    }

    public void updateDayTempCard() {
        if (today != null) {
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
    }

    public void updateDayHumidityCard() {
        if (today != null) {
            TextView textView = bottomSheet.cardDayHumidity.textViewCardInfo;
            textView.setText(
                context.getString(R.string.humidity_p, today.getHumidity())
            );
            textView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_humidity, 0, 0, 0
            );
        }
    }

    public void updateDayWindSpeed() {
        if (today != null) {
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
    }

    public void updateDayPressure() {
        if (today != null) {
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

    public void setWeather(Weather weather) {
        this.weather = weather;
        this.today = this.weather.getDaily().get(0);
    }

    public void setCurrentCity(FavouriteCity currentCity) {
        this.currentCity = currentCity;
    }
}
