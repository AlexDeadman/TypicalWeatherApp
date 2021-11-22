package com.example.typicalweatherapp.ui.main;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.weather.Weather;
import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.example.typicalweatherapp.databinding.BottomSheetMainBinding;
import com.example.typicalweatherapp.ui.about.AboutActivity;
import com.example.typicalweatherapp.ui.addcity.AddCityActivity;
import com.example.typicalweatherapp.ui.favourite.FavouriteActivity;
import com.example.typicalweatherapp.ui.settings.SettingsActivity;
import com.example.typicalweatherapp.ui.weekforecast.WeekForecastActivity;
import com.example.typicalweatherapp.utils.Constants;
import com.example.typicalweatherapp.utils.UiUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
    implements
    NavigationView.OnNavigationItemSelectedListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private ActivityMainBinding binding;

    private BottomSheetBehavior<LinearLayoutCompat> bottomSheetBehavior;
    private MaterialButton weekForecastButton;
    private ProgressBar progressBar;

    private MainViewModel viewModel;
    private Weather mWeather;
    private boolean loadError;

    private MainWeatherUiUpdater weatherUiUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        UiUtils.updateTheme();
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBottomSheet();

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        progressBar = binding.content.progressBar;

        binding.content.buttonMenu.setOnClickListener(v -> binding.drawerLayout.open());
        binding.content.buttonAddCity.setOnClickListener(v ->
            startActivity(new Intent(this, AddCityActivity.class))
        );

        App.getPreferences().registerOnSharedPreferenceChangeListener(this);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        updateWeather();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        if (itemId == R.id.nav_favourite) {
            startActivity(new Intent(this, FavouriteActivity.class));
        }

        if (itemId == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.drawerLayout.close();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isOpen()) {
            drawer.close();
        } else {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (weatherUiUpdater != null) {
            if (key.equals(Constants.UNITS_TEMP_PREF_KEY)) {
                weatherUiUpdater.updateMainInfo();
                weatherUiUpdater.updateWeatherCards();
                weatherUiUpdater.updateDayTempCard();
            }

            if (key.equals(Constants.UNITS_SPEED_PREF_KEY)) {
                weatherUiUpdater.updateDayWindSpeed();
            }

            if (key.equals(Constants.UNITS_PRESSURE_PREF_KEY)) {
                weatherUiUpdater.updateDayPressure();
            }
        }

        if (key.equals(Constants.CURRENT_CITY_INDEX_PREF_KEY)) {
            progressBar.setVisibility(View.VISIBLE);
            viewModel.fetchWeather();
        }
    }

    private void initBottomSheet() {
        BottomSheetMainBinding bottomSheetBinding = binding.content.bottomSheet;
        weekForecastButton = bottomSheetBinding.buttonWeekForecast;

        LinearLayoutCompat bottomSheetLayout = bottomSheetBinding.getRoot();

        LayoutTransition transition = new LayoutTransition();
        transition.setAnimateParentHierarchy(false);
        bottomSheetLayout.setLayoutTransition(transition);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setDraggable(false);

        LinearLayoutCompat dayWeatherLayout = bottomSheetBinding.layoutDayWeatherCards;

        bottomSheetBehavior.addBottomSheetCallback(
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (!loadError) {
                        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                            if (weekForecastButton.getVisibility() != View.GONE) {
                                weekForecastButton.setVisibility(View.INVISIBLE);
                            }
                            weekForecastButton.setVisibility(View.GONE);
                            dayWeatherLayout.setVisibility(View.VISIBLE);
                        }
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            dayWeatherLayout.setVisibility(View.INVISIBLE);
                            weekForecastButton.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            }
        );

        weekForecastButton.setOnClickListener(v -> {
            if (!loadError) {
                Intent intent = new Intent(this, WeekForecastActivity.class);
                intent.putExtra("dailies", mWeather.getDaily());
                startActivity(intent);
            }
        });
    }

    void updateWeather() {
        viewModel.fetchWeather();

        weatherUiUpdater = new MainWeatherUiUpdater(
            this,
            binding,
            getLayoutInflater()
        );

        viewModel.getNoCurrentCity().observe(
            this,
            value -> {
                if (value) {
                    Toast.makeText(this, R.string.city_not_chosen, Toast.LENGTH_SHORT).show();

                    bottomSheetBehavior.setDraggable(false);
                    weekForecastButton.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    weatherUiUpdater.clearAll();

                    startActivity(new Intent(this, AddCityActivity.class));
                }
            }
        );

        viewModel.getLoadError().observe(this, value -> loadError = value);

        if (!loadError) {
            viewModel.getWeather().observe(
                this,
                weather -> {
                    mWeather = weather;
                    weatherUiUpdater.setWeather(mWeather);
                    weatherUiUpdater.setCurrentCity(viewModel.getCurrentCity());

                    bottomSheetBehavior.setDraggable(true);
                    weekForecastButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    weatherUiUpdater.updateAll();
                }
            );
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }
}